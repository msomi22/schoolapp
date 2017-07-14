/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import com.yahoo.petermwenda83.bean.exam.BarWeight;

/**
 * @author peter
 *
 */
public interface SchoolBarWeightDAO {
	
	public BarWeight getBarWeight(String accountId,String studentId,String year);
	
	public boolean ExistBarWeight(String accountId,String studentId,String year);
	
	public boolean put(BarWeight weight,String accountId,String studentId,String year);
	
}
