/**
 * 
 */
package com.yahoo.petermwenda83.persistence.othermoney;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.othermoney.RevertedMoney;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class RevertedMoneyDAO extends GenericDAO implements SchoolRevertedMoneyDAO {

	private static RevertedMoneyDAO revertedMoneyDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static RevertedMoneyDAO getInstance() {
		if(revertedMoneyDAO == null){
			revertedMoneyDAO = new RevertedMoneyDAO();		
			}
		return revertedMoneyDAO;
	}
	
	public RevertedMoneyDAO() {
		super();
		
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public RevertedMoneyDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
	}
	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolRevertedMoneyDAO#getRevertedMoney(java.lang.String)
	 */
	@Override
	public RevertedMoney getRevertedMoney(String studentUuid) {
		RevertedMoney revertedMoney = null;
        ResultSet rset = null;
         try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM RevertedMoney WHERE studentUuid = ?;");       
        		){
        	 pstmt.setString(1, studentUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 revertedMoney  = beanProcessor.toBean(rset,RevertedMoney.class);
	   }
       	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Reverted Money for studentUuid: " + studentUuid );
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return revertedMoney; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolRevertedMoneyDAO#putstudentUuid(com.yahoo.petermwenda83.bean.othermoney.RevertedMoney)
	 */
	@Override
	public boolean putstudentUuid(RevertedMoney revertedMoney) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO RevertedMoney" 
			        		+"(Uuid,studentUuid,otherstypeUuid,amount,term,year) VALUES (?,?,?,?,?,?);");
      		){
			   
	            pstmt.setString(1, revertedMoney.getUuid());
	            pstmt.setString(2, revertedMoney.getStudentUuid());
	            pstmt.setString(3, revertedMoney.getOtherstypeUuid());
	            pstmt.setDouble(4, revertedMoney.getAmount());
	            pstmt.setString(5, revertedMoney.getTerm());
	            pstmt.setString(6, revertedMoney.getYear());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put RevertedMoney: "+revertedMoney);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolRevertedMoneyDAO#getRevertedMoneyList(java.lang.String)
	 */
	@Override
	public List<RevertedMoney> getRevertedMoneyList(String studentUuid) {
		List<RevertedMoney> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM RevertedMoney WHERE studentUuid = ?;");
     	   ) {
         	   pstmt.setString(1, studentUuid);      
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, RevertedMoney.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting RevertedMoney  List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}

}
