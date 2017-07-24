/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.test;

import java.util.ArrayList;
import java.util.List;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
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
	private static PerfomanceDAO perfomanceDAO;
	

	static{
		subjectDAO = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		subCategoryDAO = new SubCategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		categoryDAO = new CategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		examDAO = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		gradingSystemDAO = new GradingSystemDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		perfomanceDAO = new PerfomanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		String examId = "AE24F15B-5038-4A15-8607-1DB2A7A0B7DE";//AE24F15B-5038-4A15-8607-1DB2A7A0B7DE,4531A31D-1F8A-40D7-BFE6-D3CB3D91951A,69A569CA-1D4F-458E-99DD-FB2BE705BF5C
		String studentId = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
		String streamId = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
		String term = "1";
		String year = "2017";
		
		List<Perfomance> perfomanceList = new ArrayList<>();
		perfomanceList = perfomanceDAO.getStreamPerformance(accountId, examId, studentId, streamId, term, year);
		
		System.out.println("grandTotal : " + ReportUtil.computeP123(perfomanceList, subjectDAO, subCategoryDAO, categoryDAO,examDAO,gradingSystemDAO, accountId)); 

	}

}
