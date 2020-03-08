

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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 *  Persistence implementation for {@link SchoolPerfomanceDAO}
 *  
 *  Copyright (c) FasTech Solutions Ltd., Dec 02, 2015
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class PerfomanceDAO extends GenericDAO  implements SchoolPerfomanceDAO {
	
	
	private static PerfomanceDAO perfomanceDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static PerfomanceDAO getInstance(){
		
		if(perfomanceDAO == null){
			perfomanceDAO = new PerfomanceDAO();		
		}
		return perfomanceDAO;
	}
	
	/**
	 * 
	 */
	public PerfomanceDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public PerfomanceDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolPerfomanceDAO#getPerformance(java.lang.String)
	 */
	@Override
	public List<Perfomance> getPerformance(String schoolAccountUuid,String classRoomUuid,String studentUuid,String Term,String Year) {
		List<Perfomance> list = new ArrayList<>();

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Perfomance WHERE SchoolAccountUuid = ? AND"
     	         		+ " classRoomUuid = ? AND studentUuid = ? AND Term = ? AND Year = ?;");    		   
     	   ) {
         	   pstmt.setString(1, schoolAccountUuid);      
         	   pstmt.setString(2, classRoomUuid);  
         	   pstmt.setString(3, studentUuid);  
         	   pstmt.setString(4, Term); 
       	       pstmt.setString(5, Year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Perfomance.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Perfomance List for student" +studentUuid+ " of school" + schoolAccountUuid +" and classroom" +classRoomUuid); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolPerfomanceDAO#getPerformanceGeneral(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Perfomance> getPerformanceGeneral(String schoolAccountUuid, String classesuuid, String studentUuid,
			String Term, String Year) {
		List<Perfomance> list = new ArrayList<>();

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Perfomance WHERE SchoolAccountUuid = ? AND"
     	         		+ " classesuuid = ? AND studentUuid = ? AND Term = ? AND Year = ?;");    		   
     	   ) {
         	   pstmt.setString(1, schoolAccountUuid);      
         	   pstmt.setString(2, classesuuid);  
         	   pstmt.setString(3, studentUuid);  
         	   pstmt.setString(4, Term); 
       	       pstmt.setString(5, Year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Perfomance.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Perfomance List for student" +studentUuid+ " of school" + schoolAccountUuid +" and classroom" +classesuuid); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolPerfomanceDAO#deletePerfomance(com.yahoo.petermwenda83.bean.exam.Perfomance)
	 */
	@Override
	public boolean deletePerfomance(Perfomance perfomance) {
		boolean success = true;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Perfomance"
						+ " WHERE SchoolAccountUuid = ? AND StudentUuid = ? AND Term = ? AND Year = ?;");       

				){

			pstmt.setString(1, perfomance.getSchoolAccountUuid()); 
			pstmt.setString(2, perfomance.getStudentUuid()); 
			pstmt.setString(3, perfomance.getTerm()); 
			pstmt.setString(4, perfomance.getYear()); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				perfomance  = beanProcessor.toBean(rset,Perfomance.class);
			}



		}catch(SQLException e){
			logger.error("SQL Exception when deleting  Perfomance: " + perfomance);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return success; 
	}

	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolPerfomanceDAO#getPerfomanceListDistinct(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Perfomance> getPerfomanceListDistinct(String schoolAccountUuid, String classRoomUuid,String Term,String Year) {
		List<Perfomance> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT studentuuid FROM perfomance WHERE"
        		 		+ " SchoolAccountUuid = ? AND classRoomUuid = ? AND Term = ? AND Year = ?;");
     	   ) {
         	   pstmt.setString(1, schoolAccountUuid);      
         	   pstmt.setString(2, classRoomUuid);
         	   pstmt.setString(3, Term); 
        	   pstmt.setString(4, Year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Perfomance.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting DISTINCT StudentUuid List  of Perfomance for school" + schoolAccountUuid +" and classroom" +classRoomUuid); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}
	
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolPerfomanceDAO#getPerfomanceListDistinctGeneral(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Perfomance> getPerfomanceListDistinctGeneral(String schoolAccountUuid, String ClassesUuid, String Term,
			String Year) {
		List<Perfomance> list = new ArrayList<>();
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT studentuuid FROM perfomance WHERE"
        		 		+ " SchoolAccountUuid = ? AND ClassesUuid = ? AND Term = ? AND Year = ?;");
     	   ) {
         	   pstmt.setString(1, schoolAccountUuid);      
         	   pstmt.setString(2, ClassesUuid);
         	   pstmt.setString(3, Term); 
        	   pstmt.setString(4, Year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Perfomance.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting DISTINCT StudentUuid List  of Perfomance for school" + schoolAccountUuid +" and classroom" +ClassesUuid); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}
	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolPerformanceDAO#getSubjectCountPerStream(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int getSubjectCountPerStream(String accountUuid,String subjectUuid, String streamUuid, String term, String year) {
		int count = 0;
		ResultSet rset = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM perfomance WHERE SchoolAccountUuid =? AND subjectuuid =? AND classRoomUuid =? AND term =? AND year =?;");    		   
 	    ) {
        	   
        	
        	pstmt.setString(1, accountUuid);
        	pstmt.setString(2, subjectUuid);
        	pstmt.setString(3, streamUuid);
        	pstmt.setString(4, term);
        	pstmt.setString(5, year);
        	rset = pstmt.executeQuery();
        
          	  while(rset.next()){
   	       		count = rset.getInt("count");
          	  }
   	       	
        } catch (SQLException e) {
            logger.error("SQLException when getting count from performance:");
            logger.error(ExceptionUtils.getStackTrace(e));
        }
		
		return count;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolPerformanceDAO#getSubjectCountPerClass(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int getSubjectCountPerClass(String accountUuid,String subjectUuid, String classUuid, String term, String year) {
		int count = 0;
		ResultSet rset = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM perfomance WHERE SchoolAccountUuid =? AND subjectuuid =? AND ClassesUuid =? AND term =? AND year =?;");    		   
 	    ) {
        	   
        	
        	pstmt.setString(1, accountUuid);
        	pstmt.setString(2, subjectUuid);
        	pstmt.setString(3, classUuid);
        	pstmt.setString(4, term);
        	pstmt.setString(5, year);
        	rset = pstmt.executeQuery();
        	
        	while(rset.next()){
   	       		count = rset.getInt("count");
          	  }

 	       
        } catch (SQLException e) {
            logger.error("SQLException when getting count from performance:");
            logger.error(ExceptionUtils.getStackTrace(e));
        }
		
		return count;
	}


	/**
	 * account.getUuid(),
									student.getClassRoomUuid(), student.getUuid(), subject.getUuid(),
									sysConfig.getTerm(), sysConfig.getYear()
									
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolPerfomanceDAO#getPerformance(java.lang.String)
	 */
	@Override
	public List<Perfomance> getPerformance(String schoolAccountUuid, String classRoomUuid, String studentUuid,
			String subjectUuid, String Term, String Year) {
		List<Perfomance> list = new ArrayList<>();
        System.out.println("schoolAccountUuid: " + schoolAccountUuid + 
        		", classRoomUuid: " + classRoomUuid + ", "
        				+ "studentUuid: " + studentUuid + ", "
        						+ "subjectUuid: " + subjectUuid + ", Term: " + Term + ",Year: " + Year); 
		try (Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT * FROM Perfomance WHERE SchoolAccountUuid = ? AND"
								+ " classRoomUuid = ? AND studentUuid = ? and subjectUuid = ? AND Term = ? AND Year = ?;");) {
			pstmt.setString(1, schoolAccountUuid);
			pstmt.setString(2, classRoomUuid);
			pstmt.setString(3, studentUuid);
			pstmt.setString(4, subjectUuid);
			pstmt.setString(5, Term);
			pstmt.setString(6, Year);
			try (ResultSet rset = pstmt.executeQuery();) {

				list = beanProcessor.toBeanList(rset, Perfomance.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting Perfomance List for student" + studentUuid + " of school"
					+ schoolAccountUuid + " and classroom " + classRoomUuid + " and subjectUuid " + subjectUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}
 
	@Override
	public boolean deleteDuplicate(Perfomance perfomance) {
		boolean success = true;
		logger.error("deleting for ID " + perfomance.getId());
		try(
				Connection conn = dbutils.getConnection();
				/*PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Perfomance WHERE SchoolAccountUuid =? AND  classRoomUuid =?"
						+ "AND studentUuid =? AND subjectUuid =? AND term =? and year =? ;");   */
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Perfomance WHERE id =?;");   
				){
			pstmt.setInt(1, perfomance.getId());  
			//pstmt.executeUpdate();
			pstmt.execute();
		}catch(SQLException e){
			logger.error("SQL Exception when deleting  Perfomance for accountId: " + perfomance.getSchoolAccountUuid() + " ,and uuid: " + perfomance.getUuid());
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return success;
	}

}
