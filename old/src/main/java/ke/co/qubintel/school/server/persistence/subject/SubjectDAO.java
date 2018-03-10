/**
 *
 * */
package ke.co.qubintel.school.server.persistence.subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.subject.Subject;
import ke.co.qubintel.school.server.persistence.GenericDAO;



/** 
 * 
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 *
 */
public class SubjectDAO extends GenericDAO implements SchoolSubjectDAO {

	private static SubjectDAO subjectDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return subjectDAO
	 */
	public static SubjectDAO getInstance(){
		if(subjectDAO == null){
			subjectDAO = new SubjectDAO();		
		}
		return subjectDAO;
	}

	/**
	 * 
	 */
	public SubjectDAO() {
		super();
	}

	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public SubjectDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubjectDAO#getSubject(ke.co.qubintel.school.server.bean.student.StudentSubject.SubjectUi, com.yahoo.petermwenda83.view.InfoBsic, java.lang.String)
	 */
	@Override
	public Subject getSubjectById(String accountId,String uuid) {
		Subject subject = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Subject WHERE accountId = ? AND"
						+ " uuid =?;");       

				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				subject  = beanProcessor.toBean(rset,Subject.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting subject with uuid '" + uuid + "' for account '" + accountId+"'");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return subject; 
	}
	

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubjectDAO#getSubjectByCode(java.lang.String, java.lang.String)
	 */
	@Override
	public Subject getSubjectByCode(String accountId, String code) {
		Subject subject = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Subject WHERE accountId = ? AND"
						+ " code =?;");       

				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, code);
			rset = pstmt.executeQuery();
			while(rset.next()){
				subject  = beanProcessor.toBean(rset,Subject.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting subject with code '" + code + "' for account '" + accountId+"'");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return subject; 
	}



	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubjectDAO#getSubjects(java.lang.String)
	 */
	@Override
	public Subject getSubject(String accountId,String query) {
		Subject Subject = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Subject WHERE accountId = ? AND"
						+ " code =? AND numericCode =? AND description =?;");       

				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, query);
			pstmt.setString(3, query);
			pstmt.setString(4, query);
			rset = pstmt.executeQuery();
			while(rset.next()){
				Subject  = beanProcessor.toBean(rset,Subject.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting subject with query '" + query + "' for account '" + accountId+"'");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return Subject; 
	}



	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubjectDAO#putSubject(com.yahoo.petermwenda83.bean.student.Subject)
	 */
	@Override
	public boolean putSubject(Subject subject) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Subject" 
						+"(uuid,accountId,categoryId,code,numericCode,description) VALUES (?,?,?,?,?,?);");
				){

			pstmt.setString(1, subject.getUuid());
			pstmt.setString(2, subject.getAccountId());
			pstmt.setString(3, subject.getCategoryId());
			pstmt.setString(4, subject.getCode());
			pstmt.setString(5, subject.getNumericCode());
			pstmt.setString(6, subject.getDescription());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put Subject: "+subject);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			success = false;
		}


		return success;
	}


	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubjectDAO#editSubject(com.yahoo.petermwenda83.bean.student.Subject, java.lang.String)
	 */
	@Override
	public boolean updateSubject(Subject subject) {
		boolean success = true;
		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Subject SET categoryId=?,"
						+ "code=?, numericCode=?, description=? WHERE uuid = ? AND accountId =?;");
				) { 
		
			pstmt.setString(1, subject.getCategoryId());
			pstmt.setString(2, subject.getCode());
			pstmt.setString(3, subject.getNumericCode());
			pstmt.setString(4, subject.getDescription());
			pstmt.setString(5, subject.getUuid());
			pstmt.setString(6, subject.getAccountId());
			pstmt.executeUpdate(); 

		} catch (SQLException e) {
			logger.error("SQL Exception when updating Subject " + subject);
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;

	}



	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubjectDAO#deleteStudent(com.yahoo.petermwenda83.bean.student.Subject)
	 */
	@Override
	public boolean deleteSubject(String accountId,String uuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Subject WHERE accountId= ? AND uuid = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting subject with id " + uuid + " for account " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success; 
	}


	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubjectDAO#getAllStudent()
	 */
	@Override
	public List<Subject> getSubjects(String accountId) {
		List<Subject>  list = null;		
		try(   
			 Connection conn = dbutils.getConnection();
			 PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Subject WHERE accountId =?;");          		
				) {	
			pstmt.setString(1, accountId);
			
			try(ResultSet rset = pstmt.executeQuery();){
			
				list = beanProcessor.toBeanList(rset, Subject.class);
			}
		
		} catch(SQLException e){
			logger.error("SQL Exception when getting all Subject");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

}
