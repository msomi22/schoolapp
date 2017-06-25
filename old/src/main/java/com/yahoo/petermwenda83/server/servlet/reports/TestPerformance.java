/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.servlet.util.Timeit;

/**
 * @author peter
 *
 */
public class TestPerformance {

	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;

	final static String databaseName = "schooldb";
	final static String Host = "localhost";
	final static String databaseUsername = "school";
	final static String databasePassword = "AllaManO1";
	final static int databasePort = 5432;

	static{
		//perfomanceDAO = PerfomanceDAO.getInstance();
		perfomanceDAO = new PerfomanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		subjectDAO = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Timeit.code(() -> compute() ); 

	}



	public static void compute(){

		String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		String studentId = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
		String streamId = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
		String term = "1";
		String year = "2016";
		List<Perfomance> perfomanceList = null;

		String[] exams = {"D50E6399-B913-42F2-A5B6-F0D4BAAF9571","34C4244E-5CE0-4D5D-AD85-60E97FDDD80A","16C4BF00-941C-40E4-9891-272D5F0979A1"};

		int examNo = exams.length;



		if(examNo == 1){

			perfomanceList = perfomanceDAO.getStreamPerformance(accountId, exams[0] ,studentId, streamId, term, year);

		}
		if(examNo == 2){

			perfomanceList = perfomanceDAO.getStreamPerformance(accountId, exams[0] ,studentId, streamId, term, year);
			perfomanceList = perfomanceDAO.getStreamPerformance(accountId, exams[1] ,studentId, streamId, term, year);

		}
		if(examNo == 3){

			perfomanceList = perfomanceDAO.getStreamPerformance(accountId, exams[0] ,studentId, streamId, term, year);
			
			if(perfomanceList.size() >= 1){
				int total1 = 0;
				for(Perfomance perfomance : perfomanceList){
					
					perfomance.getExamId();
					perfomance.getStudentId();
					perfomance.getSubjectId();
					
					/****************************************************/
					/**  Algorithm  
					 * language: ENG, KISW, GER , FRE 
					 * sciences: CHEM, BIO , PHY , P-MATH, A-MATH
					 * humanities: CRE, GEO, HIST
					 * technical: B/S, COMP, H/S , AGR
					 */
					 
					/** 7 subjects  */
					//language  (2 best) = 2
					
					//sciences + math (2 best + math) = 3
					
					//humanities (1 best) = 1
					
					//technical (1 best) = 1 , can be replaced by a science 
					
					/** 11 subjects **/
					//languages (2 best) = 2
					
					//sciences + math ( 3 + 1) = 4
					
					//humanities ( 2 best) = 2
					
					//technical (3 best) = 3
					
					/****************************************************/
					
					total1 += perfomance.getScore();
					
					System.out.println(perfomance.getScore() + " ** " + total1 + " ** " + perfomance.getSubjectId()); 
					
				}
			}

			perfomanceList = perfomanceDAO.getStreamPerformance(accountId, exams[1] ,studentId, streamId, term, year);
			
			if(perfomanceList.size() >= 1){
				int total2 = 0;
				for(Perfomance perfomance : perfomanceList){
					total2 += perfomance.getScore();
					perfomance.getExamId();
					perfomance.getStudentId();
					perfomance.getSubjectId();
				}
			}

			perfomanceList = perfomanceDAO.getStreamPerformance(accountId, exams[2] ,studentId, streamId, term, year);
			
			if(perfomanceList.size() >= 1){
				int total3 = 0;
				for(Perfomance perfomance : perfomanceList){
					total3 += perfomance.getScore();
					perfomance.getExamId();
					perfomance.getStudentId();
					perfomance.getSubjectId();
				}
			}

		}

		


	}

}
