/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.othermoney;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.otherfee.OtherFee;
import com.yahoo.petermwenda83.bean.otherfee.TermOtherMonies;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherstypeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.TermOtherMoniesDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author peter
 *
 */
public class UpdateNewPayment  extends HttpServlet{


	final String MONEY_ASSIGNED_SUCCESS = "The updated successfully.";
	final String MONEY_ASSIGNED_ERROR = "Something went wrong while updated the details.";
	final String ERROR_AMOUNT_INVALID = "Invalid amount.";

	private static SysConfigDAO sysConfigDAO;
	private static TermOtherMoniesDAO termOtherMoniesDAO;
	private static OtherstypeDAO otherstypeDAO;
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
		sysConfigDAO = SysConfigDAO.getInstance();
		termOtherMoniesDAO = TermOtherMoniesDAO.getInstance();
		otherstypeDAO = OtherstypeDAO.getInstance();
		
		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String OtherstypeUuid = StringUtils.trimToEmpty(request.getParameter("otherstypeUuid"));
		String amount = StringUtils.trimToEmpty(request.getParameter("amount"));
		String type = StringUtils.trimToEmpty(request.getParameter("type"));
		
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

		if(StringUtils.isBlank(type)){
			session.setAttribute(SessionConstants.OTHER_MONIES_ADD_ERROR, MONEY_ASSIGNED_ERROR); 

		}else if(StringUtils.isBlank(OtherstypeUuid)){
			session.setAttribute(SessionConstants.OTHER_MONIES_ADD_ERROR, MONEY_ASSIGNED_ERROR); 

		}else if(!isNumeric(amount)){
	    	   session.setAttribute(SessionConstants.OTHER_MONIES_ADD_ERROR, ERROR_AMOUNT_INVALID); 
	    	   
	    }else if(!lengthValid(amount)){
		 	   session.setAttribute(SessionConstants.OTHER_MONIES_ADD_ERROR, ERROR_AMOUNT_INVALID); 
			   
		 }else{
			
			OtherFee otherFee = new OtherFee();
			if(otherstypeDAO.getOtherstype(OtherstypeUuid) !=null){
			otherFee = otherstypeDAO.getOtherstype(OtherstypeUuid);
			otherFee.setUuid(OtherstypeUuid);
			otherFee.setSchoolAccountUuid(school.getUuid()); 
			otherFee.setTerm(sysConfig.getTerm());
			otherFee.setYear(sysConfig.getYear()); 
			otherFee.setType(type); 
			}
			
			TermOtherMonies termOtherMonies = new TermOtherMonies();
			if(termOtherMoniesDAO.getTermOtherMoney(school.getUuid(),OtherstypeUuid) !=null){
		    termOtherMonies = termOtherMoniesDAO.getTermOtherMoney(school.getUuid(),OtherstypeUuid);
			termOtherMonies.setAmount(Double.parseDouble(amount));
			termOtherMonies.setOtherstypeUuid(OtherstypeUuid);
			termOtherMonies.setSchoolAccountUuid(school.getUuid());
			}
			
		     if(otherstypeDAO.updteOtherstype(otherFee) && termOtherMoniesDAO.updateTermOtherMonies(termOtherMonies)){
			  session.setAttribute(SessionConstants.OTHER_MONIES_ADD_SUCESS, MONEY_ASSIGNED_SUCCESS); 
		     }else{
		      session.setAttribute(SessionConstants.OTHER_MONIES_ADD_ERROR, MONEY_ASSIGNED_ERROR); 
		     }
		}

		response.sendRedirect("newPayment.jsp");  
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
		if(length<3 ||length>5){
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
	private static final long serialVersionUID = -2612273960810030426L;
}
