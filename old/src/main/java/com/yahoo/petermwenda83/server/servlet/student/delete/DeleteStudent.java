/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.student.delete;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/** 
 * @author peter
 *
 */
public class DeleteStudent extends HttpServlet{
	
	final String ERROR_STUDENT_NOT_DELETED = "Something went wrong while deleting student's exam data.";
	final String SUCCESS_STUDENT_DELETED = "The student was successfully deleted form exam register.";
	
	private static SysConfigDAO sysConfigDAO;
	private static PerfomanceDAO perfomanceDAO;
	SysConfig sysConfig;



	/** 
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       sysConfigDAO = SysConfigDAO.getInstance();
       perfomanceDAO = PerfomanceDAO.getInstance();
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String studentuuid = StringUtils.trimToEmpty(request.getParameter("studentuuid"));
      
       
       if(StringUtils.isBlank(schooluuid)){
		     session.setAttribute(SessionConstants.STUENT_DELETE_ERROR, ERROR_STUDENT_NOT_DELETED); 
		   
	   }else if(StringUtils.isBlank(studentuuid)){ 
		   session.setAttribute(SessionConstants.STUENT_DELETE_ERROR, ERROR_STUDENT_NOT_DELETED); 
		   
	   }else{
       
       sysConfig = new SysConfig();
		if(sysConfigDAO.getExamConfig(schooluuid) !=null){
			sysConfig = sysConfigDAO.getExamConfig(schooluuid);
		}
		
		Perfomance perfomance = new Perfomance();
		perfomance.setSchoolAccountUuid(schooluuid);
		perfomance.setStudentUuid(studentuuid); 
		perfomance.setTerm(sysConfig.getTerm()); 
		perfomance.setYear(sysConfig.getYear());
		
		if(perfomanceDAO.deletePerfomance(perfomance)){
			 session.setAttribute(SessionConstants.STUENT_DELETE_SUCCESS, SUCCESS_STUDENT_DELETED);
		}else{
			 session.setAttribute(SessionConstants.STUENT_DELETE_ERROR, ERROR_STUDENT_NOT_DELETED);
		}
		 
       
	   }
       
       response.sendRedirect("sysConfig.jsp");  
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
	private static final long serialVersionUID = 5814423157166989629L;

}
