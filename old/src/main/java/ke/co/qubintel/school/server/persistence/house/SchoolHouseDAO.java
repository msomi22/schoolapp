/**
 * 
 */
package ke.co.qubintel.school.server.persistence.house;

import java.util.List;

import ke.co.qubintel.school.server.bean.house.House;

/**
 * @author peter
 *
 */
public interface SchoolHouseDAO {
	
	public House getHouseById(String accountId, String uuid);
	
	public House getHouse(String accountId, String houseName); 
	
	public List<House> getHouseList(String accountId); 
	
	public boolean putHouse(House house); 
	
	public boolean updateHouse(House house); 
	
	public boolean deleteHouse(String accountId, String uuid); 

	
	

}
