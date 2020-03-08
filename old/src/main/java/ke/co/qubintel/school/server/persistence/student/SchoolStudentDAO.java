

package ke.co.qubintel.school.server.persistence.student;
import java.util.List;

import ke.co.qubintel.school.server.bean.student.Student;



/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentDAO {

	public Student getStudentById(String accountId,String uuid);

	public int getNextregNo(String accountId); 

	public Student getStudentByregNo(String accountId,String regNo);

	/**
	 * 
	 * @param schoolaccount
	 * @param admno
	 * @return	a {@link List} of {@link Student}s whose query partly or wholly
	 * matches the query and belongs to a particular school account. Matching is case 
	 * insensitive. An empty list is returned if no Student matches the query.
	 */
	public List<Student> searchStudent(String accountId, String query);
	/**
	 * 
	 * @param accountId
	 * @param key
	 * @return
	 */
	public List<Student> findDuplicate(String accountId, String key);

	public boolean putStudent(Student student);

	public boolean updateStudent(Student student);

	public boolean deleteStudent(String accountId,String uuid);

	public List<Student> getStudentByStream(String accountId,String currentStream); 
	
	/**
	 * 
	 * @param accountId
	 * @param currentStream
	 * @param isActive
	 * @return
	 */
	public List<Student> getStudentByStream(String accountId,String currentStream, String isActive); 
	
	public List<Student> getStudentByGender(String accountId, String gender, String currentStream, String isActive); 

	/**
	 * 
	 * @param accountId account id 
	 * @param limit SQL LIMIT, if limit is 5, display only the first 5 records
	 * @param offset SQL OFFSET, if offset is 5, skip the first 5 records,
	 * @return list of {@link Student} 
	 */
	public List<Student> getAllStudent(String accountId, int limit , int offset); 
	/**
	 * 
	 * @param accountId account id 
	 * @param isActive status 
	 * @param limit SQL LIMIT, if limit is 5, display only the first 5 records
	 * @param offset  SQL OFFSET, if offset is 5, skip the first 5 records,
	 * @return list of {@link Student} 
	 */
	public List<Student> getAllStudent(String accountId, String isActive, int limit , int offset); 
	
	public List<Student> getActiveStudents(String accountId, String isActive);
	
	public List<Student> getActiveStudents(String accountId, String isActive, String isGoKFeeEligibe);
	
	public List<Student> getStudents(String accountId);  
	
	public int classStudentCount(String accountId,String currentStream, String isActive);

	public int activeCount(String accountId, String isActive);
	
	
	public int activeAndGoKEligibleCount(String accountId, String isActive, String isGoKFeeEligibe);
	
	public int alumniCount(String accountId, String isAlumni);
	
	public int dayCount(String accountId, String isActive, String isBoarding);
	
	public int genderCount(String accountId, String isActive, String gender);






}
