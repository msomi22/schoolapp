/**
 * 
 * 
 */
package com.yahoo.petermwenda83.server.servlet.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Utility used to manage the log file 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class LogUtil {
	
	String user;
	String logDir;
	private Logger logger;

	/**
	 * 
	 */
	public LogUtil() {
		user = System.getProperty("user.name");
		logDir = "/home/"+user+"/school/logs/log.txt";
	}
	
	public void writeLog(String logstr){
		try
		{
		    String filename= logDir;  
		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		    fw.write(logstr + "::::" + new Date()+"::::");//appends the string to the file 
		    fw.close();
		}
		catch(IOException ioe)
		{
			logger.info("IOException while writing log: " + ioe.getMessage()); 
		}
	}
	
}
