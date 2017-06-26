/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.util.ArrayList;
import java.util.List;

import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;

/**
 * @author peter
 *
 */
public class Peter {
	
	static final String databaseName = "schooldb";
	static final String Host = "localhost";
	static final String databaseUsername = "school";
	static final String databasePassword = "AllaManO1";
	static final int databasePort = 5432;
	
	private static GradingSystemDAO gradingSystemDAO;
	
	static {
		gradingSystemDAO = new GradingSystemDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<GradingSystem> gradingSystemList = new ArrayList<>();
		String generalId = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";
		String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		
		gradingSystemList = gradingSystemDAO.getGradingSystemList(accountId, generalId);
		
		int score = 26;
		int points = 0;
		
		System.out.println("score: " + score);
		
		for(GradingSystem gradingSystem : gradingSystemList){
				
				System.out.println("LowerLimit : " + gradingSystem.getLowerLimit() +
						", UpperLimit : " + gradingSystem.getUpperLimit() + " , Point: " + gradingSystem.getPoints());
				
				if(score <= gradingSystem.getUpperLimit() &&  score >= gradingSystem.getLowerLimit()){
					points = gradingSystem.getPoints();
					System.out.println("Range found! point is : " + points);
					
				}
			
			}
		
		
	}

}
