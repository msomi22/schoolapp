/**
 * 
 */
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

/**
 * @author peter
 *
 */
public class PromotePerStudent extends HttpServlet{
	
	
	
	final String ERROR = "We are Sorry, something went wrong , try again later";
	final String SUCCESS_PROMOTE = "Students promoted successfully";
	final String ERROR_PROMOTE = "Students promotion failed";
	final String EMPTY_FIELDS = "Empty fields not allowed";
	
	private static StudentDAO studentDAO;
	Student student;


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
       String newclassuuid = StringUtils.trimToEmpty(request.getParameter("newclassuuid"));
   

       if(StringUtils.isBlank(studentAdmNo)){
    	   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, EMPTY_FIELDS); 
    	   
       }else if(StringUtils.isBlank(scholluuid)){
    	   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, ERROR); 
    	   
       }else if(StringUtils.isBlank(newclassuuid)){ 
    	   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, EMPTY_FIELDS); 
    	   
       }else if(studentDAO.getStudentObjByadmNo(scholluuid, studentAdmNo) == null){
    	   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, ERROR); 
    	   
       }else{
    	   
    	   student = studentDAO.getStudentObjByadmNo(scholluuid, studentAdmNo);
           student.setClassRoomUuid(newclassuuid); 
           studentDAO.updateStudents(student);
           session.setAttribute(SessionConstants.PROMOTE_CALSS_SUCCESS, SUCCESS_PROMOTE); 
    	   
    	   
       }
       
      
       response.sendRedirect("promotePerstudent.jsp");  
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
		private static final long serialVersionUID = 3431819371095226160L;


}
