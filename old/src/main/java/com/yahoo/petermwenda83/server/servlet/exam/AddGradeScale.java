package com.yahoo.petermwenda83.server.servlet.exam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class AddGradeScale extends HttpServlet{
	
	
	final String ERROR_EMPTY_FIRLD = "Empty fields/incorrect data type /range."; 
	final String ERROR_SCALE_ADD_ERROR = "An error occured while updating grading scale"; 
	final String ERROR_SCALE_ADD_SUCCESS = "Grading scale updated successfully"; 
	
	private static GradingSystemDAO gradingSystemDAO;

	/** 
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       gradingSystemDAO = GradingSystemDAO.getInstance();
      
   }
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String schoolAccountUuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       
       Map<String, String> paramHash = new HashMap<>();    	
	  
	   
    	   
       if(true){/*
    	   GradingSystem gradingSystem = gradingSystemDAO.getGradingSystem(schoolAccountUuid);
    	   
    	   if(gradingSystemDAO.updateGradingSystem(gradingSystem)){ 
    		   session.setAttribute(SessionConstants.GRADE_ADD_SUCCESS, ERROR_SCALE_ADD_SUCCESS); 
    	   }else{
    		   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_SCALE_ADD_ERROR); 
    	   }
    	   
       */}
       
       
       session.setAttribute(SessionConstants.GRADE_PARAM, paramHash);
       response.sendRedirect("sysConfig.jsp");  
	   return;
       
   }
   
   
   /**
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	    
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
      
  

   @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          doPost(request, response);
      }
   
   /**
	 * 
	 */
	private static final long serialVersionUID = -2890202371149608804L;
}
