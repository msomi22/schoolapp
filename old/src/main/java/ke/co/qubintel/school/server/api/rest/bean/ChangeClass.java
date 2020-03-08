/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class ChangeClass {
	
	private String studentId;
	private String oldClassId;
	private String newClassId;

	/**
	 * 
	 */
	public ChangeClass() {
		studentId = "";
		oldClassId = "";
		newClassId = "";
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getOldClassId() {
		return oldClassId;
	}

	public void setOldClassId(String oldClassId) {
		this.oldClassId = oldClassId;
	}

	public String getNewClassId() {
		return newClassId;
	}

	public void setNewClassId(String newClassId) {
		this.newClassId = newClassId;
	}

	
	@Override
	public String toString() {
		return "ChangeClass [studentId=" + studentId + ", oldClassId=" + oldClassId + ", newClassId=" + newClassId
				+ "]";
	}
	

}
