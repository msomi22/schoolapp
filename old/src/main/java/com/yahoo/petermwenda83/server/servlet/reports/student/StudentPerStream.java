/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.student;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.classroom.ClassDAO;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.reports.PdfUtil;
import com.yahoo.petermwenda83.server.servlet.util.Timeit;

/**
 * @author peter
 *
 */
public class StudentPerStream extends HttpServlet{
	
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
		String uuid = StringUtils.trim(request.getParameter("uuid"));//class or stream
		String decisionFlag = StringUtils.trim(request.getParameter("decisionFlag"));

		
		
		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4, 46, 46, 64, 64);


		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();

			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(accountId,uuid,decisionFlag);  

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		
		
	}
	
	
	private void populatePDFDocument(String accountId, String uuid, String decisionFlag) {

		Timeit.code(() -> compute(accountId,uuid,decisionFlag));

	}
	
	private void compute(String accountId, String uuid, String decisionFlag) {
		try {

			document.open();

			generateReport(accountId,uuid,decisionFlag);

			document.close();

		}catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		} 
	}
	
	
	
	
	

	private void generateReport(String accountId, String uuid, String decisionFlag) throws DocumentException{
		
		
		



		if(StringUtils.equals(decisionFlag, "1")) {//class
			
			List<Student>  students = new ArrayList<>();//copy students from each stream into this list
			
			streamDAO.getStreamList(accountId, streamDAO.getStream(accountId, uuid).getClassRoomId()).forEach(stm -> {
				
				List<Student> activeStudents = studentDAO.getStudentByStream(accountId, stm.getUuid())
						.parallelStream()
						.filter(student -> "1".equals(student.getIsActive()))
						.collect(Collectors.toList());
				     //copy 'activeStudents' list into  'students' list
				
			});
			
			

			for(Student student : students) {

				student.getRegNo();
				student.getFirstname();
				student.getMiddlename();
				student.getLastname();


			}
			
			

		}else if(StringUtils.equals(decisionFlag, "0")){//stream 

			List<Student> activeStudents = studentDAO.getStudentByStream(accountId, uuid).
					parallelStream()
					.filter(student -> "1".equals(student.getIsActive()))
					.collect(Collectors.toList());



			for(Student student : activeStudents) {

				student.getRegNo();
				student.getFirstname();
				student.getMiddlename();
				student.getLastname();


			}

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
	private static final long serialVersionUID = 4062444598011716875L;

}
