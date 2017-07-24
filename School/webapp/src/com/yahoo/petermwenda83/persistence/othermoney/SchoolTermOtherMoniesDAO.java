package com.yahoo.petermwenda83.persistence.othermoney;

import java.util.List;

import com.yahoo.petermwenda83.bean.othermoney.TermOtherMonies;

public interface SchoolTermOtherMoniesDAO {
	/**
	 * 
	 * @param SchoolAccountUuid
	 * @return
	 */
	public TermOtherMonies getTermOtherMonies(String SchoolAccountUuid);
	
	/**
	 * 
	 * @param SchoolAccountUuid
	 * @return
	 */
	public TermOtherMonies getTermOtherMoney(String SchoolAccountUuid,String OtherstypeUuid);
	/**
	 * 
	 * @param termOtherMonies
	 * @return
	 */
	public boolean putTermOtherMonies(TermOtherMonies termOtherMonies);
	/**
	 * 
	 * @param termOtherMonies
	 * @return
	 */
	public boolean updateTermOtherMonies(TermOtherMonies termOtherMonies);
	/**
	 * 
	 * @param termOtherMonies
	 * @return
	 */
	
	public boolean deleteTermOtherMonies(TermOtherMonies termOtherMonies);
	/**
	 * 
	 * @param SchoolAccountUuid
	 * @return
	 */
	public List<TermOtherMonies> getTermOtherMoniesList(String SchoolAccountUuid);

}
