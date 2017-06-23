/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import java.util.List;

import com.yahoo.petermwenda83.bean.exam.SysConfig;

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
	public SysConfig getExamConfig(String schoolAccountUuid);
	
	/**
	 * 
	 * @param sysConfig
	 * @return
	 */
	public boolean  putExamConfig(SysConfig sysConfig);
	
	 /**
	  * 
	  * @param sysConfig
	  * @return
	  */
	public boolean  updateExamConfig(SysConfig sysConfig);
	
	  /**
	   * 
	   * @param schoolAccountUuid
	   * @return
	   */
	public List<SysConfig>  getExamConfigList(String schoolAccountUuid);
	
	
	

}
