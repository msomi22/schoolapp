/**
 * 
 */
package ke.co.qubintel.school.server.persistence.house;

import java.util.List;

import ke.co.qubintel.school.server.bean.house.StudentHouse;

/**
 * @author peter
 *
 */
public interface SchoolStudentHouseDAO {

	public StudentHouse getStudentHouseById(String accountId, String uuid);

	public StudentHouse getStudentHouse(String accountId, String studentId);

	public List<StudentHouse> getStudentHouseList(String accountId, String houseId);

	public boolean putStudentHouse(StudentHouse studentHouse); 

	public boolean exitHouse(StudentHouse studentHouse); 
	
	public boolean changeHouse(StudentHouse studentHouse); 

	public boolean deleteStudentHouse(String accountId, String uuid); 

}
