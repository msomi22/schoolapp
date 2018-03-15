/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "ApiClass") 
public class ApiClass {
	
	private String uuid;
	private String accountId;
	private String description;
	private String examSubNumber;

	/**
	 * 
	 */
	public ApiClass() {
		uuid = "";
		accountId = "";
		description = "";
		examSubNumber = "";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the examSubNumber
	 */
	public String getExamSubNumber() {
		return examSubNumber;
	}

	/**
	 * @param examSubNumber the examSubNumber to set
	 */
	public void setExamSubNumber(String examSubNumber) {
		this.examSubNumber = examSubNumber;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApiClass [uuid=" + uuid + ", accountId=" + accountId + ", description=" + description
				+ ", examSubNumber=" + examSubNumber + "]";
	}

	

}
