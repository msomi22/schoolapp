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
import com.google.gson.Gson;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.server.api.rest.bean.APIStaff;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiStaffFull;
import com.yahoo.petermwenda83.server.api.rest.bean.AuthErr;

/**
 * 
 * http://localhost:8080/school/webapi/staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD/
 * 
 * http://862bdf67.ngrok.io/school/webapi/staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD
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
	
	@POST
	@Path("/{accountId}")
	public String putStatff(@PathParam("accountId") String accountId, 
			@HeaderParam("authorization") String auth , APIStaff apiStaff){

		Gson gson = new Gson();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			
			AuthErr error = new AuthErr("error");
			return gson.toJson(error); 
			
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
    		
    		AuthErr put = staffService.putStaff(staff);
    		return gson.toJson(put); 
    		
        }
		
		
	}
	
	
	
	
	@PUT
	@Path("/{accountId}")
	public String updateStatff(@PathParam("accountId") String accountId, 
			@HeaderParam("authorization") String auth , ApiStaffFull ApiStaffFull){

		Gson gson = new Gson();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			
			AuthErr error = new AuthErr("error");
			return gson.toJson(error); 
			
		}else{
			
			AuthErr put = staffService.updateStaff(ApiStaffFull);
			return gson.toJson(put); 
		}
		
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
		
		
		
		<staff>
		   <acessLevelId>BDF7F33D-1936-43F3-B14B-8FC3EA3A1265</acessLevelId>
		   <email>peter.mwenda@adcea.com</email>
		   <firstname>Peter</firstname>
		   <gender>M</gender>
		   <lastname>Njeru</lastname>
		   <middlename>Mwenda</middlename>
		   <mobile>718953974</mobile>
		   <password>12345667890</password>
		   <staffNo>456</staffNo>
		   <username>msomi22</username>
		</staff>
		
		
		
		{  
		   "acessLevelId":"C3915245-00EE-4EF4-9898-ACE59683DD60",
		   "staffNo":"1234",
		   "isActive":"1",
		   "firstname":"NICK",
		   "middlename":"KARANI",
		   "lastname":"NK",
		   "gender":"M",
		   "mobile":"7736636633",
		   "email":"na",
		   "username":"principal",
		   "password":"demo",
		   "uuid":"38EFA2D4-352D-4BC0-887F-9CA227950501",
		   "accountId":"E3CDC578-37BA-4CDB-B150-DAB0409270CD",
		   "logedUserId":"5498156A-FE83-43F4-9592-36281E377FE4",
		   "logedUserAccessId":"1CC7F06E-9938-4850-81FB-9CC249C7CFA2"
		}


		
		
		
		
	 */

}
