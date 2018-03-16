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
package ke.co.qubintel.school.server.servlet.reports;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.commons.io.output.ByteArrayOutputStream;
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

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.classroom.Stream;
import ke.co.qubintel.school.server.bean.exam.ClassMean;
import ke.co.qubintel.school.server.bean.exam.Perfomance;
import ke.co.qubintel.school.server.bean.exam.YearlyMean;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.bean.student.StudentPrimary;
import ke.co.qubintel.school.server.bean.subject.Subject;
import ke.co.qubintel.school.server.persistence.classroom.ClassDAO;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.ClassMeanDAO;
import ke.co.qubintel.school.server.persistence.exam.PerfomanceDAO;
import ke.co.qubintel.school.server.persistence.exam.YearlyMeanDAO;
import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.persistence.student.PrimaryDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;
import ke.co.qubintel.school.server.quartz.WriteToFile;
import ke.co.qubintel.school.server.servlet.reports.exam.CommonLogic;
import ke.co.qubintel.school.server.servlet.reports.exam.PerformanceBean1;
import ke.co.qubintel.school.server.servlet.util.PeterMid;
import ke.co.qubintel.school.server.servlet.util.Timeit;
import ke.co.qubintel.school.server.session.SessionConstants;
import ke.co.qubintel.school.util.performance.comparator.ClassMeanComparator;
import ke.co.qubintel.school.util.performance.comparator.MeanComparator;
import ke.co.qubintel.school.util.performance.comparator.PointsComparator;
import ke.co.qubintel.school.util.performance.comparator.SubjectPointComparator;

/**
 * 
 * 
 * http://localhost:8080/school/school/classRankingList
 * 
 * @author peter
 *
 */
public class ClassRankingList extends HttpServlet{

	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;
	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static StreamDAO streamDAO;
	//private static ExamDAO examDAO;
	private static YearlyMeanDAO yearlyMeanDAO;
	private static ClassMeanDAO classMeanDAO;
	private static ClassDAO classDAO;
	private static PrimaryDAO primaryDAO;

	private Font timesRomanNormal10 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
	private Font timesRomanBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

	//private Font timesRomanMormal8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private Font timesRomanBold8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);

	private Font timesRomanNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	private Font timesRomanBold6 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);

	private Document document;
	private PdfWriter writer;

	private Logger logger;

	private static String[] exams = {"D50E6399-B913-42F2-A5B6-F0D4BAAF9571", "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A" };//, "16C4BF00-941C-40E4-9891-272D5F0979A1"

	// , "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A", "16C4BF00-941C-40E4-9891-272D5F0979A1" 


	private boolean hidePoints = false;
	private boolean hideGrade = false;

	private boolean rankWithPoints = false;
	private boolean rankWithTotalMarks = true;

	private boolean grade7subjects = true;
	private boolean grade11subjects = false;
	private boolean classResult = true;

	private static final String LOGO_PATH = WriteToFile.LOGO_PATH;


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
		streamDAO = StreamDAO.getInstance();
		//examDAO = ExamDAO.getInstance();
		yearlyMeanDAO = YearlyMeanDAO.getInstance();
		classMeanDAO = ClassMeanDAO.getInstance();
		classDAO = ClassDAO.getInstance();
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

		accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID); 

		hidePts = Boolean.parseBoolean(request.getParameter("p"));
		hideGds= Boolean.parseBoolean(request.getParameter("g"));

		String rank = request.getParameter("rank");

		String noOfSub = request.getParameter("subjects");
		paper123Id = StringUtils.trimToEmpty(request.getParameter("paper123Id")); 
		saveMean = StringUtils.trimToEmpty(request.getParameter("saveMean")); //1 means save, 
		//saveMean = "1";

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

		streamId = StringUtils.trimToEmpty(request.getParameter("stream"));
		term = StringUtils.trimToEmpty(request.getParameter("term"));
		year = StringUtils.trimToEmpty(request.getParameter("year"));
		classroomId= StringUtils.trimToEmpty(request.getParameter("classroom"));
		//if class checked the scope is true, otherwise false
		scope = Boolean.parseBoolean((request.getParameter("scope")));
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

		document = new Document(PageSize.A4.rotate(), 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();

			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			examType = StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE) ? ReportUtil.EXAM_TYPE : "";	
			paper123Id = StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE) ? ReportUtil.PAPER123ID : "";


			populatePDFDocument(accountId,streamId,classroomId,term,year,examType, paper123Id, saveMean);

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}

	/**
	 * @param args
	 */
	public void populatePDFDocument(String accountId, String streamId ,String classroomId, String term ,
			String year ,String examType, String paper123Id, String saveMean) {
		Timeit.code(() -> compute(accountId,streamId,classroomId,term,year,examType,paper123Id,saveMean));
	}

	/**
	 * @param args
	 */
	public  void compute(String accountId, String streamId ,String classroomId, String term ,
			String year ,String examType, String paper123Id, String saveMean) {

		try {

			document.open();

			generateReport(accountId, streamId, classroomId, term, year, examType, paper123Id, saveMean);

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
	private void generateReport(String accountId, String streamId, String classroomId, String term, 
			String year, String examType, String paper123Id, String saveMean)
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


			if(classResult){//
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

		if(!performanceList.isEmpty()) {




			PdfPTable headerTable = new PdfPTable(2);
			headerTable.setWidthPercentage(100); 
			headerTable.setWidths(new int[]{70,30});

			PdfPCell logo = new PdfPCell();
			logo.addElement(createImage(LOGO_PATH+account.getLogo()));  
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
			reportTitle.add(new Chunk("CLASS RANKING LIST FOR  TERM : " + term + ", YEAR : " + year,  timesRomanBold10));
			reportTitle.add(new Chunk(" (" + rankingCriteria+")",  timesRomanNormal10));
			document.add(reportTitle);
			document.add(new Paragraph("\n"));


			/**
			 *   arrange class info here
			 */
			PdfPTable classInfoTable = new PdfPTable(3);
			classInfoTable.setWidthPercentage(90); 
			classInfoTable.setWidths(new int[]{30,30,30}); 

			/**
			 * class column
			 */
			PdfPTable classTable = new PdfPTable(2);
			classTable.setWidthPercentage(60);  
			classTable.setWidths(new int[]{10,50});  


			PdfPCell nameInfoCell = new PdfPCell(new Phrase("Class:",timesRomanBold8)); 
			PdfPCell nameDescCell = new PdfPCell(new Phrase(correctClass ,  timesRomanNormal6));
			nameInfoCell.setBorder(Rectangle.NO_BORDER);
			nameDescCell.setBorder(Rectangle.NO_BORDER);
			nameDescCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 

			classTable.addCell(nameInfoCell);
			classTable.addCell(nameDescCell);

			String classMean = "0";
			classMean = ReportUtil.getclassMean(performanceList, rankWithPoints, rankWithTotalMarks, grade7subjects, grade11subjects);
			String grade = ReportUtil.getGrade(String.valueOf((int)  (Double.parseDouble(classMean)) ), "x", accountId); 


			PdfPCell mainGradeInfoCell = new PdfPCell(new Phrase("Mean:",timesRomanBold8)); 
			PdfPCell mainGradeDescCell = new PdfPCell(new Phrase(classMean + " / " + performanceList.size() + " Grade : " + grade,  timesRomanNormal6));
			mainGradeInfoCell.setBorder(Rectangle.NO_BORDER);
			mainGradeDescCell.setBorder(Rectangle.NO_BORDER);

			classTable.addCell(mainGradeInfoCell);
			classTable.addCell(mainGradeDescCell);

			/**
			 * exam column
			 */
			PdfPTable examTable = new PdfPTable(2);
			examTable.setWidthPercentage(60);  
			examTable.setWidths(new int[]{10,50});  

			
			
			
			if(classResult) {
				//classes
				
				ReportUtil.generateClassMeans(performanceList, accountId, classroomId,rankWithPoints, 
						rankWithTotalMarks, grade7subjects, grade11subjects, exams,term,year,classMeanDAO);
				
				
			}else {//single stream
				
				String examNames = ReportUtil.getExamName(accountId, exams,1);
				
				ClassMean class_stream_Mean = new ClassMean();
				class_stream_Mean.setAccountId(accountId);
				class_stream_Mean.setClassId(classroomId);
				class_stream_Mean.setStreamId(streamId);
				class_stream_Mean.setExamId(examNames); 
				class_stream_Mean.setStreammean(Double.valueOf(classMean));
				class_stream_Mean.setClassmean(Double.valueOf(classMean)); 
				class_stream_Mean.setTerm(term);
				class_stream_Mean.setYear(year); 
 
				classMeanDAO.putClassMean(class_stream_Mean, accountId, classroomId, streamId, examNames, term, year);
				
				
			}

			


			String stringExams = ReportUtil.getExamName(accountId, exams,0);

			PdfPCell examinfoCell = new PdfPCell(new Phrase("Exam:",timesRomanBold8)); 
			PdfPCell examDescCell = new PdfPCell(new Phrase(stringExams,  timesRomanNormal6));  
			examinfoCell.setBorder(Rectangle.NO_BORDER);
			examDescCell.setBorder(Rectangle.NO_BORDER);
			examDescCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 

			examTable.addCell(examinfoCell);
			examTable.addCell(examDescCell);



			/**
			 * term/year column
			 */
			PdfPTable termYearTable = new PdfPTable(2);
			termYearTable.setWidthPercentage(60); 
			termYearTable.setWidths(new int[]{10,50}); 


			// term
			PdfPCell termInfoCell = new PdfPCell(new Phrase("Term:",timesRomanBold8)); 
			PdfPCell termDescCell = new PdfPCell(new Phrase(term,  timesRomanNormal6));
			termInfoCell.setBorder(Rectangle.NO_BORDER);
			termDescCell.setBorder(Rectangle.NO_BORDER);

			termYearTable.addCell(termInfoCell);
			termYearTable.addCell(termDescCell);

			//student year
			PdfPCell yearInfoCell = new PdfPCell(new Phrase("Year:",timesRomanBold8)); 
			PdfPCell yearDescCell = new PdfPCell(new Phrase(year,  timesRomanNormal6));
			yearInfoCell.setBorder(Rectangle.NO_BORDER);
			yearDescCell.setBorder(Rectangle.NO_BORDER);

			termYearTable.addCell(yearInfoCell);
			termYearTable.addCell(yearDescCell);


			/**
			 * put the columns in student table
			 */
			classInfoTable.addCell(classTable);
			classInfoTable.addCell(examTable);
			classInfoTable.addCell(termYearTable);


			document.add(classInfoTable);
			document.add(new Paragraph("\n"));



			List<Subject> subjects = subjectDAO.getSubjects(accountId);

			int size = subjects.size();
			if(size > 13){
				size = 13;
			}

			size += 12;

			PdfPTable rankingTable = new PdfPTable(25);   
			rankingTable.setWidthPercentage(100); 
			rankingTable.setWidths(new int[]{8,12,20,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,13,12,12,12}); 
			rankingTable.setHeaderRows(1); 
			rankingTable.isSkipFirstHeader();


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

			PdfPCell streamCell = new PdfPCell(new Paragraph("Class",timesRomanBold6));
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

			int subCount = 0;
			for(Subject subject : subjects){
				subCount++;
				PdfPCell cell = new PdfPCell(new Paragraph(subject.getCode(),timesRomanBold6));
				cell.setBackgroundColor(baseColor);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

				if(subCount > 13)
					break;

				rankingTable.addCell(cell); 


			}

			//cells = 7
			PdfPCell totalCell = new PdfPCell(new Paragraph("T",timesRomanBold6));
			totalCell.setBackgroundColor(baseColor);
			totalCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell pointsCell = new PdfPCell(new Paragraph("Pts",timesRomanBold6));
			pointsCell.setBackgroundColor(baseColor);
			pointsCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell meanCell = new PdfPCell(new Paragraph("M.G",timesRomanBold6));
			meanCell.setBackgroundColor(baseColor);
			meanCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell pmeanCell = new PdfPCell(new Paragraph("P.MG",timesRomanBold6));
			pmeanCell.setBackgroundColor(baseColor);
			pmeanCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell deviationCell = new PdfPCell(new Paragraph("Dev",timesRomanBold6));
			deviationCell.setBackgroundColor(baseColor);
			deviationCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell streamPositionCell = new PdfPCell(new Paragraph("S.P",timesRomanBold6));
			streamPositionCell.setBackgroundColor(baseColor);
			streamPositionCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell classPositionCell = new PdfPCell(new Paragraph("C.P",timesRomanBold6));
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

				String avg_points_grade = "0";
				String avgPoints = "0";
				//int avg_points = 0;


				//int mainPoint = performance2.getTotalPoint();
				//int totalMean = performance2.getTotalMean();

				if(rankWithPoints && !rankWithTotalMarks){ 

					total = performance2.getTotalPoint();

				}

				if(!rankWithPoints && rankWithTotalMarks){

					total = performance2.getTotalMean();

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

				if(StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE)){


					if(rankWithPoints && !rankWithTotalMarks){


						if(grade7subjects && !grade11subjects){

							avg_points_grade = ReportUtil.getGrade(String.valueOf(performance2.getTotalPoint()),"subjectId", accountId);
							avgPoints = String.valueOf(performance2.getTotalPoint()); 

						}else if(!grade7subjects && grade11subjects){

							double avg = ((double)Double.valueOf(performance2.getTotalPoint()) / 132) * 84; 
							avg_points_grade = ReportUtil.getGrade(String.valueOf((int)avg),"subjectId", accountId);
							avgPoints = String.valueOf((int)avg); 


						}
					}


				}



				StudentPrimary primary = new StudentPrimary();
				if(primaryDAO.getStudentPrimary(accountId, student.getUuid()) != null){
					primary = primaryDAO.getStudentPrimary(accountId, student.getUuid());

				}

				String kcpe = primary.getKcpemark();

				if(StringUtils.equals(kcpe, "0")){
					kcpe = "";
				}

				rankingTable.addCell(new Paragraph(" " + count,timesRomanNormal6));
				rankingTable.addCell(new Paragraph(student.getRegNo(),timesRomanNormal6));
				String name = student.getFirstname() + " " +  student.getMiddlename();
				name = name.substring(0, Math.min(name.length(), 14));//14
				rankingTable.addCell(new Paragraph(name ,timesRomanNormal6));
				rankingTable.addCell(new Paragraph(stream,timesRomanNormal6));
				rankingTable.addCell(new Paragraph(kcpe,timesRomanNormal6));


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


					String examAverage = ReportUtil.findExamAverage(subject,exam1Score,exam2Score,exam3Score, exams.length,examType);



					String avgrade = ReportUtil.getGrade(examAverage,subject.getUuid(), accountId);
					String avgpoints = String.valueOf(ReportUtil.getPoints(examAverage, subject.getUuid(),accountId));

					examAverage = StringUtils.equals(examAverage, "0") ? "" : examAverage;
					avgpoints = StringUtils.equals(avgpoints, "0") ? "" : avgpoints;

					String average = examAverage + " " + avgrade +  " " + avgpoints;

					if(hidePoints && hideGrade){
						average = examAverage;

					}else if(hidePoints && !hideGrade){ 
						average = examAverage + " " + avgrade;

					}else if(hideGrade && !hidePoints){
						average = examAverage + " " + avgpoints;

					}

					rankingTable.addCell(new Paragraph(average,timesRomanNormal6));



				}


				double avgMean = 0;
				double pointsAvg = 0;
				if(grade7subjects && !grade11subjects){
					avgMean = performance2.getTotalMean() > 0 ? (double)performance2.getTotalMean() / 7 : 0;
					pointsAvg = performance2.getTotalPoint();
				}
				if(!grade7subjects && grade11subjects){
					avgMean = performance2.getTotalMean() > 0 ? (double)performance2.getTotalMean() / 11 : 0;
					pointsAvg = ((double)performance2.getTotalPoint() / 132) * 84;
					pointsAvg = (int)  (pointsAvg);

				}


				String avgGradeByTotalMean = ReportUtil.getGrade(String.valueOf((int)  (pointsAvg)),"", accountId); 
				String avgGradeByMean = ReportUtil.getGrade(String.valueOf((int)  (avgMean)),"", accountId); 

				String poinst_str = "";
				String mean_str = "";

				poinst_str = String.valueOf(pointsAvg); 
				mean_str = ReportUtil.df2.format(avgMean);


				String termPosition = "";

				String classPositionMSG = "";
				String streamPositionMSG = "";

				if(classResult){



					classPositionMSG = pos + " / " + performanceList.size();

					streamPositionMSG = ReportUtil.getStreamPosition(accountId, student.getUuid(), student.getCurrentStream(), 
							performanceList,rankWithPoints,rankWithTotalMarks); 

					streamPositionMSG = StringUtils.replace(streamPositionMSG, "Out of:", "/");


				}else{

					if(!classResult){

						if(!classperformanceList.isEmpty()){
							classPositionMSG = ReportUtil.getClassPosition(accountId, student.getUuid() , 
									classperformanceList,rankWithPoints,rankWithTotalMarks);
						}

						classPositionMSG = StringUtils.replace(classPositionMSG, "Out of:", "/");
					}


					streamPositionMSG = pos + " / " + performanceList.size();

				}

				termPosition = classPositionMSG;


				if(rankWithPoints && !rankWithTotalMarks){

					if(StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE)){

						poinst_str = avgPoints + " " + avg_points_grade;

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

							yearlyMean.setMeanOne(Double.valueOf(ReportUtil.df2.format(performance2.getTotalPoint()))); 
							yearlyMean.setTermOnePosition(termPosition);

						}
						if(StringUtils.equals(term, "2")){

							yearlyMean.setMeanTwo(Double.valueOf(ReportUtil.df2.format(performance2.getTotalPoint())));
							yearlyMean.setTermTwoPosition(termPosition);

						}
						if(StringUtils.equals(term, "3")){

							yearlyMean.setMeanThree(Double.valueOf(ReportUtil.df2.format(performance2.getTotalPoint())));
							yearlyMean.setTermThreePosition(termPosition);

						}


						if(StringUtils.equals(saveMean, "1")) {
							yearlyMeanDAO.putYearlyMean(yearlyMean, accountId, student.getUuid(), classroomId, year);
						}


					}else {

						//show grade on points
						poinst_str = (int)  (pointsAvg) + " " + avgGradeByTotalMean;

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

							yearlyMean.setMeanOne(Double.valueOf(ReportUtil.df2.format(pointsAvg))); //
							yearlyMean.setTermOnePosition(termPosition);

						}
						if(StringUtils.equals(term, "2")){

							yearlyMean.setMeanTwo(Double.valueOf(ReportUtil.df2.format(pointsAvg)));
							yearlyMean.setTermTwoPosition(termPosition);

						}
						if(StringUtils.equals(term, "3")){

							yearlyMean.setMeanThree(Double.valueOf(ReportUtil.df2.format(pointsAvg)));
							yearlyMean.setTermThreePosition(termPosition);

						}


						if(StringUtils.equals(saveMean, "1")) {
							yearlyMeanDAO.putYearlyMean(yearlyMean, accountId, student.getUuid(), classroomId, year);
						}

					}




				}

				if(!rankWithPoints && rankWithTotalMarks){
					//show grade on avg
					mean_str = ReportUtil.df2.format(avgMean) + " " +  avgGradeByMean;


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

						yearlyMean.setMeanOne(Double.valueOf(ReportUtil.df2.format(avgMean))); 
						yearlyMean.setTermOnePosition(termPosition);

					}
					if(StringUtils.equals(term, "2")){

						yearlyMean.setMeanTwo(Double.valueOf(ReportUtil.df2.format(avgMean)));
						yearlyMean.setTermTwoPosition(termPosition);

					}
					if(StringUtils.equals(term, "3")){

						yearlyMean.setMeanThree(Double.valueOf(ReportUtil.df2.format(avgMean)));
						yearlyMean.setTermThreePosition(termPosition);

					}


					if(StringUtils.equals(saveMean, "1")) {
						yearlyMeanDAO.putYearlyMean(yearlyMean, accountId, student.getUuid(), classroomId, year);
					}


				}




				int thisTerm = Integer.valueOf(term);
				String accurateYear = year;

				if(thisTerm == 1){
					accurateYear = String.valueOf(Integer.valueOf(accurateYear)-1); 
				}


				YearlyMean yearlymean = new YearlyMean();
				if(yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), accurateYear) != null){
					yearlymean = yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), accurateYear);
				}

				double previousMean = 0;
				String prevMean = "";

				if(Integer.valueOf(term) == 1){
					previousMean = yearlymean.getMeanThree();
					//previousMean = yearlymean.getMeanOne();

				}else if(Integer.valueOf(term) == 2){
					previousMean = yearlymean.getMeanOne();
					//previousMean = yearlymean.getMeanTwo();

				}else if(Integer.valueOf(term) == 3){
					//previousMean = yearlymean.getMeanThree();
					previousMean = yearlymean.getMeanTwo();

				}

				
				prevMean = ReportUtil.df2.format(previousMean);

				if(StringUtils.equals(String.valueOf((int)previousMean), "0")){
					prevMean = "";
				}

				double thisMean = 0;

				if(rankWithPoints && !rankWithTotalMarks){

					thisMean = performance2.getTotalPoint();

				}

				if(!rankWithPoints && rankWithTotalMarks){

					thisMean = Double.valueOf(ReportUtil.df2.format(avgMean)); 

				}

				double deviation = thisMean - Double.valueOf(ReportUtil.df2.format(previousMean)); 

				String dev = deviation == thisMean ? "" : ReportUtil.df2.format(deviation); 
				dev = StringUtils.equals(dev, "0") ? "" : dev;



				rankingTable.addCell(new Paragraph(""+performance2.getTotalMean(),timesRomanNormal6));
				rankingTable.addCell(new Paragraph(""+poinst_str,timesRomanNormal6));//performance2.getTotalPoint() , poinst_str
				rankingTable.addCell(new Paragraph(""+mean_str,timesRomanNormal6));
				rankingTable.addCell(new Paragraph(""+prevMean,timesRomanNormal6));
				rankingTable.addCell(new Paragraph(dev,timesRomanNormal6));
				
				String[] arr_s = streamPositionMSG.split("\\/");
				String[] arr_c = classPositionMSG.split("\\/");
				
				String s_pos = arr_s[0];
				String c_pos = arr_c[0];
				
				rankingTable.addCell(new Paragraph(s_pos,timesRomanNormal6));
				rankingTable.addCell(new Paragraph(c_pos,timesRomanNormal6));


				position++;
				prevtotal=total;
				count++;


			}



			List<SubjectAnalysis>  subjectPerformance = new ArrayList<>(); 

			subjectPerformance = getclassSubjectPerformance(accountId, exams, subjectDAO, perfomanceDAO, classroomId, 
					streamId, term, year,examType,classResult , paper123Id);


			Collections.sort(subjectPerformance, new SubjectPointComparator());
			Collections.reverse(subjectPerformance);

			subjectPerformance.add(0, new SubjectAnalysis()); 

			PdfPTable subAnalysisTable = new PdfPTable(14);   
			subAnalysisTable.setWidthPercentage(100); 
			subAnalysisTable.setHeaderRows(1); 
			subAnalysisTable.isSkipFirstHeader();

			AtomicInteger scount = new AtomicInteger();

			subjectPerformance.forEach(p -> {

				int rcount = scount.incrementAndGet();
				if(rcount == 1){

					PdfPCell cell = new PdfPCell(new Paragraph("  ",timesRomanBold6));
					cell.setBackgroundColor(baseColor);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subAnalysisTable.addCell(cell); 

				}else if (rcount > 1){

					Subject subject = subjectDAO.getSubjectById(accountId, p.getSubjectId());

					PdfPCell cell = new PdfPCell(new Paragraph(subject.getCode(),timesRomanBold6));
					cell.setBackgroundColor(baseColor);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subAnalysisTable.addCell(cell); 
				}


			});

			AtomicInteger scount2 = new AtomicInteger();
			subjectPerformance.forEach(p -> {

				int rcount = scount2.incrementAndGet();
				if(rcount == 1){

					PdfPCell cell = new PdfPCell(new Paragraph(" TOTAL ",timesRomanBold6));
					cell.setBackgroundColor(baseColor);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subAnalysisTable.addCell(cell); 

				}else if (rcount > 1){

					String stotal =  String.valueOf(p.getTotal());
					stotal = StringUtils.equals(String.valueOf((int)p.getTotal()), "0") ? "" : stotal; 

					PdfPCell tCell = new PdfPCell(new Paragraph(stotal,timesRomanNormal6));
					tCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subAnalysisTable.addCell(tCell); 
				}

			});

			AtomicInteger scount3 = new AtomicInteger();
			subjectPerformance.forEach(p -> {

				int rcount = scount3.incrementAndGet();
				if(rcount == 1){

					PdfPCell cell = new PdfPCell(new Paragraph(" AVG ",timesRomanBold6));
					cell.setBackgroundColor(baseColor);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subAnalysisTable.addCell(cell); 

				}else if (rcount > 1){

					String average = ReportUtil.df2.format(p.getAverage());
					String agrade = ReportUtil.getGrade(String.valueOf((int)  (p.getAverage())),p.getSubjectId(), accountId); 

					average = StringUtils.equals(String.valueOf((int)p.getAverage()), "0") ? "" : average; 

					PdfPCell aCell = new PdfPCell(new Paragraph(average + " " + agrade,timesRomanNormal6));
					aCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subAnalysisTable.addCell(aCell); 
				}


			});

			AtomicInteger scount4 = new AtomicInteger();
			subjectPerformance.forEach(p -> {

				int rcount = scount4.incrementAndGet();
				if(rcount == 1){

					PdfPCell cell = new PdfPCell(new Paragraph(" ENTRY ",timesRomanBold6));
					cell.setBackgroundColor(baseColor);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subAnalysisTable.addCell(cell); 

				}else if (rcount > 1){

					String entry = String.valueOf(p.getEntry());
					entry = StringUtils.equals(entry, "0") ? "" : entry;

					PdfPCell eCell = new PdfPCell(new Paragraph(entry,timesRomanNormal6));
					eCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subAnalysisTable.addCell(eCell); 
				}


			});


			PdfPTable crankingTable = new PdfPTable(4);   
			crankingTable.setWidthPercentage(55);  
			crankingTable.setWidths(new int[]{10,15,15,15});  
			crankingTable.setHeaderRows(1);  
			crankingTable.isSkipFirstHeader();

			PdfPCell c_countCell = new PdfPCell(new Paragraph("#",timesRomanBold6));
			c_countCell.setBackgroundColor(baseColor);
			c_countCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell c_classCell = new PdfPCell(new Paragraph("Class",timesRomanBold6));
			c_classCell.setBackgroundColor(baseColor);
			c_classCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell c_meanCell = new PdfPCell(new Paragraph("Mean",timesRomanBold6));
			c_meanCell.setBackgroundColor(baseColor);
			c_meanCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell p_c_meanCell = new PdfPCell(new Paragraph("Prev.Mean",timesRomanBold6)); 
			p_c_meanCell.setBackgroundColor(baseColor);
			p_c_meanCell.setHorizontalAlignment(Element.ALIGN_LEFT);


			crankingTable.addCell(c_countCell);
			crankingTable.addCell(c_classCell);
			crankingTable.addCell(c_meanCell);
			crankingTable.addCell(p_c_meanCell); 

			AtomicInteger c_count = new AtomicInteger();
			
			List<ClassMean> clist = classMeanDAO.getClassMeanList(accountId, classroomId, ReportUtil.getExamName(accountId, exams,1), term, year);
			//sort the list 
			Collections.sort(clist, new ClassMeanComparator());
			Collections.reverse(clist);
			
			clist.forEach(cmean -> {

				int c = c_count.incrementAndGet();
				String stream = "";

				if(streamDAO.getStream(accountId, cmean.getStreamId()) != null) {
					stream = streamDAO.getStream(accountId, cmean.getStreamId()).getDescription(); 
				}
				
				ClassMean classMean2 = new ClassMean();
				String pmean = "";
				String prevYear = String.valueOf(Integer.valueOf(year) - 1);
				
				if(classMeanDAO.getClassMean(accountId, streamId, ReportUtil.getExamName(accountId, exams,1), term, prevYear) != null) {
					classMean2 = classMeanDAO.getClassMean(accountId, streamId, ReportUtil.getExamName(accountId, exams,1), term, prevYear);
					
				}
				
				if(classMean2 != null) {
					pmean = String.valueOf(classMean2.getStreammean()); 
				}

				crankingTable.addCell(new Paragraph(c + "" ,timesRomanNormal6));
				crankingTable.addCell(new Paragraph(stream,timesRomanNormal6));
				crankingTable.addCell(new Paragraph(cmean.getClassmean()+"",timesRomanNormal6));
				crankingTable.addCell(new Paragraph(pmean,timesRomanNormal6)); 



			});

			document.add(rankingTable); 
			document.add(new Paragraph("\n")); 
			document.add(subAnalysisTable);
			document.add(new Paragraph("\n")); 
			document.add(crankingTable);







		}else {

			document.add(new Paragraph(" No exam !")); 

		}

	}


	/**
	 * 
	 * @param accountId
	 * @param class_streamId
	 * @param term
	 * @param year
	 * @param studentsList
	 * @param examType
	 * @param classResult
	 * @return
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

		double totalPoint = 0;
		double totalMeans = 0;
		

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

						totalExam1 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), exam1, examType);

					}else {

						totalExam1 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), exam1, examType);
						totalExam2 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), exam2, examType);
						totalExam3 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), exam3,  examType);


					}



				}

				//rank 11 subjects
				if(!grade7subjects && grade11subjects){

					if(StringUtils.equals(paper123Id, ReportUtil.PAPER123ID)) {

						totalExam1 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), exam1);

					}else {

						totalExam1 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), exam1);
						totalExam2 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), exam2);
						totalExam3 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), exam3);

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
					totalExam1 = ReportUtil.findExamTotalForm234(accountId,student.getCurrentStream(), exam1, examType);
					totalExam2 = ReportUtil.findExamTotalForm234(accountId,student.getCurrentStream(), exam2, examType);
				}

				//rank 11 subjects
				if(!grade7subjects && grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm1(accountId,student.getCurrentStream(), exam1);
					totalExam2 = ReportUtil.findExamTotalForm1(accountId,student.getCurrentStream(), exam2);
				}



				totalPoint = totalExam1.getTotalPoints() + totalExam2.getTotalPoints();
				totalPoint = totalPoint / 2;

				totalMeans = totalExam1.getTotalMean() + totalExam2.getTotalMean();
				totalMeans = totalMeans / 2;


			}

			if(exams.length == 1){


				exam1 = classResult ? perfomanceDAO.getClassPerformance(accountId, exams[0], student.getUuid(), class_streamId, term, year) : 
					perfomanceDAO.getStreamPerformance(accountId, exams[0], student.getUuid(), class_streamId, term, year);
				
				//rank 7 subjects
				if(grade7subjects && !grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), exam1, examType);
				}

				//rank 11 subjects
				if(!grade7subjects && grade11subjects){
					totalExam1 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), exam1);
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
				performance2.setTotalMean((int)totalMeans); 
				performance2.setTotalPoint((int)totalPoint);
				performance2.setStreamId(student.getCurrentStream()); 
				performance2.setClassroomId(class_streamId); 

				performance2List.add(performance2);
			}


		}


		if(StringUtils.equals(paper123Id, ReportUtil.PAPER123ID)) {
			return performance2List;

		}else {
			return ReportUtil.getAverage(performance2List,accountId,grade7subjects,grade11subjects,exams.length);

		}

	}




	/**
	 * 
	 * @param accountId
	 * @param exams
	 * @param subjectDAO
	 * @param perfomanceDAO
	 * @param classroomId
	 * @param streamId
	 * @param term
	 * @param year
	 * @param classResult
	 * @return
	 */
	private static List<SubjectAnalysis> getclassSubjectPerformance(String accountId, String[] exams, SubjectDAO subjectDAO,
			PerfomanceDAO perfomanceDAO, String classroomId, String streamId, String term, String year,String examType, boolean classResult, String paper123Id) {

		List<Subject> subjects = subjectDAO.getSubjects(accountId);
		List<SubjectAnalysis> performance1List = new ArrayList<>();

		List<Perfomance> exam1  = new ArrayList<>();
		List<Perfomance> exam2 = new ArrayList<>();
		List<Perfomance> exam3 = new ArrayList<>();

		SubjectPerformance totalExam1 = new SubjectPerformance();
		SubjectPerformance totalExam2 = new SubjectPerformance();
		SubjectPerformance totalExam3 = new SubjectPerformance();
		
		

		//double totalAvg = 0;
		double total = 0;
		int entry = 0;

		for(Subject subject : subjects){
			
			List<PerformanceBean1> finalExamList = new ArrayList<>();

			if(exams.length == 3){
				
				List<PerformanceBean1> examList1 = new ArrayList<>();
				List<PerformanceBean1> examList2 = new ArrayList<>();
				List<PerformanceBean1> examList3 = new ArrayList<>();

				if(classResult){
					
					if(StringUtils.equals(paper123Id, ReportUtil.PAPER123ID)) {

						exam1 = perfomanceDAO.getClassSubjectPerfomance(accountId, ReportUtil.PAPER123ID, subject.getUuid(), classroomId, term, year);

						String entry_ = ReportUtil.findEntry(exam1,null,null,accountId,classroomId,true, exams.length, subject.getUuid());
						entry = Integer.valueOf(entry_);
				
						examList1 = CommonLogic.subjectAnalyzer(exam1, true); 
						finalExamList = CommonLogic.combineExams(examList1, null, null, 1, subject.getUuid(), accountId, classroomId);
						
					}else {
						exam1 = perfomanceDAO.getClassSubjectPerfomance(accountId, exams[0], subject.getUuid(), classroomId, term, year);
						exam2 = perfomanceDAO.getClassSubjectPerfomance(accountId, exams[1], subject.getUuid(), classroomId, term, year);
						exam3 = perfomanceDAO.getClassSubjectPerfomance(accountId, exams[2], subject.getUuid(), classroomId, term, year);
						
						String entry_ = ReportUtil.findEntry(exam1,exam2,exam3,accountId,classroomId,false, exams.length, subject.getUuid());
						entry = Integer.valueOf(entry_);
						
						examList1 = CommonLogic.subjectAnalyzer(exam1, false); 
						examList2 = CommonLogic.subjectAnalyzer(exam2, false); 
						examList3 = CommonLogic.subjectAnalyzer(exam3, false); 
						
						finalExamList = CommonLogic.combineExams(examList1, examList2, examList3, 3, subject.getUuid(), accountId, classroomId);

					}

				}else{

					if(StringUtils.equals(paper123Id, ReportUtil.PAPER123ID)) {

						exam1 = perfomanceDAO.getStreamSubjectPerfomance(accountId, ReportUtil.PAPER123ID, subject.getUuid(), streamId, term, year);

						String entry_ = ReportUtil.findEntry(exam1,null,null,accountId,classroomId,true, exams.length, subject.getUuid());
						entry = Integer.valueOf(entry_);
						
						examList1 = CommonLogic.subjectAnalyzer(exam1, true); 
						finalExamList = CommonLogic.combineExams(examList1, null, null, 1, subject.getUuid(), accountId, classroomId);
						
						
					}else {

						exam1 = perfomanceDAO.getStreamSubjectPerfomance(accountId, exams[0], subject.getUuid(), streamId, term, year);
						exam2 = perfomanceDAO.getStreamSubjectPerfomance(accountId, exams[1], subject.getUuid(), streamId, term, year);
						exam3 = perfomanceDAO.getStreamSubjectPerfomance(accountId, exams[2], subject.getUuid(), streamId, term, year);

						String entry_ = ReportUtil.findEntry(exam1,exam2,exam3, accountId, classroomId,false, exams.length, subject.getUuid());
						entry = Integer.valueOf(entry_);
						
						
						examList1 = CommonLogic.subjectAnalyzer(exam1, false); 
						examList2 = CommonLogic.subjectAnalyzer(exam2, false); 
						examList3 = CommonLogic.subjectAnalyzer(exam3, false); 
						finalExamList = CommonLogic.combineExams(examList1, examList2, examList3, 3, subject.getUuid(), accountId, classroomId);
					}
					
					


				}
				

				total = ReportUtil.findTotal(finalExamList);
				
			}
			if(exams.length == 2){
				
				List<PerformanceBean1> examList1 = new ArrayList<>();
				List<PerformanceBean1> examList2 = new ArrayList<>();

				if(classResult){
					exam1 = perfomanceDAO.getClassSubjectPerfomance(accountId, exams[0], subject.getUuid(), classroomId, term, year);
					exam2 = perfomanceDAO.getClassSubjectPerfomance(accountId, exams[1], subject.getUuid(), classroomId, term, year);
					
				}else{
					exam1 = perfomanceDAO.getStreamSubjectPerfomance(accountId, exams[0], subject.getUuid(), streamId, term, year);
					exam2 = perfomanceDAO.getStreamSubjectPerfomance(accountId, exams[1], subject.getUuid(), streamId, term, year);
					
				}

				String entry_ = ReportUtil.findEntry(exam1,exam2,null,accountId,classroomId,false, exams.length, subject.getUuid());
				entry = Integer.valueOf(entry_);
				
				examList1 = CommonLogic.subjectAnalyzer(exam1, false); 
				examList2 = CommonLogic.subjectAnalyzer(exam2, false); 
				
				finalExamList = CommonLogic.combineExams(examList1, examList2, null, 2, subject.getUuid(), accountId, classroomId);
				
				total = ReportUtil.findTotal(finalExamList);

			}
			if(exams.length == 1){
				
				List<PerformanceBean1> examList1 = new ArrayList<>();

				if(classResult){
					exam1 = perfomanceDAO.getClassSubjectPerfomance(accountId, exams[0], subject.getUuid(), classroomId, term, year);
					
				}else{
					exam1 = perfomanceDAO.getStreamSubjectPerfomance(accountId, exams[0], subject.getUuid(), streamId, term, year);
					
				}

				String entry_ = ReportUtil.findEntry(exam1,null,null,accountId,classroomId,false, exams.length, subject.getUuid());
				entry = Integer.valueOf(entry_);

				examList1 = CommonLogic.subjectAnalyzer(exam1, false); 
				finalExamList = CommonLogic.combineExams(examList1, null, null, 1, subject.getUuid(), accountId, classroomId);
				total = ReportUtil.findTotal(finalExamList);

			}


			//Business logic here
			SubjectAnalysis subjectAnalysis = new SubjectAnalysis();
			subjectAnalysis.setSubjectId(subject.getUuid()); 
			subjectAnalysis.setTotal((int)total); 
			
			double mean = 0;
			if(entry > 0) {
				mean = total / entry;
			}
			subjectAnalysis.setAverage(Double.parseDouble(ReportUtil.df2.format(mean))); 
			subjectAnalysis.setEntry(entry);
			performance1List.add(subjectAnalysis);

		}//end of subject loop


		return performance1List;
	}



	/** TODO
	 * @param realPath
	 * @return
	 */
	private Element createImage(String realPath) {
		Image img = null;
		
		try {

			File file = new File(realPath);
			
			if(!file.exists()){
				realPath = getServletContext().getRealPath("/school/images/logo.png");   

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
