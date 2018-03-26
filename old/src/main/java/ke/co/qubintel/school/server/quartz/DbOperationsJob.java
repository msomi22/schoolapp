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
package ke.co.qubintel.school.server.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.classroom.Stream;
import ke.co.qubintel.school.server.bean.exam.Exam;
import ke.co.qubintel.school.server.bean.exam.Perfomance;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.money.FeeBreakdown;
import ke.co.qubintel.school.server.bean.money.GokeMoneyUsage;
import ke.co.qubintel.school.server.bean.money.StudentFee;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.bean.subject.Subject;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.ExamDAO;
import ke.co.qubintel.school.server.persistence.exam.PerfomanceDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.money.FeeBreakdownDAO;
import ke.co.qubintel.school.server.persistence.money.GokeMoneyUsageDAO;
import ke.co.qubintel.school.server.persistence.money.StudentFeeDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;
import ke.co.qubintel.school.server.servlet.finance.FeeConstants;
import ke.co.qubintel.school.server.servlet.quartz.factory.StartDateFromLog;
import ke.co.qubintel.school.server.servlet.util.SYS_COSTANTS;


public class DbOperationsJob implements Job{

	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static FeeBreakdownDAO feeBreakdownDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static GokeMoneyUsageDAO gokeMoneyUsageDAO;

	private static SysConfigDAO sysConfigDAO;
	private static SubjectDAO subjectDAO;
	private static ExamDAO examDAO;
	private static StreamDAO streamDAO;
	private static PerfomanceDAO perfomanceDAO;


	static {
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		gokeMoneyUsageDAO = GokeMoneyUsageDAO.getInstance();

		subjectDAO = SubjectDAO.getInstance();
		examDAO = ExamDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		perfomanceDAO = PerfomanceDAO.getInstance();


	}

	public DbOperationsJob() {
		super();

	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		synchGoKeMoney();
		checkExamDuplicate();  
		lockAccount();
		

		try {
			
			//backUpWin();

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
	private static void lockAccount() {

		accountDAO.getAccounts().parallelStream().forEach(sch -> {
			sch.setIsActive(SYS_COSTANTS.STATUS_INACTIVE);  
			sch.setUsername("lock"); 
			sch.setPassword("lock123"); 
			
			StartDateFromLog.checkTimeout();
			
			if(!StringUtils.contains(sch.getName(), "Burumba")) {
				
				//TODO
				Timestamp now = new Timestamp(new Date().getTime()); 
				int days = timeDiff(now,sch.getCreationDate());  
				
				//System.out.println("Account locked!" + days);  
				
				if(Math.abs(days) > 80) {
					accountDAO.updateAccount(sch);
				}
				
			}

			
		});

	}

	/**
	 * 
	 */
	private void checkExamDuplicate() {

		if(accountDAO.getAccounts() != null) {

			List<Account> accountList = new ArrayList<>();
			accountList = accountDAO.getAccounts();

			accountList.stream().forEach(account -> {

				if(studentDAO.getStudents(account.getUuid()) != null) {

					List<Student> studentList = new ArrayList<>();
					studentList = studentDAO.getStudents(account.getUuid());

					studentList.stream().forEach(student -> {

						List<Subject> subjectList  = new ArrayList<>();
						subjectList = subjectDAO.getSubjects(account.getUuid());

						subjectList.stream().forEach(subject -> {

							List<Exam> examList  = new ArrayList<>();
							examList = examDAO.getExamList(account.getUuid());
							examList.stream().forEach(exam -> {

								List<Stream> streamList = new ArrayList<>();
								streamList = streamDAO.getStreamList(account.getUuid());
								streamList.stream().forEach(stream -> {

									SysConfig sysConfig = new SysConfig();
									sysConfig = sysConfigDAO.getSysConfig(account.getUuid());

									List<Perfomance> performanceList = new ArrayList<>();

									performanceList = perfomanceDAO.getPerformanceList(
											account.getUuid(), 
											exam.getUuid(), 
											student.getUuid(),
											stream.getUuid(), 
											subject.getUuid(),  
											sysConfig.getTerm(), 
											sysConfig.getYear());

									if(!performanceList.isEmpty()) {

										int size = performanceList.size();

										if(size > 1) {

											for(int i=1;i<size;i++) {
												//i will start with 1, then 2 .... skipping index zero
												//delete all except index zero
												deleteDuplicate(performanceList.get(i));

											}

										}

									}


								});
							});


						});


					});
				}

			});

		}


	}
	/**
	 * 
	 * @param perfomance
	 */
	private void deleteDuplicate(Perfomance perfomance) { 

		perfomanceDAO.deleteStreamSubjectDuplicate(perfomance.getAccountId(), perfomance.getUuid());

		//System.out.println(" ----------- Duplicate detected and deleted!  --------- " + perfomance + " ----------------------------- "); 
	}

	/**
	 * 
	 */
	private void synchGoKeMoney() {

		if(accountDAO.getAccounts() != null) {

			List<Account> accountList = new ArrayList<>();
			accountList = accountDAO.getAccounts();

			accountList.forEach(account -> {

				if(sysConfigDAO.getSysConfig(account.getUuid()) != null) {

					SysConfig sysConfig = new SysConfig();
					sysConfig = sysConfigDAO.getSysConfig(account.getUuid());

					FeeBreakdown feeBreakdown = new FeeBreakdown();

					feeBreakdown = feeBreakdownDAO.getFeeBreakdown(
							account.getUuid(), 
							FeeConstants.GVMT_MONEY_CODE, 
							sysConfigDAO.getSysConfig(account.getUuid()).getTerm(),
							sysConfigDAO.getSysConfig(account.getUuid()).getYear(), 
							FeeConstants.GVMT_MONEY_STATUS_ACTIVE);


					if(feeBreakdown != null){ 

						int amountToEachStudent = (int)FeeConstants.getGoKeFee(account.getUuid(), feeBreakdown.getUuid());

						AtomicInteger scount = new AtomicInteger();

						List<Student> studentList  = new ArrayList<>();
						studentList = studentDAO.getActiveStudents(account.getUuid(), "1","1");

						if(!studentList.isEmpty()) {

							studentList.forEach(st -> {

								StudentFee studentFee = new StudentFee();
								studentFee = studentFeeDAO.getStudentFee(
										account.getUuid(), 
										st.getUuid(), 
										FeeConstants.GVMT_MONEY_CODE,
										sysConfigDAO.getSysConfig(account.getUuid()).getTerm(),
										sysConfigDAO.getSysConfig(account.getUuid()).getYear());

								if(studentFee == null) {

									scount.getAndIncrement();

								}

							});

						}




						double totalAmount = feeBreakdown.getAmount();
						int no_of_students = scount.get();
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

							//active and eligible
							studentDAO.getActiveStudents(account.getUuid(), "1","1").parallelStream().forEach(student -> {

								if(studentDAO.getActiveStudents(account.getUuid(), "1","1") != null) {
									allocateGokMoney(account, sysConfigDAO.getSysConfig(account.getUuid()), student, amountToEachStudent);

								}

							});


							feeBreakdown.setStatus("0"); 
							feeBreakdownDAO.updateFeeBreakdown(feeBreakdown);



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
	private boolean allocateGokMoney(Account account, SysConfig sysConfig, Student student, int amountToEachStudent) {

		boolean success = false;


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
			success = false;
			return success;


		}else if(StringUtils.equals(student.getIsGoKFeeEligibe(), "0")) { 
			//System.out.println("error, student not eligible!"); 
			success = false;
			return success;

		}else {

			if(studentFeeDAO.putStudentFee(studentFee)) {
				//log success
				//System.out.println("GoKe money add success"); 
				success = true;
				return success;

			}else {
				//log error, contact Admin_ 
				//System.out.println("error, contact Admin"); 
				success = false;
				return success;

			}
		}

		//return success;
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
				//System.out.println(line);

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
		//String backupDir = " \"D:/pgBackup/schooldb.backup\" ";
		String dir = WriteToFile.DB_DIRECTORY;

		String pg = pg_home+" -i -h localhost -p 5432 -U school -f c -b -v -f "+dir+" schooldb";
		java.lang.Runtime rt = java.lang.Runtime.getRuntime();
		java.lang.Process p = rt.exec(pg);
		//System.out.println("*********************************************************"); 
		//System.out.println(p.toString()); 
	}



	public static Timestamp strToTstamp(String date_str) {
		//System.out.println(" date_str:  " + date_str); 
		SimpleDateFormat formatter;
		//Feb 14 2018 15:10:59 -> MMM dd yyyy hh:mm:ss
		//14 Feb 2018 15:10:59 -> dd MMM yyyy hh:mm:ss
		formatter = new SimpleDateFormat("dd MMM yyyy hh:mm:ss"); 
		Date date;
		try {

			date = (Date) formatter.parse(date_str);
			java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
			return timeStampDate;

		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}




	/**
	 * 
	 * @param date_start
	 * @param date_stop
	 * @return
	 */
	public static int timeDiff(Timestamp date_start, Timestamp date_stop) {

		DateTime dt1 = new DateTime(date_start);
		DateTime dt2 = new DateTime(date_stop);

		int days = Days.daysBetween(dt1, dt2).getDays();

		return days;
	}










}
