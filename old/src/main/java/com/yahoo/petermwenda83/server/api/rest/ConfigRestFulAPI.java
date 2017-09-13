/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yahoo.petermwenda83.server.api.rest.bean.ApiGradingScale;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiMisc;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiSysConfig;
import com.yahoo.petermwenda83.server.servlet.util.sms.AccountBalance;

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
	@Path("/{accountId}")   
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



	@ApiOperation(value = "ApiMisc object to update.", 
			notes = "Pass ApiMisc object to be updated.", 
			response = ApiMisc.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@PUT
	@Path("/misc/{accountId}")    
	public Object updateMusc(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth, ApiMisc misc) {

		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.updateMisc(accountId,misc);
	}

	/**
	 * 
	 * @param accountId
	 * @param id
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get Grading Scale object.", 
			notes = "Returns a Grading Scale object for the given id.", 
			response = ApiGradingScale.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId/id not found.") 
	} )
	@GET
	@Path("/scale/{accountId}/{uuid}")  
	public Object getGradingScaleById(@PathParam("accountId") String accountId, @PathParam("uuid") String uuid, 
			@HeaderParam("authorization") String auth) {

		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.getGradingScaleById(accountId, uuid); 
	}

	/**
	 * 
	 * @param accountId
	 * @param categoryId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get Grading Scale object.", 
			notes = "Returns a Grading Scale object for the given categoryId.", 
			response = ApiGradingScale.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId/categoryId not found.") 
	} )
	@GET
	@Path("/scale/cat/{accountId}/{categoryId}")  
	public Object getGradingScale(@PathParam("accountId") String accountId,@PathParam("categoryId") String categoryId,
			@HeaderParam("authorization") String auth) {

		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.getGradingScaleByCat(accountId, categoryId); 
	}


	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param scale
	 * @return
	 */
	@ApiOperation(value = "Grading Scale object to add.", 
			notes = "Returns whether the scale was added.", 
			response = ApiGradingScale.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )

	@Path("/scale/{accountId}") 
	@POST
	public Object addGradingScale(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth,
			ApiGradingScale scale) {

		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.addGradingScale(accountId,scale);
	}

	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param scale
	 * @return
	 */
	@ApiOperation(value = "Grading Scale object to update.", 
			notes = "Returns whether the scale was updated.", 
			response = ApiGradingScale.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@Path("/scale/{accountId}") 
	@PUT
	public Object updateGradingScale(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth,
			ApiGradingScale scale) {

		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.updateGradingScale(accountId,scale);
	}


	@Path("/smsbal/{accountId}") 
	@GET
	public String getSMSBalance(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response.toString();  
		}
		return AccountBalance.getBalance(accountId);
	}












}
