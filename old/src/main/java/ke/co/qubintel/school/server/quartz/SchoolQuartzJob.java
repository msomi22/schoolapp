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
package ke.co.qubintel.school.server.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.cache.CacheVariables;
import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.persistence.staff.StaffDAO;
import ke.co.qubintel.school.server.servlet.util.SYS_COSTANTS;
import ke.co.qubintel.school.server.servlet.util.SecurityUtil;
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
