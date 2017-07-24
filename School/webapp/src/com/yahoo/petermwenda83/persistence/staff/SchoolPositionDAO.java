/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.util.List;

import com.yahoo.petermwenda83.bean.staff.Position;

/**
 * @author peter
 *
 */
public interface SchoolPositionDAO {
	
	public Position get(String Uuid);
	
	public boolean putPosition(Position osition);
	
	public boolean updatePosition(Position osition);
	
	public boolean deletePosition(Position osition);
	
	public List<Position> getPositionList();

}
