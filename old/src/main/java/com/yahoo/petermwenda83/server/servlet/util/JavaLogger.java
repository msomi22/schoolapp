/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author peter
 *
 */
public class JavaLogger {

	
	public static void logInfox(String logstr) {

		Logger logger = Logger.getLogger(JavaLogger.class.getName());   
		FileHandler fh;  
		String path = "/home/peter/Desktop/logs/school/school_log.txt";

		try {  

			fh = new FileHandler(path, true);  
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter); 
			logger.setUseParentHandlers(false);
			
			
			logger.info(logstr);  

		} catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  

	}

}
