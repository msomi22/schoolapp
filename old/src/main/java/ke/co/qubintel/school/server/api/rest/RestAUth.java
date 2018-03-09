/**
 * 
 */
package ke.co.qubintel.school.server.api.rest;

import java.io.IOException;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.servlet.util.PropertiesConfig;

/**
 * @author peter
 *
 */
public class RestAUth {
	
	/**
	 * @param auth 
	 * @param accountId 
	 * @return
	 */
	public static boolean isUserAuthenticated(String auth, String accountId) {
		String decodedAuth = "";
		boolean success = false;

		if(!StringUtils.isBlank(auth)){

			// Header is in the format "Basic 5tyc0uiDat4"
			// We need to extract data before decoding it back to original string
			String[] authParts = auth.split("\\s+");
			String authInfo = authParts[1];
			// Decode the data back to original string
			
			byte[] base64decodedBytes = Base64.getDecoder().decode(authInfo); 
			
			
			try {
				
				decodedAuth = new String(base64decodedBytes, "utf-8");
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}

			
			//System.out.println("*****    auth: "+ decodedAuth);

			String[] parts = decodedAuth.split(":"); 

			if(parts.length == 2){

				if(StringUtils.equals(parts[0], PropertiesConfig.getConfigValue("REST_USERNAME")) && 
						StringUtils.equals(parts[1], PropertiesConfig.getConfigValue("REST_PASSWORD"))){
					success= true;
				}
			}

		}


		return success;
	}
	
	
	
	
	/**
	 * 
	 * @param auth
	 * @return
	 */
	 
	public static boolean isAdminAuthenticated(String auth) {
		String decodedAuth = "";
		boolean success = false;

		if(!StringUtils.isBlank(auth)){

			// Header is in the format "Basic 5tyc0uiDat4"
			// We need to extract data before decoding it back to original string
			String[] authParts = auth.split("\\s+");
			String authInfo = authParts[1];
			// Decode the data back to original string
			
			byte[] base64decodedBytes = Base64.getDecoder().decode(authInfo); 
			
			
			try {
				
				decodedAuth = new String(base64decodedBytes, "utf-8");
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}

			
			//System.out.println("*****    auth: "+ decodedAuth);

			String[] parts = decodedAuth.split(":"); 

			if(parts.length == 2){

				if(StringUtils.equals(parts[0], PropertiesConfig.getConfigValue("REST_ADMIN_USERNAME")) && 
						StringUtils.equals(parts[1], PropertiesConfig.getConfigValue("REST_ADMIN_PASSWORD"))){
					success= true;
				}
			}

		}


		return success;
	}

}
