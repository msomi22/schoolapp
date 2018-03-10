/**
 * 
 */
package ke.co.qubintel.school.server.persistence.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/** 
 * @author peter
 * 
 */
public class SysConfigDAO extends GenericDAO implements SchoolSysConfigDAO {

	private static SysConfigDAO sysConfigDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static SysConfigDAO getInstance(){

		if(sysConfigDAO == null){ 
			sysConfigDAO = new SysConfigDAO();		
		}
		return sysConfigDAO;
	}

	/**
	 * 
	 */
	public SysConfigDAO() {
		super();
	}

	/**
	 * 
	 */
	public SysConfigDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see ke.co.qubintel.school.server.persistence.exam.SchoolSysConfigDAO#getExamConfig(java.lang.String)
	 */
	@Override
	public SysConfig getSysConfig(String accountId) {
		SysConfig sysConfig = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SysConfig"
						+ " WHERE accountId = ?;");       

				){

			pstmt.setString(1, accountId); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				sysConfig  = beanProcessor.toBean(rset,SysConfig.class);
			}



		}catch(SQLException e){
			logger.error("SQL Exception when getting SysConfig: " + sysConfig);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return sysConfig; 
	}
	
	/**
	 * @see ke.co.qubintel.school.server.persistence.exam.SchoolSysConfigDAO#getSysConfig(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public SysConfig getSysConfig(String accountId, String term, String year) {
		SysConfig sysConfig = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SysConfig"
						+ " WHERE accountId = ? AND term =? AND year =?;");       

				){

			pstmt.setString(1, accountId); 
			pstmt.setString(2, term); 
			pstmt.setString(3, year); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				sysConfig  = beanProcessor.toBean(rset,SysConfig.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting SysConfig: " + sysConfig + " and term : " + term + " and year " + year);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return sysConfig; 
	}


	/**
	 * @see ke.co.qubintel.school.server.persistence.exam.SchoolSysConfigDAO#putExamConfig(ke.co.qubintel.school.server.bean.exam.SysConfig)
	 */
	@Override
	public boolean putSysConfig(SysConfig sysConfig) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SysConfig" 
						+"(uuid,accountId,term,year,cansendSMS) VALUES (?,?,?,?,?);");
				){

			pstmt.setString(1, sysConfig.getUuid());
			pstmt.setString(2, sysConfig.getAccountId());
			pstmt.setString(3, sysConfig.getTerm());
			pstmt.setString(4, sysConfig.getYear());
			pstmt.setString(5, sysConfig.getCansendSMS());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put SysConfig " + sysConfig);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			success = false;
		}


		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.exam.SchoolSysConfigDAO#updateExamConfig(ke.co.qubintel.school.server.bean.exam.SysConfig)
	 */
	@Override
	public boolean updateSysConfig(SysConfig sysConfig) {
		boolean success = true;
		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE SysConfig SET "
						+ "term=?, year =?, cansendSMS=? WHERE accountId = ?;");
				) { 

			pstmt.setString(1, sysConfig.getTerm());
			pstmt.setString(2, sysConfig.getYear());
			pstmt.setString(3, sysConfig.getCansendSMS());
			pstmt.setString(4, sysConfig.getAccountId());
			pstmt.executeUpdate(); 

		} catch (SQLException e) {
			logger.error("SQL Exception when updating SysConfig" + sysConfig);
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.exam.SchoolSysConfigDAO#getExamConfigList(java.lang.String)
	 */
	@Override
	public List<SysConfig> getSysConfigList(String accountId) {
		List<SysConfig> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM SysConfig WHERE accountId = ?;");   
				) {
			pstmt.setString(1,accountId);

			try(ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, SysConfig.class);
			}


		} catch(SQLException e){
			logger.error("SQL Exception when getting SysConfig List for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}
		return list;
	}

	
}
