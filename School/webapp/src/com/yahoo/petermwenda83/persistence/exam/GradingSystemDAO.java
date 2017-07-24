/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.exam.ScoolGradingSystemDAO#getGradingSystem(java.lang.String)
	 */
	@Override
	public GradingSystem getGradingSystem(String schoolAccountUuid) {
		GradingSystem gradingSystem = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GradingSystem"
						+ " WHERE SchoolAccountUuid = ?;");       

				){

			pstmt.setString(1, schoolAccountUuid); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				gradingSystem  = beanProcessor.toBean(rset,GradingSystem.class);
			}



		}catch(SQLException e){
			logger.error("SQL Exception when getting GradingSystem with schoolAccountUuid: " + schoolAccountUuid);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return gradingSystem; 
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.exam.ScoolGradingSystemDAO#putGradingSystem(com.yahoo.petermwenda83.bean.exam.GradingSystem)
	 */
	@Override
	public boolean putGradingSystem(GradingSystem gradingSystem) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO GradingSystem" 
			        		+"(Uuid,SchoolAccountUuid,GradeAplain,GradeAminus,GradeBplus,GradeBplain,"
			        		+ "GradeBminus,GradeCplus,GradeCplain,GradeCminus,GradeDplus,GradeDplain,"
			        		+ "GradeDminus,GradeE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
      		){
			   
			    pstmt.setString(1, gradingSystem.getUuid());
			    pstmt.setString(2, gradingSystem.getSchoolAccountUuid());
			    pstmt.setInt(3, gradingSystem.getGradeAplain());
	            pstmt.setInt(4, gradingSystem.getGradeAminus());
	            pstmt.setInt(5, gradingSystem.getGradeBplus());
	            pstmt.setInt(6, gradingSystem.getGradeBplain());
	            pstmt.setInt(7, gradingSystem.getGradeBminus());
	            pstmt.setInt(8, gradingSystem.getGradeCplus());
	            pstmt.setInt(9, gradingSystem.getGradeCplain());
	            pstmt.setInt(10, gradingSystem.getGradeCminus());
	            pstmt.setInt(11, gradingSystem.getGradeDplus());
	            pstmt.setInt(12, gradingSystem.getGradeDplain());
	            pstmt.setInt(13, gradingSystem.getGradeDminus());
	            pstmt.setInt(14, gradingSystem.getGradeE());
	           
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put GradingSystem: "+gradingSystem);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.exam.ScoolGradingSystemDAO#updateGradingSystem(com.yahoo.petermwenda83.bean.exam.GradingSystem)
	 */
	@Override
	public boolean updateGradingSystem(GradingSystem gradingSystem) {
		boolean success = true;
        try (  Connection conn = dbutils.getConnection();
        	PreparedStatement pstmt = conn.prepareStatement("UPDATE GradingSystem SET GradeAplain =?,"
        			+ "GradeAminus =?,GradeBplus =?,GradeBplain=?,GradeBminus =?,GradeCplus =?,"
        			+ "GradeCplain =?,GradeCminus =?,GradeDplus =?,GradeDplain =?,GradeDminus =?,"
        			+ "GradeE =?  WHERE SchoolAccountUuid = ? AND Uuid =?;");
        	) { 
        	   
			    pstmt.setInt(1, gradingSystem.getGradeAplain());
	            pstmt.setInt(2, gradingSystem.getGradeAminus());
	            pstmt.setInt(3, gradingSystem.getGradeBplus());
	            pstmt.setInt(4, gradingSystem.getGradeBplain());
	            pstmt.setInt(5, gradingSystem.getGradeBminus());
	            pstmt.setInt(6, gradingSystem.getGradeCplus());
	            pstmt.setInt(7, gradingSystem.getGradeCplain());
	            pstmt.setInt(8, gradingSystem.getGradeCminus());
	            pstmt.setInt(9, gradingSystem.getGradeDplus());
	            pstmt.setInt(10, gradingSystem.getGradeDplain());
	            pstmt.setInt(11, gradingSystem.getGradeDminus());
	            pstmt.setInt(12, gradingSystem.getGradeE());	            
			    pstmt.setString(13, gradingSystem.getSchoolAccountUuid());
			    pstmt.setString(14, gradingSystem.getUuid());
                pstmt.executeUpdate(); 

        } catch (SQLException e) {
            logger.error("SQL Exception when updating GradingSystem" + gradingSystem);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
        } 
        
        return success;
	}

}
