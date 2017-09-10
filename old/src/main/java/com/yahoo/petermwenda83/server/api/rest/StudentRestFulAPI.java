/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//import java.util.List;

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

import com.yahoo.petermwenda83.server.api.rest.bean.APIStudent;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiSubject;
import com.yahoo.petermwenda83.server.api.rest.bean.ChangeClass;
import com.yahoo.petermwenda83.server.api.rest.bean.FeeResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentStatus;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentInfo;
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
	 * http://localhost:8080/school/webapi/student/subject 
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
			response = StudentPayFee.class)

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
			response = StudentInfo.class)

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

	/**
	 * 
	 * @param accountId
	 * @param student
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Register a new student.", 
			notes = "Student basic info object.", 
			response = StudentInfo.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )

	@POST
	@Path("/{accountId}")  
	public Object newStudent(@PathParam("accountId") String accountId, StudentInfo student, @HeaderParam("authorization") String auth) {

		StudentResponse  response = new StudentResponse(); 

		ApiResponse re = new ApiResponse(); 
		re.setMessage("error");
		re.setDescription("User not authenticated");
		response.setApiResponse(re);


		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.addNewStudent(accountId,student);

	}
	
	/**
	 * 
	 * @param accountId
	 * @param student
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Update student details.", 
			notes = "Student basic info object.", 
			response = StudentInfo.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	
	@PUT
	@Path("/{accountId}")  
	public Object updateStudent(@PathParam("accountId") String accountId, StudentInfo student, @HeaderParam("authorization") String auth) {

		StudentResponse  response = new StudentResponse(); 

		ApiResponse re = new ApiResponse(); 
		re.setMessage("error");
		re.setDescription("User not authenticated");
		response.setApiResponse(re);


		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.updateStudent(accountId, student);

	}
	
	/**
	 * 
	 * @param auth
	 * @param studentId
	 * @param accountId
	 * @return
	 */
	@ApiOperation(value = "Get student's subjects.", 
			notes = "Student subject List.", 
			response = ApiSubject.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "account not found.") 
	} )
	
	@GET
	@Path("/subject/{studentId}/{accountId}")    
	public List<Object> getSubject(@HeaderParam("authorization") String auth, @PathParam("studentId") String studentId,
			@PathParam("accountId") String accountId) {
		
		 List<ApiResponse>  response = new ArrayList<>(); 

		ApiResponse re = new ApiResponse(); 
		re.setMessage("error");
		re.setDescription("User not authenticated");
		response.add(re);


		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response.stream().collect(Collectors.toList());  
		}
		
		return studentService.getSubjects(accountId,studentId);
	}
	
	
	@ApiOperation(value = "Assign a subject to a student.", 
			notes = "Student subject info object.", 
			response = ApiSubject.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "account not found.") 
	} )
	
	/**
	 * 
	 * @param auth
	 * @param apiSubject
	 * @return
	 */
	@POST
	@Path("/subject")   
	public Object addSubject(@HeaderParam("authorization") String auth, ApiSubject apiSubject) {
		
		StudentResponse  response = new StudentResponse(); 

		ApiResponse re = new ApiResponse(); 
		re.setMessage("error");
		re.setDescription("User not authenticated");
		response.setApiResponse(re);


		if(!RestAUth.isUserAuthenticated(auth, apiSubject.getAccountId())){
			return response; 
		}
		
		return studentService.assignSubject(apiSubject);
	}
	
	/**
	 * 
	 * @param auth
	 * @param apiSubject
	 * @return
	 */
	@ApiOperation(value = "Updated student's subject.", 
			notes = "Student subject info object.", 
			response = ApiSubject.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "account not found.") 
	} )
	
	@PUT
	@Path("/subject")   
	public Object updateSubject(@HeaderParam("authorization") String auth, ApiSubject apiSubject) {
		
		StudentResponse  response = new StudentResponse(); 

		ApiResponse re = new ApiResponse(); 
		re.setMessage("error");
		re.setDescription("User not authenticated");
		response.setApiResponse(re);


		if(!RestAUth.isUserAuthenticated(auth, apiSubject.getAccountId())){
			return response; 
		}
		
		return studentService.updateSubject(apiSubject);
	}
	
	/**
	 * 
	 * @param auth
	 * @param accountId
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Delete subject that has been assigned to a student.", 
			notes = "Student_subject_id.", 
			response = ApiSubject.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "account not found.") 
	} )
	
	@DELETE
	@Path("/subject")   
	public Object deleteSubject(@HeaderParam("authorization") String auth, @PathParam("accountId") String accountId, String id) {
		
		StudentResponse  response = new StudentResponse(); 

		ApiResponse re = new ApiResponse(); 
		re.setMessage("error");
		re.setDescription("User not authenticated");
		response.setApiResponse(re);


		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}
		
		return studentService.deleteSubject(accountId,id);
	}
	
	/**
	 * 
	 * @param action
	 * @param accountId
	 * @param auth
	 * @param students
	 * @return
	 */
	@ApiOperation(value = "change student status i.e activate/inactivate etc.", 
			notes = "other student status include isboarding and isalumni .", 
			response = StudentStatus.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "action/account not found.") 
	} )
	
	@PUT
	@Path("/{action}/{accountId}")  
	public Object studentStatus(@PathParam("action") String action, @PathParam("accountId") String accountId, 
			@HeaderParam("authorization") String auth ,List<StudentStatus> students) {  
		
		ApiResponse response = new ApiResponse(); 
		response.setMessage("error");
		response.setDescription("User not authenticated");
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}
		 
		return studentService.studentStatus(accountId,action,students);  
	}
	
	
	
	/**
	 * 
	 * @param accountId
	 * @param auth
	 * @param changeClass
	 * @return
	 */
	@ApiOperation(value = "change student class .", 
			notes = "pass student-change class object ", 
			response = ChangeClass.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "account not found.") 
	} )
	@PUT
	@Path("/changeclass/{accountId}")   
	public Object changeClass(@PathParam("accountId") String accountId, 
			@HeaderParam("authorization") String auth ,List<ChangeClass> changeClass) {
		
		ApiResponse response = new ApiResponse(); 
		response.setMessage("error");
		response.setDescription("User not authenticated");
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}
		
		return studentService.changeClass(accountId, changeClass); 
	}
	
	
	

}
