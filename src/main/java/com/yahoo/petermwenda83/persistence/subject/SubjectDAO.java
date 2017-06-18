/**
 * Account Management System
 * This software belong to Peter Mwenda's and Miwgi Ndungu's Company
 * copywrite peter&MigwiSoftwares.co.ltd
 */
package com.yahoo.petermwenda83.persistence.subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.GenericDAO;



/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 *
 */
public class SubjectDAO extends GenericDAO implements SchoolSubjectDAO {

	private static SubjectDAO subjectDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	/**
	 * 
	 * @return subjectDAO
	 */
	public static SubjectDAO getInstance(){
		if(subjectDAO == null){
		subjectDAO = new SubjectDAO();		
		}
		return subjectDAO;
	}
	
	/**
	 * 
	 */
	public SubjectDAO() {
		super();
	}

	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public SubjectDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolSubjectDAO#getSubject(com.yahoo.petermwenda83.bean.student.StudentSubject.SubjectUi, com.yahoo.petermwenda83.view.InfoBsic, java.lang.String)
	 */
	@Override
	public Subject getSubject(String uuid) {
		Subject Subject = null;
        ResultSet rset = null;
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Subject WHERE Uuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	Subject  = beanProcessor.toBean(rset,Subject.class);
	    }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting an subject with uuid: " + uuid);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
        
		return Subject; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolSubjectDAO#getSubjects(java.lang.String)
	 */
	@Override
	public Subject getSubjects(String subjectcode) {
		Subject Subject = null;
        ResultSet rset = null;
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Subject WHERE Subjectcode = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, subjectcode);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	Subject  = beanProcessor.toBean(rset,Subject.class);
	     }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting a subject with subjectcode: " + subjectcode);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
        
		return Subject; 
	}

	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolSubjectDAO#putSubject(com.yahoo.petermwenda83.bean.student.Subject)
	 */
	@Override
	public boolean putSubject(Subject subject) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Subject" 
			        		+"(Uuid,SubjectCode,SubjectName,SubjectCategory) VALUES (?,?,?,?);");
        		){
			   
	            pstmt.setString(1, subject.getUuid());
	            pstmt.setString(2, subject.getSubjectCode());
	            pstmt.setString(3, subject.getSubjectName());
	            pstmt.setString(4, subject.getSubjectCategory());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put Subject: "+subject);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             success = false;
		 }
		
		
		return success;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolSubjectDAO#editSubject(com.yahoo.petermwenda83.bean.student.Subject, java.lang.String)
	 */
	@Override
	public boolean editSubject(Subject subject,String uuid) {
		boolean success = true;
        try (  Connection conn = dbutils.getConnection();
        	   PreparedStatement pstmt = conn.prepareStatement("UPDATE Subject SET SubjectCode=?,"
        			+ "SubjectName=?, SubjectCategory=? WHERE Uuid = ?;");
        	) { 
	            pstmt.setString(1, subject.getSubjectCode());
	            pstmt.setString(2, subject.getSubjectName());
	            pstmt.setString(3, subject.getSubjectCategory());
	            pstmt.setString(4, subject.getUuid());
                pstmt.executeUpdate(); 

        } catch (SQLException e) {
            logger.error("SQL Exception when updating Subject with uuid " + subject);
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
        } 
        
        return success;
		
	}

	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolSubjectDAO#deleteStudent(com.yahoo.petermwenda83.bean.student.Subject)
	 */
	@Override
	public boolean deleteStudent(Subject subject) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Subject WHERE Uuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, subject.getUuid());
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting: " + subject);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
             
        }
        
		return success; 
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolSubjectDAO#getAllStudent()
	 */
	@Override
	public List<Subject> getAllSubjects() {
		List<Subject>  list = null;		
		 try(   
       		Connection conn = dbutils.getConnection();
       		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Subject;");          		
   		) {			     
			 try( ResultSet rset = pstmt.executeQuery();){
				    list = beanProcessor.toBeanList(rset, Subject.class);
	         	   }
			
          

       } catch(SQLException e){
       	logger.error("SQL Exception when getting all Subject");
           logger.error(ExceptionUtils.getStackTrace(e));
       }
     
		
		return list;
	}

	
}
