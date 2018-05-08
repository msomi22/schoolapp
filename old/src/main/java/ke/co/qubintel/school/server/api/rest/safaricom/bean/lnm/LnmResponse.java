/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.safaricom.bean.lnm;

/**
 * @author peter
 *
 */
public class LnmResponse {
	
	private String CustomerMessage;
    private String ResponseCode;
    private String CheckoutRequestID;
    private String ResponseDescription;
    private String MerchantRequestID;

	/**
	 * 
	 */
	public LnmResponse() {
		CustomerMessage = "";
		ResponseCode = "";
		CheckoutRequestID = "";
		ResponseDescription = "";
		MerchantRequestID = "";
	}

	/**
	 * @return the customerMessage
	 */
	public String getCustomerMessage() {
		return CustomerMessage;
	}

	/**
	 * @param customerMessage the customerMessage to set
	 */
	public void setCustomerMessage(String customerMessage) {
		CustomerMessage = customerMessage;
	}

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return ResponseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
	}

	/**
	 * @return the checkoutRequestID
	 */
	public String getCheckoutRequestID() {
		return CheckoutRequestID;
	}

	/**
	 * @param checkoutRequestID the checkoutRequestID to set
	 */
	public void setCheckoutRequestID(String checkoutRequestID) {
		CheckoutRequestID = checkoutRequestID;
	}

	/**
	 * @return the responseDescription
	 */
	public String getResponseDescription() {
		return ResponseDescription;
	}

	/**
	 * @param responseDescription the responseDescription to set
	 */
	public void setResponseDescription(String responseDescription) {
		ResponseDescription = responseDescription;
	}

	/**
	 * @return the merchantRequestID
	 */
	public String getMerchantRequestID() {
		return MerchantRequestID;
	}

	/**
	 * @param merchantRequestID the merchantRequestID to set
	 */
	public void setMerchantRequestID(String merchantRequestID) {
		MerchantRequestID = merchantRequestID;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LnmResponse [CustomerMessage=" + CustomerMessage + ", ResponseCode=" + ResponseCode
				+ ", CheckoutRequestID=" + CheckoutRequestID + ", ResponseDescription=" + ResponseDescription
				+ ", MerchantRequestID=" + MerchantRequestID + "]";
	}

}
