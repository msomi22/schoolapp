

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
	
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param classRoomUuid
	 * @param studentUuid
	 * @param Term
	 * @param Year
	 * @return
	 */
	 
	public List<Perfomance> getPerformance(String schoolAccountUuid,String classRoomUuid,String studentUuid,String Term,String Year); 
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param classRoomUuid
	 * @param studentUuid
	 * @param Term
	 * @param Year
	 * @return
	 */
	public List<Perfomance> getPerformanceGeneral(String schoolAccountUuid,String ClassesUuid,String studentUuid,String Term,String Year); 

	   /**
	    * 
	    * @param perfomance
	    * @return
	    */
	public boolean deletePerfomance(Perfomance perfomance);
	 
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param classRoomUuid
	 * @return
	 */
	public List<Perfomance> getPerfomanceListDistinct(String schoolAccountUuid,String classRoomUuid,String Term,String Year);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param classRoomUuid
	 * @return
	 */
	public List<Perfomance> getPerfomanceListDistinctGeneral(String schoolAccountUuid,String ClassesUuid,String Term,String Year);
	
	/**
	 * 
	 * @param subjectUuid
	 * @param streamUuid
	 * @param term
	 * @param year
	 * @return
	 */
	public int getSubjectCountPerStream(String accountUuid,String subjectUuid, String streamUuid,String term,String year);
	/**
	 * 
	 * @param subjectUuid
	 * @param classUuid
	 * @param term
	 * @param year
	 * @return
	 */
	public int getSubjectCountPerClass(String accountUuid,String subjectUuid, String classUuid,String term,String year);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param classRoomUuid
	 * @param studentUuid
	 * @param subjectUuid
	 * @param Term
	 * @param Year
	 * @return
	 */
	public List<Perfomance>  getPerformance(String schoolAccountUuid, String classId, String studentUuid,
			String subjectUuid, String Term, String Year);
	
	/**
	 * 
	 * @param perfomance
	 * @return
	 */
	public boolean deleteDuplicate(Perfomance perfomance); 
	
	
}
