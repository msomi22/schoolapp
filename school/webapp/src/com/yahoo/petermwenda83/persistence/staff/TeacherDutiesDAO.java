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

import com.yahoo.petermwenda83.bean.staff.TeacherDuties;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class TeacherDutiesDAO extends GenericDAO implements SchoolTeacherDutiesDAO {
	
	private static TeacherDutiesDAO teacherDutiesDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static TeacherDutiesDAO getInstance(){
		
		if(teacherDutiesDAO == null){
			teacherDutiesDAO = new TeacherDutiesDAO();		
		}
		return teacherDutiesDAO;
	}
	
	/**
	 * 
	 */
	public TeacherDutiesDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public TeacherDutiesDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherDutiesDAO#getTeacherDuties(java.lang.String)
	 */
	@Override
	public TeacherDuties getTeacherDuties(String teacherUuid) {
		TeacherDuties teacherDuties = new TeacherDuties();
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherDuties WHERE TeacherUuid = ?;");       
     		
     		){
     	     pstmt.setString(1, teacherUuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	teacherDuties  = beanProcessor.toBean(rset,TeacherDuties.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting TeacherDuties with TeacherUuid: " + teacherUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return teacherDuties; 
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherDutiesDAO#putTeacherDuties(com.yahoo.petermwenda83.bean.staff.TeacherDuties)
	 */
	@Override
	public boolean putTeacherDuties(TeacherDuties teacherDuty) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TeacherDuties" 
			        		+"(Uuid,TeacherUuid,DutyUuid) VALUES (?,?,?);");
  		){
	            pstmt.setString(1, teacherDuty.getUuid());
	            pstmt.setString(2, teacherDuty.getTeacherUuid());
	            pstmt.setString(3, teacherDuty.getDutyUuid());	                      
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put TeacherDuties: "+teacherDuty);
          logger.error(ExceptionUtils.getStackTrace(e)); 
          System.out.println(ExceptionUtils.getStackTrace(e));
          success = false;
		 }	
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherDutiesDAO#updateTeacherDuties(com.yahoo.petermwenda83.bean.staff.TeacherDuties)
	 */
	@Override
	public boolean updateTeacherDuties(TeacherDuties teacherDuty) {
		boolean success = true;		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE TeacherDuties SET DutyUuid=? WHERE TeacherUuid = ?;");
	) {           			 	            
			    pstmt.setString(1, teacherDuty.getUuid());
	            pstmt.setString(2, teacherDuty.getTeacherUuid());
	            pstmt.setString(3, teacherDuty.getDutyUuid());	     	           
	            pstmt.executeUpdate();

	} catch (SQLException e) {
	  logger.error("SQL Exception when updating TeacherDuties " + teacherDuty);
	  logger.error(ExceptionUtils.getStackTrace(e));
	  System.out.println(ExceptionUtils.getStackTrace(e));
	  success = false;
	} 
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherDutiesDAO#deleteTeacherDuties(com.yahoo.petermwenda83.bean.staff.TeacherDuties)
	 */
	@Override
	public boolean deleteTeacherDuties(TeacherDuties teacherDuty) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherDutiesDAO#getTeacherDutyList()
	 */
	@Override
	public List<TeacherDuties> getTeacherDutyList() {
		List<TeacherDuties> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM TeacherDuties;");   
			) {
			
			 try(ResultSet rset = pstmt.executeQuery();){
					
				 list = beanProcessor.toBeanList(rset, TeacherDuties.class);
				}
	        

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting all TeacherDuties ");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }

		
		return list;
	}

}
