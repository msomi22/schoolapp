/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.money;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.money.Deposit;
import com.yahoo.petermwenda83.bean.money.PocketMoney;
import com.yahoo.petermwenda83.bean.money.Withdraw;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.money.PMoneyDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/** 
 * @author peter
 *
 */
public class FindStudentPM extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3445421308680688055L;
	final String ERROR_STUDENT_NOT_FOUND = " The admission number you provided was not found in the system.";
	final String STUDENT_FIND_SUCCESS = "The student was found , proceed";
    final String ERROR_NO_ADMNO = "You didn't provide any admission  number?.";
    
    private static StudentDAO studentDAO;
    private static PMoneyDAO pMoneyDAO;
    private static ExamConfigDAO examConfigDAO;
    ExamConfig examConfig;


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
       examConfigDAO = ExamConfigDAO.getInstance();
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

	   HttpSession session = request.getSession(true);
       String admissionNumber = StringUtils.trimToEmpty(request.getParameter("AdmNo"));
       String schoolUuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       Map<String, Student> paramHash = new HashMap<>(); 
       Map<String, PocketMoney> PMparamHash = new HashMap<>();
       Map<String, List<Deposit>> DListparamHash = new HashMap<>();
       Map<String, List<Withdraw>> WListparamHash = new HashMap<>();

       if(StringUtils.isBlank(admissionNumber)){
		     session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_NO_ADMNO); 
		   
	   }else if(studentDAO.getStudentObjByadmNo(schoolUuid,admissionNumber)==null){ 
		   session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, ERROR_STUDENT_NOT_FOUND); 
		  
	   }else{
		   
		   examConfig = examConfigDAO.getExamConfig(schoolUuid);
		   
		   Student student = new Student();
		   if(studentDAO.getStudentObjByadmNo(schoolUuid, admissionNumber) !=null){
			   student = studentDAO.getStudentObjByadmNo(schoolUuid, admissionNumber);
			   paramHash.put("studentObj", student); 
		   }
		   
		   
		   PocketMoney pMoney = new PocketMoney();
		   if(pMoneyDAO.getMoney(student.getUuid()) !=null){
			   pMoney = pMoneyDAO.getMoney(student.getUuid());
			   PMparamHash.put("pocketmObject", pMoney);
			   
		   }
		   
		   List<Deposit> pmDList = new ArrayList<>(); 
		   List<Withdraw> pmWList = new ArrayList<>();
		   if(pMoneyDAO.getDepositList(student.getUuid()) !=null){
			   pmDList = pMoneyDAO.getDepositList(student.getUuid());
			   DListparamHash.put("depositList", pmDList);
			   
		   }
		  
		   if(pMoneyDAO.getWithdrawList(student.getUuid()) !=null){
			   pmWList = pMoneyDAO.getWithdrawList(student.getUuid());
			   WListparamHash.put("withdrawList", pmWList); 
			   
		   }
		   
		   
	       session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, STUDENT_FIND_SUCCESS);     
	   }
 
       session.setAttribute(SessionConstants.STUENT_POCKET_MONEY_PARAM, PMparamHash); 
       session.setAttribute(SessionConstants.STUENT_PM_DEPOSITE_PARAM, DListparamHash); 
       session.setAttribute(SessionConstants.STUENT_PM_WITHDRAW_PARAM, WListparamHash); 
       session.setAttribute(SessionConstants.STUENT_POCKET_MONEY_FIND_PARAM, paramHash); 
       response.sendRedirect("pocketM.jsp");  
	   return;
       
       
       
   }
   
   

   @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         doPost(request, response);
     }

}
