/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class TermFeeDAO extends GenericDAO implements SchoolTermFeeDAO {

	private static TermFeeDAO termFeeDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static TermFeeDAO getInstance() {
		if(termFeeDAO == null){
			termFeeDAO = new TermFeeDAO();		
		}
		return termFeeDAO;
	}

	public TermFeeDAO() {
		super();

	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public TermFeeDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}
	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#getTermFee(java.lang.String)
	 */
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#termFeeList(java.lang.String)
	 */
	@Override
	public List<TermFee> getTermFeeList(String schoolAccountUuid) {
		List<TermFee> List = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM TermFee WHERE "
						+ "schoolAccountUuid = ? ORDER BY YEAR DESC;");
				) {
			psmt.setString(1, schoolAccountUuid);
			try(ResultSet rset = psmt.executeQuery();){

				List = beanProcessor.toBeanList(rset, TermFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Fee List for school " +schoolAccountUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return List;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#getTermFee(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public TermFee getFee(String schoolAccountUuid, String Term, String Year) {
		TermFee termFee = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TermFee WHERE schoolAccountUuid = ? AND Term =? AND Year =?;");       

				){

			pstmt.setString(1, schoolAccountUuid);
			pstmt.setString(2, Term);
			pstmt.setString(3, Year);
			rset = pstmt.executeQuery();
			while(rset.next()){

				termFee  = beanProcessor.toBean(rset,TermFee.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting TermFee for schoolAccountUuid " + schoolAccountUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return termFee; 
	}
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#TermFee(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean TermFee(String schoolAccountUuid, String Term, String Year) {
		boolean exist = false;
		String schoolId = "";
		String termId = "";
		String yearId = ""; 
		
		try(    Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TermFee WHERE schoolAccountUuid = ? AND Term =? AND Year =?;");       

      		){
			 
	            pstmt.setString(1, schoolAccountUuid);
	            pstmt.setString(2, Term);
	            pstmt.setString(3, Year);
	            try(
						ResultSet rset = pstmt.executeQuery();
						
						) {
					
					if(rset.next()) {
						schoolId = rset.getString("schoolAccountUuid");	
						termId = rset.getString("Term");	
						yearId = rset.getString("Year");	
						
						exist = (StringUtils.equals(schoolId, schoolAccountUuid) &&
								StringUtils.equals(termId, Term) && 
								StringUtils.equals(yearId, Year)) ? true : false;		
					} 
				}
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to get TermFee for "+schoolAccountUuid);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             exist = false;
		 }
		
		return exist;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#putFee(com.yahoo.petermwenda83.bean.money.TermFee)
	 */
	@Override
	public boolean putFee(TermFee termFee) {
	
		boolean success = true;
		if(TermFee(termFee.getSchoolAccountUuid(),termFee.getTerm(),termFee.getYear())) {

			try (  Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("UPDATE termFee SET TermAmount =?, DayAmount =? WHERE  Term =? AND Year =? AND SchoolAccountUuid = ?;");
					) {           			 	            

				pstmt.setDouble(1, termFee.getTermAmount());
				pstmt.setDouble(2, termFee.getDayAmount());
				pstmt.setString(3, termFee.getTerm());
				pstmt.setString(4, termFee.getYear());
				pstmt.setString(5, termFee.getSchoolAccountUuid());
				pstmt.executeUpdate();

			} catch (SQLException e) {
				logger.error("SQL Exception when updating TermFee " + termFee);
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;
			} 
			
			
		} else {
			
			try( Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TermFee" 
						+"(Uuid,SchoolAccountUuid,Term,Year,TermAmount,DayAmount) VALUES (?,?,?,?,?,?);");
				){

			pstmt.setString(1, termFee.getUuid());
			pstmt.setString(2, termFee.getSchoolAccountUuid());
			pstmt.setString(3, termFee.getTerm());
			pstmt.setString(4, termFee.getYear());
			pstmt.setDouble(5, termFee.getTermAmount());
			pstmt.setDouble(6, termFee.getDayAmount());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put TermFee "+termFee);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}
		
		}
						
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.money.SchoolTermFeeDAO#updateFee(com.yahoo.petermwenda83.bean.money.TermFee)
	 */
	@Override
	public boolean updateFee(TermFee termFee) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE termFee SET TermAmount =?, DayAmount =? WHERE  Term =? AND Year =? AND SchoolAccountUuid = ?;");
				) {           			 	            

			pstmt.setDouble(1, termFee.getTermAmount());
			pstmt.setDouble(2, termFee.getDayAmount());
			pstmt.setString(3, termFee.getTerm());
			pstmt.setString(4, termFee.getYear());
			pstmt.setString(5, termFee.getSchoolAccountUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating TermFee " + termFee);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	
}
