
package com.yahoo.petermwenda83.persistence.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.GenericDAO;


/** 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentDAO extends GenericDAO implements SchoolStudentDAO {

	private static StudentDAO studentDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return
	 */
	public static StudentDAO getInstance(){

		if(studentDAO == null){
			studentDAO = new StudentDAO();		
		}
		return studentDAO;
	}

	/**
	 * 
	 */
	public StudentDAO() {
		super();
	}

	/**
	 * 
	 */
	public StudentDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudent(java.lang.String)
	 */

	@Override
	public Student getStudentById(String accountId,String uuid) {
		Student student = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE accountId =? AND uuid = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				student  = beanProcessor.toBean(rset,Student.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting student with uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return student; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudentADmNo(java.lang.String)
	 */
	@Override
	public int getNextregNo(String accountId) {
		Student student = new Student();
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT admNo FROM Student WHERE accountId = ?;");       

				){

			pstmt.setString(1, accountId);
			rset = pstmt.executeQuery();
			while(rset.next()){

				student  = beanProcessor.toBean(rset,Student.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting next regNo for account " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		int admno = NumberUtils.toInt(student.getRegNo());

		return admno + 1; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudents(java.lang.String)
	 */

	@Override
	public Student getStudentByregNo(String accountId,String regNo) {
		Student student = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE accountId =? AND regNo = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, regNo);
			rset = pstmt.executeQuery();
			while(rset.next()){

				student  = beanProcessor.toBean(rset,Student.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting student with regNo: " + regNo);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return student; 
	}



	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudentAdmNo(com.yahoo.petermwenda83.bean.account.Account, java.lang.String)
	 */
	@Override
	public List<Student> searchStudent(String accountId, String query) {
		List<Student> list = null;

		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE accountId = ? AND "
						+ "(regNo ILIKE ? OR firstname ILIKE ? middlename ILIKE ? lastname ILIKE ? OR bcertNo ILIKE ?) ORDER BY "
						+ "regNo ASC LIMIT ? OFFSET ?;;");    		   
				) {
			pstmt.setString(1, accountId);           
			pstmt.setString(2, "%" + query + "%");
			pstmt.setString(3, "%" + query + "%");
			pstmt.setString(4, "%" + query + "%");
			pstmt.setString(5, "%" + query + "%");
			pstmt.setString(6, "%" + query + "%");
			pstmt.setInt(7, 15);
			pstmt.setInt(8, 0);
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, Student.class);

			}
		} catch (SQLException e) {
			logger.error("SQLException when searching for student with accountId " + accountId + " and query '" + query +  "'");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		Collections.sort(list);
		return list;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#putStudents(com.yahoo.petermwenda83.bean.student.Student)
	 */
	@Override
	public boolean putStudent(Student student) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Student (uuid, accountId, regStream, currentStream , "
						+ "isActive, isAlumni, isBoarding, regNo, firstname, middlename, lastname, gender, dob, bcertNo, county, "
						+ "regTerm, finalYear, finalTerm, passport, lastUpdated, admissionDate)"
						+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
				){
		
			pstmt.setString(1, student.getUuid());
			pstmt.setString(2, student.getAccountId());
			pstmt.setString(3, student.getRegStream());
			pstmt.setString(4, student.getCurrentStream());
			pstmt.setString(5, student.getIsActive());
			pstmt.setString(6, student.getIsAlumni());
			pstmt.setString(7, student.getIsBoarding());
			pstmt.setString(8, student.getRegNo());
			pstmt.setString(9, student.getFirstname());
			pstmt.setString(10, student.getMiddlename());
			pstmt.setString(11, student.getLastname());
			pstmt.setString(12, student.getGender());
			pstmt.setString(13, student.getDob());
			pstmt.setString(14, student.getBcertNo());
			pstmt.setString(15, student.getCounty());
			pstmt.setString(16, student.getRegTerm());
			pstmt.setInt(17, student.getFinalYear());
			pstmt.setInt(18, student.getFinalTerm());
			pstmt.setString(19, student.getPassport());
			pstmt.setString(20, student.getLastUpdated());
			pstmt.setTimestamp(21, new Timestamp(student.getAdmissionDate().getTime()));
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put Student  " + student);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}




	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#editStudents(com.yahoo.petermwenda83.bean.student.Student)
	 */
	@Override
	public boolean updateStudent(Student student) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Student SET regStream =?, currentStream =?, isActive =?, isAlumni =?, "
						+ "isBoarding =?, regNo =?, firstname =?, middlename =?, lastname=?, gender =?, dob =?, bcertNo =?, county =?, "
						+ "regTerm =?, finalYear=?, finalTerm=?, passport=?, lastUpdated=? WHERE uuid = ? AND accountId = ?;");
				){
			
			
			pstmt.setString(1, student.getRegStream());
			pstmt.setString(2, student.getCurrentStream());
			pstmt.setString(3, student.getIsActive());
			pstmt.setString(4, student.getIsAlumni());
			pstmt.setString(5, student.getIsBoarding());
			pstmt.setString(6, student.getRegNo());
			pstmt.setString(7, student.getFirstname());
			pstmt.setString(8, student.getMiddlename());
			pstmt.setString(9, student.getLastname());
			pstmt.setString(10, student.getGender());
			pstmt.setString(11, student.getDob());
			pstmt.setString(12, student.getBcertNo());
			pstmt.setString(13, student.getCounty());
			pstmt.setString(14, student.getRegTerm());
			pstmt.setInt(15, student.getFinalYear());
			pstmt.setInt(16, student.getFinalTerm());
			pstmt.setString(17, student.getPassport());
			pstmt.setString(18, student.getLastUpdated());
			pstmt.setString(19, student.getUuid());
			pstmt.setString(20, student.getAccountId());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put Student: "+student);
			logger.error(ExceptionUtils.getStackTrace(e));  
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}



		return success;
	}
	
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#deleteStudent(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteStudent(String accountId,String uuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Student"
						+ " WHERE accountId = ? AND uuid =?; ");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting student uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudentByStream(java.lang.String, java.lang.String)
	 */
	public List<Student> getStudentByStream(String accountId,String currentStream) {
		List<Student> list = null;

		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Student WHERE accountId = ? AND currentStream =? ORDER BY regNo ASC;");   
				) {
			pstmt.setString(1,accountId);
			pstmt.setString(2,currentStream);

			try(ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, Student.class);
			}


		} catch(SQLException e){
			logger.error("SQL Exception when getting all Student");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}


		return list;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getAllStudent(java.lang.String, int, int)
	 */
	public List<Student> getAllStudent(String accountId, int startIndex , int endIndex){
		List<Student> studentList = new ArrayList<>();

		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Student WHERE "
						+ "accountId = ? ORDER BY regNo DESC LIMIT ? OFFSET ? ;");
				) {
			psmt.setString(1, accountId);
			psmt.setInt(2, endIndex - startIndex);
			psmt.setInt(3, startIndex);

			try(ResultSet rset = psmt.executeQuery();){

				studentList = beanProcessor.toBeanList(rset, Student.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Student List  for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return studentList;		
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#activeCount(java.lang.String, java.lang.String)
	 */
	@Override
	public int activeCount(String accountId, String isActive) {
		int count = 0;
		ResultSet rset = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Student WHERE accountId =? AND isActive =?;");    		   
				) {
			pstmt.setString(1, accountId);
			pstmt.setString(2, isActive);
			rset = pstmt.executeQuery();

			while(rset.next()){
				count = rset.getInt("count");
			}
		} catch (SQLException e) {
			logger.error("SQLException while getting active student count for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return count;
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#alumniCount(java.lang.String, java.lang.String)
	 */
	@Override
	public int alumniCount(String accountId, String isAlumni) {
		int count = 0;
		ResultSet rset = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Student WHERE accountId =? AND isAlumni =?;");    		   
				) {
			pstmt.setString(1, accountId);
			pstmt.setString(2, isAlumni);
			rset = pstmt.executeQuery();

			while(rset.next()){
				count = rset.getInt("count");
			}
		} catch (SQLException e) {
			logger.error("SQLException while getting alumni student count for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return count;
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#dayCount(java.lang.String, java.lang.String)
	 */
	@Override
	public int dayCount(String accountId, String isActive, String isBoarding) {
		int count = 0;
		ResultSet rset = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Student WHERE accountId =? AND isActive =? AND isBoarding =?;");    		   
				) {
			pstmt.setString(1, accountId);
			pstmt.setString(2, isActive);
			pstmt.setString(3, isBoarding);
			rset = pstmt.executeQuery();

			while(rset.next()){
				count = rset.getInt("count");
			}
		} catch (SQLException e) {
			logger.error("SQLException while getting day student count for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return count;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#classStudentCount(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int classStudentCount(String accountId, String currentStream, String isActive) {
		int count = 0;
		ResultSet rset = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Student WHERE accountId =? AND currentStream =? AND isActive =?;");    		   
				) {
			pstmt.setString(1, accountId);
			pstmt.setString(2, currentStream);
			pstmt.setString(3, isActive);
			rset = pstmt.executeQuery();

			while(rset.next()){
				count = rset.getInt("count");
			}
		} catch (SQLException e) {
			logger.error("SQLException while getting day student count for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return count;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#genderCount(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int genderCount(String accountId, String isActive, String gender) {
		int count = 0;
		ResultSet rset = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Student WHERE accountId =? AND isActive =? AND gender =?;");    		   
				) {
			pstmt.setString(1, accountId);
			pstmt.setString(2, isActive);
			pstmt.setString(3, gender);
			rset = pstmt.executeQuery();

			while(rset.next()){
				count = rset.getInt("count");
			}
		} catch (SQLException e) {
			logger.error("SQLException while performing student gender count for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return count;
	}




}
