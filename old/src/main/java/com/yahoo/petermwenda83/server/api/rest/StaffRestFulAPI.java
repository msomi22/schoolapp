/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
//import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.server.api.rest.bean.APIStaff;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiStaffFull;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;

/**
 * 
 * 
 * @author peter
 *
 */
@Path("/staff") 
@Api(value = "/staff") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class StaffRestFulAPI {

	StaffService staffService = new StaffService();
	
	@ApiOperation(value = "Register a new staff.", 
		    notes = "Returns whethet staff was Registred successfully or not.", 
		    response = APIStaff.class)
	@POST
	@Path("/{accountId}")
	public ApiResponse putStatff(@PathParam("accountId") String accountId, 
			@HeaderParam("authorization") String auth , APIStaff apiStaff){

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			
			ApiResponse error = new ApiResponse("error");
			return error; 
			
		}else {
        	
        	Staff staff = new Staff();
    		staff.setAccountId(accountId);
    		staff.setAcessLevelId(apiStaff.getAcessLevelId());
    		staff.setStaffNo(apiStaff.getStaffNo());
    		staff.setFirstname(apiStaff.getFirstname());
    		staff.setMiddlename(apiStaff.getMiddlename());
    		staff.setLastname(apiStaff.getLastname());
    		staff.setGender(apiStaff.getGender().toUpperCase());
    		staff.setMobile(apiStaff.getMobile());
    		staff.setEmail(apiStaff.getEmail());
    		staff.setUsername(apiStaff.getUsername());
    		staff.setPassword(apiStaff.getPassword());
    		
    		ApiResponse put = staffService.putStaff(staff);
    		return put; 
    		
        }
		
		
	}
	
	
	@ApiOperation(value = "Update a staff.", 
		    notes = "Returns whethet staff was updated successfully or not.", 
		    response = APIStaff.class)
	
	@PUT
	@Path("/{accountId}")
	public ApiResponse updateStatff(@PathParam("accountId") String accountId, 
			@HeaderParam("authorization") String auth , ApiStaffFull ApiStaffFull){

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			
			ApiResponse error = new ApiResponse("error");
			return error; 
			
		}else{
			
			ApiResponse put = staffService.updateStaff(ApiStaffFull);
			return put; 
		}
		
	}
	
	
	@Path("/{staffId}/subjects")
	public SubClassRestFulAPI getSubjectService(){
		return new SubClassRestFulAPI(); 
	}
	
	
	
	/**
	 * @param mobile
	 * @return
	 */
	public static boolean validMobileNo(String mobile){
		boolean valid = false;
		
		if(mobile.length() == 9 && StringUtils.isNumeric(mobile)){
			valid = true;
		}
		
		return valid;
	}

	
}
