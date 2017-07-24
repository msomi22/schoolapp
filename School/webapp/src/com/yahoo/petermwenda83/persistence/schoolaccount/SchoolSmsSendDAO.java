/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import java.util.List;

import com.yahoo.petermwenda83.bean.schoolaccount.SmsSend;

/**
 * @author peter
 *
 */
public interface SchoolSmsSendDAO {
	/**
	 * 
	 * @param Uuid
	 * @return
	 */
	public SmsSend getSmsSend(String Uuid);
	 /**
	  * 
	  * @param status
	  * @return
	  */
	public SmsSend getSmsSendByStatus(String status);
	 /**
	  * 
	  * @param smsSend
	  * @return
	  */
	public boolean putSmsSend(SmsSend smsSend);
	/**
	 * 
	 * @param smsSend
	 * @return
	 */
	public boolean updateSmsSend(SmsSend smsSend);
	  /**
	   * 
	   * @param smsSend
	   * @return
	   */
	public boolean deleteSmsSend(SmsSend smsSend);
	  /**
	   * 
	   * @return
	   */
	public List<SmsSend> getSmsSend();
	
	/**
	 * 
	 * @param status
	 * @return
	 */
	public List<SmsSend> getSmsSendList(String status);

}
