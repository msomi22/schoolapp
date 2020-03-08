/**
 * 
 */
package ke.co.qubintel.school.server.persistence.othermoney;

import java.util.List;

import ke.co.qubintel.school.server.bean.otherfee.RevertedMoney;

/**
 * @author peter
 *
 */
public interface SchoolRevertedMoneyDAO {
	
	public RevertedMoney getRevertedMoney(String accountId, String studentId, String uuid);
	
	public boolean putRevertedMoney(RevertedMoney revertedMoney);
	
	public boolean deleteRevertedMoney(String accountId, String studentId, String uuid);
	
	public List<RevertedMoney> getRevertedMoneyList(String studentId, int startIndex, int endIndex);
	
	public List<RevertedMoney> getRevertedMoneyList(String accountId,String studentId);

}
