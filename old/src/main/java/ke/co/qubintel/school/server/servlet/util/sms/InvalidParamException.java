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
package ke.co.qubintel.school.server.servlet.util.sms;

/**
 * @author peter
 *
 */
public class InvalidParamException extends Exception{

	/**
	 * 
	 */
	public InvalidParamException() {}
	/**
	 * 
	 * @param message
	 */
	public InvalidParamException(String message) {
		super(message);
	}
	/**
	 * 
	 * @param cause
	 */
	public InvalidParamException(Throwable cause) {
		super(cause);
	}
	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public InvalidParamException(String message, Throwable cause) {
		super(message, cause);
	}

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4454230740165585786L;

}
