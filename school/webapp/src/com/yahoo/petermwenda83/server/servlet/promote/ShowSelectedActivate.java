package com.yahoo.petermwenda83.server.servlet.promote;

import java.io.IOException;
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

import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.util.LogUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class ShowSelectedActivate extends HttpServlet{
	

	final String ERROR = "We are Sorry, something went wrong , try again later";
	
	final String ACTIVE = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
	final String STATUS_INACTIVE = "6C03705B-E05E-420B-B5B8-C7EE36643E60";

	private static StudentDAO studentDAO;
	List<Student> studentlist = new ArrayList<>();

	/**    
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentDAO = StudentDAO.getInstance();
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

	   HttpSession session = request.getSession(true);
	   
	   String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String currentclassuuid = StringUtils.trimToEmpty(request.getParameter("selectedclassId"));
       Map<String, List<Student>> studentMap = new HashMap<String, List<Student>>();    
       
       if(StringUtils.isBlank(schooluuid)){
     	  session.setAttribute(SessionConstants.STATUS_CHANGE_ERROR, ERROR); 
       }else  if(StringUtils.isBlank(currentclassuuid)){
      	  session.setAttribute(SessionConstants.STATUS_CHANGE_ERROR, ERROR); 
       }else{
    	   LogUtil logUtil = new LogUtil();
    	   studentlist = studentDAO.getAllStudents(schooluuid, currentclassuuid);
    	   List<Student> studentlist2 = new ArrayList<>(); 
    	   for(Student stu : studentlist){
    		   if(StringUtils.equalsIgnoreCase(stu.getStatusUuid(), STATUS_INACTIVE)){ 
    			   studentlist2.add(stu);
    		   }
    	   }
    	   studentMap.put("currentclassId", studentlist2);   
    	   logUtil.writeLog("show selected students " + studentlist2 + " for class " + currentclassuuid); 
    	    
       }
       
       session.setAttribute(SessionConstants.SHOW_SELECTED_STUDENT_MAP, studentMap); 
       response.sendRedirect("activateSelected.jsp");  
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
	private static final long serialVersionUID = 7953834694583708372L;

}
