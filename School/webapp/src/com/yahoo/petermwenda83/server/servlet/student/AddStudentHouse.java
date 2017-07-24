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
import com.yahoo.petermwenda83.bean.student.StudentHouse;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.student.StudentHouseDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;


/** 
 * @author peter
 *
 */
public class AddStudentHouse extends HttpServlet{
	
	final String ERROR_STUDENT_HAS_ANOTHER_ROOM = "This student has already been assigend another house.";
	final String ERROR_STUDENT_INACTIVE = "This student is Inactive, they can not be assigned a house.";
	final String ERROR_STUDENT_HAS_ROOM = "This student has already been assigend a house.";
	final String HOUSE_ADD_ERROR = "An error occured while assigning student a house";
	final String ERROR = "Unexpected error occured, find a student first";
	final String HOUSE_ADD_SUCESS = "House assigned successfully";
	final String ERROR_EMPTY_HOUSE = "Please select a house.";
	
	
	final String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
	
	private static StudentHouseDAO studentHouseDAO;
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
       studentHouseDAO = StudentHouseDAO.getInstance();
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String houseUuid = StringUtils.trimToEmpty(request.getParameter("house"));
       String systemUser = StringUtils.trimToEmpty(request.getParameter("systemuser"));
       String studentUuid = StringUtils.trimToEmpty(request.getParameter("studentUuid"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
      
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
		     session.setAttribute(SessionConstants.HOUSE_ADD_ERROR, ERROR); 
		   
	   }else if(StringUtils.isBlank(schooluuid)){ 
		   session.setAttribute(SessionConstants.HOUSE_ADD_ERROR, ERROR); 
		   
	   }else if(StringUtils.isBlank(houseUuid)){
		     session.setAttribute(SessionConstants.HOUSE_ADD_ERROR, ERROR_EMPTY_HOUSE); 
		     
	   }else if(studentHouseDAO.getHouse(studentUuid)!=null){ 
		     session.setAttribute(SessionConstants.HOUSE_ADD_ERROR, ERROR_STUDENT_HAS_ROOM); 
		     
	   }else if(studentHouseDAO.getHouseByHoudeId(houseUuid, studentUuid)!=null){
		     session.setAttribute(SessionConstants.HOUSE_ADD_ERROR, ERROR_STUDENT_HAS_ANOTHER_ROOM); 
		     
	   }else{
		   if(StringUtils.equals(student.getStatusUuid(),statusUuid)){
		   StudentHouse studentHouse = new StudentHouse();
		   studentHouse.setStudentUuid(studentUuid);
		   studentHouse.setHouseUuid(houseUuid);
		   studentHouse.setSysUser(systemUser); 
		   if(studentHouseDAO.putHouse(studentHouse)){ 
			   session.setAttribute(SessionConstants.HOUSE_ADD_SUCCESS, HOUSE_ADD_SUCESS); 
		   }else{
			   session.setAttribute(SessionConstants.HOUSE_ADD_ERROR, HOUSE_ADD_ERROR);
		   }
		   
		   }else{
 			  session.setAttribute(SessionConstants.HOUSE_ADD_ERROR,ERROR_STUDENT_INACTIVE ); 
 		  }
		
	   session.setAttribute(SessionConstants.HOUSE_PARAM, paramHash);   
	   }
       response.sendRedirect("addHouse.jsp");  
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
private static final long serialVersionUID = 5136422492539459144L;
}
