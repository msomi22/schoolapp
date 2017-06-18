
package com.yahoo.petermwenda83.persistence.guardian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ParentsDAO extends GenericDAO  implements SchoolParentsDAO {

	private static ParentsDAO parentsDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static ParentsDAO getInstance(){
		
		if(parentsDAO == null){
			parentsDAO = new ParentsDAO();		
		}
		return parentsDAO;
	}
	
	/** 
	 * 
	 */
	public ParentsDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public ParentsDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#getParent(java.lang.String)
	 */
	@Override
	public StudentParent getParent(String studentUuid) {
		StudentParent studentParent = null;
		ResultSet rset = null;
		
		  try(   Connection conn = dbutils.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM studentParent"
						+ " WHERE studentUuid =?;");
		         ){
			  pstmt.setString(1, studentUuid); 
		      rset = pstmt.executeQuery();
		     while(rset.next()){
		    	 studentParent  = beanProcessor.toBean(rset,StudentParent.class);
		   }
	        	
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to get studentParent with studentuuid: "+studentUuid);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
    
		 }
		return studentParent;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#putParent(com.yahoo.petermwenda83.bean.student.guardian.StudentParent)
	 */
	@Override
	public boolean putParent(StudentParent parent) {
		 boolean success = true;
			
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentParent" 
			        		+"(Uuid, StudentUuid,Fathername,Fatherphone,FatherEmail,FatherID,Fatheroccupation,Mothername,"
			        		+ "Motherphone,MotherEmail,MotherID,Motheroccupation,RelativeName,RelativePhone) VALUES "
			        		+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		             ){
			   
	            pstmt.setString(1, parent.getUuid());
	            pstmt.setString(2, parent.getStudentUuid());	            
	            pstmt.setString(3, parent.getFathername());	       
	            pstmt.setString(4, parent.getFatherphone());
	            pstmt.setString(5, parent.getFatherEmail());	       
	            pstmt.setString(6, parent.getFatherID());
	            pstmt.setString(7, parent.getFatheroccupation());		            
	            pstmt.setString(8, parent.getMothername());
	            pstmt.setString(9, parent.getMotherphone());	       
	            pstmt.setString(10, parent.getMotherEmail());
	            pstmt.setString(11, parent.getMotherID());	       
	            pstmt.setString(12, parent.getMotheroccupation());	     
	            pstmt.setString(13, parent.getRelativeName());	       
	            pstmt.setString(14, parent.getRelativePhone());
	            
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put StudentParent: "+parent);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#updateParent(com.yahoo.petermwenda83.bean.student.guardian.StudentParent)
	 */
	@Override
	public boolean updateParent(StudentParent parent) {
		boolean success = true;
		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentParent SET Fathername = ?,Fatherphone = ?,FatherEmail = ?,"
	             		+ "FatherID =?,Fatheroccupation =?,Mothername =?,Motherphone =?,MotherEmail=?,MotherID =?,Motheroccupation=?,"
	             		+ "RelativeName =?,RelativePhone =? WHERE StudentUuid = ?;");
	               ) {      			 	                       
	            pstmt.setString(1, parent.getFathername());	       
	            pstmt.setString(2, parent.getFatherphone());
	            pstmt.setString(3, parent.getFatherEmail());	       
	            pstmt.setString(4, parent.getFatherID());
	            pstmt.setString(5, parent.getFatheroccupation());		            
	            pstmt.setString(6, parent.getMothername());
	            pstmt.setString(7, parent.getMotherphone());	       
	            pstmt.setString(8, parent.getMotherEmail());
	            pstmt.setString(9, parent.getMotherID());	       
	            pstmt.setString(10, parent.getMotheroccupation());	     
	            pstmt.setString(11, parent.getRelativeName());	       
	            pstmt.setString(12, parent.getRelativePhone());
	            pstmt.setString(13, parent.getStudentUuid());	
	            pstmt.executeUpdate();

    } catch (SQLException e) {
      logger.error("SQL Exception when updating update StudentParent " + parent);
      logger.error(ExceptionUtils.getStackTrace(e));
      System.out.println(ExceptionUtils.getStackTrace(e));
      success = false;
   } 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#deleteParent(com.yahoo.petermwenda83.bean.student.guardian.StudentParent)
	 */
	@Override
	public boolean deleteParent(StudentParent parent) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentParent"
	         	      		+ " WHERE StudentUuid =?;");       
	      		){
	      	
	      	     pstmt.setString(1, parent.getStudentUuid());
		         pstmt.executeUpdate();
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting StudentParent : " +parent);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#getParentList()
	 */
	@Override
	public List<StudentParent> getParentList() {
		List<StudentParent> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentParent;");   
	  		ResultSet rset = pstmt.executeQuery();
			) {
	  	
	      list = beanProcessor.toBeanList(rset, StudentParent.class);

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting Parent List");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
	  return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#getParentListByStudent(java.lang.String)
	 */
	@Override
	public List<StudentParent> getParentListByStudent(String studentUuid) {
		List<StudentParent> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentParent WHERE studentUuid = ?;");   
			) {
			 pstmt.setString(1,studentUuid);

			 try(ResultSet rset = pstmt.executeQuery();){
					
				 list = beanProcessor.toBeanList(rset, StudentParent.class);
			}
	        
	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting Parent List By StudentUuid " +studentUuid);
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }

		
		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolParentsDAO#getParentList(int, int)
	 */
	@Override
	public List<StudentParent> getParentList(int startIndex, int endIndex) {
		List<StudentParent> parentList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentParent LIMIT ? OFFSET ? ;");
				) {
			psmt.setInt(1, endIndex - startIndex);
			psmt.setInt(2, startIndex);
			
			try(ResultSet rset = psmt.executeQuery();){
				parentList = beanProcessor.toBeanList(rset, StudentParent.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a parentList.");
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return parentList;		
	}
	
}
