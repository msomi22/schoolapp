
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
package com.yahoo.petermwenda83.bean.staff;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * A staff Has A position , Either a Principal ,Deputy, Hod, Teacher , ...
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Position extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -920169356050037976L;
	private String  position;
	    
	public Position() {
		position ="";
	}

	
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Position");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",position=");
		builder.append(position);
		return builder.toString(); 
		}
}
