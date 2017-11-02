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

import com.yahoo.petermwenda83.bean.exam.YearlyMean;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class YearlyMeanDAO  extends GenericDAO implements SchoolYearlyMeanDAO {

	private static YearlyMeanDAO yearlyMeanDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static YearlyMeanDAO getInstance(){

		if(yearlyMeanDAO == null){ 
			yearlyMeanDAO = new YearlyMeanDAO();		
		}
		return yearlyMeanDAO;
	}

	/**
	 * 
	 */
	public YearlyMeanDAO() {
		super();
	}

	/**
	 * 
	 */
	public YearlyMeanDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolYearlyMeanDAO#getBarWeight(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public YearlyMean getYearlyMean(String accountId,String studentId,String year) {
		YearlyMean yearlyMean = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM YearlyMean"
						+ " WHERE accountId = ? AND studentId =? AND year =?;");       

				){

			pstmt.setString(1, accountId); 
			pstmt.setString(2, studentId); 
			pstmt.setString(3, year); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				yearlyMean  = beanProcessor.toBean(rset,YearlyMean.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting YearlyMean: " + yearlyMean);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return yearlyMean; 
	}


	@Override
	public boolean existYearlyMean(String accountId,String studentId,String year) {
		boolean studentexist = false;

		String dbAccountId = "";
		String dbStudentId = "";
		String dbYear = "";

		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM YearlyMean"
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
			logger.error("SQL Exception when getting YearlyMean: ");
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}

		return studentexist; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolYearlyMeanDAO#put(com.yahoo.petermwenda83.bean.exam.YearlyMean)
	 */
	@Override
	public boolean putYearlyMean(YearlyMean yearlyMean,String accountId,String studentId,String year) {
		boolean success = true;
		if(!existYearlyMean(accountId,studentId,year)){
			try(   Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO YearlyMean" 
							+"(uuid,accountId,studentId,year,meanOne,meanTwo,meanThree,termOnePosition,termTwoPosition,termThreePosition) VALUES (?,?,?,?,?,?,?,?,?,?);");
					){

				pstmt.setString(1, yearlyMean.getUuid());
				pstmt.setString(2, accountId);
				pstmt.setString(3, studentId);
				pstmt.setString(4, year);
				pstmt.setDouble(5, yearlyMean.getMeanOne());
				pstmt.setDouble(6, yearlyMean.getMeanTwo());
				pstmt.setDouble(7, yearlyMean.getMeanThree());
				pstmt.setString(8, yearlyMean.getTermOnePosition());
				pstmt.setString(9, yearlyMean.getTermTwoPosition());
				pstmt.setString(10, yearlyMean.getTermThreePosition());

				pstmt.executeUpdate();

			}catch(SQLException e){
				logger.error("SQL Exception trying to put YearlyMean: " + yearlyMean);
				logger.error(ExceptionUtils.getStackTrace(e)); 
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;
			}

		}else{

			try (  Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("UPDATE YearlyMean SET meanOne=?,"
							+ " meanTwo=?, meanThree =?, termOnePosition =?, termTwoPosition =?, termThreePosition=?"
							+ " WHERE accountId = ? AND studentId =?"
							+ "AND year = ?;");
					) { 
				pstmt.setDouble(1, yearlyMean.getMeanOne());
				pstmt.setDouble(2, yearlyMean.getMeanTwo());
				pstmt.setDouble(3, yearlyMean.getMeanThree());
				pstmt.setString(4, yearlyMean.getTermOnePosition());
				pstmt.setString(5, yearlyMean.getTermTwoPosition());
				pstmt.setString(6, yearlyMean.getTermThreePosition());
				pstmt.setString(7, accountId);
				pstmt.setString(8, studentId);
				pstmt.setString(9, year);
				
				pstmt.executeUpdate(); 

			} catch (SQLException e) {
				logger.error("SQL Exception when updating YearlyMean" + yearlyMean);
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;
			} 


		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolYearlyMeanDAO#getYearlyMean(java.lang.String, java.lang.String)
	 */
	@Override
	public List<YearlyMean> getYearlyMean(String accountId, String studentId) {
		List<YearlyMean> list = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM YearlyMean WHERE accountId =? AND studentId =? ORDER BY year DESC;");  
				) {
			
			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
		
			try(ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, YearlyMean.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying YearlyMean List for accountId " + accountId + " , studentId " + studentId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return list;
	}

	

}
