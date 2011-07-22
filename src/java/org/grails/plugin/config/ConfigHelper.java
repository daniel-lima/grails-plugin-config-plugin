package org.grails.plugin.config;

import groovy.util.ConfigObject;

import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.plugins.GrailsPluginManager;

public interface ConfigHelper {

    public abstract ConfigObject getMergedConfig(GrailsApplication grailsApplication);

    public abstract ConfigObject getMergedConfig(GrailsPluginManager pluginManager,
            GrailsApplication grailsApplication);

    public abstract ConfigObject getMergedConfig();

}