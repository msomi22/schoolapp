/**
 * 
 */
package ke.co.qubintel.school.server.persistence.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.staff.ClassTeacher;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/**
 * @author peter
 * 
 */
public class ClassTeacherDAO extends GenericDAO implements SchoolClassTeacherDAO {
	
	private static ClassTeacherDAO classTeacherDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static ClassTeacherDAO getInstance(){
		
		if(classTeacherDAO == null){
			classTeacherDAO = new ClassTeacherDAO();		
		}
		return classTeacherDAO;
	}
	
	/**
	 * 
	 */
	public ClassTeacherDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public ClassTeacherDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	
	
	/**
	 * @see ke.co.qubintel.school.server.persistence.staff.SchoolClassTeacherDAO#getClassTeacherById(java.lang.String, java.lang.String)
	 */
	@Override
	public ClassTeacher getClassTeacherById(String accountId, String uuid) {
		ClassTeacher classTeacher =null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassTeacher WHERE accountId = ? AND uuid =?;");       

				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				classTeacher  = beanProcessor.toBean(rset,ClassTeacher.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting classTeacher with accountId " + accountId + " and uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return classTeacher; 
	}


    
	/**
	 * @see ke.co.qubintel.school.server.persistence.staff.SchoolClassTeacherDAO#getClassTeacher(java.lang.String, java.lang.String)
	 */
	public ClassTeacher getClassTeacher(String accountId, String id) {
		ClassTeacher classTeacher =null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassTeacher WHERE accountId = ? AND (teacherId = ? OR streamId =?);");       

				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, id);
			pstmt.setString(3, id);
			rset = pstmt.executeQuery();
			while(rset.next()){

				classTeacher  = beanProcessor.toBean(rset,ClassTeacher.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting classTeacher with accountId " + accountId + " and id " + id);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return classTeacher; 
	}
	
	
	
	/**
	 * @see ke.co.qubintel.school.server.persistence.staff.SchoolClassTeacherDAO#putClassTeacher(ke.co.qubintel.school.server.bean.staff.ClassTeacher)
	 */
	public boolean putClassTeacher(ClassTeacher classTeacher) {
		boolean success = true; 

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ClassTeacher" 
						+"(uuid,accountId,teacherId,streamId) VALUES (?,?,?,?);");
				){
			pstmt.setString(1, classTeacher.getUuid());
			pstmt.setString(2, classTeacher.getAccountId());
			pstmt.setString(3, classTeacher.getTeacherId());	 
			pstmt.setString(4, classTeacher.getStreamId());	 
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put classTeacher " + classTeacher);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}	

		return success;
	}
	

	/**
	 * @see ke.co.qubintel.school.server.persistence.staff.SchoolClassTeacherDAO#updateClassTeacher(ke.co.qubintel.school.server.bean.staff.ClassTeacher)
	 */
	@Override
	public boolean updateClassTeacher(ClassTeacher teacher) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE ClassTeacher SET streamId =? WHERE teacherId = ? AND accountId =?;");
				) {           			 	            

			pstmt.setString(1, teacher.getStreamId());
			pstmt.setString(2, teacher.getTeacherId());
			pstmt.setString(3, teacher.getAccountId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating teacher " + teacher);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.staff.SchoolClassTeacherDAO#deleteClassTeacher(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteClassTeacher(String accountId, String uuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ClassTeacher"
						+ " WHERE accountId =? AND uuid=? ;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting ClassTeacher with id  " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.staff.SchoolClassTeacherDAO#getClassTeacherList()
	 */
	public List<ClassTeacher> getClassTeacherList(String accountId) {
		List<ClassTeacher> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM ClassTeacher WHERE accountId =? ;");   
				) {

			pstmt.setString(1,accountId);
			
			try(ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, ClassTeacher.class);
			}

		} catch(SQLException e){
			logger.error("SQL Exception when getting all ClassTeacher for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.staff.SchoolClassTeacherDAO#getClassTeacherList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ClassTeacher> getClassTeacherList(String accountId, String id) {
		List<ClassTeacher> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM ClassTeacher WHERE accountId =? AND (teacherId = ? OR streamId =?) ;");   
				) {

			pstmt.setString(1, accountId);
			pstmt.setString(2, id);
			pstmt.setString(3, id);
			try(ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, ClassTeacher.class);
			}

		} catch(SQLException e){
			logger.error("SQL Exception when getting all ClassTeacher for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	
	

}
