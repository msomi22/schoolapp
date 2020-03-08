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

import ke.co.qubintel.school.server.persistence.exam.PerfomanceDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;

/**
 * @author peter
 *
 */
public class TestPerformance {
	
	private static final String databaseName = "schooldb";
	private static final String Host = "localhost";
	private static final String databaseUsername = "school";
	private static final String databasePassword = "AllaManO1";
	private static final int databasePort = 5432;
	
	
	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;
	
	static {
		perfomanceDAO = new PerfomanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
		subjectDAO = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String accountId = "b83e9b89-0d52-4191-a6bf-acf501267e2e1";
		String examId = "";
		String studentId = "2b387a97-f910-499f-b851-4f26e4fb5831";
		String streamId = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
		String term = "1";
		String year = "2017";
		String subjectId = "D0F7EC32-EA25-7D32-8708-2CC132446";
		String[] exams = {"D50E6399-B913-42F2-A5B6-F0D4BAAF9571","34C4244E-5CE0-4D5D-AD85-60E97FDDD80A"};
		
		for(int x=0;x<exams.length;x++) {
			
		perfomanceDAO.getStreamPerformance(accountId, exams[x], studentId, streamId, term, year).forEach(performance -> {
			
		//	System.out.println("studentId : " + performance.getStudentId() + ""); 
		//	System.out.println("ExamId : " + performance.getExamId() + ""); 
			
			String sub = subjectDAO.getSubjectById(accountId, performance.getSubjectId()).getCode(); 
			
			
			System.out.println(sub + " ---- score " + performance.getScore() + " , point : " + getPoint(performance.getScore())); 
			
		});
			
			
			System.out.println("**************************************************"); 
			
		}
		

	}
	
	public static int getPoint(int score) {
		int point = 0;
		if(score >= 83) {
			point = 12;
		}else if(score >= 73) {
			point = 11;
		}else if(score >= 67) {
			point = 10;
		}else if(score >= 58) {
			point = 9;
		}else if(score >= 53) {
			point = 8;
		}else if(score >= 46) {
			point = 7;
		}else if(score >= 42) {
			point = 6;
		}else if(score >= 38) {
			point = 5;
		}else if(score >= 32) {
			point = 4;
		}else if(score >= 28) {
			point = 3;
		}else if(score >= 25) {
			point = 2;
		}else if(score <= 24) {
			point = 1;
		}
		return point;
	}
	
	

}
