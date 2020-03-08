/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author peter
 * 
 */
@XmlRootElement(name = "ApiResponse")  //only needed if we also want to generate XML    

@ApiModel( value = "ApiResponse", description = "A Generic API Response." )
public class ApiResponse {
	
	@ApiModelProperty( value = "Response message", required = true ) 
	private String message;
	@ApiModelProperty( value = "Response message description", required = true ) 
	private String description;
	
	public ApiResponse(){
		
	}

	public ApiResponse(String message){//"error"
		this.message = message;
		description = "User not authenticated";
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApiResponse [message=" + message + ", description=" + description + "]";
	}
	
	
	
}
