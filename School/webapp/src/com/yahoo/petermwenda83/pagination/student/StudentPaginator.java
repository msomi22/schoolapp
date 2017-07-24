
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
package com.yahoo.petermwenda83.pagination.student;

import java.util.List;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.utils.StudentUtils;


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
	private String SchoolAccountUuid;
    private SchoolAccount account;
	/**
	 * @param SchoolAccountUuid 
	 * 
	 */
	public StudentPaginator(String SchoolAccountUuid) {
		account = new SchoolAccount();
		account.setUuid(SchoolAccountUuid); 
		
	    studentUtils = StudentUtils.getInstance();
		studentDAO = StudentDAO.getInstance();
		this.SchoolAccountUuid = SchoolAccountUuid;
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
       List<Student> stuList = studentDAO.getStudentList(account , 0, PAGESIZE);
       page = new StudentPage(1, getTotalPage(), PAGESIZE, stuList);	    
       return page;
   }

   
   /**
    * Provides the last page of the Incoming USSD session report
    *
    * @return	a Incoming USSD page
    */
   public StudentPage getLastPage() {
	   
	   StudentPage page = new StudentPage();

       List<Student> stuList = null;
       int  startIndex,sessionCount;
       int totalPage = getTotalPage();
       startIndex = (totalPage - 1) * PAGESIZE;
       sessionCount = studentUtils.getIncomingCount(account.getUuid());
       stuList = studentDAO.getStudentList(account, startIndex, sessionCount); 
       page = new StudentPage(totalPage, totalPage, PAGESIZE, stuList);
       return page;
   }
   

   /**
    * Moves you forward to the page of the Incoming USSD session that comes
    * after the current page
    *
    * @param currentPage
    * @return	an Incoming USSD page
    */
   public StudentPage getNextPage(final StudentPage currentPage) {
       int totalPage = getTotalPage();

       StudentPage page = new StudentPage();
       List<Student> smsList = studentDAO.getStudentList(account, currentPage.getPageNum() * PAGESIZE, 
       		((currentPage.getPageNum() * PAGESIZE) + PAGESIZE));

       page = new StudentPage(currentPage.getPageNum() + 1, totalPage, PAGESIZE, smsList);

       return page;
   }

   
   /**
    * Moves you backward to the page of the Incoming USSD session that comes
    * before the current page
    *
    * @param currentPage
    * @return	an Incoming USSD page
    */
   public StudentPage getPrevPage(final StudentPage currentPage) {
       int totalPage = getTotalPage();

       StudentPage page = new StudentPage();
       
       List<Student> smsList = studentDAO.getStudentList(account, (currentPage.getPageNum() - 2) * PAGESIZE, 
       		((currentPage.getPageNum() - 1) * PAGESIZE));

       page = new StudentPage(currentPage.getPageNum() - 1, totalPage, PAGESIZE, smsList);

       return page;
   }
   

   /**
    * Calculates the total number of pages that would be printed for the SMS
    * that belong to the logged-in account
    *
    * @return	an integer
    */
  
		public int getTotalPage() {
	        int totalSize = 0;

	        //get the number of all sessions belonging to this email
	        totalSize = studentUtils.getStudents(SchoolAccountUuid);

	//TODO: divide by the page size and add one to take care of remainders and what else?
	        return ((totalSize - 1) / PAGESIZE) + 1;
	    }
	    
		
}
