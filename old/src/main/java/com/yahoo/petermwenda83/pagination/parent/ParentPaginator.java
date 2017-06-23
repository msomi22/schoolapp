
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
package com.yahoo.petermwenda83.pagination.parent;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.utils.CommonUtils;

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
	/**
	 * 
	 */
	public ParentPaginator() {
		commonUtils = CommonUtils.getInstance();
		parentsDAO = ParentsDAO.getInstance();
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
       List<StudentParent> parentList = parentsDAO.getParentList(0, PAGESIZE);
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
       parentList = parentsDAO.getParentList(startIndex, sessionCount); 
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
       List<StudentParent> parentList = parentsDAO.getParentList(currentPage.getPageNum() * PAGESIZE, 
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
       
       List<StudentParent> parentList = parentsDAO.getParentList( (currentPage.getPageNum() - 2) * PAGESIZE, 
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
