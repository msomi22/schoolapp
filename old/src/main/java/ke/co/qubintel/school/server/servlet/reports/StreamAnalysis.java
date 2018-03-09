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
package ke.co.qubintel.school.server.servlet.reports;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicInteger;

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
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.ClassMeanDAO;
import ke.co.qubintel.school.server.persistence.exam.ExamDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.servlet.util.Timeit;

/** /school/streamAnalysis?accountId=b83e9b89-0d52-4191-a6bf-acf501267e2e1&streamId=4DA86139-6A72-4089-8858-6A3A613FDFE6&examId=1_CAT 1
 * 
 * http://localhost:8080/school/school/streamAnalysis?accountId=b83e9b89-0d52-4191-a6bf-acf501267e2e1&streamId=4DA86139-6A72-4089-8858-6A3A613FDFE6&examId=1_CAT%201
 * 
 * @author peter
 *
 */
public class StreamAnalysis extends HttpServlet{

	private static AccountDAO accountDAO;
	private static StreamDAO streamDAO;
	private static ExamDAO examDAO;
	private static ClassMeanDAO classMeanDAO;
	private static SysConfigDAO sysConfigDAO;

	private Font timesRomanNormal10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	private Font timesRomanBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

	private Font timesRomanNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	private Font timesRomanBold6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD);

	private Document document;
	private PdfWriter writer;

	private Logger logger;
	
	private static final String USER_SYSTEM = System.getProperty("user.name");
	private static final String LOGO_PATH = "/home/"+USER_SYSTEM+"/school/logo/logo.png";

	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		accountDAO = AccountDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		examDAO = ExamDAO.getInstance();
		classMeanDAO = ClassMeanDAO.getInstance();
		
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


		String accountId = StringUtils.trimToEmpty(request.getParameter("accountId")); 
		String streamId = StringUtils.trimToEmpty(request.getParameter("streamId")); 
		String examId = StringUtils.trimToEmpty(request.getParameter("examId")); 

		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4, 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();

			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(accountId,streamId,examId);

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}



	}

	/**
	 * 
	 * @param accountId
	 * @param streamId
	 */
	private void populatePDFDocument(String accountId, String streamId, String examId) {


		Timeit.code(() -> compute(accountId,streamId,examId));

	}

	/**
	 * 
	 * @param accountId
	 * @param streamId
	 * @return
	 */
	private void compute(String accountId, String streamId, String examId) {

		try {

			document.open();

			generateReport(accountId, streamId, examId);

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
	 * @throws DocumentException 
	 */
	private void generateReport(String accountId, String streamId, String examId) throws DocumentException {

		//BaseColor baseColorWhite = new BaseColor(255,255,255);//while
		BaseColor baseColor = new BaseColor(117,229,210);//#75e5d2
		//BaseColor baseColorShadow = new BaseColor(0,255,119);//#00FF77
		
		Account account = accountDAO.getAccountById(accountId);
		
		String school = "P.O Box : " + account.getAddress() + " " + account.getTown()+" "
				+ " , Cell : " + account.getMobile() + "\n"
				+ "Website : " + account .getWebsite() + "             EMAIL : " + account.getEmail(); 
		
		PdfPTable headerTable = new PdfPTable(2);
		headerTable.setWidthPercentage(100); 
		headerTable.setWidths(new int[]{70,30});

		PdfPCell logo = new PdfPCell();
		logo.addElement(createImage(LOGO_PATH)); 
		logo.setBorder(Rectangle.NO_BORDER); 
		logo.setHorizontalAlignment(Element.ALIGN_CENTER); 

		PdfPCell schoolInfo = new PdfPCell();
		schoolInfo.setBorder(Rectangle.NO_BORDER); 
		schoolInfo.setHorizontalAlignment(Element.ALIGN_LEFT);  
		schoolInfo.addElement(new Chunk(account.getName().toUpperCase(),timesRomanBold10));
		schoolInfo.addElement(new Chunk(school, timesRomanNormal10));

		headerTable.addCell(schoolInfo); 
		headerTable.addCell(logo);   

		document.add(headerTable);
		
		SysConfig sysConfig = new SysConfig();
		if(sysConfigDAO.getSysConfig(accountId) != null) {
			 sysConfig = sysConfigDAO.getSysConfig(accountId);
		}
		
		
		/*PdfContentByte topLine = writer.getDirectContent();
		topLine.setColorStroke(BaseColor.BLACK);
		topLine.moveTo(45, 463);//start dot, 45 is margin left, 463 is margin top , 
		//the bigger second value the more the point move further from the margin 
		topLine.lineTo(500, 463);
		topLine.closePathStroke();*/
		
		String exam = examId;
		
		if(examDAO.getExam(accountId, examId) != null) {
			//exam = examDAO.getExam(accountId, examId).getDescription().toUpperCase();
		}
		

		Phrase reportTitle = new Phrase();
		reportTitle.add(new Chunk("Classes ranking list for (" + exam +
				") ,  TERM : " + sysConfig.getTerm() + ", YEAR : " + sysConfig.getYear(),  timesRomanBold10));
		document.add(reportTitle);
		document.add(new Paragraph("\n"));
		
		
		if(classMeanDAO.getClassMeanList(accountId, streamId, examId, sysConfig.getTerm(), sysConfig.getYear()).isEmpty()) {
			
			document.add(new Paragraph("Nothing to display! "));  
			
		}else {
			
			
			
			
			
			
			
			PdfPTable rankingTable = new PdfPTable(3);   
			rankingTable.setWidthPercentage(54); 
			rankingTable.setWidths(new int[]{10,15,20}); 
			rankingTable.setHeaderRows(1);  
			rankingTable.isSkipFirstHeader();
			
			PdfPCell countCell = new PdfPCell(new Paragraph("#",timesRomanBold6));
			countCell.setBackgroundColor(baseColor);
			countCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell classCell = new PdfPCell(new Paragraph("Class",timesRomanBold6));
			classCell.setBackgroundColor(baseColor);
			classCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell meanCell = new PdfPCell(new Paragraph("Mean",timesRomanBold6));
			meanCell.setBackgroundColor(baseColor);
			meanCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			rankingTable.addCell(countCell);
			rankingTable.addCell(classCell);
			rankingTable.addCell(meanCell);

			
			
			AtomicInteger count = new AtomicInteger();
			classMeanDAO.getClassMeanList(accountId, streamId, examId, sysConfig.getTerm(), sysConfig.getYear()).forEach(cmean -> {
				
				int c = count.getAndIncrement();
				
				cmean.getClassmean();
				streamDAO.getStream(accountId, cmean.getStreamId()).getDescription(); 
				
				rankingTable.addCell(new Paragraph(c + "" ,timesRomanNormal6));
				rankingTable.addCell(new Paragraph(cmean.getClassmean()+"",timesRomanNormal6));
				rankingTable.addCell(new Paragraph(streamDAO.getStream(accountId, cmean.getStreamId()).getDescription(),timesRomanNormal6));
				
				
				
			});
			
			
			document.add(rankingTable);
			
			
		}
		
		
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


	/**
	 * 
	 */
	private static final long serialVersionUID = 4097395112445194350L;

}
