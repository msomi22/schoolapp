/**
 * 
 */
package com.yahoo.petermwenda83.server.api.safaricom;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.yahoo.petermwenda83.server.api.rest.JsonFromObj;
import com.yahoo.petermwenda83.server.api.rest.bean.SubClass;
import com.yahoo.petermwenda83.server.api.safaricom.bean.AccountBalance;

/**
 * @author peter
 *
 */
public class SafaricomService {
	
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String SimulateRequest(String username,String password) {
		
		String url = "https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate";
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		SimulateRequest simulateRequest = new SimulateRequest();
		
		String query = JsonFromObj.getJsonStringFromObject(simulateRequest); 
		
		url = "https://sandbox.safaricom.co.ke/oauth/v1/generate";
		String authEncoded = getAccessToken(url,username,password);
		
		// POST method
        ClientResponse response = webResource
        		                    .accept("application/json")	
        		                    .header("Authorization", "Bearer " + authEncoded)
                                    .type("application/json")
                                    .post(ClientResponse.class, query);

		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);
		
		return output;
	}
	
	
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String registerURLS(String username,String password) {
		String url = "https://sandbox.safaricom.co.ke/mpesa/c2b/v1/registerurl";
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		
		RegisterURL registerURL = new RegisterURL();
		String query = JsonFromObj.getJsonStringFromObject(registerURL); 
		
		url = "https://sandbox.safaricom.co.ke/oauth/v1/generate";
		String authEncoded = getAccessToken(url,username,password);

		 // POST method
        ClientResponse response = webResource
        		                    .accept("application/json")	
        		                    .header("Authorization", "Bearer " + authEncoded)
                                    .type("application/json")
                                    .post(ClientResponse.class, query);

		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);
		
		return output;
	}
	
	


	
/**
 * 
 * @param url Safariocm Account Balance Resource URL
 * @param username Consumer Key as provided by Safaricom
 * @param password Consumer Secret as provided by Safaricom
 * @return Result in form of JSON 
 */
	public static String getBalance(String url,String username,String password) {

		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		
		/**
		 * Base64 encoded string of the:
		 *  a) M-Pesa short code and 
		 *  b) password, 
		 *  which is encrypted using M-Pesa public key and validates the transaction on M-Pesa Core system.
		 */
		String SecurityCredential = "pSSoqpsVhavqrOzCLSeZ7T7R80tVAc75Y+tpwnqAKg6yd00vXKjS7wz/4/K8kPCSlrgtcfoTpTYXKPpnTnguKD7hYLqxlPiGniNAYKU57yAm6PRXhoYMy/Evz946uomKVhmiFwxZFH4HnGr/w7tImMIqQQK1/F8KD7XC4PUTG36T6Es3I+20H8wCeiyqUmmU0/WlD2502jAdwOH4543VEujiuagZO2uVgHWnJzfllioKPRhmXblR7yDAYGhpq4vl/G/k1zGzg3sznj3tKkiqIxc/aSnAHoaS37crHHrarxbOwtedqErSK9aeXTq13CM3BDMVobZ1SdNTWYUDb2mN7w==";
       
		AccountBalance balance = new AccountBalance();
		balance.setInitiator("testapi0321");
		balance.setSecurityCredential(SecurityCredential); 
		balance.setCommandID("AccountBalance"); 
		balance.setPartyA("600321");
		balance.setIdentifierType("4");
		balance.setRemarks("Cheking Balance");
		String domain = "http://3782cd77.ngrok.io";
		balance.setQueueTimeOutURL(domain+"/school/webapi/account/timeout");
		balance.setResultURL(domain+"/school/webapi/account/balance");
		
		String query = JsonFromObj.getJsonStringFromObject(balance); 
		
		System.out.println(query);
		
		url = "https://sandbox.safaricom.co.ke/oauth/v1/generate";
		String authEncoded = getAccessToken(url,username,password);

		 // POST method
        ClientResponse response = webResource
        		                    .accept("application/json")	
        		                    .header("Authorization", "Bearer " + authEncoded)
                                    .type("application/json")
                                    .post(ClientResponse.class, query);

		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);

		return output;
	}


	/**
	 * 
	 * @param url Safaricom URL for generating access token
	 * @param username Consumer Key as provided by Safaricom
	 * @param password Consumer Secret as provided by Safaricom
	 * @return Auth2 access token 
	 */
	public static String getAccessToken(String url,String username,String password) {
	
		String authEncoded = getAuthBase64(username,password);
		
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		ClientResponse resp = webResource.queryParam("grant_type", "client_credentials")
				.accept("application/json")
				.header("Authorization", "Basic " + authEncoded)
				.get(ClientResponse.class);


		if(resp.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = resp.getEntity(String.class);
		
		Gson g = new Gson(); 
		Token token = g.fromJson(output, Token.class);
		
		return token.getAccess_token(); 
	}
	
	/**
	 *
	 * @param username Consumer Key as provided by Safaricom
	 * @param password Consumer Secret as provided by Safaricom
	 * @return a base64 encoded String
	 */
	public static String getAuthBase64(String username,String password) {
		String authEncoded = "";
		String auth = username + ":" + password;
		try {

			authEncoded = Base64.getEncoder().encodeToString(auth.getBytes("utf-8")); 

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return authEncoded;
	}
	
	
	
	/**
	 * 
	 * @param accountBalance
	 * @param balType
	 * @return
	 */
	public static String getBalance(String accountBalance, String balType) {

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

	
	
	/**
	 * 
	 * @author peter
	 *
	 */
	static class Token{
		private String access_token;
		private String expires_in;
		
		public Token(){
			access_token = "";
			expires_in = "";
		}

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public String getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(String expires_in) {
			this.expires_in = expires_in;
		}

		@Override
		public String toString() {
			return "Token [access_token=" + access_token + ", expires_in=" + expires_in + "]";
		}
		
	}
	
	
	
	@XmlRootElement(name = "Balances") 
	static class Balances{

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
	
	
	/**
	 * 
	 * @author peter
	 *
	 */
	static class RegisterURL{
		private String ShortCode;
		private String ResponseType;
		private String ConfirmationURL;
		private String ValidationURL;
		
		public RegisterURL(){
			ShortCode = "600321";
			ResponseType = "Completed";
			ConfirmationURL = "http://3782cd77.ngrok.io/school/webapi/account/confirmation";
			ValidationURL = "http://3782cd77.ngrok.io/school/webapi/account/validation";
		}

		public String getShortCode() {
			return ShortCode;
		}

		public void setShortCode(String shortCode) {
			ShortCode = shortCode;
		}

		public String getResponseType() {
			return ResponseType;
		}

		public void setResponseType(String responseType) {
			ResponseType = responseType;
		}

		public String getConfirmationURL() {
			return ConfirmationURL;
		}

		public void setConfirmationURL(String confirmationURL) {
			ConfirmationURL = confirmationURL;
		}

		public String getValidationURL() {
			return ValidationURL;
		}

		public void setValidationURL(String validationURL) {
			ValidationURL = validationURL;
		}

		@Override
		public String toString() {
			return "RegisterURL [ShortCode=" + ShortCode + ", ResponseType=" + ResponseType
					+ ", ConfirmationURL=" + ConfirmationURL + ", ValidationURL=" + ValidationURL + "]";
		}
		
	}
	
	
	static class SimulateRequest{
		private String ShortCode;
		private String CommandID;
		private String Amount;
		private String Msisdn;
		private String BillRefNumber;
		
		public SimulateRequest() {
			ShortCode = "600321";
		    CommandID = "CustomerPayBillOnline";
		    Amount = "1000";
		    Msisdn = "254708374149";
		    BillRefNumber = "xxx";
		}

		public String getShortCode() {
			return ShortCode;
		}

		public void setShortCode(String shortCode) {
			ShortCode = shortCode;
		}

		public String getCommandID() {
			return CommandID;
		}

		public void setCommandID(String commandID) {
			CommandID = commandID;
		}

		public String getAmount() {
			return Amount;
		}

		public void setAmount(String amount) {
			Amount = amount;
		}

		public String getMsisdn() {
			return Msisdn;
		}

		public void setMsisdn(String msisdn) {
			Msisdn = msisdn;
		}

		public String getBillRefNumber() {
			return BillRefNumber;
		}

		public void setBillRefNumber(String billRefNumber) {
			BillRefNumber = billRefNumber;
		}

		@Override
		public String toString() {
			return "SimulateRequest [ShortCode=" + ShortCode + ", CommandID=" + CommandID + ", Amount=" + Amount
					+ ", Msisdn=" + Msisdn + ", BillRefNumber=" + BillRefNumber + "]";
		}
		
	}
	
	
	
	
	@XmlRootElement(name = "VCResponse") 
	static class VCResponse{
		@JsonProperty
		private String TransactionType;
		@JsonProperty
		private String TransID;
		@JsonProperty
		private String TransTime;
		@JsonProperty
		private String TransAmount;
		@JsonProperty
		private String BusinessShortCode;
		@JsonProperty
		private String BillRefNumber;
		@JsonProperty
		private String InvoiceNumber;
		@JsonProperty
		private String OrgAccountBalance;
		@JsonProperty
		private String ThirdPartyTransID;
		@JsonProperty
		private String MSISDN;
		@JsonProperty
		private String FirstName;
		@JsonProperty
		private String MiddleName;
		@JsonProperty
		private String LastName;

		public VCResponse() {
			TransactionType = "";
			TransID = "";
			TransTime = "";
			TransAmount = "";
			BusinessShortCode = "";
			BillRefNumber = "";
			InvoiceNumber = "";
			OrgAccountBalance = "";
			ThirdPartyTransID = "";
			MSISDN = "";
			FirstName = "";
			MiddleName = "";
			LastName = "";

		}

		public String getTransactionType() {
			return TransactionType;
		}

		public void setTransactionType(String transactionType) {
			TransactionType = transactionType;
		}

		public String getTransID() {
			return TransID;
		}

		public void setTransID(String transID) {
			TransID = transID;
		}

		public String getTransTime() {
			return TransTime;
		}

		public void setTransTime(String transTime) {
			TransTime = transTime;
		}

		public String getTransAmount() {
			return TransAmount;
		}

		public void setTransAmount(String transAmount) {
			TransAmount = transAmount;
		}

		public String getBusinessShortCode() {
			return BusinessShortCode;
		}

		public void setBusinessShortCode(String businessShortCode) {
			BusinessShortCode = businessShortCode;
		}

		public String getBillRefNumber() {
			return BillRefNumber;
		}

		public void setBillRefNumber(String billRefNumber) {
			BillRefNumber = billRefNumber;
		}

		public String getInvoiceNumber() {
			return InvoiceNumber;
		}

		public void setInvoiceNumber(String invoiceNumber) {
			InvoiceNumber = invoiceNumber;
		}

		public String getOrgAccountBalance() {
			return OrgAccountBalance;
		}

		public void setOrgAccountBalance(String orgAccountBalance) {
			OrgAccountBalance = orgAccountBalance;
		}

		public String getThirdPartyTransID() {
			return ThirdPartyTransID;
		}

		public void setThirdPartyTransID(String thirdPartyTransID) {
			ThirdPartyTransID = thirdPartyTransID;
		}

		public String getMSISDN() {
			return MSISDN;
		}

		public void setMSISDN(String mSISDN) {
			MSISDN = mSISDN;
		}

		public String getFirstName() {
			return FirstName;
		}

		public void setFirstName(String firstName) {
			FirstName = firstName;
		}

		public String getMiddleName() {
			return MiddleName;
		}

		public void setMiddleName(String middleName) {
			MiddleName = middleName;
		}

		public String getLastName() {
			return LastName;
		}

		public void setLastName(String lastName) {
			LastName = lastName;
		}

		@Override
		public String toString() {
			return "VCResponse [TransactionType=" + TransactionType + ", TransID=" + TransID + ", TransTime="
					+ TransTime + ", TransAmount=" + TransAmount + ", BusinessShortCode=" + BusinessShortCode
					+ ", BillRefNumber=" + BillRefNumber + ", InvoiceNumber=" + InvoiceNumber + ", OrgAccountBalance="
					+ OrgAccountBalance + ", ThirdPartyTransID=" + ThirdPartyTransID + ", MSISDN=" + MSISDN
					+ ", FirstName=" + FirstName + ", MiddleName=" + MiddleName + ", LastName=" + LastName + "]";
		}
		
	}

	
	
	
	/**
	 * API TEST CODE 
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public static String schoolTest(String url,String username,String password) {

		String authEncoded = getAuthBase64(username,password);

		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		
		SubClass subclass = new SubClass();
		subclass.setAccountId("E3CDC578-37BA-4CDB-B150-DAB0409270CD");
		subclass.setStreamId("D3733507-C113-4795-91ED-D3CD8039EA03");
		subclass.setSubjectId("F1972BF2-C788-4F41-94FE-FBA1869C92BC");
		subclass.setTeacherId("5498156A-FE83-43F4-9592-737HDHJ877S"); 


		String query = JsonFromObj.getJsonStringFromObject(subclass); 

        // POST method
        ClientResponse response = webResource
        		                    .accept("application/json")	
        		                    .header("Authorization", "Basic " + authEncoded)
                                    .type("application/json")
                                    .post(ClientResponse.class, query);




		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);

		return output;
	}



}
