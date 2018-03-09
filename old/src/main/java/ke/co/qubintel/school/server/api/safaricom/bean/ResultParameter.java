package ke.co.qubintel.school.server.api.safaricom.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "ResultParameter") 
public class ResultParameter {
	
	@JsonProperty
	private String Key;
	@JsonProperty
	private String Value;
   
	public ResultParameter() {
		Key = "";
		Value = "";
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}
	
	

	@Override
	public String toString() {
		return "ResultParameter [Key=" + Key + ", Value=" + Value + "]";
	}
	
	

}
