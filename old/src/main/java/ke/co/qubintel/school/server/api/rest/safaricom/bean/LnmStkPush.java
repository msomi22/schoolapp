/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.safaricom.bean;

/**
 * @author peter
 *
 */
public class LnmStkPush {
	
	/** This is the shortcode of the organization initiating the request and expecting the payment.
	 */
	private String BusinessShortCode;
	/** This is the Base64-encoded value of the concatenation of the Shortcode + LNM Passkey + Timestamp
	    e.g. given the test values above, and using a timestamp of 20180409093002, the encoded password will be
	    MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTgwNDA5MDkzMDAy*/
	private String Password;
	/** This is the same Timestamp used in the encoding above, in the format YYYMMDDHHmmss. */
	private String Timestamp;
	/**
	 * The type of transaction being performed. These are the same values as the C2B command IDs 
	 * (CustomerPayBillOnline and CustomerBuyGoodsOnline) and the same rules apply here. 
	 * For now, only CustomerPayBillOnline is supported.
	 */
	private String TransactionType;
	private String Amount;
	/**
	 * The Debit party of the transaction, hereby the phone number of the customer.
	 */
	private String PartyA;
	/**
	 * The credit party of the transaction, hereby being the shortcode of the organization. This is the same value as the Business Shortcode
	 */
	private String PartyB;
	/**
	 * Same as PartyA.
	 */
	private String PhoneNumber;
	/**
	 * This is the endpoint where you want the results of the transaction delivered. Same rules for Register URL API callbacks apply
	 */
	private String CallBackURL;
	/**
	 * This is the value the customer would have put as the account number on their phone if they had performed the transaction via phone.
	 */
	private String AccountReference;
	/**
	 * Short description of the transaction. Optional, but element must be present.
       After sending a successful transaction, you can expect a response in the below format:
	 */
	private String TransactionDesc;

	/**
	 * 
	 */
	public LnmStkPush() {
		BusinessShortCode = "";
	    Password = "";
	    Timestamp = "";
	    TransactionType = "";
	    Amount = "";
	    PartyA = "";
	    PartyB = "";
	    PhoneNumber = "";
	    CallBackURL = "http://41.203.216.222:8080/school/webapi/account/sktresponse";
	    AccountReference = "";
	    TransactionDesc= "";
	}

	/**
	 * @return the businessShortCode
	 */
	public String getBusinessShortCode() {
		return BusinessShortCode;
	}

	/**
	 * @param businessShortCode the businessShortCode to set
	 */
	public void setBusinessShortCode(String businessShortCode) {
		BusinessShortCode = businessShortCode;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return Timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return TransactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return Amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		Amount = amount;
	}

	/**
	 * @return the partyA
	 */
	public String getPartyA() {
		return PartyA;
	}

	/**
	 * @param partyA the partyA to set
	 */
	public void setPartyA(String partyA) {
		PartyA = partyA;
	}

	/**
	 * @return the partyB
	 */
	public String getPartyB() {
		return PartyB;
	}

	/**
	 * @param partyB the partyB to set
	 */
	public void setPartyB(String partyB) {
		PartyB = partyB;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return PhoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	/**
	 * @return the callBackURL
	 */
	public String getCallBackURL() {
		return CallBackURL;
	}

	/**
	 * @param callBackURL the callBackURL to set
	 */
	public void setCallBackURL(String callBackURL) {
		CallBackURL = callBackURL;
	}

	/**
	 * @return the accountReference
	 */
	public String getAccountReference() {
		return AccountReference;
	}

	/**
	 * @param accountReference the accountReference to set
	 */
	public void setAccountReference(String accountReference) {
		AccountReference = accountReference;
	}

	/**
	 * @return the transactionDesc
	 */
	public String getTransactionDesc() {
		return TransactionDesc;
	}

	/**
	 * @param transactionDesc the transactionDesc to set
	 */
	public void setTransactionDesc(String transactionDesc) {
		TransactionDesc = transactionDesc;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LnmStkPush [BusinessShortCode=" + BusinessShortCode + ", Password=" + Password + ", Timestamp="
				+ Timestamp + ", TransactionType=" + TransactionType + ", Amount=" + Amount + ", PartyA=" + PartyA
				+ ", PartyB=" + PartyB + ", PhoneNumber=" + PhoneNumber + ", CallBackURL=" + CallBackURL
				+ ", AccountReference=" + AccountReference + ", TransactionDesc=" + TransactionDesc + "]";
	}

}
