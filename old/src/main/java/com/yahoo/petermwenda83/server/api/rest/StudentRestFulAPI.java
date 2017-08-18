/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;



import java.util.ArrayList;
import java.util.List;

//import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.Authorization;
import com.yahoo.petermwenda83.server.api.rest.bean.APIStudent;

/**
 * 
 * @author peter
 *
 */
@Path("/student") 
@Api(value = "/student", description = "Operations about students")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class StudentRestFulAPI{

	StudentService studentService = new StudentService();

	/**
	 * http://localhost:8080/school/webapi/student/{accountId}/{streamId}
	 * 
	 * @param accountId
	 * @param sreamId
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Get students per class.", 
		    notes = "Returns List of students in the given class.", 
		    response = APIStudent.class,
		    authorizations = @Authorization(value = "api_key", type = "api_key")
		  )
	@GET
	@Path("/{accountId}/{sreamId}") 
	public List<APIStudent> getStudentPerStream(@PathParam("accountId") String accountId, 
			@PathParam("sreamId") String sreamId , @HeaderParam("authorization") String auth) { 

		List<APIStudent>  response = new ArrayList<>();
		APIStudent re = new APIStudent();
		re.setMessage("error");
		re.setDescription("User not authenticated");
		
		response.add(re);

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.getStudentPerStream(accountId,sreamId);  
	}

	

}
