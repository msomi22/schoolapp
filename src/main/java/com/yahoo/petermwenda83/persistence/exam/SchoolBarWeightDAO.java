/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import com.yahoo.petermwenda83.bean.exam.BarWeight;

/**
 * @author peter
 *
 */
public interface SchoolBarWeightDAO {
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param studentUuid
	 * @param term
	 * @param year
	 * @return
	 */
	public BarWeight getBarWeight(String schoolAccountUuid,String studentUuid,String year);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param studentUuid
	 * @param term
	 * @param year
	 * @return
	 */
	public boolean ExistBarWeight(String schoolAccountUuid,String studentUuid,String year);
	/**
	 * 
	 * @param weight
	 * @return
	 */
	public boolean put(BarWeight weight,String schoolAccountUuid,String studentUuid,String year);
	/**
	 * 
	 * @param weight
	 * @return
	 */
	public boolean update(BarWeight weight);
	
	
	

}
