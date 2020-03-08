/**
 * 
 */
package ke.co.qubintel.school.server.api.filter;

import javax.ws.rs.QueryParam;

/**
 * @author peter
 *
 */
public class StudentFilter {

	
	private @QueryParam("currentStream") String currentStream;
	private @QueryParam("query") String query;
	private @QueryParam("limit") int limit;
	private @QueryParam("offset") int offset;
	
	
	/**
	 * @return the currentStream
	 */
	public String getCurrentStream() {
		return currentStream;
	}
	/**
	 * @param currentStream the currentStream to set
	 */
	public void setCurrentStream(String currentStream) {
		this.currentStream = currentStream;
	}
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	
	

}
