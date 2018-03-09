/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.jwt;

/**
 * @author peter
 *
 */
public class ApiCredentials {
	
	private String apiUsername = "";
	private String apiKey = "";
	private String secret;
	
	public ApiCredentials(){
		apiUsername = "apiusername";
		apiKey = "apikey";
		secret = "apisecret";
	}

	/**
	 * @return the apiUsername
	 */
	public String getApiUsername() {
		return apiUsername;
	}

	/**
	 * @param apiUsername the apiUsername to set
	 */
	public void setApiUsername(String apiUsername) {
		this.apiUsername = apiUsername;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApiCredentials [apiUsername=" + apiUsername + ", apiKey=" + apiKey + ", secret=" + secret + "]";
	}

	

}
