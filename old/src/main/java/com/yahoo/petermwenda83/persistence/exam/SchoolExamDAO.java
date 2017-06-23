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
	
	public Exam getExam(String accountId,String uuid);
	
	public Exam getExamByCode(String accountId,String code);
	
	public Exam getExamByDesc(String accountId,String description);

	public boolean putExam(Exam exam);
	
	public boolean updateExam(Exam exam);
	
	public List<Exam> getExamList(String accountId);

}
