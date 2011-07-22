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
        ConfigObject mergedConfig = grailsApplication.mergedConfig
        assertEquals 123, mergedConfig.grails.plugins.logging.logVal1
        assertEquals 'abc', mergedConfig.grails.plugins.logging.logVal2
    }
}
