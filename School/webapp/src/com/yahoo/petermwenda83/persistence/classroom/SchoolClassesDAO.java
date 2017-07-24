/**
 * 
 */
package com.yahoo.petermwenda83.persistence.classroom;

import java.util.List;

import com.yahoo.petermwenda83.bean.classroom.Classes;

/**
 * @author peter
 *
 */
public interface SchoolClassesDAO {
	
	/**
	 * 
	 * @param Uuid
	 * @return
	 */
	public Classes getClass(String Uuid);
	 /**
	  * 
	  * @param Class
	  * @return
	  */
	public boolean putClass(Classes Class);
	 /**
	  * 
	  * @param Class
	  * @return
	  */
	public boolean updateClass(Classes Class);
	  /**
	   * 
	   * @return
	   */
	public List<Classes> getClassList();
	
	

}
