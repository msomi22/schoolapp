/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import java.util.List;

import com.yahoo.petermwenda83.bean.exam.GradingSystem;

/**
 * @author peter
 *
 */
public interface ScoolGradingSystemDAO {
	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @return
	 */
	public GradingSystem getGradingSystem(String accountId, String uuid);
	
	/**
	 * 
	 * @param accountId
	 * @param categoryId
	 * @param description
	 * @return
	 */
	public GradingSystem getGradesByDesc(String accountId, String categoryId, String description);
	/**
	 * 
	 * @param accountId
	 * @param categoryId
	 * @return
	 */
	public List<GradingSystem> getGradingSystemList(String accountId, String categoryId);
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
