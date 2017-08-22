/**
 * 
 */
package com.yahoo.petermwenda83.server.api;

import java.util.*;

import javax.ws.rs.core.Application;

import com.yahoo.petermwenda83.server.api.rest.StaffRestFulAPI;
import com.yahoo.petermwenda83.server.api.rest.StudentRestFulAPI;
import com.yahoo.petermwenda83.server.api.rest.SubClassRestFulAPI;

import io.swagger.jaxrs.config.BeanConfig;

/**
 * @author peter
 *
 */
public class APIApplication extends Application{

	HashSet<Object> singletons = new HashSet<Object>();

	public APIApplication(){
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.2");
		beanConfig.setSchemes(new String[]{"http"});
		beanConfig.setHost("localhost:8080/school");
		beanConfig.setBasePath("/webapi");
		beanConfig.setFilterClass("com.yahoo.petermwenda83.server.api.ApiAuthorizationFilterImpl");
		beanConfig.setResourcePackage("com.yahoo.petermwenda83.server.api.rest");
		beanConfig.setScan(true);

	}
	
	 @Override
	    public Set<Class<?>> getClasses() {
	        HashSet<Class<?>> set = new HashSet<Class<?>>();

	        set.add(SubClassRestFulAPI.class);
	        set.add(StaffRestFulAPI.class);
	        set.add(StudentRestFulAPI.class);
	        
	        set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
	        set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

	        return set;
	    }
	 
	 @Override
	    public Set<Object> getSingletons() {
	        return singletons;
	    }

}
