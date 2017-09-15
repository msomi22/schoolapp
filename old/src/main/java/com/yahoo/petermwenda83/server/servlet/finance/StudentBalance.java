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
				
				
				
				if(studentOtherMoniesDAO.getStudentOtherFeeList(accountId, studentId, term, yearLong) != null) {
					otherFeeList = studentOtherMoniesDAO.getStudentOtherFeeList(accountId, studentId, term, yearLong);
				}
				

				TermFee admTermFee = new TermFee();
				if(termFeeDAO.getFee(accountId,term, year) != null) {
					admTermFee = termFeeDAO.getFee(accountId,term, year);
				}
				

				amountPaid = 0;
				otherPaid = 0;

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

				if(StringUtils.equals(paidHas, "1")){
					balance += (admTermFee.getBoaderAmount() + otherPaid) - amountPaid;
	
				}else if(StringUtils.equals(paidHas, "0")) {
					balance += (admTermFee.getDayAmount() + otherPaid) - amountPaid;
					
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
