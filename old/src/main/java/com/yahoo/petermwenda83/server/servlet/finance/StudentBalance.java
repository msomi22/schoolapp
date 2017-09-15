/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.finance;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.reports.ReportUtil;

/**
 * @author peter
 *
 */
public class StudentBalance {

	/*private static final String databaseName = "schooldb";
	private static final String Host = "localhost";
	private static final String databaseUsername = "school";
	private static final String databasePassword = "AllaManO1";
	private static final int databasePort = 5432;*/

	private static SysConfigDAO sysConfigDAO;
	private static TermFeeDAO termFeeDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static StudentOtherFeeDAO studentOtherMoniesDAO;
	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static OtherFeeDAO otherFeeDAO;

	static{
		sysConfigDAO = SysConfigDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		studentOtherMoniesDAO = StudentOtherFeeDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		otherFeeDAO = OtherFeeDAO.getInstance();

		/*
		sysConfigDAO = new SysConfigDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		termFeeDAO = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentFeeDAO = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentOtherMoniesDAO = new StudentOtherFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentDAO = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);*/
	}



	private List<StudentFee> studentFeeList;
	private List<StudentOtherFee> otherFeeList;
	private String [] terms;


	public StudentBalance() {
		studentFeeList = new ArrayList<>();
		otherFeeList = new ArrayList<>();
		terms = new String [] {"1","2","3"};

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
		//System.out.println("finalYear:" + finalYear);

		double balance = 0;
		double amountPaid = 0;
		double otherPaid = 0;

		String admYear = yearformatter.format(student.getAdmissionDate());  

		//System.out.println("admYear:" + admYear);

		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

		currentYear = sysConfig.getYear();
		currentTerm = sysConfig.getTerm();

		//System.out.println("currentYear:" + currentYear);
		//System.out.println("currentTerm:" + currentTerm);

		int admYr = Integer.parseInt(admYear);
		int admTm = Integer.parseInt(student.getRegTerm());
		int crrntYr = Integer.parseInt(currentYear);

		//System.out.println("admYr:" + admYr);
		//System.out.println("admTm:" + admTm);
		//System.out.println("crrntYr:" + crrntYr);

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
			String term = "";

			for(int i=0;i<terms.length;i++){
				term = terms[i];

				//start finding the balance here
				studentFeeList = studentFeeDAO.getStudentFeeList(accountId, studentId, term, year);
				otherFeeList = studentOtherMoniesDAO.getStudentOtherFeeList(accountId, studentId, term, year);

				//System.out.println("studentFeeList:" + studentFeeList.size());
				//System.out.println("otherFeeList:" + otherFeeList.size());


				TermFee admTermFee = new TermFee();
				admTermFee = termFeeDAO.getFee(accountId,term, year);

				//System.out.println("TermFee:" + admTermFee.getBoaderAmount() + " ** " + admTermFee.getDayAmount());

				amountPaid = 0;
				otherPaid = 0;

				for(StudentFee studentFee :studentFeeList){
					amountPaid +=studentFee.getAmountPaid();//amount paid per term
					paidHas = studentFee.getPaidHas();//last payment type , boarders = 1, day = 0
					//System.out.println("amountPaid: " + studentFee.getAmountPaid()+ ", total: " + amountPaid + ", term: " +term+ ", year: " + year + ", paidHas: " + paidHas);

				}

				for(StudentOtherFee otherFee : otherFeeList){
					
					if(otherFeeDAO.getOtherFee(accountId, otherFee.getOtherFeeId()) != null) { 
						otherPaid += otherFeeDAO.getOtherFee(accountId, otherFee.getOtherFeeId()).getAmount();
					}
					
					//System.out.println("otherPaid: " + otherFee.getAmountPiad() + " , total:" + otherPaid);
				}

				if(StringUtils.equals(paidHas, "1")){

					balance += (admTermFee.getBoaderAmount() + otherPaid) - amountPaid;
					//System.out.println("balance 1: " + balance + " += " +admTermFee.getBoaderAmount() + " + " + otherPaid + " - " + amountPaid);

				}else if(StringUtils.equals(paidHas, "0")) {

					balance += (admTermFee.getDayAmount() + otherPaid) - amountPaid;
					//System.out.println("balance 0: " + balance + " += " +admTermFee.getDayAmount() + " + " + otherPaid + " - " + amountPaid);
				}

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
