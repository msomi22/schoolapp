/**
 * 
 */
package ke.co.qubintel.school.server.persistence.money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.money.FeeBreakdownDesc;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class FeeBreakdownDescDAO  extends GenericDAO  implements SchoolFeeBreakdownDescDAO {

	private static FeeBreakdownDescDAO feeBreakdownDescDAO;

	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static FeeBreakdownDescDAO getInstance() {
		if(feeBreakdownDescDAO == null){
			feeBreakdownDescDAO = new FeeBreakdownDescDAO();		
		}
		return feeBreakdownDescDAO;
	}


	public FeeBreakdownDescDAO() {
		super();
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public FeeBreakdownDescDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolFeeBreakdownDescDAO#getFeeBreakdownDesc(java.lang.String, java.lang.String)
	 */
	@Override
	public FeeBreakdownDesc getFeeBreakdownDesc(String accountId, String uuid) {
		FeeBreakdownDesc feeBreakdownDesc = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM FeeBreakdownDesc WHERE accountId = ?"
						+ " AND uuid =? ;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				feeBreakdownDesc  = beanProcessor.toBean(rset, FeeBreakdownDesc.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting FeeBreakdownDesc for accountId  " + accountId +" and uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return feeBreakdownDesc; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolFeeBreakdownDescDAO#getFeeBreakdownDesc(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public FeeBreakdownDesc getFeeBreakdownDesc(String accountId, String feeBreakdownId, String query) {
		FeeBreakdownDesc feeBreakdownDesc = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM FeeBreakdownDesc WHERE accountId = ?"
						+ " AND feeBreakdownId =? AND (feeCode =? OR feeDescription =?) ;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, feeBreakdownId);
			pstmt.setString(3, query);
			pstmt.setString(4, query);
			rset = pstmt.executeQuery();
			while(rset.next()){
				feeBreakdownDesc  = beanProcessor.toBean(rset, FeeBreakdownDesc.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting FeeBreakdownDesc for accountId  " + accountId +" and feeBreakdownId " + feeBreakdownId +
					" and  query " + query );
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return feeBreakdownDesc; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolFeeBreakdownDescDAO#getFeeBreakdownDescList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<FeeBreakdownDesc> getFeeBreakdownDescList(String accountId, String feeBreakdownId) {
		List<FeeBreakdownDesc> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM FeeBreakdownDesc WHERE"
						+ " accountId = ? AND feeBreakdownId = ?;");
				) {
			pstmt.setString(1, accountId);      
			pstmt.setString(2, feeBreakdownId); 
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, FeeBreakdownDesc.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting FeeBreakdownDesc List for accountId " + accountId + " and feeBreakdownId "  + feeBreakdownId); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolFeeBreakdownDescDAO#putFeeBreakdownDesc(ke.co.qubintel.school.server.bean.money.FeeBreakdownDesc)
	 */
	@Override
	public boolean putFeeBreakdownDesc(FeeBreakdownDesc feeBreakdownDesc) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO FeeBreakdownDesc" 
						+"(uuid, accountId, feeBreakdownId, feeCode, feeDescription, amount) VALUES (?,?,?,?,?,?);");
				){ 

			pstmt.setString(1, feeBreakdownDesc.getUuid());
			pstmt.setString(2, feeBreakdownDesc.getAccountId());
			pstmt.setString(3, feeBreakdownDesc.getFeeBreakdownId());
			pstmt.setString(4, feeBreakdownDesc.getFeeCode());
			pstmt.setString(5, feeBreakdownDesc.getFeeDescription());
			pstmt.setInt(6, feeBreakdownDesc.getAmount());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put FeeBreakdownDesc " + feeBreakdownDesc);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolFeeBreakdownDescDAO#updateFeeBreakdownDesc(ke.co.qubintel.school.server.bean.money.FeeBreakdownDesc)
	 */
	@Override
	public boolean updateFeeBreakdownDesc(FeeBreakdownDesc feeBreakdownDesc) {
		boolean success = true;
		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE FeeBreakdownDesc SET feeCode =?, feeDescription = ?,"
						+ "amount =? WHERE accountId =? AND feeBreakdownId =? AND uuid =?;");
				) {           			 	            

			pstmt.setString(1, feeBreakdownDesc.getFeeCode());
			pstmt.setString(2, feeBreakdownDesc.getFeeDescription());
			pstmt.setInt(3, feeBreakdownDesc.getAmount());	
			pstmt.setString(6, feeBreakdownDesc.getUuid());
			pstmt.setString(4, feeBreakdownDesc.getAccountId());
			pstmt.setString(5, feeBreakdownDesc.getFeeBreakdownId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating FeeBreakdownDesc " + feeBreakdownDesc);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolFeeBreakdownDescDAO#deleteFeeBreakdownDesc(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteFeeBreakdownDesc(String accountId, String uuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM FeeBreakdownDesc"
						+ " WHERE accountId =? AND uuid =?;");       
				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			logger.error("SQL Exception when deletting FeeBreakdownDesc for accountId  " + accountId + " and uuid" + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}


	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolFeeBreakdownDescDAO#findDuplicate(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<FeeBreakdownDesc> findDuplicate(String accountId, String feeBreakdownId, String query) {
		List<FeeBreakdownDesc> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM FeeBreakdownDesc WHERE"
						+ " accountId = ? AND feeBreakdownId = ? AND (feeCode =? OR feeDescription =?) ;");
				) {
			pstmt.setString(1, accountId);      
			pstmt.setString(2, feeBreakdownId); 
			pstmt.setString(3, query); 
			pstmt.setString(4, query); 
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, FeeBreakdownDesc.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting FeeBreakdownDesc List for accountId " + accountId + " "
					+ "and feeBreakdownId "  + feeBreakdownId + " and query " + query ); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

}
