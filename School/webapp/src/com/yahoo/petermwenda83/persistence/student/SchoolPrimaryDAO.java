

package com.yahoo.petermwenda83.persistence.student;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.StudentPrimary;

/**
 * @author peter
 *
 */
public interface SchoolPrimaryDAO {
	  /**
	   * 
	   * @param StudentUuid
	   * @return Student StudentPrimary SchoolAccount Details
	   */
	public StudentPrimary getPrimary(String StudentUuid);
	   /**
	    * 
	    * @param Primary
	    * @return
	    */
	public boolean putPrimary(StudentPrimary Primary);
	   /**
	    * 
	    * @param Primary
	    * @return
	    */
	public boolean updatePrimary(StudentPrimary Primary);
	    /**
	     * 
	     * @param Primary
	     * @return
	     */
	public boolean deletePrimary(StudentPrimary Primary);
	    /**
	     * 
	     * @return
	     */
	public List<StudentPrimary> getAllPrimary();

}
