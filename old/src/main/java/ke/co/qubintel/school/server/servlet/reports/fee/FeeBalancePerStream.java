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
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.money.FeeBreakdownDAO;
import ke.co.qubintel.school.server.persistence.money.FeeBreakdownDescDAO;
import ke.co.qubintel.school.server.persistence.money.StudentFeeDAO;
import ke.co.qubintel.school.server.persistence.money.TermFeeDAO;
import ke.co.qubintel.school.server.persistence.othermoney.OtherFeeDAO;
import ke.co.qubintel.school.server.persistence.othermoney.StudentOtherFeeDAO;
import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.servlet.finance.StudentBalance;
import ke.co.qubintel.school.server.servlet.reports.PdfUtil;

/**
 * @author peter
 *
 */
public class FeeBalancePerStream extends HttpServlet{

	private Font timesRomanBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private Font timesRomanBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private Font timesRomanNormal10= new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	//private Font timesRomanNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	private Font timesRomanNormal8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

	private Font timesRomanBold12_colored = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);

	private static final String USER_SYSTEM = System.getProperty("user.name");
	private static final String LOGO_PATH = "/home/"+USER_SYSTEM+"/school/logo/logo.png";

	private Document document;
	private PdfWriter writer;

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
		String streamId = StringUtils.trim(request.getParameter("streamId"));
		String threshold = StringUtils.trim(request.getParameter("threshold"));

		if(StringUtils.isBlank(accountId)) {
			accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		}

		if(StringUtils.isBlank(streamId)) {
			streamId = ""; 
		}

		if(StringUtils.isBlank(threshold)) {
			threshold = "5000"; 
		}

		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4, 46, 46, 64, 64);


		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();

			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(accountId, streamId, threshold);  

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}




	}



	/**
	 * 
	 * @param accountId
	 * @param streamId
	 * @param threshold
	 */
	private void populatePDFDocument(String accountId, String streamId, String threshold) {
		try {

			document.open();

			getFeeBalance(accountId, streamId, Integer.valueOf(threshold)); 

			document.close();

		}catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		} 
	}


	/**
	 * 
	 * @param accountId
	 * @param streamId
	 * @param threshold
	 * @throws DocumentException
	 */
	public  void getFeeBalance(String accountId, String streamId, int threshold) throws DocumentException {

		if(studentDAO.getStudentByStream(accountId, streamId) != null) {

			List<Student> studentslist = studentDAO.getStudentByStream(accountId, streamId); 

			studentslist.parallelStream().forEach(student -> {

				StudentBalance balance = new StudentBalance();

				double feeBalance1 = new StudentBalance(accountId, student.getUuid()).build();
				double feeBalance = balance.findBalance(accountId, student.getUuid());

				if( (int)feeBalance > threshold) {
					
					
					
					//add student to list
				}

			});

		}
		
		
		
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
				logoCell.addElement(createImage(LOGO_PATH)); 
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




}
