/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.init;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



/**
 * @author peter
 *
 */
public class Log4jInit extends HttpServlet{

	 private static final long serialVersionUID = 1L;
	    private final Logger logger = Logger.getLogger(this.getClass());
	    
	    
	    /**
	     * @param config
	     * @throws ServletException
	     */
	    public void init(ServletConfig config) throws ServletException {
	    	 super.init(config);

	        initConfig();
	        logger.info("Have initialized log4j");
	    }

	    
	    /**
		 * 
		 * @param request
	      * @param response
	      * @throws ServletException
	      * @throws IOException  
		 */	
	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    	initConfig();
	    	
	    	PrintWriter out = response.getWriter();
	    	out.println("Have reloaded log4j settings.");
	    	out.close();
	    }
	    
	    
	    /**
		 * 
		 * @param request
		 * @param response
	      * @throws ServletException
	      * @throws IOException  
		 */	
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    	doPost(request, response);
	    }
	    
	    
	    /**
	     * 
	     */
	    private void initConfig() {
	    	String prefix = getServletContext().getRealPath("/");
	        String file = getServletConfig().getInitParameter("log4j-init-file");

	        // if the log4j-init-file is not set, then no point in trying
	        if (file != null) {
	            PropertyConfigurator.configure(prefix + file);
	        }
	    }
}
