/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.result.sms;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yahoo.petermwenda83.bean.schoolaccount.SmsApi;
import com.yahoo.petermwenda83.bean.schoolaccount.SmsSend;
import com.yahoo.petermwenda83.bean.smsapi.AfricasTalking;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsApiDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.sms.send.AfricasTalkingGateway;
import com.yahoo.petermwenda83.server.servlet.util.LogUtil;

/**
 * The logic in this class is used to send SMS to parents,
 * This SMS contains student's performance for that term.
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class SendResultSMS {
	

	/**
	 * This constructor has no specific used in this class
	 */
	public SendResultSMS() {}

	/**
	 * 
	 * @param smsApiDAO smsApi Data Access Object that provides api key and username
	 * @param smsSendDAO smsSend  Data Access Object that store an SMS in a database if the SMS wasn't send.
	 * @param studentDAO Student  Data Access Object the provides a specific student
	 * @param parentsDAO Parent  Data Access Object that provides the parent to send the SMS to
	 * @param studentuuid Student ID
	 * @param schooluuid The school ID
	 * @param engscorestr English score
	 * @param kswscorestr Kiswahili score
	 * @param matscorestr Mathematics score
	 * @param physcorestr Physics score
	 * @param bioscorestr Biology score
	 * @param chemscorestr Chemistry score
	 * @param bsscorestr Business Studies score
	 * @param comscorestr Computer score
	 * @param hscscorestr Home Science score
	 * @param agriscorestr Agriculture score
	 * @param geoscorestr Geography score
	 * @param crescorestr Christians Religion score
	 * @param histscorestr History score
	 * @param studentmean The student mean score
	 */
	 
	public void sendToParents(SmsApiDAO smsApiDAO,SmsSendDAO smsSendDAO, StudentDAO studentDAO, ParentsDAO parentsDAO, String studentuuid,String schooluuid,
			String engscorestr, String kswscorestr, String matscorestr,String physcorestr,
			String bioscorestr, String chemscorestr, String bsscorestr, String comscorestr,
			String hscscorestr, String agriscorestr, String geoscorestr, String crescorestr, String histscorestr,
			double studentmean) {
		    Student student = new Student();
		    
		    String studentname = "";
		    String message = "";
		    DecimalFormat rf = new DecimalFormat("0.0"); 
			rf.setRoundingMode(RoundingMode.HALF_UP);

		    
			LogUtil logUtil = new LogUtil();
		    StudentParent studentParent = new StudentParent();
		    if(studentDAO.getStudentByuuid(schooluuid, studentuuid) !=null){
	     	student = studentDAO.getStudentByuuid(schooluuid, studentuuid);
		    }
		    if(parentsDAO.getParent(studentuuid) !=null){
	     	studentParent = parentsDAO.getParent(studentuuid);
		    }
		    
		    String formatedFirstname = StringUtils.capitalize(student.getFirstname().toLowerCase());
		    String formatedLastname = StringUtils.capitalize(student.getLastname().toLowerCase());
		    String formatedsurname = StringUtils.capitalize(student.getSurname().toLowerCase());
		    
		    formatedFirstname = formatedFirstname.substring(0, Math.min(formatedFirstname.length(), 10));
			formatedLastname = formatedLastname.substring(0, Math.min(formatedLastname.length(), 10));
			formatedsurname = formatedsurname.substring(0, Math.min(formatedsurname.length(), 10));
		     
		    studentname = formatedFirstname + " " + formatedLastname + " " + formatedsurname;
		    
		
		    message = studentname +",ENG "+engscorestr+",KIS "+kswscorestr+",MAT "+matscorestr+
		    		",PHY "+physcorestr+",BIO "+bioscorestr+",CHE "+chemscorestr+",B/S "+bsscorestr+
		    		",CMP "+comscorestr+",HSC "+hscscorestr+",AGR "+agriscorestr+",GEO "+geoscorestr+
		    		",CRE "+crescorestr+",HST "+histscorestr + ",MEAN "+ rf.format(studentmean);
		    
		  // System.out.println(message);
		    
		    String phone = "";
			String formatedphone = "";
			String realphone = "";
			
			
		    if(parentsDAO.getParent(studentuuid) !=null){
				studentParent = parentsDAO.getParent(studentuuid);
				//parentname = StringUtils.capitalize(studentParent.getFathername().toLowerCase());
				phone = studentParent.getFatherphone();
				formatedphone = phone.replaceFirst("^0+(?!$)", "");
				realphone = "+254"+formatedphone;
			}
		    
		  
			AfricasTalking africasTalking = new AfricasTalking();
			SmsApi smsApi = smsApiDAO.getSmsApi(schooluuid);
			String username = smsApi.getApiPassword();//africasTalking.getUsername();
			String apiKey   = smsApi.getApiKey();//africasTalking.getApiKey();
						
			africasTalking.setMessage(message); 
			africasTalking.setRecipients(realphone); 
			// Create a new instance of our awesome gateway class
			AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
			
			//save to database
			String thestatus ="";
			String thenumber ="";
			String themessage ="";
			String thecost ="";

			SmsSend smsSend = new SmsSend();
			smsSend.setStatus("failed");
			smsSend.setPhoneNo(realphone);
			smsSend.setMessageId(message.replaceAll("[\r\n]+", " "));
			smsSend.setCost("1");
			smsSendDAO.putSmsSend(smsSend);
			logUtil.writeLog("result sms send  " + smsSend); 


			try {
				JSONArray results = gateway.sendMessage(africasTalking.getRecipients(), africasTalking.getMessage());
				for( int i = 0; i < results.length(); ++i ) {
					JSONObject result = results.getJSONObject(i);

					thestatus = result.getString("status");
					thenumber = result.getString("number");
					themessage = message;
					thecost = result.getString("cost");

					SmsSend smsSend2 = smsSendDAO.getSmsSend(smsSend.getUuid());
					smsSend2.setStatus(thestatus);
					smsSend2.setPhoneNo(thenumber);
					smsSend2.setMessageId(themessage.replaceAll("[\r\n]+", " "));
					smsSend2.setCost(thecost);
					smsSendDAO.updateSmsSend(smsSend2); 
					logUtil.writeLog("result sms send  " + smsSend2); 

				} 

			}

			catch (Exception e) {
				e.printStackTrace(); 
			}
		
		
		
	}
	


}
