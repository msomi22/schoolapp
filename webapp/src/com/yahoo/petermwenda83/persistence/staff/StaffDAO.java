/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.GenericDAO;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;

/**
 * @author peter
 * 
 */
public class StaffDAO extends GenericDAO implements SchoolStaffDAO {
	
	private static StaffDAO staffDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static StaffDAO getInstance(){
		
		if(staffDAO == null){
			staffDAO = new StaffDAO();		
		}
		return staffDAO;
	}
	
	/**
	 * 
	 */
	public StaffDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public StaffDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaff(java.lang.String)
	 */
	public Staff getStaff(String schoolAccountUuid,String Uuid) {
		Staff staff = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE schoolAccountUuid =? AND Uuid = ?;");       
     		
     		){
     	
     	     pstmt.setString(1, schoolAccountUuid);
     	     pstmt.setString(2, Uuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	    	 staff  = beanProcessor.toBean(rset,Staff.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with Uuid " + Uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return staff; 
	}
	
	
	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffUsername(java.lang.String)
	 */
	@Override
	public Staff getStaffUsername(String username) {
		Staff staff = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE Username = ?;");       
     		
     		){
     	     pstmt.setString(1, username);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	    	 staff  = beanProcessor.toBean(rset,Staff.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with Username: " + username);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return staff; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffByUsername(java.lang.String)
	 */
	public Staff getStaffByUsername(String schoolAccountUuid,String username) {
		Staff staff = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE schoolAccountUuid =? AND Username = ?;");       
     		
     		){
     	
     	     pstmt.setString(1, schoolAccountUuid);
     	     pstmt.setString(2, username);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	    	 staff  = beanProcessor.toBean(rset,Staff.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with Username: " + username);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return staff; 
	}
	
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffByPosition(java.lang.String, java.lang.String)
	 */
	@Override
	public Staff getStaffByPosition(String schoolAccountUuid, String PositionUuid) {
		Staff staff = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE schoolAccountUuid =? AND PositionUuid = ?;");       
     		
     		){
     	
     	     pstmt.setString(1, schoolAccountUuid);
     	     pstmt.setString(2, PositionUuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	    	 staff  = beanProcessor.toBean(rset,Staff.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with PositionUuid: " + PositionUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
        //System.out.println(staff);
		return staff; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#putStaff(com.yahoo.petermwenda83.bean.staff.Staff)
	 */
	public boolean putStaff(Staff staff) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Staff" 
			        		+"(Uuid,SchoolAccountUuid,StatusUuid,Category,PositionUuid,UserName,Password) VALUES (?,?,?,?,?,?,?);");
   		){
	            pstmt.setString(1, staff.getUuid());
	            pstmt.setString(2, staff.getSchoolAccountUuid());
	            pstmt.setString(3, staff.getStatusUuid());
	            pstmt.setString(4, staff.getCategory());
	            pstmt.setString(5, staff.getPositionUuid());
	            pstmt.setString(6, staff.getUserName());
	            pstmt.setString(7, SecurityUtil.getMD5Hash(staff.getPassword()));
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put Staff: "+staff);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }	
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#updateStaff(com.yahoo.petermwenda83.bean.staff.Staff)
	 */
	public boolean updateStaff(Staff staff) {
		boolean success = true; 
		 try(   Connection conn = dbutils.getConnection();
	      PreparedStatement pstmt = conn.prepareStatement("UPDATE Staff SET Category =?,PositionUuid =?,StatusUuid =?,UserName =?,Password =?"
			+ "WHERE SchoolAccountUuid = ? AND Uuid =? ;");
     		){			   
	            
	            pstmt.setString(1, staff.getCategory());
	            pstmt.setString(2, staff.getPositionUuid());
	            pstmt.setString(3, staff.getStatusUuid());
	            pstmt.setString(4, staff.getUserName());
	            pstmt.setString(5, SecurityUtil.getMD5Hash(staff.getPassword()));
	            pstmt.setString(6, staff.getSchoolAccountUuid());
	            pstmt.setString(7, staff.getUuid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to update Staff: "+staff);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#deleteStaff(com.yahoo.petermwenda83.bean.staff.Staff)
	 */
	@Override
	public boolean deleteStaff(Staff staff) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffList(java.lang.String)
	 */
	public List<Staff> getStaffList(String schoolAccountUuid) {
		 List<Staff> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE SchoolAccountUuid = ?;");   
			) {
			 pstmt.setString(1,schoolAccountUuid);

			 try(ResultSet rset = pstmt.executeQuery();){
					
				 list = beanProcessor.toBeanList(rset, Staff.class);
				}
	        
	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting all Staff");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffList(java.lang.String, int, int)
	 */
	@Override
	public List<Staff> getStaffList(String schoolAccountUuid, int startIndex, int endIndex) {
		List<Staff> staffList = new ArrayList<>();
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Staff WHERE "
						+ "SchoolAccountUuid = ? LIMIT ? OFFSET ? ;");
				) {
			psmt.setString(1, schoolAccountUuid);
			psmt.setInt(2, endIndex - startIndex);
			psmt.setInt(3, startIndex);
			
			try(ResultSet rset = psmt.executeQuery();){
				staffList = beanProcessor.toBeanList(rset, Staff.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a staffList.");
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return staffList;		
	}

}
