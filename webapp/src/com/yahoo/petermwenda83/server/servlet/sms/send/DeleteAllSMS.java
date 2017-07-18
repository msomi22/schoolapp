package com.yahoo.petermwenda83.server.servlet.sms.send;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.schoolaccount.SmsSend;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class DeleteAllSMS extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2415014945836572645L;
	final String SMS_DELETE_SUCCESS = "SMSes deleted Successfully.";
	final String SMS_DELETE_ERROR = "An error occured while deleting SMSes.";
	final String CATEGORY_SELECT_ERROR = "Please select a category to delete.";

	private static SmsSendDAO smsSendDAO;

	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		smsSendDAO = SmsSendDAO.getInstance();

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String status = StringUtils.trimToEmpty(request.getParameter("SMScategory"));
		
		if(StringUtils.isBlank(status)){
			 session.setAttribute(SessionConstants.SMS_SEND_ERROR, CATEGORY_SELECT_ERROR); 
			 
		}else{
			
			SmsSend smsSend = new SmsSend();
			smsSend.setStatus(status);
			if(smsSendDAO.deleteSmsSend(smsSend)){
				session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_DELETE_SUCCESS); 
				
			}else{
				session.setAttribute(SessionConstants.SMS_SEND_ERROR, SMS_DELETE_ERROR); 
				
			}
			
		}
		
		 response.sendRedirect("deleteSms.jsp");  
		 return; 

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
