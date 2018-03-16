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
package ke.co.qubintel.school.server.servlet.reports.exam;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.exam.Perfomance;
import ke.co.qubintel.school.server.bean.subject.Subject;
import ke.co.qubintel.school.server.persistence.classroom.ClassDAO;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.PerfomanceDAO;
import ke.co.qubintel.school.server.persistence.house.HouseDAO;
import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;
import ke.co.qubintel.school.server.quartz.WriteToFile;
import ke.co.qubintel.school.server.servlet.reports.PdfUtil;
import ke.co.qubintel.school.server.servlet.reports.ReportUtil;
import ke.co.qubintel.school.server.servlet.util.Timeit;

/**
 * 
 * @author peter
 *
 */
public class TeacherSubjectsAnalysis extends HttpServlet{


	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;
	private static AccountDAO accountDAO;
	private static StreamDAO streamDAO;
	private static ClassDAO classDAO;

	private static HouseDAO houseDAO;

	private Font timesRomanNormal10 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
	private Font timesRomanBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

	private Font timesRomanNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	private Font timesRomanBold6 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);

	private Document document;
	private PdfWriter writer;

	private Logger logger;

	private static String[] exams = {"D50E6399-B913-42F2-A5B6-F0D4BAAF9571", "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A","16C4BF00-941C-40E4-9891-272D5F0979A1"};//, "16C4BF00-941C-40E4-9891-272D5F0979A1"

	private static final String LOGO_PATH = WriteToFile.LOGO_PATH;


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
		accountDAO = AccountDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		classDAO = ClassDAO.getInstance();

		houseDAO = HouseDAO.getInstance();

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

		//HttpSession session = request.getSession(true);

		String accountId = StringUtils.trimToEmpty(request.getParameter("accountId"));
		String classId = StringUtils.trimToEmpty(request.getParameter("classroom")); 
		String[] examIds= request.getParameterValues("exam");
		exams = examIds;
		String term = StringUtils.trimToEmpty(request.getParameter("term")); 
		String year = StringUtils.trimToEmpty(request.getParameter("year")); 

		/*accountId = "b83e9b89-0d52-4191-a6bf-acf501267e2e1";
		term = "1";
		year = "2018";
		classId = "C143978A-E021-4015-BC67-5A00D6C910D1";*/

		response.setContentType("application/pdf");

		String classname = classDAO.getClassRoom(accountId, classId).getDescription();

		String fileName = classname+"_term_"+term+"_year_"+year+".pdf";  

		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4.rotate(), 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();

			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(accountId,classId,term,year);

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}


	/**
	 * @param args
	 */
	public void populatePDFDocument(String accountId, String classroomId, String term , String year) {
		Timeit.code(() -> compute(accountId,classroomId,term,year));
	}

	/**
	 * @param args
	 */
	public  void compute(String accountId, String classroomId, String term , String year) {

		try {

			document.open();

			generateReport(accountId, classroomId, term, year);

			document.close();

		}catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}  


	}

	/**
	 * 
	 * @param accountId
	 * @param classroomId
	 * @param term
	 * @param year
	 * @throws DocumentException
	 */
	private void generateReport(String accountId, String classroomId, String term, String year) throws DocumentException {

		boolean isPaper123 = false;
		//BaseColor baseColorWhite = new BaseColor(255,255,255);//while
		BaseColor baseColor = new BaseColor(117,229,210);//#75e5d2
		//BaseColor baseColorShadow = new BaseColor(0,255,119);//#00FF77
		Account account = accountDAO.getAccountById(accountId);

		PdfPTable headerTable = new PdfPTable(2);
		headerTable.setWidthPercentage(100); 
		headerTable.setWidths(new int[]{70,30});

		PdfPCell logo = new PdfPCell();
		logo.addElement(createImage(LOGO_PATH+account.getLogo())); 
		logo.setBorder(Rectangle.NO_BORDER); 
		logo.setHorizontalAlignment(Element.ALIGN_CENTER); 

		String school = "P.O Box : " + account.getAddress() + " " + account.getTown()+" "
				+ " , Cell : " + account.getMobile() + "\n"
				+ "Website : " + account .getWebsite() + "             EMAIL : " + account.getEmail(); 

		PdfPCell schoolInfo = new PdfPCell();
		schoolInfo.setBorder(Rectangle.NO_BORDER); 
		schoolInfo.setHorizontalAlignment(Element.ALIGN_LEFT);  
		schoolInfo.addElement(new Chunk(account.getName().toUpperCase(),timesRomanBold10));
		schoolInfo.addElement(new Chunk(school, timesRomanNormal10));

		headerTable.addCell(schoolInfo); 
		headerTable.addCell(logo);   

		document.add(headerTable);

		PdfContentByte topLine = writer.getDirectContent();
		topLine.setColorStroke(BaseColor.BLACK);
		topLine.moveTo(45, 463);//start dot, 45 is margin left, 463 is margin top , 
		//the bigger second value the more the point move further from the margin 
		topLine.lineTo(790, 463);
		topLine.closePathStroke();

		String classname = classDAO.getClassRoom(accountId, classroomId).getDescription();
		String stringExams = ReportUtil.getExamName(accountId, exams,2);

		Phrase reportTitle = new Phrase();
		reportTitle.add(new Chunk(classname + " PERFORMANCE ANALYSIS FOR  TERM : " + term + ", YEAR : " + year,  timesRomanBold10));
		reportTitle.add(new Chunk(" - Exams: " + stringExams ,  timesRomanNormal10));
		document.add(reportTitle);
		document.add(new Paragraph("\n"));


		PdfPTable stream_rankingTable = new PdfPTable(21);   
		stream_rankingTable.setWidthPercentage(100); 
		stream_rankingTable.setWidths(new int[]{8,15,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,12,12,10,10}); 
		stream_rankingTable.setHeaderRows(1); 
		stream_rankingTable.isSkipFirstHeader();

		PdfPTable bygender_rankingTable = new PdfPTable(21);   
		bygender_rankingTable.setWidthPercentage(100); 
		bygender_rankingTable.setWidths(new int[]{8,15,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,12,12,10,10}); 
		bygender_rankingTable.setHeaderRows(1); 
		bygender_rankingTable.isSkipFirstHeader();


		PdfPTable house_rankingTable = new PdfPTable(21);   
		house_rankingTable.setWidthPercentage(100); 
		house_rankingTable.setWidths(new int[]{8,15,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,12,12,10,10}); 
		house_rankingTable.setHeaderRows(1); 
		house_rankingTable.isSkipFirstHeader();

		PdfPCell s_r_countCell = new PdfPCell(new Paragraph("#",timesRomanBold6));
		s_r_countCell.setBackgroundColor(baseColor);
		s_r_countCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_streamCell = new PdfPCell(new Paragraph("Stream",timesRomanBold6));
		s_r_streamCell.setBackgroundColor(baseColor);
		s_r_streamCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_genderCell = new PdfPCell(new Paragraph("Gender",timesRomanBold6));
		s_r_genderCell.setBackgroundColor(baseColor);
		s_r_genderCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_aCell = new PdfPCell(new Paragraph("A",timesRomanBold6));
		s_r_aCell.setBackgroundColor(baseColor);
		s_r_aCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_amCell = new PdfPCell(new Paragraph("A-",timesRomanBold6));
		s_r_amCell.setBackgroundColor(baseColor);
		s_r_amCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_bpCell= new PdfPCell(new Paragraph("B+",timesRomanBold6));
		s_r_bpCell.setBackgroundColor(baseColor);
		s_r_bpCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_bCell = new PdfPCell(new Paragraph("B",timesRomanBold6));
		s_r_bCell.setBackgroundColor(baseColor);
		s_r_bCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_bmCell = new PdfPCell(new Paragraph("B-",timesRomanBold6));
		s_r_bmCell.setBackgroundColor(baseColor);
		s_r_bmCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_cpCell = new PdfPCell(new Paragraph("C+",timesRomanBold6));
		s_r_cpCell.setBackgroundColor(baseColor);
		s_r_cpCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_cCell = new PdfPCell(new Paragraph("C",timesRomanBold6));
		s_r_cCell.setBackgroundColor(baseColor);
		s_r_cCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_cmCell = new PdfPCell(new Paragraph("C-",timesRomanBold6));
		s_r_cmCell.setBackgroundColor(baseColor);
		s_r_cmCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_dpCell = new PdfPCell(new Paragraph("D+",timesRomanBold6));
		s_r_dpCell.setBackgroundColor(baseColor);
		s_r_dpCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_dCell = new PdfPCell(new Paragraph("D",timesRomanBold6));
		s_r_dCell.setBackgroundColor(baseColor);
		s_r_dCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_dmCell = new PdfPCell(new Paragraph("D-",timesRomanBold6));
		s_r_dmCell.setBackgroundColor(baseColor);
		s_r_dmCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_eCell = new PdfPCell(new Paragraph("E",timesRomanBold6));
		s_r_eCell.setBackgroundColor(baseColor);
		s_r_eCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_xCell = new PdfPCell(new Paragraph("X",timesRomanBold6));
		s_r_xCell.setBackgroundColor(baseColor);
		s_r_xCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_yCell = new PdfPCell(new Paragraph("Y",timesRomanBold6));
		s_r_yCell.setBackgroundColor(baseColor);
		s_r_yCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_zCell = new PdfPCell(new Paragraph("Z",timesRomanBold6));
		s_r_zCell.setBackgroundColor(baseColor);
		s_r_zCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_entryCell = new PdfPCell(new Paragraph("Entry",timesRomanBold6));
		s_r_entryCell.setBackgroundColor(baseColor);
		s_r_entryCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_totalCell = new PdfPCell(new Paragraph("Total",timesRomanBold6));
		s_r_totalCell.setBackgroundColor(baseColor);
		s_r_totalCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_meanCell = new PdfPCell(new Paragraph("Mean",timesRomanBold6));
		s_r_meanCell.setBackgroundColor(baseColor);
		s_r_meanCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell s_r_mgCell = new PdfPCell(new Paragraph("MG",timesRomanBold6));
		s_r_mgCell.setBackgroundColor(baseColor);
		s_r_mgCell.setHorizontalAlignment(Element.ALIGN_LEFT);


		stream_rankingTable.addCell(s_r_countCell);
		stream_rankingTable.addCell(s_r_streamCell);
		stream_rankingTable.addCell(s_r_aCell);
		stream_rankingTable.addCell(s_r_amCell);
		stream_rankingTable.addCell(s_r_bpCell);
		stream_rankingTable.addCell(s_r_bCell);
		stream_rankingTable.addCell(s_r_bmCell);
		stream_rankingTable.addCell(s_r_cpCell);
		stream_rankingTable.addCell(s_r_cCell);
		stream_rankingTable.addCell(s_r_cmCell);
		stream_rankingTable.addCell(s_r_dpCell);
		stream_rankingTable.addCell(s_r_dCell);
		stream_rankingTable.addCell(s_r_dmCell);
		stream_rankingTable.addCell(s_r_eCell);
		stream_rankingTable.addCell(s_r_xCell);
		stream_rankingTable.addCell(s_r_yCell);
		stream_rankingTable.addCell(s_r_zCell);
		stream_rankingTable.addCell(s_r_entryCell);
		stream_rankingTable.addCell(s_r_totalCell);
		stream_rankingTable.addCell(s_r_meanCell);
		stream_rankingTable.addCell(s_r_mgCell);


		List<PerformanceBean1> finalExamList = new ArrayList<>();
		Map<String,List<PerformanceBean1>> finalExamMap = new HashMap<>();

		for(Subject subject : subjectDAO.getSubjects(accountId)) {

			String exam1 = "";
			String exam2 = "";
			String exam3 = "";

			if(exams.length == 1) {
				exam1 = exams[0];

				List<Perfomance> list1 = new ArrayList<>();

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year) != null) {
					list1 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year);
				}

				List<PerformanceBean1> examList1 = new ArrayList<>();

				examList1 = CommonLogic.subjectAnalyzer(list1, isPaper123);  

				finalExamList = CommonLogic.combineExams(examList1, null, null, 1, subject.getUuid(), accountId, classroomId);

			}if(exams.length == 2) {
				exam1 = exams[0];
				exam2 = exams[1];

				List<Perfomance> list1 = new ArrayList<>();
				List<Perfomance> list2 = new ArrayList<>();

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year) != null) {
					list1 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year);
				}

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam2, subject.getUuid(), classroomId, term, year) != null) {
					list2 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam2, subject.getUuid(), classroomId, term, year);
				}

				List<PerformanceBean1> examList1 = new ArrayList<>();
				List<PerformanceBean1> examList2 = new ArrayList<>();

				examList1 = CommonLogic.subjectAnalyzer(list1, isPaper123); 
				examList2 = CommonLogic.subjectAnalyzer(list2, isPaper123); 

				finalExamList = CommonLogic.combineExams(examList1, examList2, null, 2, subject.getUuid(), accountId, classroomId);


			}if(exams.length == 3) {
				exam1 = exams[0];
				exam2 = exams[1];
				exam3 = exams[2];

				List<Perfomance> list1 = new ArrayList<>();
				List<Perfomance> list2 = new ArrayList<>();
				List<Perfomance> list3 = new ArrayList<>();

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year) != null) {
					list1 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year);
				}

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam2, subject.getUuid(), classroomId, term, year) != null) {
					list2 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam2, subject.getUuid(), classroomId, term, year);
				}

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam3, subject.getUuid(), classroomId, term, year) != null) {
					list3 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam3, subject.getUuid(), classroomId, term, year);
				}

				List<PerformanceBean1> examList1 = new ArrayList<>();
				List<PerformanceBean1> examList2 = new ArrayList<>();
				List<PerformanceBean1> examList3 = new ArrayList<>();

				examList1 = CommonLogic.subjectAnalyzer(list1, isPaper123); 
				examList2 = CommonLogic.subjectAnalyzer(list2, isPaper123); 
				examList3 = CommonLogic.subjectAnalyzer(list3, isPaper123); 

				finalExamList = CommonLogic.combineExams(examList1, examList2, examList3, 3, subject.getUuid(), accountId, classroomId);


			}

			if(!finalExamList.isEmpty()) {
				finalExamMap.put(subject.getUuid(), finalExamList); 
			}


		}

		
		List<String> listOfStreams = new ArrayList<>(); 
		streamDAO.getStreamList(accountId, classroomId).stream().forEach(stream -> {
			listOfStreams.add(stream.getDescription());
		});

		String[] streamlist = listOfStreams.toArray(new String[listOfStreams.size()]);
		streamlist = append(streamlist, "Total");

		int ca_=0,cam_=0,cbp_=0,cb_=0,cbm_=0,ccp_=0,cc_=0,ccm_=0,cdp_=0,cd_=0,cdm_=0, ce_=0;
		int entry_1=0,total_1=0;
		int c1 = 1;
		for(int i=0;i<streamlist.length;i++) {

			String ca="",cam="",cbp="",cb="",cbm="",ccp="",cc="",ccm="",cdp="",cd="",cdm="", ce="";
			String entry1="",total1="";
			ca = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "A");
			cam = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "A-");
			cbp = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "B+");
			cb = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "B");
			cbm = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "B-");
			ccp = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "C+");
			cc = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "C");
			ccm = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "C-");
			cdp = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "D+");
			cd = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "D");
			cdm = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "D-");
			ce = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "E");

			entry1 = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "Entry");
			total1 = CommonLogic.streamAnalyzer(finalExamMap, streamlist[i], classroomId, accountId, "Total");

			if(ca.length() > 0) {
				ca_ += Integer.valueOf(ca);
			}

			if(cam.length() > 0) {
				cam_ += Integer.valueOf(cam);
			}

			if(cbp.length() > 0) {
				cbp_ += Integer.valueOf(cbp);
			}

			if(cb.length() > 0) {
				cb_ += Integer.valueOf(cb);
			}

			if(cbm.length() > 0) {
				cbm_ += Integer.valueOf(cbm);
			}

			if(ccp.length() > 0) {
				ccp_ += Integer.valueOf(ccp);
			}

			if(cc.length() > 0) {
				cc_ += Integer.valueOf(cc);
			}

			if(ccm.length() > 0) {
				ccm_ += Integer.valueOf(ccm);
			}

			if(cdp.length() > 0) {
				cdp_ += Integer.valueOf(cdp);
			}

			if(cd.length() > 0) {
				cd_ += Integer.valueOf(cd);
			}

			if(cdm.length() > 0) {
				cdm_ += Integer.valueOf(cdm);
			}

			if(ce.length() > 0) {
				ce_ += Integer.valueOf(ce);
			}


			if(entry1.length() > 0) {
				entry_1 += Integer.valueOf(entry1);
			}
			if(total1.length() > 0) {
				total_1 += Integer.valueOf(total1);
			}

			double mn =0,tmn =0;
			if(total1.length() > 0 && entry1.length() > 0) {
				mn = Double.valueOf(total1) / Double.valueOf(entry1);
			}

			if(i < (streamlist.length) -1) {

				stream_rankingTable.addCell(new Paragraph("" + c1,timesRomanNormal6)); 
				stream_rankingTable.addCell(new Paragraph("" + streamlist[i],timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + ca,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cam,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cbp,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cb,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cbm,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + ccp,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cc,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + ccm,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cdp,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cd,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cdm,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + ce,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + entry1,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + total1,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + ReportUtil.df2.format(mn),timesRomanNormal6));
				String grade = CommonLogic.getGrade((int)mn, accountId);
				stream_rankingTable.addCell(new Paragraph("" + grade,timesRomanNormal6));


			}else if(i == (streamlist.length) -1) {

				tmn = (double)total_1 / (double)entry_1;


				stream_rankingTable.addCell(new Paragraph("" + c1,timesRomanNormal6)); 
				stream_rankingTable.addCell(new Paragraph("" + streamlist[i],timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + ca_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cam_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cbp_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cb_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cbm_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" +  ccp_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cc_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + ccm_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cdp_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cd_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + cdm_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + ce_,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + entry_1,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + total_1,timesRomanNormal6));
				stream_rankingTable.addCell(new Paragraph("" + ReportUtil.df2.format(tmn),timesRomanNormal6)); 
				String grade = CommonLogic.getGrade((int)tmn, accountId);
				stream_rankingTable.addCell(new Paragraph("" + grade,timesRomanNormal6));



			}




			c1++;

		}



		bygender_rankingTable.addCell(s_r_countCell);
		bygender_rankingTable.addCell(s_r_genderCell);
		bygender_rankingTable.addCell(s_r_aCell);
		bygender_rankingTable.addCell(s_r_amCell);
		bygender_rankingTable.addCell(s_r_bpCell);
		bygender_rankingTable.addCell(s_r_bCell);
		bygender_rankingTable.addCell(s_r_bmCell);
		bygender_rankingTable.addCell(s_r_cpCell);
		bygender_rankingTable.addCell(s_r_cCell);
		bygender_rankingTable.addCell(s_r_cmCell);
		bygender_rankingTable.addCell(s_r_dpCell);
		bygender_rankingTable.addCell(s_r_dCell);
		bygender_rankingTable.addCell(s_r_dmCell);
		bygender_rankingTable.addCell(s_r_eCell);
		bygender_rankingTable.addCell(s_r_xCell);
		bygender_rankingTable.addCell(s_r_yCell);
		bygender_rankingTable.addCell(s_r_zCell);
		bygender_rankingTable.addCell(s_r_entryCell);
		bygender_rankingTable.addCell(s_r_totalCell);
		bygender_rankingTable.addCell(s_r_meanCell);
		bygender_rankingTable.addCell(s_r_mgCell);



		String[] headers = {"Male","Female","Total"};

		ca_=0;cam_=0;cbp_=0;cb_=0;cbm_=0;ccp_=0;cc_=0;ccm_=0;cdp_=0;cd_=0;cdm_=0; ce_=0;

		entry_1=0;total_1=0; 

		int c2 = 1;
		for(int i=0;i<3;i++) {

			String ca="",cam="",cbp="",cb="",cbm="",ccp="",cc="",ccm="",cdp="",cd="",cdm="", ce="";
			String entry="",total="";
			ca = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "A");
			cam = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "A-");
			cbp = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "B+");
			cb = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "B");
			cbm = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "B-");
			ccp = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "C+");
			cc = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "C");
			ccm = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "C-");
			cdp = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "D+");
			cd = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "D");
			cdm = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "D-");
			ce = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "E");

			entry = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "Entry");
			total = CommonLogic.genderAnalyzer(finalExamMap, headers[i], classroomId, accountId, "Total");

			if(ca.length() > 0) {
				ca_ += Integer.valueOf(ca);
			}

			if(cam.length() > 0) {
				cam_ += Integer.valueOf(cam);
			}

			if(cbp.length() > 0) {
				cbp_ += Integer.valueOf(cbp);
			}

			if(cb.length() > 0) {
				cb_ += Integer.valueOf(cb);
			}

			if(cbm.length() > 0) {
				cbm_ += Integer.valueOf(cbm);
			}

			if(ccp.length() > 0) {
				ccp_ += Integer.valueOf(ccp);
			}

			if(cc.length() > 0) {
				cc_ += Integer.valueOf(cc);
			}

			if(ccm.length() > 0) {
				ccm_ += Integer.valueOf(ccm);
			}

			if(cdp.length() > 0) {
				cdp_ += Integer.valueOf(cdp);
			}

			if(cd.length() > 0) {
				cd_ += Integer.valueOf(cd);
			}

			if(cdm.length() > 0) {
				cdm_ += Integer.valueOf(cdm);
			}

			if(ce.length() > 0) {
				ce_ += Integer.valueOf(ce);
			}


			if(entry.length() > 0) {
				entry_1 += Integer.valueOf(entry);
			}
			if(total.length() > 0) {
				total_1 += Integer.valueOf(total);
			}


			double mn =0,tmn =0;
			if(total.length() > 0 && entry.length() > 0) {
				mn = Double.valueOf(total) / Double.valueOf(entry);
			}


			if(i < (streamlist.length) -1) {

				bygender_rankingTable.addCell(new Paragraph("" + c2,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + headers[i],timesRomanNormal6)); 
				bygender_rankingTable.addCell(new Paragraph("" + ca,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cam,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cbp,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cb,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cbm,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + ccp,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cc,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + ccm,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cdp,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cd,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cdm,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + ce,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + entry,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + total,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + ReportUtil.df2.format(mn),timesRomanNormal6));
				String grade = CommonLogic.getGrade((int)mn, accountId);
				bygender_rankingTable.addCell(new Paragraph("" + grade,timesRomanNormal6));


			}else if(i == (streamlist.length) -1) {

				tmn = (double)total_1 / (double)entry_1;

				bygender_rankingTable.addCell(new Paragraph("" + c2,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + headers[i],timesRomanNormal6)); 
				bygender_rankingTable.addCell(new Paragraph("" + ca_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cam_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cbp_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cb_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cbm_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + ccp_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cc_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + ccm_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cdp_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cd_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + cdm_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + ce_,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + entry_1,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + total_1,timesRomanNormal6));
				bygender_rankingTable.addCell(new Paragraph("" + ReportUtil.df2.format(tmn),timesRomanNormal6));
				String grade = CommonLogic.getGrade((int)tmn, accountId);
				bygender_rankingTable.addCell(new Paragraph("" + grade,timesRomanNormal6));

			}



			c2++;
		}



		house_rankingTable.addCell(s_r_countCell);
		house_rankingTable.addCell(s_r_genderCell);
		house_rankingTable.addCell(s_r_aCell);
		house_rankingTable.addCell(s_r_amCell);
		house_rankingTable.addCell(s_r_bpCell);
		house_rankingTable.addCell(s_r_bCell);
		house_rankingTable.addCell(s_r_bmCell);
		house_rankingTable.addCell(s_r_cpCell);
		house_rankingTable.addCell(s_r_cCell);
		house_rankingTable.addCell(s_r_cmCell);
		house_rankingTable.addCell(s_r_dpCell);
		house_rankingTable.addCell(s_r_dCell);
		house_rankingTable.addCell(s_r_dmCell);
		house_rankingTable.addCell(s_r_eCell);
		house_rankingTable.addCell(s_r_xCell);
		house_rankingTable.addCell(s_r_yCell);
		house_rankingTable.addCell(s_r_zCell);
		house_rankingTable.addCell(s_r_entryCell);
		house_rankingTable.addCell(s_r_totalCell);
		house_rankingTable.addCell(s_r_meanCell);
		house_rankingTable.addCell(s_r_mgCell);

		
		List<String> listOfhouse = new ArrayList<>(); 
		houseDAO.getHouseList(accountId).stream().forEach(house -> { 
			listOfhouse.add(house.getHouseName());
		});

		String[] houselist = listOfhouse.toArray(new String[listOfhouse.size()]);
		houselist = append(houselist, "Total");
        
		
		//TODO
		int h_a=0,h_am=0,h_bp=0,h_b=0,h_bm=0,h_cp=0,h_c=0,h_cm=0,h_dp=0,h_d=0,h_dm=0, h_e=0; 
		int h_entry=0; int h_total=0;
		int house_c = 1;
		for(int i=0;i<houselist.length;i++) {
			
			String ha="",ham="",hbp="",hb="",hbm="",hcp="",hc="",hcm="",hdp="",hd="",hdm="", he="";
			String entry="",total="";
			
			List<PerformanceBean1> performanceBean1List = CommonLogic.houseAnalyzer(finalExamMap, houselist[i], accountId);
			
			ha = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "A");
			ham = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "A-");
			hbp = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "B+");
			hb = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "B");
			hbm = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "B-");
			hcp = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "C+");
			hc = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "C");
			hcm = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "C-");
			hdp = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "D+");
			hd = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "D");
			hdm = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "D-");
			he = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "E");
			
			entry = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "Entry");
			total = CommonLogic.analyzeHouse(performanceBean1List, accountId, houselist[i], "Total");
			
			if(ha.length() > 0) {
				h_a += Integer.valueOf(ha);
			}

			if(ham.length() > 0) {
				h_am += Integer.valueOf(ham);
			}

			if(hbp.length() > 0) {
				h_bp += Integer.valueOf(hbp);
			}

			if(hb.length() > 0) {
				h_b += Integer.valueOf(hb);
			}
			
			if(hbm.length() > 0) {
				h_bm += Integer.valueOf(hbm);
			}

			if(hcp.length() > 0) {
				h_cp += Integer.valueOf(hcp);
			}

			if(hc.length() > 0) {
				h_c += Integer.valueOf(hc);
			}

			if(hcm.length() > 0) {
				h_cm += Integer.valueOf(hcm);
			}

			if(hdp.length() > 0) {
				h_dp += Integer.valueOf(hdp);
			}

			if(hd.length() > 0) {
				h_d += Integer.valueOf(hd);
			}

			if(hdm.length() > 0) {
				h_dm += Integer.valueOf(hdm);
			}

			if(he.length() > 0) {
				h_e += Integer.valueOf(he);
			}


			if(entry.length() > 0) {
				h_entry += Integer.valueOf(entry);
			}
			if(total.length() > 0) {
				h_total += Integer.valueOf(total);
			}
			
			
			double mn =0,tmn =0;
			if(total.length() > 0 && entry.length() > 0) {
				mn = Double.valueOf(total) / Double.valueOf(entry);
			}
			
			
			if(i < (streamlist.length) -1) {
;
				house_rankingTable.addCell(new Paragraph("" + house_c,timesRomanNormal6)); 
				house_rankingTable.addCell(new Paragraph("" + houselist[i],timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + ha,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + ham,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + hbp,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + hb,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + hbm,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + hcp,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + hc,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + hcm,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + hdp,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + hd,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + hdm,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + he,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + entry,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + total,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + ReportUtil.df2.format(mn),timesRomanNormal6));
				String grade = CommonLogic.getGrade((int)mn, accountId);
				house_rankingTable.addCell(new Paragraph("" + grade,timesRomanNormal6));


			}else if(i == (streamlist.length) -1) {
				
				tmn = (double)h_total / (double)h_entry;


				house_rankingTable.addCell(new Paragraph("" + house_c,timesRomanNormal6)); 
				house_rankingTable.addCell(new Paragraph("" + houselist[i],timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_a,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_am,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_bp,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_b,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_bm,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_cp,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_c,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_cm,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_dp,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_d,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_dm,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_e,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("-",timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_entry,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + h_total,timesRomanNormal6));
				house_rankingTable.addCell(new Paragraph("" + ReportUtil.df2.format(tmn),timesRomanNormal6)); 
				String grade = CommonLogic.getGrade((int)tmn, accountId);
				house_rankingTable.addCell(new Paragraph("" + grade,timesRomanNormal6));
            


			}

			house_c++;
	
		}




		document.add(new Paragraph("Stream-Performance Analysis", timesRomanBold10));  
		document.add(new Paragraph("\n")); 

		document.add(stream_rankingTable); 
		document.add(new Paragraph("\n")); 

		document.add(new Paragraph("Gender-Performance Analysis", timesRomanBold10));  
		document.add(new Paragraph("\n")); 

		document.add(bygender_rankingTable);

		document.add(new Paragraph("House-Performance Analysis", timesRomanBold10));  
		document.add(new Paragraph("\n")); 

		document.add(house_rankingTable);




		/**
		 * Subject 
		 */


		PdfPTable subject_rankingTable = new PdfPTable(18);   

		PdfPCell sub_r_countCell = new PdfPCell(new Paragraph("#",timesRomanBold6));
		sub_r_countCell.setBackgroundColor(baseColor);
		sub_r_countCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_streamCell = new PdfPCell(new Paragraph("Stream",timesRomanBold6));
		sub_r_streamCell.setBackgroundColor(baseColor);
		sub_r_streamCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_aCell = new PdfPCell(new Paragraph("A",timesRomanBold6));
		sub_r_aCell.setBackgroundColor(baseColor);
		sub_r_aCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_amCell = new PdfPCell(new Paragraph("A-",timesRomanBold6));
		sub_r_amCell.setBackgroundColor(baseColor);
		sub_r_amCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_bpCell= new PdfPCell(new Paragraph("B+",timesRomanBold6));
		sub_r_bpCell.setBackgroundColor(baseColor);
		sub_r_bpCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_bCell = new PdfPCell(new Paragraph("B",timesRomanBold6));
		sub_r_bCell.setBackgroundColor(baseColor);
		sub_r_bCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_bmCell = new PdfPCell(new Paragraph("B-",timesRomanBold6));
		sub_r_bmCell.setBackgroundColor(baseColor);
		sub_r_bmCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_cpCell = new PdfPCell(new Paragraph("C+",timesRomanBold6));
		sub_r_cpCell.setBackgroundColor(baseColor);
		sub_r_cpCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_cCell = new PdfPCell(new Paragraph("C",timesRomanBold6));
		sub_r_cCell.setBackgroundColor(baseColor);
		sub_r_cCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_cmCell = new PdfPCell(new Paragraph("C-",timesRomanBold6));
		sub_r_cmCell.setBackgroundColor(baseColor);
		sub_r_cmCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_dpCell = new PdfPCell(new Paragraph("D+",timesRomanBold6));
		sub_r_dpCell.setBackgroundColor(baseColor);
		sub_r_dpCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_dCell = new PdfPCell(new Paragraph("D",timesRomanBold6));
		sub_r_dCell.setBackgroundColor(baseColor);
		sub_r_dCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_dmCell = new PdfPCell(new Paragraph("D-",timesRomanBold6));
		sub_r_dmCell.setBackgroundColor(baseColor);
		sub_r_dmCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_eCell = new PdfPCell(new Paragraph("E",timesRomanBold6));
		sub_r_eCell.setBackgroundColor(baseColor);
		sub_r_eCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_entryCell = new PdfPCell(new Paragraph("Entry",timesRomanBold6));
		sub_r_entryCell.setBackgroundColor(baseColor);
		sub_r_entryCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_mpCell = new PdfPCell(new Paragraph("M.Points",timesRomanBold6));
		sub_r_mpCell.setBackgroundColor(baseColor);
		sub_r_mpCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_mgCell = new PdfPCell(new Paragraph("M.Grade",timesRomanBold6));
		sub_r_mgCell.setBackgroundColor(baseColor);
		sub_r_mgCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sub_r_subtCell = new PdfPCell(new Paragraph("Sub.Teacher",timesRomanBold6));
		sub_r_subtCell.setBackgroundColor(baseColor);
		sub_r_subtCell.setHorizontalAlignment(Element.ALIGN_LEFT);




		document.add(new Paragraph("Subject Analysis", timesRomanBold10));  

		/**
		 * 
		 */

		int subc = 1;
		for(Subject subject : subjectDAO.getSubjects(accountId)) {

			String exam1 = "";
			String exam2 = "";
			String exam3 = "";

			finalExamList = new ArrayList<>();

			if(exams.length == 1) {
				exam1 = exams[0];

				List<Perfomance> list1 = new ArrayList<>();

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year) != null) {
					list1 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year);
				}

				List<PerformanceBean1> examList1 = new ArrayList<>();

				examList1 = CommonLogic.subjectAnalyzer(list1, isPaper123);  

				finalExamList = CommonLogic.combineExams(examList1, null, null, 1, subject.getUuid(), accountId, classroomId);

			}if(exams.length == 2) {
				exam1 = exams[0];
				exam2 = exams[1];

				List<Perfomance> list1 = new ArrayList<>();
				List<Perfomance> list2 = new ArrayList<>();

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year) != null) {
					list1 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year);
				}

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam2, subject.getUuid(), classroomId, term, year) != null) {
					list2 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam2, subject.getUuid(), classroomId, term, year);
				}

				List<PerformanceBean1> examList1 = new ArrayList<>();
				List<PerformanceBean1> examList2 = new ArrayList<>();

				examList1 = CommonLogic.subjectAnalyzer(list1, isPaper123); 
				examList2 = CommonLogic.subjectAnalyzer(list2, isPaper123); 

				finalExamList = CommonLogic.combineExams(examList1, examList2, null, 2, subject.getUuid(), accountId, classroomId);


			}if(exams.length == 3) {
				exam1 = exams[0];
				exam2 = exams[1];
				exam3 = exams[2];

				List<Perfomance> list1 = new ArrayList<>();
				List<Perfomance> list2 = new ArrayList<>();
				List<Perfomance> list3 = new ArrayList<>();

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year) != null) {
					list1 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam1, subject.getUuid(), classroomId, term, year);
				}

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam2, subject.getUuid(), classroomId, term, year) != null) {
					list2 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam2, subject.getUuid(), classroomId, term, year);
				}

				if(perfomanceDAO.getClassSubjectPerfomance(accountId, exam3, subject.getUuid(), classroomId, term, year) != null) {
					list3 = perfomanceDAO.getClassSubjectPerfomance(accountId, exam3, subject.getUuid(), classroomId, term, year);
				}

				List<PerformanceBean1> examList1 = new ArrayList<>();
				List<PerformanceBean1> examList2 = new ArrayList<>();
				List<PerformanceBean1> examList3 = new ArrayList<>();

				examList1 = CommonLogic.subjectAnalyzer(list1, isPaper123); 
				examList2 = CommonLogic.subjectAnalyzer(list2, isPaper123); 
				examList3 = CommonLogic.subjectAnalyzer(list3, isPaper123); 

				finalExamList = CommonLogic.combineExams(examList1, examList2, examList3, 3, subject.getUuid(), accountId, classroomId);
			}


			if(!finalExamList.isEmpty()) {

				document.add(new Paragraph("Subject: " + subc + ", " + subject.getDescription()));  

				subject_rankingTable = new PdfPTable(18);   
				subject_rankingTable.setWidthPercentage(100); 
				subject_rankingTable.setWidths(new int[]{8,15,10,10,10,10,10,10,10,10,10,10,10,10,14,14,14,16});
				subject_rankingTable.setHeaderRows(1); 
				subject_rankingTable.isSkipFirstHeader();

				subject_rankingTable.addCell(sub_r_countCell);
				subject_rankingTable.addCell(sub_r_streamCell);
				subject_rankingTable.addCell(sub_r_aCell);
				subject_rankingTable.addCell(sub_r_amCell);
				subject_rankingTable.addCell(sub_r_bpCell);
				subject_rankingTable.addCell(sub_r_bCell);
				subject_rankingTable.addCell(sub_r_bmCell);
				subject_rankingTable.addCell(sub_r_cpCell);
				subject_rankingTable.addCell(sub_r_cCell);
				subject_rankingTable.addCell(sub_r_cmCell);
				subject_rankingTable.addCell(sub_r_dpCell);
				subject_rankingTable.addCell(sub_r_dCell);
				subject_rankingTable.addCell(sub_r_dmCell);
				subject_rankingTable.addCell(sub_r_eCell);
				subject_rankingTable.addCell(sub_r_entryCell);
				subject_rankingTable.addCell(sub_r_mpCell);
				subject_rankingTable.addCell(sub_r_mgCell);
				subject_rankingTable.addCell(sub_r_subtCell);


				int tga = 0,tgam = 0,tgbp = 0,tgb = 0,tgbm = 0,tgcp = 0,tgc = 0,tgcm = 0,tgdp = 0,tgd = 0,tgdm = 0,tge = 0; 
				int entry = 0;
				double rmean = 0, mean = 0;
				int sc = 1;
				for(int i=0;i<streamlist.length;i++) {

					String ga,gam,gbp,gb,gbm,gcp,gc,gcm,gdp,gd,gdm,ge;
					String entry_ = "", mean_ = "", mgrade = "", mgrade_ = "";
					ga = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"A",streamlist[i]);
					gam = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"A-",streamlist[i]);
					gbp = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"B+",streamlist[i]);
					gb = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"B",streamlist[i]);
					gbm = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"B-",streamlist[i]);
					gcp = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"C+",streamlist[i]);
					gc = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"C",streamlist[i]);
					gcm = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"C-",streamlist[i]);
					gdp = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"D+",streamlist[i]);
					gd = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"D",streamlist[i]);
					gdm = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"D-",streamlist[i]);
					ge = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"E",streamlist[i]);
					entry_ = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"Entry",streamlist[i]);
					mean_ = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"Points",streamlist[i]);
					mgrade_ = CommonLogic.getGradeCount(finalExamList,accountId,subject.getUuid(),"Grade",streamlist[i]);


					if(ga.length() > 0) {
						tga += Integer.valueOf(ga);
					}

					if(gam.length() > 0) {
						tgam += Integer.valueOf(gam);
					}

					if(gbp.length() > 0) {
						tgbp += Integer.valueOf(gbp);
					}

					if(gb.length() > 0) {
						tgb += Integer.valueOf(gb);
					}

					if(gbm.length() > 0) {
						tgbm += Integer.valueOf(gbm);
					}

					if(gcp.length() > 0) {
						tgcp += Integer.valueOf(gcp);
					}

					if(gc.length() > 0) {
						tgc += Integer.valueOf(gc);
					}

					if(gcm.length() > 0) {
						tgcm += Integer.valueOf(gcm);
					}

					if(gdp.length() > 0) {
						tgdp += Integer.valueOf(gdp);
					}

					if(gd.length() > 0) {
						tgd += Integer.valueOf(gd);
					}

					if(gdm.length() > 0) {
						tgdm += Integer.valueOf(gdm);
					}

					if(ge.length() > 0) {
						tge += Integer.valueOf(ge);
					}

					if(entry_.length() > 0) {
						entry += Integer.valueOf(entry_);
					}

					double m = 0;

					if(mean_.length() > 0) {
						mean += Double.valueOf(mean_);  

						double t = Double.valueOf(mean_);  
						int e = Integer.valueOf(entry_);
						m = t/e;
					}



					if(i < (streamlist.length) -1) {


						subject_rankingTable.addCell(new Paragraph("" + sc,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + streamlist[i] ,timesRomanNormal6)); 
						subject_rankingTable.addCell(new Paragraph("" + ga,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + gam,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + gbp,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + gb,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + gbm,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + gcp,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + gc,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + gcm,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + gdp,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + gd,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + gdm,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + ge,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + entry_,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + ReportUtil.df2.format(m),timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + mgrade_,timesRomanNormal6));
						subject_rankingTable.addCell(new Paragraph("" + CommonLogic.getSubjectTeacher(accountId, streamlist[i], subject.getUuid()),timesRomanNormal6));

					}else

						if(i == (streamlist.length) -1) {

							rmean = mean / entry;
							mgrade = CommonLogic.getGrade((int)rmean, accountId);

							subject_rankingTable.addCell(new Paragraph("" + sc,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + streamlist[i] ,timesRomanNormal6)); 
							subject_rankingTable.addCell(new Paragraph("" + tga ,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + tgam ,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + tgbp,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + tgb ,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + tgbm,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + tgcp,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + tgc,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + tgcm,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + tgdp,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + tgd,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + tgdm,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + tge ,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + entry,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + ReportUtil.df2.format(rmean),timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" + mgrade,timesRomanNormal6));
							subject_rankingTable.addCell(new Paragraph("" ,timesRomanNormal6));



						}

					sc++;
				}

				document.add(new Paragraph("\n")); 
				document.add(subject_rankingTable);


				subc++;

			}

		}




	}
	/**
	 * 
	 * @param arr
	 * @param element
	 * @return
	 */
	private <T> T[] append(T[] arr, T element) {
		final int N = arr.length;
		arr = Arrays.copyOf(arr, N + 1);
		arr[N] = element;
		return arr;
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
	private static final long serialVersionUID = 6009672931352020766L;

}
