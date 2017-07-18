
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
package com.yahoo.petermwenda83.pagination.sponsor;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor;
import com.yahoo.petermwenda83.persistence.guardian.SponsorsDAO;
import com.yahoo.petermwenda83.persistence.utils.CommonUtils;


/** 
 * For Student pagination
 * 
 *  @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class SponsorPaginator {
	
	public final int PAGESIZE = 15;
	private static CommonUtils commonUtils;
	private static SponsorsDAO sponsorsDAO;
	/**
	 * 
	 */
	public SponsorPaginator() {
		commonUtils = CommonUtils.getInstance();
		sponsorsDAO = SponsorsDAO.getInstance();
	}
	
	    /**
	     * @param databaseName
	     * @param Host
	     * @param databaseUsername
	     * @param databasePassword
	     * @param databasePort
	     */
	    public SponsorPaginator(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
	        //initialize the DAOs
	    	commonUtils = new CommonUtils(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    	sponsorsDAO = new SponsorsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	    }
	
	 /**
    *
    * @return the first page
    */
   public SponsorPage getFirstPage() {
	   SponsorPage page = new SponsorPage();
       List<StudentSponsor> sponsorList = sponsorsDAO.getStudentSponsorList(0, PAGESIZE);
       page = new SponsorPage(1, getTotalPage(), PAGESIZE, sponsorList);	    
       return page;
   }

   
   /**
    * Provides the last page of the Incoming  session report
    *
    * @return	a Incoming sponsor page
    */
   public SponsorPage getLastPage() {
	   
	   SponsorPage page = new SponsorPage();

       List<StudentSponsor> sponsorList = null;
       int  startIndex,sessionCount;
       int totalPage = getTotalPage();
       startIndex = (totalPage - 1) * PAGESIZE;
       sessionCount = commonUtils.getSponsorCount();
       sponsorList = sponsorsDAO.getStudentSponsorList(startIndex, sessionCount); 
       page = new SponsorPage(totalPage, totalPage, PAGESIZE, sponsorList);
       return page;
   }
   

   /**
    * Moves you forward to the page of the Incoming  session that comes
    * after the current page
    *
    * @param currentPage
    * @return	an Incoming sponsor page
    */
   public SponsorPage getNextPage(final SponsorPage currentPage) {
       int totalPage = getTotalPage();

       SponsorPage page = new SponsorPage();
       List<StudentSponsor> sponsorList = sponsorsDAO.getStudentSponsorList(currentPage.getPageNum() * PAGESIZE, 
       		((currentPage.getPageNum() * PAGESIZE) + PAGESIZE));

       page = new SponsorPage(currentPage.getPageNum() + 1, totalPage, PAGESIZE, sponsorList);

       return page;
   }

   
   /**
    * Moves you backward to the page of the Incoming session that comes
    * before the current page
    *
    * @param currentPage
    * @return	an Incoming sponsor page
    */
   public SponsorPage getPrevPage(final SponsorPage currentPage) {
       int totalPage = getTotalPage();

       SponsorPage page = new SponsorPage();
       
       List<StudentSponsor> sponsorList = sponsorsDAO.getStudentSponsorList((currentPage.getPageNum() - 2) * PAGESIZE, 
       		((currentPage.getPageNum() - 1) * PAGESIZE));

       page = new SponsorPage(currentPage.getPageNum() - 1, totalPage, PAGESIZE, sponsorList);

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

	        //get the number of all sessions
	        totalSize = commonUtils.getSponsorCount();

	       //divide by the page size and add one to take care of remainders and what else?
	        return ((totalSize - 1) / PAGESIZE) + 1;
	    }
	    
		
}
