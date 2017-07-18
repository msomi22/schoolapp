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

import com.yahoo.petermwenda83.bean.staff.Position;
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
	public Position get(String Uuid) {
		Position position = new Position();
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Position WHERE Uuid = ?;");       
     		
     		){
     	     pstmt.setString(1, Uuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	position  = beanProcessor.toBean(rset,Position.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Position with uuid: " + Uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return position; 
	}
	
	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolPositionDAO#putPosition(com.yahoo.petermwenda83.bean.staff.Position)
	 */
	@Override
	public boolean putPosition(Position osition) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolPositionDAO#updatePosition(com.yahoo.petermwenda83.bean.staff.Position)
	 */
	@Override
	public boolean updatePosition(Position osition) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolPositionDAO#deletePosition(com.yahoo.petermwenda83.bean.staff.Position)
	 */
	@Override
	public boolean deletePosition(Position osition) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolPositionDAO#getPositionList(java.lang.String)
	 */
	@Override
	public List<Position> getPositionList() {
		List<Position> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Position;");   
			) {
			
			 try(ResultSet rset = pstmt.executeQuery();){
					
				 list = beanProcessor.toBeanList(rset, Position.class);
				}
	        

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting all Positions for school ");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }

		
		return list;
	}


}
