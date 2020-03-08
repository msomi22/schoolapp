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
package ke.co.qubintel.school.pagination.parent;

import java.util.List;

import ke.co.qubintel.school.server.bean.student.guardian.StudentParent;
import ke.co.qubintel.school.server.persistence.guardian.ParentsDAO;
import ke.co.qubintel.school.server.persistence.utils.CommonUtils;

/** 
 * For Student pagination
 * 
 *  @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ParentPaginator {
	
	public final int PAGESIZE = 15;
	private static CommonUtils commonUtils;
	private static ParentsDAO parentsDAO;
	private String accountId;
	/**
	 * 
	 */
	public ParentPaginator(String accountId) {
		commonUtils = CommonUtils.getInstance();
		parentsDAO = ParentsDAO.getInstance();
		this.accountId = accountId;
	}
	
	    /**
	     * @param databaseName
	     * @param Host
	     * @param databaseUsername
	     * @param databasePassword
	     * @param databasePort
	     */
	    public ParentPaginator(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
	        //initialize the DAOs
	    	commonUtils = new CommonUtils(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    	parentsDAO = new ParentsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    }
	
	 /**
    *
    * @return the first page
    */
   public ParentPage getFirstPage() {
	   ParentPage page = new ParentPage();
       List<StudentParent> parentList = parentsDAO.getParents(accountId, 0, PAGESIZE);
       page = new ParentPage(1, getTotalPage(), PAGESIZE, parentList);	    
       return page;
   }

   
   /**
    * Provides the last page
    *
    * @return	a parent page
    */
   public ParentPage getLastPage() {
	   
	   ParentPage page = new ParentPage();

       List<StudentParent> parentList = null;
       int  startIndex,sessionCount;
       int totalPage = getTotalPage();
       startIndex = (totalPage - 1) * PAGESIZE;
       sessionCount = commonUtils.getParentCount();
       parentList = parentsDAO.getParents(accountId, startIndex, sessionCount); 
       page = new ParentPage(totalPage, totalPage, PAGESIZE, parentList);
       return page;
   }
   

   /**
    * Moves you forward to the page of the Incoming session that comes
    * after the current page
    *
    * @param currentPage
    * @return	an parent page
    */
   public ParentPage getNextPage(final ParentPage currentPage) {
       int totalPage = getTotalPage();

       ParentPage page = new ParentPage();
       List<StudentParent> parentList = parentsDAO.getParents(accountId, currentPage.getPageNum() * PAGESIZE, 
       		((currentPage.getPageNum() * PAGESIZE) + PAGESIZE));

       page = new ParentPage(currentPage.getPageNum() + 1, totalPage, PAGESIZE, parentList);

       return page;
   }

   
   /**
    * Moves you backward to the page of the Incoming session that comes
    * before the current page
    *
    * @param currentPage
    * @return	an parent page
    */
   public ParentPage getPrevPage(final ParentPage currentPage) {
       int totalPage = getTotalPage();

       ParentPage page = new ParentPage();
       
       List<StudentParent> parentList = parentsDAO.getParents(accountId, (currentPage.getPageNum() - 2) * PAGESIZE, 
       		((currentPage.getPageNum() - 1) * PAGESIZE));

       page = new ParentPage(currentPage.getPageNum() - 1, totalPage, PAGESIZE, parentList);

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

	        //get the number of all sessions belonging to this email
	        totalSize = commonUtils.getParentCount();

	       //divide by the page size and add one to take care of remainders and what else?
	        return ((totalSize - 1) / PAGESIZE) + 1;
	    }
	    
		
}
