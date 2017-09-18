/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.money.FeeBreakdown;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class FeeBreakdownDAO extends GenericDAO implements SchoolFeeBreakdownDAO {

	private static FeeBreakdownDAO feeBreakdownDAO;

	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static FeeBreakdownDAO getInstance() {
		if(feeBreakdownDAO == null){
			feeBreakdownDAO = new FeeBreakdownDAO();		
		}
		return feeBreakdownDAO;
	}


	public FeeBreakdownDAO() {
		super();
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public FeeBreakdownDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolFeeBreakdownDAO#getFeeBreakdownById(java.lang.String, java.lang.String)
	 */
	@Override
	public FeeBreakdown getFeeBreakdownById(String accountId, String uuid) {
		FeeBreakdown feeBreakdown = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM FeeBreakdown WHERE accountId = ?"
						+ " AND uuid =? ;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				feeBreakdown  = beanProcessor.toBean(rset, FeeBreakdown.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting FeeBreakdown for accountId  " + accountId +" and uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return feeBreakdown; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolFeeBreakdownDAO#getFeeBreakdown(java.lang.String, java.lang.String)
	 */
	@Override
	public FeeBreakdown getFeeBreakdown(String accountId,String feeCategory, String feeCode) {
		FeeBreakdown feeBreakdown = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM FeeBreakdown WHERE accountId = ?"
						+ " AND feeCategory =? AND feeCode =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, feeCategory);
			pstmt.setString(3, feeCode);
			rset = pstmt.executeQuery();
			while(rset.next()){
				feeBreakdown  = beanProcessor.toBean(rset, FeeBreakdown.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting FeeBreakdown for accountId  " + accountId +" and feeCategory " + feeCategory + " and  feeCode" + feeCode );
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return feeBreakdown; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolFeeBreakdownDAO#getFeeBreakdownList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<FeeBreakdown> getFeeBreakdownList(String accountId, String feeCategory) {
		List<FeeBreakdown> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM FeeBreakdown WHERE"
						+ " accountId = ? AND feeCategory = ?;");
				) {
			pstmt.setString(1, accountId);      
			pstmt.setString(2, feeCategory); 
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, FeeBreakdown.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting FeeBreakdown List for accountId " + accountId + " and feeCategory "  + feeCategory); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolFeeBreakdownDAO#putFeeBreakdown(com.yahoo.petermwenda83.bean.money.FeeBreakdown)
	 */
	@Override
	public boolean putFeeBreakdown(FeeBreakdown feeBreakdown) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO FeeBreakdown" 
						+"(uuid, accountId, feeCategory, feeCode, feeDescription, amount, term, year) VALUES (?,?,?,?,?,?,?,?);");
				){ 

			pstmt.setString(1, feeBreakdown.getUuid());
			pstmt.setString(2, feeBreakdown.getAccountId());
			pstmt.setString(3, feeBreakdown.getFeeCategory());
			pstmt.setString(4, feeBreakdown.getFeeCode());
			pstmt.setString(5, feeBreakdown.getFeeDescription());
			pstmt.setInt(6, feeBreakdown.getAmount());
			pstmt.setString(7, feeBreakdown.getTerm());
			pstmt.setString(8, feeBreakdown.getYear());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put FeeBreakdown " + feeBreakdown);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolFeeBreakdownDAO#updateFeeBreakdown(com.yahoo.petermwenda83.bean.money.FeeBreakdown)
	 */
	@Override
	public boolean updateFeeBreakdown(FeeBreakdown feeBreakdown) {
		boolean success = true;
		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE FeeBreakdown SET feeCode =?, feeDescription = ?,"
						+ "amount =?,term =?,year =? WHERE accountId =? "
						+ "AND feeCategory =? AND uuid =?;");
				) {           			 	            


			pstmt.setString(1, feeBreakdown.getFeeCode());
			pstmt.setString(2, feeBreakdown.getFeeDescription());
			pstmt.setInt(3, feeBreakdown.getAmount());
			pstmt.setString(4, feeBreakdown.getTerm());
			pstmt.setString(5, feeBreakdown.getYear());
			pstmt.setString(6, feeBreakdown.getAccountId());
			pstmt.setString(7, feeBreakdown.getFeeCategory());
			pstmt.setString(8, feeBreakdown.getUuid());			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating feeBreakdown " + feeBreakdown);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolFeeBreakdownDAO#deleteFeeBreakdown(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteFeeBreakdown(String accountId, String uuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM FeeBreakdown"
						+ " WHERE accountId =? AND uuid =?;");       
				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			logger.error("SQL Exception when deletting FeeBreakdown for accountId  " + accountId + " and uuid" + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

}
