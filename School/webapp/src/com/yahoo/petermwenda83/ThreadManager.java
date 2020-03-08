/**
 * 
 */
package com.yahoo.petermwenda83;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

//import com.yahoo.petermwenda83.server.servlet.upload.ExcelUtil;

/**
 * @author pmnjeru
 *
 */
public class ThreadManager extends HttpServlet implements ServletContextListener  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3814998175345796351L;
	private Logger logger = Logger.getLogger(this.getClass());
	//private ThreadPoolExecutor executor = null;
	private ScheduledThreadPoolExecutor executor = null;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			logger.info("ThreadManager Shutting down ");
			//logger.info("ActiveCount: " + executor.getActiveCount());
			//logger.info("CorePoolSize: " + executor.getCorePoolSize());
			//logger.info("PoolSize: " + executor.getPoolSize());
			shutdownAndAwaitTermination();
			logger.info("ThreadManager Shutting down successfully ");
			//executor.shutdownNow();
			//executor.shutdown();
		} catch (Exception ex) {
			
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Starting ThreadManager");
		if ((executor == null) || (!executor.isTerminated())) {
			// executor = Executors.newSingleThreadScheduledExecutor();
			executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);
			logger.info("Starting duplicate checker");
			DuplicateChecker task = new DuplicateChecker();
			logger.info("firing " + task.getClass().getCanonicalName()); 
			//Runnable command, long initialDelay, long delay, TimeUnit unit
			executor.scheduleAtFixedRate(task, 20, 300, TimeUnit.SECONDS);
			executor.scheduleAtFixedRate(task, 20, 300, TimeUnit.SECONDS);
			logger.info("duplicate checker started");
		}
		logger.info("ThreadManager started successfully ");
	}
	
	/**
	 * 
	 */
	void shutdownAndAwaitTermination() {
		executor.shutdown(); // Disable new tasks from being submitted
		   try {
		     // Wait a while for existing tasks to terminate
		     if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
		    	 executor.shutdownNow(); // Cancel currently executing tasks
		       // Wait a while for tasks to respond to being cancelled
		       if (!executor.awaitTermination(10, TimeUnit.SECONDS))
		           System.err.println("Pool did not terminate");
		     }
		   } catch (InterruptedException ie) {
		     // (Re-)Cancel if current thread also interrupted
			   executor.shutdownNow();
		     // Preserve interrupt status
		     Thread.currentThread().interrupt();
		   }
		 }

}
