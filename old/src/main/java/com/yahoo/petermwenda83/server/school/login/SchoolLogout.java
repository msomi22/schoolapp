/**
 * 
 */
package com.yahoo.petermwenda83.server.school.login;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.server.session.SessionConstants;

/**  schoolLogout
 * 
 * @author peter
 *
 */
public class SchoolLogout  extends HttpServlet{

	private Logger logger;
	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		logger = Logger.getLogger(this.getClass());

	}
	
	/**
	    * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	    */
	   @Override
	   protected void doPost(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, IOException {

	       HttpSession session = request.getSession(true);

	       response.sendRedirect("index.jsp");
	       
	       String username = ""; 
	       
	       username = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
	       
		   logger.info(username + " has logged out"); 

	       if (session != null) {
	           //destroy the session
	           session.invalidate();
	           
	           
	           
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
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2993516306818212964L;

}
