package com.yahoo.petermwenda83.server.servlet.student.clearance;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.money.StudentClearance;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.persistence.money.StudentClearanceDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class ClearStudent extends HttpServlet{

	final String INIT_ERROR = "Something went wrong, try again.";
	final String INIT_ERROR_AMNT = "Amount not allowed , make correction and try again.";
	final String INIT_SUCCESS = "Success , find whether the  fee balance is updated.";

	private static StudentFeeDAO studentFeeDAO;
	private static StudentClearanceDAO studentClearanceDAO;

	/**   
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		studentFeeDAO = StudentFeeDAO.getInstance(); 
		studentClearanceDAO = StudentClearanceDAO.getInstance();

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String studentuuid = StringUtils.trimToEmpty(request.getParameter("studentuuid"));
		String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
		String finalyear = StringUtils.trimToEmpty(request.getParameter("finalyear"));
		String clearingAmount = StringUtils.trimToEmpty(request.getParameter("clearingAmount"));
		String securityKey = StringUtils.trimToEmpty(request.getParameter("securityKey"));
		

		if(StringUtils.isBlank(studentuuid)){
			session.setAttribute(SessionConstants.CLEAR_ERROR, INIT_ERROR); 

		}else if(StringUtils.isBlank(finalyear)){
			session.setAttribute(SessionConstants.CLEAR_ERROR, INIT_ERROR); 

		}else if(StringUtils.isBlank(schooluuid)){
			session.setAttribute(SessionConstants.CLEAR_ERROR, INIT_ERROR); 

		}else if(!isNumeric(clearingAmount)){ 
			session.setAttribute(SessionConstants.CLEAR_ERROR, INIT_ERROR_AMNT); 

		}else if(Integer.parseInt(clearingAmount) < 0 || Integer.parseInt(clearingAmount) > 100000){ 
			session.setAttribute(SessionConstants.CLEAR_ERROR, INIT_ERROR_AMNT); 

		}else if(!StringUtils.equals(securityKey, PropertiesConfig.getConfigValue("SC_SECURITY_KEY"))){ 
			session.setAttribute(SessionConstants.CLEAR_ERROR, INIT_ERROR); 

		}else{

			String finalterm = "";
			if(studentClearanceDAO.getClearanceByYear(studentuuid,finalyear)!=null){
				StudentClearance clearance = studentClearanceDAO.getClearanceByYear(studentuuid,finalyear);
				finalterm = clearance.getTerm();
			}

			StudentFee studentFe = new StudentFee();
			studentFe.setSchoolAccountUuid(schooluuid);
			studentFe.setStudentUuid(studentuuid);
			studentFe.setTransactionID("CLEARING AMOUNT-STUDENT CLEARED! ");
			studentFe.setTerm(finalterm);
			studentFe.setYear(finalyear);
			studentFe.setDatePaid(new Date());
			studentFe.setSystemUser("SuperUser");
			studentFe.setAmountPaid(Double.parseDouble(clearingAmount));  

			if(studentFeeDAO.putStudentFee(studentFe)){ 
				session.setAttribute(SessionConstants.CLEAR_SUCCESS, INIT_SUCCESS); 
			}


		}

		response.sendRedirect("studentClearance.jsp");  
		return;

	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {  
		try  
		{  
			double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8322473490086165376L;


}
