/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peter
 *
 */
public class StudentPage {
	
	private int pageSize;
	private int pages;
	private int total;
	private int currentPage; 
	private List<StudentInfo> contents;

	/**
	 * 
	 */
	public StudentPage() {
		pageSize = 0;
		pages = 0;
		total = 0;
		currentPage = 0;
		contents = new ArrayList<StudentInfo>();
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pages
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(int pages) {
		this.pages = pages;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the contents
	 */
	public List<StudentInfo> getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(List<StudentInfo> contents) {
		this.contents = contents;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentPage [pageSize=" + pageSize + ", pages=" + pages + ", total=" + total + ", currentPage="
				+ currentPage + ", contents=" + contents + "]";
	}

}
