/**
 * 
 */
package ke.co.qubintel.school.server.persistence.schoolaccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.account.OutGoingSMS;
import ke.co.qubintel.school.server.bean.staff.Staff;
import ke.co.qubintel.school.server.persistence.GenericDAO;

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
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolSmsSendDAO#getSmsSend(java.lang.String)
	 */
	@Override
	public OutGoingSMS getSmsSend(String Uuid) {
		OutGoingSMS outGoingSMS = null;
        ResultSet rset = null;
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OutGoingSMS WHERE Uuid = ?;");       
        		){
        	 pstmt.setString(1, Uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 outGoingSMS  = beanProcessor.toBean(rset,OutGoingSMS.class);
	    }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting an smsSend with uuid: " + Uuid);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
        
		return outGoingSMS; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolSmsSendDAO#getSmsSendByStatus(java.lang.String)
	 */
	@Override
	public OutGoingSMS getSmsSendByStatus(String status) {
		OutGoingSMS outGoingSMS = null;
        ResultSet rset = null;
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OutGoingSMS WHERE status = ?;");       
        		){
        	 pstmt.setString(1, status);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 outGoingSMS  = beanProcessor.toBean(rset,OutGoingSMS.class);
	    }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting an smsSend with status: " + status);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
        
		return outGoingSMS; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolSmsSendDAO#putSmsSend(ke.co.qubintel.school.server.bean.account.OutGoingSMS)
	 */
	@Override
	public boolean putSmsSend(OutGoingSMS outGoingSMS) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO OutGoingSMS" 
			        		+"(Uuid,Status,PhoneNo,MessageId,Cost) VALUES (?,?,?,?,?);");
       		){
			   
	            pstmt.setString(1, outGoingSMS.getUuid());
	            pstmt.setString(2, outGoingSMS.getStatus());
	          /*  pstmt.setString(3, outGoingSMS.getPhoneNo());
	            pstmt.setString(4, outGoingSMS.getMessageId());
	            pstmt.setString(5, outGoingSMS.getCost());*/
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put OutGoingSMS: "+outGoingSMS);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            success = false;
		 }
		
		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolSmsSendDAO#updateSmsSend(ke.co.qubintel.school.server.bean.account.OutGoingSMS)
	 */
	@Override
	public boolean updateSmsSend(OutGoingSMS outGoingSMS) {
		boolean success = true;
        try (  Connection conn = dbutils.getConnection();
        	   PreparedStatement pstmt = conn.prepareStatement("UPDATE OutGoingSMS SET Status =?,"
        	      + "PhoneNo=?,MessageId=?,Cost=? WHERE Uuid = ?;");
        	) { 
	           pstmt.setString(1, outGoingSMS.getStatus());
	           /* pstmt.setString(2, outGoingSMS.getPhoneNo());
	            pstmt.setString(3, outGoingSMS.getMessageId());
	            pstmt.setString(4, outGoingSMS.getCost());*/
	            pstmt.setString(5, outGoingSMS.getUuid());
                pstmt.executeUpdate(); 

        } catch (SQLException e) {
            logger.error("SQL Exception when updating OutGoingSMS");
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
        } 
        
        return success;
		
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolSmsSendDAO#deleteSmsSend(ke.co.qubintel.school.server.bean.account.OutGoingSMS)
	 */
	@Override
	public boolean deleteSmsSend(OutGoingSMS outGoingSMS) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM OutGoingSMS WHERE Status = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, outGoingSMS.getStatus());
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting smsSend" + outGoingSMS);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
             
        }
        
		return success; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolSmsSendDAO#getSmsSend()
	 */
	@Override
	public List<OutGoingSMS> getSmsSendList(String status) {
		 List<OutGoingSMS> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM OutGoingSMS WHERE Status = ?;");   
			) {
			 pstmt.setString(1,status);

			 try(ResultSet rset = pstmt.executeQuery();){
				 
				 list = beanProcessor.toBeanList(rset, OutGoingSMS.class);
				}
	        
	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting  OutGoingSMS List");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
		return list;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolSmsSendDAO#getSmsSendList()
	 */
	@Override
	public List<OutGoingSMS> getSmsSend() {
		List<OutGoingSMS> list =new  ArrayList<>(); 
		  try(   
	      		Connection conn = dbutils.getConnection();
	      		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM OutGoingSMS ;");   
	      		ResultSet rset = pstmt.executeQuery();
	  		) {
	      	
	          list = beanProcessor.toBeanList(rset, OutGoingSMS.class);

	      } catch(SQLException e){
	      	  logger.error("SQL Exception when getting all OutGoingSMS");
	          logger.error(ExceptionUtils.getStackTrace(e));
	          System.out.println(ExceptionUtils.getStackTrace(e));
	      }
	   
		return list;

	}

}
