package ke.co.qubintel.school.server.api.rest.safaricom.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * @author peter
 *
 */
@XmlRootElement(name = "VCResponse") 
public class VCResponse{
	
	@JsonProperty
	private String TransactionType;
	@JsonProperty
	private String TransID;
	@JsonProperty
	private String TransTime;
	@JsonProperty
	private String TransAmount;
	@JsonProperty
	private String BusinessShortCode;
	@JsonProperty
	private String BillRefNumber;
	@JsonProperty
	private String InvoiceNumber;
	@JsonProperty
	private String OrgAccountBalance;
	@JsonProperty
	private String ThirdPartyTransID;
	@JsonProperty
	private String MSISDN;
	@JsonProperty
	private String FirstName;
	@JsonProperty
	private String MiddleName;
	@JsonProperty
	private String LastName;

	public VCResponse() {
		TransactionType = "";
		TransID = "";
		TransTime = "";
		TransAmount = "";
		BusinessShortCode = "";
		BillRefNumber = "";
		InvoiceNumber = "";
		OrgAccountBalance = "";
		ThirdPartyTransID = "";
		MSISDN = "";
		FirstName = "";
		MiddleName = "";
		LastName = "";

	}

	public String getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	public String getTransID() {
		return TransID;
	}

	public void setTransID(String transID) {
		TransID = transID;
	}

	public String getTransTime() {
		return TransTime;
	}

	public void setTransTime(String transTime) {
		TransTime = transTime;
	}

	public String getTransAmount() {
		return TransAmount;
	}

	public void setTransAmount(String transAmount) {
		TransAmount = transAmount;
	}

	public String getBusinessShortCode() {
		return BusinessShortCode;
	}

	public void setBusinessShortCode(String businessShortCode) {
		BusinessShortCode = businessShortCode;
	}

	public String getBillRefNumber() {
		return BillRefNumber;
	}

	public void setBillRefNumber(String billRefNumber) {
		BillRefNumber = billRefNumber;
	}

	public String getInvoiceNumber() {
		return InvoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}

	public String getOrgAccountBalance() {
		return OrgAccountBalance;
	}

	public void setOrgAccountBalance(String orgAccountBalance) {
		OrgAccountBalance = orgAccountBalance;
	}

	public String getThirdPartyTransID() {
		return ThirdPartyTransID;
	}

	public void setThirdPartyTransID(String thirdPartyTransID) {
		ThirdPartyTransID = thirdPartyTransID;
	}

	public String getMSISDN() {
		return MSISDN;
	}

	public void setMSISDN(String mSISDN) {
		MSISDN = mSISDN;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getMiddleName() {
		return MiddleName;
	}

	public void setMiddleName(String middleName) {
		MiddleName = middleName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	@Override
	public String toString() {
		return "VCResponse [TransactionType=" + TransactionType + ", TransID=" + TransID + ", TransTime=" + TransTime
				+ ", TransAmount=" + TransAmount + ", BusinessShortCode=" + BusinessShortCode + ", BillRefNumber="
				+ BillRefNumber + ", InvoiceNumber=" + InvoiceNumber + ", OrgAccountBalance=" + OrgAccountBalance
				+ ", ThirdPartyTransID=" + ThirdPartyTransID + ", MSISDN=" + MSISDN + ", FirstName=" + FirstName
				+ ", MiddleName=" + MiddleName + ", LastName=" + LastName + "]";
	}

	
	
}