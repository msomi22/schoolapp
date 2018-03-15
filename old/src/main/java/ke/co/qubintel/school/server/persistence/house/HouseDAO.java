/**
 * 
 */
package ke.co.qubintel.school.server.persistence.house;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.house.House;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class HouseDAO extends GenericDAO implements SchoolHouseDAO {

	private static HouseDAO houseDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return
	 */
	public static HouseDAO getInstance(){

		if(houseDAO == null){
			houseDAO = new HouseDAO();		
		}
		return houseDAO;
	}

	/**
	 * 
	 */
	public HouseDAO() {
		super();
	}

	/**
	 * 
	 */
	public HouseDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolHouseDAO#getHouseById(java.lang.String, java.lang.String)
	 */
	@Override
	public House getHouseById(String accountId, String uuid) {
		House house = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM House WHERE accountId = ? AND uuid = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				house  = beanProcessor.toBean(rset, House.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting House for accountId " + accountId + " and id " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return house; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolHouseDAO#getHouse(java.lang.String, java.lang.String)
	 */
	@Override
	public House getHouse(String accountId, String houseName) {
		House house = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM House WHERE accountId = ? AND houseName = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, houseName);
			rset = pstmt.executeQuery();
			while(rset.next()){
				house  = beanProcessor.toBean(rset, House.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting House for accountId " + accountId + " and houseName " + houseName);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return house; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolHouseDAO#getHouseList(java.lang.String)
	 */
	@Override
	public List<House> getHouseList(String accountId) {
		List<House>  list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM House WHERE accountId =?;");   

				) {

			pstmt.setString(1, accountId);           
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, House.class);
			}


		} catch(SQLException e){
			logger.error("SQL Exception when getting all House for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolHouseDAO#putHouse(ke.co.qubintel.school.server.bean.house.House)
	 */
	@Override
	public boolean putHouse(House house) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO House" 
						+"(uuid,accountId,houseName,description) VALUES (?,?,?,?);");
				){

			pstmt.setString(1, house.getUuid());
			pstmt.setString(2, house.getAccountId());
			pstmt.setString(3, house.getHouseName());
			pstmt.setString(4, house.getDescription());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put House " + house);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolHouseDAO#updateHouse(ke.co.qubintel.school.server.bean.house.House)
	 */
	@Override
	public boolean updateHouse(House house) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE House SET houseName = ?, description =?"
						+ "WHERE uuid = ? AND accountId = ?;");
				) {           			 	            
			pstmt.setString(1, house.getHouseName());
			pstmt.setString(2, house.getDescription());
			pstmt.setString(3, house.getUuid());
			pstmt.setString(4, house.getAccountId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating House " + house);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.house.SchoolHouseDAO#deleteHouse(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteHouse(String accountId, String uuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM House"
						+ " WHERE accountId =? AND uuid =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting from House with  accountId  " + accountId + " and uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

}
