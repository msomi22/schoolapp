/**
 * 
 */
package ke.co.qubintel.school.server.api.rest;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import ke.co.qubintel.school.server.api.rest.bean.ApiExam;
import ke.co.qubintel.school.server.api.rest.bean.ApiHouse;
import ke.co.qubintel.school.server.api.rest.bean.ApiResponse;
import ke.co.qubintel.school.server.api.rest.bean.ApiStream;
import ke.co.qubintel.school.server.api.rest.bean.ApiStudentHouse;
import ke.co.qubintel.school.server.api.rest.bean.Response;
import ke.co.qubintel.school.server.api.rest.bean.SmsExams;
import ke.co.qubintel.school.server.servlet.reports.PerStudentSMSResult;

/**
 * @author peter
 *
 */

@Path("/general") 
@Api(value = "/general") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class GeneralRestFulAPI {

	GeneralService generalService = new GeneralService();

	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get stream list.", 
			notes = "Stream details.", 
			response = ApiStream.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )

	@GET
	@Path("/stream/{accountId}")  
	public List<Object> getAllStream(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth) {

		List<Object>  response = new ArrayList<>();

		ApiResponse re = new ApiResponse();
		re.setMessage("error");
		re.setDescription("User not authenticated");

		response.add(re);

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.getStreamList(accountId); 
	}

	
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get class list.", 
			notes = "Class details.", 
			response = ApiStream.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )

	@GET
	@Path("/class/{accountId}")  
	public List<Object> getAllClassRooms(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth) {

		List<Object>  response = new ArrayList<>();

		ApiResponse re = new ApiResponse();
		re.setMessage("error");
		re.setDescription("User not authenticated");

		response.add(re);

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.getClassList(accountId); 
	}

	
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get stream list per class.", 
			notes = "Returns stream list for the given class.", 
			response = ApiStream.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )

	@GET
	@Path("/streams/{accountId}/{classId}")   
	public Object getStreamPerClass(@PathParam("accountId") String accountId,@PathParam("classId") String classId, 
			@HeaderParam("authorization") String auth) {
		
		//System.out.println(accountId + " -- " + classId); 

		ApiResponse re = new ApiResponse();
		re.setMessage("error");
		re.setDescription("User not authenticated");


		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return re; 
		}

		return generalService.getStreamListPerClass(accountId,classId); 
	}

	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get stream object.", 
			notes = "Stream details.", 
			response = ApiStream.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )

	@GET
	@Path("/stream/{accountId}/{uuid}")   
	public Object getStream(@PathParam("accountId") String accountId, @PathParam("uuid") String uuid,
			@HeaderParam("authorization") String auth) {

		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.getStream(accountId,uuid);
	}

	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param apiStream
	 * @return
	 */

	@ApiOperation(value = "Add new stream.", 
			notes = "Stream basic details.", 
			response = ApiResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )

	@POST
	@Path("/stream/{accountId}")  
	public Object newStream(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth, ApiStream apiStream) {

		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.putStream(apiStream);
	}

	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param apiStream
	 * @return
	 */

	@ApiOperation(value = "Update stream info.", 
			notes = "Stream details.", 
			response = ApiResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )

	@PUT
	@Path("/stream/{accountId}")  
	public Object updateStream(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth, ApiStream apiStream) {

		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.updateStream(apiStream);
	}

	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "Delete a stream.", 
			notes = "Stream id.", 
			response = ApiResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )

	@DELETE
	@Path("/stream/delete/{accountId}/{uuid}")  
	public Object deleteStream(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth, @PathParam("uuid") String uuid) {

		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.deleteStream(accountId,uuid);
	}


	
	/**
	 * 
	 * @param accountId
	 * @param examId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Return ApiExam object.", 
			notes = "Pass account and exam Ids.", 
			response = ApiExam.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@GET
	@Path("/exam/{accountId}/{examId}")  
	public Object getExam(@PathParam("accountId") String accountId,@PathParam("examId") String examId,
			@HeaderParam("authorization") String auth) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}
		
		
		return generalService.getExam(accountId, examId); 
	}
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Return List of ApiExam objects.", 
			notes = "Pass account Id.", 
			response = ApiExam.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@GET
	@Path("/exam/{accountId}")  
	public List<Object> getExams(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth) { 
		
		List<Object> list = new ArrayList<>();
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");
		list.add(response);

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return list;  
		}
		
		

		return generalService.getExams(accountId); 
	}
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param apiExam
	 * @return
	 */
	
	@ApiOperation(value = "ApiExam object to add.", 
			notes = "Pass ApiExam object to be added.", 
			response = ApiResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	
	@POST 
	@Path("/exam/{accountId}")  
	public Object newExam(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth,
			ApiExam apiExam) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.newExam(apiExam); 
	}
	

	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param apiExam
	 * @return
	 */

	@ApiOperation(value = "ApiExam object to update.", 
			notes = "Pass ApiExam object to be updated.", 
			response = ApiResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	
	@PUT
	@Path("/exam/{accountId}")  
	public Object updateExam(@PathParam("accountId") String accountId, @HeaderParam("authorization") String auth, 
			ApiExam apiExam) {
		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return generalService.updateExam(apiExam); 
	}
	
	
	
	@ApiOperation(value = "Get stream list.", 
			notes = "Stream details.", 
			response = ApiResponse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )

	@POST 
	@Path("/result/{accountId}/{regNo}/{examType}/{subjectsNo}")     
	public Object getStudentExamResult(@PathParam("subjectsNo") String subjectsNo, @PathParam("examType") String examType,
			@PathParam("regNo") String regNo, @PathParam("accountId") String accountId,
			@HeaderParam("authorization") String auth, List<SmsExams> exams ) {

		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated!");
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
			
		}else if(!PerStudentSMSResult.valideRequest(accountId, regNo, subjectsNo, examType)) {
		
			response.setMessage("error");
			response.setDescription("Invalid Parameters!");
			return response; 
			
		}else if(!PerStudentSMSResult.validaExams(accountId,exams)){
			response.setMessage("error");
			response.setDescription("Invalid Exams!");
			return response; 
		}else {
			
			boolean sub7  = false;
			if(Integer.valueOf(subjectsNo) == 7) {
				sub7 = true;
			}
			
			return PerStudentSMSResult.processResult(accountId, regNo, sub7, examType ,exams);
			
		}
	}

	
	//////////////////////////////////////////////////////////////////// TODO
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "get an house info List.", 
			notes = "Return an house List.", 
			response = ApiHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@GET
	@Path("/house/{accountId}")  
	public Object getHouseList(@PathParam("accountId")String accountId, @HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.getHouseList(accountId);
		}
	}
	
	/**
	 * 
	 * @param accountId
	 * @param houseName
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "get an house info.", 
			notes = "Return an house object.", 
			response = ApiHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@GET
	@Path("/house/{accountId}/{houseName}")  
	public Object getHouse(@PathParam("accountId")String accountId, @PathParam("houseName")String houseName, 
			      @HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.getHouse(accountId, houseName);
		}
	}
	
	/**
	 * 
	 * @param apiHouse
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Add an house.", 
			notes = "Return whether the house was added successfully.", 
			response = ApiHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@POST
	@Path("/house/new")  
	public Object putHouse(ApiHouse apiHouse, @HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, apiHouse.getAccountId())){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.putHouse(apiHouse);
		}
	}
	
	/**
	 * 
	 * @param apiHouse
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Update an house.", 
			notes = "Return whether the house was updated successfully.", 
			response = ApiHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@PUT
	@Path("/house/update")   
	public Object getUpdateHouse(ApiHouse apiHouse, @HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, apiHouse.getAccountId())){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.getUpdateHouse(apiHouse);
		}
	}
	
	/**
	 * 
	 * @param apiHouse
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Delete an house.", 
			notes = "Return whether the house was deleted successfully.", 
			response = ApiHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@DELETE
	@Path("/house/delete/{accountId}/{uuid}")   
	public Object deleteHouse(@PathParam("accountId")String accountId, @PathParam("uuid")String uuid, 
			@HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.deleteHouse(accountId,uuid);
		}
	}
	
	
	////////////////////////////////////////////////////////////////// TODO
	/**
	 * 
	 * @param apiStudentHouse
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Assign house to a student.", 
			notes = "Return whether the house was assigned successfully.", 
			response = ApiStudentHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@POST
	@Path("/house/student/new")  
	public Object AssignHouse(ApiStudentHouse apiStudentHouse,  @HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, apiStudentHouse.getAccountId())){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.AssignHouse(apiStudentHouse);
		}
	}
	
	@ApiOperation(value = "Assign house to a student.", 
			notes = "Return whether the house was assigned successfully.", 
			response = ApiStudentHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@POST
	@Path("/house/student/new/{accountId}")  
	public Object AssignHouseList(@PathParam("accountId")String accountId, List<ApiStudentHouse> list,  @HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.AssignHouseList(list);
		}
	}
	/**
	 * 
	 * @param apiStudentHouse
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Change house assigned to a student.", 
			notes = "Return whether the house was changed successfully.", 
			response = ApiStudentHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@POST
	@Path("/house/student/change")  
	public Object changeHouse(ApiStudentHouse apiStudentHouse, @HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, apiStudentHouse.getAccountId())){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.changeHouse(apiStudentHouse); 
		}
	}
	
	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Exit house assigned to a student.", 
			notes = "Return whether the house was exited successfully.", 
			response = ApiStudentHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@DELETE
	@Path("/house/student/delete/{accountId}/{uuid}")  
	public Object exitAssignedHouse(@PathParam("accountId")String accountId, @PathParam("uuid")String uuid,  
			@HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.exitAssignedHouse(accountId, uuid);
		}
	}
	
	@ApiOperation(value = "Exit house assigned to a student.", 
			notes = "Return whether the house was exited successfully.", 
			response = ApiStudentHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@DELETE
	@Path("/house/student/delete/{accountId}")  
	public Object exitAssignedHouseList(@PathParam("accountId")String accountId,List<ApiStudentHouse> list,
			@PathParam("uuid")String uuid,  
			@HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.exitAssignedHouseList(list);  
		}
	}
	
	/**
	 * 
	 * @param accountId
	 * @param houseId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get list of students per house.", 
			notes = "Return StudentHouse List.", 
			response = ApiStudentHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@GET
	@Path("/house/studentlist/{accountId}/{houseId}")  
	public Object getStudentHouseList(@PathParam("accountId")String accountId, @PathParam("houseId")String houseId,  
			@HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.getStudentHouseList(accountId, houseId);
		}
	}
	
	
	
	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get students - house object.", 
			notes = "Return students - house object.", 
			response = ApiStudentHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@GET
	@Path("/house/student/{accountId}/{studentId}")  
	public Object getStudentHouse(@PathParam("accountId")String accountId, @PathParam("studentId")String studentId,  
			@HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 
			
		} else {
			return generalService.getStudentHouse(accountId, studentId);
		}
	}

	

}
