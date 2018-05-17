package ke.co.qubintel.school.server.api.rest.safaricom.mw;

import java.util.UUID;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * 
 * @author peter
 *
 */
public class WMApiService {

	public static void main(String[] args) {

		WMApiService wmApi = new WMApiService();
		System.out.println("sending...."); 
		String endPoint = "http://47.91.105.10:10786";
		//String endPoint2 = "http://47.91.105.10:10786";
		String meterNo = "0120012000812";
		//System.out.println(wmApi.queryCustomerInfo(endPoint,meterNo)); 
		
		//System.out.println(wmApi.purchaseToken(endPoint,meterNo)); 
		
		wmApi.getGuid();


	}

	/**
	 * RESPONSE 
	 * 
	 * function=querycustomerbymeternumber&errorcode=0&customername=0120011000243
	 * &customernumber=1704000006&identificationnumber=0120011000243&telephonenumber=18158120370&debt=0.000
     *
	 * @return
	 */
	public String queryCustomerInfo(String endPoint, String meterNo) {
		String url = endPoint;
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);

		// GET method
		ClientResponse response = webResource
				.queryParam("function", "querycustomerbymeternumber")
				.queryParam("meternumber", meterNo)
				.header("Host", endPoint)  
				.type("text/html")
				.accept("text/html")	
				.get(ClientResponse.class);
		
		//System.out.println("status : " + response.getStatus()); 

		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);

		return output;
	}
	
	/**
	 * 
	 * RESPONSE
	 * 
	 * 
	 * operatetype=purchasebytransid&meternumber=0120012000812&transid=61f2a41d339248b6&errorcode=0&
	 * payment=200.00&repaydebt=0.00&additionalfee=0.00&rechargeamount=200.00&rechargevolume=0.50&
	 * vatrate=0.00&vatamount=0.00&tokenlist=4822 7461 2086 6412 5250
	 * 
	 * 200.00
	 * 
	 * 0 48
	 * 2 50
	 * . 46
	 * 
	 * 50 48 48 46 48 48
	 * 050 048 048 046 048 048
	 * 050048048046048048
	 * 
	 * 61f2a41d339248b6
	 *
	 *
	 *900.00
	 *057 048 048 046 048 048 013 010
	 *057048048046048048013010
	 *
	 *fb4821bd0e6a43b3
	 *
	 * 
	 * 
	 * 
	 * @param endPoint
	 * @return
	 */
	
	public String purchaseToken(String endPoint,String meterNo) {
		String url = endPoint;
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
        String id = "fb4821bd0e6a43b3";//16 ASCII characters
        //String payment = "3353568817039E903AD4D87E2FEFD23B"; //200.00 (32 ascii characters) 
        String payment = "12ADDB7DEA58A503D41263F4EA44274F";
		String query = "operatetype=purchasebytransid&transid="+id+"&meternumber="+meterNo+"&purchaseparam="+payment; 

		// POST method
		ClientResponse response = webResource
				.header("Host", endPoint)  
				.type("text/html")
				.post(ClientResponse.class, query);
		
		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);

		return output;
	}
	
	/**
	 * UUID has 32+ characters at 4 bits/char, so 128 bits.
	 * 
	 * @return  16 ASCII characters 
	 */
	public String getGuid() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        System.out.println("Random UUID        = " + uuid);
        System.out.println("Random UUID String = " + randomUUIDString);
        System.out.println("UUID version       = " + uuid.version());
        System.out.println("UUID variant       = " + uuid.variant());
        System.out.println("Len                = " + randomUUIDString.length());
        return randomUUIDString;
    }
	
	
}
