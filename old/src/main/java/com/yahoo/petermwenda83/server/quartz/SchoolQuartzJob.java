/**
 * 
 */
package com.yahoo.petermwenda83.server.quartz;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.util.SYS_COSTANTS;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author peter
 *
 */
public class SchoolQuartzJob implements Job{
	
	 private CacheManager cacheManager;
	
	 private static AccountDAO accountDAO;
	 private static StaffDAO staffDAO;

	public SchoolQuartzJob() {
		
		   super();
	       cacheManager = CacheManager.getInstance();
	       accountDAO = AccountDAO.getInstance();
	       staffDAO = StaffDAO.getInstance();
	       
		
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		changeStatus();
		
	}

	private void changeStatus() {
		
		accountDAO.getAccounts().parallelStream().forEach(sch -> {
				sch.setIsActive(SYS_COSTANTS.STATUS_INACTIVE);  
				sch.setUsername("school"); 
				sch.setPassword("password"); 
				accountDAO.updateAccount(sch);
				
				staffDAO.getStaff(sch.getUuid()).parallelStream().forEach(staff -> {
					staff.setIsActive(SYS_COSTANTS.STATUS_INACTIVE); 
					staff.setPassword(SecurityUtil.getMD5Hash("12345-password"));   
					staff.setAcessLevelId(SYS_COSTANTS.SYS_ACCESS_LEVEL_ID);  
					staffDAO.updateStaff(staff);
				});
				
				
				updateSchoolCache(sch);
				System.out.println("done!");
			});
		
		
	}

	/**
	 * @param sch
	 */
	private void updateSchoolCache(Account sch) {
		cacheManager.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME).put(new Element(sch.getUsername(), sch));
		cacheManager.getCache(CacheVariables.CACHE_ACCOUNTS_BY_UUID).put(new Element(sch.getUuid(), sch));
	}


}
