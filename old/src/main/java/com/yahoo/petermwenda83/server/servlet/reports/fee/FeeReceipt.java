/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.fee;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.print.attribute.standard.PrinterIsAcceptingJobs;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.otherfee.OtherFee;
import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.RevertedMoneyDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.finance.StudentBalance;
import com.yahoo.petermwenda83.server.servlet.reports.PdfUtil;
import com.yahoo.petermwenda83.server.servlet.util.Timeit;

/**
 * 
 * http://127.0.0.1:8080/school/school/feeReceipt 
 * 
 * @author peter
 *
 */
public class FeeReceipt extends HttpServlet{

	private Font timesRomanBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private Font timesRomanNormal10= new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	private Font timesRomanNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	private Font timesRomanNormal8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

	private Document document;
	private PdfWriter writer;

	private Logger logger;

	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;

	private static StudentFeeDAO studentFeeDAO;
	private static OtherFeeDAO otherFeeDAO;
	private static StudentOtherFeeDAO studentOtherFeeDAO;
	private static RevertedMoneyDAO revertedMoneyDAO;

	private static TermFeeDAO termFeeDAO;
	private static SysConfigDAO sysConfigDAO;

	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();

		studentFeeDAO = StudentFeeDAO.getInstance();
		otherFeeDAO = OtherFeeDAO.getInstance();
		studentOtherFeeDAO = StudentOtherFeeDAO.getInstance();
		revertedMoneyDAO = RevertedMoneyDAO.getInstance();

		termFeeDAO = TermFeeDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();

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

		HttpSession session = request.getSession(true);


		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4, 46, 46, 64, 64);


		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();

			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD", studentId = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F"; 
			populatePDFDocument(accountId,studentId);  

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}




	private void populatePDFDocument(String accountId, String studentId) {

		Timeit.code(() -> compute(accountId,studentId));

	}

	private void compute(String accountId, String studentId) {
		try {

			document.open();

			generateReport(accountId, studentId);

			document.close();

		}catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		} 
	}


	private void generateReport(String accountId, String studentId) throws DocumentException {


		//BaseColor baseColorWhite = new BaseColor(255,255,255);//while
		BaseColor baseColor = new BaseColor(117,229,210);//#75e5d2
		//BaseColor baseColorShadow = new BaseColor(0,255,119);//#00FF77

		Account account = accountDAO.getAccountById(accountId);
		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

		SimpleDateFormat format = new SimpleDateFormat("MMMM dd HH:mm:ss ", Locale.ENGLISH); 

		Locale locale = new Locale("en","KE"); 
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		

		String sch_info_ = "LOGO & SCHOOL INFO";
		Paragraph sch_info_paragraph = new Paragraph(sch_info_,timesRomanBold10);
		sch_info_paragraph.setAlignment(Element.ALIGN_CENTER);

		String no = RandomStringUtils.randomAlphabetic(6); 
		String date = format.format(new Date());

		String receipt_title_ = "FREE EDUCATION & BASIC SCHOOL FUND \n"
				+ "OPERATIONS ACCOUNT (OFFICIAL RECEIPT) \n"; 

		Chunk receiptTite = new Chunk(receipt_title_,timesRomanBold10); 

		Chunk receipt = new Chunk("RECEPT NO:",timesRomanNormal10); 
		Chunk receiptNo = new Chunk(no,timesRomanNormal10); 

		Chunk pDate = new Chunk(", DATE:",timesRomanNormal10); 
		Chunk printDate = new Chunk(date,timesRomanNormal10); 

		Paragraph receipt_title_paragraph = new Paragraph(receiptTite +""+ receipt + " " + receiptNo + " " + pDate + " " + printDate);
		receipt_title_paragraph.setAlignment(Element.ALIGN_CENTER);


		document.add(sch_info_paragraph); 
		document.add(new Paragraph()); 

		document.add(receipt_title_paragraph); 
		document.add(new Paragraph()); 


		//TODO

		Paragraph gvmt_fundsparagraph = new Paragraph("Free Education Fund \n",timesRomanBold10);
		gvmt_fundsparagraph.setAlignment(Element.ALIGN_CENTER);

		PdfPTable gvmtFundsTable = new PdfPTable(4);
		gvmtFundsTable.setWidthPercentage(83);  
		gvmtFundsTable.setWidths(new int[]{8,25,15,25});  
		//NO  Item          Fee(KSH)   Amount Paid (KSH) 
		PdfPCell gvmtFunds_count_Cell = new PdfPCell(new Phrase("No",timesRomanBold10)); 
		gvmtFunds_count_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		gvmtFunds_count_Cell.setBackgroundColor(baseColor);

		PdfPCell gvmtFunds_item_Cell = new PdfPCell(new Phrase("Item",timesRomanBold10)); 
		gvmtFunds_item_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		gvmtFunds_item_Cell.setBackgroundColor(baseColor);

		PdfPCell gvmtFunds_fee_Cell = new PdfPCell(new Phrase("Fee (KSH)",timesRomanBold10));  
		gvmtFunds_fee_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		gvmtFunds_fee_Cell.setBackgroundColor(baseColor);

		PdfPCell gvmtFunds_amnt_Cell = new PdfPCell(new Phrase("Amount Paid (KSH)",timesRomanBold10)); 
		gvmtFunds_amnt_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		gvmtFunds_amnt_Cell.setBackgroundColor(baseColor);

		gvmtFundsTable.addCell(gvmtFunds_count_Cell);
		gvmtFundsTable.addCell(gvmtFunds_item_Cell);
		gvmtFundsTable.addCell(gvmtFunds_fee_Cell);
		gvmtFundsTable.addCell(gvmtFunds_amnt_Cell);


		//TODO

		Paragraph sch_fee_paragraph = new Paragraph("Other Payments",timesRomanBold10);
		sch_fee_paragraph.setAlignment(Element.ALIGN_CENTER);

		PdfPTable schFundsTable = new PdfPTable(4); 
		schFundsTable.setWidthPercentage(83);   
		schFundsTable.setWidths(new int[]{8,25,15,15});  
		
		PdfPCell schFunds_count_Cell = new PdfPCell(new Phrase("No",timesRomanBold10)); 
		schFunds_count_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		schFunds_count_Cell.setBackgroundColor(baseColor);
		schFunds_count_Cell.setBorder(Rectangle.NO_BORDER);

		PdfPCell schFunds_item_Cell = new PdfPCell(new Phrase("Item",timesRomanBold10)); 
		schFunds_item_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		schFunds_item_Cell.setBackgroundColor(baseColor);
		schFunds_item_Cell.setBorder(Rectangle.NO_BORDER);

		PdfPCell schFunds_fee_Cell = new PdfPCell(new Phrase("Amount (KSH)",timesRomanBold10));  
		schFunds_fee_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		schFunds_fee_Cell.setBackgroundColor(baseColor);
		schFunds_fee_Cell.setBorder(Rectangle.NO_BORDER);

		PdfPCell schFunds_date_Cell = new PdfPCell(new Phrase("Date",timesRomanBold10)); 
		schFunds_date_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		schFunds_date_Cell.setBackgroundColor(baseColor);
		schFunds_date_Cell.setBorder(Rectangle.NO_BORDER);

		schFundsTable.addCell(schFunds_count_Cell);
		schFundsTable.addCell(schFunds_item_Cell);
		schFundsTable.addCell(schFunds_fee_Cell);
		schFundsTable.addCell(schFunds_date_Cell);
		
		long yearLong = (long)Integer.valueOf(sysConfig.getYear()); 

		if(studentOtherFeeDAO.getStudentOFeeList(accountId, studentId, sysConfig.getTerm(), yearLong) != null) {
			List<StudentOtherFee> studentOtherFeeList  = studentOtherFeeDAO.getStudentOFeeList(accountId, studentId, sysConfig.getTerm(), yearLong); 
			
			int count = 1;
			for(StudentOtherFee ofee : studentOtherFeeList) {
				
				PdfPCell countCell = new PdfPCell(new Phrase(""+count,timesRomanNormal8));
				countCell.setBorder(Rectangle.NO_BORDER);
				
				OtherFee otherFee = otherFeeDAO.getOtherFee(accountId, ofee.getOtherFeeId());
				
				String description = otherFee.getDescription();
				String amount = nf.format(otherFee.getAmount());
				
				PdfPCell refNoCell = new PdfPCell(new Phrase(""+description,timesRomanNormal8));
				refNoCell.setBorder(Rectangle.NO_BORDER);
				
				PdfPCell amountCell = new PdfPCell(new Phrase(""+amount,timesRomanNormal8));
				amountCell.setBorder(Rectangle.NO_BORDER);
				
				PdfPCell dateCell = new PdfPCell(new Phrase(""+format.format(ofee.getDateAllocated()),timesRomanNormal8));
				dateCell.setBorder(Rectangle.NO_BORDER);
				
				schFundsTable.addCell(countCell);
				schFundsTable.addCell(refNoCell);
				schFundsTable.addCell(amountCell);
				schFundsTable.addCell(dateCell);
				
				
				count++;
			}
			
			
		}
		
		
		
		//TODO

		Paragraph amount_paid_paragraph = new Paragraph("Fee Payment History",timesRomanBold10);
		amount_paid_paragraph.setAlignment(Element.ALIGN_CENTER);

		PdfPTable amntPaidTable = new PdfPTable(5);
		amntPaidTable.setWidthPercentage(83); //73  
		amntPaidTable.setWidths(new int[]{8,15,20,15,15});  
		
		PdfPCell paid_count_Cell = new PdfPCell(new Phrase("No",timesRomanBold10)); 
		paid_count_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		paid_count_Cell.setBackgroundColor(baseColor);
		paid_count_Cell.setBorder(Rectangle.NO_BORDER);

		PdfPCell paid_refNo_Cell = new PdfPCell(new Phrase("Ref No",timesRomanBold10)); 
		paid_refNo_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		paid_refNo_Cell.setBackgroundColor(baseColor);
		paid_refNo_Cell.setBorder(Rectangle.NO_BORDER);
		
		PdfPCell paid_desc_Cell = new PdfPCell(new Phrase("Description",timesRomanBold10)); 
		paid_desc_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		paid_desc_Cell.setBackgroundColor(baseColor);
		paid_desc_Cell.setBorder(Rectangle.NO_BORDER);

		PdfPCell paid_amnt_Cell = new PdfPCell(new Phrase("Amount (KSH)",timesRomanBold10)); 
		paid_amnt_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		paid_amnt_Cell.setBackgroundColor(baseColor);
		paid_amnt_Cell.setBorder(Rectangle.NO_BORDER);
		
		PdfPCell paid_date_Cell = new PdfPCell(new Phrase("Date Paid",timesRomanBold10)); 
		paid_date_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		paid_date_Cell.setBackgroundColor(baseColor);
		paid_date_Cell.setBorder(Rectangle.NO_BORDER);

		amntPaidTable.addCell(paid_count_Cell);
		amntPaidTable.addCell(paid_refNo_Cell);
		amntPaidTable.addCell(paid_desc_Cell);
		amntPaidTable.addCell(paid_amnt_Cell);
		amntPaidTable.addCell(paid_date_Cell);
		
		
		if(studentFeeDAO.getStudentFeeList(accountId, studentId, sysConfig.getTerm(), sysConfig.getYear()) != null) {
			List<StudentFee> studentFeeList = studentFeeDAO.getStudentFeeList(accountId, studentId, sysConfig.getTerm(), sysConfig.getYear());
			
			int count = 1;
			for(StudentFee fee : studentFeeList) {
				
				PdfPCell countCell = new PdfPCell(new Phrase(""+count,timesRomanNormal8));
				countCell.setBorder(Rectangle.NO_BORDER);
				
				String ref = fee.getTransactionId().substring(0, Math.min(fee.getTransactionId().length(), 10));
				PdfPCell refNoCell = new PdfPCell(new Phrase(""+ref,timesRomanNormal8));
				refNoCell.setBorder(Rectangle.NO_BORDER);
				
				PdfPCell modeCell = new PdfPCell(new Phrase(""+fee.getPayMode(),timesRomanNormal8));
				modeCell.setBorder(Rectangle.NO_BORDER);
				
				PdfPCell amountCell = new PdfPCell(new Phrase(""+nf.format(fee.getAmountPaid()),timesRomanNormal8));
				amountCell.setBorder(Rectangle.NO_BORDER);
				
				PdfPCell dateCell = new PdfPCell(new Phrase(""+format.format(fee.getDatePaid()),timesRomanNormal8));
				dateCell.setBorder(Rectangle.NO_BORDER);
				
				amntPaidTable.addCell(countCell);
				amntPaidTable.addCell(refNoCell);
				amntPaidTable.addCell(modeCell);
				amntPaidTable.addCell(amountCell);
				amntPaidTable.addCell(dateCell);

				count++;
			}
			
			

		}
				
		
		


		//TODO
		Paragraph fee_anaysis_paragraph = new Paragraph("Fee Payment Summary",timesRomanBold10);
		fee_anaysis_paragraph.setAlignment(Element.ALIGN_CENTER);


		PdfPTable summaryTable = new PdfPTable(2);
		summaryTable.setWidthPercentage(83);   
		summaryTable.setWidths(new int[]{20,20});  
		//DESCRIPTION (KSH)     AMOUNT
		PdfPCell sumary_desc_Cell = new PdfPCell(new Phrase("Description",timesRomanBold10));  
		sumary_desc_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		sumary_desc_Cell.setBackgroundColor(baseColor);
		sumary_desc_Cell.setBorder(Rectangle.NO_BORDER);

		PdfPCell sumary_amnt_Cell = new PdfPCell(new Phrase("Amount (KSH)",timesRomanBold10)); 
		sumary_amnt_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		sumary_amnt_Cell.setBackgroundColor(baseColor);
		sumary_amnt_Cell.setBorder(Rectangle.NO_BORDER);

		summaryTable.addCell(sumary_desc_Cell);
		summaryTable.addCell(sumary_amnt_Cell);

		String termFee = "8,000";
		String totalPaid = "6,000";
		
		StudentBalance studentBalance = new StudentBalance();
		double feeBalance = studentBalance.findBalance(accountId, studentId);

		String balance  = nf.format(feeBalance);

		for(int i=0;i<3;i++) {

			if(i == 0) {
				PdfPCell cell1 = new PdfPCell(new Phrase("TERM FEE",timesRomanBold10));
				cell1.setBorder(Rectangle.NO_BORDER);
				
				PdfPCell cell2 = new PdfPCell(new Phrase(termFee,timesRomanNormal10));
				cell2.setBorder(Rectangle.NO_BORDER);
				
				summaryTable.addCell(cell1);
				summaryTable.addCell(cell2);
			}
			if(i == 1) {
				PdfPCell cell1 = new PdfPCell(new Phrase("TOTAL PAID",timesRomanBold10));
				cell1.setBorder(Rectangle.NO_BORDER);
				
				PdfPCell cell2 = new PdfPCell(new Phrase(totalPaid,timesRomanNormal10));
				cell2.setBorder(Rectangle.NO_BORDER);
				
				summaryTable.addCell(cell1);
				summaryTable.addCell(cell2);
			}
			if(i == 2) {
				PdfPCell cell1 = new PdfPCell(new Phrase("BALANCE",timesRomanBold10));
				cell1.setBorder(Rectangle.NO_BORDER);
				
				PdfPCell cell2 = new PdfPCell(new Phrase(balance,timesRomanNormal10));
				cell2.setBorder(Rectangle.NO_BORDER);
				
				summaryTable.addCell(cell1);
				summaryTable.addCell(cell2);
			}
		}



		String newline = "\n";

		document.add(gvmt_fundsparagraph); 
		document.add(new Paragraph(newline)); 
		document.add(gvmtFundsTable); 
		document.add(new Paragraph(newline)); 

		document.add(sch_fee_paragraph); 
		document.add(new Paragraph(newline)); 
		document.add(schFundsTable); 
		document.add(new Paragraph(newline)); 

		document.add(amount_paid_paragraph); 
		document.add(new Paragraph(newline)); 
		document.add(amntPaidTable); 
		document.add(new Paragraph(newline)); 

		document.add(fee_anaysis_paragraph); 
		document.add(new Paragraph(newline)); 
		document.add(summaryTable); 



		/*****************************************************************
		 *    LOGO
		 *    SCHOOL INFO
		 *    
		 *    FREE EDUCATION FUND & SCHOOL FUND
		 *    OPERATIONS ACCOUNT (OFFICIAL RECEIPT) 
		 *    
		 *    RECEPT NO ***** DATE ***********
		 *    
		 *    
		 *    *********** FREE EDUCATION FUND *************
		 *    NO  Item          Fee(KSH)   Amount Paid (KSH) 
		 *    
		 *    1.  R.M.I         3,000.00     --
		 *    2.  E.W & C       1,500.00     --
		 *    3.  P.E           600.00       --
		 *    4.  Activity      1,000.00     --
		 *    5.  Others(Bread) 1,2000.00    --
		 *    
		 *    ****** SCHOOL FUND **************************
		 *    NO    Item         Fee(KSH)     Date     Amount Paid(KSH) 
		 *    
		 *    1.    Damage       1,200       1/12/920   1,200 
		 *    
		 *    
		 *    AMOUNT PAID
		 *    
		 *    NO Description       Amount (KSH) 
		 *    1. Gvmt_KE            5,000
		 *    2. Fee                1,000  
		 *    3. Damage             1,200    
		 *    
		 *    DESCRIPTION (KSH)     AMOUNT
		 *    TERM FEE            = 8,000
		 *    TOTAL PAID          = 6,000
		 *     
		 *    BALANCE             = 2,000
		 *   
		 *
		 *********************************************************************/





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
	private static final long serialVersionUID = -5648308933583087169L;

}
