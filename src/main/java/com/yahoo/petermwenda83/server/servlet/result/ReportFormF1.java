/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.result;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import com.itextpdf.text.BadElementException;
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
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.persistence.HibernateUtil;
import com.yahoo.petermwenda83.persistence.StorageDAO;
import com.yahoo.petermwenda83.persistence.StorageDAOImpl;

import net.sf.ehcache.Cache;

/**
 * @author peter
 * 
 */
public class ReportFormF1 extends HttpServlet{

	private Font courierBold14 = ExamConstants.courierBold14;
	private Font timesRomanBold7 = ExamConstants.timesRomanBold7;
	private Font timesRomanItalic8 = ExamConstants.timesRomanItalic8;
	private Font timesRomanNormal7 = ExamConstants.timesRomanNormal7;
	private Font timesRomanNormal0 = ExamConstants.timesRomanNormal0;

	private Document document;
	private PdfWriter writer;
	private Cache schoolaccountCache, statisticsCache;

	private Logger logger;
	SysConfig sysConfig;
	String stffID = "";


	GradingSystem gradingSystem;
	private String PDF_SUBTITLE ="";
	private String schoolname = "";
	private String title = "";

	

	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>();
	HashMap<String, String> firstnameHash = new HashMap<String, String>();
	HashMap<String, String> roomHash = new HashMap<String, String>();
	HashMap<String, String> admYearMap = new HashMap<String, String>();
	HashMap<String, String> shameMap = new HashMap<String, String>();
	
	HashMap<String, String> admtermMap = new HashMap<String, String>();
	HashMap<String, Integer> finalyearMap = new HashMap<String, Integer>();
	HashMap<String, Date> admdaterMap = new HashMap<String, Date>();
   


	double score = 0;

	double engscore = 0;
	String engscorestr = "";

	double kswscore = 0;
	String kswscorestr = "";

	double matscore = 0;
	String matscorestr = "";

	double physcore = 0;
	String physcorestr = "";  

	double bioscore = 0;
	String bioscorestr = "";

	double chemscore = 0;
	String chemscorestr = "";

	double bsscore = 0;
	String bsscorestr = "";

	double comscore = 0;
	String comscorestr = "";

	double hscscore = 0;
	String hscscorestr = "";

	double agriscore = 0;
	String agriscorestr = "";

	double geoscore = 0;
	String geoscorestr = "";

	double crescore = 0;
	String crescorestr = "";

	double histscore = 0;
	String histscorestr = "";



	String grade = "";
	String studeadmno = "";
	String firstnamee = "";
	String studename = "";
	String admno = "";
	

	double cat1 = 0;  double cat2  = 0;double endterm  = 0;
	double catgrandscores  = 0;double catmean  = 0;double examcatgrandscore  = 0;


	int position = 1;


	String USER= "";
	String path ="";

	String EndTermOnly = "";
	String EndTermAndC2 = "";
	String EndTermC1AndC2 = "";
	
	private StorageDAO storageDAO;
	private SessionFactory sessionFactory;



	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logger = Logger.getLogger(this.getClass());
		
		sessionFactory = HibernateUtil.getSessionFactory();
		storageDAO = new StorageDAOImpl(sessionFactory); 
		
		USER = System.getProperty("user.name");
		path = "/home/"+USER+"/school/logo/logo.png";
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

		HttpSession session = request.getSession(true);
		response.setContentType("application/pdf");



		Account school = new Account();

		String classroomuuid = "";
		String schoolusername = "";
		String classID = "";
		

		if(session !=null){

			stffID = StringUtils.trimToEmpty(request.getParameter("staffid"));

		}
		classID = StringUtils.trimToEmpty(request.getParameter("classID"));
		//System.out.println("classID="+classID);
		net.sf.ehcache.Element element;
		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (Account) element.getObjectValue();
		}
		

			schoolname = school.getName().toUpperCase()+"\n";
			PDF_SUBTITLE =  "P.O BOX "+school.getAddress()+"\n" 
					+ ""+school.getTown()+" - Kenya\n" 
					+ "" + school.getMobile()+"\n"
					+ "" + school.getEmail()+"\n";

			title = "_____________________________________ \n"
					+ " End of Term Report Card ";

			
			String admYear = "";
			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("yyyy");
			Date admdate;
			


			String fileName = new StringBuffer(StringUtils.trimToEmpty("ReportCards")) 
					.append("_")
					.append(roomHash.get(classroomuuid).replaceAll(" ", "_"))
					.append(".pdf")
					.toString();
			response.setHeader("Content-Disposition", "inline; filename=\""+fileName);


			document = new Document(PageSize.A4, 46, 46, 64, 64);

			try {
				writer = PdfWriter.getInstance(document, response.getOutputStream());

				PdfUtil event = new PdfUtil();
				writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
				writer.setPageEvent(event);

			    populatePDFDocument(school,classroomuuid,classID,path);
				
			} catch (DocumentException e) {
				logger.error("DocumentException while writing into the document");
				logger.error(ExceptionUtils.getStackTrace(e));
			}

	}



	/**
	 * @param path2 
	 * @param pDistinctListGeneral 
	 * @param pDistinctList 
	 * @param classID 
	 * @param classroomuuid 
	 * @param school 
	 * @param statistics 
	 * @param statistics
	 * @param school
	 * @param classroomuuid
	 * @param classID 
	 * @param perfomanceList
	 * @param pDistinctList
	 * @param pDistinctListGeneral 
	 * @param perfomanceListGeneral 
	 * @param realPath
	 */
	private void populatePDFDocument(Account school, String classroomuuid,
			String classID,  String realPath) {


		try {

			document.open();
			document.add(new Paragraph("."));


			PdfPTable prefaceTable = new PdfPTable(2);  
			prefaceTable.setWidthPercentage(100); 
			prefaceTable.setWidths(new int[]{70,130}); 

			Paragraph content = new Paragraph();
			content.add(new Paragraph((schoolname +"") , courierBold14));//
			content.add(new Paragraph((PDF_SUBTITLE +"") , timesRomanItalic8));
			content.add(new Paragraph((title +" \n\n") , courierBold14));

			PdfPCell contentcell = new PdfPCell(content);
			contentcell.setBorder(Rectangle.NO_BORDER); 
			contentcell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			Paragraph preface = new Paragraph();
			preface.add(createImage(realPath));

			Image imgLogo = null;
			try {
				imgLogo = Image.getInstance(realPath);
			} catch (IOException e) {

				e.printStackTrace();
			}

			imgLogo.scalePercent(ExamConstants.LOGO_SCALE); 
			imgLogo.setAlignment(Element.ALIGN_LEFT);

			PdfPCell logo = new PdfPCell();
			logo.addElement(new Chunk(imgLogo,ExamConstants.LOGO_L,ExamConstants.LOGO_R)); // margin left  ,  margin top
			logo.setBorder(Rectangle.NO_BORDER); 
			logo.setHorizontalAlignment(Element.ALIGN_LEFT);

			prefaceTable.addCell(logo); 
			prefaceTable.addCell(contentcell);


			//SimpleDateFormat formatter;
			//formatter = new SimpleDateFormat("dd, MMM yyyy");

			DecimalFormat df = new DecimalFormat("0.00"); 
			df.setRoundingMode(RoundingMode.DOWN);

			DecimalFormat rf = new DecimalFormat("0.0"); 
			rf.setRoundingMode(RoundingMode.HALF_UP);

			DecimalFormat rf2 = new DecimalFormat("0"); 
			rf2.setRoundingMode(RoundingMode.UP);



			// step 5
			document.close();
		}
		catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}



	/** Find deviation from last term
	 * pass thisTermMean following lastTermMean
	 * @param lastTermMean
	 * @param thisTermMean
	 * @return the deviation 
	 */

	private double deviationFinder(double thisTermMean, double lastTermMean){
		double deviation = 0;

		if(lastTermMean == 0){
			deviation = 0;
		}else{
			deviation = thisTermMean - lastTermMean;
		}
		return deviation;
	}

	/** put comment as per deviation
	 * @param deviation
	 * @return teacher comment
	 */
	private String deviationComment(double mean){
		String comment = "";
		DecimalFormat df = new DecimalFormat("0.00"); 
		df.setRoundingMode(RoundingMode.HALF_UP);
		double dev = 0;
		dev = mean;
		if(dev<0){
			//Negative , student nose diving
			double positiveDev = Math.abs(dev);
			String newDev = "-"+df.format(positiveDev);
			comment = " Your deviation is " + newDev;
			//System.out.println(comment);

		}else if(dev>0){
			//positive , student working hard
			comment = " Your deviation is " + df.format(dev);

		}else if(dev == 0){
			//stagnant
			comment = " !";

		}

		return comment;
	}



	/**
	 * @param realPath
	 * @return
	 */
	private Element createImage(String realPath) {
		Image imgLogo = null;

		try {
			imgLogo = Image.getInstance(realPath);
			imgLogo.scaleToFit(200, 200);
			imgLogo.setAlignment(Element.ALIGN_LEFT);

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
	private static final long serialVersionUID = 3513371438433721109L;


}
