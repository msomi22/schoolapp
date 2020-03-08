/**
 * 
 */
package ke.co.qubintel.school.server.persistence.subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.qubintel.school.server.bean.subject.SubCategory;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class SubCategoryDAO extends GenericDAO implements SchoolSubCategoryDAO {
	
	private static SubCategoryDAO subCategoryDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return SubCategoryDAO
	 */
	public static SubCategoryDAO getInstance(){
		if(subCategoryDAO == null){
			subCategoryDAO = new SubCategoryDAO();		
		}
		return subCategoryDAO;
	}

	/**
	 * 
	 */
	public SubCategoryDAO() {
		super();
	}

	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public SubCategoryDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubCategoryDAO#getSubCategory(java.lang.String, java.lang.String)
	 */
	@Override
	public SubCategory getSubCategory(String accountId, String subjectId) {
		SubCategory subCategory = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SubCategory WHERE accountId = ? AND"
						+ " subjectId =?;");       

				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, subjectId);
			rset = pstmt.executeQuery();
			while(rset.next()){
				subCategory  = beanProcessor.toBean(rset,SubCategory.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting SubCategory with subjectId '" + subjectId + "' for account '" + accountId+"'");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return subCategory; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubCategoryDAO#getSubCategory(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public SubCategory getSubCategory(String accountId, String categoryId, String subjectId) {
		SubCategory subCategory = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SubCategory WHERE accountId = ? AND"
						+ " categoryId =? AND subjectId =?;");       

				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, categoryId);
			pstmt.setString(3, subjectId);
			rset = pstmt.executeQuery();
			while(rset.next()){
				subCategory  = beanProcessor.toBean(rset,SubCategory.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting SubCategory with subjectId '" + subjectId + "' for categoryId '" + categoryId +"'");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return subCategory; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubCategoryDAO#getSubCategoryList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SubCategory> getSubCategoryList(String accountId, String categoryId) {
		List<SubCategory>  list = null;		
		try(   
			 Connection conn = dbutils.getConnection();
			 PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM SubCategory WHERE accountId =? AND categoryId =?;");          		
				) {	
			pstmt.setString(1, accountId);
			pstmt.setString(2, categoryId);
			
			try(ResultSet rset = pstmt.executeQuery();){
			
				list = beanProcessor.toBeanList(rset, SubCategory.class);
			}
		
		} catch(SQLException e){
			logger.error("SQL Exception when getting SubCategory List for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubCategoryDAO#putSubCategory(ke.co.qubintel.school.server.bean.subject.SubCategory)
	 */
	@Override
	public boolean putSubCategory(SubCategory subCategory) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SubCategory (uuid,accountId,categoryId,subjectId) VALUES (?,?,?,?);");
   		){
	            pstmt.setString(1, subCategory.getUuid());
	            pstmt.setString(2, subCategory.getAccountId());
	            pstmt.setString(3, subCategory.getCategoryId());
	            pstmt.setString(4, subCategory.getSubjectId());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put SubCategory " + subCategory);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }
		
		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolSubCategoryDAO#deleteSubCategory(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteSubCategory(String accountId, String uuid) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM SubCategory"
	         	      		+ " WHERE accountId = ? AND uuid =?;");       
	      		){
	      	
	      	     pstmt.setString(1, accountId);
	      	     pstmt.setString(2, uuid);
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting SubCategory for accountId " + accountId + " with stream id " + uuid);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

}
