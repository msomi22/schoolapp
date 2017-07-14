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
	 * @param Uuid
	 * @return
	 */
	
	public Book getBookById(String accountId,String uuid);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param BookStatus
	 * @return
	 */
	public Book isBookAvailable(String accountId, String uuid, String isAvailable);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param BorrowStatus
	 * @return
	 */
	public Book getBookCategory(String accountId, String uuid, String category);
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
	public boolean deleteBook(String accountId,String uuid);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @return
	 */
	public List<Book> getBookList(String accountId);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public List<Book> getBookList(String accountId, int startIndex , int endIndex);

}
