/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.exam;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;
/**
 * @author peter
 *
 */
public class UpdateExamConfig extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7880606806285167190L;
	
	
	private static ExamConfigDAO examConfigDAO;
	
	
	TermFee termFee;
	ExamConfig examConfig;
	
	private String[] examcodeArray;
	private List<String> examcodeList;
	
	private String[] exammodeArray;
	private String[] smsSendArray;
	private List<String> exammodeList;
	private List<String> smsSendList;
	
	
	final String ERROR_EMPTY_FIELD = "Empty fields are not allowed.";
	final String ERROR_YEAR_OUTSIDE_RANGE = "Confirm that the year you entered is correct and try again";
	final String ERROR_TERM_NOT_ALLOWED = "Term value can't be greater that three (3)";
	final String ERROR_TERM_NUMERIC = "Term can only be numeric";
	final String ERROR_YEAR_NUMERIC = "Year can only be numeric";
	final String ERROR_EXAM_MODE_NOT_ALLOWED = "Exam Code can only be  ON or OFF";
	final String ERROR_SMS_SEND_NOT_ALLOWED = "SMS send enable can onlyy be ON or OFF";
	final String ERROR_EXAM_NOT_FOUND = "Exam code not found";
	
	/**
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
        examConfigDAO = ExamConfigDAO.getInstance();
        examcodeArray = new String[] {"C1", "C2", "ET", "P1","P2","P3"};
		examcodeList = Arrays.asList(examcodeArray);
		
		exammodeArray = new String[] {"ON","OFF"};
		smsSendArray = new String[] {"ON","OFF"};
		exammodeList = Arrays.asList(exammodeArray);
		smsSendList = Arrays.asList(smsSendArray);
		
		
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       HttpSession session = request.getSession(true);
       
       String schoolAccountUuid = StringUtils.trimToEmpty(request.getParameter("schoolUuid"));
       String exam = StringUtils.trimToEmpty(request.getParameter("exam"));
       String exammode = StringUtils.trimToEmpty(request.getParameter("exammode"));
       String sendSmsEnable = StringUtils.trimToEmpty(request.getParameter("sendSmsEnable"));
       
       String eTFone = StringUtils.trimToEmpty(request.getParameter("eTFone"));
       String eT = StringUtils.trimToEmpty(request.getParameter("eT"));
       String eTCtwo = StringUtils.trimToEmpty(request.getParameter("eTCtwo"));
       String eTConetwo = StringUtils.trimToEmpty(request.getParameter("eTConetwo"));

       if(StringUtils.isEmpty(exam)){
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_FIELD); 
    	   
        }else if(!examcodeList.contains(exam)){ 
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EXAM_NOT_FOUND); 
    	   
       }else if(StringUtils.isEmpty(exammode)){
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_FIELD); 
    	   
       }else if(!exammodeList.contains(exammode)){  
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EXAM_MODE_NOT_ALLOWED); 
    	   
       }else if(!exammodeList.contains(eTFone)){  
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EXAM_MODE_NOT_ALLOWED); 
    	   
       }else if(!exammodeList.contains(eT)){  
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EXAM_MODE_NOT_ALLOWED); 
    	   
       }else if(!exammodeList.contains(eTCtwo)){  
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EXAM_MODE_NOT_ALLOWED); 
    	   
       }else if(!exammodeList.contains(eTConetwo)){  
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EXAM_MODE_NOT_ALLOWED); 
    	   
       }else if(!validCodes(eT,eTCtwo,eTConetwo)){  //
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, "Only one of the 'ET', 'ET C2' or 'ET C1 C2' can be ON "); 
    	   
       }else if(!smsSendList.contains(sendSmsEnable)){  
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_SMS_SEND_NOT_ALLOWED); 
    	   
       }else if(StringUtils.isEmpty(schoolAccountUuid)){
   	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_FIELD); 
   	   
      }else{
    	   
    	
       ExamConfig examConfig = examConfigDAO.getExamConfig(schoolAccountUuid);
       examConfig.setExam(exam);
       examConfig.setExamMode(exammode); 
       examConfig.setSendSMS(sendSmsEnable);
       examConfig.seteTFone(eTFone);
       
       examConfig.seteT(eT);
       examConfig.seteTCtwo(eTCtwo);
       examConfig.seteTConetwo(eTConetwo); 
       
       if(examConfigDAO.updateExamConfig(examConfig)){
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS, SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS); 
    	   
    	   
       }else{
    	   session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, SessionConstants.EXAM_CONFIG_UPDATE_ERROR); 
    	    
       }
       
       }
       
        response.sendRedirect("examConfig.jsp"); 
	   return;
   }
   

   /**
 * @param eT
 * @param eTCtwo
 * @param eTConetwo
 * @return
 */
private boolean validCodes(String eT, String eTCtwo, String eTConetwo) {
	boolean valid = true;
	
	if(StringUtils.equals(eT, "ON") && StringUtils.equals(eTCtwo, "ON") && StringUtils.equals(eTConetwo, "ON")){
		valid = false;
		
	}else if(StringUtils.equals(eT, "OFF") && StringUtils.equals(eTCtwo, "OFF") && StringUtils.equals(eTConetwo, "OFF")){
		valid = false;
		
	}else if(StringUtils.equals(eT, "ON") && StringUtils.equals(eTCtwo, "ON") ){
		valid = false;	
		
	}else if(StringUtils.equals(eT, "ON") && StringUtils.equals(eTConetwo, "ON") ){
		valid = false;	
		
	}else if(StringUtils.equals(eT, "ON") && StringUtils.equals(eTCtwo, "ON") ){
		valid = false;	
		
	}else if(StringUtils.equals(eTCtwo, "ON") && StringUtils.equals(eTConetwo, "ON") ){
		valid = false;	
		
	}else{
		valid = true;
	}
	
	
	return valid;
}

@Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          doPost(request, response);
      }
}
