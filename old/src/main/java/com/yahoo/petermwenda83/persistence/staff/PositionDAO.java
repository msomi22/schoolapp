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
public class PositionDAO extends GenericDAO implements SchoolPositionDAO {

	private static PositionDAO positionDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static PositionDAO getInstance(){
		
		if(positionDAO == null){
			positionDAO = new PositionDAO();		
		}
		return positionDAO;
	}
	
	/**
	 * 
	 */
	public PositionDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public PositionDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

    
	

	@Override
	public AcessLevel get(String Uuid) {
		AcessLevel acessLevel = new AcessLevel();
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM AcessLevel WHERE Uuid = ?;");       
     		
     		){
     	     pstmt.setString(1, Uuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	acessLevel  = beanProcessor.toBean(rset,AcessLevel.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting AcessLevel with uuid: " + Uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return acessLevel; 
	}
	
	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolPositionDAO#putPosition(com.yahoo.petermwenda83.bean.staff.AcessLevel)
	 */
	@Override
	public boolean putPosition(AcessLevel osition) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolPositionDAO#updatePosition(com.yahoo.petermwenda83.bean.staff.AcessLevel)
	 */
	@Override
	public boolean updatePosition(AcessLevel osition) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolPositionDAO#deletePosition(com.yahoo.petermwenda83.bean.staff.AcessLevel)
	 */
	@Override
	public boolean deletePosition(AcessLevel osition) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolPositionDAO#getPositionList(java.lang.String)
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


}
