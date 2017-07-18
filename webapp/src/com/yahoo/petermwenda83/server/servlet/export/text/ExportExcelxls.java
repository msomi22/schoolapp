package com.yahoo.petermwenda83.server.servlet.export.text;

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

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class ExportExcelxls extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9040785856601966875L;
	private static StudentDAO studentDAO;
	private static RoomDAO roomDAO;
	private static SubjectDAO subjectDAO;
	private static ExamConfigDAO examConfigDAO;
	private static StudentSubjectDAO studentSubjectDAO;

	private Cache schoolaccountCache;
	private ServletOutputStream out;
	private Logger logger;	
	ExamConfig examConfig;

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
				if(StringUtils.equals(s.getStatusUuid(), STATUS_ACTIVE)){
					if(studentSubjectDAO.getsubject(s.getUuid(), subjectuuidToken) !=null){
						studentAdmno = s.getAdmno();

						XSSFRow r = sheet.createRow(i);

						XSSFCell c2 = r.createCell(0);        	
						c2.setCellValue(ch.createRichTextString(studentAdmno));

						XSSFCell c3 = r.createCell(1);

						c3.setCellValue(ch.createRichTextString(" "));   


						i++;
					}
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
