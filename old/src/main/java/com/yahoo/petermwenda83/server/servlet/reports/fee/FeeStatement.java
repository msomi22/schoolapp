/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.fee;

import java.util.Calendar;
import java.util.List;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.FeeBreakdown;
import com.yahoo.petermwenda83.bean.money.FeeBreakdownDesc;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.otherfee.OtherFee;
import com.yahoo.petermwenda83.bean.otherfee.RevertedMoney;
import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;
import com.yahoo.petermwenda83.bean.student.Student;
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
import com.yahoo.petermwenda83.server.servlet.finance.FeeConstants;

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

		if(accountDAO.getAccountById(accountId) != null) {

			Account school = accountDAO.getAccountById(accountId);
			String currentTerm = "";
			String currentYear = "";

			if(sysConfigDAO.getSysConfig(school.getUuid()) != null) {
				SysConfig sysConfig = sysConfigDAO.getSysConfig(school.getUuid()); 
				currentTerm = sysConfig.getTerm();
				currentYear = sysConfig.getYear();
			}

			System.out.println("**************************************************************"); 
			System.out.println("currentTerm: " + currentTerm + ", currentYear: " + currentYear); 

			if(studentDAO.getStudentById(school.getUuid(), studentId) != null) {

				Student student = studentDAO.getStudentById(school.getUuid(), studentId); 

				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(student.getAdmissionDate().getTime()); 
				String year = String.valueOf(cal.get(Calendar.YEAR));
				String term = student.getRegTerm();
				
				if(termFeeDAO.getFee(school.getUuid(), term, year) != null) {

					analyzeFeeByTerm(school, student, term, year);


				}


			}



		}

	}

	/**
	 * @param school
	 * @param student
	 * @param admissionTerm
	 * @param term
	 * @param year
	 * @param yearLong
	 */
	private static void analyzeFeeByTerm(Account school, Student student, String term, String year) {
		
		long yearLong = Integer.valueOf(year); 
		
		TermFee termFee = termFeeDAO.getFee(school.getUuid(), term, year);

		System.out.println("**************************************************************"); 
		System.out.println("term: " + term + ", year: " + year); 
		System.out.println("**************************************************************"); 
		System.out.println("Term Fee"); 
		System.out.println("B: " + termFee.getBoaderAmount() + " , D: " + termFee.getDayAmount());  
		
		if(studentFeeDAO.getStudentFeeList(school.getUuid(), student.getUuid(), term, year) != null) {
			List<StudentFee> studentFeeList = studentFeeDAO.getStudentFeeList(school.getUuid(), student.getUuid(), term, year);
			System.out.println("Student Fee List"); 
			System.out.println(studentFeeList.size());  

		}

		if(otherFeeDAO.getOtherFeeList(school.getUuid(), term, year) != null) {
			List<OtherFee> otherFeeList = otherFeeDAO.getOtherFeeList(school.getUuid(), term, year);
			System.out.println("Other Fee List"); 
			System.out.println(otherFeeList.size());  
		}
		
		
		if(studentOtherFeeDAO.getStudentOFeeList(school.getUuid(), student.getUuid(), term, yearLong) != null) {
			List<StudentOtherFee> studentOtherFeeList = studentOtherFeeDAO.getStudentOFeeList(school.getUuid(), student.getUuid(), term, yearLong);
			System.out.println("Student Other Fee List"); 
			System.out.println(studentOtherFeeList.size());  
		}


		if(revertedMoneyDAO.getRevertedMoneyList(school.getUuid(), student.getUuid()) != null) {
			List<RevertedMoney> revertedOtherFeeList = revertedMoneyDAO.getRevertedMoneyList(school.getUuid(), student.getUuid());
			System.out.println("Reverted Other Fee List"); 
			System.out.println(revertedOtherFeeList.size()); 
		}
		
		if(feeBreakdownDAO.getFeeBreakdown(school.getUuid(), FeeConstants.GVMT_MONEY_CODE, term, year) != null) {
			FeeBreakdown feeBreakdown = feeBreakdownDAO.getFeeBreakdown(school.getUuid(), FeeConstants.GVMT_MONEY_CODE, term, year);
			List<FeeBreakdownDesc> feeBreakdownDescList = feeBreakdownDescDAO.getFeeBreakdownDescList(school.getUuid(), feeBreakdown.getUuid());
			System.out.println("GoKe Fee Breakdown Description List"); 
			System.out.println(feeBreakdownDescList.size());  
			System.out.println("**************************************************************"); 
		}
	}

}
