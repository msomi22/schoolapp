/**
 * 
 */
package com.yahoo.petermwenda83.persistence.othermoney;

import java.util.List;

import com.yahoo.petermwenda83.bean.otherfee.Suspense;

/**
 * @author peter
 *
 */
public interface SchoolSuspenseDAO {
	
	public Suspense getSuspense(String accountId,String studentId, String uuid);
	
	public List<Suspense> getSuspense(String accountId,String studentId);
	
	public boolean putSuspense(Suspense suspense);
	
	public boolean deleteSuspense(String accountId,String studentId, String uuid);
	
	public List<Suspense> getSuspense(String accountId, int startIndex, int endIndex);
 
}
