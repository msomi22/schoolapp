/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import com.yahoo.petermwenda83.bean.otherfee.OtherFee;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDescDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.Response;
import com.yahoo.petermwenda83.server.servlet.finance.FeeConstants;

/**
 * @author peter
 *
 */
public class FinanceRestService {

	private static FeeBreakdownDescDAO feeBreakdownDescDAO;
	private static FeeBreakdownDAO feeBreakdownDAO;
	private static TermFeeDAO termFeeDAO;
	private static OtherFeeDAO otherFeeDAO;
	private static AccountDAO accountDAO;

	static {
		feeBreakdownDescDAO = FeeBreakdownDescDAO.getInstance();
		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		otherFeeDAO = OtherFeeDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
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
	 * @param year
	 * @return
	 */

	public Object getTermFeePerYear(String accountId,String year) {

		Response response = new Response();

		if(termFeeDAO.getTermFeeList(accountId, year) == null) {
			response.setMessage("error");
			response.setDescription("Term fee not found!");
			return response;

		}else {

			return termFeeDAO.getTermFeeList(accountId, year);
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

	/**
	 * 
	 * @param otherFee
	 * @return
	 */
	public Object putOtherFee(OtherFee otherFee) {
		
		Response response = new Response();

		if(otherFee.getDescription().length() < 3) { 
			response.setMessage("error");
			response.setDescription("Invalid Description.");
			return response;

		}else if(!FeeConstants.validFee(otherFee.getAmount())) {
			response.setMessage("error");
			response.setDescription("Invalid Amount.");
			return response;

		}else if(!FeeConstants.validTerm(otherFee.getTerm())) {
			response.setMessage("error");
			response.setDescription("Invalid Term.");
			return response;

		}else if(!FeeConstants.validYear(otherFee.getYear())) {
			response.setMessage("error");
			response.setDescription("Invalid Year.");
			return response;

		}else if(accountDAO.getAccountById(otherFee.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response;

		}else if(otherFeeDAO.queryOtherFee(otherFee.getAccountId(), otherFee.getDescription(), otherFee.getTerm(), otherFee.getYear()) != null) {
			response.setMessage("error");
			response.setDescription("Description exist!");
			return response;

		}else {
			
			otherFee.setUuid(new OtherFee().getUuid()); 

			if(otherFeeDAO.putOtherFee(otherFee)) {
				
				response.setMessage("sucess");
				response.setDescription("Fee added successfully."); 
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Contact Admin please.");
				return response;

			}

		}

	}
	/**
	 * 
	 * @param otherFee
	 * @return
	 */
	public Object updateOtherFee(OtherFee otherFee) {
		
		Response response = new Response();

		if(otherFee.getDescription().length() < 3) {
			response.setMessage("error");
			response.setDescription("Invalid Description.");
			return response;

		}else if(!FeeConstants.validFee(otherFee.getAmount())) {
			response.setMessage("error");
			response.setDescription("Invalid Amount.");
			return response;

		}else if(!FeeConstants.validTerm(otherFee.getTerm())) {
			response.setMessage("error");
			response.setDescription("Invalid Term.");
			return response;

		}else if(!FeeConstants.validYear(otherFee.getYear())) {
			response.setMessage("error");
			response.setDescription("Invalid Year.");
			return response;

		}else if(accountDAO.getAccountById(otherFee.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response;

		}else if(otherFeeDAO.queryOtherFee(otherFee.getAccountId(), otherFee.getTerm()) == null) {
			response.setMessage("error");
			response.setDescription("Term not found!");
			return response;

		}else if(otherFeeDAO.queryOtherFee(otherFee.getAccountId(), otherFee.getYear()) == null) {
			response.setMessage("error");
			response.setDescription("Year not found!");
			return response;

		}else if(hasDuplicate(otherFee)) {
			response.setMessage("error");
			response.setDescription("No duplicates!");
			return response;

		}else {
			
			if(otherFeeDAO.updateOtherFee(otherFee)) {
				response.setMessage("sucess");
				response.setDescription("Fee updated successfully."); 
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Contact Admin please.");
				return response;

			}

		}
	}

	/**
	 * 
	 * @param otherFee
	 * @return
	 */
	private boolean hasDuplicate(OtherFee otherFee) {
		
		if(otherFeeDAO.findDuplicate(otherFee.getAccountId(), otherFee.getDescription(), otherFee.getTerm(), otherFee.getYear()).size() == 1) {
			return false;
		}else {
			return true;
		}
		
	}

	
}
