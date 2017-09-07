/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.GenericDAO;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;

/**
 * @author peter
 * 
 */
public class StaffDAO extends GenericDAO implements SchoolStaffDAO {

	private static StaffDAO staffDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return subjectDAO
	 */
	public static StaffDAO getInstance(){
		if(staffDAO == null){
			staffDAO = new StaffDAO();		
		}
		return staffDAO;
	}

	/**
	 * 
	 */
	public StaffDAO() {
		super();
	}

	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public StaffDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaff(java.lang.String, java.lang.String)
	 */
	public Staff getStaff(String accountId, String uuid) {
		Staff StaffDetail = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE accountId = ? AND uuid =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				StaffDetail  = beanProcessor.toBean(rset,Staff.class);
			}  	

		}catch(SQLException e){
			logger.error("SQL Exception when getting Staff with uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return StaffDetail; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffByStaffNo(java.lang.String, java.lang.String)
	 */
	@Override
	public Staff getStaffByStaffNo(String accountId, String staffNo) {
		Staff StaffDetail =  null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE accountId = ? AND staffNo =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, staffNo);
			rset = pstmt.executeQuery();
			while(rset.next()){

				StaffDetail  = beanProcessor.toBean(rset,Staff.class);
			}  	

		}catch(SQLException e){
			logger.error("SQL Exception when getting Staff with staffNo: " + staffNo);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return StaffDetail; 
	}
	
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffByUsername(java.lang.String, java.lang.String)
	 */
	@Override
	public Staff getStaffByUsername(String accountId, String username) {
		Staff StaffDetail =  null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE accountId = ? AND username =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, username);
			rset = pstmt.executeQuery();
			while(rset.next()){

				StaffDetail  = beanProcessor.toBean(rset,Staff.class);
			}  	

		}catch(SQLException e){
			logger.error("SQL Exception when getting Staff with username: " + username);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return StaffDetail; 
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffByKes(java.lang.String, java.lang.String)
	 */
	@Override
	public Staff getStaffByKes(String accountId, String key) {
		Staff staff = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE accountId = ? AND "
						+ "(staffNo =? OR mobile =? OR email =? OR username =?) ;");       

				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, key);
			pstmt.setString(3, key);
			pstmt.setString(4, key);
			pstmt.setString(5, key);
			rset = pstmt.executeQuery();
			while(rset.next()){
				staff  = beanProcessor.toBean(rset,Staff.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Staff with key " + key);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return staff; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffByAccessLevel(java.lang.String, java.lang.String)
	 */
	@Override
	public Staff getStaffByAccessLevel(String accountId, String acessLevelId) {
		Staff StaffDetail =  null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE accountId = ? AND acessLevelId =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, acessLevelId);
			rset = pstmt.executeQuery();
			while(rset.next()){

				StaffDetail  = beanProcessor.toBean(rset,Staff.class);
			}  	

		}catch(SQLException e){
			logger.error("SQL Exception when getting Staff with acessLevelId: " + acessLevelId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return StaffDetail; 
	}





	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#putSStaffDetail(com.yahoo.petermwenda83.bean.staff.Staff)
	 */
	public boolean putStaff(Staff staff) {
		boolean success = true; 

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Staff" 
						+"(uuid, accountId, acessLevelId, staffNo, isActive, firstname, middlename, lastname, gender,"
						+ "mobile, email, username, password, lastupdated, regDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
				){
		
			pstmt.setString(1, staff.getUuid());
			pstmt.setString(2, staff.getAccountId());
			pstmt.setString(3, staff.getAcessLevelId());
			pstmt.setString(4, staff.getStaffNo());
			pstmt.setString(5, staff.getIsActive());
			pstmt.setString(6, staff.getFirstname());
			pstmt.setString(7, staff.getMiddlename());	            
			pstmt.setString(8, staff.getLastname());
			pstmt.setString(9, staff.getGender());
			pstmt.setString(10, staff.getMobile());
			pstmt.setString(11, staff.getEmail());
			pstmt.setString(12, staff.getUsername());
			pstmt.setString(13, SecurityUtil.getMD5Hash(staff.getPassword()));
			pstmt.setString(14, staff.getLastupdated());
			pstmt.setTimestamp(15, new Timestamp(staff.getRegDate().getTime()));
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put staff: " + staff);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}	

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#updateSStaffDetail(com.yahoo.petermwenda83.bean.staff.Staff)
	 */
	public boolean updateStaff(Staff staff) {
		boolean success = true; 
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Staff SET acessLevelId =?, staffNo =?, isActive =?,"
						+ "firstname =?, middlename =? , lastname =?, gender =?, mobile =?, email =?, username =?, password =? "
						+ ", lastupdated = ? WHERE uuid = ? AND accountId =? ;");
				){
		
			pstmt.setString(1, staff.getAcessLevelId());
			pstmt.setString(2, staff.getStaffNo());
			pstmt.setString(3, staff.getIsActive());
			pstmt.setString(4, staff.getFirstname());
			pstmt.setString(5, staff.getMiddlename());	            
			pstmt.setString(6, staff.getLastname());
			pstmt.setString(7, staff.getGender());
			pstmt.setString(8, staff.getMobile());
			pstmt.setString(9, staff.getEmail());
			pstmt.setString(10, staff.getUsername());
			pstmt.setString(11, SecurityUtil.getMD5Hash(staff.getPassword()));  
			pstmt.setString(12, staff.getLastupdated());
			pstmt.setString(13, staff.getUuid());
			pstmt.setString(14, staff.getAccountId());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to update staff " + staff);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#deleteSStaffDetail(com.yahoo.petermwenda83.bean.staff.Staff)
	 */
	@Override
	public boolean deleteStaff(String accountId, String uuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Staff"
						+ " WHERE accountId =? AND uuid=? ;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting Staff with id  " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getSStaffDetailList()
	 */
	public List<Staff> getStaff(String accountId) {
		List<Staff> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE accountId =? ;");   
				) {

			pstmt.setString(1,accountId);
			
			try(ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, Staff.class);
			}

		} catch(SQLException e){
			logger.error("SQL Exception when getting all Staff");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaff(java.lang.String, int, int)
	 */
	@Override
	public List<Staff> getStaff(String accountId, int startIndex, int endIndex) {
		List<Staff> staffList = new ArrayList<>();

		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Staff WHERE "
						+ "accountId = ? LIMIT ? OFFSET ? ;");
				) {
			psmt.setString(1, accountId);
			psmt.setInt(2, endIndex - startIndex);
			psmt.setInt(3, startIndex);

			try(ResultSet rset = psmt.executeQuery();){

				staffList = beanProcessor.toBeanList(rset, Staff.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a staff list  for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return staffList;		
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#getStaffAccessLevel(java.lang.String, java.lang.String)
	 */
	@Override
	public int getStaffAccessLevel(String accountId, String acessLevelId) {
		int count = 0;
		ResultSet rset = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Staff WHERE accountId = ? AND acessLevelId = ?;");    		   
				) {
			pstmt.setString(1, accountId);
			pstmt.setString(2, acessLevelId);
			rset = pstmt.executeQuery();

			while(rset.next()){
				count = rset.getInt("count");
			}
		} catch (SQLException e) {
			logger.error("SQLException while getting Staff count for acessLevelId: " + acessLevelId + " and accountId : " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return count;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolStaffDAO#findDuplicate(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Staff> findDuplicate(String accountId, String key) {
		List<Staff> staffList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE accountId =? AND "
						+ "(staffNo = ? OR mobile =? OR email=? OR username =?);"); 
				) {
			
			pstmt.setString(1, accountId);
			pstmt.setString(2, key);
			pstmt.setString(3, key);
			pstmt.setString(4, key);
			pstmt.setString(5, key);
			
			try(ResultSet rset = pstmt.executeQuery();){

				staffList = beanProcessor.toBeanList(rset, Staff.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying Staff List for key " + key);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return staffList;
	}

	
	

}
