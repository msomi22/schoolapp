/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.CategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubCategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.servlet.util.Timeit;
import com.yahoo.petermwenda83.util.performance.comparator.FinalPerfomancescomparator;
import com.yahoo.petermwenda83.util.performance.comparator.PerformanceComparator;

/**
 * @author peter
 *
 */
public class Test2 {

	static final String databaseName = "schooldb";
	static final String Host = "localhost";
	static final String databaseUsername = "school";
	static final String databasePassword = "AllaManO1";
	static final int databasePort = 5432;

	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;
	private static SubCategoryDAO subCategoryDAO;
	private static CategoryDAO categoryDAO;
	private static StudentDAO studentDAO;
	private static ExamDAO examDAO;

	static {
		perfomanceDAO = new PerfomanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		subjectDAO = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		subCategoryDAO = new SubCategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		categoryDAO = new CategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
		studentDAO = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		examDAO = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Timeit.code(() -> compute());

	}


	/**
	 * @param args
	 */
	public static void compute() {


		String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		//String studentId = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
		String streamId = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
		String term = "1";
		String year = "2016";
		List<Perfomance> perfomanceList = null;

		String[] exams = { "D50E6399-B913-42F2-A5B6-F0D4BAAF9571", "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A",
		"16C4BF00-941C-40E4-9891-272D5F0979A1" };

		System.out.println(); 

		List<Student> studentsList = studentDAO.getStudentByStream(accountId, streamId);

		
		HashMap<String,List<FinalPerfomance>> finalPerfomancesMap = new HashMap<>();
		
		
		
		
		
		for( int i = 0; i < exams.length; i++){
			List<FinalPerfomance> finalPerfomancesList = new ArrayList<>();
			List<FinalPerfomance> averagePerfomancesList = new ArrayList<>();
			int totScore = 0;
			for(Student student : studentsList ){
				List<Perfomance> selectedLanguagesList = new ArrayList<>();
				List<Perfomance> selectedSciencesList = new ArrayList<>();
				List<Perfomance> selectedHumanitiesList = new ArrayList<>();
				List<Perfomance> selectedTechnicalsList = new ArrayList<>();
				List<Perfomance> finalPerfomanceList = new ArrayList<>();
				List<Perfomance> removedSubjectsPerfomanceList = new ArrayList<>();
				perfomanceList = perfomanceDAO.getStreamPerformance(accountId, exams[i], student.getUuid(), streamId, term, year);
				
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
								selectedTechnicalsList.add(selectedSciencesList.remove(0));

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


				}
				
				if(!perfomanceList.isEmpty()){
					Collections.sort(selectedTechnicalsList, new PerformanceComparator());

					if(selectedTechnicalsList.size() > 0){
						Perfomance highestTechnical = selectedTechnicalsList.remove(selectedTechnicalsList.size()-1);

						removedSubjectsPerfomanceList.addAll(selectedTechnicalsList);
						finalPerfomanceList.add(highestTechnical);
						selectedTechnicalsList.clear();
					}

					finalPerfomanceList.addAll(selectedLanguagesList);
					finalPerfomanceList.addAll(selectedSciencesList);
					finalPerfomanceList.addAll(selectedHumanitiesList);

					FinalPerfomance finalPerfomance = new FinalPerfomance();
					finalPerfomance.setPerfomanceList(finalPerfomanceList);
					totScore += getTotalsPerExam(finalPerfomanceList);
					finalPerfomance.setTotalScore(getTotalsPerExam(finalPerfomanceList));
					finalPerfomancesList.add(finalPerfomance);


				}
				
			}
			
			//System.out.println("------" + exams[i] + "-----" + finalPerfomancesList.get(0).getTotalScore());
			
			finalPerfomancesMap.put(exams[i], finalPerfomancesList);
			
			
			
			
		}

        List<Integer> averagesList = new ArrayList<>();
        int count = 0;
        for(Student student : studentsList){
        	int av = 0;
        	int averaged = 0;
        	for(int i = 0; i < exams.length; i++){
        		
        		if(finalPerfomancesMap.get(exams[i]).size() > 0){
        		av += finalPerfomancesMap.get(exams[i]).get(count).getTotalScore();
        		averaged++;
        		}
        	}
        	
        	av = av / averaged;
        	System.out.println("++++++++++++++++av    " + av);
        	count++;
        }

		System.out.println("count---" + finalPerfomancesMap.size() );
		
		
		

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



}
