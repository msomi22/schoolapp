/**
 * 
 */
package ke.co.qubintel.school.server.persistence.othermoney;

import java.util.List;

import ke.co.qubintel.school.server.bean.otherfee.StudentOtherFee;

/**
 * @author peter
 *
 */
public interface SchoolStudentOtherFeeDAO {
	
	public StudentOtherFee getStudentOtherFee(String accountId, String studentId , String otherFeeId);
	
	public boolean putStudentOtherFee(StudentOtherFee studentOtherFee);
	
	public boolean updateStudentOtherFee(StudentOtherFee studentOtherFee);
	
	public boolean revertStudentOtherFee(String accountId, String studentId, String otherFeeId);
	
	public List<StudentOtherFee> getStudentOtherFeeList(String accountId, String studentId, int startIndex, int endIndex);
	
	public List<StudentOtherFee> getStudentOFeeList(String accountId, String studentId, String term, long year);
	
	public List<StudentOtherFee> getStudentOtherFeeList(String accountId, String studentId);

}
