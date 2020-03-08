/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class ApiMisc {
	
	private String uuid;
	private String key;
	private String value;

	/**
	 * 
	 */
	public ApiMisc() {
		uuid = "";
		key = "";
		value = "";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ApiMisc [uuid=" + uuid + ", key=" + key + ", value=" + value + "]";
	}

}
