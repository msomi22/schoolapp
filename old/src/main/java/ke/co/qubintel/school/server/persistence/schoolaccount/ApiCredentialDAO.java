/**
 * 
 */
package ke.co.qubintel.school.server.persistence.schoolaccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.account.ApiCredential;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class ApiCredentialDAO extends GenericDAO implements SchoolApiCredentialDAO {

	private static ApiCredentialDAO smsApiDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static ApiCredentialDAO getInstance(){
		if(smsApiDAO == null){
			smsApiDAO = new ApiCredentialDAO();		
		}
		return smsApiDAO;
	}
	
	/**
	 * 
	 */
	public ApiCredentialDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public ApiCredentialDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}



	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolApiCredentialDAO#getSmsApi(java.lang.String)
	 */
	@Override
	public ApiCredential getApiCredential(String accountId) {
		ApiCredential smsApi = null;
        ResultSet rset = null;
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ApiCredential WHERE accountId = ?;");       
        		){
        	 pstmt.setString(1, accountId);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 smsApi  = beanProcessor.toBean(rset,ApiCredential.class);
	    }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting an ApiCredential with accountId: " + accountId);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
        
		return smsApi; 
	}
	

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolApiCredentialDAO#getApiCredential(java.lang.String, java.lang.String)
	 */
	@Override
	public ApiCredential getApiCredential(String accountId, String apiType) {
		ApiCredential smsApi = null;
        ResultSet rset = null;
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ApiCredential WHERE accountId = ? AND apiType =? ;");       
        		){
        	 pstmt.setString(1, accountId);
        	 pstmt.setString(2, apiType);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 smsApi  = beanProcessor.toBean(rset,ApiCredential.class);
	    }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting an ApiCredential with accountId: " + accountId + " and apiType " + apiType);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
        
		return smsApi; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolApiCredentialDAO#putSmsApi(ke.co.qubintel.school.server.bean.account.ApiCredential)
	 */
	@Override
	public boolean putApiCredential(ApiCredential smsApi) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ApiCredential" 
			        		+"(uuid,accountId,apiType,apiKey,apisecret) VALUES (?,?,?,?,?);");
      		){
			   
	            pstmt.setString(1, smsApi.getUuid());
	            pstmt.setString(2, smsApi.getAccountId());
	            pstmt.setString(3, smsApi.getApiType());
	            pstmt.setString(4, smsApi.getApiKey());
	            pstmt.setString(5, smsApi.getApisecret()); 
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put SmsApi: "+smsApi);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             success = false;
		 }
		
		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolApiCredentialDAO#updateSmsApi(ke.co.qubintel.school.server.bean.account.ApiCredential)
	 */
	@Override
	public boolean updateApiCredential(ApiCredential smsApi) {
		boolean success = true;
        try (  Connection conn = dbutils.getConnection();
        	   PreparedStatement pstmt = conn.prepareStatement("UPDATE ApiCredential SET apiKey =?,"
        	      + "apisecret=? WHERE apiType =? AND uuid = ? AND accountId = ?;");
        	) { 
        	   
	            pstmt.setString(1, smsApi.getApiKey());
	            pstmt.setString(2, smsApi.getApisecret()); 
	            pstmt.setString(3, smsApi.getApiType());
	            pstmt.setString(4, smsApi.getUuid());
	            pstmt.setString(5, smsApi.getAccountId());
                pstmt.executeUpdate(); 

        } catch (SQLException e) {
            logger.error("SQL Exception when updating SmsApi" + smsApi);
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
        } 
        
        return success;
		
	}


}
