
package com.yahoo.petermwenda83.persistence.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.persistence.GenericDAO;


/**
 *  Persistence implementation for {@link SchoolExamEngineDAO}
 *  
 *  Copyright (c) FasTech Solutions Ltd., Dec 02, 2015
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ExamEgineDAO extends GenericDAO implements SchoolExamEngineDAO {

	private static ExamEgineDAO examEgineDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	
	public static ExamEgineDAO getInstance(){

		if(examEgineDAO == null){ 
			examEgineDAO = new ExamEgineDAO();		
		}
		return examEgineDAO;
	}

	/**
	 * 
	 */
	public ExamEgineDAO() {
		super();
	}

	/**
	 * 
	 */
	public ExamEgineDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamEngineDAO#studentScoreExist(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean studentScoreExist(String accountId, String studentId, String subjectId, String examId,
			String term, String year, String streamId) {

		boolean studentexist = false;

		String dbaccountId = "";
		String dbstudentId = "";
		String dbsubjectId = "";
		String dbexamId = "";
		String dbterm = "";
		String dbyear = "";
		String dbstreamId = "";

		ResultSet rset = null;
		try(    Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT accountId, studentId, subjectId, examId, term, year, streamId FROM Perfomance "
						+ "WHERE accountId = ? AND studentId = ? AND subjectId = ?  AND examId = ? AND term = ? AND year = ? AND streamId = ?;");
				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			pstmt.setString(3, subjectId);
			pstmt.setString(4, examId);
			pstmt.setString(5, term);
			pstmt.setString(6, year);
			pstmt.setString(7, streamId);
			rset = pstmt.executeQuery();

			if(rset.next()){
				dbaccountId = rset.getString("accountId");
				dbstudentId = rset.getString("studentId");
				dbsubjectId = rset.getString("subjectId");
				dbexamId = rset.getString("examId");
				dbterm  = rset.getString("term");
				dbyear  = rset.getString("year");
				dbstreamId  = rset.getString("streamId");

				studentexist = (dbaccountId != accountId &&
						dbstudentId != studentId && 
						dbsubjectId != subjectId && 
						dbexamId != examId && 
						dbterm != term && 
						dbyear != year && 
						dbstreamId != streamId) ? true : false;


			}


		}
		catch(SQLException e){
			logger.error("SQL Exception while getting score for  Perfomance: ");
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}


		return studentexist;

	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamEngineDAO#putPerfomance(com.yahoo.petermwenda83.bean.exam.Perfomance, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean putPerfomance(Perfomance perfomance, String accountId, String studentId, String subjectId,
			String examId, String term, String year, String streamId) {


		boolean success = true;
		if(!studentScoreExist(accountId, studentId ,subjectId ,examId ,term ,year,streamId)) {
		try(   Connection conn = dbutils.getConnection();
				
				PreparedStatement pstmtCatOne = conn.prepareStatement("INSERT INTO Perfomance"
						+"(accountId, studentId, subjectId, streamId ,classRoomId, examId, score, paper1, paper2, paper3, term, year) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
				
				){

				pstmtCatOne.setString(1, accountId);
				pstmtCatOne.setString(2, studentId);
				pstmtCatOne.setString(3, subjectId);
				pstmtCatOne.setString(4, perfomance.getStreamId());
				pstmtCatOne.setString(5, perfomance.getClassRoomId());
				pstmtCatOne.setString(6, perfomance.getExamId());
				pstmtCatOne.setInt(7, perfomance.getScore()); 
				pstmtCatOne.setInt(8, perfomance.getPaper1()); 
				pstmtCatOne.setInt(9, perfomance.getPaper2()); 
				pstmtCatOne.setInt(10, perfomance.getPaper3()); 
				pstmtCatOne.setString(11, term);
				pstmtCatOne.setString(12, year);
				pstmtCatOne.executeUpdate();
			

		}catch(SQLException e){
			logger.error("SQL Exception trying to put Perfomance " + perfomance);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}	
	
		} else { 
			
			      try(
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmtCatOne = conn.prepareStatement("UPDATE Perfomance SET score =? , paper1 =? , paper2 =? , paper3 =? ," 
							+"WHERE accountId =? AND studentId =? AND subjectId =? AND streamId = ? "
							+ "AND examId = ? AND term =? AND year = ?;");	
			    	
					) {
					
					pstmtCatOne.setDouble(1, perfomance.getScore());
					pstmtCatOne.setDouble(2, perfomance.getPaper1());
					pstmtCatOne.setDouble(3, perfomance.getPaper2());
					pstmtCatOne.setDouble(4, perfomance.getPaper3());
					pstmtCatOne.setString(5, accountId);
					pstmtCatOne.setString(6, studentId);
					pstmtCatOne.setString(7, subjectId);
					pstmtCatOne.setString(8, perfomance.getStreamId());
					pstmtCatOne.setString(9, examId);
					pstmtCatOne.setString(10, term);
					pstmtCatOne.setString(11, year);
					pstmtCatOne.executeUpdate();
				
										
			} catch(SQLException e) {
				logger.error("SQL Exception trying to update Perfomance " + perfomance);
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
		}
		
		return success;

	}



}
