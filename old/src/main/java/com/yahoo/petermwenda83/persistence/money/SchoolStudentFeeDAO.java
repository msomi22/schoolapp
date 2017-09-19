package com.yahoo.petermwenda83.persistence.money;

import java.util.List;

import com.yahoo.petermwenda83.bean.money.StudentFee;

public interface SchoolStudentFeeDAO {
	
	public StudentFee getStudentFee(String accountId , String uuid); 
	
	public StudentFee getStudentFee(String accountId, String studentId, String payMode, String termPiad,String yearPaid);  
	
	public boolean putStudentFee(StudentFee studentFee);
	
	public boolean updateStudentFee(StudentFee studentFee);

	public List<StudentFee> getStudentFeeList(String accountId , String studentId, int startIndex , int endIndex); 

	public List<StudentFee> getStudentFeeList(String accountId , String studentId, String termPiad, String yearPaid); 
	
	
}
