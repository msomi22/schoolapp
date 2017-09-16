/**
 * 
 */
package com.yahoo.petermwenda83.persistence.othermoney;

import java.util.List;

import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;

/**
 * @author peter
 *
 */
public interface SchoolStudentOtherFeeDAO {
	
	public StudentOtherFee getStudentOtherFee(String accountId, String studentId , String otherFeeId);
	
	public boolean putStudentOtherFee(StudentOtherFee studentOtherFee);
	
	public boolean updateStudentOtherFee(StudentOtherFee studentOtherFee);
	
	public List<StudentOtherFee> getStudentOtherFeeList(String accountId, String studentId, int startIndex, int endIndex);
	
	public List<StudentOtherFee> getStudentOFeeList(String accountId, String studentId, String term, long year);
	
	public List<StudentOtherFee> getStudentOtherFeeList(String accountId, String studentId);

}
