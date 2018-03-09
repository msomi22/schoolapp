package ke.co.qubintel.school.server.persistence.money;

import java.util.List;

import ke.co.qubintel.school.server.bean.money.TermFee;

public interface SchoolTermFeeDAO {
	
	public TermFee getFee(String accountId, String term, String year);
	
	public boolean termFeeAded(String accountId, String term, String year);
	
	public boolean putFee(TermFee termFee, String accountId, String term,String year);
	
	public boolean updateFee(TermFee termFee); 
	
	public List<TermFee> getTermFeeList(String accountId, int startIndex , int endIndex); 
	
	public List<TermFee> getTermFeeList(String accountId); 
	
	public List<TermFee> getTermFeeList(String accountId, String year); 
	
	public List<TermFee> findDuplicate(String accountId, String term, String year); 

}
