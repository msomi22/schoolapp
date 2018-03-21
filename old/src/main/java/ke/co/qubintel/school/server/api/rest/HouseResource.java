/**
 * 
 */
package ke.co.qubintel.school.server.api.rest;

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

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import ke.co.qubintel.school.server.api.rest.auth.RestAUth;
import ke.co.qubintel.school.server.api.rest.bean.ApiHouse;
import ke.co.qubintel.school.server.api.rest.bean.ApiStudentHouse;
import ke.co.qubintel.school.server.api.rest.bean.Response;

/**
 * @author peter
 *
 */

@Path("/house") 
@Api(value = "/house") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class HouseResource {


	HouseService houseService = new HouseService();

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
	@Path("/all/{accountId}")  
	public Object getHouseList(
			@PathParam("accountId")String accountId, 
			@HeaderParam("authorization") String auth) {

		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		} else {
			return houseService.getHouseList(accountId);
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
	@Path("/single/{accountId}/{houseName}")  
	public Object getHouse(
			@PathParam("accountId")String accountId, 
			@PathParam("houseName")String houseName, 
			@HeaderParam("authorization") String auth) {

		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		} else {
			return houseService.getHouse(accountId, houseName);
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
	@Path("/new")  
	public Object putHouse(
			ApiHouse apiHouse, 
			@HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, apiHouse.getAccountId())){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		} else {
			return houseService.putHouse(apiHouse);
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
	@Path("/update")   
	public Object getUpdateHouse(
			ApiHouse apiHouse, 
			@HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, apiHouse.getAccountId())){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		} else {
			return houseService.getUpdateHouse(apiHouse);
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
	@Path("/delete/{accountId}/{uuid}")   
	public Object deleteHouse(
			@PathParam("accountId")String accountId, 
			@PathParam("uuid")String uuid, 
			@HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		} else {
			return houseService.deleteHouse(accountId,uuid);
		}
	}


	////////////////////////////////////////////////////////////////// 


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
	@Path("/student/assign/single")  
	public Object AssignHouse(
			ApiStudentHouse apiStudentHouse,  
			@HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, apiStudentHouse.getAccountId())){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		} else {
			return houseService.AssignHouse(apiStudentHouse);
		}
	}

	/**
	 * 
	 * @param accountId
	 * @param list
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Assign house to a student.", 
			notes = "Return whether the house was assigned successfully.", 
			response = ApiStudentHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@POST
	@Path("/student/assign/many/{accountId}")  
	public Object AssignHouseList(
			@PathParam("accountId")String accountId, 
			List<ApiStudentHouse> list,  
			@HeaderParam("authorization") String auth) {

		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		} else {
			return houseService.AssignHouseList(list);
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
	@Path("/student/change/single")  
	public Object changeHouse(
			ApiStudentHouse apiStudentHouse, 
			@HeaderParam("authorization") String auth) {

		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, apiStudentHouse.getAccountId())){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		} else {
			return houseService.changeHouse(apiStudentHouse); 
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
	@Path("/student/delete/single/{accountId}/{uuid}")  
	public Object exitAssignedHouse(
			@PathParam("accountId")String accountId, 
			@PathParam("uuid")String uuid,  
			@HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		} else {
			return houseService.exitAssignedHouse(accountId, uuid);
		}
	}

	/**
	 * 
	 * @param accountId
	 * @param list
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
	@Path("/student/delete/many/{accountId}")  
	public Object exitAssignedHouseList(
			@PathParam("accountId")String accountId,
			List<ApiStudentHouse> list,
			@PathParam("uuid")String uuid,  
			@HeaderParam("authorization") String auth) {
		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		} else {
			return houseService.exitAssignedHouseList(list);  
		}
	}



	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @param auth
	 * @return
	 */
	 
	@ApiOperation(value = "Get student-house Object", 
			notes = "Return students-house Object.", 
			response = ApiStudentHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Id(s) not found.")  
	} )

	@GET
	@Path("/student/get/{accountId}/{studentId}")  
	public Object getstudentHouse(
			@PathParam("accountId")String accountId, 
			@PathParam("studentId")String studentId,
			@HeaderParam("authorization") String auth) {

		Response response = new Response();

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		}else {
			return houseService.getStudentHouse(accountId, studentId);
		}
	}



	/**
	 * 
	 * @param accountId
	 * @param houseId
	 * @param streamId
	 * @return
	 */
	@ApiOperation(value = "Filter students-house by houseId and/or streamId.", 
			notes = "Return students-house List.", 
			response = ApiStudentHouse.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Id(s) not found.")  
	} )

	@GET
	@Path("/student/filter/{accountId}/{houseId}/{streamId}")  
	public Object studentHouseFilter(
			@PathParam("accountId")String accountId, 
			@PathParam("houseId")String houseId,
			@PathParam("streamId")String streamId,
			@HeaderParam("authorization") String auth) {

		Response response = new Response();


		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			response.setMessage("error");
			response.setDescription("User not authenticated!");
			return response; 

		} else {

			if(StringUtils.equals(houseId, "1")) {
				houseId = "";
			}

			if(StringUtils.equals(streamId, "1")) {
				streamId = "";
			}

			return houseService.studentHouseFilter(accountId, houseId, streamId);
		}
	}

}
