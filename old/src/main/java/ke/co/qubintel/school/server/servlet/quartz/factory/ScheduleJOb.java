package ke.co.qubintel.school.server.servlet.quartz.factory;

import static org.quartz.JobBuilder.*;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
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
        	
        	JobDataMap jobData = new JobDataMap();
        	jobData.put("NAME", "TEST_PARAMETER");
        	
        	
            // Create Job
            JobDetail job = newJob(AccountLockJOB.class).withIdentity
                    ("jobName", "groupName")
                    .setJobData(jobData) // Set parameters 
                    .build();
            
            // Create Trigger
            Trigger trigger = newTrigger().withIdentity
                    ("triggerName", "groupName")
                    .startNow().withSchedule(simpleSchedule()
                            .withIntervalInSeconds(10)
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
            scheduler.scheduleJob(job, trigger);
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
