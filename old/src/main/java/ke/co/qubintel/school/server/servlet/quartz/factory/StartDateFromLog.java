/**
 * 
 */
package ke.co.qubintel.school.server.servlet.quartz.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.SystemUtils;

/**
 * @author peter
 *
 */
public class StartDateFromLog {

	/**
	 * 
	 * @return
	 */
	public static String checkTimeout() {

		String startDate = "";

		String path = "";

		if(SystemUtils.IS_OS_WINDOWS){
			path= "C:\\opt\\Programs\\WildFly\\8.2.0\\standalone\\log\\log4jSchool.log";
		}

		if(SystemUtils.IS_OS_LINUX){
			path = "/opt/Programs/WildFly/8.2.0/standalone/log/log4jSchool.log";
		}

		File file = new File(path);

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {

			startDate = br.readLine().substring(0,20);
			//while ((st = br.readLine()) != null)
			//System.out.println("**************************#################################"+startDate);



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return startDate;
	}

}
