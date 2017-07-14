/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.money.Deposit;
import com.yahoo.petermwenda83.bean.money.PocketMoney;
import com.yahoo.petermwenda83.bean.money.Withdraw;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 * 
 */
public class PMoneyDAO extends GenericDAO implements SchoolPMoneyDAO {

	private static PMoneyDAO pMoneyDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static PMoneyDAO getInstance() {
		if(pMoneyDAO == null){
			pMoneyDAO = new PMoneyDAO();		
		}
		return pMoneyDAO;
	}

	public PMoneyDAO() {
		super();

	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public PMoneyDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#getMoney(java.lang.String)
	 */
	@Override
	public PocketMoney getPocketMoney(String accountId,String studentId) {
		PocketMoney pMoney = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM PocketMoney WHERE accountId = ? AND studentId =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			rset = pstmt.executeQuery();
			while(rset.next()){

				pMoney  = beanProcessor.toBean(rset,PocketMoney.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception while getting PocketMoney for studentId " + studentId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return pMoney;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#hasBalance(java.lang.String)
	 */
	@Override
	public boolean studentExist(String accountId,String studentId) {
		boolean exixt = false;
		String dbaccountId = "";
		String dbstudentId = "";
		try(    Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM PocketMoney "
						+ "WHERE accountId = ? AND studentId =?;");
				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			try(
					ResultSet rset = pstmt.executeQuery();

					) {

				if(rset.next()) {
					dbaccountId = rset.getString("accountId");	
					dbstudentId = rset.getString("studentId");	

					exixt = (StringUtils.equals(dbaccountId, accountId) && StringUtils.equals(dbstudentId, studentId)) ? true : false;		
				} 
			}

		}catch(SQLException e){
			logger.error("SQL Exception trying to get student PocketMoney for studentId " + studentId);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			exixt = false;
		}

		return exixt;
	}
	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#hasBalance(com.yahoo.petermwenda83.bean.money.PocketMoney, double)
	 */
	@Override
	public boolean hasBalance(String accountId, String studentId, double amount) {
		boolean hasBalance = false;
		double balance = 0;
		try(    Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT amount FROM PocketMoney "
						+ "WHERE accountId = ? AND studentId =?;");
				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			try(
					ResultSet rset = pstmt.executeQuery();

					) {

				if(rset.next()) {

					balance = rset.getDouble("amount");	
					hasBalance = (balance >= amount) ? true : false;	

				} 
			}

		}catch(SQLException e){
			logger.error("SQL Exception trying to get student PocketMoney for  studentId  " + studentId + " of amount " + amount);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			hasBalance = false;
		}

		return hasBalance;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#addBalance(com.yahoo.petermwenda83.bean.money.PocketMoney, double)
	 */
	@Override
	public boolean addBalance(PocketMoney pocketMoney, String accountId, String studentId, double amount) {
		boolean success = true;
		if(studentExist(accountId,studentId)) {
			try( 
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("UPDATE PocketMoney " +
							"SET amount = (SELECT amount FROM PocketMoney WHERE studentId=? AND accountId =?"
							+ ") + ? " +				
							"WHERE uuid = (SELECT uuid FROM PocketMoney WHERE studentId=? AND accountId =? );");	

					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Deposit(uuid,accountId,"
							+ "studentId, amount, depositDate) VALUES(?,?,?,?,?);");
					) {

				pstmt.setString(1, studentId);	
				pstmt.setString(2, accountId);	
				pstmt.setDouble(3, amount);
				pstmt.setString(4, studentId);
				pstmt.setString(5, accountId);
				pstmt.executeUpdate();

				if(pocketMoney instanceof Deposit){
					pstmt2.setString(1, pocketMoney.getUuid());									
					pstmt2.setString(2, accountId);
					pstmt2.setString(3, studentId);
					pstmt2.setDouble(4, amount);
					pstmt2.setTimestamp(5, new Timestamp(new Date().getTime()));
					pstmt2.executeUpdate();



				}


			} catch(SQLException e) {
				logger.error("SQLException while adding by updating the balance of '" + pocketMoney +
						"' of amount " + amount+"'.");
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 


		} else { 
			try(	
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO PocketMoney(uuid, accountId,"
							+ "studentId,amount) VALUES(?,?,?,?);");	

					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Deposit(uuid,accountId,"
							+ "studentId, amount, depositDate) VALUES(?,?,?,?,?);");
					) {
				pstmt.setString(1, pocketMoney.getUuid());
				pstmt.setString(2, accountId);	
				pstmt.setString(3, studentId);	
				pstmt.setDouble(4, amount);
				pstmt.executeUpdate();	

				if(pocketMoney instanceof Deposit){
					pstmt2.setString(1, pocketMoney.getUuid());									
					pstmt2.setString(2, accountId);
					pstmt2.setString(3, studentId);
					pstmt2.setDouble(4, amount);
					pstmt2.setTimestamp(5, new Timestamp(new Date().getTime())); 
					pstmt2.executeUpdate();


				}


			} catch(SQLException e) {
				logger.error("SQLException while adding by creating the balance of '" + pocketMoney +
						"' of amount " + amount + "'.");
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
		}


		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#deductBalance(com.yahoo.petermwenda83.bean.money.PocketMoney, double)
	 */
	@Override
	public boolean deductBalance(PocketMoney pocketMoney, String accountId, String studentId, double amount) {
		boolean success = true;
		if(hasBalance(accountId,studentId,0.0)) {
			try( 
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("UPDATE PocketMoney " +
							"SET Amount = (SELECT Amount FROM PocketMoney WHERE studentId=? AND accountId =?"
							+ ") - ? " +				
							"WHERE Uuid = (SELECT Uuid FROM PocketMoney WHERE studentId=? AND accountId =?);");

					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Withdraw(uuid, accountId,"
							+ "studentId ,amount, withdrawDate) VALUES(?,?,?,?,?);");
					) {

				pstmt.setString(1, studentId);		
				pstmt.setString(2, accountId);		
				pstmt.setDouble(3, amount);
				pstmt.setString(4, studentId);
				pstmt.setString(5, accountId);		
				pstmt.executeUpdate();

				if(pocketMoney instanceof Withdraw){
					pstmt2.setString(1, pocketMoney.getUuid());									
					pstmt2.setString(2, accountId);
					pstmt2.setString(3, studentId);
					pstmt2.setDouble(4, amount);
					pstmt2.setTimestamp(5, new Timestamp(new Date().getTime()));
					pstmt2.executeUpdate();


				}


			} catch(SQLException e) {
				logger.error("SQLException while adding by updating the balance of '" + pocketMoney +
						"' of amount " + amount+"'.");
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 


		} else { 
			try(	
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO PocketMoney(uuid, accountId,"
							+ "studentId, amount) VALUES(?,?,?,?);");	

					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Withdraw(uuid, accountId,"
							+ "studentId, amount, withdrawDate) VALUES(?,?,?,?,?);");
					) {
				pstmt.setString(1, pocketMoney.getUuid());
				pstmt.setString(2, accountId);		
				pstmt.setString(3, studentId);			
				pstmt.setDouble(4, amount);
				pstmt.executeUpdate();	

				if(pocketMoney instanceof Withdraw){
					pstmt2.setString(1, pocketMoney.getUuid());									
					pstmt2.setString(2, accountId);
					pstmt2.setString(3, studentId);
					pstmt2.setDouble(4, amount);
					pstmt2.setTimestamp(5, new Timestamp(new Date().getTime()));
					pstmt2.executeUpdate();
				}	


			} catch(SQLException e) {
				logger.error("SQLException while adding by creating the balance of '" + pocketMoney +
						"' of amount " + amount + "'.");
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
		}


		return success;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#getWithdrawList(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<Withdraw> getWithdrawList(String accountId,String studentId, int startIndex , int endIndex) {
		List<Withdraw> withdrawList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Withdraw WHERE accountId =? AND "
						+ "studentId = ? ORDER BY withdrawDate DESC LIMIT ? OFFSET ?;");
				) {
			psmt.setString(1, accountId);
			psmt.setString(2, studentId);
			psmt.setInt(3, endIndex - startIndex);
			psmt.setInt(4, startIndex);

			try(ResultSet rset = psmt.executeQuery();){

				withdrawList = beanProcessor.toBeanList(rset, Withdraw.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Withdraw List for student " + studentId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return withdrawList;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#getDepositList(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<Deposit> getDepositList(String accountId,String studentId, int startIndex , int endIndex) {
		List<Deposit> depositList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Deposit WHERE accountId =? AND "
						+ "studentId = ? ORDER BY depositDate DESC LIMIT ? OFFSET ?;");
				) {
			psmt.setString(1, accountId);
			psmt.setString(2, studentId);
			psmt.setInt(3, endIndex - startIndex);
			psmt.setInt(4, startIndex);

			try(ResultSet rset = psmt.executeQuery();){

				depositList = beanProcessor.toBeanList(rset, Deposit.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Deposit List for student " + studentId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return depositList;
	}



}
