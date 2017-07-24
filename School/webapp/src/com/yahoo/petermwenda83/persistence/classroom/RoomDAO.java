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

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class RoomDAO extends GenericDAO implements SchoolRoomDAO {
	
	private static RoomDAO roomDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static RoomDAO getInstance(){
		
		if(roomDAO == null){
			roomDAO = new RoomDAO();		
		}
		return roomDAO;
	}
	
	/**  
	 * 
	 */
	public RoomDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public RoomDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolRoomDAO#getroom(java.lang.String, java.lang.String)
	 */
	@Override
	public ClassRoom getroom(String SchoolAccountUuid, String Uuid) {
		ClassRoom classRoom = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassRoom WHERE SchoolAccountUuid = ? AND Uuid =? ;");       
        		
        		){
        	
        	 pstmt.setString(1, SchoolAccountUuid);
        	 pstmt.setString(2, Uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 classRoom  = beanProcessor.toBean(rset,ClassRoom.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting classRoom with Uuid: " + Uuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return classRoom; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolRoomDAO#getroomByRoomName(java.lang.String, java.lang.String)
	 */
	@Override
	public ClassRoom getroomByRoomName(String SchoolAccountUuid, String RoomName) {
		ClassRoom classRoom = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassRoom WHERE SchoolAccountUuid = ? AND RoomName =? ;");       
        		
        		){
        	
        	 pstmt.setString(1, SchoolAccountUuid);
        	 pstmt.setString(2, RoomName);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 classRoom  = beanProcessor.toBean(rset,ClassRoom.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting classRoom with RoomName: " + RoomName);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return classRoom; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolRoomDAO#putroom(com.yahoo.petermwenda83.bean.classroom.ClassRoom)
	 */
	@Override
	public boolean putroom(ClassRoom room) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ClassRoom" 
			        		+"(Uuid, SchoolAccountUuid, RoomName) VALUES (?,?,?);");
		             ){
			   
	            pstmt.setString(1, room.getUuid());
	            pstmt.setString(2, room.getSchoolAccountUuid());
	            pstmt.setString(3, room.getRoomName());	       
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put ClassRoom "+room);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
          success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolRoomDAO#updateroom(com.yahoo.petermwenda83.bean.classroom.ClassRoom)
	 */
	@Override
	public boolean updateroom(ClassRoom room) {
		boolean success = true;
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE ClassRoom SET RoomName = ?"
			        + "WHERE SchoolAccountUuid = ? AND Uuid = ?;");
	               ) {           			 	            
	            pstmt.setString(1, room.getRoomName());
	            pstmt.setString(2, room.getSchoolAccountUuid());
	            pstmt.setString(3, room.getUuid());       
	            pstmt.executeUpdate();

    } catch (SQLException e) {
      logger.error("SQL Exception when updating update ClassRoom  " + room);
      logger.error(ExceptionUtils.getStackTrace(e));
      System.out.println(ExceptionUtils.getStackTrace(e));
      success = false;
   } 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolRoomDAO#deleteroom(com.yahoo.petermwenda83.bean.classroom.ClassRoom)
	 */
	@Override
	public boolean deleteroom(ClassRoom room) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ClassRoom"
	         	      		+ " WHERE Uuid =?;");       
	      		
	      		){
	      	
	      	     pstmt.setString(1, room.getUuid());
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting ClassRoom : " +room);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolRoomDAO#getAllRooms(java.lang.String)
	 */
	@Override
	public List<ClassRoom> getAllRooms(String SchoolAccountUuid) {
		List<ClassRoom> list = new ArrayList<>();

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassRoom WHERE SchoolAccountUuid = ?;");    		   
     	   ) {
         	   pstmt.setString(1, SchoolAccountUuid);           
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, ClassRoom.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting ClassRoom List of a school with schoolAccountUuid " + SchoolAccountUuid ); 
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	
	
	

}
