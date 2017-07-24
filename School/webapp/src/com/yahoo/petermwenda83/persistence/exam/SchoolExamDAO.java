/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import java.util.List;

import com.yahoo.petermwenda83.bean.exam.Exam;

/**
 * @author peter
 *
 */
public interface SchoolExamDAO {
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Exam getExam(String uuid);
	/**
	 * 
	 * @param examName
	 * @return
	 */
	public Exam getExamByName(String examName);
	/**
	 * 
	 * @param exam
	 * @return
	 */
	public boolean updateExam(Exam exam);
	/**
	 * 
	 * @param exam
	 * @return
	 */
	public boolean putExam(Exam exam);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @return
	 */
	public List<Exam> getExamList(String schoolAccountUuid);

}
