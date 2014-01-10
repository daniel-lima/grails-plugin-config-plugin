import org.codehaus.groovy.grails.commons.GrailsClassUtils
import org.codehaus.groovy.grails.plugins.GrailsPlugin

class PluginConfigGrailsPluginTests extends GroovyTestCase {

    def pluginManager
    def grailsApplication

    void testOnChange() {
        ConfigObject mergedConfig = grailsApplication.mergedConfig
        assertNotNull mergedConfig
        assertSame mergedConfig, grailsApplication.mergedConfig
        assertSame mergedConfig, grailsApplication.mergedConfig
        assertEquals mergedConfig, grailsApplication.mergedConfig

        GrailsPlugin plugin = pluginManager.getGrailsPluginForClassName('PluginConfigGrailsPlugin')
        def pluginInstance = plugin.instance
        //def pluginInstance = new PluginConfigGrailsPlugin()
        pluginInstance.metaClass.application = grailsApplication
        Closure onChange = GrailsClassUtils.getPropertyOrStaticPropertyOrFieldValue (pluginInstance, 'onChange')
        Map event = [ctx: grailsApplication.mainContext, application: grailsApplication]
        onChange(event)
        assertNotSame mergedConfig, grailsApplication.mergedConfig

        Closure onConfigChange = GrailsClassUtils.getPropertyOrStaticPropertyOrFieldValue (pluginInstance, 'onConfigChange')
        onConfigChange(event)
    }
}
