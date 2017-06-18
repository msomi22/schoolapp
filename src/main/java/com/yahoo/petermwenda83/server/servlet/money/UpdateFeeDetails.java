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

import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/** 
 * @author peter
 *
 */
public class UpdateFeeDetails extends HttpServlet{

	private static final long serialVersionUID = 3346337172969628933L;
	
	
	final String ERROR_TRANSACTION_NOT_UPDATED = "We are sorry but something went wrong while updating the transaction";
	final String ERROR_AMOUNT_NOT_IN_RANGE = "Fee can only be within the range of KSH 100 - KSH 100,000";
    final String EMPTY_TRANSACTION_ID = "Transaction Id/Recept Number cant be empty";
	final String EMPTY_CREDENTIALS = "Looks like you didn't search for any student";
	final String SUCCESS_TRANSACTION_UPDATED = "Transaction successfully updated";
	final String ERROR_SCHOOL_PASSWORD_INCORRECT = "Master password incorrect!";
	final String INCORRECT_SCHOL_PASSWORD = "Incorrect school password";
	final String NUMBER_FORMAT_ERROR = "Amount can only be Numeric";
	final String EMPTY_AMOUNT = "Amount can't be empty";
	
	private static StudentFeeDAO studentFeeDAO;
	private static ExamConfigDAO examConfigDAO;
	private Cache schoolaccountCache;
    ExamConfig examConfig;
    
	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentFeeDAO = StudentFeeDAO.getInstance();
       CacheManager mgr = CacheManager.getInstance();
       schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
       examConfigDAO = ExamConfigDAO.getInstance();
      
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String transactionId = StringUtils.trimToEmpty(request.getParameter("transactionid"));
       String amounttodeduct = StringUtils.trimToEmpty(request.getParameter("amountpaid"));
       String amountpaidold = StringUtils.trimToEmpty(request.getParameter("amountpaidold"));
       String systemUser = StringUtils.trimToEmpty(request.getParameter("systemuser"));
       String schoolUuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String studentUuid = StringUtils.trimToEmpty(request.getParameter("studentuuid"));
       String schoolpassword = StringUtils.trimToEmpty(request.getParameter("schoolpassword"));
       
       
       Map<String, String> paramHash = new HashMap<>();
       paramHash.put("transactionId", transactionId);
       paramHash.put("amountPaid", amounttodeduct);
       
       SchoolAccount school = new SchoolAccount();
       String  schoolusername = "";
       if(session !=null){
    	 schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    	 
    	      }
    	   net.sf.ehcache.Element element;
    	   
    	   element = schoolaccountCache.get(schoolusername);
    	   if(element !=null){
    	   school = (SchoolAccount) element.getObjectValue();
    	      }
    	  
    	   
       if(StringUtils.isBlank(transactionId)){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, EMPTY_TRANSACTION_ID); 
		   
	   }else if(StringUtils.isBlank(amounttodeduct)){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, EMPTY_AMOUNT); 
			   
	   }else if(StringUtils.isBlank(amountpaidold)){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANSACTION_NOT_UPDATED); 
			   
	   }else if(!isNumericRange(amounttodeduct)){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, NUMBER_FORMAT_ERROR); 
			   
	   }else if(!isNumeric(amounttodeduct)){
    	   session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, NUMBER_FORMAT_ERROR); 
    	   
       }else if(!lengthValid(amounttodeduct)){
	 	   session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_AMOUNT_NOT_IN_RANGE); 
		   
	   }else if(StringUtils.isBlank(systemUser)){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, EMPTY_CREDENTIALS); 
			   
	   }else if(StringUtils.isBlank(schoolUuid)){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, EMPTY_CREDENTIALS); 
			   
	   }else if(StringUtils.isBlank(studentUuid)){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, EMPTY_CREDENTIALS); 
			   
	   }else if(!StringUtils.equals(SecurityUtil.getMD5Hash(schoolpassword), school.getPassword())){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, INCORRECT_SCHOL_PASSWORD); 
			   
	   }else{
       
       

  	   examConfig = examConfigDAO.getExamConfig(school.getUuid());
  	   double balance = 0;
	   double oldamount = Double.parseDouble(amountpaidold);
	   double amountTodeduct = Double.parseDouble(amounttodeduct);
	   balance = oldamount - amountTodeduct; //oldamount 5000 , newamount 2000 , 
	   if(amountTodeduct>=oldamount){
		   session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANSACTION_NOT_UPDATED);
	   }else{
		   

			       StudentFee studentFee = studentFeeDAO.getStudentFeeByTransactionId(schoolUuid, transactionId);
			       studentFee.setTransactionID(transactionId);
			       studentFee.setAmountPaid(amountTodeduct);   
			       studentFee.setAmountTokenizer(balance); 
			       studentFee.setSystemUser(systemUser);
			       studentFee.setSchoolAccountUuid(schoolUuid);
			       studentFee.setStudentUuid(studentUuid);
			       studentFee.setTerm(examConfig.getTerm());
			       studentFee.setYear(examConfig.getYear());
			       
			       if(studentFeeDAO.updateStudentFee(studentFee)){ 
			    	   session.setAttribute(SessionConstants.STUDENT_FEE_ADD_SUCCESS, SUCCESS_TRANSACTION_UPDATED);
			       }else{
			    	   session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANSACTION_NOT_UPDATED);
			        }
			       
	       }
		   
	   }
		
       
       session.setAttribute(SessionConstants.STUENT_FEE_UPDATE_PARAM, paramHash); 
       response.sendRedirect("fee.jsp");  
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

}
