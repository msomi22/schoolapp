/**
 * 
 */
package com.yahoo.petermwenda83.server.balance.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

/**
 * @author peter
 *
 */

@WebService(endpointInterface="com.yahoo.petermwenda83.server.balance.webservice.SchoolGetStudentBalance")
public class GetStudentBalance implements SchoolGetStudentBalance {

	@Override
	public WsResponse findStudentBalance(WsRequest request) {
		WsResponse response = new WsResponse();
		response.setAdmNo(request.getAdmNo()); 
		List<ResponseParam> responseParams = new ArrayList<>();
		ResponseParam param1 = new ResponseParam();
		param1.setParamName("Balance");
		param1.setParamValue("KES1,200"); 
		responseParams.add(param1); 
		response.setResponseParams(responseParams);
		return response;
	}

	@Override
	public WsResponse getStudentDetails(WsRequest request) {
		WsResponse response = new WsResponse();
		response.setAdmNo(request.getAdmNo()); 
		List<ResponseParam> responseParams = new ArrayList<>();
		ResponseParam param1 = new ResponseParam();
		param1.setParamName("Name");
		param1.setParamValue("Peter Mwenda"); 
		
		ResponseParam param2 = new ResponseParam();
		param2.setParamName("Name");
		param2.setParamValue("Peter Mwenda"); 
		
		responseParams.add(param1); 
		responseParams.add(param2); 
		response.setResponseParams(responseParams);
		return response;
	}


}
