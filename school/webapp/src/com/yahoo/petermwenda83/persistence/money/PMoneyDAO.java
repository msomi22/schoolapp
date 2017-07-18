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
	public PocketMoney getMoney(String StudentUuid) {
		PocketMoney pMoney = null;
		ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM PocketMoney WHERE StudentUuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, StudentUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 pMoney  = beanProcessor.toBean(rset,PocketMoney.class);
	   }
        		
        }catch(SQLException e){
        	 logger.error("SQL Exception while getting PocketMoney with StudentUuid: " + StudentUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e)); 
        }
        //System.out.println(pMoney);
		return pMoney;
	}
   
	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#hasBalance(java.lang.String)
	 */
	@Override
	public boolean studentExist(String StudentUuid) {
		boolean exixt = false;
		String studentid = "";
		try(    Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT StudentUuid FROM PocketMoney "
	        			+ "WHERE StudentUuid = ?;");
      		){
			 
	            pstmt.setString(1, StudentUuid);
	            try(
						ResultSet rset = pstmt.executeQuery();
						
						) {
					
					if(rset.next()) {
						studentid = rset.getString("StudentUuid");	
						exixt = (StringUtils.equals(studentid, StudentUuid)) ? true : false;		
					} 
				}
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to get student PocketMoney for "+StudentUuid);
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
	public boolean hasBalance(PocketMoney money, double amount) {
		boolean hasBalance = false;
		double balance = 0;
		try(    Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT amount FROM PocketMoney "
	        			+ "WHERE StudentUuid = ?;");
      		){
			 
	            pstmt.setString(1, money.getStudentUuid());
	            try(
						ResultSet rset = pstmt.executeQuery();
						
						) {
					
					if(rset.next()) {
						balance = rset.getDouble("amount");	
						hasBalance = (balance >= amount) ? true : false;	
						//System.out.println(balance);
					} 
				}
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to get student PocketMoney of amount "+amount);
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
	public boolean addBalance(PocketMoney money, double amount) {
		boolean success = true;
		if(studentExist(money.getStudentUuid())) {
			try( 
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("UPDATE PocketMoney " +
							"SET amount = (SELECT amount FROM PocketMoney WHERE StudentUuid=? "
							+ ") + ? " +				
							"WHERE Uuid = (SELECT Uuid FROM PocketMoney WHERE StudentUuid=? );");	
					
					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Deposit(Uuid,"
							+ "Studentuuid,Amount,SystemUser,DateCommitted,Term,year) VALUES(?,?,?,?,?,?,?);");
					) {
				
					pstmt.setString(1, money.getStudentUuid());									
					pstmt.setDouble(2, amount);
					pstmt.setString(3, money.getStudentUuid());
					pstmt.executeUpdate();
					
					if(money instanceof Deposit){
						pstmt2.setString(1, money.getUuid());									
						pstmt2.setString(2, money.getStudentUuid());
						pstmt2.setDouble(3, amount);
						pstmt2.setString(4, money.getSystemUser()); 
						pstmt2.setTimestamp(5, new Timestamp(money.getDateCommitted().getTime()));
						pstmt2.setString(6, money.getTerm());
						pstmt2.setString(7, money.getYear());
						pstmt2.executeUpdate();
						
						
					}
				
										
			} catch(SQLException e) {
				logger.error("SQLException while adding by updating the balance of '" + money +
						"' of amount " + amount+"'.");
				logger.error(ExceptionUtils.getStackTrace(e));
				 System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
			
			
		} else { 
			try(	
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO PocketMoney(Uuid,"
							+ "Studentuuid,amount) VALUES(?,?,?);");	
					
					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Deposit(Uuid,"
							+ "Studentuuid,Amount,SystemUser,DateCommitted,Term,year) VALUES(?,?,?,?,?,?,?);");
					) {
					pstmt.setString(1, money.getUuid());
					pstmt.setString(2, money.getStudentUuid());									
					pstmt.setDouble(3, amount);
					pstmt.executeUpdate();	
					
					if(money instanceof Deposit){
						pstmt2.setString(1, money.getUuid());									
						pstmt2.setString(2, money.getStudentUuid());
						pstmt2.setDouble(3, amount);
						pstmt2.setString(4, money.getSystemUser()); 
						pstmt2.setTimestamp(5, new Timestamp(money.getDateCommitted().getTime()));
						pstmt2.setString(6, money.getTerm());
						pstmt2.setString(7, money.getYear());
						pstmt2.executeUpdate();
						
						
					}
						
										
			} catch(SQLException e) {
				logger.error("SQLException while adding by creating the balance of '" + money +
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
	public boolean deductBalance(PocketMoney money, double amount) {
		boolean success = true;
		if(hasBalance(money,0.0)) {
			try( 
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("UPDATE PocketMoney " +
							"SET Amount = (SELECT Amount FROM PocketMoney WHERE StudentUuid=? "
							+ ") - ? " +				
							"WHERE Uuid = (SELECT Uuid FROM PocketMoney WHERE StudentUuid=? );");
					
					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Withdraw(Uuid,"
							+ "Studentuuid,Amount,SystemUser,DateCommitted,Term,year) VALUES(?,?,?,?,?,?,?);");
					) {
				
					pstmt.setString(1, money.getStudentUuid());									
					pstmt.setDouble(2, amount);
					pstmt.setString(3, money.getStudentUuid());
					pstmt.executeUpdate();
					
					if(money instanceof Withdraw){
						pstmt2.setString(1, money.getUuid());									
						pstmt2.setString(2, money.getStudentUuid());
						pstmt2.setDouble(3, amount);
						pstmt2.setString(4, money.getSystemUser()); 
						pstmt2.setTimestamp(5, new Timestamp(money.getDateCommitted().getTime()));
						pstmt2.setString(6, money.getTerm());
						pstmt2.setString(7, money.getYear());
						pstmt2.executeUpdate();
						
						
					}
				
										
			} catch(SQLException e) {
				logger.error("SQLException while adding by updating the balance of '" + money +
			    "' of amount " + amount+"'.");
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
			
			
		} else { 
			try(	
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO PocketMoney(Uuid,"
							+ "Studentuuid,Amount) VALUES(?,?,?);");	
					
					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Withdraw(Uuid,"
							+ "Studentuuid,Amount,SystemUser,DateCommitted,Term,year) VALUES(?,?,?,?,?,?,?);");
					) {
					pstmt.setString(1, money.getUuid());
					pstmt.setString(2, money.getStudentUuid());									
					pstmt.setDouble(3, amount);
					pstmt.executeUpdate();	
					
					if(money instanceof Withdraw){
						pstmt2.setString(1, money.getUuid());									
						pstmt2.setString(2, money.getStudentUuid());
						pstmt2.setDouble(3, amount);
						pstmt2.setString(4, money.getSystemUser()); 
						pstmt2.setTimestamp(5, new Timestamp(money.getDateCommitted().getTime()));
						pstmt2.setString(6, money.getTerm());
						pstmt2.setString(7, money.getYear());
						pstmt2.executeUpdate();
					}	
						
										
			} catch(SQLException e) {
				logger.error("SQLException while adding by creating the balance of '" + money +
				"' of amount " + amount + "'.");
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
		}
						
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#getWithdrawList(java.lang.String)
	 */
	@Override
	public List<Withdraw> getWithdrawList(String StudentUuid,String Term,String Year) {
		List<Withdraw> withdrawList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Withdraw WHERE "
						+ "StudentUuid = ? AND Term = ? AND Year =?;");
				) {
			psmt.setString(1, StudentUuid);
			psmt.setString(2, Term);
			psmt.setString(3, Year);
			try(ResultSet rset = psmt.executeQuery();){
			
				withdrawList = beanProcessor.toBeanList(rset, Withdraw.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Withdraw List for student"+StudentUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return withdrawList;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#getDepositList(java.lang.String)
	 */
	@Override
	public List<Deposit> getDepositList(String StudentUuid,String Term,String Year) {
		List<Deposit> depositeList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Deposit WHERE "
						+ "StudentUuid = ? AND Term = ? AND Year =?;");
				) {
			psmt.setString(1, StudentUuid);
			psmt.setString(2, Term);
			psmt.setString(3, Year);
			try(ResultSet rset = psmt.executeQuery();){
			
				depositeList = beanProcessor.toBeanList(rset, Deposit.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Deposit List for student"+StudentUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return depositeList;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#getWithdrawList(java.lang.String)
	 */
	@Override
	public List<Withdraw> getWithdrawList(String StudentUuid) {
		List<Withdraw> withdrawList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Withdraw WHERE "
						+ "StudentUuid = ?;");
				) {
			psmt.setString(1, StudentUuid);
			try(ResultSet rset = psmt.executeQuery();){
			
				withdrawList = beanProcessor.toBeanList(rset, Withdraw.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Withdraw List for student"+StudentUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return withdrawList;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolPMoneyDAO#getDepositList(java.lang.String)
	 */
	@Override
	public List<Deposit> getDepositList(String StudentUuid) {
		List<Deposit> depositeList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Deposit WHERE "
						+ "StudentUuid = ?;");
				) {
			psmt.setString(1, StudentUuid);
			try(ResultSet rset = psmt.executeQuery();){
			
				depositeList = beanProcessor.toBeanList(rset, Deposit.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Deposit List for student"+StudentUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return depositeList;
	}

	

}
