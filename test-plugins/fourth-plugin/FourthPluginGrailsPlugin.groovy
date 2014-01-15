import org.springframework.util.Assert

class FourthPluginGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.7 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Your name"
    def authorEmail = ""
    def title = "Plugin summary/headline"
    def description = '''\\
Brief description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/fourth-plugin"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
        Assert.notNull application
        Assert.notNull application.mergedConfig
        Assert.isTrue 'plugin4'.equals(application.mergedConfig.grails.plugins.fourth.value1)
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
        Assert.notNull application
        Assert.notNull application.mergedConfig
        Assert.isTrue 'plugin4'.equals(application.mergedConfig.grails.plugins.fourth.value1)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
        Assert.notNull application
        Assert.notNull application.mergedConfig
        Assert.isTrue 'plugin4'.equals(application.mergedConfig.grails.plugins.fourth.value1)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
        Assert.notNull application
        Assert.notNull application.mergedConfig
        Assert.isTrue 'plugin4'.equals(application.mergedConfig.grails.plugins.fourth.value1)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.

        Assert.notNull application
        Assert.notNull application.mergedConfig
        Assert.isTrue 'plugin4'.equals(application.mergedConfig.grails.plugins.fourth.value1)
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.

        Assert.notNull application
        Assert.notNull application.mergedConfig
        Assert.isTrue 'plugin4'.equals(application.mergedConfig.grails.plugins.fourth.value1)
    }

    def afterConfigMerge = { config ->
        System.setProperty('fourthPluginAfterConfigMerge', 'true')
    }
}
