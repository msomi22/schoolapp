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
public class CommonUtils extends GenericDAO{
	
	private static CommonUtils commonUtils;
	  private final Logger logger = Logger.getLogger(this.getClass());

	  public static CommonUtils getInstance() {
	        if (commonUtils == null) {
	        	commonUtils = new CommonUtils();
	        }
	        return commonUtils;
	    }

	/**
	 * 
	 */
	public CommonUtils() {
		super();
	}
	
	public CommonUtils(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
        super(databaseName, Host, databaseUsername, databasePassword, databasePort);
    }
	
	
	/**
	 * @param SchoolAccountUuid
	 * @return
	 */
	public int getStaffCount(String SchoolAccountUuid) {
        int count = 0;
        
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE SchoolAccountUuid = ?");    		   
 	    ) {
        	pstmt.setString(1, SchoolAccountUuid);           
 	       	ResultSet rset = pstmt.executeQuery();
 	       	
 	       	while(rset.next()){
 	       		count = count + 1;
 	       	}

 	       
        } catch (SQLException e) {
            logger.error("SQLException when getting staff count for SchoolAccount " + SchoolAccountUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        
        return count;
    }
	
	/**
	 * @param SchoolAccountUuid
	 * @return
	 */
	public int getBookCount(String SchoolAccountUuid) {
        int count = 0;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Books WHERE SchoolAccountUuid = ?");    		   
 	    ) {
        	pstmt.setString(1, SchoolAccountUuid);           
 	       	ResultSet rset = pstmt.executeQuery();
 	       	
 	       	while(rset.next()){
 	       		count = count + 1;
 	       	}

        } catch (SQLException e) {
            logger.error("SQLException when getting book count for SchoolAccount  " + SchoolAccountUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        
        return count;
    }
	
	
	/**
	 * 
	 * @return
	 */
	
	public int getParentCount() {
	      int count = 0;
	      ResultSet rset = null;
	      try (
	      		 Connection conn = dbutils.getConnection();
	   	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM StudentParent"); 
		    ) {      
	    	      rset = pstmt.executeQuery();
	    	      while(rset.next()){ 
	 	       		count = rset.getInt("count");
	        	  }
	    	      
	      } catch (SQLException e) {
	          logger.error(ExceptionUtils.getStackTrace(e));
	      }
	      
	      return count;
	  }
	
	
	/**
	 * @return
	 */
	public int getSponsorCount() {
	      int count = 0;
	      ResultSet rset = null;
	      try (
	      		 Connection conn = dbutils.getConnection();
	   	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM StudentSponsor"); 
		    ) {      
	    	      rset = pstmt.executeQuery();
	    	      while(rset.next()){ 
	 	       		count = rset.getInt("count");
	        	  }
	    	      
	      } catch (SQLException e) {
	          logger.error(ExceptionUtils.getStackTrace(e));
	      }
	      
	      return count;
	  }
	
}
