package org.grails.plugin.config

import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.plugins.GrailsPluginManager;

class DefaultConfigHelper extends AbstractDefaultConfigHelper {

    @Override
    protected void enhanceGrailsApplication(GrailsPluginManager pluginManager,
    GrailsApplication grailsApplication) {
        if (log.isDebugEnabled()) {
            log.debug("Enhancing ${grailsApplication} ${pluginManager}")
        }
        MetaClass mc = grailsApplication.metaClass
        if (!mc.respondsTo('getMergedConfig')) {
            mc._mergedConfig = null
            mc.getMergedConfig = {
                if (delegate._mergedConfig == null) {
                    delegate._mergedConfig = super.buildGetMergedConfig(pluginManager, grailsApplication)
                }

                return delegate._mergedConfig
            }
        }
    }
}
