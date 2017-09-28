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

import com.yahoo.petermwenda83.server.api.rest.bean.APISubjectClasss;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.Response;
import com.yahoo.petermwenda83.server.api.rest.bean.SubClass;

import io.swagger.annotations.*;


/** http://localhost:8080/school/webapi/swagger.json
 *
 * @author peter
 *
 */

@Path("/")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class SubClassRestFulAPI {

	StaffService staffService = new StaffService();

	@ApiOperation(value = "Asign 'subject and class' to a staff.", 
		    notes = "Returns whethet 'subject and class' was assigned successfully or not.", 
		    response = ApiResponse.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Account Id not found!.") 
	} )
	
	@POST
	public ApiResponse addSubject(SubClass subClass, @HeaderParam("authorization") String auth){
		
		if(!RestAUth.isUserAuthenticated(auth, subClass.getAccountId())){

			ApiResponse error = new ApiResponse("error");
			return error; 

		}
		
		return staffService.addSubject(subClass);
	}
	

	
	@Path("/{subClassId}")
	@PUT
	@ApiOperation(value = "Updated asigned subject and class for the given staff.", 
    notes = "Returns whethet 'subject and class' was updated successfully or not.", 
    response = ApiResponse.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Subject_Stream with such Id doesn't exists") 
	} )
	
	public ApiResponse updateComment(@PathParam("subClassId") String subClassId, SubClass subClass, @HeaderParam("authorization") String auth){
		
		if(!RestAUth.isUserAuthenticated(auth, subClass.getAccountId())){

			ApiResponse error = new ApiResponse("error");
			return error; 

		}
		
		return staffService.updateSubjectClass(subClassId, subClass);
	}
	

	
	@DELETE
	@Path("/{subClassId}/{accountId}")
	@ApiOperation(value = "Delete subject and class for the given staff.", 
    notes = "Returns whethet 'subject and class' was deleted successfully or not.", 
    response = ApiResponse.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Subject_Stream with such Id doesn't exists or account Id not found.") 
	} )
	
	public ApiResponse deleteComment(@PathParam("accountId") String accountId, @PathParam("subClassId") String subClassId,
			@HeaderParam("authorization") String auth){
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){

			ApiResponse error = new ApiResponse("error");
			return error; 

		}
		
		return staffService.deleteSubjectClass(subClassId); 
	}

	
	
	@ApiOperation(value = "Get lists of 'subject and class' for the given staff.",  
		    notes = "Returns a list of 'class and subject' for the given staff.", 
		    response = APISubjectClasss.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Stff with such Id doesn't exists or account Id not found.") 
	} )
	
	@GET
	@Path("/{accountId}") 
	public Object getSubClassList(@PathParam("accountId") String accountId, @PathParam("staffId") String staffId ,
			@HeaderParam("authorization") String auth){
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
		
			Response response = new Response();
			response.setMessage("error");
			response.setDescription("User not authenticated");
			return response; 

		}
		
		return staffService.getSubjectClassList(staffId); 
	}

}
