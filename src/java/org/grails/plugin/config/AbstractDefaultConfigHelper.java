/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.plugin.config;

import grails.util.Environment;
import grails.util.GrailsNameUtils;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.MetaProperty;
import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;
import groovy.util.Eval;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.commons.cfg.ConfigurationHelper;
import org.codehaus.groovy.grails.plugins.GrailsPlugin;
import org.codehaus.groovy.grails.plugins.GrailsPluginManager;
import org.codehaus.groovy.grails.plugins.PluginManagerAware;
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * 
 * @author Daniel Henrique Alves Lima
 * 
 */
public abstract class AbstractDefaultConfigHelper implements
        PluginManagerAware, GrailsApplicationAware, InitializingBean,
        ConfigHelper {

    private static final String GRAILS_PLUGIN_SUFFIX = "GrailsPlugin";
    private static final String DEFAULT_CONFIG_SUFFIX = "DefaultConfig";

    protected final Log log = LogFactory.getLog(getClass());
    private GrailsApplication grailsApplication;
    private GrailsPluginManager pluginManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("afterPropertiesSet()");
        }
        enhanceGrailsApplication(this.pluginManager, this.grailsApplication);
    }

    @Override
    public void setGrailsApplication(GrailsApplication grailsApplication) {
        if (log.isDebugEnabled()) {
            log.debug("setGrailsApplication(): " + grailsApplication);
        }
        this.grailsApplication = grailsApplication;
    }

    @Override
    public void setPluginManager(GrailsPluginManager pluginManager) {
        if (log.isDebugEnabled()) {
            log.debug("setPluginManager(): " + pluginManager);
        }
        this.pluginManager = pluginManager;
    }

    @Override
    public ConfigObject getMergedConfig(GrailsApplication grailsApplication) {
        GrailsPluginManager pluginManager = getPluginManager(grailsApplication);
        return getMergedConfig(pluginManager, grailsApplication);
    }

    @Override
    public ConfigObject getMergedConfig(GrailsPluginManager pluginManager,
            GrailsApplication grailsApplication) {
        enhanceGrailsApplication(pluginManager, grailsApplication);

        ConfigObject mergedConfig = (ConfigObject) Eval.x(grailsApplication,
                "x.mergedConfig");
        return mergedConfig;
    }

    @Override
    public ConfigObject getMergedConfig() {
        return getMergedConfig(this.pluginManager, this.grailsApplication);
    }

    public abstract void enhanceGrailsApplication(
            GrailsPluginManager pluginManager,
            GrailsApplication grailsApplication);

    public void enhanceGrailsApplication(GrailsApplication grailsApplication) {
        enhanceGrailsApplication(getPluginManager(grailsApplication),
                grailsApplication);
    }

    public abstract void notifyConfigChange(GrailsApplication grailsApplication);

    protected ConfigObject buildMergedConfig(
            GrailsPluginManager pluginManager,
            GrailsApplication grailsApplication) {
        if (log.isDebugEnabled()) {
            log.debug("getMergedConfigImpl()");
        }

        Assert.notNull(pluginManager);
        Assert.notNull(grailsApplication);

        @SuppressWarnings("unchecked")
        final Map<String, Object> myConfig = new LinkedHashMap<String, Object>(
                (Map<String, Object>) Eval.x(grailsApplication,
                        "x.config.grails.plugins.config"));
        final boolean autoDefaultEnabled = myConfig.get("autoEnabled") == null
                || "true".equals(myConfig.get("autoEnabled").toString());

        List<Class<?>> defaultConfigClasses = new ArrayList<Class<?>>();

        final String defaultConfigPropertyName = GrailsNameUtils
                .getPropertyName(DEFAULT_CONFIG_SUFFIX);

        /* Use plugin processing order. */
        for (GrailsPlugin plugin : pluginManager.getAllPlugins()) {
            if (plugin.isEnabled()) {
                Class<?> defaultConfigClass = null;

                Class<?> pluginClass = plugin.getPluginClass();
                GroovyObject pluginInstance = plugin.getInstance();
                MetaClass pluginMetaClass = InvokerHelper
                        .getMetaClass(pluginClass);
                Object defaultConfigObj = null;

                MetaProperty prop = pluginMetaClass.hasProperty(pluginInstance,
                        defaultConfigPropertyName);
                if (prop != null) {
                    if ((prop.getModifiers() & Modifier.STATIC) != Modifier.STATIC) {
                        defaultConfigObj = prop.getProperty(pluginInstance);
                    } else {
                        defaultConfigObj = prop.getProperty(null);
                    }
                }

                if (defaultConfigObj != null) {
                    if (defaultConfigObj instanceof Class) {
                        defaultConfigClass = (Class<?>) defaultConfigObj;
                    } else {
                        log.error("getMergedConfigImpl(): "
                                + pluginClass.getName() + "."
                                + defaultConfigPropertyName + " returned "
                                + defaultConfigObj.getClass());
                        continue;
                    }
                } else if (autoDefaultEnabled) {
                    String configName = pluginClass.getSimpleName();
                    if (configName.endsWith(GRAILS_PLUGIN_SUFFIX)) {
                        configName = configName.replace(GRAILS_PLUGIN_SUFFIX,
                                DEFAULT_CONFIG_SUFFIX);
                    } else {
                        configName = configName + DEFAULT_CONFIG_SUFFIX;
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("getMergedConfigImpl(): configName "
                                + configName);
                    }

                    defaultConfigClass = grailsApplication
                            .getClassForName(configName);
                }

                if (defaultConfigClass != null) {
                    defaultConfigClasses.add(defaultConfigClass);
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("getMergedConfigImpl(): defaultConfigClasses "
                    + defaultConfigClasses);
        }

        GroovyClassLoader classLoader = null;

        {
            ClassLoader parentClassLoader = grailsApplication.getClassLoader();
            if (parentClassLoader == null) {
                parentClassLoader = Thread.currentThread()
                        .getContextClassLoader();
                if (parentClassLoader == null) {
                    parentClassLoader = this.getClass().getClassLoader();
                }
            }

            if (parentClassLoader instanceof GroovyClassLoader) {
                classLoader = (GroovyClassLoader) parentClassLoader;
            } else {
                classLoader = new GroovyClassLoader(parentClassLoader);
            }
        }

        ConfigObject config = new ConfigObject();
        mergeInDefaultConfigs(config, defaultConfigClasses, classLoader);

        ConfigurationHelper.initConfig(config, null, classLoader);
        config.merge(grailsApplication.getConfig());

        return config;
    }

  

    protected void mergeInDefaultConfigs(ConfigObject config,
            List<Class<?>> defaultConfigClasses, GroovyClassLoader classLoader) {
        ConfigSlurper configSlurper = new ConfigSlurper(Environment
                .getCurrent().getName());
        configSlurper.setClassLoader(classLoader);
        for (Class<?> defaultConfigClass : defaultConfigClasses) {
            configSlurper.setBinding(config);
            ConfigObject newConfig = configSlurper.parse(defaultConfigClass);
            config.merge(newConfig);
        }
    }

    protected GrailsPluginManager getPluginManager(
            GrailsApplication grailsApplication) {
        ApplicationContext parentContext = grailsApplication.getParentContext();
        GrailsPluginManager pluginManager = (GrailsPluginManager) parentContext
                .getBean("pluginManager");
        return pluginManager;
    }

}
