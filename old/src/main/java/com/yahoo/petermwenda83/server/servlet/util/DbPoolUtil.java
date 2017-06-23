/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.server.servlet.util;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.persistence.DBCredentials;

/**
 * Utility dealing with database connection pooling.
 * <p>
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 * @author <a href="mailto:michael@tawi.mobi">Michael Wakahe</a>
 */
public class DbPoolUtil extends HttpServlet {


	private static DBCredentials dBCredentials; 

	private Logger logger = Logger.getLogger(this.getClass());


	/**
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		dBCredentials = new DBCredentials();
	}


	/**
	 * @return the database credentials class
	 */
	public static DBCredentials getDBCredentials() {
		return dBCredentials;
	}


	/**
	 * 
	 */
	@Override
	public void destroy() {
		logger.info("Now shutting down database pools.");

		dBCredentials.closeConnections();		
	} 


	private static final long serialVersionUID = -7899535368789138778L;
}
