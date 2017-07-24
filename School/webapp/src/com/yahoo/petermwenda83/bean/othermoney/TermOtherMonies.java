package com.yahoo.petermwenda83.bean.othermoney;

import com.yahoo.petermwenda83.bean.StorableBean;

public class TermOtherMonies extends StorableBean{
	
	/** com.yahoo.petermwenda83.bean.othermoney.TermOtherMonies
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	   String SchoolAccountUuid;
	   String otherstypeUuid;
	   double amount;

	public TermOtherMonies() {
		SchoolAccountUuid = "";
		otherstypeUuid = "";
		amount =0;
	}

	
	

	/**
	 * @return the schoolAccountUuid
	 */
	public String getSchoolAccountUuid() {
		return SchoolAccountUuid;
	}




	/**
	 * @param schoolAccountUuid the schoolAccountUuid to set
	 */
	public void setSchoolAccountUuid(String schoolAccountUuid) {
		SchoolAccountUuid = schoolAccountUuid;
	}




	/**
	 * @return the otherstypeUuid
	 */
	public String getOtherstypeUuid() {
		return otherstypeUuid;
	}




	/**
	 * @param otherstypeUuid the otherstypeUuid to set
	 */
	public void setOtherstypeUuid(String otherstypeUuid) {
		this.otherstypeUuid = otherstypeUuid;
	}




	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}




	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}




	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Term Other Monies");
		builder.append(",[getUuid() = ");
		builder.append(getUuid());
		builder.append(", SchoolAccountUuid =");
		builder.append(SchoolAccountUuid); 
		builder.append(", otherstypeUuid =");
		builder.append(otherstypeUuid); 
		builder.append(", amount =");
		builder.append(amount); 
		builder.append("]");
		return builder.toString(); 
		}
}
