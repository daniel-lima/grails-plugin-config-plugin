/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.plugin.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.commons.AbstractGrailsClass;

/**
 * 
 * @author Daniel Henrique Alves Lima
 * 
 */
public class DefaultGrailsDefaultConfigClass extends AbstractGrailsClass
        implements GrailsDefaultConfigClass {

    private final Log log = LogFactory.getLog(getClass()); 
    
    public static final String DEFAULT_CONFIG = "DefaultConfig";

    public DefaultGrailsDefaultConfigClass(Class<?> clazz) {
        super(clazz, DEFAULT_CONFIG);
        if (log.isDebugEnabled()) {
            log.debug("init(): " + clazz);
        }
    }

}
