/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiStream;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

/**
 * @author peter
 *
 */

@Path("/stream") 
@Api(value = "/stream") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class GeneralRestFulAPI {
	
	GeneralService generalService = new GeneralService();

	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get stream list.", 
			notes = "Stream details.", 
			response = ApiStream.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	
	@GET
	@Path("/{accountId}")  
	public List<Object> getAllStream(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth) {
		
		List<Object>  response = new ArrayList<>();
		
		ApiResponse re = new ApiResponse();
		re.setMessage("error");
		re.setDescription("User not authenticated");

		response.add(re);

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}
		
		return generalService.getStreamList(accountId); 
	}
	
	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get stream object.", 
			notes = "Stream details.", 
			response = ApiStream.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	
	@GET
	@Path("/{accountId}/{uuid}")   
	public Object getStream(@PathParam("accountId") String accountId, @PathParam("uuid") String uuid,
			     @HeaderParam("authorization") String auth) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}
		
		return generalService.getStream(accountId,uuid);
	}
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param apiStream
	 * @return
	 */
	
	@ApiOperation(value = "Add new stream.", 
			notes = "Stream basic details.", 
			response = ApiStream.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	
	@POST
	@Path("/{accountId}")  
	public Object newStream(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth, ApiStream apiStream) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}
		
		return generalService.putStream(apiStream);
	}
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param apiStream
	 * @return
	 */
	
	@ApiOperation(value = "Update stream info.", 
			notes = "Stream details.", 
			response = ApiStream.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	
	@PUT
	@Path("/{accountId}")  
	public Object updateStream(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth, ApiStream apiStream) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}
		
		return generalService.updateStream(apiStream);
	}
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "Delete a stream.", 
			notes = "Stream id.", 
			response = ApiStream.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	
	@DELETE
	@Path("/{accountId}")  
	public Object deleteStream(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth, String uuid) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}
		
		return generalService.deleteStream(accountId,uuid);
	}
	
	

}
