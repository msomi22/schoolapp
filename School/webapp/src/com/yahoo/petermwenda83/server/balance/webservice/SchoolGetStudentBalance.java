package com.yahoo.petermwenda83.server.balance.webservice;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
//import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService
@SOAPBinding(style = Style.RPC)
public interface SchoolGetStudentBalance {
	@WebMethod
	@HandlerChain(file = "soap-handler.xml")
	@RequestWrapper(localName = "WsRequest", targetNamespace = "http://webservice.balanace.server.petermwenda83.yahoo.com/Balance", className = "com.yahoo.petermwenda83.server.balance.webservice.WsRequest")
	@ResponseWrapper(localName = "WsResponse", targetNamespace = "http://webservice.balanace.server.petermwenda83.yahoo.com/Balance", className = "com.yahoo.petermwenda83.server.balance.webservice.WsResponse")
	public WsResponse findStudentBalance(@WebParam(name = "WsRequest", targetNamespace = "") WsRequest request);

	@WebMethod
	@RequestWrapper(localName = "WsRequest", targetNamespace = "http://webservice.balanace.server.petermwenda83.yahoo.com/StudentInfo", className = "com.yahoo.petermwenda83.server.balance.webservice.WsRequest")
	@ResponseWrapper(localName = "WsResponse", targetNamespace = "http://webservice.balanace.server.petermwenda83.yahoo.com/StudentInfo", className = "com.yahoo.petermwenda83.server.balance.webservice.WsResponse")
	public WsResponse getStudentDetails(@WebParam(name = "WsRequest", targetNamespace = "") WsRequest request);
	
}
