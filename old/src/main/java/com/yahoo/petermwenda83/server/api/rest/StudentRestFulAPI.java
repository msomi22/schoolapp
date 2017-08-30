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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yahoo.petermwenda83.server.api.rest.bean.APIStudent;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.FeeResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentPayFee;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

/** 
 * 
 * @author peter
 *
 */
@Path("/student") 
@Api(value = "/student") 
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
	
	@ApiOperation(value = "Get students per stream for the given stream Id.", 
		    notes = "Returns List of students in the given sream.", 
		    response = APIStudent.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Stream Id or account Id not found.") 
	} )
		  
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
	
	/**
	 * 
	 * @param accountId
	 * @param regNo
	 * @param studentPayFee
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Pay student Fee.", 
		    notes = "Whether fee was paid successfully or not.", 
		    response = APIStudent.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId or regNo not found.") 
	} )
	@POST
	@Path("/{accountId}/{regNo}")  
	public FeeResponse studentPayFee(@PathParam("accountId") String accountId, 
			@PathParam("regNo") String regNo ,StudentPayFee studentPayFee, @HeaderParam("authorization") String auth) {
		
		FeeResponse feeResponse = new FeeResponse();
		
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("error");
		apiResponse.setDescription("User not authenticated");
		
		feeResponse.setApiResponse(apiResponse); 
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return feeResponse; 
		}
		
		return studentService.payFee(studentPayFee); 
	}
	
	/**
	 * 
	 * @param accountId
	 * @param regNo
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Get student fee basic information.", 
		    notes = "Student basic info object.", 
		    response = APIStudent.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId or regNo not found.") 
	} )
	@GET 
	@Path("/{accountId}/{regNo}") 
	public StudentResponse getStudent(@PathParam("accountId") String accountId, 
			@PathParam("regNo") String regNo , @HeaderParam("authorization") String auth) { 

		StudentResponse  response = new StudentResponse(); 
		
		ApiResponse re = new ApiResponse(); 
		re.setMessage("error");
		re.setDescription("User not authenticated");
		response.setApiResponse(re);
		

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.getStudent(accountId,regNo);  
	}

	

}
