/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.money;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.Deposit;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.PMoneyDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/** 
 * @author peter
 *
 */
public class PMDeposit extends HttpServlet{
	
	final String ERROR_AMOUNT_NOT_IN_RANGE = "You can only deposit Money within the range of KSH 100 - KSH 10,000";
	final String EMPTY_CREDENTIALS = "Looks like you didn't search for any student";
	final String ERROR_DEPOSIT_FAILED = "Something went wrong , transaction failed";
	final String SUCCESS_DEPOSIT_SUCCESSFUL = "Pocket money deposited successfully";
	final String NUMBER_FORMAT_ERROR = "Amount can only be Numeric";
	final String EMPTY_AMOUNT = "Amount cant be empty";
	
	final String ERROR_STUDENT_INACTIVE = "This student is Inactive, they can not Deposit Pocket Money";
	
	final String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
	
	final String INCORRECT_SCHOL_PASSWORD = "Incorrect Security Key";
	
	 private static StudentDAO studentDAO;
	 private static PMoneyDAO pMoneyDAO;
	 private static SysConfigDAO sysConfigDAO;
	 SysConfig sysConfig;
	 private Cache schoolaccountCache;	

	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentDAO = StudentDAO.getInstance();
       pMoneyDAO = PMoneyDAO.getInstance();
       sysConfigDAO = SysConfigDAO.getInstance();
       CacheManager mgr = CacheManager.getInstance();
	   schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);

   }
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String admissionNumber = StringUtils.trimToEmpty(request.getParameter("admnumber"));
       String schoolUuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String amount = StringUtils.trimToEmpty(request.getParameter("depositeAmount"));
       String systemuser = StringUtils.trimToEmpty(request.getParameter("systemuser"));
       String securityKey = StringUtils.trimToEmpty(request.getParameter("securityKey"));
       
       Map<String, String> paramHash = new HashMap<>();   
       paramHash.put("amount", amount);
      
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
       
       if(StringUtils.isBlank(admissionNumber)){
    	   session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, EMPTY_CREDENTIALS);
    	   
       }else if(StringUtils.isBlank(amount)){
    	   session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, EMPTY_AMOUNT);
    	   
       }else if(!isNumericRange(amount)){
		     session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, NUMBER_FORMAT_ERROR); 
			   
	   }else if(!lengthValid(amount)){
	 	   session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_AMOUNT_NOT_IN_RANGE); 
		   
	   }else if(StringUtils.isBlank(schoolUuid)){
    	   session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, EMPTY_CREDENTIALS);
    	   
       }else if(StringUtils.isBlank(systemuser)){
    	   session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, EMPTY_CREDENTIALS);
    	   
       }else if(!StringUtils.equals(SecurityUtil.getMD5Hash(securityKey), school.getPassword())){
			session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, INCORRECT_SCHOL_PASSWORD); 

		}else{
    	   
    	   Student student = new Student();
		   if(studentDAO.getStudentObjByadmNo(schoolUuid, admissionNumber) !=null){
			   student = studentDAO.getStudentObjByadmNo(schoolUuid, admissionNumber);
			   
		   }
		   if(StringUtils.equals(student.getStatusUuid(),statusUuid)){
		   sysConfig = sysConfigDAO.getExamConfig(schoolUuid);
		   Deposit d = new Deposit();
    	   d.setStudentUuid(student.getUuid());
    	   d.setSystemUser(systemuser);
    	   d.setTerm(sysConfig.getTerm());
           d.setYear(sysConfig.getYear());
    	   
    	   if(pMoneyDAO.addBalance(d, Double.parseDouble(amount))){ 
    		   session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, SUCCESS_DEPOSIT_SUCCESSFUL);
    	   }else{
    		   session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_DEPOSIT_FAILED);
    	   }
    	   
		   }else{
 			  session.setAttribute(SessionConstants.STUDENT_FIND_ERROR,ERROR_STUDENT_INACTIVE ); 
 		  }
    	 
       }
       session.setAttribute(SessionConstants.STUENT_POCKET_MONEY_PARAM, paramHash); 
       response.sendRedirect("pocketM.jsp");    
	   return;
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
	private static final long serialVersionUID = 847435422831970402L;
}
