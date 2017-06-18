/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.result.report;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.yahoo.petermwenda83.bean.classroom.Classes;
import com.yahoo.petermwenda83.bean.exam.Deviation;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.bean.student.StudentSubject;
import com.yahoo.petermwenda83.persistence.classroom.ClassesDAO;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.DeviationDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.result.ExamConstants;
import com.yahoo.petermwenda83.server.servlet.result.PdfUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;
import com.yahoo.petermwenda83.server.session.SessionStatistics;
import com.yahoo.petermwenda83.server.util.magic.MiddleNumberFor3;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author peter
 *
 */
public class PerformanceListF34 extends HttpServlet{

	//private Font courierBold10 = ExamConstants.courierBold10;
	private Font courierBold14 = ExamConstants.courierBold14;
	private Font timesRomanBold7 = ExamConstants.timesRomanBold7;
	private Font timesRomanItalic8 = ExamConstants.timesRomanItalic8;
	private Font timesRomanNormal7 = ExamConstants.timesRomanNormal7;

	private Document document;
	private PdfWriter writer;
	private Cache schoolaccountCache, statisticsCache;

	private Logger logger;
	ExamConfig examConfig;
	GradingSystem gradingSystem;

	private String PDF_SUBTITLE ="";
	private String schoolname = "";
	private String title = "";

	private static PerfomanceDAO perfomanceDAO;
	private static StudentDAO studentDAO;
	private static ClassesDAO classesDAO;
	private static ExamConfigDAO examConfigDAO;
	private static GradingSystemDAO gradingSystemDAO;

	private static DeviationDAO deviationDAO;
	private static PrimaryDAO primaryDAO;
	private static RoomDAO roomDAO;
	private static StudentSubjectDAO studentSubjectDAO;


	String schoolusername = "";

	private final String EXAM_FULL_ID = "4BE8AD46-EAE8-4151-BD18-CB23CF904DDB";
	String examID = "";

	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>();
	HashMap<String, String> roomHash = new HashMap<String, String>();
	HashMap<String, String> streamsHash = new HashMap<String, String>();
	HashMap<String, String> studentStreamHash = new HashMap<String, String>();


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
		studentDAO = StudentDAO.getInstance();
		classesDAO = ClassesDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		deviationDAO = DeviationDAO.getInstance();
		primaryDAO = PrimaryDAO.getInstance();
		roomDAO = RoomDAO.getInstance();
		studentSubjectDAO = StudentSubjectDAO.getInstance();

		USER = System.getProperty("user.name");
		path = "/home/"+USER+"/school/logo/logo.png";	}

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
		schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);

		examID = StringUtils.trimToEmpty(request.getParameter("examID"));
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

		SessionStatistics statistics = new SessionStatistics();
		if ((element = statisticsCache.get(schoolusername)) != null) {
			statistics = (SessionStatistics) element.getObjectValue();
		}

		List<Perfomance> pDistinctListGeneral = new ArrayList<Perfomance>();
		pDistinctListGeneral = perfomanceDAO.getPerfomanceListDistinctGeneral(school.getUuid(), classID,examConfig.getTerm(),examConfig.getYear());

		List<Student> studentList = new ArrayList<Student>(); 
		studentList = studentDAO.getAllStudentList(school.getUuid());

		for(Student stu : studentList){
			studentAdmNoHash.put(stu.getUuid(),stu.getAdmno()); 

			String formatedFirstname = StringUtils.capitalize(stu.getFirstname().toLowerCase());
			String formatedSurname = StringUtils.capitalize(stu.getLastname().toLowerCase());

			formatedFirstname = formatedFirstname.substring(0, Math.min(formatedFirstname.length(), 10));
			formatedSurname = formatedSurname.substring(0, Math.min(formatedSurname.length(), 10));

			studNameHash.put(stu.getUuid(),formatedFirstname + " " + formatedSurname ); 
			studentStreamHash.put(stu.getUuid(), stu.getClassRoomUuid());
		}

		List<Classes> classesList = new ArrayList<Classes>(); 
		classesList = classesDAO.getClassList(); 
		for(Classes c : classesList){
			roomHash.put(c.getUuid() , c.getClassName());
		}

		List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
		classroomList = roomDAO.getAllRooms(school.getUuid()); 
		for(ClassRoom c : classroomList){
			streamsHash.put(c.getUuid() , c.getRoomName());
		}


		String fileName = new StringBuffer(StringUtils.trimToEmpty("meritList")) 
				.append("_")
				.append(roomHash.get(classID).replaceAll(" ", "_"))
				.append(".pdf")
				.toString();
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		schoolname = school.getSchoolName().toUpperCase()+"\n";
		PDF_SUBTITLE =  "P.O BOX "+school.getPostalAddress()+"\n" 
				+ ""+school.getTown()+" - Kenya\n" 
				+ "" + school.getMobile()+"\n"
				+ "" + school.getEmail()+"\n" ;

		title = "_____________________________________ \n"

					+ " End of Term:"+examConfig.getTerm()+",Year:"+examConfig.getYear()+" Performance List For: "+roomHash.get(classID)+"\n";

		document = new Document(PageSize.A4.rotate(), 46, 46, 64, 64);

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

		Map<String,String> POSMapgn = new LinkedHashMap<String,String>();


		double mean = 0;
		double engscore = 0;String engscorestr = "";
		double kswscore = 0;String kswscorestr = "";
		double matscore = 0;String matscorestr = "";
		double physcore = 0;String physcorestr = "";  
		double bioscore = 0;String bioscorestr = "";
		double chemscore = 0;String chemscorestr = "";
		double bsscore = 0;String bsscorestr = "";
		double comscore = 0;String comscorestr = "";
		double hscscore = 0;String hscscorestr = "";
		double agriscore = 0;String agriscorestr = "";
		double geoscore = 0;String geoscorestr = "";
		double crescore = 0;String crescorestr = "";
		double histscore = 0;String histscorestr = "";



		String totalz = "";
		try {
			document.open();

			BaseColor baseColor = new BaseColor(255,255,255);//while

			Paragraph emptyline = new Paragraph(("                              "));
			PdfPTable prefaceTable = new PdfPTable(2);  
			prefaceTable.setWidthPercentage(100); 
			prefaceTable.setWidths(new int[]{70,130}); 
			// OURTABLES
			PdfPTable barchartTable = new PdfPTable(1);  
			PdfPTable SubjectTable = new PdfPTable(12);
			PdfPTable myTable = new PdfPTable(23); 
			PdfPTable subAnalysisTable = new PdfPTable(16); 

			Paragraph content = new Paragraph();
			content.add(new Paragraph((schoolname +"") , courierBold14));//
			content.add(new Paragraph((PDF_SUBTITLE +"") , timesRomanItalic8));
			content.add(new Paragraph((title +" \n") , courierBold14));



			PdfPCell contentcell = new PdfPCell(content);
			contentcell.setBorder(Rectangle.NO_BORDER); 
			contentcell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			Paragraph preface = new Paragraph();
			preface.add(createImage(realPath));

			Image imgLogo = null;
			try {
				imgLogo = Image.getInstance(realPath);
			} catch (IOException e) {

				e.printStackTrace();
			}

			imgLogo.scalePercent(ExamConstants.LOGO_SCALE); 
			imgLogo.setAlignment(Element.ALIGN_LEFT);

			PdfPCell logo = new PdfPCell();
			logo.addElement(new Chunk(imgLogo,ExamConstants.LOGO_L,ExamConstants.LOGO_R)); // margin left  ,  margin top
			logo.setBorder(Rectangle.NO_BORDER); 
			logo.setHorizontalAlignment(Element.ALIGN_LEFT);

			prefaceTable.addCell(logo); 
			prefaceTable.addCell(contentcell);

			document.add(prefaceTable);

			subAnalysisTable.addCell(new Paragraph("ENTRY",timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph(" ",timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("ENG :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.ENG_UUID, classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("KISW :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.KISWA_UUID , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("MATH :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.MATH_UUID , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));

			subAnalysisTable.addCell(new Paragraph("BIO :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.BIO_UUID , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("CHEM :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.CHEM_UUID , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("PHY :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.PHY_UUID , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));

			subAnalysisTable.addCell(new Paragraph("BS :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.BS_UUID , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("COMP :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.COMP_UUID , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("HSC :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.H_S , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("AGR :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.AGR_UUID , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));

			subAnalysisTable.addCell(new Paragraph("GEO :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.GEO_UUID , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("CRE :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.CRE_UUID , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));
			subAnalysisTable.addCell(new Paragraph("HIST :\nEntry " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(),ExamConstants.HIST_UUID , classID,examConfig.getTerm(),examConfig.getYear()),timesRomanBold7));

			subAnalysisTable.addCell(new Paragraph("TOTAL",timesRomanBold7));
			//eng,kis,math,bio,chem,phy,bs,comp,hsc,agr,geo,cre,hist
			subAnalysisTable.setWidthPercentage(100); 
			subAnalysisTable.setWidths(new int[]{15,20,20,20,20,20,20,20,20,20,20,20,20,20,20,25});    
			subAnalysisTable.setHorizontalAlignment(Element.ALIGN_LEFT);

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

			

			PdfPCell countHeader = new PdfPCell(new Paragraph("No",timesRomanBold7));
			countHeader.setBackgroundColor(baseColor);
			countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


			PdfPCell admNoHeader = new PdfPCell(new Paragraph("Ad No",timesRomanBold7));
			admNoHeader.setBackgroundColor(baseColor);//

			PdfPCell streamHeader = new PdfPCell(new Paragraph("FORM",timesRomanBold7));
			streamHeader.setBackgroundColor(baseColor);
			streamHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

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

			PdfPCell meanHeader = new PdfPCell(new Paragraph("MN",timesRomanBold7));
			meanHeader.setBackgroundColor(baseColor);
			meanHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell gradeHeader = new PdfPCell(new Paragraph("GRD",timesRomanBold7));
			gradeHeader.setBackgroundColor(baseColor);
			gradeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell deviationHeader = new PdfPCell(new Paragraph("Dev",timesRomanBold7));
			deviationHeader.setBackgroundColor(baseColor);
			deviationHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell CPHeader = new PdfPCell(new Paragraph("Cls Ps",timesRomanBold7));
			CPHeader.setBackgroundColor(baseColor);
			CPHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			myTable.addCell(countHeader);
			myTable.addCell(admNoHeader);
			myTable.addCell(streamHeader);
			myTable.addCell(nameHeader);
			myTable.addCell(kcpeHeader);
			myTable.addCell(engHeader);
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
			myTable.addCell(meanHeader);
			myTable.addCell(gradeHeader);
			myTable.addCell(deviationHeader);
			myTable.addCell(CPHeader);
			myTable.setWidthPercentage(100); 
			myTable.setWidths(new int[]{15,20,25,45,20,15,15,16,15,15,15,15,15,15,15,15,15,17,24,21,18,15,16});   
			myTable.setHorizontalAlignment(Element.ALIGN_LEFT);



			double humanityScoregn =0,techinicalScoregn = 0;
			double scienceScoregn = 0;
			double classmean = 0;
			double totalclassmark = 0;
			int studentcount = 0;


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
			double  grandscoregn = 0;

			double  paper1gn  = 0, paper2gn  = 0, paper3gn = 0,totalgn=0;
			double catTotalsgn = 0,catmeangn = 0,examcattotalgn = 0;
			double languageScoregn = 0; 

			double number = 0.0;

			List<Perfomance> listGeneral = new ArrayList<>();
			if(pDistinctListGeneral !=null){
				for(Perfomance pD : pDistinctListGeneral){     
					listGeneral = perfomanceDAO.getPerformanceGeneral(school.getUuid(), classID, pD.getStudentUuid(),examConfig.getTerm(),examConfig.getYear());

					engscoregn = 0;	kswscoregn = 0;
					matscoregn = 0;	physcoregn = 0;
					bioscoregn = 0;	chemscoregn = 0;
					bsscoregn = 0;comscoregn = 0;
					hscscoregn = 0;agriscoregn = 0;
					geoscoregn = 0;crescoregn = 0;
					histscoregn = 0;
					cat1gn  = 0; cat2gn  = 0; endtermgn = 0;

					for(Perfomance pp : listGeneral){

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
					grandscoregn = 0;

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


				int count = 1;
				int counttwo = 1;
				int stCount = 1;

				for(Object o : as){
					String items = String.valueOf(o);
					String [] item = items.split("=");
					String uuid = item[0];
					totalz = item[1];
					mean = Double.parseDouble(totalz)/7;



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

					totalclassmark +=mean;


					String studeadmno = "";String studename = "";

					studeadmno = studentAdmNoHash.get(uuid);
					studename = studNameHash.get(uuid);    



					//KCSE
					double kcpe = 0;
					if(primaryDAO.getPrimary(uuid)!=null){
						StudentPrimary primary = primaryDAO.getPrimary(uuid);
						kcpe = Integer.parseInt(primary.getKcpemark()); 
					}




					if(engscorehashgn.get(uuid)!=null && engscorehashgn.get(uuid)!=0){
						engscore = engscorehashgn.get(uuid);  
						eng_total = engscore;
						engscorestr =  rf2.format(engscore);
					}else{
						engscorestr = "";
					}


					if(kswscoreMapgn.get(uuid)!=null && kswscoreMapgn.get(uuid)!=0){
						kswscore = kswscoreMapgn.get(uuid);
						kis_total = kswscore;
						kswscorestr = rf2.format(kswscore);
					}else{
						kswscorestr = "";
					}


					if(physcoreMapgn.get(uuid)!=null && physcoreMapgn.get(uuid)!=0){
						physcore = physcoreMapgn.get(uuid);
						phy_total = physcore;
						physcorestr = rf2.format(physcore);
					}else{
						physcorestr = "";
					}


					if(bioscoreMapgn.get(uuid)!=null && bioscoreMapgn.get(uuid)!=0){
						bioscore = bioscoreMapgn.get(uuid);
						bio_total = bioscore;
						bioscorestr = rf2.format(bioscore);
					}else{
						bioscorestr = "";
					}

					if(chemscorehashgn.get(uuid)!=null && chemscorehashgn.get(uuid)!=0){
						chemscore = chemscorehashgn.get(uuid);
						chem_total = chemscore;
						chemscorestr = rf2.format(chemscore);
					}else{
						chemscorestr = "";
					}


					if(matscorehashgn.get(uuid)!=null && matscorehashgn.get(uuid)!=0){
						matscore = matscorehashgn.get(uuid);
						math_total = matscore;
						matscorestr = rf2.format(matscore);
					}else{
						matscorestr = "";
					}


					if(histscoreMapgn.get(uuid)!=null && histscoreMapgn.get(uuid)!=0){
						histscore = histscoreMapgn.get(uuid);
						hist_total = histscore;
						histscorestr = rf2.format(histscore);
					}else{
						histscorestr = "";
					}



					if(crescorehashgn.get(uuid)!=null && crescorehashgn.get(uuid)!=0){
						crescore = crescorehashgn.get(uuid);
						cre_total = crescore;
						crescorestr = rf2.format(crescore);
					}else{
						crescorestr = "";
					}

					if(geoscoreMapgn.get(uuid)!=null && geoscoreMapgn.get(uuid)!=0){
						geoscore = geoscoreMapgn.get(uuid);
						geo_total = geoscore;
						geoscorestr = rf2.format(geoscore);
					}else{
						geoscorestr = "";
					}


					if(bsscoreMapgn.get(uuid)!=null && bsscoreMapgn.get(uuid)!=0){
						bsscore = bsscoreMapgn.get(uuid);
						bs_total = bsscore;
						bsscorestr = rf2.format(bsscore);
					}else{
						bsscorestr = "";
					}

					if(agriscorehashgn.get(uuid)!=null && agriscorehashgn.get(uuid)!=0){
						agriscore = agriscorehashgn.get(uuid);
						agr_total = agriscore;
						agriscorestr = rf2.format(agriscore);
					}else{
						agriscorestr = "";
					}


					if(hscscoreMapgn.get(uuid)!=null && hscscoreMapgn.get(uuid)!=0){
						hscscore = hscscoreMapgn.get(uuid);
						hmsc_total = hscscore;
						hscscorestr = rf2.format(hscscore);
					}else{
						hscscorestr = "";
					}


					if(comscoreMapgn.get(uuid)!=null && comscoreMapgn.get(uuid)!=0){
						comscore = comscoreMapgn.get(uuid);
						comp_total = comscore;
						comscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(comscore))));
					}else{
						comscorestr = "";
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

					String pos = "";

					if(Double.parseDouble(totalz)==number){
						pos = (count-counttwo++) +" ";
						POSMapgn.put(uuid,pos);
					}
					else{
						counttwo=1;
						pos = " "+count;
						POSMapgn.put(uuid,pos);
					}



					//streamsHash.get(studentStreamHash.get(uuid));
					myTable.addCell(new Paragraph(" "+stCount+" ",timesRomanNormal7));
					myTable.addCell(new Paragraph(studeadmno,timesRomanNormal7));
					myTable.addCell(new Paragraph(streamsHash.get(studentStreamHash.get(uuid)),timesRomanNormal7));	
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
					myTable.addCell(new Paragraph(comscorestr /* " 		 " + computeSubGrade(comscorestr)*/,timesRomanNormal7));				   
					myTable.addCell(new Paragraph(halfUP.format(Double.parseDouble(totalz)),timesRomanNormal7));				   
					myTable.addCell(new Paragraph(halfUP.format(mean),timesRomanNormal7));
					myTable.addCell(new Paragraph(computeGrade(mean),timesRomanNormal7));
					myTable.addCell(new Paragraph(devComment,timesRomanNormal7));
					myTable.addCell(new Paragraph(POSMapgn.get(uuid),timesRomanNormal7));



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

					stCount ++;



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

				dataset.setValue((eng_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.ENG_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "ENG");
				dataset.setValue((kis_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.KISWA_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "KIS");
				dataset.setValue((math_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.MATH_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "MAT");

				dataset.setValue((bio_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.BIO_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "BIO");
				dataset.setValue((chem_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.CHEM_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "CHE");
				dataset.setValue((phy_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.PHY_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "PHY");

				dataset.setValue((bs_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.BS_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "BS");
				dataset.setValue((comp_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.COMP_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "CMP");
				dataset.setValue((hmsc_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.H_S, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "HSC");
				dataset.setValue((agr_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.AGR_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "AGR");

				//eng,kis,math,   bio,chem,phy,   bs,comp,hsc,agr,  geo,cre,hist
				dataset.setValue((geo_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.GEO_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "GEO");
				dataset.setValue((cre_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.CRE_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "CRE");
				dataset.setValue((hist_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.HIST_UUID, classID,examConfig.getTerm(),examConfig.getYear())*100))*12, "Pnts", "HST");


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
					BarChartHeader.addElement(new Chunk(chartImage,15,-130));// margin left  ,  margin top
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

				classmean = totalclassmark/studentcount;


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

					subAnalysisTable.addCell(new Paragraph(halfUP.format(totalclassmark)+" ",timesRomanNormal7));

				}if(i == 1){
					subAnalysisTable.addCell(new Paragraph(studentcount+" ",timesRomanNormal7)); 
					subAnalysisTable.addCell(new Paragraph("MEAN ",timesRomanNormal7));
					//System.out.println("eng " + eng_grand_total + " / " + perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.ENG_UUID, classID,examConfig.getTerm(),examConfig.getYear()) +" = "+   eng_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.ENG_UUID, classID,examConfig.getTerm(),examConfig.getYear())));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(eng_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.ENG_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(kis_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.KISWA_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(math_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.MATH_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(bio_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.BIO_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(chem_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.CHEM_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(phy_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.PHY_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(bs_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.BS_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(comp_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.COMP_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(hmsc_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.H_S, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(agr_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.AGR_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(geo_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.GEO_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(cre_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.CRE_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(halfUP.format(hist_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.HIST_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(halfUP.format(classmean)+" ",timesRomanNormal7));
				}
				if(i == 2){
					subAnalysisTable.addCell(new Paragraph(" ",timesRomanNormal7)); 
					subAnalysisTable.addCell(new Paragraph("GRADE ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(computeGrade(eng_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.ENG_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(kis_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.KISWA_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(math_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.MATH_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(computeGrade(bio_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.BIO_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(chem_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.CHEM_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(phy_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.PHY_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(computeGrade(bs_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.BS_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(comp_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.COMP_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(hmsc_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.H_S, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(agr_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.AGR_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

					subAnalysisTable.addCell(new Paragraph(computeGrade(geo_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.GEO_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(cre_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.CRE_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));
					subAnalysisTable.addCell(new Paragraph(computeGrade(hist_grand_total/(perfomanceDAO.getSubjectCountPerClass(school.getUuid(), ExamConstants.HIST_UUID, classID,examConfig.getTerm(),examConfig.getYear())))+" ",timesRomanNormal7));

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
	 * @param score
	 * @return
	 */
	private String computeGrade(double score) { 
		double mean = score;
		String grade = "";
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
		Image imgLogo = null;

		try {
			imgLogo = Image.getInstance(realPath);
			imgLogo.scaleToFit(200, 200);
			imgLogo.setAlignment(Element.ALIGN_CENTER);

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

		return imgLogo;
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
