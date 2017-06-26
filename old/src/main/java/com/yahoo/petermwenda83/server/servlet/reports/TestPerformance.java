/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.CategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubCategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.servlet.util.Timeit;
import com.yahoo.petermwenda83.util.performance.comparator.PerformanceComparator;
import com.yahoo.petermwenda83.util.performance.comparator.Test3ObjectComparator;

/**   http://localhost:8080/school/school/testPerformance
 * 
 * 
 * @author peter
 *
 */
public class TestPerformance extends HttpServlet{


	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;
	private static SubCategoryDAO subCategoryDAO;
	private static CategoryDAO categoryDAO;
	private static StudentDAO studentDAO;
	private static ExamDAO examDAO;


	private Font timesRomanNarmal8 = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
	private Font timesRomanNormal7 = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
	private Font timesRomanNormal0 = new Font(Font.FontFamily.TIMES_ROMAN,10, Font.NORMAL);
	private Font timesRomanNarmal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);

	private Document document;
	private PdfWriter writer;

	private String PDF_SUBTITLE ="";
	private Logger logger;

	private static final String[] exams = { "D50E6399-B913-42F2-A5B6-F0D4BAAF9571", "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A",
	"16C4BF00-941C-40E4-9891-272D5F0979A1" };

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
		subCategoryDAO = SubCategoryDAO.getInstance();
		categoryDAO = CategoryDAO.getInstance(); 
		studentDAO = StudentDAO.getInstance();
		examDAO = ExamDAO.getInstance();

		logger = Logger.getLogger(this.getClass());
	}


	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException,
	 *             IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		response.setContentType("application/pdf");

		String accountId = StringUtils.trimToEmpty(request.getParameter("accountId"));

		System.out.println("******************************************************************8"); 
		System.out.println("accountId " + accountId); 

		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4, 46, 46, 64, 64);


		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();



			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(accountId);


		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}






	}

	/**
	 * @param args
	 */
	public void populatePDFDocument(String accountId) {
		Timeit.code(() -> compute());
	}

	/**
	 * @param args
	 */
	public  void compute() {

		String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		String streamId = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
		String term = "1";
		String year = "2016";

		BaseColor baseColorWhite = new BaseColor(255,255,255);//while
		BaseColor baseColor = new BaseColor(117,229,210);//#75e5d2
		BaseColor baseColorShadow = new BaseColor(0,255,119);//#00FF77




		try {

			document.open();

			document.add(new Paragraph("..................."));


			List<Student> studentsList = studentDAO.getStudentByStream(accountId, streamId);

			if(exams.length == 3){
				
				


				List<Test3Object> performanceList = getStudentScore(accountId, streamId, term, year, studentsList);

				Collections.sort(performanceList, new Test3ObjectComparator());
				Collections.reverse(performanceList);

				
				for(Test3Object test3Object : performanceList){
					
					

					PdfPTable examTable = new PdfPTable(3);  
					examTable.setWidthPercentage(100); 
					examTable.setWidths(new int[]{100,100,100}); 

					PdfPCell examCell1 = new PdfPCell(new Paragraph("Exam 1",timesRomanNarmal8));
					examCell1.setBackgroundColor(baseColor);
					examCell1.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell examCell2 = new PdfPCell(new Paragraph("Exam 2",timesRomanNarmal8));
					examCell2.setBackgroundColor(baseColor);
					examCell2.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell examCell3 = new PdfPCell(new Paragraph("Exam 3",timesRomanNarmal8));
					examCell3.setBackgroundColor(baseColor);
					examCell3.setHorizontalAlignment(Element.ALIGN_LEFT);


					examTable.addCell(examCell1);
					examTable.addCell(examCell2);
					examTable.addCell(examCell3);
					
					
					
					
					
					PdfPTable exam1Table = new PdfPTable(4);  
					exam1Table.setWidthPercentage(100); 
					exam1Table.setWidths(new int[]{100,100,100,100}); 
					
					PdfPTable exam2Table = new PdfPTable(4);  
					exam2Table.setWidthPercentage(100); 
					exam2Table.setWidths(new int[]{100,100,100,100}); 
					
					PdfPTable exam3Table = new PdfPTable(4);  
					exam3Table.setWidthPercentage(100); 
					exam3Table.setWidths(new int[]{100,100,100,100}); 
					
					PdfPCell subjectCell = new PdfPCell(new Paragraph("Subject",timesRomanNarmal8));
					subjectCell.setBackgroundColor(baseColor);
					subjectCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					PdfPCell scoreCell = new PdfPCell(new Paragraph("Score",timesRomanNarmal8));
					scoreCell.setBackgroundColor(baseColor);
					scoreCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					PdfPCell pointCell = new PdfPCell(new Paragraph("Score",timesRomanNarmal8));
					pointCell.setBackgroundColor(baseColor);
					pointCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					PdfPCell gradeCell = new PdfPCell(new Paragraph("Grade",timesRomanNarmal8));
					gradeCell.setBackgroundColor(baseColor);
					gradeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					exam1Table.addCell(subjectCell);
					exam1Table.addCell(scoreCell);
					exam1Table.addCell(pointCell);
					exam1Table.addCell(gradeCell);
					
					exam2Table.addCell(subjectCell);
					exam2Table.addCell(scoreCell);
					exam2Table.addCell(pointCell);
					exam2Table.addCell(gradeCell);
					
					exam3Table.addCell(subjectCell);
					exam3Table.addCell(scoreCell);
					exam3Table.addCell(pointCell);
					exam3Table.addCell(gradeCell);

					
					List<Perfomance> exam1 = test3Object.getExam1();
					List<Perfomance> exam2 = test3Object.getExam2();
					List<Perfomance> exam3 = test3Object.getExam3(); 


					exam1.forEach(e1 -> {
						
						String subject = subjectDAO.getSubjectById(accountId, e1.getSubjectId()).getDescription();

						exam1Table.addCell(new Paragraph(subject,timesRomanNarmal6));
						exam1Table.addCell(new Paragraph(""+e1.getScore(),timesRomanNarmal6));
						exam1Table.addCell(new Paragraph("points  ",timesRomanNarmal6));
						exam1Table.addCell(new Paragraph("grade  ",timesRomanNarmal6));
						//examTable.addCell(exam1Table); 
						

						
					});

					
					exam2.forEach(e2 -> {


						String subject = subjectDAO.getSubjectById(accountId, e2.getSubjectId()).getDescription();

						exam2Table.addCell(new Paragraph(subject,timesRomanNarmal6));
						exam2Table.addCell(new Paragraph(""+e2.getScore(),timesRomanNarmal6));
						exam2Table.addCell(new Paragraph("points  ",timesRomanNarmal6));
						exam2Table.addCell(new Paragraph("grade  ",timesRomanNarmal6));
						//examTable.addCell(exam2Table); 
						

					});

					exam3.forEach(e3 -> {
						

						String subject = subjectDAO.getSubjectById(accountId, e3.getSubjectId()).getDescription();

						exam3Table.addCell(new Paragraph(subject,timesRomanNarmal6));
						exam3Table.addCell(new Paragraph(""+e3.getScore(),timesRomanNarmal6));
						exam3Table.addCell(new Paragraph("points  ",timesRomanNarmal6));
						exam3Table.addCell(new Paragraph("grade  ",timesRomanNarmal6));
						//examTable.addCell(exam3Table); 
						

					});

					
					
					
					examTable.addCell(exam1Table); 
					examTable.addCell(exam2Table); 
					examTable.addCell(exam3Table); 
					document.add(examTable);
					
					document.newPage();
					
				}


				
				
				


			}



			document.close();

		}catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}  


	}


	/**
	 * @param accountId
	 * @param streamId
	 * @param term
	 * @param year
	 * @param studentsList
	 */
	private  List<Test3Object> getStudentScore(String accountId, String streamId, String term, String year,
			List<Student> studentsList) {

		List<Test3Object> test3ObjectList = new ArrayList<>();
		List<Perfomance> exam1;
		List<Perfomance> exam2;
		List<Perfomance> exam3;

		for(Student student : studentsList ){

			exam1 = perfomanceDAO.getStreamPerformance(accountId, exams[0], student.getUuid(), streamId, term, year);
			exam2 = perfomanceDAO.getStreamPerformance(accountId, exams[1], student.getUuid(), streamId, term, year);
			exam3 = perfomanceDAO.getStreamPerformance(accountId, exams[2], student.getUuid(), streamId, term, year); 

			Test3Performance totalExam1 = null;
			Test3Performance totalExam2 = null;
			Test3Performance totalExam3 = null;
			totalExam1 = findExamTotal(accountId, exam1);
			totalExam2 = findExamTotal(accountId, exam2);
			totalExam3 = findExamTotal(accountId, exam3);


			int totals = totalExam1.getTotal() + totalExam2.getTotal() + totalExam3.getTotal();

			Test3Object test3Object = new Test3Object();
			test3Object.setExam1(totalExam1.getPerfomanceList());
			test3Object.setExam2(totalExam2.getPerfomanceList());
			test3Object.setExam3(totalExam3.getPerfomanceList());
			test3Object.setStudentId(student.getUuid());
			test3Object.setTotalScore(totals); 

			test3ObjectList.add(test3Object);

		}

		return test3ObjectList;
	}


	/**
	 * @param accountId
	 * @param exam1
	 */
	private  Test3Performance findExamTotal(String accountId, List<Perfomance> exam1) {

		List<Perfomance> finalPerfomanceList = new ArrayList<>();
		List<Perfomance> perfomanceList = new ArrayList<>();


		if(!exam1.isEmpty()){

			int languagesCount = 0;
			int sciencesCount = 0;
			int humanitiesCount = 0;

			List<Perfomance> removedSubjectsPerfomanceList = new ArrayList<>();
			List<Perfomance> selectedLanguagesList = new ArrayList<>();
			List<Perfomance> selectedSciencesList = new ArrayList<>();
			List<Perfomance> selectedHumanitiesList = new ArrayList<>();
			List<Perfomance> selectedTechnicalsList = new ArrayList<>();



			for (Perfomance perfomance : exam1) {

				perfomanceList.add(perfomance);


				String catId = subCategoryDAO.getSubCategory(accountId, perfomance.getSubjectId()).getCategoryId();
				String desc = categoryDAO.getCategoryById(accountId, catId).getDescription();

				if (StringUtils.equalsIgnoreCase(desc, "Languages")) {
					selectedLanguagesList.add(perfomance);
					languagesCount++;


					if (languagesCount > 2) {
						Collections.sort(selectedLanguagesList, new PerformanceComparator());
						removedSubjectsPerfomanceList.add(selectedLanguagesList.remove(0));

					}
				}

				if (StringUtils.equalsIgnoreCase(desc, "Sciences")) {
					selectedSciencesList.add(perfomance);
					sciencesCount++;

					if (sciencesCount > 2) {
						Collections.sort(selectedSciencesList, new PerformanceComparator());	
						selectedTechnicalsList.add(selectedSciencesList.remove(0));

					}
				}

				if (StringUtils.equalsIgnoreCase(desc, "Humanities")) {
					selectedHumanitiesList.add(perfomance);
					humanitiesCount++;

					if (humanitiesCount > 1) {
						Collections.sort(selectedHumanitiesList, new PerformanceComparator());
						selectedTechnicalsList.add(selectedHumanitiesList.remove(0));

					}
				}

				if (StringUtils.equalsIgnoreCase(desc, "Technicals")) {
					selectedTechnicalsList.add(perfomance);


				}

				if (StringUtils.equalsIgnoreCase(desc, "Mathematics")) {
					finalPerfomanceList.add(perfomance);


				}

			}

			//some code here
			Collections.sort(selectedTechnicalsList, new PerformanceComparator());

			if(selectedTechnicalsList.size() > 0){
				Perfomance highestTechnical = selectedTechnicalsList.remove(selectedTechnicalsList.size()-1);
				finalPerfomanceList.add(highestTechnical);

			}

			finalPerfomanceList.addAll(selectedLanguagesList);
			finalPerfomanceList.addAll(selectedSciencesList);
			finalPerfomanceList.addAll(selectedHumanitiesList);

		}

		Test3Performance test3Performance = new Test3Performance();
		test3Performance.setPerfomanceList(perfomanceList); 
		test3Performance.setTotal(getTotalsPerExam(finalPerfomanceList)); 


		return test3Performance;
	}



	/**
	 * @param perfomanceList
	 * @return
	 */
	public int getTotalsPerExam(List<Perfomance> perfomanceList){
		int totalPoints = 0;

		for( Perfomance perfomance : perfomanceList ){
			totalPoints += perfomance.getScore();
		}

		return totalPoints;
	}




	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException,
	 *             IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5356561358431988192L;

}
