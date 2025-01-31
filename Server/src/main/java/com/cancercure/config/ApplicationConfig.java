package com.cancercure.config;


import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class ApplicationConfig extends ResourceConfig {
	 public ApplicationConfig() {
		 this.packages("com.cancercure.main");
		 register(org.glassfish.jersey.media.multipart.MultiPartFeature.class);
		 registerInstances(new LoggingFilter(Logger.getLogger(ApplicationConfig.class.getName()), true));
	    }
}
