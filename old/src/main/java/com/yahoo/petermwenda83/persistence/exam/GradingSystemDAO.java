/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class GradingSystemDAO extends GenericDAO implements ScoolGradingSystemDAO {

	private static GradingSystemDAO gradingSystemDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static GradingSystemDAO getInstance(){
		
		if(gradingSystemDAO == null){ 
			gradingSystemDAO = new GradingSystemDAO();		
		}
		return gradingSystemDAO;
	}
	
	/**
	 * 
	 */
	public GradingSystemDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public GradingSystemDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.ScoolGradingSystemDAO#getGradingSystem(java.lang.String, java.lang.String)
	 */
	@Override
	public GradingSystem getGradingSystem(String accountId, String uuid) {
		GradingSystem gradingSystem = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GradingSystem"
						+ " WHERE accountId = ? AND uuid =?;");       

				){

			pstmt.setString(1, accountId); 
			pstmt.setString(2, uuid); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				gradingSystem  = beanProcessor.toBean(rset,GradingSystem.class);
			}



		}catch(SQLException e){
			logger.error("SQL Exception when getting GradingSystem with accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return gradingSystem; 
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.ScoolGradingSystemDAO#putGradingSystem(com.yahoo.petermwenda83.bean.exam.GradingSystem)
	 */
	@Override
	public boolean putGradingSystem(GradingSystem gradingSystem) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO GradingSystem" 
			        		+"(uuid,accountId,categoryId,lowerLimit,upperLimit,description,points)"
			        		+ " VALUES (?,?,?,?,?,?,?);");
      		){
			   
			    pstmt.setString(1, gradingSystem.getUuid());
			    pstmt.setString(2, gradingSystem.getAccountId());
			    pstmt.setString(3, gradingSystem.getCategoryId());
	            pstmt.setInt(4, gradingSystem.getLowerLimit());
	            pstmt.setInt(5, gradingSystem.getUpperLimit());
	            pstmt.setString(6, gradingSystem.getDescription());
	            pstmt.setInt(7, gradingSystem.getPoints());	            
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put GradingSystem: "+gradingSystem);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.ScoolGradingSystemDAO#updateGradingSystem(com.yahoo.petermwenda83.bean.exam.GradingSystem)
	 */
	@Override
	public boolean updateGradingSystem(GradingSystem gradingSystem) {
		boolean success = true;
        try (  Connection conn = dbutils.getConnection();
        	PreparedStatement pstmt = conn.prepareStatement("UPDATE GradingSystem SET lowerLimit =?,"
        			+ "upperLimit =?,description =?,points=? WHERE accountId = ? AND uuid =?;");
        	) { 
        	   
	        	pstmt.setInt(1, gradingSystem.getLowerLimit());
	            pstmt.setInt(2, gradingSystem.getUpperLimit());
	            pstmt.setString(3, gradingSystem.getDescription());
	            pstmt.setInt(4, gradingSystem.getPoints());
	            pstmt.setString(5, gradingSystem.getAccountId());
	            pstmt.setString(6, gradingSystem.getUuid());
                pstmt.executeUpdate(); 

        } catch (SQLException e) {
            logger.error("SQL Exception when updating GradingSystem" + gradingSystem);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
        } 
        
        return success;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.ScoolGradingSystemDAO#getGradingSystemList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<GradingSystem> getGradingSystemList(String accountId, String categoryId) {
		 List<GradingSystem> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM GradingSystem WHERE accountId = ? AND categoryId =? ORDER BY points DESC ;");   
			) {
			 pstmt.setString(1,accountId);
			 pstmt.setString(2,categoryId);

			 try(ResultSet rset = pstmt.executeQuery();){
				 list = beanProcessor.toBeanList(rset, GradingSystem.class);
			}
	        

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting GradingSystem List for accountId " + accountId + " and categoryId " + categoryId);
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
		return list;
	}

}
