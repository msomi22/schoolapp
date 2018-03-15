package ke.co.qubintel.school.server.bean.house;

import java.sql.Timestamp;
import java.util.Date;

import ke.co.qubintel.school.server.bean.StorableBean;

public class StudentHouse extends StorableBean{
	
	private String studentId;
	private String houseId;
	private Timestamp dateOut;
	private Timestamp dateIn;

	public StudentHouse() {
		studentId = "";
		houseId = "";
		dateOut = new Timestamp(new Date().getTime());
		dateIn = new Timestamp(new Date().getTime());
	}

	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the houseId
	 */
	public String getHouseId() {
		return houseId;
	}

	/**
	 * @param houseId the houseId to set
	 */
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	/**
	 * @return the dateOut
	 */
	public Timestamp getDateOut() {
		return dateOut;
	}

	/**
	 * @param dateOut the dateOut to set
	 */
	public void setDateOut(Timestamp dateOut) {
		this.dateOut = dateOut;
	}

	/**
	 * @return the dateIn
	 */
	public Timestamp getDateIn() {
		return dateIn;
	}

	/**
	 * @param dateIn the dateIn to set
	 */
	public void setDateIn(Timestamp dateIn) {
		this.dateIn = dateIn;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentHouse [studentId=" + studentId + ", houseId=" + houseId + ", dateOut=" + dateOut + ", dateIn="
				+ dateIn + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 648802730650335411L;
}
