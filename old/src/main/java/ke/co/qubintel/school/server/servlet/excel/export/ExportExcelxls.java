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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.classroom.ClassRoom;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.bean.subject.Subject;
import ke.co.qubintel.school.server.cache.CacheVariables;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.persistence.student.StudentSubjectDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;
import ke.co.qubintel.school.server.session.SessionConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class ExportExcelxls extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9040785856601966875L;
	private static StudentDAO studentDAO;
	private static StreamDAO streamDAO;
	private static SubjectDAO subjectDAO;
	private static SysConfigDAO sysConfigDAO;
	private static StudentSubjectDAO studentSubjectDAO;

	private Cache schoolaccountCache;
	private ServletOutputStream out;
	private Logger logger;	
	SysConfig sysConfig;

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
		studentSubjectDAO = StudentSubjectDAO.getInstance();
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

		out = response.getOutputStream();

		response.setContentType("application/vnd.ms-excel");


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

		
		List<Student> studentList = new ArrayList<>();
		

		subjectCode = subjectCodeHash.get(subjectuuidToken).replaceAll(" ", "_"); 
		classCode = roomHash.get(classroomuuidToken).replaceAll(" ", "_");  
		//examCode = sysConfig.getExam();

		response.setHeader("Content-Disposition","attachment; filename="+subjectCode+"."+classCode+"."+examCode+".xls");

		XSSFWorkbook  xf = new XSSFWorkbook();
		String outof = "";
		try {



			XSSFCreationHelper ch =xf.getCreationHelper();
			XSSFSheet sheet =xf.createSheet();
			sheet.setColumnWidth(0, 2500); 
			sheet.setColumnWidth(1, 2000); 

			XSSFRow r1 = sheet.createRow(0);

			XSSFCell c11 = r1.createCell(0);
			c11.setCellValue(ch.createRichTextString("Adm No")); 

			if(StringUtils.equalsIgnoreCase(examCode, "c1")){
				outof ="-30";
			}else if(StringUtils.equalsIgnoreCase(examCode, "c2")){
				outof ="-30";
			}else if(StringUtils.equalsIgnoreCase(examCode, "et")){
				outof ="-70";
			}else{
				outof ="-100";
			}


			XSSFCell c12 = r1.createCell(1);
			c12.setCellValue(ch.createRichTextString("OutOf"+outof));


			int i=1;
			for(Student s : studentList){
				final String STATUS_ACTIVE = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
				if(StringUtils.equals("", STATUS_ACTIVE)){
					
				}
			}
			xf.write(out);
			out.flush();          
			out.close(); 

		} catch (Exception e) {
			logger.error("Exception when getting exporting excel marksheet: ");
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
