
package com.yahoo.petermwenda83.persistence.guardian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ParentsDAO extends GenericDAO  implements SchoolParentsDAO {

	private static ParentsDAO parentsDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static ParentsDAO getInstance(){

		if(parentsDAO == null){
			parentsDAO = new ParentsDAO();		
		}
		return parentsDAO;
	}

	/** 
	 * 
	 */
	public ParentsDAO() {
		super();
	}

	/**
	 * 
	 */
	public ParentsDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#getParent(java.lang.String)
	 */
	@Override
	public StudentParent getParent(String accountId, String studentId) {
		StudentParent studentParent = null;
		ResultSet rset = null;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM studentParent"
						+ " WHERE accountId =? AND studentId =?;");
				){
			pstmt.setString(1, accountId); 
			pstmt.setString(2, studentId); 
			rset = pstmt.executeQuery();
			while(rset.next()){
				studentParent  = beanProcessor.toBean(rset,StudentParent.class);
			}


		}catch(SQLException e){
			logger.error("SQL Exception trying to get studentParent with studentId: "+studentId);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}
		return studentParent;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#putParent(com.yahoo.petermwenda83.bean.student.guardian.StudentParent)
	 */
	@Override
	public boolean putParent(StudentParent parent) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentParent" 
						+"(uuid, accountId, studentId, name, mobile, email, lastUpdated) VALUES "
						+ "(?,?,?,?,?,?,?);");
				){

			pstmt.setString(1, parent.getUuid());
			pstmt.setString(2, parent.getAccountId());	            
			pstmt.setString(3, parent.getStudentId());	       
			pstmt.setString(4, parent.getName());
			pstmt.setString(5, parent.getMobile());	       
			pstmt.setString(6, parent.getEmail());
			pstmt.setString(7, parent.getLastUpdated());		            
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put StudentParent: "+parent);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#updateParent(com.yahoo.petermwenda83.bean.student.guardian.StudentParent)
	 */
	@Override
	public boolean updateParent(StudentParent parent) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentParent SET name = ?, mobile = ?, email = ?,"
						+ "lastUpdated =? WHERE accountId = ? AND studentId =? ;");
				) {   

			pstmt.setString(1, parent.getName());
			pstmt.setString(2, parent.getMobile());	       
			pstmt.setString(3, parent.getEmail());
			pstmt.setString(4, parent.getLastUpdated());
			pstmt.setString(5, parent.getAccountId());	            
			pstmt.setString(6, parent.getStudentId());	 
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating update StudentParent " + parent);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#deleteParent(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteParent(String accountId, String studentId) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentParent"
						+ " WHERE accountId =? AND studentId =?;");       
				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			pstmt.executeUpdate();
		}catch(SQLException e){
			logger.error("SQL Exception when deletting parent for studentId : " +studentId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#getParentList()
	 */
	@Override
	public List<StudentParent> getParents(String accountId) {
		List<StudentParent> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentParent WHERE accountId =?;");   
				) {

			pstmt.setString(1,accountId);
			
			try(ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, StudentParent.class);
			}

		} catch(SQLException e){
			logger.error("SQL Exception when getting Parent List");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}
		return list;
	}



	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#getParents(java.lang.String, int, int)
	 */
	@Override
	public List<StudentParent> getParents(String accountId,int startIndex, int endIndex) {
		List<StudentParent> parentList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentParent WHERE accountId =? LIMIT ? OFFSET ? ;");
				) {
			
			psmt.setString(1, accountId);
			psmt.setInt(2, endIndex - startIndex);
			psmt.setInt(3, startIndex);

			try(ResultSet rset = psmt.executeQuery();){
				parentList = beanProcessor.toBeanList(rset, StudentParent.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a parentList for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return parentList;		
	}

}
