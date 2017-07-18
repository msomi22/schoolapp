package com.yahoo.petermwenda83.server.servlet.school;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jasypt.util.text.BasicTextEncryptor;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.util.FontImageGenerator;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;
import com.yahoo.petermwenda83.server.session.SessionStatistics;
import com.yahoo.petermwenda83.server.session.SessionStatisticsFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class Login extends HttpServlet{

	// Error message provided when incorrect captcha is submitted
	final String ACCOUNT_SIGN_IN_BAD_CAPTCHA = "The characters you entered did not match those provided in the image.";
	final String ERROR_ACCEPT_TERMS_AND_CONDITION = "You must agree with terms and conditions.";
	final String ERROR_WRONG_USER_DETAIL = "Incorrect staff credentials.";
	final String BLANK_FIELDS_NOT_ALLOWED = "blank fields are not allowed.";
	final String ERROR_SCHOOL_INACTIVE = "You can't login to your school account, call +254718953974 for help.";

	final String STATUS_INACTIVE = "6C03705B-E05E-420B-B5B8-C7EE36643E60";

	/**
	 * 
	 */
	private static final long serialVersionUID = 4366123889354229389L;

	private static StaffDAO staffDAO;
	private BasicTextEncryptor textEncryptor;
	private String hiddenCaptchaStr = "";
	private Cache schoolCache, statisticsCache;
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
		CacheManager mgr = CacheManager.getInstance();
		textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(FontImageGenerator.SECRET_KEY);
		schoolCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
		statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
		logger = Logger.getLogger(this.getClass());
		staffDAO = StaffDAO.getInstance();
		onlineUsersMap = new HashMap<String,String>();
		context = getServletContext();

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
		String staffposition = StringUtils.trimToEmpty(request.getParameter("staffposition"));
		String staffusername = StringUtils.trimToEmpty(request.getParameter("staffusername"));
		String staffpassword = StringUtils.trimToEmpty(request.getParameter("staffpassword"));
		hiddenCaptchaStr = request.getParameter("captchaHidden");
		String captchaAnswer = request.getParameter("captchaAnswer").trim();


		SchoolAccount school = new SchoolAccount();
		Element element;
		if ((element = schoolCache.get(schoolusername)) != null) {
			school = (SchoolAccount) element.getObjectValue();

		}

		if(school!=null){

			Staff staff = null;
			if(staffDAO.getStaffByUsername(school.getUuid(), staffusername) !=null){
				staff = staffDAO.getStaffByUsername(school.getUuid(), staffusername);
			}

			if(staffDAO.getStaffByUsername(school.getUuid(), staffusername)==null){
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, ERROR_WRONG_USER_DETAIL); 
				response.sendRedirect("index.jsp");

			}

			else  if (!validateCaptcha(hiddenCaptchaStr, captchaAnswer)) {
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, ACCOUNT_SIGN_IN_BAD_CAPTCHA);
				response.sendRedirect("index.jsp");

			} else  if (StringUtils.equals(school.getStatusUuid(), STATUS_INACTIVE)) {
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, ERROR_SCHOOL_INACTIVE);
				response.sendRedirect("index.jsp");

			}

			else if(!StringUtils.equals(staffposition, staff.getPositionUuid())){ 
				session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, ERROR_WRONG_USER_DETAIL);
				response.sendRedirect("index.jsp");

			}

			else{


				if (StringUtils.equals(SecurityUtil.getMD5Hash(staffpassword), staff.getPassword())) {

					updateCache(school.getUuid(),staff.getUuid());

					onlineUsersMap.put(staff.getUuid(),session.getId());
					context.setAttribute("onlineUsersMap", onlineUsersMap);
					

					session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID, school.getUuid());
					session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY, school.getUsername());
					session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_SUCCESS, SessionConstants.SCHOOL_ACCOUNT_LOGIN_SUCCESS); 
					session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_TIME, String.valueOf(new Date().getTime()));
					request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME, staff.getUserName()); 
					request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID, staff.getUuid());
					request.getSession().setAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_POSITION, staffposition);
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



	private boolean validateCaptcha(String encodedSystemCaptcha, String userCaptcha) {
		boolean valid = false;
		String decodedHiddenCaptcha = "";

		try {
			decodedHiddenCaptcha = textEncryptor.decrypt(URLDecoder.decode(encodedSystemCaptcha, "UTF-8"));

		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException while trying to validate captcha.");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		if (StringUtils.equalsIgnoreCase(decodedHiddenCaptcha, userCaptcha)) {
			valid = true;
		}

		return valid;
	}

	/**
	 * @param accountuuid
	 */
	private void updateCache(String accountuuid,String staffuuid) {
		SessionStatistics statistics = SessionStatisticsFactory.getSessionStatistics(accountuuid,staffuuid);
		statisticsCache.put(new Element(accountuuid, statistics));


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
