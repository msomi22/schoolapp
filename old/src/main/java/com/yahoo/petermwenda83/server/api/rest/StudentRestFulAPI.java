/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;



//import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.yahoo.petermwenda83.server.api.rest.bean.AuthErr;

/**
 * 
 * http://localhost:8080/school/webapi/student/E3CDC578-37BA-4CDB-B150-DAB0409270CD/4DA86139-6A72-4089-8858-6A3A613FDFE6
 * 
 * @author peter
 *
 */
@Path("/student") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class StudentRestFulAPI{

	StudentService studentService = new StudentService();

	@GET
	@Path("/{accountId}/{sreamId}") //List<APIStudent>
	public String getStudentPerStream(@PathParam("accountId") String accountId, 
			@PathParam("sreamId") String sreamId , @HeaderParam("authorization") String auth) { 

		Gson gson = new Gson();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			AuthErr error = new AuthErr("error");
			return gson.toJson(error); 
		}

		return gson.toJson(studentService.getStudentPerStream(accountId,sreamId));  
	}

	

}
