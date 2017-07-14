/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.staff.TeacherSubject;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 * 
 *
 */
public class TeacherSubjectDAO extends GenericDAO  implements SchoolTeacherSubjectDAO {

	private static TeacherSubjectDAO teacherSubjectDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return subjectDAO
	 */
	public static TeacherSubjectDAO getInstance(){
		if(teacherSubjectDAO == null){
			teacherSubjectDAO = new TeacherSubjectDAO();		
		}
		return teacherSubjectDAO;
	}

	/**
	 * 
	 */
	public TeacherSubjectDAO() {
		super();
	}

	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public TeacherSubjectDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubjectDAO#getSubjectClass(java.lang.String)
	 */
	public TeacherSubject getTeacherSubject(String accountId, String streamId, String subjectId) {
		TeacherSubject teachersub = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubject WHERE accountId = ? AND streamId =? AND subjectId =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, streamId);
			pstmt.setString(3, subjectId);
			rset = pstmt.executeQuery();
			while(rset.next()){

				teachersub  = beanProcessor.toBean(rset,TeacherSubject.class);
			}



		}catch(SQLException e){
			logger.error("SQL Exception when getting Staff with TeacherSubject for accountId  " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return teachersub; 
	}
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubjectDAO#putSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubject)
	 */
	public boolean putTeacherSubject(TeacherSubject subClass) {
		boolean success = true; 

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TeacherSubject" 
						+"(uuid, accountId, teacherId, subjectId, streamId, allocationDate) VALUES (?,?,?,?,?,?);");
				){
			pstmt.setString(1, subClass.getUuid());
			pstmt.setString(2, subClass.getAccountId());
			pstmt.setString(3, subClass.getTeacherId());
			pstmt.setString(4, subClass.getSubjectId());
			pstmt.setString(5, subClass.getStreamId());
			pstmt.setTimestamp(6, new Timestamp(subClass.getAllocationDate().getTime()));	           
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put TeacherSubject: "+subClass);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}	

		return success;
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubjectDAO#deleteSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubject)
	 */
	@Override
	public boolean deleteTeacherSubject(String accountId, String teacherId, String streamId, String subjectId) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM TeacherSubject WHERE accountId = ? AND teacherId =?"
						+ " AND streamId =? AND subjectId =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, teacherId);
			pstmt.setString(3, streamId);
			pstmt.setString(4, subjectId);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting TeacherSubject for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success; 
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubjectDAO#getTeacherSubjects(java.lang.String, java.lang.String)
	 */
	public List<TeacherSubject> getTeacherSubjects(String accountId, String teacherId) {
		List<TeacherSubject> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM TeacherSubject WHERE accountId =? AND teacherId =?;");   
				) {

			pstmt.setString(1,accountId);
			pstmt.setString(2,teacherId);
			
			try(ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, TeacherSubject.class);
			}

		} catch(SQLException e){
			logger.error("SQL Exception when getting all TeacherSubject for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}




}
