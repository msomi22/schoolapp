/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class UpdateFee {
	
	private String accountId;
	private String studentId;
	private String paymentId;
	private String transactingStaffId;
	private String schoolSecret;
	private int previousAmount;
	private int correctAmount;

	/**
	 * 
	 */
	public UpdateFee() {
		accountId = "";
		studentId = "";
		paymentId = "";
		transactingStaffId = "";
		schoolSecret = "";
		previousAmount = 0;
		correctAmount = 0;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getTransactingStaffId() {
		return transactingStaffId;
	}

	public void setTransactingStaffId(String transactingStaffId) {
		this.transactingStaffId = transactingStaffId;
	}

	public String getSchoolSecret() {
		return schoolSecret;
	}

	public void setSchoolSecret(String schoolSecret) {
		this.schoolSecret = schoolSecret;
	}

	public int getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(int previousAmount) {
		this.previousAmount = previousAmount;
	}

	public int getCorrectAmount() {
		return correctAmount;
	}

	public void setCorrectAmount(int correctAmount) {
		this.correctAmount = correctAmount;
	}

	@Override
	public String toString() {
		return "UpdateFee [accountId=" + accountId + ", studentId=" + studentId + ", paymentId=" + paymentId
				+ ", transactingStaffId=" + transactingStaffId + ", schoolSecret=" + schoolSecret + ", previousAmount="
				+ previousAmount + ", correctAmount=" + correctAmount + "]";
	}

	

	
}
