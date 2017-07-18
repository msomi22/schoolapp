/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.util.List;

import com.yahoo.petermwenda83.bean.staff.TeacherDuties;

/**
 * @author peter
 *
 */
public interface SchoolTeacherDutiesDAO {
	
	public TeacherDuties getTeacherDuties(String teacherUuid);
	
	public boolean putTeacherDuties(TeacherDuties teacherDuty);
	
	public boolean updateTeacherDuties(TeacherDuties teacherDuty);
	
	public boolean deleteTeacherDuties(TeacherDuties teacherDuty);
	
	public List<TeacherDuties> getTeacherDutyList();
	

}
