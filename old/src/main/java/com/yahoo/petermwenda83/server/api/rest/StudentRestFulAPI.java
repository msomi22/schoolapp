/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;



import java.util.List;
import javax.ws.rs.BeanParam;

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

import com.yahoo.petermwenda83.bean.otherfee.RevertedMoney;
import com.yahoo.petermwenda83.server.api.filter.StudentFilter;
import com.yahoo.petermwenda83.server.api.rest.bean.APIStudent;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiSubject;
import com.yahoo.petermwenda83.server.api.rest.bean.ChangeClass;
import com.yahoo.petermwenda83.server.api.rest.bean.GoKeMoney;
import com.yahoo.petermwenda83.server.api.rest.bean.Response;
import com.yahoo.petermwenda83.server.api.rest.bean.APIOtherFee;
import com.yahoo.petermwenda83.server.api.rest.bean.APIRevertGoKeFee;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentFeeAPI;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentStatus;
import com.yahoo.petermwenda83.server.api.rest.bean.UpdateFee;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentInfo;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentPayFee;
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

	@ApiOperation(value = "Get students basic info .", 
			notes = "Returns Student object .", 
			response = APIStudent.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "student or account Id not found!") 
	} )

	@GET
	@Path("/one/{accountId}/{studentId}")  
	public Object getStudentById(@PathParam("accountId") String accountId, 
			@PathParam("studentId") String studentId , @HeaderParam("authorization") String auth) { 

		
		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.getStudentById(accountId, studentId);  
	}

	
	
	@ApiOperation(value = "Get students per stream for the given stream Id.", 
			notes = "Returns List of students in the given sream.", 
			response = APIStudent.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Stream Id or account Id not found.") 
	} )

	@GET
	@Path("/{accountId}/{sreamId}") 
	public Object getStudentPerStream(@PathParam("accountId") String accountId, 
			@PathParam("sreamId") String sreamId , @HeaderParam("authorization") String auth) { 

		ApiResponse response = new ApiResponse();
		response.setMessage("error");
		response.setDescription("User not authenticated");


		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.getStudentPerStream(accountId,sreamId);  
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
			response = StudentFeeAPI.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId or regNo not found.") 
	} )
	@GET 
	@Path("/fee/{accountId}/{regNo}") 
	public Object getStudentFeeInfo(@PathParam("accountId") String accountId, 
			@PathParam("regNo") String regNo , @HeaderParam("authorization") String auth) { 

		
		Response response = new Response(); 
		response.setMessage("error");
		response.setDescription("User not authenticated");
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.getStudentFee(accountId,regNo);  
	}

	
	

	/**
	 * 
	 * @param accountId
	 * @param regNo
	 * @param auth
	 * @return
	 */

	@ApiOperation(value = "Get student RevertedMoney Info.", 
			notes = "Return Student RevertedMoney Info object.", 
			response = RevertedMoney.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId/studentId not found.") 
	} )
	@GET 
	@Path("/fee/{accountId}/{studentId}/{size}")   
	public Object getStudentOtherFeeLastRecord(@PathParam("accountId") String accountId, 
			@PathParam("studentId") String studentId, @PathParam("size")int size , @HeaderParam("authorization") String auth) { 

		
		Response response = new Response(); 
		response.setMessage("error");
		response.setDescription("User not authenticated");
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.getStudentOtherFeeLatsRecord(accountId, studentId, size);
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
			notes = "Returns whether fee was paid successfully or not.", 
			response = Response.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId or regNo not found.") 
	} )
	@POST
	@Path("/fee/{accountId}/{regNo}")  
	public Object payFee(@PathParam("accountId") String accountId, 
			@PathParam("regNo") String regNo ,StudentPayFee studentPayFee, @HeaderParam("authorization") String auth) {


		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.payFee(studentPayFee); 
	}
	
	
	@ApiOperation(value = "Update Student Fee Info.", 
			notes = "Returns whether Fee was updated successfully.", 
			response = Response.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@PUT
	@Path("/fee/{accountId}")  
	public Object updateStudentFee(@PathParam("accountId") String accountId, 
			UpdateFee updateFeeObj, @HeaderParam("authorization") String auth) {


		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.updateFeeInfo(updateFeeObj);
	}
	
	
	/**
	 * 
	 * @param accountId
	 * @param regNo
	 * @param studentPayFee
	 * @param auth
	 * @return
	 */

	@ApiOperation(value = "Pay GoKe student Fee.", 
			notes = "Returns whether GoKe fee was paid successfully or not.", 
			response = Response.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId found.") 
	} )
	@POST
	@Path("/gokefee/{accountId}")  
	public Object payGoKeFee(@PathParam("accountId") String accountId, GoKeMoney goKeMoney, @HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.asignStudentGoKeMoney(goKeMoney);
	}
	
	
	@ApiOperation(value = "Revert GoKe Fee.", 
			notes = "Returns whether GoKe Fee was Reverted.", 
			response = Response.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId or regNo not found.") 
	} )
	@PUT
	@Path("/gokefee/revert/{accountId}")   
	public Object revertGoKeFee(@PathParam("accountId") String accountId, APIRevertGoKeFee revertGoKeFee,
			@HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.revertGoKeMoney(revertGoKeFee);
	}
	
	
	
	/**
	 * 
	 * @param accountId
	 * @param apiOtherFee
	 * @param auth
	 * @return
	 */
	
	@ApiOperation(value = "Assign Other Fee to a student.", 
			notes = "Returns whether Other Fee was assigned.", 
			response = Response.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@POST
	@Path("/other/fee/{accountId}")   
	public Object assignOtherFee(@PathParam("accountId") String accountId, APIOtherFee apiOtherFee,
			@HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.assignOtherFee(apiOtherFee);
	}
	
	/**
	 * 
	 * @param accountId
	 * @param revertGoKeFee
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Revert Student Other Fee.", 
			notes = "Returns whether Student Other Fee was Reverted.", 
			response = Response.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@PUT
	@Path("/other/fee/revert/{accountId}")    
	public Object revertOtherFee(@PathParam("accountId") String accountId, APIOtherFee apiOtherFee,
			@HeaderParam("authorization") String auth) {
		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.revertOtheFee(apiOtherFee);
	}
	
	
	
	
	
	
	
	
	
	

	/**
	 * 
	 * @param accountId
	 * @param regNo
	 * @param auth
	 * @return
	 */

	@ApiOperation(value = "Get student basic information based on search query.", 
			notes = "Enter a search query (regNo/firstname/middlename/lastname/bcertNo) .", 
			response = StudentInfo.class)
	
	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "Resource not found!") 
	} )
	@GET 
	@Path("/{accountId}") 
	public Object getStudentFilter(@PathParam("accountId") String accountId, 
			@BeanParam  StudentFilter filter, @HeaderParam("authorization") String auth) { 

		ApiResponse response = new ApiResponse(); 
		response.setMessage("error");
		response.setDescription("User not authenticated");
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return studentService.getStudentFilter(accountId,filter);  
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
			response = Response.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )

	@POST
	@Path("/{accountId}")  
	public Object newStudent(@PathParam("accountId") String accountId, StudentInfo student, @HeaderParam("authorization") String auth) {

		
		ApiResponse response = new ApiResponse(); 
		response.setMessage("error");
		response.setDescription("User not authenticated");
		
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
			response = Response.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	
	@PUT
	@Path("/{accountId}")  
	public Object updateStudent(@PathParam("accountId") String accountId, StudentInfo student, @HeaderParam("authorization") String auth) {
		
		ApiResponse response = new ApiResponse(); 
		response.setMessage("error");
		response.setDescription("User not authenticated");

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
	@Path("/subject/{accountId}/{studentId}")    
	public Object getSubject(@HeaderParam("authorization") String auth,
			@PathParam("accountId") String accountId, @PathParam("studentId") String studentId) {
		
		ApiResponse response = new ApiResponse(); 
		response.setMessage("error");
		response.setDescription("User not authenticated");
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response;  
		}
		
		return studentService.getSubjects(accountId,studentId);
	}
	
	
	@ApiOperation(value = "Assign a subject to a student.", 
			notes = "Student subject info object.", 
			response = Response.class)

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
		
		ApiResponse response = new ApiResponse(); 
		response.setMessage("error");
		response.setDescription("User not authenticated");

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
	@ApiOperation(value = "Get List of all subjects.", 
			notes = "Return list of subjects.", 
			response = ApiSubject.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "account not found.") 
	} )
	
	@GET
	@Path("/subjects/{accountId}")   
	public Object getSubjects(@HeaderParam("authorization") String auth, @PathParam("accountId") String accountId) {
		
		ApiResponse response = new ApiResponse(); 
		response.setMessage("error");
		response.setDescription("User not authenticated");
		
		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}
		
		return studentService.getListofSubjects(accountId);  
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
			response = Response.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "account not found.") 
	} )
	
	@DELETE
	@Path("/subject/{accountId}/{uuid}")   
	public Object deleteSubject(@HeaderParam("authorization") String auth, @PathParam("accountId") String accountId, @PathParam("uuid") String uuid) {
		
		ApiResponse response = new ApiResponse(); 
		response.setMessage("error");
		response.setDescription("User not authenticated");


		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}
		
		return studentService.deleteSubject(accountId,uuid);
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
			response = Response.class)

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
			response = Response.class)

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
	
	
	//TODO
	//update student parent
	
	

}
