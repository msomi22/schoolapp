/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.student.clearance;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.money.StudentClearance;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.money.StudentClearanceDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/** 
 * @author peter
 *
 */
public class FindBalance extends HttpServlet{
	
	final String INIT_ERROR = "Something went wrong, try again.";
	final String INIT_SUCCESS = "Success , see the fee balance below.";
	
	private static StudentClearanceDAO studentClearanceDAO;
	private static StudentDAO studentDAO;
	private static TermFeeDAO termFeeDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static StudentOtherMoniesDAO studentOtherMoniesDAO;
	String [] terms;
	List<StudentFee> studentFeeList;
	List<StudentOtherMonies> othermoneyList;
	

	String feeStudentType = "";
	String lastTermState = "";
	
	/**  
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       studentClearanceDAO = StudentClearanceDAO.getInstance();
       studentDAO = StudentDAO.getInstance();
       termFeeDAO = TermFeeDAO.getInstance();
       studentFeeDAO = StudentFeeDAO.getInstance();
       studentOtherMoniesDAO = StudentOtherMoniesDAO.getInstance();
       terms = new String [] {"1","2","3"};
       studentFeeList = new ArrayList<>();
	   othermoneyList = new ArrayList<>();
   }
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String studentuuid = StringUtils.trimToEmpty(request.getParameter("studentuuid"));
       String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String finalyear = StringUtils.trimToEmpty(request.getParameter("finalyear"));
       Map<String, Double> balParamHash = new HashMap<>(); 

       if(StringUtils.isBlank(studentuuid)){
		     session.setAttribute(SessionConstants.CLEAR_ERROR, INIT_ERROR); 
		   
	   }else if(StringUtils.isBlank(finalyear)){
		   session.setAttribute(SessionConstants.CLEAR_ERROR, INIT_ERROR); 
		   
	   }else if(StringUtils.isBlank(schooluuid)){
		   session.setAttribute(SessionConstants.CLEAR_ERROR, INIT_ERROR); 
		   
	   }else if(studentClearanceDAO.getClearanceByYear(studentuuid,finalyear) == null){
		   session.setAttribute(SessionConstants.CLEAR_ERROR, INIT_ERROR); 
		   
	   }else{
		   
		   Student student = new Student();
		   if(studentDAO.getStudentByuuid(schooluuid, studentuuid) !=null){
			   student = studentDAO.getStudentByuuid(schooluuid, studentuuid);
		   }
		   
		    double balance = 0;
			double amountPaid = 0;
			double otherPaid = 0;
			int finalterm = 0;
			String finalTermstr = "";
			
		   if(student !=null){
			   SimpleDateFormat formatter;
			   formatter = new SimpleDateFormat("yyyy");
			   String admyear = formatter.format(student.getAdmissionDate());
			   String admterm = student.getRegTerm();
			   finalterm = student.getFinalTerm();
			   finalTermstr = Integer.toString(finalterm); 
			   int admYr = Integer.parseInt(admyear);
			   int admTm = Integer.parseInt(admterm);
			   
			   int finalYr = Integer.parseInt(finalyear);
			  
				
				if(admYr == admYr){
				     if(admTm == 2){   
				    	 terms = new String [] {"2","3"}; 
				     }else if(admTm == 3){  
				    	 terms = new String [] {"3"};
				     }else  if(admTm == 1){
				    	 terms = new String [] {"1","2","3"}; 
				     }
				}

				while(admYr <= finalYr){ 
					//start from admission year
					String year = Integer.toString(admYr);
					String term = "";
					for(int i=0;i<terms.length;i++){
						term = terms[i];
						
						//start finding the balance here
						if(studentFeeDAO.getStudentFeeByStudentUuidList(schooluuid, studentuuid, term, year) !=null){
						studentFeeList = studentFeeDAO.getStudentFeeByStudentUuidList(schooluuid, studentuuid, term, year);
						}
						if(studentOtherMoniesDAO.getStudentOtherList(studentuuid, term, year) !=null){
						othermoneyList = studentOtherMoniesDAO.getStudentOtherList(studentuuid, term, year);
						}
						TermFee admTermFee = new TermFee();
						if(termFeeDAO.getFee(schooluuid,term, year) !=null){
						admTermFee = termFeeDAO.getFee(schooluuid,term, year);
						}
						
						
						amountPaid = 0;
						otherPaid = 0;
						if(studentFeeList!=null){
						for(StudentFee studentFee : studentFeeList){
							amountPaid +=studentFee.getAmountPaid();
							feeStudentType = studentFee.getStudentType();
						}
					   }
					
					    if(othermoneyList!=null){
						for(StudentOtherMonies otherMoney :othermoneyList){
							otherPaid += otherMoney.getAmountPiad();
						}
				       }
					
					    if(StringUtils.equals(feeStudentType, "Boarder")){
							 balance += (admTermFee.getTermAmount() + otherPaid) - amountPaid;
					    }else if(StringUtils.equals(feeStudentType, "Day")) {
							 balance += (admTermFee.getDayAmount() + otherPaid) - amountPaid;
						}
							
						
						if(admYr == finalYr){
							if(StringUtils.equals(term,finalTermstr)){
								break;
							}
						}

					}
					
					terms = new String [] {"1","2","3"}; 
					admYr +=1;
				}
			   
			   
		   }
		   
		   balParamHash.put("feeBalance", balance);
		   Locale locale = new Locale("en","KE"); 
		   NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		   session.setAttribute(SessionConstants.CLEAR_SUCCESS, "Fee Balance = " +  nf.format(balance)); 
	   }
       session.setAttribute(SessionConstants.FEE_BALANCE_PARAM, balParamHash); 
       response.sendRedirect("studentClearance.jsp");  
	   return;
       
     }
   
@Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
      doPost(request, response);
  }
/**
 * 
 */
private static final long serialVersionUID = 4087438580074689381L;

}
