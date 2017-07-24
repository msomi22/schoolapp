package com.yahoo.petermwenda83.persistence.othermoney;

import java.util.List;

import com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies;

public interface SchoolStudentOtherMoniesDAO {
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public StudentOtherMonies getStudentOtherMonies(String studentUuid,String otherstypeUuid);
	
	
	/**
	 * 
	 * @param studentUuid
	 * @param otherstypeUuid
	 * @param term
	 * @param year
	 * @return
	 */
	 
	
	public StudentOtherMonies getStudentOtherMTY(String studentUuid,String otherstypeUuid, String term,String year);
	
	/**
	 * 
	 * @param studentUuid
	 * @param otherstypeUuid
	 * @param Term
	 * @param Year
	 * @return
	 */
	public List<StudentOtherMonies> getStudentOtherMoniesList(String studentUuid,String otherstypeUuid);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param classRoomUuid
	 * @return
	 */
	public List<StudentOtherMonies> getStudentOtherMoniesDistinct(String studentUuid);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param studentUuid
	 * @param Term
	 * @param Year
	 * @return
	 */
	
	public List<StudentOtherMonies> getStudentOtherList(String studentUuid,String Term,String Year); 
	/**
	 * 
	 * @param studentOtherMonies
	 * @return
	 */
	public boolean putStudentOtherMonies(StudentOtherMonies studentOtherMonies);
	/**
	 * 
	 * @param studentOtherMonies
	 * @return
	 */
	public boolean updateStudentOtherMonies(StudentOtherMonies studentOtherMonies);
	/**
	 * 
	 * @param studentOtherMonies
	 * @return
	 */
	public boolean deleteStudentOtherMonies(StudentOtherMonies studentOtherMonies);
	/**
	 * 
	 * @return
	 */
	public List<StudentOtherMonies> getStudentOtherMoniesList();

}
