/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.finance;

//import org.junit.Ignore;
import org.junit.Test;

/**
 * @author peter
 *
 */
public class TestStudentBalance {
	
	

	/**
	 * Test method for {@link com.yahoo.petermwenda83.server.servlet.finance.StudentBalance#findBalance(java.util.Date, java.lang.String, int, java.lang.String, java.lang.String)}.
	 */
	//@Ignore
	@Test
	public void testFindBalance() {
		
		StudentBalance balance = new StudentBalance();
	
		String studentId = "2b059765-ae90-4d38-8ebc-152831dd0226";
		String accountId = "b83e9b89-0d52-4191-a6bf-acf501267e2e1";
	
		System.out.println("*** " + balance.findBalance(accountId, studentId)); 
		
		
		
		
	}

}
