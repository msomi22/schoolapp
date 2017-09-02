/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.*;
import com.yahoo.petermwenda83.server.servlet.finance.StudentBalance;

/**
 * @author peter
 *
 */
public class StudentService {

	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static StaffDAO staffDAO;
	private static StreamDAO streamDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static SysConfigDAO sysConfigDAO;
	private static EmailValidator emailValidator;

	static{
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		emailValidator = EmailValidator.getInstance();
	}

	/**
	 * @param sreamId
	 * @return
	 */
	public List<APIStudent> getStudentPerStream(String accountId, String sreamId) { 

		List<Student> students = studentDAO.getStudentByStream(accountId, sreamId);
		List<APIStudent> streamStudents = new ArrayList<APIStudent>();
		students.forEach(student -> {

			APIStudent apiStudent = new APIStudent(); 
			apiStudent.setUuid(student.getUuid());
			apiStudent.setAccountId(student.getAccountId());
			apiStudent.setCurrentStream(student.getCurrentStream());
			apiStudent.setRegStream(student.getRegStream());
			apiStudent.setIsActive(student.getIsActive());
			apiStudent.setIsAlumni(student.getIsAlumni());
			apiStudent.setIsBoarding(student.getIsBoarding());
			apiStudent.setRegNo(student.getRegNo());
			apiStudent.setFirstname(student.getFirstname());
			apiStudent.setMiddlename(student.getMiddlename());
			apiStudent.setLastname(student.getLastname());
			apiStudent.setGender(student.getGender());
			apiStudent.setCounty(student.getCounty());
			apiStudent.setBcertNo(student.getBcertNo());
			apiStudent.setDob(student.getDob());
			apiStudent.setRegTerm(student.getRegTerm());
			apiStudent.setPassport(student.getPassport());
			apiStudent.setLastUpdated(student.getLastUpdated()); 
			apiStudent.setFinalTerm(student.getFinalTerm());
			apiStudent.setFinalYear(student.getFinalYear());
			apiStudent.setAdmissionDate(student.getAdmissionDate());

			apiStudent.setMessage("success");
			apiStudent.setDescription("Ok"); 

			streamStudents.add(apiStudent); 

		});



		return streamStudents; 
	}

	/**
	 * 
	 * @param studentPayFee
	 * @return
	 */
	public FeeResponse payFee(StudentPayFee studentPayFee) {

		FeeResponse feeResponse = new FeeResponse();

		ApiResponse apiResponse = new ApiResponse();

		//validation
		if(StringUtils.isEmpty(studentPayFee.getAccountId())) {

			apiResponse.setMessage("error");
			apiResponse.setDescription("AccountId is invalid!");
			feeResponse.setApiResponse(apiResponse); 
			return feeResponse;

		}else if(accountDAO.getAccountById(studentPayFee.getAccountId()) != null) { 

			apiResponse.setMessage("error");
			apiResponse.setDescription("AccountId is invalid!");
			feeResponse.setApiResponse(apiResponse); 
			return feeResponse;

		}else if(StringUtils.isEmpty(studentPayFee.getStaffId())) { 

			apiResponse.setMessage("error");
			apiResponse.setDescription("Staff is invalid!");
			feeResponse.setApiResponse(apiResponse); 
			return feeResponse;

		}else if(staffDAO.getStaff(studentPayFee.getAccountId(), studentPayFee.getStaffId()) != null) {

			apiResponse.setMessage("error");
			apiResponse.setDescription("Staff is invalid!");
			feeResponse.setApiResponse(apiResponse); 
			return feeResponse;

		}else if(studentDAO.getStudentByregNo(studentPayFee.getAccountId(), studentPayFee.getRefNo()) == null) {

			apiResponse.setMessage("error");
			apiResponse.setDescription("Student RegNo is invalid!");
			feeResponse.setApiResponse(apiResponse); 
			return feeResponse;

		}else if(!StringUtils.isNumeric(studentPayFee.getAmount())) {

			apiResponse.setMessage("error");
			apiResponse.setDescription("Amount is invalid!");
			feeResponse.setApiResponse(apiResponse); 
			return feeResponse;


		}else if(Integer.valueOf(studentPayFee.getAmount()) < 1 || Integer.valueOf(studentPayFee.getAmount()) > 100000) {

			apiResponse.setMessage("error");
			apiResponse.setDescription("Amount is invalid!");
			feeResponse.setApiResponse(apiResponse); 
			return feeResponse;

		}else {

			studentPayFee.getAccountId();
			studentPayFee.getStaffId();

			studentPayFee.getRegNo();
			studentPayFee.getAmount();
			studentPayFee.getRefNo();
			studentPayFee.getPaymentMode();
			studentPayFee.getTransactionId();

			studentPayFee.getYear();
			studentPayFee.getTerm();

			apiResponse.setMessage("success");
			apiResponse.setDescription("OK");

			feeResponse.setStudentPayFee(studentPayFee); 
		}


		return feeResponse;
	}
	/**
	 * 
	 * @param accountId
	 * @param regNo
	 * @return
	 */
	public StudentResponse getStudent(String accountId, String regNo) {

		StudentResponse response = new StudentResponse();

		if(studentDAO.getStudentByregNo(accountId, regNo) == null) {
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setMessage("error");
			apiResponse.setDescription("RegNo/AccountId Not found!"); 

			response.setApiResponse(apiResponse);
			return response;

		}else {

			Student student = studentDAO.getStudentByregNo(accountId, regNo);


			StudentFeeAPI studentFeeAPI = new StudentFeeAPI();

			studentFeeAPI.setRegNo(student.getRegNo());
			studentFeeAPI.setFirstname(student.getFirstname());
			studentFeeAPI.setMiddlename(student.getMiddlename());
			studentFeeAPI.setLastname(student.getLastname());
			studentFeeAPI.setStream(streamDAO.getStream(accountId, student.getCurrentStream()).getDescription());
			studentFeeAPI.setIsBoarding(student.getIsBoarding());

			//basic info

			//fee balance
			Locale locale = new Locale("en","KE"); 
			NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

			StudentBalance balance = new StudentBalance();
			double feeBalance = balance.findBalance(accountId, student.getUuid());

			String feeBal = nf.format(feeBalance);

			studentFeeAPI.setBalance(feeBal);

			//fee history 
			String term = sysConfigDAO.getSysConfig(accountId).getTerm();
			String year = sysConfigDAO.getSysConfig(accountId).getYear();

			List<StudentFee> feeHistory = new ArrayList<>();
			if(studentFeeDAO.getStudentFeeList(accountId, student.getUuid(), term, year) != null) {
				feeHistory = studentFeeDAO.getStudentFeeList(accountId, student.getUuid(), term, year);
			}

			studentFeeAPI.setFeeHistory(feeHistory);

			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setMessage("success");
			apiResponse.setDescription("OK"); 

			response.setApiResponse(apiResponse);
			response.setStudentFeeAPI(studentFeeAPI);


		}

		return response;
	}

	/**
	 * 
	 * @param accountId
	 * @param student
	 * @return
	 */
	public Object addNewStudent(String accountId, StudentInfo student) {

		ApiResponse apiResponse = new ApiResponse();


		if(StringUtils.isBlank(accountId)) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account Id is invalid.");
			
			return apiResponse;

		}else if(StringUtils.isBlank(student.getUuid())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Student Id is invalid.");
			
			return apiResponse;

		}else if(StringUtils.isBlank(student.getRegNo()) && !validaLength(student.getRegNo()) ) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("RegNo is invalid.");
			
			return apiResponse;

		}else if(StringUtils.isBlank(student.getRegStream())) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Registration stream is invalid.");
			
			return apiResponse;

		}else if(StringUtils.isBlank(student.getCurrentStream())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Current stream is invalid.");

		}else if(StringUtils.isBlank(student.getIsBoarding())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("IsBoarding' but be set.");
			
			return apiResponse;

		}else if(StringUtils.isBlank(student.getFirstname()) && !validaLength(student.getFirstname())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Firstname is invalid.");
			
			return apiResponse;

		}else if(StringUtils.isBlank(student.getMiddlename()) && !validaLength(student.getMiddlename())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Middlename is invalid."); 
			
			return apiResponse;

		}else if(StringUtils.isBlank(student.getGender())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Gender is invalid."); 
			
			return apiResponse;

		}else if(StringUtils.isBlank(student.getDob())) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("DOB is invalid."); 
			
			return apiResponse;

		}else if(student.hasParent()) { 

			if(StringUtils.isBlank(student.getParentName()) && !validaLength(student.getMiddlename())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Parent name is invalid."); 
				
				return apiResponse;

			}else if(StringUtils.isBlank(student.getParentEmail()) && !emailValidator.isValid(student.getParentEmail())) {  
				apiResponse.setMessage("error");
				apiResponse.setDescription("Parent email is invalid."); 
				
				return apiResponse;

			}else if(StringUtils.isBlank(student.getParentMobile()) && !StringUtils.isNumeric(student.getParentMobile()) &&
					  student.getParentMobile().length() > 9) {  
				apiResponse.setMessage("error");
				apiResponse.setDescription("Parent mobile is invalid."); 
				
				return apiResponse;

			}



		}else if(student.hasPrimary()) {  

			if(StringUtils.isBlank(student.getSchoolName()) && !validaLength(student.getSchoolName())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Primary school name is invalid."); 
				
				return apiResponse;

			}else if(StringUtils.isBlank(student.getIndex()) ) { 
				apiResponse.setMessage("error");
				apiResponse.setDescription("Primary school index is invalid."); 
				
				return apiResponse;

			}else if(StringUtils.isBlank(student.getKcpeyear()) && student.getKcpemark().length() !=4 ) { 
				apiResponse.setMessage("error");
				apiResponse.setDescription("K.C.P.E year is invalid."); 
				
				return apiResponse;

			}else if(StringUtils.isBlank(student.getKcpemark()) && !StringUtils.isNumeric(student.getKcpemark()) && 
					Integer.valueOf(student.getKcpemark()) < 100 && Integer.valueOf(student.getKcpemark()) > 500) { 
				apiResponse.setMessage("error");
				apiResponse.setDescription("K.C.P.E makrs invalid."); 
				
				return apiResponse;

			}
		}else {
			
			SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);
			
			sysConfig.getTerm();
			sysConfig.getYear();



			apiResponse.setMessage("success");
			apiResponse.setDescription("Student added successfully.");
			
			return apiResponse;

		}
		
		
		return apiResponse;
	}


	/**
	 * 
	 * @param value
	 * @return
	 */
	private boolean validaLength(String value) {
		if(value.length() < 3) {
			return false;
		}else {
			return true;
		}
	}

}
