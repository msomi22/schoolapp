package com.yahoo.petermwenda83.persistence.money;

import java.util.List;

import com.yahoo.petermwenda83.bean.money.StudentFee;

public interface SchoolStudentFeeDAO {
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param studentUuid
	 * @return
	 */
	public StudentFee getStudentFeeByStudentUuid(String schoolAccountUuid , String studentUuid,String Term,String Year); 
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param studentUuid
	 * @return
	 */
	public List<StudentFee> getStudentFeeByStudentUuidList(String schoolAccountUuid , String studentUuid,String Term,String Year); 
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param studentUuid
	 * @return
	 */
	public List<StudentFee> getStudentFeeDistinctList(String schoolAccountUuid ,String Term,String Year); 
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param transactionID
	 * @return
	 */
	public StudentFee getStudentFeeByTransactionId(String schoolAccountUuid , String transactionID); 
	/**
	 * 
	 * @param studentFee
	 * @return
	 */
	public boolean putStudentFee(StudentFee studentFee);
	/**
	 * 
	 * @param studentFee
	 * @return
	 */
	public boolean updateStudentFee(StudentFee studentFee);
	/**
	 * 
	 * @param studentFee
	 * @return
	 */
	public boolean deleteStudentFee(StudentFee studentFee);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @return
	 */
	public List<StudentFee> getStudentFeeList(String schoolAccountUuid,String Term,String Year);

}
