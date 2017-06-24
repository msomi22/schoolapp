package com.yahoo.petermwenda83.persistence.money;

import java.util.List;

import com.yahoo.petermwenda83.bean.money.StudentFee;

public interface SchoolStudentFeeDAO {
	
	public StudentFee getStudentFee(String accountId , String uuid); 
	
	public boolean putStudentFee(StudentFee studentFee);
	
	public boolean updateStudentFee(StudentFee studentFee);

	public List<StudentFee> getStudentFeeList(String accountId , String studentId, int startIndex , int endIndex); 
	
	
}
