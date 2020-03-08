/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.safaricom.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author peter
 *
 */

//@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "ReferenceData") 
public class ReferenceData {
	
	@JsonProperty
	private ReferenceItem ReferenceItem;

	/**
	 * 
	 */
	public ReferenceData() {
		ReferenceItem = new ReferenceItem();
	}

	public ReferenceItem getReferenceItem() {
		return ReferenceItem;
	}

	public void setReferenceItem(ReferenceItem referenceItem) {
		ReferenceItem = referenceItem;
	}

	@Override
	public String toString() {
		return "ReferenceData [ReferenceItem=" + ReferenceItem + "]";
	}

}
