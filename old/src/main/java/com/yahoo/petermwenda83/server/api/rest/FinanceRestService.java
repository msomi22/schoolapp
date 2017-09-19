/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDescDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.Response;

/**
 * @author peter
 *
 */
public class FinanceRestService {

	private static FeeBreakdownDescDAO feeBreakdownDescDAO;
	private static FeeBreakdownDAO feeBreakdownDAO;

	static {
		feeBreakdownDescDAO = FeeBreakdownDescDAO.getInstance();
		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
	}
	
	//TODO 

	/**
	 * 
	 * @param accountId
	 * @param feeBreakdownId
	 * @return
	 */
	public Object getFeeBreakDown(String accountId, String feeBreakdownId) {

		Response response = new Response();

		if(feeBreakdownDAO.getFeeBreakdown(accountId) == null) {
			//error
			response.setMessage("error");
			response.setDescription("Fee breakdown List not found!");
		}else {

			return feeBreakdownDAO.getFeeBreakdown(accountId);

		}

		return response;
	}

	/**
	 * 
	 * @param accountId
	 * @param feeBreakdownId
	 * @return
	 */
	public Object getGoKeMoney(String accountId, String feeBreakdownId) {

		Response response = new Response();

		if(feeBreakdownDescDAO.getFeeBreakdownDescList(accountId, feeBreakdownId) == null) {
			//error
			response.setMessage("error");
			response.setDescription("GoKe Fee breakdown not found!");
		}else {

			return feeBreakdownDescDAO.getFeeBreakdownDescList(accountId, feeBreakdownId);

		}

		return response;
	}

	




	//term fee TODO
	public Object getTermFee(String term, String year) {
		return null;
	}

	public Object getTermFees(String accountId) {
		return null;
	}

	public Object putTermFee() {
		return null;
	}

	public Object updateTermFee() {
		return null;
	}

	//term other fee TODO
	public Object getOtherFee(String term, String year) {
		return null;
	}

	public Object getOtherFees(String accountId) {
		return null;
	}

	public Object putOtherFee() {
		return null;
	}

	public Object updateOtherFee() {
		return null;
	}

	public Object deleteOtherFee() {
		return null;
	}

}
