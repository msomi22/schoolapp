/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.staff.ClassTeacher;
import com.yahoo.petermwenda83.persistence.GenericDAO;

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
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolClassTeacherDAO#getClassTeacher(java.lang.String)
	 */
	
	public ClassTeacher getClassTeacherByteacherId(String TeacherUuid) {
		ClassTeacher classTeacher =null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassTeacher WHERE TeacherUuid = ?;");       

				){
			pstmt.setString(1, TeacherUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				classTeacher  = beanProcessor.toBean(rset,ClassTeacher.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting classTeacher with TeacherUuid: " + TeacherUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return classTeacher; 
	}
    
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolClassTeacherDAO#getClassTeacherByclassId(java.lang.String)
	 */
	@Override
	public ClassTeacher getClassTeacherByclassId(String ClassRoomUuid) {
		ClassTeacher classTeacher =null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassTeacher WHERE ClassRoomUuid = ?;");       

				){
			pstmt.setString(1, ClassRoomUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				classTeacher  = beanProcessor.toBean(rset,ClassTeacher.class);
			}



		}catch(SQLException e){
			logger.error("SQL Exception when getting classTeacher with ClassRoomUuid: " + ClassRoomUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return classTeacher; 
	}
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolClassTeacherDAO#putClassTeacher(com.yahoo.petermwenda83.bean.staff.ClassTeacher)
	 */
	public boolean putClassTeacher(ClassTeacher Teacher) {
		boolean success = true; 

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ClassTeacher" 
						+"(Uuid,TeacherUuid,ClassRoomUuid) VALUES (?,?,?);");
				){
			pstmt.setString(1, Teacher.getUuid());
			pstmt.setString(2, Teacher.getTeacherUuid());
			pstmt.setString(3, Teacher.getClassRoomUuid());	                      
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put ClassTeacher: "+Teacher);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}	

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolClassTeacherDAO#updateClassTeacher(com.yahoo.petermwenda83.bean.staff.ClassTeacher)
	 */
	public boolean updateClassTeacher(ClassTeacher Teacher) {
		boolean success = true;		
		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE ClassTeacher SET ClassRoomUuid=? WHERE TeacherUuid = ?;");
				) {           			 	            
			pstmt.setString(1, Teacher.getClassRoomUuid());
			pstmt.setString(2, Teacher.getTeacherUuid());	           
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating ClassTeacher " + Teacher);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolClassTeacherDAO#deleteClassTeacher(com.yahoo.petermwenda83.bean.staff.ClassTeacher)
	 */
	@Override
	public boolean deleteClassTeacher(ClassTeacher Teacher) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ClassTeacher"
						+ " WHERE ClassRoomUuid =? AND TeacherUuid=? ;");       

				){

			pstmt.setString(1, Teacher.getClassRoomUuid());
			pstmt.setString(2, Teacher.getTeacherUuid());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting Teacher : " +Teacher);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolClassTeacherDAO#getClassTeacherList()
	 */
	public List<ClassTeacher> getClassTeacherList() {
		List<ClassTeacher> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM ClassTeacher;");   
				) {

			try(ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, ClassTeacher.class);
			}


		} catch(SQLException e){
			logger.error("SQL Exception when getting all ClassTeacher ");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return list;
	}
}
