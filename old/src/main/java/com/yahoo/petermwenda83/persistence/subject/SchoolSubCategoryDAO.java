/**
 * 
 */
package com.yahoo.petermwenda83.persistence.subject;

import java.util.List;

import com.yahoo.petermwenda83.bean.subject.SubCategory;

/**
 * @author peter
 *
 */
public interface SchoolSubCategoryDAO {
	
	public SubCategory getSubCategory(String accountId,String subjectId);
	
	public SubCategory getSubCategory(String accountId,String categoryId,String subjectId);
	
	public List<SubCategory> getSubCategoryList(String accountId,String categoryId);
	
	public boolean putSubCategory(SubCategory subCategory);
	
	public boolean deleteSubCategory(String accountId,String uuid);
	
	

}
