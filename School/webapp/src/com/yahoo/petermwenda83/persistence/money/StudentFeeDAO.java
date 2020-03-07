/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class StudentFeeDAO extends GenericDAO implements SchoolStudentFeeDAO {

	private static StudentFeeDAO studentFeeDAO;
	
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static StudentFeeDAO getInstance() {
		if(studentFeeDAO == null){
			studentFeeDAO = new StudentFeeDAO();		
			}
		return studentFeeDAO;
	}
	
	
	public StudentFeeDAO() {
		super();
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public StudentFeeDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentFeeDAO#getStudentFeeByStudentUuid(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentFee getStudentFeeByStudentUuid(String schoolAccountUuid, String studentUuid,String Term,String Year) {
		StudentFee studentFee = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentFee WHERE schoolAccountUuid = ?"
           	      		+ " AND studentUuid =? AND Term =? AND Year =? ;");       
        		
        		){
        	
        	 pstmt.setString(1, schoolAccountUuid);
        	 pstmt.setString(2, studentUuid);
        	 pstmt.setString(3, Term);
        	 pstmt.setString(4, Year);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentFee  = beanProcessor.toBean(rset,StudentFee.class);
	   }
       }catch(SQLException e){
        	 logger.error("SQL Exception when getting studentFee for student: " + studentUuid +"and schoolAccountUuid"+schoolAccountUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return studentFee; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentFeeDAO#getStudentFeeByTransactionId(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentFee getStudentFeeByTransactionId(String schoolAccountUuid, String transactionID) {
		StudentFee studentFee = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentFee WHERE schoolAccountUuid = ?"
           	      		+ " AND transactionID =?;");       
        		
        		){
        	
        	 pstmt.setString(1, schoolAccountUuid);
        	 pstmt.setString(2, transactionID);
        	
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentFee  = beanProcessor.toBean(rset,StudentFee.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting studentFee with transactionID: " + transactionID +"and schoolAccountUuid"+schoolAccountUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return studentFee; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentFeeDAO#putStudentFee(com.yahoo.petermwenda83.bean.money.StudentFee)
	 */
	@Override
	public boolean putStudentFee(StudentFee studentFee) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentFee" 
			        		+"(Uuid,SchoolAccountUuid,StudentUuid,TransactionID,AmountPaid,DatePaid,Term,Year,StudentType,SystemUser) VALUES (?,?,?,?,?,?,?,?,?,?);");
		             ){
			   
	            pstmt.setString(1, studentFee.getUuid());
	            pstmt.setString(2, studentFee.getSchoolAccountUuid());
	            pstmt.setString(3, studentFee.getStudentUuid());
	            pstmt.setString(4, studentFee.getTransactionID());
	            pstmt.setDouble(5, studentFee.getAmountPaid());
	            pstmt.setTimestamp(6, new Timestamp(studentFee.getDatePaid().getTime()));
	            pstmt.setString(7, studentFee.getTerm());
	            pstmt.setString(8, studentFee.getYear());
	            pstmt.setString(9, studentFee.getStudentType());
	            pstmt.setString(10, studentFee.getSystemUser());
	            pstmt.executeUpdate();
	            
	            
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put StudentFee "+studentFee);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentFeeDAO#updateStudentFee(com.yahoo.petermwenda83.bean.money.StudentFee)
	 */
	@Override
	public boolean updateStudentFee(StudentFee studentFee) {
		boolean success = true;
		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentFee SET TransactionID = ?,AmountPaid = ?,DatePaid = ?,"
			        + "SystemUser =?,Term =?,Year =?,StudentType =? WHERE SchoolAccountUuid = ? AND StudentUuid =? AND TransactionID = ?;");
	               ) {           			 	            
			   
	           
	            pstmt.setString(1, studentFee.getTransactionID());
	            pstmt.setDouble(2, studentFee.getAmountPaid());
	            pstmt.setTimestamp(3, new Timestamp(studentFee.getDatePaid().getTime())); 
	            pstmt.setString(4, studentFee.getSystemUser());
	            pstmt.setString(5, studentFee.getTerm());
	            pstmt.setString(6, studentFee.getYear());
	            pstmt.setString(7, studentFee.getStudentType());
	            pstmt.setString(8, studentFee.getSchoolAccountUuid());
	            pstmt.setString(9, studentFee.getStudentUuid());
	            pstmt.setString(10, studentFee.getTransactionID());
	            pstmt.executeUpdate();

  } catch (SQLException e) {
        logger.error("SQL Exception when updating StudentFee " + studentFee);
        logger.error(ExceptionUtils.getStackTrace(e));
        System.out.println(ExceptionUtils.getStackTrace(e));
        success = false;
 } 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentFeeDAO#deleteStudentFee(com.yahoo.petermwenda83.bean.money.StudentFee)
	 */
	@Override
	public boolean deleteStudentFee(StudentFee studentFee) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentFee"
	         	      		+ " WHERE SchoolAccountUuid =? AND StudentUuid =?;");       
	      		
	      		){
	      	
	      	     pstmt.setString(1, studentFee.getSchoolAccountUuid());
	      	     pstmt.setString(2, studentFee.getStudentUuid());
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting StudentFee : " +studentFee);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentFeeDAO#getStudentFeeList(java.lang.String)
	 */
	@Override
	public List<StudentFee> getStudentFeeList(String schoolAccountUuid,String Term,String Year) {
		 List<StudentFee> studentFeeList = null;
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentFee WHERE "
							+ "SchoolAccountUuid = ? AND Term =? AND Year =?;");
					) {
				psmt.setString(1, schoolAccountUuid);
				psmt.setString(2, Term);
				psmt.setString(3, Year);
				try(ResultSet rset = psmt.executeQuery();){
				
					studentFeeList = beanProcessor.toBeanList(rset, StudentFee.class);
				}
			} catch (SQLException e) {
				logger.error("SQLException when trying to get StudentFee List for school " + schoolAccountUuid);
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e)); 
		    }
			
			return studentFeeList;
	}

	@Override
	public List<StudentFee> getStudentFeeByStudentUuidList(String schoolAccountUuid, String studentUuid, String Term, String Year) {
		List<StudentFee> studentFeeList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentFee WHERE "
						+ "SchoolAccountUuid = ? AND studentUuid =? AND Term =? AND Year =?;");
				) {
			psmt.setString(1, schoolAccountUuid);
			psmt.setString(2, studentUuid);
			psmt.setString(3, Term);
			psmt.setString(4, Year);
			try(ResultSet rset = psmt.executeQuery();){
			
				studentFeeList = beanProcessor.toBeanList(rset, StudentFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get StudentFee List for school " + schoolAccountUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return studentFeeList;
	}


	@Override
	public List<StudentFee> getStudentFeeDistinctList(String schoolAccountUuid, String Term, String Year) {
		List<StudentFee> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT studentuuid FROM StudentFee WHERE"
        		 		+ " SchoolAccountUuid = ? AND Term = ? AND Year = ?;");
     	   ) {
         	   pstmt.setString(1, schoolAccountUuid);      
         	   pstmt.setString(2, Term); 
        	   pstmt.setString(3, Year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, StudentFee.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting DISTINCT StudentUuid List  of StudentFee for school" + schoolAccountUuid); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}

}
