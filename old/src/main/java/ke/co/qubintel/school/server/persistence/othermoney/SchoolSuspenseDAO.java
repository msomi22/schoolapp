/**
 * 
 */
package ke.co.qubintel.school.server.persistence.othermoney;

import java.util.List;

import ke.co.qubintel.school.server.bean.otherfee.Suspense;

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
