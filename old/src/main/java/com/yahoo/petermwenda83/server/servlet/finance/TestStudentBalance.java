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
	
		String studentId = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
		String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	
		System.out.println("*** " + balance.findBalance(accountId, studentId)); 
		
		
	}

}
