/**
 * 
 */
package ke.co.qubintel.school.server.persistence.account;

import java.util.List;

import ke.co.qubintel.school.server.bean.account.IncomingSMS;

/**
 * @author peter
 *
 */
public interface SchoolIncomingSMSDAO {

	public IncomingSMS getIncomingSMS(String accountId, String uuid); 

	public List<IncomingSMS> getIncomingSMSList(String accountId, int startIndex, int endIndex); 

	public boolean putIncomingSMS(IncomingSMS incomingSMS);

	public boolean deleteIncomingSMS(String accountId, String uuid);

	public boolean deleteIncomingSMS(String accountId);

}
