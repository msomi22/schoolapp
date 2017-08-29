/**
 * 
 */
package com.yahoo.petermwenda83.server.api.safaricom;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yahoo.petermwenda83.server.api.safaricom.bean.SafResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

/** 
 * http://localhost:8080/school/webapi/account/balance
 * http://localhost:8080/school/webapi/account/timeout
 * 
 * @author peter
 *
 */
@Path("/account") 
@Api(value = "/account") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class Callback {

	@ApiOperation(value = "Log Response From Safaricom MPESA.", 
		    notes = "Response Message.", 
		    response = SafResponse.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Account Id not found.") 
	} )
	
	@POST
	@Path("/{balance}") 
	@Produces(value = {MediaType.APPLICATION_JSON})  
	public SafResponse getAcctBalResponse(@PathParam("balance") String balance,SafResponse object) {
		
		System.out.println(object + " --- " + balance);  
		
		return object; 
	}
	
	
	
	
	
	@ApiOperation(value = "Log Timeout Response From Safaricom MPESA.", 
		    notes = "Response Message.", 
		    response = SafResponse.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Account Id not found.") 
	} )
	
	@POST
	@Path("/{timeout}") 
	public SafResponse getAcctBalTimeoutResponse(@PathParam("timeout") String timeout, SafResponse object) {
		
		System.out.println(object + " --- " + timeout);  
		
		return object;
	}
}
