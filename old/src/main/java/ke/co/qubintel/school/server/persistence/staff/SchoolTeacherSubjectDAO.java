/**
 * 
 */
package ke.co.qubintel.school.server.persistence.staff;

import java.util.List;

import ke.co.qubintel.school.server.bean.staff.TeacherSubject;

/**
 * @author peter
 *
 */
public interface SchoolTeacherSubjectDAO {
	
	public TeacherSubject getTeacherSubject(String uuid);
	
	public TeacherSubject getTeacherSubject(String accountId, String uuid);
	
	public TeacherSubject getTeacherSubject(String accountId, String streamId, String subjectId);
	
	public boolean putTeacherSubject(TeacherSubject teacherSubject);
	
	public boolean updateTeacherSubject(TeacherSubject teacherSubject);
	
	public boolean deleteTeacherSubject(String uuid);
	
	public List<TeacherSubject> getTeacherSubjects(String teacherId);
	
	

}
