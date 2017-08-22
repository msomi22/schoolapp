/**
 * 
 */
package com.yahoo.petermwenda83.server.api;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import io.swagger.models.*;
import io.swagger.models.auth.OAuth2Definition;


/**
 * @author peter
 *
 */
public class Bootstrap extends HttpServlet{

	@Override
	  public void init(ServletConfig config) throws ServletException {
	    Info info = new Info()
	            .title("School Swagger REST API")
	            .description("This is APPLE-TECH School API Server.")
	            .termsOfService("http://swagger.io/terms/")
	            .contact(new Contact()
	                    .email("apiteam@swagger.io"))
	            .license(new License()
	                    .name("Apache 2.0")
	                    .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

	    ServletContext context = config.getServletContext();
	    Swagger swagger = new Swagger()
	            .info(info);
	    
	    swagger.securityDefinition("apple_tech_auth",
	            new OAuth2Definition()
	                    .implicit("http://localhost:8080/school/oauth/dialog")
	                    .scope("email", "Access to your email address"));
	                   // .scope("staff", "Access to staff"));
	    
	    swagger.tag(new Tag()
	            .name("School")
	            .description("Everything about AppleTech School RESTFUL API") 
	            .externalDocs(new ExternalDocs("Find out more", "http://www.appletech.co.ke")));
	    
	    context.setAttribute("swagger", swagger);
	   
	  }
	
	
	

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
}
