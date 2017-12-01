/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.exam.ClassMean;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class ClassMeanDAO extends GenericDAO implements SchoolClassMeanDAO {

	private static ClassMeanDAO classMeanDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return
	 */
	public static ClassMeanDAO getInstance(){

		if(classMeanDAO == null){
			classMeanDAO = new ClassMeanDAO();		
		}
		return classMeanDAO;
	}

	/**
	 * 
	 */
	public ClassMeanDAO() {
		super();
	}

	/**
	 * 
	 */
	public ClassMeanDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolClassMeanDAO#getClassMean(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ClassMean getClassMean(String accountId, String streamId, String examId, String term, String year) {
		ClassMean classMean = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassMean"
						+ " WHERE accountId =? AND streamId = ? AND examId = ? AND term = ? AND year = ?;");       
				){

			pstmt.setString(1, accountId); 
			pstmt.setString(2, streamId); 
			pstmt.setString(3, examId); 
			pstmt.setString(4, term); 
			pstmt.setString(5, year); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				classMean  = beanProcessor.toBean(rset,ClassMean.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting ClassMean for accountId " + accountId + ", "
					+ "streamId " +  streamId + " , examId " + examId + " , term " + term + " , year " + year);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 

		}

		return classMean; 
	}


	@Override
	public boolean existClassMean(String accountId, String classId, String streamId,String examId, String term, String year) {

		boolean exist = false;

		String dbaccountId = "";
		String dbclassId = "";
		String dbstreamId = "";
		String dbexamId = "";
		String dbterm = "";
		String dbyear = "";

		ResultSet rset = null;
		try(    Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT accountId, classId, streamId, examId, term, year FROM ClassMean "
						+ "WHERE accountId = ? AND classId = ? AND streamId = ? AND examId = ?  AND term = ? AND year = ?;");
				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, classId);
			pstmt.setString(3, streamId);
			pstmt.setString(4, examId);
			pstmt.setString(5, term);
			pstmt.setString(6, year);
			rset = pstmt.executeQuery();

			if(rset.next()){
				dbaccountId = rset.getString("accountId");
				dbclassId = rset.getString("classId");
				dbstreamId = rset.getString("streamId");
				dbexamId = rset.getString("examId");
				dbterm = rset.getString("term");
				dbyear  = rset.getString("year");

				exist = (dbaccountId != accountId &&
						dbclassId != classId && 
						dbstreamId != streamId && dbexamId != examId && 
						dbterm != term && dbyear != year ) ? true : false;

			}

		}
		catch(SQLException e){
			logger.error("SQL Exception while getting score for  Perfomance: ");
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}

		return exist;
	}



	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolClassMeanDAO#putClassMean(com.yahoo.petermwenda83.bean.exam.ClassMean)
	 */
	@Override
	public boolean putClassMean(ClassMean classMean,String accountId,String classId,String streamId,String examId,String term,String year) {
		
		boolean success = true;
		if(!existClassMean(accountId, classId ,streamId ,examId ,term ,year)) {
			
			try(   Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ClassMean" 
							+"(uuid,accountId,classId,streamId,examId,classmean,streammean,term,year,dateAdded) VALUES (?,?,?,?,?,?,?,?,?,?);");
					){

				pstmt.setString(1, classMean.getUuid());
				pstmt.setString(2, accountId);
				pstmt.setString(3, classId);
				pstmt.setString(4, streamId);
				pstmt.setString(5, examId);
				pstmt.setDouble(6, classMean.getClassmean());
				pstmt.setDouble(7, classMean.getStreammean());
				pstmt.setString(8, term);
				pstmt.setString(9, year);
				pstmt.setTimestamp(10, classMean.getDateAdded());
				pstmt.executeUpdate();

			}catch(SQLException e){
				logger.error("SQL Exception trying to put classMean " + classMean);
				logger.error(ExceptionUtils.getStackTrace(e)); 
				success = false;
			}
	
		} else { 
			
			      try(
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmtCatOne = conn.prepareStatement("UPDATE ClassMean SET classmean =?, streammean =? " 
							+"WHERE accountId =? AND classId =? AND streamId =? AND examId = ? "
							+ "AND term = ? AND year =?;");	
			    	
					) {
					
					pstmtCatOne.setDouble(1, classMean.getClassmean());
					pstmtCatOne.setDouble(2, classMean.getStreammean());
					pstmtCatOne.setString(3, accountId);
					pstmtCatOne.setString(4, classId);
					pstmtCatOne.setString(5, streamId);
					pstmtCatOne.setString(6, examId);
					pstmtCatOne.setString(7, term);
					pstmtCatOne.setString(8, year);
					pstmtCatOne.executeUpdate();
				
										
			} catch(SQLException e) {
				logger.error("SQL Exception trying to update classMean " + classMean);
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
		}
		
		return success;

	
	}
	
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolClassMeanDAO#getClassMean(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ClassMean> getClassMeanList(String accountId, String streamId,String examId, String term, String year) {
		List<ClassMean> list = new ArrayList<>();
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassMean WHERE accountId = ? AND "
						+ " streamId =? AND examId =? AND term =? AND year = ?;");    		   
				) {
			pstmt.setString(1, accountId);   
			pstmt.setString(2, streamId); 
			pstmt.setString(3, examId); 
			pstmt.setString(4, term); 
			pstmt.setString(5, year); 
			try( ResultSet rset = pstmt.executeQuery();){
				list = beanProcessor.toBeanList(rset, ClassMean.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting class ClassMean List"); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

	

}
