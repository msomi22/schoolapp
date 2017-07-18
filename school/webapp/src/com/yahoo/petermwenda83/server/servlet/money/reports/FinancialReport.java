/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.money.reports;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.result.PdfUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author peter
 *
 */ 
public class FinancialReport extends HttpServlet{
	
	final String STATUS_ACTIVE = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";


	//private Font bigFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD);
	private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLDITALIC);
	private Font normalText = new Font(Font.FontFamily.COURIER, 10,Font.UNDERLINE);
	private Document document;
	private PdfWriter writer;

	private Logger logger;
	ExamConfig examConfig;

	private String PDF_TITLE ="";
	private String PDF_SUBTITLE ="";

	Locale locale = new Locale("en","KE"); 
	NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

	private Cache schoolaccountCache;
	private static StudentFeeDAO studentFeeDAO;
	private static StudentDAO studentDAO;
	private static ExamConfigDAO examConfigDAO;
	private static TermFeeDAO termFeeDAO;

	String USER= "";
	String path ="";

	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		logger = Logger.getLogger(this.getClass());
		studentFeeDAO = StudentFeeDAO.getInstance();
		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
		studentDAO = StudentDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();

		USER = System.getProperty("user.name");
		path = "/home/"+USER+"/school/logo/logo.png";


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		response.setContentType("application/pdf");




		SchoolAccount school = new SchoolAccount();
		String schoolusername = "";

		if(session !=null){
			schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);

		}
		net.sf.ehcache.Element element;

		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (SchoolAccount) element.getObjectValue();
		}

		String pdfname =school.getUsername()+"financialReport.pdf";

		response.setHeader("Content-Disposition", "inline; filename= \"" +pdfname);

		examConfig = new ExamConfig();
		if(examConfigDAO.getExamConfig(school.getUuid()) !=null){
			examConfig = examConfigDAO.getExamConfig(school.getUuid());
		}



		TermFee termFee = new TermFee();
		if(termFeeDAO.getFee(school.getUuid(),examConfig.getTerm(),examConfig.getYear()) !=null){
			termFee = termFeeDAO.getFee(school.getUuid(),examConfig.getTerm(),examConfig.getYear());
		}





		PDF_TITLE = "FINANCIAL ANALYSIS REPORT \n";
		// +"TERM :" + examConfig.getTerm() + " YEAR :"+examConfig.getYear()+" TERM FEE "+ nf.format(termFee.getTermAmount())+"\n\n\n";

		PDF_SUBTITLE =     school.getSchoolName()+"\n"
				+ "P.O BOX "+school.getPostalAddress()+"\n" 
				+ ""+school.getTown().toUpperCase()+ " - KENYA\n" 
				+ "" + school.getMobile()+"\n"
				+ "" + school.getEmail()+"\n"; 

		document = new Document(PageSize.A4, 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());


			PdfUtil event = new PdfUtil();
			writer.setBoxSize("art", new Rectangle(36, 54, 459, 588));
			writer.setPageEvent(event);

			populatePDFDocument(school,examConfig,termFee,path);


		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}




	}


	/**
	 * @param school
	 * @param examConfig2
	 * @param termFee
	 * @param feeList
	 * @param path2
	 * @throws DocumentException
	 */
	private void populatePDFDocument(SchoolAccount school, ExamConfig examConfig2, TermFee termFee,
			 String path2) throws DocumentException {



		document.open();

		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("dd, MMM yyyy");



		BaseColor baseColor = new BaseColor(32,178,170);//maroon

		String formattedDate;
		Date date = new Date();
		formattedDate = formatter.format(date);

		Paragraph emptyline = new Paragraph(("                              "));


		Paragraph current = new Paragraph(("CURRENT YEAR FEE ANALYSIS"),smallBold);
		Paragraph termOne = new Paragraph(("TERM ONE FEE ANALYSIS"),smallBold);
		Paragraph termTwo = new Paragraph(("TERM TWO FEE ANALYSIS"),smallBold);
		Paragraph termThree = new Paragraph(("TERM THREE FEE ANALYSIS"),smallBold);

		Paragraph preface = new Paragraph();
		preface.add(createImage(path2));
		preface.add(new Paragraph(PDF_TITLE, smallBold));
		preface.add(new Paragraph(PDF_SUBTITLE, normalText));
		preface.add(new Paragraph("PRINTED ON :"+formattedDate + " FOR YEAR :"+examConfig.getYear()+"\n\n", smallBold));


		PdfPTable FinanceReportTable = new PdfPTable(2);  

		PdfPCell totalpaidHeader = new PdfPCell(new Paragraph("Year Total Fee",smallBold));
		totalpaidHeader.setBackgroundColor(baseColor);
		totalpaidHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell totalfeesHeader = new PdfPCell(new Paragraph("Year Total Paid",smallBold));
		totalfeesHeader.setBackgroundColor(baseColor);
		totalfeesHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		/*PdfPCell totalduesHeader = new PdfPCell(new Paragraph("Year Total Dues",smallBold));
		totalduesHeader.setBackgroundColor(baseColor);
		totalduesHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell totalOverPaysHeader = new PdfPCell(new Paragraph("Year Total OverPay",smallBold));
		totalOverPaysHeader.setBackgroundColor(baseColor);
		totalOverPaysHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
       */
		FinanceReportTable.addCell(totalpaidHeader);
		FinanceReportTable.addCell(totalfeesHeader);
		//FinanceReportTable.addCell(totalduesHeader);
		//FinanceReportTable.addCell(totalOverPaysHeader);
		FinanceReportTable.setWidthPercentage(100); 
		FinanceReportTable.setWidths(new int[]{60,60});   
		FinanceReportTable.setHorizontalAlignment(Element.ALIGN_LEFT);



		//TERM 1
		PdfPTable termOneTable = new PdfPTable(4); 
		//table here
		
		PdfPCell termoneExpectHeader = new PdfPCell(new Paragraph("Total Fee Term 1 ",smallBold));
		termoneExpectHeader.setBackgroundColor(baseColor);
		termoneExpectHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell termoneTotalHeader = new PdfPCell(new Paragraph("Total Paid Term 1",smallBold));
		termoneTotalHeader.setBackgroundColor(baseColor);
		termoneTotalHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell lasttermTotalDuesHeader = new PdfPCell(new Paragraph("Last Term Dues",smallBold));
		lasttermTotalDuesHeader.setBackgroundColor(baseColor);
		lasttermTotalDuesHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell lasttermTotalOverPayHeader = new PdfPCell(new Paragraph("Last Term Overpay",smallBold));
		lasttermTotalOverPayHeader.setBackgroundColor(baseColor);
		lasttermTotalOverPayHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		termOneTable.addCell(termoneExpectHeader);
		termOneTable.addCell(termoneTotalHeader);
		termOneTable.addCell(lasttermTotalDuesHeader);
		termOneTable.addCell(lasttermTotalOverPayHeader);
		
		termOneTable.setWidthPercentage(100); 
		termOneTable.setWidths(new int[]{60,60,60,60});   
		termOneTable.setHorizontalAlignment(Element.ALIGN_LEFT);

		//TERM 2
		PdfPTable termTwoTable = new PdfPTable(4); 
		//table here
		
		PdfPCell termtwoExpectHeader = new PdfPCell(new Paragraph("Total Term 2 Fee",smallBold));
		termtwoExpectHeader.setBackgroundColor(baseColor);
		termtwoExpectHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell termtwoTotalHeader = new PdfPCell(new Paragraph("Total Paid Term 2",smallBold));
		termtwoTotalHeader.setBackgroundColor(baseColor);
		termtwoTotalHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell lastterm2TotalDuesHeader = new PdfPCell(new Paragraph("Last Term Dues",smallBold));
		lastterm2TotalDuesHeader.setBackgroundColor(baseColor);
		lastterm2TotalDuesHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell lastterm2TotalOverPayHeader = new PdfPCell(new Paragraph("Last Term Overpay",smallBold));
		lastterm2TotalOverPayHeader.setBackgroundColor(baseColor);
		lastterm2TotalOverPayHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		termTwoTable.addCell(termtwoExpectHeader);
		termTwoTable.addCell(termtwoTotalHeader);
		termTwoTable.addCell(lastterm2TotalDuesHeader);
		termTwoTable.addCell(lastterm2TotalOverPayHeader);
		
		termTwoTable.setWidthPercentage(100); 
		termTwoTable.setWidths(new int[]{60,60,60,60});   
		termTwoTable.setHorizontalAlignment(Element.ALIGN_LEFT);

		//TERM 3
		PdfPTable termThreeTable = new PdfPTable(4); 

		//table here
		
		PdfPCell termthreeExpectHeader = new PdfPCell(new Paragraph("Total Term 3 Fee",smallBold));
		termthreeExpectHeader.setBackgroundColor(baseColor);
		termthreeExpectHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell termthreeTotalHeader = new PdfPCell(new Paragraph("Total Paid Term 3",smallBold));
		termthreeTotalHeader.setBackgroundColor(baseColor);
		termthreeTotalHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell lastterm3TotalDuesHeader = new PdfPCell(new Paragraph("Last Term Dues",smallBold));
		lastterm3TotalDuesHeader.setBackgroundColor(baseColor);
		lastterm3TotalDuesHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell lastterm3TotalOverPayHeader = new PdfPCell(new Paragraph("Last Term Overpay",smallBold));
		lastterm3TotalOverPayHeader.setBackgroundColor(baseColor);
		lastterm3TotalOverPayHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
	
		termThreeTable.addCell(termthreeExpectHeader);
		termThreeTable.addCell(termthreeTotalHeader);
		termThreeTable.addCell(lastterm3TotalDuesHeader);
		termThreeTable.addCell(lastterm3TotalOverPayHeader);
		
		termThreeTable.setWidthPercentage(100); 
		termThreeTable.setWidths(new int[]{60,60,60,60});   
		termThreeTable.setHorizontalAlignment(Element.ALIGN_LEFT);



		List<Student> studentList = new ArrayList<>(); 

		int studentCount = 0;
		if(studentList !=null){
			studentList = studentDAO.getAllStudentList(school.getUuid()); 
			for(Student stu : studentList) {
				if(StringUtils.equals(stu.getStatusUuid(),STATUS_ACTIVE)){
				stu.getUuid();
				studentCount ++;
				}
			}
		}
		
		

		//TERM 1
		double termOneAmount = 0;
		double termOneTotals = 0;
		double termOneFee = 0;
		double termOneTotalExpect = 0;
		
		double prevdues = 0;
		double prevoverpay = 0;
		
		double yeartotalfee = 0;
		double yeartotalpaid = 0;
		
		String currentyear = "";
		currentyear = examConfig.getYear();
		int currentyearint = Integer.parseInt(currentyear);
		int prevyear = currentyearint - 1;
		
		TermFee termoneFee = new TermFee();
		if(termFeeDAO.getFee(school.getUuid(),"1",examConfig.getYear()) !=null){
			termoneFee = termFeeDAO.getFee(school.getUuid(),"1",examConfig.getYear());
			termOneFee = termoneFee.getTermAmount();
		}

		List<StudentFee> studentfeeListTerm1 = new ArrayList<StudentFee>();
		if(studentFeeDAO.getStudentFeeList(school.getUuid(),"1",examConfig.getYear()) !=null){
			studentfeeListTerm1 = studentFeeDAO.getStudentFeeList(school.getUuid(),"1",examConfig.getYear());      	 

		}
		
		yeartotalpaid = 0;
		if(studentfeeListTerm1 !=null){
			for(StudentFee term1 : studentfeeListTerm1){
				termOneAmount = term1.getAmountPaid();
				termOneTotals +=termOneAmount;
				
			}
		}
		
		termOneTotalExpect = termOneFee * studentCount;
		yeartotalfee +=termOneTotalExpect;

		
		
		
		

		yeartotalpaid +=termOneTotals;
		//System.out.println("term 1="+yeartotalpaid);
		termOneTable.addCell(nf.format(termOneTotalExpect));
		termOneTable.addCell(nf.format(termOneTotals));
		termOneTable.addCell(nf.format(prevoverpay));//prevdues
		termOneTable.addCell(nf.format(prevdues));//prevoverpay
		

		//TERM 2

		double termTwoAmount = 0;
		double termTwoTotals = 0;
		double termTwoFee = 0;
		double termTwoTotalExpect = 0;
		
		


		TermFee termtwoFee = new TermFee();
		if(termFeeDAO.getFee(school.getUuid(),"2",examConfig.getYear()) !=null){
			termtwoFee = termFeeDAO.getFee(school.getUuid(),"2",examConfig.getYear());
			termTwoFee = termtwoFee.getTermAmount();
		}
        
		// TERM 2
		List<StudentFee> studentfeeListTerm2 = new ArrayList<StudentFee>();
		if(studentFeeDAO.getStudentFeeList(school.getUuid(),"2",examConfig.getYear()) !=null){
			studentfeeListTerm2 = studentFeeDAO.getStudentFeeList(school.getUuid(),"2",examConfig.getYear());      	 

		}





		if(studentfeeListTerm2 !=null){
			for(StudentFee term2 : studentfeeListTerm2){
				termTwoAmount = term2.getAmountPaid();
				termTwoTotals+=termTwoAmount;
				
			}
		}
		
		termTwoTotalExpect = termTwoFee * studentCount;
		yeartotalfee +=termTwoTotalExpect;
        
		yeartotalpaid +=termTwoTotals;
		//System.out.println("term 2="+yeartotalpaid);
		
		termTwoTable.addCell(nf.format(termTwoTotalExpect));
		termTwoTable.addCell(nf.format(termTwoTotals));
		termTwoTable.addCell(nf.format(prevoverpay));
		termTwoTable.addCell(nf.format(prevdues));
		
		
		//TERM 3
		double termThreeAmount = 0;
		double termThreeTotals = 0;
		double termThreeFee = 0;
		double termThreeTotalExpect = 0;
	
		
		TermFee termthreeFee = new TermFee();
		if(termFeeDAO.getFee(school.getUuid(),"3",examConfig.getYear()) !=null){
			termthreeFee = termFeeDAO.getFee(school.getUuid(),"3",examConfig.getYear());
			termThreeFee = termthreeFee.getTermAmount();
		}

         //TERM 3
		List<StudentFee> studentfeeListTerm3 = new ArrayList<StudentFee>();
		if(studentFeeDAO.getStudentFeeList(school.getUuid(),"3",examConfig.getYear()) !=null){
			studentfeeListTerm3 = studentFeeDAO.getStudentFeeList(school.getUuid(),"3",examConfig.getYear());      	 

		} 
		if(studentfeeListTerm3 !=null){
			for(StudentFee term3 : studentfeeListTerm3){
				termThreeAmount = term3.getAmountPaid();
				termThreeTotals +=termThreeAmount;
				
			}
		}
		
		
		termThreeTotalExpect = termThreeFee * studentCount;
		yeartotalfee +=termThreeTotalExpect;
		
       
		yeartotalpaid +=termThreeTotals;
		//System.out.println("term 3="+yeartotalpaid);
		termThreeTable.addCell(nf.format(termThreeTotalExpect));
		termThreeTable.addCell(nf.format(termThreeTotals));
		termThreeTable.addCell(nf.format(prevoverpay));
		termThreeTable.addCell(nf.format(prevdues));
		
		
		
		// CURRENT YEAR ANALYSIS 
		
		FinanceReportTable.addCell(nf.format(yeartotalfee));
		FinanceReportTable.addCell(nf.format(yeartotalpaid));
		//FinanceReportTable.addCell(nf.format(yeartotaloverpay));
		//FinanceReportTable.addCell(nf.format(yeartotaldues));


		//PREFACE
		document.add(emptyline);
		document.add(preface);
		
		

		//TERM 1
		document.add(emptyline);
		document.add(termOne);
		document.add(emptyline);
		document.add(termOneTable);
		//TERM 2
		document.add(emptyline);
		document.add(termTwo);
		document.add(emptyline);
		document.add(termTwoTable);
		//TERM 3
		document.add(emptyline);       
		document.add(termThree);
		document.add(emptyline);
		document.add(termThreeTable);
		
		//CURENT YEAR
	     document.add(emptyline);
		 document.add(current);
		 document.add(emptyline);
		 document.add(FinanceReportTable); 
		
		




		// step 5
		document.close();


	}





	/**
	 * @param realPath
	 * @return
	 */
	private Element createImage(String realPath) {
		Image imgLogo = null;

		try {
			imgLogo = Image.getInstance(realPath);
			imgLogo.scaleToFit(150, 150);
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
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -6769826759676209553L;

}
