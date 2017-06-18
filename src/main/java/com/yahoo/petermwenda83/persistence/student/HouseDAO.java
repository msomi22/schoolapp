/**
 * 
 */
package com.yahoo.petermwenda83.persistence.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.student.House;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class HouseDAO extends GenericDAO implements SchoolHouseDAO {

	private static HouseDAO houseDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static HouseDAO getInstance(){
		
		if(houseDAO == null){
			houseDAO = new HouseDAO();		
		}
		return houseDAO;
	}
	
	/**  
	 * 
	 */
	public HouseDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public HouseDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolHouseDAO#getHouse(java.lang.String, java.lang.String)
	 */
	@Override
	public House getHouse(String schoolUuid, String Uuid) {
		House house = null;
		ResultSet rset = null;
		
		  try(   Connection conn = dbutils.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM House"
						+ " WHERE SchoolAccountUuid =? AND Uuid = ?;");
		         ){
			  pstmt.setString(1, schoolUuid); 
			  pstmt.setString(2, Uuid); 
		      rset = pstmt.executeQuery();
		     while(rset.next()){
		    	 house  = beanProcessor.toBean(rset,House.class);
		   }
	        	
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put House with: "+Uuid);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
    
		 }
		return house;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolHouseDAO#putHouse(com.yahoo.petermwenda83.bean.student.House)
	 */
	@Override
	public boolean putHouse(House house) {
		 boolean success = true;
			
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO House" 
			        		+"(Uuid, SchoolAccountUuid,HouseName) VALUES (?,?,?);");
		             ){
			   
	            pstmt.setString(1, house.getUuid());
	            pstmt.setString(2, house.getSchoolAccountUuid());
	            pstmt.setString(3, house.getHouseName());            
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put House: "+house);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolHouseDAO#updateHouse(com.yahoo.petermwenda83.bean.student.House)
	 */
	@Override
	public boolean updateHouse(House house) {
		boolean success = true;
		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE House SET HouseName = ? "
			        + "WHERE Uuid = ? AND SchoolAccountUuid =?;");
	               ) {           			 	            
			   
	            pstmt.setString(1, house.getHouseName());  
	            pstmt.setString(2, house.getUuid());
	            pstmt.setString(3, house.getSchoolAccountUuid());
	            pstmt.executeUpdate();

    } catch (SQLException e) {
      logger.error("SQL Exception when updating update House " + house);
      logger.error(ExceptionUtils.getStackTrace(e));
      System.out.println(ExceptionUtils.getStackTrace(e));
      success = false;
   } 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolHouseDAO#deleteHouse(com.yahoo.petermwenda83.bean.student.House)
	 */
	@Override
	public boolean deleteHouse(House house) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM House"
	         	      		+ " WHERE Uuid =? AND SchoolAccountUuid =?;");       
	      		
	      		){
	      	
	      	     pstmt.setString(1, house.getUuid());
	      	     pstmt.setString(2, house.getSchoolAccountUuid());
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting House : " +house);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolHouseDAO#getHouseList(java.lang.String)
	 */
	@Override
	public List<House> getHouseList(String schoolUuid) {
		 List<House> houseList = null;
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement psmt= conn.prepareStatement("SELECT * FROM House WHERE "
							+ "SchoolAccountUuid = ?;");
					) {
				psmt.setString(1, schoolUuid);
				try(ResultSet rset = psmt.executeQuery();){
				
					houseList = beanProcessor.toBeanList(rset, House.class);
				}
			} catch (SQLException e) {
				logger.error("SQLException when trying to get a House List for school"+schoolUuid);
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e)); 
		    }
			
			return houseList;
	}

}
