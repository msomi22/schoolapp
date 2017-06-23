package com.yahoo.petermwenda83.persistence.exam;

import java.util.List;

import com.yahoo.petermwenda83.bean.exam.Deviation;

public interface SchoolDeviationDAO {
	/**
	 * 
	 * @param studentUuid
	 * @param year
	 * @return
	 */
	public Deviation getDev(String studentUuid,String year);
	
	/**
	 * 
	 * @param studentUuid
	 * @param year
	 * @param devone
	 * @return
	 */
	 
	public Deviation getStudentdevone(String year,double devone);
	/**
	 * 
	 * @param studentUuid
	 * @param year
	 * @param devone
	 * @return
	 */
	public Deviation getStudentdevtwo(String year,double devtwo);
	 /**
	  * 
	  * @param studentUuid
	  * @param year
	  * @param devone
	  * @return
	  */
	public Deviation getStudentdevthree(String year,double devthree);
	 /**
	  * 
	  * @param studentUuid
	  * @param year
	  * @return
	  */
	public boolean DevExist(String studentUuid,String year);
	 /**
	  * 
	  * @param dev
	  * @return
	  */
	public boolean putDev(Deviation dev,String studentUuid,String year);
	/**
	 * 
	 * @param year
	 * @return
	 */
	
	 
	public List<Deviation> getDeviationList(String year);
	
	
	
	

}
