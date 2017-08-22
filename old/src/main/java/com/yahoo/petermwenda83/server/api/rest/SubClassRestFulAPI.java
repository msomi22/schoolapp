package com.yahoo.petermwenda83.server.api.rest;

import java.util.ArrayList;
import java.util.List;

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
import com.yahoo.petermwenda83.server.api.rest.bean.APITeacherSubject;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.SubClass;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 * @author peter
 *
 */
@Api(value = "/Staff_Subjects")  
@Path("/")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class SubClassRestFulAPI {

	StaffService staffService = new StaffService();

	/**
	 * http://localhost:8080/school/webapi/staff/{staffId}/subjects
	 * 
	 * http://localhost:8080/school/webapi/staff/5498156A-FE83-43F4-9592-36281E377FE4/subjects
	 * 
	 * @param subClass
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Asign subject and class to a staff.", 
		    notes = "Returns whethet subject and class was assigned successfully or not.", 
		    response = SubClass.class)
	@POST
	public ApiResponse addSubject(SubClass subClass, @HeaderParam("authorization") String auth){
		
		if(!RestAUth.isUserAuthenticated(auth, subClass.getAccountId())){

			ApiResponse error = new ApiResponse("error");
			return error; 

		}
		
		return staffService.addSubject(subClass);
	}
	

	/**
	 * http://localhost:8080/school/webapi/staff/{staffId}/subjects/{subjectId}
	 * 
	 * http://localhost:8080/school/webapi/staff/5498156A-FE83-43F4-9592-36281E377FE4/subjects/a8382b24-7154-4722-8c6f-9f1b961a481a
	 * 
	 * @param subClassId
	 * @param subClass
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Updated asigned subject and class for the given staff.", 
		    notes = "Returns whethet subject and class was updated successfully or not.", 
		    response = SubClass.class)
	
	@PUT
	@Path("/{subClassId}")
	public ApiResponse updateComment(@PathParam("subClassId") String subClassId, SubClass subClass, @HeaderParam("authorization") String auth){
		
		if(!RestAUth.isUserAuthenticated(auth, subClass.getAccountId())){

			ApiResponse error = new ApiResponse("error");
			return error; 

		}
		
		return staffService.updateSubjectClass(subClassId, subClass);
	}
	


	/** 
	 * http://localhost:8080/school/webapi/staff/{staffId}/subjects/{sub_class_id}/{accountId} 
	 * 
	 *   e.g 
	 *   
	 * http://localhost:8080/school/webapi/staff/5498156A-FE83-43F4-9592-36281E377FE4/subjects/F754E5B4-5340-41FF-9F48-89C6AB5EC130/E3CDC578-37BA-4CDB-B150-DAB0409270CD  
	 *   
	 * @param accountId
	 * @param subClassId
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Delete subject and class for a staff.", 
		    notes = "Returns whethet subject and class was deleted successfully or not.", 
		    response = SubClass.class)
	
	@DELETE
	@Path("/{subClassId}/{accountId}")
	public ApiResponse deleteComment(@PathParam("accountId") String accountId, @PathParam("subClassId") String subClassId,
			@HeaderParam("authorization") String auth){
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){

			ApiResponse error = new ApiResponse("error");
			return error; 

		}
		
		return staffService.deleteSubjectClass(subClassId); 
	}

	/**  
	 * http://localhost:8080/school/webapi/staff/{staffId}/subjects/{accountId}  
	 *    e.g 
	 * http://localhost:8080/school/webapi/staff/5498156A-FE83-43F4-9592-36281E377FE4/subjects/E3CDC578-37BA-4CDB-B150-DAB0409270CD
	 * 
	 * @param accountId
	 * @param staffId
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Get lists of subject and class for a staff.", 
		    notes = "Returns a list of class and subject.", 
		    response = SubClass.class)
	
	@GET
	@Path("/{accountId}") 
	public List<APITeacherSubject> getSubClassList(@PathParam("accountId") String accountId, @PathParam("staffId") String staffId ,
			@HeaderParam("authorization") String auth){
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			
			List<APITeacherSubject> response = new ArrayList<>();

			ApiResponse respo = new ApiResponse();
			respo.setMessage("error");
			respo.setDescription("User not authenticated");
			
			APISubjectClasss apiR = new APISubjectClasss();
			
			APITeacherSubject error = new APITeacherSubject(respo,apiR);
			error.setResponse(respo); 
			
			
			response.add(error);
			
			return response; 

		}
		
		return staffService.getSubjectClassList(staffId); 
	}

}
