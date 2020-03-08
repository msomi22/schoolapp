/**
 * 
 */
package ke.co.qubintel.school.server.bean.account;

import java.sql.Timestamp;
import java.util.Date;

import ke.co.qubintel.school.server.bean.StorableBean;

/**
 * @author peter
 *
 */
public class IncomingSMS extends StorableBean{
	
	private String mobile;
	private String message;
	private Timestamp receiveDate;

	/**
	 * 
	 */
	public IncomingSMS() {
		mobile = "";
		message = "";
		receiveDate = new Timestamp(new Date().getTime()); 
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Timestamp receiveDate) {
		this.receiveDate = receiveDate;
	}

	@Override
	public String toString() {
		return "IncomingSMS [mobile=" + mobile + ", message=" + message + ", receiveDate=" + receiveDate + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8944573161460710012L;

}
