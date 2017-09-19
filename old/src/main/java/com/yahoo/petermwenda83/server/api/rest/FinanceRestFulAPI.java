/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yahoo.petermwenda83.bean.money.FeeBreakdown;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.otherfee.OtherFee;
import com.yahoo.petermwenda83.server.api.rest.bean.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

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
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get Fee Breakdown List.", 
			notes = "Returns List of fee Breakdown.", 
			response = FeeBreakdown.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@GET
	@Path("/fee/category/{accountId}/")  
	public Object getFeeBreakdown(@PathParam("accountId") String accountId,
			@HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.getFeeBreakDown(accountId); 
	}


	/**
	 * 
	 * @param accountId
	 * @param feeBreakdownId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get Government money distribution List.", 
			notes = "Returns List of money distributions based on the govenment policy.", 
			response = FeeBreakdown.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@GET
	@Path("/goke/{accountId}/{feeBreakdownId}")   
	public Object getGoKeMoney(@PathParam("accountId") String accountId,@PathParam("feeBreakdownId") String feeBreakdownId,
			@HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.getGoKeMoney(accountId, feeBreakdownId); 
	}
	
	
	@ApiOperation(value = "Get Term Fee List.", 
			notes = "Returns List of fee charged for the given term and year.", 
			response = TermFee.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Id(s) not found.")  
	} )
	@GET
	@Path("/termfee/{accountId}/{term}/{year}")    
	public Object getTermFee(@PathParam("accountId") String accountId, @PathParam("term") String term,
			@PathParam("year") String year, @HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.getTermFee(accountId, term, year);
	}
	
	
	@ApiOperation(value = "Get Other Term Fee List.", 
			notes = "Returns List of Other fee charged for the given term and year.", 
			response = OtherFee.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Id(s) not found.")  
	} )
	@GET
	@Path("/fee/other/{accountId}/{term}/{year}")    
	public Object getTermOtherFee(@PathParam("accountId") String accountId, @PathParam("term") String term,
			@PathParam("year") String year, @HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.getOtherFee(accountId, term, year);
	}
	
	
	

}
