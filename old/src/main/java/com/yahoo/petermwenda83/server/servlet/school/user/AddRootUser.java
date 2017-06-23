package com.yahoo.petermwenda83.server.servlet.school.user;

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

import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.server.session.AdminSessionConstants;

/**
 * @author peter
 * 
 */
public class AddRootUser extends HttpServlet{

	
	
	private static StaffDAO staffDAO;
	private static StaffDAO staffDAO;
	final String ERROR_PHONE_INVALID = "Phone is invalid, phone number must have 10 digits (e.g. 0718953974).";//
    final String ERROR_EMPTY_ACCOUNT = "Please Select an Account.";
    final String ERROR_EMPTY_CATEGORY = "Please Select a Category.";
    final String ERROR_EMPTY_POSITION = "Please Select a AcessLevel";
    final String ERROR_EMPTY_USERNAME = "Username can't be empty";
    final String ERROR_MAX_USERNAME = "Username can only have character length of 5.";
    final String ERROR_EMPTY_PASSWORD = "Password cant be empty";
    final String ERROR_PHONE_NUMERIC = "phone can only be numeric";
    final String NAME_ERROR = "Name format error/incorrent lenght.";
    final String ERROR_USERNAME_PASSWORD_MATCH = "Usernme and password can't be the same";
    
    final String ERROR_PRINCIPAL_EXIST = "Principal already added";
    
    final String ERROR_PRINCIPAL_NOT_ADDED = "Principal add error";
    final String SUCCESS_PRINCIPAL_ADDED = "Principal Added Successfully";
    
    private final String STATUS_ACTIVE_UUID = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
    private final String PRINCIPAL_POSITION = "C3915245-00EE-4EF4-9898-ACE59683DD60";
	
	/**
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       staffDAO = StaffDAO.getInstance();
       staffDAO = StaffDAO.getInstance();
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String accountUuid = StringUtils.trimToEmpty(request.getParameter("accountUuid"));
       String principalusername = StringUtils.trimToEmpty(request.getParameter("principalusername"));
       String principalpassword = StringUtils.trimToEmpty(request.getParameter("principalpassword"));
       String employeeNo = StringUtils.trimToEmpty(request.getParameter("employeeNo"));
       String firstname = StringUtils.trimToEmpty(request.getParameter("firstname"));
       String lastname = StringUtils.trimToEmpty(request.getParameter("lastname"));
       String surname = StringUtils.trimToEmpty(request.getParameter("surname"));
       String gender = StringUtils.trimToEmpty(request.getParameter("gender"));
       String nhif = StringUtils.trimToEmpty(request.getParameter("nhif"));
       String nssf = StringUtils.trimToEmpty(request.getParameter("nssf"));
       String phone = StringUtils.trimToEmpty(request.getParameter("phone"));
       String idno = StringUtils.trimToEmpty(request.getParameter("idno"));
       String county = StringUtils.trimToEmpty(request.getParameter("county"));
       String dob = StringUtils.trimToEmpty(request.getParameter("dob"));
      
     
	   	Map<String, String> paramHash = new HashMap<>();    	
	   	paramHash.put("principalusername", principalusername);
	   	paramHash.put("principalpassword", principalpassword);
	   	paramHash.put("employeeNo", employeeNo);
	   	paramHash.put("firstname", firstname);
	   	paramHash.put("lastname", lastname);
	   	paramHash.put("surname", surname);
	   	paramHash.put("gender", gender);
	   	paramHash.put("nhif", nhif);
	   	paramHash.put("nssf", nssf);
	   	paramHash.put("phone", phone);
	   	paramHash.put("idno", idno);
		paramHash.put("county", county);
	   	paramHash.put("dob", dob);
	   
       if(StringUtils.isEmpty(accountUuid)){
    	   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, ERROR_EMPTY_ACCOUNT); 
    	   
       }else if(StringUtils.isEmpty(principalusername)){
    	   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, ERROR_EMPTY_USERNAME); 
    	   
       }else if(StringUtils.equalsIgnoreCase(principalusername, principalpassword)){
    	   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, ERROR_USERNAME_PASSWORD_MATCH); 
    	   
       }else if(!Wordlength(principalusername)){
	 	   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, NAME_ERROR); 
		   
	   }else if(!Wordlength(firstname)){
	 	   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, NAME_ERROR); 
		   
	   }else if(!Wordlength(lastname)){
	 	   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, NAME_ERROR); 
		   
	   }else if(!Wordlength(surname)){
	 	   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, NAME_ERROR); 
		   
	   }else if(!isNumeric(phone)){
    	   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, ERROR_PHONE_NUMERIC); 
    	   
       }else if(!lengthValid(phone)){
	 	   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, ERROR_PHONE_INVALID); 
		   
	  }else if(StringUtils.isEmpty(principalpassword)){
    	   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, ERROR_EMPTY_PASSWORD); 
    	   
       }else if(staffDAO.getStaffByPosition(accountUuid, PRINCIPAL_POSITION) !=null){
    	   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, ERROR_PRINCIPAL_EXIST); 
    	   
       }else{
    	   
    	   Staff staff = new Staff();
    	   staff.setSchoolAccountUuid(accountUuid); 
    	   staff.setCategory("Teaching");
    	   staff.setPositionUuid(PRINCIPAL_POSITION); 
    	   staff.setUserName(principalusername); 
    	   staff.setPassword(principalpassword); 
    	   staff.setStatusUuid(STATUS_ACTIVE_UUID); 
    	   
    	   Staff staffDetail = new Staff();
    	   staffDetail.setStaffUuid(staff.getUuid()); 
    	   staffDetail.setEmployeeNo(employeeNo);
    	   staffDetail.setFirstName(StringUtils.capitalize(firstname));
    	   staffDetail.setLastName(StringUtils.capitalize(lastname));
    	   staffDetail.setSurname(StringUtils.capitalize(surname)); 
    	   staffDetail.setGender(gender);
    	   staffDetail.setNhifNo(nhif);
    	   staffDetail.setNssfNo(nssf);
    	   staffDetail.setPhone(phone);
    	   staffDetail.setdOB(dob);
    	   staffDetail.setNationalID(idno); 
    	   staffDetail.setCounty(StringUtils.capitalize(county));
    	   staffDetail.setSysUser("Admin");
    	   
    	   if(staffDAO.putStaff(staff) && staffDAO.putSStaffDetail(staffDetail)){
    		   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_SUCCESS, SUCCESS_PRINCIPAL_ADDED);  
    	   }else{
    		   session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, ERROR_PRINCIPAL_NOT_ADDED);  
    	   }
    	   
    	   
       }
       
       
       session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_PARAM, paramHash);
       response.sendRedirect("rootUser.jsp");  
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

/**
 * 
 */
private static final long serialVersionUID = 1L;

}
