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
public class SuspenseDAO implements SchoolSuspenseDAO {

	/**
	 * 
	 */
	public SuspenseDAO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolSuspenseDAO#getSuspense(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Suspense getSuspense(String accountId, String studentId, String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolSuspenseDAO#getSuspense(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Suspense> getSuspense(String accountId, String studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolSuspenseDAO#putSuspense(com.yahoo.petermwenda83.bean.otherfee.Suspense)
	 */
	@Override
	public boolean putSuspense(Suspense suspense) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolSuspenseDAO#deleteSuspense(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteSuspense(String accountId, String studentId, String uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolSuspenseDAO#getSuspense(java.lang.String, int, int)
	 */
	@Override
	public List<Suspense> getSuspense(String accountId, int startIndex, int endIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
