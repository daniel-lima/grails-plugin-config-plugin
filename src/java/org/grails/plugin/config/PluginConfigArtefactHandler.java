package org.grails.plugin.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.commons.ArtefactHandler;
import org.codehaus.groovy.grails.commons.ArtefactInfo;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.commons.GrailsClass;
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware;

@SuppressWarnings("rawtypes")
public class PluginConfigArtefactHandler implements ArtefactHandler,
        GrailsApplicationAware {

    private final Log log = LogFactory.getLog(getClass());

    @Override
    public String getPluginName() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public boolean isArtefact(Class aClass) {
        return false;
    }

    @Override
    public GrailsClass newArtefactClass(Class artefactClass) {
        return null;
    }

    @Override
    public void initialize(ArtefactInfo artefacts) {
    }

    @Override
    public GrailsClass getArtefactForFeature(Object feature) {
        return null;
    }

    @Override
    public boolean isArtefactGrailsClass(GrailsClass artefactGrailsClass) {
        return false;
    }

    @Override
    public void setGrailsApplication(GrailsApplication grailsApplication) {
        if (log.isDebugEnabled()) {
            log.debug("setGrailsApplication(): grailsApplication "
                    + grailsApplication);
        }
        
        new DefaultConfigHelper().enhanceGrailsApplication(grailsApplication);
    }

}
