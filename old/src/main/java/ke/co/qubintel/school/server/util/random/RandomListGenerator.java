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
package ke.co.qubintel.school.server.util.random;

import java.io.File;

import java.io.IOException;
import org.apache.commons.io.FileUtils;

import org.apache.commons.math3.random.RandomDataGenerator;
/**
 * Class that randomizes string uuids
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class RandomListGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Have started list generator.");
		File outFile = new File("/home/peter/Documents/radom/randomList.txt");

		RandomDataGenerator generator = new RandomDataGenerator();
		
		int listSize = 503;	// The size of the array / list to be generated
		
		// The Strings to be randomized
		String[] strings = {"0DE968C9-7309-C481-58F7-AB6CDB1011EF", "5C1D9939-136A-55DE-FD0E-61D8204E17C9",
				"B936DA83-8A45-E9F0-2EAE-D75F5C232E78"};
				
		try {
		 	
			for(int j=0; j<listSize; j++) {
				FileUtils.write(outFile,strings[generator.nextInt(0, strings.length - 1)] + "\n",true); // Append to file                              
			}
			
		} catch(IOException e) {
			System.err.println("IOException in main.");
			e.printStackTrace();
		}			
		
		System.out.println("Have finished list generator.");
	}

}
