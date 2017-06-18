package com.yahoo.petermwenda83.server.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.SystemUtils;
import org.hibernate.SessionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yahoo.petermwenda83.persistence.HibernateUtil;
import com.yahoo.petermwenda83.persistence.StorageDAO;
import com.yahoo.petermwenda83.persistence.StorageDAOImpl;



public class QuartzJob implements Job{

	private StorageDAO storageDAO;
	private SessionFactory sessionFactory;
	
	public QuartzJob() {
		super();
		
		sessionFactory = HibernateUtil.getSessionFactory();
		storageDAO = new StorageDAOImpl(sessionFactory); 
		
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		try {

			if(SystemUtils.IS_OS_WINDOWS){
				backUpWin();
			}

			if(SystemUtils.IS_OS_LINUX){
				StartBackup();
			}

			checksentSMS();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void checksentSMS() {
		
		
		
	}

	/**
	 * 
	 */
	private void StartBackup() {
		String user = System.getProperty("user.name");
		String backup = "/home/"+user+"/svn/School/trunk/webapp/bin/backup.sh";
		ProcessBuilder pb = new ProcessBuilder(backup,"arg","arg");
		try {
			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			String line = null;

			while((line=br.readLine())!=null){
				System.out.println(line);

			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}


	}

	/**
	 * @throws IOException
	 */
	private void backUpWin() throws IOException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("h-m-s_dd-MMM yyyy"); // hour,minutes,seconds day,Month, Year
		String date = dateFormatter.format(new Date());
		String pg = "  \"C:/Program Files/PostgreSQL/9.3/bin/pg_dump.exe\" -i -h localhost -p 5432 -U school -f c -b -v -f \"D:/pgBackup/schooldb.backup\" schooldb";
		java.lang.Runtime rt = java.lang.Runtime.getRuntime();
		java.lang.Process p = rt.exec(pg);
	}

}
