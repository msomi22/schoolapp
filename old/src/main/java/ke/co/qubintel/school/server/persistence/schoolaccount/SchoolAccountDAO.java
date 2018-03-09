/**
 * 
 */
package ke.co.qubintel.school.server.persistence.schoolaccount;

import java.util.List;

import ke.co.qubintel.school.server.bean.account.Account;

/**
 * @author peter
 *
 */
public interface SchoolAccountDAO {
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Account getAccountById(String uuid);
	/**
	 * 
	 * @param credentials
	 * @return
	 */
	public Account getAccount(String credentials,String isActive);
	
	
	public List<Account> findAccountDuplicate(String credentials);
	
	/**
	 * 
	 * @param school
	 * @return
	 */
	public Account getAccountByPassword(String uuid,String password);
	/**
	 * 
	 * @param school
	 * @return whether Account has been added successfully
	 */
    public boolean putAccount(Account account);
    /**
     * 
     * @param school
     * @return whether Account has been updated successfully
     */
    public boolean updateAccount(Account account);
   /**
    * 
    * @param uuid
    * @return
    */
   
    public boolean deleteAccount(String uuid);
    /**
     * 
     * @return List of all schools 
     */
    public List<Account> getAccounts();
   

}
