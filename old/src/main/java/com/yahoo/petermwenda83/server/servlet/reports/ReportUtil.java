/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.subject.CategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubCategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.util.performance.comparator.PerformanceComparator;

/**
 * @author peter
 *
 */
public class ReportUtil {




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
	 * @param accountId
	 * @param exam1
	 * @param subCategoryDAO
	 * @param categoryDAO
	 * @param subjectDAO
	 * @param gradingSystemDAO
	 * @return
	 */

	public static  Performance3 findExamTotalForm234(String accountId, List<Perfomance> exam1 , SubCategoryDAO subCategoryDAO,
			CategoryDAO categoryDAO, SubjectDAO subjectDAO, GradingSystemDAO gradingSystemDAO) {

		List<Perfomance> finalPerfomanceList = new ArrayList<>();
		Map<String,Integer> perfomanceMap = new HashMap<>(); 


		if(!exam1.isEmpty()){

			int languagesCount = 0;
			int sciencesCount = 0;
			int humanitiesCount = 0;

			List<Perfomance> removedSubjectsPerfomanceList = new ArrayList<>();
			List<Perfomance> selectedLanguagesList = new ArrayList<>();
			List<Perfomance> selectedSciencesList = new ArrayList<>();
			List<Perfomance> selectedHumanitiesList = new ArrayList<>();
			List<Perfomance> selectedTechnicalsList = new ArrayList<>();



			for (Perfomance perfomance : exam1) {

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
		performance3.setTotalPoits(getTotalsByPointsPerExam(finalPerfomanceList, subjectDAO, gradingSystemDAO)); 


		return performance3;
	}
	
	
	
	
	/**
	 * 
	 * @param accountId
	 * @param exam1
	 * @param subCategoryDAO
	 * @param categoryDAO
	 * @param subjectDAO
	 * @param gradingSystemDAO
	 * @return
	 */

	public static  Performance3 findExamTotalForm1(String accountId, List<Perfomance> exam1 , SubCategoryDAO subCategoryDAO,
			CategoryDAO categoryDAO, SubjectDAO subjectDAO, GradingSystemDAO gradingSystemDAO) {

		List<Perfomance> finalPerfomanceList = new ArrayList<>();
		Map<String,Integer> perfomanceMap = new HashMap<>(); 


		if(!exam1.isEmpty()){

			int languagesCount = 0;
			int sciencesCount = 0;
			int humanitiesCount = 0;
			int technicalCount = 0;

			List<Perfomance> removedSubjectsPerfomanceList = new ArrayList<>();
			List<Perfomance> selectedLanguagesList = new ArrayList<>();
			List<Perfomance> selectedSciencesList = new ArrayList<>();
			List<Perfomance> selectedHumanitiesList = new ArrayList<>();
			List<Perfomance> selectedTechnicalsList = new ArrayList<>();



			for (Perfomance perfomance : exam1) {

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
		performance3.setTotalPoits(getTotalsByPointsPerExam(finalPerfomanceList, subjectDAO, gradingSystemDAO)); 


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

	public static String getGradeMain(int mean, String accountId, GradingSystemDAO gradingSystemDAO) {

		String grade = "";

		int point = (int) mean / 7; 

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
			mean = Math.ceil(sum/3);

		}
		if(arrsize == 2){

			sum = Integer.parseInt(exam1Score) + Integer.parseInt(exam2Score); 
			mean = Math.ceil(sum/2);

		}
		if(arrsize == 1){
			sum = Integer.parseInt(exam1Score); 
			mean = Math.ceil(sum); 
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

}
