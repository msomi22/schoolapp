package ke.co.qubintel.school.server.servlet.quartz;

import static org.quartz.JobBuilder.*;
import java.util.Properties;
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
public class ScheduleJOb {
	
    public static void main(String[] args) {
        try {
            // Create Job
            JobDetail job = newJob(TestJOB.class).withIdentity
                    ("jobName", "groupName")
                    .build();
            // Create Trigger
            Trigger trigger = newTrigger().withIdentity
                    ("triggerName", "groupName")
                    .startNow().withSchedule(simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();
            
            /**
             * JobDataMap jobData = new JobDataMap();
				jobData.put("NAME", "TEST_PARAMETER");
				// Create Job
				JobDetail job = newJob(TestJOB.class).withIdentity
				        ("jobName", "groupName")
				        .setJobData(jobData)   // Set parameters 
				        .build();
				        
				        
			    JobDataMap jobData = context.getJobDetail().getJobDataMap();
				//fetch parameters from JobDataMap
				String name = jobData.getString(NAME);
             */
            
            
            //Quartz Server Properties
            Properties prop = new Properties();
            prop.put("org.quartz.scheduler.rmi.proxy", "true");
            prop.put("org.quartz.scheduler.rmi.registryHost", "localhost");
            prop.put("org.quartz.scheduler.rmi.registryPort", "1099");
            prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
            prop.put("org.quartz.threadPool.threadCount", "1");
            Scheduler scheduler = new StdSchedulerFactory(prop).getScheduler();
            scheduler.scheduleJob(job, trigger);
        }
        catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
