
/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package com.yahoo.petermwenda83.pagination.book;

import java.util.List;

import com.yahoo.petermwenda83.bean.book.Book;
import com.yahoo.petermwenda83.persistence.book.BookDAO;
import com.yahoo.petermwenda83.persistence.utils.CommonUtils;


/** 
 * For Student pagination
 * 
 *  @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class BookPaginator {
	
	public final int PAGESIZE = 15;
	private static CommonUtils commonUtils;
	private static BookDAO bookDAO;
	private String SchoolAccountUuid;
	/**
	 * @param SchoolAccountUuid 
	 * 
	 */
	public BookPaginator(String SchoolAccountUuid) {
		commonUtils = CommonUtils.getInstance();
		bookDAO = BookDAO.getInstance();
		this.SchoolAccountUuid = SchoolAccountUuid;
	}
	
	    /**
	     * @param databaseName
	     * @param Host
	     * @param databaseUsername
	     * @param databasePassword
	     * @param databasePort
	     */
	    public BookPaginator(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {

	        //initialize the DAOs
	    	commonUtils = new CommonUtils(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    	bookDAO = new BookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    }
	
	 /**
    *
    * @return the first page
    */
   public BookPage getFirstPage() {
	   BookPage page = new BookPage();
       List<Book> bookList = bookDAO.getBookList(SchoolAccountUuid , 0, PAGESIZE);
       page = new BookPage(1, getTotalPage(), PAGESIZE, bookList);	    
       return page;
   }

   
   /**
    * Provides the last page
    *
    * @return	Book page
    */
   public BookPage getLastPage() {
	   
	   BookPage page = new BookPage();

       List<Book> bookList = null;
       int  startIndex,sessionCount;
       int totalPage = getTotalPage();
       startIndex = (totalPage - 1) * PAGESIZE;
       sessionCount = commonUtils.getBookCount(SchoolAccountUuid);
       bookList = bookDAO.getBookList(SchoolAccountUuid, startIndex, sessionCount); 
       page = new BookPage(totalPage, totalPage, PAGESIZE, bookList);
       return page;
   }
   

   /**
    * Moves you forward to the page 
    * after the current page
    *
    * @param currentPage
    * @return	an Book page
    */
   public BookPage getNextPage(final BookPage currentPage) {
       int totalPage = getTotalPage();

       BookPage page = new BookPage();
       List<Book> smsList = bookDAO.getBookList(SchoolAccountUuid, currentPage.getPageNum() * PAGESIZE, 
       		((currentPage.getPageNum() * PAGESIZE) + PAGESIZE));
       page = new BookPage(currentPage.getPageNum() + 1, totalPage, PAGESIZE, smsList);
       return page;
   }

   
   /**
    * Moves you backward to the page
    * before the current page
    *
    * @param currentPage
    * @return	an Book page
    */
   public BookPage getPrevPage(final BookPage currentPage) {
       int totalPage = getTotalPage();

       BookPage page = new BookPage();
       
       List<Book> smsList = bookDAO.getBookList(SchoolAccountUuid, (currentPage.getPageNum() - 2) * PAGESIZE, 
       		((currentPage.getPageNum() - 1) * PAGESIZE));
         page = new BookPage(currentPage.getPageNum() - 1, totalPage, PAGESIZE, smsList);
       return page;
   }
   

   /**
    * Calculates the total number of pages that would be printed
    * 
    * @return	an integer
    */
  
		public int getTotalPage() {
	        int totalSize = 0;
	        totalSize = commonUtils.getBookCount(SchoolAccountUuid);
	        //divide by the page size and add one to take care of remainders and what else?
	        return ((totalSize - 1) / PAGESIZE) + 1;
	    }
	    
		
}
