/**
 * 
 */
package com.yahoo.petermwenda83.persistence.othermoney;

import java.util.List;

import com.yahoo.petermwenda83.bean.othermoney.RevertedMoney;

/**
 * @author peter
 *
 */
public interface SchoolRevertedMoneyDAO {
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public RevertedMoney getRevertedMoney(String studentUuid);
	 /**
	  * 
	  * @param revertedMoney
	  * @return
	  */
	public boolean putstudentUuid(RevertedMoney revertedMoney);
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public List<RevertedMoney> getRevertedMoneyList(String studentUuid);

}
