/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import com.yahoo.petermwenda83.bean.exam.GradingSystem;

/**
 * @author peter
 *
 */
public interface ScoolGradingSystemDAO {
	/**
	 * 
	 * @param schoolAccountUuid
	 * @return
	 */
	public GradingSystem getGradingSystem(String schoolAccountUuid);
	 /**
	  * 
	  * @param gradingSystem
	  * @return
	  */
	public boolean putGradingSystem(GradingSystem gradingSystem);
	  /**
	   * 
	   * @param gradingSystem
	   * @return
	   */
	public boolean updateGradingSystem(GradingSystem gradingSystem);
	

}
