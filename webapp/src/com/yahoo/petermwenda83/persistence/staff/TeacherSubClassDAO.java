/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.staff.TeacherSubClass;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 * 
 *
 */
public class TeacherSubClassDAO extends GenericDAO  implements SchoolTeacherSubClassDAO {

	private static TeacherSubClassDAO teacherSubClassDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	/**
	 * 
	 * @return subjectDAO
	 */
	public static TeacherSubClassDAO getInstance(){
		if(teacherSubClassDAO == null){
			teacherSubClassDAO = new TeacherSubClassDAO();		
		}
		return teacherSubClassDAO;
	}
	
	/**
	 * 
	 */
	public TeacherSubClassDAO() {
		super();
	}

	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public TeacherSubClassDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#getSubjectClass(java.lang.String)
	 */
	public TeacherSubClass getSubjectClass(String teacherUuid) {
		TeacherSubClass teachersub = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubClass WHERE teacherUuid = ?;");       
     		
     		){
     	
     	     pstmt.setString(1, teacherUuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	teachersub  = beanProcessor.toBean(rset,TeacherSubClass.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with TeacherSubClass: " + teacherUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return teachersub; 
	}
	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#getSubject(java.lang.String, java.lang.String)
	 */
	@Override
	public TeacherSubClass getSubject(String SubjectUuid, String ClassRoomUuid) {
		TeacherSubClass teachersub = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubClass WHERE SubjectUuid = ? AND ClassRoomUuid = ?;");       
     		
     		){
     	
     	     pstmt.setString(1, SubjectUuid);
     	     pstmt.setString(2, ClassRoomUuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	teachersub  = beanProcessor.toBean(rset,TeacherSubClass.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with teacher with SubjectUuid: " + SubjectUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return teachersub; 
	}

	
	
	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#getSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubClass)
	 */
	@Override
	public TeacherSubClass getSubjectClass(TeacherSubClass subClass) {
		TeacherSubClass teachersub = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubClass WHERE teacherUuid = ?"
        	      		+ "AND SubjectUuid =? AND ClassRoomUuid =? ;");       
     		
     		){
     	
     	     pstmt.setString(1, subClass.getTeacherUuid());
     	     pstmt.setString(2, subClass.getSubjectUuid());
     	     pstmt.setString(3, subClass.getClassRoomUuid());
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	teachersub  = beanProcessor.toBean(rset,TeacherSubClass.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting  TeacherSubClass: " + subClass);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return teachersub; 
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#getSubjectsANDClassesList(java.lang.String)
	 */
	public List<TeacherSubClass> getSubjectsANDClassesList(String teacherUuid) {
		List<TeacherSubClass> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubClass WHERE teacherUuid = ?;");    		   
     	   ) {
         	   pstmt.setString(1, teacherUuid);      
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, TeacherSubClass.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting TeacherSubClass List with teacherUuid" +teacherUuid); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#putSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubClass)
	 */
	public boolean putSubjectClass(TeacherSubClass subClass) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TeacherSubClass" 
			        		+"(Uuid,TeacherUuid,SubjectUuid,ClassRoomUuid,SysUser,AllocationDate) VALUES (?,?,?,?,?,?);");
    		){
	            pstmt.setString(1, subClass.getUuid());
	            pstmt.setString(2, subClass.getTeacherUuid());
	            pstmt.setString(3, subClass.getSubjectUuid());
	            pstmt.setString(4, subClass.getClassRoomUuid());
	            pstmt.setString(5, subClass.getSysUser());
	            pstmt.setTimestamp(6, new Timestamp(subClass.getAllocationDate().getTime()));	           
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put TeacherSubClass: "+subClass);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }	
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#updateSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubClass)
	 */
	public boolean updateSubjectClass(TeacherSubClass subClass) {
		boolean success = true;		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE TeacherSubClass SET SubjectUuid=?, ClassRoomUuid =?,"
	             		+ " SysUser =?, AllocationDate = ? WHERE TeacherUuid = ?;");
	) {           			 	            
	            pstmt.setString(1, subClass.getSubjectUuid());
	            pstmt.setString(2, subClass.getClassRoomUuid());
	            pstmt.setString(3, subClass.getSysUser());
	            pstmt.setTimestamp(4, new Timestamp(subClass.getAllocationDate().getTime()));
	            pstmt.setString(5, subClass.getTeacherUuid());
	            pstmt.executeUpdate();

} catch (SQLException e) {
    logger.error("SQL Exception when updating TeacherSubClass " + subClass);
    logger.error(ExceptionUtils.getStackTrace(e));
    System.out.println(ExceptionUtils.getStackTrace(e));
    success = false;
} 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#deleteSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubClass)
	 */
	@Override
	public boolean deleteSubjectClass(TeacherSubClass subClass) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM TeacherSubClass WHERE TeacherUuid = ? AND SubjectUuid =? AND ClassRoomUuid =? ;");       
        		
        		){
        	
        	 pstmt.setString(1, subClass.getTeacherUuid());
        	 pstmt.setString(2, subClass.getSubjectUuid());
        	 pstmt.setString(3, subClass.getClassRoomUuid());
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting TeacherSubClass " + subClass);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
             
        }
        
		return success; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#getSubjectClassList()
	 */
	public List<TeacherSubClass> getSubjectClassList() {
		List<TeacherSubClass>  list = null;
		
		 try(   
       		Connection conn = dbutils.getConnection();
       		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM TeacherSubClass;");   
       		ResultSet rset = pstmt.executeQuery();
   		) {
       	
           list = beanProcessor.toBeanList(rset, TeacherSubClass.class);

       } catch(SQLException e){
       	logger.error("SQL Exception when getting all TeacherSubClass");
           logger.error(ExceptionUtils.getStackTrace(e));
       }
     
		
		return list;
	}

	

	
}
