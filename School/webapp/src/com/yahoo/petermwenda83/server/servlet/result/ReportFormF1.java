/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.result;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.BarWeight;
import com.yahoo.petermwenda83.bean.exam.Deviation;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.schoolaccount.Miscellanous;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.staff.ClassTeacher;
import com.yahoo.petermwenda83.bean.staff.StaffDetails;
import com.yahoo.petermwenda83.bean.staff.TeacherSubClass;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.BarWeightDAO;
import com.yahoo.petermwenda83.persistence.exam.DeviationDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.MiscellanousDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsApiDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO;
import com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.money.StudentBalance;
import com.yahoo.petermwenda83.server.servlet.result.sms.SendResultSMS;
import com.yahoo.petermwenda83.server.session.SessionConstants;
import com.yahoo.petermwenda83.server.session.SessionStatistics;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/** 
 * @author peter
 * 
 */
public class ReportFormF1 extends HttpServlet{

	private Font courierBold14 = ExamConstants.courierBold14;
	private Font timesRomanBold7 = ExamConstants.timesRomanBold7;
	private Font timesRomanItalic8 = ExamConstants.timesRomanItalic8;
	private Font timesRomanNormal7 = ExamConstants.timesRomanNormal7;
	private Font timesRomanNormal0 = ExamConstants.timesRomanNormal0;

	private Document document;
	private PdfWriter writer;
	private Cache schoolaccountCache, statisticsCache;

	private Logger logger;
	ExamConfig examConfig;
	String stffID = "";


	GradingSystem gradingSystem;
	private String PDF_SUBTITLE ="";
	private String schoolname = "";
	private String title = "";

	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;
	private static ParentsDAO parentsDAO;

	private static StudentDAO studentDAO;
	private static RoomDAO roomDAO;
	private static ExamConfigDAO examConfigDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static TermFeeDAO termFeeDAO;
	private static TeacherSubClassDAO teacherSubClassDAO;

	private static StaffDetailsDAO staffDetailsDAO;
	private static ClassTeacherDAO classTeacherDAO;

	private static StudentOtherMoniesDAO studentOtherMoniesDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static MiscellanousDAO miscellanousDAO;
	private static BarWeightDAO barWeightDAO;
	private static PrimaryDAO primaryDAO;
	private static DeviationDAO deviationDAO;
	//private StudentBalance studentBal;
	private SendResultSMS sendResultSMS;
	private static SmsSendDAO smsSendDAO;
	private static SmsApiDAO smsApiDAO;

	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>();
	HashMap<String, String> firstnameHash = new HashMap<String, String>();
	HashMap<String, String> roomHash = new HashMap<String, String>();
	HashMap<String, String> admYearMap = new HashMap<String, String>();
	HashMap<String, String> shameMap = new HashMap<String, String>();
	
	HashMap<String, String> admtermMap = new HashMap<String, String>();
	HashMap<String, Integer> finalyearMap = new HashMap<String, Integer>();
	HashMap<String, Date> admdaterMap = new HashMap<String, Date>();
   


	double score = 0;

	double engscore = 0;
	String engscorestr = "";

	double kswscore = 0;
	String kswscorestr = "";

	double matscore = 0;
	String matscorestr = "";

	double physcore = 0;
	String physcorestr = "";  

	double bioscore = 0;
	String bioscorestr = "";

	double chemscore = 0;
	String chemscorestr = "";

	double bsscore = 0;
	String bsscorestr = "";

	double comscore = 0;
	String comscorestr = "";

	double hscscore = 0;
	String hscscorestr = "";

	double agriscore = 0;
	String agriscorestr = "";

	double geoscore = 0;
	String geoscorestr = "";

	double crescore = 0;
	String crescorestr = "";

	double histscore = 0;
	String histscorestr = "";



	String grade = "";
	String studeadmno = "";
	String firstnamee = "";
	String studename = "";
	String admno = "";
	

	double cat1 = 0;  double cat2  = 0;double endterm  = 0;
	double catgrandscores  = 0;double catmean  = 0;double examcatgrandscore  = 0;


	int position = 1;


	String USER= "";
	String path ="";
	private String photopath ="";

	String EndTermOnly = "";
	String EndTermAndC2 = "";
	String EndTermC1AndC2 = "";


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
		subjectDAO = SubjectDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		roomDAO = RoomDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();

		termFeeDAO = TermFeeDAO.getInstance();
		teacherSubClassDAO = TeacherSubClassDAO.getInstance();
		staffDetailsDAO = StaffDetailsDAO.getInstance();
		classTeacherDAO = ClassTeacherDAO.getInstance();
		parentsDAO = ParentsDAO.getInstance();

		studentOtherMoniesDAO = StudentOtherMoniesDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		miscellanousDAO = MiscellanousDAO.getInstance();
		barWeightDAO = BarWeightDAO.getInstance();
		primaryDAO = PrimaryDAO.getInstance();
		deviationDAO = DeviationDAO.getInstance();
		//studentBal = new StudentBalance();
		sendResultSMS = new SendResultSMS();
		smsSendDAO = SmsSendDAO.getInstance();
		smsApiDAO = SmsApiDAO.getInstance();



		USER = System.getProperty("user.name");
		path = "/home/"+USER+"/school/logo/logo.png";
		photopath = "/home/"+USER+"/school/logo/photos/";
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

		HttpSession session = request.getSession(true);
		response.setContentType("application/pdf");



		SchoolAccount school = new SchoolAccount();

		String classroomuuid = "";
		String schoolusername = "";
		String classID = "";
		

		if(session !=null){
			schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
			//schoolusername = "demo";
			stffID = StringUtils.trimToEmpty(request.getParameter("staffid"));

		}
		classID = StringUtils.trimToEmpty(request.getParameter("classID"));
		//System.out.println("classID="+classID);
		net.sf.ehcache.Element element;
		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (SchoolAccount) element.getObjectValue();
		}
		
		//System.out.println("stffID: " + stffID + ", classIDL " + classID);


		examConfig = examConfigDAO.getExamConfig(school.getUuid());
		gradingSystem = gradingSystemDAO.getGradingSystem(school.getUuid());
		
		EndTermOnly = examConfig.geteT();
		EndTermAndC2 = examConfig.geteTCtwo();
		EndTermC1AndC2 = examConfig.geteTConetwo();

		ClassTeacher classTeacher = classTeacherDAO.getClassTeacherByteacherId(stffID);
		if(classTeacher !=null){
			classroomuuid = classTeacher.getClassRoomUuid();
		}

			SessionStatistics statistics = new SessionStatistics();
			if ((element = statisticsCache.get(schoolusername)) != null) {
				statistics = (SessionStatistics) element.getObjectValue();
			}

			schoolname = school.getSchoolName().toUpperCase()+"\n";
			PDF_SUBTITLE =  "P.O BOX "+school.getPostalAddress()+"\n" 
					+ ""+school.getTown()+" - Kenya\n" 
					+ "" + school.getMobile()+"\n"
					+ "" + school.getEmail()+"\n";

			title = "_____________________________________ \n"
					+ " End of Term Report Card ";

			List<Perfomance> pDistinctListGeneral = new ArrayList<Perfomance>();
			pDistinctListGeneral = perfomanceDAO.getPerfomanceListDistinctGeneral(school.getUuid(), classID,examConfig.getTerm(),examConfig.getYear());


			List<Perfomance> pDistinctList = new ArrayList<Perfomance>(); 
			pDistinctList = perfomanceDAO.getPerfomanceListDistinct(school.getUuid(), classroomuuid,examConfig.getTerm(),examConfig.getYear());
			
			List<Student> studentList = new ArrayList<Student>(); 
			studentList = studentDAO.getAllStudentList(school.getUuid());
			
			String admYear = "";
			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("yyyy");
			Date admdate;
			
			String admTerm;int finalYear = 0;
			for(Student stu : studentList){
				studentAdmNoHash.put(stu.getUuid(),stu.getAdmno()); 
				
				 String formatedFirstname = StringUtils.capitalize(stu.getFirstname().toLowerCase());
				 String formatedLastname = StringUtils.capitalize(stu.getLastname().toLowerCase());
				 String formatedsurname = StringUtils.capitalize(stu.getSurname().toLowerCase());
				
				formatedFirstname = formatedFirstname.substring(0, Math.min(formatedFirstname.length(), 10));
				formatedLastname = formatedLastname.substring(0, Math.min(formatedLastname.length(), 10));
				formatedsurname = formatedsurname.substring(0, Math.min(formatedsurname.length(), 10));
				
				admdate = stu.getAdmissionDate();
				admdaterMap.put(stu.getUuid(), admdate);
				admTerm = stu.getRegTerm();
				admtermMap.put(stu.getUuid(), admTerm);
				
				admYear = formatter.format(admdate);
				finalYear = stu.getFinalYear();
				finalyearMap.put(stu.getUuid(), finalYear);
				 //admtermMap finalyearMap
				admYearMap.put(stu.getUuid(), admYear); 
				studNameHash.put(stu.getUuid(),formatedFirstname + " " + formatedLastname + " " + formatedsurname +"\n"); 
				firstnameHash.put(stu.getUuid(), formatedFirstname);
			}

			List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
			classroomList = roomDAO.getAllRooms(school.getUuid()); 
			for(ClassRoom c : classroomList){
				roomHash.put(c.getUuid() , c.getRoomName());
			}



			String fileName = new StringBuffer(StringUtils.trimToEmpty("ReportCards")) 
					.append("_")
					.append(roomHash.get(classroomuuid).replaceAll(" ", "_"))
					.append(".pdf")
					.toString();
			response.setHeader("Content-Disposition", "inline; filename=\""+fileName);


			document = new Document(PageSize.A4, 46, 46, 64, 64);

			try {
				writer = PdfWriter.getInstance(document, response.getOutputStream());

				PdfUtil event = new PdfUtil();
				writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
				writer.setPageEvent(event);

			    populatePDFDocument(statistics, school,classroomuuid,classID,pDistinctList,pDistinctListGeneral,path, photopath);
				
			} catch (DocumentException e) {
				logger.error("DocumentException while writing into the document");
				logger.error(ExceptionUtils.getStackTrace(e));
			}

	}



	/**
	 * @param path2 
	 * @param pDistinctListGeneral 
	 * @param pDistinctList 
	 * @param classID 
	 * @param classroomuuid 
	 * @param school 
	 * @param statistics 
	 * @param statistics
	 * @param school
	 * @param classroomuuid
	 * @param classID 
	 * @param perfomanceList
	 * @param pDistinctList
	 * @param pDistinctListGeneral 
	 * @param perfomanceListGeneral 
	 * @param realPath
	 */
	private void populatePDFDocument(SessionStatistics statistics, SchoolAccount school, String classroomuuid,
			String classID, List<Perfomance> pDistinctList, List<Perfomance> pDistinctListGeneral, String realPath , String photopath) {



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

		Map<String,Double> MEANMapgn = new LinkedHashMap<String,Double>();
		Map<String,String> POSMapgn = new LinkedHashMap<String,String>();


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
		double  totalscoregn = 0,grandscoregn = 0;
		double totalgrandscoregn = 0;

		try {

			document.open();
			document.add(new Paragraph("."));


			

			Paragraph content = new Paragraph();
			content.add(new Paragraph((schoolname +"") , courierBold14));//
			content.add(new Paragraph((PDF_SUBTITLE +"") , timesRomanItalic8));
			content.add(new Paragraph((title +" \n\n") , courierBold14));

			PdfPCell contentcell = new PdfPCell(content);
			contentcell.setBorder(Rectangle.NO_BORDER); 
			contentcell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			PdfPCell logo = new PdfPCell();
			logo.addElement(createImage(realPath,1)); 
			logo.setBorder(Rectangle.NO_BORDER); 
			logo.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			


			//SimpleDateFormat formatter;
			//formatter = new SimpleDateFormat("dd, MMM yyyy");

			DecimalFormat df = new DecimalFormat("0.00"); 
			df.setRoundingMode(RoundingMode.DOWN);

			DecimalFormat rf = new DecimalFormat("0.0"); 
			rf.setRoundingMode(RoundingMode.HALF_UP);

			DecimalFormat rf2 = new DecimalFormat("0"); 
			rf2.setRoundingMode(RoundingMode.UP);



			//perfomanceListGeneral,pDistinctListGeneral
			int Finalposition = 0;

			int mycountgn =1;
			
			double bestTechinical = 0;
			double bestTechinical2 = 0;


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
					totalgrandscoregn = 0;

					for(Perfomance pp : listGeneral){


						cat1gn = pp.getCatOne();
						cat2gn = pp.getCatTwo();
						endtermgn = pp.getEndTerm();

						totalscoregn = 0;
						/*COMPASARY
						 * 1 ENG*/
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.ENG_UUID) ){

							 cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
							 cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
							 endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscoregn = (endtermgn/70)*100;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscoregn = cat2gn + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								 
							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  
							 }
							 
							  engscoregn = totalscoregn;				
							  grandscoregn += totalscoregn;
							  totalscoregn = 0;				
							  engscorehashgn.put(pD.getStudentUuid(),engscoregn);
							 // end exam logic

							 /*totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
							 totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
                             */
							


						 }

						 /*2 KISW*/					
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.KISWA_UUID)){

							 cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
							 cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
							 endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscoregn = (endtermgn/70)*100;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								 

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscoregn = cat2gn + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								 

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  
							 }
							 // end exam logic
							 
							  kswscoregn = totalscoregn;				
							  grandscoregn += totalscoregn;
							  totalscoregn = 0;				
							  kswscoreMapgn.put(pD.getStudentUuid(),kswscoregn);

							 

						 }

						 /*3 PHY*/			
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.PHY_UUID)){

							 cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
							 cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
							 endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscoregn = (endtermgn/70)*100;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscoregn = cat2gn + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								 

							 }
							 // end exam logic

							// totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
							// totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));

							 physcoregn = totalscoregn;				
							 grandscoregn += totalscoregn;
							 totalscoregn = 0;				
							 physcoreMapgn.put(pD.getStudentUuid(),physcoregn);

						 }


						 /*4 BIO*/					
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.BIO_UUID)){

							 cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
							 cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
							 endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscoregn = (endtermgn/70)*100;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscoregn = cat2gn + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								 

							 }
							 // end exam logic

							// totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
							 //totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));

							 bioscoregn = totalscoregn;				
							 grandscoregn += totalscoregn;
							 totalscoregn = 0;				
							 bioscoreMapgn.put(pD.getStudentUuid(),bioscoregn);

						 }
						 /*5 CHEM*/				
						 if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.CHEM_UUID)){

							 cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
							 cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
							 endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscoregn = (endtermgn/70)*100;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscoregn = cat2gn + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								 

							 }
							 // end exam logic

							 //totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
							 //totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));

							 chemscoregn = totalscoregn;				
							 grandscoregn += totalscoregn;
							 totalscoregn = 0;				
							 chemscorehashgn.put(pD.getStudentUuid(),chemscoregn);

						 }
						 /*6 MATH
						  * 
						  * END OF COMPASARY*/
						 if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.MATH_UUID)){                	  

							  cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
							  cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
							  endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));

							  /**  exam logic, determine which exam is being done     */
								 if(StringUtils.equals(EndTermOnly, "ON")){
									
									  totalscoregn = (endtermgn/70)*100;
									  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
									  

								 }else if(StringUtils.equals(EndTermAndC2, "ON")){
									 
									  totalscoregn = cat2gn + endtermgn;
									  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
									  

								 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
									 
									  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
									  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
									 

								 }
								 // end exam logic

							  //totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
							  //totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));

							  matscoregn = totalscoregn;				
							  grandscoregn += totalscoregn;
							  totalscoregn = 0;				
							  matscorehashgn.put(pD.getStudentUuid(),matscoregn);

						  }
						  /*HUMANITIES
						   * 7 HIST*/	 
						  if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.HIST_UUID)){

							   cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
							   cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
							   endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));

							   /**  exam logic, determine which exam is being done     */
								 if(StringUtils.equals(EndTermOnly, "ON")){
									
									  totalscoregn = (endtermgn/70)*100;
									  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
									  

								 }else if(StringUtils.equals(EndTermAndC2, "ON")){
									 
									  totalscoregn = cat2gn + endtermgn;
									  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
									  

								 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
									 
									  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
									  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
									 

								 }
								 // end exam logic

							   // totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
							   // totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));

							    histscoregn = totalscoregn;
							    grandscoregn += totalscoregn;
							    totalscoregn = 0;
							    histscoreMapgn.put(pD.getStudentUuid(),histscoregn);

						  }

							   /*8 GEO*/			
							   if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.GEO_UUID)){

								   cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
								   cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
								   endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));

								   /**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										
										  totalscoregn = (endtermgn/70)*100;
										  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
										  

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
										  totalscoregn = cat2gn + endtermgn;
										  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
										  

									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
										  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
										 

									 }
									 // end exam logic

								   //totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
								   //totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));

								   geoscoregn = totalscoregn;				
								   grandscoregn += totalscoregn;
								   totalscoregn = 0;				
								   geoscoreMapgn.put(pD.getStudentUuid(),geoscoregn);

							   }
							   /*9 CRE 
							    * END HUMANITY*/
							 if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.CRE_UUID)){

							    	cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
							    	cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
							    	endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));

							    	/**  exam logic, determine which exam is being done     */
									 if(StringUtils.equals(EndTermOnly, "ON")){
										
										  totalscoregn = (endtermgn/70)*100;
										  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
										  

									 }else if(StringUtils.equals(EndTermAndC2, "ON")){
										 
										  totalscoregn = cat2gn + endtermgn;
										  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
										  

									 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
										 
										  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
										  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
										 

									 }
									 // end exam logic

							    	//totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
							    	//totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));

							    	crescoregn = totalscoregn;				
							    	grandscoregn += totalscoregn;
							    	totalscoregn = 0;	
							    	crescorehashgn.put(pD.getStudentUuid(),crescoregn);

							    }

							    // end exam logic


						   /*TECHINICAL 
					                   TAKE BS AND CHOOSE 1 */	
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.BS_UUID)){
				
						cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
						cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
						endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));
				
						 /**  exam logic, determine which exam is being done     */
						 if(StringUtils.equals(EndTermOnly, "ON")){
							
							  totalscoregn = (endtermgn/70)*100;
							  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
							  
				
						 }else if(StringUtils.equals(EndTermAndC2, "ON")){
							 
							  totalscoregn = cat2gn + endtermgn;
							  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
							  
				
						 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
							 
							  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
							  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
							 
				
						 }
						 // end exam logic
				
						//totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
						//totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
				
						bsscoregn = totalscoregn;				
						grandscoregn += totalscoregn;
						totalscoregn = 0;				
						bsscoreMapgn.put(pD.getStudentUuid(),bsscoregn);
				
					}
				
				
					//double bestTechinical = 0;
					if(true){
						/*BEGIN CHOOSE */	if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.AGR_UUID)){
				
							cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
							cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
							endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));
				
							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscoregn = (endtermgn/70)*100;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  
				
							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscoregn = cat2gn + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  
				
							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								 
				
							 }
							 // end exam logic
				
							//totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
							//totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
				
							agriscoregn = totalscoregn;				
							//grandscoregn += totalscoregn;
							//totalscoregn = 0;				
							agriscorehashgn.put(pD.getStudentUuid(),agriscoregn);
				
				
						}
				
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.COMP_UUID)){
				
							cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
							cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
							endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));
				
							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscoregn = (endtermgn/70)*100;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  
				
							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscoregn = cat2gn + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  
				
							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								 
				
							 }
							 // end exam logic
							//totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
							//totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
				
							comscoregn = totalscoregn;				
							//grandscoregn += totalscoregn;
							//totalscoregn = 0;				
							comscoreMapgn.put(pD.getStudentUuid(),comscoregn);
				
						} 
				
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.H_S)){
				
							cat1gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1gn)))));
							cat2gn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2gn)))));
							endtermgn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endtermgn)))));
				
							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscoregn = (endtermgn/70)*100;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  
				
							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscoregn = cat2gn + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								  
				
							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
								  totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
								 
				
							 }
							 // end exam logic
				
				
							//totalscoregn = ((cat1gn+cat2gn)/2) + endtermgn;
							//totalscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscoregn)))));
				
							hscscoregn = totalscoregn;				
							//grandscoregn += totalscoregn;
							//totalscoregn = 0;
				
							hscscoreMapgn.put(pD.getStudentUuid(),hscscoregn);
				
						}// agriscoregn,comscoregn,hscscoregn
				
						bestTechinical = Math.max( (Math.max(agriscoregn, comscoregn)), Math.max(Math.max(agriscoregn, comscoregn), hscscoregn));
						//grandscoregn += bestTechinical;
						bestTechinical2 = bestTechinical;
						bestTechinical = 0;
				
					}//end if true
				
				
				
					// end exam logic
				
				
				}
				

					grandscoregn += bestTechinical2;
					totalgrandscoregn += grandscoregn;
					grandscoregn = 0;
					bestTechinical2 = 0;
					
					grandscoremapgn.put(pD.getStudentUuid(), totalgrandscoregn);
					totalgrandscoregn = 0;
				
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



				double meangn = 0;
				int counttwogn = 1;
				int positiongn = 1;

				String totalzgn = "";
				for(Object o : as){

					String items = String.valueOf(o);
					String [] item = items.split("=");
					String uuid = item[0];

					totalzgn = item[1];

					double the_grandscoregn = 0;
					the_grandscoregn = Double.parseDouble(totalzgn);
					meangn = the_grandscoregn/11; 
					MEANMapgn.put(uuid,meangn);
					//TODO save this mean for future use  
					Deviation dev;
					if(deviationDAO.getDev(uuid, examConfig.getYear()) !=null){
						dev = deviationDAO.getDev(uuid, examConfig.getYear());
					}else{
						dev = new Deviation();
					}



					if(StringUtils.equals(examConfig.getTerm(), "1")){
						dev.setStudentUuid(uuid);
						dev.setYear(examConfig.getYear());
						dev.setDevOne(meangn);
						deviationDAO.putDev(dev,uuid,examConfig.getYear());


					}else if(StringUtils.equals(examConfig.getTerm(), "2")){
						dev.setStudentUuid(uuid);
						dev.setYear(examConfig.getYear());
						dev.setDevTwo(meangn);
						deviationDAO.putDev(dev,uuid,examConfig.getYear());


					}else if(StringUtils.equals(examConfig.getTerm(), "3")){
						dev.setStudentUuid(uuid);
						dev.setYear(examConfig.getYear());
						dev.setDevThree(meangn); 
						deviationDAO.putDev(dev,uuid,examConfig.getYear());

					}


					String pos = "";
					if(meangn==numbergn){
						pos = (" " +(positiongn-counttwogn++)+ " / " +Finalposition);
						POSMapgn.put(uuid,pos);
					}
					else{
						counttwogn=1;
						pos = (" " +positiongn+ " / " +Finalposition);
						POSMapgn.put(uuid,pos);
					}

					positiongn++;
					numbergn=meangn;



				}

			}

			/** end general ###################################################################
			 * ################################################################################################################################# */

			Map<String,Double> kswscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> engscorehash = new LinkedHashMap<String,Double>(); 

			Map<String,Double> physcoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> matscorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> bioscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> chemscorehash = new LinkedHashMap<String,Double>(); 

			Map<String,Double> bsscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> agriscorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> hscscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> comscoreMap = new LinkedHashMap<String,Double>();

			Map<String,Double> geoscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> crescorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> histscoreMap = new LinkedHashMap<String,Double>();



			Map<String,Double> engcat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> kiscat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> matcat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> phycat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> biocat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> chemcat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> bscat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> agrcat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> comcat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> hsccat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> geocat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> hiscat1scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> crecat1scoreMap = new LinkedHashMap<String,Double>();

			Map<String,Double> engcat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> kiscat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> matcat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> phycat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> biocat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> chemcat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> bscat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> agrcat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> comcat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> hsccat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> geocat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> hiscat2scoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> crecat2scoreMap = new LinkedHashMap<String,Double>();

			Map<String,Double> engendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> kisendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> matendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> phyendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> bioendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> chemendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> bsendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> agrendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> comendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> hscendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> geoendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> hisendtscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> creendtscoreMap = new LinkedHashMap<String,Double>();



			List<Perfomance> list = new ArrayList<>();
			Map<String,Double> grandscoremap = new LinkedHashMap<String,Double>(); 

			double grandscore = 0;
			double totalscore = 0;
			double totalgrandscore = 0;
			double number = 0.0;
			int theFinalposition = 0;

			bestTechinical = 0;
			bestTechinical2 = 0;
			
			if(pDistinctList !=null){

				totalscore = 0;
				totalgrandscore = 0;
				grandscore = 0;
				int mycount =1;


				for(Perfomance s : pDistinctList){                              
					list = perfomanceDAO.getPerformance(school.getUuid(), classroomuuid, s.getStudentUuid(),examConfig.getTerm(),examConfig.getYear());
					engscore = 0;
					kswscore = 0;
					matscore = 0;
					physcore = 0;
					bioscore = 0;
					chemscore = 0;
					bsscore = 0;
					comscore = 0;
					hscscore = 0;
					agriscore = 0;
					geoscore = 0;
					crescore = 0;
					histscore = 0;

					examcatgrandscore = 0; cat1  = 0; cat2  = 0; endterm = 0; catgrandscores = 0; catmean = 0;

					for(Perfomance pp : list){

						cat1 = pp.getCatOne();
						cat2 = pp.getCatTwo();
						endterm = pp.getEndTerm();

						totalscore = 0;
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.ENG_UUID) ){

							cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
							cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
							endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscore = (endterm/70)*100;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscore = cat2 + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscore = ((cat1+cat2)/2) + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								 

							 }
							 // end exam logic

							/*totalscore = ((cat1+cat2)/2) + endterm;
							totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));*/

							engscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							engscorehash.put(s.getStudentUuid(),engscore);
							engcat1scoreMap.put(s.getStudentUuid(), cat1);
							engcat2scoreMap.put(s.getStudentUuid(), cat2);
							engendtscoreMap.put(s.getStudentUuid(), endterm);
						}

						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.KISWA_UUID)){

							cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
							cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
							endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscore = (endterm/70)*100;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscore = cat2 + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscore = ((cat1+cat2)/2) + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								 

							 }
							 // end exam logic

							/*totalscore = ((cat1+cat2)/2) + endterm;
							totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));*/

							kswscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;


							kswscoreMap.put(s.getStudentUuid(),kswscore);
							kiscat1scoreMap.put(s.getStudentUuid(), cat1);                        
							kiscat2scoreMap.put(s.getStudentUuid(), cat2);
							kisendtscoreMap.put(s.getStudentUuid(), endterm);

						}

						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.PHY_UUID)){

							cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
							cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
							endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscore = (endterm/70)*100;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscore = cat2 + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscore = ((cat1+cat2)/2) + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								 

							 }
							 // end exam logic

							/*totalscore = ((cat1+cat2)/2) + endterm;
							totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
*/
							physcore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							physcoreMap.put(s.getStudentUuid(),physcore);
							phycat1scoreMap.put(s.getStudentUuid(), cat1);                  
							phycat2scoreMap.put(s.getStudentUuid(), cat2);
							phyendtscoreMap.put(s.getStudentUuid(), endterm);

						}


						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.BIO_UUID)){

							cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
							cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
							endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscore = (endterm/70)*100;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscore = cat2 + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscore = ((cat1+cat2)/2) + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								 

							 }
							 // end exam logic
/*
							totalscore = ((cat1+cat2)/2) + endterm;
							totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
*/
							bioscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							bioscoreMap.put(s.getStudentUuid(),bioscore);
							biocat1scoreMap.put(s.getStudentUuid(), cat1);              
							biocat2scoreMap.put(s.getStudentUuid(), cat2);
							bioendtscoreMap.put(s.getStudentUuid(), endterm);

						}
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.CHEM_UUID)){

							cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
							cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
							endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscore = (endterm/70)*100;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscore = cat2 + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscore = ((cat1+cat2)/2) + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								 

							 }
							 // end exam logic

							/*totalscore = ((cat1+cat2)/2) + endterm;
							totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));*/

							chemscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							chemscorehash.put(s.getStudentUuid(),chemscore);
							chemcat1scoreMap.put(s.getStudentUuid(), cat1);                           
							chemcat2scoreMap.put(s.getStudentUuid(), cat2);
							chemendtscoreMap.put(s.getStudentUuid(), endterm);

						}
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.MATH_UUID)){                	  

							cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
							cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
							endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscore = (endterm/70)*100;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscore = cat2 + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscore = ((cat1+cat2)/2) + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								 

							 }
							 // end exam logic
/*
							totalscore = ((cat1+cat2)/2) + endterm;
							totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
*/
							matscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							matscorehash.put(s.getStudentUuid(),matscore);
							matcat1scoreMap.put(s.getStudentUuid(), cat1);                          
							matcat2scoreMap.put(s.getStudentUuid(), cat2);
							matendtscoreMap.put(s.getStudentUuid(), endterm);

						}
						/** end of */						

						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.GEO_UUID)){

							cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
							cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
							endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscore = (endterm/70)*100;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscore = cat2 + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscore = ((cat1+cat2)/2) + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								 

							 }
							 // end exam logic
/*
							totalscore = ((cat1+cat2)/2) + endterm;
							totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
*/
							geoscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							geoscoreMap.put(s.getStudentUuid(),geoscore);
							geocat1scoreMap.put(s.getStudentUuid(), cat1);                        
							geocat2scoreMap.put(s.getStudentUuid(), cat2);
							geoendtscoreMap.put(s.getStudentUuid(), endterm);

						}
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.CRE_UUID)){

							cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
							cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
							endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscore = (endterm/70)*100;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscore = cat2 + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscore = ((cat1+cat2)/2) + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								 

							 }
							 // end exam logic
/*
							totalscore = ((cat1+cat2)/2) + endterm;
							totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
*/
							crescore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							crescorehash.put(s.getStudentUuid(),crescore);
							crecat1scoreMap.put(s.getStudentUuid(), cat1);                        
							crecat2scoreMap.put(s.getStudentUuid(), cat2);
							creendtscoreMap.put(s.getStudentUuid(), endterm);

						}
						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.HIST_UUID)){

							cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
							cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
							endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscore = (endterm/70)*100;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscore = cat2 + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscore = ((cat1+cat2)/2) + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								 

							 }
							 // end exam logic
/*
							totalscore = ((cat1+cat2)/2) + endterm;
							totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
*/
							histscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							histscoreMap.put(s.getStudentUuid(),histscore);
							hiscat1scoreMap.put(s.getStudentUuid(), cat1);                       
							hiscat2scoreMap.put(s.getStudentUuid(), cat2);
							hisendtscoreMap.put(s.getStudentUuid(), endterm);

						}

						/** bs plus one*/


						if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.BS_UUID)){

							cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
							cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
							endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

							 /**  exam logic, determine which exam is being done     */
							 if(StringUtils.equals(EndTermOnly, "ON")){
								
								  totalscore = (endterm/70)*100;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermAndC2, "ON")){
								 
								  totalscore = cat2 + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								  

							 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
								 
								  totalscore = ((cat1+cat2)/2) + endterm;
								  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
								 

							 }
							 // end exam logic
/*
							totalscore = ((cat1+cat2)/2) + endterm;
							totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
*/
							bsscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							bsscoreMap.put(s.getStudentUuid(),bsscore);
							bscat1scoreMap.put(s.getStudentUuid(), cat1);                        
							bscat2scoreMap.put(s.getStudentUuid(), cat2);
							bsendtscoreMap.put(s.getStudentUuid(), endterm);

						}

						/** choose one*/
						//double bestTechinical = 0;
						if(true){
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.AGR_UUID)){

								cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
								cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
								endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

								 /**  exam logic, determine which exam is being done     */
								 if(StringUtils.equals(EndTermOnly, "ON")){
									
									  totalscore = (endterm/70)*100;
									  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
									  

								 }else if(StringUtils.equals(EndTermAndC2, "ON")){
									 
									  totalscore = cat2 + endterm;
									  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
									  

								 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
									 
									  totalscore = ((cat1+cat2)/2) + endterm;
									  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
									 

								 }
								 // end exam logic
/*
								totalscore = ((cat1+cat2)/2) + endterm;
								totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
*/
								agriscore = totalscore;

								//grandscore += totalscore;
								//totalscore = 0;

								agriscorehash.put(s.getStudentUuid(),agriscore);
								agrcat1scoreMap.put(s.getStudentUuid(), cat1);                         
								agrcat2scoreMap.put(s.getStudentUuid(), cat2);
								agrendtscoreMap.put(s.getStudentUuid(), endterm);

							}


							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.H_S)){

								cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
								cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
								endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

								 /**  exam logic, determine which exam is being done     */
								 if(StringUtils.equals(EndTermOnly, "ON")){
									
									  totalscore = (endterm/70)*100;
									  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
									  

								 }else if(StringUtils.equals(EndTermAndC2, "ON")){
									 
									  totalscore = cat2 + endterm;
									  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
									  

								 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
									 
									  totalscore = ((cat1+cat2)/2) + endterm;
									  totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
									 

								 }
								 // end exam logic
/*
								totalscore = ((cat1+cat2)/2) + endterm;
								totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
*/
								hscscore = totalscore;

								//grandscore += totalscore;
								//totalscore = 0;

								hscscoreMap.put(s.getStudentUuid(),hscscore);
								hsccat1scoreMap.put(s.getStudentUuid(), cat1);                          
								hsccat2scoreMap.put(s.getStudentUuid(), cat2);
								hscendtscoreMap.put(s.getStudentUuid(), endterm);

							}if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.COMP_UUID)){

								cat1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat1)))));
								cat2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(cat2)))));
								endterm = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(endterm)))));

								 /**  exam logic, determine which exam is being done     */
								 if(StringUtils.equals(EndTermOnly, "ON")){
									
									 totalscore = (endterm/70)*100;
									 totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
									  

								 }else if(StringUtils.equals(EndTermAndC2, "ON")){
									 
									 totalscore = cat2 + endterm;
									 totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
									  

								 }else if(StringUtils.equals(EndTermC1AndC2, "ON")){//
									 
									 totalscore = ((cat1+cat2)/2) + endterm;
									 totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
									 

								 }
								 // end exam logic
/*
								totalscore = ((cat1+cat2)/2) + endterm;
								totalscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(totalscore)))));
*/
								comscore = totalscore;

								//grandscore += totalscore;
								//totalscore = 0;

								comscoreMap.put(s.getStudentUuid(),comscore);
								comcat1scoreMap.put(s.getStudentUuid(), cat1);                           
								comcat2scoreMap.put(s.getStudentUuid(), cat2);
								comendtscoreMap.put(s.getStudentUuid(), endterm);

							} 



							bestTechinical = Math.max( (Math.max(agriscore, comscore)), Math.max(Math.max(agriscore, comscore), hscscore));
							//grandscore += bestTechinical;
							bestTechinical2 = bestTechinical;
							bestTechinical = 0;
							

						}// end if true
						
						  
					}
					  grandscore += bestTechinical2;
					  totalgrandscore += grandscore;
					  bestTechinical2 = 0;
					  grandscore = 0;

					//System.out.println("total in repoty card ="+totalgrandscore);
					grandscoremap.put(s.getStudentUuid(), totalgrandscore);
					totalgrandscore = 0;


					theFinalposition = mycount++;

				}


				double engc1 = 0,kisc1 = 0,matc1 = 0,phyc1 = 0,chemc1 = 0,bioc1 = 0;
				double bsc1 = 0,agrc1 = 0,hscc1 = 0,comc1 = 0,crec1 = 0,hisc1 = 0,geoc1 = 0;

				double engc2 = 0,kisc2 = 0,matc2 = 0,phyc2 = 0,chemc2 = 0,bioc2 = 0;
				double bsc2 = 0,agrc2 = 0,hscc2 = 0,comc2 = 0,crec2 = 0,hisc2 = 0,geoc2 = 0;

				double enget = 0,kiset = 0,matet = 0,phyet = 0,chemet = 0,bioet = 0;
				double bset = 0,agret = 0,hscet = 0,comet = 0,creet = 0,hiset = 0,geoet = 0;

				String engc1str = "",kisc1str = "",matc1str = "",phyc1str = "",chemc1str = "",bioc1str = "";
				String bsc1str = "",agrc1str = "",hscc1str = "",comc1str = "",crec1str = "",hisc1str = "",geoc1str = "";

				String engc2str = "",kisc2str = "",matc2str = "",phyc2str = "",chemc2str = "",bioc2str = "";
				String bsc2str = "",agrc2str = "",hscc2str = "",comc2str = "",crec2str = "",hisc2str = "",geoc2str = "";

				String engetstr = "",kisetstr = "",matetstr = "",phyetstr = "",chemetstr = "",bioetstr = "";
				String bsetstr = "",agretstr = "",hscetstr = "",cometstr = "",creetstr = "",hisetstr = "",geoetstr = "";




				@SuppressWarnings("unchecked")
				ArrayList<?> as = new ArrayList(grandscoremap.entrySet());
				Collections.sort(as,new Comparator(){
					public int compare(Object o1,Object o2){
						Map.Entry e1 = (Map.Entry)o1;
						Map.Entry e2 = (Map.Entry)o2;
						Double f = (Double)e1.getValue();
						Double s = (Double)e2.getValue();
						return s.compareTo(f);
					}
				});



				position = 1;
				double mean = 0;
				int counttwo = 1;
				//double grandscoreT = 0;
				String totalz = "";
				for(Object o : as){
					String items = String.valueOf(o);
					String [] item = items.split("=");
					String uuid = item[0];

					totalz = item[1];
					mean = Double.parseDouble(totalz)/11;  



					studeadmno = studentAdmNoHash.get(uuid);
					studename = studNameHash.get(uuid);    
					firstnamee = firstnameHash.get(uuid);


					engc1 = 0;kisc1 = 0;matc1 = 0;phyc1 = 0;chemc1 = 0;bioc1 = 0;
					bsc1 = 0;agrc1 = 0;hscc1 = 0;comc1 = 0;crec1 = 0;hisc1 = 0;geoc1 = 0;

					engc2 = 0;kisc2 = 0;matc2 = 0;phyc2 = 0;chemc2 = 0;bioc2 = 0;
					bsc2 = 0;agrc2 = 0;hscc2 = 0;comc2 = 0;crec2 = 0;hisc2 = 0;geoc2 = 0;

					enget = 0;kiset = 0;matet = 0;phyet = 0;chemet = 0;bioet = 0;
					bset = 0;agret = 0;hscet = 0;comet = 0;creet = 0;hiset = 0;geoet = 0;

					//grandscoreT = 0;

					if(engscorehash.get(uuid)!=null && engscorehash.get(uuid)!=0){
						engscore = engscorehash.get(uuid);  
						//grandscoreT += engscore;
						engscorestr =  rf2.format(engscore);

					}else{
						engscorestr = "";
					}



					if(engcat1scoreMap.get(uuid)!=null && engcat1scoreMap.get(uuid) != 0){
						engc1 = engcat1scoreMap.get(uuid); 
						engc1str =  rf2.format(engc1);
					}else{
						engc1str = " ";
					}
					
					if(engcat2scoreMap.get(uuid)!=null && engcat2scoreMap.get(uuid)!=0){
						engc2 = engcat2scoreMap.get(uuid); 
						engc2str =  rf2.format(engc2);
					}else{
						engc2str = " ";
					}
					
					if(engendtscoreMap.get(uuid)!=null && engendtscoreMap.get(uuid)!=0){
						enget = engendtscoreMap.get(uuid);
						engetstr = rf2.format(enget);
					}else{
						engetstr = " ";
					}



					if(kswscoreMap.get(uuid)!=null && kswscoreMap.get(uuid)!=0){
						kswscore = kswscoreMap.get(uuid); 
						//grandscoreT += kswscore;
						kswscorestr = rf2.format(kswscore);

					}else{
						kswscorestr = "";
					}




					if(kiscat1scoreMap.get(uuid)!=null && kiscat1scoreMap.get(uuid)!=0){
						kisc1 = kiscat1scoreMap.get(uuid); 
						kisc1str = rf2.format(kisc1);
					}else{
						kisc1str = " ";
					}
					
					if(kiscat2scoreMap.get(uuid)!=null && kiscat2scoreMap.get(uuid)!=0){
						kisc2 = kiscat2scoreMap.get(uuid);   
						kisc2str = rf2.format(kisc2);
					}else{
						kisc2str = " ";
					}
					
					if(kisendtscoreMap.get(uuid)!=null && kisendtscoreMap.get(uuid)!=0){
						kiset = kisendtscoreMap.get(uuid);  
						kisetstr = rf2.format(kiset);
					}else{
						kisetstr = " ";
					}


					if(physcoreMap.get(uuid)!=null && physcoreMap.get(uuid)!=0){
						physcore = physcoreMap.get(uuid);   
						//grandscoreT += physcore;
						physcorestr = rf2.format(physcore);

					}else{
						physcorestr = "";
					}



					if(phycat1scoreMap.get(uuid)!=null && phycat1scoreMap.get(uuid)!=0){
						phyc1 = phycat1scoreMap.get(uuid);   
						phyc1str = rf2.format(phyc1);
					}else{
						phyc1str = " ";
					}
					
					if(phycat2scoreMap.get(uuid)!=null && phycat2scoreMap.get(uuid)!=0){
						phyc2 = phycat2scoreMap.get(uuid); 
						phyc2str = rf2.format(phyc2);
					}else{
						phyc2str = " ";
					}
					
					if(phyendtscoreMap.get(uuid)!=null && phyendtscoreMap.get(uuid)!=0){
						phyet = phyendtscoreMap.get(uuid); 
						phyetstr = rf2.format(phyet);
					}else{
						phyetstr = " ";
					}

					if(bioscoreMap.get(uuid)!=null && bioscoreMap.get(uuid)!=0){
						bioscore = bioscoreMap.get(uuid);   
						//grandscoreT += bioscore;
						bioscorestr = rf2.format(bioscore);

					}else{
						bioscorestr = "";
					}


					if(biocat1scoreMap.get(uuid)!=null && biocat1scoreMap.get(uuid)!=0){
						bioc1 = biocat1scoreMap.get(uuid); 
						bioc1str = rf2.format(bioc1);
					}else{
						bioc1str = " ";
					}
					
					if(biocat2scoreMap.get(uuid)!=null && biocat2scoreMap.get(uuid)!=0){
						bioc2 = biocat2scoreMap.get(uuid); 
						bioc2str = rf2.format(bioc2);
					}else{
						bioc2str = " ";
					}
					
					if(bioendtscoreMap.get(uuid)!=null && bioendtscoreMap.get(uuid)!=0){
						bioet = bioendtscoreMap.get(uuid); 
						bioetstr = rf2.format(bioet);
					}else{
						bioetstr = " ";
					}


					if(chemscorehash.get(uuid)!=null && chemscorehash.get(uuid)!=0){
						chemscore = chemscorehash.get(uuid); 
						//grandscoreT += chemscore;
						chemscorestr = rf2.format(chemscore);

					}else{
						chemscorestr = "";
					} 


					if(chemcat1scoreMap.get(uuid)!=null && chemcat1scoreMap.get(uuid)!=0){
						chemc1 = chemcat1scoreMap.get(uuid); 
						chemc1str = rf2.format(chemc1);
					}else{
						chemc1str = " ";
					}
					
					if(chemcat2scoreMap.get(uuid)!=null && chemcat2scoreMap.get(uuid)!=0){
						chemc2 = chemcat2scoreMap.get(uuid); 
						chemc2str = rf2.format(chemc2);
					}else{
						chemc2str = " ";
					}
					
					if(chemendtscoreMap.get(uuid)!=null && chemendtscoreMap.get(uuid)!=0){
						chemet = chemendtscoreMap.get(uuid);  
						chemetstr = rf2.format(chemet);
					}else{
						chemetstr = " ";
					}



					if(matscorehash.get(uuid)!=null && matscorehash.get(uuid)!=0){
						matscore = matscorehash.get(uuid);              
						matscorestr = rf2.format(matscore);
						//grandscoreT += matscore;

					}else{
						matscorestr = "";
					}



					if(matcat1scoreMap.get(uuid)!=null && matcat1scoreMap.get(uuid)!=0){
						matc1 = matcat1scoreMap.get(uuid);   
						matc1str = rf2.format(matc1);
					}else{
						matc1str = " ";
					}
					
					if(matcat2scoreMap.get(uuid)!=null && matcat2scoreMap.get(uuid)!=0){
						matc2 = matcat2scoreMap.get(uuid);  
						matc2str = rf2.format(matc2);
					}else{
						matc2str = " ";
					}
					
					if(matendtscoreMap.get(uuid)!=null && matendtscoreMap.get(uuid)!=0){
						matet = matendtscoreMap.get(uuid); 
						matetstr = rf2.format(matet);
					}else{
						matetstr = " ";
					}



					if(histscoreMap.get(uuid)!=null && histscoreMap.get(uuid)!=0){
						histscore = histscoreMap.get(uuid); 
						//grandscoreT += histscore;
						histscorestr = rf2.format(histscore);

					}else{
						histscorestr = "";
					}



					if(hiscat1scoreMap.get(uuid)!=null && hiscat1scoreMap.get(uuid)!=0){
						hisc1 = hiscat1scoreMap.get(uuid); 
						hisc1str = rf2.format(hisc1);
					}else{
						hisc1str = " ";
					}
					
					if(hiscat2scoreMap.get(uuid)!=null && hiscat2scoreMap.get(uuid)!=0){
						hisc2 = hiscat2scoreMap.get(uuid);
						hisc2str = rf2.format(hisc2);
					}else{
						hisc2str = " ";
					}
					
					if(hisendtscoreMap.get(uuid)!=null && hisendtscoreMap.get(uuid)!=0){
						hiset = hisendtscoreMap.get(uuid);  
						hisetstr = rf2.format(hiset);
					}else{
						hisetstr = " ";
					}



					if(crescorehash.get(uuid)!=null && crescorehash.get(uuid)!=0){
						crescore = crescorehash.get(uuid);   
						//grandscoreT += crescore;
						crescorestr = rf2.format(crescore);

					}else{
						crescorestr = "";
					}



					if(crecat1scoreMap.get(uuid)!=null && crecat1scoreMap.get(uuid)!=0){
						crec1 = crecat1scoreMap.get(uuid);  
						crec1str = rf2.format(crec1);
					}else{
						crec1str = " ";
					}
					
					if(crecat2scoreMap.get(uuid)!=null && crecat2scoreMap.get(uuid)!=0){
						crec2 = crecat2scoreMap.get(uuid);  
						crec2str = rf2.format(crec2);
					}else{
						crec2str = " ";
					}
					
					if(creendtscoreMap.get(uuid)!=null && creendtscoreMap.get(uuid)!=0){
						creet = creendtscoreMap.get(uuid);
						creetstr = rf2.format(creet);
					}else{
						creetstr = " ";
					}




					if(geoscoreMap.get(uuid)!=null && geoscoreMap.get(uuid)!=0){
						geoscore = geoscoreMap.get(uuid);  
						//grandscoreT += geoscore;
						geoscorestr = rf2.format(geoscore);

					}else{
						geoscorestr = "";
					}



					if(geocat1scoreMap.get(uuid)!=null && geocat1scoreMap.get(uuid)!=0){
						geoc1 = geocat1scoreMap.get(uuid); 
						geoc1str = rf2.format(geoc1);
					}else{
						geoc1str = " ";
					}
					if(geocat2scoreMap.get(uuid)!=null && geocat2scoreMap.get(uuid)!=0){
						geoc2 = geocat2scoreMap.get(uuid);  
						geoc2str = rf2.format(geoc2);
					}else{
						geoc2str = " ";
					}
					if(geoendtscoreMap.get(uuid)!=null && geoendtscoreMap.get(uuid)!=0){
						geoet = geoendtscoreMap.get(uuid);  
						geoetstr = rf2.format(geoet);
					}else{
						geoetstr = " ";
					}




					if(bsscoreMap.get(uuid)!=null && bsscoreMap.get(uuid)!=0){
						bsscore = bsscoreMap.get(uuid);  
						//grandscoreT += bsscore;
						bsscorestr = rf2.format(bsscore);

					}else{
						bsscorestr = "";
					}



					if(bscat1scoreMap.get(uuid)!=null && bscat1scoreMap.get(uuid)!=0){
						bsc1 = bscat1scoreMap.get(uuid);
						bsc1str = rf2.format(bsc1);
					}else{
						bsc1str = " ";
					}
					if(bscat2scoreMap.get(uuid)!=null && bscat2scoreMap.get(uuid)!=0){
						bsc2 = bscat2scoreMap.get(uuid); 
						bsc2str = rf2.format(bsc2);
					}else{
						bsc2str = " ";
					}
					if(bsendtscoreMap.get(uuid)!=null && bsendtscoreMap.get(uuid)!=0){
						bset = bsendtscoreMap.get(uuid);  
						bsetstr = rf2.format(bset);
					}else{
						bsetstr = " ";
					}



					if(agriscorehash.get(uuid)!=null && agriscorehash.get(uuid)!=0){
						agriscore = agriscorehash.get(uuid);  
						//grandscoreT += agriscore;
						agriscorestr = rf2.format(agriscore);

					}else{
						agriscorestr = "";
					}


					if(agrcat1scoreMap.get(uuid)!=null && agrcat1scoreMap.get(uuid)!=0){
						agrc1 = agrcat1scoreMap.get(uuid); 
						agrc1str = rf2.format(agrc1);
					}else{
						agrc1str = " ";
					}
					if(agrcat2scoreMap.get(uuid)!=null && agrcat2scoreMap.get(uuid)!=0){
						agrc2 = agrcat2scoreMap.get(uuid); 
						agrc2str = rf2.format(agrc2);
					}else{
						agrc2str = " ";
					}
					if(agrendtscoreMap.get(uuid)!=null && agrendtscoreMap.get(uuid)!=0){
						agret = agrendtscoreMap.get(uuid); 
						agretstr = rf2.format(agret);
					}else{
						agretstr = " ";
					}


					if(hscscoreMap.get(uuid)!=null && hscscoreMap.get(uuid)!=0){
						hscscore = hscscoreMap.get(uuid); 
						//grandscoreT += hscscore;
						hscscorestr = rf2.format(hscscore);

					}else{
						hscscorestr = "";
					}



					if(hsccat1scoreMap.get(uuid)!=null && hsccat1scoreMap.get(uuid)!=0){
						hscc1 = hsccat1scoreMap.get(uuid); 
						hscc1str = rf2.format(hscc1);
					}else{
						hscc1str = " ";
					}
					if(hsccat2scoreMap.get(uuid)!=null && hsccat2scoreMap.get(uuid)!=0){
						hscc2 = hsccat2scoreMap.get(uuid);  
						hscc2str = rf2.format(hscc2);
					}else{
						hscc2str = " ";
					}
					if(hscendtscoreMap.get(uuid)!=null && hscendtscoreMap.get(uuid)!=0){
						hscet = hscendtscoreMap.get(uuid); 
						hscetstr = rf2.format(hscet);              	
					}else{
						hscetstr = " ";
					}



					if(comscoreMap.get(uuid)!=null && comscoreMap.get(uuid)!=0){
						comscore = comscoreMap.get(uuid);  
						//grandscoreT += comscore;
						comscorestr = rf2.format(comscore);


					}else{
						comscorestr = "";
					} 




					if(comcat1scoreMap.get(uuid)!=null && comcat1scoreMap.get(uuid)!=0){
						comc1 = comcat1scoreMap.get(uuid);   
						comc1str = rf2.format(comc1);
					}else{
						comc1str = "";
					}

					if(comcat2scoreMap.get(uuid)!=null && comcat2scoreMap.get(uuid)!=0){
						comc2 = comcat2scoreMap.get(uuid);  
						comc2str = rf2.format(comc2);
					}else{
						comc2str = "";
					}

					if(comendtscoreMap.get(uuid)!=null && comendtscoreMap.get(uuid)!=0){
						comet = comendtscoreMap.get(uuid);  
						cometstr = rf2.format(comet);
					}else{
						cometstr = "";
					}
					
					
					
					
					

					double the_grandscore = 0;

					the_grandscore = Double.parseDouble(totalz);
					mean = the_grandscore/11; 


					BaseColor baseColor = new BaseColor(255,255,255);//while
					BaseColor Colormagenta = new BaseColor(255,255,255);//  (176,196,222); magenta
					BaseColor Colorgrey = new BaseColor(255,255,255);//  (128,128,128)gray,grey


					PdfPTable containerTable = new PdfPTable(2);  
					containerTable.setWidthPercentage(100); 
					containerTable.setWidths(new int[]{100,100}); 
					containerTable.setHorizontalAlignment(Element.ALIGN_LEFT);


					PdfPTable titleTable = new PdfPTable(1);  
					titleTable.setWidthPercentage(25); 
					titleTable.setWidths(new int[]{100}); 
					titleTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPTable HeaderTable = new PdfPTable(1);  
					HeaderTable.setWidthPercentage(50); 
					HeaderTable.setWidths(new int[]{100});   
					HeaderTable.setHorizontalAlignment(Element.ALIGN_LEFT);




					PdfPCell contheader = new PdfPCell(new Paragraph(("TERM " +examConfig.getTerm() + ": YEAR " + examConfig.getYear() +"\n" +("CLASS : " + roomHash.get(classroomuuid) +"\n")) +"",timesRomanBold7));
					contheader.setBackgroundColor(Colormagenta);
					contheader.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell bodyheader = new PdfPCell(new Paragraph(("ADMISSION NUMBER : " + studentAdmNoHash.get(uuid) +"\n" +("STUDENT NAME : " + studNameHash.get(uuid) +"\n")),timesRomanBold7));
					bodyheader.setBackgroundColor(Colormagenta);
					bodyheader.setHorizontalAlignment(Element.ALIGN_LEFT);


					containerTable.addCell(bodyheader);
					containerTable.addCell(contheader);


					Paragraph emptyline = new Paragraph(("                              "));

					Paragraph reporttitle = new Paragraph();
					reporttitle.setAlignment(Element.ALIGN_CENTER); 
					reporttitle.add(new Paragraph(("               STUDENT REPORT CARD") , timesRomanBold7));

					PdfPCell CountHeader = new PdfPCell(new Paragraph("*",timesRomanBold7));
					CountHeader.setBackgroundColor(baseColor);
					CountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell subjectHeader = new PdfPCell(new Paragraph("SUBJECT",timesRomanBold7));
					subjectHeader.setBackgroundColor(baseColor);
					subjectHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell cat1Header = new PdfPCell(new Paragraph("CAT1 /30",timesRomanBold7));
					cat1Header.setBackgroundColor(baseColor);
					cat1Header.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell cat2Header = new PdfPCell(new Paragraph("CAT2 /30",timesRomanBold7));
					cat2Header.setBackgroundColor(baseColor);
					cat2Header.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell endtermHeader = new PdfPCell(new Paragraph("E.T /70",timesRomanBold7)); 
					endtermHeader.setBackgroundColor(baseColor);
					endtermHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell scoreHeader = new PdfPCell(new Paragraph("TOTAL /100",timesRomanBold7));
					scoreHeader.setBackgroundColor(baseColor);
					scoreHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell pointsHeader = new PdfPCell(new Paragraph("PNTS/12",timesRomanBold7));
					pointsHeader.setBackgroundColor(baseColor);
					pointsHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeHeader = new PdfPCell(new Paragraph("GRADE",timesRomanBold7));
					gradeHeader.setBackgroundColor(baseColor); 
					gradeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell remarkHeader = new PdfPCell(new Paragraph("REMARKS",timesRomanBold7));
					remarkHeader.setBackgroundColor(baseColor);
					remarkHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell teacherHeader = new PdfPCell(new Paragraph("Teacher",timesRomanBold7));
					teacherHeader.setBackgroundColor(baseColor);
					teacherHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


					PdfPTable myTable = new PdfPTable(10); 
					myTable.addCell(CountHeader);
					myTable.addCell(subjectHeader);
					myTable.addCell(cat1Header);
					myTable.addCell(cat2Header);
					myTable.addCell(endtermHeader);
					myTable.addCell(scoreHeader);
					myTable.addCell(pointsHeader);
					myTable.addCell(gradeHeader);
					myTable.addCell(remarkHeader);
					myTable.addCell(teacherHeader);
					myTable.setWidthPercentage(100); 
					myTable.setWidths(new int[]{15,60,20,20,20,25,20,25,60,20});   
					myTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					String maxscore = "100";

					String aplain = Integer.toString(gradingSystem.getGradeAplain()-1);
					String aminus = Integer.toString(gradingSystem.getGradeAminus()-1);

					String bplus = Integer.toString(gradingSystem.getGradeBplus()-1);
					String bplain = Integer.toString(gradingSystem.getGradeBplain()-1);
					String bminus = Integer.toString(gradingSystem.getGradeBminus()-1);

					String cplus = Integer.toString(gradingSystem.getGradeCplus()-1);
					String cplain = Integer.toString(gradingSystem.getGradeCplain()-1);
					String cminus = Integer.toString(gradingSystem.getGradeCminus()-1);

					String dplus = Integer.toString(gradingSystem.getGradeDplus()-1);
					String dplain = Integer.toString(gradingSystem.getGradeDplain()-1);
					String dminus = Integer.toString((gradingSystem.getGradeDminus()-1));

					String gradeE = Integer.toString(gradingSystem.getGradeE());


					PdfPCell gradeA = new PdfPCell(new Paragraph(maxscore+"-"+(gradingSystem.getGradeAplain()),timesRomanNormal7));
					gradeA.setBackgroundColor(baseColor);
					gradeA.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeAminus = new PdfPCell(new Paragraph(aplain+"-"+(gradingSystem.getGradeAminus()),timesRomanNormal7));
					gradeAminus.setBackgroundColor(baseColor);
					gradeAminus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeBplus = new PdfPCell(new Paragraph(aminus+"-"+(gradingSystem.getGradeBplus()),timesRomanNormal7));
					gradeBplus.setBackgroundColor(baseColor);
					gradeBplus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeBplain = new PdfPCell(new Paragraph(bplus+"-"+(gradingSystem.getGradeBplain()),timesRomanNormal7));
					gradeBplain.setBackgroundColor(baseColor);
					gradeBplain.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeBminus = new PdfPCell(new Paragraph(bplain+"-"+(gradingSystem.getGradeBminus()),timesRomanNormal7));
					gradeBminus.setBackgroundColor(baseColor);
					gradeBminus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeCplus = new PdfPCell(new Paragraph(bminus+"-"+(gradingSystem.getGradeCplus()),timesRomanNormal7));
					gradeCplus.setBackgroundColor(baseColor);
					gradeCplus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeCplain = new PdfPCell(new Paragraph(cplus+"-"+(gradingSystem.getGradeCplain()),timesRomanNormal7));
					gradeCplain.setBackgroundColor(baseColor);
					gradeCplain.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeCminus = new PdfPCell(new Paragraph(cplain+"-"+(gradingSystem.getGradeCminus()),timesRomanNormal7));
					gradeCminus.setBackgroundColor(baseColor);
					gradeCminus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeDplus = new PdfPCell(new Paragraph(cminus+"-"+(gradingSystem.getGradeDplus()),timesRomanNormal7));
					gradeDplus.setBackgroundColor(baseColor);
					gradeDplus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeDplain = new PdfPCell(new Paragraph(dplus+"-"+(gradingSystem.getGradeDplain()),timesRomanNormal7));
					gradeDplain.setBackgroundColor(baseColor);
					gradeDplain.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeDminus = new PdfPCell(new Paragraph(dplain+"-"+(gradingSystem.getGradeDminus()),timesRomanNormal7));
					gradeDminus.setBackgroundColor(baseColor);
					gradeDminus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeEE = new PdfPCell(new Paragraph(dminus+"-"+gradeE,timesRomanNormal7));
					gradeEE.setBackgroundColor(baseColor);
					gradeEE.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPTable gradeTable = new PdfPTable(12);  

					gradeTable.addCell(gradeA);
					gradeTable.addCell(gradeAminus);
					gradeTable.addCell(gradeBplus);
					gradeTable.addCell(gradeBplain);
					gradeTable.addCell(gradeBminus);
					gradeTable.addCell(gradeCplus);
					gradeTable.addCell(gradeCplain);
					gradeTable.addCell(gradeCminus);
					gradeTable.addCell(gradeDplus);
					gradeTable.addCell(gradeDplain);
					gradeTable.addCell(gradeDminus);
					gradeTable.addCell(gradeEE);
					gradeTable.setWidthPercentage(100); 
					gradeTable.setWidths(new int[]{20,20,20,20,20,25,20,20,20,20,20,20});   
					gradeTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					gradeTable.addCell(new Paragraph("A",timesRomanNormal7));
					gradeTable.addCell(new Paragraph("A-",timesRomanNormal7));
					gradeTable.addCell(new Paragraph("B+",timesRomanNormal7));
					gradeTable.addCell(new Paragraph("B",timesRomanNormal7));
					gradeTable.addCell(new Paragraph("B-",timesRomanNormal7));
					gradeTable.addCell(new Paragraph("C+",timesRomanNormal7));
					gradeTable.addCell(new Paragraph("C",timesRomanNormal7));
					gradeTable.addCell(new Paragraph("C-",timesRomanNormal7));
					gradeTable.addCell(new Paragraph("D+",timesRomanNormal7));
					gradeTable.addCell(new Paragraph("D",timesRomanNormal7));
					gradeTable.addCell(new Paragraph("D-",timesRomanNormal7));
					gradeTable.addCell(new Paragraph("E",timesRomanNormal7));

					//studentDAO parentsDAO TODO
					if(StringUtils.equals(examConfig.getSendSMS(),"ON")){
						sendResultSMS.sendToParents(smsApiDAO,smsSendDAO,studentDAO,parentsDAO,uuid,school.getUuid(),engscorestr,kswscorestr,matscorestr,
								physcorestr,bioscorestr,chemscorestr,bsscorestr,comscorestr,
								hscscorestr,agriscorestr,geoscorestr,crescorestr,histscorestr,mean);
					}


					List<Subject> subList = subjectDAO.getAllSubjects();

					int count = 1;
					for(Subject sub : subList){

						myTable.addCell(new Paragraph(" "+count,timesRomanNormal7));

						if(StringUtils.equals(sub.getUuid(), ExamConstants.ENG_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+engc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+engc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+engetstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+engscorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(engscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(engscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(engscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
                            if(engscore > 0 && engscore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							engscore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.KISWA_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+kisc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+kisc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+kisetstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+kswscorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(kswscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(kswscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(kswscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(kswscore > 0 && kswscore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							kswscore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.MATH_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+matc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+matc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+matetstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+matscorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(matscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(matscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(matscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(matscore > 0 && matscore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							matscore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.PHY_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+phyc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+phyc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+phyetstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+physcorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(physcore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(physcore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(physcore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(physcore > 0 && physcore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							physcore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.BIO_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+bioc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+bioc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+bioetstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+bioscorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(bioscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(bioscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(bioscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(bioscore > 0 && bioscore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							bioscore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.CHEM_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+chemc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+chemc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+chemetstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+chemscorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(chemscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(chemscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(chemscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(chemscore > 0 && chemscore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							chemscore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.BS_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+bsc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+bsc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+bsetstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+bsscorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(bsscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(bsscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(bsscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(bsscore > 0 && bsscore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							bsscore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.COMP_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+comc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+comc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+cometstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+comscorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(comscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(comscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(comscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(comscore > 0 && comscore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							comscore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.H_S)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+hscc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+hscc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+hscetstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+hscscorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(hscscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(hscscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(hscscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(hscscore > 0 && hscscore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							hscscore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.AGR_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+agrc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+agrc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+agretstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+agriscorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(agriscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(agriscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(agriscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(agriscore > 0 && agriscore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							agriscore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.GEO_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+geoc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+geoc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+geoetstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+geoscorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(geoscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(geoscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(geoscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(geoscore > 0 && geoscore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							geoscore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.CRE_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+crec1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+crec2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+creetstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+crescorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(crescore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(crescore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(crescore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(crescore > 0 && crescore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							crescore = 0;

						}if(StringUtils.equals(sub.getUuid(), ExamConstants.HIST_UUID)){
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+hisc1str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+hisc2str,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+hisetstr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+histscorestr,timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+pointsFinder(histscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeGrade(histscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+computeRemarks(histscore),timesRomanNormal7));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),timesRomanNormal7)); 
							if(histscore > 0 && histscore < gradingSystem.getGradeDplus()){
                            	shameMap.put(sub.getUuid(),sub.getUuid());
                            }
							histscore = 0;
						}

						count++;
					}



					//KCSE
					double kcse = 0;
					double newkcse = 0;
					if(primaryDAO.getPrimary(uuid)!=null){
						StudentPrimary primary = primaryDAO.getPrimary(uuid);
						kcse = Integer.parseInt(primary.getKcpemark()); 
						newkcse = (kcse/500)*12;
					}

					//MEANMapgn  POSMapgn

					Paragraph myposition;

					if(mean==number){
						myposition = new Paragraph((" Stream PSTN " +(position-counttwo++)+ " / " +theFinalposition)   +    "     Class PSTN " +POSMapgn.get(uuid) +    "      K.C.P.E = " + (int)kcse +" ",timesRomanNormal7);
					}
					else{
						counttwo=1;
						myposition = new Paragraph((" Stream PSTN " +position+ " / " +theFinalposition)   +    "     Class PSTN " +POSMapgn.get(uuid) +  "      K.C.P.E = " + (int)kcse + " ",timesRomanNormal7);
					}

					PdfPCell positionheader = new PdfPCell(myposition); 
					positionheader.setBackgroundColor(Colormagenta);
					positionheader.setHorizontalAlignment(Element.ALIGN_LEFT); 


					PdfPCell meanheader = new PdfPCell(new Paragraph("TOTAL MARKS = " + the_grandscore + " / 1100 , MEAN SCORE = " + df.format(mean) + " / 100 , MEAN GRADE = " +computeGrade(mean) +"\n",timesRomanNormal7));
					meanheader.setBackgroundColor(Colormagenta);
					meanheader.setHorizontalAlignment(Element.ALIGN_RIGHT);

					PdfPTable bottomTable = new PdfPTable(2);  
					bottomTable.setWidthPercentage(100); 
					bottomTable.setWidths(new int[]{80,120}); 
					bottomTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					bottomTable.addCell(positionheader);
					bottomTable.addCell(meanheader); 

					PdfPTable feeTable = new PdfPTable(2);  
					feeTable.setWidthPercentage(100); 
					feeTable.setWidths(new int[]{100,100}); 
					feeTable.setHorizontalAlignment(Element.ALIGN_LEFT);


					//fee balance
					//admdate  admTerm

					String currentTermstr = examConfig.getTerm();
					String correctTermstr = "";
					int correctTermint = 0;
					int currentTermint = Integer.parseInt(currentTermstr); 
					int nextTermint = currentTermint+1;
					if(nextTermint >3){
						correctTermint = 1;
						correctTermstr = Integer.toString(correctTermint);
					}else{
						correctTermint = currentTermint+1;
						correctTermstr = Integer.toString(correctTermint);
					}

					TermFee termFeenex = termFeeDAO.getFee(school.getUuid(),correctTermstr,examConfig.getYear()); 
					double nexttermBoardingfee = 0;
					double nexttermDayfee = 0;
					
					 nexttermBoardingfee = termFeenex.getTermAmount();
					 nexttermDayfee = termFeenex.getDayAmount();

					Locale locale = new Locale("en","KE"); 
					NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
					double balance = 0;
					String feebalance = "";
					StudentBalance studentBal = new StudentBalance();
					//admtermMap finalyearMap
					balance = studentBal.findBalance(termFeeDAO,examConfigDAO,studentFeeDAO,studentOtherMoniesDAO,admdaterMap.get(uuid),admtermMap.get(uuid),uuid,school.getUuid(),finalyearMap.get(uuid)); 
					//System.out.println("balance = " + balance + " admTerm " + admtermMap.get(uuid) + " adm no " + studentAdmNoHash.get(uuid));
					feebalance = nf.format(balance);
					
					Miscellanous closingDate = new Miscellanous();
					Miscellanous openingDate = new Miscellanous();
					Miscellanous comments = new Miscellanous();
					String cdate = "";
					String odate = "";
					String comment = "";

					closingDate = miscellanousDAO.getKey(school.getUuid(),"CLOSING_DATE");
					cdate = closingDate.getValue();

					openingDate = miscellanousDAO.getKey(school.getUuid(),"OPENING_DATE");
					odate = openingDate.getValue();

					comments = miscellanousDAO.getKey(school.getUuid(),"HEAD_TEACHER_REMARKS");
					comment = comments.getValue();
					
					String dayfee = "";
					 String boarderfee = "";
		             boarderfee = nf.format(nexttermBoardingfee);
					 dayfee = nf.format(nexttermDayfee);
					
					 String schoolfee = "";
					

				    if(StringUtils.equalsIgnoreCase(school.getDayBoarding(), "YES")){ //Both Day and Boarding
				        schoolfee = "BOARDING : " + boarderfee +"\nDAY : " + dayfee;
				     }else{
				        schoolfee = " " + boarderfee;
				    }
					


					PdfPCell feeCell = new PdfPCell(new Paragraph("Closing Fee Balance  " + feebalance +" \n\nNext Term Fee "  + schoolfee ,timesRomanNormal0));
					feeCell.setBackgroundColor(Colorgrey);
					feeCell.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell DateCell = new PdfPCell(new Paragraph(("Closing date : " +cdate+" \n\nNext Term Opening date :" +odate)+"\n",timesRomanNormal0));
					DateCell.setBackgroundColor(Colorgrey);
					DateCell.setHorizontalAlignment(Element.ALIGN_LEFT);

					feeTable.addCell(feeCell);
					feeTable.addCell(DateCell);

					//save details

					String StartYear = "";
					StartYear = admYearMap.get(uuid);
					int Year2Int = 0;
					int Year3Int = 0;
					int Year4Int = 0;

					String Year2Str = "";
					String Year3Str = "";
					String Year4Str = "";

					Year2Int = Integer.parseInt(StartYear)+1;
					Year3Int = Year2Int + 1;
					Year4Int = Year3Int + 1;

					Year2Str = Integer.toString(Year2Int);
					Year3Str = Integer.toString(Year3Int);
					Year4Str = Integer.toString(Year4Int);


					GraphWeightGenerator(mean,uuid,school.getUuid()); 
					
					// Create a simple Bar chart start
					BarWeight barWeight1 = new BarWeight();
					BarWeight barWeight2 = new BarWeight();
					BarWeight barWeight3 = new BarWeight();
					BarWeight barWeight4 = new BarWeight();
					if(GraphWeightGenerator(mean,uuid,school.getUuid())){
						if(barWeightDAO.getBarWeight(school.getUuid(), uuid, StartYear) !=null){
							barWeight1 = barWeightDAO.getBarWeight(school.getUuid(), uuid, StartYear); 
						}

						if(barWeightDAO.getBarWeight(school.getUuid(), uuid, Year2Str) !=null){
							barWeight2 = barWeightDAO.getBarWeight(school.getUuid(), uuid, Year2Str); 
						}

						if(barWeightDAO.getBarWeight(school.getUuid(), uuid, Year3Str) !=null){
							barWeight3 = barWeightDAO.getBarWeight(school.getUuid(), uuid, Year3Str); 
						}
						if(barWeightDAO.getBarWeight(school.getUuid(), uuid, Year4Str) !=null){
							barWeight4 = barWeightDAO.getBarWeight(school.getUuid(), uuid, Year4Str); 
						}


					}

					PdfPCell BAFheader = new PdfPCell();

					DefaultCategoryDataset dataset = new DefaultCategoryDataset();
					double ChartWeight = 0;
					double ChartWeight2 = 0;
					double ChartWeight3 = 0;



					dataset.setValue(newkcse, "Pnts", " K.C.P.E"); 

					//YEAR 1
					ChartWeight =  barWeight1.getWeightOne(); 
					ChartWeight2 =  barWeight1.getWeightTwo(); 	
					ChartWeight3 =  barWeight1.getWeightThree(); 


					dataset.setValue(ChartWeight, "Pnts",  StartYear+"-T 1");
					dataset.setValue(ChartWeight2, "Pnts", StartYear+"-T 2");
					dataset.setValue(ChartWeight3, "Pnts", StartYear+"-T 3");

					//YEAR 2
					ChartWeight = 0;
					ChartWeight2 = 0;
					ChartWeight3 = 0;

					ChartWeight =  barWeight2.getWeightOne(); 
					ChartWeight2 =  barWeight2.getWeightTwo(); 	
					ChartWeight3 =  barWeight2.getWeightThree(); 

					dataset.setValue(ChartWeight, "Pnts",  Year2Str+"-T 1");
					dataset.setValue(ChartWeight2, "Pnts", Year2Str+"-T 2");
					dataset.setValue(ChartWeight3, "Pnts", Year2Str+"-T 3");

					//YEAR 2
					ChartWeight = 0;
					ChartWeight2 = 0;
					ChartWeight3 = 0;

					ChartWeight =  barWeight3.getWeightOne(); 
					ChartWeight2 =  barWeight3.getWeightTwo(); 	
					ChartWeight3 =  barWeight3.getWeightThree(); 

					dataset.setValue(ChartWeight, "Pnts",  Year3Str+"-T 1");
					dataset.setValue(ChartWeight2, "Pnts", Year3Str+"-T 2");
					dataset.setValue(ChartWeight3, "Pnts", Year3Str+"-T 3");

					//YEAR 4
					ChartWeight = 0;
					ChartWeight2 = 0;
					ChartWeight3 = 0;

					ChartWeight =  barWeight4.getWeightOne(); 
					ChartWeight2 =  barWeight4.getWeightTwo(); 	
					ChartWeight3 =  barWeight4.getWeightThree(); 

					dataset.setValue(ChartWeight, "Pnts",  Year4Str+"-T 1");
					dataset.setValue(ChartWeight2, "Pnts", Year4Str+"-T 2");
					dataset.setValue(ChartWeight3, "Pnts", Year4Str+"-T 3");

					//CONTROL
					dataset.setValue(12, "Control ", "Control ");

					//END 

					JFreeChart chart = ChartFactory.createBarChart("Yearly Performance Analysis", // chart title
							"Term", // domain axis label (Y axis)
							"Weight", //  range axis label (X axis)
							dataset, // data
							PlotOrientation.VERTICAL, // orientation
							false, // include legend
							true, // tooltips?
							false);// URLs?

					ByteArrayOutputStream byte_out = new ByteArrayOutputStream();

					try {

						ChartUtilities.writeChartAsPNG(byte_out, chart, 1200, 270);
						byte [] data = byte_out.toByteArray();
						byte_out.close();
						Image chartImage = Image.getInstance(data);
						chartImage.scaleToFit(1000,300); 
						//chartPragraph.add(chartImage); 
						BAFheader.addElement(new Chunk(chartImage,15,-90));// margin left  ,  margin top
						BAFheader.setBorder(Rectangle.NO_BORDER); 
						BAFheader.setHorizontalAlignment(Element.ALIGN_LEFT);
						BAFheader.setHorizontalAlignment(Element.ALIGN_LEFT);


					} catch (IOException e) {
						e.printStackTrace();
					}

					//chart end

					//QR code start
					Paragraph QRparagraph;
					QRparagraph = new Paragraph(); 
					BarcodeQRCode my_code = new BarcodeQRCode("AdmNo: " + studentAdmNoHash.get(uuid) + 
							"\nName: " + studNameHash.get(uuid) +"Mean: " + df.format(mean) + "\nGrade: "
							+ computeGrade(mean) + "\nFee Bal: "
							+ feebalance,1,1, null);//This method accept as parameter, a string to be encoded in the QR,notice the '\n' 
					                                //This is similar to 'BufferedImage buffImage = getBufferedImage('String')' 

					Image qr_image = my_code.getImage();//create the QR image
					qr_image.scaleToFit(100, 100);//scale the image to the desired size
					QRparagraph.add(qr_image);//embed the image into PDF table cell
					//QR code end

					//chart table
					PdfPTable chartTable = new PdfPTable(1);  
					chartTable.setWidthPercentage(100); 
					chartTable.setWidths(new int[]{100}); 
					chartTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					//chart table
					PdfPTable QrTable = new PdfPTable(1);  
					QrTable.setWidthPercentage(100); 
					QrTable.setWidths(new int[]{100}); 
					QrTable.setHorizontalAlignment(Element.ALIGN_LEFT);


					PdfPCell QRheader = new PdfPCell();
					QRheader.addElement(new Chunk(qr_image,15,-50)); // margin left  ,  margin top
					QRheader.setBackgroundColor(Colormagenta);
					QRheader.setBorder(Rectangle.NO_BORDER); 
					QRheader.setHorizontalAlignment(Element.ALIGN_LEFT);

					QrTable.addCell(QRheader);
					chartTable.addCell(BAFheader);   

					//chart table end


					BarWeight barWeight = new BarWeight();
					if(barWeightDAO.getBarWeight(school.getUuid(), uuid, examConfig.getYear()) !=null){
						barWeight = barWeightDAO.getBarWeight(school.getUuid(), uuid, examConfig.getYear()); 
					}

					//  get last term mean, and this term mean, find deviation and out the comment
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
					//String shamesub = ""; 
					List<String> shamelist = new ArrayList<String>();
					for(Subject subject : subList){
						if(StringUtils.equals(subject.getUuid(), shameMap.get(subject.getUuid()))){
								shamelist.add(subject.getSubjectCode());
						}
					}
					shameMap.clear();
					String shamesub = "";
					Map<String,String> shamesMap = new HashMap<String,String>(); 
					if(!shamelist.isEmpty()){
						shamesub = ".Note that your performance in the following subject(s) is horrifying "+  shamelist.toString();
						shamesMap.put(uuid,shamesub); 
					   }
					shamelist.clear();
					String finalShame = "";
					if(shamesMap.get(uuid) != null || !shamesMap.isEmpty()){ 
						finalShame = shamesMap.get(uuid);
						if(StringUtils.equals(finalShame, "null")){
							finalShame = "";
						}
					}
					
					//class teacher comment table start
					PdfPTable classTeacherCommentTable = new PdfPTable(2);  
					classTeacherCommentTable.setWidthPercentage(100); 
					classTeacherCommentTable.setWidths(new int[]{100,100}); 
					classTeacherCommentTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					String CTcommentLabel = "CLASS TEACHER'S COMMENT \n\n";
					Paragraph CTcommentLb = new Paragraph(CTcommentLabel,timesRomanBold7);

					PdfPCell CTcommentCell = new PdfPCell(new Paragraph("Hi " + firstnamee +", "+ classteacherRemarks(school,uuid,barWeight)+  devComment + " " + finalShame + " \n",timesRomanNormal0));
					CTcommentCell.setBackgroundColor(Colormagenta);
					CTcommentCell.setColspan(2); 
					CTcommentCell.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell CTsignature = new PdfPCell(new Paragraph("Class teacher's Signature: _______________("+ classteachername(classroomuuid) +")\n\n",timesRomanNormal0));
					CTsignature.setBackgroundColor(Colormagenta);
					CTsignature.setHorizontalAlignment(Element.ALIGN_LEFT); 

					PdfPCell CTsignaturedate2 = new PdfPCell(new Paragraph("Date : _____________________    \n\n",timesRomanNormal0));
					CTsignaturedate2.setBackgroundColor(Colormagenta);
					CTsignaturedate2.setHorizontalAlignment(Element.ALIGN_LEFT);

					classTeacherCommentTable.addCell(CTcommentCell);
					classTeacherCommentTable.addCell(CTsignature);
					classTeacherCommentTable.addCell(CTsignaturedate2);
					//class teacher comment table end


					//principal comment table start
					PdfPTable principalCommentTable = new PdfPTable(2);  
					principalCommentTable.setWidthPercentage(100); 
					principalCommentTable.setWidths(new int[]{100,100}); 
					principalCommentTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					String commentLabel = "PRINCIPAL'S COMMENT \n\n";
					Paragraph commentLb = new Paragraph(commentLabel,timesRomanBold7);
                    
				    comment = comment.substring(0, Math.min(comment.length(), ExamConstants.PRINCIPAL_COMMENT_MAX_LENGHT));
					PdfPCell commentCell = new PdfPCell(new Paragraph("Thank you " + firstnamee +" "+ comment +" "+PrincipalRemarks(mean) +"\n",timesRomanNormal0));
					commentCell.setBackgroundColor(Colormagenta);
					commentCell.setColspan(2); 
					commentCell.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell Cell1 = new PdfPCell(new Paragraph("Principal's Signature: _______________   " + "\n\n",timesRomanNormal0));
					Cell1.setBackgroundColor(Colormagenta);
					Cell1.setHorizontalAlignment(Element.ALIGN_LEFT); 

					PdfPCell Cell2 = new PdfPCell(new Paragraph("            Rubber Stamp \n\n",timesRomanNormal0));
					Cell2.setBackgroundColor(Colormagenta);
					Cell2.setHorizontalAlignment(Element.ALIGN_LEFT);

					principalCommentTable.addCell(commentCell);
					principalCommentTable.addCell(Cell1);
					principalCommentTable.addCell(Cell2);
					//principal comment table end
					String equity = ExamConstants.EQUITY_ACC;
					String coop = ExamConstants.COOP_ACC;
					
					String accountLabel = "Pay school fee to the following account. "
				             + " CO-OP BANK : " + coop;
		            Paragraph caccounttLb = new Paragraph(accountLabel,timesRomanNormal0);


		            String passpartPath = photopath+studentAdmNoHash.get(uuid)+".png";
					
					PdfPCell photo = new PdfPCell();
					photo.addElement(createImage(passpartPath,2)); 
					photo.setBorder(Rectangle.NO_BORDER); 
					photo.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					PdfPTable prefaceTable = new PdfPTable(2);  
					prefaceTable.setWidthPercentage(100); 
					prefaceTable.setWidths(new int[]{40,60}); 

					prefaceTable.addCell(logo); 
					//prefaceTable.addCell(photo); 
					prefaceTable.addCell(contentcell);

					document.add(prefaceTable);
					document.add(containerTable);      	  
					document.add(emptyline);
					document.add(myTable); 
					document.add(emptyline);
					document.add(gradeTable);  
					document.add(emptyline);
					document.add(bottomTable); 

					document.add(chartTable);

					document.add(emptyline);
					document.add(emptyline);
					document.add(emptyline);
					document.add(emptyline);
					document.add(emptyline);
					//document.add(feeTable);

					//Class teacher start
					document.add(CTcommentLb);
					//document.add(emptyline); 
					document.add(classTeacherCommentTable); 

					// principal start
					document.add(commentLb);
					//document.add(emptyline); 
					document.add(principalCommentTable); 

					//document.add(emptyline);
					document.add(feeTable);

					document.add(caccounttLb);


					position++;
					number=mean;

					document.newPage();

				}

			}


			// step 5
			document.close();
		}
		catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}



	/**
	 * @param classroomuuid
	 * @return
	 */
	private String classteachername(String classroomuuid) {
		String classTeacherName = "";
		//classTeacherDAO
		String teacherId = "";
		if(classTeacherDAO.getClassTeacherByclassId(classroomuuid) !=null){
			ClassTeacher classTeacher = classTeacherDAO.getClassTeacherByclassId(classroomuuid);
			teacherId = classTeacher.getTeacherUuid();

			if(staffDetailsDAO.getStaffDetail(teacherId) !=null){
				StaffDetails StaffDetail = staffDetailsDAO.getStaffDetail(teacherId); 
				classTeacherName = StringUtils.capitalize(StaffDetail.getFirstName().toLowerCase());
			}

		}
		return classTeacherName.substring(0, Math.min(classTeacherName.length(), 8));
	}
	
	

	/**
	 * @param subjectid
	 * @param classroomid
	 * @return
	 */
	private String findSubTecher(String subjectid, String classroomid) {
		String teachername = "";
		String teacheruuid = "";
		if(teacherSubClassDAO.getSubject(subjectid, classroomid) !=null){
			TeacherSubClass teachersub = teacherSubClassDAO.getSubject(subjectid, classroomid);
			teacheruuid = teachersub.getTeacherUuid();
			if(staffDetailsDAO.getStaffDetail(teacheruuid) !=null){
				StaffDetails StaffDetail = staffDetailsDAO.getStaffDetail(teacheruuid); 
				teachername = StringUtils.capitalize(StaffDetail.getFirstName().toLowerCase());
			}	
		}
		if(StringUtils.isBlank(teachername)){
			teachername = "";
		}

		return teachername.substring(0, Math.min(teachername.length(), 8));
	}


	/**
	 * @param school
	 * @param uuid
	 * @param barWeight
	 * @return
	 */
	private String classteacherRemarks(SchoolAccount school, String uuid, BarWeight barWeight) {
		String classTeacherComent = " ";
		double T1Weight = 0;
		double T2Weight = 0;
		double T3Weight = 0;
		if(barWeight !=null){
			T1Weight = barWeight.getWeightOne();
			T2Weight = barWeight.getWeightTwo();
			T3Weight = barWeight.getWeightThree();
		}

		if(StringUtils.equalsIgnoreCase(examConfig.getTerm(), "1")){

			BarWeight barweightLastYR = new BarWeight();
			int lastyear = (Integer.parseInt(examConfig.getYear())-1);

			if(barWeightDAO.getBarWeight(school.getUuid(), uuid, Integer.toString(lastyear)) !=null){
				barweightLastYR = barWeightDAO.getBarWeight(school.getUuid(), uuid, Integer.toString(lastyear)); 
			}
			double T3lastYR = 0;
			T3lastYR = barweightLastYR.getWeightThree();

			if(T1Weight > T3lastYR && T3lastYR !=0){
				//you have improved
				classTeacherComent = "we noted some improvement, please keep it up.";

			}else if( T1Weight > T3lastYR && T3lastYR == 0){
				//stagnant
				classTeacherComent = "Your last term exam was not captured";

			}else if(T1Weight == T3lastYR){
				//you have dropped 
				classTeacherComent = "we have noted no improvement, this is worrying, work harder.";

			}else if(T1Weight < T3lastYR){
				//you have dropped 
				classTeacherComent = "you have dropped! Put more effort please.";
			}


		}else if(StringUtils.equalsIgnoreCase(examConfig.getTerm(), "2")){


			if(T2Weight > T1Weight && T1Weight !=0){
				//you have improved
				classTeacherComent = "we noted some improvement, please keep it up.";
			}else if(T2Weight > T1Weight && T1Weight == 0){
				//stagnant
				classTeacherComent = "Your last term exam was not captured";

			}else if(T2Weight == T1Weight){
				//you have dropped 
				classTeacherComent = "we have noted no improvement, this is worrying, work harder.";

			}else if(T2Weight < T1Weight){
				//you have dropped 
				classTeacherComent = "you have dropped! Put more effort please.";
			}

		}else if(StringUtils.equalsIgnoreCase(examConfig.getTerm(), "3")){

			if(T3Weight > T2Weight && T2Weight !=0){
				//you have improved
				classTeacherComent = "we noted some improvement, please keep it up.";

			}else if(T3Weight > T2Weight && T2Weight == 0){
				//stagnant
				classTeacherComent = "Your last term exam was not captured";

			}else if(T3Weight == T2Weight){
				//you have dropped 
				classTeacherComent = "we have noted no improvement, this is worrying, work harder.";

			}else if(T3Weight < T2Weight){
				//you have dropped 
				classTeacherComent = "you have dropped! Put more effort please.";
			}



		}

		return classTeacherComent;
	}



	/** Find deviation from last term
	 * pass thisTermMean following lastTermMean
	 * @param lastTermMean
	 * @param thisTermMean
	 * @return the deviation 
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
		String comment = "";
		DecimalFormat df = new DecimalFormat("0.00"); 
		df.setRoundingMode(RoundingMode.HALF_UP);
		double dev = 0;
		dev = mean;
		if(dev<0){
			//Negative , student nose diving
			double positiveDev = Math.abs(dev);
			String newDev = "-"+df.format(positiveDev);
			comment = " Your deviation is " + newDev;
			//System.out.println(comment);

		}else if(dev>0){
			//positive , student working hard
			comment = " Your deviation is " + df.format(dev);

		}else if(dev == 0){
			//stagnant
			comment = " !";

		}

		return comment;
	}

	/**
	 * 
	 * @param mean
	 * @param studentuuid
	 * @param schooluuid
	 * @return
	 */

	private boolean GraphWeightGenerator(double mean,String studentuuid,String schooluuid) {
		boolean saved = false;

		BarWeight barWeight;
		if(barWeightDAO.getBarWeight(schooluuid, studentuuid, examConfig.getYear())==null){
			barWeight = new BarWeight();
		}else{
			barWeight = barWeightDAO.getBarWeight(schooluuid, studentuuid, examConfig.getYear());
		}


		double weight = 0;
		weight =  ((mean/100)*12);

		if(StringUtils.equals(examConfig.getTerm(), "1")){
			barWeight.setWeightOne(weight);
			barWeight.setSchoolAccountUuid(schooluuid);
			barWeight.setStudentUuid(studentuuid);
			barWeight.setTerm(examConfig.getTerm());
			barWeight.setYear(examConfig.getYear());
			barWeightDAO.put(barWeight,schooluuid,studentuuid,examConfig.getYear());
			saved = true;

		}else if (StringUtils.equals(examConfig.getTerm(), "2")){
			barWeight.setWeightTwo(weight);
			barWeight.setSchoolAccountUuid(schooluuid);
			barWeight.setStudentUuid(studentuuid);
			barWeight.setTerm(examConfig.getTerm());
			barWeight.setYear(examConfig.getYear());
			barWeightDAO.put(barWeight,schooluuid,studentuuid,examConfig.getYear());
			saved = true;

		}else if (StringUtils.equals(examConfig.getTerm(), "3")){
			barWeight.setWeightThree(weight);
			barWeight.setSchoolAccountUuid(schooluuid);
			barWeight.setStudentUuid(studentuuid);
			barWeight.setTerm(examConfig.getTerm());
			barWeight.setYear(examConfig.getYear());
			barWeightDAO.put(barWeight,schooluuid,studentuuid,examConfig.getYear());
			saved = true;
		}

		return saved;

	}


	/**
	 * @param score
	 * @return
	 */
	private String PrincipalRemarks(double score) {
		String remarks = "";
		double mean = score;

		if(mean >= gradingSystem.getGradeAplain()){
			remarks = "Execellent work,let sky be your steping stone.";
		}else if(mean >= gradingSystem.getGradeAminus()){
			remarks = "Good work, sky is not the limit.";
		}else if(mean >= gradingSystem.getGradeBplus()){
			remarks = "Good work, a little more effort please.";
		}else if(mean >= gradingSystem.getGradeBplain()){
			remarks = "Good work but, this is not your best.";
		}else if(mean >= gradingSystem.getGradeBminus()){
			remarks = "An average student, you have the potential.";
		}else if(mean >= gradingSystem.getGradeCplus()){
			remarks = "Below average, this is not your best.";
		}else if(mean >= gradingSystem.getGradeCplain()){
			remarks = "Below average, you can do beter than this.";
		}else if(mean >= gradingSystem.getGradeCminus()){
			remarks = "Below average, you can do beter.";
		}else if(mean >= gradingSystem.getGradeDplus()){
			remarks = "Below average, put more effort.";
		}else if(mean >= gradingSystem.getGradeDplain()){
			remarks = "Far below average, please you deserve better.";
		}else if(mean >= gradingSystem.getGradeDminus()){
			remarks = "Far much below average but you deserve beter than this.";
		}else{
			remarks = "This is extremly poor but still you can do beter.";
		}

		if(mean ==0){
			remarks = " ";
		}

		return remarks;
	}

	/**
	 * @param score
	 * @return
	 */
	private String computeRemarks(double score) {
		String remarks = "";
		double mean = score;

		if(mean >= gradingSystem.getGradeAplain()){
			remarks = "Execellent, keep it up.";
		}else if(mean >= gradingSystem.getGradeAminus()){
			remarks = "Good work, aim higher.";
		}else if(mean >= gradingSystem.getGradeBplus()){
			remarks = "Nice job, aim higher.";
		}else if(mean >= gradingSystem.getGradeBplain()){
			remarks = "Well done, aim higher.";
		}else if(mean >= gradingSystem.getGradeBminus()){
			remarks = "Well done, you deserve better.";
		}else if(mean >= gradingSystem.getGradeCplus()){
			remarks = "Fair, you can do better.";
		}else if(mean >= gradingSystem.getGradeCplain()){
			remarks = "Fair, put more effort.";
		}else if(mean >= gradingSystem.getGradeCminus()){
			remarks = "Fair, you can improve.";
		}else if(mean >= gradingSystem.getGradeDplus()){
			remarks = "Fair, pull up your socks.";
		}else if(mean >= gradingSystem.getGradeDplain()){
			remarks = "You can do better than this.";
		}else if(mean >= gradingSystem.getGradeDminus()){
			remarks = "You can do better than this.";
		}else{
			remarks = "You can do better than this.";
		}

		if(mean ==0){
			remarks = " ";
		}

		return remarks;
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
		}else if(mean >= gradingSystem.getGradeE()){
			grade = "E";
		}

		if(mean ==0){
			grade = " ";
		}

		return grade;
	}


	/**
	 * @param score
	 * @return
	 */
	public String pointsFinder(double score){
		String points = "";
		// points 1=E, 2=D-, 3=D, 4=D+, 5=C-, 6=C, 7=c+
		//8=B-, 9=B, 10=B+,11=A-,12=A
		if(score >= gradingSystem.getGradeAplain()){
			points = "12";
		}else if(score >= gradingSystem.getGradeAminus()){
			points = "11";
		}else if(score >= gradingSystem.getGradeBplus()){
			points = "10";
		}else if(score >= gradingSystem.getGradeBplain()){
			points = "9";
		}else if(score >= gradingSystem.getGradeBminus()){
			points = "8";
		}else if(score >= gradingSystem.getGradeCplus()){
			points = "7";
		}else if(score >= gradingSystem.getGradeCplain()){
			points = "6";
		}else if(score >= gradingSystem.getGradeCminus()){
			points = "5";
		}else if(score >= gradingSystem.getGradeDplus()){
			points = "4";
		}else if(score >= gradingSystem.getGradeDplain()){
			points = "3";
		}else if(score >= gradingSystem.getGradeDminus()){
			points = "2";
		}else if(score >= gradingSystem.getGradeE()){
			points = "1";
		}

		if(score ==0){
			points = " ";
		}

		return points;
	}


	/**
	 * @param realPath
	 * @return
	 */
	private Element createImage(String realPath, int type) {
		Image img = null;

		try {

			File file = new File(realPath);
			if(!file.exists()){
				realPath = getServletContext().getRealPath("/images/default.jpg");
				
			}
			
			BufferedImage bufferedImage = ImageIO.read(new File(realPath));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			if(type == 1){//log
				ImageIO.write(resize(bufferedImage, 600,350), "png", baos);//w,h
				img = Image.getInstance(baos.toByteArray());
				img.scaleAbsolute(120f,80f); 
				img.setAlignment(Element.ALIGN_LEFT);

			}else{//student

				ImageIO.write(resize(bufferedImage, 300,150), "png", baos);
				img = Image.getInstance(baos.toByteArray());
				img.scaleAbsolute(70f,50f); 
				img.setAlignment(Element.ALIGN_LEFT);
			}


			

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 3513371438433721109L;


}
