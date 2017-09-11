/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiSysConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

/**
 * @author peter
 *
 */

@Path("/config") 
@Api(value = "/config") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class ConfigRestFulAPI {
	
	GeneralService generalService = new GeneralService();

	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param apiSysConfig
	 * @return
	 */

	@ApiOperation(value = "ApiSysConfig object to update.", 
			notes = "Pass apiSysConfig object to be updated.", 
			response = ApiSysConfig.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@PUT
	@Path("{accountId}")   
	public Object updateConfig(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth,
			ApiSysConfig apiSysConfig) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.updateConfig(apiSysConfig); 
	}
	
	
}
