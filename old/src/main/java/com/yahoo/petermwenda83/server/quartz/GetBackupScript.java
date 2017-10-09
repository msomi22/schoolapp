/**
 * 
 */
package com.yahoo.petermwenda83.server.quartz;

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
