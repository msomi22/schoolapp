/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.school.staff;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yahoo.petermwenda83.bean.schoolaccount.SmsSend;
import com.yahoo.petermwenda83.bean.smsapi.AfricasTalking;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.bean.staff.StaffDetails;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO;
import com.yahoo.petermwenda83.server.servlet.sms.send.AfricasTalkingGateway;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**  
 * @author peter
 *
 */
public class ForgotPassword extends HttpServlet{

	final String ERROR_USERNAME_EMPTY = "Username can't be empty"; 
	final String ERROR_USERNAME_NOT_FOUND = "Username was not found in the system";
	final String SUCCESS_PASSWORD_RESET = "New password has been sent to your phone";
	
	private static StaffDAO staffDAO;
	private static StaffDetailsDAO staffDetailsDAO;
	private static SmsSendDAO smsSendDAO;


	/**
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       staffDAO = StaffDAO.getInstance();
       staffDetailsDAO = StaffDetailsDAO.getInstance();
       smsSendDAO = SmsSendDAO.getInstance();
   }
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       String username = StringUtils.trimToEmpty(request.getParameter("username"));
      
       if(StringUtils.isEmpty(username)){
    	   session.setAttribute(SessionConstants.STAFF_UPDATE_SUCCESS, ERROR_USERNAME_EMPTY); 
    	   
       }else if(staffDAO.getStaffUsername(username) ==null){
    	   session.setAttribute(SessionConstants.STAFF_UPDATE_SUCCESS, ERROR_USERNAME_NOT_FOUND); 
       }
       else{
    	   String phone = "";
    	   String formatedphone = "";
		   String realphone = "";
		   String newpassword = "";
		  
    	   Staff staff = staffDAO.getStaffUsername(username);
    	   if(staff!=null){
    		   
    		   
    		   StaffDetails StaffDetail = staffDetailsDAO.getStaffDetail(staff.getUuid());
    		   if(StaffDetail!=null){
    			   phone = StaffDetail.getPhone();
    			   formatedphone = phone.replaceFirst("^0+(?!$)", "");
   				   realphone = "+254"+formatedphone;
    			   //send message
   				//send message
   				AfricasTalking africasTalking = new AfricasTalking();
   				// Specify your login credentials
   				String apiusername = africasTalking.getUsername();
   				String apiKey   = africasTalking.getApiKey();
   				
   				
   				newpassword = RandomStringUtils.randomAlphabetic(5); 
   				
   				String message = "";
   				message = "HI " + username + ", your new password is " +newpassword; 
   				africasTalking.setMessage(message); 
				africasTalking.setRecipients(realphone); 
				// Create a new instance the gateway class
				AfricasTalkingGateway gateway  = new AfricasTalkingGateway(apiusername, apiKey);
				
				String thestatus ="";
				String thenumber ="";
				String themessage ="";
				String thecost ="";

				SmsSend smsSend = new SmsSend();
				if(realphone !=null && message.replaceAll("[\r\n]+", " ") !=null){
				smsSend.setStatus("failed");
				smsSend.setPhoneNo(realphone);
				smsSend.setMessageId(message.replaceAll("[\r\n]+", " "));
				smsSend.setCost("1");
				staff.setPassword(newpassword); 
				if(staffDAO.updateStaff(staff)){
					session.setAttribute(SessionConstants.STAFF_UPDATE_SUCCESS, SUCCESS_PASSWORD_RESET); 
					smsSendDAO.putSmsSend(smsSend);
				}
				
				
				}

				try {
					JSONArray results = gateway.sendMessage(africasTalking.getRecipients(), africasTalking.getMessage());
					for( int i = 0; i < results.length(); ++i ) {
						JSONObject result = results.getJSONObject(i);
					
						thestatus = result.getString("status");
						thenumber = result.getString("number");
						themessage = message;
						thecost = result.getString("cost");
                        
						if(smsSend !=null){
						SmsSend smsSend2 = smsSendDAO.getSmsSend(smsSend.getUuid());
						smsSend2.setStatus(thestatus);
						smsSend2.setPhoneNo(thenumber);
						smsSend2.setMessageId(themessage.replaceAll("[\r\n]+", " "));
						smsSend2.setCost(thecost);
						smsSendDAO.updateSmsSend(smsSend2); 
						}

					}

				}

				catch (Exception e) {
					e.printStackTrace(); 
					
				}
		
    		   }
    		   
    	   }
       }
       
       response.sendRedirect("../index.jsp");  
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
/**
 * 
 */
private static final long serialVersionUID = 1L;
}
