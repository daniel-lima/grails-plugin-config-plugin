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
package org.grails.plugin.config

import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.plugins.GrailsPluginManager;
import org.springframework.util.Assert;

/**
 * 
 * @author Daniel Henrique Alves Lima
 *
 */
class DefaultConfigHelper extends AbstractConfigHelper {
    
    private WeakHashMap appsWitCachedConfig = new WeakHashMap()

    @Override
    public void enhanceGrailsApplication(GrailsPluginManager pluginManager,
    GrailsApplication grailsApplication) {
        if (log.isDebugEnabled()) {
            log.debug("Enhancing ${grailsApplication} ${pluginManager}")
        }
        MetaClass mc = grailsApplication.metaClass
        if (!mc.respondsTo(grailsApplication, 'getMergedConfig')) {
            log.debug('Adding getMergedConfig()')
            
            mc._mergedConfig = null
            mc.getMergedConfig = {boolean reload = false ->
                
                
                log.debug("delegate ${delegate}")
                GrailsApplication app = (GrailsApplication) delegate
                log.debug("delegate._mergedConfig ${delegate._mergedConfig?'[...]': 'null'}")
                if (delegate._mergedConfig == null || reload) {                   
                    delegate._mergedConfig = super.buildMergedConfig(
                        pluginManager, app);
                    appsWitCachedConfig.put(app, null)
                }

                return delegate._mergedConfig
            }
        }
        
        Assert.notEmpty mc.respondsTo(grailsApplication, 'getMergedConfig')
        enhanceConfigObjectClass()
    }

    @Override
    public void notifyConfigChange() {
        if (log.isDebugEnabled()) {
            log.debug("Notifying config change for ${appsWitCachedConfig.keySet()}")
        }
        for (app in appsWitCachedConfig.keySet()) {
            GrailsApplication grailsApplication = (GrailsApplication) app
            MetaClass mc = grailsApplication.metaClass
            if (mc.respondsTo(grailsApplication, 'getMergedConfig')) {
                log.debug("Clearing _mergedConfig for ${grailsApplication}")
                grailsApplication._mergedConfig = null
            } 
        } 
        appsWitCachedConfig.clear()
    }
    
    protected void enhanceConfigObjectClass() {
        if (log.isDebugEnabled()) {
            log.debug("Enhancing ${ConfigObject}")
        }
        
        MetaClass mc = ConfigObject.metaClass
        if (!mc.respondsTo(ConfigObject, 'asMap')) {
            mc.asMap = {boolean checked = false ->
                ConfigObject delegate = (ConfigObject) delegate
                return AbstractConfigHelper.ConfigObjectProxy.newInstance(delegate, checked) 
            }           
            
            Assert.notEmpty mc.respondsTo(ConfigObject, 'asMap')
        }
    }
}
