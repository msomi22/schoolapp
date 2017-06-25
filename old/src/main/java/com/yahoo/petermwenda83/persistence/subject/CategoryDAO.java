/**
 * 
 */
package com.yahoo.petermwenda83.persistence.subject;

import java.util.List;

import com.yahoo.petermwenda83.bean.subject.Category;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class CategoryDAO extends GenericDAO implements SchoolCategoryDAO {

	/**
	 * 
	 */
	public CategoryDAO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolCategoryDAO#getCategoryById(java.lang.String, java.lang.String)
	 */
	@Override
	public Category getCategoryById(String accountId, String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolCategoryDAO#getCategory(java.lang.String, java.lang.String)
	 */
	@Override
	public Category getCategory(String accountId, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolCategoryDAO#getCategoryList(java.lang.String)
	 */
	@Override
	public List<Category> getCategoryList(String accountId) {
		// TODO Auto-generated method stub
		return null;
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

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolCategoryDAO#deleteCategory(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteCategory(String accountId, String uuid) {
		// TODO Auto-generated method stub
		return false;
	}

}
