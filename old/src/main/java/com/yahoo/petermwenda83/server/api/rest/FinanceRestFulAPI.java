/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yahoo.petermwenda83.bean.money.FeeBreakdown;
import com.yahoo.petermwenda83.bean.money.FeeBreakdownDesc;
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
	
	@ApiOperation(value = "Add new Fee Breakdown.", 
			notes = "Returns whether Breakdown was added.", 
			response = FeeBreakdown.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@POST
	@Path("/fee/category/{accountId}/")  
	public Object putFeeBreakdown(@PathParam("accountId") String accountId,FeeBreakdown feeBreakdown,
			@HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.addFeeBreakdown(feeBreakdown);
	}
	
	@ApiOperation(value = "Update Fee Breakdown.", 
			notes = "Returns whether Breakdown was updated.", 
			response = FeeBreakdown.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@PUT
	@Path("/fee/category/{accountId}/")  
	public Object updateFeeBreakdown(@PathParam("accountId") String accountId, FeeBreakdown feeBreakdown,
			@HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.updateFeeBreakdown(feeBreakdown); 
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
	/**
	 * 
	 * @param accountId
	 * @param feeBreakdownDesc
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Add new Government money distribution property.", 
			notes = "Returns whether Government money distribution property was added.", 
			response = FeeBreakdown.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@POST
	@Path("/goke/{accountId}")   
	public Object putGoKeMoney(@PathParam("accountId") String accountId,FeeBreakdownDesc feeBreakdownDesc,
			@HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.putGoKeMoney(feeBreakdownDesc);
	}
	/**
	 * 
	 * @param accountId
	 * @param feeBreakdownDesc
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Update Government money distribution property.", 
			notes = "Returns whether Government money distribution property was updated.", 
			response = FeeBreakdown.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@PUT
	@Path("/goke/{accountId}")   
	public Object updateGoKeMoney(@PathParam("accountId") String accountId,FeeBreakdownDesc feeBreakdownDesc,
			          @HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.updatedGoKeMoney(feeBreakdownDesc);
	}
	/**
	 * 
	 * @param accountId
	 * @param id
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Delete Government money distribution property.", 
			notes = "Returns whether Government money distribution property was deleted.", 
			response = FeeBreakdown.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@DELETE
	@Path("/goke/{accountId}/{id}")   
	public Object deleteGoKeMoney(@PathParam("accountId") String accountId,@PathParam("id") String id,
			@HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.deleteGoKeMoney(accountId, id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param accountId
	 * @param term
	 * @param year
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get Term Fee List.", 
			notes = "Returns List of fee charged for the given term and year.", 
			response = TermFee.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.")  
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
	
	/**
	 * 
	 * @param accountId
	 * @param year
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get Term Fee List per year.", 
			notes = "Returns List of fee charged for the given  year.", 
			response = TermFee.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.")  
	} )
	@GET
	@Path("/termfee/{accountId}/{year}")    
	public Object getTermFeePerYear(@PathParam("accountId") String accountId,
			@PathParam("year") String year, @HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.getTermFeePerYear(accountId, year); 
	}
	
	/**
	 * 
	 * @param accountId
	 * @param termFee
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Add Term Fee .", 
			notes = "Returns whether term fee was added.", 
			response = TermFee.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.")  
	} )
	@POST
	@Path("/termfee/{accountId}")    
	public Object addTermFeePerYear(@PathParam("accountId") String accountId,TermFee termFee,
			       @HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.putTermFee(termFee);
	}
	
	/**
	 * 
	 * @param accountId
	 * @param termFee
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Update Term Fee .", 
			notes = "Returns whether term fee was updated.", 
			response = TermFee.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.")  
	} )
	@PUT
	@Path("/termfee/{accountId}")    
	public Object updateTermFeePerYear(@PathParam("accountId") String accountId,TermFee termFee,
			     @HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.updateTermFee(termFee);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param accountId
	 * @param term
	 * @param year
	 * @param auth
	 * @return
	 */
	
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
	
	/**
	 * 
	 * @param accountId
	 * @param otherFee
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Add new Other Term Fee.", 
			notes = "Returns whether Fee was added.", 
			response = OtherFee.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.")  
	} )
	@POST
	@Path("/fee/other/{accountId}")    
	public Object addTermOtherFee(@PathParam("accountId") String accountId, OtherFee otherFee,
			@HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.putOtherFee(otherFee);
	}
	
	/**
	 * 
	 * @param accountId
	 * @param otherFee
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Update Other Term Fee.", 
			notes = "Returns whether Fee was updated.", 
			response = OtherFee.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.")  
	} )
	@PUT
	@Path("/fee/other/{accountId}")    
	public Object updateTermOtherFee(@PathParam("accountId") String accountId,OtherFee otherFee, 
			@HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return financeRestService.updateOtherFee(otherFee);
	}
	
	
	

}
