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
package ke.co.qubintel.school.server.servlet.reports.student;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.io.output.ByteArrayOutputStream;
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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.persistence.classroom.ClassDAO;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.quartz.WriteToFile;
import ke.co.qubintel.school.server.servlet.reports.PdfUtil;
import ke.co.qubintel.school.server.servlet.util.Timeit;

/**
 * 
 *  http://localhost:8080/school/school/studentPerStream?accountId=b83e9b89-0d52-4191-a6bf-acf501267e2e1&uuid=4DA86139-6A72-4089-8858-6A3A613FDFE6&decisionFlag=0
 * 
 * 
 * @author peter
 *
 */
public class StudentPerStream extends HttpServlet{

	private Font timesRomanBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private Font timesRomanBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private Font timesRomanNormal10= new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	//private Font timesRomanNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	private Font timesRomanNormal8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

	//private Font timesRomanBold12_colored = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);

	private static final String LOGO_PATH = WriteToFile.LOGO_PATH;

	private Document document;
	private PdfWriter writer;

	private Logger logger;

	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static StreamDAO streamDAO;
	private static ClassDAO classDAO;


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
		streamDAO = StreamDAO.getInstance();
		classDAO = ClassDAO.getInstance();

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
		String streamId = StringUtils.trim(request.getParameter("uuid"));//class or stream
		String decisionFlag = StringUtils.trim(request.getParameter("decisionFlag"));


		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4.rotate(), 46, 46, 64, 64);


		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();

			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(accountId,streamId,decisionFlag);  

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}



	}


	private void populatePDFDocument(String accountId, String streamId, String decisionFlag) {

		Timeit.code(() -> compute(accountId,streamId,decisionFlag));

	}

	private void compute(String accountId, String streamId, String decisionFlag) {
		try {

			document.open();

			generateReport(accountId,streamId,decisionFlag);

			document.close();

		}catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		} 
	}






	private void generateReport(String accountId, String streamId, String decisionFlag) throws DocumentException{

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
		//headerTable.setHeaderRows(1); 
		//headerTable.isSkipFirstHeader();

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

		
		
		
		PdfPTable studentTable = new PdfPTable(11);   
		studentTable.setWidthPercentage(100); 
		studentTable.setWidths(new int[]{6,10,12,12,12,10,6,8,8,8,8}); 
		studentTable.setHeaderRows(1); 
		studentTable.isSkipFirstHeader();
        //#-regNo-firstname-middlename-lastname-admclass-currentclass-gender-----12
		
		PdfPCell countCell = new PdfPCell(new Paragraph("#",timesRomanBold10));
		countCell.setBackgroundColor(baseColor);
		countCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell regNoCell = new PdfPCell(new Paragraph("Reg-No",timesRomanBold10));
		regNoCell.setBackgroundColor(baseColor);
		regNoCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell fnameCell = new PdfPCell(new Paragraph("Firstname",timesRomanBold10));
		fnameCell.setBackgroundColor(baseColor);
		fnameCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell mnameCell = new PdfPCell(new Paragraph("Middlename",timesRomanBold10));
		mnameCell.setBackgroundColor(baseColor);
		mnameCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell lnameCell = new PdfPCell(new Paragraph("Lastname",timesRomanBold10));
		lnameCell.setBackgroundColor(baseColor);
		lnameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell reg_classCell = new PdfPCell(new Paragraph("Reg-Class",timesRomanBold10));
		reg_classCell.setBackgroundColor(baseColor);
		reg_classCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		
		PdfPCell genderCell = new PdfPCell(new Paragraph("Gender",timesRomanBold10));
		genderCell.setBackgroundColor(baseColor);
		genderCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell box1Cell = new PdfPCell(new Paragraph(" ",timesRomanBold10));
		box1Cell.setBackgroundColor(baseColor);
		box1Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell box2Cell = new PdfPCell(new Paragraph(" ",timesRomanBold10));
		box2Cell.setBackgroundColor(baseColor);
		box2Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell box3Cell = new PdfPCell(new Paragraph(" ",timesRomanBold10));
		box3Cell.setBackgroundColor(baseColor);
		box3Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell box4Cell = new PdfPCell(new Paragraph(" ",timesRomanBold10));
		box4Cell.setBackgroundColor(baseColor);
		box4Cell.setHorizontalAlignment(Element.ALIGN_LEFT);

		studentTable.addCell(countCell);
		studentTable.addCell(regNoCell);
		studentTable.addCell(fnameCell);
		studentTable.addCell(mnameCell);
		studentTable.addCell(lnameCell);
		studentTable.addCell(reg_classCell);
		studentTable.addCell(genderCell);
		studentTable.addCell(box1Cell);
		studentTable.addCell(box2Cell);
		studentTable.addCell(box3Cell);
		studentTable.addCell(box4Cell);


		String classname = "";
		String streamname = "";
		String message = "";

		if(StringUtils.equals(decisionFlag, "1")) {//class
			
			classname = classDAO.getClassRoom(accountId, streamDAO.getStream(accountId, streamId).getClassRoomId()).getDescription();
			//TODO
			message = classname;
			
			List<Student>  students = new ArrayList<>();//copy students from each stream into this list

			streamDAO.getStreamList(accountId, streamDAO.getStream(accountId, streamId).getClassRoomId()).forEach(stm -> {

				List<Student> activeStudents = studentDAO.getStudentByStream(accountId, stm.getUuid())
						.parallelStream()
						.filter(student -> "1".equals(student.getIsActive()))
						.collect(Collectors.toList());
				//copy 'activeStudents' list into  'students' list
				//Collections.copy(students, activeStudents);  
				students.addAll(activeStudents);

			});



			if(!students.isEmpty()) {

				int scount = 1;
				for(Student student : students) {
					
					String reg_stream = streamDAO.getStream(accountId, student.getRegStream()).getDescription();

					studentTable.addCell(new Paragraph(" " + scount, timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getRegNo(), timesRomanNormal10));
					
					studentTable.addCell(new Paragraph(student.getFirstname(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getMiddlename(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getLastname(), timesRomanNormal10));
					
					studentTable.addCell(new Paragraph(reg_stream, timesRomanNormal10));
					
					studentTable.addCell(new Paragraph(student.getGender().toUpperCase(), timesRomanNormal10));
					
					studentTable.addCell(new Paragraph(" ", timesRomanNormal10));
					studentTable.addCell(new Paragraph(" ", timesRomanNormal10));
					studentTable.addCell(new Paragraph(" ", timesRomanNormal10));
					studentTable.addCell(new Paragraph(" ", timesRomanNormal10));
					
					scount++;

				}
			
				
			}else {
				document.add(new Paragraph("No students to display " ,timesRomanNormal8)); 
			}


			


		}else if(StringUtils.equals(decisionFlag, "0")){//stream 
			
			streamname = streamDAO.getStream(accountId, streamId).getDescription();
			//String classnam = classDAO.getClassRoom(accountId, streamDAO.getStream(accountId, uuid).getClassRoomId()).getDescription();
			//TODO
			message =  streamname;

			List<Student> activeStudents = studentDAO.getStudentByStream(accountId, streamId).
					parallelStream()
					.filter(student -> "1".equals(student.getIsActive()))
					.collect(Collectors.toList());

			if(!activeStudents.isEmpty()) {

				int scount = 1;
				for(Student student : activeStudents) {
					
					String reg_stream = streamDAO.getStream(accountId, student.getRegStream()).getDescription();

					studentTable.addCell(new Paragraph(" " + scount, timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getRegNo(), timesRomanNormal10));
					
					studentTable.addCell(new Paragraph(student.getFirstname(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getMiddlename(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getLastname(), timesRomanNormal10));
					
					studentTable.addCell(new Paragraph(reg_stream, timesRomanNormal10));
					
					studentTable.addCell(new Paragraph(student.getGender().toUpperCase(), timesRomanNormal10));
					
					studentTable.addCell(new Paragraph(" ", timesRomanNormal10));
					studentTable.addCell(new Paragraph(" ", timesRomanNormal10));
					studentTable.addCell(new Paragraph(" ", timesRomanNormal10));
					studentTable.addCell(new Paragraph(" ", timesRomanNormal10));
					
					scount++;

				}
				
				
				
			}else {
				document.add(new Paragraph("No students to display " ,timesRomanNormal8)); 
			}



		}else {

			document.add(new Paragraph("Invalid decisionFlag" ,timesRomanNormal8)); 

		}

		document.add(headerTable);

		document.add(new Paragraph("\n " + message + " Students List \n\n" ,timesRomanBold12)); 
		
		document.add(studentTable); 






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
	private static final long serialVersionUID = 4062444598011716875L;

}
