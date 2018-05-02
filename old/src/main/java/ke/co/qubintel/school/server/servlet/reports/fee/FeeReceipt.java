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
package ke.co.qubintel.school.server.servlet.reports.fee;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.RandomStringUtils;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.money.FeeBreakdownDesc;
import ke.co.qubintel.school.server.bean.money.StudentFee;
import ke.co.qubintel.school.server.bean.money.TermFee;
import ke.co.qubintel.school.server.bean.otherfee.OtherFee;
import ke.co.qubintel.school.server.bean.otherfee.StudentOtherFee;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.money.FeeBreakdownDAO;
import ke.co.qubintel.school.server.persistence.money.FeeBreakdownDescDAO;
import ke.co.qubintel.school.server.persistence.money.StudentFeeDAO;
import ke.co.qubintel.school.server.persistence.money.TermFeeDAO;
import ke.co.qubintel.school.server.persistence.othermoney.OtherFeeDAO;
import ke.co.qubintel.school.server.persistence.othermoney.StudentOtherFeeDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.quartz.WriteToFile;
import ke.co.qubintel.school.server.servlet.finance.FeeConstants;
import ke.co.qubintel.school.server.servlet.finance.StudentBalance;
import ke.co.qubintel.school.server.servlet.reports.PdfUtil;
import ke.co.qubintel.school.server.servlet.util.Timeit;

/**
 * 
 * http://127.0.0.1:8080/school/school/feeReceipt 
 * http://127.0.0.1:8080/school/school/feeReceipt?accountId=E3CDC578-37BA-4CDB-B150-DAB0409270CD&studentId=B3D6957B-0DAE-4E1B-A244-09C33F6FEF80
 * 
 * http://127.0.0.1:8080/school/school/feeReceipt?accountId=a423d80a-3855-4008-b69f-88bb4382bf8a&studentId=b49ae551-ce88-4424-9c78-6b6f681cfe78
 * 
 * 
 * 
 * @author peter
 *
 */
public class FeeReceipt extends HttpServlet{

	private Font timesRomanBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private Font timesRomanBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private Font timesRomanNormal10= new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	//private Font timesRomanNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	private Font timesRomanNormal8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
	
	private Font timesRomanBold12_colored = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
	
	private static final String LOGO_PATH = WriteToFile.LOGO_PATH;

	private Document document;
	private PdfWriter writer;
	private ByteArrayOutputStream pdf_baos = new ByteArrayOutputStream();

	private Logger logger;

	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;

	private static StudentFeeDAO studentFeeDAO;
	private static OtherFeeDAO otherFeeDAO;
	private static StudentOtherFeeDAO studentOtherFeeDAO;
	//private static RevertedMoneyDAO revertedMoneyDAO;
	private static FeeBreakdownDAO feeBreakdownDAO;
	private static FeeBreakdownDescDAO feeBreakdownDescDAO;

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
		///revertedMoneyDAO = RevertedMoneyDAO.getInstance();
		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
		feeBreakdownDescDAO = FeeBreakdownDescDAO.getInstance();

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

		String accountId = StringUtils.trim(request.getParameter("accountId"));
		String studentId = StringUtils.trim(request.getParameter("studentId"));

		/*if(StringUtils.isBlank(accountId)) {
			accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		}

		if(StringUtils.isBlank(studentId)) {
			studentId = "D961EF8B-1F5E-40BD-8F6B-3FD878C61691"; 
		}*/

		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4, 46, 46, 64, 64);


		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();

			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

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

			PdfWriter.getInstance(document, pdf_baos); 
			document.open();

			generateReport(accountId, studentId);
			
			document.close();
			
			//save the document in the file system
			//saveToFileSystem();

		}catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		} 
	}

	/**
	 * 
	 * @param document
	 */
	public void saveToFileSystem() {
		
		String destination = "/home/peter/data/sample.pdf"; 
	    File targetFile = new File(destination); 
	    
	    byte[] pdfBytes = pdf_baos.toByteArray();
		
		try {
			
			OutputStream output = new FileOutputStream(targetFile);
			output.write(pdfBytes);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
			output.close();
			
			
		} catch (IOException e) {  
			e.printStackTrace();
		}
		
	}
	
	

	private void generateReport(String accountId, String studentId) throws DocumentException {

		//BaseColor baseColorWhite = new BaseColor(255,255,255);//while
		BaseColor baseColor = new BaseColor(117,229,210);//#75e5d2
		//BaseColor baseColorShadow = new BaseColor(0,255,119);//#00FF77

		Account account = accountDAO.getAccountById(accountId);
		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

		SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);  

		Locale locale = new Locale("en","KE"); 
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		int gokeTotal = 0;
		int otherFeeTotal = 0;
		int paidTotal = 0;

		PdfPTable schoolTable = new PdfPTable(2); 
		schoolTable.setWidthPercentage(100);  
		schoolTable.setWidths(new int[]{70,30});  

		PdfPCell logoCell = new PdfPCell();
		logoCell.addElement(createImage(LOGO_PATH+account.getLogo())); 
		logoCell.setBorder(Rectangle.NO_BORDER); 
		logoCell.setHorizontalAlignment(Element.ALIGN_CENTER); 

		String sch_info = account.getName()+"\n"
				+ "Motto: " + account.getMotto()+"\n"
				+ "Website: " + account.getWebsite()+"\n" 
				+ "Email: " + account.getEmail()+"\n"
				+ "Mobile: " + account.getMobile()+"\n"
				+ "P.O. BOX: " + account.getAddress() + " - " + account.getTown();


		PdfPCell schoolInfoCell = new PdfPCell(new Phrase(sch_info,timesRomanBold12)); 
		schoolInfoCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		//schoolInfoCell.setBackgroundColor(baseColor);
		schoolInfoCell.setBorder(Rectangle.NO_BORDER);

		schoolTable.addCell(schoolInfoCell);
		schoolTable.addCell(logoCell);
		
		

		String receiptNo = RandomStringUtils.randomAlphabetic(10); 
		String printDate = format.format(new Date());


		PdfPTable titleTable = new PdfPTable(2); 
		titleTable.setWidthPercentage(100);  
		titleTable.setWidths(new int[]{60,40}); 

		String title = "FREE EDUCATION & BASIC SCHOOL FUNDS\nOPERATIONS ACCOUNT (OFFICIAL RECEIPT)\nTERM: " + sysConfig.getTerm() +" YEAR: " + sysConfig.getYear();

		PdfPCell titleCell = new PdfPCell(new Phrase(title, timesRomanNormal10)); 
		titleCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		//titleCell.setBackgroundColor(baseColor);
		titleCell.setBorder(Rectangle.NO_BORDER);
		
		String receipt_and_date = "RECEPT NO: " +receiptNo + "\nPINTED ON : " +printDate;

		PdfPCell titleCell1 = new PdfPCell(new Phrase(receipt_and_date,timesRomanNormal10)); 
		titleCell1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		//titleCell1.setBackgroundColor(baseColor);
		titleCell1.setBorder(Rectangle.NO_BORDER);

		titleTable.addCell(titleCell);
		titleTable.addCell(titleCell1);
		
		document.add(schoolTable); 
		document.add(new Paragraph(Chunk.NEWLINE)); 
		document.add(titleTable); 
		document.add(new Paragraph(Chunk.NEWLINE)); 



		PdfPTable studentInfoTable = new PdfPTable(1); 
		studentInfoTable.setWidthPercentage(83);  
		studentInfoTable.setWidths(new int[]{83}); 

		Student student = studentDAO.getStudentById(accountId, studentId);


		String name = student.getFirstname() + " " + student.getMiddlename() + " " + student.getLastname();

		PdfPCell studentInfoCell = new PdfPCell(new Phrase("Name: " + name,timesRomanBold10)); 
		studentInfoCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
		//studentInfoCell.setBackgroundColor(baseColor);
		studentInfoCell.setBorder(Rectangle.NO_BORDER);

		PdfPCell studentRegNoCell = new PdfPCell(new Phrase("RegNo: " + student.getRegNo(),timesRomanBold10)); 
		studentRegNoCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
		//studentRegNoCell.setBackgroundColor(baseColor);
		studentRegNoCell.setBorder(Rectangle.NO_BORDER);

		studentInfoTable.addCell(studentInfoCell);
		studentInfoTable.addCell(studentRegNoCell);


		Paragraph gvmt_fundsparagraph = new Paragraph("Free Education Fund",timesRomanBold12_colored);
		gvmt_fundsparagraph.setAlignment(Element.ALIGN_CENTER);

		PdfPTable gvmtFundsTable = new PdfPTable(3); 
		gvmtFundsTable.setWidthPercentage(83);  
		gvmtFundsTable.setWidths(new int[]{8,25,25});   

		PdfPCell gvmtFunds_count_Cell = new PdfPCell(new Phrase("No",timesRomanBold10)); 
		gvmtFunds_count_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		gvmtFunds_count_Cell.setBackgroundColor(baseColor);
		gvmtFunds_count_Cell.setBorder(Rectangle.NO_BORDER);

		PdfPCell gvmtFunds_item_Cell = new PdfPCell(new Phrase("Item",timesRomanBold10)); 
		gvmtFunds_item_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		gvmtFunds_item_Cell.setBackgroundColor(baseColor);
		gvmtFunds_item_Cell.setBorder(Rectangle.NO_BORDER);

		PdfPCell gvmtFunds_amnt_Cell = new PdfPCell(new Phrase("Amount Paid (KSH)",timesRomanBold10)); 
		gvmtFunds_amnt_Cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		gvmtFunds_amnt_Cell.setBackgroundColor(baseColor);
		gvmtFunds_amnt_Cell.setBorder(Rectangle.NO_BORDER);

		gvmtFundsTable.addCell(gvmtFunds_count_Cell);
		gvmtFundsTable.addCell(gvmtFunds_item_Cell);
		gvmtFundsTable.addCell(gvmtFunds_amnt_Cell);

		if(feeBreakdownDAO.getFeeBreakdown(accountId, FeeConstants.GVMT_MONEY_CODE, sysConfig.getTerm(), sysConfig.getYear(), 
				FeeConstants.GVMT_MONEY_STATUS_ACTIVE) != null) {

			String feeBreakdownId = feeBreakdownDAO.getFeeBreakdown(accountId, FeeConstants.GVMT_MONEY_CODE, sysConfig.getTerm(), sysConfig.getYear(), 
					FeeConstants.GVMT_MONEY_STATUS_ACTIVE).getUuid();

			List<FeeBreakdownDesc> feeBreakdownDescList = feeBreakdownDescDAO.getFeeBreakdownDescList(accountId, feeBreakdownId);

			if(studentFeeDAO.getStudentFee(accountId, studentId, FeeConstants.GVMT_MONEY_CODE, sysConfig.getTerm(), sysConfig.getYear()) != null) {
				int count = 1;
				for(FeeBreakdownDesc gokefee : feeBreakdownDescList) {

					PdfPCell countCell = new PdfPCell(new Phrase(""+count,timesRomanNormal8));
					countCell.setBorder(Rectangle.NO_BORDER);

					String description = gokefee.getFeeDescription().substring(0, Math.min(gokefee.getFeeDescription().length(), 20));

					PdfPCell refNoCell = new PdfPCell(new Phrase(""+description,timesRomanNormal8));
					refNoCell.setBorder(Rectangle.NO_BORDER);

					PdfPCell dateCell2 = new PdfPCell(new Phrase(""+nf.format(gokefee.getAmount()),timesRomanNormal8));
					dateCell2.setBorder(Rectangle.NO_BORDER);

					gokeTotal += gokefee.getAmount();

					gvmtFundsTable.addCell(countCell);
					gvmtFundsTable.addCell(refNoCell);
					gvmtFundsTable.addCell(dateCell2);

					count++;

				}

			}
		}



		//TODO

		Paragraph sch_fee_paragraph = new Paragraph("Other Payments",timesRomanBold12_colored);
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

				otherFeeTotal += otherFee.getAmount();

				schFundsTable.addCell(countCell);
				schFundsTable.addCell(refNoCell);
				schFundsTable.addCell(amountCell);
				schFundsTable.addCell(dateCell);


				count++;
			}


		}



		//TODO

		Paragraph amount_paid_paragraph = new Paragraph("Fee Payment History",timesRomanBold12_colored);
		amount_paid_paragraph.setAlignment(Element.ALIGN_CENTER);

		PdfPTable amntPaidTable = new PdfPTable(5);
		amntPaidTable.setWidthPercentage(83); //73  
		amntPaidTable.setWidths(new int[]{8,15,15,15,20});  

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

				String ref = fee.getTransactionId().substring(0, Math.min(fee.getTransactionId().length(), 16));
				PdfPCell refNoCell = new PdfPCell(new Phrase(""+ref,timesRomanNormal8));
				refNoCell.setBorder(Rectangle.NO_BORDER);

				PdfPCell modeCell = new PdfPCell(new Phrase(""+fee.getPayMode(),timesRomanNormal8));
				modeCell.setBorder(Rectangle.NO_BORDER);

				PdfPCell amountCell = new PdfPCell(new Phrase(""+nf.format(fee.getAmountPaid()),timesRomanNormal8));
				amountCell.setBorder(Rectangle.NO_BORDER);

				PdfPCell dateCell = new PdfPCell(new Phrase(""+format.format(fee.getDatePaid()),timesRomanNormal8));
				dateCell.setBorder(Rectangle.NO_BORDER);

				paidTotal += fee.getAmountPaid();

				amntPaidTable.addCell(countCell);
				amntPaidTable.addCell(refNoCell);
				amntPaidTable.addCell(modeCell);
				amntPaidTable.addCell(amountCell);
				amntPaidTable.addCell(dateCell);

				count++;
			}



		}





		//TODO
		Paragraph fee_anaysis_paragraph = new Paragraph("Fee Payment Summary",timesRomanBold12_colored);
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


		String termFee = "";

		if(termFeeDAO.getFee(accountId, sysConfig.getTerm(), sysConfig.getYear()) != null) {
			TermFee fee = termFeeDAO.getFee(accountId, sysConfig.getTerm(), sysConfig.getYear());

			if(StringUtils.equals(account.getIsBoarding(), "1")) {//1 = boarding only

				termFee = nf.format(fee.getBoaderAmount()); 

			}else if(StringUtils.equals(account.getIsBoarding(), "0")) {//0 = day only

				termFee = nf.format(fee.getDayAmount()); 

			}else if(StringUtils.equals(account.getIsBoarding(), "2")) {//2 = day and boarding

				termFee = "Boarding: " + nf.format(fee.getBoaderAmount()) + " , Day: " + nf.format(fee.getDayAmount());

			}

		}


		double totalpaid =  paidTotal;
		String totalPaid = nf.format(totalpaid); 


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


		//TODO
		document.add(studentInfoTable); 
		//document.add(new Paragraph(Chunk.NEWLINE)); 

		document.add(gvmt_fundsparagraph); 
		//document.add(new Paragraph(Chunk.NEWLINE)); 
		document.add(gvmtFundsTable); 

		Paragraph paragraph = new Paragraph("TOTAL:   " + nf.format(gokeTotal),timesRomanBold10);
		paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
		paragraph.setIndentationLeft(238);
		paragraph.setIndentationRight(20);

		document.add(paragraph); 
		//document.add(new Paragraph(Chunk.NEWLINE));  

		document.add(sch_fee_paragraph); 
		//document.add(new Paragraph(Chunk.NEWLINE)); 
		document.add(schFundsTable); 

		Paragraph paragraph2 = new Paragraph("TOTAL:   " + nf.format(otherFeeTotal),timesRomanBold10);
		paragraph2.setAlignment(Element.ALIGN_JUSTIFIED);
		paragraph2.setIndentationLeft(218);
		paragraph2.setIndentationRight(20);

		document.add(paragraph2); 
		//document.add(new Paragraph(Chunk.NEWLINE)); 

		document.add(amount_paid_paragraph); 
		//document.add(new Paragraph(Chunk.NEWLINE)); 
		document.add(amntPaidTable); 

		Paragraph paragraph3 = new Paragraph("TOTAL:   " + nf.format(paidTotal),timesRomanBold10);
		paragraph3.setAlignment(Element.ALIGN_JUSTIFIED);
		paragraph3.setIndentationLeft(230);
		paragraph3.setIndentationRight(20);

		document.add(paragraph3); 
		//document.add(new Paragraph(Chunk.NEWLINE)); 

		document.add(fee_anaysis_paragraph); 
		//document.add(new Paragraph(Chunk.NEWLINE));  
		document.add(summaryTable); 



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
	private static final long serialVersionUID = -5648308933583087169L;

}
