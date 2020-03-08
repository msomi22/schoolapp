/**
 * 
 */
package ke.co.qubintel.school.server.persistence.exam;

import java.util.List;

import ke.co.qubintel.school.server.bean.exam.YearlyMean;

/**
 * @author peter
 *
 */
public interface SchoolYearlyMeanDAO {
	
	public YearlyMean getYearlyMean(String accountId,String studentId,String year);
	
	public boolean existYearlyMean(String accountId, String uuid, String studentId,String classId,String year);
	
	public boolean putYearlyMean(YearlyMean yearlyMean,String accountId,String studentId,String classId,String year);
	
	public List<YearlyMean> getYearlyMean(String accountId, String studentId);
	
}
