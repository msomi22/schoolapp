/**
 * 
 */
package ke.co.qubintel.school.server.api.rest;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import ke.co.qubintel.school.server.api.ApiConstants;
import ke.co.qubintel.school.server.api.rest.bean.APISubjectClasss;
import ke.co.qubintel.school.server.api.rest.bean.APITeacherSubject;
import ke.co.qubintel.school.server.api.rest.bean.ApiResponse;
import ke.co.qubintel.school.server.api.rest.bean.ApiStaffFull;
import ke.co.qubintel.school.server.api.rest.bean.Response;
import ke.co.qubintel.school.server.api.rest.bean.StaffProfile;
import ke.co.qubintel.school.server.bean.account.ApiCredential;
import ke.co.qubintel.school.server.bean.staff.Staff;
import ke.co.qubintel.school.server.bean.staff.TeacherSubject;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.persistence.account.ApiCredentialDAO;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.staff.AcessLevelDAO;
import ke.co.qubintel.school.server.persistence.staff.StaffDAO;
import ke.co.qubintel.school.server.persistence.staff.TeacherSubjectDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;
import ke.co.qubintel.school.server.servlet.util.SecurityUtil;
import ke.co.qubintel.school.server.servlet.util.sms.SmsObject;
import ke.co.qubintel.school.server.servlet.util.sms.SmsUtil;

/**
 * @author peter
 *
 */
public class StaffService {

	private static StaffDAO staffDAO;
	private static StreamDAO streamDAO;
	private static SubjectDAO subjectDAO;
	private static TeacherSubjectDAO teacherSubjectDAO;

	private static AccountDAO accountDAO;

	private static ApiCredentialDAO smsApiDAO;
	private static  AcessLevelDAO acessLevelDAO;

	private static EmailValidator emailValidator;

	static{
		staffDAO = StaffDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		teacherSubjectDAO = TeacherSubjectDAO.getInstance();

		accountDAO = AccountDAO.getInstance();

		smsApiDAO = ApiCredentialDAO.getInstance();
		acessLevelDAO = AcessLevelDAO.getInstance();

		emailValidator = EmailValidator.getInstance();
	}


	/**
	 * @param staff
	 * @return
	 */
	public Response putStaff(Staff staff){

		Response response = new Response(); 
		
		
		
		String principal = "100";
		String deputy_Principal = "200";
		
		staff.setAcessLevelId(acessLevelDAO.getAcessLevelById(staff.getAccountId(), staff.getAcessLevelId()).getUuid()); 
		
		principal = acessLevelDAO.getAcessLevelById(staff.getAccountId(), "100").getUuid();
		deputy_Principal = acessLevelDAO.getAcessLevelById(staff.getAccountId(), "200").getUuid();
		
		
		
		
		if(accountDAO.getAccountById(staff.getAccountId()) == null){
			response.setMessage("error");
			response.setDescription("Invalid accounId"); 
			return response;

		}else if(StringUtils.equals(principal, staff.getAcessLevelId()) && 
				staffDAO.getStaffByAccessLevel(staff.getAccountId(),principal) != null ) {
			response.setMessage("error");
			response.setDescription("Principal can not be added twice"); 
			return response;
			
		}else if(StringUtils.equals(deputy_Principal, staff.getAcessLevelId())&& 
				staffDAO.getStaffByAccessLevel(staff.getAccountId(), deputy_Principal) != null) {
			response.setMessage("error");
			response.setDescription("Deputy Principal can not be added twice"); 
			return response;
			
		}else if (staff.getStaffNo().length() < 3) { 
			response.setMessage("error");
			response.setDescription("Staff Number Invalid!"); 
			return response;
			

		}else if (staffDAO.getStaffByKeys(staff.getAccountId(), staff.getStaffNo()) != null) { 
			response.setMessage("error");
			response.setDescription("Staff Number already exist!"); 
			return response;
			

		}else if (staff.getFirstname().length() < 3) { 
			response.setMessage("error");
			response.setDescription("Firt Name Invalid!"); 
			return response;
			

		}else if (staff.getMiddlename().length() < 3) { 
			response.setMessage("error");
			response.setDescription("Middle Name Invalid!"); 
			return response;
			
		}else if (!validGender(staff.getGender())) { 
			response.setMessage("error");
			response.setDescription("Gender Can't be Empty!"); 
			return response;
			

		}else if(!validMobileNo(staff.getMobile())){
			response.setMessage("error");
			response.setDescription("Phone Number Invalid!"); 
			return response;
			

		}else if (staffDAO.getStaffByKeys(staff.getAccountId(), staff.getMobile()) != null) { 
			response.setMessage("error");
			response.setDescription("Staff Phone Number already exist!"); 
			return response;
			
		}else if (!emailValidator.isValid(staff.getEmail())) {
			response.setMessage("error");
			response.setDescription("Email Address Invalid!"); 
			return response;
			

		}else if (staffDAO.getStaffByKeys(staff.getAccountId(), staff.getEmail()) != null) { 
			response.setMessage("error");
			response.setDescription("Staff Email Address already exist!"); 
			return response;
			

		}else if (staff.getUsername().length() < 3) { 
			response.setMessage("error");
			response.setDescription("Username Invalid!"); 
			return response;
			
		}else if (staffDAO.getStaffByKeys(staff.getAccountId(), staff.getUsername()) != null) { 
			response.setMessage("error");
			response.setDescription("Staff Username already exist!"); 
			return response;
			

		}else if (staff.getPassword().length() < 4) { 
			response.setMessage("error");
			response.setDescription("Password Invalid!"); 
			return response;
			

		}else if(acessLevelDAO.getAcessLevel(staff.getAccountId(), staff.getAcessLevelId()) == null) {
			response.setMessage("error");
			response.setDescription("AccessLevelId invalid!"); 
			return response;
			

		}else {

			if(staffDAO.putStaff(staff)){
				response.setMessage("success");
				response.setDescription("Staff was registered successfully."); 
				return response;
				

			}else{
				response.setMessage("error");
				response.setDescription("Something went wrong, try again later."); 
				return response;
				

			}


		}

	}





	/**
	 * @param ApiStaffFull
	 * @return
	 */
	public ApiResponse updateStaff(ApiStaffFull apiStaffFull){

		ApiResponse apiResponse = new ApiResponse(); 
		String principal = "C3915245-00EE-4EF4-9898-ACE59683DD60";
		String deputy_Principal = "615F04C1-00BF-499C-AC7A-B46B69243AAA";

		boolean allowed = false;

		if(acessLevelDAO.getAcessLevel(apiStaffFull.getAccountId(), apiStaffFull.getAcessLevelId()) == null) {
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("AccessLevelId invalid!"); 
			return apiResponse;

		}else if(acessLevelDAO.getAcessLevel(apiStaffFull.getAccountId(), apiStaffFull.getLogedUserAccessId()) == null) {
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Loged User AccessLevelId invalid!"); 
			return apiResponse;

		}
		//user NOT logged as principal  
		else if(!StringUtils.equals(apiStaffFull.getLogedUserAccessId(), principal)){
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

			if (StringUtils.isBlank(apiStaffFull.getStaffNo())) { 
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Staff Number Can't be Empty!"); 
				return apiResponse;

			}else if(hasDuplicate(apiStaffFull.getAccountId(), apiStaffFull.getStaffNo())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Staff Number duplicated not allowed!");
				return apiResponse;

			}else if (StringUtils.isBlank(apiStaffFull.getFirstname())) { 
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("First Name Can't be Empty!"); 
				return apiResponse;

			}else if (StringUtils.isBlank(apiStaffFull.getMiddlename())) { 
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Middle Name Can't be Empty!"); 
				return apiResponse;

			}else if (!validGender(apiStaffFull.getGender())) { 
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Gender Can't be Empty!"); 
				return apiResponse;

			}else if(!validMobileNo(apiStaffFull.getMobile())){
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Phone Number Not Valid!"); 
				return apiResponse;

			}else if(hasDuplicate(apiStaffFull.getAccountId(), apiStaffFull.getMobile())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Phone Number duplicated not allowed!");
				return apiResponse;

			}else if (!emailValidator.isValid(apiStaffFull.getEmail())) {

				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Email Address Not Valid!"); 
				return apiResponse;

			}else if(hasDuplicate(apiStaffFull.getAccountId(), apiStaffFull.getEmail())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Email duplicated not allowed!");
				return apiResponse;

			}else if (StringUtils.isBlank(apiStaffFull.getUsername())) { 
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Username Can't be Empty!"); 
				return apiResponse;

			}else if(hasDuplicate(apiStaffFull.getAccountId(), apiStaffFull.getUsername())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Username duplicated not allowed!");
				return apiResponse;

			}else if (apiStaffFull.getPassword().length() < 4) { 
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Password invalid!"); 
				return apiResponse;

			}else if (staffDAO.getStaff(apiStaffFull.getAccountId(), apiStaffFull.getLogedUserId()) == null) { 
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("LogedUser Id invalid!");  
				return apiResponse;

			}else if (staffDAO.getStaff(apiStaffFull.getAccountId(), apiStaffFull.getUuid()) == null) { 
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("StaffId invalid!");  
				return apiResponse;

			}else if (staffDAO.getStaff(apiStaffFull.getAccountId(), apiStaffFull.getUuid(), "1") == null) { 
				apiResponse = new ApiResponse("error");
				apiResponse.setDescription("Staff inactive!");   
				return apiResponse;

			}else{

				Staff staff = staffDAO.getStaff(apiStaffFull.getAccountId(), apiStaffFull.getUuid());

				if(!StringUtils.equals(apiStaffFull.getLogedUserAccessId(), 
						staffDAO.getStaff(apiStaffFull.getAccountId(), apiStaffFull.getLogedUserId()).getAcessLevelId())){

					apiResponse = new ApiResponse("error");
					apiResponse.setDescription("Security breach detected!, staff has been in-activated."); 

					Staff staffUpdating = staffDAO.getStaff(apiStaffFull.getAccountId(), apiStaffFull.getLogedUserId()); 
					staffUpdating.setIsActive("0"); 
					staffDAO.updateStaff(staffUpdating);
					return apiResponse;


				}else {

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
					//staff.setPassword(password);
					staff.setStaffNo(apiStaffFull.getStaffNo());
					staff.setUsername(apiStaffFull.getUsername());

					if(staffDAO.updateStaff(staff)){
						apiResponse = new ApiResponse("success");
						apiResponse.setDescription("Staff was updated successfully.");
						return apiResponse;

					}else{

						apiResponse = new ApiResponse("error");
						apiResponse.setDescription("Something went wrong, try again later."); 
						return apiResponse;
					}

				}


			}


		}else{

			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Operation not allowed.");
			return apiResponse;
		}

	}


	/**
	 * 
	 * @param staffProfile
	 * @return
	 */
	public Object updateStaffProfile(StaffProfile staffProfile){
		Response response = new Response(); 
		
		if(accountDAO.getAccountById(staffProfile.getAccountId()) == null) {
			response .setMessage("error");
			response.setDescription("Account not found!"); 
			return response;
			
		}else if(staffDAO.getStaff(staffProfile.getAccountId(), staffProfile.getStaffId()) == null) {  
			response .setMessage("error");
			response.setDescription("Staff not found!"); 
			return response;
			
		}else {
			
			Staff staff = staffDAO.getStaff(staffProfile.getAccountId(), staffProfile.getStaffId());
			
			if(!StringUtils.equals(staff.getPassword(), SecurityUtil.getMD5Hash(staffProfile.getOldpassword()))) { 
				response .setMessage("error");
				response.setDescription("Incorrect old password!");  
				return response;
				
			}else if(!StringUtils.equals(staffProfile.getNewpassword(), staffProfile.getCnewpassword())) { 
				response .setMessage("error");
				response.setDescription("New password mismatch!");  
				return response;
				
			}else {
				
				//String password = SecurityUtil.getMD5Hash(apiStaffFull.getPassword());
				
				staff.setPassword(SecurityUtil.getMD5Hash(staffProfile.getNewpassword())); 
				
				if(staffDAO.updateStaff(staff)) {
					response .setMessage("success");
					response.setDescription("Profile updated successfully!"); 
					return response;
					
				}else {
					response .setMessage("error");
					response.setDescription("Please contact admin!");   
					return response;
					
				}
				
				
			}
			
			
		}
		
		
	}



	/**
	 * @param staffId
	 * @param subClass
	 * @return
	 */
	public ApiResponse addSubject(APISubjectClasss subClass, String staffId) {


		ApiResponse apiResponse = new ApiResponse(); 

		if(!StringUtils.equals(staffId, subClass.getTeacherId())) {
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("StaffId mismatch!"); 
			return apiResponse; 

		}else if(accountDAO.getAccountById(subClass.getAccountId()) == null) { 
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Account not found!"); 
			return apiResponse; 

		}else if(staffDAO.getStaff(subClass.getAccountId(), staffId) == null) { 
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Staff not found!"); 
			return apiResponse; 

		}else if(teacherSubjectDAO.getTeacherSubject(subClass.getAccountId(), subClass.getStreamId(), subClass.getSubjectId()) != null){
			//The subject is already assigned to another teacher 
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("The subject is already assigned to another teacher."); 
			return apiResponse; 

		}else{

			TeacherSubject teacherSubject = new TeacherSubject();
			teacherSubject.setAccountId(subClass.getAccountId());
			teacherSubject.setTeacherId(subClass.getTeacherId());
			teacherSubject.setSubjectId(subClass.getSubjectId());
			teacherSubject.setStreamId(subClass.getStreamId());

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
	public ApiResponse updateSubjectClass(String subClassId, APISubjectClasss subClass) {

		TeacherSubject teacherSubject = teacherSubjectDAO.getTeacherSubject(subClass.getAccountId(), subClassId); 
		teacherSubject.setStreamId(subClass.getStreamId());
		teacherSubject.setSubjectId(subClass.getSubjectId()); 

		ApiResponse apiResponse = new ApiResponse(); 
       
		if(accountDAO.getAccountById(subClass.getAccountId()) == null) { 
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Account not found!"); 
			return apiResponse; 

		}else if(staffDAO.getStaff(subClass.getAccountId(), subClass.getTeacherId()) == null) { 
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Staff not found!"); 
			return apiResponse; 

		}
		else if(teacherSubjectDAO.getTeacherSubject(subClass.getAccountId(), subClass.getStreamId(), subClass.getSubjectId()) != null){

			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Nothing to update / Update not allowed !"); 
			return apiResponse; 

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
	public Object getSubjectClassList(String staffId) {

		List<APISubjectClasss> tsList = new ArrayList<APISubjectClasss>();

		if(teacherSubjectDAO.getTeacherSubjects(staffId) != null){

			teacherSubjectDAO.getTeacherSubjects(staffId).forEach(ts -> {

				TeacherSubject tsc = teacherSubjectDAO.getTeacherSubject(ts.getAccountId(), ts.getUuid()); 

				tsc.setSubjectId(subjectDAO.getSubjectById(ts.getAccountId(), ts.getSubjectId()).getUuid());
				tsc.setStreamId(streamDAO.getStream(ts.getAccountId(), ts.getStreamId()).getUuid()); 

				APISubjectClasss apiSC = new APISubjectClasss();

				apiSC.setAccountId(tsc.getAccountId()); 
				apiSC.setAllocationDate(tsc.getAllocationDate());
				apiSC.setStreamId(tsc.getStreamId());
				apiSC.setStreamDesc(streamDAO.getStream(ts.getAccountId(), ts.getStreamId()).getDescription()); 
				apiSC.setSubjectId(tsc.getSubjectId());
				apiSC.setTeacherId(tsc.getTeacherId());
				apiSC.setUuid(tsc.getUuid());
				apiSC.setSubjectDesc(subjectDAO.getSubjectById(ts.getAccountId(), ts.getSubjectId()).getDescription());

				tsList.add(apiSC);

			});

		}

		return teacherSubjectDAO.getTeacherSubjects(staffId) != null ? tsList : new ArrayList<APITeacherSubject>();
	}



	/**
	 * 
	 * @param action
	 * @param accountId
	 * @param staffId
	 * @return
	 */
	public Object staffStatus(String action, String accountId, String staffId) {

		ApiResponse response = new ApiResponse(); 

		if(staffDAO.getStaff(accountId, staffId) != null) {

			Staff staff = staffDAO.getStaff(accountId, staffId);

			if(StringUtils.equals(action, "activate")) {
				staff.setIsActive("1");
			}else if(StringUtils.equals(action, "activate")) {
				staff.setIsActive("0"); 
			}else {
				response.setMessage("error");
				response.setDescription("Invalid status."); 
				return response;
			}

			if(staffDAO.updateStaff(staff)) {
				response.setMessage("success");
				response.setDescription("Staff updated successfully.");  
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Something went wrong, try again later."); 
				return response;

			}
		}



		return response;
	}


	/**
	 * TODO
	 * @param accountId
	 * @param staffId
	 * @return
	 */
	public Object getStaff(String accountId, String staffId) {

		ApiResponse apiResponse = new ApiResponse(); 

		if(staffDAO.getStaff(accountId, staffId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account/Staff not found!");
			return apiResponse;


		}else {
			ApiStaffFull apiStaffFull = new ApiStaffFull();

			try {
				BeanUtils.copyProperties(apiStaffFull, staffDAO.getStaff(accountId, staffId)); 
				
				
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			apiStaffFull.setCategory(acessLevelDAO.getAcessLevel(accountId, apiStaffFull.getAcessLevelId()).getDescription()); 
			
			//System.out.println("cat : " + acessLevelDAO.getAcessLevel(accountId, apiStaffFull.getAcessLevelId()).getDescription());
			//System.out.println("***************************************************");

			return apiStaffFull;
		}

	}

	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public Object getStaffList(String accountId) {

		ApiResponse apiResponse = new ApiResponse(); 

		if(staffDAO.getStaff(accountId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account not found!");
			return apiResponse;

		}else {
			List<ApiStaffFull> apiStaffFullList = new ArrayList<>();
			staffDAO.getStaff(accountId).forEach(staff -> {
				ApiStaffFull apiStaffFull = new ApiStaffFull();

				try {
					BeanUtils.copyProperties(apiStaffFull, staff); 
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
				apiStaffFull.setCategory(acessLevelDAO.getAcessLevel(accountId, apiStaffFull.getAcessLevelId()).getDescription()); 
				
			//	System.out.println("cat : " + acessLevelDAO.getAcessLevel(accountId, apiStaffFull.getAcessLevelId()).getDescription());
				//System.out.println("***************************************************");

				apiStaffFullList.add(apiStaffFull);
			});

			return apiStaffFullList;
		}

	}


	/**
	 * 
	 * @param account
	 * @param query
	 * @return
	 */
	public Object recoverPassword(String account,String query) {

		ApiResponse apiResponse = new ApiResponse(); 

		if(accountDAO.getAccount(account, "1") == null) {
			//account not found
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account not found!");
			return apiResponse;

		}else if(staffDAO.getStaffByKeys(accountDAO.getAccount(account, "1").getUuid(), query) == null) {
			//staff not found
			apiResponse.setMessage("error");
			apiResponse.setDescription("Staff not found!");
			return apiResponse;

		}else {

			Staff staff = staffDAO.getStaffByKeys(accountDAO.getAccount(account, "1").getUuid(), query);
			String newpassword = RandomStringUtils.randomAlphabetic(5);
			String password = SecurityUtil.getMD5Hash(newpassword);   

			staff.setPassword(password);

			if(staffDAO.updateStaff(staff)) {

				String description = "";
				String msg = "";

				//send new password via SMS
				if(smsApiDAO.getApiCredential(accountDAO.getAccount(account, "1").getUuid(), ApiConstants.SMS) != null) {

					//send SMS
					String accountId = accountDAO.getAccount(account, "1").getUuid();

					ApiCredential api = smsApiDAO.getApiCredential(accountId, ApiConstants.SMS);
					//prepare SMS
					String firstname = StringUtils.capitalize(staff.getFirstname().substring(0, Math.min(staff.getFirstname().length(), 7)).toLowerCase()); 
					String message = "Hello " + firstname + ", your new password is, " + newpassword;

					//String account,String mobile,String message,String apiUsername,String apiKey
					//System.out.println(api.getApiKey() + " -- " + api.getApisecret()); 
					SmsObject smsObject = new SmsObject(accountId,staff.getMobile(),message,api.getApisecret(),api.getApiKey());
					description = SmsUtil.sendSMS(smsObject); 
					msg = "success";

				}else {
					description = "Invalid API!";
					msg = "error";
				}


				//password reset success
				apiResponse.setMessage(msg); 
				apiResponse.setDescription(description); 
				return apiResponse;

			}else {
				//an error occurred while resetting your password, please contact Admin 
				apiResponse.setMessage("error");
				apiResponse.setDescription("An error occurred while resetting your password, please contact Admin!");
				return apiResponse;

			}

		}
	}








	/**
	 * to detect duplicate value
	 * 
	 * @param value
	 * @return
	 */
	private boolean hasDuplicate(String value,String accountId) { 
		List<Staff> accountList = new ArrayList<>();
		//if not account with such a key, return true and proceed

		if(staffDAO.findDuplicate(accountId, value) == null) { 
			return false;
		}else {
			accountList = staffDAO.findDuplicate(accountId, value); 
			//System.out.println("size: " + accountList.size() + " key: " + value ); 
			//if only one account has such a key, return true and proceed
			if(accountList.size() == 1) {
				return false;

				//if you reach here, there are more than one accounts sharing the provided key, return false.
			}else if(accountList.size() > 1) {

				return true;

			}else if(accountList.size() == 0) {
				return false;

			}else {
				return false;

			}
		}
	}






	/**
	 * 
	 * @param gender
	 * @return
	 */
	private boolean validGender(String gender) {
		String[] allowed = {"M","F","m","f"};
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);
		if(allowedList.contains(gender)) {
			return true;
		}else {
			return false;
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

}
