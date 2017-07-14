package com.yahoo.petermwenda83.persistence.othermoney;

import java.util.List;

import com.yahoo.petermwenda83.bean.otherfee.OtherFee;

public interface SchoolOtherFeeDAO {
	
	public OtherFee getOtherFee(String accountId, String uuid);
	
	public boolean putOtherFee(OtherFee otherFee);
	
	public boolean updateOtherFee(OtherFee otherFee);
	
	public List<OtherFee> getOtherFeeList(String accountId,String term,String year,int startIndex, int endIndex);
	
}
