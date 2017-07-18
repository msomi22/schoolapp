/**
 * 
 */
package com.yahoo.petermwenda83.persistence.othermoney;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies;

/**
 * @author peter
 *
 */
public class TestStudentOtherMoniesDAO {
	

	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private StudentOtherMoniesDAO store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO#StudentOtherMoniesDAO(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Ignore
	@Test
	public final void testStudentOtherMoniesDAOStringStringStringStringInt() {
		store = new StudentOtherMoniesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO#getStudentOtherMonies(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentOtherMoniesStringString() {
		store = new StudentOtherMoniesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO#getStudentOtherList(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentOtherList() {
		store = new StudentOtherMoniesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO#putStudentOtherMonies(com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies)}.
	 */
	@Ignore
	@Test
	public final void testPutStudentOtherMonies() {
		store = new StudentOtherMoniesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO#updateStudentOtherMonies(com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies)}.
	 */
	@Ignore
	@Test
	public final void testUpdateStudentOtherMonies() {
		store = new StudentOtherMoniesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO#deleteStudentOtherMonies(com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies)}.
	 */
	@Ignore
	@Test
	public final void testDeleteStudentOtherMonies() {
		store = new StudentOtherMoniesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO#getStudentOtherMoniesList()}.
	 */
	//@Ignore
	@Test
	public final void testGetStudentOtherMoniesList() {
		store = new StudentOtherMoniesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		/*List<StudentOtherMonies> list = store.getStudentOtherMonies("e4d9a770-9fda-4f4e-b7b4-19de9dfa6d04","480f89b4-0152-43dd-b278-f6fcd91112dc");
		double total = 0;
		for (StudentOtherMonies l : list) {
			double amount = l.getAmountPiad();
			total+=amount;
		}
		System.out.println(total);	*/
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO#getStudentOtherMoniesDistinct(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	//@Ignore
	@Test
	public final void testGetStudentOtherMoniesDistinct() {
		store = new StudentOtherMoniesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentOtherMonies> list = store.getStudentOtherList("6092f611-2b44-4073-a803-2e3a1374bb8c", "1", "2016");
		for (StudentOtherMonies l : list) {
			System.out.println("list="+l);	
		}
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO#getStudentOtherMonies(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentOtherMoniesStringStringStringString() {
		store = new StudentOtherMoniesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

}
