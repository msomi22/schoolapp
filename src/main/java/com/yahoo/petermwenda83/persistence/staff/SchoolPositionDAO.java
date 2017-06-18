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
public interface SchoolPositionDAO {
	
	public AcessLevel get(String Uuid);
	
	public boolean putPosition(AcessLevel osition);
	
	public boolean updatePosition(AcessLevel osition);
	
	public boolean deletePosition(AcessLevel osition);
	
	public List<AcessLevel> getPositionList();

}
