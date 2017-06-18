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

import com.yahoo.petermwenda83.bean.staff.TeacherSubClass;
import com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**    
 * @author peter
 *
 */
public class DeleteTeacherSubject extends HttpServlet{
	
	
	
	final String ERROR_EMPTY_FIELDS = "Something went wrong while deleting the subject, try again later.";
	final String DELETER_FAILED = "Something went wrong while deleting the subject, try again later.";
	final String DELETE_SUCCESS = "The subject was deleted successfully.";
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
       String teacherUuid = StringUtils.trimToEmpty(request.getParameter("staffid"));
       String classid = StringUtils.trimToEmpty(request.getParameter("classid"));
       
       if(StringUtils.isBlank(subjectId)){
    	   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_EMPTY_FIELDS); 

       }else if(StringUtils.isBlank(teacherUuid)){
    	   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_EMPTY_FIELDS); 
    	   
       }else if(StringUtils.isBlank(classid)){
    	   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_EMPTY_FIELDS); 
    	   
       }else{

    	   TeacherSubClass teacherSubClass = new TeacherSubClass();
    	   teacherSubClass.setSubjectUuid(subjectId);
    	   teacherSubClass.setTeacherUuid(teacherUuid); 
    	   teacherSubClass.setClassRoomUuid(classid); 
    	   if( teacherSubClassDAO.deleteSubjectClass(teacherSubClass)){
    		   session.setAttribute(SessionConstants.STAFF_FIND_SUCCESS, DELETE_SUCCESS); 
    	   }else{
    		   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, DELETER_FAILED); 
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
	private static final long serialVersionUID = 337887207384030934L;
}
