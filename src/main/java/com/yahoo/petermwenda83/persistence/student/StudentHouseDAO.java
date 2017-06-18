/**##########################################################
/*************************************************************
 * ##########################################################
 * ##########################################################
 * ### This is My Forth Year Project#########################
 * ####### Maasai Mara University############################
 * ####### Year:2015-2016 ###################################
 * ####### Although this software is open source, No one
 * ###### should assume it ownership and copy paste 
 * ###### the code herein without the owner's approval.
 * ###################################################
 * ##########################################################
 * ##### SchoolAccount Management System ###########################
 * ##### Uses MVC Model, Postgres database, ant for 
 * ##### project management and other technologies.
 * ##### It consist Desktop application and a web
 * #### application all sharing the same DB.
 * ##########################################################
 * 
 */
package com.yahoo.petermwenda83.persistence.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.student.StudentHouse;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentHouseDAO extends GenericDAO implements SchoolStudentHouseDAO {
	
	private static StudentHouseDAO studentHouseDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static StudentHouseDAO getInstance(){
		
		if(studentHouseDAO == null){
			studentHouseDAO = new StudentHouseDAO();		
		}
		return studentHouseDAO;
	}
	
	/** 
	 * 
	 */
	public StudentHouseDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public StudentHouseDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentHouseDAO#getHouse(java.lang.String)
	 */
	@Override
	public StudentHouse getHouse(String studentuuid) {
		StudentHouse studentHouse = null;
		ResultSet rset = null;
		
		  try(   Connection conn = dbutils.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentHouse"
						+ " WHERE StudentUuid =?;");
		         ){
			  pstmt.setString(1, studentuuid); 
		      rset = pstmt.executeQuery();
		     while(rset.next()){
		    	 studentHouse  = beanProcessor.toBean(rset,StudentHouse.class);
		   }
	        	
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to putStudent with studentuuid: "+studentuuid);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
    
		 }
		return studentHouse;
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentHouseDAO#getHouseByHoudeId(java.lang.String)
	 */
	@Override
	public StudentHouse getHouseByHoudeId(String HouseUuid,String studentuuid) {
		StudentHouse studentHouse = null;
		ResultSet rset = null;
		
		  try(   Connection conn = dbutils.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentHouse"
						+ " WHERE HouseUuid =? AND studentuuid =?;");
		         ){
			  pstmt.setString(1, HouseUuid); 
			  pstmt.setString(2, studentuuid); 
		      rset = pstmt.executeQuery();
		     while(rset.next()){
		    	 studentHouse  = beanProcessor.toBean(rset,StudentHouse.class);
		   }
	        	
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to putStudent with HouseUuid: "+HouseUuid);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
    
		 }
		return studentHouse;
	}

   
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentHouseDAO#getHouseList(java.lang.String)
	 */
	@Override
	public List<StudentHouse> getHouseList(String HouseUuid) {
		List<StudentHouse> houseList = null ;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentHouse WHERE "
						+ "HouseUuid = ?;");
				) {
			psmt.setString(1, HouseUuid);
			try(ResultSet rset = psmt.executeQuery();){
			
				houseList = beanProcessor.toBeanList(rset, StudentHouse.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a StudentHouse List for "+HouseUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return houseList;
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentHouseDAO#putHouse(com.yahoo.petermwenda83.bean.student.StudentHouse)
	 */
	@Override
	public boolean putHouse(StudentHouse studentHouse) {
		 boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentHouse" 
			        		+"(Uuid, StudentUuid, HouseUuid,SysUser,DateIn) VALUES (?,?,?,?,?);");
		             ){
			   
	            pstmt.setString(1, studentHouse.getUuid());
	            pstmt.setString(2, studentHouse.getStudentUuid());
	            pstmt.setString(3, studentHouse.getHouseUuid());	       
	            pstmt.setString(4, studentHouse.getSysUser());
	            pstmt.setTimestamp(5, new Timestamp(studentHouse.getDateIn().getTime()));
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to putStudent: "+studentHouse);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentHouseDAO#updatetHouse(com.yahoo.petermwenda83.bean.student.StudentHouse, java.lang.String)
	 */
	@Override
	public boolean updatetHouse(StudentHouse studentHouse) {
		boolean success = true;
		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentHouse SET HouseUuid = ?,SysUser = ?,DateIn = ? "
			        + "WHERE StudentUuid = ?;");
	               ) {           			 	            
	            pstmt.setString(1, studentHouse.getHouseUuid());
	            pstmt.setString(2, studentHouse.getSysUser());
	            pstmt.setTimestamp(3, new Timestamp(studentHouse.getDateIn().getTime()));
	            pstmt.setString(4, studentHouse.getStudentUuid());	           
	            pstmt.executeUpdate();

      } catch (SQLException e) {
        logger.error("SQL Exception when updating editStudent " + studentHouse);
        logger.error(ExceptionUtils.getStackTrace(e));
        System.out.println(ExceptionUtils.getStackTrace(e));
        success = false;
     } 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentHouseDAO#deleteHouse(com.yahoo.petermwenda83.bean.student.StudentHouse)
	 */
	@Override
	public boolean deleteHouse(StudentHouse studentHouse) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentHouse"
	         	      		+ " WHERE StudentUuid =?;");       
	      		
	      		){
	      	
	      	     pstmt.setString(1, studentHouse.getStudentUuid());
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting house : " +studentHouse);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentHouseDAO#getHouseList()
	 */
	@Override
	public List<StudentHouse> getHouseList() {
		List<StudentHouse> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentHouse;");   
	  		ResultSet rset = pstmt.executeQuery();
			) {
	  	
	      list = beanProcessor.toBeanList(rset, StudentHouse.class);

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting all StudentHouse");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
	  return list;
	}

	
	

}
