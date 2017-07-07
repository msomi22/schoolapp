/**
 * 
 */
package com.yahoo.petermwenda83.server.school.login;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**
 * 
 * @author peter
 *
 */
public class SchoolLogin extends HttpServlet{


	private static final long serialVersionUID = -5120422875987844562L;
	private static StaffDAO staffDAO;
	private static AccountDAO accountDAO;
	private Logger logger;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		staffDAO = StaffDAO.getInstance();
		accountDAO = AccountDAO.getInstance();

		logger = Logger.getLogger(this.getClass());

	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);//current session


		if (session != null) {
			session.invalidate();  
		}
		session = request.getSession(true);


		String schoolUsername = StringUtils.trimToEmpty(request.getParameter("schoolUsername"));
		String staffUsername = StringUtils.trimToEmpty(request.getParameter("staffUsername"));
		String staffPassword = StringUtils.trimToEmpty(request.getParameter("staffPassword"));


		if(accountDAO.getAccount(schoolUsername,"1") == null){

			session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, "Incorrect Credentials -1!");
			response.sendRedirect("index.jsp");

		}else if(staffDAO.getStaffByUsername(accountDAO.getAccount(schoolUsername,"1").getUuid(), staffUsername) == null){ 

			session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, "Incorrect Credentials -2!");
			response.sendRedirect("index.jsp");

		}else{

			Staff staff = staffDAO.getStaffByUsername(accountDAO.getAccount(schoolUsername,"1").getUuid(), staffUsername);

			if(StringUtils.equals(staff.getPassword(), SecurityUtil.getMD5Hash(staffPassword))){
				
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID, staff.getAccountId());
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY, schoolUsername);
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_SUCCESS, SessionConstants.SCHOOL_ACCOUNT_LOGIN_SUCCESS); 
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_TIME, String.valueOf(new Date().getTime()));
				request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME, staff.getUsername()); 
				request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID, staff.getUuid());
				request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_CATEGORY, staff.getAcessLevelId());
				response.sendRedirect("school/studentIndex.jsp"); 
                
				logger.info("success"); 
				

			}else{
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, "Incorrect Credentials -3!");
				response.sendRedirect("index.jsp");


			}



		}



	}


	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


}
