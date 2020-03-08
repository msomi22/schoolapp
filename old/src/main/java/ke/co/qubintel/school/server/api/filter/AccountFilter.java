/**
 * 
 */
package ke.co.qubintel.school.server.api.filter;

import javax.ws.rs.QueryParam;

/**
 * @author peter
 *
 */
public class AccountFilter {
	
	private @QueryParam("isActive") String isActive;
	private @QueryParam("name") String name;
	private @QueryParam("start") int start;
	private @QueryParam("size") int size;
	
	
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
