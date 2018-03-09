/**
 * 
 */
package ke.co.qubintel.school.server.api.safaricom.bean;
/**
 * 
 * @author peter
 *
 */
public class SimulateRequest{
	private String ShortCode;
	private String CommandID;
	private String Amount;
	private String Msisdn;
	private String BillRefNumber;
	
	public SimulateRequest() {
		ShortCode = "600321";
	    CommandID = "CustomerPayBillOnline";
	    Amount = "1000";
	    Msisdn = "254708374149";
	    BillRefNumber = "xxx";
	}

	public String getShortCode() {
		return ShortCode;
	}

	public void setShortCode(String shortCode) {
		ShortCode = shortCode;
	}

	public String getCommandID() {
		return CommandID;
	}

	public void setCommandID(String commandID) {
		CommandID = commandID;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getMsisdn() {
		return Msisdn;
	}

	public void setMsisdn(String msisdn) {
		Msisdn = msisdn;
	}

	public String getBillRefNumber() {
		return BillRefNumber;
	}

	public void setBillRefNumber(String billRefNumber) {
		BillRefNumber = billRefNumber;
	}

	@Override
	public String toString() {
		return "SimulateRequest [ShortCode=" + ShortCode + ", CommandID=" + CommandID + ", Amount=" + Amount
				+ ", Msisdn=" + Msisdn + ", BillRefNumber=" + BillRefNumber + "]";
	}
	
}



