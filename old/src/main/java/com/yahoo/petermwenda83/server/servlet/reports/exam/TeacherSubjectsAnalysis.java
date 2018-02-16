/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.exam;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
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
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.persistence.classroom.ClassDAO;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.ClassMeanDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.exam.YearlyMeanDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.servlet.reports.PdfUtil;
import com.yahoo.petermwenda83.server.servlet.reports.ReportUtil;
import com.yahoo.petermwenda83.server.servlet.util.Timeit;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class TeacherSubjectsAnalysis extends HttpServlet{


	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;
	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static StreamDAO streamDAO;
	private static ExamDAO examDAO;
	private static YearlyMeanDAO yearlyMeanDAO;
	private static ClassMeanDAO classMeanDAO;
	private static ClassDAO classDAO;
	private static PrimaryDAO primaryDAO;

	private Font timesRomanNormal10 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
	private Font timesRomanBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

	//private Font timesRomanMormal8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private Font timesRomanBold8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);

	private Font timesRomanNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	private Font timesRomanBold6 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);

	private Document document;
	private PdfWriter writer;

	private Logger logger;

	private static String[] exams = {"D50E6399-B913-42F2-A5B6-F0D4BAAF9571", "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A" };//, "16C4BF00-941C-40E4-9891-272D5F0979A1"

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
		perfomanceDAO = PerfomanceDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		//examDAO = ExamDAO.getInstance();
		yearlyMeanDAO = YearlyMeanDAO.getInstance();
		classMeanDAO = ClassMeanDAO.getInstance();
		classDAO = ClassDAO.getInstance();
		primaryDAO = PrimaryDAO.getInstance();

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

		String accountId = "";
		String streamId = "";
		String term = "";
		String year = "";
		String classroomId = "";
		String paper123Id = "C3915245-00EE-4EF4-9898-ACE59683DD60";
		String saveMean = "";


		response.setContentType("application/pdf");

		String examType = "";

		examType = StringUtils.trimToEmpty(request.getParameter("examType"));

		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4.rotate(), 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();

			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			examType = StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE) ? ReportUtil.EXAM_TYPE : "";	
			paper123Id = StringUtils.equalsIgnoreCase(examType, ReportUtil.EXAM_TYPE) ? ReportUtil.PAPER123ID : "";


			populatePDFDocument(accountId,streamId,classroomId,term,year,examType, paper123Id, saveMean);

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}


	/**
	 * @param args
	 */
	public void populatePDFDocument(String accountId, String streamId ,String classroomId, String term ,
			String year ,String examType, String paper123Id, String saveMean) {
		Timeit.code(() -> compute(accountId,streamId,classroomId,term,year,examType,paper123Id,saveMean));
	}

	/**
	 * @param args
	 */
	public  void compute(String accountId, String streamId ,String classroomId, String term ,
			String year ,String examType, String paper123Id, String saveMean) {

		try {

			document.open();

			generateReport(accountId, streamId, classroomId, term, year, examType, paper123Id, saveMean);

			document.close();

		}catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}  


	}


	/** TODO
	 * pick subjects
	 * pick stream/class
	 * pick exams(1-3)
	 * 
	 * submit
	 * 
	 * rank students
	 * 
	 * 
	 * 1. 
	 * - best student per subject
	 * - number of A's B's etc per stream , and total.
	 * - number of A's B's etc per gender , and total.
	 * 
	 * 
	 *  2.
	 * - per subject analysis
	 *   * subject
	 *   * A, B, C etc Entry, points, mean, grade , teacher,
	 *  Stream
	 *  Total
	 *   *
	 *   
	 *   
	 *   
	 *   1) class performance analysis 
	 *        - per stream ( How many A's , B's bra bra) 
	 *        Duration: 
	 *                  back end and front end - 2 days
	 *                  
	 *        
	 *        - by gender ( How many A's , B's bra bra) 
	 *        Duration:
	 *                 back end and front end - 2 days
	 *                 
	 *        
	 *   2) Subject performance analysis 
	 *       - How many A's , B's bra bra
	 *       Duration:
	 *                 back end and front end - 2 days
	 *                 
	 *  -Testing 1 day.
	 *  -Total Duration: 7 days.               
	 *                 
	 *       
	 *       
	 *   
	 *   
	 * @throws DocumentException 
	 * 
	 * 
	 * 
	 */


	private void generateReport(String accountId, String streamId, String classroomId, String term, String year,
			String examType, String paper123Id, String saveMean) throws DocumentException {


		//BaseColor baseColorWhite = new BaseColor(255,255,255);//while
		BaseColor baseColor = new BaseColor(117,229,210);//#75e5d2
		//BaseColor baseColorShadow = new BaseColor(0,255,119);//#00FF77
		Account account = accountDAO.getAccountById(accountId);


		document.add(new Paragraph("PDF "));  
		
		
		PdfPTable rankingTable = new PdfPTable(25);   
		rankingTable.setWidthPercentage(100); 
		//#,Stream,A,A-,B+,B,B-,C+,C,C-,D+,D,D-,E,X,Y,Z,Entry,Total,Mean,MG.
		rankingTable.setWidths(new int[]{8,20,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,12,12,10,10}); 
		rankingTable.setHeaderRows(1); 
		rankingTable.isSkipFirstHeader();
		
		
		
		
		
		



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
	private static final long serialVersionUID = 6009672931352020766L;

}
