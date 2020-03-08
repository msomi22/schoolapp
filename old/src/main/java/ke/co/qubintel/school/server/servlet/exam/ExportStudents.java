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
package ke.co.qubintel.school.server.servlet.exam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.persistence.student.StudentSubjectDAO;
import ke.co.qubintel.school.server.session.SessionConstants;

/** 
 * http://localhost:8080/school/school/exportStudents?accountId=b83e9b89-0d52-4191-a6bf-acf501267e2e1&streamId=4DA86139-6A72-4089-8858-6A3A613FDFE6&subjectId=D0F7EC32-EA25-7D32-8708-2CC132446
 * 
 * @author peter
 *
 */
public class ExportStudents  extends HttpServlet{

	private static final long serialVersionUID = 3896751907947782599L;

	private static StudentDAO studentDAO;
	private static StreamDAO streamDAO;
	private static SysConfigDAO sysConfigDAO;
	private static AccountDAO accountDAO;
	private static StudentSubjectDAO studentSubjectDAO;

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

		studentDAO = StudentDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		studentSubjectDAO = StudentSubjectDAO.getInstance();

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
		
		String accountId = StringUtils.trimToEmpty(request.getParameter("accountId"));
		String streamId = StringUtils.trimToEmpty(request.getParameter("streamId"));
		String subjectId = StringUtils.trimToEmpty(request.getParameter("subjectId"));

		Account school = new Account();

		school = accountDAO.getAccountById(accountId); 

		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId); 

		excelName = streamDAO.getStream(accountId, streamId).getDescription();

		response.setHeader("Content-Disposition","attachment; filename="+excelName+".xlsx"); 

		createExcelSheets(sysConfig,school,streamId,subjectId);
	}



	/**
	 * Returns MS Excel file of the data specified for exporting.
	 * @param school 
	 * @param List<IncomingLog>
	 * Method create excelSheets and sends them
	 ****/    
	public void createExcelSheets(SysConfig sysConfig, Account school, String streamId, String subjectId) throws IOException{    	

		XSSFWorkbook xf = new XSSFWorkbook();
		XSSFCreationHelper ch =xf.getCreationHelper();

		XSSFSheet s =xf.createSheet();
		s.setColumnWidth(0, 1900); //RegNo
		s.setColumnWidth(1, 3700); //Firstname
		s.setColumnWidth(2, 3700); //Middlename
		s.setColumnWidth(3, 3700); //Lastname
		s.setColumnWidth(4, 1500); //cell
		s.setColumnWidth(5, 1500); //cell
		s.setColumnWidth(6, 1500); //cell

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

		XSSFCell cell1 = r1.createCell(0);
		cell1.setCellValue(ch.createRichTextString("RegNo")); 
		cell1.setCellStyle(style2);

		XSSFCell cell2 = r1.createCell(1);
		cell2.setCellValue(ch.createRichTextString("Firstname"));
		cell2.setCellStyle(style2);

		XSSFCell cell3 = r1.createCell(2);
		cell3.setCellValue(ch.createRichTextString("Midlename")); 
		cell3.setCellStyle(style2);
		
		XSSFCell cell4 = r1.createCell(3);
		cell4.setCellValue(ch.createRichTextString("Lastname")); 
		cell4.setCellStyle(style2);

		XSSFCell cell5 = r1.createCell(4);
		cell5.setCellValue(ch.createRichTextString("Score"));
		cell5.setCellStyle(style2);

		XSSFCell cell6 = r1.createCell(5);
		cell6.setCellValue(ch.createRichTextString("Score"));
		cell6.setCellStyle(style2);

		XSSFCell cell7 = r1.createCell(6);
		cell7.setCellValue(ch.createRichTextString("Score")); 
		cell7.setCellStyle(style2);



		//TODO user stream API
		//List<Student> selectedStudent = Collections.synchronizedList(new ArrayList<Student>()); 
		List<Student> selectedStudents = new ArrayList<>();		
		List<Student> students = new ArrayList<>();


		if(studentDAO.getStudentByStream(school.getUuid(), streamId) != null){
			students = studentDAO.getStudentByStream(school.getUuid(), streamId);
		}


		students
		.parallelStream()
		.filter(student -> StringUtils.equals(student.getIsActive(), "1"))
		.filter(student -> studentSubjectDAO.getstudentSubject(student.getUuid(), subjectId) != null) 
		.forEach(student -> {   

			selectedStudents.add(student);

		});


		/**
		 * Java provides Comparable interface which should be implemented by any custom class if we want to use 
		 * Arrays or Collections sorting methods.
		 * Comparable interface has compareTo(T obj) method which is used by sorting methods
		 * We should override this method in such a way that it returns a negative integer, zero, or a positive integer 
		 * if “this” object is less than, equal to, or greater than the object passed as argument.
		 * 
		 * Read more on Java Comparator
		 * 
		 * Comparator interface compare(Object o1, Object o2) method need to be implemented that takes two Object argument, 
		 * it should be implemented in such a way that it returns negative int if first argument is less than the second one and 
		 * returns zero if they are equal and positive int if first argument is greater than second one.
		 * 
		 * Read more here ( https://www.journaldev.com/780/comparable-and-comparator-in-java-example ) 
		 */
		Collections.sort(selectedStudents); 

		AtomicInteger count = new AtomicInteger();
		
		//TODO use stream API
		selectedStudents/*.parallelStream()*/.forEach(stu -> { 
			
			count.getAndIncrement();
			//count.incrementAndGet();
			int i = count.get();
			//System.out.println("____________________ count" + i);  
			
			XSSFRow r = s.createRow(i);
			
			XSSFCell c1 = r.createCell(0);
			c1.setCellValue(stu.getRegNo());
			
			XSSFCell c2 = r.createCell(1);        	
			c2.setCellValue(stu.getFirstname());
			
			XSSFCell c3 = r.createCell(2);        	
			c3.setCellValue(stu.getMiddlename());
			
			XSSFCell c4 = r.createCell(3);        	
			c4.setCellValue(stu.getLastname());  
			
			XSSFCell c5 = r.createCell(4);        	
			c5.setCellValue(ch.createRichTextString(""));
			
			XSSFCell c6 = r.createCell(5);        	
			c6.setCellValue(ch.createRichTextString(""));
			
			XSSFCell c7 = r.createCell(6);        	
			c7.setCellValue(ch.createRichTextString(""));
			
			


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

}
