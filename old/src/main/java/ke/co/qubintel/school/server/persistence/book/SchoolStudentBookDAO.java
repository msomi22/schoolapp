/**
 * 
 */
package ke.co.qubintel.school.server.persistence.book;

import java.util.List;

import ke.co.qubintel.school.server.bean.book.StudentBook;

/**
 * @author peter
 *
 */
public interface SchoolStudentBookDAO {
	/**
	 * 
	 * @param accountId
	 * @param bookId
	 * @param hasReturned
	 * @return
	 */
	public StudentBook getStudentBook(String bookId, String hasReturned);
	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @param bookId
	 * @param hasReturned
	 * @return
	 */
	public StudentBook getStudentBook(String studentId, String bookId, String hasReturned);
	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @param hasReturned
	 * @return
	 */
	public List<StudentBook> getStudentBooks(String studentId, String hasReturned);
	
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
	 * @param accountId
	 * @param bookId
	 * @return
	 */
	public boolean deleteStudentBook(String accountId,String bookId);
	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @return
	 */
	public List<StudentBook> getStudentBookList(String accountId,String studentId);

}
