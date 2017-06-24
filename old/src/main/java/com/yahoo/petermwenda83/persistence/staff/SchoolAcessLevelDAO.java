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
	
	public AcessLevel get(String uuid);
	
	public boolean putPosition(AcessLevel acessLevel);
	
	public boolean updatePosition(AcessLevel acessLevel);
	
	public boolean deletePosition(String uuid);
	
	public List<AcessLevel> getPositionList();

}
