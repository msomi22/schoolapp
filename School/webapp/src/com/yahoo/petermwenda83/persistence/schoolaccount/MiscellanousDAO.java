/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.schoolaccount.Miscellanous;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class MiscellanousDAO extends GenericDAO  implements SchoolMiscellanousDAO {
	
	private static MiscellanousDAO miscellanousDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static MiscellanousDAO getInstance(){
		if(miscellanousDAO == null){
			miscellanousDAO = new MiscellanousDAO();		
		}
		return miscellanousDAO;
	}
	
	/**
	 * 
	 */
	public MiscellanousDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public MiscellanousDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolMiscellanousDAO#getMiscellanous(java.lang.String)
	 */
	@Override
	public Miscellanous getMiscellanous(String Uuid) {
		Miscellanous miscellanous = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Miscellanous WHERE Uuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, Uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 miscellanous  = beanProcessor.toBean(rset,Miscellanous.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting SchoolAccount with Uuid: " + Uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return miscellanous; 
	}
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolMiscellanousDAO#getKey(java.lang.String)
	 */
	@Override
	public Miscellanous getKey(String schoolAccountUuid,String key) {
		Miscellanous miscellanous = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Miscellanous WHERE schoolAccountUuid =? AND key = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, schoolAccountUuid);
     	 pstmt.setString(2, key);
	      rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 miscellanous  = beanProcessor.toBean(rset,Miscellanous.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting SchoolAccount with key: " + key);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return miscellanous; 
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolMiscellanousDAO#getMisc(java.lang.String)
	 */
	@Override
	public Miscellanous getMisc(String schoolAccountUuid) {
		Miscellanous miscellanous = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Miscellanous WHERE schoolAccountUuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, schoolAccountUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 miscellanous  = beanProcessor.toBean(rset,Miscellanous.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting SchoolAccount with schoolAccountUuid: " + schoolAccountUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return miscellanous; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolMiscellanousDAO#putMiscellanous(com.yahoo.petermwenda83.bean.schoolaccount.Miscellanous)
	 */
	@Override
	public boolean putMiscellanous(Miscellanous misc) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Miscellanous (Uuid,SchoolAccountUuid,Key,Value) VALUES (?,?,?,?);");
    		){
	            pstmt.setString(1, misc.getUuid());
	            pstmt.setString(2, misc.getSchoolAccountUuid());
	            pstmt.setString(3, misc.getKey());
	            pstmt.setString(4, misc.getValue());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put Miscellanous: "+misc);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolMiscellanousDAO#updateMiscellanous(com.yahoo.petermwenda83.bean.schoolaccount.Miscellanous)
	 */
	@Override
	public boolean updateMiscellanous(Miscellanous misc) {
		boolean success = true; 
		 try(   Connection conn = dbutils.getConnection();
	      PreparedStatement pstmt = conn.prepareStatement("UPDATE Miscellanous SET Value =? WHERE SchoolAccountUuid =? AND Uuid =?;");
      		){
	            pstmt.setString(1, misc.getValue());
	            pstmt.setString(2, misc.getSchoolAccountUuid());
	            pstmt.setString(3, misc.getUuid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to update Miscellanous: "+misc);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolMiscellanousDAO#getMiscellanousList(java.lang.String)
	 */
	@Override
	public List<Miscellanous> getMiscellanousList(String schoolAccountUuid) {
		 List<Miscellanous> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Miscellanous WHERE SchoolAccountUuid = ?;");   
			) {
			 pstmt.setString(1,schoolAccountUuid);

			 try(ResultSet rset = pstmt.executeQuery();){
				 list = beanProcessor.toBeanList(rset, Miscellanous.class);
				}
	        
	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting all Miscellanous");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
		return list;
	}

	
}
