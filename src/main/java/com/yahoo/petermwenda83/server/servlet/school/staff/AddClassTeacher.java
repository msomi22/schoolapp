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
public class AddClassTeacher extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String ERROR_ADD_FAILED = "An error occured while assigning the class.";
	final String ERROR_EMPTY_STAFF = "Seem like you didn't search for any teacher.";
	final String ERROR_TECAHER_HAS_CLASS = "The teacher is already assigned a class.";
	final String ERROR_CLASS_HAS_TEACHER = "The class is already assigned a teacher.";
	final String ERROR_EMPTY_CLASS = "Please select a class .";
	final String SUCCESS_ADD_OK = "Subject-Class assiged successfuly."; 
	
	private static ClassTeacherDAO classTeacherDAO;

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
       String teacherUuid = StringUtils.trimToEmpty(request.getParameter("staffid"));
       String classId = StringUtils.trimToEmpty(request.getParameter("classId"));
    
       if(StringUtils.isBlank(teacherUuid) || teacherUuid ==null || StringUtils.equals(teacherUuid, "null")){
		     session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_EMPTY_STAFF); 
		   
	   }else if(StringUtils.isBlank(classId)){ 
		   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_EMPTY_CLASS); 
		  
	   }else if(classTeacherDAO.getClassTeacherByteacherId(teacherUuid) !=null){ 
		   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_TECAHER_HAS_CLASS); 
		  
	   }else if(classTeacherDAO.getClassTeacherByclassId(classId) !=null){ 
		   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_CLASS_HAS_TEACHER); 
		  
	   }else{
		   
		   ClassTeacher Teacher = new ClassTeacher();
		   Teacher.setClassRoomUuid(classId);
		   Teacher.setTeacherUuid(teacherUuid);
		   if(classTeacherDAO.putClassTeacher(Teacher)){
			   session.setAttribute(SessionConstants.STAFF_FIND_SUCCESS, SUCCESS_ADD_OK); 
		   }else{
			   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_ADD_FAILED); 
		   } 
		   
	   }
      
       response.sendRedirect("addclassTecaher.jsp");  
	   return;
       
   }

@Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doPost(request, response);
   }


}
