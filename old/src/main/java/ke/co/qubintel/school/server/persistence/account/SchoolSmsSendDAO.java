/**
 * 
 */
package ke.co.qubintel.school.server.persistence.account;

import java.util.List;

import ke.co.qubintel.school.server.bean.account.OutGoingSMS;

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
	public OutGoingSMS getSmsSend(String Uuid);
	 /**
	  * 
	  * @param status
	  * @return
	  */
	public OutGoingSMS getSmsSendByStatus(String status);
	 /**
	  * 
	  * @param outGoingSMS
	  * @return
	  */
	public boolean putSmsSend(OutGoingSMS outGoingSMS);
	/**
	 * 
	 * @param outGoingSMS
	 * @return
	 */
	public boolean updateSmsSend(OutGoingSMS outGoingSMS);
	  /**
	   * 
	   * @param outGoingSMS
	   * @return
	   */
	public boolean deleteSmsSend(OutGoingSMS outGoingSMS);
	  /**
	   * 
	   * @return
	   */
	public List<OutGoingSMS> getSmsSend();
	
	/**
	 * 
	 * @param status
	 * @return
	 */
	public List<OutGoingSMS> getSmsSendList(String status);

}
