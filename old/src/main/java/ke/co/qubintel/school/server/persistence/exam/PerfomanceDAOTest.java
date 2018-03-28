/**
 * 
 *//*
package ke.co.qubintel.school.server.persistence.exam;

import static org.junit.Assert.*;

import org.junit.Test;

*//**
 * @author peter
 *
 *//*
public class PerfomanceDAOTest {
	
	private PerfomanceDAO perfomanceDAO = new PerfomanceDAO("schooldb", "localhost", "school", "AllaManO1", 5432);

	*//**
	 * Test method for {@link ke.co.qubintel.school.server.persistence.exam.PerfomanceDAO#getStudentSubCount(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 *//*
	@Test
	public void testGetStudentSubCount() {
		
		String studentId = "22ed214a-d43f-4fff-80eb-60879b71d6c6";
		String term = "1"; 
		String year = "2018";
		String examId = "D50E6399-B913-42F2-A5B6-F0D4BAAF9571"; 
		String streamId = "ec096c2d-b65f-49b4-aff3-b0c76054e62b";
		String accountId = "b83e9b89-0d52-4191-a6bf-acf501267e2e1";
		
		
		int count = perfomanceDAO.getStudentSubCount(accountId, examId, studentId, streamId, term, year);
		
		System.out.println("************ count : " + count); 
		
	}

}
*/