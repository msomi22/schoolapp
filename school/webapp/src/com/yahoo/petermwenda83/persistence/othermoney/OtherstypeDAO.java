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

import com.yahoo.petermwenda83.bean.othermoney.Otherstype;
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
	public Otherstype getOtherstype(String Uuid) {
		Otherstype otherstype = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Otherstype WHERE Uuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, Uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 otherstype  = beanProcessor.toBean(rset,Otherstype.class);
	   }
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Otherstype with Uuid: " + Uuid );
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return otherstype; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherstypeDAO#putOtherstype(com.yahoo.petermwenda83.bean.othermoney.Otherstype)
	 */
	@Override
	public boolean putOtherstype(Otherstype otherstype) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Otherstype" 
			        		+"(Uuid,SchoolAccountUuid,Type,Term,Year) VALUES (?,?,?,?,?);");
       		){
			   
	            pstmt.setString(1, otherstype.getUuid());
	            pstmt.setString(2, otherstype.getSchoolAccountUuid());
	            pstmt.setString(3, otherstype.getType());
	            pstmt.setString(4, otherstype.getTerm());
	            pstmt.setString(5, otherstype.getYear());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put otherstype: "+otherstype);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherstypeDAO#updteOtherstype(com.yahoo.petermwenda83.bean.othermoney.Otherstype)
	 */
	@Override
	public boolean updteOtherstype(Otherstype otherstype) {
		boolean success = true;
		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE Otherstype SET Type = ?,Term =? ,"
	             		+ "Year =? WHERE Uuid =? AND SchoolAccountUuid =?;");
	               ) {           			 	            
			   
	           
			  
	            pstmt.setString(1, otherstype.getType());
	            pstmt.setString(2, otherstype.getTerm());
	            pstmt.setString(3, otherstype.getYear());	 
	            pstmt.setString(4, otherstype.getUuid());
	            pstmt.setString(5, otherstype.getSchoolAccountUuid());
	            pstmt.executeUpdate();

} catch (SQLException e) {
      logger.error("SQL Exception when updating Otherstype " + otherstype);
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
	public List<Otherstype> getOtherstypeList(String schoolAccountUuid,String term,String year) {
		List<Otherstype> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Otherstype WHERE"
        		 		+ " SchoolAccountUuid = ? AND Term = ? AND Year = ?;");
     	   ) {
         	   pstmt.setString(1, schoolAccountUuid);      
         	   pstmt.setString(2, term); 
        	   pstmt.setString(3, year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Otherstype.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Otherstype  List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.othermoney.SchoolOtherstypeDAO#gettypeList(java.lang.String)
	 */
	@Override
	public List<Otherstype> gettypeList(String schoolAccountUuid) {
		List<Otherstype> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
        		 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Otherstype WHERE"
        		 		+ " SchoolAccountUuid = ?;");
     	   ) {
         	   pstmt.setString(1, schoolAccountUuid);      
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Otherstype.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Otherstype  List"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
      
        return list;
	}

}
