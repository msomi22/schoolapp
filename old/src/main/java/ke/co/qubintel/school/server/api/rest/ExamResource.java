/**
 * 
 */
package ke.co.qubintel.school.server.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import ke.co.qubintel.school.server.api.rest.auth.RestAUth;
import ke.co.qubintel.school.server.api.rest.bean.Response;
import ke.co.qubintel.school.server.api.rest.bean.StudentExam;
import ke.co.qubintel.school.server.api.rest.bean.SubmitExam;

/**
 * @author peter
 *
 */

@Path("/exam") 
@Api(value = "/exam") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class ExamResource {
	
	ExamService examService = new ExamService();

	/**
	 * 
	 * @param accountId
	 * @param streamId
	 * @param subjectId
	 * @param examUuid
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Get Students List.", 
			notes = "Returns Students List.", 
			response = StudentExam.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@GET
	@Path("/student/{accountId}/{streamId}/{subjectId}/{examUuid}")   
	public Object getStudents(@PathParam("accountId") String accountId, @PathParam("streamId") String streamId, 
			    @PathParam("subjectId") String subjectId, @PathParam("examUuid") String examUuid,
			    @HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return examService.getStudents(accountId, streamId, subjectId, examUuid);
	}
	
	
	/**
	 * 
	 * @param accountId
	 * @param submitExam
	 * @param auth
	 * @return
	 */
	@ApiOperation(value = "Submit student score.", 
			notes = "Return whether score was submited.",  
			response = Response.class)

	@ApiResponses( { @io.swagger.annotations.ApiResponse(code = 404, message = "accountId not found.") 
	} )
	@POST
	@Path("/submit/{accountId}")    
	public Object submitExamScore(@PathParam("accountId") String accountId, SubmitExam submitExam,
			    @HeaderParam("authorization") String auth) {

		Response response = new Response();
		response.setMessage("error");
		response.setDescription("User not authenticated");

		if(!RestAUth.isUserAuthenticated(auth, accountId)){
			return response; 
		}

		return examService.submitScore(submitExam);
	}

}
