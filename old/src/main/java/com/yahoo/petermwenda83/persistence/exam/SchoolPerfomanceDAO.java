

/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package com.yahoo.petermwenda83.persistence.exam;

import java.util.List;

import com.yahoo.petermwenda83.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public interface SchoolPerfomanceDAO {
	
	 
	public List<Perfomance> getStreamPerformance(String accountId,String examId,String studentId,String streamId,String term,String year); 
	
	public List<Perfomance> getClassPerformance(String accountId,String examId,String studentId,String classRoomId,String term,String year); 

	public boolean deletePerfomance(String accountId,String examId,String studentId,String term,String year);
	
	public List<Perfomance> getStreamSubjectPerfomance(String accountId,String examId,String subjectId,String streamId,String term,String year);
	
	public List<Perfomance> getClassSubjectPerfomance(String accountId,String examId,String subjectId,String classRoomId,String term,String year);
	
}
