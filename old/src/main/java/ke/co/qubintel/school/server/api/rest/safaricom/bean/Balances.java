/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.safaricom.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "Balances") 
public class Balances{

	@JsonProperty
	private String WorkingAccount;
	@JsonProperty
	private String FloatAccount;
	@JsonProperty
	private String UtilityAccount;
	@JsonProperty
	private String ChargesPaidAccount;
	@JsonProperty
	private String OrganizationSettlementAccount;

	public Balances(){
		WorkingAccount = "";
		FloatAccount = "";
		UtilityAccount = "";
		ChargesPaidAccount = "";
		OrganizationSettlementAccount = "";
	}

	public String getWorkingAccount() {
		return WorkingAccount;
	}

	public void setWorkingAccount(String workingAccount) {
		WorkingAccount = workingAccount;
	}

	public String getFloatAccount() {
		return FloatAccount;
	}

	public void setFloatAccount(String floatAccount) {
		FloatAccount = floatAccount;
	}

	public String getUtilityAccount() {
		return UtilityAccount;
	}

	public void setUtilityAccount(String utilityAccount) {
		UtilityAccount = utilityAccount;
	}

	public String getChargesPaidAccount() {
		return ChargesPaidAccount;
	}

	public void setChargesPaidAccount(String chargesPaidAccount) {
		ChargesPaidAccount = chargesPaidAccount;
	}

	public String getOrganizationSettlementAccount() {
		return OrganizationSettlementAccount;
	}

	public void setOrganizationSettlementAccount(String organizationSettlementAccount) {
		OrganizationSettlementAccount = organizationSettlementAccount;
	}

	@Override
	public String toString() {
		return "Balances [WorkingAccount=" + WorkingAccount + ", FloatAccount=" + FloatAccount + ", UtilityAccount="
				+ UtilityAccount + ", ChargesPaidAccount=" + ChargesPaidAccount + ", OrganizationSettlementAccount="
				+ OrganizationSettlementAccount + "]";
	}



}

