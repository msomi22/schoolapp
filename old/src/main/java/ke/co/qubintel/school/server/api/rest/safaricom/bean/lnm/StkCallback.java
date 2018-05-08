package ke.co.qubintel.school.server.api.rest.safaricom.bean.lnm;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author peter
 *
 */
@XmlRootElement(name = "StkCallback") 
public class StkCallback {
	
	@JsonProperty
	private String MerchantRequestID;
	@JsonProperty
	private String CheckoutRequestID;
	@JsonProperty
	private int ResultCode;
	@JsonProperty
	private String ResultDesc;
	@JsonProperty
	private CallbackMetadata CallbackMetadata;

	public StkCallback() {
		MerchantRequestID = "";
		CheckoutRequestID = "";
		ResultCode = 0;
		ResultDesc = "";
		CallbackMetadata = new CallbackMetadata();
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
	 * @return the resultCode
	 */
	public int getResultCode() {
		return ResultCode;
	}

	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(int resultCode) {
		ResultCode = resultCode;
	}

	/**
	 * @return the resultDesc
	 */
	public String getResultDesc() {
		return ResultDesc;
	}

	/**
	 * @param resultDesc the resultDesc to set
	 */
	public void setResultDesc(String resultDesc) {
		ResultDesc = resultDesc;
	}

	/**
	 * @return the callbackMetadata
	 */
	public CallbackMetadata getCallbackMetadata() {
		return CallbackMetadata;
	}

	/**
	 * @param callbackMetadata the callbackMetadata to set
	 */
	public void setCallbackMetadata(CallbackMetadata callbackMetadata) {
		CallbackMetadata = callbackMetadata;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StkCallback [MerchantRequestID=" + MerchantRequestID + ", CheckoutRequestID=" + CheckoutRequestID
				+ ", ResultCode=" + ResultCode + ", ResultDesc=" + ResultDesc + ", CallbackMetadata=" + CallbackMetadata
				+ "]";
	}

}
