package ke.co.qubintel.school.server.api.safaricom.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "ResultParameters") 
public class ResultParameters {
	
	@JsonProperty
	private List<ResultParameter> ResultParameter; 

	public ResultParameters() {
		ResultParameter = new ArrayList<ResultParameter>();  
	}

	
	public List<ResultParameter> getResultParameter() {
		return ResultParameter;
	}

	public void setResultParameter(List<ResultParameter> resultParameter) {
		ResultParameter = resultParameter;
	}




	@Override
	public String toString() {
		return "ResultParameters [ResultParameter=" + ResultParameter + "]";
	}

	
}
