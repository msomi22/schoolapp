package com.yahoo.petermwenda83.server.servlet.exam;

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

import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

public class AddGradeScale extends HttpServlet{
	
	
	final String ERROR_EMPTY_FIRLD = "Empty fields/incorrect data type /range."; 
	final String ERROR_SCALE_ADD_ERROR = "An error occured while updating grading scale"; 
	final String ERROR_SCALE_ADD_SUCCESS = "Grading scale updated successfully"; 
	
	private static GradingSystemDAO gradingSystemDAO;

	/** 
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       gradingSystemDAO = GradingSystemDAO.getInstance();
      
   }
   
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String schoolAccountUuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
       String A = StringUtils.trimToEmpty(request.getParameter("A"));
       String Am = StringUtils.trimToEmpty(request.getParameter("A-"));
       String Bp = StringUtils.trimToEmpty(request.getParameter("B+"));
       String B = StringUtils.trimToEmpty(request.getParameter("B"));
       String Bm = StringUtils.trimToEmpty(request.getParameter("B-"));
       String Cp = StringUtils.trimToEmpty(request.getParameter("C+"));
       String C = StringUtils.trimToEmpty(request.getParameter("C"));
       String Cm = StringUtils.trimToEmpty(request.getParameter("C-"));
       String Dp = StringUtils.trimToEmpty(request.getParameter("D+"));
       String D = StringUtils.trimToEmpty(request.getParameter("D"));
       String Dm = StringUtils.trimToEmpty(request.getParameter("D-"));
       String E = StringUtils.trimToEmpty(request.getParameter("E"));
       Map<String, String> paramHash = new HashMap<>();    	
	   paramHash.put("A", A);
	   paramHash.put("Am", Am);
	   paramHash.put("Bp", Bp);
	   paramHash.put("B", B);
	   paramHash.put("Bm", Bm);
	   paramHash.put("Cp", Cp);
	   paramHash.put("C", C);
	   paramHash.put("Cm", Cm);
	   paramHash.put("Dp", Dp);
	   paramHash.put("D", D);
	   paramHash.put("Dm", Dm);
	   paramHash.put("E", E);
       
	   if(StringUtils.isEmpty(A) || !isNumeric(A) || Integer.parseInt(A)>100){
    	   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_EMPTY_FIRLD); 
    	   
       }else if(StringUtils.isEmpty(Am) ||!isNumeric(Am) || Integer.parseInt(Am)>100){
    	   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_EMPTY_FIRLD); 
    	   
       }else if(StringUtils.isEmpty(B) ||!isNumeric(B) || Integer.parseInt(B)>100){
    	   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_EMPTY_FIRLD); 
    	   
       }else if(StringUtils.isEmpty(Bm) ||!isNumeric(Bm) || Integer.parseInt(Bm)>100){
    	   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_EMPTY_FIRLD); 
    	   
       }else if(StringUtils.isEmpty(Cp) ||!isNumeric(Cp) || Integer.parseInt(Cp)>100){
    	   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_EMPTY_FIRLD); 
    	   
       }else if(StringUtils.isEmpty(C) ||!isNumeric(C) || Integer.parseInt(C)>100){
    	   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_EMPTY_FIRLD); 
    	   
       }else if(StringUtils.isEmpty(Cm) ||!isNumeric(Cm) || Integer.parseInt(Cm)>100){
    	   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_EMPTY_FIRLD); 
    	   
       }else if(StringUtils.isEmpty(D) ||!isNumeric(D) || Integer.parseInt(D)>100){
    	   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_EMPTY_FIRLD); 
    	   
       }else if(StringUtils.isEmpty(Dm) ||!isNumeric(Dm) || Integer.parseInt(Dm)>100){
    	   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_EMPTY_FIRLD); 
    	   
       }else if(StringUtils.isEmpty(E) ||!isNumeric(E) || Integer.parseInt(E)>100){
    	   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_EMPTY_FIRLD); 
    	   
       }else{
    	   GradingSystem gradingSystem = gradingSystemDAO.getGradingSystem(schoolAccountUuid);
    	   gradingSystem.setSchoolAccountUuid(schoolAccountUuid); 
    	   gradingSystem.setGradeAplain(Integer.parseInt(A));
    	   gradingSystem.setGradeAminus(Integer.parseInt(Am));
    	   gradingSystem.setGradeBplus(Integer.parseInt(Bp));
    	   gradingSystem.setGradeBplain(Integer.parseInt(B));
    	   gradingSystem.setGradeBminus(Integer.parseInt(Bm));
    	   gradingSystem.setGradeCplus(Integer.parseInt(Cp));
    	   gradingSystem.setGradeCplain(Integer.parseInt(C));
    	   gradingSystem.setGradeCminus(Integer.parseInt(Cm));
    	   gradingSystem.setGradeDplus(Integer.parseInt(Dp));
    	   gradingSystem.setGradeDplain(Integer.parseInt(D));
    	   gradingSystem.setGradeDminus(Integer.parseInt(Dm));
    	   gradingSystem.setGradeE(Integer.parseInt(E)); 
    	   
    	   if(gradingSystemDAO.updateGradingSystem(gradingSystem)){ 
    		   session.setAttribute(SessionConstants.GRADE_ADD_SUCCESS, ERROR_SCALE_ADD_SUCCESS); 
    	   }else{
    		   session.setAttribute(SessionConstants.GRADE_ADD_ERROR, ERROR_SCALE_ADD_ERROR); 
    	   }
    	   
       }
       
       
       session.setAttribute(SessionConstants.GRADE_PARAM, paramHash);
       response.sendRedirect("examConfig.jsp");  
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
      
  

   @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          doPost(request, response);
      }
   
   /**
	 * 
	 */
	private static final long serialVersionUID = -2890202371149608804L;
}
