/**
 * 
 */
package com.yahoo.petermwenda83.server.api;

import java.util.*;

import javax.ws.rs.core.Application;

import com.yahoo.petermwenda83.server.api.rest.ConfigRestFulAPI;
import com.yahoo.petermwenda83.server.api.rest.FinanceRestFulAPI;
import com.yahoo.petermwenda83.server.api.rest.GeneralRestFulAPI;
import com.yahoo.petermwenda83.server.api.rest.ReportRestFulAPI;
import com.yahoo.petermwenda83.server.api.rest.StaffRestFulAPI;
import com.yahoo.petermwenda83.server.api.rest.StudentRestFulAPI;
import com.yahoo.petermwenda83.server.api.rest.SubClassRestFulAPI;
import com.yahoo.petermwenda83.server.api.rest.admin.AdminRestFulAPI;
import com.yahoo.petermwenda83.server.api.safaricom.SafaricomAPI;

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
		beanConfig.setSchemes(new String[]{"https,http"});
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
	        set.add(SafaricomAPI.class);
	        set.add(GeneralRestFulAPI.class);
	        set.add(AdminRestFulAPI.class);
	        set.add(ReportRestFulAPI.class);
	        set.add(ConfigRestFulAPI.class);
	        set.add(FinanceRestFulAPI.class);
	        
	        set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
	        set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

	        return set;
	    }
	 
	 @Override
	    public Set<Object> getSingletons() {
	        return singletons;
	    }

}
