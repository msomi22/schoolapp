/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.school.staff;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/** 
 * @author peter
 *
 */
public class UpdatePassword extends HttpServlet{
	
	/**
	 * 
	 */
	
	private static StaffDAO staffDAO;
	//private static StaffDetailsDAO staffDetailsDAO;
	
	final String ERROR_EMPTY_FIELD = "Empty fields not allowed" ;
	final String PASS_MISMATCH = "Password mismatch" ;
	final String INCORRECT_OLD_PASS = "Old password Incorrect" ;
	final String STAFF_UPDATE_SUCSESS = "Password updated successfully";
	final String STAFF_UPDATE_ERROR = " An error occured while updating Password";
	final String ERROR_USERNAME_PASSWORD_MATCH = "Usernme and password can't be the same";
	
	
	/**
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       staffDAO = StaffDAO.getInstance();
       //staffDetailsDAO = StaffDetailsDAO.getInstance();
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String oldpassword = StringUtils.trimToEmpty(request.getParameter("oldpassword"));
       String newpassowrd = StringUtils.trimToEmpty(request.getParameter("newpassword"));
       String confirmpassword = StringUtils.trimToEmpty(request.getParameter("confirmpassword"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
      
       String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
       String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
      
      if(StringUtils.isEmpty(oldpassword)){
    	   session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, ERROR_EMPTY_FIELD); 
    	   
       }else if(StringUtils.isEmpty(newpassowrd)){
    	   session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, ERROR_EMPTY_FIELD); 
    	   
       }else if(StringUtils.isEmpty(confirmpassword)){
    	   session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, ERROR_EMPTY_FIELD); 
    	   
       }else if(StringUtils.isEmpty(schooluuid)){
    	   session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, ERROR_EMPTY_FIELD); 
    	   
       }else if(!StringUtils.equals(confirmpassword, newpassowrd)){
    	   session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, PASS_MISMATCH); 
    	   
       }else if(StringUtils.equals(confirmpassword, staffUsername)){
    	   session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, ERROR_USERNAME_PASSWORD_MATCH); 
    	   
       }else{
    	   String oldpass = "";
    	   Staff staff =staffDAO.getStaff(schooluuid, stffID);
    	   oldpass = staff.getPassword();
    	   
    	   if(StringUtils.equals(oldpass, SecurityUtil.getMD5Hash(oldpassword))){

    		   staff.setPassword(confirmpassword); 

    		   if(staffDAO.updateStaff(staff)){ 
    			   session.setAttribute(SessionConstants.STAFF_UPDATE_SUCCESS, STAFF_UPDATE_SUCSESS); 
    		   }else{
    			   session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, STAFF_UPDATE_ERROR); 
    			   
    		   }

    	   }else{
    		   session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, INCORRECT_OLD_PASS);  
    	   }

       }
       
       response.sendRedirect("profile.jsp");  
	   return;
       
       
   }
   
   

   @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          doPost(request, response);
      }
   private static final long serialVersionUID = 6898405290734290326L;
}
