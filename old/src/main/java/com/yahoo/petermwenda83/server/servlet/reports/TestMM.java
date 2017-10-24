/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import com.yahoo.petermwenda83.server.servlet.util.PeterMid;

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
