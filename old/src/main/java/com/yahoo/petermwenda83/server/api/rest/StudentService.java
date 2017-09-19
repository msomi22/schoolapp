/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.otherfee.OtherFee;
import com.yahoo.petermwenda83.bean.otherfee.RevertedMoney;
import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.bean.student.StudentSubject;
import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.RevertedMoneyDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.api.filter.StudentFilter;
import com.yahoo.petermwenda83.server.api.rest.bean.APIParentPrimary;
import com.yahoo.petermwenda83.server.api.rest.bean.APIStudent;
import com.yahoo.petermwenda83.server.api.rest.bean.APIStudentFee;
import com.yahoo.petermwenda83.server.api.rest.bean.APIStudentOtherFee;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiSubject;
import com.yahoo.petermwenda83.server.api.rest.bean.ChangeClass;
import com.yahoo.petermwenda83.server.api.rest.bean.GoKeMoney;
import com.yahoo.petermwenda83.server.api.rest.bean.Response;
import com.yahoo.petermwenda83.server.api.rest.bean.RevertedFee;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentFeeAPI;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentInfo;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentPayFee;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentStatus;
import com.yahoo.petermwenda83.server.servlet.finance.FeeConstants;
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
	private static OtherFeeDAO otherFeeDAO;
	private static StudentOtherFeeDAO studentOtherFeeDAO;
	private static RevertedMoneyDAO revertedMoneyDAO;

	private static SysConfigDAO sysConfigDAO;
	private static EmailValidator emailValidator;

	private static PrimaryDAO primaryDAO;
	private static ParentsDAO parentsDAO;

	private static StudentSubjectDAO studentSubjectDAO;

	private static SubjectDAO subjectDAO;

	private static FeeBreakdownDAO feeBreakdownDAO;

	private static final String DATA_DIRECTORY = "/home/"+System.getProperty("user.name")+"/school/uploads/";

	static{
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		otherFeeDAO = OtherFeeDAO.getInstance();
		studentOtherFeeDAO = StudentOtherFeeDAO.getInstance();
		revertedMoneyDAO = RevertedMoneyDAO.getInstance();

		sysConfigDAO = SysConfigDAO.getInstance();
		emailValidator = EmailValidator.getInstance();

		parentsDAO= ParentsDAO.getInstance();
		primaryDAO=PrimaryDAO.getInstance();

		studentSubjectDAO = StudentSubjectDAO.getInstance();

		subjectDAO = SubjectDAO.getInstance();

		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
	}


	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @return
	 */
	public Object getStudentById(String accountId, String studentId) {

		ApiResponse apiResponse = new ApiResponse();

		APIStudent apiStudent = new APIStudent();



		if(studentDAO.getStudentById(accountId, studentId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("studentId is invalid!");

		}else {

			try {



				BeanUtils.copyProperties(apiStudent, studentDAO.getStudentById(accountId, studentId));

				APIParentPrimary apiParentPrimary = new APIParentPrimary();

				//primary
				if(primaryDAO.getStudentPrimary(accountId, studentId) != null) {

					StudentPrimary primary = primaryDAO.getStudentPrimary(accountId, studentId);

					apiParentPrimary.setSchoolName(primary.getSchoolName());
					apiParentPrimary.setIndex(primary.getIndex());
					apiParentPrimary.setKcpemark(primary.getKcpemark());
					apiParentPrimary.setKcpeyear(primary.getKcpeyear());
				}



				//parent
				if(parentsDAO.getParent(accountId, studentId) != null) {

					StudentParent studentParent = parentsDAO.getParent(accountId, studentId);

					apiParentPrimary.setParentName(studentParent.getName());
					apiParentPrimary.setParentEmail(studentParent.getEmail());
					apiParentPrimary.setParentMobile(studentParent.getMobile());
				}



				apiStudent.setApiParentPrimary(apiParentPrimary);

			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		}

		return apiStudent;
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
			apiStudent.setAdmissionDate(student.getAdmissionDate().toString());

			streamStudents.add(apiStudent); 

		});



		return streamStudents; 
	}

	/**
	 * 
	 * @param studentPayFee
	 * @return
	 */
	public Object payFee(StudentPayFee studentPayFee) {

		Response response = new Response();

		//validation
		if(StringUtils.isEmpty(studentPayFee.getAccountId())) {

			response.setMessage("error");
			response.setDescription("AccountId is invalid!");
			return response;

		}else if(accountDAO.getAccountById(studentPayFee.getAccountId()) != null) { 

			response.setMessage("error");
			response.setDescription("AccountId is invalid!");
			return response;

		}else if(StringUtils.isEmpty(studentPayFee.getStaffId())) { 

			response.setMessage("error");
			response.setDescription("Staff is invalid!");
			return response;

		}else if(staffDAO.getStaff(studentPayFee.getAccountId(), studentPayFee.getStaffId()) != null) {

			response.setMessage("error");
			response.setDescription("Staff is invalid!");
			return response;

		}else if(studentDAO.getStudentByregNo(studentPayFee.getAccountId(), studentPayFee.getRegNo()) == null) {

			response.setMessage("error");
			response.setDescription("Student RegNo is invalid!");
			return response;

		}else if(!StringUtils.isNumeric(studentPayFee.getAmount())) {

			response.setMessage("error");
			response.setDescription("Amount is invalid!");
			return response;


		}else if(Integer.valueOf(studentPayFee.getAmount()) < 1 || Integer.valueOf(studentPayFee.getAmount()) > 100000) {

			response.setMessage("error");
			response.setDescription("Amount is invalid!");
			return response;

		}else {

			Student student = studentDAO.getStudentByregNo(studentPayFee.getAccountId(), studentPayFee.getRegNo());
			SysConfig sysConfig = sysConfigDAO.getSysConfig(studentPayFee.getAccountId()); 

			StudentFee studentFee = new StudentFee(); 
			studentFee.setAccountId(studentPayFee.getAccountId());
			studentFee.setStudentId(student.getUuid());
			studentFee.setAmountPaid(Integer.valueOf(studentPayFee.getAmount()));
			studentFee.setPayMode(studentPayFee.getPaymentMode());
			studentFee.setTransactionId(studentPayFee.getTransactionId());
			studentFee.setPaidHas(student.getIsBoarding()); 
			studentFee.setTermPiad(sysConfig.getTerm());
			studentFee.setYearPaid(sysConfig.getYear());

			if(studentFeeDAO.putStudentFee(studentFee)) {
				response.setMessage("success");
				response.setDescription("Fee paid successsfully."); 

			}else {
				response.setMessage("error");
				response.setDescription("Something went wrong, contact Admin!");

			}

		}


		return response;
	}

	/**
	 * 
	 * @param goKeMoney
	 * @return
	 */
	public Object addGoKeMoney(GoKeMoney goKeMoney) {

		Response response = new Response();

		if(studentDAO.getStudentById(goKeMoney.getAccountId(), goKeMoney.getStudentId()) == null) {
			response.setMessage("error");
			response.setDescription("Invalid studentId!");
			return response;

		}else if(sysConfigDAO.getSysConfig(goKeMoney.getAccountId())== null){
			response.setMessage("error");
			response.setDescription("Unexpected error occured, contact Admin!"); 
			return response;

		}
		if(feeBreakdownDAO.getFeeBreakdown(goKeMoney.getAccountId(), FeeConstants.GVMT_MONEY_CODE, sysConfigDAO.getSysConfig(goKeMoney.getAccountId()).getTerm(),
				sysConfigDAO.getSysConfig(goKeMoney.getAccountId()).getYear(), FeeConstants.GVMT_MONEY_STATUS_ACTIVE) == null){ 
			response.setMessage("error");
			response.setDescription("Term/Year not set or GoKe money inactive! Contact Admin.");  
			return response;

		}else {

			Student student = studentDAO.getStudentById(goKeMoney.getAccountId(), goKeMoney.getStudentId());
			SysConfig sysConfig = sysConfigDAO.getSysConfig(goKeMoney.getAccountId()); 

			String feeBreakdownId = feeBreakdownDAO.getFeeBreakdown(goKeMoney.getAccountId(), FeeConstants.GVMT_MONEY_CODE, sysConfig.getTerm(),
					sysConfig.getYear(), FeeConstants.GVMT_MONEY_STATUS_ACTIVE).getUuid();


			StudentFee studentFee = new StudentFee(); 
			studentFee.setAccountId(goKeMoney.getAccountId());
			studentFee.setStudentId(student.getUuid());
			studentFee.setAmountPaid((int)FeeConstants.getGoKeFee(goKeMoney.getAccountId(), feeBreakdownId));   
			studentFee.setPayMode(FeeConstants.GVMT_MONEY_CODE);
			studentFee.setTransactionId(FeeConstants.GVMT_MONEY_CODE+RandomStringUtils.randomAlphabetic(5)); 
			studentFee.setPaidHas(student.getIsBoarding()); 
			studentFee.setTermPiad(sysConfig.getTerm());
			studentFee.setYearPaid(sysConfig.getYear());

			if(studentFeeDAO.getStudentFee(goKeMoney.getAccountId(), student.getUuid(), FeeConstants.GVMT_MONEY_CODE,
					sysConfig.getTerm(), sysConfig.getYear()) == null) {

				if(studentFeeDAO.putStudentFee(studentFee)) {
					response.setMessage("success");
					response.setDescription("GoKe Fee paid successsfully."); 
					return response;

				}else {
					response.setMessage("error");
					response.setDescription("Something went wrong, contact Admin!");
					return response;

				}

			}else {
				response.setMessage("error");
				response.setDescription("GoKe money already assigned!");
				return response;
			}

		}

	}




	/**
	 * 
	 * @param accountId
	 * @param regNo
	 * @return
	 */
	public Object getStudentFee(String accountId, String regNo) {

		StudentResponse studentResponse = new StudentResponse();

		if(studentDAO.getStudentByregNo(accountId, regNo) == null) {
			Response response = new Response();
			response.setMessage("error");
			response.setDescription("RegNo/AccountId Not found!"); 

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
			List<APIStudentFee> apiStudentFeeList  = new ArrayList<>();
			if(studentFeeDAO.getStudentFeeList(accountId, student.getUuid(), term, year) != null) {
				feeHistory = studentFeeDAO.getStudentFeeList(accountId, student.getUuid(), term, year);
				feeHistory.forEach(feeHist ->{
					APIStudentFee apiStudentFee = new APIStudentFee();

					try {
						BeanUtils.copyProperties(apiStudentFee, feeHist); 
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}

					apiStudentFeeList.add(apiStudentFee);
				});

			}

			studentFeeAPI.setFeeHistory(apiStudentFeeList);

			List<APIStudentOtherFee> otherfeeHistory = new ArrayList<>();
			List<RevertedFee> revertedFeeList  = new ArrayList<>();

			if(studentOtherFeeDAO.getStudentOtherFeeList(accountId, student.getUuid()) != null) {

				List<StudentOtherFee> list = studentOtherFeeDAO.getStudentOtherFeeList(accountId, student.getUuid());

				for(StudentOtherFee otherfee : list) {
					APIStudentOtherFee studentOtherFee = new APIStudentOtherFee();

					if(otherFeeDAO.getOtherFee(accountId, otherfee.getOtherFeeId()) != null) {

						OtherFee otherFee = otherFeeDAO.getOtherFee(accountId, otherfee.getOtherFeeId());

						studentOtherFee.setAmount(String.valueOf(otherFee.getAmount())); 
						studentOtherFee.setDateAllocated(otherfee.getDateAllocated().toString());
						studentOtherFee.setOtherFeeId(otherFee.getDescription()); 
						studentOtherFee.setTermPiad(otherfee.getTerm()); 

						otherfeeHistory.add(studentOtherFee);
					}


				}

			}

			if(revertedMoneyDAO.getRevertedMoneyList(accountId, student.getUuid()) != null) {

				List<RevertedMoney> revertedMoneyList = revertedMoneyDAO.getRevertedMoneyList(accountId, student.getUuid());

				RevertedFee revertedFee = new RevertedFee();

				for(RevertedMoney revertedMoney : revertedMoneyList) {

					if(otherFeeDAO.getOtherFee(accountId, revertedMoney.getOtherFeeId()) != null) {

						OtherFee otherFee = otherFeeDAO.getOtherFee(accountId, revertedMoney.getOtherFeeId());

						revertedFee.setAmount(String.valueOf(otherFee.getAmount()));
						revertedFee.setOtherFeeId(otherFee.getDescription());
						revertedFee.setDateReverted(revertedMoney.getDateReverted().toString()); 
						revertedFeeList.add(revertedFee);
					}
				}

			}



			studentFeeAPI.setOtherfeeHistory(otherfeeHistory);
			studentFeeAPI.setRevertedFeeList(revertedFeeList);  

			studentResponse.setStudentFeeAPI(studentFeeAPI);


		}

		return studentResponse;
	}

	/**
	 * 
	 * @param accountId
	 * @param student
	 * @return
	 */
	public Object addNewStudent(String accountId, StudentInfo student) {

		Response apiResponse = new Response();


		if(sysConfigDAO.getSysConfig(accountId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("System Config not set!");

			return apiResponse;

		}
		else if(StringUtils.isBlank(accountId)) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account Id is invalid.");

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

		}else if(!validStatus(student.getIsBoarding())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("IsBoarding not set.");

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
			int year = Calendar.getInstance().get(Calendar.YEAR);


			//basic
			Student newstudent = new Student();
			newstudent.setRegStream(student.getRegStream()); 
			newstudent.setCurrentStream(student.getCurrentStream());
			newstudent.setIsActive("1");
			newstudent.setIsAlumni("0");
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
			newstudent.setFinalYear(year + 3); 
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

		Response apiResponse = new Response();



		if(sysConfigDAO.getSysConfig(accountId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("System Config not set!");

			return apiResponse;

		}else if(StringUtils.isBlank(accountId)) {
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

		}else if(!validStatus(student.getIsBoarding())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("IsBoarding not set.");

			return apiResponse;

		}else if(!validStatus(student.getIsActive())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("IsActive not set.");

			return apiResponse;

		}else if(!validStatus(student.getIsAlumni())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("IsAlumni not set.");

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
	public Object getListofSubjects(String accountId) { 

		Response response = new Response();

		if(subjectDAO.getSubjects(accountId) == null) { 
			response.setMessage("error"); 
			response.setDescription("Invalid accountId!");
			return response;

		}else {

			ApiSubject apiSubject = new ApiSubject();
			List<ApiSubject> apiSubjectList = new ArrayList<>();
			subjectDAO.getSubjects(accountId).forEach(subject ->{

				apiSubject.setAccountId(accountId);
				apiSubject.setDescription(subject.getDescription());
				apiSubject.setSubjectId(subject.getUuid());
				apiSubjectList.add(apiSubject);
			});

			return apiSubjectList; 

		}

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
			apiSubject.setSubjectId(subjectDAO.getSubjectById(accountId, sub.getSubjectId()).getUuid());
			apiSubject.setUuid(sub.getUuid()); 
			apiSubject.setDescription(subjectDAO.getSubjectById(accountId, sub.getSubjectId()).getDescription());

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
	 * 
	 * @param accountId
	 * @param filter
	 * @return
	 */

	public Object getStudentFilter(String accountId, StudentFilter filter) {

		List<StudentInfo> studentInfoList = new ArrayList<>();

		if(filter.getStart() >= 0 && filter.getSize() > 0){ 

			studentDAO.getAllStudent(accountId, filter.getStart(), filter.getSize()).forEach(student -> {

				StudentInfo studentInfo = new StudentInfo();

				try {
					BeanUtils.copyProperties(studentInfo, student); 
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				studentInfoList.add(studentInfo);
			});

		}else if(!StringUtils.isBlank(filter.getQuery())) {

			studentDAO.searchStudent(accountId, filter.getQuery()).forEach(student -> {
				StudentInfo studentInfo = new StudentInfo();

				try {
					BeanUtils.copyProperties(studentInfo, student); 
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				studentInfoList.add(studentInfo);
			});
		}else if(!StringUtils.isBlank(filter.getCurrentStream())){

			studentDAO.getStudentByStream(accountId, filter.getCurrentStream()).forEach(student -> {
				StudentInfo studentInfo = new StudentInfo();

				try {
					BeanUtils.copyProperties(studentInfo, student); 
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				studentInfoList.add(studentInfo);
			});


		}else {
			studentDAO.getAllStudent(accountId, 0, 15).forEach(student -> {
				StudentInfo studentInfo = new StudentInfo();

				try {
					BeanUtils.copyProperties(studentInfo, student); 
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				studentInfoList.add(studentInfo);
			});

		}


		return studentInfoList;
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


	

	/**
	 * 
	 * @param isBoarding
	 * @return
	 */
	private boolean validStatus(String isBoarding) {
		String[] allowed = {"1","2",};
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);
		if(allowedList.contains(isBoarding)) {
			return true;
		}else {
			return false;
		}
	}


}
