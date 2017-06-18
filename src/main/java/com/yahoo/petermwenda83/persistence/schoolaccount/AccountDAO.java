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
	public Account get(String Uuid) {
		Account school = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Account WHERE Uuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, Uuid);
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
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchoolByUsername(java.lang.String)
	 */
	public Account getSchoolByUsername(String Username) {
		Account school = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Account WHERE Username = ?;");       
     		
     		){
     	      pstmt.setString(1, Username);
	         rset = pstmt.executeQuery();
	           while(rset.next()){
	    	 school  = beanProcessor.toBean(rset,Account.class);
	         }
     	   	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Account with Username: " + Username);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return school; 
	}
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchoolByPhone(java.lang.String)
	 */
	@Override
	public Account getSchoolByPhone(String mobile) {
		Account school = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Account WHERE mobile = ?;");       
     		
     		){
     	      pstmt.setString(1, mobile);
	         rset = pstmt.executeQuery();
	           while(rset.next()){
	    	 school  = beanProcessor.toBean(rset,Account.class);
	         }
     	   	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Account with mobile: " + mobile);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return school; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchoolByEmail(java.lang.String)
	 */
	@Override
	public Account getSchoolByEmail(String email) {
		Account school = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Account WHERE email = ?;");       
     		
     		){
     	      pstmt.setString(1, email);
	         rset = pstmt.executeQuery();
	           while(rset.next()){
	    	 school  = beanProcessor.toBean(rset,Account.class);
	         }
     	   	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Account with email: " + email);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return school; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchoolByName(java.lang.String)
	 */
	@Override
	public Account getSchoolByName(String schoolName) {
		Account school = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Account WHERE schoolName = ?;");       
     		
     		){
     	      pstmt.setString(1, schoolName);
	         rset = pstmt.executeQuery();
	           while(rset.next()){
	    	 school  = beanProcessor.toBean(rset,Account.class);
	         }
     	   	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Account with schoolName: " + schoolName);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return school; 
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchool(com.yahoo.petermwenda83.bean.account.Account)
	 */
	@Override
	public Account getSchool(String Uuid,String password) {
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
	public boolean put(Account school) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Account" 
			        		+"(Uuid,StatusUuid,SchoolName,Username,Password,Mobile,PostalAddress,Town,Email,DayBoarding,CreationDate) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
     		){
	            pstmt.setString(1, school.getUuid());
	            /*pstmt.setString(2, school.getStatusUuid());
	            pstmt.setString(3, school.getSchoolName());
	            pstmt.setString(4, school.getUsername());
	            pstmt.setString(5, school.getPassword());
	            pstmt.setString(6, school.getMobile());
	            pstmt.setString(7, school.getPostalAddress());
	            pstmt.setString(8, school.getTown());
	            pstmt.setString(9, school.getEmail());
	            pstmt.setString(10, school.getDayBoarding());*/
	            pstmt.setTimestamp(11, new Timestamp(school.getCreationDate().getTime()));
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
	
	public boolean update(Account school) {
		boolean success = true; 
		 try(   Connection conn = dbutils.getConnection();
	      PreparedStatement pstmt = conn.prepareStatement("UPDATE Account SET SchoolName =?,Username =?,Password =?,"
			+ "Mobile =?,PostalAddress =?,Town =?,Email =?,DayBoarding =?,StatusUuid =? WHERE Uuid = ? ;");
       		){
	            /*pstmt.setString(1, school.getSchoolName());
	            pstmt.setString(2, school.getUsername());
	            pstmt.setString(3, school.getPassword());
	            pstmt.setString(4, school.getMobile());
	            pstmt.setString(5, school.getPostalAddress());
	            pstmt.setString(6, school.getTown());
	            pstmt.setString(7, school.getEmail());
	            pstmt.setString(8, school.getDayBoarding());
	            pstmt.setString(9, school.getStatusUuid());*/
	            pstmt.setString(10, school.getUuid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to update Account: "+school);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#delete(com.yahoo.petermwenda83.bean.account.Account)
	 */
	@Override
	public boolean delete(Account school) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getAllSchools()
	 */
	public List<Account> getAllSchools() {
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


}
