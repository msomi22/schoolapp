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

import com.yahoo.petermwenda83.bean.staff.TeacherSubject;
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
	public TeacherSubject getSubjectClass(String teacherUuid) {
		TeacherSubject teachersub = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubject WHERE teacherUuid = ?;");       
     		
     		){
     	
     	     pstmt.setString(1, teacherUuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	teachersub  = beanProcessor.toBean(rset,TeacherSubject.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with TeacherSubject: " + teacherUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return teachersub; 
	}
	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#getSubject(java.lang.String, java.lang.String)
	 */
	@Override
	public TeacherSubject getSubject(String SubjectUuid, String ClassRoomUuid) {
		TeacherSubject teachersub = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubject WHERE SubjectUuid = ? AND ClassRoomUuid = ?;");       
     		
     		){
     	
     	     pstmt.setString(1, SubjectUuid);
     	     pstmt.setString(2, ClassRoomUuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	teachersub  = beanProcessor.toBean(rset,TeacherSubject.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with teacher with SubjectUuid: " + SubjectUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return teachersub; 
	}

	
	
	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#getSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubject)
	 */
	@Override
	public TeacherSubject getSubjectClass(TeacherSubject subClass) {
		TeacherSubject teachersub = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubject WHERE teacherUuid = ?"
        	      		+ "AND SubjectUuid =? AND ClassRoomUuid =? ;");       
     		
     		){
     	
     	     pstmt.setString(1, subClass.getTeacherUuid());
     	     pstmt.setString(2, subClass.getSubjectUuid());
     	     pstmt.setString(3, subClass.getClassRoomUuid());
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	teachersub  = beanProcessor.toBean(rset,TeacherSubject.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting  TeacherSubject: " + subClass);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return teachersub; 
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#getSubjectsANDClassesList(java.lang.String)
	 */
	public List<TeacherSubject> getSubjectsANDClassesList(String teacherUuid) {
		List<TeacherSubject> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubject WHERE teacherUuid = ?;");    		   
     	   ) {
         	   pstmt.setString(1, teacherUuid);      
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, TeacherSubject.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting TeacherSubject List with teacherUuid" +teacherUuid); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#putSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubject)
	 */
	public boolean putSubjectClass(TeacherSubject subClass) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TeacherSubject" 
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
			logger.error("SQL Exception trying to put TeacherSubject: "+subClass);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }	
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#updateSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubject)
	 */
	public boolean updateSubjectClass(TeacherSubject subClass) {
		boolean success = true;		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE TeacherSubject SET SubjectUuid=?, ClassRoomUuid =?,"
	             		+ " SysUser =?, AllocationDate = ? WHERE TeacherUuid = ?;");
	) {           			 	            
	            pstmt.setString(1, subClass.getSubjectUuid());
	            pstmt.setString(2, subClass.getClassRoomUuid());
	            pstmt.setString(3, subClass.getSysUser());
	            pstmt.setTimestamp(4, new Timestamp(subClass.getAllocationDate().getTime()));
	            pstmt.setString(5, subClass.getTeacherUuid());
	            pstmt.executeUpdate();

} catch (SQLException e) {
    logger.error("SQL Exception when updating TeacherSubject " + subClass);
    logger.error(ExceptionUtils.getStackTrace(e));
    System.out.println(ExceptionUtils.getStackTrace(e));
    success = false;
} 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#deleteSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubject)
	 */
	@Override
	public boolean deleteSubjectClass(TeacherSubject subClass) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM TeacherSubject WHERE TeacherUuid = ? AND SubjectUuid =? AND ClassRoomUuid =? ;");       
        		
        		){
        	
        	 pstmt.setString(1, subClass.getTeacherUuid());
        	 pstmt.setString(2, subClass.getSubjectUuid());
        	 pstmt.setString(3, subClass.getClassRoomUuid());
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting TeacherSubject " + subClass);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
             
        }
        
		return success; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.staff.SchoolTeacherSubClassDAO#getSubjectClassList()
	 */
	public List<TeacherSubject> getSubjectClassList() {
		List<TeacherSubject>  list = null;
		
		 try(   
       		Connection conn = dbutils.getConnection();
       		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM TeacherSubject;");   
       		ResultSet rset = pstmt.executeQuery();
   		) {
       	
           list = beanProcessor.toBeanList(rset, TeacherSubject.class);

       } catch(SQLException e){
       	logger.error("SQL Exception when getting all TeacherSubject");
           logger.error(ExceptionUtils.getStackTrace(e));
       }
     
		
		return list;
	}

	

	
}
