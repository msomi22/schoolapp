/**
 * 
 */
package ke.co.qubintel.school.server.persistence.exam;

import java.util.List;

import ke.co.qubintel.school.server.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public interface SchoolExamEngineDAO {
	
	public Perfomance getPerformance(String accountId,String examId,String studentId,String streamId,String term,String year,String subjectId); 
	
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
	 * @param accountId
	 * @param studentId
	 * @param subjectId
	 * @param examId
	 * @param term
	 * @param year
	 * @param streamId
	 * @return
	 */
	public List<Perfomance> scoreDuplicate(String accountId,String studentId, String subjectId, String examId,String term,String year, String streamId);
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

