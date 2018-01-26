/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.staff.AcessLevel;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class AcessLevelDAO extends GenericDAO implements SchoolAcessLevelDAO {

	private static AcessLevelDAO acessLevelDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static AcessLevelDAO getInstance(){
		
		if(acessLevelDAO == null){
			acessLevelDAO = new AcessLevelDAO();		
		}
		return acessLevelDAO;
	}
	
	/**
	 * 
	 */
	public AcessLevelDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public AcessLevelDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

    
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolAcessLevelDAO#getAcessLevel(java.lang.String)
	 */
	@Override
	public AcessLevel getAcessLevel(String uuid) {
		AcessLevel acessLevel = new AcessLevel();
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM AcessLevel WHERE uuid = ?;");       
     		
     		){
     	     pstmt.setString(1, uuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	acessLevel  = beanProcessor.toBean(rset,AcessLevel.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting AcessLevel with uuid: " + uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return acessLevel; 
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolAcessLevelDAO#getAcessLevel(java.lang.String, java.lang.String)
	 */
	@Override
	public AcessLevel getAcessLevel(String accountId, String uuid) {
		AcessLevel acessLevel = new AcessLevel();
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM AcessLevel WHERE accountId =? AND uuid =?;");       
     		
     		){
     	     pstmt.setString(1, accountId);
     	     pstmt.setString(2, uuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	acessLevel  = beanProcessor.toBean(rset,AcessLevel.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting AcessLevel with uuid: " + uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return acessLevel; 
	}


	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolAcessLevelDAO#getPositionList(java.lang.String)
	 */
	@Override
	public List<AcessLevel> getAcessLevelList(String accountId) {
		List<AcessLevel> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM AcessLevel WHERE accountId =? ;");   
				) {

			pstmt.setString(1,accountId);
			
			try(ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, AcessLevel.class);
			}

		} catch(SQLException e){
			logger.error("SQL Exception when getting all AcessLevels for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolAcessLevelDAO#putPosition(com.yahoo.petermwenda83.bean.staff.AcessLevel)
	 */
	@Override
	public boolean putAcessLevel(AcessLevel acessLevel) {
		
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO AcessLevel" 
						+"(uuid,accountId,acessId,description) VALUES (?,?,?,?);");
				){

			pstmt.setString(1, acessLevel.getUuid());
			pstmt.setString(2, acessLevel.getAccountId());
			pstmt.setString(3, acessLevel.getAcessId());
			pstmt.setString(4, acessLevel.getDescription());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put acessLevel " + acessLevel);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolAcessLevelDAO#updatePosition(com.yahoo.petermwenda83.bean.staff.AcessLevel)
	 */
	@Override
	public boolean updateAcessLevel(AcessLevel acessLevel) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE AcessLevel SET description = ?, acessId=?"
						+ "WHERE uuid = ? AND accountId = ?;");
				) {           			 	            
			pstmt.setString(1, acessLevel.getDescription());
			pstmt.setString(2, acessLevel.getAcessId());
			pstmt.setString(3, acessLevel.getUuid());
			pstmt.setString(4, acessLevel.getAccountId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating acessLevel " + acessLevel);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	@Override
	public boolean deleteAcessLevel(String uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
