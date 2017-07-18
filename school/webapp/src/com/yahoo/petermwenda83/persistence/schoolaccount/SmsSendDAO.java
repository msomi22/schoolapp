/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.schoolaccount.SmsSend;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * 
 * @author peter
 *
 */
public class SmsSendDAO extends GenericDAO implements SchoolSmsSendDAO {
	
	private static SmsSendDAO smsSendDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static SmsSendDAO getInstance(){
		if(smsSendDAO == null){
			smsSendDAO = new SmsSendDAO();		
		}
		return smsSendDAO;
	}
	
	/**
	 * 
	 */
	public SmsSendDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public SmsSendDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolSmsSendDAO#getSmsSend(java.lang.String)
	 */
	@Override
	public SmsSend getSmsSend(String Uuid) {
		SmsSend smsSend = null;
        ResultSet rset = null;
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SmsSend WHERE Uuid = ?;");       
        		){
        	 pstmt.setString(1, Uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 smsSend  = beanProcessor.toBean(rset,SmsSend.class);
	    }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting an smsSend with uuid: " + Uuid);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
        
		return smsSend; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolSmsSendDAO#getSmsSendByStatus(java.lang.String)
	 */
	@Override
	public SmsSend getSmsSendByStatus(String status) {
		SmsSend smsSend = null;
        ResultSet rset = null;
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SmsSend WHERE status = ?;");       
        		){
        	 pstmt.setString(1, status);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 smsSend  = beanProcessor.toBean(rset,SmsSend.class);
	    }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting an smsSend with status: " + status);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
        
		return smsSend; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolSmsSendDAO#putSmsSend(com.yahoo.petermwenda83.bean.schoolaccount.SmsSend)
	 */
	@Override
	public boolean putSmsSend(SmsSend smsSend) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SmsSend" 
			        		+"(Uuid,Status,PhoneNo,MessageId,Cost) VALUES (?,?,?,?,?);");
       		){
			   
	            pstmt.setString(1, smsSend.getUuid());
	            pstmt.setString(2, smsSend.getStatus());
	            pstmt.setString(3, smsSend.getPhoneNo());
	            pstmt.setString(4, smsSend.getMessageId());
	            pstmt.setString(5, smsSend.getCost());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put SmsSend: "+smsSend);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolSmsSendDAO#updateSmsSend(com.yahoo.petermwenda83.bean.schoolaccount.SmsSend)
	 */
	@Override
	public boolean updateSmsSend(SmsSend smsSend) {
		boolean success = true;
        try (  Connection conn = dbutils.getConnection();
        	   PreparedStatement pstmt = conn.prepareStatement("UPDATE SmsSend SET Status =?,"
        	      + "PhoneNo=?,MessageId=?,Cost=? WHERE Uuid = ?;");
        	) { 
	           pstmt.setString(1, smsSend.getStatus());
	            pstmt.setString(2, smsSend.getPhoneNo());
	            pstmt.setString(3, smsSend.getMessageId());
	            pstmt.setString(4, smsSend.getCost());
	            pstmt.setString(5, smsSend.getUuid());
                pstmt.executeUpdate(); 

        } catch (SQLException e) {
            logger.error("SQL Exception when updating SmsSend");
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
        } 
        
        return success;
		
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolSmsSendDAO#deleteSmsSend(com.yahoo.petermwenda83.bean.schoolaccount.SmsSend)
	 */
	@Override
	public boolean deleteSmsSend(SmsSend smsSend) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM SmsSend WHERE Status = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, smsSend.getStatus());
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting smsSend" + smsSend);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
             
        }
        
		return success; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolSmsSendDAO#getSmsSend()
	 */
	@Override
	public List<SmsSend> getSmsSendList(String status) {
		 List<SmsSend> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM SmsSend WHERE Status = ?;");   
			) {
			 pstmt.setString(1,status);

			 try(ResultSet rset = pstmt.executeQuery();){
				 
				 list = beanProcessor.toBeanList(rset, SmsSend.class);
				}
	        
	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting  SmsSend List");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolSmsSendDAO#getSmsSendList()
	 */
	@Override
	public List<SmsSend> getSmsSend() {
		List<SmsSend> list =new  ArrayList<>(); 
		  try(   
	      		Connection conn = dbutils.getConnection();
	      		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM SmsSend ;");   
	      		ResultSet rset = pstmt.executeQuery();
	  		) {
	      	
	          list = beanProcessor.toBeanList(rset, SmsSend.class);

	      } catch(SQLException e){
	      	  logger.error("SQL Exception when getting all SmsSend");
	          logger.error(ExceptionUtils.getStackTrace(e));
	          System.out.println(ExceptionUtils.getStackTrace(e));
	      }
	   
		return list;

	}

}
