/**
 * 
 */
package ke.co.qubintel.school.server.persistence.staff;

import java.util.List;

import ke.co.qubintel.school.server.bean.staff.AcessLevel;

/**
 * @author peter
 *
 */
public interface SchoolAcessLevelDAO {
	
	
	public AcessLevel getAcessLevel(String uuid);
	
	public AcessLevel getAcessLevel(String accountId, String uuid);
	
	public AcessLevel getAcessLevelById(String accountId, String  acessId);
	
	public boolean putAcessLevel(AcessLevel acessLevel);
	
	public boolean updateAcessLevel(AcessLevel acessLevel);
	
	public boolean deleteAcessLevel(String uuid);
	
	public List<AcessLevel> getAcessLevelList(String accountId);

}
