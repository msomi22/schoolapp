/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import java.util.List;

import com.yahoo.petermwenda83.bean.money.FeeBreakdown;

/**
 * @author peter
 *
 */
public interface SchoolFeeBreakdownDAO {
	

	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @return
	 */
	public FeeBreakdown getFeeBreakdownById(String accountId, String uuid); 
	/**
	 * 
	 * @param accountId
	 * @param feeCategory
	 * @param term
	 * @param year
	 * @param status
	 * @return
	 */
	public FeeBreakdown getFeeBreakdown(String accountId, String feeCategory, String term, String year, String status); 
	
	
	public FeeBreakdown getFeeBreakdown(String accountId, String feeCategory, String term, String year); 
	
	/**
	 * 
	 * @param accountId
	 * @param feeCategory
	 * @return
	 */
	public FeeBreakdown getFeeBreakdown(String accountId, String feeCategory); 
	
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public List<FeeBreakdown> getFeeBreakdown(String accountId);  
	/**
	 * 
	 * @param feeBreakdown
	 * @return
	 */
	public boolean putFeeBreakdown(FeeBreakdown feeBreakdown);
	/**
	 * 
	 */
	public boolean updateFeeBreakdown(FeeBreakdown feeBreakdown);
	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @return
	 */
	public boolean deleteFeeBreakdown(String accountId, String uuid); 
	
	

}
