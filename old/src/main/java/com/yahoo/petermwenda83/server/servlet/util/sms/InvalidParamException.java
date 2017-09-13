/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.util.sms;

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
