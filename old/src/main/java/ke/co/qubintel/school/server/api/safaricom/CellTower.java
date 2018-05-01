package ke.co.qubintel.school.server.api.safaricom;
/**
 * 
 * @author peter
 *
 */
public class CellTower {
	
	private int cellId;
	private int locationAreaCode;
	private int mobileCountryCode;
	private int mobileNetworkCode;
	private int age;
	private int signalStrength;
	private int timingAdvance;

	public CellTower() {
		cellId = 0;
		locationAreaCode = 0;
		mobileCountryCode = 0;
		mobileNetworkCode = 0;
		age = 0;
		signalStrength = 0;
		timingAdvance = 0;
	}

	/**
	 * @return the cellId
	 */
	public int getCellId() {
		return cellId;
	}

	/**
	 * @param cellId the cellId to set
	 */
	public void setCellId(int cellId) {
		this.cellId = cellId;
	}

	/**
	 * @return the locationAreaCode
	 */
	public int getLocationAreaCode() {
		return locationAreaCode;
	}

	/**
	 * @param locationAreaCode the locationAreaCode to set
	 */
	public void setLocationAreaCode(int locationAreaCode) {
		this.locationAreaCode = locationAreaCode;
	}

	/**
	 * @return the mobileCountryCode
	 */
	public int getMobileCountryCode() {
		return mobileCountryCode;
	}

	/**
	 * @param mobileCountryCode the mobileCountryCode to set
	 */
	public void setMobileCountryCode(int mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}

	/**
	 * @return the mobileNetworkCode
	 */
	public int getMobileNetworkCode() {
		return mobileNetworkCode;
	}

	/**
	 * @param mobileNetworkCode the mobileNetworkCode to set
	 */
	public void setMobileNetworkCode(int mobileNetworkCode) {
		this.mobileNetworkCode = mobileNetworkCode;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the signalStrength
	 */
	public int getSignalStrength() {
		return signalStrength;
	}

	/**
	 * @param signalStrength the signalStrength to set
	 */
	public void setSignalStrength(int signalStrength) {
		this.signalStrength = signalStrength;
	}

	/**
	 * @return the timingAdvance
	 */
	public int getTimingAdvance() {
		return timingAdvance;
	}

	/**
	 * @param timingAdvance the timingAdvance to set
	 */
	public void setTimingAdvance(int timingAdvance) {
		this.timingAdvance = timingAdvance;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CellTower [cellId=" + cellId + ", locationAreaCode=" + locationAreaCode + ", mobileCountryCode="
				+ mobileCountryCode + ", mobileNetworkCode=" + mobileNetworkCode + ", age=" + age + ", signalStrength="
				+ signalStrength + ", timingAdvance=" + timingAdvance + "]";
	}

}
