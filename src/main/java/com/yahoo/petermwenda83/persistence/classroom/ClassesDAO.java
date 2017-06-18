/**
 * 
 */
package com.yahoo.petermwenda83.persistence.classroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.classroom.Stream;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class ClassesDAO extends GenericDAO implements SchoolClassesDAO {
	
	private static ClassesDAO classesDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static ClassesDAO getInstance(){
		
		if(classesDAO == null){
			classesDAO = new ClassesDAO();		
		}
		return classesDAO;
	}
	
	/**
	 * 
	 */
	public ClassesDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public ClassesDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolClassesDAO#getClass(java.lang.String)
	 */
	public Stream getClass(String Uuid) {
		Stream Stream = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Stream WHERE Uuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, Uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 Stream  = beanProcessor.toBean(rset,Stream.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Stream with Uuid: " + Uuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return Stream; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolClassesDAO#putClass(com.yahoo.petermwenda83.bean.classroom.Stream)
	 */
	@Override
	public boolean putClass(Stream Class) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Stream" 
			        		+"(Uuid, className) VALUES (?,?);");
		             ){
			   
	            pstmt.setString(1, Class.getUuid());
	            pstmt.setString(2, Class.getClassName());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put Stream "+Class);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolClassesDAO#updateClass(com.yahoo.petermwenda83.bean.classroom.Stream)
	 */
	@Override
	public boolean updateClass(Stream Class) {
		boolean success = true;
		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE Stream SET ClassName = ?"
			        + "WHERE Uuid = ?;");
	               ) {           			 	            
	            pstmt.setString(1, Class.getClassName());
	            pstmt.setString(2, Class.getUuid());
	            pstmt.executeUpdate();

    } catch (SQLException e) {
      logger.error("SQL Exception when updating Stream " + Class);
      logger.error(ExceptionUtils.getStackTrace(e));
      System.out.println(ExceptionUtils.getStackTrace(e));
      success = false;
   } 
		
		return success;
	}
	/**
	 * @see com.yahoo.petermwenda83.persistence.classroom.SchoolClassesDAO#getClassList()
	 */
	@Override
	public List<Stream> getClassList() {
		List<Stream>  list = null;
		 try(   
 		Connection conn = dbutils.getConnection();
 		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Stream ;");   
 		ResultSet rset = pstmt.executeQuery();
		  ) {
 	
      list = beanProcessor.toBeanList(rset, Stream.class);

    } catch(SQLException e){
 	   logger.error("SQL Exception when getting all Stream");
       logger.error(ExceptionUtils.getStackTrace(e));
       System.out.println(ExceptionUtils.getStackTrace(e));
    }
       return list;
	}


}
