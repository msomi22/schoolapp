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
package ke.co.qubintel.school.server.servlet.reports.exam;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.subject.Subject;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.PerfomanceDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;

/**
 *
 * http://localhost:8080/school/school/exportExcel
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
	private static StreamDAO streamDAO;

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

		String accountId = StringUtils.trim(request.getParameter("accountId"));
		String streamId = StringUtils.trim(request.getParameter("streamId"));
		String examId = StringUtils.trim(request.getParameter("examId"));

		/*accountId = "af2e7af5-113b-408d-82e2-fc2cb815b49a";
		streamId = "126385be-53e6-45e0-ad13-9264a1d2b3cd";
		examId = "D50E6399-B913-42F2-A5B6-F0D4BAAF9571";*/

		Account school = new Account();

		school = accountDAO.getAccountById(accountId); 

		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);  
		excelName = streamDAO.getStream(accountId, streamId).getDescription(); 

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
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFCreationHelper ch = workbook.getCreationHelper();

		XSSFSheet sheet = workbook.createSheet();
		sheet.setColumnWidth(0, 1900); //RegNo
		sheet.setColumnWidth(1, 3700); //Firstname
		sheet.setColumnWidth(2, 3700); //Middlename
		sheet.setColumnWidth(3, 1900);
		sheet.setColumnWidth(4, 1900); 
		sheet.setColumnWidth(5, 1900); 
		sheet.setColumnWidth(6, 1900);
		sheet.setColumnWidth(7, 1900); 
		sheet.setColumnWidth(8, 1900); 
		sheet.setColumnWidth(9, 1900);
		sheet.setColumnWidth(10, 1900); 
		sheet.setColumnWidth(11, 1900); 
		sheet.setColumnWidth(12, 1900);
		sheet.setColumnWidth(13, 1900); 
		sheet.setColumnWidth(14, 1900); 
		sheet.setColumnWidth(15, 1900); 


		CellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(CellStyle.ALIGN_LEFT);

		XSSFFont font = workbook.createFont();
		font.setFontName(XSSFFont.DEFAULT_FONT_NAME);  
		font.setFontHeightInPoints((short)12);
		style.setFont(font); 

		XSSFRow row0 = sheet.createRow(0);
		XSSFCell cell0 = row0.createCell((short) 0);
		cell0.setCellValue(ch.createRichTextString(school.getName()+" : "+excelName+" ,  TERM " + sysConfig.getTerm()+"  "+ sysConfig.getYear()));
		cell0.setCellStyle(style);

		int rowFrom = 0, rowTo = 0, colFrom = 0, colTo = 15; 
		sheet.addMergedRegion(new CellRangeAddress(rowFrom,rowTo,colFrom,colTo)); 
		
		XSSFRow row1 = sheet.createRow(1); 

		XSSFCell cell1 = row1.createCell(0);
		cell1.setCellValue(ch.createRichTextString("RegNo")); 
		cell1.setCellStyle(style2);

		XSSFCell cell2 = row1.createCell(1);
		cell2.setCellValue(ch.createRichTextString("Firstname"));
		cell2.setCellStyle(style2);

		XSSFCell cell3 = row1.createCell(2);
		cell3.setCellValue(ch.createRichTextString("Midlename")); 
		cell3.setCellStyle(style2);

		int c = 3;
		for(Subject subject : subjectDAO.getSubjects(school.getUuid())){
			XSSFCell cell = row1.createCell(c); 
			cell.setCellValue(ch.createRichTextString(subject.getCode())); 
			cell.setCellStyle(style2);
			c++;
		}


		AtomicInteger count = new AtomicInteger();

		studentDAO.getStudentByStream(school.getUuid(), streamId).forEach(student -> {

			count.getAndIncrement();
			int i = count.get();

			XSSFRow r = sheet.createRow(i+1);  

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

		workbook.write(out);
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
