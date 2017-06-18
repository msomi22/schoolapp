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

import com.yahoo.petermwenda83.bean.staff.StaffDetails;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 * 
 */
public class StaffDetailsDAO extends GenericDAO implements SchoolStaffDetailsDAO {
	
	private static StaffDetailsDAO staffDetailsDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	/**
	 * 
	 * @return subjectDAO
	 */
	public static StaffDetailsDAO getInstance(){
		if(staffDetailsDAO == null){
			staffDetailsDAO = new StaffDetailsDAO();		
		}
		return staffDetailsDAO;
	}
	
	/**
	 * 
	 */
	public StaffDetailsDAO() {
		super();
	}

	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public StaffDetailsDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDetailsDAO#getStaffDetail(java.lang.String)
	 */
	public StaffDetails getStaffDetail(String staffUuid) {
		StaffDetails StaffDetail = null;
        ResultSet rset = null;
        try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StaffDetails WHERE staffUuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, staffUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 StaffDetail  = beanProcessor.toBean(rset,StaffDetails.class);
	   }  	
      	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting StaffDetails with staffUuid: " + staffUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return StaffDetail; 
	}
	
	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDetailsDAO#getStaffDetailByemployeeNo(java.lang.String)
	 */
	@Override
	public StaffDetails getStaffDetailByemployeeNo(String employeeNo) {
		StaffDetails StaffDetail =  null;
        ResultSet rset = null;
        try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StaffDetails WHERE employeeNo = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, employeeNo);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 StaffDetail  = beanProcessor.toBean(rset,StaffDetails.class);
	   }  	
      	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting StaffDetails with employeeNo: " + employeeNo);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return StaffDetail; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDetailsDAO#putSStaffDetail(com.yahoo.petermwenda83.bean.staff.StaffDetails)
	 */
	public boolean putSStaffDetail(StaffDetails staffDetail) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StaffDetails" 
			        		+"(Uuid,StaffUuid,EmployeeNo,FirstName,LastName,Surname,Gender,NhifNo,"
			        		+ "NssfNo,Phone,DOB,NationalID,County,SysUser,RegistrationDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
    		){
	            pstmt.setString(1, staffDetail.getUuid());
	            pstmt.setString(2, staffDetail.getStaffUuid());
	            pstmt.setString(3, staffDetail.getEmployeeNo());
	            pstmt.setString(4, staffDetail.getFirstName());
	            pstmt.setString(5, staffDetail.getLastName());
	            pstmt.setString(6, staffDetail.getSurname());
	            pstmt.setString(7, staffDetail.getGender());	            
	            pstmt.setString(8, staffDetail.getNhifNo());
	            pstmt.setString(9, staffDetail.getNssfNo());
	            pstmt.setString(10, staffDetail.getPhone());
	            pstmt.setString(11, staffDetail.getdOB());
	            pstmt.setString(12, staffDetail.getNationalID());
	            pstmt.setString(13, staffDetail.getCounty());
	            pstmt.setString(14, staffDetail.getSysUser());
	            pstmt.setTimestamp(15, new Timestamp(staffDetail.getRegistrationDate().getTime()));
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put StaffDetails: "+staffDetail);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }	
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDetailsDAO#updateSStaffDetail(com.yahoo.petermwenda83.bean.staff.StaffDetails)
	 */
	public boolean updateSStaffDetail(StaffDetails staffDetail) {
		boolean success = true; 
		 try(   Connection conn = dbutils.getConnection();
	      PreparedStatement pstmt = conn.prepareStatement("UPDATE StaffDetails SET EmployeeNo =?,FirstName =?,LastName =?,"
			+ "Surname =?,Gender =? , NhifNo =?, NssfNo =?, Phone =?, dOB =?, NationalID =?, County =?,SysUser =?  WHERE StaffUuid = ? ;");
      		){
			    pstmt.setString(1, staffDetail.getEmployeeNo());
	            pstmt.setString(2, staffDetail.getFirstName());
	            pstmt.setString(3, staffDetail.getLastName());
	            pstmt.setString(4, staffDetail.getSurname());
	            pstmt.setString(5, staffDetail.getGender());	            
	            pstmt.setString(6, staffDetail.getNhifNo());
	            pstmt.setString(7, staffDetail.getNssfNo());
	            pstmt.setString(8, staffDetail.getPhone());
	            pstmt.setString(9, staffDetail.getdOB());
	            pstmt.setString(10, staffDetail.getNationalID());
	            pstmt.setString(11, staffDetail.getCounty());
	            pstmt.setString(12, staffDetail.getSysUser());
	            pstmt.setString(13, staffDetail.getStaffUuid());	            
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to update StaffDetails: "+staffDetail);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDetailsDAO#deleteSStaffDetail(com.yahoo.petermwenda83.bean.staff.StaffDetails)
	 */
	@Override
	public boolean deleteSStaffDetail(StaffDetails staffDetail) {
		// TODO Auto-generated method stub
		return false;
	}

	 /**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDetailsDAO#getSStaffDetailList()
	 */
	public List<StaffDetails> getSStaffDetailList() {
		 List<StaffDetails> list = null;
		  try(   
	      		Connection conn = dbutils.getConnection();
	      		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StaffDetails ;");   
	      		ResultSet rset = pstmt.executeQuery();
	  		) {
	      	
	          list = beanProcessor.toBeanList(rset, StaffDetails.class);

	      } catch(SQLException e){
	      	  logger.error("SQL Exception when getting all StaffDetails");
	          logger.error(ExceptionUtils.getStackTrace(e));
	          System.out.println(ExceptionUtils.getStackTrace(e));
	      }
	   
		return list;
	}

	
}
