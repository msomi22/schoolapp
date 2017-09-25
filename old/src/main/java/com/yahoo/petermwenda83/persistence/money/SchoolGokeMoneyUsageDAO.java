/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import java.util.List;

import com.yahoo.petermwenda83.bean.money.GokeMoneyUsage;

/**
 * @author peter
 *
 */
public interface SchoolGokeMoneyUsageDAO {
	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @return
	 */
	public GokeMoneyUsage getGokeMoneyUsage(String accountId, String uuid);
	/**
	 * 
	 * @param accountId
	 * @param term
	 * @param year
	 * @return
	 */
	public GokeMoneyUsage getGokeMoneyUsage(String accountId, String  term, String  year);
	/**
	 * 
	 * @param accountId
	 * @param year
	 * @return
	 */
	public List<GokeMoneyUsage> getGokeMoneyUsageList(String accountId, String  year); 
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public List<GokeMoneyUsage> getGokeMoneyUsageList(String accountId); 
	/**
	 * 
	 * @param gokeMoneyUsage
	 * @return
	 */
	public boolean putGokeMoneyUsage(GokeMoneyUsage gokeMoneyUsage);
	/**
	 * 
	 * @param gokeMoneyUsage
	 * @return
	 */
	public boolean updateGokeMoneyUsage(GokeMoneyUsage gokeMoneyUsage);

}
