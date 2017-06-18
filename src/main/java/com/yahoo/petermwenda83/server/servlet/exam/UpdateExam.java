/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.exam;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.exam.Exam;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**  
 * @author peter
 *
 */
public class UpdateExam extends HttpServlet{


	final String ERROR_EMPTY_FIELD = "Empty fields are not allowed.";
	final String UPDATE_SUCCESS = "Details successfully updated.";
	final String UPDATE_ERROR = "Something went wrong while updating the details.";
	final String INVALID_OUT_OF = "The 'Out Of' provided is invalid, it must be a numeric greater than 30 and less tha 100 .";

	private static ExamDAO examDAO;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		examDAO = ExamDAO.getInstance();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String schoolAccountUuid = StringUtils.trimToEmpty(request.getParameter("schoolUuid"));
		String examName = StringUtils.trimToEmpty(request.getParameter("examName"));
		String outOf = StringUtils.trimToEmpty(request.getParameter("examOutOf"));
		String uuid = StringUtils.trimToEmpty(request.getParameter("examUuid"));

		if(StringUtils.isEmpty(schoolAccountUuid)){
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_FIELD); 

		}else if(StringUtils.isEmpty(examName)){ 
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_FIELD); 

		}else if(StringUtils.isEmpty(outOf)){
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_FIELD); 

		}else if(StringUtils.isEmpty(uuid)){  
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_FIELD); 

		}else if(!isNumeric(outOf)){  
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, INVALID_OUT_OF); 

		}else if(Integer.parseInt(outOf) < 30 || Integer.parseInt(outOf) > 100){  
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, INVALID_OUT_OF); 

		}else{

			if(examDAO.getExam(uuid) !=null){
				Exam exam = examDAO.getExam(uuid);
				exam.setSchoolAccountUuid(schoolAccountUuid);
				exam.setUuid(uuid);
				exam.setOutOf(Integer.parseInt(outOf));
				if(examDAO.updateExam(exam)){
					session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS, UPDATE_SUCCESS); 
				}else{
					session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, UPDATE_ERROR); 
				}
			}

			response.sendRedirect("examConfig.jsp"); 
			return;
		}
	}


	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {  
		try  
		{  
			double d = Double.parseDouble(str);  

		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -8475300658857183078L;
}
