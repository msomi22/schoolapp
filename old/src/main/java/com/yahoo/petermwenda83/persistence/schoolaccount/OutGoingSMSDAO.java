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

import com.yahoo.petermwenda83.bean.account.OutGoingSMS;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class OutGoingSMSDAO extends GenericDAO implements SchoolOutGoingSMSDAO {

	private static OutGoingSMSDAO outGoingSMSDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static OutGoingSMSDAO getInstance(){
		if(outGoingSMSDAO == null){
			outGoingSMSDAO = new OutGoingSMSDAO();		
		}
		return outGoingSMSDAO;
	}

	/**
	 * 
	 */
	public OutGoingSMSDAO() { 
		super();
	}

	/**
	 * 
	 */
	public OutGoingSMSDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolOutGoingSMSDAO#getOutGoingSMS(java.lang.String, java.lang.String)
	 */
	@Override
	public OutGoingSMS getOutGoingSMS(String accountId, String uuid) {
		OutGoingSMS outGoingSMS = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OutGoingSMS WHERE accountId = ? AND uuid =?;");       
				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				outGoingSMS  = beanProcessor.toBean(rset,OutGoingSMS.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting an OutGoingSMS for accountId " + accountId + " and " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return outGoingSMS; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolOutGoingSMSDAO#getOutGoingSMSList(java.lang.String, int, int)
	 */
	@Override
	public List<OutGoingSMS> getOutGoingSMSList(String accountId, String status, int startIndex, int endIndex) {
		List<OutGoingSMS> outGoingSMSList = new ArrayList<>();

		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM OutGoingSMS WHERE "
						+ "accountId = ? AND status =? LIMIT ? OFFSET ? ;");
				) {
			psmt.setString(1, accountId);
			psmt.setString(2, status);
			psmt.setInt(3, endIndex - startIndex);
			psmt.setInt(4, startIndex);

			try(ResultSet rset = psmt.executeQuery();){
				outGoingSMSList = beanProcessor.toBeanList(rset, OutGoingSMS.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a OutGoingSMS List  for accountId " + accountId + " status " + status);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return outGoingSMSList;		
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolOutGoingSMSDAO#putOutGoingSMS(com.yahoo.petermwenda83.bean.account.OutGoingSMS)
	 */
	@Override
	public boolean putOutGoingSMS(OutGoingSMS outGoingSMS) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO OutGoingSMS" 
						+"(uuid,accountId,status,mobile,message,smsCost,sendDate) VALUES (?,?,?,?,?,?,?);");
				){

			pstmt.setString(1, outGoingSMS.getUuid());
			pstmt.setString(2, outGoingSMS.getAccountId());
			pstmt.setString(3, outGoingSMS.getStatus());
			pstmt.setString(4, outGoingSMS.getMobile());
			pstmt.setString(5, outGoingSMS.getMessage());
			pstmt.setString(6, outGoingSMS.getSmsCost());
			pstmt.setTimestamp(7, outGoingSMS.getSendDate());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put OutGoingSMS " + outGoingSMS);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			success = false;
		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolOutGoingSMSDAO#updateOutGoingSMS(com.yahoo.petermwenda83.bean.account.OutGoingSMS)
	 */
	@Override
	public boolean updateOutGoingSMS(OutGoingSMS outGoingSMS) {
		boolean success = true;
		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE OutGoingSMS SET status =?,"
						+ "mobile=?,message=?,smsCost=? WHERE accountId = ? AND uuid =?;");
				) { 

			pstmt.setString(1, outGoingSMS.getStatus());
			pstmt.setString(2, outGoingSMS.getMobile());
			pstmt.setString(3, outGoingSMS.getMessage());
			pstmt.setString(4, outGoingSMS.getSmsCost());
			pstmt.setString(5, outGoingSMS.getAccountId());
			pstmt.setString(6, outGoingSMS.getUuid());

			pstmt.executeUpdate(); 

		} catch (SQLException e) {
			logger.error("SQL Exception when updating OutGoingSMS " + outGoingSMS);
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;

	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolOutGoingSMSDAO#deleteOutGoingSMS(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteOutGoingSMS(String accountId, String uuid) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM OutGoingSMS WHERE accountId =? AND uuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, accountId);
        	 pstmt.setString(2, uuid);
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting OutGoingSMS for accountId " + accountId + "and uuid " + uuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
             
        }
        
		return success; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.schoolaccount.SchoolOutGoingSMSDAO#deleteOutGoingSMS(java.lang.String)
	 */
	@Override
	public boolean deleteOutGoingSMSByStatus(String accountId, String status) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM OutGoingSMS WHERE accountId =? AND status = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, accountId);
        	 pstmt.setString(2, status);
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting OutGoingSMS for accountId " + accountId + "and status " + status);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
             
        }
        
		return success; 
	}



}
