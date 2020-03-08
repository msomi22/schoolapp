

package ke.co.qubintel.school.server.persistence.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.student.StudentSubject;
import ke.co.qubintel.school.server.persistence.GenericDAO;
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
	 * @see ke.co.qubintel.school.server.persistence.student.SchoolStudentSubjectDAO#getSubjectById(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentSubject getSubjectById(String accountId, String uuid) {
		StudentSubject studentsub = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentSubject WHERE accountId = ? AND uuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, accountId);
        	 pstmt.setString(2, uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentsub  = beanProcessor.toBean(rset,StudentSubject.class);
	   }
        		
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Subjects for accountId " + accountId + " and uuid " + uuid);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
       
		return studentsub; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.student.SchoolStudentSubjectDAO#StudentSubject(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentSubject getstudentSubject(String studentId,String subjectId) {
		StudentSubject studentsub = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentSubject WHERE studentId = ? AND subjectId = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentId);
        	 pstmt.setString(2, subjectId);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentsub  = beanProcessor.toBean(rset,StudentSubject.class);
	   }
        		
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Subjects for studentId " + studentId + " and subjectId " + subjectId);
             logger.error(ExceptionUtils.getStackTrace(e));
        }
       
		return studentsub; 
	}

	
	/**
	 * @see ke.co.qubintel.school.server.persistence.student.SchoolStudentSubjectDAO#getStudentSubjects(java.lang.String)
	 */
	public List<StudentSubject> getStudentSubjects(String studentId) {
		List<StudentSubject>  subjectlist = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentSubject WHERE "
						+ "studentId = ?;");
				) {
			psmt.setString(1, studentId);
			try(ResultSet rset = psmt.executeQuery();){
			
				subjectlist = beanProcessor.toBeanList(rset, StudentSubject.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get student subject List for studentId " + studentId);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return subjectlist;
	}

	
	/**
	 * @see ke.co.qubintel.school.server.persistence.student.SchoolStudentSubjectDAO#putStudentSubject(ke.co.qubintel.school.server.bean.student.StudentSubject)
	 */
	@Override
	public boolean putStudentSubject(StudentSubject studentSub) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentSubject" 
			        		+"(uuid, accountId ,studentId, subjectId, allocationDate) VALUES (?,?,?,?,?);");
		){
			   
	            pstmt.setString(1, studentSub.getUuid());
	            pstmt.setString(2, studentSub.getAccountId());
	            pstmt.setString(3, studentSub.getStudentId());
	            pstmt.setString(4, studentSub.getSubjectId());
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
	 * @see ke.co.qubintel.school.server.persistence.student.SchoolStudentSubjectDAO#deleteStudentSubject(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteAllSubject(String accountId,String studentId) {
		 boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentSubject"
	         	      		+ " WHERE accountId = ? AND studentId =?;");       
	      		
	      		){
	      	
	      	 pstmt.setString(1, accountId);
	      	 pstmt.setString(2, studentId); 
		     pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting studentSubject for studentId " + studentId);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	
	/**
	 * @see ke.co.qubintel.school.server.persistence.student.SchoolStudentSubjectDAO#deleteStudentSubject(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteSubject(String accountId, String id) {
		 boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentSubject"
	         	      		+ " WHERE accountId = ? AND uuid =?;");       
	      		
	      		){
	      	
	      	 pstmt.setString(1, accountId);
	      	 pstmt.setString(2, id); 
		     pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting studentSubject for id " + id);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

}
