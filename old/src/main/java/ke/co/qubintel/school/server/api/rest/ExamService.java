/**
 * 
 */
package ke.co.qubintel.school.server.api.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.api.rest.bean.Response;
import ke.co.qubintel.school.server.api.rest.bean.StudentExam;
import ke.co.qubintel.school.server.api.rest.bean.SubmitExam;
import ke.co.qubintel.school.server.bean.classroom.Stream;
import ke.co.qubintel.school.server.bean.exam.Exam;
import ke.co.qubintel.school.server.bean.exam.Perfomance;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.bean.subject.Subject;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.ExamDAO;
import ke.co.qubintel.school.server.persistence.exam.ExamEgineDAO;
import ke.co.qubintel.school.server.persistence.exam.PerfomanceDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;
import ke.co.qubintel.school.server.persistence.student.StudentSubjectDAO;
import ke.co.qubintel.school.server.persistence.subject.CategoryDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;
import ke.co.qubintel.school.server.servlet.reports.ReportUtil;

/**
 * @author peter
 *
 */
public class ExamService {

	private static ExamDAO examDAO;
	private static AccountDAO accountDAO;
	private static StreamDAO streamDAO;
	private static ExamEgineDAO examEgineDAO;
	private static SysConfigDAO sysConfigDAO;
	private static SubjectDAO subjectDAO;
	private static CategoryDAO categoryDAO;

	private static StudentDAO studentDAO;
	private static StudentSubjectDAO studentSubjectDAO;

	private static PerfomanceDAO perfomanceDAO;

	static {
		examDAO = ExamDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		examEgineDAO = ExamEgineDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		categoryDAO = CategoryDAO.getInstance();

		studentDAO = StudentDAO.getInstance();
		studentSubjectDAO = StudentSubjectDAO.getInstance();

		perfomanceDAO = PerfomanceDAO.getInstance();
	}



	/**
	 * 
	 * @param accountId
	 * @param streamId
	 * @param subjectId
	 * @param examUuid
	 * @return
	 */
	public Object getStudents(String accountId,String streamId,String subjectId,String examUuid) {

		List<StudentExam> studentExamList = new ArrayList<>();
		Response response = new Response();

		if(accountDAO.getAccountById(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response; 

		}else if(streamDAO.getStream(accountId, streamId) == null) { 
			response.setMessage("error");
			response.setDescription("Stream not found!");
			return response; 

		}else if(subjectDAO.getSubjectById(accountId, subjectId) == null) {
			response.setMessage("error");
			response.setDescription("Subject not found!");
			return response; 

		}else if(examDAO.getExam(accountId, examUuid) == null) {  
			response.setMessage("error");
			response.setDescription("Exam not found!");
			return response; 

		}else if(studentDAO.getStudentByStream(accountId, streamId).isEmpty()) {  
			response.setMessage("error");
			response.setDescription("No student found!");
			return response; 

		}else if(sysConfigDAO.getSysConfig(accountId) == null) {  
			response.setMessage("error");
			response.setDescription("Config not set!");
			return response; 

		}else {

			List<Student> selectedStudents = new ArrayList<>();

			studentDAO.getStudentByStream(accountId, streamId) 
			.parallelStream()
			.filter(s -> StringUtils.equals(s.getIsActive(), "1"))
			.filter(s -> studentSubjectDAO.getstudentSubject(s.getUuid(), subjectId) != null) 
			.forEach(student -> {   
				selectedStudents.add(student);
			});

			selectedStudents.
			parallelStream().
			forEach(student -> {

				StudentExam studentExam = new StudentExam();
				studentExam.setStudentId(student.getUuid());
				studentExam.setCount(0); 
				studentExam.setRegNo(student.getRegNo());
				studentExam.setFirstname(student.getFirstname());
				studentExam.setMiddlename(student.getMiddlename());
				studentExam.setLastname(student.getLastname());

				String score = "";
				String examId = examUuid;

				String p1 = "AE24F15B-5038-4A15-8607-1DB2A7A0B7DE";
				String p2 = "4531A31D-1F8A-40D7-BFE6-D3CB3D91951A";
				String p3 = "69A569CA-1D4F-458E-99DD-FB2BE705BF5C";

				String p123 = "C3915245-00EE-4EF4-9898-ACE59683DD60";


				if (StringUtils.equals(examId, p1)) {
					examId = p123;

				} else if (StringUtils.equals(examId, p2)) {
					examId = p123;

				} else if (StringUtils.equals(examId, p3)) {
					examId = p123;

				}

				Perfomance perfomance = new Perfomance();
				SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

				if (perfomanceDAO.getPerformance(accountId, examId, student.getUuid(), streamId,
						sysConfig.getTerm(), sysConfig.getYear(), subjectId) != null) {

					perfomance = perfomanceDAO.getPerformance(accountId, examId, student.getUuid(), streamId,
							sysConfig.getTerm(), sysConfig.getYear(), subjectId);

				}

				if (StringUtils.equals(examId, p123)) {

					String paper1 = "";
					String paper2 = "";
					String paper3 = "";

					if (perfomance.getPaper1() > 0) {
						paper1 = "P1: " + perfomance.getPaper1();
					}

					if (perfomance.getPaper2() > 0) {
						paper2 = ", P2: " + perfomance.getPaper2();
					}

					if (perfomance.getPaper3() > 0) {
						paper3 = ", P3: " + perfomance.getPaper3();
					}

					score = paper1 + paper2 + paper3;

				} else {

					score = perfomance.getScore() + "";
				}

				studentExam.setScore(score);

				studentExamList.add(studentExam);


			});

		}

		Collections.sort(studentExamList);
		
		return studentExamList;
	}




	/**
	 * 
	 * @param submitExam
	 * @return
	 */
	public  Object submitScore(SubmitExam submitExam) {

		Response response = new Response();

		if(accountDAO.getAccountById(submitExam.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response; 

		}else if(studentDAO.getStudentById(submitExam.getAccountId(), submitExam.getStudentId()) == null) { 
			response.setMessage("error");
			response.setDescription("Student not found!");
			return response; 

		}else if(subjectDAO.getSubjectById(submitExam.getAccountId(), submitExam.getSubjectId()) == null) {
			response.setMessage("error");
			response.setDescription("Subject not found!");
			return response; 

		}else if(examDAO.getExam(submitExam.getAccountId(), submitExam.getExamId()) == null) {  
			response.setMessage("error");
			response.setDescription("Exam not found!");
			return response; 

		}else if(streamDAO.getStream(submitExam.getAccountId(), submitExam.getStreamId()) == null) { 
			response.setMessage("error");
			response.setDescription("Stream not found!");
			return response; 

		}else if(submitExam.getOutof() < 0){
			response.setMessage("error");
			response.setDescription("Outof less than zero!");
			return response; 

		}else if(submitExam.getOutof() > 100){  
			response.setMessage("error");
			response.setDescription("Outof greater than 100!");
			return response; 

		}else if(submitExam.getScore() < 0){
			response.setMessage("error");
			response.setDescription("Score less than zero!");
			return response; 

		}else if(submitExam.getScore() > 100){  
			response.setMessage("error");
			response.setDescription("Score greater than 100!");
			return response; 

		}else {


			Exam exam = examDAO.getExam(submitExam.getAccountId(), submitExam.getExamId());
			SysConfig sysConfig = sysConfigDAO.getSysConfig(submitExam.getAccountId());
			Stream stream = streamDAO.getStream(submitExam.getAccountId(), submitExam.getStreamId());



			String catId = subjectDAO.getSubjectById(submitExam.getAccountId(), submitExam.getSubjectId()).getCategoryId();
			Subject subject = subjectDAO.getSubjectById(submitExam.getAccountId(), submitExam.getSubjectId()); 
			String subDesc = categoryDAO.getCategoryById(submitExam.getAccountId(), catId).getDescription(); 

			double scoreDouble = 0;
			boolean scoreValid = false;

			if(StringUtils.equals(exam.getCode(), "P1") || 
					StringUtils.equals(exam.getCode(), "P2") || 
					StringUtils.equals(exam.getCode(), "P3")) {


				if(StringUtils.equals(subDesc, ReportUtil.CAT_LANG)){

					if(StringUtils.equals(exam.getCode(), "P1") && submitExam.getScore() > ReportUtil.LANG_P1_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 1, Language: score can't be greater than '" + ReportUtil.LANG_P1_OUTOF + "'");
						return response; 

					}else if(StringUtils.equals(exam.getCode(), "P2") && submitExam.getScore() > ReportUtil.LANG_P2_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 2, Language: score can't be greater than '" + ReportUtil.LANG_P2_OUTOF + "'");
						return response; 

					}else if(StringUtils.equals(exam.getCode(), "P3") && submitExam.getScore() > ReportUtil.LANG_P3_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 3, Language: score can't be greater than '" + ReportUtil.LANG_P3_OUTOF + "'");
						return response;

					}else{
						scoreDouble = submitExam.getScore();
						scoreValid = true;
					}


				}else if(StringUtils.equals(subDesc, ReportUtil.CAT_SCI)){

					if(StringUtils.equals(exam.getCode(), "P1") && submitExam.getScore() > ReportUtil.SCI_AGR_P1_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 1, Science: score can't be greater than '" + ReportUtil.SCI_AGR_P1_OUTOF + "'");
						return response;


					}else if(StringUtils.equals(exam.getCode(), "P2") && submitExam.getScore() > ReportUtil.SCI_AGR_P2_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 2, Science: score can't be greater than '" + ReportUtil.SCI_AGR_P2_OUTOF + "'");
						return response;


					}else if(StringUtils.equals(exam.getCode(), "P3") && submitExam.getScore() > ReportUtil.SCI_AGR_P3_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 3, Science: score can't be greater than '" + ReportUtil.SCI_AGR_P3_OUTOF + "'");
						return response;

					}else{
						scoreDouble = submitExam.getScore();
						scoreValid = true;
					}

				}else if(StringUtils.equals(subDesc, ReportUtil.CAT_HUM) || StringUtils.equals(subDesc, ReportUtil.CAT_MATH)){

					if(StringUtils.equals(exam.getCode(), "P3")){

						response.setMessage("error");
						response.setDescription("This subject has no paper 3");
						return response;


					}else if(StringUtils.equals(exam.getCode(), "P1") && submitExam.getScore() > ReportUtil.HUMAN_TECH_MATH_P1_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 1, Humanity/maths: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P1_OUTOF + "'");
						return response;

					}else if(StringUtils.equals(exam.getCode(), "P2") && submitExam.getScore() > ReportUtil.HUMAN_TECH_MATH_P2_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 2, Humanity/maths: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P2_OUTOF + "'");
						return response;

					}else{
						scoreDouble = submitExam.getScore();
						scoreValid = true;
					}

				}else if(StringUtils.equals(subDesc, ReportUtil.CAT_TECH)){ 

					if(StringUtils.equals(subject.getCode(), "AGR") || 
							StringUtils.equals(subject.getDescription(), "Agriculture") ||
							StringUtils.equals(subject.getCode(), "HSC") || 
							StringUtils.equals(subject.getDescription(), "Home Science") ||
							StringUtils.equals(subject.getCode(), "COM") || 
							StringUtils.equals(subject.getDescription(), "Computer Studies")){

						if(StringUtils.equals(exam.getCode(), "P1") && submitExam.getScore() > ReportUtil.SCI_AGR_P1_OUTOF){

							response.setMessage("error");
							response.setDescription("Score not valid, Paper 1, Agriculture score can't be greater than '" + ReportUtil.SCI_AGR_P1_OUTOF + "'");
							return response;

						}else if(StringUtils.equals(exam.getCode(), "P2") && submitExam.getScore() > ReportUtil.SCI_AGR_P2_OUTOF){

							response.setMessage("error");
							response.setDescription("Score not valid, Paper 2, Agriculture score can't be greater than '" + ReportUtil.SCI_AGR_P2_OUTOF + "'");
							return response;

						}else if(StringUtils.equals(exam.getCode(), "P3") && submitExam.getScore() > ReportUtil.SCI_AGR_P3_OUTOF){

							response.setMessage("error");
							response.setDescription("Score not valid, Paper 3 Agriculture, score can't be greater than '" + ReportUtil.SCI_AGR_P3_OUTOF + "'");
							return response;


						}else{
							scoreDouble = submitExam.getScore();
							scoreValid = true;
						}

					}else{

						if(StringUtils.equals(exam.getCode(), "P3")){

							response.setMessage("error");
							response.setDescription("This subject has no paper 3");
							return response;


						}else if(StringUtils.equals(exam.getCode(), "P1") && submitExam.getScore() > ReportUtil.HUMAN_TECH_MATH_P1_OUTOF){

							response.setMessage("error");
							response.setDescription("Score not valid, Paper 1, Technical: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P1_OUTOF + "'");
							return response;

						}else if(StringUtils.equals(exam.getCode(), "P2") && submitExam.getScore() > ReportUtil.HUMAN_TECH_MATH_P2_OUTOF){

							response.setMessage("error");
							response.setDescription("Score not valid, Paper 2, Technical: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P2_OUTOF + "'");
							return response;


						}else{
							scoreDouble = submitExam.getScore();
							scoreValid = true;
						}

					}

				}

			}else{


				if(submitExam.getScore() > submitExam.getOutof()){  

					response.setMessage("error");
					response.setDescription("Score ' " + submitExam.getScore() + " ' not allowed for Outof '" + submitExam.getOutof() + "'." );
					return response;


				}else{

					scoreDouble = ((double)submitExam.getScore() / (double)submitExam.getOutof()) * 100;  

					scoreDouble = Math.ceil(scoreDouble);
					scoreValid = true;
				}


			}




			Perfomance perfomance = new Perfomance();
			perfomance.setAccountId(submitExam.getAccountId()); 
			perfomance.setClassRoomId(stream.getClassRoomId()); 


			String p1 = "AE24F15B-5038-4A15-8607-1DB2A7A0B7DE";
			String p2 = "4531A31D-1F8A-40D7-BFE6-D3CB3D91951A";
			String p3 = "69A569CA-1D4F-458E-99DD-FB2BE705BF5C";

			String examId = submitExam.getExamId();


			if(StringUtils.equals(submitExam.getExamId(), p1)) {

				examId = "C3915245-00EE-4EF4-9898-ACE59683DD60";

				if(examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
						submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId()) != null) {

					perfomance = examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
							submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId());

				}

				perfomance.setPaper1((int)scoreDouble);

			}else if(StringUtils.equals(examId, p2)) {

				examId = "C3915245-00EE-4EF4-9898-ACE59683DD60";

				if(examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
						submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId()) != null) {

					perfomance = examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
							submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId());
				}

				perfomance.setPaper2((int)scoreDouble);

			}else if(StringUtils.equals(examId, p3)) {

				examId = "C3915245-00EE-4EF4-9898-ACE59683DD60";

				if(examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
						submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId()) != null) {

					perfomance = examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
							submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId());
				}

				perfomance.setPaper3((int)scoreDouble);

			}else {

				if(examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
						submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId()) != null) {

					perfomance = examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
							submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId());
				}
				perfomance.setScore((int)scoreDouble);
			}


			perfomance.setExamId(examId); 
			perfomance.setStreamId(submitExam.getStreamId());
			perfomance.setStudentId(submitExam.getStudentId());
			perfomance.setSubjectId(submitExam.getSubjectId());
			perfomance.setTerm(sysConfig.getTerm());
			perfomance.setYear(sysConfig.getYear()); 

			if(scoreValid){

				boolean stored = false;

				try {

					Thread.sleep(2000);

					stored = examEgineDAO.putPerfomance(perfomance, submitExam.getAccountId(), submitExam.getStudentId(), 
							submitExam.getSubjectId(), examId, 
							sysConfig.getTerm(), sysConfig.getYear(), submitExam.getStreamId()); 

				} catch (InterruptedException e) {

					Thread.currentThread().interrupt();
					System.out.println("InterruptedException : " + e.getMessage());  

				} 

				if(stored){

					response.setMessage("success");
					response.setDescription("Score saved successfully.");
					return response;

				}else{

					response.setMessage("error");
					response.setDescription("Unexpected error has occured, contact admin please.");
					return response;

				}

			}else{

				response.setMessage("error");
				response.setDescription("Unexpected error has occured, contact admin please.");
				return response;

			}

		}




	}

	/**
	 * 
	 * @param submitExam
	 * @return
	 */


	public Object saveScore(SubmitExam submitExam) {

		Response response = new Response();

		if(accountDAO.getAccountById(submitExam.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response; 

		}else if(studentDAO.getStudentById(submitExam.getAccountId(), submitExam.getStudentId()) == null) { 
			response.setMessage("error");
			response.setDescription("Student not found!");
			return response; 

		}else if(subjectDAO.getSubjectById(submitExam.getAccountId(), submitExam.getSubjectId()) == null) {
			response.setMessage("error");
			response.setDescription("Subject not found!");
			return response; 

		}else if(examDAO.getExam(submitExam.getAccountId(), submitExam.getExamId()) == null) {  
			response.setMessage("error");
			response.setDescription("Exam not found!");
			return response; 

		}else if(streamDAO.getStream(submitExam.getAccountId(), submitExam.getStreamId()) == null) { 
			response.setMessage("error");
			response.setDescription("Stream not found!");
			return response; 

		}else if(submitExam.getOutof() < 0){
			response.setMessage("error");
			response.setDescription("Outof less than zero!");
			return response; 

		}else if(submitExam.getOutof() > 100){  
			response.setMessage("error");
			response.setDescription("Outof greater than 100!");
			return response; 

		}else if(submitExam.getScore() < 0){
			response.setMessage("error");
			response.setDescription("Score less than zero!");
			return response; 

		}else if(submitExam.getScore() > 100){  
			response.setMessage("error");
			response.setDescription("Score greater than 100!");
			return response; 

		}else {


			Exam exam = examDAO.getExam(submitExam.getAccountId(), submitExam.getExamId());
			SysConfig sysConfig = sysConfigDAO.getSysConfig(submitExam.getAccountId());
			Stream stream = streamDAO.getStream(submitExam.getAccountId(), submitExam.getStreamId());



			String catId = subjectDAO.getSubjectById(submitExam.getAccountId(), submitExam.getSubjectId()).getCategoryId();
			Subject subject = subjectDAO.getSubjectById(submitExam.getAccountId(), submitExam.getSubjectId()); 
			String subDesc = categoryDAO.getCategoryById(submitExam.getAccountId(), catId).getDescription(); 

			double scoreDouble = 0;
			boolean scoreValid = false;

			if(StringUtils.equals(exam.getCode(), "P1") || 
					StringUtils.equals(exam.getCode(), "P2") || 
					StringUtils.equals(exam.getCode(), "P3")) {


				if(StringUtils.equals(subDesc, ReportUtil.CAT_LANG)){

					if(StringUtils.equals(exam.getCode(), "P1") && submitExam.getScore() > ReportUtil.LANG_P1_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 1, Language: score can't be greater than '" + ReportUtil.LANG_P1_OUTOF + "'");
						return response; 

					}else if(StringUtils.equals(exam.getCode(), "P2") && submitExam.getScore() > ReportUtil.LANG_P2_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 2, Language: score can't be greater than '" + ReportUtil.LANG_P2_OUTOF + "'");
						return response; 

					}else if(StringUtils.equals(exam.getCode(), "P3") && submitExam.getScore() > ReportUtil.LANG_P3_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 3, Language: score can't be greater than '" + ReportUtil.LANG_P3_OUTOF + "'");
						return response;

					}else{
						scoreDouble = submitExam.getScore();
						scoreValid = true;
					}


				}else if(StringUtils.equals(subDesc, ReportUtil.CAT_SCI)){

					if(StringUtils.equals(exam.getCode(), "P1") && submitExam.getScore() > ReportUtil.SCI_AGR_P1_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 1, Science: score can't be greater than '" + ReportUtil.SCI_AGR_P1_OUTOF + "'");
						return response;


					}else if(StringUtils.equals(exam.getCode(), "P2") && submitExam.getScore() > ReportUtil.SCI_AGR_P2_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 2, Science: score can't be greater than '" + ReportUtil.SCI_AGR_P2_OUTOF + "'");
						return response;


					}else if(StringUtils.equals(exam.getCode(), "P3") && submitExam.getScore() > ReportUtil.SCI_AGR_P3_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 3, Science: score can't be greater than '" + ReportUtil.SCI_AGR_P3_OUTOF + "'");
						return response;

					}else{
						scoreDouble = submitExam.getScore();
						scoreValid = true;
					}

				}else if(StringUtils.equals(subDesc, ReportUtil.CAT_HUM) || StringUtils.equals(subDesc, ReportUtil.CAT_MATH)){

					if(StringUtils.equals(exam.getCode(), "P3")){

						response.setMessage("error");
						response.setDescription("This subject has no paper 3");
						return response;


					}else if(StringUtils.equals(exam.getCode(), "P1") && submitExam.getScore() > ReportUtil.HUMAN_TECH_MATH_P1_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 1, Humanity/maths: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P1_OUTOF + "'");
						return response;

					}else if(StringUtils.equals(exam.getCode(), "P2") && submitExam.getScore() > ReportUtil.HUMAN_TECH_MATH_P2_OUTOF){

						response.setMessage("error");
						response.setDescription("Score not valid, Paper 2, Humanity/maths: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P2_OUTOF + "'");
						return response;

					}else{
						scoreDouble = submitExam.getScore();
						scoreValid = true;
					}

				}else if(StringUtils.equals(subDesc, ReportUtil.CAT_TECH)){ 

					if(StringUtils.equals(subject.getCode(), "AGR") || 
							StringUtils.equals(subject.getDescription(), "Agriculture") ||
							StringUtils.equals(subject.getCode(), "HSC") || 
							StringUtils.equals(subject.getDescription(), "Home Science") ||
							StringUtils.equals(subject.getCode(), "COM") || 
							StringUtils.equals(subject.getDescription(), "Computer Studies")){

						if(StringUtils.equals(exam.getCode(), "P1") && submitExam.getScore() > ReportUtil.SCI_AGR_P1_OUTOF){

							response.setMessage("error");
							response.setDescription("Score not valid, Paper 1, Agriculture score can't be greater than '" + ReportUtil.SCI_AGR_P1_OUTOF + "'");
							return response;

						}else if(StringUtils.equals(exam.getCode(), "P2") && submitExam.getScore() > ReportUtil.SCI_AGR_P2_OUTOF){

							response.setMessage("error");
							response.setDescription("Score not valid, Paper 2, Agriculture score can't be greater than '" + ReportUtil.SCI_AGR_P2_OUTOF + "'");
							return response;

						}else if(StringUtils.equals(exam.getCode(), "P3") && submitExam.getScore() > ReportUtil.SCI_AGR_P3_OUTOF){

							response.setMessage("error");
							response.setDescription("Score not valid, Paper 3 Agriculture, score can't be greater than '" + ReportUtil.SCI_AGR_P3_OUTOF + "'");
							return response;


						}else{
							scoreDouble = submitExam.getScore();
							scoreValid = true;
						}

					}else{

						if(StringUtils.equals(exam.getCode(), "P3")){

							response.setMessage("error");
							response.setDescription("This subject has no paper 3");
							return response;


						}else if(StringUtils.equals(exam.getCode(), "P1") && submitExam.getScore() > ReportUtil.HUMAN_TECH_MATH_P1_OUTOF){

							response.setMessage("error");
							response.setDescription("Score not valid, Paper 1, Technical: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P1_OUTOF + "'");
							return response;

						}else if(StringUtils.equals(exam.getCode(), "P2") && submitExam.getScore() > ReportUtil.HUMAN_TECH_MATH_P2_OUTOF){

							response.setMessage("error");
							response.setDescription("Score not valid, Paper 2, Technical: score can't be greater than '" + ReportUtil.HUMAN_TECH_MATH_P2_OUTOF + "'");
							return response;


						}else{
							scoreDouble = submitExam.getScore();
							scoreValid = true;
						}

					}

				}

			}else{


				if(submitExam.getScore() > submitExam.getOutof()){  

					response.setMessage("error");
					response.setDescription("Score ' " + submitExam.getScore() + " ' not allowed for Outof '" + submitExam.getOutof() + "'." );
					return response;


				}else{

					scoreDouble = ((double)submitExam.getScore() / (double)submitExam.getOutof()) * 100;  

					scoreDouble = Math.ceil(scoreDouble);
					scoreValid = true;
				}


			}




			//THIS BE delayed TODO

			try {

				Thread.sleep(2000);


				Perfomance perfomance = new Perfomance();
				perfomance.setAccountId(submitExam.getAccountId()); 
				perfomance.setClassRoomId(stream.getClassRoomId()); 


				String p1 = "AE24F15B-5038-4A15-8607-1DB2A7A0B7DE";
				String p2 = "4531A31D-1F8A-40D7-BFE6-D3CB3D91951A";
				String p3 = "69A569CA-1D4F-458E-99DD-FB2BE705BF5C";

				String examId = submitExam.getExamId();


				if(StringUtils.equals(submitExam.getExamId(), p1)) {

					examId = "C3915245-00EE-4EF4-9898-ACE59683DD60";

					if(examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
							submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId()) != null) {

						perfomance = examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
								submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId());

					}

					perfomance.setPaper1((int)scoreDouble);

				}else if(StringUtils.equals(examId, p2)) {

					examId = "C3915245-00EE-4EF4-9898-ACE59683DD60";

					if(examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
							submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId()) != null) {

						perfomance = examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
								submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId());
					}

					perfomance.setPaper2((int)scoreDouble);

				}else if(StringUtils.equals(examId, p3)) {

					examId = "C3915245-00EE-4EF4-9898-ACE59683DD60";

					if(examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
							submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId()) != null) {

						perfomance = examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
								submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId());
					}

					perfomance.setPaper3((int)scoreDouble);

				}else {

					if(examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
							submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId()) != null) {

						perfomance = examEgineDAO.getPerformance(submitExam.getAccountId(), examId, submitExam.getStudentId(), 
								submitExam.getStreamId(), sysConfig.getTerm(), sysConfig.getYear(), submitExam.getSubjectId());
					}
					perfomance.setScore((int)scoreDouble);
				}


				perfomance.setExamId(examId); 
				perfomance.setStreamId(submitExam.getStreamId());
				perfomance.setStudentId(submitExam.getStudentId());
				perfomance.setSubjectId(submitExam.getSubjectId());
				perfomance.setTerm(sysConfig.getTerm());
				perfomance.setYear(sysConfig.getYear()); 

				if(scoreValid){

					boolean stored = false;

					stored = examEgineDAO.putPerfomance(perfomance, submitExam.getAccountId(), submitExam.getStudentId(), 
							submitExam.getSubjectId(), examId, 
							sysConfig.getTerm(), sysConfig.getYear(), submitExam.getStreamId()); 


					if(stored){

						response.setMessage("success");
						response.setDescription("Score saved successfully.");
						return response;

					}else{

						response.setMessage("error");
						response.setDescription("Unexpected error has occured, contact admin please.");
						return response;

					}

				}else{

					response.setMessage("error");
					response.setDescription("Unexpected error has occured, contact admin please.");
					return response;

				}

			} catch (InterruptedException e) {

				Thread.currentThread().interrupt();

				response.setMessage("error");
				response.setDescription("InterruptedException : " + e.getMessage());
				return response;

			} 

			//delayed end TODO

		}

	}















}
