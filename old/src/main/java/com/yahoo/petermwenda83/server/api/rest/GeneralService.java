/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.account.Miscellanous;
import com.yahoo.petermwenda83.bean.classroom.Stream;
import com.yahoo.petermwenda83.bean.exam.Exam;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.FeeBreakdown;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.ClassDAO;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.MiscellanousDAO;
import com.yahoo.petermwenda83.persistence.staff.AcessLevelDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.CategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.api.ApiConstants;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiClass;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiExam;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiGradingScale;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiMisc;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiStream;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiSysConfig;
import com.yahoo.petermwenda83.server.api.rest.bean.Response;
import com.yahoo.petermwenda83.server.servlet.finance.FeeConstants;
import com.yahoo.petermwenda83.server.servlet.reports.PerStudentSMSResult;
import com.yahoo.petermwenda83.server.servlet.reports.Performance2;
import com.yahoo.petermwenda83.server.servlet.reports.ReportUtil;

/**
 * @author peter
 *
 */
public class GeneralService {

	private static StreamDAO streamDAO;
	private static ClassDAO classDAO;
	private static AccountDAO accountDAO;
	private static ExamDAO examDAO;
	private static SysConfigDAO sysConfigDAO;
	private static MiscellanousDAO miscellanousDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static TermFeeDAO termFeeDAO;
	private static AcessLevelDAO acessLevelDAO;
	private static FeeBreakdownDAO feeBreakdownDAO;
	private static CategoryDAO categoryDAO;


	private static SubjectDAO subjectDAO;
	private static StudentDAO studentDAO;



	static {
		streamDAO = StreamDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		examDAO = ExamDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		miscellanousDAO = MiscellanousDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		acessLevelDAO = AcessLevelDAO.getInstance();
		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
		categoryDAO = CategoryDAO.getInstance();

		classDAO = ClassDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		studentDAO = StudentDAO.getInstance();


	}
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public List<Object> getStreamList(String accountId) {


		if(streamDAO.getStreamList(accountId) != null) {

			List<Object>  list = new ArrayList<>();
			//.filter(student -> studentId.equals(student.getStudentId()))
			streamDAO.
			getStreamList(accountId).
			stream().
			filter(stm -> !"SYS_DEFAULT_STREAM".equals(stm.getDescription())).
			forEach(stream -> { 
				ApiStream apiStream = new ApiStream();
				int count = studentDAO.classStudentCount(accountId, stream.getUuid(), "1");
				apiStream.setAccountId(stream.getAccountId());
				apiStream.setClassRoomId(stream.getClassRoomId());
				apiStream.setDescription(stream.getDescription() + " (" + count + ") "); 
				apiStream.setUuid(stream.getUuid()); 

				list.add(apiStream);
			});

			return list;

		}else {

			List<Object>  response = new ArrayList<>();
			ApiResponse re = new ApiResponse();
			re.setMessage("error");
			re.setDescription("No stream to display.");

			response.add(re);
			return response;

		}

	}


	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public List<Object> getClassList(String accountId) {

		if(classDAO.getClassRooms(accountId)!= null) {

			List<Object>  list = new ArrayList<>();
			classDAO.getClassRooms(accountId).forEach(stream -> {
				ApiClass apiClass = new ApiClass();
				apiClass.setAccountId(stream.getAccountId());
				apiClass.setDescription(stream.getDescription());
				apiClass.setUuid(stream.getUuid()); 

				list.add(apiClass);
			});

			return list;

		}else {

			List<Object>  response = new ArrayList<>();
			ApiResponse re = new ApiResponse();
			re.setMessage("error");
			re.setDescription("No stream to display.");

			response.add(re);
			return response;

		}

	}


	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public Object getStreamListPerClass(String accountId, String classId) {

		//System.out.println(streamDAO.getStreamList(accountId,classId)); 

		if(streamDAO.getStreamList(accountId,classId) != null) {

			List<ApiStream>  list = new ArrayList<>();

			streamDAO.
			getStreamList(accountId,classId).stream().
			filter(stm -> !"SYS_DEFAULT_STREAM".equals(stm.getDescription())).
			forEach(stream -> {

				int count = studentDAO.classStudentCount(accountId, stream.getUuid(), "1");
				ApiStream apiStream = new ApiStream();
				apiStream.setAccountId(stream.getAccountId());
				apiStream.setClassRoomId(stream.getClassRoomId());
				apiStream.setDescription(stream.getDescription() + " (" + count + ") "); 
				apiStream.setUuid(stream.getUuid()); 

				list.add(apiStream);
			});

			return list;

		}else {

			ApiResponse re = new ApiResponse();
			re.setMessage("error");
			re.setDescription("No stream to display.");

			return re;

		}

	}




	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @return
	 */
	public Object getStream(String accountId, String uuid) {

		ApiResponse apiResponse = new ApiResponse();

		if(streamDAO.getStream(accountId, uuid) != null) {

			int count = studentDAO.classStudentCount(accountId, uuid, "1");
			ApiStream apiStream = new ApiStream();
			apiStream.setAccountId(streamDAO.getStream(accountId, uuid).getAccountId());
			apiStream.setClassRoomId(streamDAO.getStream(accountId, uuid).getClassRoomId());
			apiStream.setDescription(streamDAO.getStream(accountId, uuid).getDescription() + " (" + count + ") ");
			apiStream.setUuid(streamDAO.getStream(accountId, uuid).getUuid());

			return apiStream; 

		}else {

			apiResponse.setMessage("error");
			apiResponse.setDescription("No stream to display.");

			return apiResponse; 
		}


	}

	/**
	 * 
	 * @param apiStream
	 * @return
	 */
	public Object putStream(ApiStream apiStream) {

		ApiResponse apiResponse = new ApiResponse();

		if(accountDAO.getAccountById(apiStream.getAccountId()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account not found!");
			return apiResponse;

		}else if(streamDAO.getStreamByDesc(apiStream.getAccountId(), apiStream.getDescription()) != null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("A stream with such a name already exist! '" + apiStream.getDescription() + "'");  
			return apiResponse;

		}else if(!validSream(apiStream.getDescription())){
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid Stream Description!");
			return apiResponse;

		}else if(classDAO.getClassRoom(apiStream.getAccountId(), apiStream.getClassRoomId()) == null){
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid Class!");
			return apiResponse;

		}
		else {
			Stream stream = new Stream();
			stream.setAccountId(apiStream.getAccountId());
			stream.setClassRoomId(apiStream.getClassRoomId());
			String classroom = classDAO.getClassRoom(apiStream.getAccountId(), apiStream.getClassRoomId()).getDescription();
			stream.setDescription(classroom + " " + apiStream.getDescription());

			if(streamDAO.putStream(stream)) {
				apiResponse.setMessage("success");
				apiResponse.setDescription("Stream added successfully.");

			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Try again later or contact Admin.");

			}

		}

		return apiResponse;
	}


	/**
	 * 
	 * @param apiStream
	 * @return
	 */
	public Object updateStream(ApiStream apiStream) {

		ApiResponse apiResponse = new ApiResponse();

		if(accountDAO.getAccountById(apiStream.getAccountId()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account not found!");
			return apiResponse;

		}else if(streamDAO.getStream(apiStream.getAccountId(), apiStream.getUuid()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Stream not found!");
			return apiResponse;

		}else if(!validSream(apiStream.getDescription())){
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid Stream Description!");
			return apiResponse;

		}else if(classDAO.getClassRoom(apiStream.getAccountId(), apiStream.getClassRoomId()) == null){
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid Class!");
			return apiResponse;

		}else if(streamHasDuplicate(apiStream)){
			apiResponse.setMessage("error");
			apiResponse.setDescription("No duplicate description!");
			return apiResponse;

		}else {

			Stream stream = streamDAO.getStream(apiStream.getAccountId(), apiStream.getUuid());
			stream.setClassRoomId(apiStream.getClassRoomId());
			String classroom = classDAO.getClassRoom(apiStream.getAccountId(), apiStream.getClassRoomId()).getDescription();
			stream.setDescription(classroom + " " + apiStream.getDescription());

			if(streamDAO.updateStream(stream)) { 
				apiResponse.setMessage("success");
				apiResponse.setDescription("Stream updated successfully.");

			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Try again later or contact Admin.");

			}


		}


		return apiResponse;
	}

	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @return
	 */
	public Object deleteStream(String accountId, String uuid) {

		ApiResponse apiResponse = new ApiResponse();

		if(streamDAO.getStream(accountId, uuid) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid account/Stream Id(s)!"+ uuid + "  "+accountId);  
			return apiResponse;

		}else {

			if(streamDAO.deleteStream(accountId, uuid)) {
				apiResponse.setMessage("success");
				apiResponse.setDescription("Stream removed successfully!");  

			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Something went wrong, contact Admin or try again later."); 
			}

		}



		return apiResponse;
	}

	/**
	 * 
	 * @param accountId
	 * @param examId
	 * @return
	 */
	public Object getExam(String accountId, String examId){

		ApiResponse apiResponse = new ApiResponse();

		if(examDAO.getExam(accountId, examId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Exam not found!");
			return apiResponse;

		}else {

			Exam exam = examDAO.getExam(accountId, examId);

			ApiExam apiExam = new ApiExam();
			apiExam.setUuid(exam.getUuid());
			apiExam.setAccountId(accountId);
			apiExam.setCode(exam.getCode());
			apiExam.setDescription(exam.getDescription());
			apiExam.setOutOf(exam.getOutOf()); 

			return apiExam;
		}

	}

	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public List<Object> getExams(String accountId){
		List<Object> apiExamList = new ArrayList<>();

		if(examDAO.getExamList(accountId) != null) {
			examDAO.getExamList(accountId).
			stream().filter(exm -> !"P123".equals(exm.getDescription())).
			forEach(exam -> {
				ApiExam apiExam = new ApiExam();
				apiExam.setUuid(exam.getUuid());
				apiExam.setAccountId(accountId);
				apiExam.setCode(exam.getCode());
				apiExam.setDescription(exam.getDescription());
				apiExam.setOutOf(exam.getOutOf()); 
				apiExamList.add(apiExam);
			});
		}

		return apiExamList;
	}

	/**
	 * 
	 * @param apiExam
	 * @return
	 */
	public Object newExam(ApiExam apiExam) {

		Response response = new Response();

		if(apiExam.getCode().length() < 2) {
			response.setMessage("error");
			response.setDescription("Invalid exam code!");

		}else if(apiExam.getDescription().length() < 4) {
			response.setMessage("error");
			response.setDescription("Invalid exam description!");

		}else if(StringUtils.isBlank(String.valueOf(apiExam.getOutOf()))) { 
			response.setMessage("error");
			response.setDescription("Invalid exam ouOf!");

		}else if(!StringUtils.isNumeric(String.valueOf(apiExam.getOutOf()))) {   
			response.setMessage("error");
			response.setDescription("Invalid exam ouOf!");

		}else if(apiExam.getOutOf() < 10 || apiExam.getOutOf() > 100) {   
			response.setMessage("error");
			response.setDescription("Invalid exam ouOf!");
		}else {

			Exam exam = new Exam();
			exam.setAccountId(apiExam.getAccountId());
			exam.setCode(apiExam.getCode());
			exam.setDescription(apiExam.getDescription());
			exam.setOutOf(apiExam.getOutOf()); 

			if(examDAO.putExam(exam)) {
				response.setMessage("success");
				response.setDescription("Exam added successfully."); 

			}else {
				response.setMessage("error");
				response.setDescription("Something went wrong!");

			}


		}
		return response;
	}

	/**
	 * 
	 * @param apiExam
	 * @return
	 */
	public Object updateExam(ApiExam apiExam) {
		Response response = new Response();

		if(apiExam.getCode().length() < 2) {
			response.setMessage("error");
			response.setDescription("Invalid exam code!");

		}else if(apiExam.getDescription().length() < 4) {
			response.setMessage("error");
			response.setDescription("Invalid exam description!");

		}else if(StringUtils.isBlank(String.valueOf(apiExam.getOutOf()))) { 
			response.setMessage("error");
			response.setDescription("Invalid exam ouOf!");

		}else if(!StringUtils.isNumeric(String.valueOf(apiExam.getOutOf()))) {   
			response.setMessage("error");
			response.setDescription("Invalid exam ouOf!");

		}else if(apiExam.getOutOf() < 10 || apiExam.getOutOf() > 100) {   
			response.setMessage("error");
			response.setDescription("Invalid exam ouOf!");

		}else if(examHasDuplicate(apiExam.getAccountId(), apiExam.getCode(), apiExam.getUuid())) {   
			response.setMessage("error");
			response.setDescription("Duplicate Code not allowed!");

		}else if(examHasDuplicate(apiExam.getAccountId(), apiExam.getDescription(), apiExam.getUuid())) {   
			response.setMessage("error");
			response.setDescription("Duplicate Description not allowed!");

		}else if(examDAO.getExam(apiExam.getAccountId(), apiExam.getUuid()) == null) {   
			response.setMessage("error");
			response.setDescription("Exam not found!");

		}else {

			Exam exam = examDAO.getExam(apiExam.getAccountId(), apiExam.getUuid()); 

			exam.setCode(apiExam.getCode());
			exam.setDescription(apiExam.getDescription());
			exam.setOutOf(apiExam.getOutOf()); 

			if(examDAO.updateExam(exam)) {
				response.setMessage("success");
				response.setDescription("Exam updated successfully."); 

			}else {
				response.setMessage("error");
				response.setDescription("Something went wrong!");

			}

		}
		return response;
	}

	/** TODO
	 * 
	 * @param apiSysConfig
	 * @return
	 */

	public Object updateConfig(ApiSysConfig apiSysConfig) {

		ApiResponse apiResponse = new ApiResponse();

		if(!validStatus(apiSysConfig.getCansendSMS())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid sms code!");

		}else if(!validTerm(apiSysConfig.getTerm())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid term!");

		}else if(!validYear(apiSysConfig.getYear())) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid year!");

		}else {
			SysConfig config = sysConfigDAO.getSysConfig(apiSysConfig.getAccountId());
			config.setCansendSMS(apiSysConfig.getCansendSMS());
			config.setExamId(apiSysConfig.getExamId());
			config.setTerm(apiSysConfig.getTerm());
			config.setYear(apiSysConfig.getYear());

			if(sysConfigDAO.updateSysConfig(config)) {
				apiResponse.setMessage("success");
				apiResponse.setDescription("Config updated successfully."); 

				if(termFeeDAO.getFee(apiSysConfig.getAccountId(), apiSysConfig.getTerm(), apiSysConfig.getYear()) == null) {
					TermFee termFee = new TermFee();
					termFee.setAccountId(apiSysConfig.getAccountId());
					termFee.setBoaderAmount(12000);
					termFee.setDayAmount(8000);
					termFee.setTerm(apiSysConfig.getTerm());
					termFee.setYear(apiSysConfig.getYear());
					termFeeDAO.putFee(termFee, apiSysConfig.getAccountId(), apiSysConfig.getTerm(), apiSysConfig.getYear());

				}

				if(feeBreakdownDAO.getFeeBreakdown(apiSysConfig.getAccountId(), FeeConstants.GVMT_MONEY_CODE) == null) { 

					FinanceRestService service = new FinanceRestService();

					FeeBreakdown feeBreakdown = new FeeBreakdown();
					feeBreakdown.setAccountId(apiSysConfig.getAccountId());
					feeBreakdown.setFeeCategory(FeeConstants.GVMT_MONEY_CODE);
					feeBreakdown.setTerm(apiSysConfig.getTerm());
					feeBreakdown.setYear(apiSysConfig.getYear());
					feeBreakdown.setStatus("0");
					feeBreakdown.setAmount(0);
					service.addFeeBreakdown(feeBreakdown);


				}

			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Something went wrong, try again later!"); 
			}

		}

		return apiResponse;
	}


	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public Object getMisc(String accountId) {

		Response response = new Response();

		if(accountDAO.getAccountById(accountId) == null) { 
			response.setMessage("error");
			response.setDescription("Account not found!"); 
			return response;

		}else if(miscellanousDAO.getMiscellanousList(accountId).isEmpty()) {
			response.setMessage("error");
			response.setDescription("Nothing to display!");
			return response;

		}else {

			return miscellanousDAO.getMiscellanousList(accountId);

		}
	}



	/**
	 * 
	 * @param accountId
	 * @param misc
	 * @return
	 */
	public Object updateMisc(String accountId, ApiMisc misc) {

		ApiResponse apiResponse = new ApiResponse();

		if(miscellanousDAO.getMiscById(accountId, misc.getUuid()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Key not found!");
			return apiResponse;

		}else {

			Miscellanous miscellanous = miscellanousDAO.getMiscById(accountId, misc.getUuid());
			miscellanous.setValue(misc.getValue()); 

			//System.out.println(" ************** " + misc.getValue());

			if(miscellanousDAO.updateMiscellanous(miscellanous)) { 
				apiResponse.setMessage("success");
				apiResponse.setDescription("Value updated sucessfully!");
				return apiResponse;

			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Unexpected error occured!");
				return apiResponse;
			}

		}
	}

	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @return
	 */
	public Object getGradingScaleById(String accountId, String uuid) {

		ApiResponse apiResponse = new ApiResponse();

		if(gradingSystemDAO.getGradingSystem(accountId, uuid) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Id not found!");
			return apiResponse;

		}else {

			ApiGradingScale scale = new ApiGradingScale();
			try {
				BeanUtils.copyProperties(scale, gradingSystemDAO.getGradingSystem(accountId, uuid));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			return scale; 
		}
	}

	/**
	 * 
	 * @param accountId
	 * @param categoryId
	 * @return
	 */
	public Object getGradingScaleByCat(String accountId, String categoryId) {

		ApiResponse apiResponse = new ApiResponse();

		if(gradingSystemDAO.getGradingSystemList(accountId, categoryId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Id not found!");
			return apiResponse;

		}else {

			List<ApiGradingScale> apiGradingScaleList = new ArrayList<>();
			gradingSystemDAO.getGradingSystemList(accountId, categoryId).forEach(scale ->{
				ApiGradingScale apiGradingScale = new ApiGradingScale();
				try {
					BeanUtils.copyProperties(apiGradingScale, scale);
					apiGradingScaleList.add(apiGradingScale); 

				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			});


			return apiGradingScaleList; 
		}

	}


	/**
	 * 
	 * @param accountId
	 * @param scale
	 * @return
	 */
	public Object addGradingScale(String accountId, ApiGradingScale scale) {

		ApiResponse apiResponse = new ApiResponse();
		boolean addded = false;

		GradingSystem gradingSystem = new GradingSystem();
		gradingSystem.setAccountId(accountId);
		gradingSystem.setCategoryId(scale.getCategoryId());
		gradingSystem.setDescription(scale.getDescription());
		gradingSystem.setLowerLimit(scale.getLowerLimit());
		gradingSystem.setUpperLimit(scale.getUpperLimit());
		gradingSystem.setPoints(scale.getPoints());

		if(addded) {

			//gradingSystemDAO.putGradingSystem(gradingSystem)
			apiResponse.setMessage("success");
			apiResponse.setDescription("Grading scale added sucessfully!");
			return apiResponse;

		}else {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Unexpected error occured!");
			return apiResponse;
		}
	}

	/**
	 * 
	 * @param accountId
	 * @param scale
	 * @return
	 */
	public Object updateGradingScale(String accountId, ApiGradingScale scale) {

		ApiResponse apiResponse = new ApiResponse();

		if(gradingSystemDAO.getGradingSystem(accountId, scale.getUuid()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("ScaleId not found!");
			return apiResponse;

		}else {


			GradingSystem gradingSystem = gradingSystemDAO.getGradingSystem(accountId, scale.getUuid());
			gradingSystem.setLowerLimit(scale.getLowerLimit());
			gradingSystem.setUpperLimit(scale.getUpperLimit());
			//gradingSystem.setDescription(scale.getDescription());
			//gradingSystem.setPoints(scale.getPoints()); 

			if(gradingSystemDAO.updateGradingSystem(gradingSystem)) {
				apiResponse.setMessage("success");
				apiResponse.setDescription("Grading scale updated sucessfully!");
				return apiResponse;

			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Unexpected error occured!");
				return apiResponse;
			}

		}

	}


	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public Object getAccessLevels(String accountId) {

		Response response = new Response();

		if(acessLevelDAO.getAcessLevelList(accountId) == null) {
			response.setMessage("error");
			response.setDescription("AccountId not found!");
			return response;

		}else {
			return acessLevelDAO.getAcessLevelList(accountId);
		}

	}










	//TODO

	/**
	 * 
	 * @param accountId
	 * @param parentsCategory
	 * @return
	 */


	public Object sendSMSToParents(String accountId, String parentsCategory) { 

		switch (parentsCategory){

		case ApiConstants.P_ALL: 

			return "";

		case ApiConstants.P_F_1: 

			return "";

		case ApiConstants.P_F_2: 

			return "";

		case ApiConstants.P_F_3: 

			return "";

		case ApiConstants.P_F_4: 

			return "";

		default:
			return null;


		}

	}


	/**
	 * 
	 * @param accountId account id 
	 * @param studentId student id 
	 * @param examIds exams ( 3 of them )
	 * @param subjects7 either 7 or 11
	 * @param examType if P123 , the p1,p2,p3 else other exams, leave it blank for other exams 
	 * @return
	 */

	public Object sendExamResultSMS(String accountId, String studentId, String[] examIds, boolean subjects7,String examType) {

		ApiResponse apiResponse = new ApiResponse();
		String subMessage = "";

		if(PerStudentSMSResult.geStudentResult(accountId, studentId, examIds, subjects7, examType) == null) {
			//error
			apiResponse.setMessage("error");
			apiResponse.setDescription("Unexpected error occured!");
		}else {

			List<Performance2> performance2List = PerStudentSMSResult.geStudentResult(accountId, studentId, examIds, subjects7, examType);

			for(Performance2 performance : performance2List) {

				performance.getStudentId();
				performance.getStreamId();

				double mean = 0;

				String studentScore = "";

				Student student = studentDAO.getStudentById(accountId, performance.getStudentId());

				String name = student.getFirstname() + " " + student.getMiddlename() + " , ";


				studentScore += name;

				int totalMean = performance.getTotalMean();

				if(subjects7) {

					mean = (double)totalMean / 7;

					studentScore += "Total: " + totalMean + "/700 , Avg: " + ReportUtil.df2.format(mean) +" , " + 
							ReportUtil.getGradeMainForm234((int)Math.round(mean), 
									accountId) ;


				}else {
					mean = (double)totalMean / 11; 

					studentScore += "Total: " + totalMean + "/1100 , Avg: " + ReportUtil.df2.format(mean) +" , " + 
							ReportUtil.getGradeMainForm234((int)Math.round(mean), 
									accountId);

				}


				List<Subject> subjects = subjectDAO.getSubjects(accountId);


				Map<String,Integer> exam1 = performance.getExam1();
				Map<String,Integer> exam2 = performance.getExam2();
				Map<String,Integer> exam3 = performance.getExam3(); 



				subMessage += studentScore + " . "; 

				for(Subject subject :  subjects) {

					String exam1Score = String.valueOf(exam1.get(subject.getUuid()));
					String exam2Score = String.valueOf(exam2.get(subject.getUuid()));
					String exam3Score = String.valueOf(exam3.get(subject.getUuid()));

					if(StringUtils.equals(exam1Score, "0") || exam1Score.equalsIgnoreCase("null")){
						exam1Score = "";
					}
					if(StringUtils.equals(exam2Score, "0")|| exam2Score.equalsIgnoreCase("null")){
						exam2Score = "";
					}
					if(StringUtils.equals(exam3Score, "0")|| exam3Score.equalsIgnoreCase("null")){
						exam3Score = "";
					}

					String examAverage = ReportUtil.findExamAverage(subject,exam1Score,exam2Score,exam3Score, examIds.length,examType);

					String avgrade = ReportUtil.getGrade(examAverage,subject.getUuid(), accountId);
					String avgpoints = String.valueOf(ReportUtil.getPoints(examAverage, subject.getUuid(),accountId));

					avgpoints = StringUtils.equals(avgpoints, "0") ? "" : avgpoints;

					String average = examAverage + " " + avgrade;// +  " " + avgpoints;

					if(Integer.valueOf(examAverage) > 0) {
						subMessage += subject.getCode()+" "+average + ", "; 
					}



				}

			}

			apiResponse.setMessage("sucess");

			if(StringUtils.isBlank(subMessage)) {
				subMessage = "Result not found!";
			}

			apiResponse.setDescription(subMessage);


		}

		return apiResponse;
	}





	/**
	 * 
	 * @param accountId TODO
	 * @return
	 */
	public Object getCategories(String accountId) {

		Response response = new Response();

		if(accountDAO.getAccountById(accountId) == null) {

			response.setMessage("error");
			response.setDescription("Invalid AccountId!");
			return response;

		}
		if(categoryDAO.getCategoryList(accountId) == null) { 

			response.setMessage("error");
			response.setDescription("Nothing to display!");
			return response;

		}else {

			return categoryDAO.getCategoryList(accountId);

		}

	}

	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public Object getApiSysConfig(String accountId) {

		Response response = new Response();

		if(accountDAO.getAccountById(accountId) == null) {

			response.setMessage("error");
			response.setDescription("Invalid AccountId!");
			return response;

		}else if(sysConfigDAO.getSysConfig(accountId) == null) { 

			response.setMessage("error");
			response.setDescription("Config not found!");
			return response;

		}else {

			return sysConfigDAO.getSysConfig(accountId);

		}

	}







	/**
	 * 
	 * @param term
	 * @return
	 */
	private boolean validTerm(String term) {

		String[] allowed = {"1","2","3"};
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);
		if(allowedList.contains(term)) { 
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 
	 * @param year
	 * @return
	 */
	private boolean validYear(String year) {
		if(StringUtils.isNumeric(year)) {

			if(year.length() != 4) {
				return false;

			}else {
				return true;
			}

		}else {
			return false;
		}
	}



	/**
	 * 
	 * @param description
	 * @return
	 */
	private boolean validSream(String description) {
		String[] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		List<String> alphabetList = new ArrayList<>();
		alphabetList = Arrays.asList(alphabets);

		if(alphabetList.contains(description.toUpperCase())) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 
	 * @param accountId
	 * @param description
	 * @param streamId
	 * @return
	 */
	private boolean streamHasDuplicate(ApiStream apiStream) {

		boolean hasduplicate = true;

		String classroom = classDAO.getClassRoom(apiStream.getAccountId(), apiStream.getClassRoomId()).getDescription();
		String stream = classroom + " " + apiStream.getDescription();

		if(streamDAO.findDuplicate(apiStream.getAccountId(), stream).size() == 0) {
			hasduplicate = false;

		}else if(streamDAO.findDuplicate(apiStream.getAccountId(), stream).size() == 1) {

			String id = streamDAO.getStreamByDesc(apiStream.getAccountId(), stream).getUuid();

			if(StringUtils.equals(apiStream.getUuid(), id)) {
				hasduplicate = false;

			}else {
				hasduplicate = true;
			}

		}

		return hasduplicate;
	}



	/**
	 * 
	 * @param accountId
	 * @param query
	 * @param examId
	 * @return
	 */
	private boolean examHasDuplicate(String accountId, String query, String examId) {

		boolean hasduplicate = true;

		if(examDAO.findDuplicate(accountId, query) == null) {
			hasduplicate = false;

		}else if(examDAO.findDuplicate(accountId, query).size() == 0) {
			hasduplicate = false;

		}else if(examDAO.findDuplicate(accountId, query).size() == 1) {


			String id = examDAO.getExamByQuey(accountId, query).getUuid(); 

			if(StringUtils.equals(examId, id)) {
				hasduplicate = false;

			}else {
				hasduplicate = true;
			}

		}

		return hasduplicate;
	}


	/** 
	 * @param isBoarding
	 * @return
	 */
	private boolean validStatus(String isBoarding) {
		String[] allowed = {"1","0"};
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);
		if(allowedList.contains(isBoarding)) {
			return true;
		}else {
			return false;
		}
	}




}
