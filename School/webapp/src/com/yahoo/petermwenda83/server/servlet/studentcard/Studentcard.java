/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.studentcard;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.schoolaccount.Miscellanous;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.MiscellanousDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.result.PdfUtil;

/** 
 * @author peter
 *
 */
public class Studentcard extends HttpServlet{

	private Document document;
	private PdfWriter writer;

	private Logger logger;

	private static RoomDAO roomDAO;
	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static MiscellanousDAO miscellanousDAO;

	private String USER= "";
	private String photopath ="";
	private String logopath ="";

	private String[] studentCheck = {};  




	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		roomDAO = RoomDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		miscellanousDAO = MiscellanousDAO.getInstance();

		logger = Logger.getLogger(this.getClass());
		USER = System.getProperty("user.name");
		logopath = "/home/"+USER+"/school/logo/logo.png";
		//photopath = "/home/"+USER+"/school/logo/photos/996.png";
		photopath = "/home/"+USER+"/school/logo/photos/";
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/pdf");
       

		studentCheck = request.getParameterValues("studentCheck[]");
		String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
		
		String fileName = new StringBuffer(StringUtils.trimToEmpty("StudentCard")) 
				.append("_")
				.append(schooluuid)
				.append(".pdf")
				.toString();
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);


		document = new Document(PageSize.A4, 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());

			PdfUtil event = new PdfUtil();
			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(logopath,photopath,schooluuid,studentCheck);

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}



	}


	private void populatePDFDocument(String logopath, String photopath,String schooluuid, String[] studentCheck) {

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
		SchoolAccount account = accountDAO.get(schooluuid);

		String school_name = account.getSchoolName();
		String school_motto = account.getSchoolMotto();
		String school_address = "         P.O BOX "+account.getPostalAddress()+"\n         " + account.getTown();
		String school_mobile = account.getMobile();
		String school_email = account.getEmail();
		String school_website = account.getWebsite();

		String descr =  "This Card is a property of " + school_name.toUpperCase() + "."
				+ "The card is only valid within the date specified.\n"
				+ "If found, please report to the school administration or contact the addrress provided herein.";


		school_name = school_name.substring(0, Math.min(school_name.length(), 40));
		school_motto = school_motto.substring(0, Math.min(school_motto.length(), 47));
		school_address = school_address.substring(0, Math.min(school_address.length(), 40));
		school_mobile = school_mobile.substring(0, Math.min(school_mobile.length(), 13));
		school_email = school_email.substring(0, Math.min(school_email.length(), 35));
		school_website = school_website.substring(0, Math.min(school_website.length(), 27));

		String student_name = "";
		String student_admno = "";
		String student_regdate = "";
		String card_expire = "";
		
		


		try {

			
			document.open();
			
			int topR = 176;int topG  = 176;int topB  = 176;
			int bodyR  = 176;int bodyG  = 176;int bodyB  = 176;
			int txtR = 255,txtG =255,txtB = 255;
			
			if(miscellanousDAO.getKey(schooluuid, "CARD_TOP") != null ){
				
				Miscellanous cardColorTop = miscellanousDAO.getKey(schooluuid, "CARD_TOP");
				
				String cardTop = cardColorTop.getValue();
				
				topR = Integer.parseInt(cardTop.substring(0, 2), 16);
				topG = Integer.parseInt(cardTop.substring(2, 4), 16);
				topB = Integer.parseInt(cardTop.substring(4, 6), 16);
				
			}
			
			if(miscellanousDAO.getKey(schooluuid, "CARD_BODY") != null){
				Miscellanous cardColorBody = miscellanousDAO.getKey(schooluuid, "CARD_BODY");
				
				String cardBody = cardColorBody.getValue();
				bodyR = Integer.parseInt(cardBody.substring(0, 2), 16);
				bodyG = Integer.parseInt(cardBody.substring(2, 4), 16);
				bodyB = Integer.parseInt(cardBody.substring(4, 6), 16);
			}
			
			if(miscellanousDAO.getKey(schooluuid, "CARD_TXT_COLOR") != null){
				Miscellanous cardTxtColor = miscellanousDAO.getKey(schooluuid, "CARD_TXT_COLOR");
				
				String cardtext = cardTxtColor.getValue();
				txtR = Integer.parseInt(cardtext.substring(0, 2), 16);
				txtG = Integer.parseInt(cardtext.substring(2, 4), 16);
				txtB = Integer.parseInt(cardtext.substring(4, 6), 16);
			}
			
			
			BaseColor color_magenta = new BaseColor(176,196,222);
			
			BaseColor color_blue = new BaseColor(topR,topG,topB); 
			
			BaseColor color_orange_red = new BaseColor(bodyR,bodyG,bodyB); 
			
			

			//start student fetch info


			int count = 1;
			
			for(String str : studentCheck){
				Student student = new Student();
				student = studentDAO.getStudentByuuid(schooluuid, str);
				
				
				Paragraph emptyline = new Paragraph(("                              "));


				float card_left_right_height = 160f;
				float card_content_height = 110f;
				float card_top_height = 28f;
				float card_bottom_height = 22f;
				
				
				PdfPTable cardTable = new PdfPTable(2);  
				cardTable.setWidthPercentage(100); 
				cardTable.setWidths(new int[]{100,100}); 

				PdfPTable frontSidetop = new PdfPTable(1);  
				frontSidetop.setWidthPercentage(100); 
				frontSidetop.setWidths(new int[]{100});  

				PdfPTable backSidetop = new PdfPTable(1);  
				backSidetop.setWidthPercentage(100); 
				backSidetop.setWidths(new int[]{100});  

				PdfPTable frontContentTable = new PdfPTable(3);  
				frontContentTable.setWidthPercentage(100); 
				frontContentTable.setWidths(new int[]{90,70,140});  

				PdfPTable backContentTable = new PdfPTable(1);  
				backContentTable.setWidthPercentage(100); 
				backContentTable.setWidths(new int[]{100});  

				PdfPTable commonBottom = new PdfPTable(1);  
				commonBottom.setWidthPercentage(100); 
				commonBottom.setWidths(new int[]{100});  
				
				PdfPCell card_leftCell = new PdfPCell(); 
				card_leftCell.setBackgroundColor(color_magenta);
				card_leftCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				card_leftCell.setFixedHeight(card_left_right_height);
				
				
				PdfPCell card_rightCell = new PdfPCell();
				card_rightCell.setBackgroundColor(color_magenta);
				card_rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				card_rightCell.setFixedHeight(card_left_right_height);

                 //BaseColor.WHITE
				PdfPCell front_top_Text =new PdfPCell(new Paragraph(school_name + "\n Student ID Card",  
						new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(txtR,txtG,txtB))));
				front_top_Text.setBackgroundColor(color_blue);
				front_top_Text.setHorizontalAlignment(Element.ALIGN_CENTER);
				front_top_Text.setFixedHeight(card_top_height);

				PdfPCell back_top_Text =new PdfPCell(new Paragraph(school_name + "\n Student ID Card", 
						new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(txtR,txtG,txtB))));
				back_top_Text.setBackgroundColor(color_blue);
				back_top_Text.setHorizontalAlignment(Element.ALIGN_CENTER);
				back_top_Text.setFixedHeight(card_top_height);

				PdfPCell commonBottom_text = new PdfPCell(new Paragraph(school_website, 
						new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC, new BaseColor(txtR,txtG,txtB))));
				commonBottom_text.setBackgroundColor(color_blue);
				commonBottom_text.setHorizontalAlignment(Element.ALIGN_CENTER);
				commonBottom_text.setFixedHeight(card_bottom_height);

				PdfPCell student_photo_and_info = new PdfPCell();
				student_photo_and_info.setBackgroundColor(color_orange_red);
				student_photo_and_info.setHorizontalAlignment(Element.ALIGN_RIGHT);
				student_photo_and_info.setFixedHeight(card_content_height);
				
				PdfPCell backContent = new PdfPCell();
				backContent.setBackgroundColor(color_orange_red);
				backContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
				backContent.setFixedHeight(card_content_height);


				PdfPCell school_logo = new PdfPCell();
				school_logo.setBackgroundColor(color_orange_red);
				school_logo.setHorizontalAlignment(Element.ALIGN_CENTER);

				PdfPCell schoolInfo = new PdfPCell();
				schoolInfo.setBackgroundColor(color_orange_red);
				schoolInfo.setHorizontalAlignment(Element.ALIGN_RIGHT);
				
				

				Paragraph motto = new Paragraph("Motto : ",  new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD));
				Paragraph mottoInfo = new Paragraph(school_motto,  new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.ITALIC));

				Phrase phonePhrase = new Phrase();
				phonePhrase.add(new Chunk("Mobile : ",  new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD)));
				phonePhrase.add(new Chunk(school_mobile,  new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.ITALIC)));


				Paragraph address = new Paragraph("Address :\n",  new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD));
				address.setAlignment(Element.ALIGN_LEFT);

				Paragraph addressInfo = new Paragraph(school_address,new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.ITALIC));			
				addressInfo.setAlignment(Element.ALIGN_LEFT);


				Paragraph emailPara = new Paragraph("Email : ",  new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD));
				Paragraph emailInfo = new Paragraph(school_email,  new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.ITALIC));

				frontSidetop.addCell(front_top_Text); 
				backSidetop.addCell(back_top_Text); 
				commonBottom.addCell(commonBottom_text); 

				schoolInfo.addElement(motto);
				schoolInfo.addElement(mottoInfo);
				schoolInfo.addElement(phonePhrase);
				schoolInfo.addElement(address);		 	
				schoolInfo.addElement(addressInfo);			
				schoolInfo.addElement(emailPara);
				schoolInfo.addElement(emailInfo);
				
				int addyear = 1;
				String classroomid = student.getClassRoomUuid();
				ClassRoom classroom = roomDAO.getroom(schooluuid, classroomid);
				
				if(StringUtils.contains(classroom.getRoomName(), "1")){
					addyear = 4;
				}
				if(StringUtils.contains(classroom.getRoomName(), "2")){
					addyear = 3;
				}
				if(StringUtils.contains(classroom.getRoomName(), "3")){
					addyear = 2;
				}
				if(StringUtils.contains(classroom.getRoomName(), "4")){
					addyear = 1;
				}
				
				student_name = student.getFirstname() + " " + student.getLastname();
				student_admno = student.getAdmno();
				student_regdate = dateFormatter.format(student.getAdmissionDate());
				Calendar cal = Calendar.getInstance();
				cal.setTime(student.getAdmissionDate()); 
				cal.add(Calendar.YEAR, addyear); 
				card_expire = dateFormatter.format(cal.getTime()); 

				student_name = student_name.substring(0, Math.min(student_name.length(), 17));
				student_admno = student_admno.substring(0, Math.min(student_admno.length(), 6));
				student_regdate = student_regdate.substring(0, Math.min(student_regdate.length(), 18));
				card_expire = card_expire.substring(0, Math.min(card_expire.length(), 18));
				

				//right
				String passpartPath = photopath+student_admno+".png";
				
				student_photo_and_info.addElement(createImage(passpartPath,1));
				//school_logo.addElement(new Paragraph(" ")); 
				school_logo.addElement(new Paragraph(" ")); 
				school_logo.addElement(createImage(logopath,0));



				//student method
				Paragraph namePara = new Paragraph("Name : ",  new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD));
				namePara.add(new Chunk(student_name,  new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.ITALIC)));
				
				Paragraph admPara = new Paragraph("AdmNo : ",  new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD));
				admPara.add(new Chunk(student_admno,  new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.ITALIC)));
				
				Paragraph admDatePara = new Paragraph("AdmDate : ",  new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD));
				admDatePara.add(new Chunk(student_regdate,  new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.ITALIC)));
				
				Paragraph validPara = new Paragraph("Expire : ",  new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD));
				validPara.add(new Chunk(card_expire,  new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.ITALIC)));
				
				student_photo_and_info.addElement(namePara);
				student_photo_and_info.addElement(admPara);
				student_photo_and_info.addElement(admDatePara);
				student_photo_and_info.addElement(validPara);
				
				frontContentTable.addCell(student_photo_and_info);
				frontContentTable.addCell(school_logo);
				frontContentTable.addCell(schoolInfo);
				
				
				
				PdfContentByte canvas = writer.getDirectContent();
				String code = student_admno;
				Barcode128 code128 = new Barcode128();
				code128.setBaseline(-1);
				code128.setSize(12);
				code128.setCode(code);
				code128.setBarHeight(10f);
				code128.setCodeType(Barcode128.CODE128);

				Image code128Image = code128.createImageWithBarcode(canvas, null, null);
				code128Image.scaleAbsolute(235f,350f); 


				backContent.addElement(new Chunk(descr,  new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC)));
				backContent.addElement(code128Image); 

				backContentTable.addCell(backContent);
				
				card_leftCell.addElement(frontSidetop);
				card_leftCell.addElement(frontContentTable);
				card_leftCell.addElement(commonBottom);
				

				card_rightCell.addElement(backSidetop); 
				card_rightCell.addElement(backContentTable); 
				card_rightCell.addElement(commonBottom);

				cardTable.addCell(card_leftCell);
				cardTable.addCell(card_rightCell);
				
				document.add(cardTable); 
				document.add(emptyline);

				
				
				if (count % 4 == 0) {
					//document.newPage();
				}

				count++;

			}

			document.close();


		}
		catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}




	}


	/**
	 * @param realPath
	 * @return
	 */
	private Element createImage(String realPath, int type) {
		Image img = null;

		try {

			File file = new File(realPath);
			if(!file.exists()){
				realPath = getServletContext().getRealPath("/images/default.jpg");
				
			}
			
			BufferedImage bufferedImage = ImageIO.read(new File(realPath));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			if(type == 1){//student
				ImageIO.write(resize(bufferedImage, 70,90), "png", baos);//w,h

			}else{

				ImageIO.write(resize(bufferedImage, 60,60), "png", baos);
			}


			img = Image.getInstance(baos.toByteArray());
			img.scaleAbsolute(50f,50f); 
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
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
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
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3818014581773444030L;

}
