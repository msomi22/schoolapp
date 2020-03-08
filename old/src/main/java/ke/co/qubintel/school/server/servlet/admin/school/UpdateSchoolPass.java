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
package ke.co.qubintel.school.server.servlet.admin.school;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.cache.CacheVariables;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.servlet.util.SecurityUtil;
import ke.co.qubintel.school.server.session.AdminSessionConstants;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/** 
 * @author peter
 *
 */
public class UpdateSchoolPass extends HttpServlet{

	final String ERROR_EMPTY_FIELD = "Emptyfields not allowed" ;
	
	final String PASS_MISMATCH = "Password mismatch" ;
	final String INCORRECT_OLD_PASS = "Old password Incorrect";
	
	final String UPDATE_ERROR = " An error occured while updating Password";
	final String UPDATE_SUCSESS = "Password updated successfully";
	
	private static AccountDAO accountDAO;
	private CacheManager cacheManager;
	
	/**
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       accountDAO = AccountDAO.getInstance();
       cacheManager = CacheManager.getInstance();
	}
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String oldpassword = StringUtils.trimToEmpty(request.getParameter("oldpassword"));
       String newpassowrd = StringUtils.trimToEmpty(request.getParameter("newpassword"));
       String confirmpassword = StringUtils.trimToEmpty(request.getParameter("confirmpassword"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       
      
       if(StringUtils.isEmpty(oldpassword)){
     	   session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR, ERROR_EMPTY_FIELD); 
     	   
        }else if(StringUtils.isEmpty(newpassowrd)){
     	   session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR, ERROR_EMPTY_FIELD); 
     	   
        }else if(StringUtils.isEmpty(confirmpassword)){
     	   session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR, ERROR_EMPTY_FIELD); 
     	   
        }else if(StringUtils.isEmpty(schooluuid)){
     	   session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR, UPDATE_ERROR); 
     	   
        }else if(!StringUtils.equals(confirmpassword, newpassowrd)){
     	   session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR, PASS_MISMATCH); 
     	   
        }else if(accountDAO.getAccountByPassword(schooluuid, SecurityUtil.getMD5Hash(oldpassword)) == null){
      	   session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR, INCORRECT_OLD_PASS); 
     	   
         }else{
        	
        	 //System.out.println("schoolpassword = " +  schoolpassword);
        	 Account account =  accountDAO.getAccountById(schooluuid); 
        	 account.setPassword(SecurityUtil.getMD5Hash(newpassowrd)); 
        	 updateSchoolCache(account);
  		       if(accountDAO.updateAccount(account) ){ 
  			    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_SUCCESS, UPDATE_SUCSESS); 
  			  
  		      }else{
  			    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR, UPDATE_ERROR);  
  		   }
        	
        }
        
       
       response.sendRedirect("adminIndex.jsp"); 
       return;
       
   }
   
   
   

   /**
 * @param accnt
 */
private void updateSchoolCache(Account accnt) {
   	cacheManager.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME).put(new Element(accnt.getUsername(), accnt));
   	cacheManager.getCache(CacheVariables.CACHE_ACCOUNTS_BY_UUID).put(new Element(accnt.getUuid(), accnt));
   }
   

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
