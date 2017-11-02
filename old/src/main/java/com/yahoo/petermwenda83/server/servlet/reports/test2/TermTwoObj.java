package com.yahoo.petermwenda83.server.servlet.reports.test2;

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
