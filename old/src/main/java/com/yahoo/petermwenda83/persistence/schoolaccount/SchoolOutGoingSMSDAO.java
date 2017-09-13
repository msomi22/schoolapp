/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import java.util.List;

import com.yahoo.petermwenda83.bean.account.OutGoingSMS;

/**
 * @author peter
 *
 */
public interface SchoolOutGoingSMSDAO {
	
	public OutGoingSMS getOutGoingSMS(String accountId, String uuid); 
	
	public List<OutGoingSMS> getOutGoingSMSList(String accountId,String status, int startIndex, int endIndex); 
	
	public boolean putOutGoingSMS(OutGoingSMS outGoingSMS);
	
	public boolean updateOutGoingSMS(OutGoingSMS outGoingSMS);
	
	public boolean deleteOutGoingSMS(String accountId, String uuid);
	
	public boolean deleteOutGoingSMSByStatus(String accountId,String status);

}
