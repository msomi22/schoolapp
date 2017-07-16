/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * http://localhost:8080/school/webapi/student/4DA86139-6A72-4089-8858-6A3A613FDFE6
 * @author peter
 *
 */
@Path("/student") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class SchoolapiImpl{
	
	StudentService studentService = new StudentService();

	@GET
	@Path("/{sreamId}") 
	public List<APIStudent> getStudentPerStream(@PathParam("sreamId") String sreamId) { 		
		return studentService.getStudentPerStream(sreamId);  
	}

}
