/**
 * 
 */
package com.yahoo.petermwenda83.server.api.safaricom;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.yahoo.petermwenda83.server.api.safaricom.bean.ResultParameter;
import com.yahoo.petermwenda83.server.api.safaricom.bean.ResultParameters;
import com.yahoo.petermwenda83.server.api.safaricom.bean.SafResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

/** 
 * http://localhost:8080/school/webapi/account/balance
 * http://localhost:8080/school/webapi/account/timeout
 * 
 * @author peter
 *
 */
@Path("/account") 
@Api(value = "/account") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class Callback {

	@ApiOperation(value = "Log Response From Safaricom MPESA.", 
			notes = "Response Message.", 
			response = SafResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Account Id not found.") 
	} )

	@POST
	@Path("/{balance}") 
	@Produces(value = {MediaType.APPLICATION_JSON})  
	public Balances getAcctBalResponse(@PathParam("balance") String balance,SafResponse object) {

		Gson gson = new Gson();
		String jsonObject = gson.toJson(object); 
		SafResponse safResponse = gson.fromJson(jsonObject, SafResponse.class);
		Result result = safResponse.getResult();
		ResultParameters ResultParameters = result.getResultParameters();

		List<ResultParameter> resultParameter = ResultParameters.getResultParameter();

		String acc_balance = resultParameter.parallelStream()
				.filter(resultP -> StringUtils.equalsIgnoreCase("AccountBalance", resultP.getKey()))
				.map(ResultParameter::getValue)
				.findAny()
				.orElse("");

		String[] parts = acc_balance.split("&");
		String Working_Account = parts[0]; //working is for sending
		String Float_Account = parts[1];
		String Utility_Account = parts[2];//Utility is for receiving funds
		String Charges_Paid_Account = parts[3];  
		String Organization_Settlement_Account = parts[4];  
		
		String workingBal = getBalance(Working_Account,"Working_Account");
		String utilityBal = getBalance(Utility_Account,"Utility_Account"); 
		String floatBal = getBalance(Float_Account,"Float_Account");
		String cpaBal = getBalance(Charges_Paid_Account,"Charges_Paid_Account");
		String osaBal = getBalance(Organization_Settlement_Account,"Organization_Settlement_Account");

		Balances balances = new Balances();
		balances.setChargesPaidAccount(cpaBal);
		balances.setFloatAccount(floatBal);
		balances.setOrganizationSettlementAccount(osaBal);
		balances.setUtilityAccount(utilityBal);
		balances.setWorkingAccount(workingBal);
		
		System.out.println(balances); 
		
		return balances; 
	}





	@ApiOperation(value = "Log Timeout Response From Safaricom MPESA.", 
			notes = "Response Message.", 
			response = SafResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Account Id not found.") 
	} )

	@POST
	@Path("/{timeout}") 
	public SafResponse getAcctBalTimeoutResponse(@PathParam("timeout") String timeout, SafResponse object) {

		System.out.println(object + " --- " + timeout);  

		return object;
	}

	
	/**
	 * 
	 * @param accountBalance
	 * @param balType
	 * @return
	 */
	private String getBalance(String accountBalance, String balType) {

		String[] balanceParts = {};
		String bal = "";
		String currency = "";
		String balance = "";


		switch(balType) {
		case "Working_Account":
			balanceParts = accountBalance.split("\\|");  
			if(StringUtils.equalsIgnoreCase(balanceParts[0], "Working Account")) {
				currency = balanceParts[1];
				bal = balanceParts[2]; 
				balance = currency + " " + bal; 
				return balance;
			}else {
				return "Balance Unkown"; 
			}

		case "Utility_Account":
			balanceParts = accountBalance.split("\\|");  
			if(StringUtils.equalsIgnoreCase(balanceParts[0], "Utility Account")) {
				currency = balanceParts[1];
				bal = balanceParts[2]; 
				balance = currency + " " + bal; 
				return balance;
			}else {
				return "Balance Unkown"; 
			}


		case "Float_Account":
			balanceParts = accountBalance.split("\\|");  
			if(StringUtils.equalsIgnoreCase(balanceParts[0], "Float Account")) {
				currency = balanceParts[1];
				bal = balanceParts[2]; 
				balance = currency + " " + bal; 
				return balance;
			}else {
				return "Balance Unkown"; 
			}


		case "Charges_Paid_Account":
			balanceParts = accountBalance.split("\\|");  
			if(StringUtils.equalsIgnoreCase(balanceParts[0], "Charges Paid Account")) {
				currency = balanceParts[1];
				bal = balanceParts[2]; 
				balance = currency + " " + bal; 
				return balance;
			}else {
				return "Balance Unkown"; 
			}
			

		case "Organization_Settlement_Account":
			balanceParts = accountBalance.split("\\|");  
			if(StringUtils.equalsIgnoreCase(balanceParts[0], "Organization Settlement Account")) {
				currency = balanceParts[1];
				bal = balanceParts[2]; 
				balance = currency + " " + bal; 
				return balance;
			}else {
				return "Balance Unkown"; 
			}


		default:
			return "Balance Unkown";

		}

	}



	@XmlRootElement(name = "Balances") 
	class Balances{

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


}
