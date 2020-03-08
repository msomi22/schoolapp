/**
 * 
 */
package ke.co.qubintel.school.server.bean.money;

import java.sql.Timestamp;
import java.util.Date;

import ke.co.qubintel.school.server.bean.StorableBean;

/**
 * @author peter
 *
 */
public class StudentFee extends StorableBean{

	private String studentId;
	private int amountPaid;
	private String payMode;
	private String transactionId;
	private String paidHas;//boarders = 1, day = 0
	private String termPiad;
	private String yearPaid;
	private String transactingStaffId;
	private Timestamp datePaid;


	public StudentFee() {
		studentId = "";
		amountPaid = 0;
		payMode = "";
		transactionId = "";
		paidHas = "";
		termPiad = "";
		yearPaid = "";
		transactingStaffId = "";
		datePaid = new Timestamp(new Date().getTime());

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


	public String getTransactingStaffId() {
		return transactingStaffId;
	}


	public void setTransactingStaffId(String transactingStaffId) {
		this.transactingStaffId = transactingStaffId;
	}


	public Timestamp getDatePaid() {
		return datePaid;
	}


	public void setDatePaid(Timestamp datePaid) {
		this.datePaid = datePaid;
	}


	@Override
	public String toString() {
		return "StudentFee [studentId=" + studentId + ", amountPaid=" + amountPaid + ", payMode=" + payMode
				+ ", transactionId=" + transactionId + ", paidHas=" + paidHas + ", termPiad=" + termPiad + ", yearPaid="
				+ yearPaid + ", transactingStaffId=" + transactingStaffId + ", datePaid=" + datePaid + ", getUuid()="
				+ getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}


	/** 
	 * 
	 */
	private static final long serialVersionUID = -4526710616012197088L;

}
