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
    }
}
