package org.grails.plugin.config

import grails.test.*

class DefaultConfigHelperTests extends GroovyTestCase {

    def grailsApplication
    def configHelper 
    
    void testConfigHelperInstance() {
        assertNotNull configHelper
        assertTrue configHelper instanceof DefaultConfigHelper
    }
    
    void testMergedConfigInstance() {
        ConfigObject mergedConfig = configHelper.mergedConfig
        assertNotNull mergedConfig
        assertNotSame mergedConfig, grailsApplication.config
        assertSame mergedConfig, configHelper.mergedConfig
        assertSame mergedConfig, configHelper.mergedConfig
        mergedConfig = grailsApplication.mergedConfig
        assertNotNull mergedConfig
        assertSame mergedConfig, grailsApplication.mergedConfig
        assertSame mergedConfig, grailsApplication.mergedConfig
    }
    
    void testMergedConfig() {
        ConfigObject config = grailsApplication.config
        ConfigObject mergedConfig = grailsApplication.mergedConfig
        assertEquals 123, mergedConfig.grails.plugins.logging.logVal1
        assertEquals 'abc', mergedConfig.grails.plugins.logging.logVal2
        
        assertTrue 123 == mergedConfig.grails.plugins.logging.logVal1
        assertFalse 123 == config.grails.plugins.logging.logVal1
        
        assertTrue 'abc' == mergedConfig.grails.plugins.logging.logVal2
        assertFalse 'abc' == config.grails.plugins.logging.logVal2
        
        assertEquals true, mergedConfig.grails.plugins.config.autoDefault
        assertEquals 'configValue', mergedConfig.grails.plugins.config.sampleValue2
    }
}
