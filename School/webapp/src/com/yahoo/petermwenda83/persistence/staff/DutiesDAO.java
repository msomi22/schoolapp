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

import com.yahoo.petermwenda83.bean.staff.Duties;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class DutiesDAO extends GenericDAO implements SchoolDutiesDAO {

	private static DutiesDAO dutiesDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static DutiesDAO getInstance(){
		
		if(dutiesDAO == null){
			dutiesDAO = new DutiesDAO();		
		}
		return dutiesDAO;
	}
	
	/**
	 * 
	 */
	public DutiesDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public DutiesDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolDutiesDAO#getDuty(java.lang.String)
	 */
	@Override
	public Duties getDuty(String Uuid) {
		Duties Duty = new Duties();
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Duties WHERE Uuid = ?;");       
     		
     		){
     	     pstmt.setString(1, Uuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	Duty  = beanProcessor.toBean(rset,Duties.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Duties with Uuid: " + Uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return Duty; 
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolDutiesDAO#putDuty(com.yahoo.petermwenda83.bean.staff.Duties)
	 */
	@Override
	public boolean putDuty(Duties duty) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolDutiesDAO#updateDuty(com.yahoo.petermwenda83.bean.staff.Duties)
	 */
	@Override
	public boolean updateDuty(Duties duty) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolDutiesDAO#deleteDuty(com.yahoo.petermwenda83.bean.staff.Duties)
	 */
	@Override
	public boolean deleteDuty(Duties duty) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolDutiesDAO#getDutiesList()
	 */
	@Override
	public List<Duties> getDutiesList() {
		List<Duties> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Duties;");   
			) {
			
			 try(ResultSet rset = pstmt.executeQuery();){
					
				 list = beanProcessor.toBeanList(rset, Duties.class);
				}
	        

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting all Duties ");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }

		
		return list;
	}

}
