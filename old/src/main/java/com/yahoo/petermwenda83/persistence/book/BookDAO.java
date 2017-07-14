/**
 * 
 */
package com.yahoo.petermwenda83.persistence.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.book.Book;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class BookDAO extends GenericDAO implements SchoolBookDAO {

	private static BookDAO bookDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static BookDAO getInstance(){

		if(bookDAO == null){
			bookDAO = new BookDAO();		
		}
		return bookDAO;
	}

	/**
	 * 
	 */
	public BookDAO() { 
		super();
	}

	/**
	 * 
	 */
	public BookDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolBookDAO#getBookByISBN(java.lang.String, java.lang.String)
	 */
	@Override
	public Book getBookById(String accountId, String uuid) {
		Book book = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Book WHERE accountId = ? AND uuid = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				book  = beanProcessor.toBean(rset,Book.class);
			}



		}catch(SQLException e){
			logger.error("SQL Exception when getting Book with accountId " + accountId + " and uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return book; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolBookDAO#getBookByUUID(java.lang.String, java.lang.String)
	 */
	@Override
	public Book isBookAvailable(String accountId, String uuid, String isAvailable) {
		Book book = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Book WHERE accountId = ? AND uuid = ? AND isAvailable =?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.setString(3, isAvailable);
			rset = pstmt.executeQuery();
			while(rset.next()){
				book  = beanProcessor.toBean(rset,Book.class);
			}



		}catch(SQLException e){
			logger.error("SQL Exception when getting Book with accountId " + accountId + " uuid " + uuid +  " + isAvailable + " + isAvailable);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return book; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolBookDAO#getBookByBookStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public Book getBookCategory(String accountId, String uuid, String category) {
		Book book = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Book WHERE  accountId = ? AND uuid = ? AND category = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.setString(3, category);
			rset = pstmt.executeQuery();
			while(rset.next()){

				book  = beanProcessor.toBean(rset,Book.class);
			}



		}catch(SQLException e){
			logger.error("SQL Exception when getting Book with accountId  " + accountId + " , uuid " + uuid + ", category " + category);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return book; 
	}



	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolBookDAO#putBook(com.yahoo.petermwenda83.bean.book.Book)
	 */
	@Override
	public boolean putBook(Book book) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Book" 
						+"(uuid,accountId,isbn,author,publisher,title,isAvailable,category,dateAdded) VALUES (?,?,?,?,?,?,?,?,?);");
				){

			pstmt.setString(1, book.getUuid());
			pstmt.setString(2, book.getAccountId());
			pstmt.setString(3, book.getIsbn());
			pstmt.setString(4, book.getAuthor());
			pstmt.setString(5, book.getPublisher());
			pstmt.setString(6, book.getTitle());
			pstmt.setString(7, book.getIsAvailable());
			pstmt.setString(8, book.getCategory());
			pstmt.setTimestamp(9, book.getDateAdded()); 
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put book "+book);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolBookDAO#updateBook(com.yahoo.petermwenda83.bean.book.Book)
	 */
	@Override
	public boolean updateBook(Book book) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Book SET isbn =?, author = ?, publisher =? ,"
						+ "title =? ,isAvailable =? ,category =? WHERE uuid = ? AND accountId =?;");
				) {           			 	            

			pstmt.setString(1, book.getIsbn());
			pstmt.setString(2, book.getAuthor());
			pstmt.setString(3, book.getPublisher());
			pstmt.setString(4, book.getTitle());
			pstmt.setString(5, book.getIsAvailable());
			pstmt.setString(6, book.getCategory());
			pstmt.setString(7, book.getUuid());
			pstmt.setString(8, book.getAccountId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating book " + book);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolBookDAO#deleteBook(com.yahoo.petermwenda83.bean.book.Book)
	 */
	@Override
	public boolean deleteBook(String accountId,String uuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Book"
						+ " WHERE accountId =? AND uuid;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting book  " + accountId + " uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolBookDAO#getBookList(java.lang.String)
	 */
	@Override
	public List<Book> getBookList(String accountId) {
		List<Book> bookList = new ArrayList<>();
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Book WHERE "
						+ "accountId = ?;");
				) {
			psmt.setString(1, accountId);
			try(ResultSet rset = psmt.executeQuery();){

				bookList = beanProcessor.toBeanList(rset, Book.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying Books List for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return bookList;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.book.SchoolBookDAO#getBookList(java.lang.String, int, int)
	 */
	@Override
	public List<Book> getBookList(String accountId, int startIndex, int endIndex) {
		List<Book> bookList = new ArrayList<>();
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Book WHERE "
						+ "accountId = ? LIMIT ? OFFSET ? ;");
				) {
			psmt.setString(1, accountId);
			psmt.setInt(2, endIndex - startIndex);
			psmt.setInt(3, startIndex);

			try(ResultSet rset = psmt.executeQuery();){
				bookList = beanProcessor.toBeanList(rset, Book.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a bookList accountId " + accountId + " startIndex " + startIndex + " endIndex " + endIndex);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return bookList;		
	}

}
