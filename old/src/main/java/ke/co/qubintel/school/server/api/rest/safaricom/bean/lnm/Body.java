package ke.co.qubintel.school.server.api.rest.safaricom.bean.lnm;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "Body") 
public class Body {

	@JsonProperty
	private StkCallback stkCallback;

	public Body() {
		stkCallback = new StkCallback();
	}

	/**
	 * @return the stkCallback
	 */
	public StkCallback getStkCallback() {
		return stkCallback;
	}

	/**
	 * @param stkCallback the stkCallback to set
	 */
	public void setStkCallback(StkCallback stkCallback) {
		this.stkCallback = stkCallback;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Body [stkCallback=" + stkCallback + "]";
	}

}
