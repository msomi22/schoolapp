package com.yahoo.petermwenda83.server.servlet.quartz;

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

import com.yahoo.petermwenda83.server.quartz.QuartzJob;
import com.yahoo.petermwenda83.server.quartz.SchoolQuartzJob;

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
                    JobDetail job = newJob(QuartzJob.class).withIdentity(
                                    "CronQuartzJob", "Group").build();

                    // Create a Trigger that fires every 1 minutes 
                    Trigger trigger = newTrigger()
                    .withIdentity("TriggerName", "Group")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                    .build(); 

                    // Setup the Job and Trigger with Scheduler & schedule jobs
                    scheduler = new StdSchedulerFactory().getScheduler();
                    scheduler.start();
                    scheduler.scheduleJob(job, trigger);
                    
                    
                    
                 // Setup the Job class and the Job group
                    JobDetail job2 = newJob(SchoolQuartzJob.class).withIdentity(
                                    "CronQuartzJob2", "Group2").build();

                    // Create a Trigger
                    Trigger trigger2 = newTrigger()
                    .withIdentity("TriggerName2", "Group2")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 16 ? 1/2 MON#1"))//execute the first Monday of every 2 months //
                    .build(); 

                    // Setup the Job and Trigger with Scheduler & schedule jobs
                    scheduler = new StdSchedulerFactory().getScheduler();
                    scheduler.start();
                    scheduler.scheduleJob(job2, trigger2);
                 
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
