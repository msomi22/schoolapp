/**
 * 
 */
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

/**
 * @author peter
 *
 */
public class ShowSelected extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1752760431038398523L;

	final String ERROR = "We are Sorry, something went wrong , try again later";
	
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
     	  session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, ERROR); 
       }else  if(StringUtils.isBlank(currentclassuuid)){
      	  session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, ERROR); 
       }else{
    	   LogUtil logUtil = new LogUtil();
    	   studentlist = studentDAO.getAllStudents(schooluuid, currentclassuuid);
    	   studentMap.put("currentclassId", studentlist); 
    	   logUtil.writeLog("show selected students " + studentlist + " for class " + currentclassuuid); 
    	    
       }
       
       session.setAttribute(SessionConstants.SHOW_SELECTED_STUDENT_MAP, studentMap); 
       response.sendRedirect("promoteSelected.jsp");  
	   return;
   }
   
   

   @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          doPost(request, response);
      }

}
