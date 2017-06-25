/**
 * 
 */
package com.yahoo.petermwenda83.persistence.subject;

import java.util.List;

import com.yahoo.petermwenda83.bean.subject.SubCategory;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class SubCategoryDAO extends GenericDAO implements SchoolSubCategoryDAO {

	/**
	 * 
	 */
	public SubCategoryDAO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolSubCategoryDAO#getSubCategory(java.lang.String, java.lang.String)
	 */
	@Override
	public SubCategory getSubCategory(String accountId, String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolSubCategoryDAO#getSubCategory(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public SubCategory getSubCategory(String accountId, String categoryId, String subjectId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolSubCategoryDAO#getSubCategoryList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SubCategory> getSubCategoryList(String accountId, String categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolSubCategoryDAO#putSubCategory(com.yahoo.petermwenda83.bean.subject.SubCategory)
	 */
	@Override
	public boolean putSubCategory(SubCategory subCategory) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yahoo.petermwenda83.persistence.subject.SchoolSubCategoryDAO#deleteSubCategory(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteSubCategory(String accountId, String uuid) {
		// TODO Auto-generated method stub
		return false;
	}

}
