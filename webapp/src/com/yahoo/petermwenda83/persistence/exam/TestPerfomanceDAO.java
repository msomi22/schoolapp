

/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package com.yahoo.petermwenda83.persistence.exam;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public class TestPerfomanceDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	
	final String FORM_ONE_N = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
	
	final String STUDENT_UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
	
	final String FORM_ONE = "C143978A-E021-4015-BC67-5A00D6C910D1";
	
	final Double SCORE = 20.0;
	
	final String TERM = "1";
	
	final String YEAR = "2016";
	
	private PerfomanceDAO store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO#getPerformance(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetPerformanceString() {
		store = new PerfomanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Perfomance> list = store.getPerformance(SCHOOL_UUID, FORM_ONE_N, STUDENT_UUID,TERM,YEAR);
		for(Perfomance p : list){
			System.out.println(p);
		}
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO#deletePerfomance(com.yahoo.petermwenda83.bean.exam.Perfomance)}.
	 */
	@Ignore
	@Test
	public void testDeletePerfomance() {
		store = new PerfomanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	
	@Ignore
	@Test
	public void testGetPerfomanceListDistinct() {
		store = new PerfomanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Perfomance> list = store.getPerfomanceListDistinct(SCHOOL_UUID,FORM_ONE_N,TERM,YEAR);
		for(Perfomance p : list){
			System.out.println(p.getStudentUuid());
		}
	
	}
	

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO#getPerfomanceList(java.lang.String)}.
	 */
	//@Ignore
	@Test  
	public void testGetPerfomanceListDistinctGeneral() {
		store = new PerfomanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		final String FORM1 = "C143978A-E021-4015-BC67-5A00D6C910D1";
		final String FORM2 = "3E22E428-3155-42F5-B73E-66553ED501C9";
		final String FORM3 = "A4BFC2BD-262F-4207-99C8-057D6ADF80C7";
		final String FORM4 = "14E56350-08DA-45CC-97D9-C225AF74A7AD";
		
		List<Perfomance> list = store.getPerfomanceListDistinctGeneral(SCHOOL_UUID,FORM1,TERM,YEAR);
		int count = 0;
		for(Perfomance p : list){
			count++;
		}
		System.out.println(count);
	
	}


}
