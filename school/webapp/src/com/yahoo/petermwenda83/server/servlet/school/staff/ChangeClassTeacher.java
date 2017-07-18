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

import com.yahoo.petermwenda83.bean.staff.ClassTeacher;
import com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class ChangeClassTeacher extends HttpServlet{
	

	final String ERROR_CLASS_HAS_TEACHER = "The new class is already assigned a teacher.";
	final String ERROR_CLASS_CHANGE_FAILED = "Class change failed";
	final String EMPTY_FIELDS = "Emplty fileds not allowed";
	final String SUCCESS_CLASS_CHANGED = "Class changed successfully";
	
	
	private static ClassTeacherDAO classTeacherDAO;
	ClassTeacher classTeacher;


	/**   
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       classTeacherDAO = ClassTeacherDAO.getInstance();
      
   }
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

	   HttpSession session = request.getSession(true);
	   
       String currentclassuuid = StringUtils.trimToEmpty(request.getParameter("currentclassuuid"));
       String currentclssteacher = StringUtils.trimToEmpty(request.getParameter("currentclssteacher"));
       String newclassuuid = StringUtils.trimToEmpty(request.getParameter("newclassuuid"));
       
      
       
       if(StringUtils.isBlank(currentclassuuid)){
    	   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, EMPTY_FIELDS); 
    	   
       }else if(StringUtils.isBlank(currentclssteacher)){
    	   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, EMPTY_FIELDS); 
    	   
       }else if(StringUtils.isBlank(newclassuuid)){
    	   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, EMPTY_FIELDS); 
    	   
       }else if(classTeacherDAO.getClassTeacherByclassId(newclassuuid) !=null){ 
		   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, ERROR_CLASS_HAS_TEACHER); 
		  
	   }else{
    	   
    	   
    	   //get teacher's current class
    	   ClassTeacher Teacher = classTeacherDAO.getClassTeacherByteacherId(currentclssteacher);
    	   Teacher.setTeacherUuid(currentclssteacher);
    	   Teacher.setClassRoomUuid(currentclassuuid); 
    	   if(classTeacherDAO.deleteClassTeacher(Teacher)){
    		   
    		   //assign the teacher new class
    		classTeacher = new ClassTeacher();
       	    classTeacher.setTeacherUuid(currentclssteacher);
       	    classTeacher.setClassRoomUuid(newclassuuid);
       	    
       	    if(classTeacherDAO.putClassTeacher(classTeacher)){
       	    	session.setAttribute(SessionConstants.PROMOTE_CALSS_SUCCESS, SUCCESS_CLASS_CHANGED);	
       	    }else{
       	        session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, ERROR_CLASS_CHANGE_FAILED); 
       	    }
       	   
       	     
    		   
    	   }else{
    		   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, ERROR_CLASS_CHANGE_FAILED); 
    	   }
       }
       
       
       response.sendRedirect("classTeachers.jsp");  
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
private static final long serialVersionUID = 4560404765423156515L;
}
