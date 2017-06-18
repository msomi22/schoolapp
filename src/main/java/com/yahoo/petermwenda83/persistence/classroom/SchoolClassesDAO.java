/**
 * 
 */
package com.yahoo.petermwenda83.persistence.classroom;

import java.util.List;

import com.yahoo.petermwenda83.bean.classroom.Stream;

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
	public Stream getClass(String Uuid);
	 /**
	  * 
	  * @param Class
	  * @return
	  */
	public boolean putClass(Stream Class);
	 /**
	  * 
	  * @param Class
	  * @return
	  */
	public boolean updateClass(Stream Class);
	  /**
	   * 
	   * @return
	   */
	public List<Stream> getClassList();
	
	

}
