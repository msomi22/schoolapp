

package com.yahoo.petermwenda83.persistence.guardian;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;

/**
 * @author r<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolParentsDAO {
   /**
    * 
    * @param studentUuid
    * @return
    */
	public StudentParent getParent(String studentUuid); 
	 
	  /**
	   * 
	   * @param parent
	   * @return
	   */
	public boolean putParent(StudentParent parent);
	
	 
	/**
	 * 
	 * @param parent
	 * @return
	 */
	public boolean updateParent(StudentParent parent);
	
	 
	 /**
	  * 
	  * @param parent
	  * @return
	  */
	public boolean deleteParent(StudentParent parent);
	
	/**
	 * 
	 * @return
	 */
	public List<StudentParent> getParentList();
	
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public List<StudentParent> getParentListByStudent(String studentUuid);
	/**
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public List<StudentParent> getParentList( int startIndex , int endIndex);
	
	
}
