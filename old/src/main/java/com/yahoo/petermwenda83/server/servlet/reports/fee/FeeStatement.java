/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.fee;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDescDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.RevertedMoneyDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;

/**
 * @author peter
 *
 */
public class FeeStatement {
	
	
	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static OtherFeeDAO otherFeeDAO;
	private static StudentOtherFeeDAO studentOtherFeeDAO;
	private static RevertedMoneyDAO revertedMoneyDAO;
	private static FeeBreakdownDAO feeBreakdownDAO;
	private static FeeBreakdownDescDAO feeBreakdownDescDAO;
	private static TermFeeDAO termFeeDAO;
	private static SysConfigDAO sysConfigDAO;
	
	
	private static final String databaseName = "schooldb";
	private static final String Host = "localhost";
	private static final String databaseUsername = "school";
	private static final String databasePassword = "AllaManO1";
	private static final int databasePort = 5432;
	
	
	static {
		
		accountDAO = new AccountDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		sysConfigDAO = new SysConfigDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		studentDAO = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		otherFeeDAO = new OtherFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		termFeeDAO = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		studentFeeDAO = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentOtherFeeDAO = new StudentOtherFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		revertedMoneyDAO = new RevertedMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		feeBreakdownDAO = new FeeBreakdownDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		feeBreakdownDescDAO = new FeeBreakdownDescDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		
		
		
		
		/*studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		otherFeeDAO = OtherFeeDAO.getInstance();
		studentOtherFeeDAO = StudentOtherFeeDAO.getInstance();
		///revertedMoneyDAO = RevertedMoneyDAO.getInstance();
		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
		feeBreakdownDescDAO = FeeBreakdownDescDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();*/
	}

	/**
	 * 
	 */
	public FeeStatement() {
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		String studentId = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
		
		generateStatement(accountId,studentId);

	}
	
	
	
	
	public static void generateStatement(String accountId, String studentId) {
		
		Account school = accountDAO.getAccountById(accountId);
		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId); 
		String term = sysConfig.getTerm();
		String year = sysConfig.getYear();
		//long yearLong = (long) year;
		
		studentDAO.getStudentById(accountId, studentId); 
		
		termFeeDAO.getFee(accountId, term, year);
		otherFeeDAO.getOtherFeeList(accountId, term, year);
		
		studentFeeDAO.getStudentFeeList(accountId, studentId, term, year);
		//studentOtherFeeDAO.getStudentOFeeList(accountId, studentId, term, yearLong);
		
		revertedMoneyDAO.getRevertedMoneyList(accountId, studentId);
		
		//feeBreakdownDAO.getFeeBreakdown(accountId, feeCategory, term, year);
		//feeBreakdownDescDAO.getFeeBreakdownDescList(accountId, feeBreakdownId);
		
		
		
		
		
		System.out.println(" Fee statement "); 
		
		
	}

}
