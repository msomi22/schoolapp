package com.yahoo.petermwenda83.server.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yahoo.petermwenda83.DuplicateChecker;
import com.yahoo.petermwenda83.bean.schoolaccount.SmsApi;
import com.yahoo.petermwenda83.bean.schoolaccount.SmsSend;
import com.yahoo.petermwenda83.bean.smsapi.AfricasTalking;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsApiDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.server.servlet.sms.send.AfricasTalkingGateway;
import com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig;

public class QuartzJob implements Job {

	private static SmsSendDAO smsSendDAO;
	private static SmsApiDAO smsApiDAO;
	private static String schooluuid;
	private Logger logger = Logger.getLogger(this.getClass());

	public QuartzJob() {
		super();
		smsSendDAO = SmsSendDAO.getInstance();
		smsApiDAO = SmsApiDAO.getInstance();
		schooluuid = PropertiesConfig.getConfigValue("SCHOOL_ID");
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		try {

			if (SystemUtils.IS_OS_WINDOWS) {
				backUpWin();
			}

			if (SystemUtils.IS_OS_LINUX) {
				StartBackup();
			}

			// checksentSMS();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void checksentSMS() {
		List<SmsSend> smslist = new ArrayList<>();
		if (smsSendDAO.getSmsSend() != null) {
			smslist = smsSendDAO.getSmsSend();
			if (smslist != null) {
				for (SmsSend sms : smslist) {
					String phone = sms.getPhoneNo();
					String message = sms.getMessageId();
					String status = sms.getStatus();

					if (StringUtils.equalsIgnoreCase(status, "failed")) {
						// send message
						AfricasTalking africasTalking = new AfricasTalking();
						// Specify your login credentials
						if (smsApiDAO.getSmsApi(schooluuid) != null) {
							SmsApi smsApi = smsApiDAO.getSmsApi(schooluuid);
							String username = smsApi.getApiPassword();// africasTalking.getUsername();
							String apiKey = smsApi.getApiKey();// africasTalking.getApiKey();
							africasTalking.setMessage(message);
							africasTalking.setRecipients(phone);
							// Create a new instance of our awesome gateway class
							AfricasTalkingGateway gateway = new AfricasTalkingGateway(username, apiKey);
							try {
								JSONArray results = gateway.sendMessage(africasTalking.getRecipients(),
										africasTalking.getMessage());
								for (int i = 0; i < results.length(); ++i) {
									JSONObject result = results.getJSONObject(i);

									// save to database
									String thestatus = "";
									String thenumber = "";
									String themessage = "";
									String thecost = "";

									thestatus = result.getString("status");
									thenumber = result.getString("number");
									themessage = message;
									thecost = result.getString("cost");

									if (StringUtils.isBlank(thestatus)) {
										thestatus = "failed";
									}
									if (StringUtils.isBlank(thenumber)) {
										thenumber = phone;
									}
									if (StringUtils.isBlank(thecost)) {
										thecost = "1";
									}
									SmsSend smsSend = smsSendDAO.getSmsSend(sms.getUuid());
									smsSend.setStatus(thestatus);
									smsSend.setPhoneNo(thenumber);
									smsSend.setMessageId(themessage.replaceAll("[\r\n]+", " "));
									smsSend.setCost(thecost);
									smsSendDAO.updateSmsSend(smsSend);

								}

							}

							catch (Exception e) {
								e.printStackTrace();
							}
						}
					} // end if(smsApiDAO.getSmsApi(schooluuid) !=null){
				}
			}
		} // end if(smsSendDAO.getSmsSend() !=null){
	}

	/**
	 * 
	 */
	private void StartBackup() {
		String user = System.getProperty("user.name");
		String backup = "/home/" + user + "/svn/School/trunk/webapp/bin/backup.sh";
		ProcessBuilder pb = new ProcessBuilder(backup, "arg", "arg");
		try {
			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;

			while ((line = br.readLine()) != null) {
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
		SimpleDateFormat dateFormatter = new SimpleDateFormat("h-m-s_dd-MMM yyyy"); // hour,minutes,seconds day,Month,
																					// Year
		String date = dateFormatter.format(new Date());
		String pg = "  \"C:/Program Files/PostgreSQL/9.3/bin/pg_dump.exe\" -i -h localhost -p 5432 -U school -f c -b -v -f \"D:/pgBackup/schooldb.backup\" schooldb";
		java.lang.Runtime rt = java.lang.Runtime.getRuntime();
		java.lang.Process p = rt.exec(pg);
	}

}
