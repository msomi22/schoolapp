/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.persistence;

import java.sql.SQLException;

import ke.co.qubintel.school.server.servlet.util.DbPoolUtil;




/**
 * What is common to all data access objects (DAOs).
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class GenericDAO {


	protected DBCredentials dbutils;
	/**
	 * @throws SQLException 
	 * 
	 */
	public GenericDAO()  { 
		dbutils =  DbPoolUtil.getDBCredentials();
	}
	/**
	 * 
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public GenericDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		dbutils = new DBCredentials(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	public void closeConnections() {
		dbutils.closeConnections();
	}


}
