/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.safaricom.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import ke.co.qubintel.school.server.api.rest.safaricom.Result;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "SafResponse") 
public class SafResponse {
	
	@JsonProperty
	private Result Result;

	/**
	 * 
	 */
	public SafResponse() {
		Result = new Result();
	}

	public Result getResult() {
		return Result;
	}

	public void setResult(Result result) {
		Result = result;
	}

	
	@Override
	public String toString() {
		return "SafResponse [Result=" + Result + "]";
	}
	
	

}
