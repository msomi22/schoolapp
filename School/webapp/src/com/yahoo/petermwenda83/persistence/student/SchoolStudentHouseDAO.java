

package com.yahoo.petermwenda83.persistence.student;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.StudentHouse;

/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentHouseDAO {
	

	
	   /**
	    * 
	    * @param house
	    * @return whether StudentHouse was got successfully
	    */
	public StudentHouse getHouse(String studentuuid);
	

	   /**
	    * 
	    * @param house
	    * @return whether StudentHouse was got successfully
	    */
	public StudentHouse getHouseByHoudeId(String HouseUuid,String studentuuid);
	
	 /**
	  * 
	  * @param HouseUuid
	  * @return
	  */
	public List<StudentHouse> getHouseList(String HouseUuid);
	   
		/**
		 * 
		 * @param studentSuper
		 * @param studentHouse 
		 * 
		 */
		public boolean putHouse(StudentHouse studentHouse);
		
		/**
		  * 
		  * @param studentSuper
		  * @param studentHouse
		  * 
		  */
		public boolean updatetHouse(StudentHouse studentHouse);
		
		/**
		   * 
		   * @param studentSuper
		   * @param studentHouse
		   * 
		   */
		public boolean deleteHouse(StudentHouse studentHouse);
		
		  /**
	     * 
	     * @return AllHouses
	     */
	  public List<StudentHouse> getHouseList();

}
