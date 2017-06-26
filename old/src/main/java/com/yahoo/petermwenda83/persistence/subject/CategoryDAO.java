/**
 * 
 */
package com.yahoo.petermwenda83.persistence.subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.subject.Category;
import com.yahoo.petermwenda83.persistence.GenericDAO;

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
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolCategoryDAO#getCategoryById(java.lang.String, java.lang.String)
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
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolCategoryDAO#getCategory(java.lang.String, java.lang.String)
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
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolCategoryDAO#getCategoryList(java.lang.String)
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

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolCategoryDAO#putCategory(com.yahoo.petermwenda83.bean.subject.Category)
	 */
	@Override
	public boolean putCategory(Category category) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolCategoryDAO#updateCategory(com.yahoo.petermwenda83.bean.subject.Category)
	 */
	@Override
	public boolean updateCategory(Category category) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolCategoryDAO#deleteCategory(java.lang.String, java.lang.String)
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
