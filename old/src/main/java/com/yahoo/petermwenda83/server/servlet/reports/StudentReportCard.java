/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.renderer.category.GanttRenderer;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
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
import com.yahoo.petermwenda83.util.performance.comparator.PointsComparator;

/**   http://localhost:8080/school/school/studentReportCard
 * 
 * 
 * @author peter
 *
 */
public class StudentReportCard extends HttpServlet{

	private static GradingSystemDAO gradingSystemDAO;
	private static SubCategoryDAO subCategoryDAO;
	private static PerfomanceDAO perfomanceDAO;
	private static CategoryDAO categoryDAO;
	private static SubjectDAO subjectDAO;
	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;

	private Font timesRomanNarmal8 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private Font timesRomanNarmal6 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	private Font timesRomanNarmal4 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);

	private Document document;
	private PdfWriter writer;

	private Logger logger;

	//private String[] exams= {"4531A31D-1F8A-40D7-BFE6-D3CB3D91951A,34C4244E-5CE0-4D5D-AD85-60E97FDDD80A,AE24F15B-5038-4A15-8607-1DB2A7A0B7DE"} ;
	// , "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A", "16C4BF00-941C-40E4-9891-272D5F0979A1" 

	private String[] exams = {"D50E6399-B913-42F2-A5B6-F0D4BAAF9571", "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A" ,"16C4BF00-941C-40E4-9891-272D5F0979A1"};
	
	//, "16C4BF00-941C-40E4-9891-272D5F0979A1"
	private boolean hidePoints = false;
	private boolean hideGrade = false;
	
	private boolean rankWithPoints = false;
	private boolean rankWithTotalMarks = true;
	
	private boolean showFeeInfo = false;
	
	private boolean grade7subjects = true;
	private boolean grade11subjects = false;


	private static final String USER_SYSTEM = System.getProperty("user.name");
	private static final String LOGO_PATH = "/home/"+USER_SYSTEM+"/school/logo/logo.png";
	
	
	private String accountId;
	private String streamId;
	private String term;
	private String year;
	private String classroomId;


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
		
		//check submitted states
	boolean hidePts=false,hideGds=false;
		
		hidePts = Boolean.parseBoolean(request.getParameter("p"));
		hideGds= Boolean.parseBoolean(request.getParameter("g"));
		
		String rank = request.getParameter("rank");
	
		Boolean showfee = Boolean.parseBoolean(request.getParameter("fee"));
		
		String noOfSub = request.getParameter("subjects");
		
		//check for hide points
		if(hidePts)
			hidePoints= true;
		else
			hidePoints= false;
		
		//check for hide grades
		if(hideGds)
			hideGrade= true;
		else
			hideGrade= false;
		
		//check for rank with points
		if(StringUtils.equalsIgnoreCase(rank, "points")) {//rank == "points"  
			rankWithPoints=true;
			rankWithTotalMarks= false;
		}else {
			rankWithPoints=false;
			rankWithTotalMarks= true;
		}
		
		//check show fee
		if(showfee)
			showFeeInfo= true;
		else
			showFeeInfo= false;
		
		
		//check for number of subjects to grade
		
		if(noOfSub == "eleven") {
			grade7subjects= false;
			grade11subjects= true;
		}else {
			grade7subjects= true;
			grade11subjects= false;
		}
		

		
		//log submmited exams
		String logexams="";
		
		//modify the term,year and stream
		 accountId = StringUtils.trimToEmpty(request.getParameter("accountId"));
		 streamId = StringUtils.trimToEmpty(request.getParameter("stream"));
		 term = StringUtils.trimToEmpty(request.getParameter("term"));
		 year = StringUtils.trimToEmpty(request.getParameter("year"));
		 classroomId= StringUtils.trimToEmpty(request.getParameter("classroom"));//added
		 
		 
		 //get selected exams
		 String[] examsfeed= request.getParameterValues("exam");
		 
		 
			
		//assign the global exams with the submitted	
		exams=examsfeed;
			
		for (int j= 0; j < exams.length; j++) {

			// exams[i]= examsfeed[i];
			//logexams += exams[j] + exams.length+"\n";

		}
		
		
		//log the submitted data 
		logger.info("HidePts submitted " + hidePts); 
		logger.info("HideGds submitted" + hideGds); 
		logger.info("Fee submitted" + showfee); 
		logger.info("Subjects submitted " + noOfSub); 
		
		logger.info("Exam submitted " + logexams);
		
		logger.info("Year submitted " + year); 
		logger.info("Term submitted " + term); 
		
		
		
		
		
		logger.info("HidePts " + hidePoints); 
		logger.info("HideGds " + hideGrade); 
		logger.info("Fee " + showFeeInfo); 
		logger.info("Subjects " + grade7subjects); 

		response.setContentType("application/pdf");
		
		

		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4, 46, 46, 64, 64);


		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();



			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(accountId,streamId,term,year);


		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}






	}

	/**
	 * @param year 
	 * @param term 
	 * @param streamId 
	 * @param args
	 */
	public void populatePDFDocument(String accountId, String streamId, String term, String year) {
		Timeit.code(() -> compute(accountId, streamId, term, year));
	}

	/**
	 * @param args
	 */
	public  void compute(String accountId, String streamId, String term, String year) {

		 accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		 //streamId = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
	    // term = "1";
		// year = "2016";

		try {

			document.open();

			generateReport(accountId, streamId, term, year);

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
	 * @param baseColor
	 * @throws DocumentException
	 */
	private void generateReport(String accountId, String streamId, String term, String year)
			throws DocumentException {



		//BaseColor baseColorWhite = new BaseColor(255,255,255);//while
		BaseColor baseColor = new BaseColor(117,229,210);//#75e5d2
		//BaseColor baseColorShadow = new BaseColor(0,255,119);//#00FF77

		Account account = accountDAO.getAccountById(accountId);

		List<Student> studentsList = studentDAO.getStudentByStream(accountId, streamId);

		String school = "P.O Box : " + account.getAddress() + " " + account.getTown()+" "
				+ " , Cell : " + account.getMobile() + "\n"
				+ "Website : " + account .getWebsite() + "             EMAIL : " + account.getEmail(); 


		List<Performance2> performanceList = getStudentScore3(accountId, streamId, term, year, studentsList);

		String rankingCriteria = "";

		if(rankWithPoints && !rankWithTotalMarks){
			Collections.sort(performanceList, new PointsComparator());
			Collections.reverse(performanceList);
			rankingCriteria = "Ranking done using Average Points.";
		}

		if(!rankWithPoints && rankWithTotalMarks){
			Collections.sort(performanceList, new MeanComparator());
			Collections.reverse(performanceList);
			rankingCriteria = "Ranking done using Total Average.";
		}


		int position = 1;
		int prevposition = 1;
		double total = 0;
		double prevtotal =0;
		String pos = "";

		for(Performance2 performance2 : performanceList){


			int mainPoint = performance2.getTotalPoint();
			int totalMean = performance2.getTotalMean();

			int mean = performance2.getTotalMean();

			if(rankWithPoints && !rankWithTotalMarks){

				total = mainPoint;

			}

			if(!rankWithPoints && rankWithTotalMarks){

				total = totalMean;

			}



			if(total == prevtotal){

				pos = String.valueOf(position-prevposition++);

			}else{

				prevposition = 1;
				pos =  String.valueOf(position);

			}

			PdfPTable headerTable = new PdfPTable(2);
			headerTable.setWidthPercentage(100); 
			headerTable.setWidths(new int[]{70,30});



			PdfPCell logo = new PdfPCell();
			logo.addElement(createImage(LOGO_PATH)); 
			logo.setBorder(Rectangle.NO_BORDER); 
			logo.setHorizontalAlignment(Element.ALIGN_CENTER); 

			PdfPCell schoolInfo = new PdfPCell();
			schoolInfo.setBorder(Rectangle.NO_BORDER); 
			schoolInfo.setHorizontalAlignment(Element.ALIGN_LEFT);  
			schoolInfo.addElement(new Chunk(account.getName().toUpperCase(),timesRomanNarmal8));
			schoolInfo.addElement(new Chunk(school, timesRomanNarmal6));

			headerTable.addCell(schoolInfo); 
			headerTable.addCell(logo);   

			document.add(headerTable);


			document.add(new Paragraph("__________________________________________________________________________")); 


			Phrase reportTitle = new Phrase();
			reportTitle.add(new Chunk("STUDENT END OF TERM REPORT CARD",  timesRomanNarmal8));
			reportTitle.add(new Chunk(" (" + rankingCriteria+")",  timesRomanNarmal6));
			document.add(reportTitle);
			document.add(new Paragraph("\n"));



			Student student = studentDAO.getStudentById(accountId, performance2.getStudentId()); 

			String currentClass = "4 N";

			String studentName = student.getFirstname() + " " + student.getMiddlename() + " " + student.getLastname();


			/**
			 *   arrange student info here
			 */
			PdfPTable studentInfoTable = new PdfPTable(2);
			studentInfoTable.setWidthPercentage(100); 
			studentInfoTable.setWidths(new int[]{50,50}); 

			/**
			 * left column
			 */
			PdfPTable studentLeft = new PdfPTable(2);
			studentLeft.setWidthPercentage(62);  
			studentLeft.setWidths(new int[]{12,50});  

			//student name
			PdfPCell nameInfoCell = new PdfPCell(new Phrase("Name:",timesRomanNarmal8)); 
			PdfPCell nameDescCell = new PdfPCell(new Phrase(studentName,  timesRomanNarmal6));
			nameInfoCell.setBorder(Rectangle.NO_BORDER);
			nameDescCell.setBorder(Rectangle.NO_BORDER);
			nameDescCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
			//add student name
			studentLeft.addCell(nameInfoCell);
			studentLeft.addCell(nameDescCell);

			//student regNo
			PdfPCell regNoInfoCell = new PdfPCell(new Phrase("RegNo:",timesRomanNarmal8)); 
			PdfPCell regNoDescCell = new PdfPCell(new Phrase(student.getRegNo(),  timesRomanNarmal6));
			regNoInfoCell.setBorder(Rectangle.NO_BORDER);
			regNoDescCell.setBorder(Rectangle.NO_BORDER);
			//add student name
			studentLeft.addCell(regNoInfoCell);
			studentLeft.addCell(regNoDescCell);

			//student form
			PdfPCell streamInfoCell = new PdfPCell(new Phrase("Form:",timesRomanNarmal8)); 
			PdfPCell streamDescCell = new PdfPCell(new Phrase(currentClass,  timesRomanNarmal6));
			streamInfoCell.setBorder(Rectangle.NO_BORDER);
			streamDescCell.setBorder(Rectangle.NO_BORDER);
			//add student name
			studentLeft.addCell(streamInfoCell);
			studentLeft.addCell(streamDescCell);

			//student grade
			PdfPCell mainGradeInfoCell = new PdfPCell(new Phrase("Score:",timesRomanNarmal8)); 
			
			//add student name
			studentLeft.addCell(mainGradeInfoCell);
			//rank 7 subjects
			if(grade7subjects && !grade11subjects){
				PdfPCell mainGradeDescCell = new PdfPCell(new Phrase(mainPoint + " /84 (" + ReportUtil.getGradeMainForm234(mainPoint, accountId, gradingSystemDAO) + ")" + " , Total(Avg): " + mean,  timesRomanNarmal6));
				mainGradeInfoCell.setBorder(Rectangle.NO_BORDER);
				mainGradeDescCell.setBorder(Rectangle.NO_BORDER);
				studentLeft.addCell(mainGradeDescCell);
			}

			//rank 11 subjects
			if(!grade7subjects && grade11subjects){
				PdfPCell mainGradeDescCell = new PdfPCell(new Phrase(mainPoint + " /132 (" + ReportUtil.getGradeMainForm1(mainPoint, accountId, gradingSystemDAO) + ")" + " , Total(Avg): " + mean,  timesRomanNarmal6));
				mainGradeInfoCell.setBorder(Rectangle.NO_BORDER);
				mainGradeDescCell.setBorder(Rectangle.NO_BORDER);
				studentLeft.addCell(mainGradeDescCell);
			}
			
			
			


			/**
			 * right column
			 */
			PdfPTable studentRight = new PdfPTable(2);
			studentRight.setWidthPercentage(80); 
			studentRight.setWidths(new int[]{30,50}); 


			//student term
			PdfPCell termInfoCell = new PdfPCell(new Phrase("Term:",timesRomanNarmal8)); 
			PdfPCell termDescCell = new PdfPCell(new Phrase(term,  timesRomanNarmal6));
			termInfoCell.setBorder(Rectangle.NO_BORDER);
			termDescCell.setBorder(Rectangle.NO_BORDER);
			//add student name
			studentRight.addCell(termInfoCell);
			studentRight.addCell(termDescCell);

			//student year
			PdfPCell yearInfoCell = new PdfPCell(new Phrase("Year:",timesRomanNarmal8)); 
			PdfPCell yearDescCell = new PdfPCell(new Phrase(year,  timesRomanNarmal6));
			yearInfoCell.setBorder(Rectangle.NO_BORDER);
			yearDescCell.setBorder(Rectangle.NO_BORDER);
			//add student name
			studentRight.addCell(yearInfoCell);
			studentRight.addCell(yearDescCell);

			//student class position
			PdfPCell classGradeInfoCell = new PdfPCell(new Phrase("Overall position:",timesRomanNarmal8)); 
			PdfPCell classGradeDescCell = new PdfPCell(new Phrase("" + pos + " Out of : " + performanceList.size(),timesRomanNarmal6));
			classGradeInfoCell.setBorder(Rectangle.NO_BORDER);
			classGradeDescCell.setBorder(Rectangle.NO_BORDER);
			//add student name
			studentRight.addCell(classGradeInfoCell);
			studentRight.addCell(classGradeDescCell);

			//student stream position
			PdfPCell streamGradeInfoCell = new PdfPCell(new Phrase("Stream position:",timesRomanNarmal8)); 
			PdfPCell streamGradeDescCell = new PdfPCell(new Phrase(" Out of : ",timesRomanNarmal6));
			streamGradeInfoCell.setBorder(Rectangle.NO_BORDER);
			streamGradeDescCell.setBorder(Rectangle.NO_BORDER);
			//add student name
			studentRight.addCell(streamGradeInfoCell);
			studentRight.addCell(streamGradeDescCell);


			/**
			 * put the columns in student table
			 */
			studentInfoTable.addCell(studentLeft);
			studentInfoTable.addCell(studentRight);


			document.add(studentInfoTable);
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

			Map<String,Integer> exam1 = performance2.getExam1();
			Map<String,Integer> exam2 = performance2.getExam2();
			Map<String,Integer> exam3 = performance2.getExam3(); 



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

				//TODO
				String examAverage = ReportUtil.findExamAverage(exam1Score,exam2Score,exam3Score, exams.length);


				String avgrade = ReportUtil.getGrade(examAverage,subject.getUuid(), accountId, subjectDAO, gradingSystemDAO);
				String avgpoints = String.valueOf(ReportUtil.getPoints(examAverage, subject.getUuid(),accountId,subjectDAO, gradingSystemDAO));

				String remarks = ReportUtil.getRemarks(examAverage,subject.getUuid(),accountId); 

				String exam1Grade = ReportUtil.getGrade(exam1Score,subject.getUuid(),accountId, subjectDAO, gradingSystemDAO);
				String exam1Points = String.valueOf(ReportUtil.getPoints(exam1Score, subject.getUuid(), accountId, subjectDAO, gradingSystemDAO));

				if(StringUtils.equals(exam1Points, "0")){
					exam1Points = "";
				}

				String exam2Grade = ReportUtil.getGrade(exam2Score,subject.getUuid(), accountId, subjectDAO, gradingSystemDAO);
				String exam2Points = String.valueOf(ReportUtil.getPoints(exam2Score, subject.getUuid(), accountId, subjectDAO, gradingSystemDAO));

				if(StringUtils.equals(exam2Points, "0")){
					exam2Points = "";
				}

				String exam3Grade = ReportUtil.getGrade(exam3Score,subject.getUuid(), accountId, subjectDAO, gradingSystemDAO);
				String exam3Points = String.valueOf(ReportUtil.getPoints(exam3Score, subject.getUuid(), accountId, subjectDAO, gradingSystemDAO));

				if(StringUtils.equals(exam3Points, "0")){
					exam3Points = "";
				}

				String initials = ReportUtil.getInitials(accountId,streamId,subject.getUuid());  


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
				exm1 = String.valueOf(performance2.getExam1Total());
				exm2 = String.valueOf(performance2.getExam2Total());
				exm3 = String.valueOf(performance2.getExam3Total());

				String ex1Grade = ReportUtil.getGrade(exm1,"x",accountId, subjectDAO, gradingSystemDAO);
				String ex2Grade = ReportUtil.getGrade(exm2,"x",accountId, subjectDAO, gradingSystemDAO);
				String ex3Grade = ReportUtil.getGrade(exm3,"x",accountId, subjectDAO, gradingSystemDAO);

				String exa1Point = String.valueOf(ReportUtil.getPoints(exm1,"x",accountId, subjectDAO, gradingSystemDAO));
				String exa2Point = String.valueOf(ReportUtil.getPoints(exm2,"x",accountId, subjectDAO, gradingSystemDAO));
				String exa3Point = String.valueOf(ReportUtil.getPoints(exm3,"x",accountId, subjectDAO, gradingSystemDAO));

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

					//rank 7 subjects
					if(grade7subjects && !grade11subjects){
						examTable.addCell(new Paragraph(" "+ReportUtil.getGradeMainForm234(mainPoint, accountId, gradingSystemDAO)  ,timesRomanNarmal6));
					}

					//rank 11 subjects
					if(!grade7subjects && grade11subjects){
						examTable.addCell(new Paragraph(" "+ReportUtil.getGradeMainForm1(mainPoint, accountId, gradingSystemDAO)  ,timesRomanNarmal6));
					}
					
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



			// show comments here
			Chunk underline = new Chunk("GENERAL COMMENTS. ", timesRomanNarmal8);
			underline.setUnderline(0.1f, -2f); // 0.1 thick, -2

			String classTeacherRemarks = "Class Teacher's Remarks Here";
			String headteacherRemarks = "Headteacher's Remarks Here";
			String closingDate = "Closing Date Here";
			String openingdate = "Opening Date Here";

			Phrase teacherremarkphrase = new Phrase();
			teacherremarkphrase.add(new Chunk("CLASS TEACHER'S REMARKS:",  timesRomanNarmal8));
			teacherremarkphrase.add(new Chunk("  " + classTeacherRemarks,  timesRomanNarmal6));
			teacherremarkphrase.add(new Chunk("\n"));

			Phrase headteacherremarkphrase = new Phrase();
			headteacherremarkphrase.add(new Chunk("HEAD TEACHER'S REMARKS:",  timesRomanNarmal8));
			headteacherremarkphrase.add(new Chunk("  " + headteacherRemarks,  timesRomanNarmal6));
			headteacherremarkphrase.add(new Chunk("\n"));

			Phrase datesphrase = new Phrase();
			datesphrase.add(new Chunk("SCHOOL CLOSES ON:",  timesRomanNarmal8));
			datesphrase.add(new Chunk("  " + closingDate,  timesRomanNarmal6));
			datesphrase.add(new Chunk("               NEXT TERM BEGINS ",  timesRomanNarmal8));
			datesphrase.add(new Chunk(" " + openingdate,  timesRomanNarmal6));
			datesphrase.add(new Chunk("\n"));

			Phrase signaturephrase = new Phrase();
			signaturephrase.add(new Chunk("SIGNATURE:",  timesRomanNarmal8));
			signaturephrase.add(new Chunk("__________________",  timesRomanNarmal6));
			signaturephrase.add(new Chunk("                 STAMP",  timesRomanNarmal8));
			signaturephrase.add(new Chunk("__________________",  timesRomanNarmal6));
			signaturephrase.add(new Chunk("\n"));

			// show grading scale here

			String generalGradingScale = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";

			List<GradingSystem> gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, generalGradingScale);

			//footer table
			PdfPTable footerTable = new PdfPTable(2);
			footerTable.setWidthPercentage(78); 
			footerTable.setWidths(new int[]{28,50}); 


			//fee info table
			PdfPTable feeInfoTable = new PdfPTable(2);
			feeInfoTable.setWidthPercentage(100); 
			feeInfoTable.setWidths(new int[]{50,50}); 


			PdfPCell feeInfo1 = new PdfPCell(new Phrase("Fee Analysis",timesRomanNarmal8)); 
			PdfPCell feeInfo2 = new PdfPCell(new Phrase(""));
			feeInfo1.setBorder(Rectangle.NO_BORDER);
			feeInfo2.setBorder(Rectangle.NO_BORDER);

			PdfPCell feecol1 = new PdfPCell(new Phrase("Description",timesRomanNarmal6)); 
			PdfPCell feecol2 = new PdfPCell(new Phrase("Amount",timesRomanNarmal6));

			feeInfoTable.addCell(feeInfo1);
			feeInfoTable.addCell(feeInfo2);

			feeInfoTable.addCell(feecol1);
			feeInfoTable.addCell(feecol2);

			String feeBal = "KSH 10,000";
			String nextTermFee = "KSH 26,000";

			if(!showFeeInfo){
				feeBal = "";
				nextTermFee = "";
			}

			PdfPCell feeBalInfo = new PdfPCell(new Phrase("Fee Bal:",timesRomanNarmal6));
			PdfPCell feeBalDesc = new PdfPCell(new Phrase(feeBal,timesRomanNarmal6));
			feeBalInfo.setBorder(Rectangle.NO_BORDER);
			feeBalDesc.setBorder(Rectangle.NO_BORDER);

			PdfPCell nextTermFeeInfo = new PdfPCell(new Phrase("Next Term Fee:",timesRomanNarmal6));
			PdfPCell nextTermFeeDesc = new PdfPCell(new Phrase(nextTermFee,timesRomanNarmal6));
			nextTermFeeInfo.setBorder(Rectangle.NO_BORDER);
			nextTermFeeDesc.setBorder(Rectangle.NO_BORDER);

			feeInfoTable.addCell(feeBalInfo);
			feeInfoTable.addCell(feeBalDesc); 

			feeInfoTable.addCell(nextTermFeeInfo);
			feeInfoTable.addCell(nextTermFeeDesc); 

			//grade(s) table 
			PdfPTable gradesTable = new PdfPTable(3);
			gradesTable.setWidthPercentage(28); 
			gradesTable.setWidths(new int[]{12,8,8}); 
			gradesTable.setHeaderRows(1); 
			gradesTable.isSkipFirstHeader();

			PdfPCell rangecell = new PdfPCell(new Phrase("Mark Range" , timesRomanNarmal8));
			gradesTable.addCell(rangecell);
			PdfPCell gradescell = new PdfPCell(new Phrase("Grade" , timesRomanNarmal8));
			gradesTable.addCell(gradescell);
			PdfPCell remarksscell = new PdfPCell(new Phrase("Points" , timesRomanNarmal8));
			gradesTable.addCell(remarksscell);


			for (GradingSystem  rankingScale : gradingSystemList){

				rangecell = new PdfPCell(new Phrase(rankingScale.getLowerLimit() + " - " + rankingScale.getUpperLimit(), timesRomanNarmal4));
				gradesTable.addCell(rangecell);

				gradescell = new PdfPCell(new Phrase(rankingScale.getDescription() + "" , timesRomanNarmal4));
				gradesTable.addCell(gradescell);

				remarksscell = new PdfPCell(new Phrase(rankingScale.getPoints() + " " , timesRomanNarmal4));
				gradesTable.addCell(remarksscell);

			}



			position++;
			prevtotal=total;

			document.add(examTable);

			document.add(new Chunk("\n"));

			document.add(underline);

			document.add(new Paragraph(teacherremarkphrase));

			document.add(new Paragraph(headteacherremarkphrase));

			document.add(new Paragraph(datesphrase));

			document.add(new Paragraph(signaturephrase));

			document.add(new Paragraph("\n"));

			footerTable.addCell(gradesTable);
			footerTable.addCell(feeInfoTable);

			document.add(footerTable);


			document.newPage();

		}
	}


	/**
	 * @param accountId
	 * @param streamId
	 * @param term
	 * @param year
	 * @param studentsList
	 */
	private  List<Performance2> getStudentScore3(String accountId, String streamId, String term, String year,
			List<Student> studentsList) {

		List<Performance2> performance2List = new ArrayList<>();
		List<Perfomance> exam1  = new ArrayList<>();
		List<Perfomance> exam2 = new ArrayList<>();
		List<Perfomance> exam3 = new ArrayList<>();

		Performance3 totalExam1 = new Performance3();
		Performance3 totalExam2 = new Performance3();
		Performance3 totalExam3 = new Performance3();

		int totalPoint = 0;
		int totalMeans = 0;


		for(Student student : studentsList ){

			if(exams.length == 3){

				exam1 = perfomanceDAO.getStreamPerformance(accountId, exams[0], student.getUuid(), streamId, term, year);
				exam2 = perfomanceDAO.getStreamPerformance(accountId, exams[1], student.getUuid(), streamId, term, year);
				exam3 = perfomanceDAO.getStreamPerformance(accountId, exams[2], student.getUuid(), streamId, term, year); 

				//rank 7 subjects
				if(grade7subjects && !grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm234(accountId, exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
					totalExam2 = ReportUtil.findExamTotalForm234(accountId, exam2, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
					totalExam3 = ReportUtil.findExamTotalForm234(accountId, exam3, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
				}

				//rank 11 subjects
				if(!grade7subjects && grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm1(accountId, exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
					totalExam2 = ReportUtil.findExamTotalForm1(accountId, exam2, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
					totalExam3 = ReportUtil.findExamTotalForm1(accountId, exam3, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
				}


				totalPoint = totalExam1.getTotalPoits() + totalExam2.getTotalPoits() + totalExam3.getTotalPoits();
				totalPoint = totalPoint / 3;

				totalMeans = totalExam1.getTotalMean() + totalExam2.getTotalMean() + totalExam3.getTotalMean();
				totalMeans = totalMeans / 3;


			}

			if(exams.length == 2){

				exam1 = perfomanceDAO.getStreamPerformance(accountId, exams[0], student.getUuid(), streamId, term, year);
				exam2 = perfomanceDAO.getStreamPerformance(accountId, exams[1], student.getUuid(), streamId, term, year);

				//rank 7 subjects
				if(grade7subjects && !grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm234(accountId, exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
					totalExam2 = ReportUtil.findExamTotalForm234(accountId, exam2, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
				}

				//rank 11 subjects
				if(!grade7subjects && grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm1(accountId, exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
					totalExam2 = ReportUtil.findExamTotalForm1(accountId, exam2, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
				}



				totalPoint = totalExam1.getTotalPoits() + totalExam2.getTotalPoits();
				totalPoint = totalPoint / 2;

				totalMeans = totalExam1.getTotalMean() + totalExam2.getTotalMean();
				totalMeans = totalMeans / 2;


			}

			if(exams.length == 1){

				exam1 = perfomanceDAO.getStreamPerformance(accountId, exams[0], student.getUuid(), streamId, term, year);

				//rank 7 subjects
				if(grade7subjects && !grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm234(accountId, exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
				}

				//rank 11 subjects
				if(!grade7subjects && grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm1(accountId, exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
				}



				totalPoint = totalExam1.getTotalPoits();

				totalMeans = totalExam1.getTotalMean();



			}

			Performance2 performance2 = new Performance2();
			performance2.setExam1(totalExam1.getPerfomanceMap());
			performance2.setExam2(totalExam2.getPerfomanceMap());
			performance2.setExam3(totalExam3.getPerfomanceMap()); 
			performance2.setStudentId(student.getUuid());
			performance2.setTotalMean(totalMeans); 
			performance2.setTotalPoint(totalPoint);
			performance2.setExam1Total(totalExam1.getTotalPoits());
			performance2.setExam2Total(totalExam2.getTotalPoits());
			performance2.setExam3Total(totalExam3.getTotalPoits());

			performance2List.add(performance2);

		}

		return performance2List;
	}



	/**
	 * @param realPath
	 * @return
	 */
	private Element createImage(String realPath) {
		Image img = null;

		try {

			File file = new File(realPath);
			if(!file.exists()){
				realPath = getServletContext().getRealPath("/images/default.jpg");

			}

			BufferedImage bufferedImage = ImageIO.read(new File(realPath));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ImageIO.write(resize(bufferedImage, 200,100), "png", baos);//w,h
			img = Image.getInstance(baos.toByteArray());
			img.scaleAbsolute(80f,40f); 
			img.setAlignment(Element.ALIGN_LEFT);


		} catch (BadElementException e) {
			logger.error("BadElementException Exception while creating an image");
			logger.error(ExceptionUtils.getStackTrace(e));

		} catch (MalformedURLException e) {
			logger.error("MalformedURLException for the path");
			logger.error(ExceptionUtils.getStackTrace(e));

		} catch (IOException e) {
			logger.error("IOException while creating an image");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return img;
	}


	/**
	 * @param img
	 * @param newW
	 * @param newH
	 * @return
	 */
	private BufferedImage resize(BufferedImage img, int newW, int newH) { 
		java.awt.Image tmp = img.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
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
		
		/**
		 * get the params to customize the report display
		 */
	
			
		doPost(request, response);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5356561358431988192L;

}
