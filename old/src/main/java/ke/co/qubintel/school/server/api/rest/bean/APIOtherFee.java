/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class APIOtherFee {
	
	private String accountId;
	private String studentId;
	private String otherFeeId;
	private String term;

	/**
	 * 
	 */
	public APIOtherFee() {
		accountId = "";
		studentId = "";
		otherFeeId = "";
		term = "";
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

	public String getOtherFeeId() {
		return otherFeeId;
	}

	public void setOtherFeeId(String otherFeeId) {
		this.otherFeeId = otherFeeId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	@Override
	public String toString() {
		return "APIOtherFee [accountId=" + accountId + ", studentId=" + studentId + ", otherFeeId=" + otherFeeId
				+ ", term=" + term + "]";
	}

}
