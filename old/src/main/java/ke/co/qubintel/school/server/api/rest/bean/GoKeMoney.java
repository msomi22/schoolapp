/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "GoKeMoney")
public class GoKeMoney {
	
	private String studentId;
	private String accountId;

	/**
	 * 
	 */
	public GoKeMoney() {
		studentId = "";
		accountId = "";
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "GoKeMoney [studentId=" + studentId + ", accountId=" + accountId + "]";
	}

}
