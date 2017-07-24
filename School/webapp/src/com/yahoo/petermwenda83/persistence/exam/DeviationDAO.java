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

import com.yahoo.petermwenda83.bean.exam.Deviation;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class DeviationDAO extends GenericDAO implements SchoolDeviationDAO {

	private static DeviationDAO deviationDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static DeviationDAO getInstance(){
		
		if(deviationDAO == null){ 
			deviationDAO = new DeviationDAO();		
		}
		return deviationDAO;
	}
	
	/**
	 * 
	 */
	public DeviationDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public DeviationDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/** 
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolDeviationDAO#getDev(java.lang.String, java.lang.String)
	 */
	@Override
	public Deviation getDev(String studentUuid, String year) {
		Deviation dev = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Deviation"
						+ " WHERE studentUuid =? AND year =?;");       

				){
			pstmt.setString(1, studentUuid); 
			pstmt.setString(2, year); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				dev  = beanProcessor.toBean(rset,Deviation.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting BarWeight: " + dev);
			logger.error(ExceptionUtils.getStackTrace(e));

		}
     
		return dev; 
	}


	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolDeviationDAO#getStudentdevone(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Deviation getStudentdevone(String year, double devone) {
		Deviation dev = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Deviation"
						+ " WHERE year =? AND devone =?;");       

				){
			//pstmt.setString(1, studentUuid); 
			pstmt.setString(1, year); 
			pstmt.setDouble(2, devone); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				dev  = beanProcessor.toBean(rset,Deviation.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Deviation: " + dev);
			logger.error(ExceptionUtils.getStackTrace(e));

		}
     
		return dev; 
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolDeviationDAO#getStudentdevtwo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Deviation getStudentdevtwo(String year, double devtwo) {
		Deviation dev = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Deviation"
						+ " WHERE year =? AND devone =?;");       

				){
		   //pstmt.setString(1, studentUuid); 
			pstmt.setString(1, year); 
			pstmt.setDouble(2, devtwo); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				dev  = beanProcessor.toBean(rset,Deviation.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Deviation: " + dev);
			logger.error(ExceptionUtils.getStackTrace(e));

		}
     
		return dev; 
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolDeviationDAO#getStudentdevthree(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Deviation getStudentdevthree(String year, double devthree) {
		Deviation dev = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Deviation"
						+ " WHERE year =? AND devthree =?;");       

				){
			//pstmt.setString(1, studentUuid); 
			pstmt.setString(1, year); 
			pstmt.setDouble(2, devthree); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				dev  = beanProcessor.toBean(rset,Deviation.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Deviation: " + dev);
			logger.error(ExceptionUtils.getStackTrace(e));

		}
     
		return dev; 
	}

	

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolDeviationDAO#DevExist(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean DevExist(String studentUuid, String year) {
      boolean studentexist = false;
	
		String the_studentUuid = "";
		String the_year = "";
		
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Deviation"
						+ " WHERE studentUuid =? AND year =?;");       

				){

			pstmt.setString(1, studentUuid); 
			pstmt.setString(2, year); 
			rset = pstmt.executeQuery();
			 
            if(rset.next()){
            	the_studentUuid = rset.getString("studentUuid");
            	the_year = rset.getString("year");
				
				studentexist = (the_studentUuid != studentUuid && 
								the_year != year ) ? true : false;
				
			}

		}catch(SQLException e){
			 logger.error("SQL Exception when getting Deviation: ");
			 logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));

		}

		return studentexist; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolDeviationDAO#putDev(com.yahoo.petermwenda83.bean.exam.Deviation)
	 */
	@Override
	public boolean putDev(Deviation dev,String studentUuid, String year) {
		boolean success = true;
		if(!DevExist(studentUuid,year)){
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Deviation" 
			        		+"(Uuid,StudentUuid,Year,DevOne,DevTwo,DevThree) VALUES (?,?,?,?,?,?);");
      		){
			   
			    pstmt.setString(1, dev.getUuid());
			    pstmt.setString(2, dev.getStudentUuid());
	            pstmt.setString(3, dev.getYear());
	            pstmt.setDouble(4, dev.getDevOne());
	            pstmt.setDouble(5, dev.getDevTwo());
	            pstmt.setDouble(6, dev.getDevThree());
	           
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put Deviation: "+dev);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		 
		}else{

			 try (  Connection conn = dbutils.getConnection();
	        	PreparedStatement pstmt = conn.prepareStatement("UPDATE Deviation SET DevOne=?,"
	        			+ "DevTwo=?,DevThree =? WHERE StudentUuid =? AND Year = ?;");
	        	) { 
				    pstmt.setDouble(1, dev.getDevOne());
		            pstmt.setDouble(2, dev.getDevTwo());
		            pstmt.setDouble(3, dev.getDevThree());
				    pstmt.setString(4, dev.getStudentUuid());
		            pstmt.setString(5, dev.getYear());
	                pstmt.executeUpdate(); 

	        } catch (SQLException e) {
	            logger.error("SQL Exception when updating Deviation" + dev);
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e));
	            success = false;
	        } 
			
			
		}
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolDeviationDAO#getDeviation()
	 */
	@Override
	public List<Deviation> getDeviationList(String year) {
		List<Deviation> list =null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Deviation WHERE year = ?;");
				) {
			psmt.setString(1, year);
			try(ResultSet rset = psmt.executeQuery();){
			
				list = beanProcessor.toBeanList(rset, Deviation.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get Deviation List for year "+year);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return list;
	}

}
