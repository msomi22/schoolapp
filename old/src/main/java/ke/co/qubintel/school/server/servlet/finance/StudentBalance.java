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
package ke.co.qubintel.school.server.servlet.finance;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.money.StudentFee;
import ke.co.qubintel.school.server.bean.money.TermFee;
import ke.co.qubintel.school.server.bean.otherfee.StudentOtherFee;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.money.StudentFeeDAO;
import ke.co.qubintel.school.server.persistence.money.TermFeeDAO;
import ke.co.qubintel.school.server.persistence.othermoney.OtherFeeDAO;
import ke.co.qubintel.school.server.persistence.othermoney.StudentOtherFeeDAO;
import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.servlet.reports.ReportUtil;

/**
 * @author peter
 *
 */
public class StudentBalance {

	private static SysConfigDAO sysConfigDAO;
	private static TermFeeDAO termFeeDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static StudentOtherFeeDAO studentOtherMoniesDAO;
	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static OtherFeeDAO otherFeeDAO;

	/*final static String databaseName = "schooldb";
	final static String Host = "localhost";
	final static String databaseUsername = "school";
	final static String databasePassword = "AllaManO1";
	final static int databasePort = 5432;*/

	static{

		sysConfigDAO = SysConfigDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		studentOtherMoniesDAO = StudentOtherFeeDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		otherFeeDAO = OtherFeeDAO.getInstance();

		/*sysConfigDAO = new SysConfigDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		termFeeDAO = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentFeeDAO = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentOtherMoniesDAO = new StudentOtherFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentDAO = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		accountDAO = new AccountDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		otherFeeDAO = new OtherFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
*/
	}



	private List<StudentFee> studentFeeList;
	private List<StudentOtherFee> otherFeeList;
	private String [] terms;


	public StudentBalance() {
		studentFeeList = new ArrayList<>();
		otherFeeList = new ArrayList<>();
		terms = new String [] {"1","2","3"};

	}

	private String accountId;
	private String studentId;

	public StudentBalance(String accountId, String studentId) {
		this.accountId = accountId;
		this.studentId = studentId;
	}

	public double build() {
		return findBalance(accountId,studentId);
	}

	/**
	 * @param accountId
	 * @param studentId
	 * @return
	 */
	public double findBalance(String accountId, String studentId) {

		SimpleDateFormat yearformatter = ReportUtil.yearformatter;

		String currentYear;
		String currentTerm;
		String paidHas = "";

		Student student = studentDAO.getStudentById(accountId, studentId);
		int finalYear = Integer.valueOf(student.getFinalYear()); 

		double balance = 0;
		double amountPaid = 0;
		double otherPaid = 0;

		String admYear = yearformatter.format(student.getAdmissionDate());  
		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

		currentYear = sysConfig.getYear();
		currentTerm = sysConfig.getTerm();

		int admYr = Integer.parseInt(admYear);
		int admTm = Integer.parseInt(student.getRegTerm());
		int crrntYr = Integer.parseInt(currentYear);

		if(admTm == 2){   
			terms = new String [] {"2","3"}; 
		}else if(admTm == 3){  
			terms = new String [] {"3"};
		}else  if(admTm == 1){
			terms = new String [] {"1","2","3"}; 
		}


		while(admYr <= crrntYr && crrntYr <= finalYear){ 
			//start from admission year
			String year = Integer.toString(admYr);
			long yearLong = (long)admYr;
			String term = "";

			for(int i=0;i<terms.length;i++){
				term = terms[i];

				if(studentFeeDAO.getStudentFeeList(accountId, studentId, term, year) != null) {
					studentFeeList = studentFeeDAO.getStudentFeeList(accountId, studentId, term, year);
				}



				if(studentOtherMoniesDAO.getStudentOFeeList(accountId, studentId, term, yearLong) != null) {
					otherFeeList = studentOtherMoniesDAO.getStudentOFeeList(accountId, studentId, term, yearLong);
				}


				TermFee admTermFee = new TermFee();
				if(termFeeDAO.getFee(accountId,term, year) != null) {
					admTermFee = termFeeDAO.getFee(accountId,term, year);
				}


				if(!studentFeeList.isEmpty()) {
					for(StudentFee studentFee :studentFeeList){
						amountPaid +=studentFee.getAmountPaid();//amount paid per term
						paidHas = studentFee.getPaidHas();//last payment type , boarders = 1, day = 0
					}
				}

				if(!otherFeeList.isEmpty()) {
					for(StudentOtherFee otherFee : otherFeeList){
						double amount = 0;
						if(otherFeeDAO.getOtherFee(accountId, otherFee.getOtherFeeId()) != null) { 
							amount = otherFeeDAO.getOtherFee(accountId, otherFee.getOtherFeeId()).getAmount();
							otherPaid += amount; 
							amount = 0;
						}
					}
				}


				// boarders = 1, day = 0
				double bal = 0;
				if(StringUtils.equals(paidHas, "1")){

					bal = (admTermFee.getBoaderAmount() + otherPaid) - amountPaid;

				}else if(StringUtils.equals(paidHas, "0")) {

					bal = (admTermFee.getDayAmount() + otherPaid) - amountPaid;

				}

				balance += bal;

				amountPaid = 0;
				otherPaid = 0;
				bal = 0;


				// clear our list at the end to ensure a clean start
				studentFeeList.clear();
				otherFeeList.clear();

				if(admYr == crrntYr && crrntYr <= finalYear){
					if(StringUtils.equals(term, currentTerm)){
						break;
					}
				}

			}

			terms = new String [] {"1","2","3"}; 
			admYr +=1;

		}


		return balance;
	}












	/**
	 * @param accountId
	 * @return
	 */
	public String findNextTermFee(String accountId) {

		SysConfig config = sysConfigDAO.getSysConfig(accountId);

		String nextTerm = "";
		String year = config.getYear();

		if(StringUtils.equals(config.getTerm(), "1")){

			//next term = 2
			nextTerm = "2";

		}else if(StringUtils.equals(config.getTerm(), "2")){

			//next term = 3
			nextTerm = "3";

		}else if(StringUtils.equals(config.getTerm(), "3")){

			//next term = 1
			//increment year
			nextTerm = "1";
			int yearInt = Integer.valueOf(config.getYear()); 
			yearInt +=1;

			year = yearInt+""; 

		}

		Locale locale = new Locale("en","KE"); 
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

		TermFee termFee = new TermFee();

		if(termFeeDAO.getFee(accountId, nextTerm, year) != null){

			termFee = termFeeDAO.getFee(accountId, nextTerm, year);

		}else{


		}



		String boardingFee = nf.format(termFee.getBoaderAmount());
		String dayFee = nf.format(termFee.getDayAmount()); 
		String nextTermFee = "";

		Account account = accountDAO.getAccountById(accountId);
		account.getIsBoarding();//1 = boarding only, 0 = day only, 2 = day and boarding 

		if(StringUtils.equals(account.getIsBoarding(), "0")){

			nextTermFee = dayFee; 

		}else if(StringUtils.equals(account.getIsBoarding(), "1")){

			nextTermFee = boardingFee;

		}
		else if(StringUtils.equals(account.getIsBoarding(), "2")){

			nextTermFee = "Boarding: " + boardingFee + " , Day: " + dayFee;

		}


		return nextTermFee;
	}

}
