/**
 * 
 */
package com.yahoo.petermwenda83.bean.staff;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class HouseMaster extends StorableBean{
	
	
	private String TeacherUuid;
	private String ClassRoomUuid;
	

	/**
	 * 
	 */
	public HouseMaster() {
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
		builder.append("House Master");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",TeacherUuid=");
		builder.append(TeacherUuid);
		builder.append(",ClassRoomUuid=");
		builder.append(ClassRoomUuid);
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = 731486305621670731L;
}
