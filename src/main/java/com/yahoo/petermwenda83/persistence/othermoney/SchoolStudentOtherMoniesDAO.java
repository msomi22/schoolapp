package com.yahoo.petermwenda83.persistence.othermoney;

import java.util.List;

import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;

public interface SchoolStudentOtherMoniesDAO {
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public StudentOtherFee getStudentOtherMonies(String studentUuid,String otherstypeUuid);
	
	
	/**
	 * 
	 * @param studentUuid
	 * @param otherstypeUuid
	 * @param term
	 * @param year
	 * @return
	 */
	 
	
	public StudentOtherFee getStudentOtherMTY(String studentUuid,String otherstypeUuid, String term,String year);
	
	/**
	 * 
	 * @param studentUuid
	 * @param otherstypeUuid
	 * @param Term
	 * @param Year
	 * @return
	 */
	public List<StudentOtherFee> getStudentOtherMoniesList(String studentUuid,String otherstypeUuid);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param classRoomUuid
	 * @return
	 */
	public List<StudentOtherFee> getStudentOtherMoniesDistinct(String studentUuid);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param studentUuid
	 * @param Term
	 * @param Year
	 * @return
	 */
	
	public List<StudentOtherFee> getStudentOtherList(String studentUuid,String Term,String Year); 
	/**
	 * 
	 * @param studentOtherFee
	 * @return
	 */
	public boolean putStudentOtherMonies(StudentOtherFee studentOtherFee);
	/**
	 * 
	 * @param studentOtherFee
	 * @return
	 */
	public boolean updateStudentOtherMonies(StudentOtherFee studentOtherFee);
	/**
	 * 
	 * @param studentOtherFee
	 * @return
	 */
	public boolean deleteStudentOtherMonies(StudentOtherFee studentOtherFee);
	/**
	 * 
	 * @return
	 */
	public List<StudentOtherFee> getStudentOtherMoniesList();

}
