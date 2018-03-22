/**
 * 
 */
package ke.co.qubintel.school.server.servlet.util.sms.core;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.bean.student.guardian.StudentParent;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.persistence.account.ApiCredentialDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
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
	private static SysConfigDAO sysConfigDAO;
	
	static {
		parentsDAO = ParentsDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		smsApiDAO = ApiCredentialDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
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

}
