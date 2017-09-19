/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.finance;

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













}
