/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import com.yahoo.petermwenda83.bean.account.SmsApi;

/**
 * @author peter
 *
 */
public interface SchoolSmsApiDAO {
	/**
	 * 
	 * @param schoolAccountUuid
	 * @return
	 */
	public SmsApi getSmsApi(String  schoolAccountUuid);
	/**
	 * 
	 * @param smsApi
	 * @return
	 */
	public boolean putSmsApi(SmsApi smsApi);
	/**
	 * 
	 * @param smsApi
	 * @return
	 */
	public boolean updateSmsApi(SmsApi smsApi);

}
