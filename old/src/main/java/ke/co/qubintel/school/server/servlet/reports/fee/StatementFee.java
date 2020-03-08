/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.servlet.reports.fee;

import java.sql.Timestamp;

/**
 * @author peter
 *
 */
public class StatementFee {
	
	private int boarderAmount;
	private int dayAmount;
	private int amountPaid;
	private String payMode;
	private String transactionId;
	private String paidHas;
	private Timestamp datePaid;

	/**
	 * 
	 */
	public StatementFee() {
		boarderAmount = 0;
		dayAmount = 0;
		amountPaid = 0;
		payMode = "";
		transactionId = "";
		paidHas = "";
		datePaid = null;
	}

	public int getBoarderAmount() {
		return boarderAmount;
	}

	public void setBoarderAmount(int boarderAmount) {
		this.boarderAmount = boarderAmount;
	}

	public int getDayAmount() {
		return dayAmount;
	}

	public void setDayAmount(int dayAmount) {
		this.dayAmount = dayAmount;
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

	public Timestamp getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(Timestamp datePaid) {
		this.datePaid = datePaid;
	}

	@Override
	public String toString() {
		return "StatementFee [boarderAmount=" + boarderAmount + ", dayAmount=" + dayAmount + ", amountPaid="
				+ amountPaid + ", payMode=" + payMode + ", transactionId=" + transactionId + ", paidHas=" + paidHas
				+ ", datePaid=" + datePaid + "]";
	}

	

}
