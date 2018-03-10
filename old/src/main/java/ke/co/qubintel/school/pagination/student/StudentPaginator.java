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
package ke.co.qubintel.school.pagination.student;

import java.util.List;

import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.persistence.utils.StudentUtils;


/**
 * For Student pagination
 * 
 *  @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentPaginator {
	
	public final int PAGESIZE = 15;
	private static StudentUtils studentUtils;
	private static StudentDAO studentDAO;
	private String accountId;
	/**
	 * @param SchoolAccountUuid 
	 * 
	 */
	public StudentPaginator(String accountId) {
	    studentUtils = StudentUtils.getInstance();
		studentDAO = StudentDAO.getInstance();
		this.accountId = accountId;
	}
	
	    /**
	     * @param databaseName
	     * @param Host
	     * @param databaseUsername
	     * @param databasePassword
	     * @param databasePort
	     */
	    public StudentPaginator(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {

	        //initialize the DAOs
	    	studentUtils = new StudentUtils(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    	studentDAO = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    }
	
	 /**
    *
    * @return the first page
    */
   public StudentPage getFirstPage() {
	   StudentPage page = new StudentPage();
       List<Student> stuList = studentDAO.getAllStudent(accountId , 0, PAGESIZE);
       page = new StudentPage(1, getTotalPage(), PAGESIZE, stuList);	    
       return page;
   }

   
   /**
    * Provides the last page 
    *
    * @return	
    */
   public StudentPage getLastPage() {
	   
	   StudentPage page = new StudentPage();

       List<Student> stuList = null;
       int  startIndex,sessionCount;
       int totalPage = getTotalPage();
       startIndex = (totalPage - 1) * PAGESIZE;
       sessionCount = studentUtils.getIncomingCount(accountId);
       stuList = studentDAO.getAllStudent(accountId, startIndex, sessionCount); 
       page = new StudentPage(totalPage, totalPage, PAGESIZE, stuList);
       return page;
   }
   

   /**
    * Moves you forward to the page that comes
    * after the current page
    *
    * @param currentPage
    * @return	
    */
   public StudentPage getNextPage(final StudentPage currentPage) {
       int totalPage = getTotalPage();

       StudentPage page = new StudentPage();
       List<Student> smsList = studentDAO.getAllStudent(accountId, currentPage.getPageNum() * PAGESIZE, 
       		((currentPage.getPageNum() * PAGESIZE) + PAGESIZE));

       page = new StudentPage(currentPage.getPageNum() + 1, totalPage, PAGESIZE, smsList);

       return page;
   }

   
   /**
    * Moves you backward to the page of that comes
    * before the current page
    *
    * @param currentPage
    * @return	
    */
   public StudentPage getPrevPage(final StudentPage currentPage) {
       int totalPage = getTotalPage();

       StudentPage page = new StudentPage();
       
       List<Student> smsList = studentDAO.getAllStudent(accountId, (currentPage.getPageNum() - 2) * PAGESIZE, 
       		((currentPage.getPageNum() - 1) * PAGESIZE));

       page = new StudentPage(currentPage.getPageNum() - 1, totalPage, PAGESIZE, smsList);

       return page;
   }
   

   /**
    * Calculates the total number of pages that would be printed 
    * 
    *
    * @return	an integer
    */
  
		public int getTotalPage() {
	        int totalSize = 0;

	        //get the number of all sessions belonging to this email
	        totalSize = studentUtils.getStudents(accountId);

	        return ((totalSize - 1) / PAGESIZE) + 1;
	    }
	    
		
}
