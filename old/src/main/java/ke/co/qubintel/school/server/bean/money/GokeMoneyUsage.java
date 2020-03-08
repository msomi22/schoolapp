package ke.co.qubintel.school.server.bean.money;

import java.sql.Timestamp;
import java.util.Date;

import ke.co.qubintel.school.server.bean.StorableBean;

public class GokeMoneyUsage extends StorableBean{

	private int numberOfStudents;
	private int  amountPerStudent;
	private int totalAmount;
	private int balance;
	private String  term;
	private String  year;
	private Timestamp dateAllocated;

	public GokeMoneyUsage() {
		numberOfStudents = 0;
		amountPerStudent = 0;
		totalAmount = 0;
		balance = 0;
		term = "";
		year = "";
		dateAllocated = new Timestamp(new Date().getTime());
	}

	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public int getAmountPerStudent() {
		return amountPerStudent;
	}

	public void setAmountPerStudent(int amountPerStudent) {
		this.amountPerStudent = amountPerStudent;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Timestamp getDateAllocated() {
		return dateAllocated;
	}

	public void setDateAllocated(Timestamp dateAllocated) {
		this.dateAllocated = dateAllocated;
	}

	@Override
	public String toString() {
		return "GokeMoneyUsage [numberOfStudents=" + numberOfStudents + ", amountPerStudent=" + amountPerStudent
				+ ", totalAmount=" + totalAmount + ", balance=" + balance + ", term=" + term + ", year=" + year
				+ ", dateAllocated=" + dateAllocated + "]";
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5370879663135932934L;

}
