/**
 * 
 */
package ke.co.qubintel.school.server.persistence.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.student.StudentPrimary;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class PrimaryDAO extends GenericDAO implements SchoolPrimaryDAO {

	private static PrimaryDAO primaryDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static PrimaryDAO getInstance(){
		if(primaryDAO == null){
			primaryDAO = new PrimaryDAO();		
		}
		return primaryDAO;
	}

	/**  
	 * 
	 */
	public PrimaryDAO() {
		super();
	}

	/**
	 * 
	 */
	public PrimaryDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}



	/**
	 * @see ke.co.qubintel.school.server.persistence.student.SchoolPrimaryDAO#getPrimary(java.lang.String)
	 */
	@Override
	public StudentPrimary getStudentPrimary(String accountId,String studentId) {
		StudentPrimary primary = null;
		ResultSet rset = null;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentPrimary"
						+ " WHERE accountId =? AND studentId =?;");
				){
			pstmt.setString(1, accountId); 
			pstmt.setString(2, studentId); 
			rset = pstmt.executeQuery();
			while(rset.next()){
				primary  = beanProcessor.toBean(rset,StudentPrimary.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception trying to get Student Primary Info for studentId " + studentId);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}
		return primary;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.student.SchoolPrimaryDAO#putPrimary(ke.co.qubintel.school.server.bean.student.StudentPrimary)
	 */
	@Override
	public boolean putStudentPrimary(StudentPrimary Primary) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentPrimary" 
						+"(uuid,accountId,studentId,schoolName,index,kcpeYear,kcpeMark,kcpeGrade) VALUES (?,?,?,?,?,?,?,?);");
				){

			pstmt.setString(1, Primary.getUuid());
			pstmt.setString(2, Primary.getAccountId());
			pstmt.setString(3, Primary.getStudentId());
			pstmt.setString(4, Primary.getSchoolName());	       
			pstmt.setString(5, Primary.getIndex());
			pstmt.setString(6, Primary.getKcpeyear());
			pstmt.setString(7, Primary.getKcpemark());
			pstmt.setString(8, Primary.getKcpeGrade());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put StudentPrimary  " + Primary);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.student.SchoolPrimaryDAO#updatePrimary(ke.co.qubintel.school.server.bean.student.StudentPrimary)
	 */
	@Override
	public boolean updateStudentPrimary(StudentPrimary Primary) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentPrimary SET schoolName = ?,index = ?,kcpeYear = ?,"
						+ " kcpeMark =?, kcpeGrade =?  WHERE accountId = ? AND studentId =?;");
				) {           			 	            


			pstmt.setString(1, Primary.getSchoolName());	       
			pstmt.setString(2, Primary.getIndex());
			pstmt.setString(3, Primary.getKcpeyear());
			pstmt.setString(4, Primary.getKcpemark());
			pstmt.setString(5, Primary.getKcpeGrade());
			pstmt.setString(6, Primary.getAccountId());
			pstmt.setString(7, Primary.getStudentId());	           
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating StudentPrimary " + Primary);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}


	/**
	 * @see ke.co.qubintel.school.server.persistence.student.SchoolPrimaryDAO#deletePrimary(ke.co.qubintel.school.server.bean.student.StudentPrimary)
	 */
	@Override
	public boolean deleteStudentPrimary(String accountId,String studentId) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentPrimary"
						+ " WHERE accountId =? AND studentId =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting studentId  " + studentId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}


	/**
	 * @see ke.co.qubintel.school.server.persistence.student.SchoolPrimaryDAO#getStudentPrimary(java.lang.String)
	 */
	@Override
	public List<StudentPrimary> getStudentPrimary(String accountId) {
		List<StudentPrimary> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentPrimary WHERE accountId =?;");   
				) {

			pstmt.setString(1,accountId);
			try(ResultSet rset = pstmt.executeQuery();){
				list = beanProcessor.toBeanList(rset, StudentPrimary.class);
			}

		} catch(SQLException e){
			logger.error("SQL Exception when getting List of Student's Primary Details for accountId " + accountId );
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}
		return list;
	}

}
