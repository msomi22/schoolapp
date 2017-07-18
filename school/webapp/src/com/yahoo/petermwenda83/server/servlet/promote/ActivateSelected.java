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
import com.yahoo.petermwenda83.server.servlet.util.LogUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class ActivateSelected extends HttpServlet{
	
	final String PROMOTE_ERROR = "We are sorry, something went wrong , try again later.";
	final String PROMOTE_ERROR_NO_STUDENT = "Please check some students.";
	final String PROMOTE_SUCCESS = "Student(s) actiated successfully."; 
	
	final String ACTIVE = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";

	private static StudentDAO studentDAO;
	List<Student> studentlist = new ArrayList<>();
	String[] studentCheck = {};  

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

		studentCheck = request.getParameterValues("studentCheck[]");
		String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));

		if(StringUtils.isBlank(schooluuid)){
			session.setAttribute(SessionConstants.STATUS_CHANGE_ERROR, PROMOTE_ERROR); 
		}else  if(studentCheck == null || studentCheck.length <1){ 
			session.setAttribute(SessionConstants.STATUS_CHANGE_ERROR, PROMOTE_ERROR_NO_STUDENT); 
		}else{
			LogUtil logUtil = new LogUtil();
			int count = 1;
			for(String str : studentCheck){
				if(studentDAO.getStudentByuuid(schooluuid, str) !=null){
				Student student = studentDAO.getStudentByuuid(schooluuid, str); 
				student.setUuid(str); 
				student.setStatusUuid(ACTIVE); 
				if(studentDAO.updateStudents(student)){
					logUtil.writeLog("activate selected students " + student); 
				}
			   }
				count++;
			}
			session.setAttribute(SessionConstants.STATUS_CHANGE_SUCCESS, count +" "+  PROMOTE_SUCCESS);  

		}
		response.sendRedirect("activateSelected.jsp");  
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
	private static final long serialVersionUID = 9061595074365255473L;
}
