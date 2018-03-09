

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
package ke.co.qubintel.school.server.persistence.exam;

import java.util.List;

import ke.co.qubintel.school.server.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public interface SchoolPerfomanceDAO {
	
	
	public Perfomance getPerformance(String accountId,String examId,String studentId,String streamId,String term,String year,String subjectId); 
	
	public List<Perfomance> getPerformanceList(String accountId,String examId,String studentId,String streamId,String subjectId,String term,String year); 
	
	
    //student	
	public List<Perfomance> getStreamPerformance(String accountId,String examId,String studentId,String streamId,String term,String year); 
	
	public List<Perfomance> getClassPerformance(String accountId,String examId,String studentId,String classRoomId,String term,String year); 

	
	
	public boolean deletePerfomance(String accountId,String examId,String studentId,String term,String year);
	
	
	//subject
	public List<Perfomance> getStreamSubjectPerfomance(String accountId,String examId,String subjectId,String streamId,String term,String year);
	
	public List<Perfomance> getClassSubjectPerfomance(String accountId,String examId,String subjectId,String classRoomId,String term,String year);
	
	
	/**
	 *  This method is called whenever there is a duplicate in performance table, and the duplicate must be deleted.
	 *  
	 * @param accountId The account Id
	 * @param examId The exam Id
	 * @param studentId the student Id
	 * @param subjectId The subject Id
	 * @param streamId The stream Id
	 * @param term The exam term
	 * @param year The exam year
	 * @return
	 */
	public boolean deleteStreamSubjectDuplicate(String accountId,String uuid);
	
	
}
