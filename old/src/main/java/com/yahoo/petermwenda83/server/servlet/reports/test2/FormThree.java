package com.yahoo.petermwenda83.server.servlet.reports.test2;


/**
 * 
 * @author peter
 *
 */
public class FormThree{

	private MP mp;

	public FormThree(){
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
		return "FormThree [mp=" + mp + "]";
	}

}

