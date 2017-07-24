package com.yahoo.petermwenda83.server.servlet.result.report;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.classroom.Classes;
import com.yahoo.petermwenda83.bean.exam.Deviation;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.persistence.classroom.ClassesDAO;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.DeviationDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.result.ExamConstants;
import com.yahoo.petermwenda83.server.servlet.result.PdfUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;
import com.yahoo.petermwenda83.server.session.SessionStatistics;
import com.yahoo.petermwenda83.server.util.magic.MiddleNumberFor3;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class TopTenF34 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7889655154729188580L;
	
	private Font courierBold14 = ExamConstants.courierBold14;
	private Font timesRomanBold7 = ExamConstants.timesRomanBold7;
	private Font timesRomanItalic8 = ExamConstants.timesRomanItalic8;
	private Font timesRomanNormal7 = ExamConstants.timesRomanNormal7;
	
	private Cache schoolaccountCache, statisticsCache;
	private Document document;
	private PdfWriter writer;
	private Logger logger;
	ExamConfig examConfig;
	GradingSystem gradingSystem;

	private String PDF_SUBTITLE ="";
	private String schoolname = "";
	private String title = "";


	private static PerfomanceDAO perfomanceDAO;
	private static StudentDAO studentDAO;
	private static RoomDAO roomDAO;
	private static ExamConfigDAO examConfigDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static DeviationDAO deviationDAO;
	private static PrimaryDAO primaryDAO;
	private static ClassesDAO classesDAO;
	

	String schoolusername = "";
	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>(); 
	HashMap<String, String> studClsId = new HashMap<String, String>(); 

	HashMap<String, String> roomHash = new HashMap<String, String>();
	double score = 0;
	double engscore = 0; String engscorestr = "";
	double kswscore = 0; String kswscorestr = "";
	double matscore = 0; String matscorestr = "";
	double physcore = 0; String physcorestr = "";
	double bioscore = 0; String bioscorestr = "";
	double chemscore = 0; String chemscorestr = "";
	double bsscore = 0; String bsscorestr = "";
	double compscore = 0; String compscorestr = "";
	double hscscore = 0; String hscscorestr = "";
	double agriscore = 0; String agriscorestr = "";
	double geoscore = 0; String geoscorestr = "";
	double crescore = 0; String crescorestr = "";
	double histscore = 0; String histscorestr = "";

	String grade = "",studeadmno = "",studename = "",admno = "";

	double cat1 = 0,cat2  = 0,endterm  = 0,examcattotal  = 0;
	double paper1  = 0,paper2  = 0,paper3  = 0,catTotals  = 0,catmean  = 0;


	String USER= "";
	String path ="";
	
	String EndTermOnly = "";
	String EndTermAndC2 = "";
	String EndTermC1AndC2 = "";
	
	private final String EXAM_FULL_ID = "4BE8AD46-EAE8-4151-BD18-CB23CF904DDB";
	String examID = "";


	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logger = Logger.getLogger(this.getClass());
		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
		statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
		perfomanceDAO = PerfomanceDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		roomDAO = RoomDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		deviationDAO = DeviationDAO.getInstance();
		primaryDAO = PrimaryDAO.getInstance();
		classesDAO = ClassesDAO.getInstance();
		
		USER = System.getProperty("user.name");
		path = "/home/"+USER+"/school/logo/logo.png";
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/pdf");

		SchoolAccount school = new SchoolAccount();
		HttpSession session = request.getSession(false);
		
		if(session !=null){
			schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
			
		}

		String classID = "";
		
		classID = StringUtils.trimToEmpty(request.getParameter("classID"));
		
		net.sf.ehcache.Element element;

		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (SchoolAccount) element.getObjectValue();
		}



		examConfig = examConfigDAO.getExamConfig(school.getUuid());
		gradingSystem = gradingSystemDAO.getGradingSystem(school.getUuid());
		
		//if exm mode = on , exam is full(p1,p2,p3)
				if(StringUtils.equals(examConfig.getExamMode().toUpperCase(), "ON")){ 
					examID = "4BE8AD46-EAE8-4151-BD18-CB23CF904DDB";
				}else{
					examID = "1678664C-D955-4FA7-88C2-9461D3F1E782";
				}
				
		
		EndTermOnly = examConfig.geteT();
		EndTermAndC2 = examConfig.geteTCtwo();
		EndTermC1AndC2 = examConfig.geteTConetwo();

		SessionStatistics statistics = new SessionStatistics();
		if ((element = statisticsCache.get(schoolusername)) != null) {
			statistics = (SessionStatistics) element.getObjectValue();
		}

		List<Perfomance> pDistinctListGeneral = new ArrayList<Perfomance>();
		
		if(perfomanceDAO.getPerfomanceListDistinctGeneral(school.getUuid(), classID,examConfig.getTerm(),examConfig.getYear())!=null){
			pDistinctListGeneral = perfomanceDAO.getPerfomanceListDistinctGeneral(school.getUuid(), classID,examConfig.getTerm(),examConfig.getYear());
		}

		List<Student> studentList = new ArrayList<Student>(); 
		studentList = studentDAO.getAllStudentList(school.getUuid());

		for(Student stu : studentList){
			studentAdmNoHash.put(stu.getUuid(),stu.getAdmno()); 
			studClsId.put(stu.getUuid(), stu.getClassRoomUuid());
			
			String formatedFirstname = StringUtils.capitalize(stu.getFirstname().toLowerCase());
			String formatedSurname = StringUtils.capitalize(stu.getLastname().toLowerCase());
			
			formatedFirstname = formatedFirstname.substring(0, Math.min(formatedFirstname.length(), 10));
			formatedSurname = formatedSurname.substring(0, Math.min(formatedSurname.length(), 10));
			
			studNameHash.put(stu.getUuid(),formatedFirstname + " " + formatedSurname); 
		}

		List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
		classroomList = roomDAO.getAllRooms(school.getUuid()); 
		for(ClassRoom c : classroomList){
			roomHash.put(c.getUuid() , c.getRoomName());
		}
		
		String classname = "";
		if(classesDAO.getClass(classID) !=null){
		   Classes cls = classesDAO.getClass(classID);
		   classname = cls.getClassName();
		 }
		



		String fileName = new StringBuffer(StringUtils.trimToEmpty("Top_ten_List")) 
				.append("_")
				.append(classname.replaceAll(" ", "_"))
				.append(".pdf")
				.toString();
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);
		
		
		schoolname = school.getSchoolName().toUpperCase()+"\n";
		PDF_SUBTITLE =  "P.O BOX "+school.getPostalAddress()+"\n" 
				+ ""+school.getTown()+" - Kenya\n" 
				+ "" + school.getMobile()+"\n"
				+ "" + school.getEmail()+"\n" ;

		title = "_____________________________________ \n"

				+ " End of Term:"+examConfig.getTerm()+",Year:"+examConfig.getYear()+" Top ten list for: "+classname+"\n";


		document = new Document(PageSize.A4, 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();



			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(statistics, school,classID,pDistinctListGeneral,path);


		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return;




	}

	private void populatePDFDocument(SessionStatistics statistics, SchoolAccount school, 
			String classID,List<Perfomance> pDistinctListGeneral, String realPath) {
		SimpleDateFormat formatter;
		// String formattedDate;
		//Date date = new Date();

		Map<String,Double> kswscoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> engscorehashgn = new LinkedHashMap<String,Double>(); 

		Map<String,Double> physcoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> matscorehashgn = new LinkedHashMap<String,Double>(); 
		Map<String,Double> bioscoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> chemscorehashgn = new LinkedHashMap<String,Double>(); 

		Map<String,Double> bsscoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> agriscorehashgn = new LinkedHashMap<String,Double>(); 
		Map<String,Double> hscscoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> comscoreMapgn = new LinkedHashMap<String,Double>();

		Map<String,Double> geoscoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> crescorehashgn = new LinkedHashMap<String,Double>(); 
		Map<String,Double> histscoreMapgn = new LinkedHashMap<String,Double>();

		Map<String,Double> grandscoremapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> mostImprovedMap = new LinkedHashMap<String,Double>();

		Map<String,Double> MEANMapgn = new LinkedHashMap<String,Double>();
		Map<String,String> POSMapgn = new LinkedHashMap<String,String>();

		try {
			document.open();

			BaseColor baseColor = new BaseColor(255,255,255);//while
			

			Paragraph emptyline = new Paragraph(("                              "));

			Paragraph content = new Paragraph();
			content.add(new Paragraph((schoolname +"") , courierBold14));//
			content.add(new Paragraph((PDF_SUBTITLE +"") , timesRomanItalic8));
			content.add(new Paragraph((title +" \n") , courierBold14));

			PdfPTable prefaceTable = new PdfPTable(2);  
			prefaceTable.setWidthPercentage(100); 
			prefaceTable.setWidths(new int[]{70,130}); 

			PdfPCell contentcell = new PdfPCell(content);
			contentcell.setBorder(Rectangle.NO_BORDER); 
			contentcell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			PdfPCell logo = new PdfPCell();
			logo.addElement(createImage(realPath)); 
			logo.setBorder(Rectangle.NO_BORDER); 
			logo.setHorizontalAlignment(Element.ALIGN_LEFT);

			prefaceTable.addCell(logo); 
			prefaceTable.addCell(contentcell);

			formatter = new SimpleDateFormat("dd, MMM yyyy HH:mm z");
			formatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
			//formattedDate = formatter.format(date);

			DecimalFormat df = new DecimalFormat("0.00"); 
			df.setRoundingMode(RoundingMode.DOWN);

			DecimalFormat rf = new DecimalFormat("0.0"); 
			rf.setRoundingMode(RoundingMode.HALF_UP);

			DecimalFormat rf2 = new DecimalFormat("0"); 
			rf2.setRoundingMode(RoundingMode.UP);

			DecimalFormat df2 = new DecimalFormat("0.00"); 
			df2.setRoundingMode(RoundingMode.UP); 

			DecimalFormat halfUP = new DecimalFormat("0.00"); 
			halfUP.setRoundingMode(RoundingMode.HALF_UP);

			// step 4

			//table here

			PdfPCell countHeader = new PdfPCell(new Paragraph("No",timesRomanBold7));
			countHeader.setBackgroundColor(baseColor);
			countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell admNoHeader = new PdfPCell(new Paragraph("AdNo",timesRomanBold7));
			admNoHeader.setBackgroundColor(baseColor);
			admNoHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell nameHeader = new PdfPCell(new Paragraph("Name",timesRomanBold7));
			nameHeader.setBackgroundColor(baseColor);
			nameHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell classHeader = new PdfPCell(new Paragraph("Stream",timesRomanBold7));
			classHeader.setBackgroundColor(baseColor);
			classHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell kcpeHeader = new PdfPCell(new Paragraph("KCPE",timesRomanBold7));
			kcpeHeader.setBackgroundColor(baseColor);
			kcpeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell totalHeader = new PdfPCell(new Paragraph("Total",timesRomanBold7));
			totalHeader.setBackgroundColor(baseColor);
			totalHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell meanHeader = new PdfPCell(new Paragraph("Mean",timesRomanBold7));
			meanHeader.setBackgroundColor(baseColor);
			meanHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell gradeHeader = new PdfPCell(new Paragraph("Grade",timesRomanBold7));
			gradeHeader.setBackgroundColor(baseColor);
			gradeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell deviationHeader = new PdfPCell(new Paragraph("Dev",timesRomanBold7));
			deviationHeader.setBackgroundColor(baseColor);
			deviationHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell CPcountHeader = new PdfPCell(new Paragraph("Class Ps",timesRomanBold7));
			CPcountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			
			PdfPCell MIcountHeader = new PdfPCell(new Paragraph("No",timesRomanBold7));
			MIcountHeader.setBackgroundColor(baseColor);
			MIcountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell MIadmNoHeader = new PdfPCell(new Paragraph("AdNo",timesRomanBold7));
			MIadmNoHeader.setBackgroundColor(baseColor);
			MIadmNoHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell MInameHeader = new PdfPCell(new Paragraph("Name",timesRomanBold7));
			MInameHeader.setBackgroundColor(baseColor);
			MInameHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell MIclassHeader = new PdfPCell(new Paragraph("Stream",timesRomanBold7));
			classHeader.setBackgroundColor(baseColor);
			classHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell MIdevHeader = new PdfPCell(new Paragraph("Dev",timesRomanBold7));
			MIdevHeader.setBackgroundColor(baseColor);
			MIdevHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


			// OURTABLES
			PdfPTable myTable = new PdfPTable(10); 
			PdfPTable mostImprovedTable = new PdfPTable(5); 
			
			myTable.addCell(countHeader);
			myTable.addCell(admNoHeader);
			myTable.addCell(nameHeader);
			myTable.addCell(classHeader);
			myTable.addCell(kcpeHeader);
			myTable.addCell(totalHeader);
			myTable.addCell(meanHeader);
			myTable.addCell(gradeHeader);
			myTable.addCell(deviationHeader);
			myTable.addCell(CPcountHeader);
			myTable.setWidthPercentage(100); 
			myTable.setWidths(new int[]{15,22,46,30,20,15,15,16,15,15});   
			myTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			mostImprovedTable.addCell(MIcountHeader);
			mostImprovedTable.addCell(MIadmNoHeader);
			mostImprovedTable.addCell(MInameHeader);
			mostImprovedTable.addCell(MIclassHeader);
			mostImprovedTable.addCell(MIdevHeader);
			mostImprovedTable.setWidthPercentage(100); 
			mostImprovedTable.setWidths(new int[]{15,22,46,30,20});    
			mostImprovedTable.setHorizontalAlignment(Element.ALIGN_LEFT);


			//perfomanceListGeneral,pDistinctListGeneral
			//int Finalposition = 0;
			double engscoregn = 0;
			double kswscoregn = 0;
			double matscoregn = 0;
			double physcoregn = 0;
			double bioscoregn = 0;
			double chemscoregn = 0;
			double bsscoregn = 0;
			double comscoregn = 0;
			double hscscoregn = 0;
			double agriscoregn = 0;
			double geoscoregn = 0;
			double crescoregn = 0;
			double histscoregn = 0;

			double  cat1gn  = 0, cat2gn  = 0, endtermgn = 0;
			//double totalgrandscoregn = 0;
			
			 double humanityScoregn =0,techinicalScoregn = 0;
			 double scienceScoregn = 0;
			
			
			double  paper1gn  = 0, paper2gn  = 0, paper3gn = 0,totalgn=0;
			double catTotalsgn = 0,catmeangn = 0,examcattotalgn = 0;
			double languageScoregn = 0; 
			double  grandscoregn = 0;

			int Finalposition = 0;
			int mycountgn =1;

			double numbergn = 0.0;

			List<Perfomance> listGeneral = new ArrayList<>();
			if(pDistinctListGeneral !=null){
				for(Perfomance pD : pDistinctListGeneral){     
					listGeneral = perfomanceDAO.getPerformanceGeneral(school.getUuid(), classID, pD.getStudentUuid(),examConfig.getTerm(),examConfig.getYear());

					engscoregn = 0;
					kswscoregn = 0;
					matscoregn = 0;
					physcoregn = 0;
					bioscoregn = 0;
					chemscoregn = 0;
					bsscoregn = 0;
					comscoregn = 0;
					hscscoregn = 0;
					agriscoregn = 0;
					geoscoregn = 0;
					crescoregn = 0;
					histscoregn = 0;

					cat1gn  = 0; cat2gn  = 0; endtermgn = 0;
					
					for(Perfomance pp : listGeneral){
						//Languages
						//Include all the languages
						if(true){
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.ENG_UUID) ){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 60
									paper2gn = pp.getPaperTwo(); //out of 80
									paper3gn = pp.getPaperThree();//out of 60                   
									totalgn = (paper1gn + paper2gn + paper3gn)/2; 
									engscoregn = totalgn; 
									engscorehashgn.put(pD.getStudentUuid(),engscoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									 /**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									 engscoregn = examcattotalgn; 
									 engscorehashgn.put(pD.getStudentUuid(),engscoregn);

								}
							}
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.KISWA_UUID)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 60
									paper2gn = pp.getPaperTwo(); //out of 80
									paper3gn = pp.getPaperThree();//out of 60
									totalgn = (paper1gn + paper2gn + paper3gn)/2; 
									kswscoregn = totalgn;
									kswscoreMapgn.put(pD.getStudentUuid(),kswscoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									kswscoregn = examcattotalgn; 
									kswscoreMapgn.put(pD.getStudentUuid(),kswscoregn);

								}

							}

							engscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(engscoregn)))));
							kswscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(kswscoregn)))));
							languageScoregn = (engscoregn+kswscoregn); 
							
							
						}       
						//Sciences
						//Pick best two if the student take the three
						if(true){
							double subjectBiggn = 0;
							double subjectSmallgn = 0;
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.PHY_UUID)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 80
									paper2gn = pp.getPaperTwo(); //out of 80
									paper3gn = pp.getPaperThree();//out of 40
									totalgn = ((paper1gn + paper2gn)/160)*60 + paper3gn;
									physcoregn = totalgn;
									physcoreMapgn.put(pD.getStudentUuid(),physcoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									physcoregn = examcattotalgn; 
									physcoreMapgn.put(pD.getStudentUuid(),physcoregn);

								}
							}
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.BIO_UUID)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 80
									paper2gn = pp.getPaperTwo(); //out of 80
									paper3gn = pp.getPaperThree();//out of 40
									totalgn = ((paper1gn + paper2gn)/160)*60 + paper3gn;
									bioscoregn = totalgn;
									bioscoreMapgn.put(pD.getStudentUuid(),bioscoregn);
								}
								else{
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									bioscoregn = examcattotalgn; 
									bioscoreMapgn.put(pD.getStudentUuid(),bioscoregn);

								}
							}
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.CHEM_UUID)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 80
									paper2gn = pp.getPaperTwo(); //out of 80
									paper3gn = pp.getPaperThree();//out of 40
									totalgn = ((paper1gn + paper2gn)/160)*60 + paper3gn;
									chemscoregn = totalgn;
									chemscorehashgn.put(pD.getStudentUuid(),chemscoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									chemscoregn = examcattotalgn; 
									chemscorehashgn.put(pD.getStudentUuid(),chemscoregn);

								}

							}
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.MATH_UUID)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 100
									paper2gn = pp.getPaperTwo(); //out of 100
									totalgn = (paper1gn + paper2gn)/2;
									matscoregn = totalgn;
									matscorehashgn.put(pD.getStudentUuid(),matscoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									matscoregn = examcattotalgn; 
									matscorehashgn.put(pD.getStudentUuid(),matscoregn);

								}

							}

							physcoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(physcoregn)))));
							bioscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(bioscoregn)))));
							chemscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(chemscoregn)))));
							matscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(matscoregn)))));
							MiddleNumberFor3 middle = new MiddleNumberFor3();
							subjectBiggn = Math.max( (Math.max(physcoregn, bioscoregn)), Math.max(Math.max(physcoregn, bioscoregn), chemscoregn));
							subjectSmallgn = middle.ComputeMiddle(physcoregn, bioscoregn, chemscoregn);
							scienceScoregn = (subjectBiggn+subjectSmallgn+matscoregn);
                           
						}
						//Technical
						//Here we pick one subject, the one he/she has performed best, but this subject can be replaced by a science, if the student takes 3 sciences and he/she performed better in the science than in all  the techinicals . 
						if(true){
							double bestTechinicalgn = 0;
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.BS_UUID)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 100
									paper2gn = pp.getPaperTwo(); //out of 100
									totalgn = (paper1gn + paper2gn)/2;
									bsscoregn = totalgn;
									bsscoreMapgn.put(pD.getStudentUuid(),bsscoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									bsscoregn = examcattotalgn; 
									bsscoreMapgn.put(pD.getStudentUuid(),bsscoregn);

								}

							}
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.AGR_UUID)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 80
									paper2gn = pp.getPaperTwo(); //out of 80
									paper3gn = pp.getPaperThree();//out of 40
									totalgn = ((paper1gn + paper2gn)/180)*100;
									agriscoregn = totalgn;
									agriscorehashgn.put(pD.getStudentUuid(),agriscoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									agriscoregn = examcattotalgn; 
									agriscorehashgn.put(pD.getStudentUuid(),agriscoregn);

								}

							}     
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.H_S)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 80
									paper2gn = pp.getPaperTwo(); //out of 80
									paper3gn = pp.getPaperThree();//out of 40
									totalgn = (paper1gn + paper2gn)/2 + paper3gn;
									hscscoregn = totalgn;
									hscscoreMapgn.put(pD.getStudentUuid(),hscscoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									hscscoregn = examcattotalgn; 
									hscscoreMapgn.put(pD.getStudentUuid(),hscscoregn);

								}

							}
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.COMP_UUID)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 80
									paper2gn = pp.getPaperTwo(); //out of 80
									paper3gn = pp.getPaperThree();//out of 40
									totalgn = (paper1gn + paper2gn)/2 + paper3gn;
									comscoregn = totalgn;
									comscoreMapgn.put(pD.getStudentUuid(),comscoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									comscoregn = examcattotalgn; 
									comscoreMapgn.put(pD.getStudentUuid(),comscoregn);

								}

							} 
							bsscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(bsscoregn)))));
							agriscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(agriscoregn)))));
							hscscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(hscscoregn)))));
							comscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(comscoregn)))));
							bestTechinicalgn = Math.max( (Math.max(bsscoregn, agriscoregn)), Math.max(hscscoregn, comscoregn));
							techinicalScoregn = bestTechinicalgn; 
							
						}    

						//Humanities
						//Here we pick only one subject, the one the student has performed best . 
						if(true){  
							double bestHumanitygn = 0;     
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.GEO_UUID)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 100
									paper2gn = pp.getPaperTwo(); //out of 100
									totalgn = (paper1gn + paper2gn)/2;
									geoscoregn = totalgn;                                                 
									geoscoreMapgn.put(pD.getStudentUuid(),geoscoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									geoscoregn = examcattotalgn; 
									geoscoreMapgn.put(pD.getStudentUuid(),geoscoregn);

								}

							}
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.CRE_UUID)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 100
									paper2gn = pp.getPaperTwo(); //out of 100
									totalgn = (paper1gn + paper2gn)/2;
									crescoregn = totalgn;
									crescorehashgn.put(pD.getStudentUuid(),crescoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									crescoregn = examcattotalgn; 
									crescorehashgn.put(pD.getStudentUuid(),crescoregn);

								}

							}
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.HIST_UUID)){
								if(StringUtils.equals(examID, EXAM_FULL_ID)){
									paper1gn = pp.getPaperOne(); //out of 100
									paper2gn = pp.getPaperTwo(); //out of 100
									totalgn = (paper1gn + paper2gn)/2;
									histscoregn = totalgn;                                              
									histscoreMapgn.put(pD.getStudentUuid(),histscoregn);
								}
								else {
									cat1gn = pp.getCatOne();
									cat2gn = pp.getCatTwo();
									endtermgn = pp.getEndTerm();
									
									/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										 
											examcattotalgn = (endtermgn/70)*100;
											

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
											examcattotalgn = cat2gn + endtermgn;
											
											
									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										    catTotalsgn = cat1gn + cat2gn;
											catmeangn = catTotalsgn/2;
											examcattotalgn = catmeangn + endtermgn;
											
									 }
									 
									 /**  end if     */
									
									histscoregn = examcattotalgn; 
									histscoreMapgn.put(pD.getStudentUuid(),histscoregn);
								}
							}
                            
							geoscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(geoscoregn)))));
							crescoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(crescoregn)))));
							histscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(histscoregn)))));
							bestHumanitygn = Math.max( (Math.max(geoscoregn, crescoregn)), Math.max(Math.max(geoscoregn, crescoregn), histscoregn));
							humanityScoregn = bestHumanitygn; 
							
						} 
					}
                     
					grandscoregn = languageScoregn+scienceScoregn+humanityScoregn+techinicalScoregn;
					
					languageScoregn = 0; scienceScoregn = 0; humanityScoregn = 0;techinicalScoregn = 0;  
					grandscoremapgn.put(pD.getStudentUuid(), grandscoregn); 
					mostImprovedMap.put(pD.getStudentUuid(), grandscoregn);
					grandscoregn = 0;
					Finalposition = mycountgn++;
				}

				@SuppressWarnings("unchecked")
				ArrayList<?> as = new ArrayList(grandscoremapgn.entrySet());
				Collections.sort(as,new Comparator(){
					public int compare(Object o1,Object o2){
						Map.Entry e1 = (Map.Entry)o1;
						Map.Entry e2 = (Map.Entry)o2;
						Double f = (Double)e1.getValue();
						Double s = (Double)e2.getValue();
						return s.compareTo(f);
					}
				});
				
				
				
				//Find Most Improved student
				@SuppressWarnings("unchecked")
				ArrayList<?> as2 = new ArrayList(mostImprovedMap.entrySet());
				Collections.sort(as2,new Comparator(){
					public int compare(Object o12,Object o22){
						Map.Entry e12 = (Map.Entry)o12;
						Map.Entry e22 = (Map.Entry)o22;
						Double f2 = (Double)e12.getValue();
						Double s2 = (Double)e22.getValue();
						return s2.compareTo(f2);
					}
				});
				
				
				Map<String,Double> ImprovedMap = new LinkedHashMap<String,Double>();
				//loop to find the student
				for(Object o2 : as2){

					String items = String.valueOf(o2);
					String [] item = items.split("=");
					String uuid = item[0];
					
					//if current term is 1, get deviation for term 3 ,last year
					double lastTermMean = 0;
					Deviation means = new Deviation();
					String lastyr = "";
					
					if(StringUtils.equals(examConfig.getTerm(), "1")){
						//get current year
						String thisyear = "";
						int lastyear = 0;
						
						if(examConfig !=null){
							thisyear = examConfig.getYear();
							lastyear = Integer.parseInt(thisyear) - 1;
						}
						
						lastyr = Integer.toString(lastyear); 
						
					}else{
						lastyr = examConfig.getYear();
					}
					
				
					
					if(deviationDAO.getDev(uuid, lastyr)!=null){
						 means =  deviationDAO.getDev(uuid, lastyr);
					}
					
					//System.out.println("My Object = "+means);
					
					//get last term mean 
					if(StringUtils.equals(examConfig.getTerm(), "1")){
						lastTermMean = means.getDevThree();
					}else if(StringUtils.equals(examConfig.getTerm(), "2")){
						lastTermMean = means.getDevOne();
					}else if(StringUtils.equals(examConfig.getTerm(), "3")){
						lastTermMean = means.getDevTwo();
					}
					//now we haave our last term deviation in the variable  'lastTermMean'
					
					// we get this term mean
					double thstermMean = 0;
					Deviation thisterMmeanObj = new Deviation();
					if(deviationDAO.getDev(uuid, examConfig.getYear()) !=null){
					  thisterMmeanObj = deviationDAO.getDev(uuid, examConfig.getYear());
					}
					if(StringUtils.equals(examConfig.getTerm(), "1")){
						thstermMean = thisterMmeanObj.getDevOne();
					}else if(StringUtils.equals(examConfig.getTerm(), "2")){
						thstermMean = thisterMmeanObj.getDevTwo();
					}else if(StringUtils.equals(examConfig.getTerm(), "3")){
						thstermMean = thisterMmeanObj.getDevThree();
					}
					
					//now we haave our last term deviation in the variable  'thstermMean'
					
					double deviation_from_lastTerm = 0;
					deviation_from_lastTerm = deviationFinder(thstermMean,lastTermMean);
					
					// now we have our deviation, we generate comment
					String devComment = "00";
					devComment = deviationComment(deviation_from_lastTerm);
					//end , we are done!
					double dev = 0;
					dev = Double.parseDouble(devComment);
					ImprovedMap.put(uuid, dev);
					
				}
				
				
				//sort improved
				@SuppressWarnings("unchecked")
				ArrayList<?> as23 = new ArrayList(ImprovedMap.entrySet());
				Collections.sort(as23,new Comparator(){
					public int compare(Object o123,Object o223){
						Map.Entry e123 = (Map.Entry)o123;
						Map.Entry e223 = (Map.Entry)o223;
						Double f23 = (Double)e123.getValue();
						Double s23 = (Double)e223.getValue();
						return s23.compareTo(f23);
					}
				});
				
				int devcount = 1;
				for(Object o23 : as23){
					String items = String.valueOf(o23);
					String [] item = items.split("=");
					String uuid = item[0];
					String deviation = item[1];
					double devtn = 0;
					devtn = Double.parseDouble(deviation);
					//System.out.println("Ad No= "+studentAdmNoHash.get(uuid) +",Deviation ="+deviation); 
					//roomHash.get(studClsId.get(uuid));
					
					    if(devtn > 0){
						mostImprovedTable.addCell(new Paragraph(devcount+" ",timesRomanNormal7));
						mostImprovedTable.addCell(new Paragraph(studentAdmNoHash.get(uuid)+" ",timesRomanNormal7));
						mostImprovedTable.addCell(new Paragraph(studNameHash.get(uuid)+" ",timesRomanNormal7));
						mostImprovedTable.addCell(new Paragraph(roomHash.get(studClsId.get(uuid))+" ",timesRomanNormal7));
						mostImprovedTable.addCell(new Paragraph(halfUP.format(devtn)+" ",timesRomanNormal7));
						}
					
					
					if(devcount ==5 ){
						break;
					}
					
					devcount++;
				}
				
				
				//end most improved finder


				double meangn = 0;
				int counttwogn = 1;
				int positiongn = 1;
				//double grandscoreTgn = 0;
				String totalzgn = "";
				int mycount = 1;
				for(Object o : as){

					String items = String.valueOf(o);
					String [] item = items.split("=");
					String uuid = item[0];

					totalzgn = item[1];

					double the_grandscoregn = 0;
					the_grandscoregn = Double.parseDouble(totalzgn);
					meangn = the_grandscoregn/7; 
					MEANMapgn.put(uuid,meangn);
					
					//KCSE
					double kcpe = 0;
					if(primaryDAO.getPrimary(uuid)!=null){
						StudentPrimary primary = primaryDAO.getPrimary(uuid);
						kcpe = Integer.parseInt(primary.getKcpemark()); 
					}
					
						//if current term is 1, get deviation for term 3 ,last year
						double lastTermMean = 0;
						Deviation means = new Deviation();
						String lastyr = "";
						
						if(StringUtils.equals(examConfig.getTerm(), "1")){
							//get current year
							String thisyear = "";
							int lastyear = 0;
							
							if(examConfig !=null){
								thisyear = examConfig.getYear();
								lastyear = Integer.parseInt(thisyear) - 1;
							}
							
							lastyr = Integer.toString(lastyear); 
							
						}else{
							lastyr = examConfig.getYear();
						}
						
					
						
						if(deviationDAO.getDev(uuid, lastyr)!=null){
							 means =  deviationDAO.getDev(uuid, lastyr);
						}
						
						//System.out.println("My Object = "+means);
						
						//get last term mean 
						if(StringUtils.equals(examConfig.getTerm(), "1")){
							lastTermMean = means.getDevThree();
						}else if(StringUtils.equals(examConfig.getTerm(), "2")){
							lastTermMean = means.getDevOne();
						}else if(StringUtils.equals(examConfig.getTerm(), "3")){
							lastTermMean = means.getDevTwo();
						}
						//now we haave our last term deviation in the variable  'lastTermMean'
						
						// we get this term mean
						double thstermMean = 0;
						Deviation thisterMmeanObj = new Deviation();
						if(deviationDAO.getDev(uuid, examConfig.getYear()) !=null){
						  thisterMmeanObj = deviationDAO.getDev(uuid, examConfig.getYear());
						}
						if(StringUtils.equals(examConfig.getTerm(), "1")){
							thstermMean = thisterMmeanObj.getDevOne();
						}else if(StringUtils.equals(examConfig.getTerm(), "2")){
							thstermMean = thisterMmeanObj.getDevTwo();
						}else if(StringUtils.equals(examConfig.getTerm(), "3")){
							thstermMean = thisterMmeanObj.getDevThree();
						}
						
						//now we haave our last term deviation in the variable  'thstermMean'
						
						double deviation_from_lastTerm = 0;
						deviation_from_lastTerm = deviationFinder(thstermMean,lastTermMean);
						
						// now we have our deviation, we generate comment
						String devComment = "";
						devComment = deviationComment(deviation_from_lastTerm);
						//end , we are done!
						//roomHash.get(studClsId.get(uuid));
					
					 myTable.addCell(new Paragraph(mycount+" ",timesRomanNormal7));
					 myTable.addCell(new Paragraph(studentAdmNoHash.get(uuid)+" ",timesRomanNormal7));
					 myTable.addCell(new Paragraph(studNameHash.get(uuid)+" ",timesRomanNormal7));
					 myTable.addCell(new Paragraph(roomHash.get(studClsId.get(uuid))+" ",timesRomanNormal7));
					 myTable.addCell(new Paragraph((int)kcpe+" ",timesRomanNormal7));
					 myTable.addCell(new Paragraph(halfUP.format(the_grandscoregn)+" ",timesRomanNormal7));
					 myTable.addCell(new Paragraph(halfUP.format(meangn) +" ",timesRomanNormal7));
					 myTable.addCell(new Paragraph(computeGrade(meangn)+" ",timesRomanNormal7));
					 myTable.addCell(new Paragraph(devComment +" ",timesRomanNormal7));
					
					String pos = "";
					if(meangn==numbergn){
						pos = (" " +(positiongn-counttwogn++) +"/"+Finalposition);
						POSMapgn.put(uuid,pos);
						 myTable.addCell(new Paragraph(" "+pos,timesRomanNormal7));
						
					}
					else{
						counttwogn=1;
						pos = (" " +positiongn +"/"+Finalposition);
						POSMapgn.put(uuid,pos);
						 myTable.addCell(new Paragraph(" "+pos,timesRomanNormal7));
						
					}

					positiongn++;
					numbergn=meangn;
					
					
					//top ten only
					if(mycount == 10){
						break;
					}
					mycount++;

				  }

			}
			
			  
			String Label = "Most Improved Top  Five  \n\n";
			Paragraph Lb = new Paragraph(Label,timesRomanBold7);
			

			document.add(prefaceTable);
			document.add(emptyline);
			document.add(emptyline);
			document.add(myTable);  
			document.add(emptyline);
			document.add(Lb);
			document.add(mostImprovedTable);  
		
			// step 5
			document.close();
		}
		catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}  
	}// end populate


	/**
	 * @param thisTermMean
	 * @param lastTermMean
	 * @return
	 */
	private double deviationFinder(double thisTermMean, double lastTermMean){
         double deviation = 0;
		
		if(lastTermMean == 0){
			deviation = 0;
		}else{
			deviation = thisTermMean - lastTermMean;
		}
		return deviation;
	}

	/** put comment as per deviation
	 * @param deviation
	 * @return teacher comment
	 */
	private String deviationComment(double mean){
		String comment = "00";
		DecimalFormat df = new DecimalFormat("0.00"); 
		df.setRoundingMode(RoundingMode.HALF_UP);
		if(mean<0){
			comment = df.format(mean);

		}else if(mean>0){
			comment = df.format(mean);
		}

		return comment;
	}
	

	   /**
	    * @param score
	    * @return
	    */
	   private String computeGrade(double score) {
		   double mean = score;
		   if(mean >= gradingSystem.getGradeAplain()){
			   grade = "A";
		   }else if(mean >= gradingSystem.getGradeAminus()){
			   grade = "A-";
		   }else if(mean >= gradingSystem.getGradeBplus()){
			   grade = "B+";
		   }else if(mean >= gradingSystem.getGradeBplain()){
			   grade = "B";
		   }else if(mean >= gradingSystem.getGradeBminus()){
			   grade = "B-";
		   }else if(mean >= gradingSystem.getGradeCplus()){
			   grade = "C+";
		   }else if(mean >= gradingSystem.getGradeCplain()){
			   grade = "C";
		   }else if(mean >= gradingSystem.getGradeCminus()){
			   grade = "C-";
		   }else if(mean >= gradingSystem.getGradeDplus()){
			   grade = "D+";
		   }else if(mean >= gradingSystem.getGradeDplain()){
			   grade = "D";
		   }else if(mean >= gradingSystem.getGradeDminus()){
			   grade = "D-";
		   }else{
			   grade = "E";
		   }

		   if(mean ==0){
			   grade = " ";
		   }

		   return grade;
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

				ImageIO.write(resize(bufferedImage, 600,300), "png", baos);//w,h
				img = Image.getInstance(baos.toByteArray());
				img.scaleAbsolute(150f,70f); 
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
		public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
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
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


}
