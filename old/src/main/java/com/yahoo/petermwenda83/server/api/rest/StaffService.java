/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiStaffFull;
import com.yahoo.petermwenda83.server.api.rest.bean.AuthErr;

/**
 * @author peter
 *
 */
public class StaffService {

	private static StaffDAO staffDAO;
	private static EmailValidator emailValidator;

	static{
		staffDAO = StaffDAO.getInstance();
		emailValidator = EmailValidator.getInstance();
	}


	/**
	 * @param staff
	 * @return
	 */
	public AuthErr putStaff(Staff staff){

		String  response = "";
		AuthErr error = new AuthErr(); 

		String principal = "C3915245-00EE-4EF4-9898-ACE59683DD60";
		String deputy_Principal = "615F04C1-00BF-499C-AC7A-B46B69243AAA";

		if(StringUtils.equals(staff.getAcessLevelId(), principal)){

			if(staffDAO.getStaffByAccessLevel(staff.getAccountId(), principal) != null ){
				response = "Principal can not be added twice";
				error = new AuthErr("error");
				error.setDescription(response);

			}


		}else if(StringUtils.equals(staff.getAcessLevelId(), deputy_Principal)){

			if(staffDAO.getStaffByAccessLevel(staff.getAccountId(), deputy_Principal) != null ){
				response = "Deputy Principal can not be added twice";
				error = new AuthErr("error");
				error.setDescription(response);

			}


		}else{

			if (StringUtils.isBlank(staff.getFirstname())) { 

				error = new AuthErr("error");
				error.setDescription("Firt Name Can't be Empty!"); 

			}else if (StringUtils.isBlank(staff.getMiddlename())) { 

				error = new AuthErr("error");
				error.setDescription("Middle Name Can't be Empty!"); 

			}else if (StringUtils.isBlank(staff.getGender())) { 

				error = new AuthErr("error");
				error.setDescription("Gender Can't be Empty!"); 

			}else if(!validMobileNo(staff.getMobile())){

				error = new AuthErr("error");
				error.setDescription("Phone Number Not Valid!"); 

			}else if (!emailValidator.isValid(staff.getEmail())) {

				error = new AuthErr("error");
				error.setDescription("Email Address Not Valid!"); 

			}else if (StringUtils.isBlank(staff.getUsername())) { 

				error = new AuthErr("error");
				error.setDescription("Username Can't be Empty!"); 

			}else if (StringUtils.isBlank(staff.getPassword())) { 

				error = new AuthErr("error");
				error.setDescription("Password Can't be Empty!"); 

			}else if(staffDAO.getStaffByStaffNo(staff.getAccountId(), staff.getStaffNo()) != null){
				response = "StaffNo already exist.";
				error = new AuthErr("error");
				error.setDescription(response);

			}else if(staffDAO.getStaffByUsername(staff.getAccountId(), staff.getUsername()) != null){
				response = "Staff username already exist.";
				error = new AuthErr("error");
				error.setDescription(response);

			}else if(staffDAO.putStaff(staff)){
				response = "Staff was registered successfully.";
				error = new AuthErr("success");
				error.setDescription(response);

			}else{
				response = "Something went wrong, try again later.";
				error = new AuthErr("error");
				error.setDescription(response);
			}

		}


		return error;
	}




	/**
	 * @param ApiStaffFull
	 * @return
	 */
	public AuthErr updateStaff(ApiStaffFull apiStaffFull){

		AuthErr error = new AuthErr(); 
		String principal = "C3915245-00EE-4EF4-9898-ACE59683DD60";
		String deputy_Principal = "615F04C1-00BF-499C-AC7A-B46B69243AAA";

		apiStaffFull.getLogedUserAccessId();
		apiStaffFull.getLogedUserId();
		boolean allowed = false;
		
		

		//user NOT logged as principal  
		if(!StringUtils.equals(apiStaffFull.getLogedUserAccessId(), principal)){
			//user tries to alter principal 
			if(StringUtils.equals(apiStaffFull.getAcessLevelId(), principal)){ 
				//prevent user from performing the action 
				error = new AuthErr("error");
				error.setDescription("You are performing an illegal action (Altering principal Info!)");  
				
				return error;
			}
			//user NOT deputy and tries to alter deputy principal 
			else if(!StringUtils.equals(apiStaffFull.getLogedUserAccessId(), deputy_Principal) && 
					StringUtils.equals(apiStaffFull.getAcessLevelId(), deputy_Principal)){ 
				//prevent user from performing the action 
				error = new AuthErr("error");
				error.setDescription("You are performing an illegal action (Altering deputy principal Info!)");  
				
				return error;
				
			}else{
				allowed = true;
			}
		}else{

			allowed = true;

		}

		if(allowed){
			
			if (StringUtils.isBlank(apiStaffFull.getFirstname())) { 

				error = new AuthErr("error");
				error.setDescription("Firt Name Can't be Empty!"); 

			}else if (StringUtils.isBlank(apiStaffFull.getMiddlename())) { 

				error = new AuthErr("error");
				error.setDescription("Middle Name Can't be Empty!"); 

			}else if (StringUtils.isBlank(apiStaffFull.getGender())) { 

				error = new AuthErr("error");
				error.setDescription("Gender Can't be Empty!"); 

			}else if(!validMobileNo(apiStaffFull.getMobile())){

				error = new AuthErr("error");
				error.setDescription("Phone Number Not Valid!"); 

			}else if (!emailValidator.isValid(apiStaffFull.getEmail())) {

				error = new AuthErr("error");
				error.setDescription("Email Address Not Valid!"); 

			}else if (StringUtils.isBlank(apiStaffFull.getUsername())) { 

				error = new AuthErr("error");
				error.setDescription("Username Can't be Empty!"); 

			}else if (StringUtils.isBlank(apiStaffFull.getPassword())) { 

				error = new AuthErr("error");
				error.setDescription("Password Can't be Empty!"); 

			}/*else if(staffDAO.getStaffByStaffNo(apiStaffFull.getAccountId(), apiStaffFull.getStaffNo()) != null){

				error = new AuthErr("error");
				error.setDescription("StaffNo already exist.");

			}else if(staffDAO.getStaffByUsername(apiStaffFull.getAccountId(), apiStaffFull.getUsername()) != null){

				error = new AuthErr("error");
				error.setDescription("Staff username already exist.");

			}*/else{
				
				error = new AuthErr("success");
				error.setDescription("Staff was updated successfully.");
				
				
			}
			
			
		}else{
			
			error = new AuthErr("error");
			error.setDescription("Operation not allowed.");
		}
		
		System.out.println("allowed : " + allowed); 

		return error;

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
