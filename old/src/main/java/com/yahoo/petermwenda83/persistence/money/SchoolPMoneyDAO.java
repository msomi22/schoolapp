/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import java.util.List;

import com.yahoo.petermwenda83.bean.money.Deposit;
import com.yahoo.petermwenda83.bean.money.PocketMoney;
import com.yahoo.petermwenda83.bean.money.Withdraw;

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
