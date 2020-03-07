/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.othermoney;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.othermoney.Otherstype;
import com.yahoo.petermwenda83.bean.othermoney.RevertedMoney;
import com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies;
import com.yahoo.petermwenda83.bean.othermoney.TermOtherMonies;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherstypeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.RevertedMoneyDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.money.StudentBalance;
import com.yahoo.petermwenda83.server.servlet.result.PdfUtil;
import com.yahoo.petermwenda83.server.servlet.util.LogUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/** 
 * @author peter
 *
 */
public class PrintStatement extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3176338567192522049L;


	SimpleDateFormat formatter = new SimpleDateFormat("dd, MMM yyyy");
	SimpleDateFormat yearformatter = new SimpleDateFormat("yyyy");
	BaseColor baseColor = new BaseColor(255,255,255);//while   32,178,170)
	BaseColor Colormagenta = new BaseColor(255,255,255);//  (176,196,222); magenta
	BaseColor Colorgrey = new BaseColor(128,128,128);//  (128,128,128)gray,grey
	//BaseColor baseColor = new BaseColor(32,178,170);//maroon
	//BaseColor Colormagenta = new BaseColor(176,196,222);//magenta
	

	private Cache schoolaccountCache;

	
	private Font normalText = new Font(Font.FontFamily.COURIER, 8,Font.BOLD);
	private Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
	
	private Document document;
	private PdfWriter writer;

	private Logger logger;

	private String PDF_TITLE ="";
	private String PDF_SUBTITLE ="";

	Locale locale = new Locale("en","KE"); 
	NumberFormat nf = NumberFormat.getCurrencyInstance(locale);


	private static StudentFeeDAO studentFeeDAO;
	private static StudentDAO studentDAO;
	private static ExamConfigDAO examConfigDAO;
	private static TermFeeDAO termFeeDAO;
	private static RoomDAO roomDAO;
	//private static StudentAmountDAO studentAmountDAO;
	private static RevertedMoneyDAO revertedMoneyDAO;

	private static StudentOtherMoniesDAO studentOtherMoniesDAO;


	TermOtherMonies termOtherMonies;
	StudentOtherMonies studentOtherMonies;
	Otherstype otherstype;

	String USER= "";
	String path ="";
	String pdfname = "";
	int finalYear = 0;
	
	String feeStudentType = "";
	String currentStudentType = "";

	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String> firstnameHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>();
	HashMap<String, String> roomHash = new HashMap<String, String>();
	HashMap<String, String> studentType = new HashMap<String, String>();
	LogUtil logUtil = new LogUtil();




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
		roomDAO = RoomDAO.getInstance();
		studentOtherMoniesDAO = StudentOtherMoniesDAO.getInstance();
		revertedMoneyDAO = RevertedMoneyDAO.getInstance();
		
		USER = System.getProperty("user.name");
		path = "/home/"+USER+"/school/logo/logo.png";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		response.setContentType("application/pdf");

		String studentuuid = StringUtils.trimToEmpty(request.getParameter("studentuuid"));

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

		Student stuudent = new Student();
		stuudent = studentDAO.getStudentByuuid(school.getUuid(), studentuuid);

		if(stuudent != null){
			studentAdmNoHash.put(stuudent.getUuid(),stuudent.getAdmno()); 
			
			 String formatedFirstname = StringUtils.capitalize(stuudent.getFirstname().toLowerCase());
			 String formatedLastname = StringUtils.capitalize(stuudent.getLastname().toLowerCase());
			 String formatedsurname = StringUtils.capitalize(stuudent.getSurname().toLowerCase());
			
			formatedFirstname = formatedFirstname.substring(0, Math.min(formatedFirstname.length(), 10));
			formatedLastname = formatedLastname.substring(0, Math.min(formatedLastname.length(), 10));
			formatedsurname = formatedsurname.substring(0, Math.min(formatedsurname.length(), 10));
			currentStudentType = stuudent.getStudentType();
			studentType.put(stuudent.getUuid(), stuudent.getStudentType()); 
			studNameHash.put(stuudent.getUuid(),formatedFirstname + " " + formatedLastname + " " + formatedsurname +"\n"); 
			firstnameHash.put(stuudent.getUuid(), formatedFirstname);

			pdfname =stuudent.getAdmno()+"_Fee_Statement.pdf"; 
			response.setHeader("Content-Disposition", "inline; filename= \"" +pdfname);

		}



		ExamConfig examConfig = new ExamConfig();
		if(examConfigDAO.getExamConfig(school.getUuid()) !=null){
			examConfig = examConfigDAO.getExamConfig(school.getUuid());
		}

		List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
		classroomList = roomDAO.getAllRooms(school.getUuid()); 
		for(ClassRoom c : classroomList){
			roomHash.put(c.getUuid() , c.getRoomName());
		}






		PDF_TITLE = "STUDENT FEES PAYMENT REPORT";

		PDF_SUBTITLE =     school.getSchoolName()+"\n"
				+ "P.O BOX "+school.getPostalAddress()+"\n" 
				+ ""+school.getTown().toUpperCase()+ " - Kenya\n" 
				+ "" + school.getMobile()+"\n"
				+ "" + school.getEmail()+"\n\n"; 

		document = new Document(PageSize.A4, 40, 40, 60, 60);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());


			PdfUtil event = new PdfUtil();
			writer.setBoxSize("art", new Rectangle(46, 46, 400, 400));
			writer.setPageEvent(event);

			if(school !=null && examConfig !=null && stuudent !=null){
				populatePDFDocument(school,examConfig,stuudent,path);
			}



		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}









	}

	private void populatePDFDocument(SchoolAccount school, ExamConfig examConfig2, Student stuudent,
			String path) {

		try {
			document.open();
			
			Paragraph preface = new Paragraph();
			preface.add(createImage(path));
			
			PdfPTable containerTable = new PdfPTable(2);  
			containerTable.setWidthPercentage(100); 
			containerTable.setWidths(new int[]{100,100}); 
			containerTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell contheader = new PdfPCell(); 
			//contheader = new PdfPCell(new Paragraph((PDF_TITLE) +"",normalText));
			contheader = new PdfPCell(new Paragraph((PDF_SUBTITLE) +"",normalText));
			contheader.setBackgroundColor(Colormagenta);
			contheader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell bodyheader = new PdfPCell();
			bodyheader = new PdfPCell(new Paragraph((PDF_TITLE + " FOR " + "\nADM N0 : " + studentAdmNoHash.get(stuudent.getUuid()) +"\n" + "STUDENT NAME : " + studNameHash.get(stuudent.getUuid()) + "Printed on: " + formatter.format(new Date()) + "\nTerm: " + examConfig2.getTerm()+" Year: " + examConfig2.getYear()),normalText));
			bodyheader.setBackgroundColor(Colormagenta);
			bodyheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			logUtil.writeLog("fee statement  " + studentAdmNoHash.get(stuudent.getUuid())); 
			containerTable.addCell(contheader);
			containerTable.addCell(bodyheader);
			Paragraph empline = new Paragraph(("                              "));
			document.add(preface);
			document.add(containerTable);
			document.add(empline);
			
			Date admissionDate = stuudent.getAdmissionDate();
			String regterm = stuudent.getRegTerm();

			String admYear = yearformatter.format(admissionDate);
			finalYear = stuudent.getFinalYear();

			compute(admYear,regterm,school,stuudent,examConfig2);
			
			//Reverted money
			PdfPCell mycountHeader = new PdfPCell(new Paragraph("S.N",boldFont));
			mycountHeader.setBackgroundColor(baseColor);
			mycountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell ptypeHeader = new PdfPCell(new Paragraph("Payment Type",boldFont));
			ptypeHeader.setBackgroundColor(baseColor);
			ptypeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell amountrevertedHeader = new PdfPCell(new Paragraph("Amount Reverted",boldFont));
			amountrevertedHeader.setBackgroundColor(baseColor);
			amountrevertedHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell oTernHeader = new PdfPCell(new Paragraph("Term",boldFont));
			oTernHeader.setBackgroundColor(baseColor);
			oTernHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell pYearHeader = new PdfPCell(new Paragraph("Year",boldFont));
			pYearHeader.setBackgroundColor(baseColor);
			pYearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPTable reveertTable;

			reveertTable = new PdfPTable(5); 
			reveertTable.addCell(mycountHeader);
			reveertTable.addCell(ptypeHeader);
			reveertTable.addCell(amountrevertedHeader);
			reveertTable.addCell(oTernHeader);
			reveertTable.addCell(pYearHeader);
			reveertTable.setWidthPercentage(100); 
			reveertTable.setWidths(new int[]{8,35,40,35,35});   
			reveertTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			OtherstypeDAO otherstypeDAO = OtherstypeDAO.getInstance();
			List<Otherstype> othertypeList = new ArrayList<Otherstype>(); 
			othertypeList = otherstypeDAO.gettypeList(school.getUuid());  
			HashMap<String, String> moneytypeHash = new HashMap<String, String>(); 

			if(othertypeList !=null){
				for(Otherstype om : othertypeList){
					moneytypeHash.put(om.getUuid(),om.getType());
				}
			}
			
			List<RevertedMoney> revertedList = new ArrayList<RevertedMoney>(); 
			revertedList = revertedMoneyDAO.getRevertedMoneyList(stuudent.getUuid());
			
			int rcount = 1;
			for(RevertedMoney rmoney : revertedList){
				reveertTable.addCell(new Paragraph(rcount+"",smallBold));
				reveertTable.addCell(new Paragraph(moneytypeHash.get(rmoney.getOtherstypeUuid())+"",smallBold));
				reveertTable.addCell(new Paragraph(nf.format(rmoney.getAmount())+"",smallBold));
				reveertTable.addCell(new Paragraph(rmoney.getTerm()+"",smallBold));
				reveertTable.addCell(new Paragraph(rmoney.getYear()+"",smallBold));
				rcount++;
			}
			
			Paragraph emptyline = new Paragraph(("                              "));
			document.add(emptyline); 
			document.add(reveertTable); 

			document.close();
		}
		catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		 return;
 
	}





	private void compute(String admYear, String regterm, SchoolAccount school, Student stuudent, ExamConfig examConfig2) throws DocumentException {

		int nextterm = 0;
		int nextyear =0;

		//check what  term , the initial term is,,,, either ... 1,2 or 3

		//if initial term is 1, compute and move to term 2
		if(Integer.parseInt(regterm) == 1){
		    double lastTermfeeBal = 0;
		  
			//table here
			PdfPCell countHeader = new PdfPCell(new Paragraph("S.N",boldFont));
			countHeader.setBackgroundColor(baseColor);
			countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell amountHeader = new PdfPCell(new Paragraph("Amnt Paid",boldFont));
			amountHeader.setBackgroundColor(baseColor);
			amountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell transactionHeader = new PdfPCell(new Paragraph("Transaction Number",boldFont));
			transactionHeader.setBackgroundColor(baseColor);
			transactionHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell paydateHeader = new PdfPCell(new Paragraph("Date paid",boldFont));
			paydateHeader.setBackgroundColor(baseColor);
			paydateHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell termHeader = new PdfPCell(new Paragraph("Term " + "1",boldFont));
			termHeader.setBackgroundColor(Colorgrey);
			termHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell yearHeader = new PdfPCell(new Paragraph("Year " + admYear,boldFont));
			yearHeader.setBackgroundColor(Colorgrey);
			yearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable myTable = new PdfPTable(6); 
			myTable.addCell(countHeader);
			myTable.addCell(amountHeader);
			myTable.addCell(transactionHeader);
			myTable.addCell(paydateHeader);
			myTable.addCell(termHeader);
			myTable.addCell(yearHeader);//


			myTable.setWidthPercentage(100); 
			myTable.setWidths(new int[]{8,40,80,40,12,15});   
			myTable.setHorizontalAlignment(Element.ALIGN_LEFT);


			//Get payments for the 'start year' and 'start term' 
			List<StudentFee> list = new ArrayList<>();               
			list = studentFeeDAO.getStudentFeeByStudentUuidList(school.getUuid(),stuudent.getUuid(),regterm,admYear);

			int mycount = 1;
			currentStudentType = "";feeStudentType ="";
			double totalpaid = 0;
			if(list !=null){
				for(StudentFee fee : list){

					myTable.addCell(new Paragraph(mycount+"",smallBold));
					myTable.addCell(new Paragraph(nf.format(fee.getAmountPaid())+" - " + fee.getStudentType(),smallBold));
					myTable.addCell(new Paragraph(fee.getTransactionID()+"",smallBold));
					myTable.addCell(new Paragraph(formatter.format(fee.getDatePaid())+"",smallBold));
					myTable.addCell(new Paragraph(regterm,smallBold));
					myTable.addCell(new Paragraph(admYear,smallBold));
					feeStudentType = fee.getStudentType();
					totalpaid +=fee.getAmountPaid();
					mycount++;
				}




				TermFee termfee = new TermFee();
				if(termFeeDAO.getFee(school.getUuid(),regterm,admYear) !=null){
					
					double other_m_amount = 0;
					double other_m_totals = 0;
					
					List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
					stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);
					if(stuOthermoniList !=null){
						
						for(StudentOtherMonies som  : stuOthermoniList){
							//other_m_amount = som.getAmountPiad();
							other_m_totals += som.getAmountPiad();
						}
					}
					
					
					termfee = termFeeDAO.getFee(school.getUuid(),regterm,admYear);
					
					double termfees = 0;
					//TODO term fee depending on student type
                    if(StringUtils.equals(feeStudentType, "Boarder")) {
                    	lastTermfeeBal = (( termfee.getTermAmount() + other_m_totals) - totalpaid);
                    	termfees = termfee.getTermAmount();
                    }else if(StringUtils.equals(feeStudentType, "Day")) {
						lastTermfeeBal = (( termfee.getDayAmount() + other_m_totals) - totalpaid);
						termfees = termfee.getDayAmount();
					}
                    
                    /*StudentBalance studentBalance = new StudentBalance();
            		double feeBalance =  studentBalance.findBalance(termFeeDAO, examConfigDAO, studentFeeDAO,
            				studentOtherMoniesDAO, stuudent.getAdmissionDate(), 
            				stuudent.getRegTerm(), stuudent.getUuid(), stuudent.getSchoolAccountUuid(), stuudent.getFinalYear());*/
					
					
					PdfPCell closeHeader = new PdfPCell(new Paragraph("Total Paid = " + nf.format( totalpaid) +""
							+ "               " + " Term Fee = " +nf.format(   termfees   )    +""//lastTermfeeBal
							+ "               " + " Fee Balance = " +nf.format(lastTermfeeBal), smallBold));
					//closeHeader.setBackgroundColor(baseColor);
					closeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
					closeHeader.setColspan(6); 		

					myTable.addCell(closeHeader);

				}
			}


			//START
			PdfPCell mycountHeader = new PdfPCell(new Paragraph("S.N",boldFont));
			mycountHeader.setBackgroundColor(baseColor);
			mycountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell ptypeHeader = new PdfPCell(new Paragraph("Payment Type",boldFont));
			ptypeHeader.setBackgroundColor(baseColor);
			ptypeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell amountpaidHeader = new PdfPCell(new Paragraph("Amount",boldFont));
			amountpaidHeader.setBackgroundColor(baseColor);
			amountpaidHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell oTernHeader = new PdfPCell(new Paragraph("Term ",boldFont));
			oTernHeader.setBackgroundColor(baseColor);
			oTernHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell pYearHeader = new PdfPCell(new Paragraph("Year" ,boldFont));
			pYearHeader.setBackgroundColor(baseColor);
			pYearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable OtherPayTable;

			OtherPayTable = new PdfPTable(5); 
			OtherPayTable.addCell(mycountHeader);
			OtherPayTable.addCell(ptypeHeader);
			OtherPayTable.addCell(amountpaidHeader);
			OtherPayTable.addCell(oTernHeader);
			OtherPayTable.addCell(pYearHeader);
			OtherPayTable.setWidthPercentage(100); 
			OtherPayTable.setWidths(new int[]{8,35,40,35,35});   
			OtherPayTable.setHorizontalAlignment(Element.ALIGN_LEFT);

			OtherstypeDAO otherstypeDAO = OtherstypeDAO.getInstance();
			List<Otherstype> othertypeList = new ArrayList<Otherstype>(); 
			othertypeList = otherstypeDAO.gettypeList(school.getUuid());  
			HashMap<String, String> moneytypeHash = new HashMap<String, String>(); 

			if(othertypeList !=null){
				for(Otherstype om : othertypeList){
					moneytypeHash.put(om.getUuid(),om.getType());
				}
			}





			List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
			stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);


			String somtype = "";
			int countOther = 1;
			if(stuOthermoniList !=null){
				for(StudentOtherMonies som  : stuOthermoniList){

					somtype = moneytypeHash.get(som.getOtherstypeUuid()); 

					OtherPayTable.addCell(new Paragraph(countOther+"",smallBold));
					OtherPayTable.addCell(new Paragraph(StringUtils.capitalize(somtype.toLowerCase())+"",smallBold));
					OtherPayTable.addCell(new Paragraph(nf.format(som.getAmountPiad())+"",smallBold));
					OtherPayTable.addCell(new Paragraph(som.getTerm()+"",smallBold));
					OtherPayTable.addCell(new Paragraph(som.getYear()+"",smallBold));

					countOther++;
				}
			}

			//END

			//document.add(preface);
			Paragraph emptyline = new Paragraph(("                              "));
			document.add(OtherPayTable); 
			document.add(myTable); 
			document.add(emptyline);
			
           
            	nextyear = Integer.parseInt(admYear);
    			nextterm = 2;
            	cmputeMoveTerm2(nextterm,nextyear,school,stuudent,examConfig2,lastTermfeeBal); // 2 , 2016
           
		}


		//if initial term is 2 , compute and move to term 3
		if(Integer.parseInt(regterm) == 2){
			  
			    double lastTermfeeBal;
			    lastTermfeeBal = 0;

			//table here
			PdfPCell countHeader = new PdfPCell(new Paragraph("S.N",boldFont));
			countHeader.setBackgroundColor(baseColor);
			countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell amountHeader = new PdfPCell(new Paragraph("Amnt Paid",boldFont));
			amountHeader.setBackgroundColor(baseColor);
			amountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell transactionHeader = new PdfPCell(new Paragraph("Transaction Number",boldFont));
			transactionHeader.setBackgroundColor(baseColor);
			transactionHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell paydateHeader = new PdfPCell(new Paragraph("Date paid",boldFont));
			paydateHeader.setBackgroundColor(baseColor);
			paydateHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell termHeader = new PdfPCell(new Paragraph("Term " + "2",boldFont));
			termHeader.setBackgroundColor(Colorgrey);
			termHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell yearHeader = new PdfPCell(new Paragraph("Year " + admYear,boldFont));
			yearHeader.setBackgroundColor(Colorgrey);
			yearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable myTable = new PdfPTable(6); 
			myTable.addCell(countHeader);
			myTable.addCell(amountHeader);
			myTable.addCell(transactionHeader);
			myTable.addCell(paydateHeader);
			myTable.addCell(termHeader);
			myTable.addCell(yearHeader);//


			myTable.setWidthPercentage(100); 
			myTable.setWidths(new int[]{8,40,80,40,12,15});   
			myTable.setHorizontalAlignment(Element.ALIGN_LEFT);


			//Get payments for the 'start year' and 'start term' 
			List<StudentFee> list = new ArrayList<>();               
			list = studentFeeDAO.getStudentFeeByStudentUuidList(school.getUuid(),stuudent.getUuid(),regterm,admYear);

			int mycount = 1;
			currentStudentType = "";feeStudentType ="";
			double totalpaid = 0;
			if(list !=null){
				for(StudentFee fee : list){

					myTable.addCell(new Paragraph(mycount+"",smallBold));
					myTable.addCell(new Paragraph(nf.format(fee.getAmountPaid())+" - " + fee.getStudentType(),smallBold));
					myTable.addCell(new Paragraph(fee.getTransactionID()+"",smallBold));
					myTable.addCell(new Paragraph(formatter.format(fee.getDatePaid())+"",smallBold));
					myTable.addCell(new Paragraph(regterm,smallBold));
					myTable.addCell(new Paragraph(admYear,smallBold));

					feeStudentType = fee.getStudentType();
					totalpaid +=fee.getAmountPaid();
					mycount++;
				}


				TermFee termfee = new TermFee();
				if(termFeeDAO.getFee(school.getUuid(),regterm,admYear) !=null){
					
					double other_m_amount = 0;
					double other_m_totals = 0;
					
					List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
					stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);
					if(stuOthermoniList !=null){
						
						for(StudentOtherMonies som  : stuOthermoniList){
							other_m_amount = som.getAmountPiad();
							other_m_totals +=other_m_amount;
						}
					}
					
					termfee = termFeeDAO.getFee(school.getUuid(),regterm,admYear);
					double termfees = 0;
					//TODO term fee depending on student type
                    if(StringUtils.equals(feeStudentType, "Boarder")) {
                    	lastTermfeeBal = (( termfee.getTermAmount() + other_m_totals) - totalpaid);
                    	termfees = termfee.getTermAmount();
                    }else if(StringUtils.equals(feeStudentType, "Day")) {
						lastTermfeeBal = (( termfee.getDayAmount() + other_m_totals) - totalpaid);
						termfees = termfee.getDayAmount();
					}
					
					
					PdfPCell closeHeader = new PdfPCell(new Paragraph("Total Paid = " + nf.format( totalpaid) +""
							+ "               " + " Term Fee = " +nf.format(   termfees   )    +""//lastTermfeeBal
							+ "               " + " Fee Balance = " +nf.format(lastTermfeeBal), smallBold));
					//closeHeader.setBackgroundColor(baseColor);
					closeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
					closeHeader.setColspan(6); 		

					myTable.addCell(closeHeader);


				}
			}







			//START
			PdfPCell mycountHeader = new PdfPCell(new Paragraph("S.N",boldFont));
			mycountHeader.setBackgroundColor(baseColor);
			mycountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell ptypeHeader = new PdfPCell(new Paragraph("Payment Type",boldFont));
		    ptypeHeader.setBackgroundColor(baseColor);
			ptypeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell amountpaidHeader = new PdfPCell(new Paragraph("Amount",boldFont));
			amountpaidHeader.setBackgroundColor(baseColor);
			amountpaidHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell oTernHeader = new PdfPCell(new Paragraph("Term",boldFont));
			oTernHeader.setBackgroundColor(baseColor);
			oTernHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell pYearHeader = new PdfPCell(new Paragraph("Year",boldFont));
			pYearHeader.setBackgroundColor(baseColor);
			pYearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable OtherPayTable;

			OtherPayTable = new PdfPTable(5); 
			OtherPayTable.addCell(mycountHeader);
			OtherPayTable.addCell(ptypeHeader);
			OtherPayTable.addCell(amountpaidHeader);
			OtherPayTable.addCell(oTernHeader);
			OtherPayTable.addCell(pYearHeader);
			OtherPayTable.setWidthPercentage(100); 
			OtherPayTable.setWidths(new int[]{8,35,40,35,35});   
			OtherPayTable.setHorizontalAlignment(Element.ALIGN_LEFT);

			OtherstypeDAO otherstypeDAO = OtherstypeDAO.getInstance();
			List<Otherstype> othertypeList = new ArrayList<Otherstype>(); 
			othertypeList = otherstypeDAO.gettypeList(school.getUuid());  
			HashMap<String, String> moneytypeHash = new HashMap<String, String>(); 
			if(othertypeList !=null){
				for(Otherstype om : othertypeList){
					moneytypeHash.put(om.getUuid(),om.getType());
				}
			}
			List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
			if(studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear) !=null){
				stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);
			}  


			String somtype = "";
			int countOther = 1;
			if(stuOthermoniList !=null){
				for(StudentOtherMonies som  : stuOthermoniList){

					somtype = moneytypeHash.get(som.getOtherstypeUuid()); 

					OtherPayTable.addCell(new Paragraph(countOther+"",smallBold));
					OtherPayTable.addCell(new Paragraph(StringUtils.capitalize(somtype.toLowerCase())+"",smallBold));
					OtherPayTable.addCell(new Paragraph(nf.format(som.getAmountPiad())+"",smallBold));
					OtherPayTable.addCell(new Paragraph(som.getTerm()+"",smallBold));
					OtherPayTable.addCell(new Paragraph(som.getYear()+"",smallBold));

					countOther++;
				}
			}

			//END
			//document.add(preface);
			Paragraph emptyline = new Paragraph(("                              "));
			document.add(OtherPayTable); 
			document.add(myTable); 
			document.add(emptyline);


			nextyear = Integer.parseInt(admYear);
			nextterm = 3;

			cmputeMoveTerm3(nextterm,nextyear,school,stuudent,examConfig2,lastTermfeeBal);  



		}


		//if initial term is 3, compute and move to term 1 next year , term 1 , if possible
		if(Integer.parseInt(regterm) == 3){
			
			  
		    double lastTermfeeBal;
		    lastTermfeeBal = 0;



			//table here
			PdfPCell countHeader = new PdfPCell(new Paragraph("S.N",boldFont));
			countHeader.setBackgroundColor(baseColor);
			countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell amountHeader = new PdfPCell(new Paragraph("Amnt Paid",boldFont));
			amountHeader.setBackgroundColor(baseColor);
			amountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell transactionHeader = new PdfPCell(new Paragraph("Transaction Number",boldFont));
			transactionHeader.setBackgroundColor(baseColor);
			transactionHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell paydateHeader = new PdfPCell(new Paragraph("Date paid",boldFont));
			paydateHeader.setBackgroundColor(baseColor);
			paydateHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell termHeader = new PdfPCell(new Paragraph("Term " + "3",boldFont));
			termHeader.setBackgroundColor(Colorgrey);
			termHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell yearHeader = new PdfPCell(new Paragraph("Year " + admYear,boldFont));
			yearHeader.setBackgroundColor(Colorgrey);
			yearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable myTable = new PdfPTable(6); 
			myTable.addCell(countHeader);
			myTable.addCell(amountHeader);
			myTable.addCell(transactionHeader);
			myTable.addCell(paydateHeader);
			myTable.addCell(termHeader);
			myTable.addCell(yearHeader);//


			myTable.setWidthPercentage(100); 
			myTable.setWidths(new int[]{8,40,80,40,12,15});   
			myTable.setHorizontalAlignment(Element.ALIGN_LEFT);


			//Get payments for the 'start year' and 'start term' 
			List<StudentFee> list = new ArrayList<>();               
			list = studentFeeDAO.getStudentFeeByStudentUuidList(school.getUuid(),stuudent.getUuid(),regterm,admYear);

			int mycount = 1;
			currentStudentType = "";feeStudentType ="";
			double totalpaid = 0;
			if(list !=null){
				for(StudentFee fee : list){

					myTable.addCell(new Paragraph(mycount+"",smallBold));
					myTable.addCell(new Paragraph(nf.format(fee.getAmountPaid())+" - " + fee.getStudentType(),smallBold));
					myTable.addCell(new Paragraph(fee.getTransactionID()+"",smallBold));
					myTable.addCell(new Paragraph(formatter.format(fee.getDatePaid())+"",smallBold));
					myTable.addCell(new Paragraph(regterm,smallBold));
					myTable.addCell(new Paragraph(admYear,smallBold));

					feeStudentType = fee.getStudentType();
					totalpaid +=fee.getAmountPaid();
					mycount++;
				}


				TermFee termfee = new TermFee();
				if(termFeeDAO.getFee(school.getUuid(),regterm,admYear) !=null){
					
					double other_m_amount = 0;
					double other_m_totals = 0;
					
					List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
					stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);
					if(stuOthermoniList !=null){
						
						for(StudentOtherMonies som  : stuOthermoniList){
							other_m_amount = som.getAmountPiad();
							other_m_totals +=other_m_amount;
						}
					}
					
					
					termfee = termFeeDAO.getFee(school.getUuid(),regterm,admYear);
					
					double termfees = 0;
					//TODO term fee depending on student type
                    if(StringUtils.equals(feeStudentType, "Boarder")) {
                    	lastTermfeeBal = (( termfee.getTermAmount() + other_m_totals) - totalpaid);
                    	termfees = termfee.getTermAmount();
                    }else if(StringUtils.equals(feeStudentType, "Day")) {
						lastTermfeeBal = (( termfee.getDayAmount() + other_m_totals) - totalpaid);
						termfees = termfee.getDayAmount();
					}
					
					
					PdfPCell closeHeader = new PdfPCell(new Paragraph("Total Paid = " + nf.format( totalpaid) +""
							+ "               " + " Term Fee = " +nf.format(   termfees   )    +""//lastTermfeeBal
							+ "               " + " Fee Balance = " +nf.format(lastTermfeeBal), smallBold));
					//closeHeader.setBackgroundColor(baseColor);
					closeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
					closeHeader.setColspan(6); 		

					myTable.addCell(closeHeader);


				}
			}



			//START
			PdfPCell mycountHeader = new PdfPCell(new Paragraph("S.N",boldFont));
			mycountHeader.setBackgroundColor(baseColor);
			mycountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell ptypeHeader = new PdfPCell(new Paragraph("Payment Type",boldFont));
			ptypeHeader.setBackgroundColor(baseColor);
			ptypeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell amountpaidHeader = new PdfPCell(new Paragraph("Amount",boldFont));
			amountpaidHeader.setBackgroundColor(baseColor);
			amountpaidHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell oTernHeader = new PdfPCell(new Paragraph("Term",boldFont));
			oTernHeader.setBackgroundColor(baseColor);
			oTernHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell pYearHeader = new PdfPCell(new Paragraph("Year",boldFont));
			pYearHeader.setBackgroundColor(baseColor);
			pYearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable OtherPayTable;

			OtherPayTable = new PdfPTable(5); 
			OtherPayTable.addCell(mycountHeader);
			OtherPayTable.addCell(ptypeHeader);
			OtherPayTable.addCell(amountpaidHeader);
			OtherPayTable.addCell(oTernHeader);
			OtherPayTable.addCell(pYearHeader);
			OtherPayTable.setWidthPercentage(100); 
			OtherPayTable.setWidths(new int[]{8,35,40,35,35});   
			OtherPayTable.setHorizontalAlignment(Element.ALIGN_LEFT);

			OtherstypeDAO otherstypeDAO = OtherstypeDAO.getInstance();
			List<Otherstype> othertypeList = new ArrayList<Otherstype>(); 
			othertypeList = otherstypeDAO.gettypeList(school.getUuid());  
			HashMap<String, String> moneytypeHash = new HashMap<String, String>(); 
			if(othertypeList !=null){
				for(Otherstype om : othertypeList){
					moneytypeHash.put(om.getUuid(),om.getType());
				}
			}
			List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
			if(studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear) !=null){
				stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);
			}  


			String somtype = "";
			int countOther = 1;
			if(stuOthermoniList !=null){
				for(StudentOtherMonies som  : stuOthermoniList){

					somtype = moneytypeHash.get(som.getOtherstypeUuid()); 

					OtherPayTable.addCell(new Paragraph(countOther+"",smallBold));
					OtherPayTable.addCell(new Paragraph(StringUtils.capitalize(somtype.toLowerCase())+"",smallBold));
					OtherPayTable.addCell(new Paragraph(nf.format(som.getAmountPiad())+"",smallBold));
					OtherPayTable.addCell(new Paragraph(som.getTerm()+"",smallBold));
					OtherPayTable.addCell(new Paragraph(som.getYear()+"",smallBold));
					
					countOther++;
				}
			}

			//END
			//document.add(preface);
			Paragraph emptyline = new Paragraph(("                              "));
			document.add(OtherPayTable); 
			document.add(myTable); 
			document.add(emptyline);


			nextterm = 1;
			nextyear = Integer.parseInt(admYear) + 1;
			computeNext(nextterm,nextyear,school,stuudent,examConfig2,lastTermfeeBal);

		}


	}

	/**
	 * @param nextterm
	 * @param nextyear
	 * @param school
	 * @param stuudent
	 * @param examConfig2
	 * @param newBal 
	 * @throws DocumentException
	 */
	private void computeNext(int nextterm, int nextyear, SchoolAccount school, Student stuudent, ExamConfig examConfig2, double newBal) throws DocumentException {
     //System.out.println("Balance Token="+newBal); admissionYear  && finalYr <=4 
		int finalYr = 0;
		finalYr = finalYear; 
			
		if(nextyear <= Integer.parseInt(examConfig2.getYear()) && nextyear <= finalYr){ 
			if(nextterm <= Integer.parseInt(examConfig2.getTerm())){ 
				computeTerm1(nextterm,nextyear,school,stuudent,examConfig2,newBal); 
			}
		}
	}



	/**
	 * @param nextterm
	 * @param nextyear
	 * @param school
	 * @param stuudent
	 * @param examConfig2
	 * @param newBal2 
	 * @throws DocumentException
	 */
	private void computeTerm1(int nextterm, int nextyear, SchoolAccount school, Student stuudent, ExamConfig examConfig2, double newBal2) throws DocumentException {

		String regterm = Integer.toString(nextterm);
		String admYear = Integer.toString(nextyear);
		double lastTermfeeBal;
		lastTermfeeBal = newBal2;

		//table here
		PdfPCell countHeader = new PdfPCell(new Paragraph("S.N",boldFont));
		countHeader.setBackgroundColor(baseColor);
		countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell amountHeader = new PdfPCell(new Paragraph("Amnt Paid",boldFont));
		amountHeader.setBackgroundColor(baseColor);
		amountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell transactionHeader = new PdfPCell(new Paragraph("Transaction Number",boldFont));
		transactionHeader.setBackgroundColor(baseColor);
		transactionHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell paydateHeader = new PdfPCell(new Paragraph("Date paid",boldFont));
		paydateHeader.setBackgroundColor(baseColor);
		paydateHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell termHeader = new PdfPCell(new Paragraph("Term " + regterm,boldFont));
		termHeader.setBackgroundColor(Colorgrey);
		termHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell yearHeader = new PdfPCell(new Paragraph("Year " + admYear,boldFont));
		yearHeader.setBackgroundColor(Colorgrey);
		yearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPTable myTable = new PdfPTable(6); 
		myTable.addCell(countHeader);
		myTable.addCell(amountHeader);
		myTable.addCell(transactionHeader);
		myTable.addCell(paydateHeader);
		myTable.addCell(termHeader);
		myTable.addCell(yearHeader);//


		myTable.setWidthPercentage(100); 
		myTable.setWidths(new int[]{8,40,80,40,12,15});   
		myTable.setHorizontalAlignment(Element.ALIGN_LEFT);

		double newBal = 0;
		//Get payments for the 'start year' and 'start term' 
		List<StudentFee> list = new ArrayList<>();               
		list = studentFeeDAO.getStudentFeeByStudentUuidList(school.getUuid(),stuudent.getUuid(),regterm,admYear);

		int mycount = 1;
		currentStudentType = "";feeStudentType ="";
		double totalpaid = 0;
		if(list !=null){
			for(StudentFee fee : list){

				myTable.addCell(new Paragraph(mycount+"",smallBold));
				myTable.addCell(new Paragraph(nf.format(fee.getAmountPaid())+" - " + fee.getStudentType(),smallBold));
				myTable.addCell(new Paragraph(fee.getTransactionID()+"",smallBold));
				myTable.addCell(new Paragraph(formatter.format(fee.getDatePaid())+"",smallBold));
				myTable.addCell(new Paragraph(regterm,smallBold));
				myTable.addCell(new Paragraph(admYear,smallBold));

				feeStudentType = fee.getStudentType();
				totalpaid +=fee.getAmountPaid();
				mycount++;
			}

			double prevtermbalance = 0;

			TermFee termFeenew = new TermFee();
			if(termFeeDAO.getFee(school.getUuid(),"1",admYear) !=null){
				termFeenew = termFeeDAO.getFee(school.getUuid(),"1",admYear);
			}


			double other_m_amount = 0;
			double other_m_totals = 0;
			
			List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
			stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);
			if(stuOthermoniList !=null){
				
				for(StudentOtherMonies som  : stuOthermoniList){
					other_m_amount = som.getAmountPiad();
					other_m_totals +=other_m_amount;
				}
			}
			double carriedForward = 0;
		
			carriedForward = lastTermfeeBal;
			lastTermfeeBal = 0;
			
			double termfee = 0;
			currentStudentType = studentType.get(stuudent.getUuid()); 
			//TODO term fee depending on student type  currentStudentType
			 //System.out.println(currentStudentType);
            if(StringUtils.equals(feeStudentType, "Boarder")) {
            	lastTermfeeBal = (termFeenew.getTermAmount() - prevtermbalance - totalpaid + other_m_totals);
				termfee = termFeenew.getTermAmount();
			}else if(StringUtils.equals(feeStudentType, "Day")) {
				lastTermfeeBal = (termFeenew.getDayAmount() - prevtermbalance - totalpaid + other_m_totals);
				termfee = termFeenew.getDayAmount();
			}else{
				if(StringUtils.equals(currentStudentType, "Boarder")){
					termfee = termFeenew.getTermAmount();
					lastTermfeeBal = termfee;
				}else{
					termfee = termFeenew.getDayAmount();
					lastTermfeeBal = termfee;
				}
				
			}
			
			
			newBal = carriedForward + lastTermfeeBal;
			
			PdfPCell closeHeader = new PdfPCell(new Paragraph("Total Paid = " + nf.format(totalpaid) +""
					+ "                " + " Carried Forward = "+nf.format(carriedForward) +""
					+ "                " + " Term Fee = "+nf.format(termfee) +" "//
					+ "                " + " Fee Balance = " +nf.format(newBal) , smallBold));//
			//closeHeader.setBackgroundColor(baseColor);
			closeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			closeHeader.setColspan(6); 

			myTable.addCell(closeHeader);




		}





		//START
		PdfPCell mycountHeader = new PdfPCell(new Paragraph("S.N",boldFont));
		mycountHeader.setBackgroundColor(baseColor);
		mycountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell ptypeHeader = new PdfPCell(new Paragraph("Payment Type",boldFont));
		ptypeHeader.setBackgroundColor(baseColor);
		ptypeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell amountpaidHeader = new PdfPCell(new Paragraph("Amount",boldFont));
		amountpaidHeader.setBackgroundColor(baseColor);
		amountpaidHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell oTernHeader = new PdfPCell(new Paragraph("Term",boldFont));
		oTernHeader.setBackgroundColor(baseColor);
		oTernHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell pYearHeader = new PdfPCell(new Paragraph("Year",boldFont));
		pYearHeader.setBackgroundColor(baseColor);
		pYearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPTable OtherPayTable;

		OtherPayTable = new PdfPTable(5); 
		OtherPayTable.addCell(mycountHeader);
		OtherPayTable.addCell(ptypeHeader);
		OtherPayTable.addCell(amountpaidHeader);
		OtherPayTable.addCell(oTernHeader);
		OtherPayTable.addCell(pYearHeader);
		OtherPayTable.setWidthPercentage(100); 
		OtherPayTable.setWidths(new int[]{8,35,40,35,35});   
		OtherPayTable.setHorizontalAlignment(Element.ALIGN_LEFT);

		OtherstypeDAO otherstypeDAO = OtherstypeDAO.getInstance();
		List<Otherstype> othertypeList = new ArrayList<Otherstype>(); 
		othertypeList = otherstypeDAO.gettypeList(school.getUuid());  
		HashMap<String, String> moneytypeHash = new HashMap<String, String>(); 
		if(othertypeList !=null){
			for(Otherstype om : othertypeList){
				moneytypeHash.put(om.getUuid(),om.getType());
			}
		}
		List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
		if(studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear) !=null){
			stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);
		}  


		String somtype = "";
		int countOther = 1;
		if(stuOthermoniList !=null){
			for(StudentOtherMonies som  : stuOthermoniList){

				somtype = moneytypeHash.get(som.getOtherstypeUuid()); 

				OtherPayTable.addCell(new Paragraph(countOther+"",smallBold));
				OtherPayTable.addCell(new Paragraph(StringUtils.capitalize(somtype.toLowerCase())+"",smallBold));
				OtherPayTable.addCell(new Paragraph(nf.format(som.getAmountPiad())+"",smallBold));
				OtherPayTable.addCell(new Paragraph(som.getTerm()+"",smallBold));
				OtherPayTable.addCell(new Paragraph(som.getYear()+"",smallBold));

				countOther++;
			}
		}

		//END document.add(OtherPayTable); 
		//document.add(preface);
		Paragraph emptyline = new Paragraph(("                              "));
		document.add(OtherPayTable); 
		document.add(myTable); 
		document.add(emptyline);


		nextterm = 2;
		int thisyear = nextyear;
		// this term = 1  real term = 1

		cmputeMoveTerm2(nextterm,thisyear,school,stuudent, examConfig2,newBal);


	}

	/**
	 * @param nextterm
	 * @param nextyear
	 * @param school
	 * @param stuudent
	 * @param examConfig2
	 * @param lastTermfeeBal 
	 * @throws DocumentException
	 */
	private void cmputeMoveTerm2(int nextterm, int nextyear, SchoolAccount school, Student stuudent, ExamConfig examConfig2, double lastTermfeeBal) throws DocumentException {

		String regterm = Integer.toString(nextterm);
		String admYear = Integer.toString(nextyear);

		//table here
		PdfPCell countHeader = new PdfPCell(new Paragraph("S.N",boldFont));
		countHeader.setBackgroundColor(baseColor);
		countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell amountHeader = new PdfPCell(new Paragraph("Amnt Paid",boldFont));
		amountHeader.setBackgroundColor(baseColor);
		amountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell transactionHeader = new PdfPCell(new Paragraph("Transaction Number",boldFont));
		transactionHeader.setBackgroundColor(baseColor);
		transactionHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell paydateHeader = new PdfPCell(new Paragraph("Date paid",boldFont));
		paydateHeader.setBackgroundColor(baseColor);
		paydateHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


		PdfPCell termHeader = new PdfPCell(new Paragraph("Term " + regterm,boldFont));
		termHeader.setBackgroundColor(Colorgrey);
		termHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell yearHeader = new PdfPCell(new Paragraph("Year " + admYear,boldFont));
		yearHeader.setBackgroundColor(Colorgrey);
		yearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPTable myTable = new PdfPTable(6); 
		myTable.addCell(countHeader);
		myTable.addCell(amountHeader);
		myTable.addCell(transactionHeader);
		myTable.addCell(paydateHeader);
		myTable.addCell(termHeader);
		myTable.addCell(yearHeader);//


		myTable.setWidthPercentage(100); 
		myTable.setWidths(new int[]{8,40,80,40,12,15});   
		myTable.setHorizontalAlignment(Element.ALIGN_LEFT);


		//Get payments for the 'start year' and 'start term' 
		List<StudentFee> list = new ArrayList<>();               
		list = studentFeeDAO.getStudentFeeByStudentUuidList(school.getUuid(),stuudent.getUuid(),regterm,admYear);
		double newBal = 0;
		int mycount = 1;
		currentStudentType = "";feeStudentType ="";
		double totalpaid = 0;
		if(list !=null){
			for(StudentFee fee : list){

				myTable.addCell(new Paragraph(mycount+"",smallBold));
				myTable.addCell(new Paragraph(nf.format(fee.getAmountPaid())+" - " + fee.getStudentType(),smallBold));
				myTable.addCell(new Paragraph(fee.getTransactionID()+"",smallBold));
				myTable.addCell(new Paragraph(formatter.format(fee.getDatePaid())+"",smallBold));
				myTable.addCell(new Paragraph(regterm,smallBold));
				myTable.addCell(new Paragraph(admYear,smallBold));
 
				feeStudentType = fee.getStudentType();
				totalpaid +=fee.getAmountPaid();
				mycount++;
			}



			double prevtermbalance = 0;
						
			TermFee termFeenew = new TermFee();
			if(termFeeDAO.getFee(school.getUuid(),"2",admYear) !=null){
				termFeenew = termFeeDAO.getFee(school.getUuid(),"2",admYear);
			}
			
			double other_m_amount = 0;
			double other_m_totals = 0;
			
			List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
			stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);
			if(stuOthermoniList !=null){
				
				for(StudentOtherMonies som  : stuOthermoniList){
					other_m_amount = som.getAmountPiad();
					other_m_totals +=other_m_amount;
				}
			}
			double carriedForward = 0;
			
			carriedForward = lastTermfeeBal;
			lastTermfeeBal = 0;
			

			double termfee = 0;
			//TODO term fee depending on student type
			currentStudentType = studentType.get(stuudent.getUuid()); 
			// System.out.println(currentStudentType);
            if(StringUtils.equals(feeStudentType, "Boarder")) {
            	lastTermfeeBal = (termFeenew.getTermAmount() - prevtermbalance - totalpaid + other_m_totals);
				termfee = termFeenew.getTermAmount();
            }else if(StringUtils.equals(feeStudentType, "Day")) {
				lastTermfeeBal = (termFeenew.getDayAmount() - prevtermbalance - totalpaid + other_m_totals);
				termfee = termFeenew.getDayAmount();
			}else{
				if(StringUtils.equals(currentStudentType, "Boarder")){
					termfee = termFeenew.getTermAmount();
					lastTermfeeBal = termfee;
				}else{
					termfee = termFeenew.getDayAmount();
					lastTermfeeBal = termfee;
				}
				
			}
			
			
			newBal = carriedForward + lastTermfeeBal;
			
			PdfPCell closeHeader = new PdfPCell(new Paragraph("Total Paid = " + nf.format(totalpaid) +""
					+ "                " + " Carried Forward = "+nf.format(carriedForward) +""
					+ "                " + " Term Fee = "+nf.format(termfee) +" "//
					+ "                " + " Fee Balance = " +nf.format(newBal) , smallBold));//
			//closeHeader.setBackgroundColor(baseColor);
			closeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			closeHeader.setColspan(6); 

			myTable.addCell(closeHeader);

		}



		//START
		PdfPCell mycountHeader = new PdfPCell(new Paragraph("S.N",boldFont));
		mycountHeader.setBackgroundColor(baseColor);
		mycountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell ptypeHeader = new PdfPCell(new Paragraph("Payment Type",boldFont));
		ptypeHeader.setBackgroundColor(baseColor);
		ptypeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell amountpaidHeader = new PdfPCell(new Paragraph("Amount",boldFont));
		amountpaidHeader.setBackgroundColor(baseColor);
		amountpaidHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell oTernHeader = new PdfPCell(new Paragraph("Term",boldFont));
		oTernHeader.setBackgroundColor(baseColor);
		oTernHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell pYearHeader = new PdfPCell(new Paragraph("Year",boldFont));
		pYearHeader.setBackgroundColor(baseColor);
		pYearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPTable OtherPayTable;

		OtherPayTable = new PdfPTable(5); 
		OtherPayTable.addCell(mycountHeader);
		OtherPayTable.addCell(ptypeHeader);
		OtherPayTable.addCell(amountpaidHeader);
		OtherPayTable.addCell(oTernHeader);
		OtherPayTable.addCell(pYearHeader);
		OtherPayTable.setWidthPercentage(100); 
		OtherPayTable.setWidths(new int[]{8,35,40,35,35});   
		OtherPayTable.setHorizontalAlignment(Element.ALIGN_LEFT);

		OtherstypeDAO otherstypeDAO = OtherstypeDAO.getInstance();
		List<Otherstype> othertypeList = new ArrayList<Otherstype>(); 
		othertypeList = otherstypeDAO.gettypeList(school.getUuid());  
		HashMap<String, String> moneytypeHash = new HashMap<String, String>(); 
		if(othertypeList !=null){
			for(Otherstype om : othertypeList){
				moneytypeHash.put(om.getUuid(),om.getType());
			}
		}
		List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
		if(studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear) !=null){
			stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);
		}  


		String somtype = "";
		int countOther = 1;
		if(stuOthermoniList !=null){
			for(StudentOtherMonies som  : stuOthermoniList){

				somtype = moneytypeHash.get(som.getOtherstypeUuid()); 

				OtherPayTable.addCell(new Paragraph(countOther+"",smallBold));
				OtherPayTable.addCell(new Paragraph(StringUtils.capitalize(somtype.toLowerCase())+"",smallBold));
				OtherPayTable.addCell(new Paragraph(nf.format(som.getAmountPiad())+"",smallBold));
				OtherPayTable.addCell(new Paragraph(som.getTerm()+"",smallBold));
				OtherPayTable.addCell(new Paragraph(som.getYear()+"",smallBold));

				countOther++;
			}
		}

		//END document.add(OtherPayTable); 
		//document.add(preface);
		Paragraph emptyline = new Paragraph(("                              "));
		document.add(OtherPayTable); 
		document.add(myTable); 
		document.add(emptyline);



		nextterm = 3;

		cmputeMoveTerm3(nextterm,nextyear,school,stuudent, examConfig2,newBal);


	}


	
	
	
	

	/**
	 * @param nextterm
	 * @param nextyear
	 * @param school
	 * @param stuudent
	 * @param examConfig2
	 * @param lastTermfeeBal 
	 * @throws DocumentException
	 */
	private void cmputeMoveTerm3(int nextterm, int nextyear, SchoolAccount school, Student stuudent, ExamConfig examConfig2, double lastTermfeeBal) throws DocumentException {

		String regterm = Integer.toString(nextterm);
		String admYear = Integer.toString(nextyear);


		//table here
		PdfPCell countHeader = new PdfPCell(new Paragraph("S.N",boldFont));
		countHeader.setBackgroundColor(baseColor);
		countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell amountHeader = new PdfPCell(new Paragraph("Amnt Paid",boldFont));
		amountHeader.setBackgroundColor(baseColor);
		amountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell transactionHeader = new PdfPCell(new Paragraph("Transaction Number",boldFont));
		transactionHeader.setBackgroundColor(baseColor);
		transactionHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell paydateHeader = new PdfPCell(new Paragraph("Date paid",boldFont));
		paydateHeader.setBackgroundColor(baseColor);
		paydateHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


		PdfPCell termHeader = new PdfPCell(new Paragraph("Term " + regterm,boldFont));
		termHeader.setBackgroundColor(Colorgrey);
		termHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell yearHeader = new PdfPCell(new Paragraph("Year " + admYear,boldFont));
		yearHeader.setBackgroundColor(Colorgrey);
		yearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPTable myTable = new PdfPTable(6); 
		myTable.addCell(countHeader);
		myTable.addCell(amountHeader);
		myTable.addCell(transactionHeader);
		myTable.addCell(paydateHeader);
		myTable.addCell(termHeader);
		myTable.addCell(yearHeader);//


		myTable.setWidthPercentage(100); 
		myTable.setWidths(new int[]{8,40,80,40,12,15});   
		myTable.setHorizontalAlignment(Element.ALIGN_LEFT);


		//Get payments for the 'start year' and 'start term' 
		List<StudentFee> list = new ArrayList<>();               
		list = studentFeeDAO.getStudentFeeByStudentUuidList(school.getUuid(),stuudent.getUuid(),regterm,admYear);
		double newBal = 0;
		int mycount = 1;
		currentStudentType = "";feeStudentType ="";
		double totalpaid = 0;
		if(list !=null){
			for(StudentFee fee : list){

				myTable.addCell(new Paragraph(mycount+"",smallBold));
				myTable.addCell(new Paragraph(nf.format(fee.getAmountPaid())+" - " + fee.getStudentType(),smallBold));
				myTable.addCell(new Paragraph(fee.getTransactionID()+"",smallBold));
				myTable.addCell(new Paragraph(formatter.format(fee.getDatePaid())+"",smallBold));
				myTable.addCell(new Paragraph(regterm,smallBold));
				myTable.addCell(new Paragraph(admYear,smallBold));
                
				feeStudentType = fee.getStudentType();
				totalpaid +=fee.getAmountPaid();
				mycount++;
			}

			double prevtermbalance = 0;
			TermFee termFeenew = new TermFee();
			if(termFeeDAO.getFee(school.getUuid(),"3",admYear) !=null){
				termFeenew = termFeeDAO.getFee(school.getUuid(),"3",admYear);
			}

			double other_m_amount = 0;
			double other_m_totals = 0;
			
			List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
			stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);
			if(stuOthermoniList !=null){
				
				for(StudentOtherMonies som  : stuOthermoniList){
					other_m_amount = som.getAmountPiad();
					other_m_totals +=other_m_amount;
				}
			}
			
			double carriedForward = 0;
			
			carriedForward = lastTermfeeBal;
			lastTermfeeBal = 0;
			
            double termfee = 0;
          //TODO term fee depending on student type
            currentStudentType = studentType.get(stuudent.getUuid()); 
            //System.out.println(currentStudentType);
            if(StringUtils.equals(feeStudentType, "Boarder")) {
            	lastTermfeeBal = (termFeenew.getTermAmount() - prevtermbalance - totalpaid + other_m_totals);
				termfee = termFeenew.getTermAmount();
            }else if(StringUtils.equals(feeStudentType, "Day")) {
				lastTermfeeBal = (termFeenew.getDayAmount() - prevtermbalance - totalpaid + other_m_totals);
				termfee = termFeenew.getDayAmount();
			}else{
				if(StringUtils.equals(currentStudentType, "Boarder")){
					termfee = termFeenew.getTermAmount();
					lastTermfeeBal = termfee;
				}else{
					termfee = termFeenew.getDayAmount();
					lastTermfeeBal = termfee;
				}
				
			}
			
			
			newBal = carriedForward + lastTermfeeBal;
			
			PdfPCell closeHeader = new PdfPCell(new Paragraph("Total Paid = " + nf.format(totalpaid) +""
					+ "                " + " Carried Forward = "+nf.format(carriedForward) +""
					+ "                " + " Term Fee = "+nf.format(termfee) +" "//
					+ "                " + " Fee Balance = " +nf.format(newBal) , smallBold));//
			//closeHeader.setBackgroundColor(baseColor);
			closeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			closeHeader.setColspan(6); 

			myTable.addCell(closeHeader);



		}



		//START
		PdfPCell mycountHeader = new PdfPCell(new Paragraph("S.N",boldFont));
		mycountHeader.setBackgroundColor(baseColor);
		mycountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell ptypeHeader = new PdfPCell(new Paragraph("Payment Type",boldFont));
		ptypeHeader.setBackgroundColor(baseColor);
		ptypeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell amountpaidHeader = new PdfPCell(new Paragraph("Amount",boldFont));
		amountpaidHeader.setBackgroundColor(baseColor);
		amountpaidHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell oTernHeader = new PdfPCell(new Paragraph("Term",boldFont));
		oTernHeader.setBackgroundColor(baseColor);
		oTernHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell pYearHeader = new PdfPCell(new Paragraph("Year",boldFont));
		pYearHeader.setBackgroundColor(baseColor);
		pYearHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPTable OtherPayTable;

		OtherPayTable = new PdfPTable(5); 
		OtherPayTable.addCell(mycountHeader);
		OtherPayTable.addCell(ptypeHeader);
		OtherPayTable.addCell(amountpaidHeader);
		OtherPayTable.addCell(oTernHeader);
		OtherPayTable.addCell(pYearHeader);
		OtherPayTable.setWidthPercentage(100); 
		OtherPayTable.setWidths(new int[]{8,35,40,35,35});   
		OtherPayTable.setHorizontalAlignment(Element.ALIGN_LEFT);

		OtherstypeDAO otherstypeDAO = OtherstypeDAO.getInstance();
		List<Otherstype> othertypeList = new ArrayList<Otherstype>(); 
		othertypeList = otherstypeDAO.gettypeList(school.getUuid());  
		HashMap<String, String> moneytypeHash = new HashMap<String, String>(); 
		if(othertypeList !=null){
			for(Otherstype om : othertypeList){
				moneytypeHash.put(om.getUuid(),om.getType());
			}
		}
		List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
		if(studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear) !=null){
			stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),regterm,admYear);
		}  


		String somtype = "";
		int countOther = 1;
		if(stuOthermoniList !=null){
			for(StudentOtherMonies som  : stuOthermoniList){

				somtype = moneytypeHash.get(som.getOtherstypeUuid()); 

				OtherPayTable.addCell(new Paragraph(countOther+"",smallBold));
				OtherPayTable.addCell(new Paragraph(StringUtils.capitalize(somtype.toLowerCase())+"",smallBold));
				OtherPayTable.addCell(new Paragraph(nf.format(som.getAmountPiad())+"",smallBold));
				OtherPayTable.addCell(new Paragraph(som.getTerm()+"",smallBold));
				OtherPayTable.addCell(new Paragraph(som.getYear()+"",smallBold));

				countOther++;
			}
		}

		Paragraph emptyline = new Paragraph(("                              "));
		document.add(OtherPayTable); 
		document.add(myTable); 
		document.add(emptyline);

		nextterm = 1;
		int nextyr = nextyear + 1;

		computeNext(nextterm,nextyr,school,stuudent, examConfig2,newBal);


	}

	
	

	/**
	 * @param realPath
	 * @return
	 */
	private Element createImage(String realPath) {
		Image imgLogo = null;

		try {
			imgLogo = Image.getInstance(realPath);
			imgLogo.scaleToFit(100, 100);
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

}
