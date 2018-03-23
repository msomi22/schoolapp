/**
 * 
 */
package ke.co.qubintel.school.server.persistence.account;

import java.util.List;

import ke.co.qubintel.school.server.bean.account.OutGoingSMS;

/**
 * @author peter
 *
 */
public interface SchoolOutGoingSMSDAO {
	
	public OutGoingSMS getOutGoingSMS(String accountId, String uuid); 
	
	public List<OutGoingSMS> getOutGoingSMSList(String accountId,String status, int startIndex, int endIndex); 
	
	public boolean putOutGoingSMS(OutGoingSMS outGoingSMS);
	
	public boolean updateOutGoingSMS(OutGoingSMS outGoingSMS);
	
	public boolean deleteOutGoingSMS(String accountId, String uuid);
	
	public boolean deleteOutGoingSMSByStatus(String accountId,String status);

}
