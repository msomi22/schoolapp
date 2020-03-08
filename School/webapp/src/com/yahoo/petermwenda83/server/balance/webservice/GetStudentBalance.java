/**
 * 
 */
package com.yahoo.petermwenda83.server.balance.webservice;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.money.StudentBalance;

/**
 * @author peter
 *
 */

@WebService(endpointInterface="com.yahoo.petermwenda83.server.balance.webservice.SchoolGetStudentBalance")
public class GetStudentBalance implements SchoolGetStudentBalance {
	
	/*private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static StudentOtherMoniesDAO studentOtherMoniesDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static ExamConfigDAO examConfigDAO;
	private static TermFeeDAO termFeeDAO;
	
	static {
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();

		studentOtherMoniesDAO = StudentOtherMoniesDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
	}*/

	@Override
	public WsResponse findStudentBalance(WsRequest request) {
		WsResponse response = new WsResponse();
		response.setAdmNo(request.getAdmNo()); 
		List<ResponseParam> responseParams = new ArrayList<>();
		/*
		SchoolAccount school;
		Student student = new Student();
		String fullname = "fullname";
		Date admdate = null;
		String admterm = ""; 
		String studentuuid = "";
		int finalyear = 0;
		String schooluuid = "";
		
		if(accountDAO.getSchoolByUsername(request.getAccount())!=null){
			school = accountDAO.getSchoolByUsername(request.getAccount());
			schooluuid = school.getUuid();
				if(studentDAO.getStudentObjByadmNo(schooluuid, request.getAdmNo()) !=null){
					student = studentDAO.getStudentObjByadmNo(schooluuid, request.getAdmNo());

					fullname = StringUtils.capitalize(student.getFirstname()) + " "+ StringUtils.capitalize(student.getLastname()) + " "+ StringUtils.capitalize(student.getSurname());
					admdate = student.getAdmissionDate();
					admterm = student.getRegTerm();
					studentuuid = student.getUuid();
					finalyear = student.getFinalYear();

					Locale locale = new Locale("en","KE"); 
					NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
					double balance = 0;
					String feebalance = "";
					StudentBalance studentBal = new StudentBalance();
					balance = studentBal.findBalance(termFeeDAO,examConfigDAO,studentFeeDAO,studentOtherMoniesDAO,admdate,admterm,studentuuid,schooluuid,finalyear); 
					feebalance = nf.format(balance);
					
					ResponseParam bal = new ResponseParam();
					bal.setParamName("Balance");
					bal.setParamValue(feebalance); 
					responseParams.add(bal); 
					
					ResponseParam name = new ResponseParam();
					name.setParamName("Name");
					name.setParamValue(fullname); 
					responseParams.add(name); 
					
				}else {
					ResponseParam studentNotFound = new ResponseParam();
					studentNotFound.setParamName("Error");
					studentNotFound.setParamValue("Student not found"); 
					responseParams.add(studentNotFound); 
				}
		}else {
			ResponseParam accountInvalid = new ResponseParam();
			accountInvalid.setParamName("Error");
			accountInvalid.setParamValue("Invalid account"); 
			responseParams.add(accountInvalid); 
		}
		*/
		
		ResponseParam accountInvalid = new ResponseParam();
		accountInvalid.setParamName("Error");
		accountInvalid.setParamValue("Invalid account"); 
		responseParams.add(accountInvalid); 
		
		response.setResponseParams(responseParams);
		return response;
	}

	@Override
	public WsResponse getStudentDetails(WsRequest request) {
		WsResponse response = new WsResponse();
		response.setAdmNo(request.getAdmNo()); 
		List<ResponseParam> responseParams = new ArrayList<>();
		ResponseParam param1 = new ResponseParam();
		param1.setParamName("Name");
		param1.setParamValue("Peter Mwenda"); 
		
		ResponseParam param2 = new ResponseParam();
		param2.setParamName("Name");
		param2.setParamValue("Peter Mwenda"); 
		
		responseParams.add(param1); 
		responseParams.add(param2); 
		response.setResponseParams(responseParams);
		return response;
	}


}
