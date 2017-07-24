/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.test;

import java.util.ArrayList;
import java.util.List;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.subject.CategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubCategoryDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.servlet.reports.ReportUtil;

/**
 * @author peter
 *
 */
public class P123test {
	
	final static String databaseName = "schooldb";
	final static String Host = "localhost";
	final static String databaseUsername = "school";
	final static String databasePassword = "AllaManO1";
	final static int databasePort = 5432;
	
	private static SubjectDAO subjectDAO;
	private static SubCategoryDAO subCategoryDAO;
	private static CategoryDAO categoryDAO;
	private static ExamDAO examDAO;
	private static GradingSystemDAO gradingSystemDAO;
	

	static{
		subjectDAO = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		subCategoryDAO = new SubCategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		categoryDAO = new CategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		examDAO = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		gradingSystemDAO = new GradingSystemDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		
		List<Perfomance> finalPerfomanceList = new ArrayList<>();
		
		Perfomance perfomance = new Perfomance();
		perfomance.setAccountId(accountId);
		perfomance.setClassRoomId("C143978A-E021-4015-BC67-5A00D6C910D1");
		perfomance.setExamId("D50E6399-B913-42F2-A5B6-F0D4BAAF9571");
		perfomance.setScore(56);
		perfomance.setStreamId("4DA86139-6A72-4089-8858-6A3A613FDFE6");
		perfomance.setStudentId("4F218688-6DE5-4E69-8690-66FBA2F0DC9F");
		perfomance.setSubjectId("D0F7EC32-EA25-7D32-8708-2CC132446");
		perfomance.setTerm("1");
		perfomance.setYear("2017");
		
		
		finalPerfomanceList.add(perfomance);
		
		System.out.println("grandTotal : " + ReportUtil.computeP123(finalPerfomanceList, subjectDAO, subCategoryDAO, categoryDAO,examDAO,gradingSystemDAO, accountId)); 

	}

}
