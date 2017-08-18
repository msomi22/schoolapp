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

import com.yahoo.petermwenda83.server.api.rest.bean.APITeacherSubject;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.SubClass;

/**
 *
 * @author peter
 *
 */

@Path("/")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class SubClassRestFulAPI {

	StaffService staffService = new StaffService();

	/**
	 * http://localhost:8080/school/webapi/staff/{staffId}/subjects
	 * 
	 * @param subClass
	 * @param auth
	 * @return
	 */
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
	 * @param subClassId
	 * @param subClass
	 * @param auth
	 * @return
	 */
	
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
	 * @param accountId
	 * @param subClassId
	 * @param auth
	 * @return
	 */
	
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
	 * 
	 * @param accountId
	 * @param staffId
	 * @param auth
	 * @return
	 */
	
	@GET
	@Path("/{accountId}") 
	public List<APITeacherSubject> getSubClassList(@PathParam("accountId") String accountId, @PathParam("staffId") String staffId ,
			@HeaderParam("authorization") String auth){
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			
			List<APITeacherSubject> response = new ArrayList<>();

			APITeacherSubject error = new APITeacherSubject();
			error.setMessage("error");
			error.setDescription("User not authenticated"); 
			
			response.add(error);
			
			return response; 

		}
		
		return staffService.getSubjectClassList(staffId); 
	}

	/**
	 * 
		[
    {
        "teacherId": "5498156A-FE83-43F4-9592-36281E377FE4",
        "subjectId": "Chemistry",
        "streamId": "FORM 1 N",
        "uuid": "F754E5B4-5340-41FF-9F48-89C6AB5EC130",
        "accountId": "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
        "allocationDate": 1501141262429
    },
    {
        "teacherId": "5498156A-FE83-43F4-9592-36281E377FE4",
        "subjectId": "Agriculture",
        "streamId": "FORM 1 N",
        "uuid": "CE527ACB-75CF-4B1B-A897-77132DDB0C83",
        "accountId": "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
        "allocationDate": 1501141262429
    },
    {
        "teacherId": "5498156A-FE83-43F4-9592-36281E377FE4",
        "subjectId": "Biology",
        "streamId": "FORM 4 N",
        "uuid": "57DB9162-49FB-4416-AD72-ABCE228AF5B8",
        "accountId": "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
        "allocationDate": 1501141262429
    },
    {
        "teacherId": "5498156A-FE83-43F4-9592-36281E377FE4",
        "subjectId": "Computer Studies",
        "streamId": "FORM 4 N",
        "uuid": "7E37176C-5A27-4617-BD00-B959612D7A9B",
        "accountId": "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
        "allocationDate": 1501141262429
    },
    {
        "teacherId": "5498156A-FE83-43F4-9592-36281E377FE4",
        "subjectId": "Christian Religion",
        "streamId": "FORM 2 S",
        "uuid": "2D78671A-29C0-4B3F-8055-0A7713B848FC",
        "accountId": "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
        "allocationDate": 1501141262429
    },
    {
        "teacherId": "5498156A-FE83-43F4-9592-36281E377FE4",
        "subjectId": "Mathematics",
        "streamId": "FORM 2 N",
        "uuid": "1AE66950-B137-4F2F-B01E-E262162536E0",
        "accountId": "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
        "allocationDate": 1501141262429
    },
    {
        "teacherId": "5498156A-FE83-43F4-9592-36281E377FE4",
        "subjectId": "Business",
        "streamId": "FORM 2 N",
        "uuid": "1760EC73-CD38-4023-8ACC-CEC8CD575861",
        "accountId": "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
        "allocationDate": 1501141262429
    },
    {
        "teacherId": "5498156A-FE83-43F4-9592-36281E377FE4",
        "subjectId": "Physics",
        "streamId": "FORM 3 N",
        "uuid": "AD9566E3-A48E-4E40-ACFA-7C863CFDF4EC",
        "accountId": "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
        "allocationDate": 1501141262429
    },
    {
        "teacherId": "5498156A-FE83-43F4-9592-36281E377FE4",
        "subjectId": "Home Science",
        "streamId": "FORM 1 S",
        "uuid": "011AA1FC-7CC7-41C9-A891-FD058ECF8A1E",
        "accountId": "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
        "allocationDate": 1501141262429
    }
]
	 * 
	 */





}
