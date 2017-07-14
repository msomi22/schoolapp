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

import com.yahoo.petermwenda83.bean.exam.Deviation;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class DeviationDAO extends GenericDAO implements SchoolDeviationDAO {

	private static DeviationDAO deviationDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static DeviationDAO getInstance(){

		if(deviationDAO == null){ 
			deviationDAO = new DeviationDAO();		
		}
		return deviationDAO;
	}

	/**
	 * 
	 */
	public DeviationDAO() {
		super();
	}

	/**
	 * 
	 */
	public DeviationDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/** 
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolDeviationDAO#getDev(java.lang.String, java.lang.String)
	 */
	@Override
	public Deviation getDev(String accountId,String studentId,String year) {
		Deviation dev = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Deviation"
						+ " WHERE accountId =? AND studentId =? AND year =?;");       

				){
			pstmt.setString(1, accountId); 
			pstmt.setString(2, studentId); 
			pstmt.setString(3, year); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				dev  = beanProcessor.toBean(rset,Deviation.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting BarWeight: " + dev);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return dev; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolDeviationDAO#DevExist(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean DevExist(String accountId,String studentId,String year) {
		boolean studentexist = false;

		String dbAccountId = "";
		String dbStudentId = "";		
		String dbYear = "";

		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Deviation"
						+ " WHERE accountId =? AND studentId =? AND year =?;");       

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
			logger.error("SQL Exception when getting Deviation: ");
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}

		return studentexist; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolDeviationDAO#putDev(com.yahoo.petermwenda83.bean.exam.Deviation)
	 */
	@Override
	public boolean putDev(Deviation dev,String accountId,String studentId,String year) {
		boolean success = true;
		if(!DevExist(accountId,studentId,year)){
			try(   Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Deviation" 
							+"(uuid,accountId,studentId,year,devOne,devTwo,devThree) VALUES (?,?,?,?,?,?,?);");
					){

				pstmt.setString(1, dev.getUuid());
				pstmt.setString(2, dev.getAccountId());
				pstmt.setString(3, dev.getStudentId());
				pstmt.setString(4, dev.getYear());
				pstmt.setDouble(5, dev.getDevOne());
				pstmt.setDouble(6, dev.getDevTwo());
				pstmt.setDouble(7, dev.getDevThree());

				pstmt.executeUpdate();

			}catch(SQLException e){
				logger.error("SQL Exception trying to put Deviation " + dev);
				logger.error(ExceptionUtils.getStackTrace(e)); 
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;
			}

		}else{

			try (  Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("UPDATE Deviation SET devOne=?,"
							+ "devTwo=?,devThree =? WHERE accountId =? AND studentId =? AND year = ?;");
					) { 
				pstmt.setDouble(1, dev.getDevOne());
				pstmt.setDouble(2, dev.getDevTwo());
				pstmt.setDouble(3, dev.getDevThree());
				pstmt.setString(4, dev.getAccountId());
				pstmt.setString(5, dev.getStudentId());
				pstmt.setString(6, dev.getYear());
				pstmt.executeUpdate(); 

			} catch (SQLException e) {
				logger.error("SQL Exception when updating Deviation" + dev);
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;
			} 


		}

		return success;
	}


}
