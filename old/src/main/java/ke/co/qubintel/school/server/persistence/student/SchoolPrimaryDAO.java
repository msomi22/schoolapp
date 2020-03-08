

package ke.co.qubintel.school.server.persistence.student;

import java.util.List;

import ke.co.qubintel.school.server.bean.student.StudentPrimary;

/**
 * @author peter
 *
 */
public interface SchoolPrimaryDAO {
	
	public StudentPrimary getStudentPrimary(String accountId,String studentId);
	 
	public boolean putStudentPrimary(StudentPrimary Primary);
	  
	public boolean updateStudentPrimary(StudentPrimary Primary);
	   
	public boolean deleteStudentPrimary(String accountId,String studentId);
	  
	public List<StudentPrimary> getStudentPrimary(String accountId);

}
