/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.school.staff;

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

import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**
 * @author peter
 *
 */  
public class AddStaff extends HttpServlet {
	
	/**
	 * 
	 */
	final  String pos_Pricipal =(String)  PropertiesConfig.getConfigValue("POSITION_PRINCIPAL"); 
	final  String pos_Deputy_Pricipal =(String)  PropertiesConfig.getConfigValue("POSITION_DEPUTY"); 
	private String principalId = "";
	private String DeputyprincipalId = ""; 
	final String ERROR_PHONE_INVALID = "Staff phone is invalid, the number must have 10 digits (e.g. 0718953974).";
	final String ERROR_SELECT_POSITION = "Please select a position"; 
	
	final String ERROR_EMPTY_USERNAME = "Username can't be empty";
	final String ERROR_EMPTY_FIRST_NAME = "Firstname can't be empty";
	final String ERROR_EMPTY_MIDDLE_NAME = "Middle name can't be empty";
	final String ERROR_EMPTY_LAST_NAME = "Last name can't be empty";
	
	 final String ERROR_MAX_USERNAME = "Username can only have character length of 5.";
	final String ERROR_EMPTY_GENDER = "Please select a gender";
	final String ERROR_EMPTY_PHONE = "phone can't be empty";
	final String ERROR_PHONE_NUMERIC = "phone can only be numeric";
	final String ERROR_EMPTY_IDNO = "ID number can't be empty";
	final String ERROR_EMPTY_COUNTY = "county can't be empty";
	final String ERROR_EMPTY_DOB = "date of birth can't be empty";
	
	final String ERROR_PRINCIPAL_EXIST = "Principal already added";
	final String ERROR_DPRINCIPAL_EXIST = "Deputy Principal already added";
	
	
	final String STAFF_ADD_SUCSESS = "Staff added successfully, they can user their username, and National Id number as password to login";
	final String STAFF_ADD_ERROR = " An error occured while adding staff";
	
	final String STAFF_USERNAME_EXIST= "Staff Username already exist";
	final String STAFF_EMP_NO_EXIST = "Employee Number already exist";
	final String STAFF_EMP_NO_EMPTY = "Provide Employee Number";
	
	 private final String STATUS_ACTIVE_UUID = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
	 final String ERROR_USERNAME_PASSWORD_MATCH = "Usernme and password can't be the same, national Id number is the default password";
	 
	 final String NAME_ERROR = "Name format error/incorrent lenght.";
	
	
	private static StaffDAO staffDAO;
	private EmailValidator emailValidator;
	private static StaffDAO staffDAO;

	/**
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       staffDAO = StaffDAO.getInstance();
       emailValidator = EmailValidator.getInstance();
       staffDAO = StaffDAO.getInstance();
   }
   
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String PositionUuid = StringUtils.trimToEmpty(request.getParameter("AcessLevel"));
       String username = StringUtils.trimToEmpty(request.getParameter("username"));
       String employeeNo = StringUtils.trimToEmpty(request.getParameter("employeeNo"));
       String firstname = StringUtils.trimToEmpty(request.getParameter("firstname"));
       String middlename = StringUtils.trimToEmpty(request.getParameter("lastname"));
       String lastname = StringUtils.trimToEmpty(request.getParameter("surname"));
       String gender = StringUtils.trimToEmpty(request.getParameter("gender"));
       String nhif = StringUtils.trimToEmpty(request.getParameter("nhif"));
       String nssf = StringUtils.trimToEmpty(request.getParameter("nssf"));
       String phone = StringUtils.trimToEmpty(request.getParameter("phone"));
       String idno = StringUtils.trimToEmpty(request.getParameter("idno"));
       String county = StringUtils.trimToEmpty(request.getParameter("county"));
       String dob = StringUtils.trimToEmpty(request.getParameter("dob"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String sysUser = StringUtils.trimToEmpty(request.getParameter("systemuser"));
       String category = StringUtils.trimToEmpty(request.getParameter("category"));
       String schoolAccountUuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       
       principalId = " ";
	   DeputyprincipalId = "";
       if(StringUtils.equals(PositionUuid, pos_Pricipal)){
    	   principalId = pos_Pricipal;
       }if(StringUtils.equals(PositionUuid, pos_Deputy_Pricipal)){
    	   DeputyprincipalId = pos_Deputy_Pricipal;
       }
      
	   	Map<String, String> paramHash = new HashMap<>();    	
	   	paramHash.put("username", username);
	   	paramHash.put("employeeNo", employeeNo);
	   	paramHash.put("firstname", firstname);
	   	paramHash.put("lastname", middlename);
	   	paramHash.put("surname", lastname);
	   	paramHash.put("gender", gender);
	   	paramHash.put("nhif", nhif);
	   	paramHash.put("nssf", nssf);
	   	paramHash.put("phone", phone);
	   	paramHash.put("idno", idno);
		paramHash.put("county", county);
	   	paramHash.put("dob", dob);
       
       
       if(StringUtils.isEmpty(PositionUuid)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_SELECT_POSITION); 
    	   
       }else if(StringUtils.isEmpty(username)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_EMPTY_USERNAME); 
    	   
       }else if(StringUtils.isEmpty(employeeNo)){ 
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, STAFF_EMP_NO_EMPTY);  
    	   
       }else if(StringUtils.isEmpty(firstname)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_EMPTY_FIRST_NAME); 
    	   
       }else if(StringUtils.isEmpty(middlename)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_EMPTY_MIDDLE_NAME); 
    	   
       }/*else if(StringUtils.isEmpty(surname)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_EMPTY_SURNAME); 
    	   
       }*/else if(!Wordlength(username)){
	 	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, NAME_ERROR); 
		   
	   }else if(!Wordlength(firstname)){
	 	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, NAME_ERROR); 
		   
	   }else if(!Wordlength(middlename)){
	 	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, NAME_ERROR); 
		   
	   }/*else if(!Wordlength(surname)){
	 	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, NAME_ERROR); 
		   
	   }*/else if(StringUtils.isEmpty(gender)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_EMPTY_GENDER); 
    	   
       }else if(StringUtils.isEmpty(phone)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_EMPTY_PHONE); 
    	   
       }else if(!isNumeric(phone)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_PHONE_NUMERIC); 
    	   
       }else if(!lengthValid(phone)){
	 	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_PHONE_INVALID); 
		   
	   }else if(StringUtils.isEmpty(idno)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_EMPTY_IDNO); 
    	   
       }/*else if(StringUtils.isEmpty(county)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_EMPTY_COUNTY); 
    	   
       }else if(StringUtils.isEmpty(dob)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_EMPTY_DOB); 
    	   
       }*/else if(StringUtils.equalsIgnoreCase(username, idno)){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_USERNAME_PASSWORD_MATCH); 
    	   
       }else if(staffDAO.getStaffByPosition(schoolAccountUuid, principalId) !=null){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_PRINCIPAL_EXIST); 
    	   
       }else if(staffDAO.getStaffByPosition(schoolAccountUuid, DeputyprincipalId) !=null){
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, ERROR_DPRINCIPAL_EXIST); 
    	   
       }else if(staffDAO.getStaffDetailByemployeeNo(employeeNo) !=null){ 
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR,STAFF_EMP_NO_EXIST );  
    	   
       }else if(staffDAO.getStaffByUsername(schoolAccountUuid, username) !=null){ 
    	   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, STAFF_USERNAME_EXIST); 
    	   
       }else{
    	  
    	   Staff staff = new Staff();
    	   staff.setUuid(staff.getUuid()); 
    	   staff.setCategory(category);
    	   staff.setPassword(idno);
    	   staff.setPositionUuid(PositionUuid);
    	   staff.setSchoolAccountUuid(schooluuid);
    	   staff.setUserName(username);
    	   staff.setStatusUuid(STATUS_ACTIVE_UUID); 
    	   
    	   Staff staffDetail = new Staff();
    	   staffDetail.setStaffUuid(staff.getUuid()); 
    	   staffDetail.setEmployeeNo(employeeNo);
    	   staffDetail.setFirstName(StringUtils.capitalize(firstname).toLowerCase());
    	   staffDetail.setLastName(StringUtils.capitalize(middlename).toLowerCase());
    	   staffDetail.setSurname(StringUtils.capitalize(lastname).toLowerCase()); 
    	   staffDetail.setGender(gender.toUpperCase());
    	   staffDetail.setNhifNo(nhif);
    	   staffDetail.setNssfNo(nssf);
    	   staffDetail.setPhone(phone);
    	   staffDetail.setdOB(dob);
    	   staffDetail.setNationalID(idno); 
    	   staffDetail.setCounty(StringUtils.capitalize(county).toLowerCase());
    	   staffDetail.setSysUser(sysUser);
    	   
    	   if(staffDAO.putStaff(staff) && staffDAO.putSStaffDetail(staffDetail)){
    		   session.setAttribute(SessionConstants.STAFF_ADD_SUCCESS, STAFF_ADD_SUCSESS); 
    		   paramHash.clear();
    	   }else{
    		   session.setAttribute(SessionConstants.STAFF_ADD_ERROR, STAFF_ADD_ERROR); 
    	   }
    	   
       }
       session.setAttribute(SessionConstants.STAFF_ADD_PARAM, paramHash);
       response.sendRedirect("addStaff.jsp");  
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
	 * @param mystring
	 * @return
	 */
	private static boolean Wordlength(String mystring) {
		boolean isvalid = true;
		int length = 0;
		length = mystring.length();
		if(length<3){
			isvalid = false;
		}
		return isvalid;
	}
	

   @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          doPost(request, response);
      }
   private static final long serialVersionUID = -8324266217600650362L;
}
