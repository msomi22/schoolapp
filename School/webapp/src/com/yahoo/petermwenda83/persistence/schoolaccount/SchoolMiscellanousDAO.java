/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import java.util.List;

import com.yahoo.petermwenda83.bean.schoolaccount.Miscellanous;

/**
 * @author peter
 *
 */
public interface SchoolMiscellanousDAO {
	/**
	 * 
	 * @param Uuid
	 * @return
	 */
	public Miscellanous getMiscellanous(String Uuid);
	
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	
	public Miscellanous getKey(String schoolAccountUuid,String key);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @return
	 */
	public Miscellanous getMisc(String schoolAccountUuid);
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
	public List<Miscellanous> getMiscellanousList(String schoolAccountUuid);

}
