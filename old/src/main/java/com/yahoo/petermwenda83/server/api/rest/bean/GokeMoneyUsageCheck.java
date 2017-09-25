/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class GokeMoneyUsageCheck{
	
	private int numberOfStudents;
	private int totalAmount;
	private int expectedAmountPerStudent;
	private int amountPerStudent;
	private int balance;
	private String  term;
	private String  year;

	public GokeMoneyUsageCheck() {
		numberOfStudents = 0;
		totalAmount = 0;
		expectedAmountPerStudent = 0;
		amountPerStudent = 0;
		balance = 0;
		term = "";
		year = "";
	}

	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getExpectedAmountPerStudent() {
		return expectedAmountPerStudent;
	}

	public void setExpectedAmountPerStudent(int expectedAmountPerStudent) {
		this.expectedAmountPerStudent = expectedAmountPerStudent;
	}

	public int getAmountPerStudent() {
		return amountPerStudent;
	}

	public void setAmountPerStudent(int amountPerStudent) {
		this.amountPerStudent = amountPerStudent;
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

	@Override
	public String toString() {
		return "GokeMoneyUsageCheck [numberOfStudents=" + numberOfStudents + ", totalAmount=" + totalAmount
				+ ", expectedAmountPerStudent=" + expectedAmountPerStudent + ", amountPerStudent=" + amountPerStudent
				+ ", balance=" + balance + ", term=" + term + ", year=" + year + "]";
	}
	

}