/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.exam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO;
import com.yahoo.petermwenda83.server.api.rest.jwt.ApiCredentials;
import com.yahoo.petermwenda83.server.api.rest.jwt.JWT;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**
 * 
 * @author peter
 *
 */
public class GetStudents extends HttpServlet{

	private static StudentDAO studentDAO;
	private static StudentSubjectDAO studentSubjectDAO;

	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		studentDAO = StudentDAO.getInstance();
		studentSubjectDAO = StudentSubjectDAO.getInstance();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String streamId = StringUtils.trimToEmpty(request.getParameter("streamId"));
		String subjectId = StringUtils.trimToEmpty(request.getParameter("subjectId"));
		String examId = StringUtils.trimToEmpty(request.getParameter("examId"));

		String accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID); 

		String jwt = (String) session.getAttribute(SessionConstants.USER_JSON_WEB_TOKEN);  
		String userId = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
		String jwtSubject = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);

		List<Student> selectedStudents = new ArrayList<>();
		Map<String,String> idsMap = new HashMap<>();  
		idsMap.put("streamId", streamId);
		idsMap.put("examId", examId); 
		idsMap.put("subjectId", subjectId); 
		
		/*System.out.println(jwt);
		System.out.println("userId : " + userId);
		System.out.println("jwtSubject : " + jwtSubject);*/
		
		ApiCredentials apiKey = new ApiCredentials();

		if(StringUtils.isEmpty(streamId)){

			session.setAttribute(SessionConstants.GENERIC_ERROR, "Stream not selected! Please select one."); 

		}else if(StringUtils.isEmpty(subjectId)){

			session.setAttribute(SessionConstants.GENERIC_ERROR, "Subject not selected! Please select one."); 

		}else if(StringUtils.isEmpty(examId)){

			session.setAttribute(SessionConstants.GENERIC_ERROR, "Exam not selected! Please select one."); 

		}else if(!JWT.validateJWT(jwt, apiKey.getSecret(), userId, accountId, jwtSubject)){

			session.setAttribute(SessionConstants.GENERIC_ERROR, "Invalid Json Web token.."); 

		}else{

			List<Student> students = new ArrayList<>();


			if(studentDAO.getStudentByStream(accountId, streamId) != null){
				students = studentDAO.getStudentByStream(accountId, streamId);
			}

             
			       students
			       .parallelStream()
			       .filter(s -> StringUtils.equals(s.getIsActive(), "1"))
			       .filter(s -> studentSubjectDAO.getstudentSubject(s.getUuid(), subjectId) != null) 
			       .forEach(student -> {   

			    	   selectedStudents.add(student);

			});


		}
		
		/**
		 * Java provides Comparable interface which should be implemented by any custom class if we want to use 
		 * Arrays or Collections sorting methods.
		 * Comparable interface has compareTo(T obj) method which is used by sorting methods
		 * We should override this method in such a way that it returns a negative integer, zero, or a positive integer 
		 * if “this” object is less than, equal to, or greater than the object passed as argument.
		 * 
		 * Read more on Java Comparator
		 * 
		 * Comparator interface compare(Object o1, Object o2) method need to be implemented that takes two Object argument, 
		 * it should be implemented in such a way that it returns negative int if first argument is less than the second one and 
		 * returns zero if they are equal and positive int if first argument is greater than second one.
		 * 
		 * Read more here ( https://www.journaldev.com/780/comparable-and-comparator-in-java-example ) 
		 */
		Collections.sort(selectedStudents); 

		session.setAttribute(SessionConstants.EXAM_GET_STUDENTS_IDS, idsMap);  
		session.setAttribute(SessionConstants.EXAM_GET_STUDENTS, selectedStudents); 
		response.sendRedirect("submitExam.jsp");  
		return;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -5408398940083360976L;
}
