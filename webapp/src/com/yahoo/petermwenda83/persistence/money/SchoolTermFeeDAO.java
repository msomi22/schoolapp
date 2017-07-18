package com.yahoo.petermwenda83.persistence.money;

import java.util.List;

import com.yahoo.petermwenda83.bean.money.TermFee;

public interface SchoolTermFeeDAO {
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param Term
	 * @param Year
	 * @return
	 */
	public TermFee getFee(String schoolAccountUuid,String Term,String Year);
	
	
	
	/**
	 * @param schoolAccountUuid
	 * @param Term
	 * @param Year
	 * @return
	 */
	public boolean TermFee(String schoolAccountUuid,String Term,String Year);
	
	/**
	 * 
	 * @param termFee
	 * @return
	 */
	public boolean putFee(TermFee termFee);
	
	/**
	 * 
	 * @param termFee
	 * @return
	 */
	public boolean updateFee(TermFee termFee); 
	/**
	 * 
	 * @param termFee
	 * @return
	 */
	public List<TermFee> getTermFeeList(String schoolAccountUuid); 

}
