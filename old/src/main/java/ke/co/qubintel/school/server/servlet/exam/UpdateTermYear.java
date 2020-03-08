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
package ke.co.qubintel.school.server.servlet.exam;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.money.TermFee;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.money.TermFeeDAO;
import ke.co.qubintel.school.server.servlet.util.PropertiesConfig;
import ke.co.qubintel.school.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class UpdateTermYear extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7880606806285167190L;


	private static SysConfigDAO sysConfigDAO;
	private static TermFeeDAO termFeeDAO;


	TermFee termFee;
	SysConfig sysConfig;
/*
	private String[] examcodeArray;
	private List<String> examcodeList;

	private String[] exammodeArray;
	private List<String> exammodeList;
*/

	final String ERROR_EMPTY_FIELD = "Empty fields are not allowed.";
	final String ERROR_YEAR_OUTSIDE_RANGE = "Confirm that the year you entered is correct and try again";
	final String ERROR_TERM_NOT_ALLOWED = "Term value can't be greater that three (3)";
	final String ERROR_TERM_NUMERIC = "Term can only be numeric";
	final String ERROR_YEAR_NUMERIC = "Year can only be numeric";
	final String ERROR_EXAM_MODE_NOT_ALLOWED = "Exam Mode can only be  ON or OFF";
	final String ERROR_EXAM_NOT_FOUND = "Exam code not found";
	final String ERROR_INCORRECT_SEC_KEY = "Incorrect Security Key";

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sysConfigDAO = SysConfigDAO.getInstance();
		/*examcodeArray = new String[] {"C1", "C2", "ET", "P1","P2","P3"};
		examcodeList = Arrays.asList(examcodeArray);

		exammodeArray = new String[] {"ON","OFF"};
		exammodeList = Arrays.asList(exammodeArray);*/
		termFeeDAO = TermFeeDAO.getInstance();


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String schoolAccountUuid = StringUtils.trimToEmpty(request.getParameter("schoolUuid"));
		String term = StringUtils.trimToEmpty(request.getParameter("term"));
		String year = StringUtils.trimToEmpty(request.getParameter("year"));
		String termYearSecKey = StringUtils.trimToEmpty(request.getParameter("TermYearSecKey"));//TermYearSecKey

		Calendar calendar = Calendar.getInstance();
		final int YEAR = calendar.get(Calendar.YEAR);
		int currentYearplusone = YEAR+1;

		if(StringUtils.isEmpty(term)){
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_FIELD); 
		}
		else if(!isNumeric(term)){
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_TERM_NUMERIC); 

		}else if(Integer.parseInt(term) >3 || Integer.parseInt(term) ==0){
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_TERM_NOT_ALLOWED); 

		}else if(StringUtils.isEmpty(year)){
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_FIELD); 

		}else if(!isNumeric(year)){
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_YEAR_NUMERIC); 

		}
		else if(Integer.parseInt(year)>currentYearplusone || Integer.parseInt(year)<YEAR-1){ 
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_YEAR_OUTSIDE_RANGE); 

		}else if(StringUtils.isEmpty(schoolAccountUuid)){
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_EMPTY_FIELD); 

		}else if(!StringUtils.equals(termYearSecKey, PropertiesConfig.getConfigValue("TY_SECURITY_KEY"))){
			session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, ERROR_INCORRECT_SEC_KEY); 

		}else{/*


			SysConfig sysConfig = sysConfigDAO.getExamConfig(schoolAccountUuid);
			updatTermFee(sysConfig,year);
			sysConfig.setTerm(term);
			sysConfig.setYear(year);

			if(sysConfigDAO.updateExamConfig(sysConfig)){
				session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS, SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS +" Confirm please!! [ new Term is " + sysConfig.getTerm() +" and new Year is " + sysConfig.getYear() + " ]"); 


			}else{
				session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, SessionConstants.EXAM_CONFIG_UPDATE_ERROR); 

			}

		*/}

		response.sendRedirect("prepareCommitt.jsp");  
		return;
	}


	/**
	 * When we increment year new year fee is generated 
	 * 
	 * @param examConf
	 * @param year2 
	 */
	private void updatTermFee(SysConfig examConf, String year) {
		if(Integer.parseInt(year) > Integer.parseInt(examConf.getYear())){
			String [] terms = {"1","2","3"};
			double [] fee = {14000,7000,3000};
			double [] dayfee = {5000,4000,3000};
			for(int i=0; i<terms.length;i++){
				
			}
		}

	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {  
		try  
		{  
			Double.parseDouble(str);  

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
}
