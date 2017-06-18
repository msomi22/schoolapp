/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.student;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**  
 * @author peter
 *
 */
public class UpdateParent extends HttpServlet{

	final String ERROR_EMPTY_FATHER_NAME = "Parent 1 name can't be empty.";
	final String ERROR_EMPTY_FATHER_PHONE = "Parent 1 phone can't be empty.";
	final String ERROR_EMPTY_OCCUPATION = "Parent 1 occupation can't be empty.";
	final String ERROR_EMPTY_FATHER_ID = "Parent 1 ID can't be empty.";
	final String ERROR_EMPTY_FATHER_EMAIL = "Parent 1 email can't be empty.";
	
	final String ERROR_EMPTY_MOTHER_NAME = "Parent 2 name can't be empty.";
	final String ERROR_EMPTY_MOTHER_PHONE = "Parent 2 phone can't be empty.";
	final String ERROR_EMPTY_MOTHER_OCCUPATION = "Parent 2 occupation can't be empty.";
	final String ERROR_EMPTY_MOTHER_ID = "Parent 2 ID can't be empty.";
	final String ERROR_EMPTY_MOTHER_EMAIL = "Parent 2 email can't be empty.";
	
	final String ERROR_PARENT_NOT_UPDATED = "Something went wrong while updating parent details.";
	final String ERROR_PHONE_INVALID = "Phone is invalid, the number must have 10 digits (e.g. 0718953974).";
	final String ERROR_PHONE_NUMERIC = "phone can only be numeric";
	
	
	final String SUCCESS_PARENT_UPDATED = "Parent's details updated successfully.";
	
	private static ParentsDAO parentsDAO;


	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       parentsDAO = ParentsDAO.getInstance();
   }
   
   
   
   /* (non-Javadoc)
 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String FatherName = StringUtils.trimToEmpty(request.getParameter("FatherName"));
       String FatherPhone = StringUtils.trimToEmpty(request.getParameter("FatherPhone"));
       String FatherOccupation = StringUtils.trimToEmpty(request.getParameter("FatherOccupation"));
       String FatherID = StringUtils.trimToEmpty(request.getParameter("FatherID"));
       String FatherEmail = StringUtils.trimToEmpty(request.getParameter("FatherEmail"));
       String MotherName = StringUtils.trimToEmpty(request.getParameter("MotherName"));
       String MotherPhone = StringUtils.trimToEmpty(request.getParameter("MotherPhone"));
       String MotherOccupation = StringUtils.trimToEmpty(request.getParameter("MotherOccupation"));
       String MotherID = StringUtils.trimToEmpty(request.getParameter("MotherID"));
       String MotherEmail = StringUtils.trimToEmpty(request.getParameter("MotherEmail"));
       String studentUuid = StringUtils.trimToEmpty(request.getParameter("studentUuid"));
      

       if(StringUtils.isBlank(FatherName)){ 
		   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_FATHER_NAME); 
		   
	   }else if(StringUtils.isBlank(FatherPhone)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_FATHER_PHONE); 
		     
	   }else if(!isNumeric(FatherPhone)){
	 	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_PHONE_NUMERIC); 
		   
	    }else if(!lengthValid(FatherPhone)){
		 	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_PHONE_INVALID); 
			   
		}else if(StringUtils.isBlank(FatherOccupation)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_OCCUPATION); 
			   
	   }/*else if(StringUtils.isBlank(FatherID)){ 
		   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_FATHER_ID); 
		   
	   }else if(StringUtils.isBlank(FatherEmail)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_FATHER_EMAIL); 
		     
	   }else if(StringUtils.isBlank(MotherName)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_MOTHER_NAME); 
			   
	   }else if(StringUtils.isBlank(MotherPhone)){ 
		   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_MOTHER_PHONE); 
		   
	   }else if(!isNumeric(MotherPhone)){
	 	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_PHONE_NUMERIC); 
		   
	    }else if(!lengthValid(MotherPhone)){
		 	   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_PHONE_INVALID); 
			   
		}else if(StringUtils.isBlank(MotherOccupation)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_MOTHER_OCCUPATION); 
		     
	   }else if(StringUtils.isBlank(MotherID)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_MOTHER_ID); 
		     
	   }else if(StringUtils.isBlank(MotherEmail)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_MOTHER_EMAIL); 
		     
	   }*/else if(StringUtils.isBlank(studentUuid)){
		     session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_PARENT_NOT_UPDATED); 
		     
	   }else{
		   if(parentsDAO.getParent(studentUuid)!=null){
		   StudentParent parent = parentsDAO.getParent(studentUuid);
		   parent.setFathername(StringUtils.capitalize(FatherName).toLowerCase());
		   parent.setFatherphone(FatherPhone);
		   parent.setFatheroccupation(StringUtils.capitalize(FatherOccupation).toLowerCase());
		   parent.setFatherEmail(FatherEmail);
		   parent.setFatherID(FatherID); 
		   
		   parent.setMothername(StringUtils.capitalize(MotherName).toLowerCase());
		   parent.setMotherphone(MotherPhone);
		   parent.setMotheroccupation(StringUtils.capitalize(MotherOccupation).toLowerCase());
		   parent.setMotherEmail(MotherID);
		   parent.setMotherID(MotherEmail); 
		   
		   parent.setStudentUuid(studentUuid); 

		   if(parentsDAO.updateParent(parent)){
			   session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS, SUCCESS_PARENT_UPDATED); 

		   }else{
			   session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_PARENT_NOT_UPDATED); 

		   }
		   }
	   }
       response.sendRedirect("parents.jsp");  
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
	if(length<10 ||length>10){
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

   /**
    * 
    */
   private static final long serialVersionUID = -5244727460715534586L;
}
