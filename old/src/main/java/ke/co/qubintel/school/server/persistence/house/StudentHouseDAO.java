package ke.co.qubintel.school.server.persistence.house;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.house.StudentHouse;
import ke.co.qubintel.school.server.persistence.GenericDAO;

public class StudentHouseDAO extends GenericDAO implements SchoolStudentHouseDAO {

	private static StudentHouseDAO studentHouseDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return
	 */
	public static StudentHouseDAO getInstance(){

		if(studentHouseDAO == null){
			studentHouseDAO = new StudentHouseDAO();		
		}
		return studentHouseDAO;
	}

	/**
	 * 
	 */
	public StudentHouseDAO() {
		super();
	}

	/**
	 * 
	 */
	public StudentHouseDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolStudentHouseDAO#getStudentHouseById(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentHouse getStudentHouseById(String accountId, String uuid) {
		StudentHouse studentHouse = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentHouse WHERE accountId = ? AND uuid = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				studentHouse  = beanProcessor.toBean(rset, StudentHouse.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting StudentHouse for accountId " + accountId + " and id " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return studentHouse; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolStudentHouseDAO#getStudentHouse(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentHouse getStudentHouse(String accountId, String studentId) {
		StudentHouse studentHouse = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentHouse WHERE accountId = ? AND studentId = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, studentId);
			rset = pstmt.executeQuery();
			while(rset.next()){
				studentHouse  = beanProcessor.toBean(rset, StudentHouse.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting StudentHouse for accountId " + accountId + " and studentId " + studentId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return studentHouse; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolStudentHouseDAO#getStudentHouseList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<StudentHouse> getStudentHouseList(String accountId, String houseId) {
		List<StudentHouse>  list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentHouse WHERE accountId =? AND houseId =?;");   

				) {

			pstmt.setString(1, accountId); 
			pstmt.setString(2, houseId); 
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, StudentHouse.class);
			}


		} catch(SQLException e){
			logger.error("SQL Exception when getting all StudentHouse for accountId " + accountId + " and houseId " + houseId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolStudentHouseDAO#putStudentHouse(ke.co.qubintel.school.server.bean.house.StudentHouse)
	 */
	@Override
	public boolean putStudentHouse(StudentHouse studentHouse) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentHouse" 
						+"(uuid,accountId,studentId,houseId,dateOut,dateIn) VALUES (?,?,?,?,?,?);");
				){

			pstmt.setString(1, studentHouse.getUuid());
			pstmt.setString(2, studentHouse.getAccountId());
			pstmt.setString(3, studentHouse.getStudentId());
			pstmt.setString(4, studentHouse.getHouseId());
			pstmt.setTimestamp(5, studentHouse.getDateOut());
			pstmt.setTimestamp(6, studentHouse.getDateIn());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put StudentHouse " + studentHouse);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}
	
	
	
	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolStudentHouseDAO#exitHouse(ke.co.qubintel.school.server.bean.house.StudentHouse)
	 */
	@Override
	public boolean exitHouse(StudentHouse studentHouse) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentHouse SET dateOut = ? "
						+ "WHERE uuid =? AND accountId =?;");
				) {           			 	            
			pstmt.setTimestamp(1, studentHouse.getDateOut());
			pstmt.setString(2, studentHouse.getUuid());
			pstmt.setString(3, studentHouse.getAccountId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating StudentHouse " + studentHouse);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolStudentHouseDAO#changeHouse(ke.co.qubintel.school.server.bean.house.StudentHouse)
	 */
	@Override
	public boolean changeHouse(StudentHouse studentHouse) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentHouse SET houseId =? "
						+ "WHERE uuid =? AND accountId =?;");
				) {           			 	            
			pstmt.setString(1, studentHouse.getHouseId());
			pstmt.setString(2, studentHouse.getUuid());
			pstmt.setString(3, studentHouse.getAccountId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating StudentHouse " + studentHouse);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}


	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolStudentHouseDAO#deleteStudentHouse(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteStudentHouse(String accountId, String uuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentHouse"
						+ " WHERE accountId =? AND uuid =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting from StudentHouse with  accountId  " + accountId + " and uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	
}
