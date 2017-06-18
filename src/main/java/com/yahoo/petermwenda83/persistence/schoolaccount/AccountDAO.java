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

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
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
	public SchoolAccount get(String Uuid) {
		SchoolAccount school = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SchoolAccount WHERE Uuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, Uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 school  = beanProcessor.toBean(rset,SchoolAccount.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting SchoolAccount with Uuid: " + Uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return school; 
	}
	
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchoolByUsername(java.lang.String)
	 */
	public SchoolAccount getSchoolByUsername(String Username) {
		SchoolAccount school = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SchoolAccount WHERE Username = ?;");       
     		
     		){
     	      pstmt.setString(1, Username);
	         rset = pstmt.executeQuery();
	           while(rset.next()){
	    	 school  = beanProcessor.toBean(rset,SchoolAccount.class);
	         }
     	   	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting SchoolAccount with Username: " + Username);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return school; 
	}
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchoolByPhone(java.lang.String)
	 */
	@Override
	public SchoolAccount getSchoolByPhone(String mobile) {
		SchoolAccount school = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SchoolAccount WHERE mobile = ?;");       
     		
     		){
     	      pstmt.setString(1, mobile);
	         rset = pstmt.executeQuery();
	           while(rset.next()){
	    	 school  = beanProcessor.toBean(rset,SchoolAccount.class);
	         }
     	   	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting SchoolAccount with mobile: " + mobile);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return school; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchoolByEmail(java.lang.String)
	 */
	@Override
	public SchoolAccount getSchoolByEmail(String email) {
		SchoolAccount school = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SchoolAccount WHERE email = ?;");       
     		
     		){
     	      pstmt.setString(1, email);
	         rset = pstmt.executeQuery();
	           while(rset.next()){
	    	 school  = beanProcessor.toBean(rset,SchoolAccount.class);
	         }
     	   	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting SchoolAccount with email: " + email);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return school; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchoolByName(java.lang.String)
	 */
	@Override
	public SchoolAccount getSchoolByName(String schoolName) {
		SchoolAccount school = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SchoolAccount WHERE schoolName = ?;");       
     		
     		){
     	      pstmt.setString(1, schoolName);
	         rset = pstmt.executeQuery();
	           while(rset.next()){
	    	 school  = beanProcessor.toBean(rset,SchoolAccount.class);
	         }
     	   	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting SchoolAccount with schoolName: " + schoolName);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return school; 
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getSchool(com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount)
	 */
	@Override
	public SchoolAccount getSchool(String Uuid,String password) {
		SchoolAccount school = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SchoolAccount WHERE Uuid = ? AND password =?;");       
     		
     		){
     	
		       pstmt.setString(1, Uuid);
		       pstmt.setString(2, password);
	           rset = pstmt.executeQuery();
	           while(rset.next()){
	    	    school  = beanProcessor.toBean(rset,SchoolAccount.class);
	           }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting SchoolAccount with Uuid: " + Uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return school; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#put(com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount)
	 */
	public boolean put(SchoolAccount school) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SchoolAccount" 
			        		+"(Uuid,StatusUuid,SchoolName,Username,Password,Mobile,PostalAddress,Town,Email,DayBoarding,CreationDate) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
     		){
	            pstmt.setString(1, school.getUuid());
	            pstmt.setString(2, school.getStatusUuid());
	            pstmt.setString(3, school.getSchoolName());
	            pstmt.setString(4, school.getUsername());
	            pstmt.setString(5, school.getPassword());
	            pstmt.setString(6, school.getMobile());
	            pstmt.setString(7, school.getPostalAddress());
	            pstmt.setString(8, school.getTown());
	            pstmt.setString(9, school.getEmail());
	            pstmt.setString(10, school.getDayBoarding());
	            pstmt.setTimestamp(11, new Timestamp(school.getCreationDate().getTime()));
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put SchoolAccount: "+school);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#update(com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount)
	 */
	
	public boolean update(SchoolAccount school) {
		boolean success = true; 
		 try(   Connection conn = dbutils.getConnection();
	      PreparedStatement pstmt = conn.prepareStatement("UPDATE SchoolAccount SET SchoolName =?,Username =?,Password =?,"
			+ "Mobile =?,PostalAddress =?,Town =?,Email =?,DayBoarding =?,StatusUuid =? WHERE Uuid = ? ;");
       		){
	            pstmt.setString(1, school.getSchoolName());
	            pstmt.setString(2, school.getUsername());
	            pstmt.setString(3, school.getPassword());
	            pstmt.setString(4, school.getMobile());
	            pstmt.setString(5, school.getPostalAddress());
	            pstmt.setString(6, school.getTown());
	            pstmt.setString(7, school.getEmail());
	            pstmt.setString(8, school.getDayBoarding());
	            pstmt.setString(9, school.getStatusUuid());
	            pstmt.setString(10, school.getUuid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to update SchoolAccount: "+school);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#delete(com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount)
	 */
	@Override
	public boolean delete(SchoolAccount school) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolAccountDAO#getAllSchools()
	 */
	public List<SchoolAccount> getAllSchools() {
		 List<SchoolAccount> list =new  ArrayList<>(); 
		  try(   
	      		Connection conn = dbutils.getConnection();
	      		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM SchoolAccount ;");   
	      		ResultSet rset = pstmt.executeQuery();
	  		) {
	      	
	          list = beanProcessor.toBeanList(rset, SchoolAccount.class);

	      } catch(SQLException e){
	      	  logger.error("SQL Exception when getting all Schools");
	          logger.error(ExceptionUtils.getStackTrace(e));
	          System.out.println(ExceptionUtils.getStackTrace(e));
	      }
	   
		return list;
	}


}
