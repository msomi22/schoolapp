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
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.yahoo.petermwenda83.server.api.safaricom.SafaricomService.Balances;
import com.yahoo.petermwenda83.server.api.safaricom.SafaricomService.VCResponse;
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
 * http://localhost:8080/school/webapi/account/validation
 * http://localhost:8080/school/webapi/account/confirmation

 * @author peter
 *
 */
@Path("/account") 
@Api(value = "/account") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class SafaricomCallback {

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

		String workingBal = SafaricomService.getBalance(Working_Account,"Working_Account");
		String utilityBal = SafaricomService.getBalance(Utility_Account,"Utility_Account"); 
		String floatBal = SafaricomService.getBalance(Float_Account,"Float_Account");
		String cpaBal = SafaricomService.getBalance(Charges_Paid_Account,"Charges_Paid_Account");
		String osaBal = SafaricomService.getBalance(Organization_Settlement_Account,"Organization_Settlement_Account");

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

	
	

	@ApiOperation(value = "Validation Response From Safaricom MPESA.", 
			notes = "Response Message.", 
			response = VCResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Resource not found.") 
	} )

	//validation and confirmation URLs on M-Pesa 
	@POST
	@Path("/{validation}") 
	public VCResponse validationURL(@PathParam("validation") String validation, VCResponse vresponse) {
		
		Gson gson = new Gson();
		String jsonObject = gson.toJson(vresponse);  
		VCResponse response = gson.fromJson(jsonObject, VCResponse.class);
		
		response.getBillRefNumber();
		response.getBusinessShortCode();
		response.getInvoiceNumber();
		response.getMSISDN();
		response.getOrgAccountBalance();//important
		response.getThirdPartyTransID();
		response.getTransactionType();
		response.getTransAmount();
		response.getTransID();
		response.getTransTime();
		
		response.getFirstName();
		response.getLastName();
		response.getMiddleName();
		
		//put into the DB

		System.out.println(vresponse + " " + validation); 

		return vresponse;
	}
	
	
	

	@ApiOperation(value = "Confirmation Response From Safaricom MPESA.", 
			notes = "Response Message.", 
			response = VCResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Resource not found.") 
	} )

	//validation and confirmation URLs on M-Pesa 
	@POST
	@Path("/{confirmation}") 
	public VCResponse confirmationURL(@PathParam("confirmation") String confirmation,VCResponse cresponse) {
		
		Gson gson = new Gson();
		String jsonObject = gson.toJson(cresponse);  
		VCResponse response = gson.fromJson(jsonObject, VCResponse.class);
		
		response.getBillRefNumber();
		response.getBusinessShortCode();
		response.getInvoiceNumber();
		response.getMSISDN();
		response.getOrgAccountBalance();//important
		response.getThirdPartyTransID();
		response.getTransactionType();
		response.getTransAmount();
		response.getTransID();
		response.getTransTime();
		
		response.getFirstName();
		response.getLastName();
		response.getMiddleName();
		
		//update DB
		
		
        
		System.out.println(response + " " + confirmation); 

		return cresponse;
	}



	
	


}
