/**
 * 
 */
package com.yahoo.petermwenda83.persistence.classroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.classroom.Stream;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class StreamDAO extends GenericDAO implements SchoolStreamDAO {
	
	private static StreamDAO streamDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static StreamDAO getInstance(){
		
		if(streamDAO == null){
			streamDAO = new StreamDAO();		
		}
		return streamDAO;
	}
	
	/**  
	 * 
	 */
	public StreamDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public StreamDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolStreamDAO#getroom(java.lang.String, java.lang.String)
	 */
	@Override
	public Stream getStream(String accountId,String uuid) {
		Stream stream = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Stream WHERE uuid =? ;");       
        		
        		){
        	
        	// pstmt.setString(1, accountId);
        	 pstmt.setString(1, uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 stream  = beanProcessor.toBean(rset,Stream.class);
	   }
        
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Stream for  accountId " + accountId + " and uuid " + uuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return stream; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolStreamDAO#getroomByRoomName(java.lang.String, java.lang.String)
	 */
	@Override
	public Stream getStreamByDesc(String accountId, String description) {
		Stream stream = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Stream WHERE accountId = ? AND description =? ;");       
        		
        		){
        	
        	 pstmt.setString(1, accountId);
        	 pstmt.setString(2, description);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 stream  = beanProcessor.toBean(rset,Stream.class);
	   }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Stream for accountId " + accountId + " and description " + description);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return stream; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolStreamDAO#putroom(com.yahoo.petermwenda83.bean.classroom.ClassRoom)
	 */
	@Override
	public boolean putStream(Stream stream) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Stream" 
			        		+"(uuid, accountId, classRoomId , description) VALUES (?,?,?,?);");
		             ){
			   
	            pstmt.setString(1, stream.getUuid());
	            pstmt.setString(2, stream.getAccountId());
	            pstmt.setString(3, stream.getClassRoomId());	    
	            pstmt.setString(4, stream.getDescription());	    
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put Stream " + stream);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
          success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolStreamDAO#updateroom(com.yahoo.petermwenda83.bean.classroom.ClassRoom)
	 */
	@Override
	public boolean updateStream(Stream stream) {
		boolean success = true;
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE Stream SET description = ?"
			        + "WHERE accountId = ? AND uuid = ?;");
	               ) {           			 	            
			   	    
	            pstmt.setString(1, stream.getDescription());
	            pstmt.setString(2, stream.getAccountId());
	            pstmt.setString(3, stream.getUuid());
	            pstmt.executeUpdate();

    } catch (SQLException e) {
      logger.error("SQL Exception when updating update Stream  " + stream);
      logger.error(ExceptionUtils.getStackTrace(e));
      System.out.println(ExceptionUtils.getStackTrace(e));
      success = false;
   } 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolStreamDAO#deleteroom(com.yahoo.petermwenda83.bean.classroom.ClassRoom)
	 */
	@Override
	public boolean deleteStream(String accountId,String uuid) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Stream"
	         	      		+ " WHERE accountId = ? AND uuid =?;");       
	      		){
	      	
	      	     pstmt.setString(1, accountId);
	      	     pstmt.setString(2, uuid);
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting Stream for accountId " + accountId + " with stream id " + uuid);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolStreamDAO#getAllRooms(java.lang.String)
	 */
	@Override
	public List<Stream> getStreamList(String accountId) {
		List<Stream> list = new ArrayList<>();

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Stream WHERE accountId = ?;");    		   
     	   ) {
         	   pstmt.setString(1, accountId);           
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Stream.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Stream List for account  " + accountId ); 
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}
	
	
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolStreamDAO#getStreamList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Stream> getStreamList(String accountId, String classRoomId) {
		List<Stream> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Stream WHERE accountId = ? AND classroomid =?;");    		   
     	   ) {
         	   pstmt.setString(1, accountId);  
         	   pstmt.setString(2, classRoomId);  
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Stream.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Stream List for account  " + accountId  + " and classRoomId " + classRoomId); 
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	
	
	

}
