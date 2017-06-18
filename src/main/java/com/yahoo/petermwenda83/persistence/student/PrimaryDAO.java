/**
 * 
 */
package com.yahoo.petermwenda83.persistence.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class PrimaryDAO extends GenericDAO implements SchoolPrimaryDAO {

	private static PrimaryDAO primaryDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static PrimaryDAO getInstance(){
		if(primaryDAO == null){
			primaryDAO = new PrimaryDAO();		
		}
		return primaryDAO;
	}
	
	/**  
	 * 
	 */
	public PrimaryDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public PrimaryDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}



	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolPrimaryDAO#getPrimary(java.lang.String)
	 */
	@Override
	public StudentPrimary getPrimary(String StudentUuid) {
		StudentPrimary primary = null;
		ResultSet rset = null;
		
		  try(   Connection conn = dbutils.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentPrimary"
						+ " WHERE StudentUuid =?;");
		         ){
			  pstmt.setString(1, StudentUuid); 
		      rset = pstmt.executeQuery();
		     while(rset.next()){
		    	 primary  = beanProcessor.toBean(rset,StudentPrimary.class);
		   }
	        	
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to get Student Primary Info with StudentUuid: "+StudentUuid);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
    
		 }
		return primary;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolPrimaryDAO#putPrimary(com.yahoo.petermwenda83.bean.student.StudentPrimary)
	 */
	@Override
	public boolean putPrimary(StudentPrimary Primary) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentPrimary" 
			        		+"(Uuid, StudentUuid, SchoolName,Index,KcpeYear,KcpeMark) VALUES (?,?,?,?,?,?);");
		             ){
			   
	            pstmt.setString(1, Primary.getUuid());
	            pstmt.setString(2, Primary.getStudentUuid());
	            pstmt.setString(3, Primary.getSchoolname());	       
	            pstmt.setString(4, Primary.getIndex());
	            pstmt.setString(5, Primary.getKcpeyear());
	            pstmt.setString(6, Primary.getKcpemark());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put StudentPrimary: "+Primary);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
          success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolPrimaryDAO#updatePrimary(com.yahoo.petermwenda83.bean.student.StudentPrimary)
	 */
	@Override
	public boolean updatePrimary(StudentPrimary Primary) {
		boolean success = true;
		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentPrimary SET Schoolname = ?,Index = ?,Kcpeyear = ?,"
			        + "Kcpemark =? WHERE StudentUuid = ?;");
	               ) {           			 	            
	            pstmt.setString(1, Primary.getSchoolname());	       
	            pstmt.setString(2, Primary.getIndex());
	            pstmt.setString(3, Primary.getKcpeyear());
	            pstmt.setString(4, Primary.getKcpemark());
	            pstmt.setString(5, Primary.getStudentUuid());
	            pstmt.executeUpdate();

    } catch (SQLException e) {
      logger.error("SQL Exception when updating StudentPrimary " + Primary);
      logger.error(ExceptionUtils.getStackTrace(e));
      System.out.println(ExceptionUtils.getStackTrace(e));
      success = false;
   } 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolPrimaryDAO#deletePrimary(com.yahoo.petermwenda83.bean.student.StudentPrimary)
	 */
	@Override
	public boolean deletePrimary(StudentPrimary Primary) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentPrimary"
	         	      		+ " WHERE StudentUuid =?;");       
	      		
	      		){
	      	
	      	     pstmt.setString(1, Primary.getStudentUuid());
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting StudentPrimary : " +Primary);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolPrimaryDAO#getAllPrimary()
	 */
	@Override
	public List<StudentPrimary> getAllPrimary() {
		List<StudentPrimary> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentPrimary;");   
	  		ResultSet rset = pstmt.executeQuery();
			) {
	  	
	      list = beanProcessor.toBeanList(rset, StudentPrimary.class);

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting List of Student's Primary Details");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
	  return list;
	}

}
