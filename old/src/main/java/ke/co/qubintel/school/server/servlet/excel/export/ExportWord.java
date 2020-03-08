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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.cache.CacheVariables;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.session.SessionConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class ExportWord extends HttpServlet{
	
	 private static StudentDAO studentDAO;
      
	 private Logger logger;	
	 String schoolusername = "";
	 String stffID = "";
	 String classroomuuidToken = "";
	 
	 private Cache schoolaccountCache;
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
		 studentDAO = StudentDAO.getInstance();
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
		   
		   ServletContext context = getServletContext();
		   response.setContentType("application/msword");
		   response.setHeader("Cache-Control", "cache, must-revalidate");
	       response.setHeader("Pragma", "public");
	                
		   response.setHeader("Content-Disposition","attachment; filename =myWord.doc");
		   
		   classroomuuidToken = StringUtils.trimToEmpty(request.getParameter("classroomuuid"));
	 
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
		   
		   List<Student> studentList = new ArrayList<>();
		  // studentList = studentDAO.getAllStudents(school.getUuid(), classroomuuidToken);
		  
		   XWPFDocument document = new XWPFDocument(); 
		   PrintWriter out = response.getWriter();
		   
		   XWPFParagraph Paragraph = document.createParagraph(); 
		   Paragraph.setAlignment(ParagraphAlignment.RIGHT);
		   XWPFRun Run1 = Paragraph.createRun(); 
		   Run1.setText("Doc title should be here \n\n");
		   Run1.setFontSize(24); 
		   Run1.setBold(true);
		   
		   
		   Paragraph=document.createParagraph();
		   Paragraph.setAlignment(ParagraphAlignment.CENTER);
		   XWPFRun Run2 = Paragraph.createRun(); 
		   Run2.setText("When the question is speed and efficiency,"
		   		+ " a typical high school would consider a dependable"
		   		+ " software in running the school. That why many high"
		   		+ " schools in Kenya have said yes to this software product \n\n");
		   Run2.setFontSize(18);  
		   
		   out.println(Run1); 
		   out.println(Run2); 
		  
			
			out.close();


		   
		   
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
