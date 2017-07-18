package com.yahoo.petermwenda83.server.servlet.export;

import com.yahoo.petermwenda83.persistence.GenericDAO;

import java.io. BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;


public class DbFileUtils extends GenericDAO{
	
    private static DbFileUtils dbFileUtils;
    private Logger logger = Logger.getLogger(this.getClass());

    
    /**
     * Get the singleton instance
     *
     * @return {@link DbFileUtils}
     */
    public static DbFileUtils getInstance() {
        if (dbFileUtils == null) {
        	dbFileUtils = new DbFileUtils();
        }

        return dbFileUtils;
    }

    
    /**
     *
     */
    protected DbFileUtils() {
        super();
    }
    

    /**
     * @param dbName
     * @param dbHost
     * @param dbUsername
     * @param dbPassword
     * @param dbPort
     */
    public DbFileUtils(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
        super(databaseName, Host, databaseUsername, databasePassword, databasePort);
    }

    
    /**
     * This is used to export the results of an SQL query into a CSV text file.
     * 
     * @param sqlQuery
     * @param fileName this should include the full path of the file e.g. /tmp/myFile.csv
     * @param delimiter
     * 
     * @return whether the action was successful or not
     */
    public boolean sqlResultToCSV(String sqlQuery, String fileName, char delimiter) {
    	boolean success = true;
    	String sanitizedQuery = StringUtils.remove(sqlQuery, ';');
    	
    	BufferedWriter writer;
    	
        try(
        		// Return a database connection that is not pooled
                // to enable the connection to be cast to BaseConnection
        		Connection conn = dbutils.getJdbcConnection();
        		) {
        	
            FileUtils.deleteQuietly(new File(fileName));
            FileUtils.touch(new File(fileName));
            writer = new BufferedWriter(new FileWriter(fileName));

            CopyManager copyManager = new CopyManager((BaseConnection) conn);

            StringBuffer query = new StringBuffer("COPY (")
                    .append(sanitizedQuery)
                    .append(") to STDOUT WITH DELIMITER '")
                    .append(delimiter)
                    .append("'");

            copyManager.copyOut(query.toString(), writer);
            writer.close();

        } catch (SQLException e) {
            logger.error("SQLException while exporting results of query '" + sqlQuery
                    + "' to file '" + fileName + "'.");
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;

        } catch (IOException e) {
            logger.error("IOException while exporting results of query '" + sqlQuery
                    + "' to file '" + fileName + "'.");
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
        } 

        return success;
    }
}