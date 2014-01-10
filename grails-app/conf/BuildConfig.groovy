grails.project.work.dir = 'target'

grails.project.dependency.resolution = {
    inherits 'global'
    log 'warn'
    repositories {
        grailsPlugins()
        grailsHome()
        if ("$grailsVersion" > "1.2.5") {
            grailsCentral()
        }
    }
    dependencies {
    }
    plugins {
        build ':tomcat:7.0.47', ':release:3.0.1', ':rest-client-builder:1.0.3', {
            export = false
        }
        runtime ':hibernate:3.6.10.6', {
            export = false
        }
    }
}

if (appName == "plugin-config") {
    grails.plugin.location.'first-plugin' = 'test-plugins/first-plugin'
    grails.plugin.location.'second-plugin' = 'test-plugins/second-plugin'
    grails.plugin.location.'third-plugin' = 'test-plugins/third-plugin'
    grails.plugin.location.'fourth-plugin' = 'test-plugins/fourth-plugin'
}
