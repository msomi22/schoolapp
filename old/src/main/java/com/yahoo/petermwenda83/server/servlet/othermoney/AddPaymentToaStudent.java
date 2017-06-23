package com.yahoo.petermwenda83.server.servlet.othermoney;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import com.yahoo.petermwenda83.bean.otherfee.OtherFee;
import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;
import com.yahoo.petermwenda83.bean.otherfee.TermOtherMonies;
import com.yahoo.petermwenda83.bean.smsapi.AfricasTalking;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherstypeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
import com.yahoo.petermwenda83.persistence.othermoney.TermOtherMoniesDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsApiDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.money.StudentBalance;
import com.yahoo.petermwenda83.server.servlet.sms.send.AfricasTalkingGateway;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class AddPaymentToaStudent extends HttpServlet{

	public final static String STATUS_NOT_DEDUCTED = "NOTDEDUCTED";

	final String MONEY_ASSIGNED_SUCCESS = "The money has been assigned successfully";
	final String MONEY_ASSIGNED_ERROR = "Something went wrong while assigning the money";
	final String ERROR_AMOUNT_INVALID = "Invalid amount, amount range from KSH 100 - KSH 100,000";
	final String ERROR_AMOUNT_NUMERIC = "Amount can only be numeric";
	final String EMPTY_FIELD = "Empty Fields not allowed";
	final String ERROR_MONEY_ALREADY_ASSIGNED = "Payment type already added";
	final String ERROR_DID_NOT_SEARCH_STUDENT = "It looks like you didn't search for any student";
	
	final String ERROR_STUDENT_INACTIVE = "This student is Inactive.";
	final String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";


	private static StudentOtherMoniesDAO studentOtherMoniesDAO;
	private static SysConfigDAO sysConfigDAO;
	private static TermOtherMoniesDAO termOtherMoniesDAO;

	private static StudentFeeDAO studentFeeDAO;
	private static TermFeeDAO termFeeDAO;
	private static StudentDAO studentDAO;
	private static ParentsDAO parentsDAO;
	private static SmsSendDAO smsSendDAO;
	private static OtherstypeDAO otherstypeDAO;
	private StudentBalance studentBal;
	private static SmsApiDAO smsApiDAO;

	Locale locale = new Locale("en","KE"); 
	NumberFormat nf = NumberFormat.getCurrencyInstance(locale);



	StudentOtherFee studentOtherFee;
	SysConfig sysConfig;

	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String>genderfinderHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>();
	HashMap<String, String> roomHash = new HashMap<String, String>();

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
		sysConfigDAO = SysConfigDAO.getInstance();
		termOtherMoniesDAO = TermOtherMoniesDAO.getInstance();

		studentFeeDAO = StudentFeeDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		parentsDAO = ParentsDAO.getInstance();
		smsSendDAO = SmsSendDAO.getInstance();
		otherstypeDAO = OtherstypeDAO.getInstance();
		studentBal = new StudentBalance();
		smsApiDAO = SmsApiDAO.getInstance();

		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String OtherstypeUuid = StringUtils.trimToEmpty(request.getParameter("OtherstypeUuid"));
		String StudentUuid = StringUtils.trimToEmpty(request.getParameter("StudentUuid"));



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



		sysConfig = new SysConfig();
		if(sysConfigDAO.getExamConfig(school.getUuid()) !=null){
			sysConfig = sysConfigDAO.getExamConfig(school.getUuid());
		}

		if(StringUtils.isBlank(OtherstypeUuid)){
			session.setAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_ERROR, EMPTY_FIELD); 

		}else if (StringUtils.isBlank(StudentUuid)){
			session.setAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_ERROR, ERROR_DID_NOT_SEARCH_STUDENT); 

		}else if (studentOtherMoniesDAO.getStudentOtherMTY(StudentUuid, OtherstypeUuid, sysConfig.getTerm(), sysConfig.getYear()) !=null){
			session.setAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_ERROR, ERROR_MONEY_ALREADY_ASSIGNED); 

		}else{
			

			//get student name
			Student stuudent = new Student();
			if(studentDAO.getStudentByuuid(school.getUuid(), StudentUuid) !=null){
				stuudent = studentDAO.getStudentByuuid(school.getUuid(), StudentUuid);
			}
			
			 if(StringUtils.equals(stuudent.getStatusUuid(),statusUuid)){
			
			 List<OtherFee> othertypeList = new ArrayList<OtherFee>(); 
		     othertypeList = otherstypeDAO.gettypeList(school.getUuid());  
		      HashMap<String, String> moneytypeHash = new HashMap<String, String>(); 
		     
		     if(othertypeList !=null){
		     for(OtherFee om : othertypeList){
		         moneytypeHash.put(om.getUuid(),om.getType());
		         }
		       }
		             

			double typeAmount = 0;
			String type = "";
			if(termOtherMoniesDAO.getTermOtherMoney(school.getUuid(), OtherstypeUuid) !=null){
				TermOtherMonies termOtherMonies =  termOtherMoniesDAO.getTermOtherMoney(school.getUuid(), OtherstypeUuid);
				typeAmount = termOtherMonies.getAmount();
				type = moneytypeHash.get(termOtherMonies.getOtherstypeUuid());
			}

			studentOtherFee = new StudentOtherFee();
			studentOtherFee.setStudentUuid(StudentUuid);
			studentOtherFee.setOtherstypeUuid(OtherstypeUuid);
			studentOtherFee.setAmountPiad(typeAmount); 
			studentOtherFee.setTerm(sysConfig.getTerm());
			studentOtherFee.setYear(sysConfig.getYear()); 
			
			if(studentOtherMoniesDAO.putStudentOtherMonies(studentOtherFee)){
				session.setAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_SUCCESS, MONEY_ASSIGNED_SUCCESS); 
			}else{
				session.setAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_ERROR, MONEY_ASSIGNED_ERROR); 
			}
			
			if(StringUtils.equals(sysConfig.getSendSMS(),"ON")){
			
			String feebalance = "";
			
			//get parent contact and name
			String phone = "";
			String formatedphone = "";
			String realphone = "";
			String parentname = "";
			StudentParent studentParent = new StudentParent();
			if(parentsDAO.getParent(StudentUuid) !=null){
				studentParent = parentsDAO.getParent(StudentUuid);
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



				//get new fee balance
			 double balance = 0;
             balance = studentBal.findBalance(termFeeDAO,sysConfigDAO,studentFeeDAO,studentOtherMoniesDAO,stuudent.getAdmissionDate(),stuudent.getRegTerm(),stuudent.getUuid(),school.getUuid(),stuudent.getFinalYear()); 
             System.out.println("balance = " + balance);
             feebalance = nf.format(balance);


				//send message
				AfricasTalking africasTalking = new AfricasTalking();
				// Specify your login credentials
				SmsApi smsApi = smsApiDAO.getSmsApi(school.getUuid());
				String username = smsApi.getApiPassword();//africasTalking.getUsername();
				String apiKey   = smsApi.getApiKey();//africasTalking.getApiKey();
				String message = "";
				message = "HI " + parentname + ", your " + genderfinderHash.get(StudentUuid)+ " " + studNameHash.get(StudentUuid) + " Adm.No " + studentAdmNoHash.get(StudentUuid) + " has been added additional charges  of type " + type + " ,amount " + nf.format(typeAmount) + " Term " + sysConfig.getTerm() + " Year " + sysConfig.getYear() +", Fee balance is " + feebalance;


				africasTalking.setMessage(message); 
				africasTalking.setRecipients(realphone); 
				// Create a new instance of our awesome gateway class
				AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
				
				// Thats it, hit send and we'll take care of the rest. Any errors will
				// be captured in the Exception class below

				//save to database
				String thestatus ="";
				String thenumber ="";
				String themessage ="";
				String thecost ="";

				//System.out.println(message.replaceAll("[\r\n]+", " "));  
				OutGoingSMS outGoingSMS = new OutGoingSMS();
				if(realphone !=null && message.replaceAll("[\r\n]+", " ") !=null){
					outGoingSMS.setAccountId(school.getUuid());
					outGoingSMS.setStatus("failed");
					outGoingSMS.setMobile(realphone);
					outGoingSMS.setMessage(message.replaceAll("[\r\n]+", " "));
					outGoingSMS.setSmsCost("1");
					smsSendDAO.putSmsSend(outGoingSMS);
				}

				try {
					JSONArray results = gateway.sendMessage(africasTalking.getRecipients(), africasTalking.getMessage());
					for( int i = 0; i < results.length(); ++i ) {
						JSONObject result = results.getJSONObject(i);
					
						thestatus = result.getString("status");
						thenumber = result.getString("number");
						themessage = message;
						thecost = result.getString("cost");
                        
						if(outGoingSMS !=null){
						OutGoingSMS smsSend2 = smsSendDAO.getSmsSend(outGoingSMS.getUuid());
						smsSend2.setAccountId(school.getUuid());
						smsSend2.setStatus(thestatus);
						smsSend2.setMobile(thenumber);
						smsSend2.setMessage(themessage.replaceAll("[\r\n]+", " "));
						smsSend2.setSmsCost(thecost);
						smsSendDAO.updateSmsSend(smsSend2); 
						}

					}

				}

				catch (Exception e) {
					e.printStackTrace(); 
					
				}
				
			}//end sms enable		
				
			 }else{
	 			  session.setAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_ERROR,ERROR_STUDENT_INACTIVE ); 
	 		  }
		
		}

		response.sendRedirect("studentAddOM.jsp");  
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
	private static final long serialVersionUID = -2612273960810030426L;
}
