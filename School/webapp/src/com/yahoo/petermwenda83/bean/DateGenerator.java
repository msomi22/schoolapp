/**
 * 
 */
package com.yahoo.petermwenda83.bean;

import java.time.LocalDateTime;
import java.util.Date;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.persistence.schoolaccount.MiscellanousDAO;
import com.yahoo.petermwenda83.server.servlet.upload.ExcelUtil;

/**
 * @author pmnjeru
 *
 */
public class DateGenerator {
	
	private static MiscellanousDAO miscellanousDAO;
	
	static {
		miscellanousDAO = MiscellanousDAO.getInstance();
	}

	/**
	 * 
	 */
	public DateGenerator() {
		
	}
	
	public static Date validByCreationDate(SchoolAccount school) {
		LocalDateTime now = ExcelUtil.convertToLocalDateTimeViaInstant(new java.util.Date());
		if(miscellanousDAO.getKey(school.getUuid(), "DAYS_SINCE_CREATION") != null) { 
			
		}
		
		return null;
	}
	

}
