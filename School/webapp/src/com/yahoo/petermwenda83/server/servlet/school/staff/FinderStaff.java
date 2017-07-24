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

import com.yahoo.petermwenda83.bean.staff.StaffDetails;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;


public class FinderStaff extends HttpServlet{
	
	
	final String ERROR_STAFF_NOT_FOUND = " The employee number you provided was not found in the system.";
	final String STAFF_FIND_SUCCESS = "The staff was found , proceed";
    final String ERROR_NO_EMP_NO = "You didn't provide any employee number.";
    final String ERROR = "Something went wrong check the employee number.";
    
    private static StaffDetailsDAO staffDetailsDAO;
    private static StaffDAO staffDAO;


	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       staffDetailsDAO = StaffDetailsDAO.getInstance();
       staffDAO  = StaffDAO.getInstance();
      
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

	   HttpSession session = request.getSession(true);
       String employeeNumber = StringUtils.trimToEmpty(request.getParameter("employeeNumber"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
      
       Map<String, String> paramHash = new HashMap<>();   
       
       if(StringUtils.isBlank(schooluuid)){
		     session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR); 
		   
	   }
       else if(StringUtils.isBlank(employeeNumber)){
		     session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_NO_EMP_NO); 
		   
	   }else if(staffDetailsDAO.getStaffDetailByemployeeNo(employeeNumber)==null){ 
		   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR_STAFF_NOT_FOUND); 
		  
	   }else{
		   
		   StaffDetails staff = staffDetailsDAO.getStaffDetailByemployeeNo(employeeNumber);
		   
		   
		   if(staffDAO.getStaff(schooluuid, staff.getStaffUuid()) !=null){
			   
		   paramHash.put("staffuuid", staff.getStaffUuid()); 
		   paramHash.put("staffNumber", staff.getEmployeeNo());
		   paramHash.put("firstname", StringUtils.capitalize(staff.getFirstName().toLowerCase())); 
		   paramHash.put("lastname", StringUtils.capitalize(staff.getLastName().toLowerCase())); 
		   paramHash.put("surname", StringUtils.capitalize(staff.getSurname().toLowerCase())); 
		   
		   session.setAttribute(SessionConstants.STAFF_PARAM, paramHash); 
		   session.setAttribute(SessionConstants.STAFF_FIND_SUCCESS, STAFF_FIND_SUCCESS);   
		   
		   }else{
			   session.setAttribute(SessionConstants.STAFF_FIND_ERROR, ERROR); 
		   }
		   
		 
		   
	         
	   }
 
      
       response.sendRedirect("teacherSubjects.jsp");  
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
private static final long serialVersionUID = 2758416139449181921L;

}
