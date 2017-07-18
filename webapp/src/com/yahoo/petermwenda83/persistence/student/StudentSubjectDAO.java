

package com.yahoo.petermwenda83.persistence.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.student.StudentSubject;
import com.yahoo.petermwenda83.persistence.GenericDAO;
/** 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentSubjectDAO extends GenericDAO implements SchoolStudentSubjectDAO {
	
	private static StudentSubjectDAO studentSubjectDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static StudentSubjectDAO getInstance(){
		
		if(studentSubjectDAO == null){
			studentSubjectDAO = new StudentSubjectDAO();		
		}
		return studentSubjectDAO;
	}
	
	/**  
	 * 
	 */
	public StudentSubjectDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public StudentSubjectDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
    
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentSubjectDAO#getsubject(com.yahoo.petermwenda83.bean.student.StudentSubject)
	 */
	@Override
	public StudentSubject getsubject(String studentuuid,String SubjectUuid) {
		StudentSubject studentsub = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentSubject WHERE StudentUuid = ? AND SubjectUuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentuuid);
        	 pstmt.setString(2, SubjectUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentsub  = beanProcessor.toBean(rset,StudentSubject.class);
	   }
        		
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Subjects for student " +studentuuid +"with SubjectUuid"+SubjectUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
       
		return studentsub; 
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentSubjectDAO#getstudentSub(java.lang.String)
	 */
	public List<StudentSubject> getstudentSubList(String studentuuid) {
		List<StudentSubject>  subjectlist = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentSubject WHERE "
						+ "studentuuid = ?;");
				) {
			psmt.setString(1, studentuuid);
			try(ResultSet rset = psmt.executeQuery();){
			
				subjectlist = beanProcessor.toBeanList(rset, StudentSubject.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get student subject List for " + studentuuid);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return subjectlist;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentSubjectDAO#putstudentSub(com.yahoo.petermwenda83.bean.student.StudentSubject)
	 */
	@Override
	public boolean putstudentSub(StudentSubject studentSub) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentSubject" 
			        		+"(Uuid, StudentUuid, SubjectUuid,SysUser,AllocationDate) VALUES (?,?,?,?,?);");
		){
			   
	            pstmt.setString(1, studentSub.getUuid());
	            pstmt.setString(2, studentSub.getStudentUuid());
	            pstmt.setString(3, studentSub.getSubjectUuid());
	            pstmt.setString(4, studentSub.getSysUser());
	            pstmt.setTimestamp(5, new Timestamp(studentSub.getAllocationDate().getTime()));
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put StudentSubject: "+studentSub);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		 
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentSubjectDAO#updatestudentSub(com.yahoo.petermwenda83.bean.student.StudentSubject)
	 */
	@Override
	public boolean updatestudentSub(StudentSubject studentSub) {
		boolean success = true;
		
		  try (   Connection conn = dbutils.getConnection();
	               PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentSubject SET SubjectUuid=?,SysUser = ?,"
	               		+ "AllocationDate = ? WHERE StudentUuid = ? AND Uuid = ?;");
	          ) {
	        
	            pstmt.setString(1, studentSub.getSubjectUuid());
	            pstmt.setString(2, studentSub.getSysUser());
	            pstmt.setTimestamp(3, new Timestamp(studentSub.getAllocationDate().getTime()));
	            pstmt.setString(4, studentSub.getStudentUuid());
	            pstmt.setString(5, studentSub.getUuid());
	            pstmt.executeUpdate();

     } catch (SQLException e) {
      logger.error("SQL Exception when updating StudentSubject " + studentSub);
      logger.error(ExceptionUtils.getStackTrace(e));
      System.out.println(ExceptionUtils.getStackTrace(e));
      success = false;
     } 
		
	 return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentSubjectDAO#deletestudentSub(com.yahoo.petermwenda83.bean.student.StudentSubject)
	 */
	@Override
	public boolean deletestudentSub(StudentSubject studentSub) {
		 boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentSubject"
	         	      		+ " WHERE StudentUuid = ? AND SubjectUuid =?;");       
	      		
	      		){
	      	
	      	 pstmt.setString(1, studentSub.getStudentUuid());
	      	 pstmt.setString(2, studentSub.getSubjectUuid()); 
		     pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting studentSubject : " +studentSub);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentSubjectDAO#getstudentSubListBySubID(java.lang.String)
	 */
	@Override
	public List<StudentSubject> getstudentSubListBySubID(String SubjectUuid) {
		List<StudentSubject>  subjectlist = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentSubject WHERE "
						+ "SubjectUuid = ?;");
				) {
			psmt.setString(1, SubjectUuid);
			try(ResultSet rset = psmt.executeQuery();){
			
				subjectlist = beanProcessor.toBeanList(rset, StudentSubject.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get StudentSubject " + SubjectUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return subjectlist;
	}

	
	
}
