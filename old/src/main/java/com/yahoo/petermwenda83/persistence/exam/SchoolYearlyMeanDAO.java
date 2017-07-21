/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import com.yahoo.petermwenda83.bean.exam.YearlyMean;

/**
 * @author peter
 *
 */
public interface SchoolYearlyMeanDAO {
	
	public YearlyMean getYearlyMean(String accountId,String studentId,String year);
	
	public boolean existYearlyMean(String accountId,String studentId,String year);
	
	public boolean putYearlyMean(YearlyMean yearlyMean,String accountId,String studentId,String year);
	
}
