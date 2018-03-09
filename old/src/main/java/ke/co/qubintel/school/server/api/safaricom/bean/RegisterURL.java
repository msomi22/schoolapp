/**
 * 
 */
package ke.co.qubintel.school.server.api.safaricom.bean;

/**
 * 
 * @author peter
 *
 */
public class RegisterURL{
	private String ShortCode;
	private String ResponseType;
	private String ConfirmationURL;
	private String ValidationURL;
	
	public RegisterURL(){
		ShortCode = "600321";
		ResponseType = "Completed";
		ConfirmationURL = "http://41.203.216.222:8080/school/webapi/account/confirmation";
		ValidationURL = "http://41.203.216.222:8080/school/webapi/account/validation";
	}

	public String getShortCode() {
		return ShortCode;
	}

	public void setShortCode(String shortCode) {
		ShortCode = shortCode;
	}

	public String getResponseType() {
		return ResponseType;
	}

	public void setResponseType(String responseType) {
		ResponseType = responseType;
	}

	public String getConfirmationURL() {
		return ConfirmationURL;
	}

	public void setConfirmationURL(String confirmationURL) {
		ConfirmationURL = confirmationURL;
	}

	public String getValidationURL() {
		return ValidationURL;
	}

	public void setValidationURL(String validationURL) {
		ValidationURL = validationURL;
	}

	@Override
	public String toString() {
		return "RegisterURL [ShortCode=" + ShortCode + ", ResponseType=" + ResponseType
				+ ", ConfirmationURL=" + ConfirmationURL + ", ValidationURL=" + ValidationURL + "]";
	}
	
}




