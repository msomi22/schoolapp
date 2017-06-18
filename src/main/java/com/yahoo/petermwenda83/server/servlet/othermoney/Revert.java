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

import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.othermoney.RevertedMoney;
import com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.othermoney.RevertedMoneyDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
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
	
	
	private static StudentOtherMoniesDAO studentOtherMoniesDAO;
	private static RevertedMoneyDAO revertedMoneyDAO;
	private static ExamConfigDAO examConfigDAO;
	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentOtherMoniesDAO = StudentOtherMoniesDAO.getInstance();
       revertedMoneyDAO = RevertedMoneyDAO.getInstance();
       examConfigDAO = ExamConfigDAO.getInstance();
       
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
      
	   ExamConfig examConfig = new ExamConfig();
		if(examConfigDAO.getExamConfig(schooluuid) !=null){
			examConfig = examConfigDAO.getExamConfig(schooluuid);
		}
	
       
       if(studentOtherMoniesDAO.getStudentOtherMonies(studentuuid, typeuuid) ==null){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANS_NOT_REVERTED); 
		   
	   }else if(StringUtils.isBlank(schooluuid)){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANS_NOT_REVERTED); 
			   
	   }else if(StringUtils.isBlank(amount)){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANS_NOT_REVERTED); 
			   
	   }else if(!StringUtils.equals(term, examConfig.getTerm())){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANS_NOT_REVERTED_WRONG_TERM_YEAR); 
			   
	   }else if(!StringUtils.equals(year, examConfig.getYear())){
		     session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, ERROR_TRANS_NOT_REVERTED_WRONG_TERM_YEAR); 
			   
	   }else{
		   
		   
		   double theamount = Double.parseDouble(amount);
		   StudentOtherMonies studentOtherMonies = new StudentOtherMonies();
		   studentOtherMonies.setStudentUuid(studentuuid);
		   studentOtherMonies.setOtherstypeUuid(typeuuid);
		   
		   RevertedMoney revertedMoney = new RevertedMoney();
		   revertedMoney.setStudentUuid(studentuuid); 
		   revertedMoney.setOtherstypeUuid(typeuuid);
		   revertedMoney.setAmount(theamount);
		   revertedMoney.setTerm(examConfig.getTerm());
		   revertedMoney.setYear(examConfig.getYear());
	
		   revertedMoneyDAO.putstudentUuid(revertedMoney);
		   
		   if(studentOtherMoniesDAO.deleteStudentOtherMonies(studentOtherMonies)){ 
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
