package org.grails.plugin.config

import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.plugins.GrailsPluginManager;

class DefaultConfigHelper extends AbstractDefaultConfigHelper {

    @Override
    protected void enhanceGrailsApplication(GrailsPluginManager pluginManager,
            GrailsApplication grailsApplication) {
        grailsApplication.metaClass.getMergedConfig = super.buildGetMergedConfig(pluginManager, grailsApplication)
    }

}
