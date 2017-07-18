package com.yahoo.petermwenda83.server.servlet.export.text;

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

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class ExportText extends HttpServlet{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -4685338967135208468L;
	private static StudentDAO studentDAO;
     private static RoomDAO roomDAO;
     private static SubjectDAO subjectDAO;
     private static ExamConfigDAO examConfigDAO;
     
	 private Cache schoolaccountCache;
	 private Logger logger;	
	 ExamConfig examConfig;
	 
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
	    roomDAO = RoomDAO.getInstance();
	    examConfigDAO = ExamConfigDAO.getInstance();
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
		 
		   
		   response.setContentType("text/plain");
		   
		  
		   classroomuuidToken = StringUtils.trimToEmpty(request.getParameter("classroomuuidToken"));
		   subjectuuidToken = StringUtils.trimToEmpty(request.getParameter("subjectuuidToken"));
		   //System.out.println("classroomuuidToken="+classroomuuidToken);
		   
	       SchoolAccount school = new SchoolAccount();
		   HttpSession session = request.getSession(false);
		   
		   if(session !=null){
		   schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
		   stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
		   
		      }
		   net.sf.ehcache.Element element;
		   
		   element = schoolaccountCache.get(schoolusername);
		   if(element !=null){
		   school = (SchoolAccount) element.getObjectValue();
	 
		   }
		   
		   examConfig = examConfigDAO.getExamConfig(school.getUuid());
		   
		   List<Student> studentList = new ArrayList<>();
		   studentList = studentDAO.getAllStudents(school.getUuid(), classroomuuidToken);
		   
		   List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
	         classroomList = roomDAO.getAllRooms(school.getUuid()); 
	          for(ClassRoom c : classroomList){
	             roomHash.put(c.getUuid() , c.getRoomName());
	        }
	          
	      List<Subject> subjectList = new ArrayList<Subject>(); 
	      subjectList = subjectDAO.getAllSubjects(); 
	      for(Subject s : subjectList){
	         subjectCodeHash.put(s.getUuid() , s.getSubjectCode());
	      }    

		  
	     subjectCode = subjectCodeHash.get(subjectuuidToken).replaceAll(" ", "_"); 
	 	 classCode = roomHash.get(classroomuuidToken).replaceAll(" ", "_");  
	 	 examCode = examConfig.getExam();
		   
		 response.setHeader("Content-Disposition","attachment; filename="+subjectCode+"."+classCode+"."+examCode+".txt");
		   
		   try {
			   
		   OutputStream os = response.getOutputStream();
		   for(Student s : studentList){
			   studentAdmno = s.getAdmno();
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
			   logger.error("Exception when getting exporting text marksheet: ");
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
