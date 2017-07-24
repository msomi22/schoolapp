/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.student.clearance;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author peter
 *
 */
public class PrintClearanceForm extends HttpServlet{

	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);




		/* 
		   Student student = new Student();
		   if(studentDAO.getStudentByuuid(schooluuid, studentuuid)!=null){
 	         student = studentDAO.getStudentByuuid(schooluuid, studentuuid);
              student.setStatusUuid(STATUS_INACTIVE); 
              studentDAO.updateStudents(student);
		   }*/


	}





	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 948251601886041625L;





}
