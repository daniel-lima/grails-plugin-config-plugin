package org.grails.plugin.config

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class DefaultConfigHelperTests extends GroovyTestCase {

    def grailsApplication
    //GrailsPluginManager pluginManager

    void testMergedConfigInstance() {
        ConfigObject mergedConfig = grailsApplication.mergedConfig
        assertNotNull mergedConfig
        assertNotSame mergedConfig, grailsApplication.config
        assertSame mergedConfig, grailsApplication.mergedConfig
        assertSame mergedConfig, grailsApplication.mergedConfig
        assertEquals mergedConfig, grailsApplication.mergedConfig
    }

    void testMergedConfig() {
        ConfigObject config = grailsApplication.config
        ConfigObject mergedConfig = grailsApplication.mergedConfig // Avoid to change the grailsApplication.mergedConfig

        /* heyHo won't work for Grails 2.0.0 because FilterDefaultConfig.groovy
         * will belong to config plugin instead of to filters plugin. */
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
        ConfigObject firstConfig = new ConfigSlurper().parse(grailsApplication.mainContext.classLoader.loadClass('FirstPluginDefaultConfig'))
        ConfigObject thirdConfig = new ConfigSlurper().parse(grailsApplication.mainContext.classLoader.loadClass('ThirdPluginDefaultConfig'))
        println "firstConfig ${firstConfig}"
        println "thirdConfig ${thirdConfig}"

        assertEquals true, mergedConfig.grails.plugins.first.value1
        assertEquals 'plugin1', mergedConfig.grails.plugins.first.value2

        assertEquals 'Plugin3', mergedConfig.grails.plugins.third.value1
        assertEquals thirdConfig.grails.plugins.third.value1, mergedConfig.grails.plugins.third.value1

        assertEquals 'abc', mergedConfig.grails.plugins.second.value1
        assertEquals 'Plugin3-2', mergedConfig.grails.plugins.second.value2

        assertEquals 'plugin3 has some value here', thirdConfig.grails.plugins.third.valueToOverride
        assertEquals 'app has a different value', mergedConfig.grails.plugins.third.valueToOverride

        assertEquals 'not null value', firstConfig.grails.plugins.first.valueToOverride
        assertEquals null, mergedConfig.grails.plugins.first.valueToOverride

        assertEquals 'plugin4', mergedConfig.grails.plugins.fourth.value1
    }

    void testAfterConfigMerge() {
        assertEquals 'Plugin3', System.properties.firstPluginAfterConfigMerge
        assertEquals 'true', System.properties.fourthPluginAfterConfigMerge
    }

    /*
    void testNotifyConfigChange() {
        ConfigObject mergedConfig = grailsApplication.mergedConfig
        assertSame mergedConfig, grailsApplication.mergedConfig
        GrailsPlugin plugin = pluginManager.getGrailsPluginForClassName('PluginConfigGrailsPlugin')
        assertNotNull plugin
        plugin.instance.onConfigChange(null)
        //configHelper.notifyConfigChange()
        assertNotSame mergedConfig, grailsApplication.mergedConfig
        assertEquals mergedConfig, grailsApplication.mergedConfig
    }*/

    void testAsMap() {
        ConfigObject cfg = grailsApplication.mergedConfig
        Map cfgAsMap = cfg.asMap(false)
        assertNotNull cfgAsMap

        assertEquals 'abc', cfgAsMap.grails.plugins.second.value1
        assertNull cfgAsMap.grails.plugins.second.value_1
        assertEquals 'Plugin3-2', cfgAsMap.grails.plugins.second.value2

        grailsApplication.mergedConfig.asMap(false)
        grailsApplication.mergedConfig.asMap(true)
        grailsApplication.mergedConfig.asMap()
    }
}
