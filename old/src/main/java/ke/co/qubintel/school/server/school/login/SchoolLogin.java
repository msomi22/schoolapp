/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.school.login;

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

import ke.co.qubintel.school.server.api.rest.jwt.ApiCredentials;
import ke.co.qubintel.school.server.api.rest.jwt.JWT;
import ke.co.qubintel.school.server.bean.staff.Staff;
import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.persistence.staff.AcessLevelDAO;
import ke.co.qubintel.school.server.persistence.staff.StaffDAO;
import ke.co.qubintel.school.server.servlet.util.SecurityUtil;
import ke.co.qubintel.school.server.session.SessionConstants;

/**
 * 
 * @author peter
 *
 */
public class SchoolLogin extends HttpServlet {

	private static final long serialVersionUID = -5120422875987844562L;
	private static StaffDAO staffDAO;
	private static AccountDAO accountDAO;
	private static AcessLevelDAO acessLevelDAO;
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
		acessLevelDAO = AcessLevelDAO.getInstance();

		logger = Logger.getLogger(this.getClass());

	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);// current session
		String message = "";

		if (session != null) {
			session.invalidate();
		}
		session = request.getSession(true);

		String schoolUsername = StringUtils.trimToEmpty(request.getParameter("schoolUsername"));
		String staffUsername = StringUtils.trimToEmpty(request.getParameter("staffUsername"));
		String staffPassword = StringUtils.trimToEmpty(request.getParameter("staffPassword"));

		if (accountDAO.getAccount(schoolUsername, "1") == null) {

			session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, "Incorrect Credentials!");
			response.sendRedirect("index.jsp");

		} else if (staffDAO.getStaffByUsername(accountDAO.getAccount(schoolUsername, "1").getUuid(),
				staffUsername) == null) {

			session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, "Incorrect Credentials!");
			response.sendRedirect("index.jsp");

		}/*else if (!StringUtils.contains(accountDAO.getAccount(schoolUsername, "1").getName(), "Mangu")) { 

			message = "Sorry! This Software is custom made for \"Mangu Boys High School\""; 
			session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, message);
			response.sendRedirect("index.jsp");

		} */else {

			Staff staff = staffDAO.getStaffByUsername(accountDAO.getAccount(schoolUsername, "1").getUuid(),
					staffUsername);

			if (StringUtils.equals(staff.getPassword(), SecurityUtil.getMD5Hash(staffPassword))) {

				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID, staff.getAccountId());
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY, schoolUsername);
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_SUCCESS,
						SessionConstants.SCHOOL_ACCOUNT_LOGIN_SUCCESS);
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_TIME,
						String.valueOf(new Date().getTime()));

				request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME, staff.getUsername());
				request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID, staff.getUuid());
				request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_CATEGORY,
						staff.getAcessLevelId());

				// token
				ApiCredentials apiKey = new ApiCredentials();
				// String id, String issuer, String subject, long ttlMillis, String secret
				
				//String id, String issuer, String subject, long ttlMillis, String secret
				request.getSession().setAttribute(SessionConstants.USER_JSON_WEB_TOKEN,JWT.createJWT(staff.getUuid(), 
						staff.getAccountId(), staff.getUsername(), System.currentTimeMillis(), apiKey.getSecret()));

				if (StringUtils.equals(staff.getAcessLevelId(), acessLevelDAO.getAcessLevelById(staff.getAccountId(), "100").getUuid())
						| StringUtils.equals(staff.getAcessLevelId(), acessLevelDAO.getAcessLevelById(staff.getAccountId(), "200").getUuid())
						| StringUtils.equals(staff.getAcessLevelId(), acessLevelDAO.getAcessLevelById(staff.getAccountId(), "500").getUuid())
						| StringUtils.equals(staff.getAcessLevelId(), acessLevelDAO.getAcessLevelById(staff.getAccountId(), "600").getUuid()))

					response.sendRedirect("school/studentIndex.jsp");

				else if (StringUtils.equals(staff.getAcessLevelId(), acessLevelDAO.getAcessLevelById(staff.getAccountId(), "400").getUuid())
						| StringUtils.equals(staff.getAcessLevelId(), acessLevelDAO.getAcessLevelById(staff.getAccountId(), "300").getUuid())
						)
					response.sendRedirect("school/generateReport.jsp");
				
				else if (StringUtils.equals(staff.getAcessLevelId(), acessLevelDAO.getAcessLevelById(staff.getAccountId(), "700").getUuid()))
					response.sendRedirect("school/fee.jsp");

				logger.info("success");

			} else {
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, "Incorrect Credentials!");
				response.sendRedirect("index.jsp");

			}

		}

	}

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
