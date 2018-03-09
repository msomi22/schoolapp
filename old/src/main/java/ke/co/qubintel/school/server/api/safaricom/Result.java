/**
 * 
 */
package ke.co.qubintel.school.server.api.safaricom;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import ke.co.qubintel.school.server.api.safaricom.bean.ReferenceData;
import ke.co.qubintel.school.server.api.safaricom.bean.ResultParameters;

/**
 * @author peter
 *
 */

//@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "Result") 
public class Result {

	@JsonProperty
	private String ResultType;
	@JsonProperty
	private String ResultCode;
	@JsonProperty
	private String ResultDesc;
	@JsonProperty
	private String OriginatorConversationID;
	@JsonProperty
	private String ConversationID;
	@JsonProperty
	private String TransactionID;
	@JsonProperty
	private ResultParameters ResultParameters;
	@JsonProperty
	private ReferenceData ReferenceData;
	
	public Result(){
		ResultType = "";
		ResultCode = "";
		ResultDesc = "";
		OriginatorConversationID = "";
		ConversationID = "";
		TransactionID = "";
		ResultParameters = new ResultParameters();
		ReferenceData = new ReferenceData();
	}

	public String getResultType() {
		return ResultType;
	}

	public void setResultType(String resultType) {
		ResultType = resultType;
	}

	public String getResultCode() {
		return ResultCode;
	}

	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}

	public String getResultDesc() {
		return ResultDesc;
	}

	public void setResultDesc(String resultDesc) {
		ResultDesc = resultDesc;
	}

	public String getOriginatorConversationID() {
		return OriginatorConversationID;
	}

	public void setOriginatorConversationID(String originatorConversationID) {
		OriginatorConversationID = originatorConversationID;
	}

	public String getConversationID() {
		return ConversationID;
	}

	public void setConversationID(String conversationID) {
		ConversationID = conversationID;
	}

	public String getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}

	public ResultParameters getResultParameters() {
		return ResultParameters;
	}

	public void setResultParameters(ResultParameters resultParameters) {
		ResultParameters = resultParameters;
	}

	public ReferenceData getReferenceData() {
		return ReferenceData;
	}

	public void setReferenceData(ReferenceData referenceData) {
		ReferenceData = referenceData;
	}

	@Override
	public String toString() {
		return "Result [ResultType=" + ResultType + ", ResultCode=" + ResultCode + ", ResultDesc=" + ResultDesc
				+ ", OriginatorConversationID=" + OriginatorConversationID + ", ConversationID=" + ConversationID
				+ ", TransactionID=" + TransactionID + ", ResultParameters=" + ResultParameters + ", ReferenceData="
				+ ReferenceData + "]";
	}

	
	
	
}
