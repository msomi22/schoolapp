/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 * 
 */
public class StaffDAO extends GenericDAO implements SchoolStaffDAO {
	
	private static StaffDAO staffDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	/**
	 * 
	 * @return subjectDAO
	 */
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
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public StaffDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffDetail(java.lang.String)
	 */
	public Staff getStaff(String accountId, String Uuid) {
		Staff StaffDetail = null;
        ResultSet rset = null;
        try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE staffUuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, staffUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 StaffDetail  = beanProcessor.toBean(rset,Staff.class);
	   }  	
      	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with staffUuid: " + staffUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return StaffDetail; 
	}
	
	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffDetailByemployeeNo(java.lang.String)
	 */
	@Override
	public Staff getStaffByStaffNo(String accountId, String staffNo) {
		Staff StaffDetail =  null;
        ResultSet rset = null;
        try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE employeeNo = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, employeeNo);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 StaffDetail  = beanProcessor.toBean(rset,Staff.class);
	   }  	
      	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with employeeNo: " + employeeNo);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return StaffDetail; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#putSStaffDetail(com.yahoo.petermwenda83.bean.staff.Staff)
	 */
	public boolean putStaff(Staff staff) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Staff" 
			        		+"(Uuid,StaffUuid,EmployeeNo,FirstName,LastName,Surname,Gender,NhifNo,"
			        		+ "NssfNo,Phone,DOB,NationalID,County,SysUser,RegistrationDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
    		){
	            pstmt.setString(1, staff.getUuid());
	            pstmt.setString(2, staff.getStaffUuid());
	            pstmt.setString(3, staff.getEmployeeNo());
	            pstmt.setString(4, staff.getFirstName());
	            pstmt.setString(5, staff.getLastName());
	            pstmt.setString(6, staff.getSurname());
	            pstmt.setString(7, staff.getGender());	            
	            pstmt.setString(8, staff.getNhifNo());
	            pstmt.setString(9, staff.getNssfNo());
	            pstmt.setString(10, staff.getPhone());
	            pstmt.setString(11, staff.getdOB());
	            pstmt.setString(12, staff.getNationalID());
	            pstmt.setString(13, staff.getCounty());
	            pstmt.setString(14, staff.getSysUser());
	            pstmt.setTimestamp(15, new Timestamp(staff.getRegistrationDate().getTime()));
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put staff: " + staff);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }	
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#updateSStaffDetail(com.yahoo.petermwenda83.bean.staff.Staff)
	 */
	public boolean updateStaff(Staff staff) {
		boolean success = true; 
		 try(   Connection conn = dbutils.getConnection();
	      PreparedStatement pstmt = conn.prepareStatement("UPDATE Staff SET EmployeeNo =?,FirstName =?,LastName =?,"
			+ "Surname =?,Gender =? , NhifNo =?, NssfNo =?, Phone =?, dOB =?, NationalID =?, County =?,SysUser =?  WHERE StaffUuid = ? ;");
      		){
			    pstmt.setString(1, staff.getEmployeeNo());
	            pstmt.setString(2, staff.getFirstName());
	            pstmt.setString(3, staff.getLastName());
	            pstmt.setString(4, staff.getSurname());
	            pstmt.setString(5, staff.getGender());	            
	            pstmt.setString(6, staff.getNhifNo());
	            pstmt.setString(7, staff.getNssfNo());
	            pstmt.setString(8, staff.getPhone());
	            pstmt.setString(9, staff.getdOB());
	            pstmt.setString(10, staff.getNationalID());
	            pstmt.setString(11, staff.getCounty());
	            pstmt.setString(12, staff.getSysUser());
	            pstmt.setString(13, staff.getStaffUuid());	            
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to update staff " + staff);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#deleteSStaffDetail(com.yahoo.petermwenda83.bean.staff.Staff)
	 */
	@Override
	public boolean deleteStaff(String accountId, String Uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	 /**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getSStaffDetailList()
	 */
	public List<Staff> getStaff(String accountId) {
		 List<Staff> list = null;
		  try(   
	      		Connection conn = dbutils.getConnection();
	      		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Staff ;");   
	      		ResultSet rset = pstmt.executeQuery();
	  		) {
	      	
	          list = beanProcessor.toBeanList(rset, Staff.class);

	      } catch(SQLException e){
	      	  logger.error("SQL Exception when getting all Staff");
	          logger.error(ExceptionUtils.getStackTrace(e));
	          System.out.println(ExceptionUtils.getStackTrace(e));
	      }
	   
		return list;
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaff(java.lang.String, int, int)
	 */
	@Override
	public List<Staff> getStaff(String accountId, int startIndex, int endIndex) {
		 List<Staff> list = null;
		  try(   
	      		Connection conn = dbutils.getConnection();
	      		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Staff ;");   
	      		ResultSet rset = pstmt.executeQuery();
	  		) {
	      	
	          list = beanProcessor.toBeanList(rset, Staff.class);

	      } catch(SQLException e){
	      	  logger.error("SQL Exception when getting all Staff");
	          logger.error(ExceptionUtils.getStackTrace(e));
	          System.out.println(ExceptionUtils.getStackTrace(e));
	      }
	   
		return list;
	}

	
}
