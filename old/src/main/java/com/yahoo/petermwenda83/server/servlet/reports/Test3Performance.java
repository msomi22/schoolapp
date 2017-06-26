/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.util.ArrayList;
import java.util.List;

import com.yahoo.petermwenda83.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public class Test3Performance {
	
	private int total;
	private List<Perfomance> perfomanceList;

	/**
	 * 
	 */
	public Test3Performance() {
		total = 0;
		perfomanceList = new ArrayList<>();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Perfomance> getPerfomanceList() {
		return perfomanceList;
	}

	public void setPerfomanceList(List<Perfomance> perfomanceList) {
		this.perfomanceList = perfomanceList;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Test3Performance [total=" + total + ", perfomanceList=" + perfomanceList + "]";
	}

	
}
