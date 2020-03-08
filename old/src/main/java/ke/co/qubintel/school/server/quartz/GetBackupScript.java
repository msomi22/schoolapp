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
package ke.co.qubintel.school.server.quartz;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/** 
 * 
 * @author peter
 *
 */
public class GetBackupScript  extends HttpServlet{
	
	
	private static String backupFile = "";


	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		WriteToFile.createScript();
		
		
		try {
			
			Thread.sleep(2);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		backupFile  = WriteToFile.FILENAME;
		
		//this.getServletContext().getRealPath("/WEB-INF/classes/backup.sh");  
		
	}
	
	
	public static String getBackupFile() {
		return backupFile;
	}

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
