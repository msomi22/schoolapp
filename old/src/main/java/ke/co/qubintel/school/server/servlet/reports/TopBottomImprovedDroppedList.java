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
import java.util.ArrayList;
import java.util.Collections;
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
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.exam.YearlyMean;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.persistence.classroom.ClassDAO;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.exam.YearlyMeanDAO;
import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.servlet.util.Timeit;
import ke.co.qubintel.school.util.performance.comparator.TBIDBeanComparator;
import ke.co.qubintel.school.util.performance.comparator.TBIDBeanDeviationComparator;

/**
 *    
 *    http://localhost:8080/school/school/tbidList?accountId=b83e9b89-0d52-4191-a6bf-acf501267e2e1&uuid=4DA86139-6A72-4089-8858-6A3A613FDFE6&reportFlag=0&threshold=5&decisionFlag=0
 *    
 * 
 * @author peter
 *
 */
public class TopBottomImprovedDroppedList extends HttpServlet{

	private Font timesRomanBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private Font timesRomanBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private Font timesRomanNormal10= new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	//private Font timesRomanNormal6 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	private Font timesRomanNormal8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

	//private Font timesRomanBold12_colored = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);

	private static final String USER_SYSTEM = System.getProperty("user.name");
	private static final String LOGO_PATH = "/home/"+USER_SYSTEM+"/school/logo/logo.png";

	private Document document;
	private PdfWriter writer;

	private Logger logger;

	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static YearlyMeanDAO yearlyMeanDAO;
	private static  SysConfigDAO sysConfigDAO;

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
		yearlyMeanDAO = YearlyMeanDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();

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
		String streamId = StringUtils.trim(request.getParameter("uuid"));
		String threshold = StringUtils.trim(request.getParameter("threshold"));
		String decisionFlag = StringUtils.trim(request.getParameter("decisionFlag"));

		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4.rotate(), 46, 46, 64, 64);


		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();

			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(accountId, streamId, threshold, decisionFlag);

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}


	}



	private void populatePDFDocument(String accountId, String streamId, String threshold, String decisionFlag) {

		Timeit.code(() -> compute(accountId, streamId, threshold , decisionFlag));
		
	}


	private void compute(String accountId, String streamId,String threshold, String decisionFlag) {
		try {

			document.open();

			generateReport(accountId, streamId, threshold, decisionFlag);
			
			document.close();

		}catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		} 
	}




	private void generateReport(String accountId, String streamId, String threshold, String decisionFlag) throws DocumentException{

		SysConfig config = new SysConfig(); 
		if(sysConfigDAO.getSysConfig(accountId) != null) {
			config = sysConfigDAO.getSysConfig(accountId);
		}


		//BaseColor baseColorWhite = new BaseColor(255,255,255);//while
		BaseColor baseColor = new BaseColor(117,229,210);//#75e5d2
		//BaseColor baseColorShadow = new BaseColor(0,255,119);//#00FF77

		Account account = accountDAO.getAccountById(accountId);

		String school = "P.O Box : " + account.getAddress() + " " + account.getTown()+" "
				+ " , Cell : " + account.getMobile() + "\n"
				+ "Website : " + account .getWebsite() + "             EMAIL : " + account.getEmail(); 
		

		String empty_message =  threshold + " Students - nothing to display \n";
		String found_message =  threshold + " Students \n\n";



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

		PdfPTable studentTable = new PdfPTable(8);   
		studentTable.setWidthPercentage(100); 
		studentTable.setWidths(new int[]{6,10,12,12,12,13,13,13}); 
		//studentTable.setHeaderRows(1); 
		//studentTable.isSkipFirstHeader();
		
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

		PdfPCell prevMeanCell = new PdfPCell(new Paragraph("Prev-Mean",timesRomanBold10));
		prevMeanCell.setBackgroundColor(baseColor);
		prevMeanCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell meanCell = new PdfPCell(new Paragraph("Mean",timesRomanBold10));
		meanCell.setBackgroundColor(baseColor);
		meanCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell deviationCell = new PdfPCell(new Paragraph("Deviation",timesRomanBold10));
		deviationCell.setBackgroundColor(baseColor);
		deviationCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		studentTable.addCell(countCell);
		studentTable.addCell(regNoCell);
		studentTable.addCell(fnameCell);
		studentTable.addCell(mnameCell);
		studentTable.addCell(lnameCell);
		studentTable.addCell(prevMeanCell);
		studentTable.addCell(meanCell);
		studentTable.addCell(deviationCell);


		int threshold_ = Integer.valueOf(threshold);

		String classname = "";
		String streamname = "";
		String message = "";

		if(StringUtils.equals(decisionFlag, "1")) {//class
			classname = classDAO.getClassRoom(accountId, streamDAO.getStream(accountId, streamId).getClassRoomId()).getDescription();
			message = classname;

		}else if(StringUtils.equals(decisionFlag, "0")){//stream 

			streamname = streamDAO.getStream(accountId, streamId).getDescription();
			message =  streamname;

		}

		document.add(headerTable);

		document.add(new Paragraph("\n " + message + " Performance Analysis  \n\n" ,timesRomanBold12)); 

		if(StringUtils.equals(decisionFlag, "1")) {//class

			List<Student>  students = new ArrayList<>();//copy students from each stream into this list

			streamDAO.getStreamList(accountId, streamDAO.getStream(accountId, streamId).getClassRoomId()).forEach(stm -> {

				List<Student> activeStudents = studentDAO.getStudentByStream(accountId, stm.getUuid())
						.parallelStream()
						.filter(student -> "1".equals(student.getIsActive()))
						.collect(Collectors.toList());
				//copy 'activeStudents' list into  'students' list
				if(!activeStudents.isEmpty()) {
					//Collections.copy(students, activeStudents); 
					students.addAll(activeStudents);
				}


			});



			List<TBIDBean> tbidBeanList = new ArrayList<>();
			List<TBIDBean> tbidBeanListStatic = new ArrayList<>();
			for(Student student : students) {

				if(yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), config.getYear()) != null) {

					YearlyMean yearlyMean = yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), config.getYear()); 
					double mean = 0;
					double prevMean = 0; 
					double deviation = 0; 

					if(StringUtils.equals(config.getTerm(), "1")) {
						mean = yearlyMean.getMeanOne();
						//get current year , decrement to get previous year 
						int year = Integer.valueOf(config.getYear());
						//since this is term 1, in the previous year we get mean for term 3
						if(yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), String.valueOf(year - 1)) != null) {
							prevMean = yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), String.valueOf(year - 1)).getMeanThree(); 
						}



					}else if(StringUtils.equals(config.getTerm(), "2")) {
						mean = yearlyMean.getMeanTwo();
						prevMean  = yearlyMean.getMeanOne();

					}else if(StringUtils.equals(config.getTerm(), "3")) {
						mean = yearlyMean.getMeanThree();
						prevMean  = yearlyMean.getMeanTwo();
					}


					deviation = mean - prevMean;
					deviation = deviation == mean ? 0 : deviation;

					TBIDBean tbidBean = new TBIDBean();
					tbidBean.setStudent(student);
					tbidBean.setMean(mean);
					tbidBean.setPrevMean(prevMean);
					tbidBean.setDeviation(deviation);

					tbidBeanListStatic.add(tbidBean);


				}


			}


			tbidBeanList.addAll(tbidBeanListStatic);
			Collections.sort(tbidBeanList, new TBIDBeanComparator());
			Collections.reverse(tbidBeanList);

			//TOP students TODO
			boolean datafound0 = false;
			int count0 = 1;
			for(TBIDBean tbidbean : tbidBeanList) {

				if(tbidbean.getMean() > 0) {

					Student student =  tbidbean.getStudent();

					studentTable.addCell(new Paragraph(" " + count0, timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getRegNo(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(student.getFirstname(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getMiddlename(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getLastname(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getPrevMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getDeviation()), timesRomanNormal10));

					datafound0 = true;
				}



				if(count0 >= threshold_) {
					break;
				}

				count0++;
			}
			
			
			if(!datafound0) {
				document.add(new Paragraph("TOP " + empty_message , timesRomanBold10));
			}else {

				document.add(new Paragraph("TOP " + found_message , timesRomanBold10));
				document.add(studentTable); 

			}
			
			studentTable = refreshStudentTable(baseColor);

			tbidBeanList.clear();
			tbidBeanList.addAll(tbidBeanListStatic);
			Collections.sort(tbidBeanList, new TBIDBeanComparator());

			//BOTTOM students TODO
			boolean datafound1 = false;
			int count1 = 1;
			for(TBIDBean tbidbean : tbidBeanList) {

				if(tbidbean.getMean() > 0) {

					Student student =  tbidbean.getStudent();

					studentTable.addCell(new Paragraph(" " + count1, timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getRegNo(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(student.getFirstname(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getMiddlename(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getLastname(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getPrevMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getDeviation()), timesRomanNormal10));

					datafound1 = true;
				}

				if(count1 >= threshold_) {
					break;
				}

				count1++;
			}
			
			
			if(!datafound1) {
				document.add(new Paragraph("BOTTOM " + empty_message , timesRomanBold10));
				
			}else {
				
				document.add(new Paragraph("BOTTOM " + found_message , timesRomanBold10));
				document.add(studentTable); 
				
			}
			
			studentTable = refreshStudentTable(baseColor);

			tbidBeanList.clear();
			tbidBeanList.addAll(tbidBeanListStatic);
			Collections.sort(tbidBeanList, new TBIDBeanDeviationComparator());
			Collections.reverse(tbidBeanList);

			//MOST_IMPROVED students TODO
			boolean datafound2 = false;
			int count2 = 1;
			for(TBIDBean tbidbean : tbidBeanList) {

				if(tbidbean.getDeviation() > 0) {

					Student student =  tbidbean.getStudent();

					studentTable.addCell(new Paragraph(" " + count2, timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getRegNo(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(student.getFirstname(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getMiddlename(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getLastname(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getPrevMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getDeviation()), timesRomanNormal10));
					datafound2 = true;
				}

				if(count2 >= threshold_) {
					break;
				}

				count2++;
			}
			
			
			if(!datafound2) {
				document.add(new Paragraph("MOST IMPROVED " + empty_message , timesRomanBold10));
				
			}else {

				document.add(new Paragraph("MOST IMPROVED " + found_message , timesRomanBold10));
				document.add(studentTable); 

			}
			
			studentTable = refreshStudentTable(baseColor);

			tbidBeanList.clear();
			tbidBeanList.addAll(tbidBeanListStatic);
			Collections.sort(tbidBeanList, new TBIDBeanDeviationComparator());
			
			//MOST_DROPPED students TODO
			boolean datafound3 = false;
			int count3 = 1;
			for(TBIDBean tbidbean : tbidBeanList) {

				
				if(tbidbean.getDeviation() < 0) {

					Student student =  tbidbean.getStudent();

					studentTable.addCell(new Paragraph(" " + count3, timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getRegNo(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(student.getFirstname(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getMiddlename(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getLastname(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getPrevMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getDeviation()), timesRomanNormal10));

					datafound3 = true;

				}


				if(count3 >= threshold_) {
					break;
				}

				count3++;
			}
			

			if(!datafound3) {
				document.add(new Paragraph("MOST DROPPED " + empty_message , timesRomanBold10));
				
			}else {

				document.add(new Paragraph("MOST DROPPED " + found_message , timesRomanBold10));
				document.add(studentTable); 

			}



			
			
			


		}else if(StringUtils.equals(decisionFlag, "0")){//stream 

			List<Student> activeStudents = studentDAO.getStudentByStream(accountId, streamId).
					parallelStream()
					.filter(student -> "1".equals(student.getIsActive()))
					.collect(Collectors.toList());

			List<TBIDBean> tbidBeanList = new ArrayList<>();
			List<TBIDBean> tbidBeanListStatic = new ArrayList<>();

			for(Student student : activeStudents) {

				if(yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), config.getYear()) != null) {


					YearlyMean yearlyMean = yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), config.getYear()); 
					double mean = 0;
					double prevMean = 0; 
					double deviation = 0; 

					if(StringUtils.equals(config.getTerm(), "1")) {
						mean = yearlyMean.getMeanOne();
						//get current year , decrement to get previous year 
						int year = Integer.valueOf(config.getYear());
						//since this is term 1, in the previous year we get mean for term 3
						if(yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), String.valueOf(year - 1)) != null) {
							prevMean = yearlyMeanDAO.getYearlyMean(accountId, student.getUuid(), String.valueOf(year - 1)).getMeanThree();  
						}



					}else if(StringUtils.equals(config.getTerm(), "2")) {
						mean = yearlyMean.getMeanTwo();
						prevMean  = yearlyMean.getMeanOne();

					}else if(StringUtils.equals(config.getTerm(), "3")) {
						mean = yearlyMean.getMeanThree();
						prevMean  = yearlyMean.getMeanTwo();
					}


					deviation = mean - prevMean;
					deviation = deviation == mean ? 0 : deviation;

					TBIDBean tbidBean = new TBIDBean();
					tbidBean.setStudent(student);
					tbidBean.setMean(mean);
					tbidBean.setPrevMean(prevMean);
					tbidBean.setDeviation(deviation);

					//tbidBeanList.add(tbidBean);
					tbidBeanListStatic.add(tbidBean);


				}




			}


			tbidBeanList.addAll(tbidBeanListStatic);
			Collections.sort(tbidBeanList, new TBIDBeanComparator());
			Collections.reverse(tbidBeanList);

			//TOP students TODO
			boolean datafound0 = false;
			int count0 = 1;
			for(TBIDBean tbidbean : tbidBeanList) {

				if(tbidbean.getMean() > 0) {

					Student student =  tbidbean.getStudent();

					studentTable.addCell(new Paragraph(" " + count0, timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getRegNo(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(student.getFirstname(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getMiddlename(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getLastname(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getPrevMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getDeviation()), timesRomanNormal10));

					datafound0 = true;
				}


				if(count0 >= threshold_) {
					break;
				}

				count0++;
			}

			if(!datafound0) {
				document.add(new Paragraph("TOP " + empty_message , timesRomanBold10));
			}else {

				document.add(new Paragraph("TOP " + found_message, timesRomanBold10));
				document.add(studentTable); 

			}
			
			studentTable = refreshStudentTable(baseColor);

			tbidBeanList.clear();
			tbidBeanList.addAll(tbidBeanListStatic);
			Collections.sort(tbidBeanList, new TBIDBeanComparator());
			
			//BOTTOM students TODO
			boolean datafound1 = false;
			int count1 = 1;
			for(TBIDBean tbidbean : tbidBeanList) {

				if(tbidbean.getMean() > 0) {

					Student student =  tbidbean.getStudent();

					studentTable.addCell(new Paragraph(" " + count1, timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getRegNo(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(student.getFirstname(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getMiddlename(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getLastname(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getPrevMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getDeviation()), timesRomanNormal10));

					datafound1 = true;

				}


				if(count1 >= threshold_) {
					break;
				}

				count1++;
			}

			if(!datafound1) {
				document.add(new Paragraph("BOTTOM " + empty_message , timesRomanBold10)); 
				
			}else {
				
				document.add(new Paragraph("BOTTOM " + found_message , timesRomanBold10));
				document.add(studentTable); 
				
			}
			
			studentTable = refreshStudentTable(baseColor);

			tbidBeanList.clear();
			tbidBeanList.addAll(tbidBeanListStatic);
			Collections.sort(tbidBeanList, new TBIDBeanDeviationComparator());
			Collections.reverse(tbidBeanList);

			//MOST_IMPROVED students TODO
			boolean datafound2 = false;
			int count2 = 1;
			for(TBIDBean tbidbean : tbidBeanList) {

				if(tbidbean.getDeviation() > 0) {
					
					Student student =  tbidbean.getStudent();

					studentTable.addCell(new Paragraph(" " + count2, timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getRegNo(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(student.getFirstname(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getMiddlename(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getLastname(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getPrevMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getDeviation()), timesRomanNormal10));

					datafound2 = true;
				}




				if(count2 >= threshold_) {
					break;
				}

				count2++;
			}
			
			if(!datafound2) {
				document.add(new Paragraph("MOST IMPROVED " + empty_message , timesRomanBold10));
			}else {

				document.add(new Paragraph("MOST IMPROVED " + found_message , timesRomanBold10));
				document.add(studentTable); 

			}
			
			studentTable = refreshStudentTable(baseColor);

			tbidBeanList.clear();
			tbidBeanList.addAll(tbidBeanListStatic);
			Collections.sort(tbidBeanList, new TBIDBeanDeviationComparator());
			
			//MOST_DROPPED students TODO
			boolean datafound3 = false;
			int count3 = 1;
			for(TBIDBean tbidbean : tbidBeanList) {
				
				if(tbidbean.getDeviation() < 0) {
					
					Student student =  tbidbean.getStudent();

					studentTable.addCell(new Paragraph(" " + count3, timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getRegNo(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(student.getFirstname(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getMiddlename(), timesRomanNormal10));
					studentTable.addCell(new Paragraph(student.getLastname(), timesRomanNormal10));

					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getPrevMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getMean()), timesRomanNormal10));
					studentTable.addCell(new Paragraph(ReportUtil.df2.format(tbidbean.getDeviation()), timesRomanNormal10));

					datafound3 = true;
				}

				if(count3 >= threshold_) {
					break;
				}

				count3++;
			}
			

			if(!datafound3) {
				document.add(new Paragraph("MOST DROPPED " + empty_message , timesRomanBold10));
				
			}else {

				document.add(new Paragraph("MOST DROPPED " + found_message , timesRomanBold10));
				document.add(studentTable); 

			}


		}else {

			document.add(new Paragraph("decisionFlag invalid" , timesRomanBold10)); 

		}



	}


	/**
	 * @param baseColor
	 * @return
	 * @throws DocumentException
	 */
	private PdfPTable refreshStudentTable(BaseColor baseColor) throws DocumentException {
		
		PdfPTable studentTable = new PdfPTable(8);   
		studentTable.setWidthPercentage(100); 
		studentTable.setWidths(new int[]{6,10,12,12,12,13,13,13}); 
		
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

		PdfPCell prevMeanCell = new PdfPCell(new Paragraph("Prev-Mean",timesRomanBold10));
		prevMeanCell.setBackgroundColor(baseColor);
		prevMeanCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell meanCell = new PdfPCell(new Paragraph("Mean",timesRomanBold10));
		meanCell.setBackgroundColor(baseColor);
		meanCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell deviationCell = new PdfPCell(new Paragraph("Deviation",timesRomanBold10));
		deviationCell.setBackgroundColor(baseColor);
		deviationCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		studentTable.addCell(countCell);
		studentTable.addCell(regNoCell);
		studentTable.addCell(fnameCell);
		studentTable.addCell(mnameCell);
		studentTable.addCell(lnameCell);
		studentTable.addCell(prevMeanCell);
		studentTable.addCell(meanCell);
		studentTable.addCell(deviationCell);
		return studentTable;
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
