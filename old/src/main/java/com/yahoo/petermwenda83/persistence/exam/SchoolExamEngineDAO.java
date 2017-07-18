/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import com.yahoo.petermwenda83.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public interface SchoolExamEngineDAO {
	
    /**
     * 
     * @param accountId
     * @param studentId
     * @param subjectId
     * @param examId
     * @param term
     * @param year
     * @param streamId
     * @return
     */
    
	public boolean studentScoreExist(String accountId,String studentId, String subjectId, String examId,String term,String year, String streamId); 
	/**
	 * 
	 * @param perfomance
	 * @param accountId
	 * @param studentId
	 * @param subjectId
	 * @param examId
	 * @param term
	 * @param year
	 * @param streamId
	 * @return
	 */
	public boolean putPerfomance(Perfomance perfomance,String accountId,String studentId, String subjectId, String examId,String term,String year, String streamId);
	
}

