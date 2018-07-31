package ke.co.qubintel.school.server.api.rest.safaricom.mw;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Hex;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/** 
 * 
 *  @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class WMApiService {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	//private static final String rk = "2CCBA387A3B10B126F20DFB38E2B4B6C";
	private static final byte[] rootKey = new byte[]{0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30}; 
	//private static final byte[] rootKey = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}; 
	//private static final byte[] rootKey = rk.getBytes();


	public static void main(String[] args) throws Exception {

		WMApiService wmApi = new WMApiService();
		System.out.println("sending...."); 
		//String endPoint = "http://47.91.105.10:10786";
		String endPoint = "http://10.172.19.106:18010";//TCP-18010, 9001-both - 41.90.111.70
		String meterNo = "0120012000812";
		//System.out.println(wmApi.queryCustomerInfo(endPoint,meterNo)); 


		String transactionId = wmApi.randomAlphaNumeric(16);
		System.out.println("transactionId = " + transactionId);
		String purchaseParam = generatePurchaseString(transactionId, 400.00);
		String ps = wmApi.purchaseToken(endPoint, meterNo, transactionId , purchaseParam);

		System.out.println("purchaseParam = " + purchaseParam);  
		System.out.println("Response = " + ps);  
		
		//String paymentstr = getEncryptedAmount(transactionId,300.00,"ED880A97CCD44A4741B4AA034A4A7203");
		//System.out.println("paymentstr = " + paymentstr);  


	}

	/**
	 * 
	 * @param transactionID
	 * @param payment
	 * @return
	 * @throws Exception 
	 */
	public static String generatePurchaseString(String transactionID, Double payment) throws Exception{

		byte[] transidbytes = getBytes16(transactionID);
		byte[] encryptedtransaction = AES.ecbEncrypt(transidbytes, rootKey); 

		String paymentStr = String.valueOf(payment);
		byte[] paymentbytes = getBytes16(paymentStr);  

		byte[] purchasebytes = AES.ecbEncrypt(paymentbytes, encryptedtransaction); 

		String hex = hexEncode(purchasebytes); 

		return hex; 
	}

	/**
	 * 
	 * @param transactionID
	 * @param payment
	 * @param encryptedTransaction
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getEncryptedAmount(String transactionID, Double payment, String encryptedParam) throws Exception {

		byte[] transidbytes = getBytes16(transactionID);
		byte[] encryptedtransaction = AES.ecbEncrypt(transidbytes, rootKey); 

		//encryptedTransaction hex to bytes
		byte[] encryptedbytes = hexStringToBytes(encryptedParam);

		byte[] paymentbytes = AES.ecbDecrypt(encryptedbytes, encryptedtransaction);

		String paymentstr = bytesToASCIIString(paymentbytes); 

		return paymentstr;
	}


	/**
	 * 
	 * @param transactionId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getBytes16(String toConvert) throws UnsupportedEncodingException {

		if ((toConvert.length() < 1)) {
			return null;
		}

		if ((toConvert.length() > 16)) {
			toConvert = toConvert.substring(0, 16);
			return null;
		}

		byte[] strbytes = toConvert.getBytes("UTF-8");
		/**
		 * Append 0x00 fill to 16 bytes 
		 */
		byte[] resultbytes = new byte[16];
		System.arraycopy(strbytes, 0, resultbytes, 0, strbytes.length); 
		return resultbytes;
	}


	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String hexEncode(final byte[] data) {
		try {
			final char[] result = Hex.encodeHex(data);
			return String.valueOf(result).toUpperCase(); 
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * RESPONSE 
	 * 
	 * function=querycustomerbymeternumber&errorcode=0&customername=0120011000243
	 * &customernumber=1704000006&identificationnumber=0120011000243&telephonenumber=18158120370&debt=0.000
	 * 
	 * 
	 * @param endPoint
	 * @param meterNo
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
				//.accept("text/html")	
				.get(ClientResponse.class);

		//System.out.println("status : " + response.getStatus()); 

		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);

		return output;
	}

	/**
	 * RESPONSE
	 * 
	 * operatetype=purchasebytransid&meternumber=0120012000812&transid=61f2a41d339248b6&errorcode=0&
	 * payment=200.00&repaydebt=0.00&additionalfee=0.00&rechargeamount=200.00&rechargevolume=0.50&
	 * vatrate=0.00&vatamount=0.00&tokenlist=4822 7461 2086 6412 5250
	 * 
	 * 
	 * @param endPoint
	 * @param meterNo
	 * @param transactionId
	 * @param purchaseParam
	 * @return
	 */

	public String purchaseToken(String endPoint,String meterNo, String transactionId, String purchaseParam) { 
		String url = endPoint;
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		String query = "operatetype=purchasebytransid&transid="+transactionId+"&meternumber="+meterNo+"&purchaseparam="+purchaseParam; 

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
	 * 
	 * @param count
	 * @return
	 */
	public String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString().toLowerCase();
	}

	//****************************************************************** 
	/**
	 * 
	 * @param encryptedParam
	 * @return
	 */
	public static byte[] hexStringToBytes(String encryptedParam) {
		return DatatypeConverter.parseHexBinary(encryptedParam);
	}
	/**
	 * 
	 * @param paymentbytes
	 * @return
	 */
	public static String bytesToASCIIString(byte[] paymentbytes) {
		return  new String(paymentbytes);
	}







}

