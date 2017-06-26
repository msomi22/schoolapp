/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.util.ArrayList;
import java.util.Collections;
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
import com.yahoo.petermwenda83.util.performance.comparator.PerformanceComparator;
import com.yahoo.petermwenda83.util.performance.comparator.Test3ObjectComparator;

/**
 * @author peter
 *
 */
public class Test3 {

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

	private static final String[] exams = { "D50E6399-B913-42F2-A5B6-F0D4BAAF9571", "34C4244E-5CE0-4D5D-AD85-60E97FDDD80A",
	"16C4BF00-941C-40E4-9891-272D5F0979A1" };

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
		String streamId = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
		String term = "1";
		String year = "2016";


		List<Student> studentsList = studentDAO.getStudentByStream(accountId, streamId);

		if(exams.length == 3){

			List<Test3Object> performanceList = getStudentScore(accountId, streamId, term, year, studentsList);

			Collections.sort(performanceList, new Test3ObjectComparator());
			Collections.reverse(performanceList);

			for(Test3Object test3Object : performanceList){
				
				List<Perfomance> exam1 = test3Object.getExam1();
				List<Perfomance> exam2 = test3Object.getExam2();
				List<Perfomance> exam3 = test3Object.getExam3(); 


				System.out.println("****************************************************************************************");
				System.out.println("Student: " + test3Object.getStudentId() + " , Score : " + test3Object.getTotalScore()); 
				System.out.println("_______________________________________________________________________________________");

				exam1.forEach(e1 -> {
					String subject = subjectDAO.getSubjectById(accountId, e1.getSubjectId()).getDescription();
					
					System.out.println("exam 1 sub: " + subject + " , score: " + e1.getScore());
				});
				
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

				exam2.forEach(e2 -> {
					String subject = subjectDAO.getSubjectById(accountId, e2.getSubjectId()).getDescription();
					
					System.out.println("exam 2 sub: " + subject + " , score: " + e2.getScore());
				});
				
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

				exam3.forEach(e3 -> {
					String subject = subjectDAO.getSubjectById(accountId, e3.getSubjectId()).getDescription();
					
					System.out.println("exam 3 sub: " + subject + " , score: " + e3.getScore());
				});
				
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");


			}




		}


	}


	/**
	 * @param accountId
	 * @param streamId
	 * @param term
	 * @param year
	 * @param studentsList
	 */
	private static List<Test3Object> getStudentScore(String accountId, String streamId, String term, String year,
			List<Student> studentsList) {

		List<Test3Object> test3ObjectList = new ArrayList<>();
		List<Perfomance> exam1;
		List<Perfomance> exam2;
		List<Perfomance> exam3;
		
		for(Student student : studentsList ){

			exam1 = perfomanceDAO.getStreamPerformance(accountId, exams[0], student.getUuid(), streamId, term, year);
			exam2 = perfomanceDAO.getStreamPerformance(accountId, exams[1], student.getUuid(), streamId, term, year);
			exam3 = perfomanceDAO.getStreamPerformance(accountId, exams[2], student.getUuid(), streamId, term, year); 

			Test3Performance totalExam1 = null;
			Test3Performance totalExam2 = null;
			Test3Performance totalExam3 = null;
			 totalExam1 = findExamTotal(accountId, exam1);
			 totalExam2 = findExamTotal(accountId, exam2);
			 totalExam3 = findExamTotal(accountId, exam3);


			int totals = totalExam1.getTotal() + totalExam2.getTotal() + totalExam3.getTotal();

			Test3Object test3Object = new Test3Object();
			test3Object.setExam1(totalExam1.getPerfomanceList());
			test3Object.setExam2(totalExam2.getPerfomanceList());
			test3Object.setExam3(totalExam3.getPerfomanceList());
			test3Object.setStudentId(student.getUuid());
			test3Object.setTotalScore(totals); 

			test3ObjectList.add(test3Object);

		}

		return test3ObjectList;
	}


	/**
	 * @param accountId
	 * @param exam1
	 */
	private static Test3Performance findExamTotal(String accountId, List<Perfomance> exam1) {
		
		List<Perfomance> finalPerfomanceList = new ArrayList<>();
		List<Perfomance> perfomanceList = new ArrayList<>();
		
		
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
				
				perfomanceList.add(perfomance);
				

				String catId = subCategoryDAO.getSubCategory(accountId, perfomance.getSubjectId()).getCategoryId();
				String desc = categoryDAO.getCategoryById(accountId, catId).getDescription();

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

			}

           			//some code here
			Collections.sort(selectedTechnicalsList, new PerformanceComparator());

			if(selectedTechnicalsList.size() > 0){
				Perfomance highestTechnical = selectedTechnicalsList.remove(selectedTechnicalsList.size()-1);
				finalPerfomanceList.add(highestTechnical);

			}

			finalPerfomanceList.addAll(selectedLanguagesList);
			finalPerfomanceList.addAll(selectedSciencesList);
			finalPerfomanceList.addAll(selectedHumanitiesList);

		}

		Test3Performance test3Performance = new Test3Performance();
		test3Performance.setPerfomanceList(perfomanceList); 
		test3Performance.setTotal(getTotalsPerExam(finalPerfomanceList)); 
		
		
		return test3Performance;
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
