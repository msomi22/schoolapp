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

import com.yahoo.petermwenda83.bean.exam.BarWeight;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class BarWeightDAO  extends GenericDAO implements SchoolBarWeightDAO {

	private static BarWeightDAO barWeightDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static BarWeightDAO getInstance(){

		if(barWeightDAO == null){ 
			barWeightDAO = new BarWeightDAO();		
		}
		return barWeightDAO;
	}

	/**
	 * 
	 */
	public BarWeightDAO() {
		super();
	}

	/**
	 * 
	 */
	public BarWeightDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolBarWeightDAO#getBarWeight(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public BarWeight getBarWeight(String accountId,String studentId,String year) {
		BarWeight barWeight = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM BarWeight"
						+ " WHERE accountId = ? AND studentId =? AND year =?;");       

				){

			pstmt.setString(1, accountId); 
			pstmt.setString(2, studentId); 
			pstmt.setString(3, year); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				barWeight  = beanProcessor.toBean(rset,BarWeight.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting BarWeight: " + barWeight);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return barWeight; 
	}


	@Override
	public boolean ExistBarWeight(String accountId,String studentId,String year) {
		boolean studentexist = false;

		String dbAccountId = "";
		String dbStudentId = "";
		String dbYear = "";

		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM BarWeight"
						+ " WHERE accountId = ? AND studentId =? AND year =?;");       

				){

			pstmt.setString(1, accountId); 
			pstmt.setString(2, studentId); 
			pstmt.setString(3, year); 
			rset = pstmt.executeQuery();

			if(rset.next()){
				dbAccountId = rset.getString("accountId");
				dbStudentId = rset.getString("studentId");
				dbYear = rset.getString("year");

				studentexist = (dbAccountId != accountId &&
						dbStudentId != studentId && 
						dbYear != year ) ? true : false;

			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting BarWeight: ");
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}

		return studentexist; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolBarWeightDAO#put(com.yahoo.petermwenda83.bean.exam.BarWeight)
	 */
	@Override
	public boolean put(BarWeight weight,String accountId,String studentId,String year) {
		boolean success = true;
		if(!ExistBarWeight(accountId,studentId,year)){
			try(   Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO BarWeight" 
							+"(uuid,accountId,studentId,year,meanOne,meanTwo,meanThree) VALUES (?,?,?,?,?,?,?);");
					){

				pstmt.setString(1, weight.getUuid());
				pstmt.setString(2, accountId);
				pstmt.setString(3, studentId);
				pstmt.setString(4, year);
				pstmt.setDouble(5, weight.getMeanOne());
				pstmt.setDouble(6, weight.getMeanOne());
				pstmt.setDouble(7, weight.getMeanThree());

				pstmt.executeUpdate();

			}catch(SQLException e){
				logger.error("SQL Exception trying to put BarWeight: " + weight);
				logger.error(ExceptionUtils.getStackTrace(e)); 
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;
			}

		}else{

			try (  Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("UPDATE BarWeight SET meanOne=?,"
							+ "meanTwo=?,meanThree =? WHERE accountId = ? AND studentId =?"
							+ "AND year = ?;");
					) { 
				pstmt.setDouble(1, weight.getMeanOne());
				pstmt.setDouble(2, weight.getMeanOne());
				pstmt.setDouble(3, weight.getMeanThree());
				pstmt.setString(4, accountId);
				pstmt.setString(5, studentId);
				pstmt.setString(6, year);
				pstmt.executeUpdate(); 

			} catch (SQLException e) {
				logger.error("SQL Exception when updating BarWeight" + weight);
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;
			} 


		}

		return success;
	}

	

}
