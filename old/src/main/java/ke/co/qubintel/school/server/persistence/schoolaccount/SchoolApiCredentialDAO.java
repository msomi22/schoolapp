/**
 * 
 */
package ke.co.qubintel.school.server.persistence.schoolaccount;

import ke.co.qubintel.school.server.bean.account.ApiCredential;

/**
 * @author peter
 *
 */
public interface SchoolApiCredentialDAO {
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	
	public ApiCredential getApiCredential(String  accountId);
	/**
	 * 
	 * @param accountId
	 * @param apiType
	 * @return
	 */
	
	public ApiCredential getApiCredential(String  accountId, String apiType);
	/**
	 * 
	 * @param apiCredential
	 * @return
	 */
	 
	public boolean putApiCredential(ApiCredential apiCredential);
	/**
	 * 
	 * @param apiCredential
	 * @return
	 */
	 
	public boolean updateApiCredential(ApiCredential apiCredential);

}
