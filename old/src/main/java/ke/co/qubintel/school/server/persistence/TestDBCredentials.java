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
