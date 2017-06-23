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

import com.yahoo.petermwenda83.bean.exam.SysConfig;
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
	public SysConfig getExamConfig(String schoolAccountUuid) {
		SysConfig sysConfig = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SysConfig"
						+ " WHERE SchoolAccountUuid = ?;");       

				){

			pstmt.setString(1, schoolAccountUuid); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				sysConfig  = beanProcessor.toBean(rset,SysConfig.class);
			}



		}catch(SQLException e){
			logger.error("SQL Exception when getting SysConfig: " + sysConfig);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return sysConfig; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamConfigDAO#putExamConfig(com.yahoo.petermwenda83.bean.exam.SysConfig)
	 */
	@Override
	public boolean putExamConfig(SysConfig sysConfig) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SysConfig" 
			        		+"(Uuid,SchoolAccountUuid,Term,Year,Exam,ExamMode,eTFone,eT,eTCtwo,eTConetwo,SendSMS) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
       		){
			
			    pstmt.setString(1, sysConfig.getUuid());
			    pstmt.setString(2, sysConfig.getSchoolAccountUuid());
			    pstmt.setString(3, sysConfig.getTerm());
	            pstmt.setString(4, sysConfig.getYear());
	            pstmt.setString(5, sysConfig.getExam());
	            pstmt.setString(6, sysConfig.getExamMode());
	            pstmt.setString(7, sysConfig.geteTFone());
	            pstmt.setString(8, sysConfig.geteT());
	            pstmt.setString(9, sysConfig.geteTCtwo());
	            pstmt.setString(10, sysConfig.geteTConetwo());
	            pstmt.setString(11, sysConfig.getSendSMS());
	           
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put SysConfig: "+sysConfig);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamConfigDAO#updateExamConfig(com.yahoo.petermwenda83.bean.exam.SysConfig)
	 */
	@Override
	public boolean updateExamConfig(SysConfig sysConfig) {
		boolean success = true;
        try (  Connection conn = dbutils.getConnection();
        	PreparedStatement pstmt = conn.prepareStatement("UPDATE SysConfig SET Term=?,"
        			+ "Year=?,Exam =?, ExamMode=?,eTFone =?,eT =?,eTCtwo =?,eTConetwo =?,SendSMS=? WHERE SchoolAccountUuid = ?;");
        	) { 
	            pstmt.setString(1, sysConfig.getTerm());
	            pstmt.setString(2, sysConfig.getYear());
	            pstmt.setString(3, sysConfig.getExam());
	            pstmt.setString(4, sysConfig.getExamMode());
	            pstmt.setString(5, sysConfig.geteTFone());
	            pstmt.setString(6, sysConfig.geteT());
	            pstmt.setString(7, sysConfig.geteTCtwo());
	            pstmt.setString(8, sysConfig.geteTConetwo());
	            pstmt.setString(9, sysConfig.getSendSMS());
	            pstmt.setString(10, sysConfig.getSchoolAccountUuid());
                pstmt.executeUpdate(); 

        } catch (SQLException e) {
            logger.error("SQL Exception when updating SysConfig" + sysConfig);
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
        } 
        
        return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamConfigDAO#getExamConfigList(java.lang.String)
	 */
	@Override
	public List<SysConfig> getExamConfigList(String schoolAccountUuid) {
		 List<SysConfig> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM SysConfig WHERE SchoolAccountUuid = ?;");   
			) {
			 pstmt.setString(1,schoolAccountUuid);

			 try(ResultSet rset = pstmt.executeQuery();){
					
				 list = beanProcessor.toBeanList(rset, SysConfig.class);
			}
	        

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting SysConfig List");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
		return list;
	}

}
