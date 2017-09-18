/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import java.util.List;

import com.yahoo.petermwenda83.bean.money.FeeBreakdownDesc;

/**
 * @author peter
 *
 */
public interface SchoolFeeBreakdownDescDAO {

	public FeeBreakdownDesc getFeeBreakdownDesc(String accountId, String uuid);
	
	public FeeBreakdownDesc getFeeBreakdownDesc(String accountId,String feeBreakdownId, String feeCode);
	
	public List<FeeBreakdownDesc> getFeeBreakdownDescList(String accountId, String feeBreakdownId); 
	
	public boolean putFeeBreakdownDesc(FeeBreakdownDesc feeBreakdownDesc);

	public boolean updateFeeBreakdownDesc(FeeBreakdownDesc feeBreakdownDesc);
	
	public boolean deleteFeeBreakdownDesc(String accountId, String uuid); 

}
