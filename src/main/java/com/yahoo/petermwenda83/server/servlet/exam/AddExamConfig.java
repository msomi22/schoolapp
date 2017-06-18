package com.yahoo.petermwenda83.server.servlet.exam;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class AddExamConfig extends HttpServlet{
	
	final String ERROR_EMPTY_TERM = "Please select term"; 
	final String ERROR_EMPTY_YEAR = "Please select year"; 
	final String ERROR_EMPTY_EXAM = "Please select exam"; 
	final String ERROR_EMPTY_EXAM_MODE = "Please select exam mode"; 
	final String ERROR_EXAM_CONGIGURED = "Term configuratioins already exist"; 
	final String ERROR_ECAMC_ADD_ERROR = "An error occured while adding Term configurations"; 
	final String ERROR_ECAMC_ADD_ADD_SUCCESS = "Term configurations added successfully"; 
	
	private static ExamConfigDAO examConfigDAO;

	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       examConfigDAO = ExamConfigDAO.getInstance();
      
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String schoolAccountUuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String term = StringUtils.trimToEmpty(request.getParameter("term"));
       String Year = StringUtils.trimToEmpty(request.getParameter("Year"));
       String exam = StringUtils.trimToEmpty(request.getParameter("exam"));
       String examMode = StringUtils.trimToEmpty(request.getParameter("examMode"));
       
       
       if(StringUtils.isEmpty(term)){
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_TERM); 
    	   
       }else if(StringUtils.isEmpty(Year)){
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_YEAR); 
    	   
       }else if(StringUtils.isEmpty(exam)){
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_EXAM); 
    	   
      }else if(StringUtils.isEmpty(examMode)){
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_EXAM_MODE); 
    	   
       }else if(examConfigDAO.getExamConfig(schoolAccountUuid) !=null){
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EXAM_CONGIGURED); 
    	   
       }else{
    	   ExamConfig examConfig = new ExamConfig();
    	   examConfig.setSchoolAccountUuid(schoolAccountUuid);
    	   examConfig.setTerm(term);
    	   examConfig.setYear(Year);
    	   examConfig.setExam(exam);
    	   examConfig.setExamMode(examMode);
    	   examConfig.setSendSMS("OFF");
    	   if(examConfigDAO.putExamConfig(examConfig)){ 
    		   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS, ERROR_ECAMC_ADD_ADD_SUCCESS); 
    	   }else{
    		   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_ECAMC_ADD_ERROR);
    	   }
    	   
     }
       
      
       response.sendRedirect("addExamconfg.jsp");  
	   return;
       
       
   }


   @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          doPost(request, response);
      }
   

	/**
	 * 
	 */
	private static final long serialVersionUID = -2495275430951644100L;

}
