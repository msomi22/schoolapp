/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.money;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;

/**
 * @author peter
 *
 */
public class TestStudentBalance {
	
	static final String databaseName = "schooldb";
	static final String Host = "localhost";
	static final String databaseUsername = "school";
	static final String databasePassword = "AllaManO1";
	static final int databasePort = 5432;
	
	static private TermFeeDAO termFeeDAO;
	static private ExamConfigDAO examConfigDAO;
	static private StudentFeeDAO studentFeeDAO;
	static private StudentOtherMoniesDAO studentOtherMoniesDAO;
	
	//private final Date ADMISSION_DATE = new Date(new Long("1462609723000"));//2017-01-24 08:22:22.487+03  
	static private final Date ADMISSION_DATE = new Date(new Long("1462609723000"));//2017-01-24 08:22:22.487+03  
	static private final String REG_TERM = "1";
	static private final String STUDENT_UUID = "c67b2cdf-2429-4b2b-bada-fe9151c8e4b8";//June/916/Day
	static private final String SCHOOL_UUID = "696d205b-c251-4bb9-a037-c2935cb8c788";
	static private final int FINAL_YEAR = 2020;
	

	/**
	 * Test method for {@link com.yahoo.petermwenda83.server.servlet.money.StudentBalance#findBalance(com.yahoo.petermwenda83.persistence.money.TermFeeDAO, com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO, com.yahoo.petermwenda83.persistence.money.StudentFeeDAO, com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO, java.util.Date, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	//@Ignore
	//@Test
	public void testFindBalance() {
		termFeeDAO = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		examConfigDAO = new ExamConfigDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentFeeDAO = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentOtherMoniesDAO = new StudentOtherMoniesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		StudentBalance studentBalance = new StudentBalance();
		System.out.println("Balance: " + studentBalance.findBalance(termFeeDAO, examConfigDAO, studentFeeDAO,
				studentOtherMoniesDAO, ADMISSION_DATE, REG_TERM, STUDENT_UUID, SCHOOL_UUID, FINAL_YEAR));
		
		
	}
	
	public static void main(String[] a) throws ParseException {
		Date test = new SimpleDateFormat("dd/MM/yyyy").parse("24/01/2017"); 
		System.out.println("Test test " + test);
		termFeeDAO = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		examConfigDAO = new ExamConfigDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentFeeDAO = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentOtherMoniesDAO = new StudentOtherMoniesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		StudentBalance studentBalance = new StudentBalance();
		System.out.println("Balance: " + studentBalance.findBalance(termFeeDAO, examConfigDAO, studentFeeDAO,
				studentOtherMoniesDAO, test, REG_TERM, STUDENT_UUID, SCHOOL_UUID, FINAL_YEAR));
		
	}

}
