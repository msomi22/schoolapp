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
package ke.co.qubintel.school.server.quartz;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class WriteToFile {
	
	private static final String USER_SYSTEM = System.getProperty("user.name");

	static final String FILENAME = "/home/"+USER_SYSTEM+"/school/.dbscripts/backup.sh";
    static final String DB_DIRECTORY = "/home/"+USER_SYSTEM+"/school/dbBackup/";
    public static final String LOGO_PATH = "/home/"+USER_SYSTEM+"/school/logo/";
    
	
   /**
    * 
    */
	public static void createScript() {

		BufferedWriter bw = null;
		FileWriter fw = null;

		makeDirs();
		createFile(FILENAME);

		try {

			String content = "# Begin automatic creation of role\n" + 
					"DB_USERNAME=\"postgres\"\n" + 
					"DB_PASSWORD=\"root\"\n" + 
					"DB_HOST=\"localhost\"\n" + 
					"\n" + 
					"export PGUSER=$DB_USERNAME\n" + 
					"export PGHOST=$DB_HOST\n" + 
					"export PGPASSWORD=$DB_PASSWORD\n" + 
					"\n" + 
					"# Initialize the following variables as appropriate:\n" + 
					"DB_USERNAME=\"school\"\n" + 
					"DB_PASSWORD=\"AllaManO1\"\n" + 
					"DB_HOST=\"localhost\"\n" + 
					"\n" + 
					"\n" + 
					"\n" + 
					"# There should be no need to change anything below this line.\n" + 
					"# echo \"Starting database initialization script...\"\n" + 
					"\n" + 
					"export PGUSER=$DB_USERNAME\n" + 
					"export PGHOST=$DB_HOST\n" + 
					"export PGPASSWORD=$DB_PASSWORD\n" + 
					"\n" + 
					"#echo \"backing up...\" $now-\n" + 
					"now=\"$(date +'%s-%d-%m-%y')\"\n" + 
					"pg_dump  -U school schooldb -f /home/$USER/school/dbBackup/backup.backup \n" + 
					"\n" + 
					"#echo \"done.!\"";

			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(content);

			System.out.println("Done creating backup script");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}


	}


	/**
	 * 
	 * @param filename
	 */
	static void createFile(String filename) {

		if (OSValidator.isWindows()) {

			System.out.println("This is Windows");
			
			makeLinuxFile(filename);


		} else if (OSValidator.isMac()) {

			System.out.println("This is Mac");
			
			makeLinuxFile(filename);
		}


		else if (OSValidator.isUnix()) {

			System.out.println("This is Unix");

			makeLinuxFile(filename);

		}

	}





	/**
	 * 
	 * @param filename
	 */
	public static void makeLinuxFile(String filename){

		File dir = new File(filename);

		if (!dir.exists()) {
			try {
				FileUtils.forceMkdir(dir.getParentFile());
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}

	}
	
	/**
	 * 
	 */
	public static void makeDirs(){ 
		
		System.out.println("Done creating directories");

		File backup_dir = new File(DB_DIRECTORY);
		File logo_dir = new File(LOGO_PATH);
		
		if (!backup_dir.exists()) {
			try {
				FileUtils.forceMkdir(new File(DB_DIRECTORY));
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		
		
		if (!logo_dir.exists()) {
			try {
				FileUtils.forceMkdir(new File(LOGO_PATH));
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}


	}


	/**
	 * 
	 * @author peter
	 *
	 */
	public static class OSValidator {
		static final String OS = System.getProperty("os.name").toLowerCase();

		public static boolean isWindows(){
			return (OS.indexOf("win")>=0);
		}

		public static boolean isMac(){
			return (OS.indexOf("mac")>=0);
		}

		public static boolean isUnix() {
			return (OS.indexOf("nix") >=0 || OS.indexOf("nux") >=0 || OS.indexOf("aix") >= 0);
		}

		public static boolean isSolaris(){
			return (OS.indexOf("sunos") >=0);
		}
	}

}