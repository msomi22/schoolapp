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
package ke.co.qubintel.school.server.servlet.reports.test2;

/**
 * 
 * @author peter
 *
 */
public class TermTwoObj{

	private Forms forms;

	public TermTwoObj(){
		forms = new Forms();
	}

	/**
	 * @return the forms
	 */
	public Forms getForms() {
		return forms;
	}

	/**
	 * @param forms the forms to set
	 */
	public void setForms(Forms forms) {
		this.forms = forms;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TermTwoObj [forms=" + forms + "]";
	}


}
