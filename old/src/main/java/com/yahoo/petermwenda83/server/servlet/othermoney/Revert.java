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

import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.otherfee.RevertedMoney;
import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.othermoney.RevertedMoneyDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/** 
 * @author peter
 *
 */
public class Revert extends HttpServlet{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 8723382962188024179L;
	final String SUCCESS_TRANS_REVERTED = "Transaction Reverted successfully.";
	final String ERROR_TRANS_NOT_REVERTED = "Something went wrong while Reverting the Transaction .";
	final String ERROR_TRANS_NOT_REVERTED_WRONG_TERM_YEAR = "Transaction not reverted, confirm the term and year.";
	
	
	private static StudentOtherFeeDAO studentOtherFeeDAO;
	private static RevertedMoneyDAO revertedMoneyDAO;
	private static SysConfigDAO sysConfigDAO;
	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentOtherFeeDAO = StudentOtherFeeDAO.getInstance();
       revertedMoneyDAO = RevertedMoneyDAO.getInstance();
       sysConfigDAO = SysConfigDAO.getInstance();
       
   }
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String studentuuid = StringUtils.trimToEmpty(request.getParameter("studentuuid"));
       String typeuuid = StringUtils.trimToEmpty(request.getParameter("typeuuid"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String amount = StringUtils.trimToEmpty(request.getParameter("amount"));
       String term = StringUtils.trimToEmpty(request.getParameter("term"));
       String year = StringUtils.trimToEmpty(request.getParameter("year"));
      
	   SysConfig sysConfig = new SysConfig();
		if(sysConfigDAO.getExamConfig(schooluuid) !=null){
			sysConfig = sysConfigDAO.getExamConfig(schooluuid);
		}
	
       
       if(studentOtherFeeDAO.getStudentOtherMonies(studentuuid, typeuuid) ==null){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANS_NOT_REVERTED); 
		   
	   }else if(StringUtils.isBlank(schooluuid)){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANS_NOT_REVERTED); 
			   
	   }else if(StringUtils.isBlank(amount)){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANS_NOT_REVERTED); 
			   
	   }else if(!StringUtils.equals(term, sysConfig.getTerm())){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANS_NOT_REVERTED_WRONG_TERM_YEAR); 
			   
	   }else if(!StringUtils.equals(year, sysConfig.getYear())){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANS_NOT_REVERTED_WRONG_TERM_YEAR); 
			   
	   }else{
		   
		   
		   double theamount = Double.parseDouble(amount);
		   StudentOtherFee studentOtherFee = new StudentOtherFee();
		   studentOtherFee.setStudentUuid(studentuuid);
		   studentOtherFee.setOtherstypeUuid(typeuuid);
		   
		   RevertedMoney revertedMoney = new RevertedMoney();
		   revertedMoney.setStudentUuid(studentuuid); 
		   revertedMoney.setOtherstypeUuid(typeuuid);
		   revertedMoney.setAmount(theamount);
		   revertedMoney.setTerm(sysConfig.getTerm());
		   revertedMoney.setYear(sysConfig.getYear());
	
		   revertedMoneyDAO.putstudentUuid(revertedMoney);
		   
		   if(studentOtherFeeDAO.deleteStudentOtherMonies(studentOtherFee)){ 
			   session.setAttribute(SessionConstants.STUDENT_FEE_ADD_SUCCESS, SUCCESS_TRANS_REVERTED); 
		   }else{
			   session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANS_NOT_REVERTED);  
		   }
		   
	   }
       
       response.sendRedirect("fee.jsp");  
	   return;
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
