/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.servlet.book;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.bean.book.Book;
import ke.co.qubintel.school.server.bean.book.StudentBook;
import ke.co.qubintel.school.server.persistence.book.BookDAO;
import ke.co.qubintel.school.server.persistence.book.StudentBookDAO;
import ke.co.qubintel.school.server.servlet.util.PropertiesConfig;
import ke.co.qubintel.school.server.session.SessionConstants;

public class ReturnBook extends HttpServlet{
	
	

	final String BORROW_STATUS_CLEARED = "Cleared";
	final String BORROW_STATUS_AVAILABLE = "Available";

	final String SUCCESS_BOOK_RETURN = "The book was successfully returned";
	final String ERROR_BOOK_RETURN = "Something went wrong while returning the book";
	
	final String ERROR_INCORRECT_SEC_KEY = "The security key entered was incorrect";

	
	private static StudentBookDAO studentBookDAO;
	private static BookDAO bookDAO;
	StudentBook studentBook;	

	/**   
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentBookDAO = StudentBookDAO.getInstance();
       bookDAO = BookDAO.getInstance();
      
   }
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String bookuuid = StringUtils.trimToEmpty(request.getParameter("bookuuid"));
       String bookisbn = StringUtils.trimToEmpty(request.getParameter("bkISBN"));
       String studentuuid = StringUtils.trimToEmpty(request.getParameter("studentUuid"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String securityKey = StringUtils.trimToEmpty(request.getParameter("securityKey"));
       
    
       
       if(StringUtils.isBlank(bookisbn)){
    	   session.setAttribute(SessionConstants.BOOK_RETURN_ERROR, ERROR_BOOK_RETURN); 
    	   
       }else if(StringUtils.isBlank(studentuuid)){
    	   session.setAttribute(SessionConstants.BOOK_RETURN_ERROR, ERROR_BOOK_RETURN); 
    	   
       }else if(!StringUtils.equals(securityKey, PropertiesConfig.getConfigValue("BOOK_SECURITY_KEY"))){
    	   session.setAttribute(SessionConstants.BOOK_RETURN_ERROR, ERROR_INCORRECT_SEC_KEY); 
    	
       }
       else{
    	   
    	   SimpleDateFormat formatter;
		   formatter = new SimpleDateFormat("dd, MMM yyyy");
		   
    	   String dateStri = "";
    	   Date dateNow = new Date(); 
    	   dateStri = formatter.format(dateNow);  
    	   
    	   /*studentBook = new StudentBook();
    	   studentBook.setStudentUuid(studentuuid);
    	   studentBook.setBookUuid(bookuuid); 
    	   studentBook.setReturnDate(dateStri);  
    	   studentBook.setBorrowStatus(BORROW_STATUS_CLEARED);
    	   
    	   Book book;
    	   book = bookDAO.getBookByUUID(schooluuid, bookuuid);
    	   book.setBorrowStatus(BORROW_STATUS_AVAILABLE);
    	   bookDAO.updateBook(book);
    	   
    	   if(studentBookDAO.ReturnBook(studentBook)){ 
    		   session.setAttribute(SessionConstants.BOOK_RETURN_SUCCESS, SUCCESS_BOOK_RETURN); 
    	   }else{
    		   session.setAttribute(SessionConstants.BOOK_RETURN_ERROR, ERROR_BOOK_RETURN+"3"); 
    	   }*/
    	   
       }
       
       response.sendRedirect("return.jsp");  
	   return; 
       
   }
   

   @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          doPost(request, response);
      }
   
   /**
	 * 
	 */
	private static final long serialVersionUID = -943519018520232351L;
}
