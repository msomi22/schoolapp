/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.*;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.FeeResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentPayFee;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentResponse;
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

	static{
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
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
	 * @author peter
	 *
	 */




}
