

package com.yahoo.petermwenda83.persistence.student;
import java.util.List;

import com.yahoo.petermwenda83.bean.student.Student;



/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentDAO {
	/**
	 * 
	 * @param uuid
	 * @return Student
	 */
	public Student getStudentByuuid(String schoolaccountUuid,String uuid);
	
	/**
	 * 
	 * @param uuid
	 * @return Student
	 */
	public Student getStudentADmNo(String schoolaccountUuid);
	/**
	 * 
	 * @param admno
	 * @return Student
	 */
	public Student getStudentObjByadmNo(String schoolaccountUuid,String admno);
	
	
	  

	   /**
	    * 
	    * @param schoolaccount
	    * @param admno
	    * @return	a {@link List} of {@link Student}s whose admno partly or wholly
	    * matches the admno and belongs to a particular school account. Matching is case 
	    * insensitive. An empty list is returned if no Student matches the admno.
	    */
	  public List<Student> getStudentByAdmNo(String schoolaccountUuid, String admno);
	     
	/**
	  * 
	  * @param student
	  * @return
	  */
	public boolean putStudents(Student student);
	
	/**
	 * 
	 * @param student
	 * @return whether edit was successful or not
	 */
	public boolean updateStudents(Student student);
	
	/**
	 * 
	 * @param student
	 * @return
	 */
	public boolean deleteStudents(Student student);
	
	 
	   /**
	    * 
	    * @param schoolaccountUuid
	    * @param classRoomUuid
	    * @return {@link List} of all {@link Student} per class {@link ClassRoom}, the class is specified by @param classRoomUuid
	    */
	   
	public List<Student> getAllStudents(String schoolaccountUuid,String classRoomUuid);
	
	public List<Student> getAllStudents(String schoolaccountUuid,String classRoomUuid, String statusuuid);
	
	
	/**
	 * 
	 * @param schoolaccountUuid
	 * @return {@link List} of all students in the school
	 */
	public List<Student> getAllStudentList(String schoolaccountUuid);
	  
	/**
	 * 
	 * @param statusuuid
	 * @param studenttype
	 * @param schoolaccountUuid
	 * @return
	 */
	 
	public int getStudentCount(String statusuuid,String studenttype,String schoolaccountUuid);
	/**
	 * 
	 * @param statusuuid
	 * @param schoolaccountUuid
	 * @return
	 */
	public int getStudentCount(String statusuuid,String schoolaccountUuid);
	
	

	
	

}
