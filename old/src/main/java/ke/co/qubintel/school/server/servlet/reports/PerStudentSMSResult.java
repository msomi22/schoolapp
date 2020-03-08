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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.api.rest.GeneralService;
import ke.co.qubintel.school.server.api.rest.bean.ApiResponse;
import ke.co.qubintel.school.server.api.rest.bean.SmsExams;
import ke.co.qubintel.school.server.bean.exam.Exam;
import ke.co.qubintel.school.server.bean.exam.Perfomance;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.persistence.exam.ExamDAO;
import ke.co.qubintel.school.server.persistence.exam.GradingSystemDAO;
import ke.co.qubintel.school.server.persistence.exam.PerfomanceDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.persistence.subject.CategoryDAO;
import ke.co.qubintel.school.server.persistence.subject.SubCategoryDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;

/**
 * @author peter
 *
 */
public class PerStudentSMSResult {
	
	
	private static ExamDAO examDAO;
	private static SysConfigDAO sysConfigDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static PerfomanceDAO perfomanceDAO;
	private static StudentDAO studentDAO;
	private static SubCategoryDAO subCategoryDAO;
	private static CategoryDAO categoryDAO;
	private static SubjectDAO subjectDAO;
	
	static GeneralService generalService = new GeneralService();
	
	static {
		examDAO = ExamDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		perfomanceDAO = PerfomanceDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		subCategoryDAO = SubCategoryDAO.getInstance();
		categoryDAO = CategoryDAO.getInstance(); 
		subjectDAO = SubjectDAO.getInstance();
	}
	
	
	/**
	 * 
	 * @param accountId
	 * @param regNo
	 * @param subjectsNo
	 * @param examType
	 * @return
	 */
	public static boolean valideRequest(String accountId, String regNo, String subjectsNo, String examType) {
		
		if(studentDAO.getStudentByregNo(accountId, regNo) == null) {
			//System.out.println("1");
			return false;
		}else if(!StringUtils.isNumeric(subjectsNo)) { 
			//System.out.println("2");
			return false;
		}else if(!validSubNo(subjectsNo)) { 
			//System.out.println("3");
			return false;
		}else if(!validExamType(examType)) { 
			//System.out.println("4");
			return false;
		}
		else {
			//System.out.println("5");
			return true;
		}
	}
	
	/**
	 * 
	 * @param subjectsNo
	 * @return
	 */
	private static boolean validSubNo(String subjectsNo) {
		
		if(Integer.valueOf(subjectsNo) == 7) {
			return true;
			
		}else if(Integer.valueOf(subjectsNo) == 11) {
			return true;
			
		}else {
			return false;
		}
	}

	/**
	 * 
	 * @param accountId
	 * @param exams
	 * @return
	 */
	public static boolean validaExams(String accountId,List<SmsExams> exams) {
		boolean valid = false;
		
		if(exams.size() <= 0) {
			valid = false;
			
		}else if(exams.size() > 3) {
			valid = false;
			
		}
		
		for(SmsExams exam : exams) {
			if(examDAO.getExamByQuey(accountId, exam.getExamCode()) == null) {
				valid = false;
			}else {
				valid = true;
			}
		}
		return valid;
	}
	
	
	/**
	 * 
	 * @param accountId
	 * @param regNo
	 * @param subjectsNo
	 * @param examType
	 * @param exams 
	 * @return
	 */
	public static Object processResult(String accountId, String regNo, boolean subjectsNo, String examType, List<SmsExams> exams) { 
		
		String studentId = studentDAO.getStudentByregNo(accountId, regNo).getUuid(); 
		
		String[] examIds = null;// = new String[3]; 
		
		//System.out.println("******" + exams.size()); 
		//System.out.println(" *---* " + examIds.length); 
		
		ApiResponse response = new ApiResponse();
		
		
		if(exams.size() == 1) {
			
			examIds = new String[1];
			SmsExams code = exams.get(0);
			Exam exam = examDAO.getExamByQuey(accountId, code.getExamCode());
			examIds[0] = exam.getUuid();  

			return generalService.sendExamResultSMS(accountId, studentId, examIds, subjectsNo, examType);
			
			
		}else if(exams.size() == 2) {
			
			examIds = new String[2];
			SmsExams code1 = exams.get(0);
			SmsExams code2 = exams.get(1);
			
			Exam exam1 = examDAO.getExamByQuey(accountId, code1.getExamCode());
			Exam exam2 = examDAO.getExamByQuey(accountId, code2.getExamCode());
			
			examIds[0] = exam1.getUuid();
			examIds[1] = exam2.getUuid();

			return generalService.sendExamResultSMS(accountId, studentId, examIds, subjectsNo, examType);
			
			
		}if(exams.size() == 3) {
			
			examIds = new String[3]; 
			SmsExams code1 = exams.get(0);
			SmsExams code2 = exams.get(1);
			SmsExams code3 = exams.get(2); 
			
			Exam exam1 = examDAO.getExamByQuey(accountId, code1.getExamCode());
			Exam exam2 = examDAO.getExamByQuey(accountId, code2.getExamCode());
			Exam exam3 = examDAO.getExamByQuey(accountId, code3.getExamCode());
			
			examIds[0] = exam1.getUuid();
			examIds[1] = exam2.getUuid();
			examIds[2] = exam3.getUuid();

			return generalService.sendExamResultSMS(accountId, studentId, examIds, subjectsNo, examType);
			
			
		}else {
			
			response.setMessage("error");
			response.setDescription("Invalid number os exams!");
			return response;
			
		}
		
	}
	

	/**
	 * 
	 * @param examType
	 * @return
	 */
	private static boolean validExamType(String examType) {
		
		return true;
	}

	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @param examIds
	 * @param subjects7
	 * @param examType 
	 * @return
	 */
	public static List<Performance2> geStudentResult(String accountId, String studentId, String[] examIds, boolean subjects7,String examType) {  


		int totalPoint = 0;
		int totalMeans = 0;
		
		List<Performance2> performance2List = new ArrayList<>();
		
		if(studentDAO.getStudentById(accountId, studentId) == null) {
			//error, student not found
			return null;

		}else if(sysConfigDAO.getSysConfig(accountId) == null) {
			 //error , contact ADMIN
			return null;
			
		}else {

			Student student = studentDAO.getStudentById(accountId, studentId);
			SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

			String streamId = student.getCurrentStream();
			String term = sysConfig.getTerm();
			String year = sysConfig.getYear();
			
			Performance3 totalExam1 = new Performance3();
			Performance3 totalExam2 = new Performance3();
			Performance3 totalExam3 = new Performance3();
			

			if(examIds.length == 1) {
				
				List<Perfomance> perfomanceList = new ArrayList<>();
				
				if(perfomanceDAO.getStreamPerformance(accountId, examIds[0], studentId, streamId, term, year) != null) {
					perfomanceList = perfomanceDAO.getStreamPerformance(accountId, examIds[0], studentId, streamId, term, year);
					
					if(subjects7) {
						totalExam1 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), 
								perfomanceList,examType);
					}else {
						totalExam1 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), 
								perfomanceList);
					}
					
					totalPoint = totalExam1.getTotalPoints();

					totalMeans = totalExam1.getTotalMean();
					
				}
				
				
				
			}else if(examIds.length == 2) {
				
				List<Perfomance> perfomanceList1 = new ArrayList<>();
				List<Perfomance> perfomanceList2 = new ArrayList<>();

				if(perfomanceDAO.getStreamPerformance(accountId, examIds[0], studentId, streamId, term, year) != null) {
					perfomanceList1 = perfomanceDAO.getStreamPerformance(accountId, examIds[0], studentId, streamId, term, year);
					
					if(subjects7) {
						totalExam1 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), 
								perfomanceList1,  examType);
					}else {
						totalExam1 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(),
								perfomanceList1);
					}
				}

				if(perfomanceDAO.getStreamPerformance(accountId, examIds[1], studentId, streamId, term, year) != null) {
					perfomanceList2 = perfomanceDAO.getStreamPerformance(accountId, examIds[1], studentId, streamId, term, year);
					
					if(subjects7) {
						totalExam2 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), 
								perfomanceList2,  examType);
					}else {
						totalExam2 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), 
								perfomanceList2);
					}
					
					totalPoint = totalExam1.getTotalPoints() + totalExam2.getTotalPoints();
					totalPoint = totalPoint / 2;

					totalMeans = totalExam1.getTotalMean() + totalExam2.getTotalMean();
					totalMeans = totalMeans / 2;
				}





			}
			else if(examIds.length == 3) {
				
				List<Perfomance> perfomanceList1 = new ArrayList<>();
				List<Perfomance> perfomanceList2 = new ArrayList<>();
				List<Perfomance> perfomanceList3 = new ArrayList<>();

				if(perfomanceDAO.getStreamPerformance(accountId, examIds[0], studentId, streamId, term, year) != null) {
					
					perfomanceList1 = perfomanceDAO.getStreamPerformance(accountId, examIds[0], studentId, streamId, term, year);
					
					if(subjects7) {
						totalExam1 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), 
								perfomanceList1,  examType);
					}else {
						totalExam1 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), 
								perfomanceList1);
					}
				}

				if(perfomanceDAO.getStreamPerformance(accountId, examIds[1], studentId, streamId, term, year) != null) {
					
					perfomanceList2 = perfomanceDAO.getStreamPerformance(accountId, examIds[1], studentId, streamId, term, year);
					
					if(subjects7) {
						totalExam2 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), 
								perfomanceList2,  examType);
					}else {
						totalExam2 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), 
								perfomanceList2);
					}
				}
				if( perfomanceDAO.getStreamPerformance(accountId, examIds[2], studentId, streamId, term, year) != null) {
					
					perfomanceList3 = perfomanceDAO.getStreamPerformance(accountId, examIds[2], studentId, streamId, term, year);
					
					if(subjects7) {
						totalExam3 = ReportUtil.findExamTotalForm234(accountId, student.getCurrentStream(), 
								perfomanceList3, examType);
					}else {
						totalExam3 = ReportUtil.findExamTotalForm1(accountId, student.getCurrentStream(), 
								perfomanceList3);
					}
					
					totalPoint = totalExam1.getTotalPoints() + totalExam2.getTotalPoints() + totalExam3.getTotalPoints();
					totalPoint = totalPoint / 3;

					totalMeans = totalExam1.getTotalMean() + totalExam2.getTotalMean() + totalExam3.getTotalMean();
					totalMeans = totalMeans / 3;
					
					
					
				}

			}else {
				//error
				return null;
			}
			
			
			if(totalMeans > 0 || totalPoint > 0){

				Performance2 performance2 = new Performance2();
				performance2.setExam1(totalExam1.getPerfomanceMap());
				performance2.setExam2(totalExam2.getPerfomanceMap());
				performance2.setExam3(totalExam3.getPerfomanceMap()); 
				performance2.setStudentId(student.getUuid());
				performance2.setTotalMean(totalMeans); 
				performance2.setTotalPoint(totalPoint);
				performance2.setExam1TotalPoints(totalExam1.getTotalPoints());
				performance2.setExam2TotalPoints(totalExam2.getTotalPoints());
				performance2.setExam3TotalPoints(totalExam3.getTotalPoints());
				performance2.setStreamId(student.getCurrentStream()); 

				performance2List.add(performance2);
			}


		}


		return performance2List;
	}

}
