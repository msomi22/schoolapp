/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.CategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubCategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.servlet.util.Timeit;
import com.yahoo.petermwenda83.util.performance.comparator.MeanComparator;
import com.yahoo.petermwenda83.util.performance.comparator.PerformanceComparator;
import com.yahoo.petermwenda83.util.performance.comparator.PointsComparator;

/**   http://localhost:8080/school/school/testPerformance
 * 
 * 
 * @author peter
 *
 */
public class TestPerformance extends HttpServlet{

	private static GradingSystemDAO gradingSystemDAO;
	private static SubCategoryDAO subCategoryDAO;
	private static PerfomanceDAO perfomanceDAO;
	private static CategoryDAO categoryDAO;
	private static SubjectDAO subjectDAO;
	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;

	private Font timesRomanNarmal8 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private Font timesRomanNarmal6 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

	private Document document;
	private PdfWriter writer;

	private Logger logger;

	private static final String[] exams = { "D50E6399-B913-42F2-A5B6-F0D4BAAF9571", "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A",
	"16C4BF00-941C-40E4-9891-272D5F0979A1" };

	private boolean hidePoints = false;
	private boolean hideGrade = false;
	private boolean rankWithPoints = false;
	private boolean rankWithTotalMarks = true;
	
	
	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		gradingSystemDAO = GradingSystemDAO.getInstance();
		subCategoryDAO = SubCategoryDAO.getInstance();
		perfomanceDAO = PerfomanceDAO.getInstance();
		categoryDAO = CategoryDAO.getInstance(); 
		subjectDAO = SubjectDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();

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

		//BaseColor baseColorWhite = new BaseColor(255,255,255);//while
		BaseColor baseColor = new BaseColor(117,229,210);//#75e5d2
		//BaseColor baseColorShadow = new BaseColor(0,255,119);//#00FF77




		try {

			document.open();


			Account account = accountDAO.getAccountById(accountId);

			List<Student> studentsList = studentDAO.getStudentByStream(accountId, streamId);

			if(exams.length == 3){



				String school = "P.O Box : " + account.getAddress() + " " + account.getTown()+" "
						+ " , Cell : " + account.getMobile() + "\n"
						+ "Website : " + account .getWebsite() + "             EMAIL : " + account.getEmail(); 
				

				//TODO rankWithPoints,rankWithTotalMarks

				List<Test3Object> performanceList = getStudentScore(accountId, streamId, term, year, studentsList);

				if(rankWithPoints && !rankWithTotalMarks){
					Collections.sort(performanceList, new PointsComparator());
					Collections.reverse(performanceList);
				}
				
				if(!rankWithPoints && rankWithTotalMarks){
					Collections.sort(performanceList, new MeanComparator());
					Collections.reverse(performanceList);
				}
				

				int position = 1;
				int prevposition = 1;
				double total = 0;
				double prevtotal =0;
				String pos = "";

				for(Test3Object test3Object : performanceList){


					int mainPoint = test3Object.getTotalPoint();

					total = mainPoint;

					if(total == prevtotal){
						pos = String.valueOf(position-prevposition++);
					}else{
						prevposition = 1;
						pos =  String.valueOf(position);
					}


					document.add(new Paragraph( account.getName().toUpperCase(),timesRomanNarmal8));  

					document.add(new Paragraph(school, timesRomanNarmal6));
					document.add(new Paragraph("__________________________________________________________________________")); 


					document.add(new Paragraph(" STUDENT END OF TERM REPORT CARD", timesRomanNarmal8));

					Student student = studentDAO.getStudentById(accountId, test3Object.getStudentId()); 

					String currentClass = "4 N";

					String studentName = student.getFirstname() + " " + student.getMiddlename() + " " + student.getLastname();

					PdfPTable frontContentTable = new PdfPTable(2);  
					frontContentTable.setWidthPercentage(100); 
					frontContentTable.setWidths(new int[]{140,140}); 

					Phrase studentPhrase = new Phrase();
					studentPhrase.add(new Chunk("Student Name : ",  timesRomanNarmal8));
					studentPhrase.add(new Chunk(studentName,  timesRomanNarmal6));
					studentPhrase.add(new Chunk("\n"));

					studentPhrase.add(new Chunk("Reg No             : ",  timesRomanNarmal8));
					studentPhrase.add(new Chunk(student.getRegNo(),  timesRomanNarmal6));
					studentPhrase.add(new Chunk("\n"));

					studentPhrase.add(new Chunk("Form                : ",  timesRomanNarmal8));
					studentPhrase.add(new Chunk(currentClass,  timesRomanNarmal6));
					studentPhrase.add(new Chunk("\n"));

					studentPhrase.add(new Chunk("Grade               : ",  timesRomanNarmal8));
					studentPhrase.add(new Chunk(mainPoint + " /84 (" + getGradeMain(mainPoint,accountId) + ")",  timesRomanNarmal6));
					studentPhrase.add(new Chunk("\n"));


					Phrase termPhrase = new Phrase();
					termPhrase.add(new Chunk("Term                   : ",  timesRomanNarmal8));
					termPhrase.add(new Chunk(term,  timesRomanNarmal6));
					termPhrase.add(new Chunk("\n"));

					termPhrase.add(new Chunk("Year                    : ",  timesRomanNarmal8));
					termPhrase.add(new Chunk(year,  timesRomanNarmal6));
					termPhrase.add(new Chunk("\n"));

					termPhrase.add(new Chunk("Overall position : ",  timesRomanNarmal8));
					termPhrase.add(new Chunk(" "+ pos,  timesRomanNarmal6));
					termPhrase.add(new Chunk("                Out of : ",  timesRomanNarmal8));
					termPhrase.add(new Chunk(" "+performanceList.size(),  timesRomanNarmal6)); 
					termPhrase.add(new Chunk("\n"));

					termPhrase.add(new Chunk("Stream position : ",  timesRomanNarmal8));
					termPhrase.add(new Chunk(" ",  timesRomanNarmal6));
					termPhrase.add(new Chunk("                   Out of : ",  timesRomanNarmal8));
					termPhrase.add(new Chunk(" ",  timesRomanNarmal6)); 
					termPhrase.add(new Chunk("\n"));

					Phrase outofPhrase = new Phrase();
					outofPhrase.add(new Chunk("\n\n"));

					PdfPCell cellOne = new PdfPCell(studentPhrase);
					PdfPCell cellTwo = new PdfPCell(termPhrase);


					cellOne.setBorder(Rectangle.NO_BORDER);
					cellTwo.setBorder(Rectangle.NO_BORDER);


					frontContentTable.addCell(cellOne);
					frontContentTable.addCell(cellTwo);


					document.add(frontContentTable);
					document.add(new Paragraph("\n"));




					PdfPTable examTable = new PdfPTable(7);  
					examTable.setWidthPercentage(100); 
					examTable.setWidths(new int[]{25,12,12,12,12,15,12}); 
					examTable.setHeaderRows(1); 
					examTable.isSkipFirstHeader();

					PdfPCell jubjectCell = new PdfPCell(new Paragraph("Subject",timesRomanNarmal8));
					jubjectCell.setBackgroundColor(baseColor);
					jubjectCell.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell examCell1 = new PdfPCell(new Paragraph("Exam 1",timesRomanNarmal8));
					examCell1.setBackgroundColor(baseColor);
					examCell1.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell examCell2 = new PdfPCell(new Paragraph("Exam 2",timesRomanNarmal8));
					examCell2.setBackgroundColor(baseColor);
					examCell2.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell examCell3 = new PdfPCell(new Paragraph("Exam 3",timesRomanNarmal8));
					examCell3.setBackgroundColor(baseColor);
					examCell3.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell averageCell = new PdfPCell(new Paragraph("Average",timesRomanNarmal8));
					averageCell.setBackgroundColor(baseColor);
					averageCell.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell remarksCell = new PdfPCell(new Paragraph("Remarks",timesRomanNarmal8));
					remarksCell.setBackgroundColor(baseColor);
					remarksCell.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell initialsCell = new PdfPCell(new Paragraph("Initials",timesRomanNarmal8));
					initialsCell.setBackgroundColor(baseColor);
					initialsCell.setHorizontalAlignment(Element.ALIGN_LEFT);


					examTable.addCell(jubjectCell);
					examTable.addCell(examCell1);
					examTable.addCell(examCell2);
					examTable.addCell(examCell3);
					examTable.addCell(averageCell);
					examTable.addCell(remarksCell);
					examTable.addCell(initialsCell);

					Map<String,Integer> exam1 = test3Object.getExam1();
					Map<String,Integer> exam2 = test3Object.getExam2();
					Map<String,Integer> exam3 = test3Object.getExam3(); 



					//getSubjectById(accountId, e1.getSubjectId()).getDescription();
					List<Subject> subjects = subjectDAO.getSubjects(accountId);


					subjects.forEach(subject -> {


						String exam1Score = String.valueOf(exam1.get(subject.getUuid()));
						String exam2Score = String.valueOf(exam2.get(subject.getUuid()));
						String exam3Score = String.valueOf(exam3.get(subject.getUuid()));

						if(StringUtils.equals(exam1Score, "0") || exam1Score.equalsIgnoreCase("null")){
							exam1Score = "";
						}
						if(StringUtils.equals(exam2Score, "0")|| exam2Score.equalsIgnoreCase("null")){
							exam2Score = "";
						}
						if(StringUtils.equals(exam3Score, "0")|| exam3Score.equalsIgnoreCase("null")){
							exam3Score = "";
						}

						String examAverage = findExamAverage(exam1Score,exam2Score,exam3Score);


						String avgrade = getGrade(examAverage,subject.getUuid(),accountId);
						String avgpoints = String.valueOf(getPoints(examAverage,subject.getUuid(),accountId));

						String remarks = getRemarks(examAverage,subject.getUuid(),accountId); 

						String exam1Grade = getGrade(exam1Score,subject.getUuid(),accountId);
						String exam1Points = String.valueOf(getPoints(exam1Score,subject.getUuid(),accountId));

						if(StringUtils.equals(exam1Points, "0")){
							exam1Points = "";
						}

						String exam2Grade = getGrade(exam2Score,subject.getUuid(),accountId);
						String exam2Points = String.valueOf(getPoints(exam2Score,subject.getUuid(),accountId));

						if(StringUtils.equals(exam2Points, "0")){
							exam2Points = "";
						}

						String exam3Grade = getGrade(exam3Score,subject.getUuid(),accountId);
						String exam3Points = String.valueOf(getPoints(exam3Score,subject.getUuid(),accountId));

						if(StringUtils.equals(exam3Points, "0")){
							exam3Points = "";
						}

						String initials = getInitials(accountId,streamId,subject.getUuid()); 


						examTable.addCell(new Paragraph(subject.getDescription(),timesRomanNarmal6));

						String score1 = exam1Score + " " + exam1Grade +  " " + exam1Points; 
						String score2 = exam2Score + " " + exam2Grade +  " " + exam2Points;
						String score3 = exam3Score + " " + exam3Grade +  " " + exam3Points;
						String average = examAverage + " " + avgrade +  " " + avgpoints;

						if(hidePoints && hideGrade){
							score1 = exam1Score;
							score2 = exam2Score;
							score3 = exam3Score;
							average = examAverage;
						}
						if(hidePoints && !hideGrade){ 
							score1 = exam1Score + " " + exam1Grade;
							score2 = exam2Score + " " + exam2Grade;
							score3 = exam3Score + " " + exam3Grade;
							average = examAverage + " " + avgrade;
						}
						if(hideGrade && !hidePoints){
							score1 = exam1Score +  " " + exam1Points; 
							score2 = exam2Score +  " " + exam2Points;
							score3 = exam3Score +  " " + exam3Points;
							average = examAverage + " " + avgpoints;
						}


						examTable.addCell(new Paragraph(" " + score1,timesRomanNarmal6));
						examTable.addCell(new Paragraph(" " + score2,timesRomanNarmal6));
						examTable.addCell(new Paragraph(" " + score3,timesRomanNarmal6));


						

						examTable.addCell(new Paragraph(" " + average,timesRomanNarmal6));
						examTable.addCell(new Paragraph(" " + remarks,timesRomanNarmal6));
						examTable.addCell(new Paragraph(initials,timesRomanNarmal6));


					});

					String[] headers = { "TOTAL", "MEAN GRADE", "MEAN SCORE", "OUT OF" };
					int count = 0;

					for(String header : headers){

						examTable.addCell(new Paragraph(header,timesRomanNarmal8));

						String exm1 = "0";
						String exm2 = "0";
						String exm3 = "0";
						exm1 = String.valueOf(test3Object.getExam1Total());
						exm2 = String.valueOf(test3Object.getExam2Total());
						exm3 = String.valueOf(test3Object.getExam3Total());

						String ex1Grade = getGrade(exm1,"x",accountId);
						String ex2Grade = getGrade(exm2,"x",accountId);
						String ex3Grade = getGrade(exm3,"x",accountId);

						String exa1Point = String.valueOf(getPoints(exm1,"x",accountId));
						String exa2Point = String.valueOf(getPoints(exm2,"x",accountId));
						String exa3Point = String.valueOf(getPoints(exm3,"x",accountId));

						if(StringUtils.equals(exm1, "0") || StringUtils.equals(exa1Point, "0")){
							exm1 = "";
							exa1Point = "";
						}

						if(StringUtils.equals(exm2, "0") || StringUtils.equals(exa2Point, "0")){
							exm2 = "";
							exa2Point = "";
						}

						if(StringUtils.equals(exm3, "0") || StringUtils.equals(exa3Point, "0")){
							exm3 = "";
							exa3Point = "";
						}


						//TOTAL
						if(count == 0){ 
							examTable.addCell(new Paragraph(" "+exm1 ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" "+exm2 ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" "+exm3 ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" "+mainPoint ,timesRomanNarmal6));
						}
						//MEAN GRADE
						else if(count == 1){ 
							examTable.addCell(new Paragraph(" "+ex1Grade ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" "+ex2Grade ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" "+ex3Grade ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" "+getGradeMain(mainPoint,accountId)  ,timesRomanNarmal6));
						}
						//MEAN SCORE
						else if(count == 2){ 
							examTable.addCell(new Paragraph(" "+exa1Point ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" "+exa2Point ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" "+exa3Point ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" " ,timesRomanNarmal6));
						}
						//OUT OF
						else{ 
							examTable.addCell(new Paragraph(" " ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" " ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" " ,timesRomanNarmal6));
							examTable.addCell(new Paragraph(" " ,timesRomanNarmal6));
						}

						//set other columns to blank
						examTable.addCell(new Paragraph(" " ,timesRomanNarmal6));
						examTable.addCell(new Paragraph(" " ,timesRomanNarmal6));
						count++;

					}


					position++;
					prevtotal=total;

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
	 * @param uuid
	 * @return
	 */
	private String getInitials(String accountId, String streamId, String uuid) {
		return RandomStringUtils.randomAlphabetic(2).toUpperCase();
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


			int totalPoint = totalExam1.getTotalPoits() + totalExam2.getTotalPoits() + totalExam3.getTotalPoits();
			totalPoint = totalPoint / 3;
			
			int totalMeans = totalExam1.getTotalMean() + totalExam2.getTotalMean() + totalExam3.getTotalMean();
			totalMeans = totalMeans / 3;

			Test3Object test3Object = new Test3Object();
			test3Object.setExam1(totalExam1.getPerfomanceMap());
			test3Object.setExam2(totalExam2.getPerfomanceMap());
			test3Object.setExam3(totalExam3.getPerfomanceMap()); 
			test3Object.setStudentId(student.getUuid());
			test3Object.setTotalMean(totalMeans); 
			test3Object.setTotalPoint(totalPoint);
			test3Object.setExam1Total(totalExam1.getTotalPoits());
			test3Object.setExam2Total(totalExam2.getTotalPoits());
			test3Object.setExam3Total(totalExam3.getTotalPoits());

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
		Map<String,Integer> perfomanceMap = new HashMap<>(); 


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

				perfomanceMap.put(perfomance.getSubjectId(), perfomance.getScore());

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
		test3Performance.setPerfomanceMap(perfomanceMap); 
		test3Performance.setTotalMean(getTotalsByTotalPerExam(finalPerfomanceList)); 
		test3Performance.setTotalPoits(getTotalsByPointsPerExam(finalPerfomanceList)); 


		return test3Performance;
	}



	/**
	 * @param perfomanceList
	 * @return
	 */
	public int getTotalsByPointsPerExam(List<Perfomance> perfomanceList){
		int totalPoints = 0;

		for( Perfomance perfomance : perfomanceList ){
			int point = getPoints(String.valueOf(perfomance.getScore()),perfomance.getSubjectId(),perfomance.getAccountId());

			totalPoints += point;
		}

		return totalPoints;
	}
	
	
	/**
	 * @param perfomanceList
	 * @return
	 */
	public int getTotalsByTotalPerExam(List<Perfomance> perfomanceList){
		int totals = 0;

		for( Perfomance perfomance : perfomanceList ){
			totals += perfomance.getScore();
		}

		return totals;
	}



	/**
	 * @param score
	 * @param subjectId
	 * @param accountId
	 * @return
	 */
	public int getPoints(String value, String subjectId, String accountId){

		if(value.length() == 0){
			value = "0";
		}

		int score = Integer.parseInt(value);

		int points = 0;
		String categoryId = "";

		if(subjectDAO.getSubjectById(accountId, subjectId) != null){
			categoryId = subjectDAO.getSubjectById(accountId, subjectId).getCategoryId(); 
		}


		List<GradingSystem> gradingSystemList = new ArrayList<>();

		gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, categoryId);

		if(gradingSystemList.isEmpty()){
			String generalId = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";
			gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, generalId);

		}

		for(GradingSystem gradingSystem : gradingSystemList){

			if(score <= gradingSystem.getUpperLimit() &&  score >= gradingSystem.getLowerLimit()){

				points = gradingSystem.getPoints();

			}

		}


		return points;
	}



	/**
	 * @param score
	 * @param subjectId
	 * @param accountId
	 * @return
	 */
	public String getGrade(String value, String subjectId, String accountId){

		if(value.length() == 0){
			value = "0";
		}

		int score = Integer.parseInt(value);
		String grade = "";
		String categoryId = "";

		if(subjectDAO.getSubjectById(accountId, subjectId) != null){
			categoryId = subjectDAO.getSubjectById(accountId, subjectId).getCategoryId(); 
		}

		List<GradingSystem> gradingSystemList = new ArrayList<>();

		gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, categoryId);

		if(gradingSystemList.isEmpty()){
			String generalId = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";
			gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, generalId);

		}

		for(GradingSystem gradingSystem : gradingSystemList){

			if(score <= gradingSystem.getUpperLimit() &&  score >= gradingSystem.getLowerLimit()){

				grade = gradingSystem.getDescription();

			}

		}


		return grade;
	}


	/**
	 * @param score
	 * @return
	 */
	public String getGradeMain(int mean, String accountId) {

		String grade = "";

		int point = (int) mean / 7; 

		List<GradingSystem> gradingSystemList = new ArrayList<>();

		String generalId = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";
		gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, generalId);

		for(GradingSystem gradingSystem : gradingSystemList){

			if(point == gradingSystem.getPoints()){

				grade = gradingSystem.getDescription();

			}

		}


		return grade;
	}




	/**
	 * @param exam1Score
	 * @param exam2Score
	 * @param exam3Score
	 * @return
	 */
	private String findExamAverage(String exam1Score, String exam2Score, String exam3Score) {

		if(exam1Score.length() == 0){
			exam1Score = "0";
		}

		if(exam2Score.length() == 0){
			exam2Score = "0";
		}

		if(exam3Score.length() == 0){
			exam3Score = "0";
		}

		double sum = Integer.parseInt(exam1Score) + Integer.parseInt(exam2Score) + Integer.parseInt(exam3Score); 
		double mean = Math.ceil(sum/3);

		return String.valueOf((int)mean); 
	}




	/**
	 * @param examAverage
	 * @param uuid
	 * @param accountId
	 * @return
	 */
	private String getRemarks(String examAverage, String uuid, String accountId) {

		String remark = "";

		if(examAverage.length() == 0){
			examAverage = "0";
		}

		int score = Integer.valueOf(examAverage);

		if (score >= 80) {
			remark = "Excellent";
		} else if (score >= 70) {
			remark = "Very good";
		} else if (score >= 60) {
			remark = "Relatively good";
		} else if (score >= 50) {
			remark = "Good";
		} else if (score >= 40) {
			remark = "Fair";
		} else if (score >= 35) {
			remark = "Poor";
		} else if  (score > 0){
			remark = "Very poor";
		}
		return remark;

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
