
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
package com.yahoo.petermwenda83.persistence;

import java.sql.SQLException;

import com.yahoo.petermwenda83.server.servlet.util.DbPoolUtil;




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
