package com.yahoo.petermwenda83.server.servlet.promote;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class ChangeStatusPerStudent extends HttpServlet{

	
	final String ERROR = "We are Sorry, something went wrong , try again later";
	final String SUCCESS_STATUS_CHANGE = "Students status changed successfully";
	final String ERROR_STATUS_CHANGE = "Students status change failed";
	final String EMPTY_FIELDS = "Empty fields not allowed";
	
	final String STATUS_INACTIVE = "6C03705B-E05E-420B-B5B8-C7EE36643E60";
	
	private static StudentDAO studentDAO;
	


	/**    
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentDAO = StudentDAO.getInstance();
      
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

	   HttpSession session = request.getSession(true);
	   
       String studentAdmNo = StringUtils.trimToEmpty(request.getParameter("studentAdmNo"));
       String scholluuid = StringUtils.trimToEmpty(request.getParameter("scholluuid"));
       

       if(StringUtils.isBlank(studentAdmNo)){
    	   session.setAttribute(SessionConstants.STATUS_CHANGE_ERROR, EMPTY_FIELDS); 
       }else if(StringUtils.isBlank(scholluuid)){
    	   session.setAttribute(SessionConstants.STATUS_CHANGE_ERROR, EMPTY_FIELDS); 
       }else if(studentDAO.getStudentObjByadmNo(scholluuid, studentAdmNo) == null){
    	   session.setAttribute(SessionConstants.STATUS_CHANGE_ERROR, EMPTY_FIELDS); 
       }else{
    	   Student student = new Student();
    	   student = studentDAO.getStudentObjByadmNo(scholluuid, studentAdmNo);
           student.setStatusUuid(STATUS_INACTIVE); 
           if(studentDAO.updateStudents(student)){
        	   session.setAttribute(SessionConstants.STATUS_CHANGE_SUCCESS, SUCCESS_STATUS_CHANGE);  
           }else{
        	   session.setAttribute(SessionConstants.STATUS_CHANGE_ERROR, ERROR_STATUS_CHANGE); 
           }
          
    	   
       }
       
       
       response.sendRedirect("statusPerstudent.jsp");  
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
	private static final long serialVersionUID = -3030729717319181918L;

}
