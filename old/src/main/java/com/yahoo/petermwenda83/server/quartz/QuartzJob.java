package com.yahoo.petermwenda83.server.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.FeeBreakdown;
import com.yahoo.petermwenda83.bean.money.GokeMoneyUsage;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDAO;
import com.yahoo.petermwenda83.persistence.money.GokeMoneyUsageDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.finance.FeeConstants;


public class QuartzJob implements Job{

	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static FeeBreakdownDAO feeBreakdownDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static GokeMoneyUsageDAO gokeMoneyUsageDAO;

	private static SysConfigDAO sysConfigDAO;
	
	
	static {
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		gokeMoneyUsageDAO = GokeMoneyUsageDAO.getInstance();
	}

	public QuartzJob() {
		super();

	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		synchGoKeMoney();

		try {

			if(SystemUtils.IS_OS_WINDOWS){
				backUpWin();
			}

			if(SystemUtils.IS_OS_LINUX){
				StartBackup();
			}

			checksentSMS();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * 
	 */
	private void synchGoKeMoney() {

		if(accountDAO.getAccounts() != null) {
			
			accountDAO.getAccounts().forEach(account -> {//.parallelStream()
				
				if(sysConfigDAO.getSysConfig(account.getUuid()) != null) {
					
					SysConfig sysConfig = sysConfigDAO.getSysConfig(account.getUuid());
					
					
						if(feeBreakdownDAO.getFeeBreakdown(account.getUuid(), 
								FeeConstants.GVMT_MONEY_CODE,
								sysConfig.getTerm(),
								sysConfig.getYear(), 
								FeeConstants.GVMT_MONEY_STATUS_ACTIVE) != null){ 
							
							FeeBreakdown feeBreakdown = feeBreakdownDAO.getFeeBreakdown(account.getUuid(), FeeConstants.GVMT_MONEY_CODE, sysConfig.getTerm(),
									sysConfig.getYear(), FeeConstants.GVMT_MONEY_STATUS_ACTIVE);

							int amountToEachStudent = (int)FeeConstants.getGoKeFee(account.getUuid(), feeBreakdown.getUuid());
							
							double totalAmount = feeBreakdown.getAmount();
							int no_of_students = studentDAO.activeCount(account.getUuid(), "1"); 
							double expected_amount_per_head = 0;
							double balance = 0;
							
							if(no_of_students > 0 && totalAmount > 0) {
								expected_amount_per_head = totalAmount / no_of_students;
							}
							
							if(amountToEachStudent > expected_amount_per_head) {
								//error
								
							}else {
								
								//good
								
								balance = totalAmount - (amountToEachStudent * no_of_students);
								
								if(gokeMoneyUsageDAO.getGokeMoneyUsage(account.getUuid(), sysConfig.getTerm(), sysConfig.getYear()) == null) {
									
									GokeMoneyUsage gokeMoneyUsage = new GokeMoneyUsage();
									gokeMoneyUsage.setAccountId(account.getUuid());
									gokeMoneyUsage.setNumberOfStudents(no_of_students);
									gokeMoneyUsage.setAmountPerStudent(amountToEachStudent);
									gokeMoneyUsage.setTotalAmount((int)totalAmount);
									gokeMoneyUsage.setBalance((int)balance);
									gokeMoneyUsage.setTerm(sysConfig.getTerm());
									gokeMoneyUsage.setYear(sysConfig.getYear());
									
									if(gokeMoneyUsageDAO.getGokeMoneyUsage(account.getUuid(), sysConfig.getTerm(), sysConfig.getYear()) == null) {
										gokeMoneyUsageDAO.putGokeMoneyUsage(gokeMoneyUsage);
										
									}
									
								}
								
								//we might need to sleep here
								
								
								studentDAO.getActiveStudents(account.getUuid(), "1").parallelStream().forEach(student -> {
									
									if(studentDAO.getActiveStudents(account.getUuid(), "1") != null) {
										allocateGokMoney(account, sysConfig, student, amountToEachStudent);
									}
									
								});
								
								
								
							}
							
						}else {
							//log error, GoKe money not set
							//System.out.println("error, GoKe money not set"); 
						}

				}

			});
		}

	}

	/**
	 * @param account
	 * @param sysConfig
	 * @param student
	 * @param amountToEachStudent
	 */
	private void allocateGokMoney(Account account, SysConfig sysConfig, Student student, int amountToEachStudent) {
		StudentFee studentFee = new StudentFee(); 
		studentFee.setAccountId(account.getUuid());
		studentFee.setStudentId(student.getUuid());
		studentFee.setAmountPaid(amountToEachStudent);   
		studentFee.setPayMode(FeeConstants.GVMT_MONEY_CODE);
		studentFee.setTransactionId(FeeConstants.GVMT_MONEY_CODE+RandomStringUtils.randomAlphabetic(5)); 
		studentFee.setPaidHas(student.getIsBoarding()); 
		studentFee.setTermPiad(sysConfig.getTerm());
		studentFee.setYearPaid(sysConfig.getYear());

		if(studentFeeDAO.getStudentFee(account.getUuid(), student.getUuid(), FeeConstants.GVMT_MONEY_CODE,
				sysConfig.getTerm(), sysConfig.getYear()) != null) {
			//log error, student has already been assigned GoKe money 
			//System.out.println("error, student has already been assigned GoKe money"); 
			
			

		}else {
			
			if(studentFeeDAO.putStudentFee(studentFee)) {
				//log success
				//System.out.println("GoKe money add success"); 

			}else {
				//log error, contact Admin_ 
				//System.out.println("error, contact Admin"); 

			}
		}
	}


	/**
	 * 
	 */
	private void checksentSMS() {


	}

	/**
	 * 
	 */
	private void StartBackup() {
		
		String file = GetBackupScript.getBackupFile();
		ProcessBuilder pb = new ProcessBuilder(file,"arg","arg");
		try {
			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			String line = null;

			while((line=br.readLine())!=null){
				System.out.println(line);

			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}


	}

	/**
	 * @throws IOException
	 */
	private void backUpWin() throws IOException {
		
		String pg_version = "9.3";
		String pg_home = " \"C:/Program Files/PostgreSQL/"+pg_version+"/bin/pg_dump.exe\"";  
		String backupDir = " \"D:/pgBackup/schooldb.backup\" ";
		
		String pg = pg_home+" -i -h localhost -p 5432 -U school -f c -b -v -f "+backupDir+" schooldb";
		java.lang.Runtime rt = java.lang.Runtime.getRuntime();
		java.lang.Process p = rt.exec(pg);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
