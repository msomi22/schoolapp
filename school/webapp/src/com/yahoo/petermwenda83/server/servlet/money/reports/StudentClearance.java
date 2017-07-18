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
import java.util.HashMap;
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
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.money.Deposit;
import com.yahoo.petermwenda83.bean.money.PocketMoney;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies;
import com.yahoo.petermwenda83.bean.othermoney.TermOtherMonies;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.money.PMoneyDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
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
public class StudentClearance extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3427815096919677271L;
	
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd, MMM yyyy");
	SimpleDateFormat yearformatter = new SimpleDateFormat("yyyy");
	BaseColor baseColor = new BaseColor(32,178,170);//maroon
	BaseColor Colormagenta = new BaseColor(176,196,222);//magenta
	Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	
	//private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLDITALIC);
	private Font normalText = new Font(Font.FontFamily.COURIER, 8,Font.UNDERLINE);
	
	private Document document;
	private PdfWriter writer;

	private Logger logger;
	ExamConfig examConfig;

	private String PDF_TITLE ="";
	private String PDF_SUBTITLE ="";

	Locale locale = new Locale("en","KE"); 
	NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

	private static StudentOtherMoniesDAO studentOtherMoniesDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static StudentDAO studentDAO;
	private static ExamConfigDAO examConfigDAO;
	private static TermFeeDAO termFeeDAO;
	private static PMoneyDAO pMoneyDAO;
	
	
	private Cache schoolaccountCache;


	TermOtherMonies termOtherMonies;
	StudentOtherMonies studentOtherMonies;

	String USER= "";
	String path ="";
	
	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String> firstnameHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>();
	HashMap<String, String> roomHash = new HashMap<String, String>();

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
	pMoneyDAO = PMoneyDAO.getInstance();
	examConfigDAO = ExamConfigDAO.getInstance();
	termFeeDAO = TermFeeDAO.getInstance();
	studentOtherMoniesDAO = StudentOtherMoniesDAO.getInstance();
	
	USER = System.getProperty("user.name");
	path = "/home/"+USER+"/school/logo/logo.png";
 
   
}

protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

	HttpSession session = request.getSession(true);

	response.setContentType("application/pdf");

	String studentuuid = StringUtils.trimToEmpty(request.getParameter("studentuuid"));
	studentuuid = "9703f423-7381-4496-9b8d-2c3ae0ad9b63"; //A195BAF6-D6E7-43A5-B7C9-D6C627A42815// 4F218688-6DE5-4E69-8690-66FBA2F0DC9F
	
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

	String pdfname =school.getUsername()+"Report.pdf";

	response.setHeader("Content-Disposition", "inline; filename= \"" +pdfname);

	examConfig = new ExamConfig();
	if(examConfigDAO.getExamConfig(school.getUuid()) !=null){
		examConfig = examConfigDAO.getExamConfig(school.getUuid());
	}



	TermFee termFee = new TermFee();
	if(termFeeDAO.getFee(school.getUuid(),examConfig.getTerm(),examConfig.getYear()) !=null){
		termFee = termFeeDAO.getFee(school.getUuid(),examConfig.getTerm(),examConfig.getYear());
	}

	Student stuudent = new Student();
	stuudent = studentDAO.getStudentByuuid(school.getUuid(), studentuuid);

	if(stuudent != null){
		studentAdmNoHash.put(stuudent.getUuid(),stuudent.getAdmno()); 
		String firstNameLowecase = stuudent.getFirstname().toLowerCase();
		String lastNameLowecase = stuudent.getLastname().toLowerCase();
		String surnameLowecase = stuudent.getSurname().toLowerCase();
		String formatedFirstname = firstNameLowecase.substring(0,1).toUpperCase()+firstNameLowecase.substring(1);
		String formatedLastname = lastNameLowecase.substring(0,1).toUpperCase()+lastNameLowecase.substring(1);
		String formatedsurname = surnameLowecase.substring(0,1).toUpperCase()+surnameLowecase.substring(1);
		studNameHash.put(stuudent.getUuid(),formatedFirstname + " " + formatedLastname + " " + formatedsurname +"\n"); 
		firstnameHash.put(stuudent.getUuid(), formatedFirstname);

		pdfname =new Date()+stuudent.getAdmno()+"_Fee_Statement.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename= \"" +pdfname);

	}



	PDF_TITLE = "FINANCIAL ANALYSIS REPORT \n";

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

		populatePDFDocument(school,examConfig,termFee,stuudent,path);


	} catch (DocumentException e) {
		logger.error("DocumentException while writing into the document");
		logger.error(ExceptionUtils.getStackTrace(e));
	}




	}


	/**
	 * @param school
	 * @param examConfig2
	 * @param termFee
	 * @param stuudent 
	 * @param feeList
	 * @param path2
	 * @throws DocumentException
	 */
	private void populatePDFDocument(SchoolAccount school, ExamConfig exam, TermFee termFee,Student stuudent, String path2) throws DocumentException {
     
		/**  Algorithm to clear students
		 *   1)if student has fee balance, they must clear the balance first
		 *     else if student has over payment 
		 *          1)move the over payment to pocket money account so that he/she can be able to withdraw 
		 *          
		 *          otherwise clear the student, print clearance form
		 *          
		 *               ***********************************************************
		 *          
		 *                           
		 *                    (THIS WILL ALSO BE THE LEAVING CERTIFICATE)
		 *                           
		 *               ***********************************************************
		 *                                * COAT OF ARM *
		 *                                
		 *                                ED/B 100(Rev)
		 *                                
		 *                                REPUBLIC OF KENYA
		 *                                MINISTRY OF EDUCATION
		 *                    KENYA SECONDARY SCHOOL LEAVING CERTIFICATE 
		 *                                
		 *               SCHOOL DETAILS
		 *               
		 *               STUDENT *ADM NO*
		 *               
		 *               THIS IS TO CERTIFY THAT ______________NAME__________________________
		 *               Entered this school on __________year______________  and was enrolled 
		 *               in Form ____FOUR____and left on _____NOV,2010 ______from Form __4____
		 *               having satisfactorily completed the approved Course from Form __4____
		 *               
		 *               Date of Birth (in admission Register) ________ 19*** ________________
		 *               
		 *               *Headteacher's report on the pupil's ability,industry and conduct*
		 *               
		 *               __Above average student, A very disciplined and hard working boy, A Memeber 
		 *               of __clubs__
		 *               
		 *               
		 *               Pupil's Signature.___________
		 *               Date of Issue._______________
		 *               
		 *               Head teacher Signature.___________
		 *               
		 *               This certificate was issued without any erasure or alteration whatsoever.
		 *               
		 *               GPK(L)
		 *               
		 *               
		 *               
		 *                           
		 *                           
		 *           ********************************************************************************
		 *                          The Clearance Report 
		 *           ******************************************************************************
		 *          
		 * 
		 */


		document.open();


		Paragraph preface = new Paragraph();
		preface.add(new Paragraph(PDF_TITLE, normalText));
		preface.add(new Paragraph(PDF_SUBTITLE, normalText));
		preface.add(new Paragraph(("ADM N0 : " + studentAdmNoHash.get(stuudent.getUuid()) +"\n" +("STUDENT : " + studNameHash.get(stuudent.getUuid()))),normalText));
		preface.add(new Paragraph("Printed on: " + formatter.format(new Date()) + " Term: " + exam.getTerm()+" Year: " + exam.getYear()+"\n\n", normalText));
		document.add(preface);
		

		//fee balance
		double other_m_amount = 0;
		double other_m_totals = 0;
		
       //get other payments
		List<StudentOtherMonies>  stuOthermoniList = new ArrayList<>(); 
		if(studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),examConfig.getTerm(),examConfig.getYear()) !=null){
			stuOthermoniList = studentOtherMoniesDAO.getStudentOtherList(stuudent.getUuid(),examConfig.getTerm(),examConfig.getYear());
		}  
		
		
        //calculate other payments
		if(stuOthermoniList !=null){
			for(StudentOtherMonies som  : stuOthermoniList){
				other_m_amount = som.getAmountPiad();
				other_m_totals +=other_m_amount;
			}
		}
		
		//get fee payments for this term
		List<StudentFee> feelist = new ArrayList<>();
		if(studentFeeDAO.getStudentFeeByStudentUuidList(school.getUuid(),stuudent.getUuid(),examConfig.getTerm(),examConfig.getYear()) !=null){
			feelist = studentFeeDAO.getStudentFeeByStudentUuidList(school.getUuid(),stuudent.getUuid(),examConfig.getTerm(),examConfig.getYear());
          
		}
		
		//calculate payment for the current term
		double totalpaid = 0;
		double paid = 0;
			totalpaid = 0;
			paid = 0;
			for(StudentFee fee : feelist){
				paid = fee.getAmountPaid();
				totalpaid +=paid;
		}


		// we should find previous term balance or over payments
		String previuosyear = "";
		String currentyear = examConfig.getYear();
		int currentyearint = Integer.parseInt(currentyear);
		int previousyearint = 0;

		String currenttermStr = examConfig.getTerm();
		int currenttermint = Integer.parseInt(currenttermStr);// can either be 1, 2, or 3
		int previousterm = currenttermint - 1;// if c = 3 , p = 2 // if c = 2 , p = 1 // if c = 1 p = 3
		if(previousterm == 0){
			previousterm = 3;
			previousyearint = currentyearint - 1;
			previuosyear = Integer.toString(previousyearint);
		}else{
			previuosyear = examConfig.getYear();
		}
		String previoustermStr = Integer.toString(previousterm);
		
		//now we have the previous term , we get the term amount (previous)
		//from closing balance, we add the amount, negative balance means dues, positive balance means over pay
		
		
		Locale locale = new Locale("en","KE"); 
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		
		
		//balance
		//double mybalance = 0;
		//String balance ="";
		double b_balance = 0;
		//balance = nf.format(termFee.getTermAmount() - prevtermbalance - totalpaid + other_m_totals);
		b_balance = (termFee.getTermAmount() - 0 - totalpaid + other_m_totals); 
		
		//System.out.println("balance = "+b_balance); 
		double overpayment = 0;
		
		// if the balance is over, credit pocket money account.
		// if balance is negative, over payment, move to pocket money
		//System.out.println("overpayment = "+b_balance); 
		if(b_balance <0){
			overpayment = Math.abs(b_balance); 
			//save the over payments to pocket money account
			if(overpayment>0){
				//fix it now
				System.out.println("balance abs (OverPay)= "+overpayment); 
				   Deposit d = new Deposit();
				   d.setStudentUuid(stuudent.getUuid());  
		    	   d.setSystemUser("Auto-add");
		    	   d.setTerm(examConfig.getTerm());
		           d.setYear(examConfig.getYear());
		           if(pMoneyDAO.addBalance(d,overpayment)){
		        	 
		           }
				   
					  
				
				
			}// end if(overpayment>0){

		}else{
			System.out.println("NO over pay"); 
		}
		
		

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



}
