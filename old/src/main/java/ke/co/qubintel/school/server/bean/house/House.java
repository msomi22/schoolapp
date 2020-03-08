/**
 * 
 */
package ke.co.qubintel.school.server.bean.house;

import ke.co.qubintel.school.server.bean.StorableBean;

/**
 * @author peter
 *
 */
public class House extends StorableBean{
	
	private String houseName;
	private String description;
	
	/**
	 * 
	 */
	public House() {
		houseName = "";
		description = "";
	}

	/**
	 * @return the houseName
	 */
	public String getHouseName() {
		return houseName;
	}

	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "House [houseName=" + houseName + ", description=" + description + ", getUuid()=" + getUuid()
				+ ", getAccountId()=" + getAccountId() + "]";
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 136372499995714300L;

}
