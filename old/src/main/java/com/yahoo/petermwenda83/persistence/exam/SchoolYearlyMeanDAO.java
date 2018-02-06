/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import java.util.List;

import com.yahoo.petermwenda83.bean.exam.YearlyMean;

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
