/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

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
import com.yahoo.petermwenda83.bean.classroom.Stream;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.exam.YearlyMean;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.ClassDAO;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.exam.YearlyMeanDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.MiscellanousDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.staff.TeacherSubjectDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.CategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubCategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.servlet.finance.StudentBalance;
import com.yahoo.petermwenda83.server.servlet.reports.test2.FormFour;
import com.yahoo.petermwenda83.server.servlet.reports.test2.FormOne;
import com.yahoo.petermwenda83.server.servlet.reports.test2.FormThree;
import com.yahoo.petermwenda83.server.servlet.reports.test2.FormTwo;
import com.yahoo.petermwenda83.server.servlet.reports.test2.Forms;
import com.yahoo.petermwenda83.server.servlet.reports.test2.MP;
import com.yahoo.petermwenda83.server.servlet.reports.test2.PerformanceTable;
import com.yahoo.petermwenda83.server.servlet.reports.test2.TermOneObj;
import com.yahoo.petermwenda83.server.servlet.reports.test2.TermThreeObj;
import com.yahoo.petermwenda83.server.servlet.reports.test2.TermTwoObj;
import com.yahoo.petermwenda83.server.servlet.util.Timeit;
import com.yahoo.petermwenda83.server.session.SessionConstants;
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
	private static StreamDAO streamDAO;
	private static ExamDAO examDAO;
	private static YearlyMeanDAO yearlyMeanDAO;
	private static StaffDAO staffDAO;
	private static ClassDAO classDAO;
	private static TeacherSubjectDAO teacherSubjectDAO;
	private static MiscellanousDAO miscellanousDAO;
	//private static ClassMeanDAO classMeanDAO;
	private static PrimaryDAO primaryDAO;


	private Font timesRomanNarmal8 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private Font timesRomanNarmal6 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	private Font timesRomanNarmal4 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	//private Font timesRomanNarmal6White = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);


	private Document document;
	private PdfWriter writer;

	private Logger logger;

	private String[] exams = {"D50E6399-B913-42F2-A5B6-F0D4BAAF9571", "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A" ,"16C4BF00-941C-40E4-9891-272D5F0979A1"};

	private boolean hidePoints = false;
	private boolean hideGrade = false;

	private boolean rankWithPoints = false;
	private boolean rankWithTotalMarks = true;

	private boolean showFeeInfo = false;

	private boolean grade7subjects = true;
	private boolean grade11subjects = false;
	private boolean classResult = true;


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
		examDAO = ExamDAO.getInstance();
		yearlyMeanDAO = YearlyMeanDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
		classDAO = ClassDAO.getInstance();
		teacherSubjectDAO = TeacherSubjectDAO.getInstance();
		miscellanousDAO = MiscellanousDAO.getInstance();

		//classMeanDAO = ClassMeanDAO.getInstance();
		primaryDAO = PrimaryDAO.getInstance();

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
		boolean scope= true;//check scope true for class and false for stream

		HttpSession session = request.getSession(true);

		String accountId;
		String streamId;
		String term;
		String year;
		String classroomId;
		String paper123Id = "C3915245-00EE-4EF4-9898-ACE59683DD60";
		String saveMean = "";
		String graphType = "";

		accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID); 

		hidePts = Boolean.parseBoolean(request.getParameter("p"));
		hideGds= Boolean.parseBoolean(request.getParameter("g"));

		String rank = request.getParameter("rank");

		Boolean showfee = Boolean.parseBoolean(request.getParameter("fee"));

		String noOfSub = request.getParameter("subjects");
		paper123Id = StringUtils.trimToEmpty(request.getParameter("paper123Id")); 
		saveMean = StringUtils.trimToEmpty(request.getParameter("saveMean")); //1 means save, 
		graphType = StringUtils.trimToEmpty(request.getParameter("graphType")); //1 means bar,  0 line
		//saveMean = "1";
		//graphType = "0";

		/*saveMean = "1";
		graphType = "1";*/

		//check for hide points
		hidePoints = hidePts ? true : false;
		//check for hide grades
		hideGrade = hideGds ? true : false;

		//check for rank with points
		rankWithPoints = StringUtils.equalsIgnoreCase(rank, "points") ? true : false;
		rankWithTotalMarks = StringUtils.equalsIgnoreCase(rank, "points") ? false : true;
		//check for number of subjects to grade
		grade7subjects = StringUtils.equalsIgnoreCase(noOfSub, "eleven") ? false : true;
		grade11subjects = StringUtils.equalsIgnoreCase(noOfSub, "eleven") ? true :false;

		//check show fee
		showFeeInfo = showfee ? true : false;

		streamId = StringUtils.trimToEmpty(request.getParameter("stream"));
		term = StringUtils.trimToEmpty(request.getParameter("term"));
		year = StringUtils.trimToEmpty(request.getParameter("year"));
		classroomId= StringUtils.trimToEmpty(request.getParameter("classroom"));

		//if class checked the scope is true, otherwise false
		scope= Boolean.parseBoolean((request.getParameter("scope")));
		classResult = scope ? true : false;


		//get selected exams
		String[] examsfeed= request.getParameterValues("exam");

		//assign the global exams with the submitted	
		exams=examsfeed;

		response.setContentType("application/pdf");

		String examType = "";

		examType = StringUtils.trimToEmpty(request.getParameter("examType"));


		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4, 46, 46, 64, 64);


		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();

			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			if(exams.length == 3) {
				saveMean = "1";
			}

			examType = StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE) ? ReportUtil.EXAM_TYPE : "";
			paper123Id = StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE) ? ReportUtil.PAPER123ID : "";

			populatePDFDocument(accountId, streamId, classroomId, term, year, examType, paper123Id, saveMean, graphType);

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
	public void populatePDFDocument(String accountId, String streamId, String classroomId ,String term,
			String year, String examType, String paper123Id, String saveMean, String graphType) {
		Timeit.code(() -> compute(accountId, streamId, classroomId ,term, year, examType, paper123Id, saveMean, graphType));
	}

	/**
	 * @param args
	 */
	public  void compute(String accountId, String streamId, String classroomId , 
			String term, String year, String examType, String paper123Id, String saveMean, String graphType) {

		try {

			document.open();

			generateReport(accountId, streamId, classroomId , term, year, examType, paper123Id, saveMean, graphType);

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
	private void generateReport(String accountId, String streamId,  String classroomId ,
			String term, String year, String examType, String paper123Id, String saveMean, String graphType)
					throws DocumentException {



		//BaseColor baseColorWhite = new BaseColor(255,255,255);//while
		BaseColor baseColor = new BaseColor(117,229,210);//#75e5d2
		//BaseColor baseColorShadow = new BaseColor(0,255,119);//#00FF77

		Account account = accountDAO.getAccountById(accountId);

		List<Student> studentsList = new ArrayList<>();
		List<Student> classstudentsList = new ArrayList<>();

		List<Performance2> performanceList =  new ArrayList<>();
		List<Performance2> classperformanceList =  new ArrayList<>();

		String correctClass = "";


		if(streamDAO.getStream(accountId, streamId) != null && !classResult){

			classroomId = streamDAO.getStream(accountId, streamId).getClassRoomId();			
			correctClass = streamDAO.getStream(accountId, streamId).getDescription();

			studentsList = studentDAO.getStudentByStream(accountId, streamId) != null ? studentDAO.getStudentByStream(accountId, streamId) : new ArrayList<>();

			List<Stream> streamList = streamDAO.getStreamList(accountId, classroomId) != null ? streamDAO.getStreamList(accountId, classroomId) : new ArrayList<>();

			streamList.forEach(stream -> {

				List<Student> studentListStream = studentDAO.getStudentByStream(accountId, stream.getUuid()) != null ? studentDAO.getStudentByStream(accountId, stream.getUuid()) : new ArrayList<>(); 

				if(!studentListStream.isEmpty())
					classstudentsList.addAll(studentListStream); 

			});



		}else if(classDAO.getClassRoom(accountId, classroomId) != null && classResult){

			correctClass = classDAO.getClassRoom(accountId, classroomId).getDescription(); 

			List<Stream> streamList = streamDAO.getStreamList(accountId, classroomId) != null ? streamDAO.getStreamList(accountId, classroomId) : new ArrayList<>();

			for(Stream stream : streamList){

				List<Student> studentListStream = studentDAO.getStudentByStream(accountId, stream.getUuid()) != null ? studentDAO.getStudentByStream(accountId, stream.getUuid()) : new ArrayList<>(); 

				if(!studentListStream.isEmpty())
					studentsList.addAll(studentListStream); 

			}
		}

		if(studentsList.isEmpty()){
			//avoid document has no page exception
			document.add(new Paragraph("No students for " + correctClass)); 
		}else{


			if(classResult){

				performanceList = getStudentScore3(accountId, classroomId, term, year, studentsList, examType, classResult, paper123Id);	



			}else{


				performanceList = getStudentScore3(accountId, streamId, term, year, studentsList, examType, classResult, paper123Id);


				String classId = streamDAO.getStream(accountId, streamId).getClassRoomId();
				classperformanceList  = getStudentScore3(accountId, classId, term, year, classstudentsList, examType, true, paper123Id); 





				if(rankWithPoints && !rankWithTotalMarks){
					Collections.sort(classperformanceList, new PointsComparator());
					Collections.reverse(classperformanceList);

				}

				if(!rankWithPoints && rankWithTotalMarks){
					Collections.sort(classperformanceList, new MeanComparator());
					Collections.reverse(classperformanceList);

				}


			}



		}




		//avoid document has no page exception
		if(!performanceList.isEmpty()){


			String school = "P.O Box : " + account.getAddress() + " " + account.getTown()+" "
					+ " , Cell : " + account.getMobile() + "\n"
					+ "Website : " + account .getWebsite() + "             EMAIL : " + account.getEmail(); 




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

				String avg_points_grade = "0";
				String avgPoints = "0";

				int mainPoint = performance2.getTotalPoint();
				int meanTotal = performance2.getTotalMean();

				if(rankWithPoints && !rankWithTotalMarks){

					total = mainPoint;


				}

				if(!rankWithPoints && rankWithTotalMarks){

					total = meanTotal;

				}



				if(total == prevtotal){

					pos = String.valueOf(position-prevposition++);

				}else{

					prevposition = 1;
					pos =  String.valueOf(position);

				}

				List<Subject> subjects = subjectDAO.getSubjects(accountId);



				Map<String,Integer> exam1 = performance2.getExam1();
				Map<String,Integer> exam2 = performance2.getExam2();
				Map<String,Integer> exam3 = performance2.getExam3(); 


				if(StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE)){


					if(rankWithPoints && !rankWithTotalMarks){


						if(grade7subjects && !grade11subjects){

							avg_points_grade = ReportUtil.getGrade(String.valueOf(mainPoint),"subjectId", accountId, subjectDAO, gradingSystemDAO);
							avgPoints = String.valueOf(mainPoint); 

						}else if(!grade7subjects && grade11subjects){

							double avg = ((double)Double.valueOf(mainPoint) / 132) * 84; 
							avg_points_grade = ReportUtil.getGrade(String.valueOf((int)avg),"subjectId", accountId, subjectDAO, gradingSystemDAO);
							avgPoints = String.valueOf((int)avg); 


						}
					}


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
				reportTitle.add(new Chunk("STUDENT END OF TERM: " + term +" , YEAR: " + year + " REPORT CARD",  timesRomanNarmal8));
				reportTitle.add(new Chunk(" (" + rankingCriteria+")",  timesRomanNarmal6));
				document.add(reportTitle);
				document.add(new Paragraph("\n"));



				Student student = studentDAO.getStudentById(accountId, performance2.getStudentId()); 


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


				String strm = streamDAO.getStream(accountId, student.getCurrentStream()).getDescription(); 
				strm = StringUtils.replace(strm, "FORM", ""); 
				strm = " (" + strm + ")";

				if(!classResult){
					strm = "";
				}

				//TODO
				String kcpe = " , KCPE : ";
				if(primaryDAO.getStudentPrimary(accountId, student.getUuid()) != null) {
					kcpe += primaryDAO.getStudentPrimary(accountId, student.getUuid()).getKcpemark();
				}


				//student form
				PdfPCell streamInfoCell = new PdfPCell(new Phrase("Class:",timesRomanNarmal8)); 
				PdfPCell streamDescCell = new PdfPCell(new Phrase(correctClass + strm + kcpe,  timesRomanNarmal6)); 
				streamInfoCell.setBorder(Rectangle.NO_BORDER);
				streamDescCell.setBorder(Rectangle.NO_BORDER);
				//add student name
				studentLeft.addCell(streamInfoCell);
				studentLeft.addCell(streamDescCell);

				//student grade
				PdfPCell mainGradeInfoCell = new PdfPCell(new Phrase("Score:",timesRomanNarmal8)); 
				mainGradeInfoCell.setBorder(Rectangle.NO_BORDER);

				//add student name
				studentLeft.addCell(mainGradeInfoCell);



				//rank 7 subjects 


				double mean = 0;

				//TODO
				String termPosition = "";


				String classPositionMSG = "";
				String streamPositionMSG = "";

				if(classResult){



					classPositionMSG = pos + " Out of : " + performanceList.size();

					streamPositionMSG = ReportUtil.getStreamPosition(accountId, student.getUuid(), student.getCurrentStream(), 
							performanceList,rankWithPoints,rankWithTotalMarks); 


				}else{

					if(!classResult){

						if(!classperformanceList.isEmpty()){
							classPositionMSG = ReportUtil.getClassPosition(accountId, student.getUuid() , 
									classperformanceList,rankWithPoints,rankWithTotalMarks);
						}
					}


					streamPositionMSG = pos + " Out of : " + performanceList.size();

				}


				termPosition = classPositionMSG;

				if(grade7subjects && !grade11subjects){


					mean = (double)meanTotal / 7;

					String studentScore = "";

					if(rankWithPoints && !rankWithTotalMarks){


						if(StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE)){

							studentScore = avgPoints + " /84 (" + avg_points_grade + ")";

						}else {

							studentScore = mainPoint + " /84 (" + ReportUtil.getGradeMainForm234(mainPoint, 
									accountId, gradingSystemDAO) + ")";

						}


						YearlyMean yearlyMean;
						if(yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), year) == null) {
							yearlyMean = new YearlyMean();
						}else {
							yearlyMean = yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), year);
						}

						yearlyMean.setAccountId(accountId);
						yearlyMean.setStudentId(student.getUuid());
						yearlyMean.setYear(year);

						if(StringUtils.equals(term, "1")){

							yearlyMean.setMeanOne(mainPoint * ReportUtil.STD_CONSTANT);
							yearlyMean.setTermOnePosition(termPosition);

						}
						if(StringUtils.equals(term, "2")){

							yearlyMean.setMeanTwo(mainPoint * ReportUtil.STD_CONSTANT);
							yearlyMean.setTermTwoPosition(termPosition);

						}
						if(StringUtils.equals(term, "3")){

							yearlyMean.setMeanThree(mainPoint * ReportUtil.STD_CONSTANT);
							yearlyMean.setTermThreePosition(termPosition);

						}

						//TODO
						if(StringUtils.equals(saveMean, "1")) {
							yearlyMeanDAO.putYearlyMean(yearlyMean, accountId, student.getUuid(), classroomId, year);
							
						}

					}

					if(!rankWithPoints && rankWithTotalMarks){

						studentScore = "Total: " + meanTotal + "/700 , Avg: " + ReportUtil.df2.format(mean) +" , " + 
								ReportUtil.getGradeMainForm234((int)Math.round(mean), 
										accountId, gradingSystemDAO);


						YearlyMean yearlyMean;
						if(yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), year) == null) {
							yearlyMean = new YearlyMean();
						}else {
							yearlyMean = yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), year);
						}

						yearlyMean.setAccountId(accountId);
						yearlyMean.setStudentId(student.getUuid());
						yearlyMean.setYear(year);

						if(StringUtils.equals(term, "1")){

							yearlyMean.setMeanOne(Double.valueOf(ReportUtil.df2.format(mean))); 
							yearlyMean.setTermOnePosition(termPosition);

						}
						if(StringUtils.equals(term, "2")){

							yearlyMean.setMeanTwo(Double.valueOf(ReportUtil.df2.format(mean)));
							yearlyMean.setTermTwoPosition(termPosition);

						}
						if(StringUtils.equals(term, "3")){

							yearlyMean.setMeanThree(Double.valueOf(ReportUtil.df2.format(mean)));
							yearlyMean.setTermThreePosition(termPosition);

						}

						//TODO
						if(StringUtils.equals(saveMean, "1")) {
							yearlyMeanDAO.putYearlyMean(yearlyMean, accountId, student.getUuid(), classroomId, year);
						}



					}


					PdfPCell mainGradeDescCell = new PdfPCell(new Phrase(studentScore ,  timesRomanNarmal6));				
					mainGradeInfoCell.setBorder(Rectangle.NO_BORDER);
					mainGradeDescCell.setBorder(Rectangle.NO_BORDER);
					studentLeft.addCell(mainGradeDescCell);


				}

				//rank 11 subjects 
				if(!grade7subjects && grade11subjects){


					mean = (double)meanTotal / 11; 

					String studentScore = "";

					double avg = ((double)mainPoint / 132) * 84;


					if(rankWithPoints && !rankWithTotalMarks){

						if(StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE)){

							studentScore = avgPoints + " /84 (" + avg_points_grade + ")";

						}else {
							studentScore = Math.round(avg) + " /84 (" + ReportUtil.getGradeMainForm234((int)Math.round(avg), 
									accountId, gradingSystemDAO) + ")";


						}

					}

					if(!rankWithPoints && rankWithTotalMarks){

						studentScore = "Total: " + meanTotal + "/1100 , Avg: " + ReportUtil.df2.format(mean) +" , " + 
								ReportUtil.getGradeMainForm234((int)Math.round(mean), 
										accountId, gradingSystemDAO);
					}

					PdfPCell mainGradeDescCell = new PdfPCell(new Phrase(studentScore ,  timesRomanNarmal6));
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
				PdfPCell classGradeDescCell = new PdfPCell(new Phrase(classPositionMSG,timesRomanNarmal6));
				classGradeInfoCell.setBorder(Rectangle.NO_BORDER);
				classGradeDescCell.setBorder(Rectangle.NO_BORDER);
				//add student name
				studentRight.addCell(classGradeInfoCell);
				studentRight.addCell(classGradeDescCell);

				//student stream position
				PdfPCell streamGradeInfoCell = new PdfPCell(new Phrase("Stream position:",timesRomanNarmal8)); 
				PdfPCell streamGradeDescCell = new PdfPCell(new Phrase(streamPositionMSG,timesRomanNarmal6));
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

				String exam11 = "";
				String exam22 = "";
				String exam33 = "";

				if(exams.length == 1){

					exam11 = examDAO.getExam(accountId, exams[0]) != null ? examDAO.getExam(accountId, exams[0]).getDescription() : "";

				}

				if(exams.length == 2){

					exam11 = examDAO.getExam(accountId, exams[0]) != null ? examDAO.getExam(accountId, exams[0]).getDescription() : "";
					exam22 = examDAO.getExam(accountId, exams[1]) != null ? examDAO.getExam(accountId, exams[1]).getDescription() : "";

				}

				if(exams.length == 3){

					exam11 = examDAO.getExam(accountId, exams[0]) != null ? examDAO.getExam(accountId, exams[0]).getDescription() : "";
					exam22 = examDAO.getExam(accountId, exams[1]) != null ? examDAO.getExam(accountId, exams[1]).getDescription() : "";
					exam33 = examDAO.getExam(accountId, exams[2]) != null ? examDAO.getExam(accountId, exams[2]).getDescription() : "";

				}

				PdfPCell jubjectCell = new PdfPCell(new Paragraph("Subject",timesRomanNarmal8));
				jubjectCell.setBackgroundColor(baseColor);
				jubjectCell.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell examCell1 = new PdfPCell(new Paragraph(exam11,timesRomanNarmal8));
				examCell1.setBackgroundColor(baseColor);
				examCell1.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell examCell2 = new PdfPCell(new Paragraph(exam22,timesRomanNarmal8));
				examCell2.setBackgroundColor(baseColor);
				examCell2.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell examCell3 = new PdfPCell(new Paragraph(exam33,timesRomanNarmal8));
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


				List<FailedSubject> failedSubjects = new ArrayList<>();


				subjects.forEach(subject -> {

					FailedSubject failedSubject = new FailedSubject();

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

					String examAverage = ReportUtil.findExamAverage(subject,exam1Score,exam2Score,exam3Score, exams.length,examType);

					if(Integer.valueOf(examAverage) > 0){
						failedSubject.setScore(Integer.valueOf(examAverage));
						failedSubject.setStudentId(student.getUuid());
						failedSubject.setSubjectCode(subject.getCode());

						failedSubjects.add(failedSubject);
					}



					String avgrade = ReportUtil.getGrade(examAverage,subject.getUuid(), accountId, subjectDAO, gradingSystemDAO);
					String avgpoints = String.valueOf(ReportUtil.getPoints(examAverage, subject.getUuid(),accountId,subjectDAO, gradingSystemDAO));

					String remarks = ReportUtil.getRemarks(examAverage,subject.getUuid(),accountId); 

					String exam1Grade = ReportUtil.getGrade(exam1Score,subject.getUuid(),accountId, subjectDAO, gradingSystemDAO);
					String exam1Points = String.valueOf(ReportUtil.getPoints(exam1Score, subject.getUuid(), accountId, subjectDAO, gradingSystemDAO));

					exam1Points = StringUtils.equals(exam1Points, "0") ? "" : exam1Points;

					String exam2Grade = ReportUtil.getGrade(exam2Score,subject.getUuid(), accountId, subjectDAO, gradingSystemDAO);
					String exam2Points = String.valueOf(ReportUtil.getPoints(exam2Score, subject.getUuid(), accountId, subjectDAO, gradingSystemDAO));

					exam2Points = StringUtils.equals(exam2Points, "0") ? "" : exam2Points;

					String exam3Grade = ReportUtil.getGrade(exam3Score,subject.getUuid(), accountId, subjectDAO, gradingSystemDAO);
					String exam3Points = String.valueOf(ReportUtil.getPoints(exam3Score, subject.getUuid(), accountId, subjectDAO, gradingSystemDAO));

					exam3Points = StringUtils.equals(exam3Points, "0") ? "" : exam3Points;

					String teacherId = "";					
					teacherId = ReportUtil.getInitials(accountId,streamId,subject.getUuid(),teacherSubjectDAO);
					Staff staff = staffDAO.getStaff(accountId, teacherId) != null ? staffDAO.getStaff(accountId, teacherId) : new Staff();

					String initialName = "";
					initialName += staff.getFirstname().length() > 1 ? staff.getFirstname().substring(0, 1)+"." : ""; 
					initialName += staff.getMiddlename().length() > 1 ? staff.getMiddlename().substring(0, 1)+"." : ""; 
					initialName += staff.getLastname().length() > 1 ? staff.getLastname().substring(0, 1)+"." : ""; 


					String initials = initialName.toUpperCase(); 


					examTable.addCell(new Paragraph(subject.getDescription(),timesRomanNarmal6));

					examAverage = StringUtils.equals(examAverage, "0") ? "" : examAverage;
					avgpoints = StringUtils.equals(avgpoints, "0") ? "" : avgpoints;

					String score1 = exam1Score + " " + exam1Grade +  " " + exam1Points; 
					String score2 = exam2Score + " " + exam2Grade +  " " + exam2Points;
					String score3 = exam3Score + " " + exam3Grade +  " " + exam3Points;
					String average = examAverage + " " + avgrade +  " " + avgpoints;


					if(StringUtils.equals(ReportUtil.EXAM_TYPE, examType)){

						score1 = exam1Score;
						score2 = exam2Score;
						score3 = exam3Score;


					}else{


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




					}




					examTable.addCell(new Paragraph(" " + score1,timesRomanNarmal6));
					examTable.addCell(new Paragraph(" " + score2,timesRomanNarmal6));
					examTable.addCell(new Paragraph(" " + score3,timesRomanNarmal6));




					examTable.addCell(new Paragraph(" " + average,timesRomanNarmal6));
					examTable.addCell(new Paragraph(" " + remarks,timesRomanNarmal6));
					examTable.addCell(new Paragraph(initials,timesRomanNarmal6));


				});

				String[] headers = { "TOTAL", "MEAN GRADE", "MEAN SCORE"};
				int count = 0;



				for(String header : headers){

					examTable.addCell(new Paragraph(header,timesRomanNarmal8));



					String exm1 = "0";
					String exm2 = "0";
					String exm3 = "0";

					exm1 = String.valueOf(performance2.getExam1TotalPoints());
					exm2 = String.valueOf(performance2.getExam2TotalPoints());
					exm3 = String.valueOf(performance2.getExam3TotalPoints());

					////mean

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



					//mean
					String meanStr = ReportUtil.df2.format(mean); 



					double avg = ((double)mainPoint / 132) * 84;
					String mainScore = "";



					if(StringUtils.equals(ReportUtil.EXAM_TYPE, examType)){

						exm1 = ""; exm2 = ""; exm3 = "";
						ex1Grade = ""; ex2Grade = ""; ex3Grade = "";
						exa1Point = ""; exa2Point = ""; exa3Point = "";

					}



					//TOTAL
					if(count == 0){ 
						examTable.addCell(new Paragraph(" "+exm1 ,timesRomanNarmal6));
						examTable.addCell(new Paragraph(" "+exm2 ,timesRomanNarmal6));
						examTable.addCell(new Paragraph(" "+exm3 ,timesRomanNarmal6));



						//studentScore = avgPoints + " /84 (" + avg_points_grade + ")";

						if(grade7subjects && !grade11subjects){

							if(StringUtils.equals(ReportUtil.EXAM_TYPE, examType)){
								mainScore = avgPoints;

							}else {
								mainScore = (int)Math.round(mainPoint) + "";

							}



						}

						if(!grade7subjects && grade11subjects){
							mainScore = (int)Math.round(avg) + "";
						}

						examTable.addCell(new Paragraph(meanStr + " , " + mainScore ,timesRomanNarmal6)); 


					}
					//MEAN GRADE   
					else if(count == 1){ 
						examTable.addCell(new Paragraph(" "+ex1Grade ,timesRomanNarmal6));
						examTable.addCell(new Paragraph(" "+ex2Grade ,timesRomanNarmal6));
						examTable.addCell(new Paragraph(" "+ex3Grade ,timesRomanNarmal6));


						String mainExam = "";


						//rank 7 subjects
						if(grade7subjects && !grade11subjects){


							if(rankWithPoints && !rankWithTotalMarks){

								if(StringUtils.equals(ReportUtil.EXAM_TYPE, examType)){

									mainExam = avg_points_grade;

								}else {
									mainExam = ReportUtil.getGradeMainForm234(mainPoint, accountId, gradingSystemDAO);

								}
							}

							if(!rankWithPoints && rankWithTotalMarks){


								mainExam = ReportUtil.getGradeMainForm234((int)Math.round(mean), accountId, gradingSystemDAO);

							}


							examTable.addCell(new Paragraph(" "+mainExam  ,timesRomanNarmal6));

						}

						//rank 11 subjects
						if(!grade7subjects && grade11subjects){


							if(rankWithPoints && !rankWithTotalMarks){

								mainExam = ReportUtil.getGradeMainForm234((int)Math.round(avg), accountId, gradingSystemDAO);

							}

							if(!rankWithPoints && rankWithTotalMarks){

								mainExam = ReportUtil.getGradeMainForm234((int)Math.round(mean), accountId, gradingSystemDAO);

							}


							examTable.addCell(new Paragraph(" "+mainExam  ,timesRomanNarmal6));

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




				Chunk underline = new Chunk("GENERAL COMMENTS. ", timesRomanNarmal8);
				underline.setUnderline(0.1f, -2f); // 0.1 thick, -2


				String closingDate = miscellanousDAO.getValueByKey(accountId, "CLOSING_DATE") !=null ?
						miscellanousDAO.getValueByKey(accountId, "CLOSING_DATE") : "";

						String openingdate = miscellanousDAO.getValueByKey(accountId, "OPENING_DATE") !=null ?
								miscellanousDAO.getValueByKey(accountId, "OPENING_DATE") : "";

								String headteacherRemarks = "Thanks " + student.getFirstname().toUpperCase() + " ";
								headteacherRemarks += miscellanousDAO.getValueByKey(accountId, "HEAD_TEACHER_REMARKS")!= null
										? miscellanousDAO.getValueByKey(accountId, "HEAD_TEACHER_REMARKS") : "";


										String classTeacherRemarks = ReportUtil.getclassTeacherComment(accountId,total,failedSubjects);

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
										footerTable.setWidthPercentage(100); 
										footerTable.setWidths(new int[]{70,30}); 

										PdfPTable graphTable = new PdfPTable(1);
										graphTable.setWidthPercentage(70); 
										graphTable.setWidths(new int[]{70});    


										//fee info table
										PdfPTable feeInfoTable = new PdfPTable(2);
										feeInfoTable.setWidthPercentage(30); 
										feeInfoTable.setWidths(new int[]{15,15}); 


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


										Locale locale = new Locale("en","KE"); 
										NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

										StudentBalance balance = new StudentBalance();
										double feeBalance = balance.findBalance(accountId, student.getUuid());

										String feeBal = nf.format(feeBalance);

										String nextTermFee = balance.findNextTermFee(accountId); 

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


										ByteArrayOutputStream byte_out = new ByteArrayOutputStream();
										JFreeChart lineGraph = ReportUtil.generateLineGraph(accountId,student.getUuid() ,yearlyMeanDAO, studentDAO, graphType);  

										try {

											ChartUtilities.writeChartAsPNG(byte_out, lineGraph, 1200, 270);
											byte [] data = byte_out.toByteArray();
											byte_out.close();
											Image chartImage = Image.getInstance(data);
											graphTable.addCell(chartImage); 

										} catch (IOException e) {
											e.printStackTrace();
										}




										footerTable.addCell(graphTable);
										footerTable.addCell(feeInfoTable);


										//grade(s) table 
										PdfPTable gradesTable = new PdfPTable(12);
										gradesTable.setWidthPercentage(96);  
										gradesTable.setWidths(new int[]{8,8,8,8,8,8,8,8,8,8,8,8}); 

										gradingSystemList.forEach(grade ->{

											PdfPCell cell = new PdfPCell(new Phrase(grade.getLowerLimit() + " - " + grade.getUpperLimit() , timesRomanNarmal4)); 
											cell.setHorizontalAlignment(Element.ALIGN_LEFT);

											gradesTable.addCell(cell);

										});


										gradingSystemList.forEach(grade ->{

											PdfPCell cell = new PdfPCell(new Phrase(grade.getDescription(), timesRomanNarmal4));  
											cell.setHorizontalAlignment(Element.ALIGN_LEFT);

											gradesTable.addCell(cell);

										});
										//end grade table



										position++;
										prevtotal=total;


										PdfPTable perfTable = new PdfPTable(9);   
										perfTable.setWidthPercentage(90); 
										perfTable.setWidths(new int[]{8,10,10,10,10,10,10,10,10});  
										//timesRomanNarmal6White.setColor(BaseColor.WHITE); 
										//*************
										PdfPCell termCell1 = new PdfPCell(new Paragraph("Term", timesRomanNarmal4));
										termCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
										termCell1.setBackgroundColor(baseColor);

										PdfPCell meanCell1 = new PdfPCell(new Paragraph("Mean", timesRomanNarmal4));
										meanCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
										meanCell1.setBackgroundColor(baseColor);

										PdfPCell posCell1 = new PdfPCell(new Paragraph("Position", timesRomanNarmal4));
										posCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
										posCell1.setBackgroundColor(baseColor);
										//*************
										PdfPCell meanCell2 = new PdfPCell(new Paragraph("Mean", timesRomanNarmal4));
										meanCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
										meanCell2.setBackgroundColor(baseColor);

										PdfPCell posCell2 = new PdfPCell(new Paragraph("Position", timesRomanNarmal4));
										posCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
										posCell2.setBackgroundColor(baseColor);
										//*************
										PdfPCell meanCell3 = new PdfPCell(new Paragraph("Mean", timesRomanNarmal4));
										meanCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
										meanCell3.setBackgroundColor(baseColor);

										PdfPCell posCell3 = new PdfPCell(new Paragraph("Position", timesRomanNarmal4));
										posCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
										posCell3.setBackgroundColor(baseColor);
										//*************
										PdfPCell meanCell4 = new PdfPCell(new Paragraph("Mean", timesRomanNarmal4));
										meanCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
										meanCell4.setBackgroundColor(baseColor);

										PdfPCell posCell4 = new PdfPCell(new Paragraph("Position", timesRomanNarmal4));
										posCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
										posCell4.setBackgroundColor(baseColor);

										PdfPTable titleTable = new PdfPTable(5);   
										titleTable.setWidthPercentage(90); 
										titleTable.setWidths(new int[]{8,20,20,20,20});   

										PdfPCell cell0 = new PdfPCell(new Paragraph("", timesRomanNarmal6));
										cell0.setHorizontalAlignment(Element.ALIGN_LEFT);
										//cell0.setBackgroundColor(baseColor);

										PdfPCell cell1 = new PdfPCell(new Paragraph("Form 1", timesRomanNarmal6));
										cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
										cell1.setBackgroundColor(baseColor);

										PdfPCell cell2 = new PdfPCell(new Paragraph("Form 2", timesRomanNarmal6));
										cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
										cell2.setBackgroundColor(baseColor);

										PdfPCell cell3 = new PdfPCell(new Paragraph("Form 3", timesRomanNarmal6));
										cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
										cell3.setBackgroundColor(baseColor);

										PdfPCell cell4 = new PdfPCell(new Paragraph("Form 4", timesRomanNarmal6));
										cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
										cell4.setBackgroundColor(baseColor);

										titleTable.addCell(cell0);
										titleTable.addCell(cell1);
										titleTable.addCell(cell2); 
										titleTable.addCell(cell3);
										titleTable.addCell(cell4);



										perfTable.addCell(termCell1);

										perfTable.addCell(meanCell1);
										perfTable.addCell(posCell1);

										perfTable.addCell(meanCell2);
										perfTable.addCell(posCell2);

										perfTable.addCell(meanCell3);
										perfTable.addCell(posCell3);

										perfTable.addCell(meanCell4);
										perfTable.addCell(posCell4);

										//add performance table TODO

										PerformanceTable performanceT = new PerformanceTable();


										List<YearlyMean> yearlyMeanList  = new ArrayList<>();
										if(!yearlyMeanDAO.getYearlyMean(accountId, performance2.getStudentId()).isEmpty()) {
											yearlyMeanList = yearlyMeanDAO.getYearlyMean(accountId, performance2.getStudentId());
										}

										if(!yearlyMeanList.isEmpty()) {

											yearlyMeanList.parallelStream().forEach(yearlymean ->{
												
												if(StringUtils.equals(yearlymean.getClassId(), "C143978A-E021-4015-BC67-5A00D6C910D1")) {
													
													TermOneObj t1 = new TermOneObj();
													Forms forms_t1 = new Forms(); ///
													
													FormOne formone_t1 = new FormOne(); 
													FormOne formone_t2 = new FormOne(); 
													FormOne formone_t3 = new FormOne(); 
													
													MP mp_t1 = new MP();
													mp_t1.setMean(yearlymean.getMeanOne()+""); 
													mp_t1.setPos(yearlymean.getTermOnePosition()); 
													formone_t1.setMp(mp_t1);
													forms_t1.setFormOne(formone_t1);
													t1.setForms(forms_t1); 


													TermTwoObj t2 = new TermTwoObj();
													Forms forms_t2 = new Forms();
													
													MP mp_t2 = new MP();
													mp_t2.setMean(yearlymean.getMeanTwo()+""); 
													mp_t2.setPos(yearlymean.getTermTwoPosition()); 
													formone_t2.setMp(mp_t2);
													forms_t2.setFormOne(formone_t2);
													t2.setForms(forms_t2); 
													
													
													TermThreeObj t3 = new TermThreeObj();
													Forms forms_t3 = new Forms();
													
													MP mp_t3 = new MP();
													mp_t3.setMean(yearlymean.getMeanThree()+""); 
													mp_t3.setPos(yearlymean.getTermThreePosition()); 
													formone_t3.setMp(mp_t3);
													forms_t3.setFormOne(formone_t3);
													t3.setForms(forms_t3); 

													performanceT.setTermOneObj(t1);
													performanceT.setTermTwoObj(t2);
													performanceT.setTermThreeObj(t3);
													
												}else if(StringUtils.equals(yearlymean.getClassId(), "3E22E428-3155-42F5-B73E-66553ED501C9")) {
													

													
													TermOneObj t1 = new TermOneObj();
													Forms forms_t1 = new Forms(); ///
													
													FormTwo formtwo_t1 = new FormTwo(); 
													FormTwo formtwo_t2 = new FormTwo(); 
													FormTwo formtwo_t3 = new FormTwo(); 
													
													MP mp_t1 = new MP();
													mp_t1.setMean(yearlymean.getMeanOne()+""); 
													mp_t1.setPos(yearlymean.getTermOnePosition()); 
													formtwo_t1.setMp(mp_t1);
													forms_t1.setFormTwo(formtwo_t1);
													t1.setForms(forms_t1); 


													TermTwoObj t2 = new TermTwoObj();
													Forms forms_t2 = new Forms();
													
													MP mp_t2 = new MP();
													mp_t2.setMean(yearlymean.getMeanTwo()+""); 
													mp_t2.setPos(yearlymean.getTermTwoPosition()); 
													formtwo_t2.setMp(mp_t2);
													forms_t2.setFormTwo(formtwo_t2);
													t2.setForms(forms_t2); 
													
													
													TermThreeObj t3 = new TermThreeObj();
													Forms forms_t3 = new Forms();
													
													MP mp_t3 = new MP();
													mp_t3.setMean(yearlymean.getMeanThree()+""); 
													mp_t3.setPos(yearlymean.getTermThreePosition()); 
													formtwo_t3.setMp(mp_t3);
													forms_t3.setFormTwo(formtwo_t3);
													t3.setForms(forms_t3); 

													performanceT.setTermOneObj(t1);
													performanceT.setTermTwoObj(t2);
													performanceT.setTermThreeObj(t3);
													
												
													
												}else if(StringUtils.equals(yearlymean.getClassId(), "A4BFC2BD-262F-4207-99C8-057D6ADF80C7")) {
													
													TermOneObj t1 = new TermOneObj();
													Forms forms_t1 = new Forms(); ///
													
													FormThree formthree_t1 = new FormThree(); 
													FormThree formthree_t2 = new FormThree(); 
													FormThree formthree_t3 = new FormThree(); 
													
													MP mp_t1 = new MP();
													mp_t1.setMean(yearlymean.getMeanOne()+""); 
													mp_t1.setPos(yearlymean.getTermOnePosition()); 
													formthree_t1.setMp(mp_t1);
													forms_t1.setFormThree(formthree_t1);
													t1.setForms(forms_t1); 


													TermTwoObj t2 = new TermTwoObj();
													Forms forms_t2 = new Forms();
													
													MP mp_t2 = new MP();
													mp_t2.setMean(yearlymean.getMeanTwo()+""); 
													mp_t2.setPos(yearlymean.getTermTwoPosition()); 
													formthree_t2.setMp(mp_t2);
													forms_t2.setFormThree(formthree_t2);
													t2.setForms(forms_t2); 
													
													
													TermThreeObj t3 = new TermThreeObj();
													Forms forms_t3 = new Forms();
													
													MP mp_t3 = new MP();
													mp_t3.setMean(yearlymean.getMeanThree()+""); 
													mp_t3.setPos(yearlymean.getTermThreePosition()); 
													formthree_t3.setMp(mp_t3);
													forms_t3.setFormThree(formthree_t3);
													t3.setForms(forms_t3); 

													performanceT.setTermOneObj(t1);
													performanceT.setTermTwoObj(t2);
													performanceT.setTermThreeObj(t3);
													
												}else if(StringUtils.equals(yearlymean.getClassId(), "14E56350-08DA-45CC-97D9-C225AF74A7AD")) {
													
													TermOneObj t1 = new TermOneObj();
													Forms forms_t1 = new Forms(); ///
													
													FormFour formfour_t1 = new FormFour(); 
													FormFour formfour_t2 = new FormFour(); 
													FormFour formfour_t3 = new FormFour(); 
													
													MP mp_t1 = new MP();
													mp_t1.setMean(yearlymean.getMeanOne()+""); 
													mp_t1.setPos(yearlymean.getTermOnePosition()); 
													formfour_t1.setMp(mp_t1);
													forms_t1.setFormFour(formfour_t1);
													t1.setForms(forms_t1); 


													TermTwoObj t2 = new TermTwoObj();
													Forms forms_t2 = new Forms();
													
													MP mp_t2 = new MP();
													mp_t2.setMean(yearlymean.getMeanTwo()+""); 
													mp_t2.setPos(yearlymean.getTermTwoPosition()); 
													formfour_t2.setMp(mp_t2);
													forms_t2.setFormFour(formfour_t2);
													t2.setForms(forms_t2); 
													
													
													TermThreeObj t3 = new TermThreeObj();
													Forms forms_t3 = new Forms();
													
													MP mp_t3 = new MP();
													mp_t3.setMean(yearlymean.getMeanThree()+""); 
													mp_t3.setPos(yearlymean.getTermThreePosition()); 
													formfour_t3.setMp(mp_t3);
													forms_t3.setFormFour(formfour_t3);
													t3.setForms(forms_t3); 

													performanceT.setTermOneObj(t1);
													performanceT.setTermTwoObj(t2);
													performanceT.setTermThreeObj(t3);
													
												}
											
											});
										}

										generateTable(perfTable, performanceT);







										document.add(examTable);

										document.add(gradesTable);
										//document.add(new Paragraph("\n"));

										document.add(underline);

										document.add(new Paragraph(teacherremarkphrase));

										document.add(new Paragraph(headteacherremarkphrase));

										document.add(new Paragraph(datesphrase));

										document.add(new Paragraph(signaturephrase));

										document.add(new Paragraph("\n"));

										document.add(footerTable);
										document.add(titleTable);
										document.add(perfTable);


										document.newPage();




			}// end  for loop


		}else{
			document.add(new Paragraph(" Exam not found for " + correctClass)); 

		}




	}


	/**
	 * @param perfTable
	 * @param performanceT 
	 */
	private void generateTable(PdfPTable perfTable, PerformanceTable performanceT) {
		
	//	System.out.println(performanceT); 
		//*****************************************TERM 1
		PdfPCell t1_termCell = new PdfPCell(new Paragraph("1" ,timesRomanNarmal4));											
		t1_termCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t1_form1Mean = new PdfPCell(new Paragraph( performanceT.getTermOneObj().getForms().getFormOne().getMp().getMean() ,timesRomanNarmal4));											
		t1_form1Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t1_form1Position = new PdfPCell(new Paragraph(performanceT.getTermOneObj().getForms().getFormOne().getMp().getPos() ,timesRomanNarmal4));											
		t1_form1Position.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t1_form2Mean = new PdfPCell(new Paragraph(performanceT.getTermOneObj().getForms().getFormTwo().getMp().getMean() ,timesRomanNarmal4));											
		t1_form2Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t1_form2Position = new PdfPCell(new Paragraph(performanceT.getTermOneObj().getForms().getFormTwo().getMp().getPos() ,timesRomanNarmal4));											
		t1_form2Position.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t1_form3Mean = new PdfPCell(new Paragraph(performanceT.getTermOneObj().getForms().getFormThree().getMp().getMean() ,timesRomanNarmal4));											
		t1_form3Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t1_form3Position = new PdfPCell(new Paragraph(performanceT.getTermOneObj().getForms().getFormThree().getMp().getPos() ,timesRomanNarmal4));											
		t1_form3Position.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t1_form4Mean = new PdfPCell(new Paragraph(performanceT.getTermOneObj().getForms().getFormFour().getMp().getMean() ,timesRomanNarmal4));											
		t1_form4Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t1_form4Position = new PdfPCell(new Paragraph(performanceT.getTermOneObj().getForms().getFormFour().getMp().getPos() ,timesRomanNarmal4));											
		t1_form4Position.setHorizontalAlignment(Element.ALIGN_LEFT);


		perfTable.addCell(t1_termCell);

		perfTable.addCell(t1_form1Mean);
		perfTable.addCell(t1_form1Position);

		perfTable.addCell(t1_form2Mean);
		perfTable.addCell(t1_form2Position);

		perfTable.addCell(t1_form3Mean);
		perfTable.addCell(t1_form3Position);

		perfTable.addCell(t1_form4Mean);
		perfTable.addCell(t1_form4Position);
		//*****************************************TERM 1 END
		//*****************************************TERM 2
		PdfPCell t2_termCell = new PdfPCell(new Paragraph("2" ,timesRomanNarmal4));											
		t2_termCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t2_form1Mean = new PdfPCell(new Paragraph(performanceT.getTermTwoObj().getForms().getFormOne().getMp().getMean() ,timesRomanNarmal4));											
		t2_form1Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t2_form1Position = new PdfPCell(new Paragraph(performanceT.getTermTwoObj().getForms().getFormOne().getMp().getPos() ,timesRomanNarmal4));											
		t2_form1Position.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t2_form2Mean = new PdfPCell(new Paragraph(performanceT.getTermTwoObj().getForms().getFormTwo().getMp().getMean() ,timesRomanNarmal4));											
		t2_form2Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t2_form2Position = new PdfPCell(new Paragraph(performanceT.getTermTwoObj().getForms().getFormTwo().getMp().getPos() ,timesRomanNarmal4));											
		t2_form2Position.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t2_form3Mean = new PdfPCell(new Paragraph(performanceT.getTermTwoObj().getForms().getFormThree().getMp().getMean() ,timesRomanNarmal4));											
		t2_form3Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t2_form3Position = new PdfPCell(new Paragraph(performanceT.getTermTwoObj().getForms().getFormThree().getMp().getPos() ,timesRomanNarmal4));											
		t2_form3Position.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t2_form4Mean = new PdfPCell(new Paragraph(performanceT.getTermTwoObj().getForms().getFormFour().getMp().getMean() ,timesRomanNarmal4));											
		t2_form4Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t2_form4Position = new PdfPCell(new Paragraph(performanceT.getTermTwoObj().getForms().getFormFour().getMp().getPos() ,timesRomanNarmal4));											
		t2_form4Position.setHorizontalAlignment(Element.ALIGN_LEFT);


		perfTable.addCell(t2_termCell);

		perfTable.addCell(t2_form1Mean);
		perfTable.addCell(t2_form1Position);

		perfTable.addCell(t2_form2Mean);
		perfTable.addCell(t2_form2Position);

		perfTable.addCell(t2_form3Mean);
		perfTable.addCell(t2_form3Position);

		perfTable.addCell(t2_form4Mean);
		perfTable.addCell(t2_form4Position);
		//*****************************************TERM 2 END
		//*****************************************TERM 3
		PdfPCell t3_termCell = new PdfPCell(new Paragraph("3" ,timesRomanNarmal4));											
		t3_termCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t3_form1Mean = new PdfPCell(new Paragraph(performanceT.getTermThreeObj().getForms().getFormOne().getMp().getMean() ,timesRomanNarmal4));											
		t3_form1Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t3_form1Position = new PdfPCell(new Paragraph(performanceT.getTermThreeObj().getForms().getFormOne().getMp().getPos() ,timesRomanNarmal4));											
		t3_form1Position.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t3_form2Mean = new PdfPCell(new Paragraph(performanceT.getTermThreeObj().getForms().getFormTwo().getMp().getMean() ,timesRomanNarmal4));											
		t3_form2Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t3_form2Position = new PdfPCell(new Paragraph(performanceT.getTermThreeObj().getForms().getFormTwo().getMp().getPos() ,timesRomanNarmal4));											
		t3_form2Position.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t3_form3Mean = new PdfPCell(new Paragraph(performanceT.getTermThreeObj().getForms().getFormThree().getMp().getMean() ,timesRomanNarmal4));											
		t3_form3Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t3_form3Position = new PdfPCell(new Paragraph(performanceT.getTermThreeObj().getForms().getFormThree().getMp().getPos() ,timesRomanNarmal4));											
		t3_form3Position.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t3_form4Mean = new PdfPCell(new Paragraph(performanceT.getTermThreeObj().getForms().getFormFour().getMp().getMean(),timesRomanNarmal4));											
		t3_form4Mean.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell t3_form4Position = new PdfPCell(new Paragraph(performanceT.getTermThreeObj().getForms().getFormFour().getMp().getPos(),timesRomanNarmal4));											
		t3_form4Position.setHorizontalAlignment(Element.ALIGN_LEFT);


		perfTable.addCell(t3_termCell);

		perfTable.addCell(t3_form1Mean);
		perfTable.addCell(t3_form1Position);

		perfTable.addCell(t3_form2Mean);
		perfTable.addCell(t3_form2Position);

		perfTable.addCell(t3_form3Mean);
		perfTable.addCell(t3_form3Position);

		perfTable.addCell(t3_form4Mean);
		perfTable.addCell(t3_form4Position);
		//*****************************************TERM 3 END
	}


	/**
	 * @param accountId
	 * @param streamId
	 * @param term
	 * @param year
	 * @param studentsList
	 */
	private  List<Performance2> getStudentScore3(String accountId,String class_streamId, String term, String year,
			List<Student> studentsList, String examType, boolean classResult, String paper123Id) {

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

				if(classResult){

					if(StringUtils.equals(paper123Id, ReportUtil.PAPER123ID)) {

						exam1 = perfomanceDAO.getClassPerformance(accountId, ReportUtil.PAPER123ID, student.getUuid(), class_streamId, term, year);

					}else {

						exam1 = perfomanceDAO.getClassPerformance(accountId, exams[0], student.getUuid(), class_streamId, term, year);
						exam2 = perfomanceDAO.getClassPerformance(accountId, exams[1], student.getUuid(), class_streamId, term, year);
						exam3 = perfomanceDAO.getClassPerformance(accountId, exams[2], student.getUuid(), class_streamId, term, year); 

					}




				}else{

					if(StringUtils.equals(paper123Id, ReportUtil.PAPER123ID)) {

						exam1 = perfomanceDAO.getStreamPerformance(accountId, ReportUtil.PAPER123ID, student.getUuid(), class_streamId, term, year);

					}else {

						exam1 = perfomanceDAO.getStreamPerformance(accountId, exams[0], student.getUuid(), class_streamId, term, year);
						exam2 = perfomanceDAO.getStreamPerformance(accountId, exams[1], student.getUuid(), class_streamId, term, year);
						exam3 = perfomanceDAO.getStreamPerformance(accountId, exams[2], student.getUuid(), class_streamId, term, year); 	


					}

				}


				//rank 7 subjects
				if(grade7subjects && !grade11subjects){

					if(StringUtils.equals(paper123Id, ReportUtil.PAPER123ID)) {

						//if(!exam1.isEmpty()) {
						totalExam1 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO, examDAO, examType);
						//}


					}else {
						totalExam1 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO, examDAO, examType);
						totalExam2 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), exam2, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO, examDAO, examType);
						totalExam3 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), exam3, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO, examDAO, examType);


					}

				}


				//rank 11 subjects
				if(!grade7subjects && grade11subjects){

					if(StringUtils.equals(paper123Id, ReportUtil.PAPER123ID)) {

						if(!exam1.isEmpty()) {
							totalExam1 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO, examDAO, examType);
						}


					}else {

						totalExam1 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
						totalExam2 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), exam2, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
						totalExam3 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), exam3, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);

					}

				}


				if(StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE) || StringUtils.equals(paper123Id, ReportUtil.PAPER123ID)){

					totalPoint = totalExam1.getTotalPoints();
					totalMeans = totalExam1.getTotalMean();




				}else {
					totalPoint = totalExam1.getTotalPoints() + totalExam2.getTotalPoints() + totalExam3.getTotalPoints();
					totalPoint = totalPoint / 3;

					totalMeans = totalExam1.getTotalMean() + totalExam2.getTotalMean() + totalExam3.getTotalMean();
					totalMeans = totalMeans / 3;



				}


			}

			if(exams.length == 2){



				if(classResult){

					exam1 = perfomanceDAO.getClassPerformance(accountId, exams[0], student.getUuid(), class_streamId, term, year);
					exam2 = perfomanceDAO.getClassPerformance(accountId, exams[1], student.getUuid(), class_streamId, term, year);

				}else{

					exam1 = perfomanceDAO.getStreamPerformance(accountId, exams[0], student.getUuid(), class_streamId, term, year);
					exam2 = perfomanceDAO.getStreamPerformance(accountId, exams[1], student.getUuid(), class_streamId, term, year);	

				}

				//rank 7 subjects
				if(grade7subjects && !grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm234(accountId,student.getCurrentStream(), exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO, examDAO, examType);
					totalExam2 = ReportUtil.findExamTotalForm234(accountId,student.getCurrentStream(), exam2, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO, examDAO, examType);
				}

				//rank 11 subjects
				if(!grade7subjects && grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm1(accountId,student.getCurrentStream(), exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
					totalExam2 = ReportUtil.findExamTotalForm1(accountId,student.getCurrentStream(), exam2, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
				}



				totalPoint = totalExam1.getTotalPoints() + totalExam2.getTotalPoints();
				totalPoint = totalPoint / 2;

				totalMeans = totalExam1.getTotalMean() + totalExam2.getTotalMean();
				totalMeans = totalMeans / 2;


			}

			if(exams.length == 1){

				if(classResult){

					exam1 = perfomanceDAO.getClassPerformance(accountId, exams[0], student.getUuid(), class_streamId, term, year);

				}else{

					exam1 = perfomanceDAO.getStreamPerformance(accountId, exams[0], student.getUuid(), class_streamId, term, year);

				}

				//rank 7 subjects
				if(grade7subjects && !grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO, examDAO, examType);
				}

				//rank 11 subjects
				if(!grade7subjects && grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), exam1, subCategoryDAO, categoryDAO, subjectDAO, gradingSystemDAO);
				}



				totalPoint = totalExam1.getTotalPoints();

				totalMeans = totalExam1.getTotalMean();



			}




			if(totalMeans > 0 || totalPoint > 0){

				Performance2 performance2 = new Performance2();


				if(StringUtils.equals(paper123Id, ReportUtil.PAPER123ID)) {

					performance2.setExam1(totalExam1.getPaper1Map());
					performance2.setExam2(totalExam1.getPaper2Map());
					performance2.setExam3(totalExam1.getPaper3Map()); 

					performance2.setExam1TotalPoints(totalExam1.getTotalPoints());
					performance2.setExam2TotalPoints(totalExam1.getTotalPoints());
					performance2.setExam3TotalPoints(totalExam1.getTotalPoints());

				}else {

					performance2.setExam1(totalExam1.getPerfomanceMap());
					performance2.setExam2(totalExam2.getPerfomanceMap());
					performance2.setExam3(totalExam3.getPerfomanceMap()); 

					performance2.setExam1TotalPoints(totalExam1.getTotalPoints());
					performance2.setExam2TotalPoints(totalExam2.getTotalPoints());
					performance2.setExam3TotalPoints(totalExam3.getTotalPoints());

				}

				performance2.setStudentId(student.getUuid());
				performance2.setTotalMean(totalMeans); 
				performance2.setTotalPoint(totalPoint);
				performance2.setStreamId(student.getCurrentStream()); 
				performance2.setClassroomId(class_streamId); 

				performance2List.add(performance2);


			}

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
