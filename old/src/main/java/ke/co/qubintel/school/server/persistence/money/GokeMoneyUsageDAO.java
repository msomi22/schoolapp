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

import ke.co.qubintel.school.server.bean.money.GokeMoneyUsage;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class GokeMoneyUsageDAO extends GenericDAO  implements SchoolGokeMoneyUsageDAO {

	private static GokeMoneyUsageDAO gokeMoneyUsageDAO;

	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static GokeMoneyUsageDAO getInstance() {
		if(gokeMoneyUsageDAO == null){
			gokeMoneyUsageDAO = new GokeMoneyUsageDAO();		
		}
		return gokeMoneyUsageDAO;
	}


	public GokeMoneyUsageDAO() {
		super();
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public GokeMoneyUsageDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolGokeMoneyUsageDAO#getGokeMoneyUsage(java.lang.String, java.lang.String)
	 */
	@Override
	public GokeMoneyUsage getGokeMoneyUsage(String accountId, String uuid) {
		GokeMoneyUsage gokeMoneyUsage = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GokeMoneyUsage WHERE accountId = ?"
						+ " AND uuid =? ;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				gokeMoneyUsage  = beanProcessor.toBean(rset, GokeMoneyUsage.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting GokeMoneyUsage for accountId  " + accountId +" and uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return gokeMoneyUsage; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolGokeMoneyUsageDAO#getGokeMoneyUsage(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public GokeMoneyUsage getGokeMoneyUsage(String accountId, String term, String year) {
		GokeMoneyUsage gokeMoneyUsage = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GokeMoneyUsage WHERE accountId = ?"
						+ " AND term =? AND year =? ;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, term);
			pstmt.setString(3, year);
			rset = pstmt.executeQuery();
			while(rset.next()){
				gokeMoneyUsage  = beanProcessor.toBean(rset, GokeMoneyUsage.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting GokeMoneyUsage for accountId  " + accountId +" and term " + term + " and year " + year);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return gokeMoneyUsage; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolGokeMoneyUsageDAO#getGokeMoneyUsageList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<GokeMoneyUsage> getGokeMoneyUsageList(String accountId, String year) {
		List<GokeMoneyUsage> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GokeMoneyUsage WHERE"
						+ " accountId =? AND year =? ORDER BY term ASC;");
				) {
			pstmt.setString(1, accountId);    
			pstmt.setString(2, year);    
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, GokeMoneyUsage.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting GokeMoneyUsage List for accountId " + accountId + " and year " + year); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolGokeMoneyUsageDAO#getGokeMoneyUsageList(java.lang.String)
	 */
	@Override
	public List<GokeMoneyUsage> getGokeMoneyUsageList(String accountId) {
		List<GokeMoneyUsage> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GokeMoneyUsage WHERE"
						+ " accountId =? ORDER BY year DESC;");
				) {
			pstmt.setString(1, accountId);    
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, GokeMoneyUsage.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting GokeMoneyUsage List for accountId " + accountId); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolGokeMoneyUsageDAO#putGokeMoneyUsage(ke.co.qubintel.school.server.bean.money.GokeMoneyUsage)
	 */
	@Override
	public boolean putGokeMoneyUsage(GokeMoneyUsage gokeMoneyUsage) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO GokeMoneyUsage" 
						+"(uuid, accountId, numberOfStudents, amountPerStudent, totalAmount, balance, term, year, dateAllocated) "
						+ "VALUES (?,?,?,?,?,?,?,?,?);");
				){ 
			
			pstmt.setString(1, gokeMoneyUsage.getUuid());
			pstmt.setString(2, gokeMoneyUsage.getAccountId());
			pstmt.setInt(3, gokeMoneyUsage.getNumberOfStudents());
			pstmt.setInt(4, gokeMoneyUsage.getAmountPerStudent());
			pstmt.setInt(5, gokeMoneyUsage.getTotalAmount());
			pstmt.setInt(6, gokeMoneyUsage.getBalance());
			pstmt.setString(7, gokeMoneyUsage.getTerm());
			pstmt.setString(8, gokeMoneyUsage.getYear());
			pstmt.setTimestamp(9, gokeMoneyUsage.getDateAllocated());
			pstmt.executeUpdate(); 
			
		}catch(SQLException e){
			logger.error("SQL Exception trying to put GokeMoneyUsage " + gokeMoneyUsage);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.money.SchoolGokeMoneyUsageDAO#updateGokeMoneyUsage(ke.co.qubintel.school.server.bean.money.GokeMoneyUsage)
	 */
	@Override
	public boolean updateGokeMoneyUsage(GokeMoneyUsage gokeMoneyUsage) {
		boolean success = true;
		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE GokeMoneyUsage SET numberOfStudents =?,"
						+ "amountPerStudent =?, totalAmount =?, balance =?, term =?,year =? WHERE uuid =? AND accountId =?;");
				) {           			 	            
			pstmt.setInt(1, gokeMoneyUsage.getNumberOfStudents());
			pstmt.setInt(2, gokeMoneyUsage.getAmountPerStudent());
			pstmt.setInt(3, gokeMoneyUsage.getTotalAmount());
			pstmt.setInt(4, gokeMoneyUsage.getBalance());
			pstmt.setString(5, gokeMoneyUsage.getTerm());
			pstmt.setString(6, gokeMoneyUsage.getYear());	
			pstmt.setString(7, gokeMoneyUsage.getUuid());
			pstmt.setString(8, gokeMoneyUsage.getAccountId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating GokeMoneyUsage " + gokeMoneyUsage);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

}
