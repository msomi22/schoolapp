/**
 * 
 */
package ke.co.qubintel.school.server.persistence.money;

import java.util.List;

import ke.co.qubintel.school.server.bean.money.Deposit;
import ke.co.qubintel.school.server.bean.money.PocketMoney;
import ke.co.qubintel.school.server.bean.money.Withdraw;

/**
 * @author peter
 *
 */
public interface SchoolPMoneyDAO {
	
	public PocketMoney getPocketMoney(String accountId,String studentId);
	
	public boolean studentExist(String accountId,String studentId);
	
	public boolean hasBalance(String accountId, String studentId, double amount);
	
	public boolean addBalance(PocketMoney pocketMoney, String accountId, String studentId, double amount);
	
	public boolean deductBalance(PocketMoney pocketMoney, String accountId, String studentId, double amount);
	 
	public List<Withdraw> getWithdrawList(String accountId,String studentId, int startIndex , int endIndex);
	 
	public List<Deposit> getDepositList(String accountId,String studentId, int startIndex , int endIndex);
	

}
