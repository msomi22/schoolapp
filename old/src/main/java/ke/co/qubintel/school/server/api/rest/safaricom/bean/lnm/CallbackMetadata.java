package ke.co.qubintel.school.server.api.rest.safaricom.bean.lnm;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "CallbackMetadata") 
public class CallbackMetadata {
	
	@JsonProperty
	private ArrayList<Item> Item;

	public CallbackMetadata() {
		Item = new ArrayList<>();
	}

	/**
	 * @return the item
	 */
	public ArrayList<Item> getItem() {
		return Item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(ArrayList<Item> item) {
		Item = item;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CallbackMetadata [Item=" + Item + "]";
	}

}
