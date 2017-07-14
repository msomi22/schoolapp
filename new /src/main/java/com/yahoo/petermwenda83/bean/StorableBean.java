/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.bean;

import java.io.Serializable;
import java.util.Random;

/***
 * This class represents an object in the School System architecture that can be
 * stored in the RDBMS as well as cached.
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StorableBean implements Serializable {

	/**
	 * 
	 */
	public static final long serialVersionUID = new Random().nextLong();
}
