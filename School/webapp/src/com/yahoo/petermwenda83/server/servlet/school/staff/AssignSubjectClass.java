package com.yahoo.petermwenda83.server.servlet.school.staff;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.staff.TeacherSubClass;
import com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class AssignSubjectClass extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String ERROR_ASSIGN_FAILED = "An error occured while assigning the subject.";
	final String ERROR_SUB_TAKEN = "Sorry, this subject has already been assigned another teacher.";
	final String ERROR_EMPTY_STAFF = "Seem like you didn't search for any teacher!";
	//final String ERROR_SUBJECT_EXIST = "Subject/Class already assigned.";
	final String ERROR_EMPTY_SUBECT = "Please select a subject .";
	final String ERROR_EMPTY_CLASS = "Please select atleast one class .";
	final String SUCCESS_ASSIGN_OK = "Subject-Class assiged successfuly."; 
	
	private static TeacherSubClassDAO teacherSubClassDAO;
	
	

	/**    
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       teacherSubClassDAO = TeacherSubClassDAO.getInstance();
       
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

	   HttpSession session = request.getSession(true);
	   
	   String subjectId = StringUtils.trimToEmpty(request.getParameter("subjectId"));
       String [] classId = null; 
       String staffid = StringUtils.trimToEmpty(request.getParameter("staffid"));
       String systemuser = StringUtils.trimToEmpty(request.getParameter("systemuser"));
       
        if(StringUtils.isBlank(subjectId) || subjectId ==null || StringUtils.equals(subjectId, "null")){
		     session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_EMPTY_SUBECT); 
			   
	      }
        else if( request.getParameterValues("classId") ==null){
    	   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_EMPTY_CLASS);
	    	 
	    }else{
	 
	    	classId = request.getParameterValues("classId");
	    	
	    	if(StringUtils.isBlank(staffid) || staffid ==null || StringUtils.equals(staffid, "null")){
			     session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_EMPTY_STAFF); 
				   
		      }else{
		    	  
				   for(int i = 0; i<classId.length;i++){
					   TeacherSubClass subClass = new TeacherSubClass();
					       subClass.setClassRoomUuid(classId[i]);
						   subClass.setSubjectUuid(subjectId);
						   subClass.setTeacherUuid(staffid);
						   subClass.setSysUser(systemuser);
				   if(teacherSubClassDAO.getSubject(subjectId,classId[i]) !=null){ 
		    		     session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_SUB_TAKEN);    
		    	        }else{
		    	        	if(teacherSubClassDAO.putSubjectClass(subClass)){
		    		        	   session.setAttribute(SessionConstants.STAFF_FIND_SUCCESS, SUCCESS_ASSIGN_OK); 
		    				   }else{
		    					   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_ASSIGN_FAILED); 
		    				   }
		    	        }
				  
			   } //end for loop
		
		}
	  } 
		       response.sendRedirect("teacherSubjects.jsp");  
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
