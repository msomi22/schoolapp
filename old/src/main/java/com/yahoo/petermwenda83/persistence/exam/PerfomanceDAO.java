

/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package com.yahoo.petermwenda83.persistence.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 *  Persistence implementation for {@link SchoolPerfomanceDAO}
 *  
 *  Copyright (c) FasTech Solutions Ltd., Dec 02, 2015
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class PerfomanceDAO extends GenericDAO  implements SchoolPerfomanceDAO {
	
	
	private static PerfomanceDAO perfomanceDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static PerfomanceDAO getInstance(){
		
		if(perfomanceDAO == null){
			perfomanceDAO = new PerfomanceDAO();		
		}
		return perfomanceDAO;
	}
	
	/**
	 * 
	 */
	public PerfomanceDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public PerfomanceDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolPerfomanceDAO#getStreamPerformance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Perfomance> getStreamPerformance(String accountId, String examId, String studentId, String streamId,
			String term, String year) {
		List<Perfomance> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Perfomance WHERE accountId = ? AND"
     	         		+ " examId=? AND studentId = ? AND streamId =? AND term = ? AND year = ? ORDER BY examId;");    		   
     	   ) {
         	   pstmt.setString(1, accountId);      
         	   pstmt.setString(2, examId); 
         	   pstmt.setString(3, studentId);  
         	   pstmt.setString(4, streamId); 
         	   pstmt.setString(5, term); 
       	       pstmt.setString(6, year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Perfomance.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Stream Perfomance List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolPerfomanceDAO#getClassPerformance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Perfomance> getClassPerformance(String accountId, String examId, String studentId, String classRoomId,
			String term, String year) {
		List<Perfomance> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Perfomance WHERE accountId = ? AND"
     	         		+ " examId=? AND studentId = ? AND classRoomId =? AND term = ? AND year = ?;");    		   
     	   ) {
         	   pstmt.setString(1, accountId);   
         	   pstmt.setString(2, examId); 
         	   pstmt.setString(3, studentId);  
         	   pstmt.setString(4, classRoomId); 
         	   pstmt.setString(5, term); 
       	       pstmt.setString(6, year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Perfomance.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting class Perfomance List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolPerfomanceDAO#deletePerfomance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deletePerfomance(String accountId, String examId, String studentId, String term, String year) {
		boolean success = true;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Perfomance"
						+ " WHERE accountId = ? AND  examId=? AND  studentId = ? AND term = ? AND year =?;");       

				){

			pstmt.setString(1, accountId); 
			pstmt.setString(2, examId); 
			pstmt.setString(3, studentId); 
			pstmt.setString(4, term); 
			pstmt.setString(5, year); 
			pstmt.executeUpdate();


		}catch(SQLException e){
			logger.error("SQL Exception when deleting  Perfomance for studentId " + studentId);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return success; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolPerfomanceDAO#getStreamSubjectPerfomance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Perfomance> getStreamSubjectPerfomance(String accountId, String examId, String subjectId,
			String streamId, String term, String year) {
		List<Perfomance> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Perfomance WHERE accountId = ? AND"
     	         		+ " examId=? AND  subjectId = ? AND streamId =? AND term = ? AND year = ?;");    		   
     	   ) {
         	   pstmt.setString(1, accountId);  
         	   pstmt.setString(2, examId); 
         	   pstmt.setString(3, subjectId);  
         	   pstmt.setString(4, streamId); 
         	   pstmt.setString(5, term); 
       	       pstmt.setString(6, year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Perfomance.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting stream subject Perfomance List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolPerfomanceDAO#getClassSubjectPerfomance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Perfomance> getClassSubjectPerfomance(String accountId, String examId, String subjectId,
			String classRoomId, String term, String year) {
		List<Perfomance> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Perfomance WHERE accountId = ? AND"
     	         		+ " examId=? AND subjectId = ? AND classRoomId =? AND term = ? AND year = ?;");    		   
     	   ) {
         	   pstmt.setString(1, accountId);  
         	   pstmt.setString(2, examId); 
         	   pstmt.setString(3, subjectId);  
         	   pstmt.setString(4, classRoomId); 
         	   pstmt.setString(5, term); 
       	       pstmt.setString(6, year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Perfomance.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting class subject Perfomance List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}


}
