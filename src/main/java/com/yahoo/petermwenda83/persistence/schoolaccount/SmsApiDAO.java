/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.account.SmsApi;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class SmsApiDAO extends GenericDAO implements SchoolSmsApiDAO {

	private static SmsApiDAO smsApiDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static SmsApiDAO getInstance(){
		if(smsApiDAO == null){
			smsApiDAO = new SmsApiDAO();		
		}
		return smsApiDAO;
	}
	
	/**
	 * 
	 */
	public SmsApiDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public SmsApiDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}



	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolSmsApiDAO#getSmsApi(java.lang.String)
	 */
	@Override
	public SmsApi getSmsApi(String schoolAccountUuid) {
		SmsApi smsApi = null;
        ResultSet rset = null;
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SmsApi WHERE schoolAccountUuid = ?;");       
        		){
        	 pstmt.setString(1, schoolAccountUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 smsApi  = beanProcessor.toBean(rset,SmsApi.class);
	    }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting an SmsApi with schoolAccountUuid: " + schoolAccountUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
        
		return smsApi; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolSmsApiDAO#putSmsApi(com.yahoo.petermwenda83.bean.account.SmsApi)
	 */
	@Override
	public boolean putSmsApi(SmsApi smsApi) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SmsApi" 
			        		+"(Uuid,SchoolAccountUuid,ApiKey,ApiPassword) VALUES (?,?,?,?);");
      		){
			   
	            pstmt.setString(1, smsApi.getUuid());
	            //pstmt.setString(2, smsApi.getSchoolAccountUuid());
	            pstmt.setString(3, smsApi.getApiKey());
	            pstmt.setString(4, smsApi.getApiPassword()); 
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put SmsApi: "+smsApi);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolSmsApiDAO#updateSmsApi(com.yahoo.petermwenda83.bean.account.SmsApi)
	 */
	@Override
	public boolean updateSmsApi(SmsApi smsApi) {
		boolean success = true;
        try (  Connection conn = dbutils.getConnection();
        	   PreparedStatement pstmt = conn.prepareStatement("UPDATE SmsApi SET ApiKey =?,"
        	      + "ApiPassword=? WHERE Uuid = ? AND SchoolAccountUuid = ?;");
        	) { 
        	   
	            pstmt.setString(1, smsApi.getApiKey());
	            pstmt.setString(2, smsApi.getApiPassword()); 
	            pstmt.setString(3, smsApi.getUuid());
	            //pstmt.setString(4, smsApi.getSchoolAccountUuid());
                pstmt.executeUpdate(); 

        } catch (SQLException e) {
            logger.error("SQL Exception when updating SmsApi");
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
        } 
        
        return success;
		
	}

}
