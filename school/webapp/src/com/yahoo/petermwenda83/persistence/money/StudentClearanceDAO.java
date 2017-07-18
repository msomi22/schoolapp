/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.money.StudentClearance;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class StudentClearanceDAO extends GenericDAO implements SchoolStudentClearanceDAO {
	
	private static StudentClearanceDAO studentClearanceDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static StudentClearanceDAO getInstance() {
		if(studentClearanceDAO == null){
			studentClearanceDAO = new StudentClearanceDAO();		
			}
		return studentClearanceDAO;
	}
	
	public StudentClearanceDAO() {
		super();
		
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public StudentClearanceDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentClearanceDAO#getClearance(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public StudentClearance getClearance(String studentUuid, String term, String year) {
		StudentClearance clearance = null;
		ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentClearance WHERE studentUuid = ? AND term = ? AND year = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentUuid);
        	 pstmt.setString(2, term);
        	 pstmt.setString(3, year);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 clearance  = beanProcessor.toBean(rset,StudentClearance.class);
	   }
        		
        }catch(SQLException e){
        	 logger.error("SQL Exception while getting StudentClearance with StudentUuid: " + studentUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e)); 
        }
        
		return clearance;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentClearanceDAO#getClearanceByYear(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentClearance getClearanceByYear(String studentUuid, String year) {
		StudentClearance clearance = null;
		ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentClearance WHERE studentUuid = ? AND year = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentUuid);
        	 pstmt.setString(2, year);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 clearance  = beanProcessor.toBean(rset,StudentClearance.class);
	   }
        		
        }catch(SQLException e){
        	 logger.error("SQL Exception while getting StudentClearance with StudentUuid: " + studentUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e)); 
        }
        
		return clearance;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentClearanceDAO#getClearanceByStudentId(java.lang.String)
	 */
	@Override
	public StudentClearance getClearanceByStudentId(String studentUuid) {
		StudentClearance clearance = null;
		ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentClearance WHERE studentUuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 clearance  = beanProcessor.toBean(rset,StudentClearance.class);
	   }
        		
        }catch(SQLException e){
        	 logger.error("SQL Exception while getting StudentClearance with StudentUuid: " + studentUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e)); 
        }
        
		return clearance;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentClearanceDAO#existStudent(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean existStudent(String studentUuid, String term, String year) {
		boolean studentexist = false;
		
		String the_studentUuid = "";
		String the_term = "";
		String the_year = "";
		
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentClearance"
						+ " WHERE studentUuid = ? AND term =? AND year =?;");       

				){

			pstmt.setString(1, studentUuid); 
			pstmt.setString(2, term); 
			pstmt.setString(3, year); 
			rset = pstmt.executeQuery();
			 
            if(rset.next()){
            	the_studentUuid = rset.getString("studentUuid");
            	the_term = rset.getString("term");
            	the_year = rset.getString("year");
				
				studentexist = (the_studentUuid != studentUuid&&
						        the_term != term && 
								the_year != year ) ? true : false;
				
			}

		}catch(SQLException e){
			 logger.error("SQL Exception when getting BarWeight: ");
			 logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));

		}

		return studentexist; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolStudentClearanceDAO#put(com.yahoo.petermwenda83.bean.money.StudentClearance)
	 */
	@Override
	public boolean put(StudentClearance clearance,String studentUuid, String term, String year) {
		boolean success = true;
		if(!existStudent(studentUuid,term,year)){
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentClearance" 
			        		+"(Uuid,studentUuid,clearingAmount,term,year) VALUES (?,?,?,?,?);");
      		){
			   
			    pstmt.setString(1, clearance.getUuid());
			    pstmt.setString(2, studentUuid);
			    pstmt.setDouble(3, clearance.getClearingAmount());
	            pstmt.setString(4, term);
	            pstmt.setString(5, year);
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put StudentClearance: "+clearance);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		 
		}else{

			 try (  Connection conn = dbutils.getConnection();
	        	PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentClearance SET clearingAmount=?"
	        			+ " WHERE studentUuid = ? AND term =?"
	        			+ "AND year = ?;"); 
	        	) { 
				    pstmt.setDouble(1, clearance.getClearingAmount());
		            pstmt.setString(2, studentUuid);
		            pstmt.setString(3, term);
		            pstmt.setString(4, year);
	                pstmt.executeUpdate(); 

	        } catch (SQLException e) {
	            logger.error("SQL Exception when updating StudentClearance" + clearance);
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e));
	            success = false;
	        } 
			
			
		}
		
		return success;
	}

}
