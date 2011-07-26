package org.plugin.config

import org.grails.plugin.config.AbstractConfigHelper;
import org.springframework.jmx.export.assembler.AbstractConfigurableMBeanInfoAssembler;

import grails.test.*
import grails.util.Environment;

class ConfigObjectProxyTests extends GrailsUnitTestCase {
    
    void testUncheckedMap() {        
        ConfigObject config = loadConfig('PluginConfigDefaultConfig')
        Map configAsMap = AbstractDefaultConfigHelper.ConfigObjectProxy.newInstance(config, false)
        
        assertNotNull configAsMap
        assertTrue configAsMap instanceof Map
        assertTrue configAsMap instanceof Writable
        
        assertNull configAsMap.groovy
        assertFalse configAsMap.containsKey('groovy')
        assertNotNull configAsMap.grails
        
        assertEquals 'configValue', configAsMap.grails.plugins.config.sampleValue2
        assertEquals config.grails.plugins.config.sampleValue2, configAsMap.grails.plugins.config.sampleValue2
        
        assertNull configAsMap.grails.plugins.config.sampleValue5
        assertFalse configAsMap.grails.plugins.config.containsKey('sampleValue5')
        configAsMap.grails.plugins.config.sampleValue5 = 'xyz'
        assertEquals 'xyz', configAsMap.grails.plugins.config.sampleValue5
        assertEquals config.grails.plugins.config.sampleValue5, configAsMap.grails.plugins.config.sampleValue5
        
        
        assertEquals 1, configAsMap.grails.plugins.config.sampleValue1
        assertEquals true, configAsMap.grails.plugins.config.autoDefault
    }
    
    
    void testCheckedMap() {
        ConfigObject config = loadConfig('PluginConfigDefaultConfig')
        Map configAsMap = AbstractDefaultConfigHelper.ConfigObjectProxy.newInstance(config, true)
        
        assertNotNull configAsMap
        assertTrue configAsMap instanceof Map
        assertTrue configAsMap instanceof Writable
        
        assertFalse configAsMap.containsKey('groovy')
        try {
            configAsMap.groovy
            fail()
        } catch (IllegalArgumentException e) {
            assertTrue true
        }
         
        assertNotNull configAsMap.grails
        assertNotNull config.groovy 
        assertNotNull configAsMap.groovy
    }
    
    
    def ConfigObject loadConfig(String className) {
        ClassLoader cl = Thread.currentThread().contextClassLoader ?: this.class.classLoader
        ConfigSlurper cs = new ConfigSlurper(Environment.current.name)
        ConfigObject config = cs.parse(cl.loadClass(className))
        return config
    }
    
}
