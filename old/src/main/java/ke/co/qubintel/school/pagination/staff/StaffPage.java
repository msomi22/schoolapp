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
package ke.co.qubintel.school.pagination.staff;

import java.util.ArrayList;
import java.util.List;

import ke.co.qubintel.school.server.bean.staff.Staff;


/** 
 * For staff pagination 
 * 
 *  @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StaffPage {
	
	private int pageNum;
	private int totalPage;
	private int pagesize;
    private List<Staff> contents;

	/**
	 * 
	 */
	public StaffPage() {
		pageNum = 1;
		totalPage = 1;
		pagesize = 1;
		contents = new ArrayList<>();
	}
   
	
	public StaffPage(final int pageNum, final int totalPage, final int pagesize,
			final List<Staff> contents) {
		this.pageNum = pageNum;
		this.totalPage = totalPage;
		this.pagesize = pagesize;
		this.contents = contents;
	}
	
	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return the pagesize
	 */
	public int getPagesize() {
		return pagesize;
	}

	/**
	 * @param pagesize the pagesize to set
	 */
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	/**
	 * @return the contents
	 */
	public List<Staff> getContents() {
		return new ArrayList<>(contents);
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(List<Staff> contents) {
		this.contents = contents;
	}
	 
	

    /**
     *
     * @return		<code>true</code> if this is the first page; <code>false</code>
     * for otherwise
     */
	
	public boolean isFirstPage() {
	        return pageNum == 1;
	    }

	    /**
	     *
	     * @return		<code>true</code> if this is the last page; <code>false</code>
	     * for otherwise
	     */
	    public boolean isLastPage() {
	        return pageNum == totalPage;
	    }

}
