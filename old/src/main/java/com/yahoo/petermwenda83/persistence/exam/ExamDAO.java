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

import com.yahoo.petermwenda83.bean.exam.Exam;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class ExamDAO extends GenericDAO implements SchoolExamDAO {

	private static ExamDAO examDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static ExamDAO getInstance(){
		
		if(examDAO == null){ 
			examDAO = new ExamDAO();		
		}
		return examDAO;
	}
	
	/**
	 * 
	 */
	public ExamDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public ExamDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamDAO#getExam(java.lang.String)
	 */
	@Override
	public Exam getExam(String accountId,String uuid) {
		Exam exam = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Exam"
						+ " WHERE accountId =? AND uuid = ?;");       
				){

			pstmt.setString(1, accountId); 
			pstmt.setString(2, uuid); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				exam  = beanProcessor.toBean(rset,Exam.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Exam with id " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 

		}

		return exam; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamDAO#getExamByName(java.lang.String)
	 */
	@Override
	public Exam getExamByCode(String accountId,String code) {
		Exam exam = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Exam"
						+ " WHERE accountId =? AND code = ?;");       
				){

			pstmt.setString(1, accountId); 
			pstmt.setString(2, code); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				exam  = beanProcessor.toBean(rset,Exam.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Exam with code " + code);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 

		}

		return exam; 
	}
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamDAO#getExamByName(java.lang.String)
	 */
	@Override
	public Exam getExamByDesc(String accountId,String description) {
		Exam exam = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Exam"
						+ " WHERE accountId =? AND description = ?;");       
				){

			pstmt.setString(1, accountId); 
			pstmt.setString(2, description); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				exam  = beanProcessor.toBean(rset,Exam.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Exam with description " + description);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 

		}

		return exam; 
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamDAO#putExam(com.yahoo.petermwenda83.bean.exam.Exam)
	 */
	@Override
	public boolean putExam(Exam exam) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Exam" 
			        		+"(uuid,accountId,code,description,outOf) VALUES (?,?,?,?,?);");
		             ){
			   
	            pstmt.setString(1, exam.getUuid());
	            pstmt.setString(2, exam.getAccountId());
	            pstmt.setString(3, exam.getCode());	  
	            pstmt.setString(4, exam.getDescription());	  
	            pstmt.setInt(5, exam.getOutOf());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put Exam "+exam);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }
		
		return success;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamDAO#updateExam(com.yahoo.petermwenda83.bean.exam.Exam)
	 */
	@Override
	public boolean updateExam(Exam exam) {
		boolean success = true;
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE Exam SET code = ?,"
			        + "description=?, outOf=? WHERE accountId = ? AND uuid = ?;");
	               ) {   
			  
	            pstmt.setString(1, exam.getCode());
	            pstmt.setString(2, exam.getDescription());
	            pstmt.setInt(3, exam.getOutOf());
	            pstmt.setString(4, exam.getAccountId());
	            pstmt.setString(5, exam.getUuid());       
	            pstmt.executeUpdate();
	
	  } catch (SQLException e) {
	    logger.error("SQL Exception when updating update Exam  " + exam);
	    logger.error(ExceptionUtils.getStackTrace(e));
	    System.out.println(ExceptionUtils.getStackTrace(e));
	    success = false;
	 } 
		
		return success;
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamDAO#getExamList(java.lang.String)
	 */
	@Override
	public List<Exam> getExamList(String accountId) {
		 List<Exam> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Exam WHERE accountId = ?;");   
			) {
			 pstmt.setString(1,accountId);

			 try(ResultSet rset = pstmt.executeQuery();){
				 list = beanProcessor.toBeanList(rset, Exam.class);
			}
	        

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting Exam List for accountId " + accountId);
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
		return list;
	}

}
