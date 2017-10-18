/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class AccountDAO extends GenericDAO implements SchoolAccountDAO {

	private static AccountDAO accountDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static AccountDAO getInstance(){

		if(accountDAO == null){
			accountDAO = new AccountDAO();		
		}
		return accountDAO;
	}

	/**
	 * 
	 */
	public AccountDAO() { 
		super();
	}

	/**
	 * 
	 */
	public AccountDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}



	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#get(java.lang.String)
	 */
	public Account getAccountById(String uuid) {
		Account school = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Account WHERE uuid = ?;");       

				){

			pstmt.setString(1, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				school  = beanProcessor.toBean(rset,Account.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Account with uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return school; 
	}



	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchoolByUsername(java.lang.String)
	 */
	public Account getAccount(String credentials,String isActive) {
		Account school = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Account WHERE username = ? OR mobile =? OR"
						+ " email =? OR name =? AND isActive =?;");       

				){
			pstmt.setString(1, credentials);
			pstmt.setString(2, credentials);
			pstmt.setString(3, credentials);
			pstmt.setString(4, credentials);
			pstmt.setString(5, isActive);
			rset = pstmt.executeQuery();
			while(rset.next()){
				school  = beanProcessor.toBean(rset,Account.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Account with credentials " + credentials);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return school; 
	}



	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchool(com.yahoo.petermwenda83.bean.account.Account)
	 */
	@Override
	public Account getAccountByPassword(String Uuid,String password) {
		Account school = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Account WHERE Uuid = ? AND password =?;");       

				){

			pstmt.setString(1, Uuid);
			pstmt.setString(2, password);
			rset = pstmt.executeQuery();
			while(rset.next()){
				school  = beanProcessor.toBean(rset,Account.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Account with Uuid: " + Uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return school; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#put(com.yahoo.petermwenda83.bean.account.Account)
	 */
	public boolean putAccount(Account school) {
		boolean success = true; 

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Account" 
						+"(uuid,isActive,name,motto,website,logo,signature,username,password,mobile,email,address,"
						+ "town,isBoarding,isMixed,lastUpdated,CreationDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
				){

			pstmt.setString(1, school.getUuid());
			pstmt.setString(2, school.getIsActive());
			pstmt.setString(3, school.getName());
			pstmt.setString(4, school.getMotto());
			pstmt.setString(5, school.getWebsite());
			pstmt.setString(6, school.getLogo());
			pstmt.setString(7, school.getSignature());
			pstmt.setString(8, school.getUsername());
			pstmt.setString(9, school.getPassword());
			pstmt.setString(10, school.getMobile());
			pstmt.setString(11, school.getEmail());
			pstmt.setString(12, school.getAddress());
			pstmt.setString(13, school.getTown());
			pstmt.setString(14, school.getIsBoarding());
			pstmt.setString(15, school.getIsMixed());
			pstmt.setString(16, school.getLastUpdated());
			pstmt.setTimestamp(17, new Timestamp(school.getCreationDate().getTime()));
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put Account: "+school);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}


		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#update(com.yahoo.petermwenda83.bean.account.Account)
	 */

	public boolean updateAccount(Account school) {
		boolean success = true; 
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Account SET isActive =? ,name =? ,motto  =? ,website  =? ,"
						+ "logo =? ,signature =? ,username =? ,password =? ,mobile =? ,email =? ,address =? ," + 
						"town =? ,isBoarding =? ,isMixed =? ,lastUpdated =? WHERE Uuid = ?;"); 
				){
			pstmt.setString(1, school.getIsActive());
			pstmt.setString(2, school.getName());
			pstmt.setString(3, school.getMotto());
			pstmt.setString(4, school.getWebsite());
			pstmt.setString(5, school.getLogo());
			pstmt.setString(6, school.getSignature());
			pstmt.setString(7, school.getUsername());
			pstmt.setString(8, school.getPassword());
			pstmt.setString(9, school.getMobile());
			pstmt.setString(10, school.getEmail());
			pstmt.setString(11, school.getAddress());
			pstmt.setString(12, school.getTown());
			pstmt.setString(13, school.getIsBoarding());
			pstmt.setString(14, school.getIsMixed());
			pstmt.setString(15, school.getLastUpdated());
			pstmt.setString(16, school.getUuid());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to update Account: "+school);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#delete(com.yahoo.petermwenda83.bean.account.Account)
	 */
	@Override
	public boolean deleteAccount(String uuid) {
		boolean success = true; 
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Account SET isActive = ?  WHERE Uuid = ?;"); 
				){
			pstmt.setString(1, "0");
			pstmt.setString(2, uuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to deleting account for uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getAllSchools()
	 */
	public List<Account> getAccounts() {
		List<Account> list =new  ArrayList<>(); 
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Account ;");   
				ResultSet rset = pstmt.executeQuery();
				) {

			list = beanProcessor.toBeanList(rset, Account.class);

		} catch(SQLException e){
			logger.error("SQL Exception when getting all Schools");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#findAccountDuplicate(java.lang.String)
	 */
	@Override
	public List<Account> findAccountDuplicate(String credentials) {
		List<Account> accountList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Account WHERE username = ? OR mobile =? OR"
						+ " email =? OR name =?;"); 
				) {
			
			pstmt.setString(1, credentials);
			pstmt.setString(2, credentials);
			pstmt.setString(3, credentials);
			pstmt.setString(4, credentials);
			
			try(ResultSet rset = pstmt.executeQuery();){

				accountList = beanProcessor.toBeanList(rset, Account.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying Account List for credentials " + credentials);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return accountList;
	}

	
	

}
