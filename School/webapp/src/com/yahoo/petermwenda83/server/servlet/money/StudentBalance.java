/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.money;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
import com.yahoo.petermwenda83.server.servlet.util.LogUtil;

/**
 * @author peter
 *
 */
public class StudentBalance {

	private SimpleDateFormat yearformatter;
	private ExamConfig termConfig;
	private String admYear;
	private List<StudentFee> studentFeeList;
	private List<StudentOtherMonies> othermoneyList;
	//private TermFee termFee;
	private String [] terms;
	private String currentYear;
	private String currentTerm;
	
	private String feeStudentType = "";
	LogUtil logUtil = new LogUtil();

	/** find the student balance between (admission year and term) - (current year and term)
	 * 
	 */
	public StudentBalance() {
		yearformatter = new SimpleDateFormat("yyyy");
		termConfig = new ExamConfig();
		admYear = "";
		studentFeeList = new ArrayList<>();
		othermoneyList = new ArrayList<>();
		//termFee = new TermFee();
		terms = new String [] {"1","2","3"};
		currentYear = "";

	}

	public double findBalance(TermFeeDAO termFeeDAO, ExamConfigDAO examConfigDAO, StudentFeeDAO studentFeeDAO,
			StudentOtherMoniesDAO studentOtherMoniesDAO, Date admissiondate, String studentRegTerm, String studentuuid, String schooluuid,int finalYear) {
		System.out.println("START: " + new Date());
		double balance = 0;
		double amountPaid = 0;
		double otherPaid = 0;
		admYear = yearformatter.format(admissiondate);
		termConfig = examConfigDAO.getExamConfig(schooluuid);
		//termFee = termFeeDAO.getFee(schooluuid,termConfig.getTerm(), termConfig.getYear());
		currentYear = termConfig.getYear();
		currentTerm = termConfig.getTerm();

		int admYr = Integer.parseInt(admYear);
		int admTm = Integer.parseInt(studentRegTerm);
		int crrntYr = Integer.parseInt(currentYear);
		
		if(admYr == admYr){
		     if(admTm == 2){   
		    	 terms = new String [] {"2","3"}; 
		     }else if(admTm == 3){  
		    	 terms = new String [] {"3"};
		     }else  if(admTm == 1){
		    	 terms = new String [] {"1","2","3"}; 
		     }
		}

		while(admYr <= crrntYr && crrntYr <= finalYear){ 
			//start from admission year
			String year = Integer.toString(admYr);
			String term = "";
			for(int i=0;i<terms.length;i++){
				term = terms[i];
				
				System.out.println("schooluuid = " +schooluuid);
				System.out.println("studentuuid = " + studentuuid);
				System.out.println("term = " + term);
				System.out.println("year = " + year);
				//start finding the balance here
				studentFeeList = studentFeeDAO.getStudentFeeByStudentUuidList(schooluuid, studentuuid, term, year);
				System.out.println("size = " + studentFeeList.size());
				othermoneyList = studentOtherMoniesDAO.getStudentOtherList(studentuuid, term, year);
				TermFee admTermFee = new TermFee();
				admTermFee = termFeeDAO.getFee(schooluuid,term, year);
				System.out.println("[ TermFee = " + admTermFee.getTermAmount() +" Term " + admTermFee.getTerm() + " Year " + admTermFee.getYear() +  "]");
				
				amountPaid = 0;
				otherPaid = 0;
				logUtil.writeLog("find fee balance  " + studentFeeList);
				logUtil.writeLog("find fee balance  " + othermoneyList);
				
				for(StudentFee studentFee :studentFeeList){
					amountPaid +=studentFee.getAmountPaid();//amount paid per term
					System.out.println("amountPaid = " + amountPaid +" Term " + studentFee.getTerm() + " Year " + studentFee.getYear());
					feeStudentType = studentFee.getStudentType();//last payment type , day/boarder
					System.out.println("feeStudentType " + feeStudentType);
				}

				for(StudentOtherMonies otherMoney :othermoneyList){
					otherPaid += otherMoney.getAmountPiad();
					System.out.println("otherPaid = " + otherPaid +" Term " + otherMoney.getTerm() + " Year " + otherMoney.getYear());
				}
		
				if(StringUtils.equals(feeStudentType, "Boarder")){
					 balance += (admTermFee.getTermAmount() + otherPaid) - amountPaid;
				}else if(StringUtils.equals(feeStudentType, "Day")) {
					 balance += (admTermFee.getDayAmount() + otherPaid) - amountPaid;
				}
				// clear our list at the end to ensure a clean start
				studentFeeList.clear();
				othermoneyList.clear();
				
				System.out.println("***balance***" + balance);
				
				if(admYr == crrntYr && crrntYr <= finalYear){
					if(StringUtils.equals(term, currentTerm)){
						break;
					}
				}

			}
			
			terms = new String [] {"1","2","3"}; 
			admYr +=1;
			System.out.println("STOP: " + new Date());
		}
		logUtil.writeLog("find fee balance = " + balance);
		return balance;
	}

}
