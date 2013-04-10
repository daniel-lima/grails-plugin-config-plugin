package org.plugin.config

import grails.test.GrailsUnitTestCase
import grails.util.Environment

import org.grails.plugin.config.AbstractConfigHelper

class ConfigObjectProxyTests extends GrailsUnitTestCase {

    void testUncheckedMap() {
        ConfigObject config = loadConfig('PluginConfigDefaultConfig')
        Map configAsMap = AbstractConfigHelper.ConfigObjectProxy.newInstance(config, false)

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
        Map configAsMap = AbstractConfigHelper.ConfigObjectProxy.newInstance(config, true)

        assertNotNull configAsMap
        assertTrue configAsMap instanceof Map
        assertTrue configAsMap instanceof Writable

        assertFalse configAsMap.containsKey('groovy')

        shouldFail(IllegalArgumentException) {
            configAsMap.groovy
        }

        assertNotNull configAsMap.grails
        assertNotNull config.groovy
        assertNotNull configAsMap.groovy
    }

    ConfigObject loadConfig(String className) {
        ClassLoader cl = Thread.currentThread().contextClassLoader ?: this.class.classLoader
        ConfigSlurper cs = new ConfigSlurper(Environment.current.name)
        ConfigObject config = cs.parse(cl.loadClass(className))
        return config
    }
}
