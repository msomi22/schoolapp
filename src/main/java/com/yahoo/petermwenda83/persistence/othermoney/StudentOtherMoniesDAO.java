package com.yahoo.petermwenda83.persistence.othermoney;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;
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
	public StudentOtherFee getStudentOtherMonies(String studentUuid,String otherstypeUuid) {
		StudentOtherFee studentOtherFee = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentOtherFee WHERE studentUuid =? AND otherstypeUuid =?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentUuid);
        	 pstmt.setString(2, otherstypeUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentOtherFee  = beanProcessor.toBean(rset,StudentOtherFee.class);
	   }
       	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting StudentOtherFee for studentUuid: " + studentUuid );
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return studentOtherFee; 
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#getStudentOtherMTY(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public StudentOtherFee getStudentOtherMTY(String studentUuid, String otherstypeUuid, String term, String year) {
		StudentOtherFee studentOtherFee = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentOtherFee WHERE studentUuid =?"
           	      		+ " AND otherstypeUuid =? AND term =? AND year =?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentUuid);
        	 pstmt.setString(2, otherstypeUuid);
        	 pstmt.setString(3, term);
        	 pstmt.setString(4, year);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentOtherFee  = beanProcessor.toBean(rset,StudentOtherFee.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting StudentOtherFee for studentUuid: " + studentUuid );
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return studentOtherFee; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#getStudentOtherList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<StudentOtherFee> getStudentOtherList(String studentUuid,String Term, String Year) {
		 List<StudentOtherFee> List = null;
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentOtherFee WHERE "
							+ "studentUuid = ? AND Term =? AND Year =?;");
					) {
				psmt.setString(1, studentUuid);
				psmt.setString(2, Term);
				psmt.setString(3, Year);
				try(ResultSet rset = psmt.executeQuery();){
				
					List = beanProcessor.toBeanList(rset, StudentOtherFee.class);
				}
			} catch (SQLException e) {
				logger.error("SQLException when trying to get StudentOtherFee List for studentUuid " +studentUuid);
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e)); 
		    }
			
			return List;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#putStudentOtherMonies(com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee)
	 */
	@Override
	public boolean putStudentOtherMonies(StudentOtherFee studentOtherFee) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentOtherFee" 
			        		+"(Uuid,StudentUuid,OtherstypeUuid,AmountPiad,Term,Year) VALUES (?,?,?,?,?,?);");
     		){
			   
	            pstmt.setString(1, studentOtherFee.getUuid());
	            pstmt.setString(2, studentOtherFee.getStudentUuid());
	            pstmt.setString(3, studentOtherFee.getOtherstypeUuid());
	            pstmt.setDouble(4, studentOtherFee.getAmountPiad());
	            pstmt.setString(5, studentOtherFee.getTerm());
	            pstmt.setString(6, studentOtherFee.getYear());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put StudentOtherFee: "+studentOtherFee);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#updateStudentOtherMonies(com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee)
	 */
	@Override
	public boolean updateStudentOtherMonies(StudentOtherFee studentOtherFee) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentOtherFee SET AmountPiad = ?,Term =?,Year =? WHERE StudentUuid =?"
						+ "AND OtherstypeUuid =?;");
     		){
			   
	           
	            pstmt.setDouble(1, studentOtherFee.getAmountPiad());
	            pstmt.setString(2, studentOtherFee.getTerm());
	            pstmt.setString(3, studentOtherFee.getYear());
	            pstmt.setString(4, studentOtherFee.getStudentUuid());
	            pstmt.setString(5, studentOtherFee.getOtherstypeUuid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put StudentOtherFee: "+studentOtherFee);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
          success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolStudentOtherMoniesDAO#deleteStudentOtherMonies(com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee)
	 */
	@Override
	public boolean deleteStudentOtherMonies(StudentOtherFee studentOtherFee) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentOtherFee"
	         	      		+ " WHERE StudentUuid =? AND OtherstypeUuid =?;");       
	      		
	      		){
	      	
	    	     pstmt.setString(1, studentOtherFee.getStudentUuid());
	             pstmt.setString(2, studentOtherFee.getOtherstypeUuid());
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting studentOtherFee : " +studentOtherFee);
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
	public List<StudentOtherFee> getStudentOtherMoniesList() {
		List<StudentOtherFee>  list = null;		
		 try(   
      		Connection conn = dbutils.getConnection();
      		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentOtherFee;");          		
  		) {			     
			 try( ResultSet rset = pstmt.executeQuery();){
	     	       
		  list = beanProcessor.toBeanList(rset, StudentOtherFee.class);
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
	public List<StudentOtherFee> getStudentOtherMoniesDistinct(String studentUuid) {
		List<StudentOtherFee> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT otherstypeUuid FROM StudentOtherFee WHERE"
        		 		+ " studentUuid = ?;");
     	   ) {
         	   pstmt.setString(1, studentUuid);      
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, StudentOtherFee.class);
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
	public List<StudentOtherFee> getStudentOtherMoniesList(String studentUuid, String otherstypeUuid) {
		List<StudentOtherFee> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentOtherFee WHERE"
        		 		+ " studentUuid = ? AND otherstypeUuid = ?;");
     	   ) {
         	   pstmt.setString(1, studentUuid);  
         	   pstmt.setString(2, otherstypeUuid);  
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, StudentOtherFee.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting  Student OtherMonies List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}

	

}
