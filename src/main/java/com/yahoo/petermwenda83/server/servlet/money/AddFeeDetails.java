/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.money;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.account.SmsApi;
import com.yahoo.petermwenda83.bean.account.OutGoingSMS;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;
import com.yahoo.petermwenda83.bean.smsapi.AfricasTalking;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsApiDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.sms.send.AfricasTalkingGateway;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**  
 * @author peter
 *
 */
public class AddFeeDetails extends HttpServlet{


	final String ERROR_AMOUNT_NOT_IN_RANGE = "Fee can only be within the range of KSH 100 - KSH 100,000";
	final String ERROR_AMOUNT_NUMERIC = "Amount can only be numeric";
	final String ERROR_NO_SLIPNIMBER = "You didn't provide any admission  number?.";
	final String ERROR_SLIP_NO_EXIST = "This bank slip has already been registered.";
	final String ERROR_AMOUNT_NOT_ADDED = "Something went wrong, amount not added.";
	final String UNEXPECTED_ERROR = "Looks like you didn'd search for a student.";
	final String ERROR_NO_AMOUNT = "You didn't provide any admission  number!.";
	final String SUCCESS_AMOUNT_NOT_ADDED = "Fee details successfully added.";
	final String NUMBER_FORMAT_ERROR = "Amount can only be Numeric";
	final String INCORRECT_SCHOL_PASSWORD = "Incorrect Security Key";

	final String ERROR_STUDENT_INACTIVE = "This student is Inactive, they can not Pay Fee";

	final String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";

	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String>genderfinderHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>();
	HashMap<String, String> roomHash = new HashMap<String, String>();



	private static StudentOtherMoniesDAO studentOtherMoniesDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static ExamConfigDAO examConfigDAO;
	private static TermFeeDAO termFeeDAO;
	private static StudentDAO studentDAO;
	private static ParentsDAO parentsDAO;
	private static SmsSendDAO smsSendDAO;
	private StudentBalance studentBal;
	private static SmsApiDAO smsApiDAO;

    

	SysConfig sysConfig = new SysConfig();
	double Studentbalance = 0;
	double Accumstudentbalance = 0;

	Locale locale = new Locale("en","KE"); 
	NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
	private Cache schoolaccountCache;	

	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		studentOtherMoniesDAO = StudentOtherMoniesDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		parentsDAO = ParentsDAO.getInstance();
		smsSendDAO = SmsSendDAO.getInstance();
		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
		studentBal = new StudentBalance();
		smsApiDAO = SmsApiDAO.getInstance();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String Amountpaid = StringUtils.trimToEmpty(request.getParameter("Amountpaid"));
		String slipNumber = StringUtils.trimToEmpty(request.getParameter("slipNumber"));
		String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
		String systemuser = StringUtils.trimToEmpty(request.getParameter("systemuser"));
		String studentuuid = StringUtils.trimToEmpty(request.getParameter("studentuuid"));
		String schoolpassword = StringUtils.trimToEmpty(request.getParameter("schoolpassword"));

		Map<String, String> addparamHash = new HashMap<>(); 
		addparamHash.put("Amountpaid", Amountpaid);
		addparamHash.put("slipNumber", slipNumber);

		Account school = new Account();
		String  schoolusername = "";
		if(session !=null){
			schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);

		}
		net.sf.ehcache.Element element;

		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (Account) element.getObjectValue();
		}




		if(StringUtils.isBlank(Amountpaid)){
			session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_NO_AMOUNT); 

		}else if(!isNumericRange(Amountpaid)){
			session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, NUMBER_FORMAT_ERROR); 

		}else if(!isNumeric(Amountpaid)){
			session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_AMOUNT_NUMERIC); 

		}else if(!lengthValid(Amountpaid)){
			session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_AMOUNT_NOT_IN_RANGE); 

		}else if(StringUtils.isBlank(slipNumber)){
			session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_NO_SLIPNIMBER); 

		}
		else if(StringUtils.isBlank(schooluuid)){
			session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, UNEXPECTED_ERROR); 

		}
		else if(StringUtils.isBlank(studentuuid)){
			session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, UNEXPECTED_ERROR); 

		}
		else if(StringUtils.isBlank(systemuser)){
			session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, UNEXPECTED_ERROR); 

		}else if(studentFeeDAO.getStudentFeeByTransactionId(schooluuid, slipNumber) !=null){
			session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_SLIP_NO_EXIST); 

		}else if(!StringUtils.equals(SecurityUtil.getMD5Hash(schoolpassword), school.getPassword())){
			session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, INCORRECT_SCHOL_PASSWORD); 

		}else{


			if(examConfigDAO.getExamConfig(schooluuid) !=null){
				sysConfig = examConfigDAO.getExamConfig(schooluuid);
			}
			
			//get student 
			Student stuudent = new Student();
			if(studentDAO.getStudentByuuid(schooluuid, studentuuid) !=null){
				stuudent = studentDAO.getStudentByuuid(schooluuid, studentuuid);
			}

			if(StringUtils.equals(stuudent.getStatusUuid(),statusUuid)){


				double amountTopay =  Double.parseDouble(Amountpaid);
				String feebalance = "";
                String studentType = stuudent.getStudentType();
                if(StringUtils.isBlank(studentType) || StringUtils.equals(studentType, "null")){
                	studentType = "Boarder";
                }
				StudentFee studentFee = new StudentFee();
				studentFee.setSchoolAccountUuid(schooluuid);
				studentFee.setStudentUuid(studentuuid);
				studentFee.setTransactionID("KE " + slipNumber.toUpperCase() + "-" + new Date()); 
				studentFee.setAmountPaid(amountTopay);  
				studentFee.setTerm(sysConfig.getTerm());
				studentFee.setYear(sysConfig.getYear());
				studentFee.setStudentType(studentType); 
				studentFee.setSystemUser(systemuser);

				if(studentFeeDAO.putStudentFee(studentFee)){
					session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, SUCCESS_AMOUNT_NOT_ADDED);
				}
				else{
					session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, UNEXPECTED_ERROR);
				}
				
				if(StringUtils.equals(sysConfig.getSendSMS(),"ON")){

				//get parent contact and name
				String phone = "";
				String formatedphone = "";
				String realphone = "";
				String parentname = "";
				StudentParent studentParent = new StudentParent();
				if(parentsDAO.getParent(studentuuid) !=null){
					studentParent = parentsDAO.getParent(studentuuid);
					parentname = studentParent.getFathername();
					phone = studentParent.getFatherphone();
					formatedphone = phone.replaceFirst("^0+(?!$)", "");
					realphone = "+254"+formatedphone;
				}

				String genderfinder = "";
				String gender = "";

				if(stuudent != null){
					studentAdmNoHash.put(stuudent.getUuid(),stuudent.getAdmno()); 
					String firstNameLowecase = StringUtils.capitalize(stuudent.getFirstname().toLowerCase());
					String lastNameLowecase = StringUtils.capitalize(stuudent.getLastname().toLowerCase());
					String formatedFirstname = firstNameLowecase;
					String formatedLastname = lastNameLowecase;
					studNameHash.put(stuudent.getUuid(),formatedFirstname + " " + formatedLastname +"\n"); 
					gender = stuudent.getGender();
					if(StringUtils.equalsIgnoreCase(gender, "Male")) {
						genderfinder = "son";
					}else{
						genderfinder = "daughter";
					}
					genderfinderHash.put(stuudent.getUuid(), genderfinder);
				}
                double balance = 0;
                balance = studentBal.findBalance(termFeeDAO,examConfigDAO,studentFeeDAO,studentOtherMoniesDAO,stuudent.getAdmissionDate(),stuudent.getRegTerm(),stuudent.getUuid(),schooluuid,stuudent.getFinalYear()); 
                System.out.println("balance = " + balance);
                feebalance = nf.format(balance);
                
				//send message
				AfricasTalking africasTalking = new AfricasTalking();
				// Specify your login credentials
				SmsApi smsApi = smsApiDAO.getSmsApi(school.getUuid());
				String username = smsApi.getApiPassword();//africasTalking.getUsername();
				String apiKey   = smsApi.getApiKey();//africasTalking.getApiKey();
				String message = "";
				message = "HI " + parentname + ", your " + genderfinderHash.get(studentuuid)+ " " + studNameHash.get(studentuuid) + " Adm.No " + studentAdmNoHash.get(studentuuid) + "  has paid fee of amount " + nf.format(amountTopay) + " Term " + sysConfig.getTerm() + " Year " + sysConfig.getYear() + "  , Fee balance is " + feebalance;


				africasTalking.setMessage(message); 
				africasTalking.setRecipients(realphone); 
				// Create a new instance of our awesome gateway class
				AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
				//	System.out.println("message ="+message);
				// Thats it, hit send and we'll take care of the rest. Any errors will
				// be captured in the Exception class below

				//save to database
				String thestatus ="";
				String thenumber ="";
				String themessage ="";
				String thecost ="";

				//System.out.println(message.replaceAll("[\r\n]+", " "));  

				OutGoingSMS outGoingSMS = new OutGoingSMS();
				outGoingSMS.setAccountId(schooluuid);
				outGoingSMS.setStatus("failed");
				outGoingSMS.setMobile(realphone);
				outGoingSMS.setMessage(message.replaceAll("[\r\n]+", " "));
				outGoingSMS.setSmsCost("1");
				smsSendDAO.putSmsSend(outGoingSMS);


				try {
					JSONArray results = gateway.sendMessage(africasTalking.getRecipients(), africasTalking.getMessage());
					for( int i = 0; i < results.length(); ++i ) {
						JSONObject result = results.getJSONObject(i);

						thestatus = result.getString("status");
						thenumber = result.getString("number");
						themessage = message;
						thecost = result.getString("cost");

						OutGoingSMS smsSend2 = smsSendDAO.getSmsSend(outGoingSMS.getUuid());
						smsSend2.setAccountId(schooluuid);
						smsSend2.setStatus(thestatus);
						smsSend2.setMobile(thenumber);
						smsSend2.setMessage(themessage.replaceAll("[\r\n]+", " "));
						smsSend2.setSmsCost(thecost);
						smsSendDAO.updateSmsSend(smsSend2); 

					} 

				}

				catch (Exception e) {
					e.printStackTrace(); 
				}
				
			}//end sms enabled


			}else{
				session.setAttribute(SessionConstants.STUDENT_FIND_ERROR,ERROR_STUDENT_INACTIVE ); 
			}
		}



		session.setAttribute(SessionConstants.STUENT_FEE_ADD_PARAM, addparamHash); 
		response.sendRedirect("addFee.jsp");  
		return;

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

	/**
	 * @param amount
	 * @return
	 */
	private boolean isNumericRange(String amount) {
		boolean valid = true;
		String regex = "[0-9]+";
		if(amount.matches(regex)){ 
			valid = true;
		}else{
			valid = false;
		}

		return valid;
	}

	/**
	 * @param mystring
	 * @return
	 */
	private static boolean lengthValid(String mystring) {
		boolean isvalid = true;
		int length = 0;
		length = mystring.length();
		//System.out.println("lenght = " + length);
		if(length<3 ||length>6){
			isvalid = false;
		}
		return isvalid;
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
	private static final long serialVersionUID = 2453548782759986198L;

}
