/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.admin;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.yahoo.petermwenda83.server.api.rest.RestAUth;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.admin.ApiAccount;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * http://localhost:8080/school/webapi/admin/account
 * @author peter
 *
 */
@Path("/admin") 
@Api(value = "/admin") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class AdminRestFulAPI {
	
	AdminService adminService = new AdminService();
	
	
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Register a new school account.", 
			notes = "Account details.", 
			response = ApiAccount.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "account exist.") 
	} )
	
	
	@POST
	@Path("/account")  
	public Object addAccount(ApiAccount apiAccount, @HeaderParam("authorization") String auth) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, apiAccount.getUuid())){
			return response; 
		}
		
		return adminService.newAccount(apiAccount);
	}
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Update school account details.", 
			notes = "Account details.", 
			response = ApiAccount.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "account doesn't exist.") 
	} )
	
	@PUT
	@Path("/account")  
	public Object updateAccount(ApiAccount apiAccount, @HeaderParam("authorization") String auth) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, apiAccount.getUuid())){
			return response; 
		}
		
		return adminService.updateAccount(apiAccount);
	}
	
	
	
	
	
	
	
	
	
	/*@GET
	@Path("/data")  
	public List<ApiAccData> getAccData() { 
		return adminService.getAccData();
	}

	
	@POST
	@Path("/data")  
	public String getAccFromLopy(String data) {
		System.out.println(data); 
		adminService.putData(data);
		return data;
	}
	
	*/

}
