package com.yahoo.petermwenda83.bean.money;

import com.yahoo.petermwenda83.bean.StorableBean;

public class Repeater extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3620812499809110603L;
	String studentUuid;
    double discount;

	public Repeater() {
		studentUuid = "";
		discount = 0;
	}
	
	
	/**
	 * @return the studentUuid
	 */
	public String getStudentUuid() {
		return studentUuid;
	}


	/**
	 * @param studentUuid the studentUuid to set
	 */
	public void setStudentUuid(String studentUuid) {
		this.studentUuid = studentUuid;
	}


	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}


	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Repeater");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(",studentUuid =");
		builder.append(studentUuid);
		builder.append(", discount =");
		builder.append(discount);
		builder.append("]");
		return builder.toString(); 
		}

}
