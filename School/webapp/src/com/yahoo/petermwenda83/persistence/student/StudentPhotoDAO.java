/**
 * 
 */
package com.yahoo.petermwenda83.persistence.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.student.StudentPhoto;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class StudentPhotoDAO extends GenericDAO implements SchoolStudentPhotoDAO {

	private static StudentPhotoDAO studentPhotoDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static StudentPhotoDAO getInstance(){

		if(studentPhotoDAO == null){
			studentPhotoDAO = new StudentPhotoDAO();		
		}
		return studentPhotoDAO;
	}

	/**  
	 * 
	 */
	public StudentPhotoDAO() {
		super();
	}

	/**
	 * 
	 */
	public StudentPhotoDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentPhotoDAO#getPhotoByStudentid(java.lang.String)
	 */
	@Override
	public StudentPhoto getPhotoByStudentid(String StudentUuid) {
		StudentPhoto photo = null;
		ResultSet rset = null;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentPhoto WHERE StudentUuid =?;");
				){
			pstmt.setString(1, StudentUuid); 
			rset = pstmt.executeQuery();
			while(rset.next()){
				photo  = beanProcessor.toBean(rset,StudentPhoto.class);
			}


		}catch(SQLException e){
			logger.error("SQL Exception trying to put StudentPhoto with StudentUuid : " + StudentUuid);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}
		return photo;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentPhotoDAO#getPhotoByPhotopath(java.lang.String)
	 */
	@Override
	public StudentPhoto getPhotoByPhotopath(String imagePath) {
		StudentPhoto photo = null;
		ResultSet rset = null;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentPhoto WHERE imagePath =?;");
				){
			pstmt.setString(1, imagePath); 
			rset = pstmt.executeQuery();
			while(rset.next()){
				photo  = beanProcessor.toBean(rset,StudentPhoto.class);
			}


		}catch(SQLException e){
			logger.error("SQL Exception trying to put StudentPhoto with imagePath : " + imagePath);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}
		return photo;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentPhotoDAO#putPhoto(com.yahoo.petermwenda83.bean.student.StudentPhoto)
	 */
	@Override
	public boolean putPhoto(StudentPhoto photo) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentPhoto" 
						+"(Uuid, StudentUuid, ImagePath) VALUES (?,?,?);");
				){

			pstmt.setString(1, photo.getUuid());
			pstmt.setString(2, photo.getStudentUuid());
			pstmt.setString(3, photo.getImagePath());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put StudentPhoto: " + photo);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentPhotoDAO#updatePhoto(com.yahoo.petermwenda83.bean.student.StudentPhoto)
	 */
	@Override
	public boolean updatePhoto(StudentPhoto photo) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentPhoto SET ImagePath = ? WHERE StudentUuid =?;");
				) {           			 	            

			pstmt.setString(1, photo.getImagePath());  
			pstmt.setString(2, photo.getStudentUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating update StudentPhoto " + photo);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

}
