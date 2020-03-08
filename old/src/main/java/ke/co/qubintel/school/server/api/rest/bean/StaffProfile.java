/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class StaffProfile {
	
	private String staffId; 
	private String accountId; 
	private String username;
	private String oldpassword;
	private String newpassword;
	private String cnewpassword;

	/**
	 * 
	 */
	public StaffProfile() {
		staffId = "";
		accountId = "";
		username = "";
		oldpassword = "";
		newpassword = "";
		cnewpassword = "";
	}

	/**
	 * @return the staffId
	 */
	public String getStaffId() {
		return staffId;
	}

	/**
	 * @param staffId the staffId to set
	 */
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the oldpassword
	 */
	public String getOldpassword() {
		return oldpassword;
	}

	/**
	 * @param oldpassword the oldpassword to set
	 */
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	/**
	 * @return the newpassword
	 */
	public String getNewpassword() {
		return newpassword;
	}

	/**
	 * @param newpassword the newpassword to set
	 */
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	/**
	 * @return the cnewpassword
	 */
	public String getCnewpassword() {
		return cnewpassword;
	}

	/**
	 * @param cnewpassword the cnewpassword to set
	 */
	public void setCnewpassword(String cnewpassword) {
		this.cnewpassword = cnewpassword;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StaffProfile [staffId=" + staffId + ", accountId=" + accountId + ", username=" + username
				+ ", oldpassword=" + oldpassword + ", newpassword=" + newpassword + ", cnewpassword=" + cnewpassword
				+ "]";
	}

}
