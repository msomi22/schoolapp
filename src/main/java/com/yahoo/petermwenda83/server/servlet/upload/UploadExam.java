/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.upload;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamEgineDAO;
import com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author peter   
 * 
 */
public class UploadExam extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3828305434810949182L;
	public final static String UPLOAD_FEEDBACK = "UploadFeedback";
	public final static String UPLOAD_SUCCESS = "You have successfully uploaded your results.";
	
	public String USER = "";
	public String UPLOAD_DIR = "";
	
	private Cache schoolCache;
	
	private Logger logger;
	String staffUsername;
	String schooluuid = "";
	//private UploadUtil uploadUtil;
	private ExcelUtil excelUtil;
	
	private static StudentDAO studentDAO;
	private static ExamEgineDAO examEgineDAO;
	private static RoomDAO roomDAO;
	private static SubjectDAO subjectDAO;
	private static TeacherSubClassDAO teacherSubClassDAO;
	private static ExamConfigDAO examConfigDAO;
	
	String classuuid = "";
	String room = "";
	
	
	final String FORM1 = "FORM 1";
	final String FORM2 = "FORM 2";
	final String FORM3 = "FORM 3";
	final String FORM4 = "FORM 4";
	/**
    *
    * @param config
    * @throws ServletException
    */
 
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       
       // Create a factory for disk-based file items
       DiskFileItemFactory factory = new DiskFileItemFactory();

       File repository = FileUtils.getTempDirectory();
       factory.setRepository(repository);
       
       USER = System.getProperty("user.name");
       UPLOAD_DIR =  "/home/"+USER+"/school/exams"; 
       
     //  uploadUtil = new UploadUtil();
       excelUtil = new ExcelUtil();
       CacheManager mgr = CacheManager.getInstance();
       schoolCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
       studentDAO = StudentDAO.getInstance();
       examEgineDAO = ExamEgineDAO.getInstance();
       roomDAO = RoomDAO.getInstance();
       subjectDAO = SubjectDAO.getInstance();
       teacherSubClassDAO = TeacherSubClassDAO.getInstance();
       examConfigDAO = ExamConfigDAO.getInstance();
       
   }
	
	
	/**
    * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 	*/
	
	@Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		File uploadedFile = null;
		HttpSession session = request.getSession(false);
		

		String schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
		staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
		String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
		  SchoolAccount school = new SchoolAccount();
	       Element element;
	       if ((element = schoolCache.get(schoolusername)) != null) {
	    	   school = (SchoolAccount) element.getObjectValue();
	    	   schooluuid = school.getUuid();
	        }
		 
		// Create a factory for disk-based file items
       DiskFileItemFactory factory = new DiskFileItemFactory();

       // Set up where the files will be stored on disk
       File repository = new File(UPLOAD_DIR + File.separator + "user");
       FileUtils.forceMkdir(repository); 
       factory.setRepository(repository);
       
       // Create a new file upload handler
       ServletFileUpload upload = new ServletFileUpload(factory);
       // Parse the request    
       
       try {
    	
		List<FileItem> items = upload.parseRequest(request);
		Iterator<FileItem> iter = items.iterator();
		
		FileItem item;
		
		while (iter.hasNext()) {
		    item = iter.next();
		   // System.out.println("My Item = "+item+"\n");
		    if (!item.isFormField()) {
		    	if(item!=null){
		    		uploadedFile = processUploadedFile(item);
		    		 //System.out.println("uploadedFile="+uploadedFile+"\n");
		    		
		    		 String feedback = "";
		    	       
		    	       if(uploadedFile !=null){
		    	    	   feedback = excelUtil.inspectResultFile(uploadedFile,schooluuid,stffID,roomDAO, subjectDAO,teacherSubClassDAO,studentDAO);
		    	       }
		    	      
		    	      // System.out.println("Feedback = "+feedback+"\n");
		    	       
		    		   session.setAttribute(UPLOAD_FEEDBACK,"<p class='error'>"+feedback+"<p>");
		    		   
		    		    // Process the file into the database if it is ok
		    	       if(StringUtils.equals(feedback, UPLOAD_SUCCESS)) {
		    	    	   if(uploadedFile !=null){
		    	    		excelUtil.saveResults(uploadedFile, stffID, school, examEgineDAO, studentDAO, roomDAO, subjectDAO, examConfigDAO); 
		    	    	   }
		    	         }
		    	      
			    	
		    	}	
		    } 
		}// end 'while (iter.hasNext())'
    
	    } catch (FileUploadException e) {
	    	logger.error("FileUploadException while getting File Items.");
			logger.error(e);
	   } 
	 
      
	   response.sendRedirect("examUpload.jsp");
       return;
	   
	}
	

	
	/**
	 * @param item
	 * @return the file handle
	 */
	private File processUploadedFile(FileItem item) {
		// A specific folder in the system
		String folder = UPLOAD_DIR + File.separator +staffUsername;
		File file = null;
		
        try {
			FileUtils.forceMkdir(new File(folder));
			file = new File(folder + File.separator + item.getName());
			item.write(file); 
			
		} catch (IOException e) {
			logger.error("IOException while processUploadedFile: " + item.getName());
			logger.error(e);
			
		} catch (Exception e) {
			logger.error("Exception while processUploadedFile: " + item.getName());
			logger.error(e);
		} 
        //System.out.println("Saved File = "+file+"\n");
        return file;
	}
}
