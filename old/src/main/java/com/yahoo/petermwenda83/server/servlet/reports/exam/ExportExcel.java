/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.exam;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;

/**
 * @author peter
 *
 */
public class ExportExcel  extends HttpServlet{
	
	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;
	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static SysConfigDAO sysConfigDAO;
	
	static final String databaseName = "schooldb";
	static final String Host = "localhost";
	static final String databaseUsername = "school";
	static final String databasePassword = "AllaManO1";
	static final int databasePort = 5432;
	
	private ServletOutputStream out;
	private String excelName = "";
	
	static {
		perfomanceDAO = new PerfomanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		subjectDAO = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentDAO = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);       

		perfomanceDAO = PerfomanceDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance(); 

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

		String accountId = StringUtils.trim(request.getParameter("accountId"));
		String streamId = StringUtils.trim(request.getParameter("uuid"));
		String decisionFlag = StringUtils.trim(request.getParameter("decisionFlag"));

		Account school = new Account();

		school = accountDAO.getAccountById(accountId); 

		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId); 

		excelName = "";

		response.setHeader("Content-Disposition","attachment; filename="+excelName+".xlsx"); 

		//createExcelSheets(sysConfig,school,streamId,decisionFlag);
	}


	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		report();
		
	}
	
	
	
	/** TODO
	 * 1. StreamId (Form 1 N)
	 *    ExamId           (Cat 1)
	 * 
	 * 2. StreamId (Form 1 N)
	 *    ExamId           (Cat 1)
	 *    SubjectId        (Eng, Kisw)
	 *    
	 *    Fetch the students for a stream
	 *    fetch performance data for each subject.
	 * 
	 * 
	 */
	
	
	public static void report() {
		
		String accountId = "ca4eab8a-b9f5-428c-b9e6-2cbe0d582f70";
		String streamId = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
		String examId = "D50E6399-B913-42F2-A5B6-F0D4BAAF9571";
		String term = "1";
		String year = "2018";
		
		//****************************
		XSSFWorkbook xf = new XSSFWorkbook();
		XSSFCreationHelper ch =xf.getCreationHelper();

		XSSFSheet s =xf.createSheet();
		s.setColumnWidth(0, 1900); //RegNo
		s.setColumnWidth(1, 3700); //Firstname
		s.setColumnWidth(2, 3700); //Middlename

		s.setColumnWidth(3, 3700);
		s.setColumnWidth(4, 3700); 
		s.setColumnWidth(5, 3700); 
		
		s.setColumnWidth(6, 3700);
		s.setColumnWidth(7, 3700); 
		s.setColumnWidth(8, 3700); 
		
		s.setColumnWidth(9, 3700);
		s.setColumnWidth(10, 3700); 
		s.setColumnWidth(11, 3700); 
		
		s.setColumnWidth(12, 3700);
		s.setColumnWidth(13, 3700); 
		s.setColumnWidth(14, 3700); 
		
		s.setColumnWidth(15, 3700); 


		CellStyle style = xf.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle style2 = xf.createCellStyle();
		style2.setAlignment(CellStyle.ALIGN_LEFT);

		XSSFFont font = xf.createFont();
		font.setFontName(XSSFFont.DEFAULT_FONT_NAME);  
		font.setFontHeightInPoints((short)12);
		style.setFont(font); 

		//create the first row   (14 columns) 
		XSSFRow r1 = s.createRow(0);

		XSSFCell cell1 = r1.createCell(0);
		cell1.setCellValue(ch.createRichTextString("RegNo")); 
		cell1.setCellStyle(style2);

		XSSFCell cell2 = r1.createCell(1);
		cell2.setCellValue(ch.createRichTextString("Firstname"));
		cell2.setCellStyle(style2);

		XSSFCell cell3 = r1.createCell(2);
		cell3.setCellValue(ch.createRichTextString("Midlename")); 
		cell3.setCellStyle(style2);

		
		//**********************************************************
		

		
		studentDAO.getStudentByStream(accountId, streamId).forEach(student -> {
			
			AtomicInteger ai = new AtomicInteger();
			perfomanceDAO.getStreamPerformance(accountId, examId, student.getUuid(), streamId, term, year).forEach(performance -> {
				
				int count = ai.incrementAndGet();
				int count2 = ai.incrementAndGet();
				count += 2;
				
				String sub = subjectDAO.getSubjectById(accountId, performance.getSubjectId()).getCode(); 
				
				
				XSSFCell sub111 = r1.createCell(count);  
				sub111.setCellValue(ch.createRichTextString(sub)); 
				sub111.setCellStyle(style2);
				
				
				
				
			
				
				System.out.println("reg: " +student.getRegNo() + " , sub : " + sub + ", score : " + performance.getScore());
				
				
				
				
				
				
			});
			System.out.println(" -------------------------- ");
			
		});;
		
		
		
		
		
	}
	
	
	
	
	
	
	
	

}
