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
import com.yahoo.petermwenda83.server.api.rest.jwt.ApiCredentials;
import com.yahoo.petermwenda83.server.api.rest.jwt.JWT;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**
 * 
 * @author peter
 *
 */
public class SchoolLogin extends HttpServlet {

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
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);// current session

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

		} else {

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


				if (StringUtils.equals(staff.getAcessLevelId(), "C3915245-00EE-4EF4-9898-ACE59683DD60")
						| StringUtils.equals(staff.getAcessLevelId(), "615F04C1-00BF-499C-AC7A-B46B69243AAA")
						| StringUtils.equals(staff.getAcessLevelId(), "0DE968C9-7309-C481-58F7-AB6CDB1011EH")
						| StringUtils.equals(staff.getAcessLevelId(), "BDF7F33D-1936-43F3-B14B-8FC3EA3A1265")
						| StringUtils.equals(staff.getAcessLevelId(), "64553348-3229-4869-A13D-CADFC1D3AF46"))

					response.sendRedirect("school/studentIndex.jsp");

				else if (StringUtils.equals(staff.getAcessLevelId(), "1CC7F06E-9938-4850-81FB-9CC249C7CFA2"))
					response.sendRedirect("school/generateReport.jsp");
				else if (StringUtils.equals(staff.getAcessLevelId(), "0DE968C9-7309-C481-58F7-AB6CDB1011EF"))
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
