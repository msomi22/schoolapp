/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.smsapi;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.schoolaccount.SmsApi;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsApiDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/** 
 * @author peter
 *
 */
public class UpdateSmsApi extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2932452508002032402L;
	final String ERROR_SMS_API_UPDATE = "Something went wrong while updating the sms api.";
	final String SUCCESS_SMS_API_UPDATE = "Sms api was updated successfully.";
	
	private static SmsApiDAO smsApiDAO;


	/** 
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       smsApiDAO = SmsApiDAO.getInstance();
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String schoolUuid = StringUtils.trimToEmpty(request.getParameter("schoolUuid"));
       String apiuuid = StringUtils.trimToEmpty(request.getParameter("apiuuid"));
       String apiKey = StringUtils.trimToEmpty(request.getParameter("apiKey"));
       String apiPassword = StringUtils.trimToEmpty(request.getParameter("apiPassword"));
       
       if(StringUtils.isBlank(schoolUuid)){
		     session.setAttribute(SessionConstants.API_UPDATE_ERROR, ERROR_SMS_API_UPDATE); 
		   
	   }else if(StringUtils.isBlank(apiuuid)){ 
		   session.setAttribute(SessionConstants.API_UPDATE_ERROR, ERROR_SMS_API_UPDATE); 
		   
	   }else if(StringUtils.isBlank(apiKey)){
		     session.setAttribute(SessionConstants.API_UPDATE_ERROR, ERROR_SMS_API_UPDATE); 
		     
	   }else if(StringUtils.isBlank(apiPassword)){
		     session.setAttribute(SessionConstants.API_UPDATE_ERROR, ERROR_SMS_API_UPDATE); 
		     
	   }else{
		   
		   SmsApi smsApi;
		   if(smsApiDAO.getSmsApi(schoolUuid) !=null){
			    smsApi = smsApiDAO.getSmsApi(schoolUuid);
		   }else{
			    smsApi = new SmsApi();
			    smsApi.setSchoolAccountUuid(schoolUuid); 
		   }
		   
		   smsApi.setApiKey(apiKey);
		   smsApi.setApiPassword(apiPassword); 
		   
		   if(smsApiDAO.updateSmsApi(smsApi)){
			   session.setAttribute(SessionConstants.API_UPDATE_SUCCESS, SUCCESS_SMS_API_UPDATE);
		   }else{
			   session.setAttribute(SessionConstants.API_UPDATE_ERROR, ERROR_SMS_API_UPDATE);  
		   }
		   
		   
		   
	   }
       
       response.sendRedirect("smsApi.jsp"); 
       return;
	}
   

@Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doPost(request, response);
   }


}
