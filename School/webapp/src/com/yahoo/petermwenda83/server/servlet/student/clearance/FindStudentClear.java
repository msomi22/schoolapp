/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.student.clearance;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.money.StudentClearance;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.money.StudentClearanceDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**  
 * @author peter
 *
 */
public class FindStudentClear extends HttpServlet{
	
	final String ERROR_ADM_NO_NOT_FOUND = "The admission number provided does'n exist.";
	final String SUCCESS_STUDENT_FOUND = "A student with the below details was found.";
	
	private static StudentClearanceDAO studentClearanceDAO;
	private static StudentDAO studentDAO;
	private static RoomDAO roomDAO;
	HashMap<String, String> roomHash = new HashMap<String, String>();
	
	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentClearanceDAO = StudentClearanceDAO.getInstance();
       studentDAO = StudentDAO.getInstance();
       roomDAO = RoomDAO.getInstance();
   }
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String admissionNo = StringUtils.trimToEmpty(request.getParameter("admissionNo"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       Map<String, String> studentParamHash = new HashMap<>(); 
       
       if(studentDAO.getStudentObjByadmNo(schooluuid, admissionNo) == null){
		     session.setAttribute(SessionConstants.CLEAR_ERROR, ERROR_ADM_NO_NOT_FOUND); 
		   
	   }else{
		   
		   Student student = new Student();
		   student = studentDAO.getStudentObjByadmNo(schooluuid, admissionNo);
		   
		    SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat("yyyy");
		    
		    List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
			classroomList = roomDAO.getAllRooms(schooluuid); 
			for(ClassRoom c : classroomList){
				roomHash.put(c.getUuid() , c.getRoomName());
			}
		  
	       studentParamHash.put("admnumber", student.getAdmno()); 
	       studentParamHash.put("firstname", StringUtils.capitalize(student.getFirstname().toLowerCase())); 
	       studentParamHash.put("middlename", StringUtils.capitalize(student.getLastname().toLowerCase())); 
	       studentParamHash.put("lastname", StringUtils.capitalize(student.getSurname().toLowerCase()));  
	       studentParamHash.put("admyear", formatter.format(student.getAdmissionDate())); 
	       studentParamHash.put("regterm", student.getRegTerm()); 
	       studentParamHash.put("finalyear", Integer.toString(student.getFinalYear()));  
	       studentParamHash.put("finalterm", Integer.toString(student.getFinalTerm()));   
	       studentParamHash.put("classroom", roomHash.get(student.getClassRoomUuid()));   
	       studentParamHash.put("studentType", student.getStudentType());    
	       studentParamHash.put("studentuuid", student.getUuid());   
	       

		   StudentClearance clearance = new StudentClearance();
		   clearance.setStudentUuid(student.getUuid());  
		   clearance.setTerm(Integer.toString(student.getFinalTerm()));
		   clearance.setYear(Integer.toString(student.getFinalYear())); 
		   clearance.setClearingAmount(0); 
		  
		   
		   studentClearanceDAO.put(clearance, student.getUuid(), Integer.toString(student.getFinalTerm()), Integer.toString(student.getFinalYear()));
			
	       
	       session.setAttribute(SessionConstants.CLEAR_SUCCESS, SUCCESS_STUDENT_FOUND); 
		   
	   }
       session.setAttribute(SessionConstants.CLEAR_PARAM, studentParamHash); 
       response.sendRedirect("studentClearance.jsp");  
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
private static final long serialVersionUID = -2552130104417346738L;

 
}
