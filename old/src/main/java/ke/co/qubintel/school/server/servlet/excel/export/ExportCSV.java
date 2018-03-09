/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.servlet.excel.export;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.classroom.ClassRoom;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.bean.subject.Subject;
import ke.co.qubintel.school.server.cache.CacheVariables;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;
import ke.co.qubintel.school.server.session.SessionConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class ExportCSV extends HttpServlet{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 9101560430546019648L;
	private static StudentDAO studentDAO;
     private static StreamDAO streamDAO;
     private static SubjectDAO subjectDAO;
     private static SysConfigDAO sysConfigDAO;
     
	 private Cache schoolaccountCache;
	 private Logger logger;	
	 SysConfig sysConfig;
	 
	 private static final int BYTES_DOWNLOAD = 1024;
	 private String subjectCode = "";
	 private String classCode = "";
	 private String examCode = "";
	 
	 String classroomuuidToken = "";
	 String subjectuuidToken = "";
	 
	 
	 String term = "";//1,2,3
	 String year = "";//2016
	 String examcode = "";//C1,C2....
	 String examMood = "";//ON/OFF
	 
	 String schoolusername = "";
	 String stffID = "";
	 
	 String content = "";
	 
	 String studentAdmno = "";
	 
	 HashMap<String, String> roomHash = new HashMap<String, String>();
	 HashMap<String, String> subjectCodeHash = new HashMap<String, String>();
	   
	 /**
	 *
	 * @param config
	 * @throws ServletException
	 */
	 public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    logger = Logger.getLogger(this.getClass());
	    CacheManager mgr = CacheManager.getInstance();
	    schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
	    subjectDAO = SubjectDAO.getInstance();
	    studentDAO = StudentDAO.getInstance();
	    streamDAO = StreamDAO.getInstance();
	    sysConfigDAO = SysConfigDAO.getInstance();
	 }
	 
	 /**
	    *
	    * @param request
	    * @param response
	    * @throws ServletException, IOException
	    */
	   @Override
	   protected void doPost(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, IOException {
		   
		  
		   response.setContentType("text/csv");
		   
		  
		   classroomuuidToken = StringUtils.trimToEmpty(request.getParameter("classroomuuidToken"));
		   subjectuuidToken = StringUtils.trimToEmpty(request.getParameter("subjectuuidToken"));
		   //System.out.println("classroomuuidToken="+classroomuuidToken);
		   
	       Account school = new Account();
		   HttpSession session = request.getSession(false);
		   
		   if(session !=null){
		   schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
		   stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
		   
		      }
		   net.sf.ehcache.Element element;
		   
		   element = schoolaccountCache.get(schoolusername);
		   if(element !=null){
		   school = (Account) element.getObjectValue();
	 
		   }
		   
		   //sysConfig = sysConfigDAO.getExamConfig(school.getUuid());
		   
		   List<Student> studentList = new ArrayList<>();
		   

		  
	     subjectCode = subjectCodeHash.get(subjectuuidToken).replaceAll(" ", "_"); 
	 	 classCode = roomHash.get(classroomuuidToken).replaceAll(" ", "_");  
	 	 //examCode = sysConfig.getExam();
		   
		 response.setHeader("Content-Disposition","attachment; filename="+subjectCode+"."+classCode+"."+examCode+".csv");
		   
		   try {
			   
		   OutputStream os = response.getOutputStream();
		   for(Student s : studentList){
			  // studentAdmno = s.getAdmno();
			   content = studentAdmno+",\n"; 
			  
		            InputStream input = new ByteArrayInputStream(content.getBytes("UTF8")); 
		            int read = 0;
			        byte[] bytes = new byte[BYTES_DOWNLOAD];
			        
		            while ((read = input.read(bytes)) != -1) {
		                os.write(bytes, 0, read);
		            }
		            os.flush();
		            
		           
		       
		   }
		         os.close();
		    
		   } catch (Exception e) {
			   logger.error("Exception when getting exporting csv marksheet: ");
	           logger.error(ExceptionUtils.getStackTrace(e));
	        }
		 
	 
	   }
   
	   /**
	    *
	    * @param request
	    * @param response
	    * @throws ServletException, IOException
	    */
	   @Override
	   protected void doGet(HttpServletRequest request, HttpServletResponse response)
	         throws ServletException, IOException {
	        doPost(request, response);
	   }
}
