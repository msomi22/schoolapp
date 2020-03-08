/**
 * 
 */
package ke.co.qubintel.school.server.persistence.money;

import java.util.List;

import ke.co.qubintel.school.server.bean.money.FeeBreakdownDesc;

/**
 * @author peter
 *
 */
public interface SchoolFeeBreakdownDescDAO {

	public FeeBreakdownDesc getFeeBreakdownDesc(String accountId, String uuid);
	
	public FeeBreakdownDesc getFeeBreakdownDesc(String accountId,String feeBreakdownId, String query);
	
	public List<FeeBreakdownDesc> getFeeBreakdownDescList(String accountId, String feeBreakdownId); 
	
	public List<FeeBreakdownDesc> findDuplicate(String accountId, String feeBreakdownId, String query); 
	
	public boolean putFeeBreakdownDesc(FeeBreakdownDesc feeBreakdownDesc);

	public boolean updateFeeBreakdownDesc(FeeBreakdownDesc feeBreakdownDesc);
	
	public boolean deleteFeeBreakdownDesc(String accountId, String uuid); 

}
