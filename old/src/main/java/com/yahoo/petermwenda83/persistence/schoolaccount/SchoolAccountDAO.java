/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import java.util.List;

import com.yahoo.petermwenda83.bean.account.Account;

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
	public Account getAccount(String credentials);
	
	/**
	 * 
	 * @param school
	 * @return
	 */
	public Account getSchool(String uuid,String password);
	/**
	 * 
	 * @param school
	 * @return whether Account has been added successfully
	 */
    public boolean put(Account school);
    /**
     * 
     * @param school
     * @return whether Account has been updated successfully
     */
    public boolean update(Account school);
    /**
     * 
     * @param school
     * @return whether Account has been deleted successfully
     */
    public boolean delete(Account school);
    /**
     * 
     * @return List of all schools 
     */
    public List<Account> getAllSchools();
    
    

}
