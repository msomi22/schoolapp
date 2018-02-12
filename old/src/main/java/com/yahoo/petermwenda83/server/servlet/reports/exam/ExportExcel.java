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
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;

/**
 * 
 * /school/exportExcel
 * 
 * exportExcel
 * 
 * @author peter
 *
 */
public class ExportExcel  extends HttpServlet{

	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;
	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static SysConfigDAO sysConfigDAO;

	private ServletOutputStream out;
	private String excelName = "";


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
		String streamId = StringUtils.trim(request.getParameter("streamId"));
		String examId = StringUtils.trim(request.getParameter("examId"));

		accountId = "af2e7af5-113b-408d-82e2-fc2cb815b49a";
		streamId = "126385be-53e6-45e0-ad13-9264a1d2b3cd";
		examId = "D50E6399-B913-42F2-A5B6-F0D4BAAF9571";

		Account school = new Account();

		school = accountDAO.getAccountById(accountId); 

		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId); 

		excelName = "test";

		response.setHeader("Content-Disposition","attachment; filename="+excelName+".xlsx"); 

		createExcelSheets(sysConfig,school,streamId,examId);
	}





	/** 
	 * @param examId
	 * @param streamId
	 * @param school 
	 * @param sysConfig 
	 * @throws IOException 
	 *
	 */


	public void createExcelSheets(SysConfig sysConfig, Account school, String streamId, String examId) throws IOException { 

		String term = sysConfig.getTerm();
		String year = sysConfig.getYear();

		//****************************
		XSSFWorkbook xf = new XSSFWorkbook();
		XSSFCreationHelper ch =xf.getCreationHelper();

		XSSFSheet s =xf.createSheet();
		s.setColumnWidth(0, 1900); //RegNo
		s.setColumnWidth(1, 3700); //Firstname
		s.setColumnWidth(2, 3700); //Middlename

		s.setColumnWidth(3, 1900);
		s.setColumnWidth(4, 1900); 
		s.setColumnWidth(5, 1900); 

		s.setColumnWidth(6, 1900);
		s.setColumnWidth(7, 1900); 
		s.setColumnWidth(8, 1900); 

		s.setColumnWidth(9, 1900);
		s.setColumnWidth(10, 1900); 
		s.setColumnWidth(11, 1900); 

		s.setColumnWidth(12, 1900);
		s.setColumnWidth(13, 1900); 
		s.setColumnWidth(14, 1900); 

		s.setColumnWidth(15, 1900); 


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

		int c = 3;
		for(Subject subject : subjectDAO.getSubjects(school.getUuid())){
			XSSFCell cell = r1.createCell(c); 
			cell.setCellValue(ch.createRichTextString(subject.getCode())); 
			cell.setCellStyle(style2);
			c++;
		}


		AtomicInteger count = new AtomicInteger();

		studentDAO.getStudentByStream(school.getUuid(), streamId).forEach(student -> {

			count.getAndIncrement();
			int i = count.get();

			XSSFRow r = s.createRow(i);

			XSSFCell c1 = r.createCell(0);
			c1.setCellValue(student.getRegNo());

			XSSFCell c2 = r.createCell(1);        	
			c2.setCellValue(student.getFirstname());

			XSSFCell c3 = r.createCell(2);         	
			c3.setCellValue(student.getMiddlename());


			AtomicInteger subc = new AtomicInteger();
			subjectDAO.getSubjects(school.getUuid()).forEach(subject -> {

				subc.getAndIncrement();
				int sc = subc.get();

				perfomanceDAO.getPerformanceList(school.getUuid(), examId, student.getUuid(), streamId, subject.getUuid(), term, year).forEach(performance -> {
					XSSFCell c4 = r.createCell(sc+2);        	
					c4.setCellValue(performance.getScore());

				});
			});
		
		});

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
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2163317809910216680L;

}
