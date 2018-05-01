/**
 * 
 */
package ke.co.qubintel.school.server.api.safaricom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peter
 *
 */
public class ISPLocRequest {
	private int homeMobileCountryCode;
	private int homeMobileNetworkCode;
	private String radioType;
	private String carrier;
	private String considerIp;
	private List<CellTower> cellTowers;

	/**
	 * 
	 */
	public ISPLocRequest() {
		homeMobileCountryCode = 0;
		homeMobileNetworkCode = 0;
		radioType = "";
		carrier = "";
		considerIp = "";
		cellTowers = new ArrayList<>();
	}

	/**
	 * @return the homeMobileCountryCode
	 */
	public int getHomeMobileCountryCode() {
		return homeMobileCountryCode;
	}

	/**
	 * @param homeMobileCountryCode the homeMobileCountryCode to set
	 */
	public void setHomeMobileCountryCode(int homeMobileCountryCode) {
		this.homeMobileCountryCode = homeMobileCountryCode;
	}

	/**
	 * @return the homeMobileNetworkCode
	 */
	public int getHomeMobileNetworkCode() {
		return homeMobileNetworkCode;
	}

	/**
	 * @param homeMobileNetworkCode the homeMobileNetworkCode to set
	 */
	public void setHomeMobileNetworkCode(int homeMobileNetworkCode) {
		this.homeMobileNetworkCode = homeMobileNetworkCode;
	}

	/**
	 * @return the radioType
	 */
	public String getRadioType() {
		return radioType;
	}

	/**
	 * @param radioType the radioType to set
	 */
	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}

	/**
	 * @return the carrier
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @param carrier the carrier to set
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return the considerIp
	 */
	public String getConsiderIp() {
		return considerIp;
	}

	/**
	 * @param considerIp the considerIp to set
	 */
	public void setConsiderIp(String considerIp) {
		this.considerIp = considerIp;
	}

	/**
	 * @return the cellTowers
	 */
	public List<CellTower> getCellTowers() {
		return cellTowers;
	}

	/**
	 * @param cellTowers the cellTowers to set
	 */
	public void setCellTowers(List<CellTower> cellTowers) {
		this.cellTowers = cellTowers;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ISPLocRequest [homeMobileCountryCode=" + homeMobileCountryCode + ", homeMobileNetworkCode="
				+ homeMobileNetworkCode + ", radioType=" + radioType + ", carrier=" + carrier + ", considerIp="
				+ considerIp + ", cellTowers=" + cellTowers + "]";
	}
	
}
