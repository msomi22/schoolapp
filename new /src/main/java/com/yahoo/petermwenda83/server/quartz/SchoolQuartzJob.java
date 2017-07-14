/**
 * 
 */
package com.yahoo.petermwenda83.server.quartz;

import org.hibernate.SessionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yahoo.petermwenda83.persistence.AppHibernateUtil;
import com.yahoo.petermwenda83.persistence.StorageDAO;
import com.yahoo.petermwenda83.persistence.StorageDAOImpl;

/**
 * @author peter
 *
 */
public class SchoolQuartzJob implements Job{
	
	 private StorageDAO storageDAO;
	 private SessionFactory sessionFactory;

	public SchoolQuartzJob() {
		
		   super();
	      
		   sessionFactory = AppHibernateUtil.getSessionFactory();
		   storageDAO = new StorageDAOImpl(sessionFactory); 
	       
		
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		changeStatus();
		
	}

	private void changeStatus() {
		
		final String STATUS_INACTIVE = "0";
		
		
		
	}


}
