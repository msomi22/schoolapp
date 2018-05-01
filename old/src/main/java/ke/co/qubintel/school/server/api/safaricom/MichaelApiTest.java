/**
 * 
 */
package ke.co.qubintel.school.server.api.safaricom;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import ke.co.qubintel.school.server.api.rest.util.JsonFromObj;
import ke.co.qubintel.school.server.api.safaricom.bean.Token;

/**
 * @author peter
 *
 */
public class MichaelApiTest {

	/**
	 * 
	 */
	public MichaelApiTest() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//String url = "http://kegtracker.azurewebsites.net/token"; 
		//String token = getAccessToken(url,"a@d.co","password");
		//System.out.println("token : " + token);
		//System.out.println(fetchRealTimeData(token)); 
		//String queyBy = "BB00000010";
		//System.out.println(queryDataById(token,queyBy)); 
		//String date = "2018-01-25T11:48:10+00:00"; 
		//System.out.println(queryDataByDate(token,date));
		
		System.out.println(" response : " + googleLocationBycellTowers());

	}

	/**
	 * 
	 * @param token
	 * @param date
	 */
	public static String queryDataByDate(String token, String date) {
		String url = "http://kegtracker.azurewebsites.net/api/AntennaSamples"; 
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);

		System.out.println("url : " + url); 

		ClientResponse response = webResource
				.queryParam("filter", date)  
				.accept("application/json")	
				.type("application/json")
				.header("Authorization", "Bearer " + token)
				.get(ClientResponse.class);

		System.out.println("status : " + response.getStatus()); 

		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);

		return output;

	}
	/**
	 * 
	 * @param token
	 * @param queyBy
	 */
	public static String queryDataById(String token, String queyBy) {
		String url = "http://kegtracker.azurewebsites.net/api/AntennaSamples"; 
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);

		System.out.println("url : " + url); 

		ClientResponse response = webResource
				.queryParam("filter", queyBy) 
				.accept("application/json")	
				.type("application/json")
				.header("Authorization", "Bearer " + token)
				.get(ClientResponse.class);

		System.out.println("status : " + response.getStatus()); 

		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);

		return output;

	}

	/**
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public static String getAccessToken(String url,String username,String password) {

		String authEncoded = getAuthBase64(username,password);

		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		ClientResponse resp = webResource.queryParam("grant_type", "password")
				.accept("application/json")
				.type("application/json; charset=utf-8")
				.header("Authorization", "Basic " + authEncoded)
				.post(ClientResponse.class,"grant_type=password&username=a@d.co&password=password");  

		if(resp.getStatus() == 200) {
			String output = resp.getEntity(String.class);

			Gson g = new Gson(); 
			Token token = g.fromJson(output, Token.class);

			return token.getAccess_token(); 
		}else {
			return resp.getStatus() + " -> " + resp.getEntity(String.class);   
		}
	}

	/**
	 *
	 * @param username 
	 * @param password 
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
	 * @param token 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String fetchRealTimeData(String token) { 
		String url = "http://kegtracker.azurewebsites.net/api/Antennas"; 
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);

		ClientResponse response = webResource
				.accept("application/json")	
				.type("application/json")
				.header("Authorization", "Bearer " + token)
				.get(ClientResponse.class);

		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);

		return output;
	}

	/**
	 * 
	 * @return
	 */
	public static String googleLocationBycellTowers() {
		String output = "";
		String apikey = "AIzaSyC4G-4f9_c6Eje-h1kLTtgk_lAwxxrw4Hk";
		String url = "https://www.googleapis.com/geolocation/v1/geolocate?key="+apikey; 
		
		int mcc = 639;
		int mnc = 2;
		
		ISPLocRequest ispLocRequest = new ISPLocRequest();
		ispLocRequest.setHomeMobileCountryCode(mcc);
		ispLocRequest.setHomeMobileNetworkCode(mnc);
		ispLocRequest.setRadioType("gsm");
		ispLocRequest.setCarrier("safaricom");
		ispLocRequest.setConsiderIp("true"); 

		List<CellTower> cellTowers = new ArrayList<>();
		
		int noOfCells = 3;
		int[] cids = {4966,2211,1371};
		int[] lacs = {4110,4110,4110};
		int[] ages = {0,0,0,}; 
		int[] ss = {-49,-75,-87}; 
		int[] ta = {15,15,15};  
		for(int i=0;i<noOfCells;i++) {
			CellTower cell = new CellTower();
			cell.setCellId(cids[i]); 
			cell.setLocationAreaCode(lacs[i]); 
			cell.setMobileCountryCode(mcc); 
			cell.setMobileNetworkCode(mnc);
			cell.setAge(ages[i]);
			cell.setSignalStrength(ss[i]);
			cell.setTimingAdvance(ta[i]);  
			cellTowers.add(cell);
		}
		ispLocRequest.setCellTowers(cellTowers); 
		
		String query = JsonFromObj.getJsonStringFromObject(ispLocRequest);  
		//System.out.println(cellTowers.size() + " cell(s)" + " " + query);   
		
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		ClientResponse response = webResource
				.accept("application/json")	
				.type("application/json")
				.post(ClientResponse.class,query);

		if(response.getStatus() != 200){
			output = "Unable to connect to the server, status: " + response.getStatus(); 
		}

		output = response.getEntity(String.class);

		return output;
	}

}
