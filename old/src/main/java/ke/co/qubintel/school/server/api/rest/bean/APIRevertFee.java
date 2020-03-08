/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class APIRevertFee {
	
	private String otherFeeId;
	private String description;
	private String amount;
	private String dateReverted;

	/**
	 * 
	 */
	public APIRevertFee() {
		otherFeeId = "";
		description = "";
		amount = "";
		dateReverted = "";
	}

	public String getOtherFeeId() {
		return otherFeeId;
	}

	public void setOtherFeeId(String otherFeeId) {
		this.otherFeeId = otherFeeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDateReverted() {
		return dateReverted;
	}

	public void setDateReverted(String dateReverted) {
		this.dateReverted = dateReverted;
	}

	@Override
	public String toString() {
		return "APIRevertFee [otherFeeId=" + otherFeeId + ", description=" + description + ", amount=" + amount
				+ ", dateReverted=" + dateReverted + "]";
	}

	

}
