/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import java.util.List;

import com.yahoo.petermwenda83.bean.exam.CatOne;
import com.yahoo.petermwenda83.bean.exam.CatTwo;
import com.yahoo.petermwenda83.bean.exam.Common;
import com.yahoo.petermwenda83.bean.exam.EndTerm;
import com.yahoo.petermwenda83.bean.exam.PaperOne;
import com.yahoo.petermwenda83.bean.exam.PaperThree;
import com.yahoo.petermwenda83.bean.exam.PaperTwo;
import com.yahoo.petermwenda83.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public interface SchoolExamEngineDAO {
	
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
    public Common getCommon(String schoolAccountUuid,String classRoomUuid,String studentUuid,String subjectUuid,String Term,String Year);
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
	public boolean Checker(String schoolAccountUuid,String classRoomUuid,String studentUuid,String subjectUuid,String Term,String Year);
	 /**
	  * 
	  * @param perfomance
	  * @return
	  */
	public boolean putScore(Perfomance perfomance,String schoolAccountUuid,String classRoomUuid,String studentUuid,String subjectUuid,String Term,String Year);
	 /**
	  * 
	  * @param schoolAccountUuid
	  * @param classRoomUuid
	  * @param Term
	  * @param Year
	  * @return
	  */
	public List<CatOne> getcatoneList(String schoolAccountUuid,String classRoomUuid,String Term,String Year);
	  /**
	   * 
	   * @param schoolAccountUuid
	   * @param classRoomUuid
	   * @param Term
	   * @param Year
	   * @return
	   */
	public List<CatTwo> getcatwoList(String schoolAccountUuid,String classRoomUuid,String Term,String Year);
	  /**
	   * 
	   * @param schoolAccountUuid
	   * @param classRoomUuid
	   * @param Term
	   * @param Year
	   * @return
	   */
	public List<EndTerm> getendtermList(String schoolAccountUuid,String classRoomUuid,String Term,String Year);
	  /**
	   * 
	   * @param schoolAccountUuid
	   * @param classRoomUuid
	   * @param Term
	   * @param Year
	   * @return
	   */
	public List<PaperOne> getPaperOneList(String schoolAccountUuid,String classRoomUuid,String Term,String Year);
	   /**
	    * 
	    * @param schoolAccountUuid
	    * @param classRoomUuid
	    * @param Term
	    * @param Year
	    * @return
	    */
	public List<PaperTwo> getPaperTwoList(String schoolAccountUuid,String classRoomUuid,String Term,String Year);
	   /**
	    * 
	    * @param schoolAccountUuid
	    * @param classRoomUuid
	    * @param Term
	    * @param Year
	    * @return
	    */
	public List<PaperThree> getpaperThreeList(String schoolAccountUuid,String classRoomUuid,String Term,String Year);

}

