

package ke.co.qubintel.school.server.persistence.guardian;

import java.util.List;

import ke.co.qubintel.school.server.bean.student.guardian.StudentParent;

/**
 * @author r<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolParentsDAO {
  
	public StudentParent getParent(String accountId, String studentId); 
	
	public boolean putParent(StudentParent parent);
	
	public boolean updateParent(StudentParent parent);
	
	public boolean deleteParent(String accountId, String studentId);
	
	public List<StudentParent> getParents(String accountId);
	
	public List<StudentParent> getParents(String accountId,int startIndex, int endIndex);
	
}
