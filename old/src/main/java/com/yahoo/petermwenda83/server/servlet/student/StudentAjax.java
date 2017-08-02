package com.yahoo.petermwenda83.server.servlet.student;

import java.io.File;
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
import org.apache.log4j.Logger;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;


public class StudentAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private static StudentDAO studentDAO;
	private static PrimaryDAO primaryDAO;
	private static ParentsDAO parentsDAO;
	private static final String DATA_DIRECTORY = "/home/"+System.getProperty("user.name")+"/school/uploads/";
	
	
	
	//private Logger logger;
	
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
		parentsDAO= ParentsDAO.getInstance();
		primaryDAO=PrimaryDAO.getInstance();
		
      
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		String passport=StringUtils.trimToEmpty(request.getParameter("profile_url"));
		//String lastUpdated;
		//Timestamp admissionDate;
		
		String accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID); 
		
		
		Student student= new Student();
		//student.setUuid(student.getUuid());
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
		student.setPassport(passport);
		
		
		//Primary details
		//String studentId;
		Boolean primaryState=Boolean.parseBoolean(request.getParameter("primaryschool"));
		String schoolName=StringUtils.trimToEmpty(request.getParameter("schoolname"));
		String index=StringUtils.trimToEmpty(request.getParameter("indexno"));
		String kcpeyear=StringUtils.trimToEmpty(request.getParameter("kcpeyear"));
		String kcpemark=StringUtils.trimToEmpty(request.getParameter("kcpemarks"));
		
		
		
		
		
		
		
		//Parent's details
		
		//String studentId;
		Boolean parentState=Boolean.parseBoolean(request.getParameter("parent"));
		String name=StringUtils.trimToEmpty(request.getParameter("pfname")) +" "+
				StringUtils.trimToEmpty(request.getParameter("plname"));
		String mobile=StringUtils.trimToEmpty(request.getParameter("phone"));
		String email=StringUtils.trimToEmpty(request.getParameter("email"));
		//String lastUpdated;
		
		
		
		


		Gson gson = new GsonBuilder().disableHtmlEscaping()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting().serializeNulls().create();
		
		
		
		if(StringUtils.equalsIgnoreCase(action, "add")){
			
			
			
			
			out.write(gson.toJson(saveStudent(student, accountId, parentState, name, mobile, email, primaryState, schoolName, index, kcpeyear, kcpemark)).getBytes());
			out.flush();
			out.close();

		}


	}
	
	
	/**
	 * 
	 * @param student
	 * @param accountId
	 * @param parentState
	 * @param name
	 * @param mobile
	 * @param email
	 * @param primaryState
	 * @param schoolName
	 * @param index
	 * @param kcpeyear
	 * @param kcpemark
	 * @return
	 */
	
	public JsonElement saveStudent(Student student, String accountId,Boolean parentState,String name, String mobile, String email,
			Boolean primaryState, String schoolName, String index, String kcpeyear, String kcpemark) {
		
		JsonObject jsonObject = new JsonObject();
		

		
		//check if the regno already exists 
		if(studentDAO.getStudentByregNo(accountId, student.getRegNo()) != null) {
			
			jsonObject.addProperty("responseMessage", "Exists");
			
			
		}else {
			
			
			student.setPassport(renameImage(student.getPassport(),student.getRegNo()));
		
		
		if (studentDAO.putStudent(student)) {
			
			
			
			
			
			//check for primary details then add them
			if(primaryState) {
				
				StudentPrimary studentPrimary= new StudentPrimary();
				studentPrimary.setAccountId(accountId);
				//studentPrimary.setUuid(studentPrimary.getUuid());
				studentPrimary.setStudentId(student.getUuid());
				studentPrimary.setSchoolName(schoolName);
				studentPrimary.setIndex(index);
				studentPrimary.setKcpemark(kcpemark);
				studentPrimary.setKcpeyear(kcpeyear);
				
				primaryDAO.putStudentPrimary(studentPrimary);
				
			/*	if(primaryDAO.putStudentPrimary(studentPrimary)) {
					jsonObject.addProperty("responseMessage", "OK");
				}*/
				
			}
			
			
			//check for parent's details then add them
			
			if(parentState) {
				StudentParent studentParent= new StudentParent();
				//studentParent.setUuid(studentParent.getUuid());
				studentParent.setAccountId(accountId);
				studentParent.setStudentId(student.getUuid());
				studentParent.setName(name);
				studentParent.setMobile(mobile);
				studentParent.setEmail(email);
				
				
				parentsDAO.putParent(studentParent);
				
				
				//log the submitted data 
				//logger.info("HidePts submitted " + student.getUuid()); 
				//logger.info("HideGds submitted" + studentParent.getStudentId()); 
				
				
				/*if(parentsDAO.putParent(studentParent)) {
					jsonObject.addProperty("responseMessage", "OK");
				}*/
			}
			
			
			
			jsonObject.addProperty("responseMessage", "OK");
			
			
			
			

			

		} else {

			jsonObject.addProperty("responseMessage", "Unexpected error has occured, contact admin please ."+ student.getUuid() +","+student.getAccountId()
			+","+student.getCurrentStream()+","+student.getRegStream());

		}
		}
		
		
		
		
		return jsonObject;
		
	}
	
	/**
	 * 
	 * @param initalName
	 * @param regNo
	 * @return
	 */
	private String renameImage(String initalName,String regNo) {
		 String renamed= initalName;
		
		 File passport = new File(DATA_DIRECTORY+initalName); 

		 if(passport.renameTo(new File(DATA_DIRECTORY+regNo+".png")))
			 renamed= regNo+".png";
		return renamed;
		
	}

}
