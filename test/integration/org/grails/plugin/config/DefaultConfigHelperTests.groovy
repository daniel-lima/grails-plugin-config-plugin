package org.grails.plugin.config

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

import grails.test.*

class DefaultConfigHelperTests extends GroovyTestCase {

    def grailsApplication
    def configHelper 
    
    @Override
    void setUp() {
        super.setUp()
        configHelper = grailsApplication.mainContext.getBean(DefaultConfigHelper.class.name) 
    }
    
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
        assertEquals mergedConfig, configHelper.mergedConfig
        mergedConfig = grailsApplication.mergedConfig
        assertNotNull mergedConfig
        assertSame mergedConfig, grailsApplication.mergedConfig
        assertSame mergedConfig, grailsApplication.mergedConfig
        assertEquals mergedConfig, grailsApplication.mergedConfig
    }
    
    void testMergedConfig() {
        ConfigObject config = grailsApplication.config 
        ConfigObject mergedConfig = grailsApplication.mergedConfig // Avoid to change the grailsApplication.mergedConfig
        
        
        assertEquals 123, mergedConfig.grails.plugins.heyHo.logVal1
        assertEquals 'abc', mergedConfig.grails.plugins.heyHo.logVal2
        
        assertTrue 123 == mergedConfig.grails.plugins.heyHo.logVal1
        assertFalse 123 == config.grails.plugins.heyHo.logVal1
        
        assertTrue 'abc' == mergedConfig.grails.plugins.heyHo.logVal2
        assertFalse 'abc' == config.grails.plugins.heyHo.logVal2
        
        assertEquals true, mergedConfig.grails.plugins.config.autoDefault
        assertEquals 'configValue', mergedConfig.grails.plugins.config.sampleValue2
        
        ConfigurationHolder.setConfig null
        grailsApplication.getMergedConfig(true)
    }
    
    
    
    void testPluginMergedConfig() {
        
        ConfigObject mergedConfig = grailsApplication.mergedConfig
        
        assertEquals true, mergedConfig.grails.plugins.first.value1
        assertEquals 'plugin1', mergedConfig.grails.plugins.first.value2
        
        assertEquals 'Plugin3', mergedConfig.grails.plugins.third.value1
        
        assertEquals 'abc', mergedConfig.grails.plugins.second.value1
        assertEquals 'Plugin3-2', mergedConfig.grails.plugins.second.value2
        
    }
    
    void testAfterConfigMerge() {
        
        assertEquals 'Plugin3', System.properties.firstPluginAfterConfigMerge    
    }
    
    
    void testNotifyConfigChange() {
        ConfigObject mergedConfig = grailsApplication.mergedConfig
        assertSame mergedConfig, grailsApplication.mergedConfig
        configHelper.notifyConfigChange(grailsApplication)
        assertNotSame mergedConfig, grailsApplication.mergedConfig
        assertEquals mergedConfig, grailsApplication.mergedConfig
    }
}
