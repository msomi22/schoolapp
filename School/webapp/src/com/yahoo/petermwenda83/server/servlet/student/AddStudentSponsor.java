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

import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor;
import com.yahoo.petermwenda83.persistence.guardian.SponsorsDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;


/**  
 * @author peter
 *
 */
public class AddStudentSponsor extends HttpServlet{
	
	final String ERROR_STUDENT_SPONSOR_EXIST = "This student's sponsor has already been registered.";
	final String SPONSOR_ADD_ERROR = "An error occured while adding Sponsor details";
	final String ERROR_EMPTY_OCCUPATION = "Sponsor occupation can't be empty.";
	final String SPONSOR_ADD_SUCESS = "Sponsor detail added successfully";
	final String ERROR = "Unexpected error occured, find a student first";
	final String ERROR_EMPTY_COUNTRY = "Sponsor country can't be empty.";
	final String ERROR_EMPTY_COUNTY = "Sponsor county can't be empty.";
	final String ERROR_EMPTY_PHONE = "Sponsor phone can't be empty.";
	final String ERROR_PHONE_INVALID = "Sponsor phone is invalid, phone number must have 10 digits (e.g. 0718953974).";
	final String ERROR_STUDENT_INACTIVE = "This student is Inactive, you can not assign them a sponsor.";
	final String ERROR_EMPTY_NAME = "Sponsor name can't be empty.";
	
	final String NAME_ERROR = "Name format error/incorrent lenght.";
	
	final String ERROR_PHONE_NUMERIC = "phone can only be numeric";
	
	final String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
	
	private static SponsorsDAO sponsorsDAO;
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
       sponsorsDAO = SponsorsDAO.getInstance();
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String SponsorName = StringUtils.trimToEmpty(request.getParameter("SponsorName"));
       String SponsorPhone = StringUtils.trimToEmpty(request.getParameter("SponsorPhone"));
       String SponsorOccupation = StringUtils.trimToEmpty(request.getParameter("SponsorOccupation"));
       String SponsorCountry = StringUtils.trimToEmpty(request.getParameter("SponsorCountry"));
       String SponsorCounty = StringUtils.trimToEmpty(request.getParameter("SponsorCounty"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String studentUuid = StringUtils.trimToEmpty(request.getParameter("studentUuid"));
       
       
       Map<String, String> sponsorParamHash = new HashMap<>(); 
       sponsorParamHash.put("SponsorName", SponsorName); 
       sponsorParamHash.put("SponsorPhone", SponsorPhone);
       sponsorParamHash.put("SponsorOccupation", SponsorOccupation); 
       sponsorParamHash.put("SponsorCountry", SponsorCountry); 
       sponsorParamHash.put("SponsorCounty", SponsorCounty); 
       
       Map<String, String> paramHash = new HashMap<>(); 
       Student student = studentDAO.getStudentByuuid(schooluuid, studentUuid);
	   if(student !=null){ 
	   paramHash.put("studentuuid", student.getUuid()); 
	   paramHash.put("admNumber", student.getAdmno());
	   paramHash.put("firstname", student.getFirstname()); 
	   paramHash.put("lastname", student.getLastname()); 
	   paramHash.put("surname", student.getSurname()); 
	   }
       
       if(StringUtils.isBlank(studentUuid)){
		     session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR, ERROR); 
		   
	   }else if(StringUtils.isBlank(schooluuid)){ 
		   session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR, ERROR); 
		   
	   }else if(StringUtils.isBlank(SponsorName)){ 
		   session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR, ERROR_EMPTY_NAME); 
		   
	   }else if(StringUtils.isBlank(SponsorPhone)){ 
		   session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR, ERROR_EMPTY_PHONE); 
		   
	   }else if(!isNumeric(SponsorPhone)){
	 	   session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR, ERROR_PHONE_NUMERIC); 
		   
	    }else if(!lengthValid(SponsorPhone)){
		 	   session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR, ERROR_PHONE_INVALID); 
			   
		}/*else if(StringUtils.isBlank(SponsorOccupation)){ 
		   session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR, ERROR_EMPTY_OCCUPATION); 
		   
	   }else if(StringUtils.isBlank(SponsorCountry)){ 
		   session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR, ERROR_EMPTY_COUNTRY); 
		   
	   }else if(StringUtils.isBlank(SponsorCounty)){ 
		   session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR, ERROR_EMPTY_COUNTY); 
		   
	   }*/else if(sponsorsDAO.getSponsor(studentUuid) !=null){ 
		   session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR, ERROR_STUDENT_SPONSOR_EXIST); 
		   
	   }else{
		   
		   if(StringUtils.equals(student.getStatusUuid(),statusUuid)){
		   
		StudentSponsor sponsor = new StudentSponsor();
		sponsor.setStudentUuid(studentUuid);
		sponsor.setSponsorName(StringUtils.capitalize(SponsorName.toLowerCase()));
		sponsor.setSponsorPhone(SponsorPhone);
		sponsor.setSponsorOccupation(StringUtils.capitalize(SponsorOccupation.toLowerCase()));
		sponsor.setSponsorCountry(StringUtils.capitalize(SponsorCountry.toLowerCase()));
		sponsor.setSponsorCounty(StringUtils.capitalize(SponsorCounty.toLowerCase())); 

		session.setAttribute(SessionConstants.SPONSOR_PARAM, paramHash); 
		
		  if(sponsorsDAO.putSponsor(sponsor)){
			  sponsorParamHash.clear();
			  session.setAttribute(SessionConstants.SPONSOR_ADD_SUCCESS, SPONSOR_ADD_SUCESS); 
		  }else{
			  session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR, SPONSOR_ADD_ERROR);   
		  }
		  
		   }else{
 			  session.setAttribute(SessionConstants.SPONSOR_ADD_ERROR,ERROR_STUDENT_INACTIVE ); 
 		  }
	   }
       
       session.setAttribute(SessionConstants.STUDENT_SPONSOR_PARAM, sponsorParamHash); 
       response.sendRedirect("addSponsor.jsp");  
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
private static final long serialVersionUID = 7119631153789944957L;
}
