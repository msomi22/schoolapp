/**
 * 
 */
package ke.co.qubintel.school.server.api;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

/**
 * @author peter
 *
 */
public class ApiOriginFilter implements javax.servlet.Filter{

	@Override
	  public void doFilter(ServletRequest request, ServletResponse response,
	      FilterChain chain) throws IOException, ServletException {
		
		
	    HttpServletResponse res = (HttpServletResponse) response;
	    res.addHeader("Access-Control-Allow-Origin", "*");
	    res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
	    //res.addHeader("Access-Control-Allow-Headers", "Content-Type");
	    res.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
	    chain.doFilter(request, response);
	    
	  }

	  @Override
	  public void destroy() {
	  }

	  @Override
	  public void init(FilterConfig filterConfig) throws ServletException {
	  }
}
