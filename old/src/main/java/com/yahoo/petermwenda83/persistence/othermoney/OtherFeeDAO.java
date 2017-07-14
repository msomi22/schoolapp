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

import com.yahoo.petermwenda83.bean.otherfee.OtherFee;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**  
 * @author peter
 *
 */
public class OtherFeeDAO extends GenericDAO implements SchoolOtherFeeDAO {

	private static OtherFeeDAO otherFeeDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static OtherFeeDAO getInstance() {
		if(otherFeeDAO == null){
			otherFeeDAO = new OtherFeeDAO();		
		}
		return otherFeeDAO;
	}

	public OtherFeeDAO() {
		super();

	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public OtherFeeDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherFeeDAO#getOtherstype(java.lang.String)
	 */
	@Override
	public OtherFee getOtherFee(String accountId, String uuid) {
		OtherFee otherFee = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OtherFee WHERE accountId = ? AND uuid =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				otherFee  = beanProcessor.toBean(rset,OtherFee.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting OtherFee with uuid " + uuid );
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return otherFee; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherFeeDAO#putOtherstype(com.yahoo.petermwenda83.bean.otherfee.OtherFee)
	 */
	@Override
	public boolean putOtherFee(OtherFee otherFee) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO OtherFee" 
						+"(uuid, accountId, description, amount, term, year) VALUES (?,?,?,?,?,?);");
				){

			pstmt.setString(1, otherFee.getUuid());
			pstmt.setString(2, otherFee.getAccountId());
			pstmt.setString(3, otherFee.getDescription());
			pstmt.setInt(4, otherFee.getAmount());
			pstmt.setString(5, otherFee.getTerm());
			pstmt.setString(6, otherFee.getYear());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put otherFee " + otherFee);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherFeeDAO#updteOtherstype(com.yahoo.petermwenda83.bean.otherfee.OtherFee)
	 */
	@Override
	public boolean updateOtherFee(OtherFee otherFee) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE OtherFee SET description = ?, amount =? ,"
						+ "term =?, year =? WHERE uuid =? AND accountId =?;");
				) {           			 	            


			pstmt.setString(1, otherFee.getDescription());
			pstmt.setInt(2, otherFee.getAmount());
			pstmt.setString(3, otherFee.getTerm());
			pstmt.setString(4, otherFee.getYear());
			pstmt.setString(5, otherFee.getUuid());
			pstmt.setString(6, otherFee.getAccountId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating OtherFee " + otherFee);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherFeeDAO#getOtherFeeList(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<OtherFee> getOtherFeeList(String accountId, String term, String year, int startIndex, int endIndex) {
		List<OtherFee> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OtherFee WHERE"
						+ " accountId = ? AND term = ? AND year = ? LIMIT ? OFFSET ? ;");
				) {
			pstmt.setString(1, accountId);      
			pstmt.setString(2, term); 
			pstmt.setString(3, year); 
			pstmt.setInt(4, endIndex - startIndex);
			pstmt.setInt(5, startIndex);
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, OtherFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting OtherFee  List for accountId " + accountId); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}


}
