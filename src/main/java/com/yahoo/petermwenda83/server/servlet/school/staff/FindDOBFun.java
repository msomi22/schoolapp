package com.yahoo.petermwenda83.server.servlet.school.staff;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**
 * 
 * @author peter
 *
 */
public class FindDOBFun extends HttpServlet{

	
	final String PROVIDE_YEAR_OF_BIRTH = "Please provide your year of birth";
	final String PROVIDE_MONTH_OF_BIRTH = "Please provide your month of birth";
	final String PROVIDE_DAY_OF_BIRTH = "Please provide your day of birth";

	/**   
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String year = StringUtils.trimToEmpty(request.getParameter("year"));
		String month = StringUtils.trimToEmpty(request.getParameter("month"));
		String day = StringUtils.trimToEmpty(request.getParameter("day"));


		if(StringUtils.isEmpty(year)){
			session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, PROVIDE_YEAR_OF_BIRTH); 

		}else if(StringUtils.isEmpty(month)){
			session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, PROVIDE_MONTH_OF_BIRTH); 

		}else if(StringUtils.isEmpty(day)){ 
			session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, PROVIDE_DAY_OF_BIRTH);  

		}else{
			String dob = "";
			dob = year+"-"+month+"-"+day;
			
			try
			{

			DateTimeFormatter fullFormat =	DateTimeFormatter.ofPattern("MMMM d, YYYY");
			DateTimeFormatter monthDayFormat = DateTimeFormatter.ofPattern("MMMM d");

			LocalDate birthDate;
			birthDate = LocalDate.parse(dob);
			
			DayOfWeek birthDayOfWeek = birthDate.getDayOfWeek();	
			long years = birthDate.until(LocalDate.now(), ChronoUnit.YEARS);
			LocalDate nextBDay = birthDate.plusYears(years + 1);	
			long wait = LocalDate.now().until(nextBDay,	ChronoUnit.DAYS);
			LocalDate halfBirthday = birthDate.plusMonths(6);
			

			if (birthDate.isAfter(LocalDate.now()))	
			{
				session.setAttribute(SessionConstants.STAFF_UPDATE_SUCCESS, " You are not yet born!");
			} else{
				
				session.setAttribute(SessionConstants.STAFF_UPDATE_SUCCESS, "Mambo, you were born on " + birthDate.format(fullFormat) +
						", it was on a " + birthDayOfWeek + "." + "Your next birthday is " + nextBDay.format(fullFormat) + "." + 
						"That's just " + wait + " days from now!" + "Your half-birthday is " + halfBirthday.format(monthDayFormat) +
						". Thanks, in case of anything ping me on 0718953974 ."); 
				
			}

			
			

			}catch (DateTimeParseException ex){
				session.setAttribute(SessionConstants.STAFF_UPDATE_SUCCESS, " Sorry, that is not a valid date.");
			}
		}

		response.sendRedirect("profile.jsp");  
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
	private static final long serialVersionUID = 1L;


}
