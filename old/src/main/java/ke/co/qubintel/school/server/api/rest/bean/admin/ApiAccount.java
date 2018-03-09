/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean.admin;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "ApiAccount") 
public class ApiAccount{
	
	private String uuid;
	private String isActive;
	private String name;
	private String motto;
	private String website;
	private String logo;
	private String signature;
	private String username ;
	private String password;
	private String mobile;
	private String email;
	private String address;
	private String town;
	private String isBoarding;
	private String isMixed;
	private String lastUpdated;
	private String creationDate;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2784181069116126865L;

	/**
	 * 
	 */
	public ApiAccount() {
		uuid = "";
		isActive = "";//1=active , 0 = inactive
		name = "";
		motto = "";
		website = "";
		logo = "";
		signature = "";
		username = "";
		password = "";
		mobile = "";
		email = "";
		address = "";
		town = "";
		isBoarding = "";//1 = boarding only, 0 = day only, 2 = day and boarding 
		isMixed = "";// 1= yes , 0 = no
		lastUpdated = "";
		creationDate = "";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getIsBoarding() {
		return isBoarding;
	}

	public void setIsBoarding(String isBoarding) {
		this.isBoarding = isBoarding;
	}

	public String getIsMixed() {
		return isMixed;
	}

	public void setIsMixed(String isMixed) {
		this.isMixed = isMixed;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ApiAccount [uuid=" + uuid + ", isActive=" + isActive + ", name=" + name + ", motto=" + motto
				+ ", website=" + website + ", logo=" + logo + ", signature=" + signature + ", username=" + username
				+ ", password=" + password + ", mobile=" + mobile + ", email=" + email + ", address=" + address
				+ ", town=" + town + ", isBoarding=" + isBoarding + ", isMixed=" + isMixed + ", lastUpdated="
				+ lastUpdated + ", creationDate=" + creationDate + "]";
	}

	
}
