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

import ke.co.qubintel.school.server.bean.subject.Category;
import ke.co.qubintel.school.server.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class CategoryDAO extends GenericDAO implements SchoolCategoryDAO {

	private static CategoryDAO categoryDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return CategoryDAO
	 */
	public static CategoryDAO getInstance(){
		if(categoryDAO == null){
			categoryDAO = new CategoryDAO();		
		}
		return categoryDAO;
	}

	/**
	 * 
	 */
	public CategoryDAO() {
		super();
	}

	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public CategoryDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolCategoryDAO#getCategoryById(java.lang.String, java.lang.String)
	 */
	@Override
	public Category getCategoryById(String accountId, String uuid) {
		Category category = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Category WHERE accountId = ? AND"
						+ " uuid =?;");       

				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				category  = beanProcessor.toBean(rset,Category.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Category with uuid '" + uuid + "' for account '" + accountId+"'");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return category; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolCategoryDAO#getCategory(java.lang.String, java.lang.String)
	 */
	@Override
	public Category getCategory(String accountId, String description) {
		Category category = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Category WHERE accountId = ? AND"
						+ " description =?;");       

				){
			pstmt.setString(1, accountId);
			pstmt.setString(2, description);
			rset = pstmt.executeQuery();
			while(rset.next()){
				category  = beanProcessor.toBean(rset,Category.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Category with description '" + description + "' for account '" + accountId+"'");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return category; 
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolCategoryDAO#getCategoryList(java.lang.String)
	 */
	@Override
	public List<Category> getCategoryList(String accountId) {
		List<Category>  list = null;		
		try(   
			 Connection conn = dbutils.getConnection();
			 PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Category WHERE accountId =?;");          		
				) {	
			pstmt.setString(1, accountId);
			
			try(ResultSet rset = pstmt.executeQuery();){
			
				list = beanProcessor.toBeanList(rset, Category.class);
			}
		
		} catch(SQLException e){
			logger.error("SQL Exception when getting Category List for accountId " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolCategoryDAO#putCategory(ke.co.qubintel.school.server.bean.subject.Category)
	 */
	@Override
	public boolean putCategory(Category category) {
		
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Category" 
						+"(uuid,accountId,description,maxNo) VALUES (?,?,?,?);");
				){

			pstmt.setString(1, category.getUuid());
			pstmt.setString(2, category.getAccountId());
			pstmt.setString(3, category.getDescription());
			pstmt.setInt(4, category.getMaxNo());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put Category " + category);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolCategoryDAO#updateCategory(ke.co.qubintel.school.server.bean.subject.Category)
	 */
	@Override
	public boolean updateCategory(Category category) {

		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Category SET description = ?, maxNo =? "
						+ "WHERE uuid = ? AND accountId = ?;");
				) {           			 	            
			pstmt.setString(1, category.getDescription());
			pstmt.setInt(2, category.getMaxNo());
			pstmt.setString(3, category.getUuid());
			pstmt.setString(4, category.getAccountId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating category " + category);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.qubintel.school.server.persistence.subject.SchoolCategoryDAO#deleteCategory(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteCategory(String accountId, String uuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Category WHERE accountId= ? AND uuid = ?;");       

				){

			pstmt.setString(1, accountId);
			pstmt.setString(2, uuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting Category with id " + uuid + " for account " + accountId);
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success; 
	}

}
