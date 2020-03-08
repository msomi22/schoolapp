/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class APIStudentFee {
	

	private String studentId;
	private int amountPaid;
	private String payMode;
	private String transactionId;
	private String paidHas;//boarders = 1, day = 0
	private String termPiad;
	private String yearPaid;
	private String datePaid;

	/**
	 * 
	 */
	public APIStudentFee() {
		studentId = "";
		amountPaid = 0;
		payMode = "";
		transactionId = "";
		paidHas = "";
		termPiad = "";
		yearPaid = "";
		datePaid = "";
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(int amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPaidHas() {
		return paidHas;
	}

	public void setPaidHas(String paidHas) {
		this.paidHas = paidHas;
	}

	public String getTermPiad() {
		return termPiad;
	}

	public void setTermPiad(String termPiad) {
		this.termPiad = termPiad;
	}

	public String getYearPaid() {
		return yearPaid;
	}

	public void setYearPaid(String yearPaid) {
		this.yearPaid = yearPaid;
	}

	public String getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(String datePaid) {
		this.datePaid = datePaid;
	}

	@Override
	public String toString() {
		return "APIStudentFee [studentId=" + studentId + ", amountPaid=" + amountPaid + ", payMode=" + payMode
				+ ", transactionId=" + transactionId + ", paidHas=" + paidHas + ", termPiad=" + termPiad + ", yearPaid="
				+ yearPaid + ", datePaid=" + datePaid + "]";
	}

}
