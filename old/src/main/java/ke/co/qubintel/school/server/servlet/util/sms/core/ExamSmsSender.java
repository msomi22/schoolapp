/**
 * 
 */
package ke.co.qubintel.school.server.servlet.util.sms.core;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.bean.money.StudentFee;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.bean.student.guardian.StudentParent;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.persistence.account.ApiCredentialDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.guardian.ParentsDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.servlet.finance.StudentBalance;
import ke.co.qubintel.school.server.servlet.util.sms.SmsObject;
import ke.co.qubintel.school.server.servlet.util.sms.SmsUtil;

/**
 * @author peter
 *
 */
public class ExamSmsSender {

	private static ParentsDAO parentsDAO;
	private static AccountDAO accountDAO;
	private static ApiCredentialDAO smsApiDAO;
	private static SysConfigDAO sysConfigDAO;
	private static StudentDAO studentDAO;

	static {
		parentsDAO = ParentsDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		smsApiDAO = ApiCredentialDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
	}

	/**
	 * 
	 * @param accountId
	 * @param student
	 * @param totalMean
	 * @param mean
	 * @param grade
	 * @return
	 */
	public static Object sendScoreSMS(String accountId, Student student, int totalMean, String mean, String grade, String examNames) { 

		String parentName = "";
		String parentMobile = "";
		String message = "";
		boolean isMale = false;
		SmsObject smsObject = new SmsObject();
		String accountName = "";
		String term = "";
		String year = "";

		if(parentsDAO.getParent(accountId, student.getUuid()) != null) {
			StudentParent studentParent = parentsDAO.getParent(accountId, student.getUuid());

			String multiName[] = studentParent.getName().split("\\s+"); //split by space 

			parentMobile = studentParent.getMobile();

			if(StringUtils.equalsAnyIgnoreCase(student.getGender(), "M")) {
				isMale = true;
			}


			if(parentMobile.length() == 9 && multiName.length > 0) {   

				parentName = multiName[0];

				message = "Hi " + parentName;

				if(isMale) {
					message += ", your son";
				}else {
					message += ", your daughter ";
				}

				accountName = accountDAO.getAccountById(accountId).getName();  
				term = sysConfigDAO.getSysConfig(accountId).getTerm(); 
				year = sysConfigDAO.getSysConfig(accountId).getYear();

				message += student.getFirstname() + " scores for term: " + term + ", year: " + year + " are"; 
				message += ", T " + totalMean + ", M " + mean + ", G " + grade +", Exam " + examNames;  


				if(smsApiDAO.getApiCredential(accountId, "SMS_API") != null) {

					String key = smsApiDAO.getApiCredential(accountId, "SMS_API").getApiKey();//key
					String secret = smsApiDAO.getApiCredential(accountId, "SMS_API").getApisecret();//user_name
					smsObject = new SmsObject(accountId,parentMobile,message,secret,key);
					SmsUtil.sendSMS(smsObject); 

				}

			}

		}

		return smsObject;
	}


	/**
	 * 
	 * @param studentFee
	 */
	public static void sendFeeBalSMS(StudentFee studentFee) {

		String parentName = "";
		String parentMobile = "";
		String message = "";
		boolean isMale = false;
		SmsObject smsObject = new SmsObject();
		//String accountName = "";
		String term = "";
		String year = "";

		if(parentsDAO.getParent(studentFee.getAccountId(), studentFee.getStudentId()) != null) {
			StudentParent studentParent = parentsDAO.getParent(studentFee.getAccountId(), studentFee.getStudentId());
			Student student = studentDAO.getStudentById(studentFee.getAccountId(), studentFee.getStudentId());

			String multiName[] = studentParent.getName().split("\\s+"); //split by space 

			parentMobile = studentParent.getMobile();

			if(StringUtils.equalsAnyIgnoreCase(student.getGender(), "M")) {
				isMale = true;
			}


			if(parentMobile.length() == 9 && multiName.length > 0) {   

				parentName = multiName[0];

				message = "Hi " + parentName;

				if(isMale) {
					message += ", your son";
				}else {
					message += ", your daughter ";
				}

				//accountName = accountDAO.getAccountById(studentFee.getAccountId()).getName();  
				term = sysConfigDAO.getSysConfig(studentFee.getAccountId()).getTerm(); 
				year = sysConfigDAO.getSysConfig(studentFee.getAccountId()).getYear();

				String paid = "";

				Locale locale = new Locale("en", "KE");
				NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

				StudentBalance balance = new StudentBalance();
				double feeBalance = balance.findBalance(studentFee.getAccountId(), studentFee.getStudentId()); 

				String feeBal = nf.format(feeBalance);
				paid =  nf.format(studentFee.getAmountPaid()); 

				message += student.getFirstname() + " has paid fee of amount " + paid + " for term: "
						+ term + ", year: " + year + " fee bal is: " + feeBal; 



				if(smsApiDAO.getApiCredential(studentFee.getAccountId(), "SMS_API") != null) {

					String key = smsApiDAO.getApiCredential(studentFee.getAccountId(), "SMS_API").getApiKey();//key
					String secret = smsApiDAO.getApiCredential(studentFee.getAccountId(), "SMS_API").getApisecret();//user_name
					smsObject = new SmsObject(studentFee.getAccountId(),parentMobile,message,secret,key);
					SmsUtil.sendSMS(smsObject); 

				}

			}

		}

	}

}
