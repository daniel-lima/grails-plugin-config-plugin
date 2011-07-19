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

import groovy.util.ConfigObject;

import org.codehaus.groovy.grails.commons.ArtefactHandler;
import org.codehaus.groovy.grails.commons.ArtefactHandlerAdapter;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware;
import org.codehaus.groovy.grails.plugins.support.aware.GrailsConfigurationAware;

/**
 * 
 * @author Daniel Henrique Alves Lima
 * 
 */
public class DefaultConfigArtefactHandler extends ArtefactHandlerAdapter
        implements ArtefactHandler, GrailsApplicationAware, GrailsConfigurationAware {

    public static final String TYPE = DefaultGrailsDefaultConfigClass.DEFAULT_CONFIG;
    public static final String PLUGIN_NAME = "defaultConfig";

    public DefaultConfigArtefactHandler(String type, Class<?> grailsClassType,
            Class<?> grailsClassImpl, String artefactSuffix) {
        super(TYPE, GrailsDefaultConfigClass.class,
                DefaultGrailsDefaultConfigClass.class,
                DefaultGrailsDefaultConfigClass.DEFAULT_CONFIG, false);
    }

    @Override
    public void setConfiguration(ConfigObject co) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setGrailsApplication(GrailsApplication grailsApplication) {
        // TODO Auto-generated method stub
        
    }

}
