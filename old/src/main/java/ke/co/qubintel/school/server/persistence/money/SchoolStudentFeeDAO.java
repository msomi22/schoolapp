package ke.co.qubintel.school.server.persistence.money;

import java.util.List;

import ke.co.qubintel.school.server.bean.money.StudentFee;

public interface SchoolStudentFeeDAO {
	
	public StudentFee getStudentFee(String accountId , String studentId, String uuid); 
	
	public StudentFee getStudentFee(String accountId, String studentId, String payMode, String termPiad,String yearPaid);  
	
	public boolean putStudentFee(StudentFee studentFee);
	
	public boolean updateStudentFee(StudentFee studentFee);
	
	public boolean revertStudentGokeFee(String accountId , String studentId, String termPiad, String yearPaid,String payMode);

	public List<StudentFee> getStudentFeeList(String accountId , String studentId, int startIndex , int endIndex); 

	public List<StudentFee> getStudentFeeList(String accountId , String studentId, String termPiad, String yearPaid); 
	
	public List<StudentFee> getStudentGoKFeeList(String accountId , String termPiad, String yearPaid, String payMode); 
	
	
}
