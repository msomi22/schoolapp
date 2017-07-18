package com.yahoo.petermwenda83.server.servlet.school.student;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.student.StudentSubject;
import com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class AssignSubject extends HttpServlet{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2002599857346900299L;
	final String ERROR_ASSIGN_FAILED = "An error occured while assigning the subject.";
	final String ERROR_EMPTY_STUDENT = "Seem like you didn't search for any student.";
	final String ERROR_EMPTY_SUBJECT = "Please select atleast one subject.";
	final String ERROR_SUBJECT_EXIST = "Subject already assigned.";
	final String SUCCESS_ASSIGN_OK = "Subject assiged successfuly."; 
	
	private static StudentSubjectDAO studentSubjectDAO;

	/**   
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentSubjectDAO = StudentSubjectDAO.getInstance();
   }

   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

	   HttpSession session = request.getSession(true);
	   
       String [] subjectId = null;
       String studentid = StringUtils.trimToEmpty(request.getParameter("studentid"));
       String systemuser = StringUtils.trimToEmpty(request.getParameter("systemuser"));
       
       if(request.getParameterValues("subjectId") ==null){
 	    	 session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_EMPTY_SUBJECT); 
 	    	 
 	    }else {
 	    	   subjectId = request.getParameterValues("subjectId");
 	          if(StringUtils.isBlank(studentid) || studentid ==null || StringUtils.equals(studentid, "null")){ 
  	  		     session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_EMPTY_STUDENT); 
  	  			   
  	  	      }else{
  	  	    	  
 	    	   for(int i = 0; i<subjectId.length;i++){
 	    		   StudentSubject sb = new StudentSubject(); 
 	    	       sb.setStudentUuid(studentid);
 	    		   sb.setSubjectUuid(subjectId[i]); 
 	    		   sb.setSysUser(systemuser);
 	    		   if(studentSubjectDAO.getsubject(studentid,subjectId[i]) !=null){ 
 	    		     session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_SUBJECT_EXIST);    
 	    	        }else{
 	    	  			   if(studentSubjectDAO.putstudentSub(sb)){ 
 	    	  				   session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, SUCCESS_ASSIGN_OK); 
 	    	  			   }else{
 	    	  				   session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_ASSIGN_FAILED); 
 	    	  			
 	    	  			   }
 	    	  		   }//end for loop
 	    	  		  
 	    	  		 
 	    	  	      }
 	    	        	
 	    	        	
 	    	        	
 	    	        }
 	    	   }
 	 	      
       response.sendRedirect("studentSubjects.jsp");  
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

}
