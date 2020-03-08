/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.session;
/**
 * This class manages sessions for the admin
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class AdminSessionConstants {

	final public static int SESSION_TIMEOUT = 400; 

	//Admin sessions
	public static final String ADMIN_SIGN_IN_ERROR_KEY = "Error Login";
	final public static String ADMIN_SESSION_KEY = "Admin Session Key";

	public static final String ADMIN_SIGN_IN_KEY = "admin Signin Key";
	public static final String ADMIN_SIGN_IN_TIME = "Admin Signin Time";

	final public static String ADMIN_SIGN_IN_ERROR_VALUE = "Sorry, the administrator username and/or " +
			"password are incorrect. Please try again.";

	final public static String SCHOOL_ACCOUNT_ADD_SUCCESS = "Account Account Added Successfully";
	final public static String SCHOOL_ACCOUNT_ADD_KEY = "Account Account Add Key";
	final public static String SCHOOL_ACCOUNT_ADD_ERROR = "Account Account Add Error";
	final public static String SCHOOL_ACCOUNT_UPDATE_ERROR = "Account Account Update Error";
	final public static String SCHOOL_ACCOUNT_UPDATE_SUCCESS = "Account Account Update Success";
	final public static String SCHOOL_ACCOUNT_PARAM = "Account Account Parameters";

	final public static String PRINCIPAL_ADD_ERROR = "Principal add error";
	final public static String PRINCIPAL_ADD_SUCCESS = "Principal added Successfully";

}
