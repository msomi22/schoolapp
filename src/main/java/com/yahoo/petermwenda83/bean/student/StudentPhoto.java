
/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package com.yahoo.petermwenda83.bean.student;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * Holds Student's Photo
 * 
 *@author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 */
public class StudentPhoto extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 265718919760185531L;
	private String StudentUuid;
	private byte[] Image;

	/**
	 * 
	 */
	public StudentPhoto() {
		StudentUuid = "";
		Image = new byte[2048]; 
		
	}
	

	/**
	 * @return the studentUuid
	 */
	public String getStudentUuid() {
		return StudentUuid;
	}


	/**
	 * @param studentUuid the studentUuid to set
	 */
	public void setStudentUuid(String studentUuid) {
		StudentUuid = studentUuid;
	}


	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return Image;
	}


	/**
	 * @param image the image to set
	 */
	public void setImage(byte[] image) {
		Image = image;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Student's Photo [getUuid()");
		builder.append(getUuid());
		builder.append(", StudentUuid =");
		builder.append(StudentUuid);
		builder.append(",Image =");
		builder.append(Image);
		builder.append("]");
		return builder.toString(); 
		}
}
