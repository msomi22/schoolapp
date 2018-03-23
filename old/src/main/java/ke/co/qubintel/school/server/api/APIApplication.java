/**
 * 
 */
package ke.co.qubintel.school.server.api;

import java.util.*;

import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;
import ke.co.qubintel.school.server.api.rest.ClassTeacherResource;
import ke.co.qubintel.school.server.api.rest.ConfigResource;
import ke.co.qubintel.school.server.api.rest.ExamResource;
import ke.co.qubintel.school.server.api.rest.FinanceResource;
import ke.co.qubintel.school.server.api.rest.GeneralResource;
import ke.co.qubintel.school.server.api.rest.HouseResource;
import ke.co.qubintel.school.server.api.rest.ReportResource;
import ke.co.qubintel.school.server.api.rest.StaffResource;
import ke.co.qubintel.school.server.api.rest.StudentResource;
import ke.co.qubintel.school.server.api.rest.SubClassResource;
import ke.co.qubintel.school.server.api.rest.admin.AdminResource;
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
		beanConfig.setFilterClass("ke.co.qubintel.school.server.api.ApiAuthorizationFilterImpl");
		beanConfig.setResourcePackage("ke.co.qubintel.school.server.api.rest");
		beanConfig.setScan(true);

	}
	
	 @Override
	    public Set<Class<?>> getClasses() {
	        HashSet<Class<?>> set = new HashSet<Class<?>>();

	        set.add(StaffResource.class);
	        set.add(SubClassResource.class);
	        set.add(ClassTeacherResource.class);
	        
	        set.add(StudentResource.class);
	        set.add(SafaricomAPI.class);
	        set.add(GeneralResource.class);
	        set.add(AdminResource.class);
	        set.add(ReportResource.class);
	        set.add(ConfigResource.class);
	        set.add(FinanceResource.class);
	        
	        set.add(ExamResource.class); 
	        
	        set.add(HouseResource.class); 
	        
	       
	        set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
	        set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

	        return set;
	    }
	 
	 @Override
	    public Set<Object> getSingletons() {
	        return singletons;
	    }

}
