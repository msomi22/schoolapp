package com.yahoo.petermwenda83.persistence.othermoney;

import java.util.List;

import com.yahoo.petermwenda83.bean.otherfee.OtherFee;

public interface SchoolOtherstypeDAO {
	/**
	 * 
	 * @param Uuid
	 * @return
	 */
	public OtherFee getOtherstype(String Uuid);
	/**
	 * 
	 * @param otherFee
	 * @return
	 */
	public boolean putOtherstype(OtherFee otherFee);
	/**
	 * 
	 * @param otherFee
	 * @return
	 */
	public boolean updteOtherstype(OtherFee otherFee);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param term
	 * @param year
	 * @return
	 */
	
	public List<OtherFee> getOtherstypeList(String schoolAccountUuid,String term,String year);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @return
	 */
	
	public List<OtherFee> gettypeList(String schoolAccountUuid);

}
