/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.admin;


import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yahoo.petermwenda83.server.api.filter.AccountFilter;
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
	
	
	@ApiOperation(value = "Get Account details .", 
			notes = "Returns Account object given the Id.", 
			response = ApiAccount.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Account Id not found.") 
	} )
	
	@GET
	@Path("/{accountId}") 
	public Object getStaff(@PathParam("accountId") String accountId, 
			@HeaderParam("authorization") String auth) {
		
		if(!RestAUth.isAdminAuthenticated(auth)){

			ApiResponse error = new ApiResponse("error");
			return error; 

		}else {
			return adminService.getAccount(accountId); 
		}
	
	}
	
	@ApiOperation(value = "Get List of all Accounts.", 
			notes = "Returns List of all the Accounts.", 
			response = ApiAccount.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Resource not found!") 
	} )
	
	@GET
	@Path("/all") 
	public Object getStaffList(@HeaderParam("authorization") String auth, @BeanParam  AccountFilter filter) {
		
		if(!RestAUth.isAdminAuthenticated(auth)){ 

			ApiResponse error = new ApiResponse("error");
			return error; 

		}else {
			return adminService.getAccountList(); 
		}
		
	}
	
	
	
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Register a new school account.", 
			notes = "Account object to add.", 
			response = ApiResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "account exist.") 
	} )
	
	
	@POST
	@Path("/account")  
	public Object addAccount(ApiAccount apiAccount, @HeaderParam("authorization") String auth) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isAdminAuthenticated(auth)){
			return response; 
		}
		
		//return adminService.newAccount(apiAccount);
		
		response.setMessage("error");
		response.setDescription("Ah! you are busted!");
		return response;
	}
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Update school account details.", 
			notes = "Account object to update.", 
			response = ApiResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "account doesn't exist.") 
	} )
	
	@PUT
	@Path("/account")  
	public Object updateAccount(ApiAccount apiAccount, @HeaderParam("authorization") String auth) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isAdminAuthenticated(auth)){
			return response; 
		}
		
		return adminService.updateAccount(apiAccount);
	}
	

}
