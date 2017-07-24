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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.BarWeight;
import com.yahoo.petermwenda83.bean.exam.Deviation;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.staff.ClassTeacher;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.BarWeightDAO;
import com.yahoo.petermwenda83.persistence.exam.DeviationDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.session.SessionConstants;
import com.yahoo.petermwenda83.server.session.SessionStatistics;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;


/**
 * @author peter
 *
 */
public class ClassListF1 extends HttpServlet{

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


	private static ClassTeacherDAO classTeacherDAO;
	private static PerfomanceDAO perfomanceDAO;
	private static StudentDAO studentDAO;
	private static RoomDAO roomDAO;
	private static ExamConfigDAO examConfigDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static DeviationDAO deviationDAO;
	private static PrimaryDAO primaryDAO;
	private static BarWeightDAO barWeightDAO;

	String classroomuuid = "";
	String schoolusername = "";
	String stffID = "";
	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>(); 
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
		classTeacherDAO = ClassTeacherDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		roomDAO = RoomDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		deviationDAO = DeviationDAO.getInstance();
		primaryDAO = PrimaryDAO.getInstance();
		barWeightDAO = BarWeightDAO.getInstance();

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

		// ServletContext context = getServletContext();
		response.setContentType("application/pdf");

		SchoolAccount school = new SchoolAccount();
		HttpSession session = request.getSession(false);

		if(session !=null){
			schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
			//stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
			stffID = StringUtils.trimToEmpty(request.getParameter("staffid"));

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

		List<Perfomance> pDistinctListGeneral = new ArrayList<Perfomance>();
		pDistinctListGeneral = perfomanceDAO.getPerfomanceListDistinctGeneral(school.getUuid(), classID,examConfig.getTerm(),examConfig.getYear());

		List<Perfomance> pDistinctList = new ArrayList<Perfomance>();
		pDistinctList = perfomanceDAO.getPerfomanceListDistinct(school.getUuid(), classroomuuid,examConfig.getTerm(),examConfig.getYear());  

		List<Student> studentList = new ArrayList<Student>(); 
		studentList = studentDAO.getAllStudentList(school.getUuid());

		for(Student stu : studentList){
			studentAdmNoHash.put(stu.getUuid(),stu.getAdmno()); 

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


		String fileName = new StringBuffer(StringUtils.trimToEmpty("meritList")) 
				.append("_")
				.append(roomHash.get(classroomuuid).replaceAll(" ", "_"))
				.append(".pdf")
				.toString();
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		schoolname = school.getSchoolName().toUpperCase()+"\n";
		PDF_SUBTITLE =  "P.O BOX "+school.getPostalAddress()+"\n" 
				+ ""+school.getTown()+" - Kenya\n" 
				+ "" + school.getMobile()+"\n"
				+ "" + school.getEmail()+"\n" ;

		title = "_____________________________________ \n"

				+ " End of Term:"+examConfig.getTerm()+",Year:"+examConfig.getYear()+" Performance List For: "+roomHash.get(classroomuuid)+"\n";


		document = new Document(PageSize.A4.rotate(), 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();



			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(statistics, school,classroomuuid,classID,pDistinctList,pDistinctListGeneral,path);


		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return;

	}



	private void populatePDFDocument(SessionStatistics statistics, SchoolAccount school, String classroomuuid2, String classID, 
			List<Perfomance> pDistinctList, List<Perfomance> pDistinctListGeneral, String realPath) {
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

		Map<String,Double> MEANMapgn = new LinkedHashMap<String,Double>();
		Map<String,String> POSMapgn = new LinkedHashMap<String,String>();

		// double totalclassmark = 0;
		double classmean = 0;
		int studentcount = 0;
		double themean = 0;

		//languages
		Map<String,Double> kswscoreMap = new LinkedHashMap<String,Double>();
		Map<String,Double> engscorehash = new LinkedHashMap<String,Double>(); 
		//sciences
		Map<String,Double> physcoreMap = new LinkedHashMap<String,Double>();  
		Map<String,Double> matscorehash = new LinkedHashMap<String,Double>(); 
		Map<String,Double> bioscoreMap = new LinkedHashMap<String,Double>();
		Map<String,Double> chemscorehash = new LinkedHashMap<String,Double>(); 
		//techinicals
		Map<String,Double> bsscoreMap = new LinkedHashMap<String,Double>();
		Map<String,Double> agriscorehash = new LinkedHashMap<String,Double>(); 
		Map<String,Double> compscoreMap = new LinkedHashMap<String,Double>();
		Map<String,Double> hscscoreMap = new LinkedHashMap<String,Double>();
		//humanities 
		Map<String,Double> crescorehash = new LinkedHashMap<String,Double>(); 
		Map<String,Double> histscoreMap = new LinkedHashMap<String,Double>();
		Map<String,Double> geoscoreMap = new LinkedHashMap<String,Double>();

		String totalz = "";
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

			PdfPCell countHeader = new PdfPCell(new Paragraph("No",timesRomanBold7));
			countHeader.setBackgroundColor(baseColor);
			countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


			PdfPCell admNoHeader = new PdfPCell(new Paragraph("AdNo",timesRomanBold7));
			admNoHeader.setBackgroundColor(baseColor);
			admNoHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell nameHeader = new PdfPCell(new Paragraph("NAME",timesRomanBold7));
			nameHeader.setBackgroundColor(baseColor);
			nameHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell kcpeHeader = new PdfPCell(new Paragraph("KCPE",timesRomanBold7));
			kcpeHeader.setBackgroundColor(baseColor);
			kcpeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell engHeader = new PdfPCell(new Paragraph("ENG",timesRomanBold7));
			engHeader.setBackgroundColor(baseColor);
			engHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell kisHeader = new PdfPCell(new Paragraph("KIS",timesRomanBold7));
			kisHeader.setBackgroundColor(baseColor);
			kisHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell matHeader = new PdfPCell(new Paragraph("MAT",timesRomanBold7));
			matHeader.setBackgroundColor(baseColor);
			matHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell phyHeader = new PdfPCell(new Paragraph("PHY",timesRomanBold7));
			phyHeader.setBackgroundColor(baseColor);
			phyHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell cheHeader = new PdfPCell(new Paragraph("CHE",timesRomanBold7));
			cheHeader.setBackgroundColor(baseColor);
			cheHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell bioHeader = new PdfPCell(new Paragraph("BIO",timesRomanBold7));
			bioHeader.setBackgroundColor(baseColor);
			bioHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hisHeader = new PdfPCell(new Paragraph("HIS",timesRomanBold7));
			hisHeader.setBackgroundColor(baseColor);
			hisHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell creHeader = new PdfPCell(new Paragraph("CRE",timesRomanBold7));
			creHeader.setBackgroundColor(baseColor);
			creHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell geoHeader = new PdfPCell(new Paragraph("GEO",timesRomanBold7));
			geoHeader.setBackgroundColor(baseColor);
			geoHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell bsHeader = new PdfPCell(new Paragraph("B/S",timesRomanBold7));
			bsHeader.setBackgroundColor(baseColor);
			bsHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell agrHeader = new PdfPCell(new Paragraph("AGR",timesRomanBold7));
			agrHeader.setBackgroundColor(baseColor);
			agrHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hscHeader = new PdfPCell(new Paragraph("HSC",timesRomanBold7));
			hscHeader.setBackgroundColor(baseColor);
			hscHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell comHeader = new PdfPCell(new Paragraph("COM",timesRomanBold7));
			comHeader.setBackgroundColor(baseColor);
			comHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell totalsHeader = new PdfPCell(new Paragraph("TOTAL",timesRomanBold7));
			totalsHeader.setBackgroundColor(baseColor);
			totalsHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell SmeanHeader = new PdfPCell(new Paragraph("MEAN",timesRomanBold7));
			SmeanHeader.setBackgroundColor(baseColor);
			SmeanHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


			PdfPCell gradeHeader = new PdfPCell(new Paragraph("GRD",timesRomanBold7));
			gradeHeader.setBackgroundColor(baseColor);
			gradeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell deviationHeader = new PdfPCell(new Paragraph("Dev",timesRomanBold7));
			deviationHeader.setBackgroundColor(baseColor);
			deviationHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell SPcountHeader = new PdfPCell(new Paragraph("Stm Ps",timesRomanBold7));
			SPcountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell CPcountHeader = new PdfPCell(new Paragraph("Cls Ps",timesRomanBold7));
			CPcountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


			// OURTABLES
			PdfPTable myTable = new PdfPTable(23); 
			PdfPTable barchartTable = new PdfPTable(1);  
			PdfPTable SubjectTable = new PdfPTable(12);
			PdfPTable subAnalysisTable = new PdfPTable(16); 


			myTable.addCell(countHeader);
			myTable.addCell(admNoHeader);
			myTable.addCell(nameHeader);
			myTable.addCell(kcpeHeader);//engtable
			myTable.addCell(engHeader);//engHeader
			myTable.addCell(kisHeader);
			myTable.addCell(matHeader);
			myTable.addCell(phyHeader);
			myTable.addCell(cheHeader);
			myTable.addCell(bioHeader);
			myTable.addCell(hisHeader);
			myTable.addCell(creHeader);
			myTable.addCell(geoHeader);
			myTable.addCell(bsHeader);
			myTable.addCell(agrHeader);
			myTable.addCell(hscHeader);
			myTable.addCell(comHeader);
			myTable.addCell(totalsHeader); 
			myTable.addCell(SmeanHeader);
			myTable.addCell(gradeHeader); 
			myTable.addCell(deviationHeader);
			myTable.addCell(SPcountHeader);
			myTable.addCell(CPcountHeader);
			myTable.setWidthPercentage(100); 
			myTable.setWidths(new int[]{15,21,46,20,15,15,16,15,15,15,15,15,15,15,15,15,17,25,22,20,15,16,15});   
			myTable.setHorizontalAlignment(Element.ALIGN_LEFT);

			subAnalysisTable.addCell(new Paragraph("ENTRY",timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph(" ",timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("ENG :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.ENG_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("KISW :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.KISWA_UUID , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("MATH :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.MATH_UUID , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));

			subAnalysisTable.addCell(new Paragraph("BIO :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.BIO_UUID , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("CHEM :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.CHEM_UUID , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("PHY :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.PHY_UUID , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));

			subAnalysisTable.addCell(new Paragraph("BS :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.BS_UUID , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("COMP :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.COMP_UUID , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("HSC :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.H_S , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("AGR :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.AGR_UUID , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));

			subAnalysisTable.addCell(new Paragraph("GEO :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.GEO_UUID , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("CRE :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.CRE_UUID , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("HIST :\nEntry " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.HIST_UUID , classroomuuid,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));

			subAnalysisTable.addCell(new Paragraph("TOTAL",timesRomanBold7));
			//eng,kis,math,bio,chem,phy,bs,comp,hsc,agr,geo,cre,hist
			subAnalysisTable.setWidthPercentage(100); 
			subAnalysisTable.setWidths(new int[]{15,20,20,20,20,20,20,20,20,20,20,20,20,20,20,25});    
			subAnalysisTable.setHorizontalAlignment(Element.ALIGN_LEFT);


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
			double  totalscoregn = 0,grandscoregn = 0;
			double totalgrandscoregn = 0;

			double eng_total,eng_grand_total =0;
			double kis_total,kis_grand_total =0;
			double math_total,math_grand_total =0;
			double bio_total,bio_grand_total =0;
			double chem_total,chem_grand_total =0;
			double phy_total,phy_grand_total =0;
			double bs_total,bs_grand_total =0;
			double agr_total,agr_grand_total =0;
			double comp_total,comp_grand_total =0;
			double hmsc_total,hmsc_grand_total =0;
			double cre_total,cre_grand_total =0;
			double hist_total,hist_grand_total =0;
			double geo_total,geo_grand_total =0;


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

							bsscoregn = totalscoregn;				
							grandscoregn += totalscoregn;
							totalscoregn = 0;				
							bsscoreMapgn.put(pD.getStudentUuid(),bsscoregn);

						}


						if(true){
							/*BEGIN CHOOSE */	
							if(StringUtils.equals(pp.getSubjectUuid(), ExamConstants.AGR_UUID)){

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


								agriscoregn = totalscoregn;					
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

								comscoregn = totalscoregn;							
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

								hscscoregn = totalscoregn;				
								hscscoreMapgn.put(pD.getStudentUuid(),hscscoregn);

							}

							bestTechinical = Math.max( (Math.max(agriscoregn, comscoregn)), Math.max(Math.max(agriscoregn, comscoregn), hscscoregn));
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
				//double grandscoreTgn = 0;
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
						pos = (" " +(positiongn-counttwogn++));
						POSMapgn.put(uuid,pos);
					}
					else{
						counttwogn=1;
						pos = (" " +positiongn);
						POSMapgn.put(uuid,pos);
					}

					positiongn++;
					numbergn=meangn;


				}

			}

			/** end general ###################################################################
			 * ################################################################################################################################# */





			String studeadmno = "",studename = "";double number = 0.0;
			List<Perfomance> list = new ArrayList<>();
			Map<String,Double> grandscoremap = new LinkedHashMap<String,Double>(); 

			double totalscore = 0;
			double grandscore = 0;
			double totalgrandscore = 0;


			int gradeCountA = 0;
			int gradeCountAm = 0;
			int gradeCountBp = 0;
			int gradeCountB = 0;
			int gradeCountBm = 0;
			int gradeCountCP = 0;
			int gradeCountC = 0;
			int gradeCountCm = 0;
			int gradeCountDp = 0;
			int gradeCountD = 0;
			int gradeCountDm = 0;
			int gradeCountE = 0;

			bestTechinical2 = 0;

			if(pDistinctList !=null){

				totalgrandscore = 0;
				grandscore = 0;
				totalscore = 0;

				for(Perfomance s : pDistinctList){ 
					list = perfomanceDAO.getPerformance(school.getUuid(), classroomuuid, s.getStudentUuid(),examConfig.getTerm(),examConfig.getYear());    

					engscore = 0;
					kswscore = 0;
					matscore = 0;
					physcore = 0;
					bioscore = 0;
					chemscore = 0;
					bsscore = 0;
					compscore = 0;
					hscscore = 0;
					agriscore = 0;
					geoscore = 0;
					crescore = 0;
					histscore = 0;

					cat1  = 0; cat2  = 0; endterm = 0;



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

							engscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							engscorehash.put(s.getStudentUuid(),engscore);


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

							kswscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;


							kswscoreMap.put(s.getStudentUuid(),kswscore);


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

							physcore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							physcoreMap.put(s.getStudentUuid(),physcore);



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

							bioscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							bioscoreMap.put(s.getStudentUuid(),bioscore);



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


							chemscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							chemscorehash.put(s.getStudentUuid(),chemscore);


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
							matscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							matscorehash.put(s.getStudentUuid(),matscore);


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

							geoscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							geoscoreMap.put(s.getStudentUuid(),geoscore);


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
							crescore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							crescorehash.put(s.getStudentUuid(),crescore);




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
							histscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							histscoreMap.put(s.getStudentUuid(),histscore);




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

							bsscore = totalscore;

							grandscore += totalscore;
							totalscore = 0;

							bsscoreMap.put(s.getStudentUuid(),bsscore);


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

								agriscore = totalscore;

								//grandscore += totalscore;
								//totalscore = 0;

								agriscorehash.put(s.getStudentUuid(),agriscore);



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

								hscscore = totalscore;

								//grandscore += totalscore;
								//totalscore = 0;

								hscscoreMap.put(s.getStudentUuid(),hscscore);



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

								compscore = totalscore;

								compscoreMap.put(s.getStudentUuid(),compscore);

							} 

							bestTechinical = Math.max( (Math.max(agriscore, compscore)), Math.max(Math.max(agriscore, compscore), hscscore));
							//grandscore += bestTechinical;
							bestTechinical2 = bestTechinical;
							bestTechinical = 0;


						}// end if true


					}    


					grandscore += bestTechinical2;
					bestTechinical2 = 0;
					totalgrandscore += grandscore;
					grandscore = 0;
					grandscoremap.put(s.getStudentUuid(), totalgrandscore);
					totalgrandscore = 0;

				}

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


				int count = 1;
				int counttwo = 1;	


				double mean = 0;
				double totalmean = 0;


				eng_total = 0;
				kis_total = 0;
				math_total = 0;
				bio_total = 0;
				chem_total = 0;
				phy_total = 0;
				agr_total = 0;
				bs_total = 0;
				comp_total = 0;
				hmsc_total = 0;
				hist_total = 0;
				cre_total = 0;
				geo_total = 0;




				//end
				int gA = 0;
				int gAm = 0;
				int gBp = 0; 
				int gB = 0;
				int gBm = 0;
				int gCp = 0; 
				int gC = 0;
				int gCm = 0;
				int gDp = 0; 
				int gD = 0;
				int gDm = 0;
				int gE = 0;



				int stCount = 1;

				for(Object o : as){
					String items = String.valueOf(o);
					String [] item = items.split("=");
					String uuid = item[0];
					totalz = item[1];
					totalmean = 0;
					mean = Double.parseDouble(totalz)/11;	
					totalmean = mean;

					GraphWeightGenerator(mean,uuid,school.getUuid()); 

					eng_total = 0;
					kis_total = 0;
					math_total = 0;
					bio_total = 0;
					chem_total = 0;
					phy_total = 0;
					agr_total = 0;
					bs_total = 0;
					comp_total = 0;
					hmsc_total = 0;
					hist_total = 0;
					cre_total = 0;
					geo_total = 0;


					//KCSE
					double kcpe = 0;
					if(primaryDAO.getPrimary(uuid)!=null){
						StudentPrimary primary = primaryDAO.getPrimary(uuid);
						kcpe = Integer.parseInt(primary.getKcpemark()); 
					}




					studeadmno = studentAdmNoHash.get(uuid);
					studename = studNameHash.get(uuid);     

					if(engscorehash.get(uuid)!=null && engscorehash.get(uuid)!=0){
						engscore = engscorehash.get(uuid);  
						eng_total = engscore;
						engscorestr =  rf2.format(engscore);
					}else{
						engscorestr = "";
					}





					if(kswscoreMap.get(uuid)!=null && kswscoreMap.get(uuid)!=0){
						kswscore = kswscoreMap.get(uuid);
						kis_total = kswscore;
						kswscorestr = rf2.format(kswscore);
					}else{
						kswscorestr = "";
					}





					if(physcoreMap.get(uuid)!=null && physcoreMap.get(uuid)!=0){
						physcore = physcoreMap.get(uuid);
						phy_total = physcore;
						physcorestr = rf2.format(physcore);
					}else{
						physcorestr = "";
					}







					if(bioscoreMap.get(uuid)!=null && bioscoreMap.get(uuid)!=0){
						bioscore = bioscoreMap.get(uuid);
						bio_total = bioscore;
						bioscorestr = rf2.format(bioscore);
					}else{
						bioscorestr = "";
					}








					if(chemscorehash.get(uuid)!=null && chemscorehash.get(uuid)!=0){
						chemscore = chemscorehash.get(uuid);
						chem_total = chemscore;
						chemscorestr = rf2.format(chemscore);
					}else{
						chemscorestr = "";
					}







					if(matscorehash.get(uuid)!=null && matscorehash.get(uuid)!=0){
						matscore = matscorehash.get(uuid);
						math_total = matscore;
						matscorestr = rf2.format(matscore);
					}else{
						matscorestr = "";
					}




					if(histscoreMap.get(uuid)!=null && histscoreMap.get(uuid)!=0){
						histscore = histscoreMap.get(uuid);
						hist_total = histscore;
						histscorestr = rf2.format(histscore);
					}else{
						histscorestr = "";
					}







					if(crescorehash.get(uuid)!=null && crescorehash.get(uuid)!=0){
						crescore = crescorehash.get(uuid);
						cre_total = crescore;
						crescorestr = rf2.format(crescore);
					}else{
						crescorestr = "";
					}








					if(geoscoreMap.get(uuid)!=null && geoscoreMap.get(uuid)!=0){
						geoscore = geoscoreMap.get(uuid);
						geo_total = geoscore;
						geoscorestr = rf2.format(geoscore);
					}else{
						geoscorestr = "";
					}






					if(bsscoreMap.get(uuid)!=null && bsscoreMap.get(uuid)!=0){
						bsscore = bsscoreMap.get(uuid);
						bs_total = bsscore;
						bsscorestr = rf2.format(bsscore);
					}else{
						bsscorestr = "";
					}








					if(agriscorehash.get(uuid)!=null && agriscorehash.get(uuid)!=0){
						agriscore = agriscorehash.get(uuid);
						agr_total = agriscore;
						agriscorestr = rf2.format(agriscore);
					}else{
						agriscorestr = "";
					}







					if(hscscoreMap.get(uuid)!=null && hscscoreMap.get(uuid)!=0){
						hscscore = hscscoreMap.get(uuid);
						hmsc_total = hscscore;
						hscscorestr = rf2.format(hscscore);
					}else{
						hscscorestr = "";
					}






					if(compscoreMap.get(uuid)!=null && compscoreMap.get(uuid)!=0){
						compscore = compscoreMap.get(uuid);
						comp_total = compscore;
						compscorestr = rf2.format(compscore);
					}else{
						compscorestr = "";
					}   
					//kcpe

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



					myTable.addCell(new Paragraph(" "+stCount+" ",timesRomanNormal7));
					myTable.addCell(new Paragraph(studeadmno,timesRomanNormal7));
					myTable.addCell(new Paragraph(studename,timesRomanNormal7));				   
					myTable.addCell(new Paragraph((int)kcpe +" ",timesRomanNormal7));
					myTable.addCell(new Paragraph(engscorestr /* "         " + computeSubGrade(engscorestr)*/,timesRomanNormal7));
					myTable.addCell(new Paragraph(kswscorestr /* "   		 " + computeSubGrade(kswscorestr)*/,timesRomanNormal7));
					myTable.addCell(new Paragraph(matscorestr /* " 		 " + computeSubGrade(matscorestr)*/,timesRomanNormal7));
					myTable.addCell(new Paragraph(physcorestr /* "  		 " + computeSubGrade(physcorestr)*/,timesRomanNormal7));
					myTable.addCell(new Paragraph(chemscorestr /* " 		 " + computeSubGrade(chemscorestr)*/,timesRomanNormal7));
					myTable.addCell(new Paragraph(bioscorestr /* "		 " + computeSubGrade(bioscorestr)*/,timesRomanNormal7));
					myTable.addCell(new Paragraph(histscorestr /* " 		 " + computeSubGrade(histscorestr)*/,timesRomanNormal7));
					myTable.addCell(new Paragraph(crescorestr /* " 		 " + computeSubGrade(crescorestr)*/,timesRomanNormal7));				   
					myTable.addCell(new Paragraph(geoscorestr /* " 		 " + computeSubGrade(geoscorestr)*/,timesRomanNormal7));
					myTable.addCell(new Paragraph(bsscorestr /* " 		 " + computeSubGrade(bsscorestr)*/,timesRomanNormal7));
					myTable.addCell(new Paragraph(agriscorestr /* " 		 " + computeSubGrade(agriscorestr)*/,timesRomanNormal7));
					myTable.addCell(new Paragraph(hscscorestr /* " 		 " + computeSubGrade(hscscorestr)*/,timesRomanNormal7));
					myTable.addCell(new Paragraph(compscorestr /* " 		 " + computeSubGrade(compscorestr)*/,timesRomanNormal7));   
					myTable.addCell(new Paragraph(halfUP.format(Double.parseDouble(totalz)),timesRomanNormal7));				   
					myTable.addCell(new Paragraph(halfUP.format(mean),timesRomanNormal7));
					myTable.addCell(new Paragraph(computeGrade(mean),timesRomanNormal7));
					myTable.addCell(new Paragraph(devComment,timesRomanNormal7));

					if(Double.parseDouble(totalz)==number){
						myTable.addCell(new Paragraph(" "+(count-counttwo++),timesRomanNormal7));

					}
					else{
						counttwo=1;
						myTable.addCell(new Paragraph(" "+count+" ",timesRomanNormal7));

					}

					myTable.addCell(new Paragraph(POSMapgn.get(uuid),timesRomanNormal7));




					//analyze the grades



					/**start grade analyzer */

					if(mean >= gradingSystem.getGradeAplain()){
						gradeCountA++;// set to 0
						gA += gradeCountA;	// ince gA	= 1
						gradeCountA = 0; //reset to 0

					}else if(mean >= gradingSystem.getGradeAminus()){
						gradeCountAm++;
						gAm +=gradeCountAm;	
						gradeCountAm = 0;


					}else if(mean >= gradingSystem.getGradeBplus()){
						gradeCountBp++;
						gBp +=gradeCountBp;	
						gradeCountBp = 0;


					}else if(mean >= gradingSystem.getGradeBplain()){
						//System.out.println("B grade ="+ gradeCountB);

						gradeCountB++;
						gB +=gradeCountB;	
						gradeCountB = 0;
					}else if(mean >= gradingSystem.getGradeBminus()){
						//System.out.println("B- grade ="+ gradeCountBm);
						gradeCountBm++;
						gBm +=gradeCountBm;	
						gradeCountBm = 0;
					}else if(mean >= gradingSystem.getGradeCplus()){
						//System.out.println("C+ grade ="+ gradeCountCP);
						gradeCountCP++;
						gCp +=gradeCountCP;	
						gradeCountCP = 0;
					}else if(mean >= gradingSystem.getGradeCplain()){
						//System.out.println("C grade ="+ gradeCountC);	
						gradeCountC++;
						gC +=gradeCountC;	
						gradeCountC = 0;

					}else if(mean >= gradingSystem.getGradeCminus()){
						// System.out.println("C- grade ="+ gradeCountCm);					  
						gradeCountCm++;
						gCm +=gradeCountCm;	
						gradeCountCm = 0;
					}else if(mean >= gradingSystem.getGradeDplus()){
						//System.out.println("D+ grade ="+ gradeCountDp);	
						gradeCountDp++;
						gDp +=gradeCountDp;	
						gradeCountDp = 0;

					}else if(mean >= gradingSystem.getGradeDplain()){
						//System.out.println("D grade ="+ gradeCountD);	
						gradeCountD++;
						gD +=gradeCountD;	
						gradeCountD = 0;

					}else if(mean >= gradingSystem.getGradeDminus()){
						// System.out.println("D- grade ="+ gradeCountDm);
						gradeCountDm++;
						gDm +=gradeCountDm;	
						gradeCountDm = 0;
					}else{
						// System.out.println("E grade ="+ gradeCountE);
						gradeCountE++;
						gE +=gradeCountE;	
						gradeCountE = 0;

					}

					if(mean ==0){
						//System.out.println("E grade ="+ gradeCountE);
						gradeCountE++;
						gE +=gradeCountE;	
						gradeCountE = 0;

					}//end if



					count++;
					studentcount++;
					number=Double.parseDouble(totalz);

					themean += totalmean;
					themean = Double.parseDouble(df.format(themean));
					totalmean = 0;

					//add totals
					eng_grand_total +=eng_total;
					kis_grand_total +=kis_total;
					math_grand_total +=math_total;
					bio_grand_total +=bio_total;
					phy_grand_total +=phy_total;
					chem_grand_total +=chem_total;
					agr_grand_total +=agr_total;
					bs_grand_total +=bs_total;
					comp_grand_total +=comp_total;
					hmsc_grand_total +=hmsc_total;
					hist_grand_total +=hist_total;
					cre_grand_total +=cre_total;
					geo_grand_total +=geo_total;

					stCount++;

				}




				//TABLE START
				SubjectTable.addCell(new Paragraph("A",timesRomanBold7));
				SubjectTable.addCell(new Paragraph("A-",timesRomanBold7));
				SubjectTable.addCell(new Paragraph("B+",timesRomanBold7));

				SubjectTable.addCell(new Paragraph("B",timesRomanBold7));
				SubjectTable.addCell(new Paragraph("B-",timesRomanBold7));
				SubjectTable.addCell(new Paragraph("C+",timesRomanBold7));

				SubjectTable.addCell(new Paragraph("C",timesRomanBold7));
				SubjectTable.addCell(new Paragraph("C-",timesRomanBold7));
				SubjectTable.addCell(new Paragraph("D+",timesRomanBold7));

				SubjectTable.addCell(new Paragraph("D",timesRomanBold7));
				SubjectTable.addCell(new Paragraph("D-",timesRomanBold7));
				SubjectTable.addCell(new Paragraph("E",timesRomanBold7));


				SubjectTable.setWidthPercentage(100); 
				SubjectTable.setWidths(new int[]{20,20,20,20,20,20,20,20,20,20,20,20});   
				SubjectTable.setHorizontalAlignment(Element.ALIGN_LEFT);

				//chart table

				barchartTable.setWidthPercentage(100); 
				barchartTable.setWidths(new int[]{100}); 
				barchartTable.setHorizontalAlignment(Element.ALIGN_LEFT);



				SubjectTable.addCell(new Paragraph(gA+" ",timesRomanNormal7));
				SubjectTable.addCell(new Paragraph(gAm+" ",timesRomanNormal7));

				SubjectTable.addCell(new Paragraph(gBp+" ",timesRomanNormal7));
				SubjectTable.addCell(new Paragraph(gB+" ",timesRomanNormal7));
				SubjectTable.addCell(new Paragraph(gBm+" ",timesRomanNormal7));

				SubjectTable.addCell(new Paragraph(gCp+" ",timesRomanNormal7));
				SubjectTable.addCell(new Paragraph(gC+" ",timesRomanNormal7));
				SubjectTable.addCell(new Paragraph(gCm+" ",timesRomanNormal7));

				SubjectTable.addCell(new Paragraph(gDp+" ",timesRomanNormal7));
				SubjectTable.addCell(new Paragraph(gD+" ",timesRomanNormal7));
				SubjectTable.addCell(new Paragraph(gDm+" ",timesRomanNormal7));

				SubjectTable.addCell(new Paragraph(gE+" ",timesRomanNormal7));


				//END TABLE

				//draw chart start
				PdfPCell BarChartHeader = new PdfPCell();
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();


				dataset.setValue((eng_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.ENG_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "ENG");
				dataset.setValue((kis_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.KISWA_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "KIS");
				dataset.setValue((math_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.MATH_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "MAT");

				dataset.setValue((bio_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.BIO_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "BIO");
				dataset.setValue((chem_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.CHEM_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "CHE");
				dataset.setValue((phy_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.PHY_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "PHY");

				dataset.setValue((bs_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.BS_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "BS");
				dataset.setValue((comp_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.COMP_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "CMP");
				dataset.setValue((hmsc_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.H_S, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "HSC");
				dataset.setValue((agr_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.AGR_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "AGR");

				//eng,kis,math,   bio,chem,phy,   bs,comp,hsc,agr,  geo,cre,hist
				dataset.setValue((geo_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.GEO_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "GEO");
				dataset.setValue((cre_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.CRE_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "CRE");
				dataset.setValue((hist_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.HIST_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "HST");


				//CONTROL
				dataset.setValue(12, "Control ", "Control ");
				JFreeChart chart = ChartFactory.createBarChart("Subjects Performance Analysis", // chart title
						"Subject", // domain axis label (X axis)
						"Weight", //  range axis label (Y axis)
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
					chartImage.scaleToFit(1500,500); 
					BarChartHeader.addElement(new Chunk(chartImage,15,-130));// margin left  ,  margin right
					BarChartHeader.setBorder(Rectangle.NO_BORDER); 
					BarChartHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
					BarChartHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


				} catch (IOException e) {
					e.printStackTrace();
				}

				barchartTable.addCell(BarChartHeader);
				//draw chart end


				//reset variables
				gA = 0;
				gAm = 0;
				gBp = 0;
				gB = 0;
				gBm = 0;
				gCp = 0;
				gC = 0;
				gCm = 0;
				gDp = 0;
				gD = 0;
				gDm = 0;
				gE = 0;





				classmean =themean/studentcount;


			}

			for(int i = 0; i < 3;i++){

				if(i == 0){
					subAnalysisTable.addCell(new Paragraph(studentcount+" ",timesRomanNormal7)); 
					subAnalysisTable.addCell(new Paragraph("TOTAL ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(eng_grand_total)+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(kis_grand_total)+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(math_grand_total)+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(bio_grand_total)+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(chem_grand_total)+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(phy_grand_total)+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(bs_grand_total)+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(comp_grand_total)+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(hmsc_grand_total)+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(agr_grand_total)+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(geo_grand_total)+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(cre_grand_total)+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(hist_grand_total)+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(themean)+" ",timesRomanNormal7));

				}if(i == 1){
					subAnalysisTable.addCell(new Paragraph(studentcount+" ",timesRomanNormal7)); 
					subAnalysisTable.addCell(new Paragraph("MEAN ",timesRomanNormal7));
					//System.out.println("eng " + eng_grand_total + " / " + perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.ENG_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear()) +" = "+   eng_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.ENG_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(eng_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.ENG_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(kis_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.KISWA_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(math_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.MATH_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(bio_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.BIO_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(chem_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.CHEM_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(phy_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.PHY_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(bs_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.BS_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(comp_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.COMP_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(hmsc_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.H_S, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(agr_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.AGR_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(geo_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.GEO_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(cre_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.CRE_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(hist_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.HIST_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(classmean)+" ",timesRomanNormal7));
				}
				if(i == 2){
					subAnalysisTable.addCell(new Paragraph(" ",timesRomanNormal7)); 
					subAnalysisTable.addCell(new Paragraph("GRADE ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(computeGrade(eng_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.ENG_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(kis_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.KISWA_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(math_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.MATH_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(computeGrade(bio_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.BIO_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(chem_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.CHEM_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(phy_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.PHY_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(computeGrade(bs_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.BS_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(comp_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.COMP_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(hmsc_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.H_S, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(agr_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.AGR_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(computeGrade(geo_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.GEO_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(cre_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.CRE_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(hist_grand_total/(perfomanceDAO.getSubjectCountPerStream(school.getUuid(), ExamConstants.HIST_UUID, classroomuuid,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(computeGrade(classmean)+" ",timesRomanNormal7));
				}


			}

			eng_grand_total = 0;
			kis_grand_total = 0;
			math_grand_total = 0;
			phy_grand_total = 0;
			chem_grand_total = 0;
			bio_grand_total = 0;
			hist_grand_total = 0;
			cre_grand_total =0;
			geo_grand_total = 0;
			bs_grand_total = 0;
			agr_grand_total = 0;
			hmsc_grand_total = 0;
			comp_grand_total = 0;

			document.add(prefaceTable);
			document.add(emptyline);

			document.add(myTable);  
			document.add(emptyline);

			document.newPage();

			document.add(SubjectTable); 
			document.add(emptyline);
			document.add(barchartTable); 
			document.add(emptyline);
			document.add(emptyline);
			document.add(emptyline);
			document.add(emptyline);
			document.add(emptyline);
			document.add(emptyline);
			document.add(emptyline);
			document.add(emptyline);
			document.add(subAnalysisTable);

			//BARChartHeader  SubjectTable
			// step 5
			document.close();
		}
		catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}  
	}


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
		String comment = "";
		DecimalFormat df = new DecimalFormat("0.00"); 
		df.setRoundingMode(RoundingMode.HALF_UP);
		double dev = 0;
		dev = mean;
		if(dev<0){
			//Negative , student nose diving
			double positiveDev = Math.abs(dev);
			String newDev = "-"+df.format(positiveDev);
			comment = " " + newDev;
			//System.out.println(comment);

		}else if(dev>0){
			//positive , student working hard
			comment = " " + df.format(dev);

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
	 * @param scoreStr
	 * @return
	 */
	private String computeSubGrade(String scoreStr){
		String grade = "";
		double theGrade = 0;
		if(!StringUtils.isBlank(scoreStr)){
			theGrade = Double.parseDouble(scoreStr);
			if(theGrade >= gradingSystem.getGradeAplain()){
				grade = "A";
			}else if(theGrade >= gradingSystem.getGradeAminus()){
				grade = "A-";
			}else if(theGrade >= gradingSystem.getGradeBplus()){
				grade = "B+";
			}else if(theGrade >= gradingSystem.getGradeBplain()){
				grade = "B";
			}else if(theGrade >= gradingSystem.getGradeBminus()){
				grade = "B-";
			}else if(theGrade >= gradingSystem.getGradeCplus()){
				grade = "C+";
			}else if(theGrade >= gradingSystem.getGradeCplain()){
				grade = "C";
			}else if(theGrade >= gradingSystem.getGradeCminus()){
				grade = "C-";
			}else if(theGrade >= gradingSystem.getGradeDplus()){
				grade = "D+";
			}else if(theGrade >= gradingSystem.getGradeDplain()){
				grade = "D";
			}else if(theGrade >= gradingSystem.getGradeDminus()){
				grade = "D-";
			}else{
				grade = "E";
			}


		}else{
			grade = ""; 
		}
		return grade;
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
		}else if(mean >= 1){
			grade = "E";
		}else{
			grade = " ";
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 3513371438433721109L;
}
