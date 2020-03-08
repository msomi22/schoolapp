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

public class Forms{

	private FormOne formOne;
	private FormTwo formTwo;
	private FormThree formThree;
	private FormFour formFour;
	
	public Forms(){
		formOne = new FormOne();
		formTwo = new FormTwo();
		formThree = new FormThree();
		formFour = new FormFour();
	}

	
	/**
	 * @return the formOne
	 */
	public FormOne getFormOne() {
		return formOne;
	}


	/**
	 * @param formOne the formOne to set
	 */
	public void setFormOne(FormOne formOne) {
		this.formOne = formOne;
	}


	/**
	 * @return the formTwo
	 */
	public FormTwo getFormTwo() {
		return formTwo;
	}

	/**
	 * @param formTwo the formTwo to set
	 */
	public void setFormTwo(FormTwo formTwo) {
		this.formTwo = formTwo;
	}


	/**
	 * @return the formThree
	 */
	public FormThree getFormThree() {
		return formThree;
	}


	/**
	 * @param formThree the formThree to set
	 */
	public void setFormThree(FormThree formThree) {
		this.formThree = formThree;
	}


	/**
	 * @return the formFour
	 */
	public FormFour getFormFour() {
		return formFour;
	}

	/**
	 * @param formFour the formFour to set
	 */
	public void setFormFour(FormFour formFour) {
		this.formFour = formFour;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Forms [formOne=" + formOne + ", formTwo=" + formTwo + ", formThree=" + formThree + ", formFour="
				+ formFour + "]";
	}
	
}
