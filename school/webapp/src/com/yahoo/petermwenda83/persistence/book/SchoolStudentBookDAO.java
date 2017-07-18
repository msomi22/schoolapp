/**
 * 
 */
package com.yahoo.petermwenda83.persistence.book;

import java.util.List;

import com.yahoo.petermwenda83.bean.book.StudentBook;

/**
 * @author peter
 *
 */
public interface SchoolStudentBookDAO {
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public StudentBook getStudentBookByStatus(String ISBookUuidBN,String borrowStatus);
	/**
	 * 
	 * @param studentUuid
	 * @param borrowStatus
	 * @return
	 */
	public StudentBook getStudentBook(String studentUuid,String BookUuid);
	
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public List<StudentBook> getStudentBookByStudentId(String studentUuid,String borrowStatus);
	/**
	 * 
	 * @param ISBN
	 * @return
	 */
	public StudentBook getStudentBookByUuid(String BookUuid);
	/**
	 * 
	 * @param borrowStatus
	 * @return
	 */
	public StudentBook getStudentBookByBorrowStatus(String borrowStatus);
	/**
	 * 
	 * @param studentBook
	 * @return
	 */
	public boolean BorrowBook(StudentBook studentBook);
	/**
	 * 
	 * @param studentBook
	 * @return
	 */
	public boolean ReturnBook(StudentBook studentBook);
	/**
	 * 
	 * @param studentBook
	 * @return
	 */
	public boolean deleteStudentBook(StudentBook studentBook);
	/**
	 * 
	 * @return
	 */
	public List<StudentBook> getStudentBookList(String borrowStatus);

}
