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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.othermoney.TermOtherMonies;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**  
 * @author peter
 *
 */
public class TermOtherMoniesDAO extends GenericDAO implements SchoolTermOtherMoniesDAO {

	private static TermOtherMoniesDAO termOtherMoniesDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static TermOtherMoniesDAO getInstance() {
		if(termOtherMoniesDAO == null){
			termOtherMoniesDAO = new TermOtherMoniesDAO();		
			}
		return termOtherMoniesDAO;
	}
	
	public TermOtherMoniesDAO() {
		super();
		
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public TermOtherMoniesDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolTermOtherMoniesDAO#getTermOtherMonies(java.lang.String)
	 */
	@Override
	public TermOtherMonies getTermOtherMonies(String SchoolAccountUuid) {
		TermOtherMonies termOtherMonies = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TermOtherMonies WHERE SchoolAccountUuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, SchoolAccountUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 termOtherMonies  = beanProcessor.toBean(rset,TermOtherMonies.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting TermOtherMonies with SchoolAccountUuid: " + SchoolAccountUuid );
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return termOtherMonies; 
	}
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolTermOtherMoniesDAO#getTermOtherMoney(java.lang.String, java.lang.String)
	 */
	@Override
	public TermOtherMonies getTermOtherMoney(String SchoolAccountUuid, String OtherstypeUuid) {
		TermOtherMonies termOtherMonies = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TermOtherMonies WHERE SchoolAccountUuid = ? AND OtherstypeUuid =?;");       
        		
        		){
        	
        	 pstmt.setString(1, SchoolAccountUuid);
        	 pstmt.setString(2, OtherstypeUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 termOtherMonies  = beanProcessor.toBean(rset,TermOtherMonies.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting TermOtherMonies with SchoolAccountUuid: " + SchoolAccountUuid );
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return termOtherMonies; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolTermOtherMoniesDAO#putTermOtherMonies(com.yahoo.petermwenda83.bean.othermoney.TermOtherMonies)
	 */
	@Override
	public boolean putTermOtherMonies(TermOtherMonies termOtherMonies) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TermOtherMonies" 
			        		+"(Uuid,SchoolAccountUuid,OtherstypeUuid,Amount) VALUES (?,?,?,?);");
      		){
			   
	            pstmt.setString(1, termOtherMonies.getUuid());
	            pstmt.setString(2, termOtherMonies.getSchoolAccountUuid());
	            pstmt.setString(3, termOtherMonies.getOtherstypeUuid());
	            pstmt.setDouble(4, termOtherMonies.getAmount());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put termOtherMonies: "+termOtherMonies);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolTermOtherMoniesDAO#updateTermOtherMonies(com.yahoo.petermwenda83.bean.othermoney.TermOtherMonies)
	 */
	@Override
	public boolean updateTermOtherMonies(TermOtherMonies termOtherMonies) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE TermOtherMonies SET Amount = ? WHERE Uuid =?"
						+ "AND SchoolAccountUuid =? AND OtherstypeUuid =? ;");
      		){
			   
			   
	           
	            pstmt.setDouble(1, termOtherMonies.getAmount());
	            pstmt.setString(2, termOtherMonies.getUuid());
	            pstmt.setString(3, termOtherMonies.getSchoolAccountUuid());
	            pstmt.setString(4, termOtherMonies.getOtherstypeUuid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put TermOtherMonies: "+termOtherMonies);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolTermOtherMoniesDAO#deleteTermOtherMonies(com.yahoo.petermwenda83.bean.othermoney.TermOtherMonies)
	 */
	@Override
	public boolean deleteTermOtherMonies(TermOtherMonies termOtherMonies) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM TermOtherMonies"
	         	      		+ " WHERE Uuid =? AND SchoolAccountUuid =? AND OtherstypeUuid =?;");       
	      		
	      		){
	      	
	    	     pstmt.setString(1, termOtherMonies.getUuid());
	             pstmt.setString(2, termOtherMonies.getSchoolAccountUuid());
	             pstmt.setString(3, termOtherMonies.getOtherstypeUuid());
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting termOtherMonies : " +termOtherMonies);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolTermOtherMoniesDAO#getTermOtherMoniesList(java.lang.String)
	 */
	@Override
	public List<TermOtherMonies> getTermOtherMoniesList(String SchoolAccountUuid) {
		List<TermOtherMonies> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TermOtherMonies WHERE"
        		 		+ " SchoolAccountUuid = ?;");
     	   ) {
         	   pstmt.setString(1, SchoolAccountUuid);      
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, TermOtherMonies.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting TermOtherMonies List for school" + SchoolAccountUuid); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}

	
}
