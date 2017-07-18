/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public class TestPerfomanceDAO {
	
	private final String databaseName = "schooldb";
	private final String Host = "localhost";
	private final String databaseUsername = "school";
	private final String databasePassword = "AllaManO1";
	private final int databasePort = 5432;
	
	private final String ACCOUNT_ID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	private final String EXAM_ID = "D50E6399-B913-42F2-A5B6-F0D4BAAF9571";
	private final String STUDENT_ID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
	private final String STREAM_ID = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
	private final String SUBJECT_ID = "D0F7EC32-EA25-7D32-8708-2CC132446";
	
	private final String TERM_ID = "1";
	private final String YEAR_ID = "2016";
	
	
	PerfomanceDAO store = new PerfomanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO#PerfomanceDAO(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	//@Ignore
	@Test
	public void testPerfomanceDAOStringStringStringStringInt() {
		Perfomance perfomance = store.getPerformance(ACCOUNT_ID, EXAM_ID, STUDENT_ID, STREAM_ID, TERM_ID, YEAR_ID, SUBJECT_ID);
		
		System.out.println("perfomance: " + perfomance.getScore()); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO#getStreamPerformance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetStreamPerformance() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO#getClassPerformance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetClassPerformance() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO#deletePerfomance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testDeletePerfomance() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO#getStreamSubjectPerfomance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetStreamSubjectPerfomance() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO#getClassSubjectPerfomance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetClassSubjectPerfomance() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO#getPerformance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetPerformance() {
		fail("Not yet implemented");
	}

}
