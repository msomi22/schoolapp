/**
 * 
 */
package ke.co.qubintel.school.server.persistence.account;

import java.util.List;

import ke.co.qubintel.school.server.bean.account.Miscellanous;

/**
 * @author peter
 *
 */
public interface SchoolMiscellanousDAO {
	
	/**
	 * 
	 * @param accountId
	 * @param key
	 * @return
	 */
	public String getValueByKey(String accountId,String key);
	
	/**
	 * 
	 * @param accountId
	 * @param key
	 * @return
	 */
	public Miscellanous getMiscById(String accountId,String uuid); 
	
	/**
	 * 
	 * @param misc
	 * @return
	 */
	public boolean putMiscellanous(Miscellanous misc);
	 /**
	  * 
	  * @param misc
	  * @return
	  */
	public boolean updateMiscellanous(Miscellanous misc);
	 /**
	  * 
	  * @param schoolAccountUuid
	  * @return
	  */
	public List<Miscellanous> getMiscellanousList(String accountId);

}
