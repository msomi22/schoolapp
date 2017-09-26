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
public interface SchoolSysConfigDAO {
	
	public SysConfig getSysConfig(String accountId);
	
	public SysConfig getSysConfig(String accountId, String term, String year);
	
	public boolean  putSysConfig(SysConfig sysConfig);
	
	public boolean  updateSysConfig(SysConfig sysConfig);
	
	public List<SysConfig>  getSysConfigList(String accountId);
	
	
	

}
