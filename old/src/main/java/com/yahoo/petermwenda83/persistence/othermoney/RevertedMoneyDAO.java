/**
 * 
 */
package com.yahoo.petermwenda83.persistence.othermoney;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.otherfee.RevertedMoney;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class RevertedMoneyDAO extends GenericDAO implements SchoolRevertedMoneyDAO {

	private static RevertedMoneyDAO revertedMoneyDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static RevertedMoneyDAO getInstance() {
		if(revertedMoneyDAO == null){
			revertedMoneyDAO = new RevertedMoneyDAO();		
		}
		return revertedMoneyDAO;
	}

	public RevertedMoneyDAO() {
		super();

	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public RevertedMoneyDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolRevertedMoneyDAO#getRevertedMoney(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public RevertedMoney getRevertedMoney(String accountId, String studentId, String uuid) {
		RevertedMoney revertedMoney = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM RevertedMoney WHERE accountId = ? AND studentId = ? AND uuid =?;");       
				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			pstmt.setString(3, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				revertedMoney  = beanProcessor.toBean(rset,RevertedMoney.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Reverted Money for studentId  " + studentId );
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return revertedMoney; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolRevertedMoneyDAO#putRevertedMoney(com.yahoo.petermwenda83.bean.otherfee.RevertedMoney)
	 */
	@Override
	public boolean putRevertedMoney(RevertedMoney revertedMoney) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO RevertedMoney" 
						+"(uuid, accountId, studentId, otherFeeId, dateReverted) VALUES (?,?,?,?,?);");
				){

			pstmt.setString(1, revertedMoney.getUuid());
			pstmt.setString(2, revertedMoney.getAccountId());
			pstmt.setString(3, revertedMoney.getStudentId());
			pstmt.setString(4, revertedMoney.getOtherFeeId());
			pstmt.setTimestamp(5, revertedMoney.getDateReverted());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put RevertedMoney " + revertedMoney);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}


		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolRevertedMoneyDAO#putstudentUuid(com.yahoo.petermwenda83.bean.otherfee.RevertedMoney)
	 */
	@Override
	public boolean deleteRevertedMoney(String accountId, String studentId, String uuid) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM RevertedMoney WHERE accountId =? AND studentId =? AND uuid =?;");
				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			pstmt.setString(3, uuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to delete RevertedMoney for studentId  " + studentId + " with id " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}


		return success;
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolRevertedMoneyDAO#getRevertedMoneyList(java.lang.String, int, int)
	 */
	@Override
	public List<RevertedMoney> getRevertedMoneyList(String studentId, int startIndex, int endIndex) {
		List<RevertedMoney> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM RevertedMoney WHERE studentId = ? LIMIT ? OFFSET ?;");
				) {
			pstmt.setString(1, studentId);    
			pstmt.setInt(2, endIndex - startIndex);
			pstmt.setInt(3, startIndex);
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, RevertedMoney.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting RevertedMoney  List for studentId " +  studentId); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolRevertedMoneyDAO#getRevertedMoneyList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<RevertedMoney> getRevertedMoneyList(String accountId, String studentId) {
		List<RevertedMoney> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM RevertedMoney WHERE studentId = ? AND studentId = ?;");
				) {
			
			pstmt.setString(1, studentId);    
			pstmt.setString(2,studentId);
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, RevertedMoney.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting RevertedMoney  List for studentId " +  studentId); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

}
