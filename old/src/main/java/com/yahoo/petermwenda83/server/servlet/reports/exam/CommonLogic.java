package com.yahoo.petermwenda83.server.servlet.reports.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.classroom.Stream;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Category;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.staff.TeacherSubjectDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.CategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubCategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.servlet.reports.ReportUtil;

public class CommonLogic {

	private static SubCategoryDAO subCategoryDAO;
	private static CategoryDAO categoryDAO;
	private static SubjectDAO subjectDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static StreamDAO streamDAO;
	private static StudentDAO studentDAO;
	//private static ClassDAO classDAO;
	private static TeacherSubjectDAO teacherSubjectDAO;
	private static StaffDAO staffDAO;

	static {
		subCategoryDAO = SubCategoryDAO.getInstance();
		categoryDAO = CategoryDAO.getInstance(); 
		subjectDAO = SubjectDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		//classDAO = ClassDAO.getInstance();
		teacherSubjectDAO = TeacherSubjectDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
	}
	/**
	 * 
	 * @param perfomanceList
	 * @param isPaper123
	 * @return
	 */
	public static List<PerformanceBean1> subjectAnalyzer(List<Perfomance> perfomanceList, boolean isPaper123) {

		List<PerformanceBean1> objList = new ArrayList<>();

		perfomanceList.stream().forEach(performance -> {

			PerformanceBean1 obj = new PerformanceBean1();
			obj.setStudentId(performance.getStudentId());
			obj.setSubjectId(performance.getSubjectId());
			obj.setStreamId(performance.getStreamId());
			obj.setClassRoomId(performance.getClassRoomId());

			if(isPaper123) {

				int score = computePaper123(performance.getAccountId(), performance.getSubjectId(), performance.getPaper1(),
						performance.getPaper2(), performance.getPaper3());

				obj.setScore(score);

			}else {
				int score = performance.getScore();
				obj.setScore(score);

			}

			objList.add(obj);

		});

		return objList;

	}



	/**
	 * 
	 * @param accountId
	 * @param subjetId
	 * @param paper1
	 * @param paper2
	 * @param paper3
	 * @return
	 */
	private static int computePaper123(String accountId,String subjetId, int paper1, int paper2, int paper3) { 

		Subject subject = subjectDAO.getSubjectById(accountId, subjetId);
		String catId = subCategoryDAO.getSubCategory(accountId, subject.getUuid()).getCategoryId();
		Category cat = categoryDAO.getCategoryById(accountId, catId);

		double sum = 0, grandTotal= 0;
		int grandPoints =0;

		if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Languages")){
			sum = (double)(paper1 + paper2 + paper3) / 2;  
			grandTotal += sum;

			int point = ReportUtil.getPoints(String.valueOf((int)Math.round(sum)), subjetId, accountId);
			grandPoints += point;


		}

		if(StringUtils.equals(cat.getDescription(), "Sciences")){
			sum =  (double) (paper1 + paper2) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT + paper3;  
			grandTotal += sum;

			int point = ReportUtil.getPoints(String.valueOf((int)Math.round(sum)), subjetId, accountId);
			grandPoints += point;


		}


		if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Technicals")){


			if(StringUtils.equalsIgnoreCase(subject.getCode(), "AGR") || 
					StringUtils.containsIgnoreCase(subject.getDescription(), "Agriculture") || 
					StringUtils.equalsIgnoreCase(subject.getCode(), "HSC") || 
					StringUtils.containsIgnoreCase(subject.getDescription(), "Home Science") ||
					StringUtils.equalsIgnoreCase(subject.getCode(), "COM") || 
					StringUtils.containsIgnoreCase(subject.getDescription(), "Computer Studies")){

				sum =  (double) (paper1 + paper2) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT +paper3;  
				grandTotal += sum;

				int point = ReportUtil.getPoints(String.valueOf((int)Math.round(sum)), subjetId, accountId);
				grandPoints += point;



			}else{

				sum = (double)(paper1 + paper2) / 2;
				grandTotal += sum;

				int point = ReportUtil.getPoints(String.valueOf((int)Math.round(sum)), subjetId, accountId);
				grandPoints += point; 

			}

		}


		if(StringUtils.equalsIgnoreCase(cat.getDescription(), "Humanities") || 
				StringUtils.equalsIgnoreCase(cat.getDescription(), "Mathematics")){

			sum = (double)(paper1 + paper2) / 2;
			grandTotal += sum;

			int point = ReportUtil.getPoints(String.valueOf((int)Math.round(sum)), subjetId, accountId);
			grandPoints += point; 

		}

		return (int)Math.round(grandTotal); 
	}


	/**
	 * 
	 * @param examList1
	 * @param examList2
	 * @param examList3
	 * @param predicate
	 * @param subjectId 
	 * @param accountId 
	 * @param classroomId 
	 * @return
	 */

	public static List<PerformanceBean1> combineExams(List<PerformanceBean1> examList1, List<PerformanceBean1> examList2, 
			List<PerformanceBean1> examList3, int predicate, String subjectId, String accountId, String classroomId) { 

		switch (predicate) {

		case 1:
			return combiner(examList1,null,null,predicate, subjectId, accountId, classroomId);

		case 2:
			return combiner(examList1,examList2,null,predicate, subjectId, accountId, classroomId);

		case 3:
			return combiner(examList1,examList2,examList3,predicate, subjectId, accountId, classroomId);

		default:
			return null;

		}

	}


	/**
	 * 
	 * @param examList1
	 * @param examList2
	 * @param examList3
	 * @param predicate
	 * @param subjectId 
	 * @param accountId 
	 * @param classroomId 
	 * @return
	 */
	private static List<PerformanceBean1> combiner(List<PerformanceBean1> examList1, List<PerformanceBean1> examList2, 
			List<PerformanceBean1> examList3, int predicate, String subjectId, String accountId, String classroomId) { 

		List<PerformanceBean1> examListFinal = new ArrayList<>(); 

		List<Student> studentsList = new ArrayList<>();

		List<Stream> streamList = streamDAO.getStreamList(accountId, classroomId) != null ? streamDAO.getStreamList(accountId, classroomId) : new ArrayList<>();

		for(Stream stream : streamList){

			List<Student> studentListStream = studentDAO.getStudentByStream(accountId, stream.getUuid()) != null ? studentDAO.getStudentByStream(accountId, stream.getUuid()) : new ArrayList<>(); 

			if(!studentListStream.isEmpty())
				studentsList.addAll(studentListStream); 

		}



		/**
		 * three exams
		 */
		if(predicate == 3) { 

			//all students
			studentsList.stream().forEach(student -> {

				PerformanceBean1 performanceBean1 = new PerformanceBean1();

				int score1 = examList1.stream()
						.filter(perfor -> StringUtils.equals(perfor.getStudentId(), student.getUuid()))
						.map(PerformanceBean1::getScore)
						.findAny()
						.orElse(0);

				int score2 = examList2.stream()
						.filter(perfor -> StringUtils.equals(perfor.getStudentId(), student.getUuid()))
						.map(PerformanceBean1::getScore)
						.findAny()
						.orElse(0);

				int score3 = examList3.stream()
						.filter(perfor -> StringUtils.equals(perfor.getStudentId(), student.getUuid()))
						.map(PerformanceBean1::getScore)
						.findAny()
						.orElse(0);

				int sum = 0;
				double mean = 0;

				sum = score1 + score2 + score3;
				mean = sum / 3;

				performanceBean1.setStudentId(student.getUuid());
				performanceBean1.setSubjectId(subjectId);
				performanceBean1.setStreamId(student.getCurrentStream());
				performanceBean1.setClassRoomId(classroomId);
				performanceBean1.setScore((int)mean);

				if(sum > 0) {
					examListFinal.add(performanceBean1); 
				}


			});


		}
		/**
		 * two exams
		 */
		if(predicate == 2) {

			//all students
			studentsList.stream().forEach(student -> {

				PerformanceBean1 performanceBean1 = new PerformanceBean1();

				int score1 = examList1.stream()
						.filter(perfor -> StringUtils.equals(perfor.getStudentId(), student.getUuid()))
						.map(PerformanceBean1::getScore)
						.findAny()
						.orElse(0);

				int score2 = examList2.stream()
						.filter(perfor -> StringUtils.equals(perfor.getStudentId(), student.getUuid()))
						.map(PerformanceBean1::getScore)
						.findAny()
						.orElse(0);


				int sum = 0;
				double mean = 0;

				sum = score1 + score2;
				mean = sum / 2;

				performanceBean1.setStudentId(student.getUuid());
				performanceBean1.setSubjectId(subjectId);
				performanceBean1.setStreamId(student.getCurrentStream());
				performanceBean1.setClassRoomId(classroomId);
				performanceBean1.setScore((int)mean);

				if(sum > 0) {
					examListFinal.add(performanceBean1); 
				}

			});


		}

		/**
		 *  one exam
		 */
		if(predicate == 1) {

			//all students
			studentsList.stream().forEach(student -> {

				PerformanceBean1 performanceBean1 = new PerformanceBean1();

				int score1 = examList1.stream()
						.filter(perfor -> StringUtils.equals(perfor.getStudentId(), student.getUuid()))
						.map(PerformanceBean1::getScore)
						.findAny()
						.orElse(0);

				performanceBean1.setStudentId(student.getUuid());
				performanceBean1.setSubjectId(subjectId);
				performanceBean1.setStreamId(student.getCurrentStream());
				performanceBean1.setClassRoomId(classroomId);
				performanceBean1.setScore(score1);

				if(score1 > 0) {
					examListFinal.add(performanceBean1); 
				}

			});


		}

		return examListFinal;
	}

	/**
	 * 
	 * @param finalExamList
	 * @param accountId
	 * @param string
	 * @param string2
	 * @return 
	 * @return
	 */
	public static String getGradeCount(List<PerformanceBean1> finalExamList, String accountId, 
			String subjectId, String item, String rowItem) {

		String value = "";
		String generalId = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";
		long count = 0;
		double sum = 0;
		double avg = 0;
		List<PerformanceBean1> list = new ArrayList<>();


		if(!StringUtils.equals(rowItem, "Total")) {

			if(streamDAO.getStreamByDesc(accountId, rowItem) != null) {

				list = finalExamList.stream().
						filter(perfo -> StringUtils.equals(perfo.getSubjectId(), subjectId)).
						filter(perfo -> StringUtils.equals(perfo.getStreamId(), streamDAO.getStreamByDesc(accountId, rowItem).getUuid())).
						collect(Collectors.toList());

				sum = list.stream().filter(performance -> performance.getScore() > 0).mapToInt(performance -> performance.getScore()).sum();
				//sum = list.stream().filter(performance -> performance.getScore() > 0).mapToInt(PerformanceBean1::getScore).sum();

			}
		}



		if(!list.isEmpty()) {

			//System.out.println("sum : " + sum + " size :" + list.size() + " sub: " + subjectDAO.getSubjectById(accountId, subjectId).getCode());  


			switch(item) {
			case "A":
				if(!StringUtils.equals(rowItem, "Total")) {
					//Collections.frequency(list, "xx");
					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "A") != null) {
						count = list.stream().filter(performance -> 
						performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "A").getLowerLimit()
						&&
						performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "A").getUpperLimit()
								).count(); 

					}
					value = "" + count;


				}else {
					//total
				}
				return value;


			case "A-":
				if(!StringUtils.equals(rowItem, "Total")) {
					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "A-") != null) {
						count = list.stream().filter(performance -> 
						performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "A-").getLowerLimit()
						&&
						performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "A-").getUpperLimit()
								).count(); 
					}
					value = "" + count;

				}else {
					//total
				}
				return value;


			case "B+":
				if(!StringUtils.equals(rowItem, "Total")) {
					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "B+") != null) {
						count = list.stream().filter(performance -> 
						performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "B+").getLowerLimit()
						&&
						performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "B+").getUpperLimit()
								).count(); 
					}
					value = "" + count;

				}else {
					//total
				}
				return value;



			case "B":
				if(!StringUtils.equals(rowItem, "Total")) {
					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "B") != null) {
						count = list.stream().filter(performance -> 
						performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "B").getLowerLimit()
						&&
						performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "B").getUpperLimit()
								).count(); 
					}
					value = "" + count;

				}else {
					//total
				}
				return value;



			case "B-":
				if(!StringUtils.equals(rowItem, "Total")) {
					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "B-") != null) {
						count = list.stream().filter(performance -> 
						performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "B-").getLowerLimit()
						&&
						performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "B-").getUpperLimit()
								).count(); 
					}
					value = "" + count;

				}else {
					//total
				}
				return value;



			case "C+":
				if(!StringUtils.equals(rowItem, "Total")) {
					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "C+") != null) {
						count = list.stream().filter(performance -> 
						performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "C+").getLowerLimit()
						&&
						performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "C+").getUpperLimit()
								).count(); 
					}
					value = "" + count;

				}else {
					//total
				}
				return value;



			case "C":
				if(!StringUtils.equals(rowItem, "Total")) {
					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "C") != null) {
						count = list.stream().filter(performance -> 
						performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "C").getLowerLimit()
						&&
						performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "C").getUpperLimit()
								).count(); 
					}
					value = "" + count;

				}else {
					//total
				}
				return value;



			case "C-":
				if(!StringUtils.equals(rowItem, "Total")) {
					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "C-") != null) {
						count = list.stream().filter(performance ->
						performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "C-").getLowerLimit()
						&&
						performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "C-").getUpperLimit()
								).count(); 
					}
					value = "" + count;

				}else {
					//total
				}
				return value;



			case "D+":
				if(!StringUtils.equals(rowItem, "Total")) {
					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "D+") != null) {
						count = list.stream().filter(performance -> 
						performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "D+").getLowerLimit()
						&&
						performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "D+").getUpperLimit()
								).count(); 
					}
					value = "" + count;

				}else {
					//total
				}
				return value;




			case "D":
				if(!StringUtils.equals(rowItem, "Total")) {
					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "D") != null) {
						count = list.stream().filter(performance -> 
						performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "D").getLowerLimit()
						&&
						performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "D").getUpperLimit()
								).count(); 
					}
					value = "" + count;

				}else {
					//total
				}
				return value;



			case "D-":

				if(!StringUtils.equals(rowItem, "Total")) {
					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "D-") != null) {
						count = list.stream().filter(performance -> 
						performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "D-").getLowerLimit()
						&&
						performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "D-").getUpperLimit()
								).count(); 
					}
					value = "" + count;

				}else {
					//total
				}
				return value;



			case "E":
				if(!StringUtils.equals(rowItem, "Total")) {

					if(gradingSystemDAO.getGradesByDesc(accountId, generalId, "E") != null) {

						count = list.stream()
								.filter(performance -> 
								performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, "E").getLowerLimit()
								&&
								performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, "E").getUpperLimit()
										).count(); 



					}
					value = "" + count;



				}else {
					//total
				}
				return value;


			case "Entry":
				if(!StringUtils.equals(rowItem, "Total")) {


					value = "" + list.size();



				}else {
					//total
				}
				return value;

			case "Points":
				if(!StringUtils.equals(rowItem, "Total")) {


					//value = "" + ReportUtil.df2.format(avg); 
					value = "" + ReportUtil.df2.format(sum); 




				}else {
					//total
				}
				return value;

			case "Grade":
				if(!StringUtils.equals(rowItem, "Total")) {

					if(list.size() > 0) {
						avg = sum / list.size(); 
					}


					String grade = ReportUtil.getGradeMainForm234((int)avg, accountId);
					value = "" + grade;



				}else {
					//total
				}
				return value;




			default:
				value = "";
				return value;

			}

		}else {

			value = "";
			return value;

		}

	}



	/**
	 * 
	 * @param accountId
	 * @param rowItem
	 * @param subjectId
	 * @return
	 */
	public static String getSubjectTeacher(String accountId, String rowItem, String subjectId) {

		String streamId = "";
		if(!StringUtils.equals(rowItem, "Total")) {
			if(streamDAO.getStreamByDesc(accountId, rowItem) != null) {
				streamId = streamDAO.getStreamByDesc(accountId, rowItem).getUuid();
			}

		}

		String teacherId = "";
		if(teacherSubjectDAO.getTeacherSubject(accountId, streamId, subjectId) !=null) {
			teacherId = teacherSubjectDAO.getTeacherSubject(accountId, streamId, subjectId).getTeacherId();
		}

		String  teacher = "";
		if(staffDAO.getStaff(accountId, teacherId) != null) {
			teacher = staffDAO.getStaff(accountId, teacherId).getFirstname() + " " + staffDAO.getStaff(accountId, teacherId).getLastname();
		}

		teacher = teacher.substring(0, Math.min(teacher.length(), 14));

		return teacher;
	}


	/**
	 * 
	 * @param finalExamMap
	 * @param streamName
	 * @param accountId
	 * @param item (a grade, etc)  
	 */

	public static String streamAnalyzer(Map<String, List<PerformanceBean1>> finalExamMap, String rowItem, String classroomId,
			String accountId, String predicate) {

		String value = "";

		// as long as the column is not last (the total column, this means as long as we have a stream name) 
		if(!StringUtils.equals(rowItem, "Total")) {
			// as long as the column item ( a stream name , is found in the database)  
			if(streamDAO.getStreamByDesc(accountId, rowItem) != null) {

				List<PerformanceBean1> performanceBean1List = new ArrayList<>();

				subjectDAO.getSubjects(accountId).stream().forEach(subject -> {

					if(finalExamMap.get(subject.getUuid()) != null) {

						studentDAO.getStudentByStream(accountId, streamDAO.getStreamByDesc(accountId, rowItem).getUuid(), "1")
						.stream().forEach(student ->{

							int score = finalExamMap.get(subject.getUuid())
									.stream()
									.filter(perfor -> StringUtils.equals(perfor.getStudentId(), student.getUuid()))
									.map(PerformanceBean1::getScore)
									.findAny()
									.orElse(0);

							PerformanceBean1 performanceBean1 = new PerformanceBean1();
							performanceBean1.setStudentId(student.getUuid());
							performanceBean1.setSubjectId(subject.getUuid());
							performanceBean1.setStreamId(student.getCurrentStream()); 
							performanceBean1.setClassRoomId(classroomId);
							performanceBean1.setScore(score); 

							performanceBean1List.add(performanceBean1);

						});

					}

				});
				//logic here

				//System.out.println(performanceBean1List);
				//System.out.println("******************************************");
				value = analyzeStream(performanceBean1List, accountId, predicate, rowItem);


			}
		}


		return value;

	}

	/**
	 * 
	 * @param performanceBean1List
	 * @param predicate
	 * @return
	 */
	private static String analyzeStream(List<PerformanceBean1> performanceBean1List, String accountId, String predicate, String rowItem) {

		String value = "";

		switch(predicate) {

		case"A":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "A");

			}else {
				//total
			}
			return value;
			
		case"A-":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "A-");

			}else {
				//total
			}
			return value;
			
		case"B+":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "B+");

			}else {
				//total
			}
			return value;
			
		case"B":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "B");

			}else {
				//total
			}
			return value;
			
		case"B-":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "B-");

			}else {
				//total
			}
			return value;
			
		case"C+":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "C+");

			}else {
				//total
			}
			return value;
			
		case"C":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "C");

			}else {
				//total
			}
			return value;
			
		case"C-":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "C-");

			}else {
				//total
			}
			return value;
			
		case"D+":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "D+");

			}else {
				//total
			}
			return value;
			
		case"D":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "D");

			}else {
				//total
			}
			return value;
			
		case"D-":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "D-");

			}else {
				//total
			}
			return value;
			
		case"E":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeResult(performanceBean1List, accountId, "E");

			}else {
				//total
			}
			return value;

		default:
			return value;

		}

	}

	/**
	 * 
	 * @param performanceBean1List
	 * @param string
	 * @return
	 */
	private static String computeResult(List<PerformanceBean1> performanceBean1List, String accountId, String predicate) {

		String generalId = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";

		long count = 0;
		if(gradingSystemDAO.getGradesByDesc(accountId, generalId, predicate) != null) {

			count = performanceBean1List.stream()
					.filter(performance -> 
					performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, predicate).getLowerLimit()
					&&
					performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, predicate).getUpperLimit()
							).count(); 
		}

		return ""+count;
	}



	/**
	 * 
	 * @param finalExamMap
	 * @param rowItem
	 * @param accountId
	 * @param columValue
	 * @return
	 */
	public static String genderAnalyzer(Map<String, List<PerformanceBean1>> finalExamMap, String rowItem, String classroomId,
			String accountId, String predicate) {
		
		
		List<Student> studentsList = new ArrayList<>();

		List<Stream> streamList = streamDAO.getStreamList(accountId, classroomId) != null ? streamDAO.getStreamList(accountId, classroomId) : new ArrayList<>();

		for(Stream stream : streamList){

			List<Student> studentListStream = studentDAO.getStudentByStream(accountId, stream.getUuid()) != null ? studentDAO.getStudentByStream(accountId, stream.getUuid()) : new ArrayList<>(); 

			if(!studentListStream.isEmpty())
				studentsList.addAll(studentListStream); 

		}
		
		//System.out.println("size : " + studentsList.size());

		String value = "";
		
		StringBuilder rowItemV = new StringBuilder();
		// as long as the column is not last (the total column, this means as long as we have a stream name) 
		if(!StringUtils.equals(rowItem, "Total")) {
			// as long as the column item ( a stream name , is found in the database)  
			
			if(StringUtils.equals(rowItem, "Male")) {
				rowItemV.append("M");
			}if(StringUtils.equals(rowItem, "Female")) {
				rowItemV.append("F");
			}
			
			//System.out.println("rowItemV : " + rowItemV.toString()); 
			
				List<PerformanceBean1> performanceBean1List = new ArrayList<>();

				subjectDAO.getSubjects(accountId).stream().forEach(subject -> {

					if(finalExamMap.get(subject.getUuid()) != null) {

						studentsList
						.stream() 
						.filter(stu -> StringUtils.equalsIgnoreCase(stu.getGender(), rowItemV.toString().toUpperCase()))
						.filter(stu -> StringUtils.equalsIgnoreCase(stu.getIsActive(),"1")) 
						.forEach(student ->{ 

							int score = finalExamMap.get(subject.getUuid())
									.stream()
									.filter(perfor -> StringUtils.equals(perfor.getStudentId(), student.getUuid()))
									.map(PerformanceBean1::getScore)
									.findAny()
									.orElse(0);

							PerformanceBean1 performanceBean1 = new PerformanceBean1();
							performanceBean1.setStudentId(student.getUuid());
							performanceBean1.setSubjectId(subject.getUuid());
							performanceBean1.setStreamId(student.getCurrentStream()); 
							performanceBean1.setClassRoomId(classroomId);
							performanceBean1.setScore(score); 

							performanceBean1List.add(performanceBean1);

						});

					}

				});
				//logic here

				//System.out.println(performanceBean1List);
				//System.out.println("******************************************");

				value = analyzeGender(performanceBean1List, accountId, predicate, rowItemV.toString());


		
		}

		return value;

	}


	/**
	 * 
	 * @param performanceBean1List
	 * @param accountId
	 * @param predicate
	 * @param rowItem
	 * @return
	 */
	private static String analyzeGender(List<PerformanceBean1> performanceBean1List, String accountId, String predicate,
			String rowItem) {

		String value = "";

		switch(predicate) {


		case"A":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "A");

			}else {
				//total
			}
			return value;
			
		case"A-":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "A-");

			}else {
				//total
			}
			return value;
			
		case"B+":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "B+");

			}else {
				//total
			}
			return value;
			
		case"B":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "B");

			}else {
				//total
			}
			return value;
			
		case"B-":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "B-");

			}else {
				//total
			}
			return value;
			
		case"C+":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "C+");

			}else {
				//total
			}
			return value;
			
		case"C":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "C");

			}else {
				//total
			}
			return value;
			
		case"C-":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "C-");

			}else {
				//total
			}
			return value;
			
		case"D+":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "D+");

			}else {
				//total
			}
			return value;
			
		case"D":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "D");

			}else {
				//total
			}
			return value;
			
		case"D-":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "D-");

			}else {
				//total
			}
			return value;
			
		case"E":
			if(!StringUtils.equals(rowItem, "Total")) {
				value = "" + computeGender(performanceBean1List, accountId, "E");

			}else {
				//total
			}
			return value;
			
			

		default:
			return value;

		}

	}


/**
 * 
 * @param performanceBean1List
 * @param accountId
 * @param string
 * @return
 */
	private static String computeGender(List<PerformanceBean1> performanceBean1List, String accountId, String predicate) {
		String generalId = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";

		long count = 0;
		if(gradingSystemDAO.getGradesByDesc(accountId, generalId, predicate) != null) {

			count = performanceBean1List.stream()
					.filter(performance -> 
					performance.getScore() >= gradingSystemDAO.getGradesByDesc(accountId, generalId, predicate).getLowerLimit()
					&&
					performance.getScore() <= gradingSystemDAO.getGradesByDesc(accountId, generalId, predicate).getUpperLimit()
							).count(); 
		}

		return ""+count;
	}


}
