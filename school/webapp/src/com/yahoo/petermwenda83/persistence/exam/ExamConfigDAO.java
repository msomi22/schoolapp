/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 * 
 */
public class ExamConfigDAO extends GenericDAO implements SchoolExamConfigDAO {

	private static ExamConfigDAO examConfigDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static ExamConfigDAO getInstance(){
		
		if(examConfigDAO == null){ 
			examConfigDAO = new ExamConfigDAO();		
		}
		return examConfigDAO;
	}
	
	/**
	 * 
	 */
	public ExamConfigDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public ExamConfigDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamConfigDAO#getExamConfig(java.lang.String)
	 */
	@Override
	public ExamConfig getExamConfig(String schoolAccountUuid) {
		ExamConfig examConfig = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ExamConfig"
						+ " WHERE SchoolAccountUuid = ?;");       

				){

			pstmt.setString(1, schoolAccountUuid); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				examConfig  = beanProcessor.toBean(rset,ExamConfig.class);
			}



		}catch(SQLException e){
			logger.error("SQL Exception when getting ExamConfig: " + examConfig);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return examConfig; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamConfigDAO#putExamConfig(com.yahoo.petermwenda83.bean.exam.ExamConfig)
	 */
	@Override
	public boolean putExamConfig(ExamConfig examConfig) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ExamConfig" 
			        		+"(Uuid,SchoolAccountUuid,Term,Year,Exam,ExamMode,eTFone,eT,eTCtwo,eTConetwo,SendSMS) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
       		){
			
			    pstmt.setString(1, examConfig.getUuid());
			    pstmt.setString(2, examConfig.getSchoolAccountUuid());
			    pstmt.setString(3, examConfig.getTerm());
	            pstmt.setString(4, examConfig.getYear());
	            pstmt.setString(5, examConfig.getExam());
	            pstmt.setString(6, examConfig.getExamMode());
	            pstmt.setString(7, examConfig.geteTFone());
	            pstmt.setString(8, examConfig.geteT());
	            pstmt.setString(9, examConfig.geteTCtwo());
	            pstmt.setString(10, examConfig.geteTConetwo());
	            pstmt.setString(11, examConfig.getSendSMS());
	           
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put ExamConfig: "+examConfig);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamConfigDAO#updateExamConfig(com.yahoo.petermwenda83.bean.exam.ExamConfig)
	 */
	@Override
	public boolean updateExamConfig(ExamConfig examConfig) {
		boolean success = true;
        try (  Connection conn = dbutils.getConnection();
        	PreparedStatement pstmt = conn.prepareStatement("UPDATE ExamConfig SET Term=?,"
        			+ "Year=?,Exam =?, ExamMode=?,eTFone =?,eT =?,eTCtwo =?,eTConetwo =?,SendSMS=? WHERE SchoolAccountUuid = ?;");
        	) { 
	            pstmt.setString(1, examConfig.getTerm());
	            pstmt.setString(2, examConfig.getYear());
	            pstmt.setString(3, examConfig.getExam());
	            pstmt.setString(4, examConfig.getExamMode());
	            pstmt.setString(5, examConfig.geteTFone());
	            pstmt.setString(6, examConfig.geteT());
	            pstmt.setString(7, examConfig.geteTCtwo());
	            pstmt.setString(8, examConfig.geteTConetwo());
	            pstmt.setString(9, examConfig.getSendSMS());
	            pstmt.setString(10, examConfig.getSchoolAccountUuid());
                pstmt.executeUpdate(); 

        } catch (SQLException e) {
            logger.error("SQL Exception when updating ExamConfig" + examConfig);
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
        } 
        
        return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamConfigDAO#getExamConfigList(java.lang.String)
	 */
	@Override
	public List<ExamConfig> getExamConfigList(String schoolAccountUuid) {
		 List<ExamConfig> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM ExamConfig WHERE SchoolAccountUuid = ?;");   
			) {
			 pstmt.setString(1,schoolAccountUuid);

			 try(ResultSet rset = pstmt.executeQuery();){
					
				 list = beanProcessor.toBeanList(rset, ExamConfig.class);
			}
	        

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting ExamConfig List");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
		return list;
	}

}
