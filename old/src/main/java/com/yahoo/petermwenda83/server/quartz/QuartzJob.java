package com.yahoo.petermwenda83.server.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yahoo.petermwenda83.bean.account.ApiCredential;
import com.yahoo.petermwenda83.bean.account.OutGoingSMS;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.smsapi.AfricasTalking;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.ApiCredentialDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.finance.FeeConstants;


public class QuartzJob implements Job{

	private static SmsSendDAO smsSendDAO;
	private static ApiCredentialDAO smsApiDAO;

	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static FeeBreakdownDAO feeBreakdownDAO;
	private static StudentFeeDAO studentFeeDAO;

	private static SysConfigDAO sysConfigDAO;
	
	static {
		smsSendDAO = SmsSendDAO.getInstance();
		smsApiDAO = ApiCredentialDAO.getInstance();

		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
	}

	public QuartzJob() {
		super();
		
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		System.out.println("quartz **** "); 
		
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
		accountDAO.getAccounts().parallelStream().forEach(account -> {

			SysConfig sysConfig = sysConfigDAO.getSysConfig(account.getUuid());
			studentDAO.getActiveStudents(account.getUuid(), "1").parallelStream().forEach(student -> {

				if(feeBreakdownDAO.getFeeBreakdown(account.getUuid(), 
						FeeConstants.GVMT_MONEY_CODE,
						sysConfig.getTerm(),
						sysConfig.getYear(), 
						FeeConstants.GVMT_MONEY_STATUS_ACTIVE) != null){ 

					String feeBreakdownId = feeBreakdownDAO.getFeeBreakdown(account.getUuid(), FeeConstants.GVMT_MONEY_CODE, sysConfig.getTerm(),
							sysConfig.getYear(), FeeConstants.GVMT_MONEY_STATUS_ACTIVE).getUuid();

					StudentFee studentFee = new StudentFee(); 
					studentFee.setAccountId(account.getUuid());
					studentFee.setStudentId(student.getUuid());
					studentFee.setAmountPaid((int)FeeConstants.getGoKeFee(account.getUuid(), feeBreakdownId));   
					studentFee.setPayMode(FeeConstants.GVMT_MONEY_CODE);
					studentFee.setTransactionId(FeeConstants.GVMT_MONEY_CODE+RandomStringUtils.randomAlphabetic(5)); 
					studentFee.setPaidHas(student.getIsBoarding()); 
					studentFee.setTermPiad(sysConfig.getTerm());
					studentFee.setYearPaid(sysConfig.getYear());

					if(studentFeeDAO.getStudentFee(account.getUuid(), student.getUuid(), FeeConstants.GVMT_MONEY_CODE,
							sysConfig.getTerm(), sysConfig.getYear()) != null) {

						if(studentFeeDAO.putStudentFee(studentFee)) {
							//log success
							System.out.println("GoKe money add success"); 

						}else {
							//log error, contact Admin_ 
							System.out.println("error, contact Admin"); 

						}

					}else {
						//log error, student has already been assigned GoKe money 
						System.out.println("error, student has already been assigned GoKe money"); 
					}

				}else {
					//log error, GoKe money not set
					System.out.println("error, GoKe money not set"); 
				}



			});

		});

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
		String user = System.getProperty("user.name");
		String backup = "/home/"+user+"/svn/School/trunk/webapp/bin/backup.sh";
		ProcessBuilder pb = new ProcessBuilder(backup,"arg","arg");
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
		SimpleDateFormat dateFormatter = new SimpleDateFormat("h-m-s_dd-MMM yyyy"); // hour,minutes,seconds day,Month, Year
		String date = dateFormatter.format(new Date());
		String pg = "  \"C:/Program Files/PostgreSQL/9.3/bin/pg_dump.exe\" -i -h localhost -p 5432 -U school -f c -b -v -f \"D:/pgBackup/schooldb.backup\" schooldb";
		java.lang.Runtime rt = java.lang.Runtime.getRuntime();
		java.lang.Process p = rt.exec(pg);
	}

}
