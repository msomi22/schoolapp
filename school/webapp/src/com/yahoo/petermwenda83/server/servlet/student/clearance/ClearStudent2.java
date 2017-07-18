/**
 * 
 */
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

import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class ClearStudent2 extends HttpServlet{


	final String INIT_ERROR = "Something went wrong, try again.";
	final String INIT_SUCCESS = "Success , find whether the  fee balance is updated.";
	
	private static StudentFeeDAO studentFeeDAO;


	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		 studentFeeDAO = StudentFeeDAO.getInstance(); 
 
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String studentuuid = StringUtils.trimToEmpty(request.getParameter("studentuuid"));
		String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
		String finalterm = StringUtils.trimToEmpty(request.getParameter("finalterm"));
		String finalyear = StringUtils.trimToEmpty(request.getParameter("finalyear"));
		String clearingAmount = StringUtils.trimToEmpty(request.getParameter("clearingAmount"));


		if(StringUtils.isBlank(studentuuid)){
			session.setAttribute(SessionConstants.STUDENT_ADD_ERROR, INIT_ERROR); 

		}else if(StringUtils.isBlank(finalterm)){
			session.setAttribute(SessionConstants.STUDENT_ADD_ERROR, INIT_ERROR); 

		}else if(StringUtils.isBlank(finalyear)){
			session.setAttribute(SessionConstants.STUDENT_ADD_ERROR, INIT_ERROR); 

		}else if(StringUtils.isBlank(schooluuid)){
			session.setAttribute(SessionConstants.STUDENT_ADD_ERROR, INIT_ERROR); 

		}else{
			
			StudentFee studentFe = new StudentFee();
			studentFe.setSchoolAccountUuid(schooluuid);
			studentFe.setStudentUuid(studentuuid);
			studentFe.setTransactionID("CLEARING AMOUNT");
			studentFe.setTerm(finalterm);
			studentFe.setYear(finalyear);
			studentFe.setDatePaid(new Date());
			studentFe.setSystemUser("SuperUser");
			studentFe.setAmountPaid(Double.parseDouble(clearingAmount));  
			
			if(studentFeeDAO.putStudentFee(studentFe)){ 
				session.setAttribute(SessionConstants.STUDENT_ADD_SUCCESS, INIT_SUCCESS); 
			}
			
			
		}
		
		response.sendRedirect("#.jsp");  
		return;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8181766699141449276L;
}
