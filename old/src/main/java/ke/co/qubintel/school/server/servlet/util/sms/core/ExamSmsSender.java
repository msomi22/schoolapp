/**
 * 
 */
package ke.co.qubintel.school.server.servlet.util.sms.core;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.bean.student.guardian.StudentParent;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.persistence.account.ApiCredentialDAO;
import ke.co.qubintel.school.server.persistence.guardian.ParentsDAO;
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
	
	static {
		parentsDAO = ParentsDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		smsApiDAO = ApiCredentialDAO.getInstance();
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
	public static Object sendScoreSMS(String accountId, Student student, int totalMean, String mean, String grade) { 
		
		String parentName = "";
		String parentMobile = "";
		String message = "";
		boolean isMale = false;
		SmsObject smsObject = new SmsObject();
		String accountName = "";
		
		if(parentsDAO.getParent(accountId, student.getUuid()) != null) {
			StudentParent studentParent = parentsDAO.getParent(accountId, student.getUuid());
			parentName = studentParent.getName();
			parentMobile = studentParent.getMobile();
			
			if(StringUtils.equalsAnyIgnoreCase(student.getGender(), "M")) {
				isMale = true;
			}
			
			
			if(parentMobile.length() == 9 && parentName.length() > 3) {  
				
				message = "Hi " + parentName;
				
				if(isMale) {
					message += ", your son";
				}else {
					message += ", your daughter ";
				}
				
				message += student.getFirstname() + "Score is ";
				message += ",T " + totalMean + ",M " + mean + ",G " + grade; 
				
				accountName = accountDAO.getAccountById(accountId).getName();  
				
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

}
