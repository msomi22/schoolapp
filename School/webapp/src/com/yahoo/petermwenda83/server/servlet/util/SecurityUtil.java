/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the License); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.server.servlet.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * This class is used to hash passwords using md5
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 * @author <a href="mailto:michael@tawi.mobi">Michael Wakahe</a>
 *
 */
public class SecurityUtil {

	private static Logger logger = Logger.getLogger(SecurityUtil.class);
	
	
	/**
	 * Return the MD5 hash of a String. It will work correctly for most 
	 * strings. A word which does not work correctly is "michael" (check 
	 * against online MD5 hash tools).	
	 *
	 * @param toHash plain text string to encryption
	 * @return an md5 hashed string
	 */
	public static String getMD5Hash(String toHash) {	
	    String md5Hash = "";
	
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        
	        md.update(toHash.getBytes(), 0, toHash.length());
	
	        md5Hash = new BigInteger(1, md.digest()).toString(16);
			
	    } catch (NoSuchAlgorithmException e) {
	    	logger.error("NoSuchAlgorithmException while getting MD5 hash of '" + toHash + "'");
			logger.error(ExceptionUtils.getStackTrace(e));
	    }
	
	    return md5Hash;
	}

}
