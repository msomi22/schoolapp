/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.safaricom.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */
@XmlRootElement(name = "AccountBalance")  //only needed if we also want to generate XML 
public class AccountBalance {
	
	
	/**
	 * The name of Initiator to initiating  the request	
	 * Alpha-Numeric	
	 * This is the credential/username used to authenticate the transaction request 
	 */
	private String Initiator;
	/**
	 * Encrypted Credential of user getting transaction amount	
	 * String	
	 * Encrypted password for the initiator to autheticate the transaction request
	 */
	private String SecurityCredential;
	/**
	 * Takes only 'AccountBalance' CommandID
         String	
         AccountBalance
	 */
	private String CommandID;
	/**
	 * Type of organization receiving the transaction	
	 *  Numeric	
        XXXXXX
	 */
	private String PartyA;
	/**
	 * Type of organization receiving the transaction
        Numeric	
		1 – MSISDN
		2 – Till Number
		4 – Organization short code
	 */
	private String IdentifierType;
	/**
	 * Comments that are sent along with the transaction.	
	 * String	
	 * sequence of characters up to 100
	 */
	private String Remarks;
	/**
	 *  The path that stores information of 'time out transaction'
	 * 	URL	
	 *  https://ip or domain:port/path
	 */
	private String QueueTimeOutURL;
	/**
	 * The path that stores information of transaction 	
	 * URL	
	 * https://ip or domain:port/path
	 * 
	 */
	private String ResultURL;
	
	/**
	 * 
	 */
	public AccountBalance() {
		Initiator = "";
		SecurityCredential = "";
		CommandID = "";
		PartyA = "";
		IdentifierType = "";
		Remarks = "";
		QueueTimeOutURL = "";
		ResultURL = "";
	}

	public String getInitiator() {
		return Initiator;
	}

	public void setInitiator(String initiator) {
		Initiator = initiator;
	}

	public String getSecurityCredential() {
		return SecurityCredential;
	}

	public void setSecurityCredential(String securityCredential) {
		SecurityCredential = securityCredential;
	}

	public String getCommandID() {
		return CommandID;
	}

	public void setCommandID(String commandID) {
		CommandID = commandID;
	}

	public String getPartyA() {
		return PartyA;
	}

	public void setPartyA(String partyA) {
		PartyA = partyA;
	}

	public String getIdentifierType() {
		return IdentifierType;
	}

	public void setIdentifierType(String identifierType) {
		IdentifierType = identifierType;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public String getQueueTimeOutURL() {
		return QueueTimeOutURL;
	}

	public void setQueueTimeOutURL(String queueTimeOutURL) {
		QueueTimeOutURL = queueTimeOutURL;
	}

	public String getResultURL() {
		return ResultURL;
	}

	public void setResultURL(String resultURL) {
		ResultURL = resultURL;
	}

	@Override
	public String toString() {
		return "AccountBalance [Initiator=" + Initiator + ", SecurityCredential=" + SecurityCredential + ", CommandID="
				+ CommandID + ", PartyA=" + PartyA + ", IdentifierType=" + IdentifierType + ", Remarks=" + Remarks
				+ ", QueueTimeOutURL=" + QueueTimeOutURL + ", ResultURL=" + ResultURL + "]";
	}

	
}
