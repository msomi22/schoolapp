/**
 * 
 */
package com.yahoo.petermwenda83.server.api;

import io.swagger.model.*;
import io.swagger.core.filter.SwaggerSpecFilter;

import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;
import org.slf4j.*;

import java.util.Map;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

/**  
 * 
 * @author peter
 *
 */

public class ApiAuthorizationFilterImpl implements SwaggerSpecFilter {

	static Logger logger = LoggerFactory.getLogger(ApiAuthorizationFilterImpl.class);


	/**
	 * @see io.swagger.core.filter.SwaggerSpecFilter#isOperationAllowed(io.swagger.models.Operation, io.swagger.model.ApiDescription, java.util.Map, java.util.Map, java.util.Map)
	 */
	public boolean isOperationAllowed(Operation operation, ApiDescription api, Map<String, List<String>> params,
			Map<String, String> cookies, Map<String, List<String>> headers) {

		boolean isAuthorized = checkKey(params, headers);
		
		if(isAuthorized) {
			return true;
		}
		
		else {
			
			if(!"GET".equalsIgnoreCase(api.getMethod()) || api.getPath().indexOf("/school") != -1) {
				//return false;
				return true;
			}
			
			else return true;
		}
	}


	/**
	 * @see io.swagger.core.filter.SwaggerSpecFilter#isParamAllowed(io.swagger.models.parameters.Parameter, io.swagger.models.Operation, io.swagger.model.ApiDescription, java.util.Map, java.util.Map, java.util.Map)
	 */
	public boolean isParamAllowed(Parameter parameter, Operation operation, ApiDescription api,Map<String,
			List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {

		boolean isAuthorized = checkKey(params, headers);
		
		if("internal".equals(parameter.getAccess()) && !isAuthorized)
			return false;
		else
			return true;
	}


	/**
	 * @see io.swagger.core.filter.SwaggerSpecFilter#isPropertyAllowed(io.swagger.models.Model, io.swagger.models.properties.Property, java.lang.String, java.util.Map, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean isPropertyAllowed(Model model, Property property, String propertyName, 
			Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
		
		return true;
	}

	/**
	 * @param params
	 * @param headers
	 * @return
	 */
	public boolean checkKey(Map<String, List<String>> params, Map<String, List<String>> headers) {
		String keyValue = null;
		if(params.containsKey("api_key"))
			keyValue = params.get("api_key").get(0);
		else {
			if(headers.containsKey("api_key"))
				keyValue = headers.get("api_key").get(0);
		}
		if("special-key".equals(keyValue))
			return true;
		else
			return false;
	}
}