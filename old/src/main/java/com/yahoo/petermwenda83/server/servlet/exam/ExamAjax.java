package com.yahoo.petermwenda83.server.servlet.exam;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yahoo.petermwenda83.bean.exam.Exam;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamEgineDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class ExamAjax extends HttpServlet{
	
	private static ExamDAO examDAO;
	private static ExamEgineDAO examEgineDAO;

	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
        
		examDAO = ExamDAO.getInstance();
		examEgineDAO = ExamEgineDAO.getInstance();
       
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		OutputStream out = response.getOutputStream();
		response.setContentType("application/json;charset=UTF-8");

		String studentId = StringUtils.trimToEmpty(request.getParameter("studentId"));
		String subjectId = StringUtils.trimToEmpty(request.getParameter("subjectId"));
		String examId = StringUtils.trimToEmpty(request.getParameter("examId"));
		String score = StringUtils.trimToEmpty(request.getParameter("score"));
		String decision = StringUtils.trimToEmpty(request.getParameter("decision"));

		String accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID); 

		Gson gson = new GsonBuilder().disableHtmlEscaping()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting().serializeNulls().create();


		if(StringUtils.equalsIgnoreCase(decision, "submitExam")){

			out.write(gson.toJson(processData(accountId,studentId, subjectId,examId,score)).getBytes());
			out.flush();
			out.close();

		}

	}

	/**
	 * @param accountId
	 * @param studentId
	 * @param subjectId
	 * @param examId
	 * @param score
	 * @return
	 */
	private JsonElement processData(String accountId,String studentId, String subjectId, String examId, String score) {
 
		JsonObject jsonObject = new JsonObject();
		
		if(StringUtils.isBlank(studentId)){

			jsonObject.addProperty("responseMessage", "Unexpected error occured, no studentId.");
			
		}else if(StringUtils.isBlank(subjectId)){
			
			jsonObject.addProperty("responseMessage", "Unexpected error occured, no subjectId.");

		}else if(StringUtils.isBlank(examId)){
			
			jsonObject.addProperty("responseMessage", "Unexpected error occured, no examId.");

		}else if(StringUtils.isBlank(accountId)){
			
			jsonObject.addProperty("responseMessage", "Unexpected error occured, no accountId.");

		}else if(StringUtils.isBlank(score)){
			
			jsonObject.addProperty("responseMessage", "Blank score not allowed.");

		}else if(!StringUtils.isNumeric(score)){  
			
			jsonObject.addProperty("responseMessage", "Score not valid, numerics only.");
			
		}else if(Integer.valueOf(score) < 0 || Integer.valueOf(score) > 100){  
			
			jsonObject.addProperty("responseMessage", "Score not valid, scores should be between 0 and 100.");
			
		}else if(Integer.valueOf(score) > examDAO.getExam(accountId, examId).getOutOf()){  
			
			jsonObject.addProperty("responseMessage", "Score not allowed.");
			
		}else{
			
			
			Exam exam = examDAO.getExam(accountId, examId);
		
			int scoreInt = Integer.valueOf(score);
			
			double scoreDouble = (scoreInt / exam.getOutOf()) * 100; 
			
			scoreDouble = Math.ceil(scoreDouble);
			
			
		
			jsonObject.addProperty("responseMessage", "Score saved successfully." + scoreDouble);
			
		}

		return jsonObject;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1828953756187958674L;


}
