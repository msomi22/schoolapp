/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig;

import sun.misc.BASE64Decoder;

/**
 * @author peter
 *
 */
public class RestAUth {
	
	/**
	 * @param auth 
	 * @return
	 */
	public static boolean isUserAuthenticated(String auth) {
		String decodedAuth = "";
		boolean success = false;

		if(!StringUtils.isBlank(auth)){

			// Header is in the format "Basic 5tyc0uiDat4"
			// We need to extract data before decoding it back to original string
			String[] authParts = auth.split("\\s+");
			String authInfo = authParts[1];
			// Decode the data back to original string
			byte[] bytes = null;
			try {
				bytes = new BASE64Decoder().decodeBuffer(authInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}

			decodedAuth = new String(bytes);
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

}
