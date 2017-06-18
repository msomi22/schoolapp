package com.yahoo.petermwenda83.server.servlet.student;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class UpdateStudentBasic extends HttpServlet{
	/**
	 *  
	 */
	private static final long serialVersionUID = 2171267736101525967L;
	
	
	final String ERROR_EMPTY_ADM_NO = "Admission number can't be empty.";
	
	final String NAME_ERROR = "Name format error/incorrent lenght.";
	
	final String ERROR_EMPTY_FIRST_NAME = "Firstname can't be empty.";
	final String ERROR_EMPTY_MIDDLE_NAME = "Middlename can't be empty.";
	final String ERROR_EMPTY_LASTNAME = "Lastname can't be empty.";
	
	final String ERROR_EMPTY_GENDER = "Student gender can't be empty.";
	final String ERROR_EMPTY_DAY = "Student day of birth can't be empty.";
	final String ERROR_EMPTY_MONTH = "Student month of birth can't be empty.";
	final String ERROR_EMPTY_YEAR = "Student year of birth can't be empty.";
	
	final String ERROR_EMPTY_BCERT_NO = "Birth certificate number can't be empty.";
	final String ERROR_EMPTY_COUNTY = "County can't be empty.";
	final String ERROR_EMPTY_PRIMARY = "Primary school name can't be empty.";
	final String ERROR_EMPTY_INDEX_NO = "KCPE index number can't be empty.";
	final String ERROR_EMPTY_KCPE_YAER = "KCPE year can't be empty.";
	final String ERROR_EMPTY_KCPE_MARK = "KCPE mark  can't be empty.";
	final String UNEXPECTED_ERROR = "Unexpected error occured.";
	
	final String ERROR_ADMNO_EXIST = "Admission number already exist in the system.";
	final String STUDENT_UPDATE_ERROR = "An error occured while updating student.";
	final String STUDENT_UPDATE_SUCCESS = "Student updated successfully";
	final String ERROR_FINAL_YEAR_INVALID = "Final Year must be numeric/is invalid.";
	final String ERROR_TERM_NOT_ALLOWED = "Check the term and try again.";
	final String STUDENT_TYPE_BLANK = "Please select student type";
	
	final String ERROR_MARKS_INVALID = "The K.C.P.E marks are invalid, a valid mark is numeric  number greater than 100 and less tham 500";
	
	final String STATUS_ACTIVE = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
	
	private static StudentDAO studentDAO;
	private static PrimaryDAO primaryDAO;
	


	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentDAO = StudentDAO.getInstance();
       primaryDAO = PrimaryDAO.getInstance();
       
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
     
       String admnumber = StringUtils.trimToEmpty(request.getParameter("admNo"));
       String firstname = StringUtils.trimToEmpty(request.getParameter("firstname"));
       String middlename = StringUtils.trimToEmpty(request.getParameter("lastname"));
       String lastname = StringUtils.trimToEmpty(request.getParameter("surname"));
       String gender = StringUtils.trimToEmpty(request.getParameter("gender"));
       String dob = StringUtils.trimToEmpty(request.getParameter("dob"));
       String BcertNo = StringUtils.trimToEmpty(request.getParameter("BcertNo"));
       String County = StringUtils.trimToEmpty(request.getParameter("county"));
       String primary = StringUtils.trimToEmpty(request.getParameter("primary"));
       String indexno = StringUtils.trimToEmpty(request.getParameter("kcpeindex"));
       String kcpeaddYear = StringUtils.trimToEmpty(request.getParameter("kcpeyear"));
       String kcpemark = StringUtils.trimToEmpty(request.getParameter("kcpemark"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schoolUuid"));
       String studentUuid = StringUtils.trimToEmpty(request.getParameter("studentUuid"));
       String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
       String finalYear = StringUtils.trimToEmpty(request.getParameter("finalYear"));//FinalTerm
       String finalTerm = StringUtils.trimToEmpty(request.getParameter("finalTerm"));//
       String StydentType = StringUtils.trimToEmpty(request.getParameter("StydentType"));
       
       Calendar calendar = Calendar.getInstance();
	   final int YEAR = calendar.get(Calendar.YEAR);
      
       
       if(StringUtils.isBlank(admnumber)){ 
		   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_ADM_NO); 
		   
	   }else if(StringUtils.isBlank(firstname)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_FIRST_NAME); 
		     
	   }else if(!lengthValid(firstname)){
	 	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, NAME_ERROR); 
		   
	   }else if(StringUtils.isBlank(middlename)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_MIDDLE_NAME); 
			   
	   }else if(!lengthValid(middlename)){
	 	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, NAME_ERROR); 
		   
	   }/*else if(StringUtils.isBlank(lastname)){ 
		   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_SURNAME); 
		   
	   }else if(!lengthValid(lastname)){
	 	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, NAME_ERROR); 
		   
	   }*/else if(StringUtils.isBlank(gender)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_GENDER); 
		     
	   }else if(StringUtils.isBlank(dob)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_DAY); 
			   
	   }else if(StringUtils.isBlank(dob)){ 
		   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_MONTH); 
		   
	   }else if(StringUtils.isBlank(dob)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_YEAR); 
		     
	   }/*else if(StringUtils.isBlank(BcertNo)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_BCERT_NO); 
		     
	   }else if(StringUtils.isBlank(County)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_COUNTY); 
		     
	   }else if(StringUtils.isBlank(primary)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_PRIMARY); 
		     
	   }else if(StringUtils.isBlank(indexno)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_INDEX_NO); 
		     
	   }else if(StringUtils.isBlank(kcpeaddYear)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_KCPE_YAER); 
		     
	   }*/else if(StringUtils.isBlank(kcpemark)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_KCPE_MARK); 
		     
	   }else if(!isNumeric(kcpemark)){
	 	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_MARKS_INVALID); 
		   
	   }else if(!lengthValid(kcpemark)){
		 	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_MARKS_INVALID); 
			   
	   }else if(!kcpeValid(kcpemark)){
	 	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_MARKS_INVALID); 
		   
      }else if(StringUtils.isBlank(schooluuid)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, UNEXPECTED_ERROR); 
		     
	   }else if(StringUtils.isBlank(staffUsername)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, UNEXPECTED_ERROR); 
		     
	   }/*else if(StringUtils.isBlank(StydentType)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, STUDENT_TYPE_BLANK); 
		     
	   }*/else if(!isNumeric(finalYear) || Integer.parseInt(finalYear ) > YEAR+3){
	 	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_FINAL_YEAR_INVALID + " It mus be less than " +(YEAR+3)); 
		   
	   }else if(StringUtils.isEmpty(finalTerm)){
    	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_TERM_NOT_ALLOWED); 
       }
       else if(!isNumeric(finalTerm)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_TERM_NOT_ALLOWED); 
			   
	   }else if(Integer.parseInt(finalTerm) >3 || Integer.parseInt(finalTerm) ==0){
    	  session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_TERM_NOT_ALLOWED); 
    	   
       }else{
		   
		   Student student = studentDAO.getStudentByuuid(schooluuid, studentUuid);
		   student.setSchoolAccountUuid(schooluuid); 
		   student.setStatusUuid(STATUS_ACTIVE); 
		   student.setAdmno(admnumber);
		   student.setFirstname(StringUtils.capitalize(firstname).toLowerCase());
		   student.setLastname(StringUtils.capitalize(middlename).toLowerCase());
		   student.setSurname(StringUtils.capitalize(lastname).toLowerCase());
		   student.setGender(gender);
		   student.setdOB(dob); 
		   student.setBcertno(BcertNo);
		   student.setCounty(StringUtils.capitalize(County).toLowerCase()); 
		   student.setSysUser(staffUsername); 
		   student.setFinalYear(Integer.parseInt(finalYear));
		   student.setFinalTerm(Integer.parseInt(finalTerm)); 
		   
		   if(StringUtils.isBlank(StydentType)){
			   StydentType = student.getStudentType();
		   }
		  
		   student.setStudentType(StydentType); 
		  
		   StudentPrimary sprimary;
		   if(primaryDAO.getPrimary(student.getUuid()) == null){
			   sprimary = new StudentPrimary();
			   sprimary.setStudentUuid(student.getUuid());
			   sprimary.setSchoolname(StringUtils.capitalize(primary));
			   sprimary.setIndex(indexno);
			   sprimary.setKcpemark(kcpemark);
			   sprimary.setKcpeyear(kcpeaddYear); 
			   primaryDAO.putPrimary(sprimary);
		   }else{
			   sprimary = primaryDAO.getPrimary(student.getUuid());
			   sprimary.setSchoolname(StringUtils.capitalize(primary));
			   sprimary.setIndex(indexno);
			   sprimary.setKcpemark(kcpemark);
			   sprimary.setKcpeyear(kcpeaddYear); 
			   primaryDAO.updatePrimary(sprimary);
		   }
		   
		   if(studentDAO.updateStudents(student)){  
			 
			   session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS, STUDENT_UPDATE_SUCCESS);  
		   }else{
			   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, STUDENT_UPDATE_ERROR);  
		   }
		  
		   
	   }
	  
       response.sendRedirect("studentIndex.jsp");  
	   return;
       
       
   }
   

   /**
    * @param str
    * @return
    */
   public static boolean isNumeric(String str) {  
	   try  
	   {  
		   double d = Double.parseDouble(str);  
	   }  
	   catch(NumberFormatException nfe)  
	   {  
		   return false;  
	   }  
	   return true;  
   }

   /**
    * @param mystring
    * @return
    */
   private static boolean lengthValid(String mystring) {
	   boolean isvalid = true;
	   int length = 0;
	   length = mystring.length();
	   if(length<3){
		   isvalid = false;
	   }
	   return isvalid;
   }
   
   /**
 * @param mystring
 * @return
 */
private static boolean kcpeValid(String mystring) {
	   boolean isvalid = true;
	   int mark = 0;
	   mark = Integer.parseInt(mystring);
	   if(mark>500){
		   isvalid = false;
	   }
	   return isvalid;
   }


   
   

/**
 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
@Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
      doPost(request, response);
  }
}
