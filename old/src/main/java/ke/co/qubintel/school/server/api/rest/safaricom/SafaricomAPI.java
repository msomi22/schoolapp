/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.safaricom;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

import ke.co.qubintel.school.server.api.rest.safaricom.bean.Balances;
import ke.co.qubintel.school.server.api.rest.safaricom.bean.ResultParameter;
import ke.co.qubintel.school.server.api.rest.safaricom.bean.ResultParameters;
import ke.co.qubintel.school.server.api.rest.safaricom.bean.SafResponse;
import ke.co.qubintel.school.server.api.rest.safaricom.bean.SimulateRequest;
import ke.co.qubintel.school.server.api.rest.safaricom.bean.VCResponse;
import ke.co.qubintel.school.server.api.rest.util.JsonFromObj;
import ke.co.qubintel.school.server.servlet.util.email.EmailUtil;

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
public class SafaricomAPI {

	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Simulate MPESA C2B Request.", 
			notes = "Response Message.", 
			response = SimulateRequest.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Account Id not found.") 
	} )
	@POST
	@Path("/simulate")  
	@Produces(value = {MediaType.APPLICATION_JSON})  
	public Object simulateRequest() {

		String consumer_key = "Rwqrrjj4wV2UhgMZYtLMF2X8SQhci6TV";
		String consumer_secret = "GeGSs75GrGGa5zAv"; 

		SafaricomService.registerURLS(consumer_key,consumer_secret);

		String res = SafaricomService.SimulateRequest(consumer_key,consumer_secret);

		return res;
	}


	/**
	 * 
	 * @param object
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Log Response From Safaricom MPESA.", 
			notes = "Response Message.", 
			response = SafResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Account Id not found.") 
	} )

	@POST
	@Path("/balance") 
	@Produces(value = {MediaType.APPLICATION_JSON})  
	public Balances getAcctBalResponse(SafResponse object, @HeaderParam("authorization") String auth) {

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
	@Path("/timeout") 
	public SafResponse getAcctBalTimeoutResponse(SafResponse object) {

		System.out.println(object + " ---  timeout");  

		return object;
	}




	@ApiOperation(value = "Validation Response From Safaricom MPESA.", 
			notes = "Response Message.", 
			response = VCResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Resource not found.") 
	} )

	//validation and confirmation URLs on M-Pesa 
	@POST
	@Path("/validation") 
	public String validationURL(VCResponse vresponse) {

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

		System.out.println(vresponse + "  validation"); 

		Response MPESAresponse = new Response();
		String TOMPESA = JsonFromObj.getJsonStringFromObject(MPESAresponse); 

		//TOMPESA = TOMPESA+";";

		System.out.println();
		System.out.println(TOMPESA); 


		return TOMPESA;
	}

	@ApiOperation(value = "Confirmation Response From Safaricom MPESA.", 
			notes = "Response Message.", 
			response = VCResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Resource not found.") 
	} )

	//validation and confirmation URLs on M-Pesa 
	@POST
	@Path("/confirmation")  
	public String confirmationURL(VCResponse cresponse) {

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



		System.out.println(response + "  confirmation"); 
		sendEmail(response);

		return jsonObject;
	}

	/**
	 * 
	 * @param response
	 */
	private void sendEmail(VCResponse response) {
		
		final String OUT_E_SERVER ="mail.adcea.com"; 
		final int OUT_E_PORT = 143;
		final String FROM ="peter.mwenda@adcea.com";
		
		final String SUBJECT = "MPESA EMAIL ALERTS"; 
		
		
		String BODY = "Hello there, MPESA response is : " + response;   
		
		String[] emailsTo = {"mwendapeter72@gmail.com", "cornewabwile@gmail.com "}; 
		
		for(int i=0;i<emailsTo.length;i++) {
			
			EmailUtil util = new EmailUtil(FROM, emailsTo[i], SUBJECT, BODY, OUT_E_SERVER, OUT_E_PORT,
					FROM, "w3nd@@dc");  
			
			util.run();
		}

	}
	




	/**
	 * 
	 * @author peter
	 *
	 */
	class Response{

		@JsonProperty
		private String ResponseCode;
		@JsonProperty
		private String ResponseDesc;

		public Response() {
			ResponseCode = "00000000";
			ResponseDesc = "success";
		}

		public String getResponseCode() {
			return ResponseCode;
		}

		public void setResponseCode(String responseCode) {
			ResponseCode = responseCode;
		}

		public String getResponseDesc() {
			return ResponseDesc;
		}

		public void setResponseDesc(String responseDesc) {
			ResponseDesc = responseDesc;
		}

		@Override
		public String toString() {
			return "Response [ResponseCode=" + ResponseCode + ", ResponseDesc=" + ResponseDesc + "]";
		}

	}





}
