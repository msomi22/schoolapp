/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.safaricom.bean.lnm;

/**
 * @author peter
 *
 */
public class LnmStkPushResponse {

	private Body Body;

	/**
	 * 
	 */
	public LnmStkPushResponse() {
		Body = new Body();
	}

	/**
	 * @return the body
	 */
	public Body getBody() {
		return Body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(Body body) {
		Body = body;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LnmStkPushResponse [Body=" + Body + "]";
	}


}
