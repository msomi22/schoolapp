/**
 * 
 */
package com.yahoo.petermwenda83.persistence.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.book.StudentBook;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class StudentBookDAO extends GenericDAO implements SchoolStudentBookDAO {

	private static StudentBookDAO studentBookDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static StudentBookDAO getInstance(){
		
		if(studentBookDAO == null){
			studentBookDAO = new StudentBookDAO();		
		}
		return studentBookDAO;
	}
	
	/**
	 * 
	 */
	public StudentBookDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public StudentBookDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolStudentBookDAO#getStudentBook(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public StudentBook getStudentBook(String bookId, String hasReturned) {
		StudentBook studentBook = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentBook WHERE bookId =? AND hasReturned =?;");       
        		
        		){
        	 pstmt.setString(1, bookId);
        	 pstmt.setString(2, hasReturned);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentBook  = beanProcessor.toBean(rset,StudentBook.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting studentBook");
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return studentBook; 
	}
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolStudentBookDAO#getStudentBook(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentBook getStudentBook(String studentId, String bookId, String hasReturned) {
		StudentBook studentBook = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentBook WHERE studentId = ? AND bookId =? AND hasReturned =?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentId);
        	 pstmt.setString(2, bookId);
        	 pstmt.setString(3, hasReturned);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 studentBook  = beanProcessor.toBean(rset,StudentBook.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting studentBook for student " + studentId);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return studentBook; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolStudentBookDAO#getStudentBookByStudentId(java.lang.String)
	 */
	@Override
	public List<StudentBook> getStudentBooks(String studentId, String hasReturned) {
		List<StudentBook> bookList = new ArrayList<>();
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentBook WHERE studentId =? AND hasReturned = ?;");
				) {
			psmt.setString(1, studentId);
			psmt.setString(2, hasReturned);
			try(ResultSet rset = psmt.executeQuery();){
			
				bookList = beanProcessor.toBeanList(rset, StudentBook.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a StudentBook List for studentId" + studentId + " hasReturned" + hasReturned);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return bookList;
		
	}

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolStudentBookDAO#putStudentBook(com.yahoo.petermwenda83.bean.book.StudentBook)
	 */
	@Override
	public boolean BorrowBook(StudentBook studentBook) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentBook" 
			        		+"(uuid,accountId,studentId,bookId,hasReturned,returnDate,borrowDate) VALUES (?,?,?,?,?,?,?);");
		             ){
			   
	            pstmt.setString(1, studentBook.getUuid());
	            pstmt.setString(2, studentBook.getAccountId());
	            pstmt.setString(2, studentBook.getStudentId());
	            pstmt.setString(3, studentBook.getBookId());
	            pstmt.setString(4, studentBook.getHasReturned());
	            pstmt.setString(5, studentBook.getReturnDate());
	            pstmt.setTimestamp(6, new Timestamp(studentBook.getBorrowDate().getTime()));
	           	pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		 logger.error("SQL Exception trying to put studentBook " + studentBook);
         logger.error(ExceptionUtils.getStackTrace(e)); 
         System.out.println(ExceptionUtils.getStackTrace(e));
         success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolStudentBookDAO#updateStudentBook(com.yahoo.petermwenda83.bean.book.StudentBook)
	 */
	@Override
	public boolean ReturnBook(StudentBook studentBook) {
		boolean success = true;
		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentBook SET hasReturned =?,"
			        + "returnDate =?  WHERE bookId = ? AND studentId =? ;");
	               ) {    
			 
			    pstmt.setString(1, studentBook.getHasReturned());
	            pstmt.setString(2, studentBook.getReturnDate());
	            pstmt.setString(3, studentBook.getBookId());
	            pstmt.setString(4, studentBook.getStudentId());
	            pstmt.executeUpdate();

		  } catch (SQLException e) {
		    logger.error("SQL Exception when updating studentBook " + studentBook);
		    logger.error(ExceptionUtils.getStackTrace(e));
		    System.out.println(ExceptionUtils.getStackTrace(e));
		    success = false;
		 } 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolStudentBookDAO#deleteStudentBook(com.yahoo.petermwenda83.bean.book.StudentBook)
	 */
	@Override
	public boolean deleteStudentBook(String accountId, String bookId) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentBook"
	         	      		+ " WHERE accountId =? AND bookId =?;");       
	      		
	      		){
	      	     pstmt.setString(1, accountId);
	      	     pstmt.setString(2, bookId);
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting studentBook for accountId " + accountId + " bookId " + bookId);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolStudentBookDAO#StudentBookList()
	 */
	@Override
	public List<StudentBook> getStudentBookList(String accountId,String studentId) {
		List<StudentBook> studentBookList = new ArrayList<>();
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentBook WHERE "
						+ "accountId = ?AND studentId =?;");
				) {
			psmt.setString(1, accountId);
			psmt.setString(2, studentId);
			try(ResultSet rset = psmt.executeQuery();){
			
				studentBookList = beanProcessor.toBeanList(rset, StudentBook.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Student Book List with accountId " + accountId + " studentId " + studentId);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		return studentBookList;
	}

	

}
