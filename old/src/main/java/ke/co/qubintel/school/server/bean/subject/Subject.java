
package ke.co.qubintel.school.server.bean.subject;

import ke.co.qubintel.school.server.bean.StorableBean;

/** 
 * A subject in a Account
 * 
 *  @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 * 
 */
public class Subject extends StorableBean {
	
	  private String categoryId;
	  private String code;
	  private String numericCode;
	  private String description;
	 
	/**
	 * 
	 */
	public Subject() {
		super();
		categoryId = "";
		code = "";
		numericCode = "";
		description = "";
	}
	

	  /**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}


	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the numericCode
	 */
	public String getNumericCode() {
		return numericCode;
	}


	/**
	 * @param numericCode the numericCode to set
	 */
	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
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
		return "Subject [categoryId=" + categoryId + ", code=" + code + ", numericCode=" + numericCode
				+ ", description=" + description + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId()
				+ "]";
	}



	private static final long serialVersionUID = 1L;

}
