/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.util.List;

import com.yahoo.petermwenda83.bean.staff.Duties;

/**
 * @author peter
 *
 */
public interface SchoolDutiesDAO {
	/**
	 * 
	 * @param Uuid
	 * @return
	 */
	public Duties getDuty(String Uuid);
	/**
	 * 
	 * @param duty
	 * @return
	 */
	public boolean putDuty(Duties duty);
	/**
	 * 
	 * @param duty
	 * @return
	 */
	public boolean updateDuty(Duties duty);
	 /**
	  * 
	  * @param duty
	  * @return
	  */
	public boolean deleteDuty(Duties duty);
	/**
	 * 
	 * @return
	 */
	public List<Duties> getDutiesList();
	
	

}
