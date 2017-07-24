/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.exam.BarWeight;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class BarWeightDAO  extends GenericDAO implements SchoolBarWeightDAO {

	private static BarWeightDAO barWeightDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static BarWeightDAO getInstance(){
		
		if(barWeightDAO == null){ 
			barWeightDAO = new BarWeightDAO();		
		}
		return barWeightDAO;
	}
	
	/**
	 * 
	 */
	public BarWeightDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public BarWeightDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolBarWeightDAO#getBarWeight(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public BarWeight getBarWeight(String schoolAccountUuid, String studentUuid,String year) {
		BarWeight barWeight = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM BarWeight"
						+ " WHERE schoolAccountUuid = ? AND studentUuid =? AND year =?;");       

				){

			pstmt.setString(1, schoolAccountUuid); 
			pstmt.setString(2, studentUuid); 
			pstmt.setString(3, year); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				barWeight  = beanProcessor.toBean(rset,BarWeight.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting BarWeight: " + barWeight);
			logger.error(ExceptionUtils.getStackTrace(e));

		}
     
		return barWeight; 
	}
	

	@Override
	public boolean ExistBarWeight(String schoolAccountUuid, String studentUuid, String year) {
		boolean studentexist = false;
		
		String the_schoolAccountUuid = "";
		String the_studentUuid = "";
		String the_year = "";
		
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM BarWeight"
						+ " WHERE schoolAccountUuid = ? AND studentUuid =? AND year =?;");       

				){

			pstmt.setString(1, schoolAccountUuid); 
			pstmt.setString(2, studentUuid); 
			pstmt.setString(3, year); 
			rset = pstmt.executeQuery();
			 
            if(rset.next()){
            	the_schoolAccountUuid = rset.getString("schoolAccountUuid");
            	the_studentUuid = rset.getString("studentUuid");
            	the_year = rset.getString("year");
				
				studentexist = (the_schoolAccountUuid != schoolAccountUuid&&
						        the_studentUuid != studentUuid && 
								the_year != year ) ? true : false;
				
			}

		}catch(SQLException e){
			 logger.error("SQL Exception when getting BarWeight: ");
			 logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));

		}

		return studentexist; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolBarWeightDAO#put(com.yahoo.petermwenda83.bean.exam.BarWeight)
	 */
	@Override
	public boolean put(BarWeight weight,String schoolAccountUuid, String studentUuid, String year) {
		boolean success = true;
		if(!ExistBarWeight(schoolAccountUuid,studentUuid,year)){
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO BarWeight" 
			        		+"(Uuid,SchoolAccountUuid,StudentUuid,Year,WeightOne,WeightTwo,WeightThree) VALUES (?,?,?,?,?,?,?);");
      		){
			   
			    pstmt.setString(1, weight.getUuid());
			    pstmt.setString(2, weight.getSchoolAccountUuid());
			    pstmt.setString(3, weight.getStudentUuid());
	            pstmt.setString(4, weight.getYear());
	            pstmt.setDouble(5, weight.getWeightOne());
	            pstmt.setDouble(6, weight.getWeightTwo());
	            pstmt.setDouble(7, weight.getWeightThree());
	           
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put BarWeight: "+weight);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		 
		}else{

			 try (  Connection conn = dbutils.getConnection();
	        	PreparedStatement pstmt = conn.prepareStatement("UPDATE BarWeight SET WeightOne=?,"
	        			+ "WeightTwo=?,WeightThree =? WHERE SchoolAccountUuid = ? AND StudentUuid =?"
	        			+ "AND Year = ?;");
	        	) { 
		            pstmt.setDouble(1, weight.getWeightOne());
		            pstmt.setDouble(2, weight.getWeightTwo());
		            pstmt.setDouble(3, weight.getWeightThree());
		            pstmt.setString(4, weight.getSchoolAccountUuid());
				    pstmt.setString(5, weight.getStudentUuid());
		            pstmt.setString(6, weight.getYear());
	                pstmt.executeUpdate(); 

	        } catch (SQLException e) {
	            logger.error("SQL Exception when updating BarWeight" + weight);
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e));
	            success = false;
	        } 
			
			
		}
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolBarWeightDAO#update(com.yahoo.petermwenda83.bean.exam.BarWeight)
	 */
	@Override
	public boolean update(BarWeight weight) {
		boolean success = true;
        try (  Connection conn = dbutils.getConnection();
        	PreparedStatement pstmt = conn.prepareStatement("UPDATE BarWeight SET WeightOne=?,"
        			+ "WeightTwo=?,WeightThree =? WHERE SchoolAccountUuid = ? AND StudentUuid =?"
        			+ "AND Year = ?;");
        	) { 
	            pstmt.setDouble(1, weight.getWeightOne());
	            pstmt.setDouble(2, weight.getWeightTwo());
	            pstmt.setDouble(3, weight.getWeightThree());
	            pstmt.setString(4, weight.getSchoolAccountUuid());
			    pstmt.setString(5, weight.getStudentUuid());
	            pstmt.setString(6, weight.getYear());
                pstmt.executeUpdate(); 

        } catch (SQLException e) {
            logger.error("SQL Exception when updating BarWeight" + weight);
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
        } 
        
        return success;
	}


}
