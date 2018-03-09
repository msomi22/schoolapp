/**
 * 
 */
package ke.co.qubintel.school.server.persistence.subject;

import java.util.List;

import ke.co.qubintel.school.server.bean.subject.Category;

/**
 * @author peter
 *
 */
public interface SchoolCategoryDAO {
	
	public Category getCategoryById(String accountId,String uuid);
	
	public Category getCategory(String accountId,String description);
	
	public List<Category> getCategoryList(String accountId); 
	
	public boolean putCategory(Category category);
	
	public boolean updateCategory(Category category);
	
	public boolean deleteCategory(String accountId,String uuid);

}
