/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.yahoo.petermwenda83.bean.student.Student;

/**
 * @author peter
 *
 */
@XmlRootElement(name = "student")  //only needed if we also want to generate XML     
public class APIStudent extends Student{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public APIStudent() {
		
	}


}
