/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public class TestExamEgineDAO {
	

	private final String databaseName = "schooldb";
	private final String Host = "localhost";
	private final String databaseUsername = "school";
	private final String databasePassword = "AllaManO1";
	private final int databasePort = 5432;
	
	private ExamEgineDAO store = new ExamEgineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
	
	private final String ACCOUNT_ID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	private final String STUDENT_ID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F", STUDENT_ID_NEW="B3D6957B-0DAE-4E1B-A244-09C33F6FEF80";
	private final String SUBJECT_ID = "D0F7EC32-EA25-7D32-8708-2CC132446", SUBJECT_ID_NEW ="66027e51-b1ad-4b10-8250-63af64d23323";
	private final String EXAM_ID = "D50E6399-B913-42F2-A5B6-F0D4BAAF9571", EXAM_ID_NEW ="34C4244E-5CE0-4D5D-AD85-60E97FDDD80A";
	private final String TERM_ID = "1";
	private final String YEAR_ID = "2016";
	private final String STREAM_ID = "4DA86139-6A72-4089-8858-6A3A613FDFE6", STREAM_ID_NEW = "59E5F556-4B04-43B2-8139-E2D39A7836C6";
	private final String SCLASS_ID = "C143978A-E021-4015-BC67-5A00D6C910D1";

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.ExamEgineDAO#studentScoreExist(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testStudentScoreExist() {
		boolean exist = store.studentScoreExist(ACCOUNT_ID, STUDENT_ID, SUBJECT_ID, EXAM_ID, TERM_ID, YEAR_ID, STREAM_ID);
		System.out.println("exist: " + exist); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.ExamEgineDAO#putPerfomance(com.yahoo.petermwenda83.bean.exam.Perfomance, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	//@Ignore
	@Test
	public void testPutPerfomance() {
		Perfomance perfomance = new Perfomance();
		
		perfomance.setAccountId(ACCOUNT_ID);
		perfomance.setClassRoomId(SCLASS_ID);
		perfomance.setExamId(EXAM_ID);
		perfomance.setScore(22);
		perfomance.setStreamId(STREAM_ID);
		perfomance.setStudentId(STUDENT_ID);
		perfomance.setSubjectId(SUBJECT_ID);
		perfomance.setTerm(TERM_ID);
		perfomance.setYear(YEAR_ID); 
		
		//boolean put = store.putPerfomance(perfomance, ACCOUNT_ID, STUDENT_ID, SUBJECT_ID, EXAM_ID, TERM_ID, YEAR_ID, STREAM_ID);
		//System.out.println("put: " + put); 
		
		//NEW STUDENT
		perfomance.setAccountId(ACCOUNT_ID);
		perfomance.setClassRoomId(SCLASS_ID);
		perfomance.setExamId(EXAM_ID_NEW);
		perfomance.setScore(20);
		perfomance.setStreamId(STREAM_ID_NEW);
		perfomance.setStudentId(STUDENT_ID_NEW);
		perfomance.setSubjectId(SUBJECT_ID_NEW);
		perfomance.setTerm(TERM_ID);
		perfomance.setYear(YEAR_ID); 
		
		//boolean putnew = store.putPerfomance(perfomance, ACCOUNT_ID, STUDENT_ID_NEW, SUBJECT_ID_NEW, EXAM_ID_NEW, TERM_ID, YEAR_ID, STREAM_ID_NEW);
		//System.out.println("putnew: " + putnew); 
		
		//UPDATE STUDENT
		perfomance.setAccountId(ACCOUNT_ID);
		perfomance.setClassRoomId(SCLASS_ID);
		perfomance.setExamId(EXAM_ID_NEW);
		perfomance.setScore(30);
		perfomance.setStreamId(STREAM_ID_NEW);
		perfomance.setStudentId(STUDENT_ID_NEW);
		perfomance.setSubjectId(SUBJECT_ID_NEW);
		perfomance.setTerm(TERM_ID);
		perfomance.setYear(YEAR_ID); 
		
		boolean update  = store.putPerfomance(perfomance, ACCOUNT_ID, STUDENT_ID_NEW, SUBJECT_ID_NEW, EXAM_ID_NEW, TERM_ID, YEAR_ID, STREAM_ID_NEW);
		System.out.println("update: " + update); 
	}

}
