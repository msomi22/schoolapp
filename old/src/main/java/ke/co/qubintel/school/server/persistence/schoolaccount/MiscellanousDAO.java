/**
 * 
 */
package ke.co.qubintel.school.server.persistence.schoolaccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.account.Miscellanous;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class MiscellanousDAO extends GenericDAO  implements SchoolMiscellanousDAO {
	
	private static MiscellanousDAO miscellanousDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static MiscellanousDAO getInstance(){
		if(miscellanousDAO == null){
			miscellanousDAO = new MiscellanousDAO();		
		}
		return miscellanousDAO;
	}
	
	/**
	 * 
	 */
	public MiscellanousDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public MiscellanousDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}



	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolMiscellanousDAO#getKey(java.lang.String)
	 */
	@Override
	public String getValueByKey(String accountId,String key) {
		Miscellanous miscellanous = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Miscellanous WHERE accountId =? AND key = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, accountId);
     	 pstmt.setString(2, key);
	      rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 miscellanous  = beanProcessor.toBean(rset,Miscellanous.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Account with key: " + key);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return miscellanous.getValue(); 
	}
	
	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolMiscellanousDAO#getMiscById(java.lang.String, java.lang.String)
	 */
	@Override
	public Miscellanous getMiscById(String accountId, String uuid) {
		Miscellanous miscellanous = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Miscellanous WHERE accountId =? AND uuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, accountId);
     	 pstmt.setString(2, uuid); 
	      rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 miscellanous  = beanProcessor.toBean(rset,Miscellanous.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting for accountId "+accountId+" with uuid: " + uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return miscellanous; 
	}


	

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolMiscellanousDAO#putMiscellanous(ke.co.qubintel.school.server.bean.account.Miscellanous)
	 */
	@Override
	public boolean putMiscellanous(Miscellanous misc) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Miscellanous (uuid,accountId,key,value) VALUES (?,?,?,?);");
    		){
	            pstmt.setString(1, misc.getUuid());
	            pstmt.setString(2, misc.getAccountId());
	            pstmt.setString(3, misc.getKey());
	            pstmt.setString(4, misc.getValue());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			logger.error("SQL Exception trying to put Miscellanous: "+misc);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolMiscellanousDAO#updateMiscellanous(ke.co.qubintel.school.server.bean.account.Miscellanous)
	 */
	@Override
	public boolean updateMiscellanous(Miscellanous misc) {
		boolean success = true; 
		 try(   Connection conn = dbutils.getConnection();
	      PreparedStatement pstmt = conn.prepareStatement("UPDATE Miscellanous SET Value = ? WHERE accountId =? AND key =?;");
      		){
	            pstmt.setString(1, misc.getFullValue());
	            pstmt.setString(2, misc.getAccountId()); 
	            pstmt.setString(3, misc.getKey());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to update Miscellanous: "+misc);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolMiscellanousDAO#getMiscellanousList(java.lang.String)
	 */
	@Override
	public List<Miscellanous> getMiscellanousList(String accountId) {
		 List<Miscellanous> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Miscellanous WHERE accountId = ?;");   
			) {
			 pstmt.setString(1,accountId);

			 try(ResultSet rset = pstmt.executeQuery();){
				 list = beanProcessor.toBeanList(rset, Miscellanous.class);
				}
	        
	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting all Miscellanous");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
		return list;
	}

	
	
}
