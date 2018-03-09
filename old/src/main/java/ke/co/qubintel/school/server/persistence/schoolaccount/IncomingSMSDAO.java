/**
 * 
 */
package ke.co.qubintel.school.server.persistence.schoolaccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.account.IncomingSMS;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class IncomingSMSDAO extends GenericDAO implements SchoolIncomingSMSDAO {

	private static IncomingSMSDAO incomingSMSDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static IncomingSMSDAO getInstance(){
		if(incomingSMSDAO == null){
			incomingSMSDAO = new IncomingSMSDAO();		
		}
		return incomingSMSDAO;
	}
	
	/**
	 * 
	 */
	public IncomingSMSDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public IncomingSMSDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolIncomingSMSDAO#getIncomingSMS(java.lang.String, java.lang.String)
	 */
	@Override
	public IncomingSMS getIncomingSMS(String accountId, String uuid) {
		IncomingSMS incomingSMS = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM IncomingSMS WHERE accountId = ? AND uuid =?;");       
				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				incomingSMS  = beanProcessor.toBean(rset,IncomingSMS.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting an IncomingSMS for accountId " + accountId + " and " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return incomingSMS; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolIncomingSMSDAO#getIncomingSMSList(java.lang.String, int, int)
	 */
	@Override
	public List<IncomingSMS> getIncomingSMSList(String accountId, int startIndex, int endIndex) {
		List<IncomingSMS> incomingSMSList = new ArrayList<>();

		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM IncomingSMS WHERE "
						+ "accountId = ?  LIMIT ? OFFSET ? ;");
				) {
			psmt.setString(1, accountId);
			psmt.setInt(2, endIndex - startIndex);
			psmt.setInt(3, startIndex);

			try(ResultSet rset = psmt.executeQuery();){
				incomingSMSList = beanProcessor.toBeanList(rset, IncomingSMS.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a IncomingSMS List  for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}
		return incomingSMSList;		
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolIncomingSMSDAO#putIncomingSMS(ke.co.qubintel.school.server.bean.account.IncomingSMS)
	 */
	@Override
	public boolean putIncomingSMS(IncomingSMS incomingSMS) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO IncomingSMS" 
						+"(uuid,accountId,mobile,message,receiveDate) VALUES (?,?,?,?,?);");
				){

			pstmt.setString(1, incomingSMS.getUuid());
			pstmt.setString(2, incomingSMS.getAccountId());
			pstmt.setString(3, incomingSMS.getMobile());
			pstmt.setString(4, incomingSMS.getMessage());
			pstmt.setTimestamp(5, incomingSMS.getReceiveDate());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put IncomingSMS " + incomingSMS);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolIncomingSMSDAO#deleteIncomingSMS(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteIncomingSMS(String accountId, String uuid) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM IncomingSMS WHERE accountId =? AND uuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, accountId);
        	 pstmt.setString(2, uuid);
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting IncomingSMS for accountId " + accountId + "and uuid " + uuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
             
        }
        
		return success; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.schoolaccount.SchoolIncomingSMSDAO#deleteIncomingSMS(java.lang.String)
	 */
	@Override
	public boolean deleteIncomingSMS(String accountId) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM IncomingSMS WHERE accountId =?;");       
        		
        		){
        	
        	 pstmt.setString(1, accountId);
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting IncomingSMS for accountId " + accountId);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
        }
        
		return success; 
	}

}
