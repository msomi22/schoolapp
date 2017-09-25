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
	public StudentFee getStudentFee(String accountId , String studentId, String uuid) {
		StudentFee studentFee = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentFee WHERE accountId = ? AND studentId =?"
						+ " AND uuid =? ;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			pstmt.setString(3, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				studentFee  = beanProcessor.toBean(rset,StudentFee.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting studentFee with accountId  " + accountId +" and studentId " 
		                  + studentId + " and uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return studentFee; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentFeeDAO#getStudentFee(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public StudentFee getStudentFee(String accountId, String studentId, String payMode, String termPiad,
			String yearPaid) {
		StudentFee studentFee = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentFee WHERE accountId = ?"
						+ " AND studentId =? AND payMode =? AND termPiad =? AND yearPaid =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			pstmt.setString(3, payMode);
			pstmt.setString(4, termPiad);
			pstmt.setString(5, yearPaid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				studentFee  = beanProcessor.toBean(rset,StudentFee.class);
			}
		}catch(SQLException e){    
			logger.error("SQL Exception when getting studentFee for accountId  " + accountId +" and studentId " 
					+ studentId + " and payMode " + payMode + " and termPiad " + termPiad + " and yearPaid " + yearPaid);
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
						+"(uuid, accountId, studentId, amountPaid, payMode, transactionId, paidHas,termPiad, yearPaid,transactingStaffId, datePaid) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?);");
				){ 

			pstmt.setString(1, studentFee.getUuid());
			pstmt.setString(2, studentFee.getAccountId());
			pstmt.setString(3, studentFee.getStudentId());
			pstmt.setInt(4, studentFee.getAmountPaid());
			pstmt.setString(5, studentFee.getPayMode());
			pstmt.setString(6, studentFee.getTransactionId());
			pstmt.setString(7, studentFee.getPaidHas());
			pstmt.setString(8, studentFee.getTermPiad());
			pstmt.setString(9, studentFee.getYearPaid());
			pstmt.setString(10, studentFee.getTransactingStaffId()); 
			pstmt.setTimestamp(11, new Timestamp(studentFee.getDatePaid().getTime()));
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
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentFee SET amountPaid = ? WHERE accountId = ? "
						+ "AND studentId =? AND uuid =? AND transactingStaffId =?;");
				) {           			 	            


			pstmt.setInt(1, studentFee.getAmountPaid());
			pstmt.setString(2, studentFee.getAccountId());
			pstmt.setString(3, studentFee.getStudentId());
			pstmt.setString(4, studentFee.getUuid());
			pstmt.setString(5, studentFee.getTransactingStaffId());
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
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentFeeDAO#getStudentFeeList(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<StudentFee> getStudentFeeList(String accountId , String studentId, int startIndex , int endIndex) {
		List<StudentFee> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentFee WHERE"
						+ " accountId = ? AND studentId = ?  ORDER BY datePaid DESC LIMIT ? OFFSET ? ;");
				) {
			pstmt.setString(1, accountId);      
			pstmt.setString(2, studentId); 
			pstmt.setInt(3, endIndex - startIndex);
			pstmt.setInt(4, startIndex);
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, StudentFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting StudentFee List for studentId " + studentId); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentFeeDAO#getStudentFeeList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<StudentFee> getStudentFeeList(String accountId, String studentId, String termPiad, String yearPaid) {
		List<StudentFee> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentFee WHERE"
						+ " accountId = ? AND studentId = ? AND termPiad =? AND yearPaid =?  ORDER BY datePaid DESC;");
				) {
			pstmt.setString(1, accountId);      
			pstmt.setString(2, studentId); 
			pstmt.setString(3, termPiad); 
			pstmt.setString(4, yearPaid); 

			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, StudentFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting StudentFee List for studentId " + studentId); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentFeeDAO#revertStudentGokeFee(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean revertStudentGokeFee(String accountId, String studentId, String termPiad, String yearPaid,
			String payMode) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentFee"
						+ " WHERE accountId =? AND studentId =? AND termPiad =? AND yearPaid =? AND payMode =? ;");       
				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			pstmt.setString(3, termPiad);
			pstmt.setString(4, yearPaid);
			pstmt.setString(5, payMode);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting StudentFee for accountId " + accountId + " and studentId  " 
		                + studentId + " and termPiad " + termPiad + " and  yearPaid " + yearPaid + " and payMode " + payMode);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}


}
