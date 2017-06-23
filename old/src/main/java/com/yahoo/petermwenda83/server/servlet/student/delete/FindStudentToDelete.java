/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.student.delete;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
public class FindStudentToDelete extends HttpServlet{
	
	
	final String ERROR_STUDENT_NOT_FOUND = " The admission number you provided was not found in the system.";
	final String STUDENT_FIND_SUCCESS = " A student was found ,scroll to the bottom to proceed.";
    final String ERROR_NO_ADMNO = " You didn't provide any admission number?.";
    
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
       String admissionNumber = StringUtils.trimToEmpty(request.getParameter("AdmNo"));
       String schoolUuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       
       Map<String, Student> paramHash = new HashMap<>();  
       
       if(StringUtils.isBlank(admissionNumber)){
		     session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_NO_ADMNO); 
		   
	   }else if(studentDAO.getStudentObjByadmNo(schoolUuid,admissionNumber)==null){ 
		   session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_STUDENT_NOT_FOUND); 
		  
	   }else{
		   
		   Student student = studentDAO.getStudentObjByadmNo(schoolUuid, admissionNumber);
		   paramHash.put("studentTOdelete", student); 
		   
		   
	      session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, STUDENT_FIND_SUCCESS);     
	   }

       session.setAttribute(SessionConstants.STUENT_DELETE_PARAM, paramHash); 
       response.sendRedirect("sysConfig.jsp");  
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
private static final long serialVersionUID = 9053405898613362255L;
}
