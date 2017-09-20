package com.yahoo.petermwenda83.persistence.othermoney;

import java.util.List;

import com.yahoo.petermwenda83.bean.otherfee.OtherFee;

public interface SchoolOtherFeeDAO {
	
	public OtherFee getOtherFee(String accountId, String uuid);
	
	public OtherFee queryOtherFee(String accountId, String query); 
	
	public OtherFee queryOtherFee(String accountId, String description, String term,String year); 
	
	public List<OtherFee> findDuplicate(String accountId, String description, String term,String year); 
	
	public boolean putOtherFee(OtherFee otherFee);
	
	public boolean updateOtherFee(OtherFee otherFee);
	
	public List<OtherFee> getOtherFeeList(String accountId,String term,String year,int startIndex, int endIndex);
	
	public List<OtherFee> getOtherFeeList(String accountId,String term,String year);
	
}
