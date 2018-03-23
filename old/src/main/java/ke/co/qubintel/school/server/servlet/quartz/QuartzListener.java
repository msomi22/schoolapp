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
package ke.co.qubintel.school.server.servlet.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

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

public class QuartzListener extends HttpServlet implements ServletContextListener {
	
	/** 
	 * 
	 */
	private static final long serialVersionUID = -1289063163218775813L;
	Scheduler scheduler = null;
    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent servletContext) {
              
    	  try {
    		  
                    // Setup the Job class and the Job group
                    JobDetail job = newJob(DbOperationsJob.class).withIdentity(
                                    "CronQuartzJob", "Group").build();

                    // Create a Trigger that fires every 1 minutes 
                    Trigger trigger = newTrigger()
                    .withIdentity("TriggerName", "Group")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                    .build(); 
                    
                    //create a trigger that simply fires every 5 minutes
                    //“0 0/5 * * * ?”
                    //"0 0 16 ? 1/2 MON#1"- execute the first Monday of every 2 months //


                    // Setup the Job and Trigger with Scheduler & schedule jobs
                    scheduler = new StdSchedulerFactory().getScheduler();
                    scheduler.start();
                    scheduler.scheduleJob(job, trigger);
                 
                 }
             catch (SchedulerException e) {
               e.printStackTrace();
            }
    }

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent servletContext) {
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
