/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import java.util.List;

import com.yahoo.petermwenda83.bean.account.IncomingSMS;

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
