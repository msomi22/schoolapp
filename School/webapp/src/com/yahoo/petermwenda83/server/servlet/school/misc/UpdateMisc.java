/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.school.misc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.schoolaccount.Miscellanous;
import com.yahoo.petermwenda83.persistence.schoolaccount.MiscellanousDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/** 
 * @author peter
 *
 */
public class UpdateMisc extends HttpServlet{
	
	final String UPDATE_SUCSESS = "Variable updated successfully";
	final String UPDATE_ERROR = " An error occured while updating the Variable";
	
	private static MiscellanousDAO miscellanousDAO;

	/**
	
	/**
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       miscellanousDAO = MiscellanousDAO.getInstance();
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String schoolUuid = StringUtils.trimToEmpty(request.getParameter("schoolUuid"));
       String miscUuid = StringUtils.trimToEmpty(request.getParameter("miscUuid"));
       String value = StringUtils.trimToEmpty(request.getParameter("value"));
      
      
      if(StringUtils.isEmpty(schoolUuid)){
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, UPDATE_ERROR); 
    	   
       }else if(StringUtils.isEmpty(miscUuid)){
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, UPDATE_ERROR); 
    	   
       }else if(StringUtils.isEmpty(value)){
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, UPDATE_ERROR); 
    	   
       }else{
    	   
    	   Miscellanous misc = miscellanousDAO.getMisc(schoolUuid);
    	   misc.setValue(value); 
    	   misc.setUuid(miscUuid); 
    	   if(miscellanousDAO.updateMiscellanous(misc)){
    		   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS, UPDATE_SUCSESS);
    		   
    	   }else{
    		   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, UPDATE_ERROR);
    	   }
    	   
    	   
       }
       
       response.sendRedirect("examConfig.jsp");  
	   return;
       
       
   }
   
   

   @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          doPost(request, response);
      }
   private static final long serialVersionUID = 6898405290734290326L;
}
