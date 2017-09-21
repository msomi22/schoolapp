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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class TermFeeDAO extends GenericDAO implements SchoolTermFeeDAO {

	private static TermFeeDAO termFeeDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static TermFeeDAO getInstance() {
		if(termFeeDAO == null){
			termFeeDAO = new TermFeeDAO();		
		}
		return termFeeDAO;
	}

	public TermFeeDAO() {
		super();

	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public TermFeeDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}
	
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#getTermFee(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public TermFee getFee(String accountId, String term,String year) {
		TermFee termFee = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TermFee WHERE accountId = ? AND term =? AND year =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, term);
			pstmt.setString(3, year);
			rset = pstmt.executeQuery();
			while(rset.next()){

				termFee  = beanProcessor.toBean(rset,TermFee.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting TermFee for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return termFee; 
	}
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#TermFee(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean termFeeAded(String accountId, String term,String year) {
		boolean exist = false;
		String dbaccountId = "";
		String dbterm = "";
		String dbyear = ""; 
		
		try(    Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TermFee WHERE accountId = ? AND term =? AND year =?;");       

      		){
			 
	            pstmt.setString(1, accountId);
	            pstmt.setString(2, term);
	            pstmt.setString(3, year);
	            try(
						ResultSet rset = pstmt.executeQuery();
						
						) {
					
					if(rset.next()) {
						dbaccountId = rset.getString("accountId");	
						dbterm = rset.getString("term");	
						dbyear = rset.getString("year");	
						
						exist = (StringUtils.equals(dbaccountId, accountId) &&
								StringUtils.equals(dbterm, term) && 
								StringUtils.equals(dbyear, year)) ? true : false;		
					} 
				}
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to get TermFee for dbaccountId " + dbaccountId);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             exist = false;
		 }
		
		return exist;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#putFee(com.yahoo.petermwenda83.bean.money.TermFee, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean putFee(TermFee termFee,String accountId, String term,String year) {
	
		boolean success = true;
		if(termFeeAded(accountId,term,year)) {

			try (  Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("UPDATE termFee SET boaderAmount =?, dayAmount =? "
							+ "WHERE  term =? AND year =? AND accountId = ? AND uuid =?;");
					) {           			 	            

				pstmt.setDouble(1, termFee.getBoaderAmount());
				pstmt.setDouble(2, termFee.getDayAmount());
				pstmt.setString(3, termFee.getTerm());
				pstmt.setString(4, termFee.getYear());
				pstmt.setString(5, termFee.getAccountId());
				pstmt.setString(6, termFee.getUuid()); 
				pstmt.executeUpdate();

			} catch (SQLException e) {
				logger.error("SQL Exception when updating TermFee " + termFee);
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;
			} 
			
			
		} else {
			
			try( Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TermFee" 
						+"(uuid, accountId, term, year, boaderAmount, dayAmount) VALUES (?,?,?,?,?,?);");
				){

			pstmt.setString(1, termFee.getUuid());
			pstmt.setString(2, termFee.getAccountId());
			pstmt.setString(3, termFee.getTerm());
			pstmt.setString(4, termFee.getYear());
			pstmt.setInt(5, termFee.getBoaderAmount());
			pstmt.setInt(6, termFee.getDayAmount());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put TermFee "+termFee);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}
		
		}
						
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#updateFee(com.yahoo.petermwenda83.bean.money.TermFee)
	 */
	@Override
	public boolean updateFee(TermFee termFee) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE termFee SET boaderAmount =?, dayAmount =? WHERE "
						+ " term =? AND year =? AND accountId = ? AND uuid =?;");
				) {           			 	            

			pstmt.setInt(1, termFee.getBoaderAmount());
			pstmt.setInt(2, termFee.getDayAmount());
			pstmt.setString(3, termFee.getTerm());
			pstmt.setString(4, termFee.getYear());
			pstmt.setString(5, termFee.getAccountId());
			pstmt.setString(6, termFee.getUuid()); 
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating TermFee " + termFee);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#getTermFeeList(java.lang.String, int, int)
	 */
	@Override
	public List<TermFee> getTermFeeList(String accountId, int startIndex , int endIndex) {
		List<TermFee> List = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM TermFee WHERE "
						+ "accountId = ? LIMIT ? OFFSET ? ;");
				) {
			psmt.setString(1, accountId);
			psmt.setInt(2, endIndex - startIndex);
			psmt.setInt(3, startIndex);
			try(ResultSet rset = psmt.executeQuery();){

				List = beanProcessor.toBeanList(rset, TermFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Fee List for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return List;
	}
	
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#getTermFeeList(java.lang.String)
	 */
	@Override
	public List<TermFee> getTermFeeList(String accountId) {
		List<TermFee> List = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM TermFee WHERE "
						+ "accountId = ?;");
				) {
			psmt.setString(1, accountId);
			try(ResultSet rset = psmt.executeQuery();){

				List = beanProcessor.toBeanList(rset, TermFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Fee List for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return List;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#getTermFeeList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TermFee> getTermFeeList(String accountId, String year) {
		List<TermFee> List = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM TermFee WHERE "
						+ "accountId = ? AND year =?;");
				) {
			psmt.setString(1, accountId);
			psmt.setString(2, year);
			try(ResultSet rset = psmt.executeQuery();){

				List = beanProcessor.toBeanList(rset, TermFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Fee List for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return List;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#findDuplicate(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TermFee> findDuplicate(String accountId, String term, String year) {
		List<TermFee> List = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM TermFee WHERE "
						+ "accountId = ? AND term =? AND year =?;");
				) {
			psmt.setString(1, accountId);
			psmt.setString(2, term);
			psmt.setString(3, year);
			try(ResultSet rset = psmt.executeQuery();){

				List = beanProcessor.toBeanList(rset, TermFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Fee List for accountId " + accountId + " and term " + term + " and year " + year);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return List;
	}
	
}
