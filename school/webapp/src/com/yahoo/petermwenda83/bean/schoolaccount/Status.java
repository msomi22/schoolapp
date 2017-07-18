
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
package com.yahoo.petermwenda83.bean.schoolaccount;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * Status, either active or inactive
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Status extends StorableBean {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 8415380524706287323L;
	private String status;

	/**
	 * 
	 */
	public Status() {
		status ="";
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Status");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",status=");
		builder.append(status);
		builder.append("]");
		return builder.toString(); 
		}
}
