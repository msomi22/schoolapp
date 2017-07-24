/**
 * 
 */
package com.yahoo.petermwenda83.persistence.student;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.House;

/**
 * @author peter
 *
 */
public interface SchoolHouseDAO {
	/**
	 * 
	 * @param schoolUuid
	 * @param Uuid
	 * @return
	 */
	public House getHouse(String schoolUuid,String Uuid);
	 /**
	  * 
	  * @param house
	  * @return
	  */
	public boolean putHouse(House house);
	  /**
	   * 
	   * @param house
	   * @return
	   */
	public boolean updateHouse(House house);
	  /**
	   * 
	   * @param house
	   * @return
	   */
	public boolean deleteHouse(House house);
	  /**
	   * 
	   * @param schoolUuid
	   * @return
	   */
	public List<House> getHouseList(String schoolUuid); 

}
