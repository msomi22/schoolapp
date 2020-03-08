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
