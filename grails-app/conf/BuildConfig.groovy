grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
    inherits("global") {
    }
    log "warn"
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
        build ":tomcat:7.0.47", {
            export = false
        }
        runtime ":hibernate:3.6.10.6", {
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
