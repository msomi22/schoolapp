
package com.yahoo.petermwenda83.persistence.guardian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor;
import com.yahoo.petermwenda83.persistence.GenericDAO;


/** 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class SponsorsDAO extends GenericDAO implements SchoolSponsorsDAO {

	private static SponsorsDAO sponsorsDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static SponsorsDAO getInstance(){
		
		if(sponsorsDAO == null){
			sponsorsDAO = new SponsorsDAO();		
		}
		return sponsorsDAO;
	}
	
	/**
	 * 
	 */
	public SponsorsDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public SponsorsDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolSponsorsDAO#getSponsor(java.lang.String)
	 */
	@Override
	public StudentSponsor getSponsor(String studentUuid) {
		StudentSponsor studentSponsor = null;
		ResultSet rset = null;
		
		  try(   Connection conn = dbutils.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentSponsor"
						+ " WHERE studentUuid =?;");
		         ){
			  pstmt.setString(1, studentUuid); 
		      rset = pstmt.executeQuery();
		     while(rset.next()){
		    	 studentSponsor  = beanProcessor.toBean(rset,StudentSponsor.class);
		   }
	        	
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to get StudentSponsor with studentuuid: "+studentUuid);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
    
		 }
		return studentSponsor;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolSponsorsDAO#putSponsor(com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor)
	 */
	@Override
	public boolean putSponsor(StudentSponsor sponsor) {
		 boolean success = true;
			
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentSponsor" 
			        		+"(Uuid, StudentUuid, SponsorName,SponsorPhone,SponsorOccupation,SponsorCountry,SponsorCounty)"
			        		+ " VALUES (?,?,?,?,?,?,?);");
		             ){
	            pstmt.setString(1, sponsor.getUuid());
	            pstmt.setString(2, sponsor.getStudentUuid());
	            pstmt.setString(3, sponsor.getSponsorName());	       
	            pstmt.setString(4, sponsor.getSponsorPhone());
	            pstmt.setString(5, sponsor.getSponsorOccupation());
	            pstmt.setString(6, sponsor.getSponsorCountry());
	            pstmt.setString(7, sponsor.getSponsorCounty());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put StudentSponsor"+sponsor);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolSponsorsDAO#updateSponsor(com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor)
	 */
	@Override
	public boolean updateSponsor(StudentSponsor sponsor) {
		boolean success = true;
		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentSponsor SET SponsorName = ?,SponsorPhone = ?,SponsorOccupation = ?,"
	             		+ "SponsorCountry =?,SponsorCounty =? WHERE StudentUuid = ?;");
	               ) {           			 	            
	            pstmt.setString(1, sponsor.getSponsorName());
	            pstmt.setString(2, sponsor.getSponsorPhone());
	            pstmt.setString(3, sponsor.getSponsorOccupation());
	            pstmt.setString(4, sponsor.getSponsorCountry());
	            pstmt.setString(5, sponsor.getSponsorCounty());
	            pstmt.setString(6, sponsor.getStudentUuid());	           
	            pstmt.executeUpdate();

    } catch (SQLException e) {
      logger.error("SQL Exception when updating update StudentSponsor " + sponsor);
      logger.error(ExceptionUtils.getStackTrace(e));
      System.out.println(ExceptionUtils.getStackTrace(e));
      success = false;
   } 
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolSponsorsDAO#deleteSponsor(com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor)
	 */
	@Override
	public boolean deleteSponsor(StudentSponsor sponser) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentSponsor"
	         	      		+ " WHERE StudentUuid =?;");       
	      		){
	      	
	      	     pstmt.setString(1, sponser.getStudentUuid());
		         pstmt.executeUpdate();
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting StudentSponsor " +sponser);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolSponsorsDAO#getStudentSponsorList()
	 */
	@Override
	public List<StudentSponsor> getStudentSponsorList() {
		List<StudentSponsor> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentSponsor;");   
	  		ResultSet rset = pstmt.executeQuery();
			) {
	  	
	      list = beanProcessor.toBeanList(rset, StudentSponsor.class);

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting Sponsor List");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
	  return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.guardian.SchoolSponsorsDAO#getStudentSponsorList(int, int)
	 */
	@Override
	public List<StudentSponsor> getStudentSponsorList(int startIndex, int endIndex) {
		List<StudentSponsor> sponsorList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentSponsor LIMIT ? OFFSET ? ;");
				) {
			psmt.setInt(1, endIndex - startIndex);
			psmt.setInt(2, startIndex);
			
			try(ResultSet rset = psmt.executeQuery();){
				sponsorList = beanProcessor.toBeanList(rset, StudentSponsor.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a sponsorList.");
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return sponsorList;		
	}


	
	

}
