/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.exam.YearlyMean;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Category;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.YearlyMeanDAO;
import com.yahoo.petermwenda83.persistence.staff.TeacherSubjectDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.CategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubCategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.util.performance.comparator.PerformanceComparator;

/**
 * @author peter
 *
 */
public class ReportUtil {


	public static SimpleDateFormat yearformatter = new SimpleDateFormat("yyyy");
	public static DecimalFormat df2 = new DecimalFormat(".##");
	public final static double STD_CONSTANT = 1.19;
	public final static double GRAPH_CONSTANT = 0.12;
	
	public final static String PAPER123ID = "C3915245-00EE-4EF4-9898-ACE59683DD60";

	public static final int LANG_P1_OUTOF = 60;
	public static final int LANG_P2_OUTOF = 80;
	public static final int LANG_P3_OUTOF = 60;

	public static final int SCI_AGR_P1_OUTOF = 80;
	public static final int SCI_AGR_P2_OUTOF = 80;
	public static final int SCI_AGR_P3_OUTOF = 30;

	public static final int HUMAN_TECH_MATH_P1_OUTOF = 100;
	public static final int HUMAN_TECH_MATH_P2_OUTOF = 100;
	
	public static final int PAPER_1_2_CONSTANT = 70;
	public static final int PAPER_1_2_DIVISOR = 160;

	public static final String EXAM_TYPE = "P123";

	public static final String SCOPE_CLASS = "class";
	public static final String SCOPE_STREAM = "stream";
	
	public static final String CAT_LANG = "Languages";
	public static final String CAT_SCI = "Sciences";
	public static final String CAT_HUM = "Humanities";
	public static final String CAT_TECH = "Technicals";
	public static final String CAT_MATH = "Mathematics";
	
	private static SubCategoryDAO subCategoryDAO2;
	private static CategoryDAO categoryDAO2;
	private static Logger logger;// = Logger.getLogger(this.getClass());
	
	private static SubjectDAO subDAO;
	
	
	static {
		subCategoryDAO2 = SubCategoryDAO.getInstance();
		categoryDAO2 = CategoryDAO.getInstance();
		logger = Logger.getLogger(ReportUtil.class);
		subDAO = SubjectDAO.getInstance();
	}
	
	

	/**
	 * @param accountId
	 * @param streamId
	 * @param teacherSubjectDAO 
	 * @param uuid
	 * @return
	 */
	public static String getInitials(String accountId, String streamId, String subjectId, TeacherSubjectDAO teacherSubjectDAO) { 
		String teacher = "";
		if(teacherSubjectDAO.getTeacherSubject(accountId, streamId, subjectId) !=null) {
			teacher = teacherSubjectDAO.getTeacherSubject(accountId, streamId, subjectId).getTeacherId();
		}
		return teacher;
	}



	/**
	 * 
	 * @param accountId account unique id
	 * @param exam exam to rank
	 * @param subCategoryDAO subject category DAO
	 * @param categoryDAO category DAO
	 * @param subjectDAO subject DAO
	 * @param gradingSystemDAO ranking criteria DAO
	 * 
	 * @return student performance object based on the best 7/11 subjects
	 */

	public static Performance3 findExamTotalForm234(String accountId, String streamId, List<Perfomance> exam , SubCategoryDAO subCategoryDAO,
			CategoryDAO categoryDAO, SubjectDAO subjectDAO, GradingSystemDAO gradingSystemDAO, ExamDAO examDAO,String examType) {

		List<Perfomance> finalPerfomanceList = new ArrayList<>();
		Map<String,Integer> perfomanceMap = new HashMap<>(); 
		Map<String,Integer> paper1Map = new HashMap<>(); 
		Map<String,Integer> paper2Map = new HashMap<>(); 
		Map<String,Integer> paper3Map = new HashMap<>(); 
		
		Performance3 performance3 = new Performance3();

		if(!exam.isEmpty()){


			List<Perfomance> filteredExam = exam.parallelStream()
					.filter(performance -> streamId.equals(performance.getStreamId()))
					.collect(Collectors.toList());

			int languagesCount = 0;
			int sciencesCount = 0;
			int humanitiesCount = 0;

			List<Perfomance> removedSubjectsPerfomanceList = new ArrayList<>();
			List<Perfomance> selectedLanguagesList = new ArrayList<>();
			List<Perfomance> selectedSciencesList = new ArrayList<>();
			List<Perfomance> selectedHumanitiesList = new ArrayList<>();
			List<Perfomance> selectedTechnicalsList = new ArrayList<>();

			for (Perfomance perfomance : filteredExam) {
			
				if(StringUtils.equalsIgnoreCase(examType, EXAM_TYPE)){
					PerformanceP123 performanceP123 = computePaper123(subjectDAO,subCategoryDAO, categoryDAO , accountId, perfomance, gradingSystemDAO);
					perfomance.setScore(performanceP123.getTotalMean()); 
					//mm
				}
				
				perfomanceMap.put(perfomance.getSubjectId(), perfomance.getScore());
				
				paper1Map.put(perfomance.getSubjectId(), perfomance.getPaper1());
				paper2Map.put(perfomance.getSubjectId(), perfomance.getPaper2());
				paper3Map.put(perfomance.getSubjectId(), perfomance.getPaper3());
				
				//perfomance.setScore(performanceP123.getTotalMean()); 
				

				String catId = subCategoryDAO.getSubCategory(accountId, perfomance.getSubjectId()).getCategoryId();
				String desc = categoryDAO.getCategoryById(accountId, catId).getDescription();

				//select two best languages
				if (StringUtils.equalsIgnoreCase(desc, "Languages")) {
					selectedLanguagesList.add(perfomance);
					languagesCount++;


					if (languagesCount > 2) {
						Collections.sort(selectedLanguagesList, new PerformanceComparator());
						removedSubjectsPerfomanceList.add(selectedLanguagesList.remove(0));

					}
				}

				//select two best sciences
				else if (StringUtils.equalsIgnoreCase(desc, "Sciences")) {
					selectedSciencesList.add(perfomance);
					sciencesCount++;

					if (sciencesCount > 2) {
						//add remaining subjects if any to technical list
						Collections.sort(selectedSciencesList, new PerformanceComparator());	
						selectedTechnicalsList.add(selectedSciencesList.remove(0));

					}
				}

				//select one best humanity 
				else if (StringUtils.equalsIgnoreCase(desc, "Humanities")) {
					selectedHumanitiesList.add(perfomance);
					humanitiesCount++;

					if (humanitiesCount > 1) {
						//add remaining subjects if any to technical list
						Collections.sort(selectedHumanitiesList, new PerformanceComparator());
						selectedTechnicalsList.add(selectedHumanitiesList.remove(0));

					}
				}

				//add all technical
				else if (StringUtils.equalsIgnoreCase(desc, "Technicals")) {
					selectedTechnicalsList.add(perfomance);


				}

				//add mathematics
				else if (StringUtils.equalsIgnoreCase(desc, "Mathematics")) {

					String subjectId = finalPerfomanceList.parallelStream()
							.filter(p -> 
							p.getSubjectId().equals(perfomance.getSubjectId())
									)
							.map(Perfomance::getSubjectId)
							.findAny()
							.orElse("");

					  finalPerfomanceList.add(perfomance);

					if(StringUtils.isEmpty(subjectId) || StringUtils.isBlank(subjectId)){ 
						//finalPerfomanceList.add(perfomance);
					}

				}

			}

			//select one best from the technical
			Collections.sort(selectedTechnicalsList, new PerformanceComparator());

			if(selectedTechnicalsList.size() > 0){
				Perfomance highestTechnical = selectedTechnicalsList.remove(selectedTechnicalsList.size()-1);
				finalPerfomanceList.add(highestTechnical);

			}

			finalPerfomanceList.addAll(selectedLanguagesList);
			finalPerfomanceList.addAll(selectedSciencesList);
			finalPerfomanceList.addAll(selectedHumanitiesList);

			performance3.setPerfomanceMap(perfomanceMap); 
			performance3.setPaper1Map(paper1Map);
			performance3.setPaper2Map(paper2Map);
			performance3.setPaper3Map(paper3Map);

			performance3.setTotalMean(getTotalsByTotalPerExam(finalPerfomanceList)); 
			performance3.setTotalPoints(getTotalsByPointsPerExam(finalPerfomanceList, subjectDAO, gradingSystemDAO)); 
		}


		return performance3;
	}




/**
 * 
 * @param finalPerfomanceList
 * @param subjectDAO
 * @param subCategoryDAO
 * @param categoryDAO
 * @param examDAO
 * @param gradingSystemDAO
 * @param accountId
 * @return
 */
	public static PerformanceP123 computeP123(List<Perfomance> finalPerfomanceList, SubjectDAO subjectDAO, 
			SubCategoryDAO subCategoryDAO, CategoryDAO categoryDAO , ExamDAO examDAO, GradingSystemDAO gradingSystemDAO, String accountId){ 

		PerformanceP123 performanceP123 = new PerformanceP123();
		//double total = 0;
		double grandTotal = 0;
		int grandPoints = 0;
		double sum = 0;

		//List<String> paper1 = new ArrayList<>();

		List<Subject> subjects = subjectDAO.getSubjects(accountId);

		Map<String,Subject> subjectMap = new HashMap<>();
		for(Subject sub : subjects){
			subjectMap.put(sub.getUuid(), sub);
		}


		for(Perfomance perfomance : finalPerfomanceList){
			
			performanceP123.setStudentId(perfomance.getStudentId()); 
			
			Subject subject = subjectMap.get(perfomance.getSubjectId());
			String catId = subCategoryDAO.getSubCategory(accountId, subject.getUuid()).getCategoryId();
			Category cat = categoryDAO.getCategoryById(accountId, catId);

			//Exam exam = examDAO.getExam(accountId, perfomance.getExamId());


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Languages")){
				//check the exam
				sum = (double)(perfomance.getPaper1() + perfomance.getPaper2() + perfomance.getPaper3()) / 2; 
				grandTotal += sum;
				//paper1.add("sum:" + sum + " , grandTotal:" + grandTotal);

				int point = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
				grandPoints += point;
				

			}

			if(StringUtils.equals(cat.getDescription(), "Sciences")){
				//check the exam
				sum =  (double) (perfomance.getPaper1() + perfomance.getPaper2()) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT + perfomance.getPaper3();  
				grandTotal += sum;
				
				//paper1.add("sum:" + sum + " , grandTotal:" + grandTotal);

				int point = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
				grandPoints += point;
				

			}


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Technicals")){


				if(StringUtils.equalsIgnoreCase(subject.getCode(), "AGR") || 
						StringUtils.containsIgnoreCase(subject.getDescription(), "Agriculture") || 
						StringUtils.equalsIgnoreCase(subject.getCode(), "HSC") || 
						StringUtils.containsIgnoreCase(subject.getDescription(), "Home Science") ||
						StringUtils.equalsIgnoreCase(subject.getCode(), "COM") || 
						StringUtils.containsIgnoreCase(subject.getDescription(), "Computer Studies")){
				
					sum =  (double) (perfomance.getPaper1() + perfomance.getPaper2()) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT + perfomance.getPaper3();  
					grandTotal += sum;
					
					//paper1.add("sum:" + sum + " , grandTotal:" + grandTotal);

					int point = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
					grandPoints += point;



				}else{

					sum = (double)(perfomance.getPaper1() + perfomance.getPaper2()) / 2;
					grandTotal += sum;
					
					//paper1.add("sum:" + sum + " , grandTotal:" + grandTotal);

					int point = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
					grandPoints += point;

				}

			}


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Humanities") || 
					StringUtils.equalsIgnoreCase(cat.getDescription(), "Mathematics")){
				//check the exam
				sum = (double)(perfomance.getPaper1() + perfomance.getPaper2()) / 2;
				grandTotal += sum;
				
				//paper1.add("sum:" + sum + " , grandTotal:" + grandTotal);

				int point = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
				grandPoints += point;
				
			}
			

		}
		
		
		
		performanceP123.setTotalMean((int)Math.round(grandTotal));
		performanceP123.setTotalPoints(grandPoints); 
		
		
		return performanceP123;

	}

	
	/**
	 * 
	 * @param finalPerfomanceList
	 * @param subjectDAO
	 * @param subCategoryDAO
	 * @param categoryDAO
	 * @param examDAO
	 * @param gradingSystemDAO
	 * @param accountId
	 */
	public static PerformanceP123 computePaper123(SubjectDAO subjectDAO,SubCategoryDAO subCategoryDAO, CategoryDAO categoryDAO , String accountId,
			Perfomance perfomance, GradingSystemDAO gradingSystemDAO) {
	
		
		PerformanceP123 performanceP123 = new PerformanceP123();
		int points = 0;
		double sum = 0;
		
		List<Subject> subjects = subjectDAO.getSubjects(accountId);

		Map<String,Subject> subjectMap = new HashMap<>();
		for(Subject sub : subjects){
			subjectMap.put(sub.getUuid(), sub);
		}


			Subject subject = subjectMap.get(perfomance.getSubjectId());
			String catId = subCategoryDAO.getSubCategory(accountId, subject.getUuid()).getCategoryId();
			Category cat = categoryDAO.getCategoryById(accountId, catId);


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Languages")){
				
				sum = (double)(perfomance.getPaper1() + perfomance.getPaper2() + perfomance.getPaper3()) / 2; 
				points = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
				
				performanceP123.setStudentId(perfomance.getStudentId());
				performanceP123.setTotalMean((int)Math.round(sum)); 
				performanceP123.setTotalPoints(points);
				
				return performanceP123;
				

			}

			if(StringUtils.equals(cat.getDescription(), "Sciences")){
				
				sum =  (double) (perfomance.getPaper1() + perfomance.getPaper2()) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT + perfomance.getPaper3();  
				points = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
				
				performanceP123.setStudentId(perfomance.getStudentId());
				performanceP123.setTotalMean((int)Math.round(sum)); 
				performanceP123.setTotalPoints(points);
				
				return performanceP123;


			}


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Technicals")){


				if(StringUtils.equalsIgnoreCase(subject.getCode(), "AGR") || 
						StringUtils.containsIgnoreCase(subject.getDescription(), "Agriculture") || 
						StringUtils.equalsIgnoreCase(subject.getCode(), "HSC") || 
						StringUtils.containsIgnoreCase(subject.getDescription(), "Home Science") ||
						StringUtils.equalsIgnoreCase(subject.getCode(), "COM") || 
						StringUtils.containsIgnoreCase(subject.getDescription(), "Computer Studies")){
				
					sum =  (double) (perfomance.getPaper1() + perfomance.getPaper2()) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT + perfomance.getPaper3();  
					points = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
					
					performanceP123.setStudentId(perfomance.getStudentId());
					performanceP123.setTotalMean((int)Math.round(sum)); 
					performanceP123.setTotalPoints(points);
					
					return performanceP123;

				}else{


					sum = (double)(perfomance.getPaper1() + perfomance.getPaper2()) / 2;
					points = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
					
					performanceP123.setStudentId(perfomance.getStudentId());
					performanceP123.setTotalMean((int)Math.round(sum)); 
					performanceP123.setTotalPoints(points);
					
					return performanceP123;

				}

			}


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Humanities") || 
					StringUtils.equalsIgnoreCase(cat.getDescription(), "Mathematics")){
				
				sum = (double)(perfomance.getPaper1() + perfomance.getPaper2()) / 2;
				points = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
				
				performanceP123.setStudentId(perfomance.getStudentId());
				performanceP123.setTotalMean((int)Math.round(sum)); 
				performanceP123.setTotalPoints(points);
				
				return performanceP123;
				
			}
			
			return performanceP123;
	
	}


	/**
	 * 
	 * @param accountId
	 * @param exam
	 * @param subCategoryDAO
	 * @param categoryDAO
	 * @param subjectDAO
	 * @param gradingSystemDAO
	 * @return
	 */

	public static Performance3 findExamTotalForm1(String accountId, String streamId, List<Perfomance> exam , SubCategoryDAO subCategoryDAO,
			CategoryDAO categoryDAO, SubjectDAO subjectDAO, GradingSystemDAO gradingSystemDAO) {




		List<Perfomance> finalPerfomanceList = new ArrayList<>();
		Map<String,Integer> perfomanceMap = new HashMap<>(); 


		if(!exam.isEmpty()){


			List<Perfomance> filteredExam = exam.parallelStream()
					.filter(performance -> streamId.equals(performance.getStreamId()))
					.collect(Collectors.toList());

			int languagesCount = 0;
			int sciencesCount = 0;
			int humanitiesCount = 0;
			int technicalCount = 0;

			List<Perfomance> removedSubjectsPerfomanceList = new ArrayList<>();
			List<Perfomance> selectedLanguagesList = new ArrayList<>();
			List<Perfomance> selectedSciencesList = new ArrayList<>();
			List<Perfomance> selectedHumanitiesList = new ArrayList<>();
			List<Perfomance> selectedTechnicalsList = new ArrayList<>();



			for (Perfomance perfomance : filteredExam) {

				perfomanceMap.put(perfomance.getSubjectId(), perfomance.getScore());

				String catId = subCategoryDAO.getSubCategory(accountId, perfomance.getSubjectId()).getCategoryId();
				String desc = categoryDAO.getCategoryById(accountId, catId).getDescription();

				//select two best languages
				if (StringUtils.equalsIgnoreCase(desc, "Languages")) {
					selectedLanguagesList.add(perfomance);
					languagesCount++;


					if (languagesCount > 2) {
						Collections.sort(selectedLanguagesList, new PerformanceComparator());
						removedSubjectsPerfomanceList.add(selectedLanguagesList.remove(0));

					}
				}

				//select three best sciences
				if (StringUtils.equalsIgnoreCase(desc, "Sciences")) {
					selectedSciencesList.add(perfomance);
					sciencesCount++;

					if (sciencesCount > 3) {
						//add remaining subjects if any to technical list
						Collections.sort(selectedSciencesList, new PerformanceComparator());
						selectedSciencesList.remove(0);

					}
				}

				//select three best humanity 
				if (StringUtils.equalsIgnoreCase(desc, "Humanities")) {
					selectedHumanitiesList.add(perfomance);
					humanitiesCount++;

					if (humanitiesCount > 3) {
						//add remaining subjects if any to technical list
						Collections.sort(selectedHumanitiesList, new PerformanceComparator());
						selectedHumanitiesList.remove(0);

					}
				}

				//select two best technical
				if (StringUtils.equalsIgnoreCase(desc, "Technicals")) {
					selectedTechnicalsList.add(perfomance);
					technicalCount++;

					if(technicalCount > 2){
						Collections.sort(selectedTechnicalsList, new PerformanceComparator());
						selectedTechnicalsList.remove(0);
					}


				}

				//add mathematics
				if (StringUtils.equalsIgnoreCase(desc, "Mathematics")) {
					finalPerfomanceList.add(perfomance);


				}

			}//end for each loop


			finalPerfomanceList.addAll(selectedLanguagesList);
			finalPerfomanceList.addAll(selectedSciencesList);
			finalPerfomanceList.addAll(selectedHumanitiesList);
			finalPerfomanceList.addAll(selectedTechnicalsList);

		}

		Performance3 performance3 = new Performance3();
		performance3.setPerfomanceMap(perfomanceMap); 
		performance3.setTotalMean(getTotalsByTotalPerExam(finalPerfomanceList)); 
		performance3.setTotalPoints(getTotalsByPointsPerExam(finalPerfomanceList, subjectDAO, gradingSystemDAO)); 

		return performance3;
	}
	
	
	

	/**
	 * @param accountId
	 * @param exam
	 * @param subCategoryDAO
	 * @param categoryDAO
	 * @param subjectDAO
	 * @param gradingSystemDAO
	 * @param examDAO
	 * @param examType
	 */
	public static SubjectPerformance findSubjectPerformance(String accountId, List<Perfomance> exam, SubCategoryDAO subCategoryDAO,
			CategoryDAO categoryDAO, SubjectDAO subjectDAO, GradingSystemDAO gradingSystemDAO, ExamDAO examDAO,
			String examType) {
		
		
		SubjectPerformance subjectPerformance = new SubjectPerformance();
		
		AtomicInteger entry = new AtomicInteger();
		exam.parallelStream().forEach(performance -> {
			if(performance.getPaper1() > 0 || performance.getPaper2() > 0 || performance.getPaper3() > 0 || performance.getScore() > 0) {
				entry.getAndIncrement();
			}
		});
		
		subjectPerformance.setEntry(entry.get()); 
		
		
		if(StringUtils.equalsIgnoreCase(examType, EXAM_TYPE)){
			
			PerformanceP123 performanceP123 = new PerformanceP123();
			performanceP123 = ReportUtil.computeP123(exam, subjectDAO, subCategoryDAO, categoryDAO, examDAO, gradingSystemDAO, accountId);

			if(performanceP123.getTotalMean()  > 0){
				subjectPerformance.setAverage((double)performanceP123.getTotalMean() / (double)exam.size());  
			}
			
			subjectPerformance.setTotal(performanceP123.getTotalMean());  
			
			

		}else{

			if(getTotalsByTotalPerExam(exam) > 0){
				subjectPerformance.setAverage((double)getTotalsByTotalPerExam(exam) / (double)exam.size()); 
			}
			
			subjectPerformance.setTotal((double)getTotalsByTotalPerExam(exam));  

		}
		
		return subjectPerformance;
	}




	/**
	 * 
	 * @param perfomanceList
	 * @param subjectDAO
	 * @param gradingSystemDAO
	 * @return
	 */


	public static int getTotalsByPointsPerExam(List<Perfomance> perfomanceList, SubjectDAO subjectDAO, GradingSystemDAO gradingSystemDAO){
		int totalPoints = 0;

		for( Perfomance perfomance : perfomanceList ){
			int point = getPoints(String.valueOf(perfomance.getScore()),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);

			totalPoints += point;
		}

		return totalPoints;
	}



	/**
	 * 
	 * @param perfomanceList
	 * @return
	 */

	public static int getTotalsByTotalPerExam(List<Perfomance> perfomanceList){
		
		int totals = 0;
		for( Perfomance perfomance : perfomanceList ){
			totals += perfomance.getScore();
			
			String code  = "";
			if(subDAO.getSubjectById(perfomance.getAccountId(), perfomance.getSubjectId()).getCode() != null) {
				code = subDAO.getSubjectById(perfomance.getAccountId(), perfomance.getSubjectId()).getCode();
			}else {
				code = perfomance.getSubjectId();
			}
			logger.info(code); 
		}
		
		

		return totals;
	}




	/**
	 * 
	 * @param value
	 * @param subjectId
	 * @param accountId
	 * @param subjectDAO
	 * @param gradingSystemDAO
	 * @return
	 */

	public static int getPoints(String value, String subjectId, String accountId, SubjectDAO subjectDAO, GradingSystemDAO gradingSystemDAO){

		if(value.length() == 0){
			value = "0";
		}

		int score = Integer.parseInt(value);

		int points = 0;
		String categoryId = "";

		if(subjectDAO.getSubjectById(accountId, subjectId) != null){
			categoryId = subjectDAO.getSubjectById(accountId, subjectId).getCategoryId(); 
		}


		List<GradingSystem> gradingSystemList = new ArrayList<>();

		gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, categoryId);

		if(gradingSystemList.isEmpty()){
			String generalId = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";
			gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, generalId);

		}

		for(GradingSystem gradingSystem : gradingSystemList){

			if(score <= gradingSystem.getUpperLimit() &&  score >= gradingSystem.getLowerLimit()){

				points = gradingSystem.getPoints();

			}

		}


		return points;
	}








	/**
	 * 
	 * @param value
	 * @param subjectId
	 * @param accountId
	 * @param subjectDAO
	 * @param gradingSystemDAO
	 * @return
	 */

	public static String getGrade(String value, String subjectId, String accountId, SubjectDAO subjectDAO, GradingSystemDAO gradingSystemDAO){

		if(value.length() == 0){
			value = "0";
		}

		int score = Integer.parseInt(value);
		String grade = "";
		String categoryId = "";

		if(subjectDAO.getSubjectById(accountId, subjectId) != null){
			categoryId = subjectDAO.getSubjectById(accountId, subjectId).getCategoryId(); 
		}

		List<GradingSystem> gradingSystemList = new ArrayList<>();

		if(gradingSystemDAO.getGradingSystemList(accountId, categoryId) != null) {
			gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, categoryId);
		}
		

		if(gradingSystemList.isEmpty()){
			String generalId = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";
			gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, generalId);

		}

		for(GradingSystem gradingSystem : gradingSystemList){

			if(score <= gradingSystem.getUpperLimit() &&  score >= gradingSystem.getLowerLimit()){

				grade = gradingSystem.getDescription();

			}

		}


		return grade;
	}





	/**
	 * 
	 * @param mean
	 * @param accountId
	 * @param gradingSystemDAO
	 * @return
	 */

	public static String getGradeMainForm234(int mean, String accountId, GradingSystemDAO gradingSystemDAO) {

		String grade = "";

		double meanDouble = (double) mean / 7;

		int point = (int) Math.round(meanDouble); 

		List<GradingSystem> gradingSystemList = new ArrayList<>();

		String generalId = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";
		gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, generalId);

		for(GradingSystem gradingSystem : gradingSystemList){

			if(point == gradingSystem.getPoints()){

				grade = gradingSystem.getDescription();

			}

		}

		return grade;
	}




	/** 
	 * 
	 * @param exam1Score
	 * @param exam2Score
	 * @param exam3Score
	 * @return
	 */

	public static String findExamAverage(Subject subject,String exam1Score, String exam2Score, String exam3Score, int arrsize, String examType) {

		if(exam1Score.length() == 0){
			exam1Score = "0";
		}

		if(exam2Score.length() == 0){
			exam2Score = "0";
		}

		if(exam3Score.length() == 0){
			exam3Score = "0";
		}

		double sum = 0;
		double mean = 0;
		
		if(StringUtils.equalsIgnoreCase(examType, EXAM_TYPE)){
			
			String catId = subCategoryDAO2.getSubCategory(subject.getAccountId(), subject.getUuid()).getCategoryId();
			Category cat = categoryDAO2.getCategoryById(subject.getAccountId(), catId);
			
			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Languages")){
				//
				sum = Integer.parseInt(exam1Score) + Integer.parseInt(exam2Score) + Integer.parseInt(exam3Score); 
				mean = Math.round(sum/2); 

			}

			if(StringUtils.equals(cat.getDescription(), "Sciences")){
				//(double)(num1 + num2) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT + num3; 
				sum =  (double) (Integer.parseInt(exam1Score) + Integer.parseInt(exam2Score)) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT + Integer.parseInt(exam3Score);  
				mean = Math.round(sum); 

			}


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Technicals")){


				if(StringUtils.equalsIgnoreCase(subject.getCode(), "AGR") || 
						StringUtils.containsIgnoreCase(subject.getDescription(), "Agriculture") || 
						StringUtils.equalsIgnoreCase(subject.getCode(), "HSC") || 
						StringUtils.containsIgnoreCase(subject.getDescription(), "Home Science") ||
						StringUtils.equalsIgnoreCase(subject.getCode(), "COM") || 
						StringUtils.containsIgnoreCase(subject.getDescription(), "Computer Studies")){
					
					sum =  (double) (Integer.parseInt(exam1Score) + Integer.parseInt(exam2Score)) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT + Integer.parseInt(exam3Score);  
					mean = Math.round(sum); 

				}else{
	               //
					sum = Integer.parseInt(exam1Score) + Integer.parseInt(exam2Score); 
					mean = Math.round(sum/2); 

					

				}

			}


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Humanities") || 
					StringUtils.equalsIgnoreCase(cat.getDescription(), "Mathematics")){
				
	            //
				sum = Integer.parseInt(exam1Score) + Integer.parseInt(exam2Score); 
				mean = Math.round(sum/2); 
				
			}
			
			return String.valueOf((int)mean); 
			
		}else {
			
			
			if(arrsize == 3){

				sum = Integer.parseInt(exam1Score) + Integer.parseInt(exam2Score) + Integer.parseInt(exam3Score); 
				mean = Math.round(sum/3);

			}
			if(arrsize == 2){

				sum = Integer.parseInt(exam1Score) + Integer.parseInt(exam2Score); 
				mean = Math.round(sum/2);

			}
			if(arrsize == 1){
				sum = Integer.parseInt(exam1Score); 
				mean = Math.round(sum); 
			}
			
			return String.valueOf((int)mean); 
		}
		
	}




	/**
	 * 
	 * @param examAverage
	 * @param uuid
	 * @param accountId
	 * @return
	 */

	public static String getRemarks(String examAverage, String uuid, String accountId) {

		String remark = "";

		if(examAverage.length() == 0){
			examAverage = "0";
		}

		int score = Integer.valueOf(examAverage);

		if (score >= 80) {
			remark = "Excellent";
		} else if (score >= 70) {
			remark = "Very good";
		} else if (score >= 60) {
			remark = "Relatively good";
		} else if (score >= 50) {
			remark = "Good";
		} else if (score >= 40) {
			remark = "Fair";
		} else if (score >= 35) {
			remark = "Poor";
		} else if  (score > 0){
			remark = "Very poor";
		}
		return remark;

	}




	/**
	 * @param accountId
	 * @param uuid
	 * @param yearlyMeanDAO 
	 * @param studentDAO 
	 * @param sysConfigDAO 
	 * @return
	 */
	public static JFreeChart generateLineGraph(String accountId, String studentId, YearlyMeanDAO yearlyMeanDAO, 
			StudentDAO studentDAO) {

		Student student = studentDAO.getStudentById(accountId, studentId); 
		int regYear = Integer.valueOf(yearformatter.format(student.getAdmissionDate()));  

		YearlyMean yearOne = new YearlyMean();
		YearlyMean yearTwo = new YearlyMean();
		YearlyMean yearThree = new YearlyMean();
		YearlyMean yearFour = new YearlyMean();

		if(yearlyMeanDAO.getYearlyMean(accountId, studentId, Integer.toString(regYear)) != null){
			yearOne = yearlyMeanDAO.getYearlyMean(accountId, studentId, Integer.toString(regYear)); 
		}

		if(yearlyMeanDAO.getYearlyMean(accountId, studentId, Integer.toString(regYear + 1)) != null){
			yearTwo = yearlyMeanDAO.getYearlyMean(accountId, studentId, Integer.toString(regYear + 1)); 
		}

		if(yearlyMeanDAO.getYearlyMean(accountId, studentId, Integer.toString(regYear + 2)) != null){
			yearThree = yearlyMeanDAO.getYearlyMean(accountId, studentId, Integer.toString(regYear + 2)); 
		}

		if(yearlyMeanDAO.getYearlyMean(accountId, studentId, Integer.toString(regYear + 3)) != null){
			yearFour = yearlyMeanDAO.getYearlyMean(accountId, studentId, Integer.toString(regYear + 3)); 
		}

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.setValue(Double.valueOf(df2.format(yearOne.getMeanOne() * GRAPH_CONSTANT)) , "Mean" , yearOne.getYear() + " T 1");
		dataset.setValue(Double.valueOf(df2.format(yearOne.getMeanTwo() * GRAPH_CONSTANT)), "Mean" , yearOne.getYear() + " T 2");
		dataset.setValue(Double.valueOf(df2.format(yearOne.getMeanThree() * GRAPH_CONSTANT)), "Mean" , yearOne.getYear() + " T 3");

		dataset.setValue(Double.valueOf(df2.format(yearTwo.getMeanOne() * GRAPH_CONSTANT)) , "Mean" , yearTwo.getYear() + " T 1");
		dataset.setValue(Double.valueOf(df2.format(yearTwo.getMeanTwo() * GRAPH_CONSTANT)), "Mean" , yearTwo.getYear() + " T 2");
		dataset.setValue(Double.valueOf(df2.format(yearTwo.getMeanThree() * GRAPH_CONSTANT)), "Mean" , yearTwo.getYear() + " T 3");

		dataset.setValue(Double.valueOf(df2.format(yearThree.getMeanOne() * GRAPH_CONSTANT)) , "Mean" , yearThree.getYear() + " T 1");
		dataset.setValue(Double.valueOf(df2.format(yearThree.getMeanTwo() * GRAPH_CONSTANT)), "Mean" , yearThree.getYear() + " T 2");
		dataset.setValue(Double.valueOf(df2.format(yearThree.getMeanThree() * GRAPH_CONSTANT)), "Mean" , yearThree.getYear() + " T 3");

		dataset.setValue(Double.valueOf(df2.format(yearFour.getMeanOne() * GRAPH_CONSTANT)) , "Mean" , yearFour.getYear() + " T 1");
		dataset.setValue(Double.valueOf(df2.format(yearFour.getMeanTwo() * GRAPH_CONSTANT)), "Mean" , yearFour.getYear() + " T 2");
		dataset.setValue(Double.valueOf(df2.format(yearFour.getMeanThree() * GRAPH_CONSTANT)), "Mean" , yearFour.getYear() + " T 3");

		dataset.setValue(12, "Control ", "Control ");

		JFreeChart chart = ChartFactory.createBarChart("Yearly Performance", // chart title
				"Year", // domain axis label (Y axis)
				"Mean", //  range axis label (X axis)
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend
				true, // tooltips?
				false);// URLs?


		CategoryPlot categoryPlot = chart.getCategoryPlot();
		BarRenderer br = (BarRenderer) categoryPlot.getRenderer();
		br.setMaximumBarWidth(0.05); // set maximum width to 10% of chart


		return chart;
	}



	static class StreamResult{

		String studentId;
		String result;
		double total;
		double point;

		public StreamResult(){
			studentId = "";
			result = "";
			total= 0;
			point = 0;
		}

		/**
		 * @return the studentId
		 */
		public String getStudentId() {
			return studentId;
		}

		/**
		 * @param studentId the studentId to set
		 */
		public void setStudentId(String studentId) {
			this.studentId = studentId;
		}

		/**
		 * @return the result
		 */
		public String getResult() {
			return result;
		}

		/**
		 * @param result the result to set
		 */
		public void setResult(String result) {
			this.result = result;
		}

		/**
		 * @return the total
		 */
		public double getTotal() {
			return total;
		}

		/**
		 * @param total the total to set
		 */
		public void setTotal(double total) {
			this.total = total;
		}

		/**
		 * @return the point
		 */
		public double getPoint() {
			return point;
		}

		/**
		 * @param point the point to set
		 */
		public void setPoint(double point) {
			this.point = point;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "StreamResult [studentId=" + studentId + ", result=" + result + ", total=" + total + ", point="
					+ point + "]";
		}



	}



	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @param exams
	 * @param streamId
	 * @param performanceList
	 * @param rankWithTotalMarks 
	 * @param rankWithPoints 
	 * @return
	 */

	public static String getStreamPosition(String accountId, String studentId, String streamId,
			List<Performance2> performanceList, boolean rankWithPoints, boolean rankWithTotalMarks) {




		List<Performance2> result = performanceList.parallelStream()
				.filter(performance -> streamId.equals(performance.getStreamId()))
				.collect(Collectors.toList()); 

		double total = 0;
		double prevTotal = 0;

		int scount = 1;
		int prevscount = 1;

		String cposition = "";


		List<StreamResult> positionList = new ArrayList<>();

		for(Performance2 performance : result){


			int mainPoint = performance.getTotalPoint();
			int totalMean = performance.getTotalMean();

			if(rankWithPoints && !rankWithTotalMarks){  

				total = mainPoint;


			}

			if(!rankWithPoints && rankWithTotalMarks){

				total = totalMean;

			}


			if(total == prevTotal){

				cposition = String.valueOf(scount-prevscount++);

			}else{

				prevscount = 1;
				cposition =  String.valueOf(scount);

			}

			StreamResult streamResult = new StreamResult();
			streamResult.setResult(cposition + " Out of: " + result.size());
			streamResult.setStudentId(performance.getStudentId());

			positionList.add(streamResult);


			scount++;
			prevTotal = total;
		}



		StreamResult position = positionList.parallelStream()
				.filter(student -> studentId.equals(student.getStudentId()))
				.findAny()
				.orElse(null);


		return position.getResult();
	}




	/**
	 * @param accountId
	 * @param uuid
	 * @param classroomId
	 * @param classResult
	 * @param performanceList
	 * @param rankWithPoints
	 * @param rankWithTotalMarks
	 * @return
	 */
	public static String getClassPosition(String accountId, String studentId,
			List<Performance2> performanceList, boolean rankWithPoints, boolean rankWithTotalMarks) {

		double total = 0;
		double prevTotal = 0;

		int scount = 1;
		int prevscount = 1;

		String cposition = "";

		List<StreamResult> positionList = new ArrayList<>();

		for(Performance2 performance : performanceList){


			int mainPoint = performance.getTotalPoint();
			int totalMean = performance.getTotalMean();

			if(rankWithPoints && !rankWithTotalMarks){  

				total = mainPoint;


			}

			if(!rankWithPoints && rankWithTotalMarks){

				total = totalMean;

			}


			if(total == prevTotal){

				cposition = String.valueOf(scount-prevscount++);

			}else{

				prevscount = 1;
				cposition =  String.valueOf(scount);

			}


			StreamResult streamResult = new StreamResult();
			streamResult.setResult(cposition + " Out of: " + performanceList.size());
			streamResult.setStudentId(performance.getStudentId());
			streamResult.setTotal(totalMean);
			streamResult.setPoint(mainPoint); 

			positionList.add(streamResult);


			scount++;
			prevTotal = total;
		}

		//return object
		StreamResult position = positionList.parallelStream()
				.filter(student -> studentId.equals(student.getStudentId()))
				.findAny()
				.orElse(null);

		/**return string 
		 String positionStr = positionList.parallelStream()
	                .filter(student -> studentId.equals(student.getStudentId()))
	                .map(StreamResult::getResult) //convert stream to String
	                .findAny()
	                .orElse("");*/



		return position.getResult();

	}



	/**
	 * @param performanceList
	 * @param rankWithPoints
	 * @param rankWithTotalMarks
	 * @return
	 */
	public static String getclassMean(List<Performance2> performanceList, boolean rankWithPoints, boolean rankWithTotalMarks, 
			boolean grade7subjects,boolean grade11subjects) {


		double total = 0;
		double totalMean = 0;
		double median = 0;
		double classMean = 0;

		for(Performance2 performance : performanceList){


			if(!rankWithPoints && rankWithTotalMarks){

				total = performance.getTotalMean();

				if(grade7subjects && !grade11subjects){
					median = total > 0 ? total / 7 : 0;
				}
				if(!grade7subjects && grade11subjects){
					median = total > 0 ? total / 11 : 0;
				}

			}else{

				total = performance.getTotalPoint();

				if(grade7subjects && !grade11subjects){
					median = total;
				}
				if(!grade7subjects && grade11subjects){

					median = (total / 132) * 84;
				}

			}


			totalMean += median;
			//a += b is short-hand for a = a + b
			//a =+ b is a = (+b)    

		}

		classMean = totalMean / performanceList.size();

		return df2.format(classMean); 
	}



	



	/**
	 * @param accountId
	 * @param total
	 * @param failedSubjects
	 * @return
	 */
	public static String getclassTeacherComment(String accountId, double total, List<FailedSubject> failedSubjects) {
		
		List<String> failedList = new ArrayList<>();
		
		String message = "";
		
		if(total>70){
			message = "Excellent";
		}else if(total>60){
			message = "Good";
		}else if(total>50){
			message = "Average";
		}else if(total>40){
			message = "Below average";
		}else if(total>30){
			message = "Much below average";
		}else{
			message = "Horrible"; 
		}
		
		failedSubjects.forEach(p -> {
			
			if(p.getScore() < 30){
				failedList.add(p.getSubjectCode()); 
			}
			
			
		});
		
		String subjects = "";
		if(!failedList.isEmpty()){
			 subjects = failedList.toString();
		}
		
		if(!StringUtils.isBlank(subjects)){
			message += " , put more effort in " + failedList.toString();
		}
		
		return message; 
	}




}












/***
 * 
 * 1) Languages. (P1,2 and 3)
 *    *P1 = 60
 *    *P2 = 80
 *    *P3 = 60
 * 
 * 
 * 2) Sciences. (P1,2 and 3)
 *    *P1 = 80
 *    *P2 = 80
 *    *p3 = 60
 * 
 * 
 * 3) Humanity (P1 and 2)
 *    *P1 = 100
 *    *P2 = 100
 * 
 * 
 * 4) Technical (P1 and 2 except AGR)
 *    
 *    AGR
 *    *P1 = 80
 *    *P2 = 80
 *    *P3 = 40
 *    
 *    else 
 *    P1 = 100
 *    P2 = 100
 * 
 * 
 * 
 * 5) Math (P1 and 2)
 *    *P1 = 100
 *    *P2 = 100
 *    
 *    
 *    
 *    new Thread(new Runnable() {
		     public void run() {
		          // code goes here.

		     }
		}).start();

 *
 */















