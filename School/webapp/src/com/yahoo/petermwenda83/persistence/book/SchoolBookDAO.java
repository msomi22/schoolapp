/**
 * 
 */
package com.yahoo.petermwenda83.persistence.book;

import java.util.List;

import com.yahoo.petermwenda83.bean.book.Book;

/**
 * @author peter
 *
 */
public interface SchoolBookDAO {
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param ISBN
	 * @return
	 */
	public Book getBookByISBN(String schoolAccountUuid,String ISBN);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param Uuid
	 * @return
	 */
	
	public Book getBookByUUID(String schoolAccountUuid,String Uuid);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param BookStatus
	 * @return
	 */
	public Book getBookByBookStatus(String ISBN,String BookStatus);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param BorrowStatus
	 * @return
	 */
	public Book getBookByBorrowStatus(String ISBN,String BorrowStatus);
	/**
	 * 
	 * @param book
	 * @return
	 */
	public boolean putBook(Book book);
	 /**
	  * 
	  * @param book
	  * @return
	  */
	public boolean updateBook(Book book);
	 /**
	  * 
	  * @param book
	  * @return
	  */
	public boolean deleteBook(Book book);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @return
	 */
	public List<Book> getBookList(String schoolAccountUuid);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public List<Book> getBookList(String schoolAccountUuid, int startIndex , int endIndex);

}
