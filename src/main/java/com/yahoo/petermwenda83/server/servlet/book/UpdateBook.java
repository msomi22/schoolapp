package com.yahoo.petermwenda83.server.servlet.book;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.book.Book;
import com.yahoo.petermwenda83.persistence.book.BookDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class UpdateBook extends HttpServlet{
	
	final String EMPTY_BOOK_ISBN = "Book ISBN can't be empty";
	final String EMPTY_BOOK_AUTHOR = "Book author can't be empty";
	final String EMPTY_BOOK_PUBLISHER = "Book publisher can't be empty";
	final String EMPTY_BOOK_TITTLE = "Book title can't be empty";
	final String EMPTY_CATEGORY = "Select Book Category";
	
	final String ERROR_BOOK_EXIST = "A book with the given ISBN/Book Number already exist";
	
	final String ERROR_SOMETHING_WENT_WRONG = "Sorry, something went wrong while updating the book";
	final String SUCCESS_BOOK_UPDATED = "The book was successfully updated";
	
	final String NAME_ERROR = "Data format error/incorrent lenght.";
	
	private static BookDAO bookDAO;
	Book book;
	/**   
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       
       bookDAO = BookDAO.getInstance();
   }
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);//
       
       String bookuuid = StringUtils.trimToEmpty(request.getParameter("bookuuid"));
       String isbn = StringUtils.trimToEmpty(request.getParameter("bkISBN"));
       String author = StringUtils.trimToEmpty(request.getParameter("bkAUTHOR"));
       String publisher = StringUtils.trimToEmpty(request.getParameter("bkPUBLISHER"));
       String title = StringUtils.trimToEmpty(request.getParameter("bkTitle"));
       String bookstatus = StringUtils.trimToEmpty(request.getParameter("bookstatus"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       //System.out.println("bookuuid="+bookuuid); 
       if(StringUtils.isBlank(isbn)){
    	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, EMPTY_BOOK_ISBN); 
    	   
       }else if(!Wordlength(isbn)){
	 	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, NAME_ERROR); 
		   
	   }else if(StringUtils.isBlank(author)){
    	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, EMPTY_BOOK_AUTHOR); 
    	   
       }else if(!Wordlength(author)){
	 	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, NAME_ERROR); 
		   
	   }else if(StringUtils.isBlank(publisher)){
    	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, EMPTY_BOOK_PUBLISHER); 
    	   
       }else if(!Wordlength(publisher)){
	 	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, NAME_ERROR); 
		   
	   }else if(StringUtils.isBlank(title)){
    	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, EMPTY_BOOK_TITTLE); 
    	   
       }else if(!Wordlength(title)){
	 	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, NAME_ERROR); 
		   
	   }else if(StringUtils.isBlank(bookstatus)){
    	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, EMPTY_CATEGORY); 
    	   
       }else if(StringUtils.isBlank(schooluuid)){
    	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, ERROR_SOMETHING_WENT_WRONG); 
    	   
       }else if(StringUtils.isBlank(bookuuid)){
    	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, ERROR_SOMETHING_WENT_WRONG); 
    	   
       }else{
    	   
    	   
    	   book = bookDAO.getBookByUUID(schooluuid, bookuuid);
           book.setISBN(isbn);
           book.setAuthor(author);
           book.setPublisher(publisher);
           book.setTitle(title);
           book.setBookStatus(bookstatus);
           
           if(bookDAO.updateBook(book)){
        	   session.setAttribute(SessionConstants.BOOK_UPDATE_SUCCESS, SUCCESS_BOOK_UPDATED); 
           }else{
        	   session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, ERROR_SOMETHING_WENT_WRONG); 
           }    
       }
      
	response.sendRedirect("lib.jsp");  
	return;
       
   }
   

	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	    
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
   

	/**
	 * @param mystring
	 * @return
	 */
	private static boolean Wordlength(String mystring) {
		boolean isvalid = true;
		int length = 0;
		length = mystring.length();
		if(length<3){
			isvalid = false;
		}
		return isvalid;
	}
   

   @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          doPost(request, response);
      }

	/**
	 * 
	 */
	private static final long serialVersionUID = -4014004421955048608L;

}
