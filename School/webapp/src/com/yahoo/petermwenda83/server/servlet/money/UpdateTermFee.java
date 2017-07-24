/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.money;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.util.LogUtil;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/** 
 * @author peter
 *
 */
public class UpdateTermFee extends HttpServlet{

	final String EMPTY_TERM = "Term can't be empty";
	final String EMPTY_YEAR = "Year can't be empty";
	final String EMPTY_B_AMOUNT = "Boarding Fee Amount can't be empty";
	final String EMPTY_A_AMOUNT = "Day Fee Amount can't be empty";
	final String EMPTY_SECURITY_KEY = "Security Key can't be empty"; 
	final String NUMBER_FORMAT_ERROR = "Fee can only be Numeric";
	final String ERROR_AMOUNT_NOT_IN_RANGE = "Fee can only be within the range of KSH 100 - KSH 100,000";
	final String ERROR_SYSTEM_ERR = "Something went wrong while updating fee";
	final String SUCCESS_FEE_UPDATES = "Fee updated successfully";
	final String INCORRECT_SCHOL_PASSWORD = "Incorrect Security Key";
	final String ERROR_FEE_NOT_UPDATED_WRONG_TERM_YEAR = "Fee not updated, confirm the term and year.";
	
	private static TermFeeDAO termFeeDAO;
	private static ExamConfigDAO examConfigDAO;
	private Cache schoolaccountCache;	

	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		termFeeDAO = TermFeeDAO.getInstance();
		 examConfigDAO = ExamConfigDAO.getInstance();
		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String Term = StringUtils.trimToEmpty(request.getParameter("Term"));
		String Year = StringUtils.trimToEmpty(request.getParameter("Year"));
		String BAmount = StringUtils.trimToEmpty(request.getParameter("BAmount"));
		String DAmount = StringUtils.trimToEmpty(request.getParameter("DAmount"));
		String securityKey = StringUtils.trimToEmpty(request.getParameter("securityKey"));
		String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
		
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
		
		 ExamConfig examConfig = new ExamConfig();
			if(examConfigDAO.getExamConfig(schooluuid) !=null){
				examConfig = examConfigDAO.getExamConfig(schooluuid);
			}

		if(StringUtils.isBlank(Term)){
			session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, EMPTY_TERM); 

		}else if(StringUtils.isBlank(Year)){
			session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, EMPTY_YEAR); 

		}else if(!StringUtils.equals(Term, examConfig.getTerm())){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_FEE_NOT_UPDATED_WRONG_TERM_YEAR); 
			   
	   }else if(!StringUtils.equals(Year, examConfig.getYear())){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_FEE_NOT_UPDATED_WRONG_TERM_YEAR); 
			   
	   }else if(StringUtils.isBlank(BAmount)){
			session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, EMPTY_B_AMOUNT); 

		}else if(StringUtils.isBlank(DAmount)){
			session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, EMPTY_A_AMOUNT); 

		}else if(StringUtils.isBlank(securityKey)){
			session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, EMPTY_SECURITY_KEY); 

		}else if(!isNumericRange(BAmount) && !isNumericRange(DAmount)){
			session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, NUMBER_FORMAT_ERROR); 

		}else if(!isNumeric(BAmount) && !isNumeric(DAmount)){
			session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, NUMBER_FORMAT_ERROR); 

		}else if(!lengthValid(BAmount) && !lengthValid(DAmount)){
			session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_AMOUNT_NOT_IN_RANGE); 

		}else if(StringUtils.isBlank(schooluuid)){
			session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_SYSTEM_ERR); 

		}else if(!StringUtils.equals(SecurityUtil.getMD5Hash(securityKey), school.getPassword())){
			session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, INCORRECT_SCHOL_PASSWORD); 

		}else{
			LogUtil logUtil = new LogUtil();
			double boardingAmount = 0;
			double dayAmount = 0;
            if(termFeeDAO.getFee(schooluuid,Term,Year) !=null){
			   TermFee termFee = termFeeDAO.getFee(schooluuid,Term,Year);
			   boardingAmount = Double.parseDouble(BAmount);
			   dayAmount = Double.parseDouble(DAmount);
			   termFee.setTermAmount(boardingAmount);
			   termFee.setDayAmount(dayAmount); 
			   if(termFeeDAO.putFee(termFee)){
				   logUtil.writeLog("update term fee " + termFee);  
				   session.setAttribute(SessionConstants.STUDENT_FEE_ADD_SUCCESS, SUCCESS_FEE_UPDATES); 
			   }else{
				   session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_SYSTEM_ERR);    
			   }
			  
            }
		}
		   response.sendRedirect("changeTermYear.jsp");  
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
	private static final long serialVersionUID = 1L;
}
