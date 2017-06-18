package com.yahoo.petermwenda83.server.servlet.school;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.HibernateUtil;
import com.yahoo.petermwenda83.persistence.StorageDAO;
import com.yahoo.petermwenda83.persistence.StorageDAOImpl;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class Login extends HttpServlet{

	// Error message provided when incorrect captcha is submitted
	final String ACCOUNT_SIGN_IN_BAD_CAPTCHA = "The characters you entered did not match those provided in the image.";
	final String ERROR_ACCEPT_TERMS_AND_CONDITION = "You must agree with terms and conditions.";
	final String ERROR_WRONG_USER_DETAIL = "Incorrect staff credentials.";
	final String BLANK_FIELDS_NOT_ALLOWED = "blank fields are not allowed.";
	final String ERROR_SCHOOL_INACTIVE = "You can't login to your school account, call +254718953974 for help.";

	final String STATUS_INACTIVE = "0";

	private StorageDAO storageDAO;
	private SessionFactory sessionFactory;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4366123889354229389L;

	private Logger logger;

	Map<String,String> onlineUsersMap;
	ServletContext context;


	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logger = Logger.getLogger(this.getClass());
		onlineUsersMap = new HashMap<String,String>();
		context = getServletContext();
		
		sessionFactory = HibernateUtil.getSessionFactory();
		storageDAO = new StorageDAOImpl(sessionFactory); 

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

		String schoolusername = StringUtils.trimToEmpty(request.getParameter("schoolusername"));
		String staffacesslevel = StringUtils.trimToEmpty(request.getParameter("staffacesslevel"));
		String staffusername = StringUtils.trimToEmpty(request.getParameter("staffusername"));
		String staffpassword = StringUtils.trimToEmpty(request.getParameter("staffpassword"));
		
		
		Account school = (Account) storageDAO.get(Account.class, schoolusername);

		if(school!=null){

			Staff staff = null;
			staff = (Staff) storageDAO.get(Staff.class, staffusername);
			
			if (StringUtils.equals(school.getIsActive(), STATUS_INACTIVE)) {
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, ERROR_SCHOOL_INACTIVE);
				response.sendRedirect("index.jsp");

			}

			else if(!StringUtils.equals(staffacesslevel, staff.getAcessLevelId())){ 
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, ERROR_WRONG_USER_DETAIL);
				response.sendRedirect("index.jsp");

			}

			else{


				if (StringUtils.equals(SecurityUtil.getMD5Hash(staffpassword), staff.getPassword())) {


					onlineUsersMap.put(staff.getUuid(),session.getId());
					context.setAttribute("onlineUsersMap", onlineUsersMap);


					session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID, school.getUuid());
					session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY, school.getUsername());
					session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_SUCCESS, SessionConstants.SCHOOL_ACCOUNT_LOGIN_SUCCESS); 
					session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_TIME, String.valueOf(new Date().getTime()));
					request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME, staff.getUsername()); 
					request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID, staff.getUuid());
					request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_POSITION, staffacesslevel);
					response.sendRedirect("school/schoolIndex.jsp"); 

				}else {
					session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, ERROR_WRONG_USER_DETAIL);
					response.sendRedirect("index.jsp");

				} 
			}


		}else{
			session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, ERROR_WRONG_USER_DETAIL);
			response.sendRedirect("index.jsp");
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
