package com.yahoo.petermwenda83.server.servlet.reports.test2;

public class MP{

	private String mean;
	private String pos;

	public MP(){
		mean = "";
		pos = "";
	}

	/**
	 * @return the mean
	 */
	public String getMean() {
		return mean;
	}

	/**
	 * @param mean the mean to set
	 */
	public void setMean(String mean) {
		this.mean = mean;
	}

	/**
	 * @return the pos
	 */
	public String getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(String pos) {
		this.pos = pos;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MP [mean=" + mean + ", pos=" + pos + "]";
	}

}
