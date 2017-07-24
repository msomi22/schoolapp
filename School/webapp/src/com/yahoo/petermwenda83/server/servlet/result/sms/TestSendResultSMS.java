/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.result.sms;

import org.junit.Test;

import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsApiDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;

/**
 * @author peter
 *
 */
public class TestSendResultSMS {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String studentUuid = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
	final String schooluuid = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	
	final String engscorestr = "63",kswscorestr = "56",matscorestr ="38",physcorestr ="45";
	final String bioscorestr = "56",chemscorestr = "49",bsscorestr ="67",comscorestr ="";
	final String hscscorestr = "60",agriscorestr = "56",geoscorestr ="",crescorestr ="CRE ";
	final String histscorestr = "";
	final double Studentmean = 78.23;
	
	private SendResultSMS store;
	private StudentDAO studentDAO;
	private ParentsDAO parentsDAO;
	private SmsSendDAO smsSendDAO;
	private static SmsApiDAO smsApiDAO;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.server.servlet.result.sms.SendResultSMS#sendToParents(com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO, com.yahoo.petermwenda83.persistence.student.StudentDAO, com.yahoo.petermwenda83.persistence.guardian.ParentsDAO, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, double)}.
	 */
	@Test
	public final void testSendToParents() {
		store = new SendResultSMS();
		studentDAO = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		parentsDAO = new ParentsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    smsSendDAO = new SmsSendDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    smsApiDAO = new SmsApiDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    
		store.sendToParents(smsApiDAO,smsSendDAO, studentDAO, parentsDAO, studentUuid, schooluuid,
				engscorestr, kswscorestr, matscorestr, physcorestr, bioscorestr, 
				chemscorestr, bsscorestr, comscorestr, hscscorestr, agriscorestr,
				geoscorestr, crescorestr, histscorestr, Studentmean);
		
		
	}

}
