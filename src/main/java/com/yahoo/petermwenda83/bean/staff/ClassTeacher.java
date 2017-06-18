/**
 * 
 */
package com.yahoo.petermwenda83.bean.staff;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *  
 */
public class ClassTeacher extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -720546801232129197L;
	private String TeacherUuid;
	private String ClassRoomUuid;
	

	/**
	 * 
	 */
	public ClassTeacher() {
		TeacherUuid = "";
		ClassRoomUuid = "";
	}
	

	/**
	 * @return the teacherUuid
	 */
	public String getTeacherUuid() {
		return TeacherUuid;
	}


	/**
	 * @param teacherUuid the teacherUuid to set
	 */
	public void setTeacherUuid(String teacherUuid) {
		TeacherUuid = teacherUuid;
	}


	/**
	 * @return the classRoomUuid
	 */
	public String getClassRoomUuid() {
		return ClassRoomUuid;
	}


	/**
	 * @param classRoomUuid the classRoomUuid to set
	 */
	public void setClassRoomUuid(String classRoomUuid) {
		ClassRoomUuid = classRoomUuid;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("ClassTeacher");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",TeacherUuid=");
		builder.append(TeacherUuid);
		builder.append(",ClassRoomUuid=");
		builder.append(ClassRoomUuid);
		return builder.toString(); 
		}

}
