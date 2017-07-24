/**
 * 
 */
package com.yahoo.petermwenda83.persistence.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.persistence.GenericDAO;




/**
 * @author peter
 *
 */
public class StudentUtils extends GenericDAO {
	
	  private static StudentUtils studentUtils;
	  private final Logger logger = Logger.getLogger(this.getClass());

	  public static StudentUtils getInstance() {
	        if (studentUtils == null) {
	        	studentUtils = new StudentUtils();
	        }

	        return studentUtils;
	    }
	   
	/**
	 * 
	 */
	public StudentUtils() {
		 super();
	}
	
	public StudentUtils(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
        super(databaseName, Host, databaseUsername, databasePassword, databasePort);
    }
	
	
	/**
	 * @param SchoolAccountUuid
	 * @return
	 */
	public int getStudents(String SchoolAccountUuid) {
        int count = 0;
        
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM student WHERE SchoolAccountUuid = ?");    		   
 	    ) {
        	pstmt.setString(1, SchoolAccountUuid);           
 	       	ResultSet rset = pstmt.executeQuery();
 	       	
 	       	while(rset.next()){
 	       		count = count + 1;
 	       //	 System.out.println(count);
 	       	}

 	       
        } catch (SQLException e) {
            //logger.error("SQLException when getting count of SchoolAccount with SchoolAccountUuid: " + SchoolAccountUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        
        return count;
    }
    
	
	 /**
	 * @param accountuuid
	 * @return
	 */
	public int getIncomingCount(String accountuuid) {
	        int count=0;

	        try ( Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("SELECT count(*) FROM Student WHERE SchoolAccountUuid = ?;");
	        		){           
	            
	            pstmt.setString(1, accountuuid);
	                try(ResultSet rset = pstmt.executeQuery();){
	            
	            rset.next();
	            count = count + rset.getInt(1);
	            //System.out.println(count);
	                }

	        } catch (SQLException e) {
	            logger.error("SQLException while getting all incoming count of schoolaccount with uuid '"
	                    + accountuuid + "'");
	            logger.error(ExceptionUtils.getStackTrace(e));

	        } 
	        return count;
	    }
	
	
	
	
	
	
	

}
