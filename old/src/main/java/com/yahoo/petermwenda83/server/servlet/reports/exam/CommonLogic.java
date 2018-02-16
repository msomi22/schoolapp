package com.yahoo.petermwenda83.server.servlet.reports.exam;

import java.util.List;

import com.yahoo.petermwenda83.bean.exam.Perfomance;

public class CommonLogic {



	public static List<Perfomance> subjectAnalyzer(List<Perfomance> perfomanceList) {

		return perfomanceList;

	}


	public static void combineAnalyzer(List<Perfomance>[] arrayOfList) { 

		if(arrayOfList.length == 1) {
			computeOneList(arrayOfList[0]);
		}

		if(arrayOfList.length == 2) {
			computeTwoLists(arrayOfList[0], arrayOfList[1]);
		}

		if(arrayOfList.length == 3) { 
			computeThreeLists(arrayOfList[0], arrayOfList[1], arrayOfList[2]); 
		
		}

	}

	/**
	 * 
	 * @param list
	 * @param list2
	 * @param list3
	 */
	private static void computeThreeLists(List<Perfomance> list, List<Perfomance> list2, List<Perfomance> list3) {

		System.out.println("List 1: " + list.size()); 
		System.out.println("List 2: " + list2.size()); 
		System.out.println("List 3: " + list3.size()); 

	}

	/**
	 * 
	 * @param list
	 * @param list2
	 */
	private static void computeTwoLists(List<Perfomance> list, List<Perfomance> list2) {

		System.out.println("List 1: " + list.size()); 
		System.out.println("List 2: " + list2.size()); 


	}

	/**
	 * 
	 * @param list
	 */
	private static void computeOneList(List<Perfomance> list) {

		System.out.println("List 1: " + list.size()); 

	}



}
