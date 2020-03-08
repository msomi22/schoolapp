package ke.co.qubintel.school.server.api.rest.safaricom.bean.lnm;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author peter
 *
 */
@XmlRootElement(name = "Item") 
public class Item {
	
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Value;

	public Item() {
		Name = "";
		Value = "";
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return Value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		Value = value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Item [Name=" + Name + ", Value=" + Value + "]";
	}

}
