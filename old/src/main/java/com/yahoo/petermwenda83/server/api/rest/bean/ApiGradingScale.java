/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class ApiGradingScale {
	
	private String uuid;
	private String categoryId;
	private int lowerLimit;
	private int upperLimit;
	private String description;
	private int points;

	/**
	 * 
	 */
	public ApiGradingScale() {
		uuid = "";
		categoryId = "";
		lowerLimit = 0;
		upperLimit = 0;
		description = "";
		points = 0;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public int getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(int lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public int getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "ApiGradingScale [uuid=" + uuid + ", categoryId=" + categoryId + ", lowerLimit=" + lowerLimit
				+ ", upperLimit=" + upperLimit + ", description=" + description + ", points=" + points + "]";
	}

}
