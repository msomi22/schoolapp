/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import java.util.List;

import com.yahoo.petermwenda83.bean.exam.ExamConfig;

/**
 * @author peter
 *
 */
public interface SchoolExamConfigDAO {
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @return
	 */
	public ExamConfig getExamConfig(String schoolAccountUuid);
	
	/**
	 * 
	 * @param examConfig
	 * @return
	 */
	public boolean  putExamConfig(ExamConfig examConfig);
	
	 /**
	  * 
	  * @param examConfig
	  * @return
	  */
	public boolean  updateExamConfig(ExamConfig examConfig);
	
	  /**
	   * 
	   * @param schoolAccountUuid
	   * @return
	   */
	public List<ExamConfig>  getExamConfigList(String schoolAccountUuid);
	
	
	

}
