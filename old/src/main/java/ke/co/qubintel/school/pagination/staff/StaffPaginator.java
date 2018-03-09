
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
package ke.co.qubintel.school.pagination.staff;

import java.util.List;

import ke.co.qubintel.school.server.bean.staff.Staff;
import ke.co.qubintel.school.server.persistence.staff.StaffDAO;
import ke.co.qubintel.school.server.persistence.utils.CommonUtils;


/** 
 * For Staff pagination
 * 
 *  @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StaffPaginator {
	
	public final int PAGESIZE = 10;
	private static CommonUtils commonUtils;
	private static StaffDAO staffDAO;
	private String accountId;;
	/**
	 * @param SchoolAccountUuid 
	 * 
	 */
	public StaffPaginator(String accountId) {
		commonUtils = CommonUtils.getInstance();
		staffDAO = StaffDAO.getInstance();
		this.accountId = accountId;
	}
	
	    /**
	     * @param databaseName
	     * @param Host
	     * @param databaseUsername
	     * @param databasePassword
	     * @param databasePort
	     */
	    public StaffPaginator(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {

	        //initialize the DAOs
	    	commonUtils = new CommonUtils(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    	staffDAO = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    }
	
	 /**
    *
    * @return the first page
    */
   public StaffPage getFirstPage() {
	   StaffPage page = new StaffPage();
       List<Staff> staffList = staffDAO.getStaff(accountId , 0, PAGESIZE);
       page = new StaffPage(1, getTotalPage(), PAGESIZE, staffList);	    
       return page;
   }

   
   /**
    * Provides the last page of the Incoming USSD session report
    *
    * @return	a Incoming USSD page
    */
   public StaffPage getLastPage() {
	   
	   StaffPage page = new StaffPage();

       List<Staff> staffList = null;
       int  startIndex,sessionCount;
       int totalPage = getTotalPage();
       startIndex = (totalPage - 1) * PAGESIZE;
       sessionCount = commonUtils.getStaffCount(accountId);
       staffList = staffDAO.getStaff(accountId, startIndex, sessionCount); 
       page = new StaffPage(totalPage, totalPage, PAGESIZE, staffList);
       return page;
   }
   

   /**
    * Moves you forward to the page 
    * after the current page
    *
    * @param currentPage
    * @return	an staff page
    */
   public StaffPage getNextPage(final StaffPage currentPage) {
       int totalPage = getTotalPage();

       StaffPage page = new StaffPage();
       List<Staff> staffList = staffDAO.getStaff(accountId, currentPage.getPageNum() * PAGESIZE, 
       		((currentPage.getPageNum() * PAGESIZE) + PAGESIZE));

       page = new StaffPage(currentPage.getPageNum() + 1, totalPage, PAGESIZE, staffList);

       return page;
   }

   
   /**
    * Moves you backward to the page
    * before the current page
    *
    * @param currentPage
    * @return	an staff page
    */
   public StaffPage getPrevPage(final StaffPage currentPage) {
       int totalPage = getTotalPage();

       StaffPage page = new StaffPage();
       
       List<Staff> staffList = staffDAO.getStaff(accountId, (currentPage.getPageNum() - 2) * PAGESIZE, 
       		((currentPage.getPageNum() - 1) * PAGESIZE));

       page = new StaffPage(currentPage.getPageNum() - 1, totalPage, PAGESIZE, staffList);

       return page;
   }
   

   /**
    * Calculates the total number of pages that would be printed 
    * that belong to the logged-in account
    *
    * @return	an integer
    */
  
		public int getTotalPage() {
	        int totalSize = 0;

	        //get the number of all sessions 
	        totalSize = commonUtils.getStaffCount(accountId);

	        //divide by the page size and add one to take care of remainders and what else?
	        return ((totalSize - 1) / PAGESIZE) + 1;
	    }
	    
		
}
