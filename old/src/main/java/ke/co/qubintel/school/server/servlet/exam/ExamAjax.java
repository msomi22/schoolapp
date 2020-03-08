/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.servlet.exam;

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

import ke.co.qubintel.school.server.api.rest.ExamService;
import ke.co.qubintel.school.server.api.rest.bean.SubmitExam;
import ke.co.qubintel.school.server.bean.classroom.Stream;
import ke.co.qubintel.school.server.bean.exam.Exam;
import ke.co.qubintel.school.server.bean.exam.Perfomance;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.subject.Subject;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.ExamDAO;
import ke.co.qubintel.school.server.persistence.exam.ExamEgineDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.subject.CategoryDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;
import ke.co.qubintel.school.server.servlet.reports.ReportUtil;
import ke.co.qubintel.school.server.session.SessionConstants;

public class ExamAjax extends HttpServlet{

	private static ExamDAO examDAO;
	private static StreamDAO streamDAO;
	private static ExamEgineDAO examEgineDAO;
	private static SysConfigDAO sysConfigDAO;
	private static SubjectDAO subjectDAO;
	private static CategoryDAO categoryDAO;

	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		examDAO = ExamDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		examEgineDAO = ExamEgineDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		categoryDAO = CategoryDAO.getInstance();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		OutputStream out = response.getOutputStream();
		response.setContentType("application/json;charset=UTF-8");

		String studentId = StringUtils.trimToEmpty(request.getParameter("studentId"));
		String subjectId = StringUtils.trimToEmpty(request.getParameter("subjectId"));
		String examId = StringUtils.trimToEmpty(request.getParameter("examId"));
		String streamId = StringUtils.trimToEmpty(request.getParameter("streamId"));
		String score = StringUtils.trimToEmpty(request.getParameter("score"));
		String outof = StringUtils.trimToEmpty(request.getParameter("outof"));
		String decision = StringUtils.trimToEmpty(request.getParameter("decision"));

		outof = StringUtils.trimToEmpty(request.getParameter("outOf"));

		String accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID); 

		Gson gson = new GsonBuilder().disableHtmlEscaping()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting().serializeNulls().create();


		ExamService examService = new ExamService();


		if(StringUtils.equalsIgnoreCase(decision, "submitExam")){

			SubmitExam submitExam = new SubmitExam();
			submitExam.setAccountId(accountId);
			submitExam.setStudentId(studentId);
			submitExam.setSubjectId(subjectId);
			submitExam.setExamId(examId);
			submitExam.setStreamId(streamId);
			submitExam.setScore(Integer.valueOf(score));
			submitExam.setOutof(Integer.valueOf(outof)); 

			//out.write(gson.toJson(examService.saveScore(submitExam)).getBytes());

			out.write(gson.toJson(processData(accountId, studentId, subjectId, examId, streamId, score, outof)).getBytes());

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
	 * @param jwt 
	 * @param userId 
	 * @param jwtSubject 
	 * @return
	 */
	public JsonElement processData(String accountId,String studentId, String subjectId, String examId, 
			String streamId, String score, String outof) {

		JsonObject jsonObject = new JsonObject();
		String message = "";


		if(StringUtils.isBlank(studentId)){

			jsonObject.addProperty("responseMessage", "Unexpected error occured, no studentId.");
			return jsonObject;

		}else if(StringUtils.isBlank(subjectId)){

			jsonObject.addProperty("responseMessage", "Unexpected error occured, no subjectId.");
			return jsonObject;

		}else if(StringUtils.isBlank(examId)){

			jsonObject.addProperty("responseMessage", "Unexpected error occured, no examId.");
			return jsonObject;

		}else if(StringUtils.isBlank(streamId)){

			jsonObject.addProperty("responseMessage", "Unexpected error occured, no streamId.");
			return jsonObject;

		}else if(StringUtils.isBlank(accountId)){

			jsonObject.addProperty("responseMessage", "Unexpected error occured, no accountId.");
			return jsonObject;

		}else if(StringUtils.isBlank(score)){

			jsonObject.addProperty("responseMessage", "Blank score not allowed " + score + "." );
			return jsonObject;

		}else if(!StringUtils.isNumeric(score)){  

			jsonObject.addProperty("responseMessage", "Score not valid, numerics only " + score + "." );
			return jsonObject;

		}else if(Integer.valueOf(score) < 0 || Integer.valueOf(score) > 100){  

			jsonObject.addProperty("responseMessage", "Score not valid, scores should be between 0 and 100 " + score + "." );
			return jsonObject;

		}else if(StringUtils.isBlank(outof)){

			jsonObject.addProperty("responseMessage", "Blank outof not allowed " + outof + "." );
			return jsonObject;

		}else if(!StringUtils.isNumeric(outof)){  

			jsonObject.addProperty("responseMessage", "Score  outof not valid, numerics only " + outof + "." );
			return jsonObject;

		}else if(Integer.valueOf(outof) < 0 || Integer.valueOf(outof) > 100){  

			jsonObject.addProperty("responseMessage", "Score outof not valid, outof should be between 0 and 100 " + outof + "." );
			return jsonObject;

		}/*else if(Integer.valueOf(score) > Integer.valueOf(outof)){  

			jsonObject.addProperty("responseMessage", "Score " + score + " cant be geater tha outof " + outof);
			return jsonObject;

		}*/else{


			Exam exam = examDAO.getExam(accountId, examId);
			assert(exam != null);

			SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);
			assert(sysConfig != null);

			Stream stream = streamDAO.getStream(accountId, streamId);
			assert(stream != null);

			int scoreInt = Integer.valueOf(score);

			String catId = subjectDAO.getSubjectById(accountId, subjectId).getCategoryId();
			Subject subject = subjectDAO.getSubjectById(accountId, subjectId);
			String subDesc = categoryDAO.getCategoryById(accountId, catId).getDescription(); 

			double scoreDouble = 0;
			boolean scoreValid = false;

			if(StringUtils.equals(exam.getCode(), "P1") || 
					StringUtils.equals(exam.getCode(), "P2") || 
					StringUtils.equals(exam.getCode(), "P3")) {


				if(StringUtils.equals(subDesc, ReportUtil.CAT_LANG)){

					if(StringUtils.equals(exam.getCode(), "P1") && scoreInt > ReportUtil.LANG_P1_OUTOF){

						message = "Score not valid, Paper 1, Language: score can't be greater than '" + ReportUtil.LANG_P1_OUTOF + "'";
						jsonObject.addProperty("responseMessage", message); 
						return jsonObject;

					}else if(StringUtils.equals(exam.getCode(), "P2") && scoreInt > ReportUtil.LANG_P2_OUTOF){

						message = "Score not valid, Paper 2, Language: score can't be greater than '" + ReportUtil.LANG_P2_OUTOF + "'";
						jsonObject.addProperty("responseMessage", message); 
						return jsonObject;

					}else if(StringUtils.equals(exam.getCode(), "P3") && scoreInt > ReportUtil.LANG_P3_OUTOF){

						message = "Score not valid, Paper 3, Language: score can't be greater than '" + ReportUtil.LANG_P3_OUTOF + "'";
						jsonObject.addProperty("responseMessage", message); 
						return jsonObject;

					}else{
						scoreDouble = scoreInt;
						scoreValid = true;
					}


				}else if(StringUtils.equals(subDesc, ReportUtil.CAT_SCI)){

					if(StringUtils.equals(exam.getCode(), "P1") && scoreInt > ReportUtil.SCI_AGR_P1_OUTOF){

						message = "Score not valid, Paper 1, Science: score can't be greater than '" + ReportUtil.SCI_AGR_P1_OUTOF + "'";
						jsonObject.addProperty("responseMessage", message); 
						return jsonObject;

					}else if(StringUtils.equals(exam.getCode(), "P2") && scoreInt > ReportUtil.SCI_AGR_P2_OUTOF){

						message = "Score not valid, Paper 2, Science: score can't be greater than '" + ReportUtil.SCI_AGR_P2_OUTOF + "'";
						jsonObject.addProperty("responseMessage", message); 
						return jsonObject;

					}else if(StringUtils.equals(exam.getCode(), "P3") && scoreInt > ReportUtil.SCI_AGR_P3_OUTOF){

						message = "Score not valid, Paper 3, Science: score can't be greater than '" + ReportUtil.SCI_AGR_P3_OUTOF + "'";
						jsonObject.addProperty("responseMessage", message); 
						return jsonObject;

					}else{
						scoreDouble = scoreInt;
						scoreValid = true;
					}

				}else if(StringUtils.equals(subDesc, ReportUtil.CAT_HUM) || StringUtils.equals(subDesc, ReportUtil.CAT_MATH)){

					if(StringUtils.equals(exam.getCode(), "P3")){

						message = "This subject has no paper 3";
						jsonObject.addProperty("responseMessage", message); 
						return jsonObject;

					}else if(StringUtils.equals(exam.getCode(), "P1") && scoreInt > ReportUtil.HUMAN_TECH_MATH_P1_OUTOF){

						message = "Score not valid, Paper 1, Humanity/maths: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P1_OUTOF + "'";
						jsonObject.addProperty("responseMessage", message); 
						return jsonObject;

					}else if(StringUtils.equals(exam.getCode(), "P2") && scoreInt > ReportUtil.HUMAN_TECH_MATH_P2_OUTOF){

						message = "Score not valid, Paper 2, Humanity/maths: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P2_OUTOF + "'";
						jsonObject.addProperty("responseMessage", message); 
						return jsonObject;

					}else{
						scoreDouble = scoreInt;
						scoreValid = true;
					}

				}else if(StringUtils.equals(subDesc, ReportUtil.CAT_TECH)){ 

					if(StringUtils.equals(subject.getCode(), "AGR") || 
							StringUtils.equals(subject.getDescription(), "Agriculture") ||
							StringUtils.equals(subject.getCode(), "HSC") || 
							StringUtils.equals(subject.getDescription(), "Home Science") ||
							StringUtils.equals(subject.getCode(), "COM") || 
							StringUtils.equals(subject.getDescription(), "Computer Studies")){

						if(StringUtils.equals(exam.getCode(), "P1") && scoreInt > ReportUtil.SCI_AGR_P1_OUTOF){

							message = "Score not valid, Paper 1, Agriculture score can't be greater than '" + ReportUtil.SCI_AGR_P1_OUTOF + "'";
							jsonObject.addProperty("responseMessage", message); 
							return jsonObject;

						}else if(StringUtils.equals(exam.getCode(), "P2") && scoreInt > ReportUtil.SCI_AGR_P2_OUTOF){

							message = "Score not valid, Paper 2, Agriculture score can't be greater than '" + ReportUtil.SCI_AGR_P2_OUTOF + "'";
							jsonObject.addProperty("responseMessage", message); 
							return jsonObject;

						}else if(StringUtils.equals(exam.getCode(), "P3") && scoreInt > ReportUtil.SCI_AGR_P3_OUTOF){

							message = "Score not valid, Paper 3 Agriculture, score can't be greater than '" + ReportUtil.SCI_AGR_P3_OUTOF + "'";
							jsonObject.addProperty("responseMessage", message); 
							return jsonObject;

						}else{
							scoreDouble = scoreInt;
							scoreValid = true;
						}

					}else{

						if(StringUtils.equals(exam.getCode(), "P3")){

							message = "This subject has no paper 3";
							jsonObject.addProperty("responseMessage", message); 
							return jsonObject;

						}else if(StringUtils.equals(exam.getCode(), "P1") && scoreInt > ReportUtil.HUMAN_TECH_MATH_P1_OUTOF){

							message = "Score not valid, Paper 1, Technical: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P1_OUTOF + "'";
							jsonObject.addProperty("responseMessage", message); 
							return jsonObject;

						}else if(StringUtils.equals(exam.getCode(), "P2") && scoreInt > ReportUtil.HUMAN_TECH_MATH_P2_OUTOF){

							message = "Score not valid, Paper 2, Technical: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P2_OUTOF + "'";
							jsonObject.addProperty("responseMessage", message); 
							return jsonObject;

						}else{
							scoreDouble = scoreInt;
							scoreValid = true;
						}

					}

				}

			}else{


				if(Integer.valueOf(score) > Integer.valueOf(outof)){  

					jsonObject.addProperty("responseMessage", "Score not allowed " + score + "." );
					return jsonObject;

				}else{

					scoreDouble = ((double)scoreInt / (double)Integer.valueOf(outof)) * 100; 

					scoreDouble = Math.ceil(scoreDouble);
					scoreValid = true;
				}


			}




			try {

				Thread.sleep(2000);

				Perfomance perfomance = new Perfomance();
				perfomance.setAccountId(accountId);
				perfomance.setClassRoomId(stream.getClassRoomId()); 


				String p1 = "AE24F15B-5038-4A15-8607-1DB2A7A0B7DE";
				String p2 = "4531A31D-1F8A-40D7-BFE6-D3CB3D91951A";
				String p3 = "69A569CA-1D4F-458E-99DD-FB2BE705BF5C";
				//PAPER_1_2_3_ID

				//TODO
				if(StringUtils.equals(examId, p1)) {

					examId = "C3915245-00EE-4EF4-9898-ACE59683DD60";
					if(examEgineDAO.getPerformance(accountId, examId, studentId, streamId, sysConfig.getTerm(), sysConfig.getYear(), subjectId) != null) {
						perfomance = examEgineDAO.getPerformance(accountId, examId, studentId, streamId, sysConfig.getTerm(), sysConfig.getYear(), subjectId);
					}
					perfomance.setPaper1((int)scoreDouble);

				}else if(StringUtils.equals(examId, p2)) {

					examId = "C3915245-00EE-4EF4-9898-ACE59683DD60";
					if(examEgineDAO.getPerformance(accountId, examId, studentId, streamId, sysConfig.getTerm(), sysConfig.getYear(), subjectId) != null) {
						perfomance = examEgineDAO.getPerformance(accountId, examId, studentId, streamId, sysConfig.getTerm(), sysConfig.getYear(), subjectId);
					}
					perfomance.setPaper2((int)scoreDouble);

				}else if(StringUtils.equals(examId, p3)) {

					examId = "C3915245-00EE-4EF4-9898-ACE59683DD60";
					if(examEgineDAO.getPerformance(accountId, examId, studentId, streamId, sysConfig.getTerm(), sysConfig.getYear(), subjectId) != null) {
						perfomance = examEgineDAO.getPerformance(accountId, examId, studentId, streamId, sysConfig.getTerm(), sysConfig.getYear(), subjectId);
					}
					perfomance.setPaper3((int)scoreDouble);

				}else {
					if(examEgineDAO.getPerformance(accountId, examId, studentId, streamId, sysConfig.getTerm(), sysConfig.getYear(), subjectId) != null) {
						perfomance = examEgineDAO.getPerformance(accountId, examId, studentId, streamId, sysConfig.getTerm(), sysConfig.getYear(), subjectId);
					}
					perfomance.setScore((int)scoreDouble);
				}


				perfomance.setExamId(examId); 
				perfomance.setStreamId(streamId);
				perfomance.setStudentId(studentId);
				perfomance.setSubjectId(subjectId);
				perfomance.setTerm(sysConfig.getTerm());
				perfomance.setYear(sysConfig.getYear()); 

				if(scoreValid){

					boolean stored = false;

					stored = examEgineDAO.putPerfomance(perfomance, accountId, studentId, subjectId, examId, 
							sysConfig.getTerm(), sysConfig.getYear(), streamId);


					if(stored){

						message = "OK";

						jsonObject.addProperty("responseMessage", message + " -- " + stored);
						return jsonObject;

					}else{

						jsonObject.addProperty("responseMessage", "Unexpected error has occured, contact admin please.");
						return jsonObject;

					}




				}else{

					message = message.length() == 0 ? "Unexpected error has occured, contact admin please." : message;

					jsonObject.addProperty("responseMessage", message);
					return jsonObject;

				}


			} catch (InterruptedException e) {
				e.printStackTrace();
			} 


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
