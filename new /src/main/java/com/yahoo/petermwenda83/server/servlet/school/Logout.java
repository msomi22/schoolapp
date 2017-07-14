/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.school;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yahoo.petermwenda83.server.session.SessionConstants;


/**
 * 
 * @author peter
 *
 */
 
public class Logout extends HttpServlet {

	
	private Map<String,String> onlineUsersMap;
	private ServletContext context;
	
	

    
    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        onlineUsersMap = new HashMap<String,String>();
		context = getServletContext();
    }
    

    /**
     *
     * @param request
     * @param response
     * @throws ServletException, IOException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    
    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        response.sendRedirect("index.jsp");

        if (session != null) {
            // Remove the statistics of this user from cache
           // String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
            String userId = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
            //Account school = new Account();
           
            onlineUsersMap =  (HashMap<String,String>)context.getAttribute("onlineUsersMap");
            onlineUsersMap.remove(userId);

            //destroy the session
            session.invalidate();
            
            
        } // end 'if (session != null) '
        
    }
    

    /**
	 * 
	 */
	private static final long serialVersionUID = -3607520645710832503L;
}
