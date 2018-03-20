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
package ke.co.qubintel.school.server.servlet.quartz.factory;

import static org.quartz.JobBuilder.*;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import ke.co.qubintel.school.server.quartz.DbOperationsJob;

import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
/** 
 * 
 * @author peter
 *
 */
public class ScheduleJOb extends HttpServlet implements ServletContextListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	Scheduler scheduler = null;
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {

        try {
        	
        	//JobDataMap jobData = new JobDataMap();
        	//jobData.put("NAME", "TEST_PARAMETER");
        	
        	
            // Create Job
            JobDetail account_lock_job = newJob(AccountLockJOB.class).withIdentity
                    ("AccountLock", "Account")
                    //.setJobData(jobData) // Set parameters 
                    .build();
            
            // Create Trigger
            Trigger account_lock_trigger = newTrigger().withIdentity
                    ("lockTrigger", "Account")
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 15 10 L * ?"))  
                    .build();
            
            /**
             * http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
             */
            
            JobDetail db_backup_job = newJob(DbOperationsJob.class).withIdentity
                    ("DatabaseBackup", "Database")
                    .build();
            
            Trigger db_backup_trigger = newTrigger().withIdentity
                    ("backupTrigger", "Database")
                    .startNow().withSchedule(simpleSchedule()
                    .withIntervalInSeconds(20)
                    .repeatForever())  
                    .build();
            
            
            
            //Quartz Server Properties
            Properties prop = new Properties();
            prop.put("org.quartz.scheduler.rmi.proxy", "true");
            prop.put("org.quartz.scheduler.rmi.registryHost", "localhost");
            prop.put("org.quartz.scheduler.rmi.registryPort", "1099");
            prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
            prop.put("org.quartz.threadPool.threadCount", "1");
            scheduler = new StdSchedulerFactory(prop).getScheduler();
            scheduler.scheduleJob(account_lock_job, account_lock_trigger);
            scheduler.scheduleJob(db_backup_job, db_backup_trigger);
        }
        catch (SchedulerException e) {
            e.printStackTrace();
        }
    
		
	}
	
	
	

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

        try 
        {
            scheduler.shutdown();
          } 
          catch (SchedulerException e) 
         {
             e.printStackTrace();
        }
   
		
	}

	
	
}
