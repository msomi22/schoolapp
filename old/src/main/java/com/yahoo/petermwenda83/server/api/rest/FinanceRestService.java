/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDescDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherFeeDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.Response;

/**
 * @author peter
 *
 */
public class FinanceRestService {

	private static FeeBreakdownDescDAO feeBreakdownDescDAO;
	private static FeeBreakdownDAO feeBreakdownDAO;
	private static TermFeeDAO termFeeDAO;
	private static OtherFeeDAO otherFeeDAO;

	static {
		feeBreakdownDescDAO = FeeBreakdownDescDAO.getInstance();
		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		otherFeeDAO = OtherFeeDAO.getInstance();
	}
	
	//TODO 

	/**
	 * 
	 * @param accountId
	 * @return
	 */
	 
	public Object getFeeBreakDown(String accountId) {

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
	/**
	 * 
	 * @param accountId
	 * @param term
	 * @param year
	 * @return
	 */
	public Object getTermFee(String accountId, String term, String year) {
		
		Response response = new Response();
		
		if(termFeeDAO.getFee(accountId, term, year) == null) {
			response.setMessage("error");
			response.setDescription("Term fee not found!");
			return response;
			
		}else {
			
			return termFeeDAO.getFee(accountId, term, year);
		}
		
	}
     /**
      * 
      * @param accountId
      * @return
      */
	public Object getTermFees(String accountId) {
		
		Response response = new Response();
		
		if(termFeeDAO.getTermFeeList(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Term fee not found!");
			return response;
			
		}else {
			return termFeeDAO.getTermFeeList(accountId);
			
		}
	}

	public Object putTermFee() {
		return null;
	}

	public Object updateTermFee() {
		return null;
	}

	//term other fee TODO
	/**
	 * 
	 * @param accountId
	 * @param term
	 * @param year
	 * @return
	 */
	public Object getOtherFee(String accountId, String term, String year) {
		
		Response response = new Response();
		
		if(otherFeeDAO.getOtherFeeList(accountId, term, year) == null) {
			response.setMessage("error");
			response.setDescription("Fee not found!");
			return response;
		}else {
			return otherFeeDAO.getOtherFeeList(accountId, term, year);
			
		}
		
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
