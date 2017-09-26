/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.fee;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peter
 *
 */
public class StatementObject {
	
	private List<StatementFee> statementFeeList;
	private List<StatementOtherFee> statementOtherFeeList;

	/**
	 * 
	 */
	public StatementObject() {
		statementFeeList = new ArrayList<>();
		statementOtherFeeList = new ArrayList<>();
		
	}

	public List<StatementFee> getStatementFeeList() {
		return statementFeeList;
	}

	public void setStatementFeeList(List<StatementFee> statementFeeList) {
		this.statementFeeList = statementFeeList;
	}

	public List<StatementOtherFee> getStatementOtherFeeList() {
		return statementOtherFeeList;
	}

	public void setStatementOtherFeeList(List<StatementOtherFee> statementOtherFeeList) {
		this.statementOtherFeeList = statementOtherFeeList;
	}

	@Override
	public String toString() {
		return "StatementObject [statementFeeList=" + statementFeeList + ", statementOtherFeeList="
				+ statementOtherFeeList + "]";
	}


}
