

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
	public StudentParent getParent(String accountId, String studentId); 
	 
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
	public boolean deleteParent(String accountId, String studentId);
	
	/**
	 * 
	 * @return
	 */
	public List<StudentParent> getParents(String accountId);
	
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public List<StudentParent> getParents(String accountId,int startIndex, int endIndex);
	
}
