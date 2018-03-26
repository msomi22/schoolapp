/**
 * 
 */
package ke.co.qubintel.school.server.servlet.quartz.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author peter
 *
 */
public class StartDateFromLog {

	/**
	 * 
	 * @return
	 */
	public static String checkTimeout(String path, int logic) {

		String firstLine = "";

		File file = new File(path);

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			
			if(logic == 0) {
				firstLine = br.readLine().substring(0,20);
			}else if(logic == 1) {
				firstLine = br.readLine(); 
			}

			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return firstLine;
	}

}
