/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.bean.staff.TeacherSubject;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.staff.TeacherSubjectDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.APISubjectClasss;
import com.yahoo.petermwenda83.server.api.rest.bean.APITeacherSubject;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiStaffFull;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.SubClass;

/**
 * @author peter
 *
 */
public class StaffService {

	private static StaffDAO staffDAO;
	private static StreamDAO streamDAO;
	private static SubjectDAO subjectDAO;
	private static TeacherSubjectDAO teacherSubjectDAO;
	
	
	private static EmailValidator emailValidator;

	static{
		staffDAO = StaffDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		teacherSubjectDAO = TeacherSubjectDAO.getInstance();
		
		emailValidator = EmailValidator.getInstance();
	}


	/**
	 * @param staff
	 * @return
	 */
	public ApiResponse putStaff(Staff staff){

		String  response = "";
		ApiResponse apiResponse = new ApiResponse(); 

		String principal = "C3915245-00EE-4EF4-9898-ACE59683DD60";
		String deputy_Principal = "615F04C1-00BF-499C-AC7A-B46B69243AAA";

		if(StringUtils.equals(staff.getAcessLevelId(), principal)){

			if(staffDAO.getStaffByAccessLevel(staff.getAccountId(), principal) != null ){
				response = "Principal can not be added twice";
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription(response);

			}


		}else if(StringUtils.equals(staff.getAcessLevelId(), deputy_Principal)){

			if(staffDAO.getStaffByAccessLevel(staff.getAccountId(), deputy_Principal) != null ){
				response = "Deputy Principal can not be added twice";
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription(response);

			}


		}else{

			if (StringUtils.isBlank(staff.getFirstname())) { 

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Firt Name Can't be Empty!"); 

			}else if (StringUtils.isBlank(staff.getMiddlename())) { 

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Middle Name Can't be Empty!"); 

			}else if (StringUtils.isBlank(staff.getGender())) { 

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Gender Can't be Empty!"); 

			}else if(!validMobileNo(staff.getMobile())){

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Phone Number Not Valid!"); 

			}else if (!emailValidator.isValid(staff.getEmail())) {

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Email Address Not Valid!"); 

			}else if (StringUtils.isBlank(staff.getUsername())) { 

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Username Can't be Empty!"); 

			}else if (StringUtils.isBlank(staff.getPassword())) { 

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Password Can't be Empty!"); 

			}else if(staffDAO.getStaffByStaffNo(staff.getAccountId(), staff.getStaffNo()) != null){
				response = "StaffNo "+staff.getStaffNo() +" already exist.";
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription(response);

			}else if(staffDAO.getStaffByUsername(staff.getAccountId(), staff.getUsername()) != null){
				response = "Staff username, "+staff.getUsername() +" already exist.";
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription(response);

			}else if(staffDAO.putStaff(staff)){
				response = "Staff was registered successfully.";
				apiResponse = new ApiResponse("success");
				apiResponse.setDescription(response);

			}else{
				response = "Something went wrong, try again later.";
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription(response);
			}

		}


		return apiResponse;
	}




	/**
	 * @param ApiStaffFull
	 * @return
	 */
	public ApiResponse updateStaff(ApiStaffFull apiStaffFull){

		ApiResponse apiResponse = new ApiResponse(); 
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
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("You are performing an illegal action (Altering principal Info!)");  
				
				return apiResponse;
			}
			//user NOT deputy and tries to alter deputy principal 
			else if(!StringUtils.equals(apiStaffFull.getLogedUserAccessId(), deputy_Principal) && 
					StringUtils.equals(apiStaffFull.getAcessLevelId(), deputy_Principal)){ 
				//prevent user from performing the action 
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("You are performing an illegal action (Altering deputy principal Info!)");  
				
				return apiResponse;
				
			}else{
				allowed = true;
			}
		}else{

			allowed = true;

		}

		if(allowed){
			
			if (StringUtils.isBlank(apiStaffFull.getFirstname())) { 

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("First Name Can't be Empty!"); 

			}else if (StringUtils.isBlank(apiStaffFull.getMiddlename())) { 

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Middle Name Can't be Empty!"); 

			}else if (StringUtils.isBlank(apiStaffFull.getGender())) { 

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Gender Can't be Empty!"); 

			}else if(!validMobileNo(apiStaffFull.getMobile())){

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Phone Number Not Valid!"); 

			}else if (!emailValidator.isValid(apiStaffFull.getEmail())) {

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Email Address Not Valid!"); 

			}else if (StringUtils.isBlank(apiStaffFull.getUsername())) { 

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Username Can't be Empty!"); 

			}else if (StringUtils.isBlank(apiStaffFull.getPassword())) { 

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Password Can't be Empty!"); 

			}else if (staffDAO.getStaff(apiStaffFull.getAccountId(), apiStaffFull.getUuid()) == null) { 

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Staff details invalid!");  

			}/*else if(staffDAO.getStaffByStaffNo(apiStaffFull.getAccountId(), apiStaffFull.getStaffNo()) != null){

				error = new ApiResponse("error");
				error.setDescription("StaffNo already exist.");

			}else if(staffDAO.getStaffByUsername(apiStaffFull.getAccountId(), apiStaffFull.getUsername()) != null){

				error = new ApiResponse("error");
				error.setDescription("Staff username already exist.");

			}*/else{
				
				Staff staff = staffDAO.getStaff(apiStaffFull.getAccountId(), apiStaffFull.getUuid());
				staff.setAccountId(apiStaffFull.getAccountId());
				staff.setAcessLevelId(apiStaffFull.getAcessLevelId());
				staff.setEmail(apiStaffFull.getEmail());
				staff.setFirstname(apiStaffFull.getFirstname());
				staff.setGender(apiStaffFull.getGender().toUpperCase());
				staff.setIsActive(apiStaffFull.getIsActive());
				staff.setLastname(apiStaffFull.getLastname());
				staff.setLastupdated(new Date().toString());
				staff.setMiddlename(apiStaffFull.getMiddlename());
				staff.setMobile(apiStaffFull.getMobile());
				staff.setPassword(apiStaffFull.getPassword());
				staff.setStaffNo(apiStaffFull.getStaffNo());
				staff.setUsername(apiStaffFull.getUsername());
				
				if(staffDAO.updateStaff(staff)){
					apiResponse = new ApiResponse("success");
					apiResponse.setDescription("Staff was updated successfully.");
					
				}else{
					
					apiResponse = new ApiResponse("error");
					apiResponse.setDescription("Something went wrong, try again later."); 
				}
				
				
				
				
			}
			
			
		}else{
			
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Operation not allowed.");
		}
		
		return apiResponse;

	}
	
	
	
	

	/**
	 * @param staffId
	 * @param subClass
	 * @return
	 */
	public ApiResponse addSubject(SubClass subClass) {
		
		TeacherSubject teacherSubject = new TeacherSubject();
		teacherSubject.setAccountId(subClass.getAccountId());
		teacherSubject.setTeacherId(subClass.getTeacherId());
		teacherSubject.setSubjectId(subClass.getSubjectId());
		teacherSubject.setStreamId(subClass.getStreamId());
		
		ApiResponse apiResponse = new ApiResponse(); 
		
		if(teacherSubjectDAO.getTeacherSubject(subClass.getAccountId(), subClass.getStreamId(), subClass.getSubjectId()) != null){
			
			//The subject is already assigned to another teacher 
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("The subject is already assigned to another teacher."); 
			
		}else{
			
			if(teacherSubjectDAO.putTeacherSubject(teacherSubject)){
				
				//subject allocated successfully 
				apiResponse = new ApiResponse("success");
				apiResponse.setDescription("subject allocated successfully."); 
				
			}else{
				
				//Something went wrong, try again later or contact the Admin 
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Something went wrong, try again later or contact the Admin."); 
				
			}
			
		}
		
		return apiResponse; 
	}



	/**
	 * @param staffId
	 * @param subClassId
	 * @param subClass
	 * @return
	 */
	public ApiResponse updateSubjectClass(String subClassId, SubClass subClass) {
		
		TeacherSubject teacherSubject = teacherSubjectDAO.getTeacherSubject(subClass.getAccountId(), subClassId); 
		teacherSubject.setStreamId(subClass.getStreamId());
		teacherSubject.setSubjectId(subClass.getSubjectId()); 
		
		ApiResponse apiResponse = new ApiResponse(); 
		
		if(teacherSubjectDAO.getTeacherSubject(subClass.getAccountId(), subClass.getStreamId(), subClass.getSubjectId()) != null){
			
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Nothing to update / Update not allowed !"); 
			
		}else{
			
			if(teacherSubjectDAO.updateTeacherSubject(teacherSubject)){
				
				// Info updated successfully
				
				apiResponse = new ApiResponse("success");
				apiResponse.setDescription("Info updated successfully"); 
				
				
			}else{
				
				//Something went wrong while updating the Info
				
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Something went wrong while updating the Info"); 
				
			}
			
		}
		
		return apiResponse;
	}

	/**
	 * @param subClassId
	 * @return
	 */
	public ApiResponse deleteSubjectClass(String subClassId) {
		
		ApiResponse apiResponse = new ApiResponse(); 
		
		if(teacherSubjectDAO.getTeacherSubject(subClassId) == null){
			
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Nothing to delete!"); 
			return apiResponse;

		}else{
			
			if(teacherSubjectDAO.deleteTeacherSubject(subClassId)){
				
				// Info deleted successfully
				apiResponse = new ApiResponse("success");
				apiResponse.setDescription("Info deleted successfully"); 
				
				
			}else{
				
				//Something went wrong while deleting the Info
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Something went wrong while deleting the Info"); 
				
				
			}
			
		}
		
		
		
		return apiResponse;
	}

	/**
	 * @param staffId
	 * @return
	 */
	public List<APITeacherSubject> getSubjectClassList(String staffId) {
		
		List<APITeacherSubject> tsList = new ArrayList<APITeacherSubject>();
		
		if(teacherSubjectDAO.getTeacherSubjects(staffId) != null){
			
			teacherSubjectDAO.getTeacherSubjects(staffId).forEach(ts -> {
				
				TeacherSubject tsc = teacherSubjectDAO.getTeacherSubject(ts.getAccountId(), ts.getUuid()); 
				
				tsc.setSubjectId(subjectDAO.getSubjectById(ts.getAccountId(), ts.getSubjectId()).getDescription());
				tsc.setStreamId(streamDAO.getStream(ts.getAccountId(), ts.getStreamId()).getDescription()); 
				
				ApiResponse response = new ApiResponse();
				response.setMessage("success");
				response.setDescription("OK");
				

				APISubjectClasss apiSC = new APISubjectClasss();
				
				apiSC.setAccountId(tsc.getAccountId()); 
				apiSC.setAllocationDate(tsc.getAllocationDate());
				apiSC.setStreamId(tsc.getStreamId());
				apiSC.setSubjectId(tsc.getSubjectId());
				apiSC.setTeacherId(tsc.getTeacherId());
				apiSC.setUuid(tsc.getUuid());
				
				APITeacherSubject apiTSC = new APITeacherSubject(response, apiSC); 
				apiTSC.setApiSubjectClasss(apiSC);
				apiTSC.setResponse(response);
				
				
				
				
				tsList.add(apiTSC);
				
			});
			
		}
		
		return teacherSubjectDAO.getTeacherSubjects(staffId) != null ? tsList : new ArrayList<APITeacherSubject>();
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
