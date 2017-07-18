/**
 * 
 */
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

/**  
 * @author peter
 *
 */
public class DeleteStudentSubject extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2530652082852570760L;
	final String ERROR_DELETE_FAILED = "An error occured while deleting the subject.";
	final String SUCCESS_DEKETE_OK = "Subject deleted successfuly."; 
	
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
	  
       String studentid = StringUtils.trimToEmpty(request.getParameter("studentid"));
       String subjectId = StringUtils.trimToEmpty(request.getParameter("subjectid"));
      
     
  	  	    	  
 	   StudentSubject sb = new StudentSubject(); 
 	   sb.setStudentUuid(studentid);
 	   sb.setSubjectUuid(subjectId); 
 	  
 	    		   
 	   if(studentSubjectDAO.deletestudentSub(sb)){ 
 	    	session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, SUCCESS_DEKETE_OK); 
 	   }else{
 	        session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_DELETE_FAILED); 
 	    	  			
 	    	        	
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
