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
	 * @param Uuid
	 * @return
	 */
	public Account get(String Uuid);
	
	/**
	 * 
	 * @param Username
	 * @return the SchoolName
	 */
	public Account getSchoolByUsername(String Username);
	/**
	 * 
	 * @param mobile
	 * @return
	 */
	public Account getSchoolByPhone(String mobile);
	/**
	 * 
	 * @param email
	 * @return
	 */
	public Account getSchoolByEmail(String email);
	/**
	 * 
	 * @param schoolName
	 * @return
	 */
	public Account getSchoolByName(String schoolName);
	/**
	 * 
	 * @param school
	 * @return
	 */
	public Account getSchool(String Uuid,String password);
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
