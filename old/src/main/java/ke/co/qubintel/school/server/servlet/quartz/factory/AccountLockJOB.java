package ke.co.qubintel.school.server.servlet.quartz.factory;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
/**
 * 
 * @author peter
 *
 */
public class AccountLockJOB implements Job {
	
    public void execute(JobExecutionContext context){
        System.out.println("I am JOB, schdule me with Quartz");
        System.out.println("Send SMS here");
   }
    
}
