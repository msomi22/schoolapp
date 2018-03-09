/**
 * 
 */
package ke.co.qubintel.school.server.api;

import java.util.*;

import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;
import ke.co.qubintel.school.server.api.rest.ClassTeacherRestFulAPI;
import ke.co.qubintel.school.server.api.rest.ConfigRestFulAPI;
import ke.co.qubintel.school.server.api.rest.ExamResourceAPI;
import ke.co.qubintel.school.server.api.rest.FinanceRestFulAPI;
import ke.co.qubintel.school.server.api.rest.GeneralRestFulAPI;
import ke.co.qubintel.school.server.api.rest.ReportRestFulAPI;
import ke.co.qubintel.school.server.api.rest.StaffRestFulAPI;
import ke.co.qubintel.school.server.api.rest.StudentRestFulAPI;
import ke.co.qubintel.school.server.api.rest.SubClassRestFulAPI;
import ke.co.qubintel.school.server.api.rest.admin.AdminRestFulAPI;
import ke.co.qubintel.school.server.api.safaricom.SafaricomAPI;

/**
 * @author peter
 *
 */
public class APIApplication extends Application{

	HashSet<Object> singletons = new HashSet<Object>();

	public APIApplication(){
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.2");
		beanConfig.setSchemes(new String[]{"https,http"});
		beanConfig.setHost("localhost:8080/school");
		beanConfig.setBasePath("/webapi");
		beanConfig.setFilterClass("com.yahoo.petermwenda83.server.api.ApiAuthorizationFilterImpl");
		beanConfig.setResourcePackage("com.yahoo.petermwenda83.server.api.rest");
		beanConfig.setScan(true);

	}
	
	 @Override
	    public Set<Class<?>> getClasses() {
	        HashSet<Class<?>> set = new HashSet<Class<?>>();

	        set.add(StaffRestFulAPI.class);
	        set.add(SubClassRestFulAPI.class);
	        set.add(ClassTeacherRestFulAPI.class);
	        
	        set.add(StudentRestFulAPI.class);
	        set.add(SafaricomAPI.class);
	        set.add(GeneralRestFulAPI.class);
	        set.add(AdminRestFulAPI.class);
	        set.add(ReportRestFulAPI.class);
	        set.add(ConfigRestFulAPI.class);
	        set.add(FinanceRestFulAPI.class);
	        
	        set.add(ExamResourceAPI.class); 
	        
	        
	        set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
	        set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

	        return set;
	    }
	 
	 @Override
	    public Set<Object> getSingletons() {
	        return singletons;
	    }

}
