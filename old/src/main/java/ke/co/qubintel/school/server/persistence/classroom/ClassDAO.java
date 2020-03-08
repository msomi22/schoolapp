/**
 * 
 */
package ke.co.qubintel.school.server.persistence.classroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.classroom.ClassRoom;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class ClassDAO extends GenericDAO implements SchoolClassDAO {

	private static ClassDAO classDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static ClassDAO getInstance(){

		if(classDAO == null){
			classDAO = new ClassDAO();		
		}
		return classDAO;
	}

	/**
	 * 
	 */
	public ClassDAO() { 
		super();
	}

	/**
	 * 
	 */
	public ClassDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	/**
	 * @see ke.co.qubintel.school.server.persistence.classroom.SchoolClassDAO#getClassRoomById(java.lang.String, java.lang.String)
	 */
	@Override
	public ClassRoom getClassRoom(String accountId, String uuid) {
		ClassRoom classRoom = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM classRoom WHERE accountId = ? AND uuid = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				classRoom  = beanProcessor.toBean(rset,ClassRoom.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting ClassRoom for accountId " + accountId + " and id " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return classRoom; 
	}

	
	/**
	 * @see ke.co.qubintel.school.server.persistence.classroom.SchoolClassDAO#getClassRoom(java.lang.String, java.lang.String)
	 */
	public ClassRoom getClassRoomByDesc(String accountId, String description) {
		ClassRoom classRoom = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM classRoom WHERE accountId = ? AND description = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, description);
			rset = pstmt.executeQuery();
			while(rset.next()){

				classRoom  = beanProcessor.toBean(rset,ClassRoom.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting ClassRoom for accountId " + accountId + " and description " + description);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return classRoom; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.classroom.SchoolClassDAO#putClass(ke.co.qubintel.school.server.bean.classroom.Stream)
	 */
	@Override
	public boolean putClassRoom(ClassRoom classRoom) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ClassRoom" 
						+"(uuid,accountId,description,examSubNumber) VALUES (?,?,?,?);");
				){

			pstmt.setString(1, classRoom.getUuid());
			pstmt.setString(2, classRoom.getAccountId());
			pstmt.setString(3, classRoom.getDescription());
			pstmt.setInt(4, classRoom.getExamSubNumber());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put ClassRoom " + classRoom);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.classroom.SchoolClassDAO#updateClass(ke.co.qubintel.school.server.bean.classroom.Stream)
	 */
	@Override
	public boolean updateClassRoom(ClassRoom classRoom) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE ClassRoom SET description = ?, examSubNumber =?"
						+ "WHERE uuid = ? AND accountId = ?;");
				) {           			 	            
			pstmt.setString(1, classRoom.getDescription());
			pstmt.setInt(2, classRoom.getExamSubNumber());
			pstmt.setString(3, classRoom.getUuid());
			pstmt.setString(4, classRoom.getAccountId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating classRoom " + classRoom);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}
	/**
	 * @see ke.co.qubintel.school.server.persistence.classroom.SchoolClassDAO#getClassList()
	 */
	@Override
	public List<ClassRoom> getClassRooms(String accountId) {
		List<ClassRoom>  list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM ClassRoom WHERE accountId =?;");   

				) {

			pstmt.setString(1, accountId);           
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, ClassRoom.class);
			}


		} catch(SQLException e){
			logger.error("SQL Exception when getting all ClassRooms for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

	

}
