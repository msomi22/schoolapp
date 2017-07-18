/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.exam.Exam;

/**
 * @author peter
 *
 */
public class TestExamDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String UUID = "AE24F15B-5038-4A15-8607-1DB2A7A0B7DE";
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	final String EXAM_NAME = "P1";
	final int OUT_OF = 60;
	
	
	private ExamDAO store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.ExamDAO#getExam(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetExam() {
		store = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Exam exam = new Exam();
		exam = store.getExam(UUID);
		assertEquals(exam.getUuid(),UUID);
		assertEquals(exam.getSchoolAccountUuid(),SCHOOL_UUID);
		assertEquals(exam.getExamName(),EXAM_NAME);
		assertEquals(exam.getOutOf(),OUT_OF);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.ExamDAO#getExamByName(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetExamByName() {
		store = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Exam exam = new Exam();
		exam = store.getExamByName(EXAM_NAME);
		assertEquals(exam.getUuid(),UUID);
		assertEquals(exam.getSchoolAccountUuid(),SCHOOL_UUID);
		assertEquals(exam.getExamName(),EXAM_NAME);
		assertEquals(exam.getOutOf(),OUT_OF);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.ExamDAO#updateExam(com.yahoo.petermwenda83.bean.exam.Exam)}.
	 */
	@Ignore
	@Test
	public final void testUpdateExam() {
		store = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Exam exam = new Exam();
		exam.setOutOf(56);
		exam.setUuid(UUID);
		exam.setSchoolAccountUuid(SCHOOL_UUID);
		assertTrue(store.updateExam(exam));    
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.ExamDAO#putExam(com.yahoo.petermwenda83.bean.exam.Exam)}.
	 */
	@Ignore
	@Test
	public final void testPutExam() {
		store = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Exam exam = new Exam();
		exam.setUuid(UUID); 
		exam.setExamName(EXAM_NAME);
		exam.setOutOf(OUT_OF);
		exam.setSchoolAccountUuid(SCHOOL_UUID);
		assertTrue(store.putExam(exam));    
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.ExamDAO#getExamList(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetExamList() {
		store = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Exam> list = store.getExamList(SCHOOL_UUID);
		for (Exam cl : list) {
			System.out.println(cl); 
		}
	}

}
