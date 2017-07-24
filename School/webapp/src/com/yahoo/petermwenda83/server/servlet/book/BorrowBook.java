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
import com.yahoo.petermwenda83.bean.book.StudentBook;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.book.BookDAO;
import com.yahoo.petermwenda83.persistence.book.StudentBookDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class BorrowBook extends HttpServlet{
	
	
	final String BOOK__REFERENCE = "You can't borrow a reference book";//reference , shortloan
	final String BOOK_NOT_AVALILABLE = "This book is not available at the moment";//cleared , borrowed
	
	final String BOOK_STATUS_REFERENCE = "Reference";
	final String BORROW_STATUS_BORROWED = "Borrowed";
	
	final String ERROR_STUDENT_ADMNO_NOT_FOUND = "The admission number provided was not found.";
	final String ERROR_BOOK_NOT_FOUND = "The book specified was not found.";

	final String SUCCESS_BOOK_BORROWED = "The book was successfully borrowed";
	final String ERROR_SOMETHING_WENT_WRONG = "Something went wrong while borrowing the book";
	final String ERROR_STUDENT_INACTIVE = "This student is Inactive, they can not borrow a book";
	
	final String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
	
	
	private static StudentBookDAO studentBookDAO;
	private static StudentDAO studentDAO;
	private static BookDAO bookDAO;
	
	

	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentBookDAO = StudentBookDAO.getInstance();
       studentDAO = StudentDAO.getInstance();
       bookDAO = BookDAO.getInstance();
   }
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String bookisbn = StringUtils.trimToEmpty(request.getParameter("bookisbn"));
       String bookuuid = StringUtils.trimToEmpty(request.getParameter("bookuuid"));
       String studentAdmNo = StringUtils.trimToEmpty(request.getParameter("admissionNo"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       //System.out.println("bookuuid="+bookuuid);
       
       if(StringUtils.isBlank(bookisbn)){
    	   session.setAttribute(SessionConstants.BOOK_BORROW_ERROR, ERROR_BOOK_NOT_FOUND); 
    	   
       }
       if(StringUtils.isBlank(bookuuid)){
    	   session.setAttribute(SessionConstants.BOOK_BORROW_ERROR, ERROR_SOMETHING_WENT_WRONG); 
    	   
       }else if(StringUtils.isBlank(studentAdmNo)){
    	   session.setAttribute(SessionConstants.BOOK_BORROW_ERROR, ERROR_STUDENT_ADMNO_NOT_FOUND); 
    	   
       }else if(StringUtils.isBlank(schooluuid)){
    	   session.setAttribute(SessionConstants.BOOK_BORROW_ERROR,ERROR_SOMETHING_WENT_WRONG ); 
    	   
       }else if(bookDAO.getBookByBorrowStatus(bookisbn, BORROW_STATUS_BORROWED) !=null){
    	   session.setAttribute(SessionConstants.BOOK_BORROW_ERROR, BOOK_NOT_AVALILABLE); 
    	   
       }else if(bookDAO.getBookByBookStatus(bookisbn,BOOK_STATUS_REFERENCE ) !=null){
    	   session.setAttribute(SessionConstants.BOOK_BORROW_ERROR, BOOK__REFERENCE); 
    	   
       }else if(studentBookDAO.getStudentBook(bookuuid,BORROW_STATUS_BORROWED) !=null){
    	   session.setAttribute(SessionConstants.BOOK_BORROW_ERROR, BOOK_NOT_AVALILABLE); 
    	   
       }else{
    	   Book book;
    	   Student student;
    	   StudentBook studentBook;
    	   student = studentDAO.getStudentObjByadmNo(schooluuid, studentAdmNo);
    	   if(student !=null){
    		   if(StringUtils.equals(student.getStatusUuid(),statusUuid)){
    		   studentBook = new StudentBook();
    		   studentBook.setStudentUuid(student.getUuid());
    		   studentBook.setBookUuid(bookuuid); 
    		   studentBook.setBorrowStatus(BORROW_STATUS_BORROWED);
    		   
    		   book = bookDAO.getBookByUUID(schooluuid, bookuuid);
    		   book.setBorrowStatus(BORROW_STATUS_BORROWED); 
    		   bookDAO.updateBook(book);

    		   if(studentBookDAO.BorrowBook(studentBook)){
    			   session.setAttribute(SessionConstants.BOOK_BORROW_SUCCESS, SUCCESS_BOOK_BORROWED); 
    		   }else{
    			   session.setAttribute(SessionConstants.BOOK_BORROW_ERROR, ERROR_SOMETHING_WENT_WRONG); 
    		   }
    		  }else{
    			  session.setAttribute(SessionConstants.BOOK_BORROW_ERROR,ERROR_STUDENT_INACTIVE ); 
    		  }
    	   }else{
    		   session.setAttribute(SessionConstants.BOOK_BORROW_ERROR,ERROR_STUDENT_ADMNO_NOT_FOUND ); 
    	   }
    	   
       }
       
       response.sendRedirect("lib.jsp");  
	   return; 
     
   }
   

/**
 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
@Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          doPost(request, response);
      }

/**
 * 
 */
private static final long serialVersionUID = 8351012024792795726L;

}
