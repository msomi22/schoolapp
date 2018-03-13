/**
 * 
 */
package ke.co.qubintel.school.server.bean.student;

import ke.co.qubintel.school.server.bean.StorableBean;

/**
 * @author peter
 *
 */
public class StudentMisc extends StorableBean{
	
	private String studentId;
	private String key;
	private String value;

	/**
	 * 
	 */
	public StudentMisc() {
		studentId = "";
		key = "";
		value = "";
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
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentMisc [studentId=" + studentId + ", key=" + key + ", value=" + value + ", getUuid()=" + getUuid()
				+ ", getAccountId()=" + getAccountId() + "]";
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1995251088342993445L;

}
