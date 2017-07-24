package com.yahoo.petermwenda83.persistence.othermoney;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies;
import com.yahoo.petermwenda83.persistence.GenericDAO;

public class StudentOtherMoniesDAO extends GenericDAO implements SchoolStudentOtherMoniesDAO {
	
	private static StudentOtherMoniesDAO studentOtherMoniesDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static StudentOtherMoniesDAO getInstance() {
		if(studentOtherMoniesDAO == null){
			studentOtherMoniesDAO = new StudentOtherMoniesDAO();		
			}
		return studentOtherMoniesDAO;
	}
	
	public StudentOtherMoniesDAO() {
		super();
		
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public StudentOtherMoniesDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#getStudentOtherMonies(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentOtherMonies getStudentOtherMonies(String studentUuid,String otherstypeUuid) {
		StudentOtherMonies studentOtherMonies = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentOtherMonies WHERE studentUuid =? AND otherstypeUuid =?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentUuid);
        	 pstmt.setString(2, otherstypeUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentOtherMonies  = beanProcessor.toBean(rset,StudentOtherMonies.class);
	   }
       	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting StudentOtherMonies for studentUuid: " + studentUuid );
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return studentOtherMonies; 
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#getStudentOtherMTY(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public StudentOtherMonies getStudentOtherMTY(String studentUuid, String otherstypeUuid, String term, String year) {
		StudentOtherMonies studentOtherMonies = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentOtherMonies WHERE studentUuid =?"
           	      		+ " AND otherstypeUuid =? AND term =? AND year =?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentUuid);
        	 pstmt.setString(2, otherstypeUuid);
        	 pstmt.setString(3, term);
        	 pstmt.setString(4, year);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentOtherMonies  = beanProcessor.toBean(rset,StudentOtherMonies.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting StudentOtherMonies for studentUuid: " + studentUuid );
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return studentOtherMonies; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#getStudentOtherList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<StudentOtherMonies> getStudentOtherList(String studentUuid,String Term, String Year) {
		 List<StudentOtherMonies> List = null;
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentOtherMonies WHERE "
							+ "studentUuid = ? AND Term =? AND Year =?;");
					) {
				psmt.setString(1, studentUuid);
				psmt.setString(2, Term);
				psmt.setString(3, Year);
				try(ResultSet rset = psmt.executeQuery();){
				
					List = beanProcessor.toBeanList(rset, StudentOtherMonies.class);
				}
			} catch (SQLException e) {
				logger.error("SQLException when trying to get StudentOtherMonies List for studentUuid " +studentUuid);
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e)); 
		    }
			
			return List;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#putStudentOtherMonies(com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies)
	 */
	@Override
	public boolean putStudentOtherMonies(StudentOtherMonies studentOtherMonies) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentOtherMonies" 
			        		+"(Uuid,StudentUuid,OtherstypeUuid,AmountPiad,Term,Year) VALUES (?,?,?,?,?,?);");
     		){
			   
	            pstmt.setString(1, studentOtherMonies.getUuid());
	            pstmt.setString(2, studentOtherMonies.getStudentUuid());
	            pstmt.setString(3, studentOtherMonies.getOtherstypeUuid());
	            pstmt.setDouble(4, studentOtherMonies.getAmountPiad());
	            pstmt.setString(5, studentOtherMonies.getTerm());
	            pstmt.setString(6, studentOtherMonies.getYear());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put StudentOtherMonies: "+studentOtherMonies);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#updateStudentOtherMonies(com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies)
	 */
	@Override
	public boolean updateStudentOtherMonies(StudentOtherMonies studentOtherMonies) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentOtherMonies SET AmountPiad = ?,Term =?,Year =? WHERE StudentUuid =?"
						+ "AND OtherstypeUuid =?;");
     		){
			   
	           
	            pstmt.setDouble(1, studentOtherMonies.getAmountPiad());
	            pstmt.setString(2, studentOtherMonies.getTerm());
	            pstmt.setString(3, studentOtherMonies.getYear());
	            pstmt.setString(4, studentOtherMonies.getStudentUuid());
	            pstmt.setString(5, studentOtherMonies.getOtherstypeUuid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put StudentOtherMonies: "+studentOtherMonies);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
          success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#deleteStudentOtherMonies(com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies)
	 */
	@Override
	public boolean deleteStudentOtherMonies(StudentOtherMonies studentOtherMonies) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentOtherMonies"
	         	      		+ " WHERE StudentUuid =? AND OtherstypeUuid =?;");       
	      		
	      		){
	      	
	    	     pstmt.setString(1, studentOtherMonies.getStudentUuid());
	             pstmt.setString(2, studentOtherMonies.getOtherstypeUuid());
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting studentOtherMonies : " +studentOtherMonies);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#getStudentOtherMoniesList()
	 */
	@Override
	public List<StudentOtherMonies> getStudentOtherMoniesList() {
		List<StudentOtherMonies>  list = null;		
		 try(   
      		Connection conn = dbutils.getConnection();
      		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentOtherMonies;");          		
  		) {			     
			 try( ResultSet rset = pstmt.executeQuery();){
	     	       
		  list = beanProcessor.toBeanList(rset, StudentOtherMonies.class);
	         	   }
			
         

      } catch(SQLException e){
      	  logger.error("SQL Exception when getting all SubjectUi");
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
      }
    
		
		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#getStudentOtherMoniesDistinct(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<StudentOtherMonies> getStudentOtherMoniesDistinct(String studentUuid) {
		List<StudentOtherMonies> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT otherstypeUuid FROM StudentOtherMonies WHERE"
        		 		+ " studentUuid = ?;");
     	   ) {
         	   pstmt.setString(1, studentUuid);      
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, StudentOtherMonies.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting DISTINCT Student OtherMonies List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#getStudentOtherMonies(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<StudentOtherMonies> getStudentOtherMoniesList(String studentUuid, String otherstypeUuid) {
		List<StudentOtherMonies> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentOtherMonies WHERE"
        		 		+ " studentUuid = ? AND otherstypeUuid = ?;");
     	   ) {
         	   pstmt.setString(1, studentUuid);  
         	   pstmt.setString(2, otherstypeUuid);  
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, StudentOtherMonies.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting  Student OtherMonies List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}

	

}
