/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.promote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

/**  
 * @author peter
 *
 */
public class PromoteByClass extends HttpServlet{
	
	
	
	final String ERROR = "We are Sorry, something went wrong , try again later";
	final String SUCCESS_PROMOTE = "Students promoted successfully";
	final String ERROR_PROMOTE = "Students promotion failed";
	final String EMPTY_FIELDS = "Empty fields not allowed";
	
	
	private static StudentDAO studentDAO;
	Student student;
	List<Student> studentlist = null;


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
	   
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String newclassuuid = StringUtils.trimToEmpty(request.getParameter("newclassuuid"));
       String currentclassuuid = StringUtils.trimToEmpty(request.getParameter("currentclassuuid"));
   
       if(StringUtils.isBlank(newclassuuid)){
    	   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, EMPTY_FIELDS); 
       }else if(StringUtils.isBlank(currentclassuuid)){
    	   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, EMPTY_FIELDS); 
       }
       studentlist = new ArrayList<>();
       studentlist = studentDAO.getAllStudents(schooluuid, currentclassuuid);
       for(Student stu : studentlist){
    	   student = studentDAO.getStudentByuuid(schooluuid, stu.getUuid());
    	   student.setClassRoomUuid(newclassuuid); 
    	   studentDAO.updateStudents(student);
         }
       
       
       //session response
       session.setAttribute(SessionConstants.PROMOTE_CALSS_SUCCESS, SUCCESS_PROMOTE); 
       response.sendRedirect("promote.jsp");  
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
	private static final long serialVersionUID = 1L;

}
