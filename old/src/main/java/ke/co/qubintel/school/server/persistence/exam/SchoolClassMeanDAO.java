/**
 * 
 */
package ke.co.qubintel.school.server.persistence.exam;

import java.util.List;

import ke.co.qubintel.school.server.bean.exam.ClassMean;

/**
 * @author peter
 *
 */
public interface SchoolClassMeanDAO {
	
	
	public ClassMean getClassMean(String accountId,String streamId,String examId,String term,String year); 
	
	/**
	 * 
	 * @param accountId
	 * @param classId
	 * @param streamId
	 * @param term
	 * @param year
	 * @return
	 */
	public boolean existClassMean(String accountId, String classId,String streamId, String examId, String term,String year);
	
	/**
	 * 
	 * @param classMean
	 * @return
	 */
	public boolean putClassMean(ClassMean classMean,String accountId,String classId,String streamId,String examId,String term,String year);
	
	/**
	 * 
	 * @param accountId
	 * @param Id
	 * @param term
	 * @param year
	 * @return
	 */
	public List<ClassMean> getClassMeanList(String accountId,String classid, String examId, String term, String year); 

}
