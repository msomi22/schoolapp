/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.student;

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
import org.apache.commons.validator.routines.EmailValidator;

import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class AddStudentParent extends HttpServlet{
	
	
	final String FATHER_MOTHER_ADD_ERROR = "An error occured while saving the details.";
	final String ERROR_STUDENT_PARENT_EXIST = "Student parent details already exist.";
	final String ERROR_EMPTY_FATHER_OCCUPATION = "Father occupation cant be empty.";
	final String ERROR_EMPTY_MOTHER_OCCUPATION = "Mother occupation cant be empty.";
	final String ERROR_INVALID_FATHER_EMAIL = "Father email address is not valid";
	final String ERROR_INVALID_MOTHER_EMAIL = "Mother email address is not valid";
	final String ERROR_EMPTY_RELATIVE_PHONE = "Relative phone cant be empty.";
	final String ERROR_EMPTY_MOTHER_ID = "Mother ID number cant be empty.";
	final String ERROR_EMPTY_FATHER_PHONE = "Father phone cant be empty.";
	final String ERROR_EMPTY_FATHER_ID = "Father ID number cant be empty.";
	final String ERROR_EMPTY_RELATIVE_NAME = "Relative name cant be empty.";
	final String ERROR_EMPTY_FATHER_EMAIL = "Father email cant be empty.";
	final String ERROR = "Unexpected error occured, find a student first";
	final String ERROR_EMPTY_MOTHER_PHONE = "Mother phone cant be empty.";
	final String ERROR_EMPTY_MOTHER_EMAIL = "Mother email cant be empty.";
	final String FATHER_MOTHER_ADD_SUCCESS = "Details added successfully.";
	final String ERROR_EMPTY_MOTHER_NAME = "Mother name cant be empty.";
	final String ERROR_EMPTY_FATHER_NAME = "Father name cant be empty.";
	final String ERROR_PHONE_INVALID = "Phone is invalid, the number must have 10 digits (e.g. 0718953974).";
	final String ERROR_STUDENT_INACTIVE = "This student is Inactive, you can not assign them a parent.";
	
	final String ERROR_PHONE_NUMERIC = "phone can only be numeric";
	final String NAME_ERROR = "Name format error/incorrent lenght.";
	
	final String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
	
	private static StudentDAO studentDAO;
	private static ParentsDAO parentsDAO;
	private EmailValidator emailValidator;

	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentDAO = StudentDAO.getInstance();
       parentsDAO = ParentsDAO.getInstance();
       emailValidator = EmailValidator.getInstance();
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
     
     
       String RelativePhone = "not-set";
       String RelativeName = "not-set";
       String MotherOccupation = "not-set";
       String MotherPhone = "not-set";
       String MotherEmail = "not-set";
       String MotherName = "not-set";
       String MotherID = "not-set";
       
       
      
       MotherOccupation = StringUtils.trimToEmpty(request.getParameter("MotherOccupation"));
       MotherPhone = StringUtils.trimToEmpty(request.getParameter("MotherPhone"));
       MotherEmail = StringUtils.trimToEmpty(request.getParameter("MotherEmail"));
       MotherName = StringUtils.trimToEmpty(request.getParameter("MotherName"));
       MotherID = StringUtils.trimToEmpty(request.getParameter("MotherID"));
       
       RelativePhone = StringUtils.trimToEmpty(request.getParameter("RelativePhone"));
       RelativeName = StringUtils.trimToEmpty(request.getParameter("RelativeName"));
       
       String FatherOccupation = StringUtils.trimToEmpty(request.getParameter("FatherOccupation"));
       String FatherPhone = StringUtils.trimToEmpty(request.getParameter("FatherPhone"));
       String FatherEmail = StringUtils.trimToEmpty(request.getParameter("FatherEmail"));
       String studentUuid = StringUtils.trimToEmpty(request.getParameter("studentUuid"));
       String FatherName = StringUtils.trimToEmpty(request.getParameter("FatherName"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String FatherID = StringUtils.trimToEmpty(request.getParameter("FatherID"));
      
       
       
       Map<String, String> fatherMotherParamHash = new HashMap<>(); 
       Map<String, String> paramHash = new HashMap<>(); 
	   fatherMotherParamHash.put("FatherName", FatherName); 
	   fatherMotherParamHash.put("FatherPhone", FatherPhone);
	   fatherMotherParamHash.put("FatherOccupation", FatherOccupation); 
	   fatherMotherParamHash.put("FatherID", FatherID); 
	   fatherMotherParamHash.put("FatherEmail", FatherEmail); 
	   
	   fatherMotherParamHash.put("MotherName", MotherName); 
	   fatherMotherParamHash.put("MotherPhone", MotherPhone);
	   fatherMotherParamHash.put("MotherOccupation", MotherOccupation); 
	   fatherMotherParamHash.put("MotherID", MotherID); 
	   fatherMotherParamHash.put("MotherEmail", MotherEmail); 
	   
	   fatherMotherParamHash.put("RelativeName", RelativeName); 
	   fatherMotherParamHash.put("RelativePhone", RelativePhone); 
	   
	   Student student = studentDAO.getStudentByuuid(schooluuid, studentUuid);
	   if(student !=null){ 
	   paramHash.put("studentuuid", student.getUuid()); 
	   paramHash.put("admNumber", student.getAdmno());
	   paramHash.put("firstname", student.getFirstname()); 
	   paramHash.put("lastname", student.getLastname()); 
	   paramHash.put("surname", student.getSurname()); 
	   }
	  
	   
	if(StringUtils.isBlank(studentUuid)){
		     session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR); 
		   
	 }else if(StringUtils.isBlank(schooluuid)){ 
		   session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR); 	  
	}
    else if(StringUtils.isBlank(FatherName)){
			session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_FATHER_NAME); 
						   
	}else if(StringUtils.isBlank(FatherPhone)){ 
			session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_FATHER_PHONE); 
							  
	}else if(!isNumeric(FatherPhone)){
 	   session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_PHONE_NUMERIC); 
	   
    }else if(!lengthValid(FatherPhone)){
	 	   session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_PHONE_INVALID); 
		   
	}/*else if(StringUtils.isBlank(FatherOccupation)){ 
		session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_FATHER_OCCUPATION); 
							  
	}else if(StringUtils.isBlank(FatherID)){ 
			session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_FATHER_ID); 
							  
	}/*else if(StringUtils.isBlank(FatherEmail)){ 
		session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_FATHER_EMAIL); 
							  
	}
	else if(!emailValidator.isValid(FatherEmail)){		     
		   session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_INVALID_FATHER_EMAIL);  
	  	   
    }
	/*else if(StringUtils.isBlank(MotherName)){
			session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_MOTHER_NAME); 
						   
	}else if(StringUtils.isBlank(MotherPhone)){ 
			session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_MOTHER_PHONE); 
							  
	}else if(StringUtils.isBlank(MotherOccupation)){ 
		 session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_MOTHER_OCCUPATION); 
							  
	}else if(StringUtils.isBlank(MotherID)){ 
						   session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_MOTHER_ID); 
							  
	}else if(StringUtils.isBlank(MotherEmail)){ 
			session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_MOTHER_EMAIL); 					  
	}
	else if(!emailValidator.isValid(MotherEmail)){		     
		   session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_INVALID_MOTHER_EMAIL);  
		  	   
	}
	else if(StringUtils.isBlank(RelativeName)){ 
		 session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_RELATIVE_NAME); 
			  
   }else if(StringUtils.isBlank(RelativePhone)){ 
		   session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_EMPTY_RELATIVE_PHONE); 
		   
   }*/else if(parentsDAO.getParent(studentUuid) !=null){ 
		   session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, ERROR_STUDENT_PARENT_EXIST); 
		   
	}else{
		
		if(StringUtils.equals(student.getStatusUuid(),statusUuid)){
		
		    RelativePhone = "not-set";
	        RelativeName = "not-set";
	        MotherOccupation = "not-set";
	        MotherPhone = "not-set";
	        MotherEmail = "not-set";
	        MotherName = "not-set";
	        MotherID = "not-set";
	   
		StudentParent parent = new StudentParent();
		parent.setStudentUuid(studentUuid); 
		parent.setFathername(StringUtils.capitalize(FatherName.toLowerCase()));
		parent.setFatheroccupation(StringUtils.capitalize(FatherOccupation.toLowerCase()));
		parent.setFatherEmail(FatherEmail);
		parent.setFatherID(FatherID);
		parent.setFatherphone(FatherPhone);
		
		parent.setMothername(MotherName.toLowerCase());
		parent.setMotheroccupation(MotherOccupation.toLowerCase());
		parent.setMotherEmail(MotherEmail);
		parent.setMotherID(MotherID);
		parent.setMotherphone(MotherPhone);
		parent.setRelativeName(RelativeName.toLowerCase());
		parent.setRelativePhone(RelativePhone);
	    if(parentsDAO.putParent(parent)){
	    	 fatherMotherParamHash.clear();
	    	 session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_SUCCESS, FATHER_MOTHER_ADD_SUCCESS); 	
	    }else{
	    	session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, FATHER_MOTHER_ADD_ERROR); 	
	    }
	    
		 }else{
			  session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR,ERROR_STUDENT_INACTIVE ); 
		  }
	   
	   session.setAttribute(SessionConstants.PARENT_PARAM, paramHash);    
   
	   }
	   session.setAttribute(SessionConstants.FATHER_MOTHER_PARAM, fatherMotherParamHash); 
       response.sendRedirect("addParent.jsp");  
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
	
   
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
      doPost(request, response);
  }
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 3250078508908690801L;
}
