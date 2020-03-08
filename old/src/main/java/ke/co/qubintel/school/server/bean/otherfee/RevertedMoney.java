/**
 * 
 */
package ke.co.qubintel.school.server.bean.otherfee;

import java.sql.Timestamp;
import java.util.Date;

import ke.co.qubintel.school.server.bean.StorableBean;

/**
 * @author peter
 *
 */
public class RevertedMoney extends StorableBean{
	
	
	private String studentId;
	private String otherFeeId;
	private Timestamp dateReverted;
	   
	

	/**
	 * 
	 */
	public RevertedMoney() {
		studentId = "";
		otherFeeId = "";
		dateReverted = new Timestamp(new Date().getTime());
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
	 * @return the otherFeeId
	 */
	public String getOtherFeeId() {
		return otherFeeId;
	}


	/**
	 * @param otherFeeId the otherFeeId to set
	 */
	public void setOtherFeeId(String otherFeeId) {
		this.otherFeeId = otherFeeId;
	}


	/**
	 * @return the dateReverted
	 */
	public Timestamp getDateReverted() {
		return dateReverted;
	}


	/**
	 * @param dateReverted the dateReverted to set
	 */
	public void setDateReverted(Timestamp dateReverted) {
		this.dateReverted = dateReverted;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RevertedMoney [studentId=" + studentId + ", otherFeeId=" + otherFeeId + ", dateReverted=" + dateReverted
				+ ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}


	/**
		 * 
		 */
		private static final long serialVersionUID = 4675525343037574737L;
}
