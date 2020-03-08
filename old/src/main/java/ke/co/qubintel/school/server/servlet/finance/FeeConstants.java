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
package ke.co.qubintel.school.server.servlet.finance;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.bean.money.FeeBreakdownDesc;
import ke.co.qubintel.school.server.persistence.money.FeeBreakdownDescDAO;

/**
 * @author peter
 *
 */
public class FeeConstants {

	private static FeeBreakdownDescDAO feeBreakdownDescDAO;

	static{
		feeBreakdownDescDAO = FeeBreakdownDescDAO.getInstance();
	}

	public static final String GVMT_MONEY_CODE = "GO_KE";
	public static final String GVMT_MONEY_STATUS_ACTIVE = "1";
	public static final String GVMT_MONEY_STATUS_INACTIVE = "0"; 

	/**
	 * 
	 * @param accountId
	 * @param feeBreakdownId
	 * @return
	 */
	public static double getGoKeFee(String accountId, String feeBreakdownId) {

		double gokeTotal = 0; 
		if(feeBreakdownDescDAO.getFeeBreakdownDescList(accountId,feeBreakdownId) != null) {
			List<FeeBreakdownDesc> feeBreakdownDescList = feeBreakdownDescDAO.getFeeBreakdownDescList(accountId,feeBreakdownId);
			for(FeeBreakdownDesc gokefee : feeBreakdownDescList) {
				gokeTotal += gokefee.getAmount();
			}
		}
		return gokeTotal;
	}
	
	/**
	 * 
	 * @param amount
	 * @return
	 */
	public static String formatFee(int amount) {
		Locale locale = new Locale("en","KE"); 
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		String formated = nf.format(amount); 
		return formated;
	}
	
	



	/**
	 * 
	 * @param amount
	 * @return
	 */
	public static boolean validFee(int amount) {
		boolean valid = true;

		if(amount <= 0) {
			valid = false;
		}

		if(amount > 100000) { 
			valid = false;
		}
		

		return valid;
	}
	
	public static boolean validGoKeFee(int amount) {
		boolean valid = true;

		if(amount <= 0) {
			valid = false;
		}

		//+2 147 483 647
		// 100,000,000
		if(amount > 100000000) { 
			valid = false;
		}

		return valid;
	}


	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean validTerm(String term) {
		String[] allowed = {"1","2","3"};
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);
		if(allowedList.contains(term)) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 
	 * @param year
	 * @return
	 */
	public static boolean validYear(String year) {

		if(year.length() != 4) {
			return false;
		}else {
			return true;
		}
	}


	/**
	 * 
	 * @param status
	 * @return
	 */
	public static boolean validStatus(String status) {
		String[] allowed = {"1","0"}; 
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);
		if(allowedList.contains(status)) {
			return true;
		}else {
			return false;
		}
	}








}
