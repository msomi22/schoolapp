/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.admin;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "ApiAccData")
public class ApiAccData {
	
	private String uuid;
	private String Roll;
	private String Pitch;
	private String Yaw;
	private Timestamp addDate;

	/**
	 * 
	 */
	public ApiAccData() {
		uuid = "";
		Roll = "";
		Pitch = "";
		Yaw = "";
		addDate = new Timestamp(new Date().getTime());
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRoll() {
		return Roll;
	}

	public void setRoll(String roll) {
		Roll = roll;
	}

	public String getPitch() {
		return Pitch;
	}

	public void setPitch(String pitch) {
		Pitch = pitch;
	}

	public String getYaw() {
		return Yaw;
	}

	public void setYaw(String yaw) {
		Yaw = yaw;
	}

	public Timestamp getAddDate() {
		return addDate;
	}

	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}

	@Override
	public String toString() {
		return "ApiAccData [uuid=" + uuid + ", Roll=" + Roll + ", Pitch=" + Pitch + ", Yaw=" + Yaw + ", addDate="
				+ addDate + "]";
	}

	
	
}
