package ke.co.qubintel.school.server.servlet.quartz.factory;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/** 
 * 
 * @author peter
 *
 */
public class QuartzProperties extends HttpServlet{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1500797214116239844L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
     * @param config
     * @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {
    	 super.init(config);

        initQuartzProperties();
        logger.info("Have initialized QuartzProperties");
    }
	
	private void initQuartzProperties() {
		
		try {
			Properties prop = new Properties();

			//============================================================================
			/** Quartz server   */
			//============================================================================
			prop.put("org.quartz.scheduler.rmi.export", "true");
			prop.put("org.quartz.scheduler.rmi.createRegistry", "true");
			prop.put("org.quartz.scheduler.rmi.registryHost", "localhost");
			prop.put("org.quartz.scheduler.rmi.registryPort", "1099");
			prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			prop.put("org.quartz.threadPool.threadCount", "2");

			//============================================================================
			/** Quartz Server Properties   */
			//============================================================================
			prop.put("quartz.scheduler.instanceName", "ServerScheduler");
			prop.put("org.quartz.scheduler.instanceId", "AUTO");
			prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
			prop.put("org.quartz.scheduler.instanceId", "NON_CLUSTERED");
			prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
			prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");

			//============================================================================
			/** Configure JdbcJobStore   */
			//============================================================================

			prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
			prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
			prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
			prop.put("org.quartz.jobStore.isClustered", "false");

			//============================================================================
			/** Configure Datasources  */
			//============================================================================
			prop.put("org.quartz.dataSource.quartzDataSource.driver", "org.postgresql.Driver");
			prop.put("org.quartz.dataSource.quartzDataSource.URL", "jdbc:postgresql://localhost:5432/quartz_db");
			prop.put("org.quartz.dataSource.quartzDataSource.user", "quartz_user");
			prop.put("org.quartz.dataSource.quartzDataSource.password", "quartz_password");
			prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", "10");


			SchedulerFactory stdSchedulerFactory = new StdSchedulerFactory(prop);
			Scheduler scheduler = stdSchedulerFactory.getScheduler();
			scheduler.start();


		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
