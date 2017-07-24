
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

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

/**
 * Tests our class with database credentials.
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */

public class TestDBCredentials {
	private DBCredentials dBCredentials;
	@Test
	public void getConnection() throws SQLException {
		System.out.println("connection test"); 
		
	dBCredentials = new DBCredentials("schooldb", "localhost", "school", "AllaManO1", 5432);
		
		Connection con; 
		con = dBCredentials.getConnection();
		System.out.println("Connection is: " + con);
	}

}
