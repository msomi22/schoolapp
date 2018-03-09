/**
 * 
 */
package ke.co.qubintel.school.server.api.rest;

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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import ke.co.qubintel.school.server.api.rest.bean.ApiClassTeacher;
import ke.co.qubintel.school.server.api.rest.bean.Response;

/**
 * @author peter
 *
 */
@Path("/")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class ClassTeacherRestFulAPI {
	
	ClassTeacherService classTeacherService = new ClassTeacherService();
	
	/**
	 * 
	 * @param accountId
	 * @param staffId
	 * @param auth
	 * @return
	 */
	
	
	
	@ApiOperation(value = "Get class teacher object.",  
		    notes = "Returns a class teacher object for the given staffId.",  
		    response = ApiClassTeacher.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Account/staffId not found.") 
	} )

	
	@GET
	@Path("/{accountId}/{staffId}")  
	public Object getClassTeacher(@PathParam("accountId") String accountId, @PathParam("staffId") String staffId ,
			@HeaderParam("authorization") String auth){
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
		
			Response response = new Response();
			response.setMessage("error");
			response.setDescription("User not authenticated");
			return response; 

		}
		
		return classTeacherService.getClassTeacher(accountId, staffId);
	}
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	 
	@ApiOperation(value = "Get lists of class teachers.",  
		    notes = "Returns a list of class teachers.",  
		    response = ApiClassTeacher.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "AccountId not found.") 
	} )
	
	@GET
	@Path("/{accountId}") 
	public Object getClassTeachers(@PathParam("accountId") String accountId,
			@HeaderParam("authorization") String auth){
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
		
			Response response = new Response();
			response.setMessage("error");
			response.setDescription("User not authenticated");
			return response; 

		}
		
		return classTeacherService.getClassTeachers(accountId);
	}

	/**
	 * 
	 * @param obj
	 * @param auth
	 * @return
	 */
	 
	
	@ApiOperation(value = "Assign staff a class.", 
		    notes = "Returns the class was assigned or not.", 
		    response = Response.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Resource not found!.") 
	} )
	@POST
	public Object addClassTeacher(ApiClassTeacher obj, @HeaderParam("authorization") String auth){
		

		if(!RestAUth.isUserAuthenticated(auth, obj.getAccountId())){
		
			Response response = new Response();
			response.setMessage("error");
			response.setDescription("User not authenticated");
			return response; 

		}
		
		return classTeacherService.addClassTeacher(obj);
	}
	
	/**
	 * 
	 * @param obj
	 * @param auth
	 * @return
	 */
	 
	@ApiOperation(value = "Update staff_class.", 
		    notes = "Returns the object was updated or not.", 
		    response = Response.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Resource not found!.") 
	} )
	@PUT
	public Object updateClassTeacher(ApiClassTeacher obj, @HeaderParam("authorization") String auth){
		

		if(!RestAUth.isUserAuthenticated(auth, obj.getAccountId())){
		
			Response response = new Response();
			response.setMessage("error");
			response.setDescription("User not authenticated");
			return response; 

		}
		
		return classTeacherService.updateClassTeacher(obj); 
	}
	
	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @param auth
	 * @return
	 */
	 
	@DELETE
	@Path("/{accountId}/{uuid}")
	@ApiOperation(value = "Delete class teacher.",  
    notes = "Returns whethet class teacher was deleted successfully.", 
    response = Response.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "AccountId not found.") 
	} )
	
	public Object deleteClassTeacher(@PathParam("accountId") String accountId, @PathParam("uuid") String uuid,
			@HeaderParam("authorization") String auth){
		

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
		
			Response response = new Response();
			response.setMessage("error");
			response.setDescription("User not authenticated");
			return response; 

		}
		
		return classTeacherService.deleteClassTeacher(accountId, uuid);
	}


}
