/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.bean.student.StudentSubject;
import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
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

	private static PrimaryDAO primaryDAO;
	private static ParentsDAO parentsDAO;

	private static StudentSubjectDAO studentSubjectDAO;

	private static SubjectDAO subjectDAO;

	private static final String DATA_DIRECTORY = "/home/"+System.getProperty("user.name")+"/school/uploads/";

	static{
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		emailValidator = EmailValidator.getInstance();

		parentsDAO= ParentsDAO.getInstance();
		primaryDAO=PrimaryDAO.getInstance();

		studentSubjectDAO = StudentSubjectDAO.getInstance();

		subjectDAO = SubjectDAO.getInstance();
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

		}else if(studentDAO.getStudentByregNo(accountId, student.getRegNo()) != null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("RegNo exist!");

			return apiResponse;

		}else if(StringUtils.isBlank(student.getRegStream()) && streamDAO.getStream(accountId, student.getRegStream()) == null) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Registration stream is invalid.");

			return apiResponse;

		}else if(StringUtils.isBlank(student.getCurrentStream()) && streamDAO.getStream(accountId, student.getCurrentStream()) == null) {
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

		}else if(!validGender(student.getGender())) {
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

			//basic
			Student newstudent = new Student();
			newstudent.setRegStream(student.getRegStream()); 
			newstudent.setCurrentStream(student.getCurrentStream());
			newstudent.setIsActive(student.getIsActive());
			newstudent.setIsAlumni(student.getIsAlumni());
			newstudent.setIsBoarding(student.getIsBoarding());
			newstudent.setRegNo(student.getRegNo());
			newstudent.setFirstname(student.getFirstname());
			newstudent.setMiddlename(student.getMiddlename());
			newstudent.setLastname(student.getLastname());
			newstudent.setGender(student.getGender().toUpperCase());
			newstudent.setDob(student.getDob());
			newstudent.setBcertNo(student.getBcertNo());
			newstudent.setCounty(student.getCounty());
			newstudent.setRegTerm(sysConfig.getTerm());
			newstudent.setFinalYear(Integer.valueOf(sysConfig.getYear()) + 3); 
			newstudent.setFinalTerm(3); 
			newstudent.setPassport(renameImage(student.getPassport(),student.getRegNo()));
			newstudent.setLastUpdated(new Date().toString());  


			String response = "";

			if(studentDAO.putStudent(newstudent)) {

				response = "Student basic info saved successfully.";

				//parent
				if(student.hasParent()) {
					StudentParent studentParent= new StudentParent();
					studentParent.setAccountId(accountId);
					studentParent.setStudentId(student.getUuid());
					studentParent.setName(student.getParentName());
					studentParent.setMobile(student.getParentMobile());
					studentParent.setEmail(student.getParentEmail()); 

					if(parentsDAO.putParent(studentParent)) {

						response += "Student parent info saved successfully.";

					}else {

						response += "Student parent info NOT saved.";

					}
				}

				//primary
				if(student.hasPrimary()) {
					StudentPrimary studentPrimary= new StudentPrimary();
					studentPrimary.setAccountId(accountId);
					studentPrimary.setStudentId(student.getUuid());
					studentPrimary.setSchoolName(student.getSchoolName());
					studentPrimary.setIndex(student.getIndex());
					studentPrimary.setKcpemark(student.getKcpemark());
					studentPrimary.setKcpeyear(student.getKcpeyear());

					if(primaryDAO.putStudentPrimary(studentPrimary)) {

						response += "Student primary info saved successfully.";

					}else { 

						response += "Student primary info NOT saved.";

					}
				}

			}


			apiResponse.setMessage("success");
			apiResponse.setDescription(response); 

			return apiResponse;

		}


		return apiResponse;
	}



	/**
	 * 
	 * @param accountId
	 * @param student
	 * @return
	 */
	public Object updateStudent(String accountId, StudentInfo student) {

		ApiResponse apiResponse = new ApiResponse();


		if(StringUtils.isBlank(accountId)) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account Id is invalid.");

			return apiResponse;

		}else if(StringUtils.isBlank(student.getUuid()) && studentDAO.getStudentById(accountId, student.getUuid()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Student Id is invalid.");

			return apiResponse;

		}else if(StringUtils.isBlank(student.getRegNo()) && !validaLength(student.getRegNo()) ) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("RegNo is invalid.");

			return apiResponse;

		}else if(hasDuplicate(student.getRegNo(),accountId)) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Student RegNo duplicated not allowed!");
			return apiResponse;

		}else if(StringUtils.isBlank(student.getRegStream()) && streamDAO.getStream(accountId, student.getRegStream()) == null) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Registration stream is invalid.");

			return apiResponse;

		}else if(StringUtils.isBlank(student.getCurrentStream()) && streamDAO.getStream(accountId, student.getCurrentStream()) == null) {
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

		}else if(!validGender(student.getGender())) {
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

			//basic
			Student newstudent = studentDAO.getStudentById(accountId, student.getUuid());

			newstudent.setRegStream(student.getRegStream()); 
			newstudent.setCurrentStream(student.getCurrentStream());
			newstudent.setIsActive(student.getIsActive());
			newstudent.setIsAlumni(student.getIsAlumni());
			newstudent.setIsBoarding(student.getIsBoarding());
			newstudent.setRegNo(student.getRegNo());
			newstudent.setFirstname(student.getFirstname());
			newstudent.setMiddlename(student.getMiddlename());
			newstudent.setLastname(student.getLastname());
			newstudent.setGender(student.getGender().toUpperCase());
			newstudent.setDob(student.getDob());
			newstudent.setBcertNo(student.getBcertNo());
			newstudent.setCounty(student.getCounty());
			newstudent.setRegTerm(sysConfig.getTerm());
			newstudent.setFinalYear(student.getFinalYear()); 
			newstudent.setFinalTerm(student.getFinalTerm()); 
			newstudent.setPassport(renameImage(student.getPassport(),student.getRegNo()));
			newstudent.setLastUpdated(new Date().toString());  


			String response = "";

			if(studentDAO.updateStudent(newstudent)) { 

				response = "Student basic info updated successfully.";

				//parent
				if(student.hasParent()) {
					StudentParent studentParent = parentsDAO.getParent(accountId, student.getUuid()); 
					studentParent.setName(student.getParentName());
					studentParent.setMobile(student.getParentMobile());
					studentParent.setEmail(student.getParentEmail()); 

					if(parentsDAO.updateParent(studentParent)) { 

						response += "Student parent info updated successfully.";

					}else {

						response += "Student parent info NOT updated.";

					}
				}

				//primary
				if(student.hasPrimary()) {
					StudentPrimary studentPrimary = primaryDAO.getStudentPrimary(accountId, student.getUuid()); 
					studentPrimary.setSchoolName(student.getSchoolName());
					studentPrimary.setIndex(student.getIndex());
					studentPrimary.setKcpemark(student.getKcpemark());
					studentPrimary.setKcpeyear(student.getKcpeyear());

					if(primaryDAO.updateStudentPrimary(studentPrimary)) { 

						response += "Student primary info updated successfully.";

					}else { 

						response += "Student primary info NOT updated.";

					}
				}

			}


			apiResponse.setMessage("success");
			apiResponse.setDescription(response); 

			return apiResponse;

		}


		return apiResponse;
	}





	/** 
	 * 
	 * @param accountId
	 * @param id
	 * @return
	 */
	public Object deleteSubject(String accountId, String uuid) {

		ApiResponse apiResponse = new ApiResponse();

		if(studentSubjectDAO.getSubjectById(accountId, uuid) == null) {
			apiResponse.setMessage("error"); 
			apiResponse.setDescription("invalid id.");
			return apiResponse;

		}else {

			if(studentSubjectDAO.deleteSubject(accountId, uuid)) {
				apiResponse.setMessage("success"); 
				apiResponse.setDescription("Subject was removed successfully.");

			}else {

				apiResponse.setMessage("error"); 
				apiResponse.setDescription("Subject not removed!");
			}

		}

		return apiResponse;
	}
	/** 
	 * 
	 * @param apiSubject
	 * @return
	 */
	public Object updateSubject(ApiSubject apiSubject) {

		ApiResponse apiResponse = new ApiResponse();

		if(studentSubjectDAO.getSubjectById(apiSubject.getAccountId(), apiSubject.getUuid()) == null) {
			apiResponse.setMessage("error"); 
			apiResponse.setDescription("Invalid id!");

		}else {
			StudentSubject studentsub = studentSubjectDAO.getSubjectById(apiSubject.getAccountId(), apiSubject.getUuid());
			studentsub.setSubjectId(apiSubject.getSubjectId());
			studentsub.setStudentId(apiSubject.getStudentId());

			apiResponse.setMessage("sucess"); 
			apiResponse.setDescription("Not Applicable for now!");

		}


		return apiResponse;
	}
	/** 
	 * 
	 * @param apiSubject
	 * @return
	 */
	public Object assignSubject(ApiSubject apiSubject) {


		ApiResponse apiResponse = new ApiResponse();

		if(studentSubjectDAO.getstudentSubject(apiSubject.getStudentId(), apiSubject.getSubjectId()) != null) {
			apiResponse.setMessage("error"); 
			apiResponse.setDescription("Subject already assigned!");
			return apiResponse;


		}else {
			StudentSubject studentsub = new StudentSubject();
			studentsub.setAccountId(apiSubject.getAccountId());
			studentsub.setStudentId(apiSubject.getStudentId()); 
			studentsub.setSubjectId(apiSubject.getSubjectId());

			if(studentSubjectDAO.putStudentSubject(studentsub)) {
				apiResponse.setMessage("success"); 
				apiResponse.setDescription("Subject assiged successfully.");  

			}else {
				apiResponse.setMessage("error"); 
				apiResponse.setDescription("Subject not assiged!"); 

			}

		}

		return apiResponse;
	}


	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @return
	 */
	public List<Object> getSubjects(String accountId, String studentId) {
		List<StudentSubject>  subjectlist = new ArrayList<>();
		if(studentSubjectDAO.getStudentSubjects(studentId) != null) {
			subjectlist = studentSubjectDAO.getStudentSubjects(studentId); 
		}
		ApiSubject apiSubject = new ApiSubject();
		List<ApiSubject>  apiSubjectList = new ArrayList<>();

		subjectlist.forEach(sub -> {
			apiSubject.setAccountId(sub.getAccountId());
			apiSubject.setStudentId(sub.getStudentId());
			apiSubject.setSubjectId(subjectDAO.getSubjectById(accountId, sub.getSubjectId()).getDescription());
			apiSubject.setUuid(sub.getUuid()); 
			sub.getAllocationDate();
			apiSubjectList.add(apiSubject);

		});

		return apiSubjectList.stream().collect(Collectors.toList());
	} 




	/**
	 * 
	 * @param accountId
	 * @param action
	 * @param studentIds
	 * @return
	 */
	public Object studentStatus(String accountId, String action, List<StudentStatus> students) { 

		ApiResponse response = new ApiResponse(); 
		boolean update = false;

		if(StringUtils.equals(action, "activate")) {

			for(StudentStatus studentid : students) {
			   
				if(studentDAO.getStudentById(accountId, studentid.getUuid()) != null) {
					Student student = studentDAO.getStudentById(accountId, studentid.getUuid()); 
					student.setIsActive("1");  
					student.setIsAlumni("0"); 
					update = studentDAO.updateStudent(student);
				}
			}

			if(update) {
				response.setMessage("success");
				response.setDescription("Student(s) activated successfully."); 

			}else {
				response.setMessage("error");
				response.setDescription("Something went wrong, try again later."); 
				
			}

		}else if(StringUtils.equals(action, "inactivate")) {
			
			for(StudentStatus studentid : students) {

				if(studentDAO.getStudentById(accountId, studentid.getUuid()) != null) {
					Student student = studentDAO.getStudentById(accountId, studentid.getUuid());
					student.setIsActive("0");  
					student.setIsAlumni("1");  
					update = studentDAO.updateStudent(student);
				}
			}
			
			if(update) {
				response.setMessage("success");
				response.setDescription("Student(s) inactivated successfully."); 

			}else {
				response.setMessage("error");
				response.setDescription("Something went wrong, try again later."); 
				
			}



		}else {

			response.setMessage("error");
			response.setDescription("Invalid action '"+action+"'"); 

		}


		return response;
	}

	/**
	 * 
	 * @param accountId
	 * @param changeClass
	 * @return
	 */
	public Object changeClass(String accountId, List<ChangeClass> changeClass) {

		ApiResponse response = new ApiResponse();
		boolean update = false;

		for(ChangeClass stream : changeClass) {
			
			if(studentDAO.getStudentById(accountId, stream.getStudentId()) != null) {
				Student student = studentDAO.getStudentById(accountId, stream.getStudentId()); 
				student.setCurrentStream(stream.getNewClassId()); 
				update = studentDAO.updateStudent(student);
			}
		
		}
		
		if(update) {
			response.setMessage("success");
			response.setDescription("Class changed successfully."); 

		}else {
			response.setMessage("error");
			response.setDescription("Something went wrong, try again later."); 
			
		}

		return response;
	}




	/**
	 * to detect duplicate value
	 * 
	 * @param value
	 * @return
	 */
	private boolean hasDuplicate(String value, String accountId) { 
		List<Student> accountList = new ArrayList<>();
		//if not account with such a key, return true and proceed

		if(studentDAO.findDuplicate(accountId, value) == null) { 
			return false;
		}else {
			accountList = studentDAO.findDuplicate(accountId, value); 
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
	 * 
	 * @param initalName
	 * @param regNo
	 * @return
	 */
	private String renameImage(String initalName,String regNo) {
		String renamed= initalName;

		File passport = new File(DATA_DIRECTORY+initalName); 

		if(passport.renameTo(new File(DATA_DIRECTORY+regNo+".png")))
			renamed= regNo+".png";
		return renamed;

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
