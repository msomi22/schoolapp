/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.CategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubCategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.util.performance.comparator.PerformanceComparator;

/**   http://localhost:8080/school/school/testPerformance
 * 
 * 
 * @author peter
 *
 */
public class TestPerformance extends HttpServlet{


	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;
	private static SubCategoryDAO subCategoryDAO;
	private static CategoryDAO categoryDAO;
	private static StudentDAO studentDAO;


	private Font timesRomanNarmal8 = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
	private Font timesRomanNormal7 = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
	private Font timesRomanNormal0 = new Font(Font.FontFamily.TIMES_ROMAN,10, Font.NORMAL);

	private Document document;
	private PdfWriter writer;

	private String PDF_SUBTITLE ="";
	private Logger logger;

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
		subCategoryDAO = SubCategoryDAO.getInstance();
		categoryDAO = CategoryDAO.getInstance(); 
		studentDAO = StudentDAO.getInstance();

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
		response.setContentType("application/pdf");

		String accountId = StringUtils.trimToEmpty(request.getParameter("accountId"));

		System.out.println("******************************************************************8"); 
		System.out.println("accountId " + accountId); 

		String fileName = "file.pdf"; 
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		document = new Document(PageSize.A4, 46, 46, 64, 64);


		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());           
			PdfUtil event = new PdfUtil();



			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(accountId);


		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}






	}

	/**
	 * @param args
	 */
	public void populatePDFDocument(String accountId) {
		//Timeit.code(() -> compute());


		 accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		//String studentId = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
		String streamId = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
		String term = "1";
		String year = "2016";
		List<Perfomance> perfomanceList = null;

		String[] exams = { "D50E6399-B913-42F2-A5B6-F0D4BAAF9571", "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A",
		"16C4BF00-941C-40E4-9891-272D5F0979A1" };

		try {
			document.open();

			document.add(new Paragraph("peter *****************************"));
			logger.info("*************************************************************");

			System.out.println("******************************************************************"); 

			List<Student> studentsList = studentDAO.getStudentByStream(accountId, streamId);

			for(Student student : studentsList ){

				//Student student = studentDAO.getStudentById(accountId, studentId);

				document.add(new Paragraph("Firstname: " + student.getFirstname()));
				document.add(new Paragraph("Middlename: " + student.getMiddlename()));
				document.add(new Paragraph("Lastname: " + student.getLastname()));
				document.add(new Paragraph("RegNo: " + student.getRegNo())); 


				//perfomanceList = perfomanceDAO.getStreamPerformance(accountId, exams[0], studentId, streamId, term, year);
				HashMap<String,List<Perfomance>> perfomancesMap = new HashMap<>();

				for( int i = 0; i < exams.length; i++){
					perfomanceList = perfomanceDAO.getStreamPerformance(accountId, exams[i], student.getUuid(), streamId, term, year);


					List<Perfomance> selectedLanguagesList = new ArrayList<>();
					List<Perfomance> selectedSciencesList = new ArrayList<>();
					List<Perfomance> selectedHumanitiesList = new ArrayList<>();
					List<Perfomance> selectedTechnicalsList = new ArrayList<>();
					List<Perfomance> finalPerfomanceList = new ArrayList<>();
					List<Perfomance> removedSubjectsPerfomanceList = new ArrayList<>();

					if (perfomanceList.size() >= 1) {
						int languagesCount = 0;
						int sciencesCount = 0;
						int humanitiesCount = 0;

						for (Perfomance perfomance : perfomanceList) {

							perfomance.getExamId();
							perfomance.getStudentId();
							perfomance.getSubjectId();


							String catId = subCategoryDAO.getSubCategory(accountId, perfomance.getSubjectId()).getCategoryId();
							String desc = categoryDAO.getCategoryById(accountId, catId).getDescription();
							String subject = subjectDAO.getSubjectById(accountId, perfomance.getSubjectId()).getDescription();

							if (StringUtils.equalsIgnoreCase(desc, "Languages")) {
								selectedLanguagesList.add(perfomance);
								languagesCount++;

								if (languagesCount > 2) {
									Collections.sort(selectedLanguagesList, new PerformanceComparator());
									removedSubjectsPerfomanceList.add(selectedLanguagesList.remove(0));

								}
							}

							if (StringUtils.equalsIgnoreCase(desc, "Sciences")) {
								selectedSciencesList.add(perfomance);
								sciencesCount++;

								if (sciencesCount > 2) {
									Collections.sort(selectedSciencesList, new PerformanceComparator());	
									Perfomance removedScience = selectedSciencesList.remove(0);
									selectedTechnicalsList.add(removedScience);
									//removedSubjectsPerfomanceList.add(removedScience)


								}
							}

							if (StringUtils.equalsIgnoreCase(desc, "Humanities")) {
								selectedHumanitiesList.add(perfomance);
								humanitiesCount++;

								if (humanitiesCount > 1) {
									Collections.sort(selectedHumanitiesList, new PerformanceComparator());
									selectedTechnicalsList.add(selectedHumanitiesList.remove(0));

								}
							}

							if (StringUtils.equalsIgnoreCase(desc, "Technicals")) {
								selectedTechnicalsList.add(perfomance);


							}

							if (StringUtils.equalsIgnoreCase(desc, "Mathematics")) {
								finalPerfomanceList.add(perfomance);


							}

							//System.out.println("score = " + perfomance.getScore() + " ** " + subject + "(" + desc + ")");

						}

						Collections.sort(selectedTechnicalsList, new PerformanceComparator());

						if(selectedTechnicalsList.size() > 0){
							Perfomance highestTechnical = selectedTechnicalsList.remove(selectedTechnicalsList.size()-1);
							//System.out.println("highestTechnical score = " + highestTechnical.getScore());
							removedSubjectsPerfomanceList.addAll(selectedTechnicalsList);
							finalPerfomanceList.add(highestTechnical);
						}

						finalPerfomanceList.addAll(selectedLanguagesList);
						finalPerfomanceList.addAll(selectedSciencesList);
						finalPerfomanceList.addAll(selectedHumanitiesList);

						System.out.println("-------final----" + removedSubjectsPerfomanceList.size()); 
						//finalPerfomanceList.forEach(p->System.out.println("Subject:::" + p.getSubjectId() + ":::score:::" + p.getScore()));

						perfomancesMap.put(exams[i], finalPerfomanceList);

					}


				}



				/*document.add(new Paragraph("Series 1: " + " s1 ")); 
			document.add(new Paragraph("Series 2: " + " s2 ")); 
			document.add(new Paragraph("Series 3: " + " s3 ")); 
				 */

				for(int i = 0; i< exams.length; i++){
					List<Perfomance> mylist = null;

					if(perfomancesMap.get(exams[i]) != null){
						mylist = perfomancesMap.get(exams[i]);

						System.out.println(getTotalsPerExam(mylist)); 
						document.add(new Paragraph("Series  " + i + ":" + getTotalsPerExam(mylist))); 
						//System.out.println(mylist); 
						System.out.println("__________________________________________________________"); 
					}


				}

				document.newPage();

			}

			document.close();

		}catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}  

	

	}

	

	/**
	 * @param perfomanceList
	 * @return
	 */
	public static int getTotalsPerExam(List<Perfomance> perfomanceList){
		int totalPoints = 0;

		for( Perfomance perfomance : perfomanceList ){
			totalPoints += perfomance.getScore();
		}

		return totalPoints;
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
	private static final long serialVersionUID = 5356561358431988192L;

}
