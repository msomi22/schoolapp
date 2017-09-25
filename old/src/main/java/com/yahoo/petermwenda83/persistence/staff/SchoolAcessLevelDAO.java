/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.util.List;

import com.yahoo.petermwenda83.bean.staff.AcessLevel;

/**
 * @author peter
 *
 */
public interface SchoolAcessLevelDAO {
	
	
	public AcessLevel getAcessLevel(String uuid);
	
	public AcessLevel getAcessLevel(String accountId, String uuid);
	
	public boolean putAcessLevel(AcessLevel acessLevel);
	
	public boolean updateAcessLevel(AcessLevel acessLevel);
	
	public boolean deleteAcessLevel(String uuid);
	
	public List<AcessLevel> getAcessLevelList(String accountId);

}
