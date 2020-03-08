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
package ke.co.qubintel.school.server.servlet.reports;

import ke.co.qubintel.school.server.servlet.util.PeterMid;

/**
 * @author peter
 *
 */
public class TestMM {

	/**
	 * 
	 */
	public TestMM() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// 54 41 20
		
		int num1 = 80;
		int num2 = 80;
		int num3 = 30;
		
		double total = (double)(num1 + num2) / ReportUtil.PAPER_1_2_DIVISOR * ReportUtil.PAPER_1_2_CONSTANT + num3; 
		
		System.out.println(PeterMid.computeMax(40.0, 0.1, 0.2)); 

	}

}
