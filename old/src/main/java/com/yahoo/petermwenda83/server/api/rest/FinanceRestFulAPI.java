/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;

/** 
 * 
 * @author peter
 *
 */
@Path("/finance") 
@Api(value = "/finance") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class FinanceRestFulAPI {
	
	FinanceRestService financeRestService = new FinanceRestService();

	
	//TODO get,post,put,delete go_ke items
	

}
