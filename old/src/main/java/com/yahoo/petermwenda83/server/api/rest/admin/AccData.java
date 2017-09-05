/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.admin;

/**
 * @author peter
 *
 */
public class AccData {
	
	private String Roll;
	private String Pitch;
	private String Yaw;
	
	/**
	 * 
	 */
	public AccData() {
		Roll = "";
		Pitch = "";
		Yaw = "";
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

	@Override
	public String toString() {
		return "AccData [Roll=" + Roll + ", Pitch=" + Pitch + ", Yaw=" + Yaw + "]";
	}
	
	

}
