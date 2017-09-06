/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.admin;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;

/**
 * 
 * http://192.168.43.69:8080/school/webapi/admin/data
 * @author peter
 *
 */
@Path("/admin") 
@Api(value = "/admin") 
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML}) 
public class AdminRestFulAPI {
	
	AdminService adminService = new AdminService();
	
	
	@GET
	@Path("/data")  
	public List<ApiAccData> getAccData() { 
		return adminService.getAccData();
	}

	
	@POST
	@Path("/data")  
	public String getAccFromLopy(String data) {
		System.out.println(data); 
		adminService.putData(data);
		return data;
	}
	
	

}
