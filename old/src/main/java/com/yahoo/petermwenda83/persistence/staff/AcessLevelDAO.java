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

    
	

	@Override
	public AcessLevel get(String uuid) {
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
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolAcessLevelDAO#getPositionList(java.lang.String)
	 */
	@Override
	public List<AcessLevel> getPositionList() {
		List<AcessLevel> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM AcessLevel;");   
			) {
			
			 try(ResultSet rset = pstmt.executeQuery();){
					
				 list = beanProcessor.toBeanList(rset, AcessLevel.class);
				}
	        

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting all Positions for school ");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }

		
		return list;
	}

	@Override
	public boolean putPosition(AcessLevel acessLevel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updatePosition(AcessLevel acessLevel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePosition(String uuid) {
		// TODO Auto-generated method stub
		return false;
	}


}
