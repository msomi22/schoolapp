/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.finance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yahoo.petermwenda83.bean.money.FeeBreakdownDesc;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDescDAO;

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



	//validTerm








}
