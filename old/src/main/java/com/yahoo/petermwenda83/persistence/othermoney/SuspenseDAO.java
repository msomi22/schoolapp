/**
 * 
 */
package com.yahoo.petermwenda83.persistence.othermoney;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.otherfee.Suspense;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class SuspenseDAO extends GenericDAO implements SchoolSuspenseDAO {

	private static SuspenseDAO suspenseDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return suspenseDAO instance 
	 * 
	 */
	public static SuspenseDAO getInstance() {
		if(suspenseDAO == null){
			suspenseDAO = new SuspenseDAO();		
		}
		return suspenseDAO;
	}

	public SuspenseDAO() {
		super();

	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public SuspenseDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolSuspenseDAO#getSuspense(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Suspense getSuspense(String accountId, String studentId, String uuid) {
		Suspense suspense = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Suspense WHERE accountId = ? AND studentId =?"
						+ " AND uuid =? ;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				suspense  = beanProcessor.toBean(rset, Suspense.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting Suspense with id  " + uuid +" and accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return suspense; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolSuspenseDAO#getSuspense(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Suspense> getSuspense(String accountId, String studentId) {
		List<Suspense> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Suspense WHERE"
						+ " accountId = ? AND studentId = ? ORDER BY datePaid DESC;");
				) {
			pstmt.setString(1, accountId);      
			pstmt.setString(2, studentId); 
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, Suspense.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting Suspense  List for studentId " + studentId); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolSuspenseDAO#putSuspense(com.yahoo.petermwenda83.bean.otherfee.Suspense)
	 */
	@Override
	public boolean putSuspense(Suspense suspense) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Suspense" 
						+"(uuid, accountId, studentId, amountPaid, payMode, transactionId, paidHas, termPiad, yearPaid, datePaid) VALUES (?,?,?,?,?,?,?,?);");
				){ 

			pstmt.setString(1, suspense.getUuid());
			pstmt.setString(2, suspense.getAccountId());
			pstmt.setString(3, suspense.getStudentId());
			pstmt.setInt(4, suspense.getAmountPaid());
			pstmt.setString(5, suspense.getPayMode());
			pstmt.setString(6, suspense.getTransactionId());
			pstmt.setString(7, suspense.getPaidHas());
			pstmt.setString(8, suspense.getTermPiad());
			pstmt.setString(9, suspense.getYearPaid());
			pstmt.setTimestamp(10, new Timestamp(suspense.getDatePaid().getTime()));
			pstmt.executeUpdate();



		}catch(SQLException e){
			logger.error("SQL Exception trying to put suspense " + suspense);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolSuspenseDAO#deleteSuspense(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteSuspense(String accountId, String studentId, String uuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Suspense"
						+ " WHERE accountId = ? AND studentId =? AND uuid =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			pstmt.setString(3, uuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting Suspense studentId " + studentId + " uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolSuspenseDAO#getSuspense(java.lang.String, int, int)
	 */
	@Override
	public List<Suspense> getSuspense(String accountId, int startIndex, int endIndex) {
		List<Suspense> list = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Suspense WHERE "
						+ "accountId = ? LIMIT ? OFFSET ?;");
				) {
			psmt.setString(1, accountId);
			psmt.setInt(2, endIndex - startIndex);
			psmt.setInt(3, startIndex);
			try(ResultSet rset = psmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, Suspense.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get Suspense List for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return list;
	}

}
