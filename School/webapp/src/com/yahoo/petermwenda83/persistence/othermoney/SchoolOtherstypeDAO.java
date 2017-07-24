package com.yahoo.petermwenda83.persistence.othermoney;

import java.util.List;

import com.yahoo.petermwenda83.bean.othermoney.Otherstype;

public interface SchoolOtherstypeDAO {
	/**
	 * 
	 * @param Uuid
	 * @return
	 */
	public Otherstype getOtherstype(String Uuid);
	/**
	 * 
	 * @param otherstype
	 * @return
	 */
	public boolean putOtherstype(Otherstype otherstype);
	/**
	 * 
	 * @param otherstype
	 * @return
	 */
	public boolean updteOtherstype(Otherstype otherstype);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param term
	 * @param year
	 * @return
	 */
	
	public List<Otherstype> getOtherstypeList(String schoolAccountUuid,String term,String year);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @return
	 */
	
	public List<Otherstype> gettypeList(String schoolAccountUuid);

}
