package com.yahoo.petermwenda83.server.servlet.student;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;


public class StudentAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private static StudentDAO studentDAO;
	
	private static SysConfigDAO sysConfigDAO;
       
	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
       
		studentDAO = StudentDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
      
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);

		OutputStream out = response.getOutputStream();
		response.setContentType("application/json;charset=UTF-8");
		
		
		// String currentStream;
		
		
		String firstname=StringUtils.trimToEmpty(request.getParameter("fname"));
		String middlename=StringUtils.trimToEmpty(request.getParameter("mname"));	
		String lastname=StringUtils.trimToEmpty(request.getParameter("lname"));
		String gender=StringUtils.trimToEmpty(request.getParameter("gender"));
		String dob=StringUtils.trimToEmpty(request.getParameter("dob"));
		String bcertNo=StringUtils.trimToEmpty(request.getParameter("bcertno"));
		String county=StringUtils.trimToEmpty(request.getParameter("county"));
		String regTerm=StringUtils.trimToEmpty(request.getParameter("term"));
		//String finalYear=StringUtils.trimToEmpty(request.getParameter(""));
		//String finalTerm=StringUtils.trimToEmpty(request.getParameter(""));
		String regStream = StringUtils.trimToEmpty(request.getParameter("stream"));
		String currentStream=StringUtils.trimToEmpty(request.getParameter("stream"));
		String isActive = StringUtils.trimToEmpty(request.getParameter("active"));
		String isAlumni = StringUtils.trimToEmpty(request.getParameter("alumni"));
		String isBoarding = StringUtils.trimToEmpty(request.getParameter("boarding"));
		String regNo = StringUtils.trimToEmpty(request.getParameter("regno"));
		String action = StringUtils.trimToEmpty(request.getParameter("action"));
		//String passport=StringUtils.trimToEmpty(request.getParameter(""));
		//String lastUpdated;
		//Timestamp admissionDate;
		//schoolname

		

		String accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID); 
		

		Gson gson = new GsonBuilder().disableHtmlEscaping()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting().serializeNulls().create();
		
		
		
		if(StringUtils.equalsIgnoreCase(action, "add")){
			
			JsonObject jsonObject = new JsonObject();
			
			Student student= new Student();
			student.setUuid(student.getUuid());
			student.setAccountId(accountId);
			student.setCurrentStream(currentStream);
			student.setFirstname(firstname);
			student.setLastname(lastname);
			student.setMiddlename(middlename);
			student.setGender(gender);
			student.setDob(dob);
			student.setBcertNo(bcertNo);
			student.setCounty(county);
			student.setRegTerm(regTerm);
			student.setRegStream(regStream);
			student.setIsActive(isActive);
			student.setIsBoarding(isBoarding);
			student.setRegNo(regNo);
			student.setIsAlumni(isAlumni);
			
			
			studentDAO= StudentDAO.getInstance();
			
			if (studentDAO.putStudent(student)) {

				jsonObject.addProperty("responseMessage", "OK");

			} else {

				jsonObject.addProperty("responseMessage", "Unexpected error has occured, contact admin please ."+ student.getUuid() +","+student.getAccountId()
				+","+student.getCurrentStream()+","+student.getRegStream());

			}
			
			

			out.write(gson.toJson(jsonObject).getBytes());
			out.flush();
			out.close();

		}


	}

}
