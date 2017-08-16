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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.google.gson.Gson;
import com.yahoo.petermwenda83.bean.staff.Staff;

/**
 * 
 * http://localhost:8080/school/webapi/staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD/
 * 
 * 
 * @author peter
 *
 */
@Path("/staff") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class StaffRestFulAPI {

	StaffService staffService = new StaffService();
	private static EmailValidator emailValidator;
	
	static {
		emailValidator = EmailValidator.getInstance();
	}

	@PUT 
	@Path("/{accountId}")
	public String putStatff(@PathParam("accountId") String accountId, 
			@HeaderParam("authorization") String auth , APIStaff apiStaff){

		Gson gson = new Gson();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			
			AuthErr error = new AuthErr("error");
			return gson.toJson(error); 
			
		}else if(apiStaff.getMobile().length() != 9 && apiStaff.getMobile().isEmpty() && !StringUtils.isNumeric(apiStaff.getMobile()) ){
			
			AuthErr error = new AuthErr("error");
			error.setDescription("Phone Number Not Valid!"); 
			return gson.toJson(error); 
			
		}else if (!emailValidator.isValid(apiStaff.getEmail())) {
			
			AuthErr error = new AuthErr("error");
			error.setDescription("Email Address Not Valid!"); 
			return gson.toJson(error); 

        }else {
        	
        	Staff staff = new Staff();
    		staff.setAccountId(accountId);
    		staff.setAcessLevelId(apiStaff.getAcessLevelId());
    		staff.setStaffNo(apiStaff.getStaffNo());
    		staff.setFirstname(apiStaff.getFirstname());
    		staff.setMiddlename(apiStaff.getMiddlename());
    		staff.setLastname(apiStaff.getLastname());
    		staff.setGender(apiStaff.getGender());
    		staff.setMobile(apiStaff.getMobile());
    		staff.setEmail(apiStaff.getEmail());
    		staff.setUsername(apiStaff.getUsername());
    		staff.setPassword(apiStaff.getPassword());
    		
    		return gson.toJson(staffService.putStaff(staff)); 
        }
		
		
	}

	/**
	 *   {  
		   "acessLevelId":"BDF7F33D-1936-43F3-B14B-8FC3EA3A1265",
		   "staffNo":"3060",
		   "firstname":"Peter",
		   "middlename":"Mwenda",
		   "lastname":"Njeru",
		   "gender":"M",
		   "mobile":"718953974",
		   "email":"peter.mwenda@adcea.com",
		   "username":"msomi22",
		   "password":"12345667890"
		}
	 */

}
