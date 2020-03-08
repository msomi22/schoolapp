/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.servlet.reports.fee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.money.StudentFee;
import ke.co.qubintel.school.server.bean.money.TermFee;
import ke.co.qubintel.school.server.bean.otherfee.OtherFee;
import ke.co.qubintel.school.server.bean.otherfee.RevertedMoney;
import ke.co.qubintel.school.server.bean.otherfee.StudentOtherFee;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.money.StudentFeeDAO;
import ke.co.qubintel.school.server.persistence.money.TermFeeDAO;
import ke.co.qubintel.school.server.persistence.othermoney.OtherFeeDAO;
import ke.co.qubintel.school.server.persistence.othermoney.RevertedMoneyDAO;
import ke.co.qubintel.school.server.persistence.othermoney.StudentOtherFeeDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;

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
			System.out.println("**************************************************************"); 


			if(studentDAO.getStudentById(school.getUuid(), studentId) != null) {

				Student student = studentDAO.getStudentById(school.getUuid(), studentId); 
				////boarders = 1, day = 0
				String status = StringUtils.equals(student.getIsBoarding(), "1") ? "Boarder" : "Day";  
				System.out.println("Student status:" + status);  

				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(student.getAdmissionDate().getTime()); 
				String year = String.valueOf(cal.get(Calendar.YEAR));

				while(Integer.valueOf(year) <= Integer.valueOf(currentYear)) {

					for(int termi =1; termi <=3; termi++) {

						//do the computations here
						if(termFeeDAO.getFee(school.getUuid(), String.valueOf(termi), year) != null) {
							StatementObject statementObject = analyzeFeeByTerm(school, student, String.valueOf(termi), year);
							List<StatementFee> statementFeeList = statementObject.getStatementFeeList();
							List<StatementOtherFee> statementOtherFeeList = statementObject.getStatementOtherFeeList();
							System.out.println(statementFeeList);   
							System.out.println(statementOtherFeeList);   

						}
						//end the computation now

						boolean maxTerm = StringUtils.equals(currentTerm, String.valueOf(termi));
						boolean maxYear = StringUtils.equals(year, currentYear);

						if(maxTerm && maxYear) { 
							break;
						}
					}

					//year increment 
					year = String.valueOf(Integer.valueOf(year) + 1); 
					if(Integer.valueOf(year) == Integer.valueOf(currentYear)) {
						year = currentYear; 
					}



				}

				if(revertedMoneyDAO.getRevertedMoneyList(school.getUuid(), student.getUuid()) != null) {
					List<RevertedMoney> revertedOtherFeeList = revertedMoneyDAO.getRevertedMoneyList(school.getUuid(), student.getUuid());
					System.out.println("**************************************************************"); 
					System.out.println("Reverted Other Fee List"); 
					System.out.println(revertedOtherFeeList.size()); 
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
	private static StatementObject analyzeFeeByTerm(Account school, Student student, String term, String year) {

		long yearLong = Integer.valueOf(year); 

		StatementObject statementObject = new StatementObject();
		List<StatementOtherFee> statementOtherFeeList = new ArrayList<>();
		List<StatementFee> statementFeeList =  new ArrayList<>();

		TermFee termFee = termFeeDAO.getFee(school.getUuid(), term, year);

		if(studentFeeDAO.getStudentFeeList(school.getUuid(), student.getUuid(), term, year) != null) {
			List<StudentFee> studentFeeList = studentFeeDAO.getStudentFeeList(school.getUuid(), student.getUuid(), term, year);
			studentFeeList.forEach(studentFee -> {
				StatementFee statementFee = new StatementFee();
				statementFee.setBoarderAmount(termFee.getBoaderAmount());
				statementFee.setDayAmount(termFee.getDayAmount()); 
				statementFee.setAmountPaid(studentFee.getAmountPaid());
				statementFee.setPayMode(studentFee.getPayMode());
				statementFee.setTransactionId(studentFee.getTransactionId());
				statementFee.setPaidHas(studentFee.getPaidHas());
				statementFee.setDatePaid(studentFee.getDatePaid());
				statementFeeList.add(statementFee); 

			});

		}


		if(studentOtherFeeDAO.getStudentOFeeList(school.getUuid(), student.getUuid(), term, yearLong) != null) {
			List<StudentOtherFee> studentOtherFeeList = studentOtherFeeDAO.getStudentOFeeList(school.getUuid(), student.getUuid(), term, yearLong);
			studentOtherFeeList.forEach(studentOtherFee -> {
				OtherFee otherfee = otherFeeDAO.getOtherFee(school.getUuid(), studentOtherFee.getOtherFeeId());
				StatementOtherFee statementOtherFee = new StatementOtherFee();
				statementOtherFee.setOtherAmount(otherfee.getAmount());
				statementOtherFee.setOtherAmountTerm(otherfee.getTerm());
				statementOtherFee.setOtherAmountYear(otherfee.getYear());
				statementOtherFee.setOtherAmountDescription(otherfee.getDescription());
				statementOtherFee.setOtherAmountDateAllocated(studentOtherFee.getDateAllocated());
				statementOtherFeeList.add(statementOtherFee);

			});
		}

		statementObject.setStatementFeeList(statementFeeList);
		statementObject.setStatementOtherFeeList(statementOtherFeeList);  

		return statementObject; 
	}


}
