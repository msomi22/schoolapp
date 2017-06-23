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
import com.yahoo.petermwenda83.bean.money.Withdraw;
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
public class PMWithdraw extends HttpServlet{
	
	final String ERROR_AMOUNT_NOT_IN_RANGE = "You can only withdraw Money within the range of KSH 100 - KSH 10,000";
	final String EMPTY_CREDENTIALS = "Looks like you didn't search for any student";
	final String ERROR_WITHDRAW_FAILED = "Something went wrong , transaction failed";
	final String SUCCESS_WITHDRAW_SUCCESSFUL = "Pocket money withdrwan successfully";
	final String NUMBER_FORMAT_ERROR = "Amount can only be Numeric";
	final String EMPTY_AMOUNT = "Amount cant be empty";
	
	final String ERROR_STUDENT_INACTIVE = "This student is Inactive, they can not Withdraw Pocket Money";
	
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
       String amount = StringUtils.trimToEmpty(request.getParameter("amountTowithdraw"));
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
		   Withdraw w = new Withdraw();
    	   w.setStudentUuid(student.getUuid());
    	   w.setSystemUser(systemuser);
    	   w.setTerm(sysConfig.getTerm());
           w.setYear(sysConfig.getYear());
           
    	   
    	   if(pMoneyDAO.deductBalance(w, Double.parseDouble(amount))){ 
    		   session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, SUCCESS_WITHDRAW_SUCCESSFUL);
    	   }else{
    		   session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_WITHDRAW_FAILED);
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
	private static final long serialVersionUID = -5564781153459133280L;

}
