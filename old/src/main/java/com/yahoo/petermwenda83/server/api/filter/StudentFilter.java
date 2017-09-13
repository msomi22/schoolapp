/**
 * 
 */
package com.yahoo.petermwenda83.server.api.filter;

import javax.ws.rs.QueryParam;

/**
 * @author peter
 *
 */
public class StudentFilter {

	
	private @QueryParam("currentStream") String currentStream;
	private @QueryParam("query") String query;
	private @QueryParam("start") int start;
	private @QueryParam("size") int size;
	public String getCurrentStream() {
		return currentStream;
	}
	public void setCurrentStream(String currentStream) {
		this.currentStream = currentStream;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	

}
