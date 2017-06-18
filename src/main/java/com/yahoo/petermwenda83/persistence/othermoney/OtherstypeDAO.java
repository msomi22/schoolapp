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

import com.yahoo.petermwenda83.bean.otherfee.OtherFee;
import com.yahoo.petermwenda83.persistence.GenericDAO;
 
/**  
 * @author peter
 *
 */
public class OtherstypeDAO extends GenericDAO implements SchoolOtherstypeDAO {

	private static OtherstypeDAO otherstypeDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static OtherstypeDAO getInstance() {
		if(otherstypeDAO == null){
			otherstypeDAO = new OtherstypeDAO();		
			}
		return otherstypeDAO;
	}
	
	public OtherstypeDAO() {
		super();
		
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public OtherstypeDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherstypeDAO#getOtherstype(java.lang.String)
	 */
	@Override
	public OtherFee getOtherstype(String Uuid) {
		OtherFee otherFee = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OtherFee WHERE Uuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, Uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 otherFee  = beanProcessor.toBean(rset,OtherFee.class);
	   }
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting OtherFee with Uuid: " + Uuid );
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return otherFee; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherstypeDAO#putOtherstype(com.yahoo.petermwenda83.bean.otherfee.OtherFee)
	 */
	@Override
	public boolean putOtherstype(OtherFee otherFee) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO OtherFee" 
			        		+"(Uuid,SchoolAccountUuid,Type,Term,Year) VALUES (?,?,?,?,?);");
       		){
			   
	            pstmt.setString(1, otherFee.getUuid());
	            pstmt.setString(2, otherFee.getSchoolAccountUuid());
	            pstmt.setString(3, otherFee.getType());
	            pstmt.setString(4, otherFee.getTerm());
	            pstmt.setString(5, otherFee.getYear());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put otherFee: "+otherFee);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherstypeDAO#updteOtherstype(com.yahoo.petermwenda83.bean.otherfee.OtherFee)
	 */
	@Override
	public boolean updteOtherstype(OtherFee otherFee) {
		boolean success = true;
		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE OtherFee SET Type = ?,Term =? ,"
	             		+ "Year =? WHERE Uuid =? AND SchoolAccountUuid =?;");
	               ) {           			 	            
			   
	           
			  
	            pstmt.setString(1, otherFee.getType());
	            pstmt.setString(2, otherFee.getTerm());
	            pstmt.setString(3, otherFee.getYear());	 
	            pstmt.setString(4, otherFee.getUuid());
	            pstmt.setString(5, otherFee.getSchoolAccountUuid());
	            pstmt.executeUpdate();

} catch (SQLException e) {
      logger.error("SQL Exception when updating OtherFee " + otherFee);
      logger.error(ExceptionUtils.getStackTrace(e));
      System.out.println(ExceptionUtils.getStackTrace(e));
      success = false;
} 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherstypeDAO#getOtherstypeList()
	 */
	@Override
	public List<OtherFee> getOtherstypeList(String schoolAccountUuid,String term,String year) {
		List<OtherFee> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OtherFee WHERE"
        		 		+ " SchoolAccountUuid = ? AND Term = ? AND Year = ?;");
     	   ) {
         	   pstmt.setString(1, schoolAccountUuid);      
         	   pstmt.setString(2, term); 
        	   pstmt.setString(3, year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, OtherFee.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting OtherFee  List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherstypeDAO#gettypeList(java.lang.String)
	 */
	@Override
	public List<OtherFee> gettypeList(String schoolAccountUuid) {
		List<OtherFee> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OtherFee WHERE"
        		 		+ " SchoolAccountUuid = ?;");
     	   ) {
         	   pstmt.setString(1, schoolAccountUuid);      
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, OtherFee.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting OtherFee  List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}

}
