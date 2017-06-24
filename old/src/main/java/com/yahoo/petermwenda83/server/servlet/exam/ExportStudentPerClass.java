/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.exam;

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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/** 
 * @author peter
 *
 */
public class ExportStudentPerClass  extends HttpServlet{

	private static final long serialVersionUID = 3896751907947782599L;
	
	HashMap<String, String> roomHash = new HashMap<String, String>();
	private Cache schoolaccountCache;
	SysConfig sysConfig;


	private static StudentDAO studentDAO;
	private static StreamDAO streamDAO;
	private static SysConfigDAO sysConfigDAO;

	private String classroomuuid = "";
	String schoolusername = "";
	private ServletOutputStream out;
	
	private String classCode = "";
	private String examCode = "";


	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);       
		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);

		studentDAO = StudentDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		
	}

	/**    
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 * @throws java.io.IOException
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		out = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Cache-Control", "cache, must-revalidate");
		response.setHeader("Pragma", "public");

		HttpSession session = request.getSession(false); 

		classroomuuid = StringUtils.trimToEmpty(request.getParameter("classroomuuid"));

		Account school = new Account();

		if(session !=null){
			schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);

		}
		net.sf.ehcache.Element element;

		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (Account) element.getObjectValue();

		}

		/*sysConfig = sysConfigDAO.getExamConfig(school.getUuid());


		List<Student> studentList = new ArrayList<>();
		studentList = studentDAO.getAllStudents(school.getUuid(), classroomuuid);

		List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
		classroomList = streamDAO.getAllRooms(school.getUuid()); 
		for(ClassRoom c : classroomList){
			roomHash.put(c.getUuid() , c.getRoomName());
		}
         
		classCode = roomHash.get(classroomuuid).replaceAll(" ", "_");  
		examCode = sysConfig.getExam();*/

		response.setHeader("Content-Disposition","attachment; filename="+classCode+"."+examCode+".xlsx");
		
		//createExcelSheets(studentList,school);
	}



	/**
	 * Returns MS Excel file of the data specified for exporting.
	 * @param school 
	 * @param List<IncomingLog>
	 * Method create excelSheets and sends them
	 ****/    
	public void createExcelSheets(List<Student>studentList, Account school) throws IOException{    	

		XSSFWorkbook xf = new XSSFWorkbook();
		XSSFCreationHelper ch =xf.getCreationHelper();

		XSSFSheet s =xf.createSheet();
		s.setColumnWidth(0, 4000); 
		s.setColumnWidth(1, 1700); 
		s.setColumnWidth(2, 1500); 
		s.setColumnWidth(3, 1500); 
		s.setColumnWidth(4, 1500); 
		s.setColumnWidth(5, 1500); 
		s.setColumnWidth(6, 1500); 
		s.setColumnWidth(7, 1500); 
		s.setColumnWidth(8, 1500); 
		s.setColumnWidth(9, 1500); 
		s.setColumnWidth(10,1500);
		s.setColumnWidth(11,1500);
		s.setColumnWidth(12,1500);
		s.setColumnWidth(13,1500);
		s.setColumnWidth(14,1500);

		CellStyle style = xf.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle style2 = xf.createCellStyle();
		style2.setAlignment(CellStyle.ALIGN_LEFT);

		XSSFFont font = xf.createFont();
		font.setFontName(XSSFFont.DEFAULT_FONT_NAME);  
		font.setFontHeightInPoints((short)12);
		style.setFont(font); 

		//create the first row
		XSSFRow r1 = s.createRow(0);
		
		XSSFCell c00 = r1.createCell(0);
		c00.setCellValue(ch.createRichTextString("NAME")); 
		c00.setCellStyle(style2);

		XSSFCell c01 = r1.createCell(1);
		c01.setCellValue(ch.createRichTextString("ADMNO"));
		c01.setCellStyle(style2);
		
		//LOOP SUBJECTS
	         String outof = "";
	         String exam = "";
	        // exam = sysConfig.getExam();
	         if(StringUtils.equalsIgnoreCase(exam, "C1")){
	        	 outof = "30";
	         }else if(StringUtils.equalsIgnoreCase(exam, "C2")){
	        	 outof = "30";
	         }else if(StringUtils.equalsIgnoreCase(exam, "ET")){
	        	 outof = "70";
	         }else if(StringUtils.equalsIgnoreCase(exam, "P1")){
	        	 outof = "80";
	         }else if(StringUtils.equalsIgnoreCase(exam, "P2")){
	        	 outof = "80";
	         }else if(StringUtils.equalsIgnoreCase(exam, "P3")){
	        	 outof = "40"; 
	         }

			//languages		
			XSSFCell c02 = r1.createCell(2);
			c02.setCellValue(ch.createRichTextString("ENG"+"/"+outof)); 
			c02.setCellStyle(style2);

			XSSFCell c03 = r1.createCell(3);
			c03.setCellValue(ch.createRichTextString("KIS"+"/"+outof));
			c03.setCellStyle(style2);

			XSSFCell c04 = r1.createCell(4);
			c04.setCellValue(ch.createRichTextString("MAT"+"/"+outof));
			c04.setCellStyle(style2);
			
			//sciences    
			XSSFCell c05 = r1.createCell(5);
			c05.setCellValue(ch.createRichTextString("PHY"+"/"+outof));
			c05.setCellStyle(style2);

			XSSFCell c06 = r1.createCell(6);
			c06.setCellValue(ch.createRichTextString("BIO"+"/"+outof));
			c06.setCellStyle(style2);
			
			XSSFCell c07 = r1.createCell(7);
			c07.setCellValue(ch.createRichTextString("CHE"+"/"+outof));
			c07.setCellStyle(style2);
			
			//technical
			
			XSSFCell c08 = r1.createCell(8);
			c08.setCellValue(ch.createRichTextString("BS"+"/"+outof));
			c08.setCellStyle(style2);

			XSSFCell c09 = r1.createCell(9);
			c09.setCellValue(ch.createRichTextString("AGR"+"/"+outof));
			c09.setCellStyle(style2);
		
			XSSFCell c010 = r1.createCell(10);
			c010.setCellValue(ch.createRichTextString("COM"+"/"+outof));
			c010.setCellStyle(style2);
			
			XSSFCell c011 = r1.createCell(11);
			c011.setCellValue(ch.createRichTextString("HSC"+"/"+outof));
			c011.setCellStyle(style2);
			
            //humanities
			XSSFCell c012 = r1.createCell(12);
			c012.setCellValue(ch.createRichTextString("GEO"+"/"+outof));
			c012.setCellStyle(style2);
	
			XSSFCell c013 = r1.createCell(13);
			c013.setCellValue(ch.createRichTextString("CRE"+"/"+outof));
			c013.setCellStyle(style2);
			
			XSSFCell c014 = r1.createCell(14);
			c014.setCellValue(ch.createRichTextString("HIS"+"/"+outof));
			c014.setCellStyle(style2);
			



			

		//END LOOP SUBJECT

		int i=1;
		
		String formatedFirstname = "";
		String formatedLastname = "";
		//create other rows
		if(studentList != null){
			for(Student stu :studentList){
				final String STATUS_ACTIVE = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
				if(StringUtils.equals("", STATUS_ACTIVE)){
					
					formatedFirstname =  StringUtils.capitalize(stu.getFirstname().toLowerCase());
					formatedLastname = StringUtils.capitalize(stu.getLastname().toLowerCase());
					
					XSSFRow r = s.createRow(i);
					//row number
					XSSFCell c1 = r.createCell(0);
					c1.setCellValue(formatedFirstname + " " + formatedLastname);

					//get message  
					XSSFCell c2 = r.createCell(1);        	
					c2.setCellValue(ch.createRichTextString(""));
                    //languages
					XSSFCell c3 = r.createCell(2);
					c3.setCellValue(" ");        		   	     

					XSSFCell c4 = r.createCell(3);
					c4.setCellValue(" ");
 
					XSSFCell c5 = r.createCell(4);
					c5.setCellValue(" "); 
                    //sciences
					XSSFCell c6 = r.createCell(5);    
					c6.setCellValue(" ");

					XSSFCell c7 = r.createCell(6); 
					c7.setCellValue(" "); 

					XSSFCell c8 = r.createCell(7); 
					c8.setCellValue(" "); 
                    //technical
					XSSFCell c9 = r.createCell(8); 
					c9.setCellValue(" "); 

					XSSFCell c10 = r.createCell(9); 
					c10.setCellValue(" "); 

					XSSFCell c11 = r.createCell(10); 
					c11.setCellValue(" ");
					
					XSSFCell c12 = r.createCell(11); 
					c12.setCellValue(" ");
					//humanity
					XSSFCell c13 = r.createCell(12); 
					c13.setCellValue(" ");
					
					XSSFCell c14 = r.createCell(13); 
					c14.setCellValue(" ");
					
					XSSFCell c15 = r.createCell(14); 
					c15.setCellValue(" ");



					i++;   	  
				}//end if status
			}//end for
		}//end if
		xf.write(out);
		out.flush();          
		out.close();
	}





	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 * @throws java.io.IOException
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
