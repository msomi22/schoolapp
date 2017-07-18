/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.money.reports;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.money.StudentBalance;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author peter
 *
 */ 
public class PerClassFinanceReport extends HttpServlet{

	final String STATUS_ACTIVE = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";

	ExamConfig examConfig;

	Locale locale = new Locale("en","KE"); 
	NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

	private Cache schoolaccountCache;

	private static StudentFeeDAO studentFeeDAO;
	private static StudentDAO studentDAO;
	private static ExamConfigDAO examConfigDAO;
	private static TermFeeDAO termFeeDAO;
	private static StudentOtherMoniesDAO studentOtherMoniesDAO;
	private StudentBalance studentBal;
	
	private static RoomDAO roomDAO;
	private final String SPREADSHEET_NAME = ".xlsx";
	private static final long serialVersionUID = 3896751907947782599L;
	private static int pageno;

	private String classroomuuid = "";
	String schoolusername = "";
	private ServletOutputStream out;

	String USER= "";
	String path ="";
	String pdfname = "";


	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String> firstnameHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>();
	HashMap<String, String> roomHash = new HashMap<String, String>();
	HashMap<String, String> studentHouseHash = new HashMap<String, String>();
	HashMap<String, String> houseHash = new HashMap<String, String>();



	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		studentFeeDAO = StudentFeeDAO.getInstance();
		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
		studentDAO = StudentDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		roomDAO = RoomDAO.getInstance();
		studentOtherMoniesDAO = StudentOtherMoniesDAO.getInstance();
		studentBal = new StudentBalance();

		USER = System.getProperty("user.name");
		path = "/home/"+USER+"/school/logo/logo.png";



	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		out = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Cache-Control", "cache, must-revalidate");
		response.setHeader("Pragma", "public");

		HttpSession session = request.getSession(false); 


		classroomuuid = StringUtils.trimToEmpty(request.getParameter("classroomuuid"));



		SchoolAccount school = new SchoolAccount();
		String schoolusername = "";

		if(session !=null){
			schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);

		}
		net.sf.ehcache.Element element;

		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (SchoolAccount) element.getObjectValue();
		}

		examConfig = examConfigDAO.getExamConfig(school.getUuid());

		List<Student> studentList = new ArrayList<>();
		studentList = studentDAO.getAllStudents(school.getUuid(), classroomuuid);

		for(Student stu : studentList){

			studentAdmNoHash.put(stu.getUuid(),stu.getAdmno()); 
			String formatedFirstname = StringUtils.capitalize(stu.getFirstname().toLowerCase());
			String formatedLastname = StringUtils.capitalize(stu.getLastname().toLowerCase());
			String formatedsurname = StringUtils.capitalize(stu.getSurname().toLowerCase());
			studNameHash.put(stu.getUuid(),formatedFirstname + " " + formatedLastname + " " + formatedsurname +"\n"); 
			firstnameHash.put(stu.getUuid(), formatedFirstname);
		}


		
		examConfig = new ExamConfig();
		if(examConfigDAO.getExamConfig(school.getUuid()) !=null){
			examConfig = examConfigDAO.getExamConfig(school.getUuid());
		}

		List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
		classroomList = roomDAO.getAllRooms(school.getUuid()); 
		for(ClassRoom c : classroomList){
			roomHash.put(c.getUuid() , c.getRoomName());
		}




		  String fileName = new StringBuffer(StringUtils.trimToEmpty("FeeBal"))
	                .append("_")
	                .append(roomHash.get(classroomuuid))
	                .append(SPREADSHEET_NAME)
	                .toString();

	     response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");     
	          
		createExcelSheets(studentList,school);



	}


	private void createExcelSheets(List<Student> studentList, SchoolAccount school) throws IOException {

		XSSFWorkbook xf = new XSSFWorkbook();
		XSSFCreationHelper ch =xf.getCreationHelper();

		//SN, ADM NO, NAME ,BALANCE
		XSSFSheet s =xf.createSheet();
		s.setColumnWidth(0, 1000); 
		s.setColumnWidth(1, 2500); 
		s.setColumnWidth(2, 5500); 
		s.setColumnWidth(3, 2500); 



		CellStyle style = xf.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		//style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		//style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		CellStyle style2 = xf.createCellStyle();
		style2.setAlignment(CellStyle.ALIGN_LEFT);
		//style2.setFillForegroundColor(IndexedColors.AQUA.getIndex()); 
		//style2.setFillPattern(CellStyle.DIAMONDS); 

		XSSFFont font = xf.createFont();
		font.setFontName(XSSFFont.DEFAULT_FONT_NAME);  
		font.setFontHeightInPoints((short)11);
		//font.setColor(IndexedColors.MAROON.getIndex()); 
		style.setFont(font); 

		XSSFRow r0 = s.createRow(0);
		XSSFCell cell = r0.createCell((short) 0);
		cell.setCellValue(ch.createRichTextString(roomHash.get(classroomuuid)+" Fee Payment Analysis,  TERM " + examConfig.getTerm()+"  "+ examConfig.getYear()));
		cell.setCellStyle(style);

		s.addMergedRegion(new CellRangeAddress(0,0,3,0));//row from,row to,col from, col to

		//create the first row
		XSSFRow r1 = s.createRow(1);
		XSSFCell c11 = r1.createCell(0);
		c11.setCellValue(ch.createRichTextString("S N")); 
		c11.setCellStyle(style2);

		XSSFCell c12 = r1.createCell(1);
		c12.setCellValue(ch.createRichTextString("Adm No"));
		c12.setCellStyle(style2);

		XSSFCell c13 = r1.createCell(2);
		c13.setCellValue(ch.createRichTextString("Student Name"));
		c13.setCellStyle(style2);


		XSSFCell c15 = r1.createCell(3);
		c15.setCellValue(ch.createRichTextString("Balance"));
		c15.setCellStyle(style2);



		int i=2;
		int count = 1;

		String formatedFirstname = "";
		String formatedLastname = "";
		String formatedSurname = "";
		
		//create other rows
		if(studentList != null){
			for(Student stu :studentList){
				if(StringUtils.equals(stu.getStatusUuid(), STATUS_ACTIVE)){
				
				formatedFirstname = StringUtils.capitalize(stu.getFirstname().toLowerCase());
				formatedLastname = StringUtils.capitalize(stu.getLastname().toLowerCase());
				formatedSurname = StringUtils.capitalize(stu.getSurname().toLowerCase());
				
				Locale locale = new Locale("en","KE"); 
				NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
				double balance = 0;
				String feebalance = "";
                balance = studentBal.findBalance(termFeeDAO,examConfigDAO,studentFeeDAO,studentOtherMoniesDAO,stu.getAdmissionDate(),stu.getRegTerm(),stu.getUuid(),school.getUuid(),stu.getFinalYear()); 
                feebalance = nf.format(balance);


				XSSFRow r = s.createRow(i);
				//row number
				XSSFCell c1 = r.createCell(0);
				c1.setCellValue(count+pageno);

				//get adm no 
				XSSFCell c2 = r.createCell(1);        	
				c2.setCellValue(ch.createRichTextString(stu.getAdmno()));

				//get student name
				XSSFCell c3 = r.createCell(2);

				c3.setCellValue(ch.createRichTextString(formatedFirstname+" "+formatedLastname +" "+formatedSurname));        		

				//get class
				XSSFCell c4 = r.createCell(3);
				
				c4.setCellValue(ch.createRichTextString(feebalance));        		
                
				
               
                

				i++;
				count++;      	  
			}
			}
		}
		xf.write(out);
		out.flush();          
		out.close();
	}





	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
