/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.school.staff;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.staff.ClassTeacher;
import com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class DeleteTeacherClasss  extends HttpServlet{

	final String CLASS_DELETED_SUCCESS = "The teacher was successfully removed from class teacher's register.";
	final String CLASS_DELETE_ERROR = "Something went wrong while removing the teacher from class teacher's register.";

	private static ClassTeacherDAO classTeacherDAO;

	/**   
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		classTeacherDAO = ClassTeacherDAO.getInstance();

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String currentclassuuid = StringUtils.trimToEmpty(request.getParameter("currentclassroomuuid"));
		String currentclssteacher = StringUtils.trimToEmpty(request.getParameter("teacheruuid"));

		 
	       if(StringUtils.isBlank(currentclassuuid)){
	    	   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, CLASS_DELETE_ERROR); 
	    	   
	       }else if(StringUtils.isBlank(currentclssteacher)){
	    	   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, CLASS_DELETE_ERROR); 
	    	   
	       }else{
	    	   ClassTeacher classTeacher = new ClassTeacher();
	    	   classTeacher.setClassRoomUuid(currentclassuuid);
	    	   classTeacher.setTeacherUuid(currentclssteacher); 
	    	   
	    	   if(classTeacherDAO.deleteClassTeacher(classTeacher)){
	    		   session.setAttribute(SessionConstants.PROMOTE_CALSS_SUCCESS, CLASS_DELETED_SUCCESS); 
	    	   }else{
	    		   session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, CLASS_DELETE_ERROR); 
	    	   }
	    	   
	    	 }
	       response.sendRedirect("classTeachers.jsp");  
		   return;

	}



	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 5771580789942495692L;
}
