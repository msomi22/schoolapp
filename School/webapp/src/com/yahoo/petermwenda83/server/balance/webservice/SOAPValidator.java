/**
 * 
 */
package com.yahoo.petermwenda83.server.balance.webservice;
import java.util.Set;

import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
 
public class SOAPValidator implements SOAPHandler {
    @Override
    public void close(MessageContext context) {
      
    }
 
    @Override
    public Set<?> getHeaders() {
       
        return null;
    }

	@Override
	public boolean handleFault(MessageContext arg0) {
		
		return false;
	}

	@Override
	public boolean handleMessage(MessageContext arg0) {
		
		return false;
	}
 
}
