/**
 * 
 */
package com.yahoo.petermwenda83.server.quartz;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author peter
 *
 */
public class SchoolQuartzJob implements Job{
	
	 private CacheManager cacheManager;
	
	 private static AccountDAO accountDAO;

	public SchoolQuartzJob() {
		
		   super();
	       cacheManager = CacheManager.getInstance();
	       accountDAO = AccountDAO.getInstance();
	       
		
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		changeStatus();
		
	}

	private void changeStatus() {
		
		final String STATUS_INACTIVE = "6C03705B-E05E-420B-B5B8-C7EE36643E60";
		
		List<SchoolAccount> schoolList = new ArrayList<>();
		schoolList = accountDAO.getAllSchools();
		for(SchoolAccount sch : schoolList){
			sch.setStatusUuid(STATUS_INACTIVE); 
			//sch.setUsername("school"); 
			sch.setPassword("password"); 
			accountDAO.update(sch);
			updateSchoolCache(sch);
			System.out.println("done!");
		}
		
	}

	/**
	 * @param sch
	 */
	private void updateSchoolCache(SchoolAccount sch) {
		cacheManager.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME).put(new Element(sch.getUsername(), sch));
		cacheManager.getCache(CacheVariables.CACHE_ACCOUNTS_BY_UUID).put(new Element(sch.getUuid(), sch));
	}


}
