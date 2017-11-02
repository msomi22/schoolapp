package com.yahoo.petermwenda83.server.servlet.reports.test2;

/**
 * 
 * @author peter
 *
 */
public class FormTwo{

	private MP mp;

	public FormTwo(){
		mp = new MP();
	}

	/**
	 * @return the mp
	 */
	public MP getMp() {
		return mp;
	}

	/**
	 * @param mp the mp to set
	 */
	public void setMp(MP mp) {
		this.mp = mp;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FormTwo [mp=" + mp + "]";
	}

}

