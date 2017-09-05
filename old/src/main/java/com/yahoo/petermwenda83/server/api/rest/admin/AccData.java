/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * @author peter
 *
 */
public class AccData {
	
	private String uuid;
	private String roll;
	private String pitch;
	private String yaw;
	private Timestamp addDate;
	
	/**
	 * 
	 */
	public AccData() {
		uuid = UUID.randomUUID().toString();
		roll = "";
		pitch = "";
		yaw = "";
		addDate = new Timestamp(new Date().getTime());
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRoll() {
		return roll;
	}

	public void setRoll(String roll) {
		this.roll = roll;
	}

	public String getPitch() {
		return pitch;
	}

	public void setPitch(String pitch) {
		this.pitch = pitch;
	}

	public String getYaw() {
		return yaw;
	}

	public void setYaw(String yaw) {
		this.yaw = yaw;
	}

	public Timestamp getAddDate() {
		return addDate;
	}

	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}

	@Override
	public String toString() {
		return "AccData [uuid=" + uuid + ", roll=" + roll + ", pitch=" + pitch + ", yaw=" + yaw + ", addDate=" + addDate
				+ "]";
	}

	

}
