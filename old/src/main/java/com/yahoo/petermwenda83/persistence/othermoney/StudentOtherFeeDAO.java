package com.yahoo.petermwenda83.persistence.othermoney;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;
import com.yahoo.petermwenda83.persistence.GenericDAO;

public class StudentOtherFeeDAO extends GenericDAO implements SchoolStudentOtherFeeDAO {
	
	private static StudentOtherFeeDAO studentOtherFeeDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static StudentOtherFeeDAO getInstance() {
		if(studentOtherFeeDAO == null){
			studentOtherFeeDAO = new StudentOtherFeeDAO();		
			}
		return studentOtherFeeDAO;
	}
	
	public StudentOtherFeeDAO() {
		super();
		
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public StudentOtherFeeDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#getStudentOtherMonies(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentOtherFee getStudentOtherFee(String accountId, String studentId , String otherFeeId) {
		StudentOtherFee studentOtherFee = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentOtherFee WHERE accountId =? AND studentId =? AND otherFeeId =?;");       
        		
        		){
        	
        	 pstmt.setString(1, accountId);
        	 pstmt.setString(2, studentId);
        	 pstmt.setString(3, otherFeeId);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentOtherFee  = beanProcessor.toBean(rset,StudentOtherFee.class);
	   }
       	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting StudentOtherFee for studentId " + studentId );
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return studentOtherFee; 
	}
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#putStudentOtherMonies(com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee)
	 */
	@Override
	public boolean putStudentOtherFee(StudentOtherFee studentOtherFee) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentOtherFee" 
			        		+"(uuid, accountId, studentId, otherFeeId, amountPiad, payMode, datePaid) VALUES (?,?,?,?,?,?,?);");
     		){
			   
	            pstmt.setString(1, studentOtherFee.getUuid());
	            pstmt.setString(2, studentOtherFee.getAccountId());
	            pstmt.setString(3, studentOtherFee.getStudentId());
	            pstmt.setString(4, studentOtherFee.getOtherFeeId());
	            pstmt.setInt(5, studentOtherFee.getAmountPiad());
	            pstmt.setString(6, studentOtherFee.getPayMode());
	            pstmt.setTimestamp(7, studentOtherFee.getDatePaid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put StudentOtherFee " + studentOtherFee);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		
		return success;
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherFeeDAO#updateStudentOtherFee(com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee)
	 */
	@Override
	public boolean updateStudentOtherFee(StudentOtherFee studentOtherFee) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentOtherFee SET amountPiad = ? WHERE accountId =?"
						+ "AND studentId =? AND otherFeeId = ?;");
     		){
			 
			
	           
	            pstmt.setInt(1, studentOtherFee.getAmountPiad());
	            pstmt.setString(2, studentOtherFee.getAccountId());
	            pstmt.setString(3, studentOtherFee.getStudentId());
	            pstmt.setString(4, studentOtherFee.getOtherFeeId());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put StudentOtherFee: "+studentOtherFee);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
          success = false;
		 }
		
		
		return success;
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherFeeDAO#StudentOtherFeeList(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<StudentOtherFee> StudentOtherFeeList(String accountId, String studentId, int startIndex, int endIndex) {
		 List<StudentOtherFee> List = null;
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentOtherFee WHERE "
							+ "accountId = ? AND studentId =? LIMIT ? OFFSET ?;");
					) {
				psmt.setString(1, accountId);
				psmt.setString(2, studentId);
				psmt.setInt(3, endIndex - startIndex);
				psmt.setInt(4, startIndex);
				try(ResultSet rset = psmt.executeQuery();){
				
					List = beanProcessor.toBeanList(rset, StudentOtherFee.class);
				}
			} catch (SQLException e) {
				logger.error("SQLException when trying to get StudentOtherFee List for studentId " + studentId);
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e)); 
		    }
			
			return List;
	}


}
