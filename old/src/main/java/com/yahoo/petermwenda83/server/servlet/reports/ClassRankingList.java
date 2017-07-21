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
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
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

/**
 * 
 * 
 * http://localhost:8080/school/school/classRankingList
 * 
 * @author peter
 *
 */
public class ClassRankingList extends HttpServlet{

	private static GradingSystemDAO gradingSystemDAO;
	private static SubCategoryDAO subCategoryDAO;
	private static PerfomanceDAO perfomanceDAO;
	private static CategoryDAO categoryDAO;
	private static SubjectDAO subjectDAO;
	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static StreamDAO streamDAO;

	private Font timesRomanNormal10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	private Font timesRomanBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

	//private Font timesRomanMormal8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private Font timesRomanBold8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);

	private Font timesRomanNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	private Font timesRomanBold6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD);

	private Document document;
	private PdfWriter writer;

	private Logger logger;

	private static final String[] exams = {"D50E6399-B913-42F2-A5B6-F0D4BAAF9571", "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A" };//, "16C4BF00-941C-40E4-9891-272D5F0979A1"

	// , "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A", "16C4BF00-941C-40E4-9891-272D5F0979A1" 


	private boolean hidePoints = false;
	private boolean hideGrade = false;
	private boolean rankWithPoints = false;
	private boolean rankWithTotalMarks = true;
	private boolean grade7subjects = true;
	private boolean grade11subjects = false;

	private static final String USER_SYSTEM = System.getProperty("user.name");
	private static final String LOGO_PATH = "/home/"+USER_SYSTEM+"/school/logo/logo.png";


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
		streamDAO = StreamDAO.getInstance();


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

		logger.info("******************************************************************"); 
		logger.info("accountId " + accountId); 

		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		//Document(Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom)
		document = new Document(PageSize.A4.rotate(), 46, 46, 64, 64);
		document.addAuthor("Peter Mwenda (254718953974)"); 
		document.addCreationDate();


		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();



			writer.setBoxSize("art", new Rectangle(46, 64, 300, 900)); 
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
		schoolInfo.addElement(new Chunk(account.getName().toUpperCase(),timesRomanBold10));
		schoolInfo.addElement(new Chunk(school, timesRomanNormal10));

		headerTable.addCell(schoolInfo); 
		headerTable.addCell(logo);   

		document.add(headerTable);

		PdfContentByte topLine = writer.getDirectContent();
		topLine.setColorStroke(BaseColor.BLACK);
		topLine.moveTo(45, 463);//start dot, 45 is margin left, 463 is margin top , 
		//the bigger second value the more the point move further from the margin 
		topLine.lineTo(790, 463);
		topLine.closePathStroke();

		Phrase reportTitle = new Phrase();
		reportTitle.add(new Chunk("CLASS RANKING LIST FOR  TERM : YEAR : ",  timesRomanBold10));
		reportTitle.add(new Chunk(" (" + rankingCriteria+")",  timesRomanNormal10));
		document.add(reportTitle);
		document.add(new Paragraph("\n"));


		/**
		 *   arrange class info here
		 */
		PdfPTable classInfoTable = new PdfPTable(2);
		classInfoTable.setWidthPercentage(100); 
		classInfoTable.setWidths(new int[]{50,50}); 

		/**
		 * left column
		 */
		PdfPTable classLeft = new PdfPTable(2);
		classLeft.setWidthPercentage(58);  
		classLeft.setWidths(new int[]{8,50});  

		//student name
		PdfPCell nameInfoCell = new PdfPCell(new Phrase("Class:",timesRomanBold8)); 
		PdfPCell nameDescCell = new PdfPCell(new Phrase(streamDAO.getStream(accountId, streamId).getDescription(),  timesRomanNormal6));
		nameInfoCell.setBorder(Rectangle.NO_BORDER);
		nameDescCell.setBorder(Rectangle.NO_BORDER);
		nameDescCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		//add class name
		classLeft.addCell(nameInfoCell);
		classLeft.addCell(nameDescCell);

		//student grade
		PdfPCell mainGradeInfoCell = new PdfPCell(new Phrase("Mean:",timesRomanBold8)); 
		PdfPCell mainGradeDescCell = new PdfPCell(new Phrase("**",  timesRomanNormal6));
		mainGradeInfoCell.setBorder(Rectangle.NO_BORDER);
		mainGradeDescCell.setBorder(Rectangle.NO_BORDER);
		//add student name
		classLeft.addCell(mainGradeInfoCell);
		classLeft.addCell(mainGradeDescCell);


		/**
		 * right column
		 */
		PdfPTable classRight = new PdfPTable(2);
		classRight.setWidthPercentage(58); 
		classRight.setWidths(new int[]{8,50}); 


		//student term
		PdfPCell termInfoCell = new PdfPCell(new Phrase("Term:",timesRomanBold8)); 
		PdfPCell termDescCell = new PdfPCell(new Phrase(term,  timesRomanNormal6));
		termInfoCell.setBorder(Rectangle.NO_BORDER);
		termDescCell.setBorder(Rectangle.NO_BORDER);
		//add student name
		classRight.addCell(termInfoCell);
		classRight.addCell(termDescCell);

		//student year
		PdfPCell yearInfoCell = new PdfPCell(new Phrase("Year:",timesRomanBold8)); 
		PdfPCell yearDescCell = new PdfPCell(new Phrase(year,  timesRomanNormal6));
		yearInfoCell.setBorder(Rectangle.NO_BORDER);
		yearDescCell.setBorder(Rectangle.NO_BORDER);
		//add student name
		classRight.addCell(yearInfoCell);
		classRight.addCell(yearDescCell);


		/**
		 * put the columns in student table
		 */
		classInfoTable.addCell(classLeft);
		classInfoTable.addCell(classRight);


		document.add(classInfoTable);
		document.add(new Paragraph("\n"));



		List<Subject> subjects = subjectDAO.getSubjects(accountId);

		int size = subjects.size() + 6 + 5;
		if(size > 13){
			size = 13;
		}

		PdfPTable rankingTable = new PdfPTable(25);   
		rankingTable.setWidthPercentage(100); 
		rankingTable.setWidths(new int[]{8,12,20,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,8,12}); 
		//rankingTable.setHeaderRows(1); 
		//rankingTable.isSkipFirstHeader();


		//cells = 5
		PdfPCell countCell = new PdfPCell(new Paragraph("#",timesRomanBold6));
		countCell.setBackgroundColor(baseColor);
		countCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell regNoCell = new PdfPCell(new Paragraph("RegNo",timesRomanBold6));
		regNoCell.setBackgroundColor(baseColor);
		regNoCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell nameCell = new PdfPCell(new Paragraph("Name",timesRomanBold6));
		nameCell.setBackgroundColor(baseColor);
		nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell streamCell = new PdfPCell(new Paragraph("Stream",timesRomanBold6));
		streamCell.setBackgroundColor(baseColor);
		streamCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell kcpeCell = new PdfPCell(new Paragraph("KCPE",timesRomanBold6));
		kcpeCell.setBackgroundColor(baseColor);
		kcpeCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		rankingTable.addCell(countCell);
		rankingTable.addCell(regNoCell);
		rankingTable.addCell(nameCell);
		rankingTable.addCell(streamCell);
		rankingTable.addCell(kcpeCell);

		//cells = 13


		for(Subject subject : subjects){

			PdfPCell cell = new PdfPCell(new Paragraph(subject.getCode(),timesRomanBold6));
			cell.setBackgroundColor(baseColor);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);

			rankingTable.addCell(cell); 

		}

		//cells = 7
		PdfPCell totalCell = new PdfPCell(new Paragraph("Total",timesRomanBold6));
		totalCell.setBackgroundColor(baseColor);
		totalCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell pointsCell = new PdfPCell(new Paragraph("Points",timesRomanBold6));
		pointsCell.setBackgroundColor(baseColor);
		pointsCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell meanCell = new PdfPCell(new Paragraph("Mean",timesRomanBold6));
		meanCell.setBackgroundColor(baseColor);
		meanCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell pmeanCell = new PdfPCell(new Paragraph("P Mean",timesRomanBold6));
		pmeanCell.setBackgroundColor(baseColor);
		pmeanCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell deviationCell = new PdfPCell(new Paragraph("Dev",timesRomanBold6));
		deviationCell.setBackgroundColor(baseColor);
		deviationCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell streamPositionCell = new PdfPCell(new Paragraph("Pos",timesRomanBold6));
		streamPositionCell.setBackgroundColor(baseColor);
		streamPositionCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell classPositionCell = new PdfPCell(new Paragraph("ClassPos",timesRomanBold6));
		classPositionCell.setBackgroundColor(baseColor);
		classPositionCell.setHorizontalAlignment(Element.ALIGN_LEFT);


		rankingTable.addCell(totalCell);
		rankingTable.addCell(pointsCell);
		rankingTable.addCell(meanCell);
		rankingTable.addCell(pmeanCell);
		rankingTable.addCell(deviationCell);
		rankingTable.addCell(streamPositionCell);
		rankingTable.addCell(classPositionCell);


		//code here

		int position = 1;
		int prevposition = 1;
		double total = 0;
		double prevtotal =0;
		String pos = "";
		int count = 1;
		for(Performance2 performance2 : performanceList){


			int mainPoint = performance2.getTotalPoint();
			int totalMean = performance2.getTotalMean();

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

			Student student = studentDAO.getStudentById(accountId, performance2.getStudentId()); 

			String stream = "";
			if(streamDAO.getStream(accountId, student.getCurrentStream()) != null){
				stream = streamDAO.getStream(accountId, student.getCurrentStream()).getDescription();
			}

			stream = StringUtils.remove(stream, "FORM"); 



			Map<String,Integer> exam1 = performance2.getExam1();
			Map<String,Integer> exam2 = performance2.getExam2();
			Map<String,Integer> exam3 = performance2.getExam3(); 


			rankingTable.addCell(new Paragraph(" " + count,timesRomanNormal6));
			rankingTable.addCell(new Paragraph(student.getRegNo(),timesRomanNormal6));
			rankingTable.addCell(new Paragraph(student.getFirstname(),timesRomanNormal6));
			rankingTable.addCell(new Paragraph(stream,timesRomanNormal6));
			rankingTable.addCell(new Paragraph("-",timesRomanNormal6));


			for(Subject subject : subjects){


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

				String examAverage = ReportUtil.findExamAverage(exam1Score,exam2Score,exam3Score, exams.length);



				String avgrade = ReportUtil.getGrade(examAverage,subject.getUuid(), accountId, subjectDAO, gradingSystemDAO);
				String avgpoints = String.valueOf(ReportUtil.getPoints(examAverage, subject.getUuid(),accountId,subjectDAO, gradingSystemDAO));



				String average = examAverage + " " + avgrade +  " " + avgpoints;

				if(hidePoints && hideGrade){
					average = examAverage;
				}

				if(hidePoints && !hideGrade){ 
					average = examAverage + " " + avgrade;
				}

				if(hideGrade && !hidePoints){
					average = examAverage + " " + avgpoints;
				}

				rankingTable.addCell(new Paragraph(average,timesRomanNormal6));



			}


			double avgMean = 0;
			if(totalMean > 0){ 
				avgMean = Math.round( (double)totalMean / 7);  
			}

			String avgGradeByTotalMean = ReportUtil.getGrade(String.valueOf(performance2.getTotalPoint()),"", accountId, subjectDAO, gradingSystemDAO); 
			String avgGradeByMean = ReportUtil.getGrade(String.valueOf((int)avgMean),"", accountId, subjectDAO, gradingSystemDAO); 

			String poinst_str = "";
			String mean_str = "";

			poinst_str = String.valueOf(performance2.getTotalPoint()); 
			mean_str = String.valueOf((int)avgMean); 

			if(rankWithPoints && !rankWithTotalMarks){
				//show grade on points
				poinst_str = performance2.getTotalPoint() + " " + avgGradeByTotalMean;

			}

			if(!rankWithPoints && rankWithTotalMarks){
				//show grade on avg
				mean_str = (int)avgMean + " " +  avgGradeByMean;
			}


			rankingTable.addCell(new Paragraph(""+performance2.getTotalMean(),timesRomanNormal6));
			rankingTable.addCell(new Paragraph(""+poinst_str,timesRomanNormal6));
			rankingTable.addCell(new Paragraph(""+mean_str,timesRomanNormal6));
			rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
			rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
			rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
			rankingTable.addCell(new Paragraph(pos,timesRomanNormal6));


			position++;
			prevtotal=total;
			count++;


		}







		document.add(rankingTable);

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
		doPost(request, response);
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 2416580639977445935L;
}
