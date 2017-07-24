/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.admin.school;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;
import com.yahoo.petermwenda83.server.session.AdminSessionConstants;

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
     	   
        }else if(accountDAO.getSchool(schooluuid, SecurityUtil.getMD5Hash(oldpassword)) == null){
      	   session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR, INCORRECT_OLD_PASS); 
     	   
         }else{
        	
        	 //System.out.println("schoolpassword = " +  schoolpassword);
        	 SchoolAccount account =  accountDAO.get(schooluuid); 
        	 account.setPassword(SecurityUtil.getMD5Hash(newpassowrd)); 
        	 updateSchoolCache(account);
  		       if(accountDAO.update(account) ){ 
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
private void updateSchoolCache(SchoolAccount accnt) {
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
