/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.school.staff;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.staff.TeacherDuties;

/**
 * @author peter
 *
 */
public class AddHouseMaster extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String EMPTY_FIELDS = "Empty fields not allowed";
	final String ASSIGN_SUUCESS = "House assigned successfully";
	final String ASSIGN_ERROR = "Something went wrong while assigning house";
	final String ERROR_HOUSE_ALREADY_ASIGNED = "This house has already been assigned a teacher";
	final String ERROR_TEACHER_HAS_HOUSE = "The teacher has already been assigned another house";
	
	
	//private static TeacherDutiesDAO teacherDutiesDAO;
	TeacherDuties teacherDuty;


	/**    
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       //teacherDutiesDAO = TeacherDutiesDAO.getInstance();
      
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

	  // HttpSession session = request.getSession(true);
	   
       String teacheruuid = StringUtils.trimToEmpty(request.getParameter("teacheruuid"));
       String houseuuid = StringUtils.trimToEmpty(request.getParameter("houseuuid"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
   
       if(StringUtils.isBlank(teacheruuid)){
    	   
       }else if(StringUtils.isBlank(houseuuid)){
    	   
       }else if(StringUtils.isBlank(schooluuid)){
    	   
       }else{
    	   
    	    teacherDuty = new TeacherDuties();
    	   // teacherDuty.setDutyUuid(dutyUuid);
    	    teacherDuty.setTeacherUuid(teacheruuid);
    	   
    	   
    	   
       }
       
       }
   

@Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doPost(request, response);
   }
   

}
