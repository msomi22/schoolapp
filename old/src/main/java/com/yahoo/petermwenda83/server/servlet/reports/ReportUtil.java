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

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.yahoo.petermwenda83.bean.exam.Exam;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.exam.YearlyMean;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Category;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.YearlyMeanDAO;
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

	public static final int LANG_P1_OUTOF = 60;
	public static final int LANG_P2_OUTOF = 80;
	public static final int LANG_P3_OUTOF = 60;

	public static final int SCI_AGR_P1_OUTOF = 80;
	public static final int SCI_AGR_P2_OUTOF = 80;
	public static final int SCI_AGR_P3_OUTOF = 40;

	public static final int HUMAN_TECH_MATH_P1_OUTOF = 100;
	public static final int HUMAN_TECH_MATH_P2_OUTOF = 100;
	
	public static final String EXAM_TYPE = "P123";


	/**
	 * @param accountId
	 * @param streamId
	 * @param uuid
	 * @return
	 */
	public static String getInitials(String accountId, String streamId, String uuid) {
		return RandomStringUtils.randomAlphabetic(2).toUpperCase();
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

	public static Performance3 findExamTotalForm234(String accountId, List<Perfomance> exam , SubCategoryDAO subCategoryDAO,
			CategoryDAO categoryDAO, SubjectDAO subjectDAO, GradingSystemDAO gradingSystemDAO, ExamDAO examDAO,String examType) {

		List<Perfomance> finalPerfomanceList = new ArrayList<>();
		Map<String,Integer> perfomanceMap = new HashMap<>(); 


		if(!exam.isEmpty()){

			int languagesCount = 0;
			int sciencesCount = 0;
			int humanitiesCount = 0;

			List<Perfomance> removedSubjectsPerfomanceList = new ArrayList<>();
			List<Perfomance> selectedLanguagesList = new ArrayList<>();
			List<Perfomance> selectedSciencesList = new ArrayList<>();
			List<Perfomance> selectedHumanitiesList = new ArrayList<>();
			List<Perfomance> selectedTechnicalsList = new ArrayList<>();



			for (Perfomance perfomance : exam) {

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

				//select two best sciences
				if (StringUtils.equalsIgnoreCase(desc, "Sciences")) {
					selectedSciencesList.add(perfomance);
					sciencesCount++;

					if (sciencesCount > 2) {
						//add remaining subjects if any to technical list
						Collections.sort(selectedSciencesList, new PerformanceComparator());	
						selectedTechnicalsList.add(selectedSciencesList.remove(0));

					}
				}

				//select one best humanity 
				if (StringUtils.equalsIgnoreCase(desc, "Humanities")) {
					selectedHumanitiesList.add(perfomance);
					humanitiesCount++;

					if (humanitiesCount > 1) {
						//add remaining subjects if any to technical list
						Collections.sort(selectedHumanitiesList, new PerformanceComparator());
						selectedTechnicalsList.add(selectedHumanitiesList.remove(0));

					}
				}

				//add all technical
				if (StringUtils.equalsIgnoreCase(desc, "Technicals")) {
					selectedTechnicalsList.add(perfomance);


				}

				//add mathematics
				if (StringUtils.equalsIgnoreCase(desc, "Mathematics")) {
					finalPerfomanceList.add(perfomance);


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

		}

		Performance3 performance3 = new Performance3();
		performance3.setPerfomanceMap(perfomanceMap); 
		performance3.setTotalMean(getTotalsByTotalPerExam(finalPerfomanceList)); 
		performance3.setTotalPoints(getTotalsByPointsPerExam(finalPerfomanceList, subjectDAO, gradingSystemDAO)); 

		if(StringUtils.equalsIgnoreCase(examType, EXAM_TYPE)){

			PerformanceP123 performanceP123 = new PerformanceP123();
			performanceP123 = ReportUtil.computeP123(finalPerfomanceList, subjectDAO, subCategoryDAO, categoryDAO, examDAO, gradingSystemDAO, accountId);

			performance3.setTotalMean(performanceP123.getTotalMean());
			performance3.setTotalPoints(performanceP123.getTotalPoints());

		}else{

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
	 * @param accountId
	 * @return
	 */
	public static PerformanceP123 computeP123(List<Perfomance> finalPerfomanceList, SubjectDAO subjectDAO, 
			SubCategoryDAO subCategoryDAO, CategoryDAO categoryDAO , ExamDAO examDAO, GradingSystemDAO gradingSystemDAO, String accountId){ 

		double total = 0;
		double grandTotal = 0;
		int grandPoints = 0;
		double sum = 0;

		double scoreP1 = 0;
		double scoreP2 = 0;
		double scoreP3 = 0;

		List<Subject> subjects = subjectDAO.getSubjects(accountId);

		Map<String,Subject> subjectMap = new HashMap<>();
		for(Subject sub : subjects){
			subjectMap.put(sub.getUuid(), sub);
		}


		for(Perfomance perfomance : finalPerfomanceList){
			//TODO

			Subject subject = subjectMap.get(perfomance.getSubjectId());
			String catId = subCategoryDAO.getSubCategory(accountId, subject.getUuid()).getCategoryId();
			Category cat = categoryDAO.getCategoryById(accountId, catId);

			Exam exam = examDAO.getExam(accountId, perfomance.getExamId());


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Languages")){
				//check the exam
				if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 1")){
					scoreP1 = perfomance.getScore();
				}else if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 2")){
					scoreP2 = perfomance.getScore();
				}else if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 3")){
					scoreP3 = perfomance.getScore();
				}


				sum = scoreP1 + scoreP2 + scoreP3;

				total = sum / 2;
				grandTotal += total;

				int point = getPoints(String.valueOf((int)Math.round(total)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
				grandPoints += point;

				//System.out.println("\nLang --> scoreP1: " + scoreP1 + " , scoreP2: " + scoreP2 + " , scoreP3:" + scoreP3 + " , total: " + total  + " , point:" + point);

				total = 0; scoreP1 = 0; scoreP2 = 0; scoreP3 = 0;


			}

			if(StringUtils.equals(cat.getDescription(), "Sciences")){
				//check the exam
				if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 1")){
					scoreP1 = perfomance.getScore();
				}else if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 2")){
					scoreP2 = perfomance.getScore();
				}else if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 3")){
					scoreP3 = perfomance.getScore();
				}

				sum = scoreP1 + scoreP2;

				total = (double)sum / 2;
				total += scoreP3;

				grandTotal += total;

				int point = getPoints(String.valueOf((int)Math.round(total)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
				grandPoints += point;

				//System.out.println("\nSci --> scoreP1: " + scoreP1 + " , scoreP2: " + scoreP2 + " , scoreP3:" + scoreP3 + " , total: " + total  + " , point:" + point);


				total = 0; scoreP1 = 0; scoreP2 = 0; scoreP3 = 0;


			}


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Technicals")){


				if(StringUtils.equalsIgnoreCase(subject.getCode(), "AGR") || StringUtils.containsIgnoreCase(subject.getDescription(), "AGR")){

					//check the exam
					if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 1")){
						scoreP1 = perfomance.getScore();
					}else if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 2")){
						scoreP2 = perfomance.getScore();
					}else if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 3")){
						scoreP3 = perfomance.getScore();
					}

					sum = scoreP1 + scoreP2;

					total = (double)sum / 2;
					total += scoreP3;

					grandTotal += total;

					int point = getPoints(String.valueOf((int)Math.round(total)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
					grandPoints += point;

					//System.out.println("\nAgr --> scoreP1: " + scoreP1 + " , scoreP2: " + scoreP2 + " , scoreP3:" + scoreP3 + " , total: " + total  + " , point:" + point);


					total = 0; scoreP1 = 0; scoreP2 = 0; scoreP3 = 0;



				}else{


					//check the exam
					if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 1")){
						scoreP1 = perfomance.getScore();
					}else if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 2")){
						scoreP2 = perfomance.getScore();
					}

					sum = scoreP1 + scoreP2;
					total = (double)sum / 2;

					grandTotal += total;

					int point = getPoints(String.valueOf((int)Math.round(total)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
					grandPoints += point;

					//System.out.println("\nTech --> scoreP1: " + scoreP1 + " , scoreP2: " + scoreP2 + " , scoreP3:" + scoreP3 + " , total: " + total  + " , point:" + point);

					total = 0; scoreP1 = 0; scoreP2 = 0; scoreP3 = 0;


				}

			}


			if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Humanities") || StringUtils.equalsIgnoreCase(cat.getDescription(), "Mathematics")){
				//check the exam
				if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 1")){
					scoreP1 = perfomance.getScore();
				}else if(StringUtils.equalsIgnoreCase(exam.getDescription(), "Paper 2")){
					scoreP2 = perfomance.getScore();
				}


				sum = scoreP1 + scoreP2;
				total = (double)sum / 2;

				grandTotal += total;

				int point = getPoints(String.valueOf((int)Math.round(total)),perfomance.getSubjectId(),perfomance.getAccountId(), subjectDAO, gradingSystemDAO);
				grandPoints += point;

				//System.out.println("\nHuman --> scoreP1: " + scoreP1 + " , scoreP2: " + scoreP2 + " , scoreP3:" + scoreP3 + " , total: " + total + " , point:" + point);


				total = 0; scoreP1 = 0; scoreP2 = 0; scoreP3 = 0;


			}

		}



		//System.out.println("_____________________________________________________________________ grandPoints: " + grandPoints); 

		PerformanceP123 performanceP123 = new PerformanceP123();
		performanceP123.setTotalMean((int)Math.round(grandTotal));
		performanceP123.setTotalPoints(grandPoints); 


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

	public static Performance3 findExamTotalForm1(String accountId, List<Perfomance> exam , SubCategoryDAO subCategoryDAO,
			CategoryDAO categoryDAO, SubjectDAO subjectDAO, GradingSystemDAO gradingSystemDAO) {

		List<Perfomance> finalPerfomanceList = new ArrayList<>();
		Map<String,Integer> perfomanceMap = new HashMap<>(); 


		if(!exam.isEmpty()){

			int languagesCount = 0;
			int sciencesCount = 0;
			int humanitiesCount = 0;
			int technicalCount = 0;

			List<Perfomance> removedSubjectsPerfomanceList = new ArrayList<>();
			List<Perfomance> selectedLanguagesList = new ArrayList<>();
			List<Perfomance> selectedSciencesList = new ArrayList<>();
			List<Perfomance> selectedHumanitiesList = new ArrayList<>();
			List<Perfomance> selectedTechnicalsList = new ArrayList<>();



			for (Perfomance perfomance : exam) {

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

		gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, categoryId);

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

	public static String findExamAverage(String exam1Score, String exam2Score, String exam3Score, int arrsize) {

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

		System.out.println("---------------------------------" + yearOne.getMeanOne()* GRAPH_CONSTANT); 

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
 */















