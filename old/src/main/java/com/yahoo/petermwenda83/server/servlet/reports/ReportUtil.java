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
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
import com.yahoo.petermwenda83.util.performance.comparator.FinalResultMeanComparator;
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



	private static GradingSystemDAO gradingSystemDAO;
	private static SubCategoryDAO subCategoryDAO;
	private static CategoryDAO categoryDAO;
	private static SubjectDAO subjectDAO;
	private static StudentDAO studentDAO;
	private static YearlyMeanDAO yearlyMeanDAO;
	private static ExamDAO examDAO;



	static {
		subCategoryDAO2 = SubCategoryDAO.getInstance();
		categoryDAO2 = CategoryDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		subCategoryDAO = SubCategoryDAO.getInstance();
		categoryDAO = CategoryDAO.getInstance(); 
		subjectDAO = SubjectDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		yearlyMeanDAO = YearlyMeanDAO.getInstance();
		examDAO = ExamDAO.getInstance();

	}

	
	public static String getExamName(String accountId, String[] exams, int i) { 
		
		String exam11 = "";
		String exam22 = "";
		String exam33 = "";

		if(exams.length == 1){

			exam11 = examDAO.getExam(accountId, exams[0]) != null ? examDAO.getExam(accountId, exams[0]).getDescription() : "";

		}

		if(exams.length == 2){

			exam11 = examDAO.getExam(accountId, exams[0]) != null ? examDAO.getExam(accountId, exams[0]).getDescription() : "";
			exam22 = examDAO.getExam(accountId, exams[1]) != null ? examDAO.getExam(accountId, exams[1]).getDescription() : "";

		}

		if(exams.length == 3){

			exam11 = examDAO.getExam(accountId, exams[0]) != null ? examDAO.getExam(accountId, exams[0]).getDescription() : "";
			exam22 = examDAO.getExam(accountId, exams[1]) != null ? examDAO.getExam(accountId, exams[1]).getDescription() : "";
			exam33 = examDAO.getExam(accountId, exams[2]) != null ? examDAO.getExam(accountId, exams[2]).getDescription() : "";

		}
		
		String examNames = exams.length+"_";
		
		if(i == 1) {
			
			examNames += exam11.length()== 0 ? "" : exam11+"_";
			examNames += exam22.length()== 0 ? "" : exam22+"_";
			examNames += exam33.length()== 0 ? "" : exam33+"_";
			
			if(StringUtils.endsWith(examNames, "_")) {
				examNames = removeLastChar(examNames); 
			}
			
		}else if(i == 0) {
			
			examNames = "(1) " + exam11 + "\n(2) " + exam22 +"\n(3) " + exam33;
			
		}
		
		return examNames;
	}


	private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
	/**
	 * 
	 * @param accountId
	 * @param streamId
	 * @param subjectId
	 * @param teacherSubjectDAO
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
	 * @param accountId  accountId account unique id
	 * @param streamId
	 * @param exam exam exam to rank
	 * @param examType
	 * @return performance object based on the best 7/11 subjects 
	 */

	public static Performance3 findExamTotalForm234(String accountId, String streamId, List<Perfomance> exam , String examType) {

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
					PerformanceP123 performanceP123 = computePaper123( accountId, perfomance);
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

					//JavaLogger.logInfo("____________ languagesCount" + languagesCount);


					if (languagesCount > 2) {
						Collections.sort(selectedLanguagesList, new PerformanceComparator());
						removedSubjectsPerfomanceList.add(selectedLanguagesList.remove(0));

					}
				}

				//select two best sciences
				else if (StringUtils.equalsIgnoreCase(desc, "Sciences")) {
					selectedSciencesList.add(perfomance);
					sciencesCount++;

					//JavaLogger.logInfo("____________ sciencesCount" + sciencesCount);

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

					//JavaLogger.logInfo("____________ Humanities" + humanitiesCount);

					if (humanitiesCount > 1) {
						//add remaining subjects if any to technical list
						Collections.sort(selectedHumanitiesList, new PerformanceComparator());
						selectedTechnicalsList.add(selectedHumanitiesList.remove(0));

					}
				}

				//add all technical
				else if (StringUtils.equalsIgnoreCase(desc, "Technicals")) {
					selectedTechnicalsList.add(perfomance);

					//JavaLogger.logInfo("____________ Technicals" );


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

					// JavaLogger.logInfo("____________ Mathematics" );

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
			performance3.setTotalPoints(getTotalsByPointsPerExam(finalPerfomanceList)); 
		}


		return performance3;
	}




	/**
	 * 
	 * @param performanceList
	 * @param accountId
	 * @param grade11subjects 
	 * @param grade7subjects 
	 * @param length 
	 * @return
	 */
	public static List<Performance2> getAverage( List<Performance2> performanceList, String accountId, boolean grade7subjects,
			boolean grade11subjects, int length) {

		List<Performance2> finalList = new ArrayList<>();

		for(Performance2 performance2 : performanceList){

			Map<String,Integer> exam1 = performance2.getExam1();
			Map<String,Integer> exam2 = performance2.getExam2();
			Map<String,Integer> exam3 = performance2.getExam3(); 

			List<Subject> subjects = subjectDAO.getSubjects(accountId);

			List<FinaResult> linaResultList = new ArrayList<>();

			subjects.parallelStream().forEach(subject -> {

				String exam1Score = String.valueOf(exam1.get(subject.getUuid()));
				String exam2Score = String.valueOf(exam2.get(subject.getUuid()));
				String exam3Score = String.valueOf(exam3.get(subject.getUuid()));



				if(StringUtils.equals(exam1Score, "0") || exam1Score.equalsIgnoreCase("null")){
					exam1Score = "";
				}
				if(StringUtils.equals(exam2Score, "0")|| exam2Score.equalsIgnoreCase("null")){
					exam2Score = "";
				}
				if(StringUtils.equals(exam3Score, "0")|| exam3Score.equalsIgnoreCase("null")){
					exam3Score = "";
				}


				double average  = 0;
				if(length == 3) {
					average = ReportUtil.findThreeExamAverage(exam1Score,exam2Score,exam3Score);
					
				}else if(length == 2) {
					average = ReportUtil.findTwoExamAverage(exam1Score,exam2Score);
					
				}else if(length == 1) {
					average = ReportUtil.findOneExamAverage(exam1Score);
					
				}
				
				
				String avgpoint = String.valueOf(ReportUtil.getPoints(String.valueOf((int)average), subject.getUuid(),accountId));
				int point =  Integer.valueOf(avgpoint); 

				FinaResult finaResult = new FinaResult();
				finaResult.setAverage((int)average); 
				finaResult.setPoint(point);
				finaResult.setSubjectId(subject.getUuid());

				linaResultList.add(finaResult);

			});


			//Student stu = studentDAO.getStudentById(accountId, performance2.getStudentId());
			//System.out.println(" ______________ " + stu.getRegNo() + " , name : " + stu.getFirstname());

			if(grade7subjects && !grade11subjects) {

				ExamAvg examavg = examAverage(linaResultList, accountId, performance2.getStudentId()); 
				performance2.setTotalMean(examavg.getToatlAverage());
				performance2.setTotalPoint(examavg.getTotalPoint());
				finalList.add(performance2);

			}

			if(!grade7subjects && grade11subjects) {

				ExamAvg examavg = examAverage11(linaResultList, accountId, performance2.getStudentId()); 
				performance2.setTotalMean(examavg.getToatlAverage());
				performance2.setTotalPoint(examavg.getTotalPoint());
				finalList.add(performance2);

			}




		}

		return finalList;
	}


	/**
	 * 
	 * @param linaResultList
	 * @param accountId
	 * @return
	 */
	public static ExamAvg examAverage(List<FinaResult> linaResultList , String accountId, String studentId) {

		List<FinaResult> finalPerfomanceList = new ArrayList<>();

		ExamAvg examAvg = new ExamAvg();

		if(!linaResultList.isEmpty()){

			int languagesCount = 0;
			int sciencesCount = 0;
			int humanitiesCount = 0;

			List<FinaResult> removedSubjectsPerfomanceList = new ArrayList<>();
			List<FinaResult> selectedLanguagesList = new ArrayList<>();
			List<FinaResult> selectedSciencesList = new ArrayList<>();
			List<FinaResult> selectedHumanitiesList = new ArrayList<>();
			List<FinaResult> selectedTechnicalsList = new ArrayList<>();

			for (FinaResult finaResult : linaResultList) {

				Subject subj;

				if(subjectDAO.getSubjectById(accountId, finaResult.getSubjectId()) != null) {

					subj = subjectDAO.getSubjectById(accountId, finaResult.getSubjectId()); 

				}else {

					subj = new Subject();
				}

				String desc = "";
				if(categoryDAO.getCategoryById(accountId, subj.getCategoryId()) != null) {
					desc = categoryDAO.getCategoryById(accountId, subj.getCategoryId()).getDescription(); 
				}


				//select two best languages
				if (StringUtils.equalsIgnoreCase(desc, "Languages")) {
					selectedLanguagesList.add(finaResult);
					languagesCount++;


					if (languagesCount > 2) {
						Collections.sort(selectedLanguagesList, new FinalResultMeanComparator());
						removedSubjectsPerfomanceList.add(selectedLanguagesList.remove(0));

					}

				}

				//select two best sciences
				else if (StringUtils.equalsIgnoreCase(desc, "Sciences")) {
					selectedSciencesList.add(finaResult);
					sciencesCount++;

					if (sciencesCount > 2) {
						//add remaining subjects if any to technical list
						Collections.sort(selectedSciencesList, new FinalResultMeanComparator());	
						selectedTechnicalsList.add(selectedSciencesList.remove(0));

					}

				}

				//select one best humanity 
				else if (StringUtils.equalsIgnoreCase(desc, "Humanities")) {
					selectedHumanitiesList.add(finaResult);
					humanitiesCount++;

					if (humanitiesCount > 1) {
						//add remaining subjects if any to technical list
						Collections.sort(selectedHumanitiesList, new FinalResultMeanComparator());
						selectedTechnicalsList.add(selectedHumanitiesList.remove(0));

					}

				}

				//add all technical
				else if (StringUtils.equalsIgnoreCase(desc, "Technicals")) {
					selectedTechnicalsList.add(finaResult);


				}

				//add mathematics
				else if (StringUtils.equalsIgnoreCase(desc, "Mathematics")) {

					finalPerfomanceList.add(finaResult);

				}

			}

			//select one best from the technical
			Collections.sort(selectedTechnicalsList, new FinalResultMeanComparator());

			if(selectedTechnicalsList.size() > 0){
				FinaResult highestTechnical = selectedTechnicalsList.remove(selectedTechnicalsList.size()-1);
				finalPerfomanceList.add(highestTechnical);

			}

			finalPerfomanceList.addAll(selectedLanguagesList);
			finalPerfomanceList.addAll(selectedSciencesList);
			finalPerfomanceList.addAll(selectedHumanitiesList);

			examAvg.setToatlAverage(getTotalAvg(finalPerfomanceList));
			examAvg.setTotalPoint(getTotalPints(finalPerfomanceList));

		}

		return examAvg;
	}


	/**
	 * 
	 * @param linaResultList
	 * @param accountId
	 * @return
	 */
	public static ExamAvg examAverage11(List<FinaResult> linaResultList , String accountId, String studentId) {

		List<FinaResult> finalPerfomanceList = new ArrayList<>();

		ExamAvg examAvg = new ExamAvg();

		if(!linaResultList.isEmpty()){

			int languagesCount = 0;
			int sciencesCount = 0;
			int humanitiesCount = 0;
			int technicalCount = 0;

			List<FinaResult> removedSubjectsPerfomanceList = new ArrayList<>();
			List<FinaResult> selectedLanguagesList = new ArrayList<>();
			List<FinaResult> selectedSciencesList = new ArrayList<>();
			List<FinaResult> selectedHumanitiesList = new ArrayList<>();
			List<FinaResult> selectedTechnicalsList = new ArrayList<>();

			for (FinaResult finaResult : linaResultList) {

				Subject subj = subjectDAO.getSubjectById(accountId, finaResult.getSubjectId()); 
				String desc = categoryDAO.getCategoryById(accountId, subj.getCategoryId()).getDescription(); 

				//select two best languages
				if (StringUtils.equalsIgnoreCase(desc, "Languages")) {
					selectedLanguagesList.add(finaResult);
					languagesCount++;


					if (languagesCount > 2) {
						Collections.sort(selectedLanguagesList, new FinalResultMeanComparator());
						removedSubjectsPerfomanceList.add(selectedLanguagesList.remove(0));

					}

				}

				//select two best sciences
				else if (StringUtils.equalsIgnoreCase(desc, "Sciences")) {
					selectedSciencesList.add(finaResult);
					sciencesCount++;

					if (sciencesCount > 3) {
						//add remaining subjects if any to technical list
						Collections.sort(selectedSciencesList, new FinalResultMeanComparator());	
						selectedTechnicalsList.add(selectedSciencesList.remove(0));

					}

				}

				//select one best humanity 
				else if (StringUtils.equalsIgnoreCase(desc, "Humanities")) {
					selectedHumanitiesList.add(finaResult);
					humanitiesCount++;

					if (humanitiesCount > 3) {
						//add remaining subjects if any to technical list
						Collections.sort(selectedHumanitiesList, new FinalResultMeanComparator());
						selectedTechnicalsList.add(selectedHumanitiesList.remove(0));

					}

				}

				//add selected technical
				else if (StringUtils.equalsIgnoreCase(desc, "Technicals")) {

					selectedTechnicalsList.add(finaResult);
					technicalCount++;

					if(technicalCount > 2){
						Collections.sort(selectedTechnicalsList, new FinalResultMeanComparator());
						selectedTechnicalsList.remove(0);
					}


				}

				//add mathematics
				else if (StringUtils.equalsIgnoreCase(desc, "Mathematics")) {

					finalPerfomanceList.add(finaResult);

				}

			}



			//select one best from the technical
			Collections.sort(selectedTechnicalsList, new FinalResultMeanComparator());

			if(selectedTechnicalsList.size() > 0){
				FinaResult highestTechnical = selectedTechnicalsList.remove(selectedTechnicalsList.size()-1);
				finalPerfomanceList.add(highestTechnical);

			}

			finalPerfomanceList.addAll(selectedLanguagesList);
			finalPerfomanceList.addAll(selectedSciencesList);
			finalPerfomanceList.addAll(selectedHumanitiesList);

			finalPerfomanceList.addAll(selectedTechnicalsList);

			examAvg.setToatlAverage(getTotalAvg(finalPerfomanceList));
			examAvg.setTotalPoint(getTotalPints(finalPerfomanceList));

		}

		return examAvg;
	}

	/** 
	 * 
	 * @param finalPerfomanceList
	 * @return
	 */
	public static int getTotalAvg(List<FinaResult> finalPerfomanceList) {
		int total = 0;
		for( FinaResult result : finalPerfomanceList ){

			total += result.getAverage();

			//System.out.println(" ,,,,,,,,,,,,,,,,,,, avg:  " + result.getAverage() + " , total : " + total); 
		}
		return total;
	}

	/**
	 * 
	 * @param finalPerfomanceList
	 */
	public static int getTotalPints(List<FinaResult> finalPerfomanceList) {
		int totalPoint = 0;
		for( FinaResult result : finalPerfomanceList ){
			int point = result.getPoint();
			totalPoint += point;

			//System.out.println(" ,,,,,,,,,,,,,,,,, avg: " + result.getAverage() + " , totalPoint : " + totalPoint); 
		}
		return totalPoint;
	}


	/**
	 * 
	 * @param finalPerfomanceList
	 * @param accountId
	 * @return
	 */
	public static PerformanceP123 computeP123(List<Perfomance> finalPerfomanceList, String accountId){ 

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

				int point = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId());
				grandPoints += point;


			}

			if(StringUtils.equals(cat.getDescription(), "Sciences")){
				//check the exam
				sum =  (double) (perfomance.getPaper1() + perfomance.getPaper2()) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT + perfomance.getPaper3();  
				grandTotal += sum;

				//paper1.add("sum:" + sum + " , grandTotal:" + grandTotal);

				int point = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId());
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

					int point = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId());
					grandPoints += point;



				}else{

					sum = (double)(perfomance.getPaper1() + perfomance.getPaper2()) / 2;
					grandTotal += sum;

					//paper1.add("sum:" + sum + " , grandTotal:" + grandTotal);

					int point = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId());
					grandPoints += point;

				}

			}


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Humanities") || 
					StringUtils.equalsIgnoreCase(cat.getDescription(), "Mathematics")){
				//check the exam
				sum = (double)(perfomance.getPaper1() + perfomance.getPaper2()) / 2;
				grandTotal += sum;

				//paper1.add("sum:" + sum + " , grandTotal:" + grandTotal);

				int point = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId());
				grandPoints += point;

			}


		}



		performanceP123.setTotalMean((int)Math.round(grandTotal));
		performanceP123.setTotalPoints(grandPoints); 


		return performanceP123;

	}


	/**
	 * 
	 * @param accountId
	 * @param perfomance
	 * @return
	 */
	public static PerformanceP123 computePaper123(String accountId,Perfomance perfomance) {


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
			points = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId());

			performanceP123.setStudentId(perfomance.getStudentId());
			performanceP123.setTotalMean((int)Math.round(sum)); 
			performanceP123.setTotalPoints(points);

			return performanceP123;


		}

		if(StringUtils.equals(cat.getDescription(), "Sciences")){

			sum =  (double) (perfomance.getPaper1() + perfomance.getPaper2()) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT + perfomance.getPaper3();  
			points = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId());

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
				points = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId());

				performanceP123.setStudentId(perfomance.getStudentId());
				performanceP123.setTotalMean((int)Math.round(sum)); 
				performanceP123.setTotalPoints(points);

				return performanceP123;

			}else{


				sum = (double)(perfomance.getPaper1() + perfomance.getPaper2()) / 2;
				points = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId());

				performanceP123.setStudentId(perfomance.getStudentId());
				performanceP123.setTotalMean((int)Math.round(sum)); 
				performanceP123.setTotalPoints(points);

				return performanceP123;

			}

		}


		if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Humanities") || 
				StringUtils.equalsIgnoreCase(cat.getDescription(), "Mathematics")){

			sum = (double)(perfomance.getPaper1() + perfomance.getPaper2()) / 2;
			points = getPoints(String.valueOf((int)Math.round(sum)),perfomance.getSubjectId(),perfomance.getAccountId());

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
	 * @param streamId
	 * @param exam
	 * @return
	 */
	public static Performance3 findExamTotalForm1(String accountId, String streamId, List<Perfomance> exam ) {




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

					//System.out.println("____________" + " Languages " + perfomance.getSubjectId() + " *** "+ perfomance.getScore());


					if (languagesCount > 2) {
						Collections.sort(selectedLanguagesList, new PerformanceComparator());
						removedSubjectsPerfomanceList.add(selectedLanguagesList.remove(0));

					}
				}

				//select three best sciences
				if (StringUtils.equalsIgnoreCase(desc, "Sciences")) {
					selectedSciencesList.add(perfomance);
					sciencesCount++;

					//System.out.println("____________" + " Sciences " + perfomance.getSubjectId() + " *** "+ perfomance.getScore()); 

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

					//System.out.println("____________" + " Humanities " + perfomance.getSubjectId() + " *** "+ perfomance.getScore()); 

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

					//System.out.println("____________" + " Technicals " + perfomance.getSubjectId() + " *** "+ perfomance.getScore()); 

					if(technicalCount > 2){
						Collections.sort(selectedTechnicalsList, new PerformanceComparator());
						selectedTechnicalsList.remove(0);
					}


				}

				//add mathematics
				if (StringUtils.equalsIgnoreCase(desc, "Mathematics")) {
					finalPerfomanceList.add(perfomance);
					//System.out.println("____________" + " Mathematics " + perfomance.getSubjectId() + " *** "+ perfomance.getScore()); 


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

		performance3.setTotalPoints(getTotalsByPointsPerExam(finalPerfomanceList)); 

		return performance3;
	}



	/**
	 * 
	 * @param accountId
	 * @param exam
	 * @param examType
	 * @return
	 */
	public static SubjectPerformance findSubjectPerformance(String accountId, List<Perfomance> exam, String examType) {


		SubjectPerformance subjectPerformance = new SubjectPerformance();



		if(StringUtils.equalsIgnoreCase(examType, EXAM_TYPE)){


			PerformanceP123 performanceP123 = new PerformanceP123();
			performanceP123 = ReportUtil.computeP123(exam, accountId);

			if(performanceP123.getTotalMean()  > 0){
				subjectPerformance.setTotal(performanceP123.getTotalMean());  
			}

			



		}else{
			
			
			int examTotal = getTotalsByTotalPerExam(exam);

			if(examTotal > 0){
				subjectPerformance.setTotal((double)examTotal);  
			}

			

		}

		return subjectPerformance;
	}



	/**
	 * 
	 * @param perfomanceList
	 * @return
	 */
	public static int getTotalsByPointsPerExam(List<Perfomance> perfomanceList){
		int totalPoints = 0;
		for( Perfomance perfomance : perfomanceList ){
			int point = getPoints(String.valueOf(perfomance.getScore()),perfomance.getSubjectId(),perfomance.getAccountId());
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
			
			/*System.out.println("********** totals : " + totals + " score : " + perfomance.getScore() + " sub: " + 
			subjectDAO.getSubjectById(perfomance.getAccountId(), perfomance.getSubjectId()).getCode());*/
		}
		
	//	System.out.println("************************************** : "  + totals);
		
		return totals;
	}



	/**
	 * 
	 * @param value
	 * @param subjectId
	 * @param accountId
	 * @return
	 */

	public static int getPoints(String value, String subjectId, String accountId){

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
	 * @return
	 */
	public static String getGrade(String value, String subjectId, String accountId){

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
	 * @return
	 */
	public static String getGradeMainForm234(int mean, String accountId) {

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
	 * @param subject
	 * @param exam1Score
	 * @param exam2Score
	 * @param exam3Score
	 * @param arrsize
	 * @param examType
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
				mean = Math.round(sum/3); //TODO

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
	 * @param exam1Score
	 * @param exam2Score
	 * @param exam3Score
	 * @return
	 */
	public static double findThreeExamAverage(String exam1Score, String exam2Score, String exam3Score) {

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

		sum = Integer.parseInt(exam1Score) + Integer.parseInt(exam2Score) + Integer.parseInt(exam3Score); 
		mean = Math.round(sum/3);

		return mean;

	}

	
	/**
	 * 
	 * @param exam1Score
	 * @param exam2Score
	 * @param exam3Score
	 * @return
	 */
	public static double findTwoExamAverage(String exam1Score, String exam2Score) {

		if(exam1Score.length() == 0){
			exam1Score = "0";
		}

		if(exam2Score.length() == 0){
			exam2Score = "0";
		}


		double sum = 0;
		double mean = 0;

		sum = Integer.parseInt(exam1Score) + Integer.parseInt(exam2Score); 
		mean = Math.round(sum/2);

		return mean;

	}

	
	/**
	 * 
	 * @param exam1Score
	 * @param exam2Score
	 * @param exam3Score
	 * @return
	 */
	public static double findOneExamAverage(String score) {

		if(score.length() == 0){
			score = "0";
		}

		
		double sum = 0;
		double mean = 0;

		sum = Integer.parseInt(score); 
		mean = Math.round(sum);

		return mean;

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
	 * 
	 * @param accountId
	 * @param studentId
	 * @param graphType
	 * @return
	 */
	public static JFreeChart generateLineGraph(String accountId, String studentId,
			String graphType) {

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

		if(StringUtils.equals(graphType, "1")) {

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

		}else {

			JFreeChart chart = ChartFactory.createLineChart("Yearly Performance", // chart title
					"Year", // domain axis label (Y axis)
					"Mean", //  range axis label (X axis)
					dataset, // data
					PlotOrientation.VERTICAL, // orientation
					true, // include legend
					true, // tooltips?
					false);// URLs?


			return chart;

		}

	}


	/**
	 * 
	 * @author peter
	 *
	 */
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
	 * @param studentId
	 * @param streamId
	 * @param performanceList
	 * @param rankWithPoints
	 * @param rankWithTotalMarks
	 * @return
	 */
	static String getStreamPosition(String accountId, String studentId, String streamId,
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
	 * 
	 * @param accountId
	 * @param studentId
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


			if(rankWithPoints && !rankWithTotalMarks){  

				total = performance.getTotalPoint();


			}

			if(!rankWithPoints && rankWithTotalMarks){

				total = performance.getTotalMean();

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
			streamResult.setTotal(performance.getTotalMean());
			streamResult.setPoint(performance.getTotalPoint()); 

			positionList.add(streamResult);


			scount++;
			prevTotal = total;
		}

		//return object
		StreamResult position = positionList.stream() 
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
	 * 
	 * @param performanceList
	 * @param rankWithPoints
	 * @param rankWithTotalMarks
	 * @param grade7subjects
	 * @param grade11subjects
	 * @return
	 */

	public static String getclassMean(List<Performance2> performanceList, boolean rankWithPoints, boolean rankWithTotalMarks, 
			boolean grade7subjects,boolean grade11subjects) {


		double total = 0;
		double totalMean = 0;
		double median = 0;
		double classMean = 0;

		for(Performance2 performance : performanceList){


			//if(!rankWithPoints && rankWithTotalMarks){

			total = performance.getTotalMean();

			if(grade7subjects && !grade11subjects){
				median = total > 0 ? total / 7 : 0;
			}
			if(!grade7subjects && grade11subjects){
				median = total > 0 ? total / 11 : 0;
			}

			/*}/*else{

				total = performance.getTotalPoint();

				if(grade7subjects && !grade11subjects){
					median = total;
				}
				if(!grade7subjects && grade11subjects){

					median = (total / 132) * 84;
				}

			}*/


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


	/**
	 * 
	 * @return
	 */
	public static String getHeadTeacherRemarks(int mean) {

		String remarks = "Your class work is ";

		if(mean > 80) {
			remarks += "Excellent.";

		}else if(mean > 70) {
			remarks += "Good.";

		}else if(mean > 60) {
			remarks += "good but you can do better.";

		}else if(mean > 50) {
			remarks += "not very good.";

		}else if(mean > 40) {
			remarks += "much below average.";

		}else {
			remarks += "Horrible!";
		}

		if(mean <=0) {
			remarks = "";
		}


		return remarks;
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















