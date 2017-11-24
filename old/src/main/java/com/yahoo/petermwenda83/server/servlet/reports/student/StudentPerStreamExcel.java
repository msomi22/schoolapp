/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;

/** http://localhost:8080/school/school/studentPerStreamExcel?accountId=b83e9b89-0d52-4191-a6bf-acf501267e2e1&streamId=4DA86139-6A72-4089-8858-6A3A613FDFE6&decisionFlag=0
 * 
 * @author peter
 *
 */
public class StudentPerStreamExcel extends HttpServlet{

	private static StudentDAO studentDAO;
	private static StreamDAO streamDAO;
	private static SysConfigDAO sysConfigDAO;
	private static AccountDAO accountDAO;

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
		String streamId = StringUtils.trim(request.getParameter("uuid"));//class or stream
		String decisionFlag = StringUtils.trim(request.getParameter("decisionFlag"));

		Account school = new Account();

		school = accountDAO.getAccountById(accountId); 

		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId); 

		excelName = streamDAO.getStream(accountId, streamId).getDescription();

		response.setHeader("Content-Disposition","attachment; filename="+excelName+".xlsx"); 

		createExcelSheets(sysConfig,school,streamId,decisionFlag);
	}



	private void createExcelSheets(SysConfig sysConfig, Account school, String streamId, String decisionFlag) throws IOException {




		XSSFWorkbook xf = new XSSFWorkbook();
		XSSFCreationHelper ch =xf.getCreationHelper();

		XSSFSheet s =xf.createSheet();
		s.setColumnWidth(0, 1900); //RegNo
		s.setColumnWidth(1, 3700); //Firstname
		s.setColumnWidth(2, 3700); //Middlename
		s.setColumnWidth(3, 3700); //Lastname

		s.setColumnWidth(4, 3700); //AdmClass
		s.setColumnWidth(5, 3700); //CurrentClass
		s.setColumnWidth(6, 3700); //Gender


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

		XSSFCell cell4 = r1.createCell(2);
		cell4.setCellValue(ch.createRichTextString("Lastname")); 
		cell4.setCellStyle(style2);

		XSSFCell cell5 = r1.createCell(2);
		cell5.setCellValue(ch.createRichTextString("AdmClass")); 
		cell5.setCellStyle(style2);

		XSSFCell cell6 = r1.createCell(2);
		cell6.setCellValue(ch.createRichTextString("CurrentClass")); 
		cell6.setCellStyle(style2);

		XSSFCell cell7 = r1.createCell(2);
		cell7.setCellValue(ch.createRichTextString("Gender")); 
		cell7.setCellStyle(style2);

		
		Map<String,String> streamMap = new ConcurrentHashMap<>();


		streamDAO.getStreamList(school.getUuid()).parallelStream().forEach(stream -> {
			streamMap.put(stream.getUuid(), stream.getDescription());
		});




		if(StringUtils.equals(decisionFlag, "1")) {//class
			
			List<Student>  students_2 = new ArrayList<>();//copy students from each stream into this list

			streamDAO.getStreamList(school.getUuid(), streamDAO.getStream(school.getUuid(), streamId).getClassRoomId()).forEach(stm -> {

				List<Student> activeStudents = studentDAO.getStudentByStream(school.getUuid(), stm.getUuid())
						.parallelStream()
						.filter(student -> "1".equals(student.getIsActive()))
						.collect(Collectors.toList());
				students_2.addAll(activeStudents);

			});
			
			Collections.sort(students_2);  

			AtomicInteger count = new AtomicInteger();
			students_2.forEach(stu -> {

				count.getAndIncrement();
				//count.incrementAndGet();
				int i = count.get();

				String regStream = streamMap.get(stu.getRegStream());
				String currentStream = streamMap.get(stu.getCurrentStream());

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
				c5.setCellValue(regStream);

				XSSFCell c6 = r.createCell(5);        	
				c6.setCellValue(currentStream);

				XSSFCell c7 = r.createCell(6);         	
				c7.setCellValue(stu.getGender().toUpperCase());


			});

			xf.write(out);
			out.flush();          
			out.close();



		}else if(StringUtils.equals(decisionFlag, "0")){//stream 


			List<Student> selectedStudents = new ArrayList<>();		
			List<Student> students = new ArrayList<>();


			if(studentDAO.getStudentByStream(school.getUuid(), streamId) != null){
				students = studentDAO.getStudentByStream(school.getUuid(), streamId);
			}


			students
			.parallelStream()
			.filter(student -> StringUtils.equals(student.getIsActive(), "1"))
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

			//Map<String,Stream> streamMap2 = new HashMap<>();
			/**It allows concurrent modification of the Map from several threads 
			 * 
			 *  -You should use ConcurrentHashMap when you need very high concurrency in your project.
				-It is thread safe without synchronizing the whole map.
				-Reads can happen very fast while write is done with a lock.
				-There is no locking at the object level.
				-The locking is at a much finer granularity at a hashmap bucket level.
				-ConcurrentHashMap doesn’t throw a ConcurrentModificationException if one thread tries to modify 
				    it while another is iterating over it.
				-ConcurrentHashMap uses multitude of locks.
			 * 
			 * **/
			
			AtomicInteger count = new AtomicInteger();
			selectedStudents.forEach(stu -> {

				count.getAndIncrement();
				//count.incrementAndGet();
				int i = count.get();

				String regStream = streamMap.get(stu.getRegStream());
				String currentStream = streamMap.get(stu.getCurrentStream());

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
				c5.setCellValue(regStream);

				XSSFCell c6 = r.createCell(5);        	
				c6.setCellValue(currentStream);

				XSSFCell c7 = r.createCell(6);         	
				c7.setCellValue(stu.getGender().toUpperCase());


			});

			xf.write(out);
			out.flush();          
			out.close();


		}

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
	private static final long serialVersionUID = 4835080146729498795L;

}
