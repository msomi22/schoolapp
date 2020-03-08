/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.safaricom;

import ke.co.qubintel.school.server.api.rest.safaricom.bean.lnm.Body;
import ke.co.qubintel.school.server.api.rest.safaricom.bean.lnm.LnmStkPushResponse;
import ke.co.qubintel.school.server.api.rest.util.JsonFromObj;

/**
 * @author peter
 *
 */
public class SafaricomServiceTest {
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {



		//String url = "https://sandbox.safaricom.co.ke/oauth/v1/generate";
		String balUrl = "https://sandbox.safaricom.co.ke/mpesa/accountbalance/v1/query";
		String consumer_key = "BdxK4NoxTTzibPykKoCjQ1YFhgknwpMM";
		String consumer_secret = "OogO6G9bB7Lad933"; 

		//String consumer_Key = "Rwqrrjj4wV2UhgMZYtLMF2X8SQhci6TV";
		//String consumer_Secret = "GeGSs75GrGGa5zAv";


		//System.out.println(SafaricomService.getBalance(balUrl, consumer_key, consumer_secret)); 
		
		//String regUrls = SafaricomService.registerURLS(consumer_key,consumer_secret);
		//System.out.println("regUrls : " + regUrls);  
		
		//System.out.println(SafaricomService.SimulateRequest(consumer_key,consumer_secret)); 
		
		//SafaricomService.lnm_STKP_PUSH(consumer_key,consumer_secret);
		//String shortCode, String  lnmPasskey, String timestamp
		
		//System.out.println(SafaricomService.generatePassword("174379","BNYTYA3C2OOkUO3GJmvPvbCDM8Iv","20180409093002")); 
		
		
		//System.out.println("*** " + test());
		
		//System.out.println(SafaricomService.jsonPrettyPrint(JsonFromObj.getJsonStringFromObject(new LnmStkPushResponse())));
		//System.out.println(JsonFromObj.getJsonStringFromObject(new LnmStkPushResponse()));
		
		
		//System.out.println("*** " + SafaricomService.lnm_STKP_PUSH(consumer_key,consumer_secret)); 
		
		//System.out.println(Generic.getAccessToken(url, username, password));

		//String staffId = "5498156A-FE83-43F4-9592-737HDHJ877S";
		
		//System.out.println(JsonFromObj.getJsonStringFromObject(new VCResponse())); 


		/*String schoolUrl = "http://localhost:8080/school/webapi/staff/"+staffId+"/subjects";
		String username2 = "demo";
		String password2 = "12345678";
		System.out.println();
		System.out.println(Generic.schoolTest(schoolUrl, username2, password2));*/

	}



	
}
